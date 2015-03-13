package net.sourceforge.zbar.android.CameraTest;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Scan extends Activity {
	private Button scan;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scan);
		scan = (Button) findViewById(R.id.scan);
		scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Scan.this, CameraTestActivity.class);
				startActivity(i);
		
			}
		});
		final ImageView iv = (ImageView) findViewById(R.id.ima);
		final int []imageArray={R.drawable.a,R.drawable.b,R.drawable.c};


		final Handler handler = new Handler();
		         Runnable runnable = new Runnable() {
		            int i=0;
		            public void run() {
		                iv.setImageResource(imageArray[i]);
		                i++;
		                if(i>imageArray.length-1)
		                {
		                i=0;    
		                }
		                handler.postDelayed(this, 3000);  //for interval...
		            }
		        };
		        handler.postDelayed(runnable, 50); //for initial delay..
		    }
	}


