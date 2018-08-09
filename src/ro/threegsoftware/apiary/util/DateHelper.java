/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author andrei
 */
public class DateHelper {
    
public final static Locale ROMANIAN = new Locale("ro", "ROU");

    private final static String SIMPLE_FORMAT_PATTERN = "dd-MM-yyyy";

    private final static String SIMPLE_TIME_PATTERN = "H:m";

    public DateHelper() {
    }

    public static Date FormatDate(String dateString) {
        DateFormat dt = new SimpleDateFormat("dd-MM-yy");
        Date date = null;

        try {
            date = new Date((dt.parse(dateString).getTime()));
        }
        catch (ParseException ex) {
            DateFormat dtYYYY = new SimpleDateFormat("dd/MM/yy");
            try {
                date = new Date((dtYYYY.parse(dateString).getTime()));
            }
            catch (ParseException ex1) {
                DateFormat dMMMyyyy = new SimpleDateFormat("d MMM yyyy", ROMANIAN);
                try {
                    date = new Date((dMMMyyyy.parse(dateString).getTime()));
                } catch (ParseException ex2) {
                    System.out.println(ex2);
                }
            }
        }

        return date;
    }

    public static java.util.Date getCurrentDate() {
        Calendar rightNow = Calendar.getInstance();
        java.util.Date date = new java.util.Date(rightNow.getTimeInMillis());
        
        return date;
    }

    public static Integer getDay(java.util.Date date) {
        GregorianCalendar grCalendar = new GregorianCalendar();
        grCalendar.setTime(date);
        
        return grCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getMonth(java.util.Date date) {
        GregorianCalendar grCalendar = new GregorianCalendar();
        grCalendar.setTime(date);
        
        return grCalendar.get(Calendar.MONTH);
    }

    public static Integer getYear(java.util.Date date) {
        GregorianCalendar grCalendar = new GregorianCalendar();
        grCalendar.setTime(date);
        
        return grCalendar.get(Calendar.YEAR);
    }

    public static String toLocaleString(java.util.Date date, Locale locale) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        
        return df.format(date);
    }

    public static String currentDateToLocaleString(Locale locale) {
       java.util.Date date = getCurrentDate();
       
       return toLocaleString(date, locale);
    }

    public static String currentDateToSimpleFormat() {
        SimpleDateFormat formater = new SimpleDateFormat(SIMPLE_FORMAT_PATTERN);
        
        return formater.format(getCurrentDate());
    }

    public static String currentTimeToSimpleFormat() {
        SimpleDateFormat formater = new SimpleDateFormat(SIMPLE_TIME_PATTERN);
        return formater.format(getCurrentDate());
    }

    public static String formatDateToSimpleFormat(java.util.Date date) {
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_FORMAT_PATTERN);
        
        return date != null ? format.format(date) : null;
    }

    public static Long getCurrentLongDate() {
        return getCurrentDate().getTime();
    }

    public static String getMonthName(java.util.Date date, Locale locale) {
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String months[] = dfs.getMonths();
        GregorianCalendar gc = new GregorianCalendar(locale);
        gc.setTime(date);

        return months[gc.get(GregorianCalendar.MONTH)];
    }

    public static int daysBetween(java.util.Date d1, java.util.Date d2){
    	return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }    
}
