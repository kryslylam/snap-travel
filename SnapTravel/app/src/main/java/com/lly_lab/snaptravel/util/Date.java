package com.lly_lab.snaptravel.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
    private String mFormattedString;
    private static String DATE_FORMAT="yyyy-MM-dd";

    public static int formattedStringToYear(String formattedString) {
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
        
    }

    public static String formatYMDtoString(int year,int month,int day)  {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
        calendar.set(year,month,day);
        return dateFormat.format(calendar.getTime());
    }
}
