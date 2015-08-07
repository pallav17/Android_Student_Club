package com.example.ramesh.couponoic_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FranchiseeSerachCouponActivity extends ActionBarActivity {

    protected ImageButton iBtnSearch;
    protected EditText eTxtSCouponCode;
    protected TextView txtMsg;
    public String CouponCode;
    ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisee_serach_coupon);

        iBtnSearch=(ImageButton)findViewById(R.id.iBtnSearch);
        eTxtSCouponCode=(EditText)findViewById(R.id.etxtSCouponCode);
        txtMsg=(TextView)findViewById(R.id.txtMsg);
        txtMsg.setText("");

        iBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponCode=eTxtSCouponCode.getText().toString();
                CouponDetails pdas=new CouponDetails();
                pdas.execute(CouponCode);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_franchisee_serach_coupon, menu);
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

    class CouponDetails extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');

            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=Franchisee_getCouponID&CouponCode="+arg0[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(FranchiseeSerachCouponActivity.this);
            p.setMessage("Loading... Please wait");
            p.setCanceledOnTouchOutside(false);
            p.setIndeterminate(true);
            p.show();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            try{

                if(result.length()>30){
                    Toast.makeText(getApplicationContext(), "No result found.", Toast.LENGTH_LONG).show();
                    txtMsg.setText("No Result found");
                }
                else{
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    Intent i=new Intent(FranchiseeSerachCouponActivity.this, FranchiseeCouponDetailActivity.class);
                    i.putExtra("CouponID",result);
                    startActivity(i);
                    finish();
                }

            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), "Connection fail. Try Again.", Toast.LENGTH_LONG).show();
            }

            p.hide();
            //intent
        }

    }
}
