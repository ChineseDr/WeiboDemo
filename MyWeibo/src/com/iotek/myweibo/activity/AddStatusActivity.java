package com.iotek.myweibo.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iotek.myweibo.R;
import com.iotek.myweibo.constants.AccessTokenKeeper;
import com.iotek.myweibo.constants.Constants;
import com.iotek.myweibo.utils.ImageUtils;
import com.iotek.myweibo.utils.TitleBuilder;
import com.iotek.myweibo.utils.ToastUtils;
import com.iotek.myweibo.widget.PictureGridView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

public class AddStatusActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	/** 用于获取微博信息流等操作的API */
    private StatusesAPI mStatusesAPI;
    
  //  private static final String TAG = AddStatusActivity.class.getName();
	
	//输入框
	private EditText et_add_status;
	//添加九宫格图片
	private PictureGridView gv_add_status;
	// 底部菜单
	private ImageView iv_pics;
	private ImageView iv_at;
	private ImageView iv_tk;
	private ImageView iv_em;
	private ImageView iv_more;
	
	private ArrayList<Uri> imgUris = new ArrayList<Uri>();

	private Oauth2AccessToken mAccessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_status);
		// 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
		mStatusesAPI=new StatusesAPI(this, Constants.APP_KEY, mAccessToken);
		initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_tx_left:
			finish();
			break;
		case R.id.titlebar_tx_right:
			ToastUtils.showToast(this, "发送准备", Toast.LENGTH_SHORT);
			sendStatus();
			finish();
			ToastUtils.showToast(this, "发送成功", Toast.LENGTH_SHORT);
			break;
		case R.id.iv_add_pic:
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 控件初始化
	 */
	private void initView() {
		new TitleBuilder(this).setTitleText("发微博").setLeftText("取消")
				.setLeftOnClickListener(this).setRightText("发送")
				.setRightOnClicklistener(this).build();
		//输入框
		et_add_status=(EditText) findViewById(R.id.et_add_status);
		//九宫格图片
		gv_add_status=(PictureGridView) findViewById(R.id.gv_add_status);
		//底部按钮
		iv_pics=(ImageView) findViewById(R.id.iv_add_pic);
		iv_at=(ImageView) findViewById(R.id.iv_add_at);
		iv_tk=(ImageView) findViewById(R.id.iv_add_tk);
		iv_em=(ImageView) findViewById(R.id.iv_add_em);
		iv_more=(ImageView) findViewById(R.id.iv_add_more);
		
	}

	/*
	 * 发微博
	 */
	private void sendStatus() {
		// 发送文字
		String statusContent = et_add_status.getText().toString();//输入内容
		if (TextUtils.isEmpty(statusContent)) {
			ToastUtils.showToast(this, "微博内容不能为空", Toast.LENGTH_SHORT);
			return;
		}
		Log.i("Test", "调用接口");
		//mStatusesAPI.update(statusContent,  null, null, mListener);
		Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        mStatusesAPI.upload(statusContent, bitmap, null, null, mListener);
	}
	
	private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
               // LogUtil.i(TAG, response);
                if (response.startsWith("{\"statuses\"")) {
                    // 调用 StatusList#parse 解析字符串成微博列表对象
                    StatusList statuses = StatusList.parse(response);
                    if (statuses != null && statuses.total_number > 0) {
                        Toast.makeText(AddStatusActivity.this, 
                                "获取微博信息流成功, 条数: " + statuses.statusList.size(), 
                                Toast.LENGTH_LONG).show();
                    }
                } else if (response.startsWith("{\"created_at\"")) {
                    // 调用 Status#parse 解析字符串成微博对象
                    Status status = Status.parse(response);
                    Toast.makeText(AddStatusActivity.this, 
                            "发送一送微博成功, id = " + status.id, 
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddStatusActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
           // LogUtil.e(TAG, e.getMessage());
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(AddStatusActivity.this, info.toString(), Toast.LENGTH_LONG).show();
        }
    };
}
