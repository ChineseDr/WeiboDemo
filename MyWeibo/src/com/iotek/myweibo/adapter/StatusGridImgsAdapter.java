package com.iotek.myweibo.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.iotek.myweibo.R;
import com.lidroid.xutils.BitmapUtils;

public class StatusGridImgsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> datasPic_url;
	private BitmapUtils bitmapUtil;

	public StatusGridImgsAdapter(Context context,
			ArrayList<String> datasPic_url) {
		super();
		this.context = context;
		this.datasPic_url = datasPic_url;
	}

	@Override
	public int getCount() {
		return datasPic_url.size();
	}

	@Override
	public String getItem(int position) {
		return datasPic_url.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			convertView = View.inflate(context, R.id.ll_status_image, null);
			ImageView iv_image = (ImageView) convertView.findViewById(R.id.iv_status_image);
			
		
		GridView gv = (GridView) parent;
		int horizontalSpacing = gv.getHorizontalSpacing();//水平间距
		int numColumns = gv.getNumColumns();//列数
		//(宽-间距-内边距)/列数
		int itemWidth = (gv.getWidth() - (numColumns-1) * horizontalSpacing
				- gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;

		LayoutParams params = new LayoutParams(itemWidth, itemWidth);
		iv_image.setLayoutParams(params);
//		String urls = getItem(position);
		Log.i("Test", datasPic_url.get(position)+"");
		bitmapUtil.display(iv_image,datasPic_url.get(position)+"");
		
		return convertView;
	}

}
