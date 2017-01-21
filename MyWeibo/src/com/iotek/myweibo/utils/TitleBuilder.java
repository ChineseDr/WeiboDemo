package com.iotek.myweibo.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.myweibo.R;

/**
 * include标题栏创建类
 * 
 * @author Administrator
 * 
 */
public class TitleBuilder {
	private View titleView;
	private TextView tvTitle;
	private ImageView ivLeft;
	private ImageView ivRight;
	private TextView tvLeft;
	private TextView tvRight;

	// 传递activity的构造方法
	public TitleBuilder(Activity context) {
		titleView =context.findViewById(R.id.ic_titlebar);
		tvTitle = (TextView) titleView.findViewById(R.id.titletext);
		ivLeft = (ImageView) titleView.findViewById(R.id.titlebar_iw_left);
		ivRight = (ImageView) titleView.findViewById(R.id.titlebar_iw_right);
		tvLeft = (TextView) titleView.findViewById(R.id.titlebar_tx_left);
		tvRight = (TextView) titleView.findViewById(R.id.titlebar_tx_right);
	}

	// 传递view的构造方法
	public TitleBuilder(View context) {
		titleView = context.findViewById(R.id.ic_titlebar);
		tvTitle = (TextView) titleView.findViewById(R.id.titletext);
		ivLeft = (ImageView) titleView.findViewById(R.id.titlebar_iw_left);
		ivRight = (ImageView) titleView.findViewById(R.id.titlebar_iw_right);
		tvLeft = (TextView) titleView.findViewById(R.id.titlebar_tx_left);
		tvRight = (TextView) titleView.findViewById(R.id.titlebar_tx_right);
	}

	// 标题titleview
	// 1、背景资源
	public TitleBuilder setTitleBgRes(int resid) {
		titleView.setBackgroundResource(resid);
		return this;
	}

	// 2、标题文本内容,view的setVisibility方法，Visibility控制view可见性
	public TitleBuilder setTitleText(String text) {
		tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		tvTitle.setText(text);
		return this;
	}

	// Left
	// 1、左边图片，参数
	public TitleBuilder setLeftImage(int resId) {
		ivLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
		ivLeft.setImageResource(resId);
		return this;
	}

	// 2、左边文字,参数
	public TitleBuilder setLeftText(String text) {
		tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		tvLeft.setText(text);
		return this;
	}

	// 3、左边点击，参数listener
	public TitleBuilder setLeftOnClickListener(OnClickListener listener) {
		if (ivLeft.getVisibility() == View.VISIBLE) {
			ivLeft.setOnClickListener(listener);
		} else if (tvLeft.getVisibility() == View.VISIBLE) {
			tvLeft.setOnClickListener(listener);
		}
		return this;
	}

	// Right
	// 1、右边图片，参数
	public TitleBuilder setRightImage(int resId) {
		ivRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
		ivRight.setImageResource(resId);
		return this;
	}

	// 2、右边文字,参数
	public TitleBuilder setRightText(String text) {
		tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		tvRight.setText(text);
		return this;
	}

	// 3、右边点击，参数listener
	public TitleBuilder setRightOnClicklistener(OnClickListener listener) {
		if (ivRight.getVisibility() == View.VISIBLE) {
			ivRight.setOnClickListener(listener);
		} else if (tvRight.getVisibility() == View.VISIBLE) {
			tvRight.setOnClickListener(listener);
		}
		return this;
	}
	
	//返回值标题view
	public View build(){
		return titleView;
	}

}
