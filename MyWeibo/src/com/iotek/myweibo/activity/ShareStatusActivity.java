package com.iotek.myweibo.activity;

import com.iotek.myweibo.R;
import com.iotek.myweibo.constants.AccessTokenKeeper;
import com.iotek.myweibo.constants.Constants;
import com.iotek.myweibo.utils.TitleBuilder;
import com.iotek.myweibo.utils.ToastUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ShareStatusActivity extends Activity implements
OnClickListener, OnItemClickListener{
	
	private Status status;
	// 标题栏
	private RelativeLayout rl_titlebar;
	// 输入框
	private EditText et_add_status;
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI statusAPI;

	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {

				if (response.startsWith("{\"statuses\"")) {
					// 调用 StatusList#parse 解析字符串成微博列表对象
					StatusList statuses = StatusList.parse(response);
					if (statuses != null && statuses.total_number > 0) {
						Toast.makeText(ShareStatusActivity.this,
								"获取微博信息流成功, 条数: " + statuses.statusList.size(),
								Toast.LENGTH_LONG).show();
					}
				} else if (response.startsWith("{\"created_at\"")) {
					// 调用 Status#parse 解析字符串成微博对象
					Status status = Status.parse(response);
					Toast.makeText(ShareStatusActivity.this,
							"发送一送微博成功, id = " + status.id, Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(ShareStatusActivity.this, response,
							Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {

			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(ShareStatusActivity.this, info.toString(),
					Toast.LENGTH_LONG).show();
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_status);
		init();

	};
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	private void init() {
		// 获取当前已保存过的 Token
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		// 对statusAPI实例化
		statusAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);
		
		// 标题栏
		new TitleBuilder(this).setTitleText("发微博").setLeftText("取消")
		.setLeftOnClickListener(this).setRightText("发送")
		.setRightOnClicklistener(this).build();
		et_add_status = (EditText) findViewById(R.id.et_add_status);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 回退
		case R.id.titlebar_tx_left:
			finish();
			break;
		// 发送微博
		case R.id.titlebar_tx_right:
			shareStatus();
			break;
		// 选取图片
		case R.id.iv_image:
			break;
		case R.id.iv_add_at:
			break;
		case R.id.iv_add_tk:
			break;
		case R.id.iv_add_em:
			
			break;
		case R.id.iv_add_more:
			break;
		
		}

	}

	private void shareStatus() {
		String comment = et_add_status.getText().toString();
		if (TextUtils.isEmpty(comment)) {
			showToast("微博内容不能为空");
			return;
		}
		Bundle source = getIntent().getBundleExtra("bundle");
		if (source != null) {
			source.getString("shared_id");

		}
//		 * @param id            要转发的微博ID
//	     * @param status        添加的转发文本，内容不超过140个汉字，不填则默认为“转发微博”
//	     * @param commentType   是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
		statusAPI.repost(Long.parseLong(source.getString("shared_id")), comment, 0, mListener);
	}

	protected void showToast(String msg) {
		ToastUtils.showToast(this, msg, Toast.LENGTH_LONG);
	}
	
}
