package galaxysoftware.galaxycatcherextra_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class FourthStage extends AppCompatActivity implements SurfaceHolder.Callback,
        Runnable, OnClickListener, OnTouchListener {

    SurfaceView SV;
    Thread thread = null;
    boolean GET, Loaded, Debug, Loop, SurfaceReady;
    int Prize1X, Prize1Y, TagX, TagY, up, down, reward, YBase3D;
    Bitmap Prize, Crane, Wallpaper, Galaxy, Wall, Plate;
    TextView CoinView, plays;
    ImageButton yoko, tate, stop, input, Target, Restart, Retire;
    ImageView CoinBG, TitlePlate, Price, PlayLeft;
    GameControlManager Manager;
    Canvas canvas;
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
            Wallpaper = Bitmap.createScaledBitmap(wallpbase, (int) (Manager.SurfaceWidth * 0.7),
                    (int) (Manager.SurfaceHeight * 0.55), false);
            wallpbase.recycle();
            Bitmap galaxybase = BitmapFactory.decodeResource(getResources(), R.drawable.galaxy);
            Galaxy = Bitmap.createScaledBitmap(galaxybase, Manager.SurfaceWidth, Manager.SurfaceHeight, false);
            galaxybase.recycle();
            Bitmap cbase = BitmapFactory.decodeResource(getResources(), R.drawable.crane);
            Crane = Bitmap.createScaledBitmap(cbase, (int) (Manager.SurfaceWidth * 0.26),
                    (int) (Manager.SurfaceHeight * 0.12), false);
            cbase.recycle();
            Bitmap wallbase = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
            Wall = Bitmap.createScaledBitmap(wallbase, Manager.SurfaceWidth, (int) (Manager.SurfaceHeight * 0.6), false);
            wallbase.recycle();
            Bitmap platebase = BitmapFactory.decodeResource(getResources(), R.drawable.game4);
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
            }
            if (Manager.closearm) {
                Manager.CloseArm();
                CloseArm();
            }
            if (Manager.goup) {
                Manager.goup();
            }
            if (Manager.back2pos) {
                Manager.backXY();
            }
            if(Manager.Prizeout){
                Manager.Prizeout();
                if(Manager.GameClear){
                    GameClear();
                }
            }
            if (GET) {
                if (TagY < Manager.ScreenHeight) {
                    Prize1Y += 15;
                    TagY += 15;
                } else {
                    onCatch();
                }
            }
            canvas.drawBitmap(Galaxy, 0, 0, null);
            canvas.drawBitmap(Wall, 0, 0, null);
            canvas.drawBitmap(Plate, 0, (int) (Manager.SurfaceHeight * 0.6), null);
            canvas.drawBitmap(Prize, Prize1X, Prize1Y, null);
            canvas.drawBitmap(Wallpaper, (int) (Manager.SurfaceWidth * 0.15), (int) (Manager.SurfaceHeight * 0.03), null);
            Manager.TAG.setStrokeWidth((int) (Manager.SurfaceWidth * 0.05));
            canvas.drawLine(TagX + up, TagY, TagX + down, TagY + 190, Manager.TAG);
            Manager.TAG.setStrokeWidth(10);
            canvas.drawLine(TagX + down, TagY+100, Prize1X+(Prize.getWidth()/2), Prize1Y, Manager.TAG);
            canvas.drawBitmap(Crane, (int) (Manager.SurfaceWidth * 0.03) + Manager.Xmove,
                    (int) (Manager.SurfaceHeight * 0.4) + Manager.Ymove, null);
            canvas.drawLine(Manager.LeftArmStartX, Manager.LeftArmStartY, Manager.LeftArmMiddleX,
                    Manager.LeftArmMiddleY, Manager.arm);
            canvas.drawCircle(Manager.LeftArmMiddleX, Manager.LeftArmMiddleY, Manager.ArmStrokeWidth / 2, Manager.arm);
            canvas.drawLine(Manager.LeftArmMiddleX, Manager.LeftArmStopY, Manager.LeftArmStopX, Manager.LeftArmBottom, Manager.arm);
            canvas.drawLine(Manager.RightArmStartX, Manager.RightArmStartY, Manager.RightArmMiddleX,
                    Manager.RightArmMiddleY, Manager.arm);
            canvas.drawCircle(Manager.RightArmMiddleX, Manager.RightArmMiddleY, Manager.ArmStrokeWidth / 2, Manager.arm);
            canvas.drawLine(Manager.RightArmMiddleX, Manager.RightArmStopY, Manager.RightArmStopX, Manager.RightArmBottom, Manager.arm);
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
            if(Manager.DisplaySec > 0){
                Manager.DisplaySec--;
                canvas.drawText(getString(R.string.GotPrize), (int)(Manager.SurfaceWidth*0.1), Manager.SurfaceHeight / 2, Manager.GameMessage);
            }
            this.SV.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void CloseArm() {
        if (Manager.DCNT == 50) {
            if (Manager.RightArmStopX < TagX + up + (int) (Manager.SurfaceWidth * 0.05)
                    && Manager.pointer > TagY && Manager.pointer < TagY + 80) {
                up -= 1;
            }
            if (Manager.RightArmStopX < TagX + down + (int) (Manager.SurfaceWidth * 0.05)
                    && Manager.pointer > TagY + 120 && Manager.pointer < TagY + 190) {
                down -= 1;
            }
            if (TagX + down < Manager.SurfaceWidth * 0.35 && TagX + up < Manager.SurfaceWidth * 0.35) {
                GET = true;
            }
        }
    }

    void GameClear() {
        Intent clear = new Intent(this, StageClear.class);
        clear.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Manager.GameClear(Manager.Reward);
        startActivity(clear);
        finish();
    }

    public void restart() {
        up = 0;
        down = 0;
        TagX = (int) (Manager.SurfaceWidth * 0.39);
        TagY = (int) (Manager.SurfaceHeight * 0.72);
        Prize1X = (int) (Manager.SurfaceWidth * 0.39) - Prize.getWidth() / 2;
        Prize1Y = (int) (Manager.SurfaceHeight * 0.9);
    }

    public void onCatch() {
        Manager.DisplaySec = 100;
        if (GET) {
            Manager.GameClear = true;
            GET = false;
        }
    }

    void complain(final String message, final String answer, final int method) {
        Manager.Handler.post(new Runnable() {
            public void run() {
                new AlertDialog.Builder(FourthStage.this).setMessage(message)
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v.getId() == R.id.yoko) {
                if (!Manager.yokoMoved && Manager.Playleft >= 1) {
                    Manager.play_started();
                    plays.setText("" + Manager.Playleft);
                }
            } else if (v.getId() == R.id.tate) {
                if (Manager.yokoMoved && !Manager.tateMoved) {
                    Manager.tateMove = true;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (v.getId() == R.id.yoko) {
                if (!Manager.yokoMoved && Manager.yokoMove) {
                    Manager.yoko_finished();
                }
            } else if (v.getId() == R.id.tate) {
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
        if (adView != null) {
            adView.resume();
        }
        Manager.GameBGM.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
        Manager.GameBGM.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
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
