package top.kirisamemarisa.onebotspring.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: 通用工具类
 * @Date: 2024/2/21
 */
public class Utils {

    /**
     * 时间转字符串
     *
     * @param date .
     * @return .
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 倒计时
     *
     * @param date 结束时间
     * @return .
     */
    public static String getRemainingTime(Date date) {
        if (date == null) return null;
        long remainingTime = date.getTime() - new Date().getTime();
        if (remainingTime < 0) return null;

        long hours = (remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (remainingTime % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (remainingTime % (1000 * 60)) / 1000;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            if (hours < 10) sb.append("0");
            sb.append(hours).append("时");
        }
        if (minutes > 0) {
            if (minutes < 10) sb.append("0");
            sb.append(minutes).append("分");
        }
        if (seconds < 10) sb.append("0");
        sb.append(seconds).append("秒");
        return sb.toString();

    }

    /**
     * 毫秒转秒
     *
     * @param millis 毫秒值
     * @return 秒
     */
    public static long millisToSeconds(long millis) {
        return millis / 1000;
    }



    public static void main(String[] args) {
        long l = (1000 * 60 * 14);
        String remainingTime = getRemainingTime(new Date(System.currentTimeMillis() + l));
        System.out.println(remainingTime);
    }
}
