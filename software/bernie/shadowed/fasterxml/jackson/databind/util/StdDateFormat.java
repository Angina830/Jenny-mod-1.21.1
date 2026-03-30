/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StdDateFormat
/*     */   extends DateFormat
/*     */ {
/*     */   protected static final String PATTERN_PLAIN_STR = "\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d";
/*  31 */   protected static final Pattern PATTERN_PLAIN = Pattern.compile("\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d"); protected static final Pattern PATTERN_ISO8601;
/*     */   public static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
/*     */   
/*     */   static {
/*  35 */     Pattern p = null;
/*     */     try {
/*  37 */       p = Pattern.compile("\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d[T]\\d\\d[:]\\d\\d(?:[:]\\d\\d)?(\\.\\d+)?(Z|[+-]\\d\\d(?:[:]?\\d\\d)?)?");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  42 */     catch (Throwable t) {
/*  43 */       throw new RuntimeException(t);
/*     */     } 
/*  45 */     PATTERN_ISO8601 = p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   protected static final String[] ALL_FORMATS = new String[] { "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");
/*     */ 
/*     */   
/*  86 */   protected static final Locale DEFAULT_LOCALE = Locale.US;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   protected static final DateFormat DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", DEFAULT_LOCALE); static {
/* 100 */     DATE_FORMAT_RFC1123.setTimeZone(DEFAULT_TIMEZONE);
/* 101 */   } protected static final DateFormat DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", DEFAULT_LOCALE); static {
/* 102 */     DATE_FORMAT_ISO8601.setTimeZone(DEFAULT_TIMEZONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static final StdDateFormat instance = new StdDateFormat();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient TimeZone _timezone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Locale _locale;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean _lenient;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient DateFormat _formatRFC1123;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdDateFormat() {
/* 137 */     this._locale = DEFAULT_LOCALE;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public StdDateFormat(TimeZone tz, Locale loc) {
/* 142 */     this._timezone = tz;
/* 143 */     this._locale = loc;
/*     */   }
/*     */   
/*     */   protected StdDateFormat(TimeZone tz, Locale loc, Boolean lenient) {
/* 147 */     this._timezone = tz;
/* 148 */     this._locale = loc;
/* 149 */     this._lenient = lenient;
/*     */   }
/*     */   
/*     */   public static TimeZone getDefaultTimeZone() {
/* 153 */     return DEFAULT_TIMEZONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdDateFormat withTimeZone(TimeZone tz) {
/* 161 */     if (tz == null) {
/* 162 */       tz = DEFAULT_TIMEZONE;
/*     */     }
/* 164 */     if (tz == this._timezone || tz.equals(this._timezone)) {
/* 165 */       return this;
/*     */     }
/* 167 */     return new StdDateFormat(tz, this._locale, this._lenient);
/*     */   }
/*     */   
/*     */   public StdDateFormat withLocale(Locale loc) {
/* 171 */     if (loc.equals(this._locale)) {
/* 172 */       return this;
/*     */     }
/* 174 */     return new StdDateFormat(this._timezone, loc, this._lenient);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdDateFormat withLenient(Boolean b) {
/* 181 */     if (_equals(b, this._lenient)) {
/* 182 */       return this;
/*     */     }
/* 184 */     return new StdDateFormat(this._timezone, this._locale, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdDateFormat clone() {
/* 191 */     return new StdDateFormat(this._timezone, this._locale, this._lenient);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static DateFormat getISO8601Format(TimeZone tz, Locale loc) {
/* 205 */     return _cloneFormat(DATE_FORMAT_ISO8601, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", tz, loc, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static DateFormat getRFC1123Format(TimeZone tz, Locale loc) {
/* 219 */     return _cloneFormat(DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", tz, loc, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 231 */     return this._timezone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeZone(TimeZone tz) {
/* 240 */     if (!tz.equals(this._timezone)) {
/* 241 */       _clearFormats();
/* 242 */       this._timezone = tz;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLenient(boolean enabled) {
/* 253 */     Boolean newValue = Boolean.valueOf(enabled);
/* 254 */     if (!_equals(newValue, this._lenient)) {
/* 255 */       this._lenient = newValue;
/*     */       
/* 257 */       _clearFormats();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLenient() {
/* 264 */     return (this._lenient == null || this._lenient.booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String dateStr) throws ParseException {
/* 276 */     dateStr = dateStr.trim();
/* 277 */     ParsePosition pos = new ParsePosition(0);
/* 278 */     Date dt = _parseDate(dateStr, pos);
/* 279 */     if (dt != null) {
/* 280 */       return dt;
/*     */     }
/* 282 */     StringBuilder sb = new StringBuilder();
/* 283 */     for (String f : ALL_FORMATS) {
/* 284 */       if (sb.length() > 0) {
/* 285 */         sb.append("\", \"");
/*     */       } else {
/* 287 */         sb.append('"');
/*     */       } 
/* 289 */       sb.append(f);
/*     */     } 
/* 291 */     sb.append('"');
/* 292 */     throw new ParseException(String.format("Cannot parse date \"%s\": not compatible with any of standard forms (%s)", new Object[] { dateStr, sb.toString() }), pos.getErrorIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String dateStr, ParsePosition pos) {
/*     */     try {
/* 302 */       return _parseDate(dateStr, pos);
/* 303 */     } catch (ParseException e) {
/*     */ 
/*     */       
/* 306 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Date _parseDate(String dateStr, ParsePosition pos) throws ParseException {
/* 311 */     if (looksLikeISO8601(dateStr)) {
/* 312 */       return parseAsISO8601(dateStr, pos);
/*     */     }
/*     */     
/* 315 */     int i = dateStr.length();
/* 316 */     while (--i >= 0) {
/* 317 */       char ch = dateStr.charAt(i);
/* 318 */       if (ch < '0' || ch > '9')
/*     */       {
/* 320 */         if (i > 0 || ch != '-') {
/*     */           break;
/*     */         }
/*     */       }
/*     */     } 
/* 325 */     if (i < 0 && (dateStr.charAt(0) == '-' || NumberInput.inLongRange(dateStr, false)))
/*     */     {
/*     */       
/* 328 */       return _parseDateFromLong(dateStr, pos);
/*     */     }
/*     */     
/* 331 */     return parseAsRFC1123(dateStr, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
/* 344 */     TimeZone tz = this._timezone;
/* 345 */     if (tz == null) {
/* 346 */       tz = DEFAULT_TIMEZONE;
/*     */     }
/* 348 */     _format(tz, this._locale, date, toAppendTo);
/* 349 */     return toAppendTo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void _format(TimeZone tz, Locale loc, Date date, StringBuffer buffer) {
/* 355 */     Calendar calendar = new GregorianCalendar(tz, loc);
/* 356 */     calendar.setTime(date);
/*     */     
/* 358 */     pad4(buffer, calendar.get(1));
/* 359 */     buffer.append('-');
/* 360 */     pad2(buffer, calendar.get(2) + 1);
/* 361 */     buffer.append('-');
/* 362 */     pad2(buffer, calendar.get(5));
/* 363 */     buffer.append('T');
/* 364 */     pad2(buffer, calendar.get(11));
/* 365 */     buffer.append(':');
/* 366 */     pad2(buffer, calendar.get(12));
/* 367 */     buffer.append(':');
/* 368 */     pad2(buffer, calendar.get(13));
/* 369 */     buffer.append('.');
/* 370 */     pad3(buffer, calendar.get(14));
/*     */     
/* 372 */     int offset = tz.getOffset(calendar.getTimeInMillis());
/* 373 */     if (offset != 0) {
/* 374 */       int hours = Math.abs(offset / 60000 / 60);
/* 375 */       int minutes = Math.abs(offset / 60000 % 60);
/* 376 */       buffer.append((offset < 0) ? 45 : 43);
/* 377 */       pad2(buffer, hours);
/*     */ 
/*     */ 
/*     */       
/* 381 */       pad2(buffer, minutes);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 386 */       buffer.append("+0000");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void pad2(StringBuffer buffer, int value) {
/* 391 */     int tens = value / 10;
/* 392 */     if (tens == 0) {
/* 393 */       buffer.append('0');
/*     */     } else {
/* 395 */       buffer.append((char)(48 + tens));
/* 396 */       value -= 10 * tens;
/*     */     } 
/* 398 */     buffer.append((char)(48 + value));
/*     */   }
/*     */   
/*     */   private static void pad3(StringBuffer buffer, int value) {
/* 402 */     int h = value / 100;
/* 403 */     if (h == 0) {
/* 404 */       buffer.append('0');
/*     */     } else {
/* 406 */       buffer.append((char)(48 + h));
/* 407 */       value -= h * 100;
/*     */     } 
/* 409 */     pad2(buffer, value);
/*     */   }
/*     */   
/*     */   private static void pad4(StringBuffer buffer, int value) {
/* 413 */     int h = value / 100;
/* 414 */     if (h == 0) {
/* 415 */       buffer.append('0').append('0');
/*     */     } else {
/* 417 */       pad2(buffer, h);
/* 418 */       value -= 100 * h;
/*     */     } 
/* 420 */     pad2(buffer, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 431 */     return String.format("DateFormat %s: (timezone: %s, locale: %s, lenient: %s)", new Object[] { getClass().getName(), this._timezone, this._locale, this._lenient });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toPattern() {
/* 436 */     StringBuilder sb = new StringBuilder(100);
/* 437 */     sb.append("[one of: '").append("yyyy-MM-dd'T'HH:mm:ss.SSSZ").append("', '").append("EEE, dd MMM yyyy HH:mm:ss zzz").append("' (");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 443 */     sb.append(Boolean.FALSE.equals(this._lenient) ? "strict" : "lenient").append(")]");
/*     */ 
/*     */     
/* 446 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 451 */     return (o == this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 456 */     return System.identityHashCode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean looksLikeISO8601(String dateStr) {
/* 471 */     if (dateStr.length() >= 7 && Character.isDigit(dateStr.charAt(0)) && Character.isDigit(dateStr.charAt(3)) && dateStr.charAt(4) == '-' && Character.isDigit(dateStr.charAt(5)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 477 */       return true;
/*     */     }
/* 479 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private Date _parseDateFromLong(String longStr, ParsePosition pos) throws ParseException {
/*     */     long ts;
/*     */     try {
/* 486 */       ts = NumberInput.parseLong(longStr);
/* 487 */     } catch (NumberFormatException e) {
/* 488 */       throw new ParseException(String.format("Timestamp value %s out of 64-bit value range", new Object[] { longStr }), pos.getErrorIndex());
/*     */     } 
/*     */ 
/*     */     
/* 492 */     return new Date(ts);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Date parseAsISO8601(String dateStr, ParsePosition pos) throws ParseException {
/*     */     try {
/* 499 */       return _parseAsISO8601(dateStr, pos);
/* 500 */     } catch (IllegalArgumentException e) {
/* 501 */       throw new ParseException(String.format("Cannot parse date \"%s\", problem: %s", new Object[] { dateStr, e.getMessage() }), pos.getErrorIndex());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Date _parseAsISO8601(String dateStr, ParsePosition pos) throws IllegalArgumentException, ParseException {
/*     */     String formatStr;
/* 510 */     int totalLen = dateStr.length();
/*     */     
/* 512 */     TimeZone tz = DEFAULT_TIMEZONE;
/* 513 */     if (this._timezone != null && 'Z' != dateStr.charAt(totalLen - 1)) {
/* 514 */       tz = this._timezone;
/*     */     }
/* 516 */     Calendar cal = new GregorianCalendar(tz, this._locale);
/* 517 */     if (this._lenient != null) {
/* 518 */       cal.setLenient(this._lenient.booleanValue());
/*     */     }
/*     */     
/* 521 */     if (totalLen <= 10) {
/* 522 */       Matcher m = PATTERN_PLAIN.matcher(dateStr);
/* 523 */       if (m.matches()) {
/* 524 */         int year = _parse4D(dateStr, 0);
/* 525 */         int month = _parse2D(dateStr, 5) - 1;
/* 526 */         int day = _parse2D(dateStr, 8);
/*     */         
/* 528 */         cal.set(year, month, day, 0, 0, 0);
/* 529 */         cal.set(14, 0);
/* 530 */         return cal.getTime();
/*     */       } 
/* 532 */       formatStr = "yyyy-MM-dd";
/*     */     } else {
/* 534 */       Matcher m = PATTERN_ISO8601.matcher(dateStr);
/* 535 */       if (m.matches()) {
/*     */ 
/*     */ 
/*     */         
/* 539 */         int seconds, start = m.start(2);
/* 540 */         int end = m.end(2);
/* 541 */         int len = end - start;
/* 542 */         if (len > 1) {
/*     */           
/* 544 */           int offsetSecs = _parse2D(dateStr, start + 1) * 3600;
/* 545 */           if (len >= 5) {
/* 546 */             offsetSecs += _parse2D(dateStr, end - 2);
/*     */           }
/* 548 */           if (dateStr.charAt(start) == '-') {
/* 549 */             offsetSecs *= -1000;
/*     */           } else {
/* 551 */             offsetSecs *= 1000;
/*     */           } 
/* 553 */           cal.set(15, offsetSecs);
/*     */           
/* 555 */           cal.set(16, 0);
/*     */         } 
/*     */         
/* 558 */         int year = _parse4D(dateStr, 0);
/* 559 */         int month = _parse2D(dateStr, 5) - 1;
/* 560 */         int day = _parse2D(dateStr, 8);
/*     */ 
/*     */         
/* 563 */         int hour = _parse2D(dateStr, 11);
/* 564 */         int minute = _parse2D(dateStr, 14);
/*     */ 
/*     */ 
/*     */         
/* 568 */         if (totalLen > 16 && dateStr.charAt(16) == ':') {
/* 569 */           seconds = _parse2D(dateStr, 17);
/*     */         } else {
/* 571 */           seconds = 0;
/*     */         } 
/* 573 */         cal.set(year, month, day, hour, minute, seconds);
/*     */ 
/*     */         
/* 576 */         start = m.start(1) + 1;
/* 577 */         end = m.end(1);
/* 578 */         int msecs = 0;
/* 579 */         if (start >= end) {
/* 580 */           cal.set(14, 0);
/*     */         } else {
/*     */           
/* 583 */           msecs = 0;
/* 584 */           switch (end - start) {
/*     */             case 3:
/* 586 */               msecs += dateStr.charAt(start + 2) - 48;
/*     */             case 2:
/* 588 */               msecs += 10 * (dateStr.charAt(start + 1) - 48);
/*     */             case 1:
/* 590 */               msecs += 100 * (dateStr.charAt(start) - 48);
/*     */               break;
/*     */             default:
/* 593 */               throw new ParseException(String.format("Cannot parse date \"%s\": invalid fractional seconds '%s'; can use at most 3 digits", new Object[] { dateStr, m.group(1).substring(1) }), pos.getErrorIndex());
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 599 */           cal.set(14, msecs);
/*     */         } 
/* 601 */         return cal.getTime();
/*     */       } 
/* 603 */       formatStr = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
/*     */     } 
/*     */     
/* 606 */     throw new ParseException(String.format("Cannot parse date \"%s\": while it seems to fit format '%s', parsing fails (leniency? %s)", new Object[] { dateStr, formatStr, this._lenient }), pos.getErrorIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int _parse4D(String str, int index) {
/* 613 */     return 1000 * (str.charAt(index) - 48) + 100 * (str.charAt(index + 1) - 48) + 10 * (str.charAt(index + 2) - 48) + str.charAt(index + 3) - 48;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int _parse2D(String str, int index) {
/* 620 */     return 10 * (str.charAt(index) - 48) + str.charAt(index + 1) - 48;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Date parseAsRFC1123(String dateStr, ParsePosition pos) {
/* 626 */     if (this._formatRFC1123 == null) {
/* 627 */       this._formatRFC1123 = _cloneFormat(DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", this._timezone, this._locale, this._lenient);
/*     */     }
/*     */     
/* 630 */     return this._formatRFC1123.parse(dateStr, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final DateFormat _cloneFormat(DateFormat df, String format, TimeZone tz, Locale loc, Boolean lenient) {
/* 642 */     if (!loc.equals(DEFAULT_LOCALE)) {
/* 643 */       df = new SimpleDateFormat(format, loc);
/* 644 */       df.setTimeZone((tz == null) ? DEFAULT_TIMEZONE : tz);
/*     */     } else {
/* 646 */       df = (DateFormat)df.clone();
/* 647 */       if (tz != null) {
/* 648 */         df.setTimeZone(tz);
/*     */       }
/*     */     } 
/* 651 */     if (lenient != null) {
/* 652 */       df.setLenient(lenient.booleanValue());
/*     */     }
/* 654 */     return df;
/*     */   }
/*     */   
/*     */   protected void _clearFormats() {
/* 658 */     this._formatRFC1123 = null;
/*     */   }
/*     */   
/*     */   protected static <T> boolean _equals(T value1, T value2) {
/* 662 */     if (value1 == value2) {
/* 663 */       return true;
/*     */     }
/* 665 */     return (value1 != null && value1.equals(value2));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\StdDateFormat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */