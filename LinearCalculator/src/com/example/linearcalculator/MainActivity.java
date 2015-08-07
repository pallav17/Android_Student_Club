package com.example.linearcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	EditText et;
	Button but[]=new Button[19];
	int bid[]={R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,R.id.btnplus,R.id.btnminus,R.id.btnequal,R.id.btndiv,R.id.btnmulti,R.id.btnpoint,R.id.btnac,R.id.btnpm,R.id.btnmod};
	String data="",data1="";
	String ope="";
	int flag=0;
	String ans="";
	double no1,no2,d;
	private static long back_pressed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et=(EditText)findViewById(R.id.etcalc);
		
		for(int i=0;i<but.length;i++)
		{
			but[i]=(Button)findViewById(bid[i]);
			but[i].setOnClickListener(this);
		}
			
	}

	@Override
	public void onClick(View v) 
	{
		et.setText("");
		
		Button b=(Button)v;
		
		String s=b.getText().toString();
				
		if(flag==1)
		{
			if((s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*")) && !data1.equals(""))
			{
				try
				{
				check();
				
				flag=1;
				data=ans;
				data1="";
				ope="s";
				}
				catch(Exception e)
				{					
					data=ans;
					ope="s";
					flag=1;	
					data1="";
				}
			}
			else if(s.equals("="))
			{
				et.setText(data);
			}
			
		}
		
		if(data.equals("") && flag==1)
		{
			data="0";
		}
		
		if(data1.equals("") && flag==1 && s.equals("="))
		{
			data1=data;
			check();
			
			flag=0;
			data=ans;
			data1="";
			//ope="";
			 
		}
		
		
		if(s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*"))
		{
			et.setText(data);
			ope=s;
			//et.setText(s);
			flag=1;

		}
		else if(s.equals("="))
		{
			et.setText(ans);
			flag=2;
		}
		else if(s.equals("AC"))
		{
			data="";
			data1="";
			ope="";
			flag=0;		
			et.setText("0");
		}
		else if(s.equals("C"))
		{
			if(flag==0)
			{
				data="0";
				et.setText(data);
				flag=0;
			}	
			if(flag==1)
			{
				data1="0";
				et.setText(data1);
				flag=1;
			}
			but[16].setText("AC");
		}
		else if(s.equals("+/-"))
		{
			if(flag==0)
			{
				if(data.contains("-"))
					data=data.substring(1);
				else
					data="-"+data;
				
				et.setText(data);
			}	
			if(flag==1)
			{
				if(data1.contains("-"))
					data1=data1.substring(1);
				else
					data1="-"+data1;
				
				et.setText(data1);
			}
		}
		else if(s.equals("%"))
		{
			try
			{
				if(ope.equals(""))
				{
						double a=Double.parseDouble(data);
						a=a/100;
						data=Double.toString(a);
						et.setText(data);
						flag=1;
						ans=data;
						data1=data;
				}
				else if(ope.equals("+") || ope.equals("-") )
				{
					double x=Double.parseDouble(data);
					double y=Double.parseDouble(data1);
				
					double z=x*y/100;
				
					data1=Double.toString(z);
					et.setText(data1);
					flag=1;
				}
				else if(ope.equals("/") || ope.equals("*") )
				{
					double y=Double.parseDouble(data1);
				
					double z=y/100;
		
					data1=Double.toString(z);
					et.setText(data1);
					flag=1;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		else if(s.equals("."))
		{
			if(flag==0)
			{
				if(data.contains("."))
					data=data;
				else
					data=data+".";
				et.setText(data);
			}
			if(flag==1)
			{
				if(data1.contains("."))
					data1=data1;
				else
					data1=data1+".";
				et.setText(data1);
			}
			but[16].setText("C");
		}
		else if(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") || s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9") || s.equals("0"))
		{
			if(flag==0)
			{
				data=data+s;
				et.setText(data);
			}
			if(flag==1)
			{
				data1=data1+s;
				et.setText(data1);
			}
			
			but[16].setText("C");
		}
		
		if(flag==2)
		{
			try
			{
			check();
			
			flag=0;
			data=ans;
			data1="";
			//ope="";
			}
			catch(Exception e)
			{

			}
		}
		
	}
	protected void check()
	{
		no1=Double.parseDouble(data);
		no2=Double.parseDouble(data1);
		d=0.0;
		
		if(ope.equals("+"))
		{
				d=no1+no2;
		}
		else if(ope.equals("-"))
		{
			d=no1-no2;
		}
		else if(ope.equals("*"))
		{
			d=no1*no2;
		}
		else if(ope.equals("/"))
		{
			d=no1/no2;
		}
		else
		{
			d=Double.parseDouble(ans);
		}
		ans=Double.toString(d);
		et.setText(ans);
	}
	
	@Override
	public void onBackPressed()
	{
	        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
	        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
	        back_pressed = System.currentTimeMillis();
	}
	
}
