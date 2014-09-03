package com.udaan.sugarpatrol.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.udaan.sugarpatrol.Settings;
import com.udaan.sugarpatrol.android.util.IabHelper;
import com.udaan.sugarpatrol.android.util.IabResult;
import com.udaan.sugarpatrol.android.util.Inventory;
import com.udaan.sugarpatrol.android.util.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirus on 8/5/14.
 */
public class ShopActivity extends Activity {
    private final static String SKU_100_COINS = "sugarpatrol_100_coins"; //$0.99
    private final static String SKU_250_COINS = "sugarpatrol_250_coins"; //$1.99
    private final static String SKU_750_COINS = "sugarpatrol_750_coins"; //$4.99
    private final static String TAG = "ShopActivity";

    private IabHelper mHelper;
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
    private List<String> skuList;
    private String coins100Price, coins250Price, coins750Price;

    private String key1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgju8rWQAdi7KtrQQdIIYWTr3LyOEHaqxyXPKSrhjZcoUZGO/ciH/";
    private String key2 = "gauYLjM1a3kdD+J4Ixu551lb/RLD4clO1cw92vFbSX/Qr+XSllYgEFpDFyiiX0kNDsCzGKVmhgNPKHwhs8CbKeHrXe/";
    private String key3 = "ks7NZOxLUPbauPk4pquwz7wHvKyZJwT/SIyThHOJLrgSQBnRLgB0KIbcZzMkriJXZDcRdqxjSIfSNeSugw9k6i0LIdPN";
    private String key4 = "+Fmp5rIouhVWAvpZqOVmNOC/z0ICeYERp5dGZBi7gk+Z06tg+sfD0cknXgxgfRkh4zQ7Q0hUZqiLB3jSlEIqmO6ZvtF4/";
    private String key5 = "yxRyz04XnpT4/wIDAQAB";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);

        ImageView cancel = (ImageView) findViewById(R.id.back);
        ImageView coins100 = (ImageView) findViewById(R.id.btn_coins_100);
        ImageView coins250 = (ImageView) findViewById(R.id.btn_coins_250);
        ImageView coins750 = (ImageView) findViewById(R.id.btn_coins_750);

        LinearLayout shopLL = (LinearLayout) findViewById(R.id.shop_ll);

        switch (Settings.getSelectedBackground()) {
            case 0:
                shopLL.setBackgroundResource(getResources().getIdentifier("background_wood_1", "raw", this.getPackageName()));
                break;
            case 1:
                shopLL.setBackgroundResource(getResources().getIdentifier("background_brick", "raw", this.getPackageName()));
                break;
            case 2:
                shopLL.setBackgroundResource(getResources().getIdentifier("background_wood_2", "raw", this.getPackageName()));
                break;
            case 3:
                shopLL.setBackgroundResource(getResources().getIdentifier("background_steel", "raw", this.getPackageName()));
                break;
            case 4:
                shopLL.setBackgroundResource(getResources().getIdentifier("background_marble", "raw", this.getPackageName()));
                break;
            case 5:
                shopLL.setBackgroundResource(getResources().getIdentifier("background_concrete", "raw", this.getPackageName()));
                break;
        }

        skuList = new ArrayList<String>();
        skuList.add(SKU_100_COINS);
        skuList.add(SKU_250_COINS);
        skuList.add(SKU_750_COINS);

        mHelper = new IabHelper(this, key1 + key2 + key3 + key4 + key5);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    //there was a problem.
                    Log.e(TAG, "Problem setting up In-app Billing: " + result);
                }
                Log.d(TAG, "IAB is fully setup. Querying inventory");
                mHelper.queryInventoryAsync(true, skuList, mGotInventoryListener);
            }
        });

        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory)
            {
                if (result.isFailure()) {
                    // handle error
                    Toast.makeText(getApplicationContext(), "Error getting price", Toast.LENGTH_LONG).show();
                    return;
                }

                if (inventory.getSkuDetails(SKU_100_COINS) == null) Log.d(TAG, "Inventory getSkuDetails null");
                //TODO
                coins100Price = inventory.getSkuDetails(SKU_100_COINS).getPrice();
                coins250Price = inventory.getSkuDetails(SKU_250_COINS).getPrice();
                coins750Price = inventory.getSkuDetails(SKU_750_COINS).getPrice();
            }
        };

        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            @Override
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {
                    Log.d(TAG, "Error purchasing: " + result);
                    //Toast.makeText(getApplicationContext(), "Error purchasing: " + result, Toast.LENGTH_LONG).show();
                    return;
                }

                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        };

        mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                if (result.isSuccess()) {
                    if (purchase.getSku().equals(SKU_100_COINS)) {
                        Settings.setGoldCoins(Settings.getGoldCoins() + 100);
                        Toast.makeText(getApplicationContext(), "Successfully purchased 100 coins", Toast.LENGTH_LONG).show();
                    }
                    else if (purchase.getSku().equals(SKU_250_COINS)) {
                        Settings.setGoldCoins(Settings.getGoldCoins() + 250);
                        Toast.makeText(getApplicationContext(), "Successfully purchased 250 coins", Toast.LENGTH_LONG).show();
                    }
                    else if (purchase.getSku().equals(SKU_750_COINS)) {
                        Settings.setGoldCoins(Settings.getGoldCoins() + 750);
                        Toast.makeText(getApplicationContext(), "Successfully purchased 750 coins", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    showAlertOK("There was an error processing your purchase. Please send an email to developer and mention order: " + purchase.getOrderId());
                }
            }
        };

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        coins100.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*coins100.setImageResource(R.raw.btn_100_coins_clicked);

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        coins100.setImageResource(R.raw.btn_100_coins);
                    }
                }.start();*/

                Log.d(TAG, "Purchase price is " + coins100Price);
                mHelper.launchPurchaseFlow(ShopActivity.this, SKU_100_COINS, 5, mPurchaseFinishedListener, "coins100purchase");
            }
        });

        coins250.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*coins100.setImageResource(R.raw.btn_100_coins_clicked);

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        coins100.setImageResource(R.raw.btn_100_coins);
                    }
                }.start();*/

                Log.d(TAG, "Purchase price is " + coins250Price);
                mHelper.launchPurchaseFlow(ShopActivity.this, SKU_250_COINS, 5, mPurchaseFinishedListener, "coins250purchase");
            }
        });

        coins750.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*coins100.setImageResource(R.raw.btn_100_coins_clicked);

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        coins100.setImageResource(R.raw.btn_100_coins);
                    }
                }.start();*/

                Log.d(TAG, "Purchase price is " + coins750Price);
                mHelper.launchPurchaseFlow(ShopActivity.this, SKU_750_COINS, 5, mPurchaseFinishedListener, "coins750purchase");
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    private void showAlertOK(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }
}
