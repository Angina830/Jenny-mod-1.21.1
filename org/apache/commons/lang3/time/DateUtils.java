/*      */ package org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.text.ParseException;
/*      */ import java.text.ParsePosition;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateUtils
/*      */ {
/*      */   public static final long MILLIS_PER_SECOND = 1000L;
/*      */   public static final long MILLIS_PER_MINUTE = 60000L;
/*      */   public static final long MILLIS_PER_HOUR = 3600000L;
/*      */   public static final long MILLIS_PER_DAY = 86400000L;
/*      */   public static final int SEMI_MONTH = 1001;
/*   83 */   private static final int[][] fields = new int[][] { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_SUNDAY = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_MONDAY = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_RELATIVE = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_CENTER = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_MONTH_SUNDAY = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_MONTH_MONDAY = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum ModifyType
/*      */   {
/*  127 */     TRUNCATE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  132 */     ROUND,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  137 */     CEILING;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameDay(Date date1, Date date2) {
/*  167 */     if (date1 == null || date2 == null) {
/*  168 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  170 */     Calendar cal1 = Calendar.getInstance();
/*  171 */     cal1.setTime(date1);
/*  172 */     Calendar cal2 = Calendar.getInstance();
/*  173 */     cal2.setTime(date2);
/*  174 */     return isSameDay(cal1, cal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameDay(Calendar cal1, Calendar cal2) {
/*  191 */     if (cal1 == null || cal2 == null) {
/*  192 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  194 */     return (cal1.get(0) == cal2.get(0) && cal1
/*  195 */       .get(1) == cal2.get(1) && cal1
/*  196 */       .get(6) == cal2.get(6));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameInstant(Date date1, Date date2) {
/*  212 */     if (date1 == null || date2 == null) {
/*  213 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  215 */     return (date1.getTime() == date2.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
/*  230 */     if (cal1 == null || cal2 == null) {
/*  231 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  233 */     return (cal1.getTime().getTime() == cal2.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
/*  250 */     if (cal1 == null || cal2 == null) {
/*  251 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  253 */     return (cal1.get(14) == cal2.get(14) && cal1
/*  254 */       .get(13) == cal2.get(13) && cal1
/*  255 */       .get(12) == cal2.get(12) && cal1
/*  256 */       .get(11) == cal2.get(11) && cal1
/*  257 */       .get(6) == cal2.get(6) && cal1
/*  258 */       .get(1) == cal2.get(1) && cal1
/*  259 */       .get(0) == cal2.get(0) && cal1
/*  260 */       .getClass() == cal2.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDate(String str, String... parsePatterns) throws ParseException {
/*  279 */     return parseDate(str, null, parsePatterns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDate(String str, Locale locale, String... parsePatterns) throws ParseException {
/*  302 */     return parseDateWithLeniency(str, locale, parsePatterns, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
/*  322 */     return parseDateStrictly(str, null, parsePatterns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDateStrictly(String str, Locale locale, String... parsePatterns) throws ParseException {
/*  344 */     return parseDateWithLeniency(str, locale, parsePatterns, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date parseDateWithLeniency(String str, Locale locale, String[] parsePatterns, boolean lenient) throws ParseException {
/*  366 */     if (str == null || parsePatterns == null) {
/*  367 */       throw new IllegalArgumentException("Date and Patterns must not be null");
/*      */     }
/*      */     
/*  370 */     TimeZone tz = TimeZone.getDefault();
/*  371 */     Locale lcl = (locale == null) ? Locale.getDefault() : locale;
/*  372 */     ParsePosition pos = new ParsePosition(0);
/*  373 */     Calendar calendar = Calendar.getInstance(tz, lcl);
/*  374 */     calendar.setLenient(lenient);
/*      */     
/*  376 */     for (String parsePattern : parsePatterns) {
/*  377 */       FastDateParser fdp = new FastDateParser(parsePattern, tz, lcl);
/*  378 */       calendar.clear();
/*      */       try {
/*  380 */         if (fdp.parse(str, pos, calendar) && pos.getIndex() == str.length()) {
/*  381 */           return calendar.getTime();
/*      */         }
/*  383 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */ 
/*      */       
/*  386 */       pos.setIndex(0);
/*      */     } 
/*  388 */     throw new ParseException("Unable to parse the date: " + str, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addYears(Date date, int amount) {
/*  402 */     return add(date, 1, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMonths(Date date, int amount) {
/*  416 */     return add(date, 2, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addWeeks(Date date, int amount) {
/*  430 */     return add(date, 3, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addDays(Date date, int amount) {
/*  444 */     return add(date, 5, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addHours(Date date, int amount) {
/*  458 */     return add(date, 11, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMinutes(Date date, int amount) {
/*  472 */     return add(date, 12, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addSeconds(Date date, int amount) {
/*  486 */     return add(date, 13, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMilliseconds(Date date, int amount) {
/*  500 */     return add(date, 14, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date add(Date date, int calendarField, int amount) {
/*  515 */     validateDateNotNull(date);
/*  516 */     Calendar c = Calendar.getInstance();
/*  517 */     c.setTime(date);
/*  518 */     c.add(calendarField, amount);
/*  519 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setYears(Date date, int amount) {
/*  534 */     return set(date, 1, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMonths(Date date, int amount) {
/*  549 */     return set(date, 2, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setDays(Date date, int amount) {
/*  564 */     return set(date, 5, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setHours(Date date, int amount) {
/*  580 */     return set(date, 11, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMinutes(Date date, int amount) {
/*  595 */     return set(date, 12, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setSeconds(Date date, int amount) {
/*  610 */     return set(date, 13, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMilliseconds(Date date, int amount) {
/*  625 */     return set(date, 14, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date set(Date date, int calendarField, int amount) {
/*  642 */     validateDateNotNull(date);
/*      */     
/*  644 */     Calendar c = Calendar.getInstance();
/*  645 */     c.setLenient(false);
/*  646 */     c.setTime(date);
/*  647 */     c.set(calendarField, amount);
/*  648 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar toCalendar(Date date) {
/*  661 */     Calendar c = Calendar.getInstance();
/*  662 */     c.setTime(date);
/*  663 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar toCalendar(Date date, TimeZone tz) {
/*  675 */     Calendar c = Calendar.getInstance(tz);
/*  676 */     c.setTime(date);
/*  677 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date round(Date date, int field) {
/*  708 */     validateDateNotNull(date);
/*  709 */     Calendar gval = Calendar.getInstance();
/*  710 */     gval.setTime(date);
/*  711 */     modify(gval, field, ModifyType.ROUND);
/*  712 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar round(Calendar date, int field) {
/*  743 */     if (date == null) {
/*  744 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  746 */     Calendar rounded = (Calendar)date.clone();
/*  747 */     modify(rounded, field, ModifyType.ROUND);
/*  748 */     return rounded;
/*      */   }
/*      */   
/*      */   private static IllegalArgumentException nullDateIllegalArgumentException() {
/*  752 */     return new IllegalArgumentException("The date must not be null");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date round(Object date, int field) {
/*  784 */     if (date == null) {
/*  785 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  787 */     if (date instanceof Date)
/*  788 */       return round((Date)date, field); 
/*  789 */     if (date instanceof Calendar) {
/*  790 */       return round((Calendar)date, field).getTime();
/*      */     }
/*  792 */     throw new ClassCastException("Could not round " + date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date truncate(Date date, int field) {
/*  813 */     validateDateNotNull(date);
/*  814 */     Calendar gval = Calendar.getInstance();
/*  815 */     gval.setTime(date);
/*  816 */     modify(gval, field, ModifyType.TRUNCATE);
/*  817 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar truncate(Calendar date, int field) {
/*  836 */     if (date == null) {
/*  837 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  839 */     Calendar truncated = (Calendar)date.clone();
/*  840 */     modify(truncated, field, ModifyType.TRUNCATE);
/*  841 */     return truncated;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date truncate(Object date, int field) {
/*  861 */     if (date == null) {
/*  862 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  864 */     if (date instanceof Date)
/*  865 */       return truncate((Date)date, field); 
/*  866 */     if (date instanceof Calendar) {
/*  867 */       return truncate((Calendar)date, field).getTime();
/*      */     }
/*  869 */     throw new ClassCastException("Could not truncate " + date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date ceiling(Date date, int field) {
/*  891 */     validateDateNotNull(date);
/*  892 */     Calendar gval = Calendar.getInstance();
/*  893 */     gval.setTime(date);
/*  894 */     modify(gval, field, ModifyType.CEILING);
/*  895 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar ceiling(Calendar date, int field) {
/*  915 */     if (date == null) {
/*  916 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  918 */     Calendar ceiled = (Calendar)date.clone();
/*  919 */     modify(ceiled, field, ModifyType.CEILING);
/*  920 */     return ceiled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date ceiling(Object date, int field) {
/*  941 */     if (date == null) {
/*  942 */       throw nullDateIllegalArgumentException();
/*      */     }
/*  944 */     if (date instanceof Date)
/*  945 */       return ceiling((Date)date, field); 
/*  946 */     if (date instanceof Calendar) {
/*  947 */       return ceiling((Calendar)date, field).getTime();
/*      */     }
/*  949 */     throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void modify(Calendar val, int field, ModifyType modType) {
/*  963 */     if (val.get(1) > 280000000) {
/*  964 */       throw new ArithmeticException("Calendar value too large for accurate calculations");
/*      */     }
/*      */     
/*  967 */     if (field == 14) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  977 */     Date date = val.getTime();
/*  978 */     long time = date.getTime();
/*  979 */     boolean done = false;
/*      */ 
/*      */     
/*  982 */     int millisecs = val.get(14);
/*  983 */     if (ModifyType.TRUNCATE == modType || millisecs < 500) {
/*  984 */       time -= millisecs;
/*      */     }
/*  986 */     if (field == 13) {
/*  987 */       done = true;
/*      */     }
/*      */ 
/*      */     
/*  991 */     int seconds = val.get(13);
/*  992 */     if (!done && (ModifyType.TRUNCATE == modType || seconds < 30)) {
/*  993 */       time -= seconds * 1000L;
/*      */     }
/*  995 */     if (field == 12) {
/*  996 */       done = true;
/*      */     }
/*      */ 
/*      */     
/* 1000 */     int minutes = val.get(12);
/* 1001 */     if (!done && (ModifyType.TRUNCATE == modType || minutes < 30)) {
/* 1002 */       time -= minutes * 60000L;
/*      */     }
/*      */ 
/*      */     
/* 1006 */     if (date.getTime() != time) {
/* 1007 */       date.setTime(time);
/* 1008 */       val.setTime(date);
/*      */     } 
/*      */ 
/*      */     
/* 1012 */     boolean roundUp = false;
/* 1013 */     for (int[] aField : fields) {
/* 1014 */       for (int element : aField) {
/* 1015 */         if (element == field) {
/*      */           
/* 1017 */           if (modType == ModifyType.CEILING || (modType == ModifyType.ROUND && roundUp)) {
/* 1018 */             if (field == 1001) {
/*      */ 
/*      */ 
/*      */               
/* 1022 */               if (val.get(5) == 1) {
/* 1023 */                 val.add(5, 15);
/*      */               } else {
/* 1025 */                 val.add(5, -15);
/* 1026 */                 val.add(2, 1);
/*      */               }
/*      */             
/* 1029 */             } else if (field == 9) {
/*      */ 
/*      */ 
/*      */               
/* 1033 */               if (val.get(11) == 0) {
/* 1034 */                 val.add(11, 12);
/*      */               } else {
/* 1036 */                 val.add(11, -12);
/* 1037 */                 val.add(5, 1);
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1043 */               val.add(aField[0], 1);
/*      */             } 
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 1050 */       int offset = 0;
/* 1051 */       boolean offsetSet = false;
/*      */       
/* 1053 */       switch (field) {
/*      */         case 1001:
/* 1055 */           if (aField[0] == 5) {
/*      */ 
/*      */ 
/*      */             
/* 1059 */             offset = val.get(5) - 1;
/*      */ 
/*      */             
/* 1062 */             if (offset >= 15) {
/* 1063 */               offset -= 15;
/*      */             }
/*      */             
/* 1066 */             roundUp = (offset > 7);
/* 1067 */             offsetSet = true;
/*      */           } 
/*      */           break;
/*      */         case 9:
/* 1071 */           if (aField[0] == 11) {
/*      */ 
/*      */             
/* 1074 */             offset = val.get(11);
/* 1075 */             if (offset >= 12) {
/* 1076 */               offset -= 12;
/*      */             }
/* 1078 */             roundUp = (offset >= 6);
/* 1079 */             offsetSet = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1085 */       if (!offsetSet) {
/* 1086 */         int min = val.getActualMinimum(aField[0]);
/* 1087 */         int max = val.getActualMaximum(aField[0]);
/*      */         
/* 1089 */         offset = val.get(aField[0]) - min;
/*      */         
/* 1091 */         roundUp = (offset > (max - min) / 2);
/*      */       } 
/*      */       
/* 1094 */       if (offset != 0) {
/* 1095 */         val.set(aField[0], val.get(aField[0]) - offset);
/*      */       }
/*      */     } 
/* 1098 */     throw new IllegalArgumentException("The field " + field + " is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<Calendar> iterator(Date focus, int rangeStyle) {
/* 1128 */     validateDateNotNull(focus);
/* 1129 */     Calendar gval = Calendar.getInstance();
/* 1130 */     gval.setTime(focus);
/* 1131 */     return iterator(gval, rangeStyle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<Calendar> iterator(Calendar focus, int rangeStyle) {
/* 1159 */     if (focus == null) {
/* 1160 */       throw nullDateIllegalArgumentException();
/*      */     }
/* 1162 */     Calendar start = null;
/* 1163 */     Calendar end = null;
/* 1164 */     int startCutoff = 1;
/* 1165 */     int endCutoff = 7;
/* 1166 */     switch (rangeStyle) {
/*      */       
/*      */       case 5:
/*      */       case 6:
/* 1170 */         start = truncate(focus, 2);
/*      */         
/* 1172 */         end = (Calendar)start.clone();
/* 1173 */         end.add(2, 1);
/* 1174 */         end.add(5, -1);
/*      */         
/* 1176 */         if (rangeStyle == 6) {
/* 1177 */           startCutoff = 2;
/* 1178 */           endCutoff = 1;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/* 1186 */         start = truncate(focus, 5);
/* 1187 */         end = truncate(focus, 5);
/* 1188 */         switch (rangeStyle) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 1193 */             startCutoff = 2;
/* 1194 */             endCutoff = 1;
/*      */             break;
/*      */           case 3:
/* 1197 */             startCutoff = focus.get(7);
/* 1198 */             endCutoff = startCutoff - 1;
/*      */             break;
/*      */           case 4:
/* 1201 */             startCutoff = focus.get(7) - 3;
/* 1202 */             endCutoff = focus.get(7) + 3;
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       default:
/* 1209 */         throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
/*      */     } 
/* 1211 */     if (startCutoff < 1) {
/* 1212 */       startCutoff += 7;
/*      */     }
/* 1214 */     if (startCutoff > 7) {
/* 1215 */       startCutoff -= 7;
/*      */     }
/* 1217 */     if (endCutoff < 1) {
/* 1218 */       endCutoff += 7;
/*      */     }
/* 1220 */     if (endCutoff > 7) {
/* 1221 */       endCutoff -= 7;
/*      */     }
/* 1223 */     while (start.get(7) != startCutoff) {
/* 1224 */       start.add(5, -1);
/*      */     }
/* 1226 */     while (end.get(7) != endCutoff) {
/* 1227 */       end.add(5, 1);
/*      */     }
/* 1229 */     return new DateIterator(start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<?> iterator(Object focus, int rangeStyle) {
/* 1249 */     if (focus == null) {
/* 1250 */       throw nullDateIllegalArgumentException();
/*      */     }
/* 1252 */     if (focus instanceof Date)
/* 1253 */       return iterator((Date)focus, rangeStyle); 
/* 1254 */     if (focus instanceof Calendar) {
/* 1255 */       return iterator((Calendar)focus, rangeStyle);
/*      */     }
/* 1257 */     throw new ClassCastException("Could not iterate based on " + focus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMilliseconds(Date date, int fragment) {
/* 1293 */     return getFragment(date, fragment, TimeUnit.MILLISECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInSeconds(Date date, int fragment) {
/* 1331 */     return getFragment(date, fragment, TimeUnit.SECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMinutes(Date date, int fragment) {
/* 1369 */     return getFragment(date, fragment, TimeUnit.MINUTES);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInHours(Date date, int fragment) {
/* 1407 */     return getFragment(date, fragment, TimeUnit.HOURS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInDays(Date date, int fragment) {
/* 1445 */     return getFragment(date, fragment, TimeUnit.DAYS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
/* 1483 */     return getFragment(calendar, fragment, TimeUnit.MILLISECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInSeconds(Calendar calendar, int fragment) {
/* 1520 */     return getFragment(calendar, fragment, TimeUnit.SECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMinutes(Calendar calendar, int fragment) {
/* 1558 */     return getFragment(calendar, fragment, TimeUnit.MINUTES);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInHours(Calendar calendar, int fragment) {
/* 1596 */     return getFragment(calendar, fragment, TimeUnit.HOURS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInDays(Calendar calendar, int fragment) {
/* 1636 */     return getFragment(calendar, fragment, TimeUnit.DAYS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getFragment(Date date, int fragment, TimeUnit unit) {
/* 1651 */     validateDateNotNull(date);
/* 1652 */     Calendar calendar = Calendar.getInstance();
/* 1653 */     calendar.setTime(date);
/* 1654 */     return getFragment(calendar, fragment, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getFragment(Calendar calendar, int fragment, TimeUnit unit) {
/* 1669 */     if (calendar == null) {
/* 1670 */       throw nullDateIllegalArgumentException();
/*      */     }
/*      */     
/* 1673 */     long result = 0L;
/*      */     
/* 1675 */     int offset = (unit == TimeUnit.DAYS) ? 0 : 1;
/*      */ 
/*      */     
/* 1678 */     switch (fragment) {
/*      */       case 1:
/* 1680 */         result += unit.convert((calendar.get(6) - offset), TimeUnit.DAYS);
/*      */         break;
/*      */       case 2:
/* 1683 */         result += unit.convert((calendar.get(5) - offset), TimeUnit.DAYS);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1689 */     switch (fragment) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 5:
/*      */       case 6:
/* 1697 */         result += unit.convert(calendar.get(11), TimeUnit.HOURS);
/*      */       
/*      */       case 11:
/* 1700 */         result += unit.convert(calendar.get(12), TimeUnit.MINUTES);
/*      */       
/*      */       case 12:
/* 1703 */         result += unit.convert(calendar.get(13), TimeUnit.SECONDS);
/*      */       
/*      */       case 13:
/* 1706 */         result += unit.convert(calendar.get(14), TimeUnit.MILLISECONDS);
/*      */ 
/*      */ 
/*      */       
/*      */       case 14:
/* 1711 */         return result;
/*      */     } 
/*      */     throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
/* 1728 */     return (truncatedCompareTo(cal1, cal2, field) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean truncatedEquals(Date date1, Date date2, int field) {
/* 1745 */     return (truncatedCompareTo(date1, date2, field) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
/* 1763 */     Calendar truncatedCal1 = truncate(cal1, field);
/* 1764 */     Calendar truncatedCal2 = truncate(cal2, field);
/* 1765 */     return truncatedCal1.compareTo(truncatedCal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int truncatedCompareTo(Date date1, Date date2, int field) {
/* 1783 */     Date truncatedDate1 = truncate(date1, field);
/* 1784 */     Date truncatedDate2 = truncate(date2, field);
/* 1785 */     return truncatedDate1.compareTo(truncatedDate2);
/*      */   }
/*      */   
/*      */   private static void validateDateNotNull(Date date) {
/* 1789 */     Validate.notNull(date, "The date must not be null", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DateIterator
/*      */     implements Iterator<Calendar>
/*      */   {
/*      */     private final Calendar endFinal;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Calendar spot;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DateIterator(Calendar startFinal, Calendar endFinal) {
/* 1808 */       this.endFinal = endFinal;
/* 1809 */       this.spot = startFinal;
/* 1810 */       this.spot.add(5, -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1820 */       return this.spot.before(this.endFinal);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Calendar next() {
/* 1830 */       if (this.spot.equals(this.endFinal)) {
/* 1831 */         throw new NoSuchElementException();
/*      */       }
/* 1833 */       this.spot.add(5, 1);
/* 1834 */       return (Calendar)this.spot.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1845 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\DateUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */