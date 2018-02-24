package galaxysoftware.galaxycatcherextra_android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StageClear extends AppCompatActivity {
    GameControlManager Manager;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stageclear);
        Manager = (GameControlManager) this.getApplication();
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequestBuilder = new AdRequest.Builder().build();
        adView.loadAd(adRequestBuilder);
        ((TextView) findViewById(R.id.name)).setText(Manager.StageName);
        ((ImageView) findViewById(R.id.Prize)).setImageResource(Manager.img);
        ((TextView) findViewById(R.id.TimesPlayed)).setText(String.valueOf(Manager.Tried));
        findViewById(R.id.OK).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.PostToTwitter).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String messsage = Uri.encode(getString(R.string.PostedFromGCE) + getString(R.string.AppURL));
                    intent.setData(Uri.parse("twitter://post?message=" + messsage));
//                intent.setData(Uri.parse("line://msg/text/" + messsage)); //LINE
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(StageClear.this, getString(R.string.ErrorOnTwitter),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adView.resume();
        Manager.BGM.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        adView.pause();
        Manager.BGM.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }
}