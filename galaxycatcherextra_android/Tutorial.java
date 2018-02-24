package galaxysoftware.galaxycatcherextra_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import galaxysoftware.galaxycatcherextra_android.GameControlManager;
import galaxysoftware.galaxycatcherextra_android.StageClear;

public class Tutorial extends AppCompatActivity
        implements SurfaceHolder.Callback, Runnable, OnClickListener, OnTouchListener {

    SurfaceView SV;
    Thread thread = null;
    boolean GET, GET2, Loaded, Debug, Loop, SurfaceReady;
    int Prize1X, Prize1Y, Prize2X, Prize2Y, YBase3D, Shadow1, Shadow2, Drop, LeftSlide, RightSlide, YMoveWay;
    Bitmap Prize, Crane, Wallpaper, Galaxy, Wall, Plate;
    TextView CoinView, plays;
    ImageButton yoko, tate, stop, input, Target, Restart, Retire;
    ImageView CoinBG, TitlePlate, Price, PlayLeft;
    GameControlManager Manager;
    Canvas canvas;
    Random RX = new Random();
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage);
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT && !Loaded) {
            Manager = (GameControlManager) this.getApplication();
            input = (ImageButton) findViewById(R.id.input);
            CoinBG = (ImageView) findViewById(R.id.CoinBG);
            CoinView = (TextView) findViewById(R.id.Coin);
            plays = (TextView) findViewById(R.id.Playleft);
            yoko = (ImageButton) findViewById(R.id.yoko);
            tate = (ImageButton) findViewById(R.id.tate);
            stop = (ImageButton) findViewById(R.id.stop);
            TitlePlate = (ImageView) findViewById(R.id.TitlePlate);
            Price = (ImageView) findViewById(R.id.display);
            PlayLeft = (ImageView) findViewById(R.id.PlayBG);
            Target = (ImageButton) findViewById(R.id.Target);
            Restart = (ImageButton) findViewById(R.id.Restart);
            Retire = (ImageButton) findViewById(R.id.Retire);
            input.setOnClickListener(this);
            Retire.setOnClickListener(this);
            Restart.setOnClickListener(this);
            Target.setOnClickListener(this);
            yoko.setOnTouchListener(this);
            tate.setOnTouchListener(this);
            stop.setOnClickListener(this);
            SV = (SurfaceView) findViewById(R.id.surfaceView);
            SurfaceHolder holder = SV.getHolder();
            holder.setFormat(PixelFormat.TRANSLUCENT);
            holder.addCallback(this);
            SV.setFocusable(true);
            SV.setZOrderOnTop(true);
            YBase3D = (Manager.SurfaceHeight - (int) (Manager.SurfaceHeight * 0.13));
            Manager.Load();
            CoinView.setText("" + Manager.GameCoin);
            Manager.GameBGM.seekTo(0);
            ViewGroup.LayoutParams params = plays.getLayoutParams();
            params.height = (int) (Manager.ScreenHeight * 0.08);
            params.width = (int) (Manager.ScreenWidth * 0.06);
            plays.setLayoutParams(params);
            Bitmap P1 = BitmapFactory.decodeResource(getResources(), Manager.img);
            // createScaledBitmapでクラッシュする時は、SurfaceWidth,Heightの値が保存してあるか一度チェックしよう
            Prize = Bitmap.createScaledBitmap(P1, (int) (Manager.SurfaceWidth * 0.15),
                    (int) (Manager.SurfaceHeight * 0.15), false);
            P1.recycle();
            Bitmap platesource = BitmapFactory.decodeResource(getResources(), R.drawable.label);
            Bitmap platenew = Bitmap.createScaledBitmap(platesource, Manager.SurfaceWidth,
                    (int) (Manager.ScreenHeight * 0.05), false);
            platesource.recycle();
            Bitmap inputsource = BitmapFactory.decodeResource(getResources(), R.drawable.coininput);
            Bitmap inputnew = Bitmap.createScaledBitmap(inputsource, (int) (Manager.ScreenWidth * 0.07),
                    (int) (Manager.ScreenHeight * 0.08), false);
            inputsource.recycle();
            Bitmap pricesource = BitmapFactory.decodeResource(getResources(), R.drawable.pricedisplay);
            Bitmap pricenew = Bitmap.createScaledBitmap(pricesource, (int) (Manager.ScreenWidth * 0.25),
                    (int) (Manager.ScreenHeight * 0.08), false);
            pricesource.recycle();
            Bitmap playsource = BitmapFactory.decodeResource(getResources(), R.drawable.playleft);
            Bitmap playnew = Bitmap.createScaledBitmap(playsource, (int) (Manager.ScreenWidth * 0.07),
                    (int) (Manager.ScreenHeight * 0.08), false);
            playsource.recycle();
            Bitmap coinsource = BitmapFactory.decodeResource(getResources(), R.drawable.coinbackground);
            Bitmap coinnew = Bitmap.createScaledBitmap(coinsource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.05), false);
            coinsource.recycle();
            Bitmap yokosource = BitmapFactory.decodeResource(getResources(), R.drawable.rightarrow);
            Bitmap yokonew = Bitmap.createScaledBitmap(yokosource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.08), false);
            yokosource.recycle();
            Bitmap tatesource = BitmapFactory.decodeResource(getResources(), R.drawable.uparrow);
            Bitmap tatenew = Bitmap.createScaledBitmap(tatesource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.08), false);
            tatesource.recycle();
            Bitmap stopsource = BitmapFactory.decodeResource(getResources(), R.drawable.stop);
            Bitmap stopnew = Bitmap.createScaledBitmap(stopsource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.08), false);
            stopsource.recycle();
            Bitmap targetsource = BitmapFactory.decodeResource(getResources(), R.drawable.target);
            Bitmap targetnew = Bitmap.createScaledBitmap(targetsource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.05), false);
            targetsource.recycle();
            Bitmap restartsource = BitmapFactory.decodeResource(getResources(), R.drawable.restart);
            Bitmap restartnew = Bitmap.createScaledBitmap(restartsource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.05), false);
            restartsource.recycle();
            Bitmap retiresource = BitmapFactory.decodeResource(getResources(), R.drawable.retire);
            Bitmap retirenew = Bitmap.createScaledBitmap(retiresource, (int) (Manager.ScreenWidth * 0.1),
                    (int) (Manager.ScreenHeight * 0.05), false);
            retiresource.recycle();
            yoko.setImageBitmap(yokonew);
            tate.setImageBitmap(tatenew);
            stop.setImageBitmap(stopnew);
            TitlePlate.setImageBitmap(platenew);
            input.setImageBitmap(inputnew);
            CoinBG.setImageBitmap(coinnew);
            Price.setImageBitmap(pricenew);
            PlayLeft.setImageBitmap(playnew);
            Target.setImageBitmap(targetnew);
            Restart.setImageBitmap(restartnew);
            Retire.setImageBitmap(retirenew);
            Bitmap wallpbase = BitmapFactory.decodeResource(getResources(), R.drawable.titleicon);
            Wallpaper = Bitmap.createScaledBitmap(wallpbase, (int) (Manager.SurfaceWidth * 0.7), (int) (Manager.SurfaceHeight * 0.55), false);
            wallpbase.recycle();
            Bitmap galaxybase = BitmapFactory.decodeResource(getResources(), R.drawable.galaxy);
            Galaxy = Bitmap.createScaledBitmap(galaxybase, Manager.SurfaceWidth, Manager.SurfaceHeight, false);
            galaxybase.recycle();
            Bitmap cbase = BitmapFactory.decodeResource(getResources(), R.drawable.crane);
            Crane = Bitmap.createScaledBitmap(cbase, (int) (Manager.SurfaceWidth * 0.26), (int) (Manager.SurfaceHeight * 0.12), false);
            cbase.recycle();
            Bitmap wallbase = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
            Wall = Bitmap.createScaledBitmap(wallbase, Manager.SurfaceWidth, (int) (Manager.SurfaceHeight * 0.6), false);
            wallbase.recycle();
            Bitmap platebase = BitmapFactory.decodeResource(getResources(), R.drawable.plate);
            Plate = Bitmap.createScaledBitmap(platebase, Manager.SurfaceWidth, (int) (Manager.SurfaceHeight * 0.4), false);
            platebase.recycle();
            adView = (AdView) findViewById(R.id.adView);
            AdRequest adRequestBuilder = new AdRequest.Builder().build();
            adView.loadAd(adRequestBuilder);
            restart();
            Loaded = true;
        }
    }

    @Override
    public void run() {
        while (Loop) {
            if (!SurfaceReady && !Loaded) {
                continue;
            }
            canvas = this.SV.getHolder().lockCanvas();
            if (canvas == null) {
                continue;
            }
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (Manager.yokoMove) {
                Manager.yokoMove();
            }
            if (Manager.tateMove) {
                Manager.tateMove();
            }
            if (Manager.openarm) {
                Manager.openarm();
            }
            if (Manager.godown) {
                Manager.godown();
                if (Manager.LeftArmStopX > Prize1X && Manager.LeftArmStopX < Prize1X + Prize.getWidth()
                        && Manager.pointer >= Prize1Y && Manager.pointer <= Prize1Y + Prize.getHeight()) {
                    if (Manager.LeftArmBottom > Prize1Y) {
                        Manager.LeftStop = true;
                        Manager.Reached2obj();
                    }
                }
                if (Manager.RightArmStopX > Prize1X && Manager.RightArmStopX < Prize1X + Prize.getWidth()
                        && Manager.pointer >= Prize1Y && Manager.pointer <= Prize1Y + Prize.getHeight()) {
                    if (Manager.RightArmBottom > Prize1Y) {
                        Manager.RightStop = true;
                        Manager.Reached2obj();
                    }
                }
                if (Manager.LeftArmStopX > Prize2X && Manager.LeftArmStopX < Prize2X + Prize.getWidth()
                        && Manager.pointer >= Prize1Y && Manager.pointer <= Prize1Y + Prize.getHeight()) {
                    if (Manager.LeftArmBottom > Prize1Y) {
                        Manager.LeftStop = true;
                        Manager.Reached2obj();
                    }
                }
                if (Manager.RightArmStopX > Prize2X && Manager.RightArmStopX < Prize2X + Prize.getWidth()
                        && Manager.pointer >= Prize1Y && Manager.pointer <= Prize1Y + Prize.getHeight()) {
                    if (Manager.RightArmBottom > Prize1Y) {
                        Manager.RightStop = true;
                        Manager.Reached2obj();
                    }
                }
            }
            if (Manager.closearm) {
                Manager.CloseArm();
                CloseArm();
            }
            if (Manager.goup) {
                Manager.goup();
                LiftPrize();
            }
            if (Manager.back2pos) {
                Manager.backXY();
                CarryPrize();
            }
            if (Manager.Prizeout) {
                Manager.Prizeout();
                if (Manager.GameClear) {
                    GameClear();
                }
            }
            if (Drop == 1) {
                Prize1Y = Drop(Prize1Y);
            }
            if (Drop == 2) {
                Prize2Y = Drop(Prize2Y);
            }
            canvas.drawBitmap(Galaxy, 0, 0, null);
            canvas.drawBitmap(Wall, 0, 0, null);
            canvas.drawBitmap(Plate, 0, (int) (Manager.SurfaceHeight * 0.6), null);
            canvas.drawBitmap(Wallpaper, (int) (Manager.SurfaceWidth * 0.15), (int) (Manager.SurfaceHeight * 0.03), null);
            Paint a = new Paint();
            a.setARGB(255, 0, 0, 0);
            canvas.drawLine(0, (int) (Manager.SurfaceHeight * 0.87), Manager.SurfaceWidth, (int) (Manager.SurfaceHeight * 0.87), a);
            if (!GET) {
                canvas.drawBitmap(Prize, Prize1X, Prize1Y, null);
            }
            if (Manager.CATCH == 1) {
                canvas.drawLine(Prize1X, Shadow1, Prize1X + Prize.getWidth(), Shadow1, Manager.Shadow);
            }
            if (!GET2) {
                canvas.drawBitmap(Prize, Prize2X, Prize2Y, null);
            }
            if (Manager.CATCH == 2) {
                canvas.drawLine(Prize2X, Shadow2, Prize2X + Prize.getWidth(), Shadow2, Manager.Shadow);
            }
            canvas.drawBitmap(Crane, (int) (Manager.SurfaceWidth * 0.03) + Manager.Xmove,
                    (int) (Manager.SurfaceHeight * 0.4) + Manager.Ymove, null);
            canvas.drawLine(Manager.LeftArmStartX, Manager.LeftArmStartY, Manager.LeftArmMiddleX,
                    Manager.LeftArmMiddleY, Manager.arm);
            canvas.drawCircle(Manager.LeftArmMiddleX, Manager.LeftArmMiddleY, Manager.ArmStrokeWidth / 2, Manager.arm);
            canvas.drawLine(Manager.LeftArmMiddleX, Manager.LeftArmStopY, Manager.LeftArmStopX, Manager.LeftArmBottom, Manager.arm);
            canvas.drawLine(Manager.RightArmStartX, Manager.RightArmStartY, Manager.RightArmMiddleX,
                    Manager.RightArmMiddleY, Manager.arm);
            canvas.drawCircle(Manager.RightArmMiddleX, Manager.RightArmMiddleY, Manager.ArmStrokeWidth / 2, Manager.arm);
            canvas.drawLine(Manager.RightArmMiddleX, Manager.RightArmStopY, Manager.RightArmStopX,
                    Manager.RightArmBottom, Manager.arm);
            canvas.drawLine((int) (Manager.SurfaceWidth * 0.16) + Manager.Xmove, 0, (int) (Manager.SurfaceWidth * 0.16) + Manager.Xmove,
                    (int) (Manager.SurfaceHeight * 0.4) + Manager.Ymove, Manager.machine);
            if (Manager.Pointer) {
                canvas.drawLine(Manager.LeftArmStartX, Manager.pointer, Manager.RightArmStartX, Manager.pointer, Manager.point);
            }
            if (Manager.GameOver) {
                Manager.GameMessage.setTextSize(Manager.SurfaceWidth / 10);
                canvas.drawText(getString(R.string.gameover), (int) (Manager.SurfaceWidth * 0.3), Manager.SurfaceHeight / 2, Manager.GameMessage);
                Manager.GameMessage.setTextSize(Manager.SurfaceWidth / 7);
                canvas.drawText("Tap", (int) (Manager.SurfaceWidth * 0.4), Manager.SurfaceHeight / 2+250, Manager.GameMessage);
            }
            if (Manager.DisplaySec > 0) {
                Manager.DisplaySec--;
                canvas.drawText(getString(R.string.GotPrize), (int) (Manager.SurfaceWidth * 0.1), Manager.SurfaceHeight / 2, Manager.GameMessage);
            }
//            if (Debug) {
//                int size = Manager.SurfaceWidth / 12;
//                int margin = 50;
//                canvas.drawText("Prize:" + (Prize1Y + (Prize.getHeight() / Manager.TateVal) / 4) + "(+"
//                        + (Prize.getHeight() / Manager.TateVal) * Manager.TateVal + ")", 20, margin, Manager.Debug);
//                canvas.drawText(Manager.SurfaceWidth + "/" + Manager.SurfaceHeight, 20, size + margin, Manager.Debug);
//                canvas.drawText("Left:" + Manager.LeftArm + "Right:" + Manager.RightArm, 20, size * 2 + margin, Manager.Debug);
//                canvas.drawText("LeftCnt:" + Manager.Leftcnt + "RightCnt:" + Manager.Rightcnt, 20, size * 3 + margin, Manager.Debug);
//                canvas.drawText("LStop:" + Manager.LeftStop + "RStop:" + Manager.RightStop, 20, size * 4 + margin, Manager.Debug);
//                canvas.drawText("OpenArm:" + Manager.openarm, 20, size * 5 + margin, Manager.Debug);
//                canvas.drawText("CloseArm:" + Manager.closearm, 20, size * 6 + margin, Manager.Debug);
//                canvas.drawText("Cycle:" + Manager.cycle, 20, size * 7 + margin, Manager.Debug);
//                canvas.drawText("Catch:" + Manager.CATCH, 20, size * 8 + margin, Manager.Debug);
//                canvas.drawText("ArmPower:" + Manager.ArmPower, 20, size * 9 + margin, Manager.Debug);
//                canvas.drawText("Lift:" + Manager.Lift, 20, size * 10 + margin, Manager.Debug);
//            }
            this.SV.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public int Drop(int value) {
        if (Manager.Lift > 0) {
            Manager.Lift--;
            if (YMoveWay != 1) {
                value += 2 * Manager.UPDOWN;
            }
        } else if (Manager.Lift == 0) {
            if (Drop == 1) {
                if (Prize1Y + (Prize.getHeight() / 2) > YBase3D) {
                    GET = true;
                    onCatch();
                }
            }
            if (Drop == 2) {
                if (Prize2Y + (Prize.getHeight() / 2) > YBase3D) {
                    GET2 = true;
                    onCatch();
                }
            }
            Drop = 0;
        }
        return value;
    }

    public int YMoveWay(int prize) {
        int i = 0;
        if (Manager.pointer > prize && Manager.pointer < prize + (int) (Prize.getHeight() * 0.333)) {
            i = 1;
            Manager.Lift = RX.nextInt(5) + 5;
            Manager.ArmPower = Manager.Lift;
        } else if (Manager.pointer > prize + (int) (Prize.getHeight() * 0.333)
                && Manager.pointer < prize + (int) (Prize.getHeight() * 0.666)) {
            i = 2;
            Manager.Lift = RX.nextInt(20) + 30;
            Manager.ArmPower = Manager.Lift;
        } else if (Manager.pointer > prize + (int) (Prize.getHeight() * 0.666)
                && Manager.pointer < prize + Prize.getHeight()) {
            i = 3;
            Manager.Lift = RX.nextInt(20) + 15;
            Manager.ArmPower = Manager.Lift;
        }
        return i;
    }

    public void Slide(int left, int right, int slide, int catched) {
        LeftSlide = left / 2;
        RightSlide = right / 2;
        // Slide = 0;
        Manager.CATCH = catched;
    }

    public void XMoveWay(int prize) {
        if (Manager.Leftcnt == Manager.Rightcnt) {
            Slide(0, 0, 0, prize);
        } else {
            int a = Manager.Leftcnt - Manager.Rightcnt;
            int b = Manager.Rightcnt - Manager.Leftcnt;
            if (a > b) {
                if (a <= 10) {
                    Slide(0, 0, 0, prize);
                } else {
                    Slide(a, 0, prize, 0);
                }
            } else if (a < b) {
                if (b <= 10) {
                    Slide(0, 0, 0, prize);
                } else {
                    Slide(0, b, prize, 0);
                }
            }
        }
    }

    public void CloseArm() {
        if (Manager.DCNT == 50) {
            if (Manager.LeftArmMiddleX < Prize1X && Manager.RightArmMiddleX > Prize1X + Prize.getWidth() && !GET) {
                if (Manager.LeftArmStopX >= Prize1X && Manager.pointer < Prize1Y + Prize.getHeight()
                        && Manager.pointer > Prize1Y) {
                    Manager.LeftArm = true;
                }
                if (Manager.RightArmStopX <= Prize1X + Prize.getWidth()
                        && Manager.pointer < Prize1Y + Prize.getHeight() && Manager.pointer > Prize1Y) {
                    Manager.RightArm = true;
                }
                if (Manager.LeftArm && Manager.RightArm) {
                    YMoveWay = YMoveWay(Prize1Y);
                    XMoveWay(1);
                    Manager.CatchorStack();
                }
            }
            if (Manager.LeftArmMiddleX < Prize2X && Manager.RightArmMiddleX > Prize2X + Prize.getWidth() && !GET2) {
                if (Manager.LeftArmStopX >= Prize2X && Manager.pointer < Prize2Y + Prize.getHeight()
                        && Manager.pointer > Prize2Y) {
                    Manager.LeftArm = true;
                }
                if (Manager.RightArmStopX <= Prize2X + Prize.getWidth()
                        && Manager.pointer < Prize2Y + Prize.getHeight() && Manager.pointer > Prize2Y) {
                    Manager.RightArm = true;
                }
                if (Manager.LeftArm && Manager.RightArm) {
                    YMoveWay = YMoveWay(Prize2Y);
                    XMoveWay(2);
                    Manager.CatchorStack();
                }
            }
        }
    }

    public void LiftPrize() {
        if (Manager.CATCH != 0) {
            if (Manager.CATCH == 1) {
                Manager.ArmPower--;
                Prize1Y -= Manager.UPDOWN;
                if (YMoveWay == 1) {
                    Shadow1 -= Manager.UPDOWN;
                } else {
                    Shadow1 += Manager.UPDOWN;
                }
            }
            if (Manager.CATCH == 2) {
                Manager.ArmPower--;
                Prize2Y -= Manager.UPDOWN;
                if (YMoveWay == 1) {
                    Shadow2 -= Manager.UPDOWN;
                } else {
                    Shadow2 += Manager.UPDOWN;
                }
            }
            if (Manager.ArmPower == 0) {
                if (Manager.CATCH == 1) {
                    Drop = 1;
                }
                if (Manager.CATCH == 2) {
                    Drop = 2;
                }
                Manager.CATCH = 0;
                Manager.closearm = true;
            }
        }
    }

    public void CarryPrize() {
        if (Manager.CATCH != 0) {
            if (Manager.back2pos) {
                Manager.ArmPower--;
                if (Manager.CATCH == 1) {
                    Prize1X -= Manager.YokoVal;
                    Prize1Y += Manager.TateVal;
                    Shadow1 += Manager.TateVal;
                }
                if (Manager.CATCH == 2) {
                    Prize2X -= Manager.YokoVal;
                    Prize2Y += Manager.TateVal;
                    Shadow2 += Manager.TateVal;
                }
            }
            if (Manager.ArmPower == 0) {
                if (Manager.CATCH == 1) {
                    Drop = 1;
                }
                if (Manager.CATCH == 2) {
                    Drop = 2;
                }
                Manager.CATCH = 0;
                Manager.closearm = true;
            }
        }
    }

    public void restart() {
        if (Prize != null) {
            if (!GET) {
                Prize1Y = decideY();
                Prize1X = (int) (Manager.YokoVal * (RX.nextInt(21) + 40));
                Shadow1 = Prize1Y + Prize.getHeight();
            }
            if (!GET2) {
                Prize2Y = decideY();
                Prize2X = (int) (Manager.SurfaceWidth / 2 + Manager.YokoVal * (RX.nextInt(21)));
                Shadow2 = Prize2Y + Prize.getHeight();
            }
        }
    }

    public int decideY() {
        // return YBase3D - Manager.TateVal * (Manager.RX.nextInt(21) + 50);
        return (int) (Manager.SurfaceHeight * 0.6);
    }

    void GameClear() {
        Intent clear = new Intent(this, StageClear.class);
        clear.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Manager.GameClear(Manager.Reward);
        startActivity(clear);
        finish();
    }

    public void onCatch() {
        Manager.DisplaySec = 100;
        if (GET && GET2) {
            Manager.GameClear = true;
        }
    }

    void complain(final String message, final String answer, final int method) {
        Manager.Handler.post(new Runnable() {
            public void run() {
                new AlertDialog.Builder(Tutorial.this).setMessage(message)
                        .setPositiveButton(answer, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                switch (method) {
                                    case 1:
                                        finish();
                                        break;
                                    case 2:
                                        restart();
                                        break;
                                }
                            }
                        }).setNegativeButton(R.string.Cancel, null).show().setCancelable(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.Target) {
            Manager.Pointer_Status();
        } else if (id == R.id.input) {
            if (Manager.input()) {
                CoinView.setText("" + Manager.GameCoin);
                plays.setText("" + Manager.Playleft);
            }
        } else if (id == R.id.Retire) {
            complain(getString(R.string.Exitm), getString(R.string.yes), 1);
        } else if (id == R.id.Restart) {
            complain(getString(R.string.askrestart), getString(R.string.yes), 2);
        } else if (id == R.id.stop) {
            Debug = !Debug;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (id == R.id.yoko) {
                if (!Manager.yokoMoved && Manager.Playleft >= 1) {
                    Manager.play_started();
                    plays.setText("" + Manager.Playleft);
                }
            } else if (id == R.id.tate) {
                if (Manager.yokoMoved && !Manager.tateMoved) {
                    Manager.tateMove = true;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (id == R.id.yoko) {
                if (!Manager.yokoMoved && Manager.yokoMove) {
                    Manager.yoko_finished();
                }
            } else if (id == R.id.tate) {
                if (Manager.yokoMoved && !Manager.tateMoved) {
                    Manager.tate_finished();
                }
            }
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (Manager.GameOver) {
            finish();
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        this.thread = new Thread(this);
        this.thread.start();
        SurfaceReady = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
        Loop = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        SurfaceReady = false;
        boolean retry = true;
        synchronized (SV) { // 同期処理
            Loop = false; // メンバ変数 終了フラグを立てる
        }
        while (retry) {
            try {
                this.thread.join(); // 別スレッドが終了するまで待つ
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        this.thread = null; // スレッド終了
    }

    @Override
    public void onResume() {
        super.onResume();
        Manager.GameBGM.start();
        adView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Manager.GameBGM.pause();
        adView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adView.destroy();
        Manager.reset();
        CleanUP();
    }

    private void CleanUP() {
        if (CoinBG != null) {
            CoinBG.setImageBitmap(null);
        }
        if (TitlePlate != null) {
            TitlePlate.setImageBitmap(null);
        }
        if (input != null) {
            input.setImageBitmap(null);
            input.setOnClickListener(null);
        }
        if (PlayLeft != null) {
            PlayLeft.setImageBitmap(null);
        }
        if (yoko != null) {
            yoko.setOnTouchListener(null);
            yoko.setImageBitmap(null);
        }
        if (tate != null) {
            tate.setOnTouchListener(null);
            tate.setImageBitmap(null);
        }
        if (stop != null) {
            stop.setOnClickListener(null);
            stop.setImageBitmap(null);
        }
        if (Target != null) {
            Target.setImageBitmap(null);
            Target.setOnClickListener(null);
        }
        if (Restart != null) {
            Restart.setImageBitmap(null);
            Restart.setOnClickListener(null);
        }
        if (Retire != null) {
            Retire.setImageBitmap(null);
            Retire.setOnClickListener(null);
        }
        Loaded = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
