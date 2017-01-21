package com.iotek.myweibo.framment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iotek.myweibo.R;
import com.iotek.myweibo.utils.TitleBuilder;
import com.iotek.myweibo.utils.ToastUtils;

public class SearchFragment extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = View.inflate(getActivity(), R.layout.fg_search, null);

		new TitleBuilder(view).setTitleText("发现").setRightText("发现").setRightOnClicklistener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ToastUtils.showToast(getActivity(), "发现", Toast.LENGTH_SHORT);
					}
				});
		return view;
	}
}
