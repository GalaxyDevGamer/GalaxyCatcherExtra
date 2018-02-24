package galaxysoftware.galaxycatcherextra_android;
//import com.GalaxySoftware.GalaxyCatcherExtra.Billing.IabHelper;
//import com.GalaxySoftware.GalaxyCatcherExtra.Billing.IabResult;
//import com.GalaxySoftware.GalaxyCatcherExtra.Billing.Inventory;
//import com.GalaxySoftware.GalaxyCatcherExtra.Billing.Purchase;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.TextView;
//
//public class Shop extends Activity implements OnClickListener {
//
//	IabHelper mHelper;
//	// (arbitrary) request code for the purchase flow
//	static final int RC_REQUEST = 10001;
//	SharedPreferences preference;
//	String item;
//	Editor editor;
//	TextView money;
//	GameControlManager Manager;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.shop);
//		Manager = (GameControlManager)this.getApplication();
//		findViewById(R.id.menu).findViewById(R.id.stage).setOnClickListener(this);
//		preference = getSharedPreferences("setting", MODE_PRIVATE);
//		editor = preference.edit();
//		// base64EncodedPublicKey��Developer Console�������Ă���
//		String base64EncodedPublicKey = "";
//		mHelper = new IabHelper(this, base64EncodedPublicKey);
//		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//			public void onIabSetupFinished(IabResult result) {
//
//				if (!result.isSuccess()) {
//					// Oh noes, there was a problem.
//					complain("Problem setting up in-app billing: " + result);
//					return;
//				}
//
//				// Have we been disposed of in the meantime? If so, quit.
//				if (mHelper == null)
//					return;
//				mHelper.queryInventoryAsync(mGotInventoryListener);
//			}
//		});
//	}
//
//	@Override
//	public void onClick(View v) {
//		Manager.PlaySound(0);
//	}
//
//	public void buy(String item) {
//		String payload = "";
//		mHelper.launchPurchaseFlow(this, item, RC_REQUEST,
//				mPurchaseFinishedListener, payload);
//	}
//
//	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
//		public void onQueryInventoryFinished(IabResult result,
//				Inventory inventory) {
//			// Have we been disposed of in the meantime? If so, quit.
//			if (mHelper == null)
//				return;
//
//			// Is it a failure?
//			if (result.isFailure()) {
//				complain("Failed to query inventory: " + result);
//				return;
//			}
//			preference = getSharedPreferences("setting", MODE_PRIVATE);
//			// Item Already Owned�G���[�ōw���ł��Ȃ���΁A( �����ɃA�C�e����ID )��ݒ肵�đ��点��
//			// �菇�͂���http://stackoverflow.com/questions/14600664/android-in-app-purchase-signature-verification-failed
//			// if (inventory.hasPurchase(item)) {
//			// mHelper.consumeAsync(inventory.getPurchase(item),null); }
//
//			/*
//			 * Check for items we own. Notice that for each purchase, we check
//			 * the developer payload to see if it's correct! See
//			 * verifyDeveloperPayload().
//			 */
//		}
//	};
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (mHelper == null)
//			return;
//
//		// Pass on the activity result to the helper for handling
//		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
//			// not handled, so handle it ourselves (here's where you'd
//			// perform any handling of activity results not related to in-app
//			// billing...
//			super.onActivityResult(requestCode, resultCode, data);
//		} else {
//		}
//	}
//
//	/** Verifies the developer payload of a purchase. */
//	boolean verifyDeveloperPayload(Purchase p) {
////		String payload = p.getDeveloperPayload();
//		/*
//		 * TODO: verify that the developer payload of the purchase is correct.
//		 * It will be the same one that you sent when initiating the purchase.
//		 *
//		 * WARNING: Locally generating a random string when starting a purchase
//		 * and verifying it here might seem like a good approach, but this will
//		 * fail in the case where the user purchases an item on one device and
//		 * then uses your app on a different device, because on the other device
//		 * you will not have access to the random string you originally
//		 * generated.
//		 *
//		 * So a good developer payload has these characteristics:
//		 *
//		 * 1. If two different users purchase an item, the payload is different
//		 * between them, so that one user's purchase can't be replayed to
//		 * another user.
//		 *
//		 * 2. The payload must be such that you can verify it even when the app
//		 * wasn't the one who initiated the purchase flow (so that items
//		 * purchased by the user on one device work on other devices owned by
//		 * the user).
//		 *
//		 * Using your own server to store and verify developer payloads across
//		 * app installations is recommended.
//		 */
//
//		return true;
//	}
//
//	// Callback for when a purchase is finished
//	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
//		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
//			// if we were disposed of in the meantime, quit.
//			if (mHelper == null)
//				return;
//
//			if (result.isFailure()) {
//				complain("Error:purchase canceled");
//				return;
//			}
//			if (!verifyDeveloperPayload(purchase)) {
//				complain("Error purchasing. Authenticity verification failed.");
//				return;
//			}
//			preference = getSharedPreferences("setting", MODE_PRIVATE);
//			switch (purchase.getSku()) {
//			case "5tickets":
//				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
//				break;
//			}
//		}
//	};
//
//	// Called when consumption is complete
//	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
//		public void onConsumeFinished(Purchase purchase, IabResult result) {
//
//			// if we were disposed of in the meantime, quit.
//			if (mHelper == null)
//				return;
//
//			// We know this is the "gas" sku because it's the only one we
//			// consume,
//			// so we don't check which sku was consumed. If you have more than
//			// one
//			// sku, you probably should check...
//			if (result.isSuccess()) {
//				// successfully consumed, so we apply the effects of the item in
//				// our
//				// game world's logic, which in our case means filling the gas
//				// tank a bit
//				switch (purchase.getSku()) {
//				case "5tickets":
//					break;
//				}
//				complain(getString(R.string.charge_success));
//
//			} else {
//				complain("Error while processing");
//			}
//		}
//	};
//
//	void complain(String message) {
//		new AlertDialog.Builder(this).setMessage(message)
//				.setPositiveButton("OK", null).show();
//	}
//	@Override
//	public void onResume() {
//		super.onResume();
//	}
//	@Override
//	public void onPause() {
//		super.onPause();
//	}
//	@Override
//	public void onDestroy() {
//		if (mHelper != null) {
//			mHelper.dispose();
//			mHelper = null;
//		}
//		super.onDestroy();
//	}
//}
