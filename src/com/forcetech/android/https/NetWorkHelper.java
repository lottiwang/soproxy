package com.forcetech.android.https;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class NetWorkHelper {
    private static String LOG_TAG = "NetWorkHelper";

    public static boolean isEthernetDataEnable(Context paramContext)
            throws Exception {
        return ((ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(9)
                .isConnectedOrConnecting();
    }

    public static boolean isNetworkAvailable(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean bool = false;
        if (localConnectivityManager == null) {
            DebugLog.w(LOG_TAG, "couldn't get connectivity manager");
            return bool;
        }
        while (true) {
            DebugLog.d(LOG_TAG, "network is not available");
            NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager
                    .getAllNetworkInfo();
            if (arrayOfNetworkInfo != null) {
                for (int i = 0; ; i++) {
                    if (i >= arrayOfNetworkInfo.length)
                        return bool;
                    if ((arrayOfNetworkInfo[i].isAvailable())
                            && (arrayOfNetworkInfo[i].isConnected())) {
                        DebugLog.d(LOG_TAG, "network is available");
                        bool = true;
                        return bool;
                    }
                }
            } else {
                DebugLog.d(LOG_TAG, "arrayOfNetworkInfo is null");
            }
        }
    }

    public static boolean isWifiDataEnable(Context paramContext)
            throws Exception {
        return ((ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1)
                .isConnectedOrConnecting();
    }
}
