package com.iotek.myweibo.framment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class FragmentController{
	private int containerId;//容器地址
	private FragmentManager fm;//管理器
	private ArrayList<Fragment> fragments;//集合存放fragment
	
	private static FragmentController fg_controller;//?单利
	
	

	private FragmentController(Activity activity,int containerId) {
		this.containerId = containerId;
		fm=activity.getFragmentManager();
		initFragment();
	}

	public static FragmentController getInstance(Activity activity, int containerId) {
		if (fg_controller == null) {
			fg_controller=new FragmentController(activity,containerId);
		}
		return fg_controller;
	}
	
	//初始化fragments集合
	private void initFragment() {
		// TODO Auto-generated method stub
		fragments=new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new MessageFragment());
		fragments.add(new SearchFragment());
		fragments.add(new SelfFragment());
		
		FragmentTransaction ft=fm.beginTransaction();
		//遍历集合中的fragment
		for (Fragment fragment : fragments) {
			ft.add(containerId, fragment);
		}
		ft.commit();
	}
	
	//隐藏fragment
	public void hideFragment() {
		// TODO Auto-generated method stub
		FragmentTransaction ft=fm.beginTransaction();
		for (Fragment fragment : fragments) {
			if(fragment!=null){
				ft.hide(fragment);
			}
		}
		ft.commit();
	}
	
	//显示fragment
	public void showFragment(int position) {
		// TODO Auto-generated method stub
		hideFragment();
		Fragment fragment=fragments.get(position);
		FragmentTransaction ft=fm.beginTransaction();
		ft.show(fragment);
		ft.commit();
	}
	
	//获取fragment
	public Fragment getFragment(int position) {
		// TODO Auto-generated method stub
		return fragments.get(position);

	}
	
	
}
