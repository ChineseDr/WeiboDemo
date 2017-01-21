package com.iotek.myweibo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.iotek.myweibo.R;
import com.iotek.myweibo.constants.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class SplashActivity extends Activity {
	
	private static final int WHAT_INTENT2LOGIN = 1;
	private static final int WHAT_INTENT2MAIN = 2;
	private static final long SPLASH_DUR_TIME = 1000;


	private Oauth2AccessToken accessToken;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case WHAT_INTENT2LOGIN:
				Intent intent1=new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(intent1);
				finish();
				break;
			case WHAT_INTENT2MAIN:
				Intent intent2=new Intent(SplashActivity.this,MainActivity.class);
				startActivity(intent2);
				finish();
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_welcome);
		
		accessToken = AccessTokenKeeper.readAccessToken(this);
		if(accessToken.isSessionValid()) {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2MAIN, SPLASH_DUR_TIME);
		} else {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2LOGIN, SPLASH_DUR_TIME);
		}
	}
}
