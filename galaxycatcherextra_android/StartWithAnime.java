package galaxysoftware.galaxycatcherextra_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StartWithAnime extends AppCompatActivity {

    boolean Tap, TaptoStart;
    GameControlManager Manager;
    Handler TapHand;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        Manager = (GameControlManager) this.getApplication();
        TapHand = new Handler();
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequestBuilder = new AdRequest.Builder().build();
        adView.loadAd(adRequestBuilder);
    }

    @Override
    public void onResume() {
        super.onResume();
        Manager.BGM.start();
        TapHand.postDelayed(new Runnable() {
            public void run() {
                if (TaptoStart) {
                    TaptoStart = false;
                    findViewById(R.id.start).setVisibility(View.VISIBLE);
                } else {
                    TaptoStart = true;
                    findViewById(R.id.start).setVisibility(View.INVISIBLE);
                }
                TapHand.postDelayed(this, 1000);
            }
        }, 1000);
        adView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Manager.BGM.pause();
        TapHand.removeCallbacksAndMessages(null);
        adView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // From Ver 3.0
        if (!Manager.preference.getBoolean("License", false)) {
            if (!Tap) {
                Tap = true;
                new AlertDialog.Builder(this).setTitle(R.string.LicenseTitle).setMessage(R.string.LicenseSimple)
                        .setPositiveButton(getString(R.string.agree), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Manager.editor.putBoolean("License", true).apply();
                                start();
                            }
                        })
                        .setNegativeButton(getString(R.string.Exit), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setCancelable(false).show();
            }
        } else {
            start();
        }
        return true;
    }

    public void start() {
        Intent i = new Intent(this, Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setMessage(R.string.Exit_check)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).setNegativeButton(R.string.Cancel, null).show();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
