package com.example.ramesh.couponoic_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CustomerHomeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView CouponList;
    String AppUserID;
    ProgressDialog p;
    String[] from={"CampaignName","BenefitValue","CouponCode","EndDate"};
    int[] to={R.id.tvCampaignName,R.id.tvValue,R.id.tvCouponCode,R.id.tvEndDate};
    ArrayList<HashMap<String,String>> data= new ArrayList<HashMap<String,String>>();
    HashMap<String,String> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        CouponList=(ListView)findViewById(R.id.list);


        //Intent i=getIntent();
        //Bundle b=i.getExtras();
        //if(b!=null){
          //  AppUserID=(String)b.get("AppUserID");

        //}
        SharedPreferences pref=getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
        String AppUserID=pref.getString("AppUserID", "0");

        CouponList.setOnItemClickListener(this);
        CouponListAsync evlas=new CouponListAsync();
        evlas.execute(AppUserID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer_home, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String , String> hm = data.get(position);
        String CouponID=hm.get("CouponID");
        Intent i=new Intent(this,CouponDetailActivity.class);
        i.putExtra("CouponID", CouponID);
        i.putExtra("CustomerID",AppUserID);
        startActivity(i);
    }

    class CouponListAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');

            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=Customer_MyWallet&CustomerID="+arg0[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(CustomerHomeActivity.this);
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
                JSONArray arr=new JSONArray(result);

                for(int i=0;i<arr.length();i++){


                    JSONObject CouponListObj=arr.getJSONObject(i);
                    String CampaignName=CouponListObj.getString("CampaignName");
                    String Type=CouponListObj.getString("CampaignType");
                    String jEndDate=CouponListObj.getString("EndDate");
                    int idx1=jEndDate.indexOf("(");
                    int idx2=jEndDate.indexOf(")");
                    String EndDate =jEndDate.substring(idx1+1,idx2);
                    long l=Long.valueOf(EndDate);
                    Date EDate=new Date(l);
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    EndDate = "Last Date :"+sdf.format(EDate);
                    String BenefitValue="";
                    if(Type.equals("Benefit")){

                        BenefitValue="Benefit Value :"+CouponListObj.getString("BenefitValue");

                    }
                    if(Type.equals("Discount")){
                        BenefitValue="Discount Percentage :"+CouponListObj.getString("DiscountValue");
                    }
                    String CouponCode="Coupon code:"+CouponListObj.getString("CouponCode");

                    int CouponID=CouponListObj.getInt("CouponID");

                    HashMap<String, String> hm=new HashMap<String, String>();
                    hm.put("CampaignName", CampaignName);
                    hm.put("CouponCode", CouponCode);
                    hm.put("BenefitValue", BenefitValue);
                    hm.put("EndDate", EndDate);
                    hm.put("CouponID", CouponID+"");
                    data.add(hm);
                }
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), "Connection Error..! Try Again .", Toast.LENGTH_LONG).show();
            }

            SimpleAdapter sa=new SimpleAdapter(getApplicationContext(), data, R.layout.list_row, from, to);
            CouponList.setAdapter(sa);

            p.hide();
        }

    }
}
