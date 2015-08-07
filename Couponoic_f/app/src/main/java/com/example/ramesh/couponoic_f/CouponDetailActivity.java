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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CouponDetailActivity extends ActionBarActivity {

    TextView tvCampaignName,tvCompanyName, tvCouponCode, tvStartDate, tvEndDate, tvPurchaseValue, tvBenefitvalue, tvType;
    Button btnRedeem;
    ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        tvCampaignName=(TextView)findViewById(R.id.tvCampaignNameDetailPage);
        tvCompanyName=(TextView)findViewById(R.id.tvCompanyNameDetailPage);
        tvCouponCode=(TextView)findViewById(R.id.tvCouponCodeDetailPage);
        tvStartDate=(TextView)findViewById(R.id.tvStartDateDetailPage);
        tvEndDate=(TextView)findViewById(R.id.tvendDateDetailPage);
        tvPurchaseValue=(TextView)findViewById(R.id.tvPurchaseValueDetailPage);
        tvBenefitvalue=(TextView)findViewById(R.id.tvBenefitValueDetailPage);
        tvType=(TextView)findViewById(R.id.tvType);
        btnRedeem=(Button)findViewById(R.id.btnRedeem);

        Intent i=getIntent();
        final String CouponID=i.getStringExtra("CouponID");
        final String CustomerID=i.getStringExtra("CustomerID");

        CouponDetails pdas=new CouponDetails();
        pdas.execute(CouponID);

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ActivFranchiseeListActivity.class);
                i.putExtra("CouponID",CouponID);
                i.putExtra("CustomerID", CustomerID);
                //Intent ii=new Intent(getApplicationContext(),CustomerHomeActivity.class);
                //ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(ii);

                startActivity(i);


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon_detail, menu);
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

            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=Customer_CouponDetails&CouponID="+arg0[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(CouponDetailActivity.this);
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
                JSONObject coupon=arr.getJSONObject(0);
                tvCampaignName.setText(coupon.getString("Name"));
                tvCompanyName.setText(" by "+coupon.getString("CompanyName"));
                tvCouponCode.setText(coupon.getString("CouponCode"));

                //start Date
                String SDate=coupon.getString("StartDate");
                int idx1=SDate.indexOf("(");
                int idx2=SDate.indexOf(")");
                String StartDate =SDate.substring(idx1+1,idx2);
                long l=Long.valueOf(StartDate);
                Date SDate1=new Date(l);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                StartDate = sdf.format(SDate1);
                tvStartDate.setText(StartDate);

                //End Date
                String EDate=coupon.getString("EndDate");
                idx1=EDate.indexOf("(");
                idx2=EDate.indexOf(")");
                String EndDate =EDate.substring(idx1+1,idx2);
                l=Long.valueOf(EndDate);
                Date EDate1=new Date(l);
                EndDate = sdf.format(EDate1);
                tvEndDate.setText(EndDate);

                tvPurchaseValue.setText(coupon.getString("BaseValue"));
                String type=coupon.getString("Type");
                if(type.equals("Benefit")){
                    tvType.setText("Benefit Amount");
                    tvBenefitvalue.setText(coupon.getString("BenefitValue"));
                }
                if(type.equals("Discount")){
                    tvType.setText("Discount Percentage");
                    tvBenefitvalue.setText(coupon.getString("DiscountValue"));
                }
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), "Connection fail. Try Again.", Toast.LENGTH_LONG).show();
            }

            p.hide();
        }

    }

}
