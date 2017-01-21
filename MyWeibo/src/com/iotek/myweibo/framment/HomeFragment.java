package com.iotek.myweibo.framment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.iotek.myweibo.R;
import com.iotek.myweibo.adapter.StatusAdapter;
import com.iotek.myweibo.constants.AccessTokenKeeper;
import com.iotek.myweibo.constants.Constants;
import com.iotek.myweibo.utils.TitleBuilder;
import com.iotek.myweibo.utils.ToastUtils;
import com.iotek.myweibo.widget.RefreshableView;
import com.iotek.myweibo.widget.RefreshableView.PullToRefreshListener;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

public class HomeFragment extends Fragment {
	private StatusList statuses;
	private Status status;
	private View view;
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;

	RefreshableView refreshableView;
	private ListView lv_home;
	private StatusAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fg_home, null);
		initView();
		return view;
	}

	private void initView() {
		new TitleBuilder(view).setTitleText("首页")
				.setLeftImage(R.drawable.ic_launcher)
				.setLeftOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ToastUtils.showToast(getActivity(), "右",
								Toast.LENGTH_SHORT);
					}
				});
	}

	//
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// 获取当前已保存过的 Token
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		// 对statusAPI实例化
		mStatusesAPI = new StatusesAPI(getActivity(), Constants.APP_KEY,
				mAccessToken);
		lv_home = (ListView) view.findViewById(R.id.lv_home_status);
		refreshableView = (RefreshableView) view
				.findViewById(R.id.refreshable_view);
		/*
		 * 获取当前登录用户及其所关注用户的最新微博。
		 * 
		 * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
		 * 
		 * @param max_id 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
		 * 
		 * @param count 单页返回的记录条数，默认为50。
		 * 
		 * @param page 返回结果的页码，默认为1。
		 * 
		 * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false。
		 * 
		 * @param featureType 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。 <li>{@link
		 * #FEATURE_ALL} <li>{@link #FEATURE_ORIGINAL} <li>{@link
		 * #FEATURE_PICTURE} <li>{@link #FEATURE_VIDEO} <li>{@link
		 * #FEATURE_MUSICE}
		 * 
		 * @param trim_user
		 * 返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false。
		 * 
		 * @param listener 异步请求回调接口
		 */
		mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);

	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {

			if (!TextUtils.isEmpty(response)) {
				if (response.startsWith("{\"statuses\"")) {
					// 调用 StatusList#parse 解析字符串成微博列表对象
					StatusList statuses = StatusList.parse(response);
					if (statuses != null && statuses.total_number > 0) {
						adapter = new StatusAdapter(getActivity(), statuses);
						lv_home.setAdapter(adapter);
						refreshableView.setOnRefreshListener(
								new PullToRefreshListener() {
									@Override
									public void onRefresh() {
										try {

											Thread.sleep(2000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										refreshableView.finishRefreshing();
									}
								}, 0);
					}
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
		}
	};

}
