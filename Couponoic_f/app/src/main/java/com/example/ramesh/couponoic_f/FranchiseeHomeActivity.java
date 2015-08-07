package com.example.ramesh.couponoic_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FranchiseeHomeActivity extends ActionBarActivity {

    protected ImageButton btnVisible, btnDisable;
    protected Button btnReceivedCoupons, btnLogout, btnSearchCoupon;
    protected TextView txtVisibilityMsg;
    public String AppUserID,FranchiseeVisibilityMode;
    public ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisee_home);

        btnVisible=(ImageButton)findViewById(R.id.btnVisible);
        btnDisable=(ImageButton)findViewById(R.id.btnDisable);
        btnReceivedCoupons=(Button)findViewById(R.id.btnReceivedCoupons);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        txtVisibilityMsg=(TextView)findViewById(R.id.txtVisibilityMsg);
        btnSearchCoupon=(Button)findViewById(R.id.btnSearchCoupon);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        if(b!=null){
            AppUserID=(String)b.get("AppUserID");
            FranchiseeVisibilityMode=(String)b.get("FranchiseeVisibilityMode");
        }

        if(FranchiseeVisibilityMode.toString().equals("0")){
            btnDisable.setVisibility(View.INVISIBLE);
            txtVisibilityMsg.setText("Your Device is Disable. Please Click on Visible to make device Visible. ");
        }
        else if(FranchiseeVisibilityMode.toString().equals("1")){
            btnVisible.setVisibility(View.INVISIBLE);
            txtVisibilityMsg.setText("Your Device is Visible. You can make it Disable by clicking on Disable. ");
        }

        btnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeMeVisible mv = new MakeMeVisible();
                mv.execute(AppUserID);
            }
        });

        btnDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeMeDisable mv=new MakeMeDisable();
                mv.execute(AppUserID);
            }
        });

        btnReceivedCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FranchiseeHomeActivity.this, FranchiseeAllCouponsActivity.class);
                i.putExtra("AppUserID",AppUserID);
                startActivity(i);
            }
        });

        btnSearchCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FranchiseeHomeActivity.this, FranchiseeSerachCouponActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FranchiseeHomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_franchisee_home, menu);
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


    class MakeMeVisible extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');

            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=MakeMeVisible&AppUserID="+arg0[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(FranchiseeHomeActivity.this);
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
            if(result.equals("true")) {
                p.hide();
                btnDisable.setVisibility(View.VISIBLE);
                btnVisible.setVisibility(View.INVISIBLE);
                txtVisibilityMsg.setText("Your Device is Visible. You can make it Disable by clicking on Disable. ");
            }
            else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
    }

    class MakeMeDisable extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');

            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=MakeMeDisable&AppUserID="+arg0[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(FranchiseeHomeActivity.this);
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

            if(result.equals("true")) {
                p.hide();
                btnDisable.setVisibility(View.INVISIBLE);
                btnVisible.setVisibility(View.VISIBLE);
                txtVisibilityMsg.setText("Your Device is Disable. Please Click on Visible to make device Visible. ");
            }
            else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
    }
}
