package com.iotek.myweibo.widget;

/**
 * 图片布局
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class PictureGridView extends GridView {

	public PictureGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PictureGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PictureGridView(Context context) {
		super(context);
	}

	/*
	 * 图片尺寸 (non-Javadoc)
	 * makeMeasureSpec方法参数（size，mode）
	 * @see android.widget.GridView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightSpec);
	}

}
