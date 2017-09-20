package com.pharmacopoeia.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Created by xus on 2016/11/3.
 *唯一标识类
 */
public class AndroidIDUtil {
    private static String m_szBTMAC;
    private static String m_szWLANMAC;
    private static String m_szAndroidID;
    private static String m_szDevIDShort;
    private static String szImei;


    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 蓝牙
     * @return
     */
    private static String getM_szBTMAC() {
        try {
            if (TextUtils.isEmpty(m_szBTMAC)) {
                BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
                m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(m_BluetoothAdapter==null){
                    return m_szBTMAC+"NULL";

                }
                m_szBTMAC = m_BluetoothAdapter.getAddress();
            }
        }catch (Exception e){

        }

        return m_szBTMAC;
    }

    /**
     * mac
     * @param context
     * @return
     */
    private static String getM_szWLANMAC(Context context) {
        try{
            if (TextUtils.isEmpty(m_szWLANMAC)) {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            }
        }catch (Exception e){

        }

        return m_szWLANMAC;
    }

    /**
     * android id
     * @param context
     * @return
     */
    private static String getM_szAndroidID(Context context) {
        try{
            if (TextUtils.isEmpty(m_szAndroidID)) {
                m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }catch (Exception e){

        }

        return m_szAndroidID;
    }

    /**
     * 硬件信息合一码
     * @return
     */
    private static String getM_szDevIDShort() {
        if (TextUtils.isEmpty(m_szDevIDShort)) {
            m_szDevIDShort = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        }
        return m_szDevIDShort;
    }

    /**
     * Imei码
     * @param context
     * @return
     */
    public static String getSzImei(Context context) {
        try {
            if (TextUtils.isEmpty(szImei)) {
                TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
                szImei = TelephonyMgr.getDeviceId();
            }
        }catch (Exception e){

        }

        return szImei;
    }

    /**
     * 五码合一标识
     * @param context
     * @return
     */
    public static String getID(Context context) {
        String m_szLongID = getM_szWLANMAC(context)
                + getM_szAndroidID(context) + getM_szDevIDShort() + getSzImei(context);
// compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
// get md5 bytes
        byte p_md5Data[] = m.digest();
// create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
// if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
// add number to string
            m_szUniqueID += Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }
    //根据IP获取本地Mac
    public static String getLocalMacAddressFromIp(Context context) {
        String mac_s= "";
        try {
            byte[] mac;
            NetworkInterface ne= NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            mac_s = byte2hex(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mac_s;
    }
    //获取本地IP
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }

        return null;
    }
    public static  String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }
}
