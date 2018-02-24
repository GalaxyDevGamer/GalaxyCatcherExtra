package galaxysoftware.galaxycatcherextra_android;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Setup extends Activity implements SurfaceHolder.Callback {

	SurfaceView SV;
	ImageButton yoko, tate, stop, input, Target, Restart, Retire;
	ImageView CoinBG, TitlePlate, Price, PlayLeft;
	TextView CoinView, plays;
	Configuration config;
	GameControlManager Manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage);
		config = getResources().getConfiguration();
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
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
			SV = (SurfaceView) findViewById(R.id.surfaceView);
			SurfaceHolder holder = SV.getHolder();
			holder.setFormat(PixelFormat.TRANSLUCENT);
			holder.addCallback(Setup.this);
			SV.setFocusable(true);
			SV.setZOrderOnTop(true);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Display display = getWindowManager().getDefaultDisplay();
					Point p = new Point();
					display.getSize(p);
					LayoutParams params = plays.getLayoutParams();
					params.height = (int) (p.y * 0.08);
					params.width = (int) (p.x * 0.06);
					plays.setLayoutParams(params);
					Bitmap platesource = BitmapFactory.decodeResource(getResources(), R.drawable.label);
					Bitmap platenew = Bitmap.createScaledBitmap(platesource, p.x,
							(int) (p.y * 0.05), false);
					platesource.recycle();
					Bitmap inputsource = BitmapFactory.decodeResource(getResources(), R.drawable.coininput);
					Bitmap inputnew = Bitmap.createScaledBitmap(inputsource, (int) (p.x * 0.07),
							(int) (p.y * 0.08), false);
					inputsource.recycle();
					Bitmap pricesource = BitmapFactory.decodeResource(getResources(), R.drawable.pricedisplay);
					Bitmap pricenew = Bitmap.createScaledBitmap(pricesource, (int) (p.x * 0.25),
							(int) (p.y * 0.08), false);
					pricesource.recycle();
					Bitmap playsource = BitmapFactory.decodeResource(getResources(), R.drawable.playleft);
					Bitmap playnew = Bitmap.createScaledBitmap(playsource, (int) (p.x * 0.07),
							(int) (p.y * 0.08), false);
					playsource.recycle();
					Bitmap coinsource = BitmapFactory.decodeResource(getResources(), R.drawable.coinbackground);
					Bitmap coinnew = Bitmap.createScaledBitmap(coinsource, (int) (p.x * 0.1),
							(int) (p.y * 0.05), false);
					coinsource.recycle();
					Bitmap yokosource = BitmapFactory.decodeResource(getResources(), R.drawable.rightarrow);
					Bitmap yokonew = Bitmap.createScaledBitmap(yokosource, (int) (p.x * 0.1),
							(int) (p.y * 0.08), false);
					yokosource.recycle();
					Bitmap tatesource = BitmapFactory.decodeResource(getResources(), R.drawable.uparrow);
					Bitmap tatenew = Bitmap.createScaledBitmap(tatesource, (int) (p.x * 0.1),
							(int) (p.y * 0.08), false);
					tatesource.recycle();
					Bitmap stopsource = BitmapFactory.decodeResource(getResources(), R.drawable.stop);
					Bitmap stopnew = Bitmap.createScaledBitmap(stopsource, (int) (p.x * 0.1),
							(int) (p.y * 0.08), false);
					stopsource.recycle();
					Bitmap targetsource = BitmapFactory.decodeResource(getResources(), R.drawable.target);
					Bitmap targetnew = Bitmap.createScaledBitmap(targetsource, (int) (p.x * 0.1),
							(int) (p.y * 0.05), false);
					targetsource.recycle();
					Bitmap restartsource = BitmapFactory.decodeResource(getResources(), R.drawable.restart);
					Bitmap restartnew = Bitmap.createScaledBitmap(restartsource, (int) (p.x * 0.1),
							(int) (p.y * 0.05), false);
					restartsource.recycle();
					Bitmap retiresource = BitmapFactory.decodeResource(getResources(), R.drawable.retire);
					Bitmap retirenew = Bitmap.createScaledBitmap(retiresource, (int) (p.x * 0.1),
							(int) (p.y * 0.05), false);
					retiresource.recycle();
					TitlePlate.setImageBitmap(platenew);
					input.setImageBitmap(inputnew);
					CoinBG.setImageBitmap(coinnew);
					Price.setImageBitmap(pricenew);
					PlayLeft.setImageBitmap(playnew);
					yoko.setImageBitmap(yokonew);
					tate.setImageBitmap(tatenew);
					stop.setImageBitmap(stopnew);
					Target.setImageBitmap(targetnew);
					Restart.setImageBitmap(restartnew);
					Retire.setImageBitmap(retirenew);
					Manager.editor.putInt("ScreenHeight", p.y).apply();
					Manager.editor.putInt("ScreenWidth", p.x).apply();
				}
			}, 0);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, final int width, final int height) {
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Manager.editor.putInt("SurfaceHeight", height).apply();
			Manager.editor.putInt("SurfaceWidth", width).apply();
			Manager.editor.putString("Version", getString(R.string.version)).apply();
			Manager.editor.putInt("VersionCode", 50).apply();
			Manager.SurfaceHeight = height;
			Manager.SurfaceWidth = width;
			Manager.ScreenHeight = Manager.preference.getInt("ScreenHeight", 0);
			Manager.ScreenWidth = Manager.preference.getInt("ScreenWidth", 0);
			//初回起動時にはSurfaceWidthとHeightのデータがない為、ここで設定しないと各種値が反映されない
			Manager.ArmStrokeWidth = (int) (Manager.SurfaceWidth * 0.02);
			Manager.arm.setStrokeWidth((int) (Manager.SurfaceWidth * 0.02));
			Manager.ArmVal = (int) (Manager.SurfaceWidth * 0.15) / 30;
			Manager.TateVal = (int) (Manager.SurfaceHeight * 0.4) / 100;
			Manager.YokoVal = Manager.SurfaceWidth / 200;
			Manager.UPDOWN = 1 * (int) (Manager.SurfaceHeight * 0.25) / 50;
			Manager.point.setStrokeWidth(Manager.TateVal);
			Manager.editor.putBoolean("Tutorial", true).apply();
			finish();
		}
	}

	private void CleanUP() {
		if (input != null) {
			input.setImageBitmap(null);
		}
		if (TitlePlate != null) {
			TitlePlate.setImageBitmap(null);
		}
		if (CoinBG != null) {
			CoinBG.setImageBitmap(null);
		}
		if (Price != null) {
			Price.setImageBitmap(null);
		}
		if (PlayLeft != null) {
			PlayLeft.setImageBitmap(null);
		}
		if (yoko != null) {
			yoko.setImageBitmap(null);
		}
		if (tate != null) {
			tate.setImageBitmap(null);
		}
		if (stop != null) {
			stop.setImageBitmap(null);
		}
		if (SV != null) {
			SV = null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		CleanUP();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
}
