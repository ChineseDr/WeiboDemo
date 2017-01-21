package com.iotek.myweibo.framment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.myweibo.R;
import com.iotek.myweibo.adapter.SelfAdapter;
import com.iotek.myweibo.constants.AccessTokenKeeper;
import com.iotek.myweibo.constants.Constants;
import com.iotek.myweibo.entity.SelfItem;
import com.iotek.myweibo.utils.TitleBuilder;
import com.iotek.myweibo.utils.ToastUtils;
import com.iotek.myweibo.widget.WrapHeightListView;
import com.lidroid.xutils.BitmapUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

public class SelfFragment extends Fragment {
	private LinearLayout ll_self_info;
	// 个人信息
	private ImageView iv_avatar;// 用户头像
	private TextView tv_subhead;// 用户名
	private TextView tv_caption;// 简介
	// 微博，关注，粉丝数
	private TextView tv_status_count;// 微博
	private TextView tv_follow_count;// 关注
	private TextView tv_fans_count;// 粉丝
	// 我的每一条
	private WrapHeightListView lv_self_items;// 个人页自定义listview

	private View view;
	private long uid;

	private SelfAdapter adapter;
	private List<SelfItem> selfItems;

	private StatusesAPI statusesAPI;
	private Oauth2AccessToken mAccessToken;
	private UsersAPI usersAPI;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fg_self, null);
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());

		// 用户信息接口
		usersAPI = new UsersAPI(getActivity(), Constants.APP_KEY, mAccessToken);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			uid = Long.parseLong(mAccessToken.getUid());
			// 获取用户信息
			usersAPI.show(uid, myRListener);
			long[] uids = { Long.parseLong(mAccessToken.getUid()) };
			// 获取用户粉丝关注微博数
			usersAPI.counts(uids, myRListener);
		}

		
		buildTitle();
		initView();
		setItem();

		return view;
	}

	private RequestListener myRListener = new RequestListener() {

		@Override
		public void onWeiboException(WeiboException arg0) {

		}

		@Override
		public void onComplete(String arg0) {
			if (!TextUtils.isEmpty(arg0)) {
				User user = User.parse(arg0);
				if (user != null) {
					 setUserInfo(user);
					Log.i("Test", "" + user.screen_name);
				}
			}

		}
	};

	private void initView() {
		ll_self_info = (LinearLayout) view.findViewById(R.id.ll_self_info);

		iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
		tv_subhead = (TextView) view.findViewById(R.id.tv_subhead);
		tv_caption = (TextView) view.findViewById(R.id.tv_caption);
		//微博数，关注数，粉丝数
		tv_status_count = (TextView) view.findViewById(R.id.tv_status_count);
		tv_follow_count = (TextView) view.findViewById(R.id.tv_follow_count);
		tv_fans_count = (TextView) view.findViewById(R.id.tv_fans_count);

		// 列表
		lv_self_items = (WrapHeightListView) view
				.findViewById(R.id.lv_self_items);
		selfItems = new ArrayList<SelfItem>();
		adapter = new SelfAdapter(getActivity(), selfItems);
		lv_self_items.setAdapter(adapter);

	}

	// 设置标题栏
	public void buildTitle() {
		new TitleBuilder(view)
				.setTitleText("我")
				.setLeftText("添加好友")
				.setLeftOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ToastUtils.showToast(getActivity(), "添加好友",
								Toast.LENGTH_SHORT);
					}
				}).setRightText("设置")
				.setRightOnClicklistener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ToastUtils
								.showToast(getActivity(), "设置", Toast.LENGTH_SHORT);
					}
				});
	}

	// 设置用户信息
	private void setUserInfo(User user) {
		BitmapUtils bitmapUtils=new BitmapUtils(getActivity());
		bitmapUtils.display(iv_avatar, user.profile_url);
		tv_subhead.setText(user.screen_name+"");
		tv_caption.setText("简介:"+user.description);
		tv_status_count.setText(user.statuses_count+"");
		tv_follow_count.setText(user.followers_count+"");
		tv_fans_count.setText(user.friends_count+"");
	}

	// 设置列表项
	private void setItem() {
		selfItems.add(new SelfItem(false, R.drawable.icon_item_self_1, "新的朋友",
				""));
		selfItems.add(new SelfItem(false, R.drawable.icon_item_self_2, "微博等级",
				"Lv13"));
		selfItems.add(new SelfItem(false, R.drawable.icon_item_self_3, "编辑资料",
				""));
		selfItems.add(new SelfItem(true, R.drawable.icon_item_self_4, "我的相册",
				"(18)"));
		selfItems.add(new SelfItem(false, R.drawable.icon_item_self_5, "我的点评",
				""));
		selfItems.add(new SelfItem(false, R.drawable.icon_item_self_4, "我的赞",
				"(32)"));
		selfItems.add(new SelfItem(true, R.drawable.icon_item_self_3, "微博支付",
				""));
		selfItems.add(new SelfItem(false, R.drawable.icon_item_self_2, "微博运动",
				"步数、卡路里、跑步轨迹"));
		selfItems.add(new SelfItem(true, R.drawable.icon_item_self_1, "更多",
				"收藏、名片"));
		adapter.notifyDataSetChanged();

	}

}
