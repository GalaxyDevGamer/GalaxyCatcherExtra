package galaxysoftware.galaxycatcherextra_android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Home extends Activity implements OnClickListener {

    GameControlManager Manager;
    AdView adView;
    LinearLayout container;
    int Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Manager = (GameControlManager) this.getApplication();
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequestBuilder = new AdRequest.Builder().build();
        adView.loadAd(adRequestBuilder);
        container = (LinearLayout) findViewById(R.id.Home_Container);
        findViewById(R.id.Stage).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadStageList();
            }
        });
        findViewById(R.id.Shop).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadShop();
            }
        });
        findViewById(R.id.Menu).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadMenu();
            }
        });
        LoadStageList();
    }

    void Stage(int img, String Stage, Class<?> place, int weight) {
        Intent stage = new Intent(this, place);
        stage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Manager.img = img;
        Manager.StageName = Stage;
        Manager.Weight = weight;
        startActivity(stage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stage1:
                Stage(R.drawable.box, getString(R.string.Tutorial), Tutorial.class, 30);
                break;
            case R.id.stage2:
                Stage(R.drawable.android, getString(R.string.Stage1), NormalStage.class, 30);
                break;
            case R.id.stage3:
                Stage(R.drawable.androidbox, getString(R.string.Stage2), NormalStage.class, 35);
                break;
            case R.id.stage4:
                Stage(R.drawable.androidbox2, getString(R.string.Stage3), ThirdStage.class, 20);
                break;
            case R.id.stage5:
                Stage(R.drawable.androidbox3, getString(R.string.Stage4), FourthStage.class, 400);
                break;
            case R.id.Back2Title:
                finish();
                break;
            case R.id.Share:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String messsage = Uri.encode(getString(R.string.PostedFromGCE) + getString(R.string.AppURL));
                    intent.setData(Uri.parse("twitter://post?message=" + messsage));
//                intent.setData(Uri.parse("line://msg/text/" + messsage)); //LINE
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this, getString(R.string.ErrorOnTwitter),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.About:
                container.removeAllViews();
                getLayoutInflater().inflate(R.layout.about, container);
                ((TextView)findViewById(R.id.Title)).setText(getString(R.string.About));
                Layout = 4;
                break;
        }
        Manager.PlaySound(0);
    }

    public void LoadStageList() {
        container.removeAllViews();
        getLayoutInflater().inflate(R.layout.stagelist, container);
        ((TextView)findViewById(R.id.Title)).setText(getString(R.string.stage));
        findViewById(R.id.stage1).setOnClickListener(this);
        findViewById(R.id.stage2).setOnClickListener(this);
        findViewById(R.id.stage3).setOnClickListener(this);
        findViewById(R.id.stage4).setOnClickListener(this);
        findViewById(R.id.stage5).setOnClickListener(this);
//		findViewById(R.id.stage2).setVisibility(Manager.preference.getBoolean("Stage2", false) ? View.VISIBLE : View.GONE);
//		findViewById(R.id.stage2).setOnClickListener(Manager.preference.getBoolean("Stage2", false) ? this : null);
        Layout = 1;
    }

    public void LoadShop() {
        container.removeAllViews();
        getLayoutInflater().inflate(R.layout.shop, container);
        ((TextView)findViewById(R.id.Title)).setText(getString(R.string.shop));
        Layout = 2;
    }

    public void LoadMenu() {
        container.removeAllViews();
        getLayoutInflater().inflate(R.layout.menulist, container);
        ((TextView)findViewById(R.id.Title)).setText(getString(R.string.Menu));
        findViewById(R.id.Back2Title).setOnClickListener(this);
        findViewById(R.id.Share).setOnClickListener(this);
        findViewById(R.id.About).setOnClickListener(this);
        Layout = 3;
    }

    @Override
    public void onBackPressed() {
        switch (Layout){
            case 1:
                super.onBackPressed();
                break;
            case 4:
                LoadMenu();
                break;
            default:
                LoadStageList();
        }
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
