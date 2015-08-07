package com.example.ramesh.couponoic_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity {
    EditText etxtUsername, etxtPassword;
    Button btnLogin;
    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etxtUsername=(EditText)findViewById(R.id.etxtUsername);
        etxtPassword=(EditText)findViewById(R.id.etxtPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAsync l=new LoginAsync();
                l.execute(etxtUsername.getText().toString(),etxtPassword.getText().toString());
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    class LoginAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p=new ProgressDialog(LoginActivity.this);
            p.setMessage("Loading... Please wait");
            p.setCanceledOnTouchOutside(false);
            p.setIndeterminate(true);
            p.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //String res=GetWebContent.getResponseFromWeb('"'+R.string.web_url+"CheckLogin.aspx?username="+arg0[0]+"&password="+arg0[1]+'"');
            String res=GetWebContent.getResponseFromWeb(getString(R.string.web_url)+"?type=VerifyLogin"+"&username="+arg0[0]+"&password="+arg0[1]);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if(result!=null) {
                try {

                    JSONObject user = new JSONObject(result);

                    String AppUserID = user.getString("AppUserID");

                    String type = user.getString("Type");
                    if (type.toString().equals("Customer")) {
                        Intent i = new Intent(getApplicationContext(),CustomerHomeActivity.class);
                        i.putExtra("AppUserID", AppUserID);
                        startActivity(i);
                    } else if (type.toString().equals("Franchisee")) {
                        String v1=user.getString("FranchiseeVisibilityMode");
                        Intent i = new Intent(getApplicationContext(),FranchiseeHomeActivity.class);
                        i.putExtra("AppUserID", AppUserID);
                        i.putExtra("FranchiseeVisibilityMode", v1);
                        startActivity(i);
                    }

                    SharedPreferences pref=getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor edit=pref.edit();
                    edit.putString("AppUserID", AppUserID).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Try Again ..", Toast.LENGTH_LONG).show();
                    Log.e("QWERT","Error in login" );
                }

            }else{
                Toast.makeText(getApplicationContext(), "Invalid Credentials..Try Again", Toast.LENGTH_LONG).show();
            }
            p.hide();
        }

    }

}
