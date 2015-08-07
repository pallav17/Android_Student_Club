package com.example.ramesh.couponoic_f;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FinalRedemptionActivity extends ActionBarActivity implements AccelerometerListener {

    Button btnTransfer;
    String CouponID, FranchiseeID, CustomerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_redemption);

        btnTransfer = (Button) findViewById(R.id.btnTransfer);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            CouponID = (String) b.get("CouponID");
            FranchiseeID = (String) b.get("FranchiseeID");
            CustomerID=(String)b.get("CustomerID");
        }

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShakeAsync pdas=new ShakeAsync();
                pdas.execute(FranchiseeID,CouponID);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_redemption, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShake(float force) {
        Toast.makeText(getBaseContext(), "Motion detected",
                Toast.LENGTH_LONG).show();
        ShakeAsync pdas=new ShakeAsync();
        pdas.execute(FranchiseeID,CouponID);
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onResume() {
        super.onResume();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isSupported(this)) {

            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

        }


    }

    class ShakeAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');

            String url = getString(R.string.web_url)+"?type=Transfer_CustomerToFranchisee&FranchiseeID="+arg0[0]+"&CouponID="+arg0[1];
            Log.e("QWERT", url);
            String res=GetWebContent.getResponseFromWeb(url);
            Log.e("QWERT", url);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if(result.equals("true")){

                AlertDialog ald=new AlertDialog.Builder(FinalRedemptionActivity.this).create();
                ald.setTitle("Shake Captured ");
                ald.setMessage("Coupon Transferred successfully !");
                ald.setButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "Dialog closed", Toast.LENGTH_LONG).show();

                        Intent ii=new Intent(getApplicationContext(),CustomerHomeActivity.class);
                        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ii.putExtra("AppUserID",CustomerID);
                        startActivity(ii);
                    }
                });
                ald.show();
            }
            else if(result.equals("false")){
                Toast.makeText(getApplicationContext(), "Connection Error ..! ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Try Again..!", Toast.LENGTH_LONG).show();
            }



        }

    }
}

