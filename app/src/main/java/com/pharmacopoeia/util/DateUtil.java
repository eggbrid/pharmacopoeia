package com.pharmacopoeia.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xus on 2016/7/19.
 */
public class DateUtil {
    public static DateUtil dateUtil;

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate, String format) {
        try {


            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return 0;
        }
    }

    public long stringToLongDian(String strTime, String formatType) {
        try {
            if (strTime != null && strTime.indexOf(".") == -1) {
                formatType = "yyyy-MM";
            }
            Date date = stringToDate(strTime, formatType); // String类型转成date类型
            if (date == null) {
                return 0;
            } else {
                long currentTime = dateToLong(date); // date类型转成long类型
                return currentTime;
            }

        } catch (Exception e) {
            return 0;
        }
    }

    public long stringToLong(String strTime, String formatType) {
        try {
            if (strTime != null && strTime.indexOf(".") == -1) {
                String[] strings = formatType.split("-");
                if (strings.length == 2) {
                    formatType = "yyyy-MM";
                } else {
                    formatType = "yyyy-MM-dd";
                }
            }
            Date date = stringToDate(strTime, formatType); // String类型转成date类型
            if (date == null) {
                return 0;
            } else {
                long currentTime = dateToLong(date); // date类型转成long类型
                return currentTime;
            }

        } catch (Exception e) {
            return 0;
        }
    }

    public Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }


    public long dateToLong(Date date) {
        return date.getTime();
    }

    public synchronized static DateUtil getInstance() {
        if (dateUtil == null) {
            dateUtil = new DateUtil();
        }
        return dateUtil;
    }

    private SimpleDateFormat simHM = new SimpleDateFormat("HH:mm");

    public String simHM(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simHM.format(dates);
    }

    private SimpleDateFormat simM_D = new SimpleDateFormat("MM-dd");

    public String simM_D(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simM_D.format(dates);
    }

    private SimpleDateFormat simDD_SMM_SYY = new SimpleDateFormat("dd/MM/yyyy");

    public String simDD_SMM_SYY(Date date) {
        return simDD_SMM_SYY.format(date);
    }

    private SimpleDateFormat simSMM_SYY = new SimpleDateFormat("MM/yyyy");

    public String simSMM_SYY(Date date) {
        return simSMM_SYY.format(date);
    }

    private SimpleDateFormat simdd = new SimpleDateFormat("dd");

    public String simdd(Date date) {
        return simdd.format(date);
    }

    public Date parseDD_SMM_SYY(String date) {
        try {
            return simDD_SMM_SYY.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    private SimpleDateFormat simY = new SimpleDateFormat("yyyy");
    private SimpleDateFormat simM = new SimpleDateFormat("MM");

    public String simY(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simY.format(dates);
    }

    public String simM(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simM.format(dates);
    }

    private SimpleDateFormat simYM = new SimpleDateFormat("yyyyMM");
    private SimpleDateFormat simY_M = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat simCHY = new SimpleDateFormat("yyyy年");
    private SimpleDateFormat simCHM_D = new SimpleDateFormat("M月d日");
    private SimpleDateFormat simMY = new SimpleDateFormat("MM月");

    public String simMY(Date date) {
        if (date == null) {
            date = new Date();
        }
        return simMY.format(date);
    }

    public String simY_M_DtoCHM_D(String date) {
        String dates = "";
        try {
            dates = simCHM_D.format(simY_M_D.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public String simY_M_DtoCHY(String date) {
        String dates = "";
        try {
            dates = simCHY.format(simY_M_D.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public String simYM(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simYM.format(dates);
    }

    public String simYMtoY_M(String date) {
        String dates = "";
        try {
            dates = simY_M.format(simYM.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    private SimpleDateFormat simMD_HMS = new SimpleDateFormat("MM月dd日");

    public String simMD_HMS(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simMD_HMS.format(dates);
    }

    private SimpleDateFormat simMD_HM = new SimpleDateFormat("MM月dd日 HH:mm");

    public String simMD_HM(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simMD_HM.format(dates);
    }

    private SimpleDateFormat simY_M_D_H_M_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Date parseY_M_D_H_M_S(String date) {
        try {
            return simY_M_D_H_M_S.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public String simY_M_D_H_M_S(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simY_M_D_H_M_S.format(dates);
    }

    private SimpleDateFormat simY_M_D_H_M = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public String simY_M_D_H_M(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simY_M_D_H_M.format(dates);
    }

    private SimpleDateFormat simY_M_D = new SimpleDateFormat("yyyy-MM-dd");

    public String simY_M_D(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simY_M_D.format(dates);
    }

    public String simY_M_D(Date date) {
        return simY_M_D.format(date);
    }

    private SimpleDateFormat simMSlashD_HMS = new SimpleDateFormat("MM/dd HH:mm");

    public String simMSlashD_HMS(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simMSlashD_HMS.format(dates);
    }

    private SimpleDateFormat simMPoiD_HMS = new SimpleDateFormat("yyyy.MM.dd");

    public String simMPoiD_HMS(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simMPoiD_HMS.format(dates);
    }

    private SimpleDateFormat simYPoiM = new SimpleDateFormat("yyyy.MM");

    public String simYPoiM(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simYPoiM.format(dates);
    }

    private SimpleDateFormat simYSlashMSlashD = new SimpleDateFormat("yyyy/MM/dd");

    public String simYSlashMSlashD(String date) {
        if (TextUtils.isEmpty(date))
            return "";
        Date dates = new Date(Long.parseLong(date));
        return simYSlashMSlashD.format(dates);
    }

    /**
     * 把.轉成-
     *
     * @return
     */
    public String get_TimeForPoi(String string) {
        if (string == null) {
            return "";
        }
        try {
            string = string.replaceAll("\\.", "-");
            return string;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public String friendly_time(String sdate) {
        if (TextUtils.isEmpty(sdate))
            return "";
        Date time = new Date(Long.parseLong(sdate));
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = simMPoiD_HMS.format(cal.getTime());
        String paramDate = simMPoiD_HMS.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = simMPoiD_HMS.format(time);
        }
        return ftime;
    }


    public String friendly_timeYZ(Date date) {
        return friendly_timeYZ(date + "");
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public String friendly_timeYZ(String sdate) {
        if (TextUtils.isEmpty(sdate))
            return "";
        Date time = new Date(Long.parseLong(sdate));
        if (time == null) {
            return "Unknown";
        }
        Calendar cal = Calendar.getInstance();
        //判断是否是同一天
        String curDate = simMPoiD_HMS.format(cal.getTime());
        String paramDate = simMPoiD_HMS.format(time);
        if (curDate.equals(paramDate)) {
            return simHM(sdate);
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            return simHM(sdate);
        } else if (days > 356) {
            return simM_D(sdate);
        } else {
            return simY_M_D(sdate);

        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public String friendly_timeJob(String sdate) {
        if (TextUtils.isEmpty(sdate))
            return "";
        Date time = new Date(Long.parseLong(sdate));
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = simMPoiD_HMS.format(cal.getTime());
        String paramDate = simMPoiD_HMS.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        } else {
            return simY_M_D(sdate);
        }
    }
}
