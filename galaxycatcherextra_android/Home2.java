package galaxysoftware.galaxycatcherextra_android;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//public class Home extends Activity implements OnClickListener {
//
//	TextView money, title, Label;
//	SharedPreferences preference;
//	Editor editor;
//	ScrollView list;
//	RelativeLayout content;
//	GameControlManager Manager;
//	Button mode;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.home);
//		preference = getSharedPreferences("setting", MODE_PRIVATE);
//		editor = preference.edit();
//		Manager = (GameControlManager) this.getApplication();
//		findViewById(R.id.menu).findViewById(R.id.store).setOnClickListener(this);
//		findViewById(R.id.menu).findViewById(R.id.stage).setOnClickListener(this);
//		money = (TextView) findViewById(R.id.status).findViewById(R.id.money);
//		content = (RelativeLayout) findViewById(R.id.content);
//		title = (TextView) findViewById(R.id.status).findViewById(R.id.title);
//		Manager.StageURI = R.layout.normallist;
//		Manager.Current = "Stage";
//	}
//
//	void Stage(int img, String Stage, String Next,
//			Class<?> place, int weight, int reward) {
//		int map = 0;
//		if (!Stage.equals("FourthStage")) {
//			map = R.drawable.game;
//		} else {
//			map = R.drawable.game2;
//		}
//		Intent stage = new Intent(this, place);
//		stage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		stage.putExtra("img", img);
//		stage.putExtra("Stage Name", Stage);
//		stage.putExtra("Next Stage", Next);
//		Manager.Weight = weight;
//		Manager.Reward = reward;
//		stage.putExtra("Map", map);
//		startActivity(stage);
//	}
//
//	@Override
//	public void onClick(View v) {
//		preference = getSharedPreferences("setting", MODE_PRIVATE);
//		// TODO �����������ꂽ���\�b�h�E�X�^�u
//		ClearResource();
//		int id = v.getId();
//		if (id == R.id.stage1) {
//			Stage(R.drawable.android, "First Stage\n1 - 1", "Stage2",
//					NormalStage.class, 30, 100);
//		} else if (id == R.id.stage6) {
//			Stage(R.drawable.bear, "Second Stage", "Stage7",
//					NormalStage.class, 40, 200);
//		} else if (id == R.id.stage11) {
//			Stage(R.drawable.android, "Third Stage", "Stage12",
//					ThirdStage.class, 20, 300);
//		} else if (id == R.id.stage16) {
//			Stage(R.drawable.android, "Fourth Stage", "Stage17",
//					FourthStage.class, 3, 400);
//		} else if (id == R.id.specialstage1) {
//			Stage(R.drawable.bear, "First Stage\nFreePlay", null,
//					NormalStage.class, 0, 25);
//		} else if (id == R.id.specialstage2) {
//			Stage(R.drawable.penguin, "Second Stage\nFreePlay", null,
//					NormalStage.class, 40, 100);
//		} else if (id == R.id.specialstage3) {
//			Stage(R.drawable.android, "Third Stage\nFreePlay", null,
//					ThirdStage.class, 20, 200);
//		} else if (id == R.id.specialstage4) {
//			Stage(R.drawable.android, "Fourth Stage\nFreePlay", null,
//					FourthStage.class, 1, 250);
//		} else if (id == R.id.store) {
//			findViewById(R.id.menu).findViewById(R.id.stage).setEnabled(true);
//			Intent act = new Intent(this, Shop.class);
//			act.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(act);
//		} else if (id == R.id.mode) {
//			ModeChange();
//		}
//		Manager.PlaySound(0);
//	}
//
//	public void ClearResource() {
//		switch (Manager.Current) {
//		case "Stage":
//			if (Manager.StageURI == R.layout.normallist) {
//				deleteNormal();
//			} else if (Manager.StageURI == R.layout.speciallist) {
//				deleteFreePlay();
//			}
//			break;
//		}
//	}
//
//	public void RefreshList(int layout) {
//		list = (ScrollView) findViewById(R.id.stagelist);
//		list.removeAllViews();
//		getLayoutInflater().inflate(layout, list);
//	}
//
//	public void RefreshHome(int layout) {
//		content.removeAllViews();
//		getLayoutInflater().inflate(layout, content);
//	}
//
//	public void ModeChange() {
//		if (Manager.StageURI == R.layout.normallist) {
//			Manager.StageURI = R.layout.speciallist;
//		} else if (Manager.StageURI == R.layout.speciallist) {
//			Manager.StageURI = R.layout.normallist;
//		}
//		RefreshList(Manager.StageURI);
//		ChangeBg();
//	}
//
//	public void initHome() {
//		RefreshHome(R.layout.stagelist);
//		RefreshList(Manager.StageURI);
//		ChangeBg();
//	}
//
//	public void ChangeBg() {
//		mode = (Button) findViewById(R.id.mode);
//		Label = (TextView) findViewById(R.id.Label);
//		if (Manager.StageURI == R.layout.normallist) {
//			mode.setText(getString(R.string.Special));
//			mode.setBackgroundResource(R.color.green);
//			Label.setText(getString(R.string.Normal));
//			Label.setBackgroundResource(R.color.skyblue);
//			HomeView();
//		} else if (Manager.StageURI == R.layout.speciallist) {
//			mode.setText(getString(R.string.Normal));
//			mode.setBackgroundResource(R.color.skyblue);
//			Label.setText(getString(R.string.Special));
//			Label.setBackgroundResource(R.color.green);
//			FreePlay();
//		}
//		findViewById(R.id.mode).setOnClickListener(this);
//	}
//
//	public void HomeView() {
//		preference = getSharedPreferences("setting", MODE_PRIVATE);
//		title.setText(R.string.stage);
//		findViewById(R.id.stage1).setOnClickListener(this);
//		findViewById(R.id.stage6).setVisibility(
//				preference.getBoolean("Stage6", false) ? View.VISIBLE
//						: View.GONE);
//		findViewById(R.id.stage6).setOnClickListener(
//				preference.getBoolean("Stage6", false) ? this : null);
//		findViewById(R.id.stage11).setVisibility(
//				preference.getBoolean("Stage11", false) ? View.VISIBLE
//						: View.GONE);
//		findViewById(R.id.stage11).setOnClickListener(
//				preference.getBoolean("Stage11", false) ? this : null);
//		findViewById(R.id.stage16).setVisibility(
//				preference.getBoolean("Stage16", false) ? View.VISIBLE
//						: View.GONE);
//		findViewById(R.id.stage16).setOnClickListener(
//				preference.getBoolean("Stage16", false) ? this : null);
//	}
//
//	public void FreePlay() {
//		preference = getSharedPreferences("setting", MODE_PRIVATE);
//		findViewById(R.id.specialstage5).setVisibility(View.GONE);
//		findViewById(R.id.specialstage4).setOnClickListener(this);
//		findViewById(R.id.specialstage4).setVisibility(
//				preference.getBoolean("Stage16", false) ? View.VISIBLE
//						: View.GONE);
//		findViewById(R.id.specialstage3).setOnClickListener(this);
//		findViewById(R.id.specialstage3).setVisibility(
//				preference.getBoolean("Stage11", false) ? View.VISIBLE
//						: View.GONE);
//		findViewById(R.id.specialstage2).setOnClickListener(this);
//		findViewById(R.id.specialstage2).setVisibility(
//				preference.getBoolean("Stage6", false) ? View.VISIBLE
//						: View.GONE);
//		findViewById(R.id.specialstage1).setOnClickListener(this);
//	}
//
//	public void deleteNormal() {
//		findViewById(R.id.stage1).setOnClickListener(null);
//		findViewById(R.id.stage6).setOnClickListener(null);
//		findViewById(R.id.stage11).setOnClickListener(null);
//		findViewById(R.id.stage16).setOnClickListener(null);
//	}
//
//	public void deleteFreePlay() {
//		findViewById(R.id.specialstage1).setOnClickListener(null);
//		findViewById(R.id.specialstage2).setOnClickListener(null);
//		findViewById(R.id.specialstage3).setOnClickListener(null);
//		findViewById(R.id.specialstage4).setOnClickListener(null);
//	}
//
//	public void UpdateStatus() {
//		money.setText(Manager.getMoney());
//	}
//
//	void complain(String message) {
//		new AlertDialog.Builder(this).setMessage(message)
//				.setPositiveButton("OK", null).show();
//	}
//
//	@Override
//	public void onPause() {
//		Manager.music.pause();
//		super.onPause();
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		UpdateStatus();
//		if (Manager.Current.equals("Stage")) {
//			initHome();
//		}
//		if (preference.getBoolean("FirstLaunch", false) == false) {
//			new AlertDialog.Builder(this).setTitle(R.string.version)
//					.setMessage(R.string.VersionInfo)
//					.setPositiveButton("OK", null).show();
//			editor.putBoolean("FirstLaunch", true).commit();
//		}
//		Manager.music.start();
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//	}
//
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// �����ꂽ�L�[�����擾
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			new AlertDialog.Builder(this)
//					.setMessage(R.string.back2title)
//					.setPositiveButton(R.string.yes,
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int whichButton) {
//									/* ������YES�̏��� */
//									finish();
//								}
//							}).setNegativeButton(R.string.Cancel, null).show();
//
//			return false;
//		} else {
//			// ���������ƒʏ��Back�L�[�Ƃ��Ă̓���ɂȂ�
//			return super.onKeyDown(keyCode, event);
//		}
//	}
//}
