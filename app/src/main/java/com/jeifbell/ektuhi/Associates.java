package com.jeifbell.ektuhi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Associates extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		ImageView f = (ImageView) findViewById(R.id.imageView2);
		f.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse ("http://snapower.net/tech/")));
			}
			
		});
		ImageView f1 = (ImageView) findViewById(R.id.imageView1);
		f1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Here 1");
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse ("http://fostinno.com/")));
				System.out.println("Here 2");
			}
			
		});
		ImageView f2 = (ImageView) findViewById(R.id.imageView3);
		f2.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				System.out.println("on image click");
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse ("http://www.startupelves.com/")));
			}
			
		});
		
	}
}
