package com.iotek.myweibo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageUtils {
	public static final int REQUEST_CODE_FROM_ALBUM = 5002;

	// 获取手机中的图片
	public static void pickImageFromAlbum(final Activity activity) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
	}

	
	//获取文件路径
	public static String getImageAbsolutePath(Context context, Uri uri) {
		Cursor cursor = MediaStore.Images.Media.query(
				context.getContentResolver(), uri,
				new String[] { MediaStore.Images.Media.DATA });
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}
		return null;
	}

	public static void showImagePickDialog(final Activity activity) {

		String[] items = new String[] { "相册" };
		new AlertDialog.Builder(activity).setItems(items,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							pickImageFromAlbum(activity);
							break;
						default:
							break;
						}
					}
				}).show();
	}

}
