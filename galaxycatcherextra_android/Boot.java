package galaxysoftware.galaxycatcherextra_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.ImageView;

public class Boot extends Activity {
    ImageView Logo;
    Bitmap Wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot);
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        Bitmap wallbase = BitmapFactory.decodeResource(getResources(), R.drawable.titleicon);
        Wallpaper = Bitmap.createScaledBitmap(wallbase, (int) (p.x * 0.5), (int) (p.y * 0.5), false);
        wallbase.recycle();
        Logo = (ImageView) findViewById(R.id.Logo);
        Logo.setImageBitmap(Wallpaper);
    }

    @Override
    public void onResume() {
        super.onResume();
        final Intent i;
        final GameControlManager Manager;
        Manager = (GameControlManager)getApplication();
        if (!Manager.preference.getBoolean("Tutorial", false)) {
            i = new Intent(Boot.this, Setup.class);
        } else {
            i = new Intent(Boot.this, StartWithAnime.class);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
                if (Manager.preference.getBoolean("Tutorial", false)) {
                    finish();
                }
            }
        }, 1250);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logo.setImageBitmap(null);
        Wallpaper.recycle();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // バックキーを押したとき
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return false;
        } else {
            // 何もしない
            return super.onKeyDown(keyCode, event);
        }
    }
}
