/**
 *
 */
package org.nipun.cisco.dnas.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author ravichand.a
 *
 */
public class DateUtil {

    public static final DateTime SYSTEM_MAX_DATE_TIME = new DateTime(2999, 12, 31, 0, 0, 0, 0);
    public static final DateTime SYSTEM_MIN_DATE_TIME = new DateTime(1900, 1, 1, 0, 0, 0, 0);
    public static final String DATE_FORMAT = "MM/dd/yyyy";

    public static final String DATE_FORMAT_ddMMyyyy = "dd/MM/yyyy";
    public static final String DATE_FORMAT_ddMM = "dd-MM";
    public static final String DATE_FORMAT_yyyyMMdd = "yyyy-MM-dd";
    public static final String DATE_FORMAT_TIME = "dd/MM/yyyy HH:mm:ss";
    // public static final String DATEMMYYHHMMSS = "dd/MM/yyyy HH:mm:ss";

    public static final String DATE_FORMAT_ddMMyyyy_HH_MM = "dd/MM/yyyy HH:mm";
    public static final String YY_MM_DATE_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DAY_FORMAT = "d";
    public static final String DAY_HALFDAY_FORMAT = "d EE";
    public static final String MONTH_FORMAT = "MMM";
    public static final String HALFDAY_FORMAT = "EE";
    public static final String FULLDAY_FORMAT = "EEEE";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT_HHMMSS = "HH:mm:ss";
    public static final String yyyy_MM_dd_T_HH_mm_ssZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String yyyy_MM_dd_T_HH_mm_ss_sssZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String SHORT_YEAR_DATE_FORMAT = "dd/MM/yy HH:mm:ss";
    public static final String MYSQL_DATE_FOMATE = "yyyy-MM-dd HH:mm:ss.S";
    public static final String[] DATE_TIME_FORMATS = { DATE_FORMAT, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyyMMdd", "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss",
            "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", "MM/dd/yyyy'T'HH:mm:ssZ",
            "MM/dd/yyyy'T'HH:mm:ss", "yyyy:MM:dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S", "dd-MMM-yy" };
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT);
    public static final DateTimeFormatter SHORT_DATE_TIME_FORMATTER = DateTimeFormat.forPattern(SHORT_YEAR_DATE_FORMAT);

    public static final DateTimeFormatter MYSQL_DATE_TIME_FORMATTER = DateTimeFormat.forPattern(MYSQL_DATE_FOMATE);
    public static final String SYSTEM_MAX_DATE = "12/31/2999";
    public static final String SYSTEM_MIN_DATE = "01/01/1900";
    public static final String DATE_FORMATYMD = "yyyy-MM-dd";
    public static final String DATE_FORMATYMDMILLISEC = "dd/MM/yyyy HH:mm:ss.SSS";
    public static final String DATE_FORMATYMDSEC = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMATYMDHHMMSEC = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMATYMDHHMM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMATE_MMDDYYYY_HHMMSS = "MM/dd/yyyy HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ = "yyyy-MM-dd HH:mm:ss.SSSZ";

    /**
     * Specialized date format for Minute Book Project uploads
     */
    public static final String LONG_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String LONG_DATE_FORMAT_AM_PM = "MM/dd/yyyy hh:mm:ss a";
    public static final String LONG_DATE_FORMAT_2 = "dd/MM/yyyy hh:mm a";
    public static final String LONG_TIME_FORMAT = "hh:mm:ss a";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_2 = DateTimeFormat.forPattern(LONG_DATE_FORMAT_2);

    public static final String DATE_FORMAT_AM_PM = "dd-MM-yyyy hh:mm:ss a";
    /**
     * "dd-MM-yyyy HH:mm:ss"
     *
     * @author ravichand.a
     */
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    /**
     * Constructor for DateUtil
     */
    protected DateUtil() {
        // do nothing
    }

    public static Date addHours(final Date d1, final int hours) {
        if (d1 != null) {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(d1); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, hours); // adds one hour
            return cal.getTime();
        }
        return null;
    }

    public static boolean between(final Date d1, final Date d2, final Date d3) {
        return (d1.equals(d2) || d1.equals(d3)) || (d1.after(d2) && d1.before(d3));
    }

    public static boolean between(final DateTime d1, final DateTime d2, final DateTime d3) {
        return (d1.equals(d2) || d1.equals(d3)) || (d1.isAfter(d2) && d1.isBefore(d3));
    }

    /**
     * @param day
     * @param defaultFromTime
     * @return
     */
    public static String buildDateTime(final String day, final String defaultTime) {
        StringBuilder result = new StringBuilder();
        result.append(day);
        result.append(org.apache.commons.lang3.StringUtils.SPACE);
        result.append(defaultTime);
        return result.toString();
    }

    public static int calculateDifference(final Date a, final Date b) {
        int tempDifference = 0;
        int difference = 0;
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        if (a.compareTo(b) < 0) {
            earlier.setTime(a);
            later.setTime(b);
        }
        else {
            earlier.setTime(b);
            later.setTime(a);
        }

        while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
            tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
            tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        return difference;
    }

    /**
     * This method will checks all possible formats and returns {@link Date} if matches, otherwise returns null.
     *
     * @param dataStr
     * @return
     * @author ravichand.a
     */
    public static Date checkAllPossibleFormates(final String dataStr) {
        for (int i = 0; i < DATE_TIME_FORMATS.length; i++) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_TIME_FORMATS[i]);
            try {
                //System.out.println("Matched Formate:" + DATE_TIME_FORMATS[i]);
                return dateFormatter.parse(dataStr);
            }
            catch (ParseException e) {
                //return null;
            }
        }
        return null;
    }

    @Deprecated
    public static Date checkGivenStringDateIsVaild(final String date) {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy_HH_MM);
            try {
                return dateFormatter.parse(date);
            }
            catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    @Deprecated
    public static Date checkGivenStringDateIsVaildOrNot(final String date) {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(YY_MM_DATE_HH_MM);
            try {
                return dateFormatter.parse(date);
            }
            catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static boolean checkGivenStringIsVaild(final String date, final String dateFormate) {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormate);
            try {
                Date parsedDate = dateFormatter.parse(date);
                if (parsedDate != null) {
                    return true;
                }
            }
            catch (ParseException e) {
                return false;
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static String convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ(final Date strDate) {
        String format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ).format(strDate);
        return format;
    }

    public static Date convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ(final String strDate) {
        try {
            Date format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ).parse(strDate);
            return format;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertDATE_FORMAT_yyyy_MM_dd_T_HH_mm_s(final String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ssZ);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date format = sdf.parse(strDate);
            return format;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertDATE_FORMAT_yyyy_MM_dd_T_HH_mm_sssZ(final String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ss_sssZ);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date format = sdf.parse(strDate);
            return format;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertDATE_FORMAT_yyyy_MM_dd_T_HH_mm_ssZ(final String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ssZ);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date format = sdf.parse(strDate);
            return format;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateAsStringByDDMMYYHHMM(final Date date) {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy_HH_MM);
            return dateFormatter.format(date);
        }
        return null;
    }

    public static String convertDateAsStringByDDMMYYYY(final Date date) {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
            return dateFormatter.format(date);
        }
        return null;
    }

    public static String convertDateAsStringByYYMMDDHHSS(final Date date) {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(YY_MM_DATE_HH_MM);
            return dateFormatter.format(date);
        }
        return null;
    }

    /**
     * utility method for date validations on the form
     *
     * @param msg
     * @param startDate
     * @param endDate
     * @return boolean
     */

    public static String convertDateStringFormat(final String strDate, final String srcFormat, final String destFormat) {
        if (StringUtils.isNotEmpty(strDate)) {
            DateTimeFormatter srcFormatter = DateTimeFormat.forPattern(srcFormat);
            DateTimeFormatter destFormatter = DateTimeFormat.forPattern(destFormat);
            DateTime date = null;
            try {
                date = srcFormatter.parseDateTime(strDate);
            }
            catch (Exception e) {
                return StringUtils.EMPTY;
            }
            return date.toString(destFormatter);
        }
        return StringUtils.EMPTY;
    }

    public static String convertDateStringFormatAM(final Date strDate) {
        //  Calendar cal = Calendar.getInstance();

        String format = new SimpleDateFormat("MM/dd/yyyy").format(strDate);
        StringBuilder dateAdd = new StringBuilder();
        dateAdd.append(format);
        dateAdd.append(" 12:00:01 AM");
        return dateAdd.toString();

    }

    public static String convertDateStringFormatPM(final Date strDate) {
        //  Calendar cal = Calendar.getInstance();

        String format = new SimpleDateFormat("MM/dd/yyyy").format(strDate);
        StringBuilder dateAdd = new StringBuilder();
        dateAdd.append(format);
        dateAdd.append(" 11:59:59 PM");
        return dateAdd.toString();

    }

    public static String convertDateToSqlForamtStr(final Date date) {

        if (date != null) {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(YY_MM_DATE_HH_MM);
                String parse = dateFormatter.format(date);
                return dateFormatter.format(date);
            }
            catch (Exception ex) {
                // System.out.println(ex);
                return null;
            }

        }
        return null;
    }

    public static String convertDateToStringFormat(final Date date, final String desirFormate) {

        String format = new SimpleDateFormat(desirFormate).format(date);
        return format;

    }

    public static String convertddMMyyyyStringToSqlForamtStr(final String date) {

        if (date != null) {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
                Date parse = dateFormatter.parse(date);
                SimpleDateFormat dateFormatter1 = new SimpleDateFormat(DATE_FORMAT_yyyyMMdd);
                return dateFormatter1.format(parse);
            }
            catch (Exception ex) {
                // System.out.println(ex);
                return null;
            }

        }
        return null;
    }

    public static String convertString_FORMAT_yyyy_MM_dd_T_HH_mm_ssZ(final Date strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ssZ);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String format = sdf.format(strDate);
        return format;
    }

    public static Date convertStringAsDateByDDMMYYHHMM(final String date) throws ParseException {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy_HH_MM);
            return dateFormatter.parse(date);
        }
        return null;
    }

    public static Date convertStringAsDateByDDMMYYYY(final String date) throws ParseException {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
            return dateFormatter.parse(date);
        }
        return null;
    }

    public static Date convertStringAsDateByYYMMDDHHSS(final String date) throws ParseException {
        if (date != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(YY_MM_DATE_HH_MM);
            return dateFormatter.parse(date);
        }
        return null;
    }

    public static String convertStringToDateAndSring(final String date) {
        //  Calendar cal = Calendar.getInstance();

        String dateStr = "";
        try {
            dateStr = new SimpleDateFormat(DATE_FORMAT_ddMM).format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateStr;

    }

    /**
     * utility method for date validations on the form
     *
     * @param msg
     * @param startDate
     * @param endDate
     * @return boolean
     */

    public static DateTime convertStringToDateString(final String strDate, final String srcFormat, final String destFormat) {
        if (StringUtils.isNotEmpty(strDate)) {
            DateTimeFormatter srcFormatter = DateTimeFormat.forPattern(srcFormat);
            DateTimeFormat.forPattern(destFormat);
            DateTime date = null;
            try {
                date = srcFormatter.parseDateTime(strDate);

            }
            catch (Exception e) {
                return null;
            }
            return date;
        }
        return null;
    }

    public static String convertStringToSqlForamtStr(final String date) {

        if (date != null) {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy_HH_MM);
                Date parse = dateFormatter.parse(date);
                SimpleDateFormat dateFormatter1 = new SimpleDateFormat(YY_MM_DATE_HH_MM);
                return dateFormatter1.format(parse);
            }
            catch (Exception ex) {
                // System.out.println(ex);
                return null;
            }

        }
        return null;
    }

    /**
     * convert date to GMT date
     *
     * @param date
     * @return
     * @author ravichand.a
     */
    public static Date cvtToGmt(final Date date) {
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date(date.getTime() - tz.getRawOffset());

        // if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
        if (tz.inDaylightTime(ret)) {
            Date dstDate = new Date(ret.getTime() - tz.getDSTSavings());

            // check to make sure we have not crossed back into standard time
            // this happens when we are on the cusp of DST (7pm the day before the change for PDT)
            if (tz.inDaylightTime(dstDate)) {
                ret = dstDate;
            }
        }
        return ret;
    }

    public static boolean equal(final Date d1, final Date d2) {
        return d1.equals(d2);
    }

    /**
     * NOTE: This really should have been called parse(), not format
     *
     * @param date
     *            String representing the date
     * @return DateTime
     */
    public static DateTime format(final String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return DATE_TIME_FORMATTER.parseDateTime(date);
    }

    public static Date formatDate(final String ds) {
        if (StringUtils.isBlank(ds)) {
            return null;
        }
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        try {
            return dateFormatter.parse(ds);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static String formatDateAsHHMMSSString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(TIME_FORMAT_HHMMSS);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatDateAsHHMMString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(TIME_FORMAT);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatDateAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatDateAsStringYMD(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMD);
            date = dateFormatter.format(formatdate);

        }
        return date;
    }

    public static String formatDateAsStringYYYY_MM_DD(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static Date formatDateStrToDDMMYYYY(final String ds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
        try {
            return dateFormatter.parse(ds);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /* public static String formatDateDDMMYYYHHMM(final Date formatdate) {
         String date = null;
         if (formatdate != null) {
             SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATE_MMDDYYYY_HHMMSS);
             date = dateFormatter.format(formatdate);
         }
         return date;
     }*/

    public static Date formatDateStrToYYYYMMDD(final String ds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMD);
        try {
            return dateFormatter.parse(ds);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param formatdate
     * @return Date format in (dd/MM/yyyy HH:mm:ss)
     * @author ravichand.a
     */
    public static String formatDateTimeAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_TIME);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatDateTimeAsStringWithMilliSec(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMDMILLISEC);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    /**
     *
     * @param ds
     * @return
     */

    public static Date formatDateToMMDDYYYY(final String ds) {
        String format = "MMddyyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        try {
            return dateFormatter.parse(ds);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static String formatDateToStrYMDHHMMSEC(final Date ds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMDHHMMSEC);
        return dateFormatter.format(ds);
    }

    public static String formatDayAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DAY_FORMAT);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatDayHalfDayAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DAY_HALFDAY_FORMAT);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    /**
     * @author ravichand.a
     * @param formatdate
     *            in java.util.Date
     * @return String(yyyy-MM-dd)
     */
    public static String formateDateAsStringyyyyMMdd(final Date formatdate) {

        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_yyyyMMdd);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    /**
     * @author ravichand.a
     * @param date
     *            in String (yyyy-MM-dd)
     * @return java.util.date
     */
    public static Date formateStringAsDate(final String formatdate) {

        Date date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_yyyyMMdd);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                // TODO Auto-generated catch block
                return null;
                // e.printStackTrace();
            }
        }
        return date;
    }

    public static Date formateStringAsDatedmy(final String formatdate) {

        Date date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                // TODO Auto-generated catch block
                return null;
                // e.printStackTrace();
            }
        }
        return date;
    }

    public static String formatFullDayAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(FULLDAY_FORMAT);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatHalfDayAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(HALFDAY_FORMAT);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatMonthAsString(final Date formatdate) {
        String date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(MONTH_FORMAT);
            date = dateFormatter.format(formatdate);
        }
        return date;
    }

    public static String formatMySqlDateToStr(final Date ds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(MYSQL_DATE_FOMATE);
        return dateFormatter.format(ds);
    }

    /**
     * @param date
     *            in string (yyyy-MM-dd HH:mm:ss.S)
     * @return java.util.Date
     * @author ravichand.a
     */
    public static Date formatMySqlStrToDate(final String ds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(MYSQL_DATE_FOMATE);
        try {
            return dateFormatter.parse(ds);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param ds
     * @return
     */
    public static DateTime formatMySqlStrToDateTime(final String ds) {
        if (StringUtils.isNotBlank(ds)) {
            return MYSQL_DATE_TIME_FORMATTER.parseDateTime(ds);

        }
        else {
            return null;
        }
    }

    public static DateTime formatShortDate(final String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return SHORT_DATE_TIME_FORMATTER.parseDateTime(date);
    }

    public static Date formatStringAsDate(final String formatdate) {

        Date date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_yyyyMMdd);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                // TODO Auto-generated catch block
                return null;
                // e.printStackTrace();
            }
        }
        return date;
    }

    public static Date formatStringAsDateTime(final String formatdate) {
        Date date = null;
        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_TIME);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * Converts the string(dd-MM-yyyy hh:mm:ss a) to java.util.Date
     *
     * @author ravichand.a
     * @param dateStr
     *            (dd-MM-yyyy hh:mm:ss a)
     * @return java.util.Date
     */
    public static Date formatStringAsDateTime_DD_MM_YYYY_HHMMSS_AM_PM(final String dateStr) {
        Date date = null;

        if (dateStr != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_AM_PM);
            try {
                date = dateFormatter.parse(dateStr);
            }
            catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return date;
    }

    public static Date formatStringAsDateTime_MMDDYYYY_HHMMSS(final String formatdate) {
        Date date = null;

        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATE_MMDDYYYY_HHMMSS);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date formatStringAsDateTime_MMDDYYYY_HHMMSSAM_PM(final String formatdate) {
        Date date = null;

        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(LONG_DATE_FORMAT_AM_PM);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date formatStringAsDateTimeYMDHHMM(final String formatdate) {
        Date date = null;

        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMDHHMM);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date formatStringAsDateTimeYMDHHMMss(final String formatdate) {
        Date date = null;

        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMDHHMMSEC);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date formatStringAsDD_MM_YYYY_HHMMSS(final String formatdate) {
        Date date = null;

        if (formatdate != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_TIME_FORMAT);
            try {
                date = dateFormatter.parse(formatdate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * @param date
     * @return
     */
    public static Date formatStringAsHHMMDate(final String date) {
        Date parse = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
        if (StringUtils.isNotBlank(date)) {
            try {
                parse = simpleDateFormat.parse(date);
            }
            catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return parse;
    }

    /**
     * This method converts date with any format into {@link java.util.Date}, if provided date is in the provided format other wise it
     * returns null. <p>Example_1: <code>DateUtil.formateStringToDateTime("21/02/2018 15:00:02","dd/MM/yyyy HH:mm:ss")</code> returns
     * {@link java.util.Date} <br> Example_2: <code>DateUtil.formateStringToDateTime("21/02/2018 15:00:02","MM/dd/yyyy HH:mm:ss")</code>
     * returns <code>null</code><p>
     *
     * @param strDate
     * @param dateFormat
     *            (custom date format)
     * @return
     * @author ravichand.a
     */
    public static Date formatStringToDateTime(final String strDate, final String dateFormat) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        try {
            return dateFormatter.parse(strDate);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * This method will converts the desired date(which is in String) into org.joda.time.DateTime
     *
     * @param date
     * @param dateFormat
     *            (custom date format)
     * @return org.joda.time.DateTime
     * @author ravichand.a
     */
    public static DateTime formatStrToDateTime(final String date, final String dateFormat) {
        if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(dateFormat)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
            return dateTimeFormatter.parseDateTime(date);
        }
        else {
            return null;
        }
    }

    public static Date formatStrToDateYMDHMSEC(final String ds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATYMDSEC);
        try {
            return dateFormatter.parse(ds);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author ravichand.a
     * @param fromDate
     * @param toDate
     * @return Days difference + 1
     */
    public static long getBetweenDaysCount(final Date fromDate, final Date toDate) {
        long noOfDays = 0L;
        try {
            long diff = toDate.getTime() - fromDate.getTime();
            noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return noOfDays;
    }

    public static DateTime getCurrentDate() {
        return new DateTime().withTimeAtStartOfDay();
    }

    public static DateTime getCurrentYearEnd() {
        DateTime dateTime = new DateTime();
        return new DateTime(dateTime.getYear(), 12, 31, 00, 00, 00, 00);
    }

    public static String getDateForGivenDuration(final int pDuration, final String pSourceDate) {
        String targetDate = "";
        Calendar calendar = Calendar.getInstance();

        DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date date;

        SimpleDateFormat dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
        try {
            dateFormatter.applyPattern(DATE_FORMAT);
            dateFormatter.setTimeZone(TimeZone.getDefault());

            date = dateFormatter.parse(pSourceDate);

            calendar.setTimeZone(TimeZone.getDefault()); // Get and Set required Time zone
            calendar.setTime(date);

            calendar.add(Calendar.DAY_OF_MONTH, pDuration);
            targetDate = dateFormatter.format(calendar.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return targetDate;
    }

    public static DateTime getDateFromMonth(final String monthName) {
        DateTime dateTime = new DateTime();
        try {
            Date date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            int dayOfMonth = dateTime.getMonthOfYear();
            dateTime = dateTime.minusMonths(dayOfMonth - (month + 1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static List<Date> getDateRange(final Date fromDate, final Date toDate, final int addDays) {
        List<Date> dates = new ArrayList<Date>();
        for (Date currentDate = fromDate; !currentDate.after(toDate); currentDate = DateUtils.addDays(currentDate, addDays)) {
            dates.add(currentDate);
        }
        return dates;
    }

    /**
     * Given a DateTime, retuen it as a String in the standard format
     *
     * @param dateTime
     *            the Date to format
     * @return String
     */
    public static String getDateTimeAsString(final DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.print(dateTime);
    }

    public static DateTime getDateTimeFromLongString(final String ds) {
        return parseDate(ds);
    }

    /**
     * Parse the date from the String
     *
     * @param ds
     *            the string to parse
     * @return DateTime
     * @deprecated use {@link DateUtil#format(String)}
     */
    @Deprecated
    public static DateTime getDateTimeFromString(final String ds) {
        return format(ds);
    }

    /**
     * Converts dateTime into long value
     *
     * @param java
     *            .util.Date
     * @return Long (yyMMddHHmmss)
     * @author ravichand.a
     */
    public static Long getDateTimeInLong(final Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String format = dateFormat.format(date);
        Long jobId = Long.valueOf(format);
        return jobId;
    }

    // returns a day of the date in full form
    public static String getdayofDate(final Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Calendar.getInstance(TimeZone.getDefault());
        String dayOfWeek = new SimpleDateFormat(FULLDAY_FORMAT).format(date);

        return dayOfWeek;
    }

    /**
     * @deprecated Use: DateUtil.SYSTEM_MAX_DATE_TIME
     * @return DateTime
     */
    @Deprecated
    public static DateTime getDefaultEndDate() {
        return SYSTEM_MAX_DATE_TIME;
    }

    /**
     * This method returns default start date in DateTime format.
     *
     * @deprecated Use: DateUtil.SYSTEM_MIN_DATE_TIME
     * @return DateTime
     */
    @Deprecated
    public static DateTime getDefaultStartDate() {
        return SYSTEM_MIN_DATE_TIME;
    }

    public static Calendar getNaxtDay(final Date formatDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        cal.add(Calendar.DATE, 1);
        cal.getTime();
        return cal;
    }

    public static String getNexttDate(final DateTime date) {

        DateTime NexttDate = new DateTime(date);
        NexttDate = NexttDate.plusDays(1);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        return fmt.print(NexttDate);
    }

    public static String getPastDate(final DateTime date) {

        DateTime pastDate = new DateTime(date);
        pastDate = pastDate.minusDays(1);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        return fmt.print(pastDate);
    }

    public static Calendar getPrevDay(final Date formatDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        cal.add(Calendar.DATE, -1);
        cal.getTime();
        return cal;
    }

    public static String getStringFromDateInLongDateFormat(final DateTime value) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(LONG_DATE_FORMAT);
        if (value != null) {
            return formatter.print(value);
        }
        else {
            return null;
        }
    }

    public static Long getTimeDifferenceDays(final Date d1, final Date d2) {
        try {
            return TimeUnit.MILLISECONDS.toDays(d2.getTime() - d1.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return TimeUnit.MILLISECONDS.toHours(0L);
    }

    public static String getTimeDifferenceDaysHrMin(final Date d1, final Date d2) {
        StringBuilder result = new StringBuilder();
        try {
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            result.append(diffDays).append(" Days, ").append(diffHours).append(" Hr, ").append(diffMinutes).append(" Min");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String getTimeDifferenceDaysHrMinSecFromSeconds(final Long diffSeconds) {
        StringBuilder result = new StringBuilder();
        try {
            if (diffSeconds > 0) {

                long diffMinutes = diffSeconds / (60) % 60;
                long secLapsed = diffSeconds % 60;

                long diffHours = diffSeconds / (60 * 60) % 24;
                long diffDays = diffSeconds / (24 * 60 * 60);
                result.append(diffDays).append(" Days, ").append(diffHours).append(" Hr, ").append(diffMinutes).append(" Min, ")
                        .append(secLapsed).append(" Sec");
            }
            else {
                result.append(0).append(" Days, ").append(0).append(" Hr, ").append(0).append(" Min, ").append(0).append(" Sec");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static Long getTimeDifferenceHours(final Date d1, final Date d2) {
        try {
            return TimeUnit.MILLISECONDS.toHours(d2.getTime() - d1.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return TimeUnit.MILLISECONDS.toHours(0L);
    }

    /**
     * @param timeDiffSeconds
     * @return
     */
    public static Double getTimeDifferenceHrMin(final Long secDiff) {
        Double timeDifference = new Double(0);
        try {
            Long diffMinutes = secDiff / (60) % 60;
            Long diffHours = secDiff / (60 * 60) % 24;

            timeDifference += diffHours.doubleValue();
            timeDifference += diffMinutes.doubleValue() / 100;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return timeDifference;
    }

    public static String getTimeDifferenceHrMinSec(final Date d1, final Date d2) {
        StringBuilder result = new StringBuilder();
        try {
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();
            long diffSec = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            result.append(diffHours).append(" Hr, ").append(diffMinutes).append(" Min, ").append(diffSec).append(" Sec");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String getTimeDifferenceHrMinSecFromSeconds(final Long diffSeconds) {
        StringBuilder result = new StringBuilder();
        try {
            if (diffSeconds > 0) {

                long diffMinutes = diffSeconds / (60) % 60;
                long secLapsed = diffSeconds % 60;

                long diffHours = diffSeconds / (60 * 60) % 24;
                result.append(diffHours).append(" Hr, ").append(diffMinutes).append(" Min, ").append(secLapsed).append(" Sec");
            }
            else {
                result.append(0).append(" Hr, ").append(0).append(" Min, ").append(0).append(" Sec");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * @param d1
     * @param d2
     * @return Minutes difference + 1
     */
    public static long getTimeDifferenceMinutes(final Date d1, final Date d2) {
        try {
            //return long java.time.Duration.between(dateTime, dateTime2).toMinutes();
            return TimeUnit.MILLISECONDS.toMinutes(d2.getTime() - d1.getTime()) + 1;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return TimeUnit.MILLISECONDS.toHours(0L);
    }

    public static long getTimeDifferenceSeconds(final Date d1, final Date d2) {
        try {
            return TimeUnit.MILLISECONDS.toSeconds(d2.getTime() - d1.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return TimeUnit.MILLISECONDS.toSeconds(0L);
    }

    public static long getTimeDifferenceYears(final Date d1, final Date d2) {
        long diffYears = 0L;
        try {
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();
            diffYears = diff / (60 * 60 * 1000 * 24 * 365);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return diffYears;
    }

    public static String getTimeStringFromDateTime(final DateTime value) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(LONG_TIME_FORMAT);
        if (value != null) {
            return formatter.print(value);
        }
        else {
            return null;
        }
    }

    public static String getToday() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String getWorkingHours(final Date d1, final Date d2) {
        String duration = null;
        if (d1 != null && d2 != null) {
            long diff = d1.getTime() - d2.getTime();
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);

            if (diffHours >= 0) {
                duration = diffHours + ":" + diffMinutes;
                return duration;
            }
            else {
                duration = "00:00";
                return duration;
            }
        }
        return null;
    }

    public static boolean greaterThan(final Date d1, final Date d2) {
        return d1.after(d2);
    }

    public static boolean greaterThanEqual(final Date d1, final Date d2) {
        return d1.equals(d2) || d1.after(d2);
    }

    /**
     * @param date
     * @return
     */
    public static boolean isFutureDate(final String date) {
        if (DateUtil.greaterThan(DateUtil.formatDate(date), DateUtil.formatDate(getToday()))) {
            return true;
        }
        return false;
    }

    /**
     * @param remainderDate
     * @return
     */
    public static boolean isPastDate(final String date) {
        if (DateUtil.lessThanEqual(DateUtil.formatDate(date), DateUtil.formatDate(getToday()))) {
            return true;
        }
        return false;
    }

    /**
     * @param date
     * @param dateFormate
     * @return
     * @author ravichand.a
     */
    public static boolean isValidateDateFormate(final String date, final String dateFormate) {

        SimpleDateFormat df = new SimpleDateFormat(dateFormate);
        Date testDate = null;
        try {
            testDate = df.parse(date);
        }
        catch (Exception e) {
            /*
             *  invalid date format
             */
            return false;
        }
        /*
         *  now test for legal values of parameters
         */
        if (!df.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    public static boolean isValidateMySqlDateFormate(final String date) {

        SimpleDateFormat df = new SimpleDateFormat(MYSQL_DATE_FOMATE);
        Date testDate = null;
        try {
            testDate = df.parse(date);
        }
        catch (Exception e) {
            /*
             *  invalid date format
             */
            return false;
        }
        /*
         *  now test for legal values of parameters
         */
        if (!df.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(final String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
            String sdate[] = date.split("\\/");
            String sdt = sdate[2];
            Integer.parseInt(sdt);
        }
        catch (ParseException pe) {
            return false;
        }
        catch (NumberFormatException ne) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
        if (!sdf.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    public static boolean isValidDateAndYearRange(final String date) {
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
            String sdate[] = date.split("\\/");
            String sdt = sdate[2];
            i = Integer.parseInt(sdt);
        }
        catch (ParseException pe) {
            return false;
        }
        catch (NumberFormatException ne) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
        if (!sdf.format(testDate).equals(date)) {
            return false;
        }
        if (i < 1800 || i > 2999) {
            return false;
        }
        return true;
    }

    public static boolean lessThan(final Date d1, final Date d2) {
        return d1.before(d2);
    }

    public static boolean lessThanEqual(final Date d1, final Date d2) {
        return d1.equals(d2) || d1.before(d2);
    }

    public static DateTime parseDate(final String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return DATE_TIME_FORMATTER_2.parseDateTime(date);
    }

    /**
     * Perform a conversion from an Object to a DateTime. If the input value is string, then it should be one of predefined format in
     * formats array, else it can be an object that can represent a Date.
     *
     * @param value
     * @return DateTime
     */
    public static DateTime toDate(final Object value) {
        if (value == null) {
            return null;
        }
        else if (value instanceof DateTime) {
            return (DateTime) value;
        }
        else if (value instanceof String) {
            final String valueString = value.toString();
            for (String format : DATE_TIME_FORMATS) {
                try {
                    DateTime parsed = DateTimeFormat.forPattern(format).parseDateTime(valueString);
                    return parsed;
                }
                catch (Exception e) {
                    /*
                     *  format is not matching, but don't fail
                     *  just go to the next format
                     */
                }
            }
        }

        return new DateTime(value);
    }

    public static boolean validateDate(final String date) {

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date testDate = null;
        try {
            testDate = df.parse(date);
        }
        catch (Exception e) {
            /*
             *  invalid date format
             */
            return false;
        }
        /*
         *  now test for legal values of parameters
         */
        if (!df.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    public static boolean validateDateDDMMYYYY(final String date) {

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
        Date testDate = null;
        try {
            testDate = df.parse(date);
        }
        catch (Exception e) {
            /*
             *  invalid date format
             */
            return false;
        }
        /*
         *  now test for legal values of parameters
         */
        if (!df.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    public static boolean validateDateDDMMYYYYHHMM(final String date) {

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy_HH_MM);
        Date testDate = null;
        try {
            testDate = df.parse(date);
        }
        catch (Exception e) {
            /*
             *  invalid date format
             */
            return false;
        }
        /*
         *  now test for legal values of parameters
         */
        if (!df.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    public static boolean validateDateDDMMYYYYHHMMSS(final String date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_TIME);
        Date testDate = null;
        try {
            testDate = df.parse(date);
        }
        catch (Exception e) {
            /*
             *  invalid date format
             */
            return false;
        }
        /*
         *  now test for legal values of parameters
         */
        if (!df.format(testDate).equals(date)) {
            return false;
        }
        return true;
    }

    /*
     * Validate the Expiry Date greater than System Date i.e 12/31/2999
     * validateDate
     * @param String date
     * @return boolean
     */
    public static boolean validateExpDateGreaterThanSysDate(final String date) {
        if (DateUtil.greaterThan(DateUtil.formatDate(date), DateUtil.formatDate(SYSTEM_MAX_DATE))) {
            return false;
        }
        return true;
    }

}
