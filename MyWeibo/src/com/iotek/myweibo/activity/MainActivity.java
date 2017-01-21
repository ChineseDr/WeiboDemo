package com.iotek.myweibo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.iotek.myweibo.R;
import com.iotek.myweibo.framment.FragmentController;
import com.iotek.myweibo.utils.ToastUtils;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		OnClickListener {

	private RadioGroup tab_rg;// 底部布局
	private ImageView add_iv;// 发送微博按钮
	private FragmentController fc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		fc = FragmentController.getInstance(this, R.id.fg_main);
		fc.showFragment(0);

		initView();
	}

	// 初始化空间
	private void initView() {
		// TODO Auto-generated method stub
		tab_rg = (RadioGroup) findViewById(R.id.bottom_tab_bar);
		add_iv = (ImageView) findViewById(R.id.add_iw);

		tab_rg.setOnCheckedChangeListener(this);
		add_iv.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.home_btn:
			fc.showFragment(0);
			break;
		case R.id.message_btn:
			fc.showFragment(1);
			break;
		case R.id.search_btn:
			fc.showFragment(2);
			break;
		case R.id.self_btn:
			fc.showFragment(3);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_iw:
			
			startActivity(new Intent(MainActivity.this, AddStatusActivity.class));
			ToastUtils.showToast(this, "发送微博", Toast.LENGTH_SHORT);
			finish();
			break;

		default:
			break;
		}
	}
}
