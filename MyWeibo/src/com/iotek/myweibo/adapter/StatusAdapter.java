package com.iotek.myweibo.adapter;

import java.util.ArrayList;

import com.iotek.myweibo.R;
import com.iotek.myweibo.activity.ShareStatusActivity;
import com.iotek.myweibo.utils.DateUtils;
import com.lidroid.xutils.BitmapUtils;
import com.sina.weibo.sdk.call.Position;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StatusAdapter extends BaseAdapter {
	private Context context;
	private StatusList datas;
	private Status status;
	private ArrayList<String> pic_urls;

	// private ImageLoader imageLoader;

	public StatusAdapter(Context context, StatusList datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	public int getCount() {
		return datas.statusList.size();
	}

	@Override
	public Status getItem(int position) {
		return datas.statusList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 关联空间
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_status, null);
			holder.ll_status_content = (LinearLayout) convertView
					.findViewById(R.id.ll_status_content);
			holder.iv_status_from = (ImageView) convertView
					.findViewById(R.id.iv_status_from);
			holder.rl_status_form = (RelativeLayout) convertView
					.findViewById(R.id.rl_status_form);
			holder.tv_subhead = (TextView) convertView
					.findViewById(R.id.tv_subhead);
			holder.tv_caption = (TextView) convertView
					.findViewById(R.id.tv_caption);
			// 微博正文
			holder.tv_status_content = (TextView) convertView
					.findViewById(R.id.tv_status_content);
			// 图片
			holder.include_status_image = (LinearLayout) convertView
					.findViewById(R.id.ll_status_image);

			holder.gv_status_images = (GridView) convertView
					.findViewById(R.id.gv_status_images);
			holder.iv_status_image = (ImageView) convertView
					.findViewById(R.id.iv_status_image);
			// 转发评论，赞
			holder.ll_share_bottom = (LinearLayout) convertView
					.findViewById(R.id.ll_share_bottom);
			holder.iv_share_bottom = (ImageView) convertView
					.findViewById(R.id.iv_share_bottom);
			holder.tv_share_bottom = (TextView) convertView
					.findViewById(R.id.tv_share_bottom);
			holder.ll_comment_bottom = (LinearLayout) convertView
					.findViewById(R.id.ll_comment_bottom);
			holder.iv_comment_bottom = (ImageView) convertView
					.findViewById(R.id.iv_comment_bottom);
			holder.tv_comment_bottom = (TextView) convertView
					.findViewById(R.id.tv_comment_bottom);
			holder.ll_like_bottom = (LinearLayout) convertView
					.findViewById(R.id.ll_like_bottom);
			holder.iv_like_bottom = (ImageView) convertView
					.findViewById(R.id.iv_like_bottom);
			holder.tv_like_bottom = (TextView) convertView
					.findViewById(R.id.tv_like_bottom);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 作者的用户信息
		if (datas.statusList.get(position).user != null) {
			// 获取头像
			bitmapUtils.display(holder.iv_status_from,
					datas.statusList.get(position).user.profile_image_url + "");
			// 用户名
			holder.tv_subhead
					.setText(datas.statusList.get(position).user.screen_name
							+ "");
			// 发布时间和来源
			holder.tv_caption.setText(DateUtils.getShortTime(datas.statusList
					.get(position).created_at)
					+ "来自"
					+ Html.fromHtml(datas.statusList.get(position).source));
		}
		// 微博的信息内容
		holder.tv_status_content.setText(datas.statusList.get(position).text
				+ "");
		/*// 判断是否拿到图片数据
		if (datas.statusList.get(position).pic_urls != null) {

			pic_urls = new ArrayList<String>();
			pic_urls = datas.statusList.get(position).pic_urls;

			holder.gv_status_images.setAdapter(new GridAdapter(pic_urls));

		}*/
		// setImages(bitmapUtils,position, holder.include_status_image,
		// holder.gv_status_images, holder.iv_status_image);
		// 转发数
		holder.tv_share_bottom
				.setText(datas.statusList.get(position).reposts_count + "");
		// 评论数
		holder.tv_comment_bottom
				.setText(datas.statusList.get(position).comments_count + "");
		// 点赞数
		holder.tv_like_bottom
				.setText(datas.statusList.get(position).attitudes_count + "");
		// 转发操作
		holder.ll_share_bottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ShareStatusActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("shared_id", datas.statusList.get(position).id);
				intent.putExtra("bundle", bundle);
				context.startActivity(intent);
			}
		});

		// 评论操作
		holder.ll_comment_bottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		// 点赞操作
		holder.ll_like_bottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return convertView;
	}

	/*
	 * private void setImages(BitmapUtils bitmapUtil,int position, LinearLayout
	 * imgContainer, GridView gv_images, ImageView iv_image) { ArrayList<String>
	 * pic_urls = datas.statusList.get(position).pic_urls; String thumbnail_pic
	 * = datas.statusList.get(position).bmiddle_pic;;
	 * 
	 * if(pic_urls != null && pic_urls.size() > 1) {
	 * imgContainer.setVisibility(View.VISIBLE);
	 * gv_images.setVisibility(View.VISIBLE); iv_image.setVisibility(View.GONE);
	 * 
	 * StatusGridImgsAdapter gvAdapter = new StatusGridImgsAdapter(context,
	 * pic_urls); gv_images.setAdapter(gvAdapter); } else if(thumbnail_pic !=
	 * null) { imgContainer.setVisibility(View.VISIBLE);
	 * gv_images.setVisibility(View.GONE); iv_image.setVisibility(View.VISIBLE);
	 * 
	 * bitmapUtil.display(iv_image,thumbnail_pic); } else {
	 * imgContainer.setVisibility(View.GONE); } }
	 */

	// 控件持有
	public static class ViewHolder {
		private LinearLayout ll_status_content;// 整个微博内容控件
		private ImageView iv_status_from;// 微博发表者头像
		private RelativeLayout rl_status_form;//
		private TextView tv_subhead;// 微博发表者昵称
		private TextView tv_caption;// 微博来自
		// 微博正文
		private TextView tv_status_content;// 微博内容
		public LinearLayout include_status_image;// 微博嵌套framlayout图片
		public GridView gv_status_images;// 微博图片GridView
		public ImageView iv_status_image;// 微博图片
		// 转发微博的正文
		public TextView tv_shared_content;// 被转发微博的内容
		public FrameLayout include_shared_status_image;// 被转发微博嵌套framlayout图片
		public GridView gv_shared_images;// 被转微博图片GridView
		public ImageView iv_shared_image;// ImageView
		// 转发评论，赞
		public LinearLayout ll_share_bottom;
		public ImageView iv_share_bottom;
		public TextView tv_share_bottom;
		public LinearLayout ll_comment_bottom;
		public ImageView iv_comment_bottom;
		public TextView tv_comment_bottom;
		public LinearLayout ll_like_bottom;
		public ImageView iv_like_bottom;
		public TextView tv_like_bottom;
	}

	class GridAdapter extends BaseAdapter {
		private ImageView iv;
		private ArrayList<String> pic_urls;

		public GridAdapter(ArrayList<String> pic_urls) {
			this.pic_urls = pic_urls;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pic_urls.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pic_urls.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.id.ll_status_image, null);

			}
			iv = (ImageView) convertView.findViewById(R.id.iv_status_image);

			BitmapUtils bitmapUtils = new BitmapUtils(context);
			bitmapUtils.display(iv, pic_urls.get(position) + "");

			return convertView;
		}

	}

}
