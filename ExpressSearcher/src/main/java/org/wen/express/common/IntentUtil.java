package org.wen.express.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.wen.express.R;


/***
 * Intent工具类，包含activity跳转，service启动，broadcast发送等
 * 
 * @author wenjiahui
 */
public class IntentUtil {

	public static void startActivity(Context context, Class clazz, Bundle data) {
		Intent intent = new Intent(context, clazz);
        intent.putExtras(data);
		context.startActivity(intent);
		try {
			Activity activity = (Activity)context;
			activity.overridePendingTransition(R.anim.base_slide_enter,R.anim.base_alpha_exit);
		} catch (Exception e) {}
	}

	public static void startActivity(Context context, String action) {
		Intent intent = new Intent(action);
		context.startActivity(intent);
		try {
			Activity activity = (Activity)context;
			activity.overridePendingTransition(R.anim.base_slide_enter,R.anim.base_alpha_exit);
		} catch (Exception e) {}
	}


	public static void startActivity(Context context, String action, Bundle bundle) {
		Intent intent = new Intent(action);
		intent.putExtras(bundle);
		context.startActivity(intent);
		try {
			Activity activity = (Activity)context;
			activity.overridePendingTransition(R.anim.base_slide_enter,R.anim.base_alpha_exit);
		} catch (Exception e) {}
	}

	public static void startService(Context context, Class clazz) {
		Intent intent = new Intent(context, clazz);
		context.startService(intent);
	}

	public static void startService(Context context, String action) {
		Intent intent = new Intent(action);
		context.startService(intent);
	}

	public static void startService(Context context, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		context.startService(intent);
	}

	public static void startService(Context context, String action, Bundle bundle) {
		Intent intent = new Intent(action);
		intent.putExtras(bundle);
		context.startService(intent);
	}

	public static void sendBroadcast(Context context, String action) {
		Intent intent = new Intent(action);
		context.sendBroadcast(intent);
	}

	public static void sendBroadcast(Context context, String action, Bundle bundle) {
		Intent intent = new Intent(action);
		intent.putExtras(bundle);
		context.sendBroadcast(intent);
	}

}
