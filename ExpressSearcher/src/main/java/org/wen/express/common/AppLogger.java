package org.wen.express.common;


import org.wen.express.BuildConfig;

public class AppLogger {
	protected static final String TAG = "ExpressSearcher";

	private AppLogger() {
	}

	public static void v(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(TAG, buildMessage(msg));
	}

	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(tag, msg);
	}

	public static void v(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(TAG, buildMessage(msg), thr);
	}

	public static void d(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(TAG, buildMessage(msg));
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(tag, msg);
	}

	public static void d(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(TAG, buildMessage(msg), thr);
	}

	public static void i(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(TAG, buildMessage(msg));
	}

	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(tag, msg);
	}

	public static void i(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(TAG, buildMessage(msg), thr);
	}

	public static void e(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(TAG, buildMessage(msg));
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(tag, msg);
	}

	public static void w(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(TAG, buildMessage(msg));
	}

	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, msg);
	}

	public static void w(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(TAG, buildMessage(msg), thr);
	}

	public static void w(Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(TAG, buildMessage(""), thr);
	}

	public static void e(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(TAG, buildMessage(msg), thr);
	}

	protected static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace()
				.getStackTrace()[2];

		return new StringBuilder().append(caller.getClassName()).append(".")
				.append(caller.getMethodName()).append("(): ").append(msg)
				.toString();
	}
}
