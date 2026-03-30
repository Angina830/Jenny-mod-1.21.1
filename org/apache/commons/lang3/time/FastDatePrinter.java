/*      */ package org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.text.FieldPosition;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FastDatePrinter
/*      */   implements DatePrinter, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   public static final int FULL = 0;
/*      */   public static final int LONG = 1;
/*      */   public static final int MEDIUM = 2;
/*      */   public static final int SHORT = 3;
/*      */   private final String mPattern;
/*      */   private final TimeZone mTimeZone;
/*      */   private final Locale mLocale;
/*      */   private transient Rule[] mRules;
/*      */   private transient int mMaxLengthEstimate;
/*      */   private static final int MAX_DIGITS = 10;
/*      */   
/*      */   protected FastDatePrinter(String pattern, TimeZone timeZone, Locale locale) {
/*  151 */     this.mPattern = pattern;
/*  152 */     this.mTimeZone = timeZone;
/*  153 */     this.mLocale = locale;
/*      */     
/*  155 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/*  162 */     List<Rule> rulesList = parsePattern();
/*  163 */     this.mRules = rulesList.<Rule>toArray(new Rule[0]);
/*      */     
/*  165 */     int len = 0;
/*  166 */     for (int i = this.mRules.length; --i >= 0;) {
/*  167 */       len += this.mRules[i].estimateLength();
/*      */     }
/*      */     
/*  170 */     this.mMaxLengthEstimate = len;
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
/*      */   protected List<Rule> parsePattern() {
/*  182 */     DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
/*  183 */     List<Rule> rules = new ArrayList<>();
/*      */     
/*  185 */     String[] ERAs = symbols.getEras();
/*  186 */     String[] months = symbols.getMonths();
/*  187 */     String[] shortMonths = symbols.getShortMonths();
/*  188 */     String[] weekdays = symbols.getWeekdays();
/*  189 */     String[] shortWeekdays = symbols.getShortWeekdays();
/*  190 */     String[] AmPmStrings = symbols.getAmPmStrings();
/*      */     
/*  192 */     int length = this.mPattern.length();
/*  193 */     int[] indexRef = new int[1];
/*      */     
/*  195 */     for (int i = 0; i < length; i++) {
/*  196 */       Rule rule; String sub; indexRef[0] = i;
/*  197 */       String token = parseToken(this.mPattern, indexRef);
/*  198 */       i = indexRef[0];
/*      */       
/*  200 */       int tokenLen = token.length();
/*  201 */       if (tokenLen == 0) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  206 */       char c = token.charAt(0);
/*      */       
/*  208 */       switch (c) {
/*      */         case 'G':
/*  210 */           rule = new TextField(0, ERAs);
/*      */           break;
/*      */         case 'Y':
/*      */         case 'y':
/*  214 */           if (tokenLen == 2) {
/*  215 */             rule = TwoDigitYearField.INSTANCE;
/*      */           } else {
/*  217 */             rule = selectNumberRule(1, Math.max(tokenLen, 4));
/*      */           } 
/*  219 */           if (c == 'Y') {
/*  220 */             rule = new WeekYear((NumberRule)rule);
/*      */           }
/*      */           break;
/*      */         case 'M':
/*  224 */           if (tokenLen >= 4) {
/*  225 */             rule = new TextField(2, months); break;
/*  226 */           }  if (tokenLen == 3) {
/*  227 */             rule = new TextField(2, shortMonths); break;
/*  228 */           }  if (tokenLen == 2) {
/*  229 */             rule = TwoDigitMonthField.INSTANCE; break;
/*      */           } 
/*  231 */           rule = UnpaddedMonthField.INSTANCE;
/*      */           break;
/*      */         
/*      */         case 'd':
/*  235 */           rule = selectNumberRule(5, tokenLen);
/*      */           break;
/*      */         case 'h':
/*  238 */           rule = new TwelveHourField(selectNumberRule(10, tokenLen));
/*      */           break;
/*      */         case 'H':
/*  241 */           rule = selectNumberRule(11, tokenLen);
/*      */           break;
/*      */         case 'm':
/*  244 */           rule = selectNumberRule(12, tokenLen);
/*      */           break;
/*      */         case 's':
/*  247 */           rule = selectNumberRule(13, tokenLen);
/*      */           break;
/*      */         case 'S':
/*  250 */           rule = selectNumberRule(14, tokenLen);
/*      */           break;
/*      */         case 'E':
/*  253 */           rule = new TextField(7, (tokenLen < 4) ? shortWeekdays : weekdays);
/*      */           break;
/*      */         case 'u':
/*  256 */           rule = new DayInWeekField(selectNumberRule(7, tokenLen));
/*      */           break;
/*      */         case 'D':
/*  259 */           rule = selectNumberRule(6, tokenLen);
/*      */           break;
/*      */         case 'F':
/*  262 */           rule = selectNumberRule(8, tokenLen);
/*      */           break;
/*      */         case 'w':
/*  265 */           rule = selectNumberRule(3, tokenLen);
/*      */           break;
/*      */         case 'W':
/*  268 */           rule = selectNumberRule(4, tokenLen);
/*      */           break;
/*      */         case 'a':
/*  271 */           rule = new TextField(9, AmPmStrings);
/*      */           break;
/*      */         case 'k':
/*  274 */           rule = new TwentyFourHourField(selectNumberRule(11, tokenLen));
/*      */           break;
/*      */         case 'K':
/*  277 */           rule = selectNumberRule(10, tokenLen);
/*      */           break;
/*      */         case 'X':
/*  280 */           rule = Iso8601_Rule.getRule(tokenLen);
/*      */           break;
/*      */         case 'z':
/*  283 */           if (tokenLen >= 4) {
/*  284 */             rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 1); break;
/*      */           } 
/*  286 */           rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 0);
/*      */           break;
/*      */         
/*      */         case 'Z':
/*  290 */           if (tokenLen == 1) {
/*  291 */             rule = TimeZoneNumberRule.INSTANCE_NO_COLON; break;
/*  292 */           }  if (tokenLen == 2) {
/*  293 */             rule = Iso8601_Rule.ISO8601_HOURS_COLON_MINUTES; break;
/*      */           } 
/*  295 */           rule = TimeZoneNumberRule.INSTANCE_COLON;
/*      */           break;
/*      */         
/*      */         case '\'':
/*  299 */           sub = token.substring(1);
/*  300 */           if (sub.length() == 1) {
/*  301 */             rule = new CharacterLiteral(sub.charAt(0)); break;
/*      */           } 
/*  303 */           rule = new StringLiteral(sub);
/*      */           break;
/*      */         
/*      */         default:
/*  307 */           throw new IllegalArgumentException("Illegal pattern component: " + token);
/*      */       } 
/*      */       
/*  310 */       rules.add(rule);
/*      */     } 
/*      */     
/*  313 */     return rules;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String parseToken(String pattern, int[] indexRef) {
/*  324 */     StringBuilder buf = new StringBuilder();
/*      */     
/*  326 */     int i = indexRef[0];
/*  327 */     int length = pattern.length();
/*      */     
/*  329 */     char c = pattern.charAt(i);
/*  330 */     if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
/*      */ 
/*      */       
/*  333 */       buf.append(c);
/*      */       
/*  335 */       while (i + 1 < length) {
/*  336 */         char peek = pattern.charAt(i + 1);
/*  337 */         if (peek == c) {
/*  338 */           buf.append(c);
/*  339 */           i++;
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  346 */       buf.append('\'');
/*      */       
/*  348 */       boolean inLiteral = false;
/*      */       
/*  350 */       for (; i < length; i++) {
/*  351 */         c = pattern.charAt(i);
/*      */         
/*  353 */         if (c == '\'')
/*  354 */         { if (i + 1 < length && pattern.charAt(i + 1) == '\'') {
/*      */             
/*  356 */             i++;
/*  357 */             buf.append(c);
/*      */           } else {
/*  359 */             inLiteral = !inLiteral;
/*      */           }  }
/*  361 */         else { if (!inLiteral && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
/*      */             
/*  363 */             i--;
/*      */             break;
/*      */           } 
/*  366 */           buf.append(c); }
/*      */       
/*      */       } 
/*      */     } 
/*      */     
/*  371 */     indexRef[0] = i;
/*  372 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NumberRule selectNumberRule(int field, int padding) {
/*  383 */     switch (padding) {
/*      */       case 1:
/*  385 */         return new UnpaddedNumberField(field);
/*      */       case 2:
/*  387 */         return new TwoDigitNumberField(field);
/*      */     } 
/*  389 */     return new PaddedNumberField(field, padding);
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
/*      */   @Deprecated
/*      */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
/*  407 */     if (obj instanceof Date)
/*  408 */       return format((Date)obj, toAppendTo); 
/*  409 */     if (obj instanceof Calendar)
/*  410 */       return format((Calendar)obj, toAppendTo); 
/*  411 */     if (obj instanceof Long) {
/*  412 */       return format(((Long)obj).longValue(), toAppendTo);
/*      */     }
/*  414 */     throw new IllegalArgumentException("Unknown class: " + ((obj == null) ? "<null>" : obj
/*  415 */         .getClass().getName()));
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
/*      */   String format(Object obj) {
/*  427 */     if (obj instanceof Date)
/*  428 */       return format((Date)obj); 
/*  429 */     if (obj instanceof Calendar)
/*  430 */       return format((Calendar)obj); 
/*  431 */     if (obj instanceof Long) {
/*  432 */       return format(((Long)obj).longValue());
/*      */     }
/*  434 */     throw new IllegalArgumentException("Unknown class: " + ((obj == null) ? "<null>" : obj
/*  435 */         .getClass().getName()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(long millis) {
/*  444 */     Calendar c = newCalendar();
/*  445 */     c.setTimeInMillis(millis);
/*  446 */     return applyRulesToString(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String applyRulesToString(Calendar c) {
/*  455 */     return ((StringBuilder)applyRules(c, new StringBuilder(this.mMaxLengthEstimate))).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Calendar newCalendar() {
/*  463 */     return Calendar.getInstance(this.mTimeZone, this.mLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(Date date) {
/*  471 */     Calendar c = newCalendar();
/*  472 */     c.setTime(date);
/*  473 */     return applyRulesToString(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(Calendar calendar) {
/*  481 */     return ((StringBuilder)format(calendar, new StringBuilder(this.mMaxLengthEstimate))).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(long millis, StringBuffer buf) {
/*  489 */     Calendar c = newCalendar();
/*  490 */     c.setTimeInMillis(millis);
/*  491 */     return applyRules(c, buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(Date date, StringBuffer buf) {
/*  499 */     Calendar c = newCalendar();
/*  500 */     c.setTime(date);
/*  501 */     return applyRules(c, buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(Calendar calendar, StringBuffer buf) {
/*  510 */     return format(calendar.getTime(), buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <B extends Appendable> B format(long millis, B buf) {
/*  518 */     Calendar c = newCalendar();
/*  519 */     c.setTimeInMillis(millis);
/*  520 */     return applyRules(c, buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <B extends Appendable> B format(Date date, B buf) {
/*  528 */     Calendar c = newCalendar();
/*  529 */     c.setTime(date);
/*  530 */     return applyRules(c, buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <B extends Appendable> B format(Calendar calendar, B buf) {
/*  539 */     if (!calendar.getTimeZone().equals(this.mTimeZone)) {
/*  540 */       calendar = (Calendar)calendar.clone();
/*  541 */       calendar.setTimeZone(this.mTimeZone);
/*      */     } 
/*  543 */     return applyRules(calendar, buf);
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
/*      */   @Deprecated
/*      */   protected StringBuffer applyRules(Calendar calendar, StringBuffer buf) {
/*  558 */     return applyRules(calendar, buf);
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
/*      */   private <B extends Appendable> B applyRules(Calendar calendar, B buf) {
/*      */     try {
/*  572 */       for (Rule rule : this.mRules) {
/*  573 */         rule.appendTo((Appendable)buf, calendar);
/*      */       }
/*  575 */     } catch (IOException ioe) {
/*  576 */       ExceptionUtils.rethrow(ioe);
/*      */     } 
/*  578 */     return buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPattern() {
/*  588 */     return this.mPattern;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeZone getTimeZone() {
/*  596 */     return this.mTimeZone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  604 */     return this.mLocale;
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
/*      */   public int getMaxLengthEstimate() {
/*  617 */     return this.mMaxLengthEstimate;
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
/*      */   public boolean equals(Object obj) {
/*  630 */     if (!(obj instanceof FastDatePrinter)) {
/*  631 */       return false;
/*      */     }
/*  633 */     FastDatePrinter other = (FastDatePrinter)obj;
/*  634 */     return (this.mPattern.equals(other.mPattern) && this.mTimeZone
/*  635 */       .equals(other.mTimeZone) && this.mLocale
/*  636 */       .equals(other.mLocale));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  646 */     return this.mPattern.hashCode() + 13 * (this.mTimeZone.hashCode() + 13 * this.mLocale.hashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  656 */     return "FastDatePrinter[" + this.mPattern + "," + this.mLocale + "," + this.mTimeZone.getID() + "]";
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
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*  670 */     in.defaultReadObject();
/*  671 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void appendDigits(Appendable buffer, int value) throws IOException {
/*  681 */     buffer.append((char)(value / 10 + 48));
/*  682 */     buffer.append((char)(value % 10 + 48));
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
/*      */   private static void appendFullDigits(Appendable buffer, int value, int minFieldWidth) throws IOException {
/*  696 */     if (value < 10000) {
/*      */ 
/*      */       
/*  699 */       int nDigits = 4;
/*  700 */       if (value < 1000) {
/*  701 */         nDigits--;
/*  702 */         if (value < 100) {
/*  703 */           nDigits--;
/*  704 */           if (value < 10) {
/*  705 */             nDigits--;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  710 */       for (int i = minFieldWidth - nDigits; i > 0; i--) {
/*  711 */         buffer.append('0');
/*      */       }
/*      */       
/*  714 */       switch (nDigits) {
/*      */         case 4:
/*  716 */           buffer.append((char)(value / 1000 + 48));
/*  717 */           value %= 1000;
/*      */         case 3:
/*  719 */           if (value >= 100) {
/*  720 */             buffer.append((char)(value / 100 + 48));
/*  721 */             value %= 100;
/*      */           } else {
/*  723 */             buffer.append('0');
/*      */           } 
/*      */         case 2:
/*  726 */           if (value >= 10) {
/*  727 */             buffer.append((char)(value / 10 + 48));
/*  728 */             value %= 10;
/*      */           } else {
/*  730 */             buffer.append('0');
/*      */           } 
/*      */         case 1:
/*  733 */           buffer.append((char)(value + 48));
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*      */     } else {
/*  739 */       char[] work = new char[10];
/*  740 */       int digit = 0;
/*  741 */       while (value != 0) {
/*  742 */         work[digit++] = (char)(value % 10 + 48);
/*  743 */         value /= 10;
/*      */       } 
/*      */ 
/*      */       
/*  747 */       while (digit < minFieldWidth) {
/*  748 */         buffer.append('0');
/*  749 */         minFieldWidth--;
/*      */       } 
/*      */ 
/*      */       
/*  753 */       while (--digit >= 0) {
/*  754 */         buffer.append(work[digit]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface Rule
/*      */   {
/*      */     int estimateLength();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void appendTo(Appendable param1Appendable, Calendar param1Calendar) throws IOException;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface NumberRule
/*      */     extends Rule
/*      */   {
/*      */     void appendTo(Appendable param1Appendable, int param1Int) throws IOException;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CharacterLiteral
/*      */     implements Rule
/*      */   {
/*      */     private final char mValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CharacterLiteral(char value) {
/*  809 */       this.mValue = value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  817 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/*  825 */       buffer.append(this.mValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class StringLiteral
/*      */     implements Rule
/*      */   {
/*      */     private final String mValue;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StringLiteral(String value) {
/*  842 */       this.mValue = value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  850 */       return this.mValue.length();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/*  858 */       buffer.append(this.mValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TextField
/*      */     implements Rule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */ 
/*      */     
/*      */     private final String[] mValues;
/*      */ 
/*      */ 
/*      */     
/*      */     TextField(int field, String[] values) {
/*  877 */       this.mField = field;
/*  878 */       this.mValues = values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  886 */       int max = 0;
/*  887 */       for (int i = this.mValues.length; --i >= 0; ) {
/*  888 */         int len = this.mValues[i].length();
/*  889 */         if (len > max) {
/*  890 */           max = len;
/*      */         }
/*      */       } 
/*  893 */       return max;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/*  901 */       buffer.append(this.mValues[calendar.get(this.mField)]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class UnpaddedNumberField
/*      */     implements NumberRule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     UnpaddedNumberField(int field) {
/*  917 */       this.mField = field;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  925 */       return 4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/*  933 */       appendTo(buffer, calendar.get(this.mField));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(Appendable buffer, int value) throws IOException {
/*  941 */       if (value < 10) {
/*  942 */         buffer.append((char)(value + 48));
/*  943 */       } else if (value < 100) {
/*  944 */         FastDatePrinter.appendDigits(buffer, value);
/*      */       } else {
/*  946 */         FastDatePrinter.appendFullDigits(buffer, value, 1);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class UnpaddedMonthField
/*      */     implements NumberRule
/*      */   {
/*  955 */     static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  970 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/*  978 */       appendTo(buffer, calendar.get(2) + 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(Appendable buffer, int value) throws IOException {
/*  986 */       if (value < 10) {
/*  987 */         buffer.append((char)(value + 48));
/*      */       } else {
/*  989 */         FastDatePrinter.appendDigits(buffer, value);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PaddedNumberField
/*      */     implements NumberRule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */     
/*      */     private final int mSize;
/*      */ 
/*      */ 
/*      */     
/*      */     PaddedNumberField(int field, int size) {
/* 1008 */       if (size < 3)
/*      */       {
/* 1010 */         throw new IllegalArgumentException();
/*      */       }
/* 1012 */       this.mField = field;
/* 1013 */       this.mSize = size;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1021 */       return this.mSize;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1029 */       appendTo(buffer, calendar.get(this.mField));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(Appendable buffer, int value) throws IOException {
/* 1037 */       FastDatePrinter.appendFullDigits(buffer, value, this.mSize);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TwoDigitNumberField
/*      */     implements NumberRule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TwoDigitNumberField(int field) {
/* 1053 */       this.mField = field;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1061 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1069 */       appendTo(buffer, calendar.get(this.mField));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(Appendable buffer, int value) throws IOException {
/* 1077 */       if (value < 100) {
/* 1078 */         FastDatePrinter.appendDigits(buffer, value);
/*      */       } else {
/* 1080 */         FastDatePrinter.appendFullDigits(buffer, value, 2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TwoDigitYearField
/*      */     implements NumberRule
/*      */   {
/* 1089 */     static final TwoDigitYearField INSTANCE = new TwoDigitYearField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1103 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1111 */       appendTo(buffer, calendar.get(1) % 100);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(Appendable buffer, int value) throws IOException {
/* 1119 */       FastDatePrinter.appendDigits(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TwoDigitMonthField
/*      */     implements NumberRule
/*      */   {
/* 1127 */     static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1141 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1149 */       appendTo(buffer, calendar.get(2) + 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(Appendable buffer, int value) throws IOException {
/* 1157 */       FastDatePrinter.appendDigits(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TwelveHourField
/*      */     implements NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TwelveHourField(FastDatePrinter.NumberRule rule) {
/* 1174 */       this.mRule = rule;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1182 */       return this.mRule.estimateLength();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1190 */       int value = calendar.get(10);
/* 1191 */       if (value == 0) {
/* 1192 */         value = calendar.getLeastMaximum(10) + 1;
/*      */       }
/* 1194 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, int value) throws IOException {
/* 1202 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TwentyFourHourField
/*      */     implements NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TwentyFourHourField(FastDatePrinter.NumberRule rule) {
/* 1219 */       this.mRule = rule;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1227 */       return this.mRule.estimateLength();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1235 */       int value = calendar.get(11);
/* 1236 */       if (value == 0) {
/* 1237 */         value = calendar.getMaximum(11) + 1;
/*      */       }
/* 1239 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, int value) throws IOException {
/* 1247 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DayInWeekField
/*      */     implements NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */     
/*      */     DayInWeekField(FastDatePrinter.NumberRule rule) {
/* 1258 */       this.mRule = rule;
/*      */     }
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1263 */       return this.mRule.estimateLength();
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1268 */       int value = calendar.get(7);
/* 1269 */       this.mRule.appendTo(buffer, (value == 1) ? 7 : (value - 1));
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, int value) throws IOException {
/* 1274 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class WeekYear
/*      */     implements NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */     
/*      */     WeekYear(FastDatePrinter.NumberRule rule) {
/* 1285 */       this.mRule = rule;
/*      */     }
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1290 */       return this.mRule.estimateLength();
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1295 */       this.mRule.appendTo(buffer, calendar.getWeekYear());
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, int value) throws IOException {
/* 1300 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1306 */   private static final ConcurrentMap<TimeZoneDisplayKey, String> cTimeZoneDisplayCache = new ConcurrentHashMap<>(7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale) {
/* 1318 */     TimeZoneDisplayKey key = new TimeZoneDisplayKey(tz, daylight, style, locale);
/* 1319 */     String value = cTimeZoneDisplayCache.get(key);
/* 1320 */     if (value == null) {
/*      */       
/* 1322 */       value = tz.getDisplayName(daylight, style, locale);
/* 1323 */       String prior = cTimeZoneDisplayCache.putIfAbsent(key, value);
/* 1324 */       if (prior != null) {
/* 1325 */         value = prior;
/*      */       }
/*      */     } 
/* 1328 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TimeZoneNameRule
/*      */     implements Rule
/*      */   {
/*      */     private final Locale mLocale;
/*      */ 
/*      */     
/*      */     private final int mStyle;
/*      */ 
/*      */     
/*      */     private final String mStandard;
/*      */     
/*      */     private final String mDaylight;
/*      */ 
/*      */     
/*      */     TimeZoneNameRule(TimeZone timeZone, Locale locale, int style) {
/* 1348 */       this.mLocale = locale;
/* 1349 */       this.mStyle = style;
/*      */       
/* 1351 */       this.mStandard = FastDatePrinter.getTimeZoneDisplay(timeZone, false, style, locale);
/* 1352 */       this.mDaylight = FastDatePrinter.getTimeZoneDisplay(timeZone, true, style, locale);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1363 */       return Math.max(this.mStandard.length(), this.mDaylight.length());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1371 */       TimeZone zone = calendar.getTimeZone();
/* 1372 */       if (calendar.get(16) == 0) {
/* 1373 */         buffer.append(FastDatePrinter.getTimeZoneDisplay(zone, false, this.mStyle, this.mLocale));
/*      */       } else {
/* 1375 */         buffer.append(FastDatePrinter.getTimeZoneDisplay(zone, true, this.mStyle, this.mLocale));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TimeZoneNumberRule
/*      */     implements Rule
/*      */   {
/* 1385 */     static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
/* 1386 */     static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean mColon;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TimeZoneNumberRule(boolean colon) {
/* 1396 */       this.mColon = colon;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1404 */       return 5;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1413 */       int offset = calendar.get(15) + calendar.get(16);
/*      */       
/* 1415 */       if (offset < 0) {
/* 1416 */         buffer.append('-');
/* 1417 */         offset = -offset;
/*      */       } else {
/* 1419 */         buffer.append('+');
/*      */       } 
/*      */       
/* 1422 */       int hours = offset / 3600000;
/* 1423 */       FastDatePrinter.appendDigits(buffer, hours);
/*      */       
/* 1425 */       if (this.mColon) {
/* 1426 */         buffer.append(':');
/*      */       }
/*      */       
/* 1429 */       int minutes = offset / 60000 - 60 * hours;
/* 1430 */       FastDatePrinter.appendDigits(buffer, minutes);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Iso8601_Rule
/*      */     implements Rule
/*      */   {
/* 1441 */     static final Iso8601_Rule ISO8601_HOURS = new Iso8601_Rule(3);
/*      */     
/* 1443 */     static final Iso8601_Rule ISO8601_HOURS_MINUTES = new Iso8601_Rule(5);
/*      */     
/* 1445 */     static final Iso8601_Rule ISO8601_HOURS_COLON_MINUTES = new Iso8601_Rule(6);
/*      */ 
/*      */ 
/*      */     
/*      */     final int length;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Iso8601_Rule getRule(int tokenLen) {
/* 1455 */       switch (tokenLen) {
/*      */         case 1:
/* 1457 */           return ISO8601_HOURS;
/*      */         case 2:
/* 1459 */           return ISO8601_HOURS_MINUTES;
/*      */         case 3:
/* 1461 */           return ISO8601_HOURS_COLON_MINUTES;
/*      */       } 
/* 1463 */       throw new IllegalArgumentException("invalid number of X");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Iso8601_Rule(int length) {
/* 1475 */       this.length = length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1483 */       return this.length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
/* 1491 */       int offset = calendar.get(15) + calendar.get(16);
/* 1492 */       if (offset == 0) {
/* 1493 */         buffer.append("Z");
/*      */         
/*      */         return;
/*      */       } 
/* 1497 */       if (offset < 0) {
/* 1498 */         buffer.append('-');
/* 1499 */         offset = -offset;
/*      */       } else {
/* 1501 */         buffer.append('+');
/*      */       } 
/*      */       
/* 1504 */       int hours = offset / 3600000;
/* 1505 */       FastDatePrinter.appendDigits(buffer, hours);
/*      */       
/* 1507 */       if (this.length < 5) {
/*      */         return;
/*      */       }
/*      */       
/* 1511 */       if (this.length == 6) {
/* 1512 */         buffer.append(':');
/*      */       }
/*      */       
/* 1515 */       int minutes = offset / 60000 - 60 * hours;
/* 1516 */       FastDatePrinter.appendDigits(buffer, minutes);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TimeZoneDisplayKey
/*      */   {
/*      */     private final TimeZone mTimeZone;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int mStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Locale mLocale;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TimeZoneDisplayKey(TimeZone timeZone, boolean daylight, int style, Locale locale) {
/* 1539 */       this.mTimeZone = timeZone;
/* 1540 */       if (daylight) {
/* 1541 */         this.mStyle = style | Integer.MIN_VALUE;
/*      */       } else {
/* 1543 */         this.mStyle = style;
/*      */       } 
/* 1545 */       this.mLocale = locale;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1553 */       return (this.mStyle * 31 + this.mLocale.hashCode()) * 31 + this.mTimeZone.hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/* 1561 */       if (this == obj) {
/* 1562 */         return true;
/*      */       }
/* 1564 */       if (obj instanceof TimeZoneDisplayKey) {
/* 1565 */         TimeZoneDisplayKey other = (TimeZoneDisplayKey)obj;
/* 1566 */         return (this.mTimeZone
/* 1567 */           .equals(other.mTimeZone) && this.mStyle == other.mStyle && this.mLocale
/*      */           
/* 1569 */           .equals(other.mLocale));
/*      */       } 
/* 1571 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\FastDatePrinter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */