package galaxysoftware.galaxycatcherextra_android;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GameControlManager extends Application {
	int UPDOWN, ArmVal, TateVal, YokoVal, SurfaceHeight, SurfaceWidth, ScreenHeight, ScreenWidth, Reward, Weight = 30,
			cycle, Playleft, GameCoin, five;
	Paint machine = new Paint(), arm = new Paint(), point = new Paint(), Notice = new Paint(), Shadow = new Paint(), TAG = new Paint(),
			Debug = new Paint(), GameMessage = new Paint();
	Handler Handler = new Handler();
	// /////////////////////�A�[���̓���p///////////////////////
	int Xmove, Ymove, LeftArmStartX, RightArmStartX, LeftArmMiddleX, RightArmMiddleX, LeftArmStopX, RightArmStopX,
			LeftArmStartY, RightArmStartY, LeftArmMiddleY, RightArmMiddleY, LeftArmStopY, RightArmStopY, LeftArmBottom,
			RightArmBottom, y, DCNT, pointer, x, ArmStrokeWidth, ArmPower, Leftcnt, Rightcnt, CATCH, Lift, Tried, img, DisplaySec;
	boolean yokoMove, tateMove, yokoMoved, tateMoved, goup, godown, openarm, closearm, Prizeout, Pointer, back2pos,
			prizeout, LeftArm, RightArm, Position, LeftStop, RightStop, GameClear, GameOver;
	// /////////////////////////////////////////////////////////
	SoundPool SoundPool;
	int[] Sound = new int[5];
	MediaPlayer BGM, GameBGM;
	public static Context context;
	SharedPreferences preference;
	SharedPreferences.Editor editor;
    String StageName;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		preference = getSharedPreferences("Setting", MODE_PRIVATE);
		editor = preference.edit();
		SurfaceHeight = preference.getInt("SurfaceHeight", 0);
		SurfaceWidth = preference.getInt("SurfaceWidth", 0);
		ScreenHeight = preference.getInt("ScreenHeight", 0);
		ScreenWidth = preference.getInt("ScreenWidth", 0);
		BGM = MediaPlayer.create(this, R.raw.smartriot);
		BGM.setLooping(true);
		GameBGM = MediaPlayer.create(this, R.raw.stoker);
		GameBGM.setLooping(true);
		machine.setARGB(255, 211, 211, 211);
		arm.setARGB(255, 211, 211, 211);
		point.setARGB(100, 255, 0, 0);
		Notice.setARGB(255, 255, 0, 0);
		Shadow.setARGB(200, 64, 64, 64);
		Shadow.setStrokeWidth(30);
		machine.setStrokeWidth(30);
		TAG.setARGB(100, 0, 255, 0);
		Debug.setARGB(200, 255, 0, 0);
		Debug.setTextSize(SurfaceWidth / 12);
		ArmStrokeWidth = (int) (SurfaceWidth * 0.02);
		arm.setStrokeWidth(ArmStrokeWidth);
        GameMessage.setARGB(200, 255, 0, 0);
        GameMessage.setTextSize(SurfaceWidth / 11);
		ArmVal = (int) (SurfaceWidth * 0.15) / 30;
		TateVal = (int) (SurfaceHeight * 0.4) / 100;
		YokoVal = SurfaceWidth / 200;
		UPDOWN = 1 * (int) (SurfaceHeight * 0.25) / 50;
		point.setStrokeWidth(TateVal);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			createSoundPoolWithBuilder();
		} else {
			createSoundPoolWithConstructor();
		}
		Sound[0] = SoundPool.load(context, R.raw.buttonse2, 1);
		Sound[1] = SoundPool.load(context, R.raw.inputse, 1);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		if (BGM != null) {
			BGM.reset();
			BGM.release();
			BGM = null;
		}
		if (GameBGM != null) {
			GameBGM.reset();
			GameBGM.release();
			GameBGM = null;
		}
	}

	public void Load() {
		LeftArmStartY = (int) (SurfaceHeight * 0.5);
		RightArmStartY = (int) (SurfaceHeight * 0.5);
		LeftArmMiddleY = (int) (SurfaceHeight * 0.6);
		RightArmMiddleY = (int) (SurfaceHeight * 0.6);
		LeftArmStopY = (int) (SurfaceHeight * 0.6);
		RightArmStopY = (int) (SurfaceHeight * 0.6);
		LeftArmStartX = (int) (SurfaceWidth * 0.08);
		LeftArmMiddleX = (int) (SurfaceWidth * 0.01);
		LeftArmStopX = (int) (SurfaceWidth * 0.15);
		RightArmStartX = (int) (SurfaceWidth * 0.25);
		RightArmMiddleX = (int) (SurfaceWidth * 0.32);
		RightArmStopX = (int) (SurfaceWidth * 0.18);
		LeftArmBottom = (int) (SurfaceHeight * 0.73);
		RightArmBottom = (int) (SurfaceHeight * 0.73);
		pointer = (int) (SurfaceHeight * 0.73) + UPDOWN * 50;
		Xmove = 0;
		Ymove = 0;
		x = 0;
		y = 0;
		Leftcnt = 0;
		Rightcnt = 0;
		CATCH = 0;
		Playleft = 0;
		GameCoin = 5000;
		five = 0;
        Lift = 0;
		ArmPower = 0;
		Tried = 0;
        GameClear = false;
        GameOver = false;
	}

	public void shareWith(String message) {
		List<Intent> shareIntentList = new ArrayList<Intent>();
		//起動するインテントのリスト
		List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(
				new Intent(Intent.ACTION_SEND).setType("image/png"), 0);
		//SNSアプリの一覧
		for (ResolveInfo info : resolveInfoList) {
			Intent shareIntent = new Intent(Intent.ACTION_SEND).setType("image/png");
//            if (info.activityInfo.packageName.toLowerCase().contains("com.facebook.katana")) {
//                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
//                shareIntent.setPackage("com.facebook.katana");
//                shareIntentList.add(shareIntent);
//            }
		}

		//もし該当するアプリが1つでもあれば起動する
		if (!shareIntentList.isEmpty()) {
			Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), getString(R.string.Share));
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new Parcelable[]{}));
			startActivity(chooserIntent);
		}
	}
	@SuppressWarnings("deprecation")
	protected void createSoundPoolWithConstructor() {
		SoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected void createSoundPoolWithBuilder() {
		AudioAttributes attributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
				.build();

		SoundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(6).build();
	}

	public void PlaySound(int which) {
		SoundPool.play(Sound[which], 1.0f, 1.0f, 0, 0, 1.0f);
	}

	public void Pointer_Status() {
		Pointer = !Pointer;
	}

	public boolean input() {
		boolean result;
		if (Playleft < 6 && GameCoin >= 100) {
			five++;
			Playleft++;
			GameCoin -= 100;
			if (five == 5) {
				Playleft = 6;
			}
			PlaySound(1);
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void play_started() {
		yokoMove = true;
		five = 0;
		Playleft--;
		Tried++;
	}

	public void GameClear(int reward) {
		Reward += reward;
	}

	public void yokoMove() {
		Xmove += YokoVal;
		x++;
		LeftArmStartX += YokoVal;
		RightArmStartX += YokoVal;
		LeftArmMiddleX += YokoVal;
		RightArmMiddleX += YokoVal;
		LeftArmStopX += YokoVal;
		RightArmStopX += YokoVal;
		if (RightArmMiddleX >= SurfaceWidth) {
			yoko_finished();
		}
	}

	void yoko_finished() {
		yokoMove = false;
		yokoMoved = true;
	}

	public void tateMove() {
		Ymove -= TateVal;
		y++;
		LeftArmStartY -= TateVal;
		RightArmStartY -= TateVal;
		LeftArmMiddleY -= TateVal;
		RightArmMiddleY -= TateVal;
		LeftArmStopY -= TateVal;
		RightArmStopY -= TateVal;
		LeftArmBottom -= TateVal;
		RightArmBottom -= TateVal;
		// pointer -= height/150;
		pointer -= TateVal;
		if (pointer <= (int) (SurfaceHeight * 0.6)) {
			tate_finished();
		}
	}

	void tate_finished() {
		if (yokoMoved) {
			tateMove = false;
			tateMoved = true;
			openarm = true;
			cycle = 1;
		}
	}

	public void openarm() {
		if (LeftArmMiddleY >= LeftArmStartY) {
			LeftArmStopX -= ArmVal;
			LeftArmMiddleY -= ArmVal;
			LeftArmStopY -= ArmVal;
			Leftcnt++;
		}
		if (RightArmMiddleY >= RightArmStartY) {
			RightArmStopX += ArmVal;
			RightArmMiddleY -= ArmVal;
			RightArmStopY -= ArmVal;
			Rightcnt++;
		}
		if (LeftArmMiddleY <= LeftArmStartY && RightArmMiddleY <= RightArmStartY) {
			openarm = false;
			if (cycle == 1) {
				godown = true;
			} else if (cycle == 4) {
				// アームが最大まで開くが、閉じる動作がすぐ止まる(閉じるフラグと開けるフラグがtrue)。ここであのフラグをfalseにすると
				// またアームが最大まで開き、フラグがtrueになりちゃんと閉じる
				//ここで値を4にすると一旦は5になるが、途中から4になり止まってしまう (2017/3/16サイクルを若干いじってからは未検証
				Handler.postDelayed(new Runnable() {
					public void run() {
						cycle = 5;
						closearm = true;
					}
				}, 300);
			}
		}
	}

	public void godown() {
		if (DCNT < 50) {
			Ymove += UPDOWN;
			LeftArmStartY += UPDOWN;
			RightArmStartY += UPDOWN;
			LeftArmMiddleY += UPDOWN;
			RightArmMiddleY += UPDOWN;
			LeftArmStopY += UPDOWN;
			RightArmStopY += UPDOWN;
			LeftArmBottom += UPDOWN;
			RightArmBottom += UPDOWN;
			DCNT++;
		} else {
			godown = false;
			cycle = 2;
			closearm = true;
		}
	}

	public void Reached2obj() {
		godown = false;
		cycle = 2;
		closearm = true;
	}

	public void CloseArm() {
        if(Leftcnt > 0 || Rightcnt > 0) {
            if (!LeftArm && !LeftStop) {
                LeftArm();
            }
            if (!RightArm && !RightStop) {
                RightArm();
            }
            if (LeftStop || RightStop) {
                CatchorStack();
            }
            if (Leftcnt == 0 && Rightcnt == 0) {
                closearm = false;
                if (cycle == 2) {
                    CatchorStack();
                }
                if (cycle == 5) {
                    Prizeout = true;
                }
            }
        } else {
            closearm = false;
        }
	}

	public void CatchorStack() {
		Handler.postDelayed(new Runnable() {
			public void run() {
				goup = true;
				LeftStop = false;
				RightStop = false;
                LeftArm = false;
                RightArm = false;
                cycle = 3;
			}
		}, 1500);
	}

	public void LeftArm() {
		if (Leftcnt > 0) {
			LeftArmMiddleY += ArmVal;
			LeftArmStopY += ArmVal;
			LeftArmStopX += ArmVal;
			Leftcnt--;
		}
	}

	public void RightArm() {
		if (Rightcnt > 0) {
			RightArmMiddleY += ArmVal;
			RightArmStopY += ArmVal;
			RightArmStopX -= ArmVal;
			Rightcnt--;
		}
	}

	public void goup() {
		if (DCNT > 0) {
			Ymove -= UPDOWN;
			LeftArmStartY -= UPDOWN;
			RightArmStartY -= UPDOWN;
			LeftArmMiddleY -= UPDOWN;
			RightArmMiddleY -= UPDOWN;
			LeftArmStopY -= UPDOWN;
			RightArmStopY -= UPDOWN;
			LeftArmBottom -= UPDOWN;
			RightArmBottom -= UPDOWN;
			DCNT--;
		} else {
			goup = false;
			back2pos = true;
		}
	}

	public void backXY() {
		if (x > 0) {
			Xmove -= YokoVal;
			x--;
			LeftArmStartX -= YokoVal;
			RightArmStartX -= YokoVal;
			LeftArmMiddleX -= YokoVal;
			RightArmMiddleX -= YokoVal;
			LeftArmStopX -= YokoVal;
			RightArmStopX -= YokoVal;
		}
		if (y > 0) {
			Ymove += TateVal;
			y--;
			LeftArmStartY += TateVal;
			RightArmStartY += TateVal;
			LeftArmMiddleY += TateVal;
			RightArmMiddleY += TateVal;
			LeftArmStopY += TateVal;
			RightArmStopY += TateVal;
			LeftArmBottom += TateVal;
			RightArmBottom += TateVal;
			pointer += TateVal;
		}
		if (x == 0 && y == 0) {
			Handler.postDelayed(new Runnable() {
				public void run() {
					back2pos = false;
					cycle = 4;
					openarm = true;
				}
			}, 300);
		}
	}
	public void Prizeout(){
        reset();
        if (!GameClear && GameCoin < 100 && Playleft == 0) {
            GameOver = true;
        }
        Prizeout = false;
    }

	public void reset() {
		cycle = 0;
		ArmPower = 0;
		Lift = 0;
		pointer = (int) (SurfaceHeight * 0.73) + UPDOWN * 50;
		yokoMove = false;
		tateMove = false;
		yokoMoved = false;
		tateMoved = false;
		LeftArm = false;
		RightArm = false;
		Position = false;
		prizeout = false;
		goup = false;
		godown = false;
		openarm = false;
		closearm = false;
		back2pos = false;
		LeftStop = false;
		RightStop = false;
	}
}
