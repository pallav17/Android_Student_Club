package com.chintanvacchani.bmi_fitness;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.chintanvacchani.bmi_fitness.R;

public class MainActivity extends Activity
{
	int flag=1;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		final ImageView boy = (ImageView)findViewById(R.id.imageView1);
		final ImageView girl = (ImageView)findViewById(R.id.imageView2);
		
		final TextView tv3 = (TextView)findViewById(R.id.textView3);
		final TextView tv4 = (TextView)findViewById(R.id.textView4);
		final TextView tv5 = (TextView)findViewById(R.id.textView5);
		final TextView tv6 = (TextView)findViewById(R.id.textView7);
		final TextView tv7 = (TextView)findViewById(R.id.textView8);
		
		girl.setColorFilter(Color.GRAY);
		girl.setColorFilter(Color.BLACK);
		
		boy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				girl.setColorFilter(Color.GRAY);
				boy.setColorFilter(Color.BLACK);
				flag=1;
			}
		});
		

		girl.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				boy.setColorFilter(Color.GRAY);
				girl.setColorFilter(Color.BLACK);
				flag=0;
			}
		});
		
				
		Button calculate = (Button)findViewById(R.id.button1);
		calculate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				tv3.setTextColor(Color.GRAY);
				tv4.setTextColor(Color.GRAY);
				tv5.setTextColor(Color.GRAY);
				tv6.setTextColor(Color.GRAY);
				tv7.setTextColor(Color.GRAY);
				
				EditText weight = (EditText)findViewById(R.id.editText1);
				EditText height = (EditText)findViewById(R.id.editText2);

				float we = Float.parseFloat(weight.getText().toString());
				float he = Float.parseFloat(height.getText().toString());
				
				float ans = (float) ((we)/Math.pow(he,2));
				
				if(flag==0)
				{
					ans=ans-1;
				}
				
				if(ans < 15)
				{
					tv3.setTextColor(Color.RED);
				}
				else if( ans < 18.5 )
				{
					tv4.setTextColor(Color.BLUE);
				}
				else if( ans < 25 )
				{
					tv5.setTextColor(Color.GREEN);
				}
				else if( ans < 30 )
				{
					tv4.setTextColor(Color.BLUE);
				}
				else
				{
					tv4.setTextColor(Color.RED);
				}
				
				
			}
		});
		
	}
}
