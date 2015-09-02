package com.forcetech.android;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.forcetech.android.https.Base64Cutego;
import com.forcetech.android.https.HttpUtils;
import com.forcetech.android.https.MD5Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ForceTV
 *
 * @author yuqile@gmail.com
 */
public class ForceTV {
    private Context context = null;
    private boolean p2pIsStart = false;
    private final static int tcpport = 9906;
    private final static int pool20M = 20971520;
    private final static String local = "0.0.0.0:";
    private final static String cmdNetstat = "netstat";
    private final static String TAG = "ForceTV";
//    private P2PServer mP2PServer = null;

    static {
        try {
            System.loadLibrary("forcetv");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public ForceTV(Context context) {
        this.context = context;
//        mP2PServer = P2PServer.getInstance();
    }

    public void initForceClient() {
        System.out.println("startClient Force P2P.........");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec(cmdNetstat).getInputStream()), 1024);
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) {
                    if (!this.p2pIsStart)
                        Log.d(TAG, String.valueOf(startClient(tcpport, pool20M)));
                    return;
                }
                if (str.contains(new StringBuffer(local).append(tcpport).toString())) {
                    this.p2pIsStart = true;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int startClient(int protNo, int pool_size) {
//        if (mP2PServer != null)
//            mP2PServer.startServer();
        return start(protNo, pool_size);
    }

    public int stopClient() {
//        if (mP2PServer != null) {
//            mP2PServer.stopServer();
//            mP2PServer = null;
//        }
        return stop();
    }

    private native int start(int protNo, int pool_size);

    private native int stop();


    /**
     * @param id     鏄寚棰戦亾ID,瑙傜湅涓嶅悓棰戦亾鏃�搴旀寚鍚戜笉鍚岀殑棰戦亾.
     *               姝ゅ弬鏁板彲涓虹┖,灏嗕細鎿嶄綔榛樿鐨勭涓�釜鎺у埗棰戦亾
     * @param server 鏈嶅姟鍣ㄥ湴鍧�     * @param cmd    鍛戒护
     * @param link
     * @return true, false
     */
    private boolean cmd(String id, String server, String cmd,
                        String link) {
        boolean result = false;
        StringBuffer stringBuffer = new StringBuffer("http://127.0.0.1:")
                .append(tcpport).append("/cmd.xml?cmd=");
        stringBuffer.append(cmd);
        stringBuffer.append("&id=").append(id);
        stringBuffer.append("&server=").append(server);
        if (link != null && link.length() > 0) {
            stringBuffer.append("&link=").append(link);
        }
        Log.d(TAG, stringBuffer.toString());
        try {
            URL url = new URL(stringBuffer.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection != null) {
                InputStreamReader inputStreamReader =
                        new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String str1 = reader.readLine();
                String str2 = new String();
                while (str1 != null) {
                    str2 += str1;
                    str1 = reader.readLine();
                }
                Log.d("P2P Utils", "P2P response: " + str2);
                reader.close();
                connection.disconnect();
                result = true;
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }

        return result;
    }


    private static final String CMD_SWITCH_CHAN = "switch_chan";

    private static final String STR_AND_TOKEN = "&";
    private static final String STR_PARAM_LINK_TOKEN = "\\?l=";
    private static final String STR_POINT_TOKEN = ".";
    private static final String CMD_STOPALL_CHAN = "stop_all_chan";

    public void stopChan() {
        StringBuffer stringBuffer = new StringBuffer("http://127.0.0.1:")
                .append(tcpport).append("/cmd.xml?cmd=");
        stringBuffer.append(CMD_STOPALL_CHAN);
        Log.d(TAG, stringBuffer.toString());
        String repose = HttpUtils.getContent(stringBuffer.toString(), null, null);
        if (repose != null && repose.contains("success")) {
        }
//        mP2PServer.getHandler().sendEmptyMessage(P2PServer.MSG_CMD_STOP_STATECHECK);
    }

    /**
     * p2p://live.qx169.com:2018/51b814d2000cf60a4d021462440b7ee8.ts
     *
     * @param p2purl
     * @return
     */
    public String geturl(String p2purl) {
        String[] strBuffer = p2purl.split("/");
        String url = null;

        if (strBuffer.length >= 4) {
            String fccs = strBuffer[2];
            String id = strBuffer[3].split("\\.")[0];
            url = "http://127.0.0.1:" + tcpport + "/" + id + "." + "ts";
            String link = null;
            try {
                link = strBuffer[3].split(STR_PARAM_LINK_TOKEN)[1];
            } catch (Exception e) {
                link = null;
            }
            if (!cmd(id, fccs, CMD_SWITCH_CHAN, link))
                url = null;
        }
//        mP2PServer.getHandler().sendEmptyMessage(P2PServer.MSG_CMD_START_STATECHECK);
        return url;
    }


    public static String get_livekey()
    {
        return MD5Util.getMD5String("time-" + String.valueOf(System.currentTimeMillis()).substring(0, 8) + "/key-52itvlive").substring(0, 16);
    }

    private static final String User_Ver =
            "HdpLive (" + Build.MODEL + ")";
    private static final String User_Agent
            = "Mozilla/5.0 (Linux; U; Android 4.0; en-us; Nexus One Build/ERD62; iPad; CPU iPad OS 7_0_0 like Mac OS X) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17";

    public String cmd2(String url) {
        String str2 = new String();
        StringBuffer stringBuffer = new StringBuffer("http://127.0.0.1:6990/play?enc=base64&url=")
                .append(new String(Base64Cutego.encode(url.getBytes())));
        Log.d(TAG, stringBuffer.toString());
        try {
            URL requrl = new URL(stringBuffer.toString());
//            Header[] arrayOfHeader = new Header[4];
//            arrayOfHeader[0] = new BasicHeader("User-Agent", User_Agent);
//            arrayOfHeader[1] = new BasicHeader("User-Key", get_livekey());
//            arrayOfHeader[2] = new BasicHeader("User-Ver", User_Ver);
//            arrayOfHeader[3] = new BasicHeader("Accept", "*/*");
            HttpURLConnection connection = (HttpURLConnection) requrl.openConnection();
            if (connection != null) {
                connection.setRequestProperty("User-Agent", User_Agent);
                connection.setRequestProperty("User-Key", get_livekey());
                connection.setRequestProperty("User-Ver", User_Ver);
                connection.setRequestProperty("Accept", "*/*");
                InputStreamReader inputStreamReader =
                        new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String str1 = reader.readLine();

                while (str1 != null) {
                    str2 += str1;
                    str1 = reader.readLine();
                }
                Log.d("CMD2", "P2P response: " + str2);
                reader.close();
                connection.disconnect();
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }

        return str2;
    }

}
