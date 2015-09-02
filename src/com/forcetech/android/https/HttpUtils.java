package com.forcetech.android.https;

import android.content.Context;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

public class HttpUtils {
	private static final String TAG = "HttpUtils";

	public static byte[] getBinary(String s, Header aheader[],
			NameValuePair anamevaluepair[]) {
		byte abyte0[] = null;
		HttpResult httpresult = HttpClientHelper.get(s, aheader,
				anamevaluepair, null, 0);
		if (httpresult != null
				&& httpresult.getStatusCode() == HttpResult.HTTP_STATUS_CODE_OK)
			abyte0 = httpresult.getResponse();
		return abyte0;
	}

	public static String getContent(String s, Header aheader[],
			NameValuePair anamevaluepair[]) {
		String s1 = null;
		HttpResult httpresult = HttpClientHelper
				.get(s, aheader, anamevaluepair);
		if (httpresult != null && httpresult.getStatusCode() == 200)
			s1 = httpresult.getHtml();
		return s1;
	}

	public static boolean isEthernetDataEnable(Context context) {
		boolean flag = false;
		try {
			flag = NetWorkHelper.isEthernetDataEnable(context);
		} catch (Exception e) {
			flag = false;
			Log.e(TAG, e.toString());
		}
		return flag;
	}

	public static boolean isNetworkAvailable(Context context) {
		return NetWorkHelper.isNetworkAvailable(context);
	}

	public static boolean isWifiDataEnable(Context context) {
		boolean flag = false;
		try {
			flag = NetWorkHelper.isWifiDataEnable(context);
		} catch (Exception e) {
			flag = false;
			Log.e("httpUtils.isWifiDataEnable()", e.toString());
		}
		return flag;
	}
}
