package com.example.ramesh.couponoic_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ActivFranchiseeListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView FranchiseeList;
    String CouponID, CustomerID;
    ProgressDialog p;
    String[] from={"FranchiseeName","Address","City","State"};
    int[] to={R.id.tvFranchiseeName,R.id.tvAddress,R.id.tvCity,R.id.tvState};
    ArrayList<HashMap<String,String>> data= new ArrayList<HashMap<String,String>>();
    HashMap<String,String> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activ_franchisee_list);
        FranchiseeList=(ListView)findViewById(R.id.list_f);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        if(b!=null){
            CouponID=(String)b.get("CouponID");
            CustomerID=(String)b.get("CustomerID");
        }
        FranchiseeList.setOnItemClickListener(this);

        FranchiseeAsync a=new FranchiseeAsync();
        a.execute(CouponID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activ_franchisee_list, menu);
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
        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
        HashMap<String , String> hm = data.get(position);
        String FranchiseeID=hm.get("FranchiseeID");
        Intent i=new Intent(this,FinalRedemptionActivity.class);
        i.putExtra("FranchiseeID", FranchiseeID);
        i.putExtra("CouponID", CouponID);
        startActivity(i);

    }


    class FranchiseeAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');

            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=Customer_ListAllFranchisee&CouponID="+arg0[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(ActivFranchiseeListActivity.this);
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
                    String FranchiseeName=CouponListObj.getString("Name");
                    String Address=CouponListObj.getString("Address");
                    String City=CouponListObj.getString("City");
                    String State=CouponListObj.getString("State");
                    int FranchiseeID=CouponListObj.getInt("AppUserID");

                    HashMap<String, String> hm=new HashMap<String, String>();
                    hm.put("FranchiseeName", FranchiseeName);
                    hm.put("Address", Address);
                    hm.put("City", City);
                    hm.put("State", State);
                    hm.put("FranchiseeID", FranchiseeID+"");
                    data.add(hm);
                }
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), "Connection Fail. Try Again ", Toast.LENGTH_LONG).show();
            }

            SimpleAdapter sa=new SimpleAdapter(getApplicationContext(), data, R.layout.list_row_f, from, to);
            FranchiseeList.setAdapter(sa);

            p.hide();
        }

    }
}
