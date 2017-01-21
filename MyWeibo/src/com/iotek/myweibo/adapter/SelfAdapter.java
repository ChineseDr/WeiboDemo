package com.iotek.myweibo.adapter;

import java.util.List;

import com.iotek.myweibo.R;
import com.iotek.myweibo.entity.SelfItem;
import com.iotek.myweibo.utils.ToastUtils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelfAdapter extends BaseAdapter{
	private Context context;
	private List<SelfItem> selfdatas;

	public SelfAdapter(Context context, List<SelfItem> selfdatas) {
		super();
		this.context = context;
		this.selfdatas = selfdatas;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return selfdatas.size();
	}

	@Override
	public SelfItem getItem(int position) {
		// TODO Auto-generated method stub
		return selfdatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//定义控件
	public static class ViewHolder{
		public View v_divider;
		public View ll_content;
		public ImageView iv_left;
		public TextView tv_subhead;
		public TextView tv_caption;
	}
	
	//关联控件
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView =View.inflate(context, R.layout.item_self, null);
			holder.v_divider=convertView.findViewById(R.id.v_divider);
			holder.ll_content = convertView.findViewById(R.id.ll_content);
			holder.iv_left = (ImageView) convertView.findViewById(R.id.iv_left);
			holder.tv_subhead = (TextView) convertView.findViewById(R.id.tv_subhead);
			holder.tv_caption = (TextView) convertView.findViewById(R.id.tv_caption);
			convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
		
		//绑定数据
		SelfItem item = getItem(position);
		holder.iv_left.setImageResource(item.getLeftImg());
		holder.tv_subhead.setText(item.getSubhead());
		holder.tv_caption.setText(item.getCaption());

		holder.v_divider.setVisibility(item.isShowTopDivider() ? 
				View.VISIBLE : View.GONE);
		holder.ll_content.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtils.showToast(context, "item click position = " + position, Toast.LENGTH_SHORT);
			}
		});
		
		return convertView;
	}

}
