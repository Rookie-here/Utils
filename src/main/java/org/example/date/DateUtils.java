package org.example.date;

import org.example.exception.ProcessException;
import org.example.enums.ErrorCodeEnum;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private final static String date_pattern = "yyyy-MM-dd";

    /**
     *  yyyy-MM-dd -> timestamp
     */
    public static String dateToTimeStamp(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date_pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try{

            Date d = simpleDateFormat.parse(date);
            long time = d.getTime()/1000;
            return String.valueOf(time);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ProcessException(ErrorCodeEnum.DATE_FORMAT_ERROR);
        }
    }

    /**
     *  timestamp -> yyyy-MM-dd
     */
    public static String timeStampToDate(String time){
        String tsStr = "";
        Integer int_time = Integer.valueOf(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(date_pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            tsStr = dateFormat.format(new Timestamp((long)int_time*1000));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException(ErrorCodeEnum.DATE_FORMAT_ERROR);
        }
        return tsStr;
    }

    /**
     * "xxx" -> xxx
     */
    public static String replaceQuotes(String originString) {
        if (originString == null || "".equals(originString)) {
            throw new ProcessException(ErrorCodeEnum.PARAM_ERROR);
        }
        if (originString.startsWith("\"") || originString.startsWith("'")) {
            return originString.substring(1, originString.length() - 1);
        }
        return originString;
    }

    /**
     *  yyyy-MM-dd -> yyyyMMdd
     */
    public static String transferDate(String originString) {
        return originString.replace("-","");
    }

    /**
      * yyyyMMdd -> yyyy-MM-dd
     */
    public static String formatDateStr(String originString){
        if (originString == null || "".equals(originString))
            throw new ProcessException(ErrorCodeEnum.PARAM_ERROR);

        String regex = "(\\d{4})(\\d{2})(\\d{2})";
        return originString.replaceAll(regex,"$1-$2-$3");
    }

    public static void main(String[] args){
        System.out.println(formatDateStr("20231101"));
    }
}
