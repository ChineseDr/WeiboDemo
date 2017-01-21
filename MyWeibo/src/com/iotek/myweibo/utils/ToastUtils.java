package com.iotek.myweibo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * @author Administrator
 *
 */
public class ToastUtils {
	private static Toast mToast;
	
	/*
	 * 显示Toast
	 */
	@SuppressWarnings("unused")
	public static void showToast(Context context,CharSequence text,int duration) {
		// TODO Auto-generated method stub
		if(mToast==null){
			mToast=Toast.makeText(context, text, duration);
		}else{
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
	}
	
}
