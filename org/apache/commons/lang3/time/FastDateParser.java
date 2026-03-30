/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastDateParser
/*     */   implements DateParser, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3L;
/*  82 */   static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
/*     */ 
/*     */   
/*     */   private final String pattern;
/*     */   
/*     */   private final TimeZone timeZone;
/*     */   
/*     */   private final Locale locale;
/*     */   
/*     */   private final int century;
/*     */   
/*     */   private final int startYear;
/*     */   
/*     */   private transient List<StrategyAndWidth> patterns;
/*     */   
/*  97 */   private static final Comparator<String> LONGER_FIRST_LOWERCASE = Comparator.reverseOrder();
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
/*     */   protected FastDateParser(String pattern, TimeZone timeZone, Locale locale) {
/* 111 */     this(pattern, timeZone, locale, null);
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
/*     */   protected FastDateParser(String pattern, TimeZone timeZone, Locale locale, Date centuryStart) {
/*     */     int centuryStartYear;
/* 126 */     this.pattern = pattern;
/* 127 */     this.timeZone = timeZone;
/* 128 */     this.locale = locale;
/*     */     
/* 130 */     Calendar definingCalendar = Calendar.getInstance(timeZone, locale);
/*     */ 
/*     */     
/* 133 */     if (centuryStart != null) {
/* 134 */       definingCalendar.setTime(centuryStart);
/* 135 */       centuryStartYear = definingCalendar.get(1);
/* 136 */     } else if (locale.equals(JAPANESE_IMPERIAL)) {
/* 137 */       centuryStartYear = 0;
/*     */     } else {
/*     */       
/* 140 */       definingCalendar.setTime(new Date());
/* 141 */       centuryStartYear = definingCalendar.get(1) - 80;
/*     */     } 
/* 143 */     this.century = centuryStartYear / 100 * 100;
/* 144 */     this.startYear = centuryStartYear - this.century;
/*     */     
/* 146 */     init(definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(Calendar definingCalendar) {
/* 156 */     this.patterns = new ArrayList<>();
/*     */     
/* 158 */     StrategyParser fm = new StrategyParser(definingCalendar);
/*     */     while (true) {
/* 160 */       StrategyAndWidth field = fm.getNextStrategy();
/* 161 */       if (field == null) {
/*     */         break;
/*     */       }
/* 164 */       this.patterns.add(field);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StrategyAndWidth
/*     */   {
/*     */     final FastDateParser.Strategy strategy;
/*     */ 
/*     */     
/*     */     final int width;
/*     */ 
/*     */     
/*     */     StrategyAndWidth(FastDateParser.Strategy strategy, int width) {
/* 179 */       this.strategy = strategy;
/* 180 */       this.width = width;
/*     */     }
/*     */     
/*     */     int getMaxWidth(ListIterator<StrategyAndWidth> lt) {
/* 184 */       if (!this.strategy.isNumber() || !lt.hasNext()) {
/* 185 */         return 0;
/*     */       }
/* 187 */       FastDateParser.Strategy nextStrategy = ((StrategyAndWidth)lt.next()).strategy;
/* 188 */       lt.previous();
/* 189 */       return nextStrategy.isNumber() ? this.width : 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class StrategyParser
/*     */   {
/*     */     private final Calendar definingCalendar;
/*     */     
/*     */     private int currentIdx;
/*     */     
/*     */     StrategyParser(Calendar definingCalendar) {
/* 201 */       this.definingCalendar = definingCalendar;
/*     */     }
/*     */     
/*     */     FastDateParser.StrategyAndWidth getNextStrategy() {
/* 205 */       if (this.currentIdx >= FastDateParser.this.pattern.length()) {
/* 206 */         return null;
/*     */       }
/*     */       
/* 209 */       char c = FastDateParser.this.pattern.charAt(this.currentIdx);
/* 210 */       if (FastDateParser.isFormatLetter(c)) {
/* 211 */         return letterPattern(c);
/*     */       }
/* 213 */       return literal();
/*     */     }
/*     */     
/*     */     private FastDateParser.StrategyAndWidth letterPattern(char c) {
/* 217 */       int begin = this.currentIdx; do {  }
/* 218 */       while (++this.currentIdx < FastDateParser.this.pattern.length() && 
/* 219 */         FastDateParser.this.pattern.charAt(this.currentIdx) == c);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       int width = this.currentIdx - begin;
/* 225 */       return new FastDateParser.StrategyAndWidth(FastDateParser.this.getStrategy(c, width, this.definingCalendar), width);
/*     */     }
/*     */     
/*     */     private FastDateParser.StrategyAndWidth literal() {
/* 229 */       boolean activeQuote = false;
/*     */       
/* 231 */       StringBuilder sb = new StringBuilder();
/* 232 */       while (this.currentIdx < FastDateParser.this.pattern.length()) {
/* 233 */         char c = FastDateParser.this.pattern.charAt(this.currentIdx);
/* 234 */         if (!activeQuote && FastDateParser.isFormatLetter(c))
/*     */           break; 
/* 236 */         if (c == '\'' && (++this.currentIdx == FastDateParser.this.pattern.length() || FastDateParser.this.pattern.charAt(this.currentIdx) != '\'')) {
/* 237 */           activeQuote = !activeQuote;
/*     */           continue;
/*     */         } 
/* 240 */         this.currentIdx++;
/* 241 */         sb.append(c);
/*     */       } 
/*     */       
/* 244 */       if (activeQuote) {
/* 245 */         throw new IllegalArgumentException("Unterminated quote");
/*     */       }
/*     */       
/* 248 */       String formatField = sb.toString();
/* 249 */       return new FastDateParser.StrategyAndWidth(new FastDateParser.CopyQuotedStrategy(formatField), formatField.length());
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isFormatLetter(char c) {
/* 254 */     return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/* 264 */     return this.pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 272 */     return this.timeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 280 */     return this.locale;
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
/*     */   public boolean equals(Object obj) {
/* 294 */     if (!(obj instanceof FastDateParser)) {
/* 295 */       return false;
/*     */     }
/* 297 */     FastDateParser other = (FastDateParser)obj;
/* 298 */     return (this.pattern.equals(other.pattern) && this.timeZone
/* 299 */       .equals(other.timeZone) && this.locale
/* 300 */       .equals(other.locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 310 */     return this.pattern.hashCode() + 13 * (this.timeZone.hashCode() + 13 * this.locale.hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 320 */     return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
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
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 334 */     in.defaultReadObject();
/*     */     
/* 336 */     Calendar definingCalendar = Calendar.getInstance(this.timeZone, this.locale);
/* 337 */     init(definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String source) throws ParseException {
/* 345 */     return parse(source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String source) throws ParseException {
/* 353 */     ParsePosition pp = new ParsePosition(0);
/* 354 */     Date date = parse(source, pp);
/* 355 */     if (date == null) {
/*     */       
/* 357 */       if (this.locale.equals(JAPANESE_IMPERIAL)) {
/* 358 */         throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\nUnparseable date: \"" + source, pp
/*     */             
/* 360 */             .getErrorIndex());
/*     */       }
/* 362 */       throw new ParseException("Unparseable date: " + source, pp.getErrorIndex());
/*     */     } 
/* 364 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String source, ParsePosition pos) {
/* 372 */     return parse(source, pos);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String source, ParsePosition pos) {
/* 390 */     Calendar cal = Calendar.getInstance(this.timeZone, this.locale);
/* 391 */     cal.clear();
/*     */     
/* 393 */     return parse(source, pos, cal) ? cal.getTime() : null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean parse(String source, ParsePosition pos, Calendar calendar) {
/* 411 */     ListIterator<StrategyAndWidth> lt = this.patterns.listIterator();
/* 412 */     while (lt.hasNext()) {
/* 413 */       StrategyAndWidth strategyAndWidth = lt.next();
/* 414 */       int maxWidth = strategyAndWidth.getMaxWidth(lt);
/* 415 */       if (!strategyAndWidth.strategy.parse(this, calendar, source, pos, maxWidth)) {
/* 416 */         return false;
/*     */       }
/*     */     } 
/* 419 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static StringBuilder simpleQuote(StringBuilder sb, String value) {
/* 426 */     for (int i = 0; i < value.length(); i++) {
/* 427 */       char c = value.charAt(i);
/* 428 */       switch (c) {
/*     */         case '$':
/*     */         case '(':
/*     */         case ')':
/*     */         case '*':
/*     */         case '+':
/*     */         case '.':
/*     */         case '?':
/*     */         case '[':
/*     */         case '\\':
/*     */         case '^':
/*     */         case '{':
/*     */         case '|':
/* 441 */           sb.append('\\'); break;
/*     */       } 
/* 443 */       sb.append(c);
/*     */     } 
/*     */     
/* 446 */     if (sb.charAt(sb.length() - 1) == '.')
/*     */     {
/* 448 */       sb.append('?');
/*     */     }
/* 450 */     return sb;
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
/*     */   private static Map<String, Integer> appendDisplayNames(Calendar cal, Locale locale, int field, StringBuilder regex) {
/* 462 */     Map<String, Integer> values = new HashMap<>();
/*     */     
/* 464 */     Map<String, Integer> displayNames = cal.getDisplayNames(field, 0, locale);
/* 465 */     TreeSet<String> sorted = new TreeSet<>(LONGER_FIRST_LOWERCASE);
/* 466 */     for (Map.Entry<String, Integer> displayName : displayNames.entrySet()) {
/* 467 */       String key = ((String)displayName.getKey()).toLowerCase(locale);
/* 468 */       if (sorted.add(key)) {
/* 469 */         values.put(key, displayName.getValue());
/*     */       }
/*     */     } 
/* 472 */     for (String symbol : sorted) {
/* 473 */       simpleQuote(regex, symbol).append('|');
/*     */     }
/* 475 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int adjustYear(int twoDigitYear) {
/* 484 */     int trial = this.century + twoDigitYear;
/* 485 */     return (twoDigitYear >= this.startYear) ? trial : (trial + 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class Strategy
/*     */   {
/*     */     private Strategy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 499 */       return false;
/*     */     }
/*     */     
/*     */     abstract boolean parse(FastDateParser param1FastDateParser, Calendar param1Calendar, String param1String, ParsePosition param1ParsePosition, int param1Int);
/*     */   }
/*     */   
/*     */   private static abstract class PatternStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private Pattern pattern;
/*     */     
/*     */     private PatternStrategy() {}
/*     */     
/*     */     void createPattern(StringBuilder regex) {
/* 513 */       createPattern(regex.toString());
/*     */     }
/*     */     
/*     */     void createPattern(String regex) {
/* 517 */       this.pattern = Pattern.compile(regex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 528 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean parse(FastDateParser parser, Calendar calendar, String source, ParsePosition pos, int maxWidth) {
/* 533 */       Matcher matcher = this.pattern.matcher(source.substring(pos.getIndex()));
/* 534 */       if (!matcher.lookingAt()) {
/* 535 */         pos.setErrorIndex(pos.getIndex());
/* 536 */         return false;
/*     */       } 
/* 538 */       pos.setIndex(pos.getIndex() + matcher.end(1));
/* 539 */       setCalendar(parser, calendar, matcher.group(1));
/* 540 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void setCalendar(FastDateParser param1FastDateParser, Calendar param1Calendar, String param1String);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Strategy getStrategy(char f, int width, Calendar definingCalendar) {
/* 553 */     switch (f) {
/*     */       default:
/* 555 */         throw new IllegalArgumentException("Format '" + f + "' not supported");
/*     */       case 'D':
/* 557 */         return DAY_OF_YEAR_STRATEGY;
/*     */       case 'E':
/* 559 */         return getLocaleSpecificStrategy(7, definingCalendar);
/*     */       case 'F':
/* 561 */         return DAY_OF_WEEK_IN_MONTH_STRATEGY;
/*     */       case 'G':
/* 563 */         return getLocaleSpecificStrategy(0, definingCalendar);
/*     */       case 'H':
/* 565 */         return HOUR_OF_DAY_STRATEGY;
/*     */       case 'K':
/* 567 */         return HOUR_STRATEGY;
/*     */       case 'M':
/* 569 */         return (width >= 3) ? getLocaleSpecificStrategy(2, definingCalendar) : NUMBER_MONTH_STRATEGY;
/*     */       case 'S':
/* 571 */         return MILLISECOND_STRATEGY;
/*     */       case 'W':
/* 573 */         return WEEK_OF_MONTH_STRATEGY;
/*     */       case 'a':
/* 575 */         return getLocaleSpecificStrategy(9, definingCalendar);
/*     */       case 'd':
/* 577 */         return DAY_OF_MONTH_STRATEGY;
/*     */       case 'h':
/* 579 */         return HOUR12_STRATEGY;
/*     */       case 'k':
/* 581 */         return HOUR24_OF_DAY_STRATEGY;
/*     */       case 'm':
/* 583 */         return MINUTE_STRATEGY;
/*     */       case 's':
/* 585 */         return SECOND_STRATEGY;
/*     */       case 'u':
/* 587 */         return DAY_OF_WEEK_STRATEGY;
/*     */       case 'w':
/* 589 */         return WEEK_OF_YEAR_STRATEGY;
/*     */       case 'Y':
/*     */       case 'y':
/* 592 */         return (width > 2) ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
/*     */       case 'X':
/* 594 */         return ISO8601TimeZoneStrategy.getStrategy(width);
/*     */       case 'Z':
/* 596 */         if (width == 2)
/* 597 */           return ISO8601TimeZoneStrategy.ISO_8601_3_STRATEGY;  break;
/*     */       case 'z':
/*     */         break;
/*     */     } 
/* 601 */     return getLocaleSpecificStrategy(15, definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 606 */   private static final ConcurrentMap<Locale, Strategy>[] caches = (ConcurrentMap<Locale, Strategy>[])new ConcurrentMap[17];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ConcurrentMap<Locale, Strategy> getCache(int field) {
/* 614 */     synchronized (caches) {
/* 615 */       if (caches[field] == null) {
/* 616 */         caches[field] = new ConcurrentHashMap<>(3);
/*     */       }
/* 618 */       return caches[field];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Strategy getLocaleSpecificStrategy(int field, Calendar definingCalendar) {
/* 629 */     ConcurrentMap<Locale, Strategy> cache = getCache(field);
/* 630 */     Strategy strategy = cache.get(this.locale);
/* 631 */     if (strategy == null) {
/* 632 */       strategy = (field == 15) ? new TimeZoneStrategy(this.locale) : new CaseInsensitiveTextStrategy(field, definingCalendar, this.locale);
/*     */ 
/*     */       
/* 635 */       Strategy inCache = cache.putIfAbsent(this.locale, strategy);
/* 636 */       if (inCache != null) {
/* 637 */         return inCache;
/*     */       }
/*     */     } 
/* 640 */     return strategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CopyQuotedStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final String formatField;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CopyQuotedStrategy(String formatField) {
/* 655 */       this.formatField = formatField;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 663 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean parse(FastDateParser parser, Calendar calendar, String source, ParsePosition pos, int maxWidth) {
/* 668 */       for (int idx = 0; idx < this.formatField.length(); idx++) {
/* 669 */         int sIdx = idx + pos.getIndex();
/* 670 */         if (sIdx == source.length()) {
/* 671 */           pos.setErrorIndex(sIdx);
/* 672 */           return false;
/*     */         } 
/* 674 */         if (this.formatField.charAt(idx) != source.charAt(sIdx)) {
/* 675 */           pos.setErrorIndex(sIdx);
/* 676 */           return false;
/*     */         } 
/*     */       } 
/* 679 */       pos.setIndex(this.formatField.length() + pos.getIndex());
/* 680 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CaseInsensitiveTextStrategy
/*     */     extends PatternStrategy
/*     */   {
/*     */     private final int field;
/*     */ 
/*     */     
/*     */     final Locale locale;
/*     */ 
/*     */     
/*     */     private final Map<String, Integer> lKeyValues;
/*     */ 
/*     */     
/*     */     CaseInsensitiveTextStrategy(int field, Calendar definingCalendar, Locale locale) {
/* 699 */       this.field = field;
/* 700 */       this.locale = locale;
/*     */       
/* 702 */       StringBuilder regex = new StringBuilder();
/* 703 */       regex.append("((?iu)");
/* 704 */       this.lKeyValues = FastDateParser.appendDisplayNames(definingCalendar, locale, field, regex);
/* 705 */       regex.setLength(regex.length() - 1);
/* 706 */       regex.append(")");
/* 707 */       createPattern(regex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/* 715 */       String lowerCase = value.toLowerCase(this.locale);
/* 716 */       Integer iVal = this.lKeyValues.get(lowerCase);
/* 717 */       if (iVal == null)
/*     */       {
/* 719 */         iVal = this.lKeyValues.get(lowerCase + '.');
/*     */       }
/* 721 */       cal.set(this.field, iVal.intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class NumberStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final int field;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NumberStrategy(int field) {
/* 737 */       this.field = field;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 745 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean parse(FastDateParser parser, Calendar calendar, String source, ParsePosition pos, int maxWidth) {
/* 750 */       int idx = pos.getIndex();
/* 751 */       int last = source.length();
/*     */       
/* 753 */       if (maxWidth == 0) {
/*     */         
/* 755 */         for (; idx < last; idx++) {
/* 756 */           char c = source.charAt(idx);
/* 757 */           if (!Character.isWhitespace(c)) {
/*     */             break;
/*     */           }
/*     */         } 
/* 761 */         pos.setIndex(idx);
/*     */       } else {
/* 763 */         int end = idx + maxWidth;
/* 764 */         if (last > end) {
/* 765 */           last = end;
/*     */         }
/*     */       } 
/*     */       
/* 769 */       for (; idx < last; idx++) {
/* 770 */         char c = source.charAt(idx);
/* 771 */         if (!Character.isDigit(c)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 776 */       if (pos.getIndex() == idx) {
/* 777 */         pos.setErrorIndex(idx);
/* 778 */         return false;
/*     */       } 
/*     */       
/* 781 */       int value = Integer.parseInt(source.substring(pos.getIndex(), idx));
/* 782 */       pos.setIndex(idx);
/*     */       
/* 784 */       calendar.set(this.field, modify(parser, value));
/* 785 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int modify(FastDateParser parser, int iValue) {
/* 795 */       return iValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 800 */   private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1)
/*     */     {
/*     */ 
/*     */       
/*     */       int modify(FastDateParser parser, int iValue)
/*     */       {
/* 806 */         return (iValue < 100) ? parser.adjustYear(iValue) : iValue;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   static class TimeZoneStrategy
/*     */     extends PatternStrategy
/*     */   {
/*     */     private static final String RFC_822_TIME_ZONE = "[+-]\\d{4}";
/*     */     
/*     */     private static final String GMT_OPTION = "GMT[+-]\\d{1,2}:\\d{2}";
/*     */     private final Locale locale;
/* 818 */     private final Map<String, TzInfo> tzNames = new HashMap<>();
/*     */     private static final int ID = 0;
/*     */     
/*     */     private static class TzInfo { TimeZone zone;
/*     */       int dstOffset;
/*     */       
/*     */       TzInfo(TimeZone tz, boolean useDst) {
/* 825 */         this.zone = tz;
/* 826 */         this.dstOffset = useDst ? tz.getDSTSavings() : 0;
/*     */       } }
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
/*     */     TimeZoneStrategy(Locale locale) {
/* 840 */       this.locale = locale;
/*     */       
/* 842 */       StringBuilder sb = new StringBuilder();
/* 843 */       sb.append("((?iu)[+-]\\d{4}|GMT[+-]\\d{1,2}:\\d{2}");
/*     */       
/* 845 */       Set<String> sorted = new TreeSet<>(FastDateParser.LONGER_FIRST_LOWERCASE);
/*     */       
/* 847 */       String[][] zones = DateFormatSymbols.getInstance(locale).getZoneStrings();
/* 848 */       for (String[] zoneNames : zones) {
/*     */         
/* 850 */         String tzId = zoneNames[0];
/* 851 */         if (!tzId.equalsIgnoreCase("GMT")) {
/*     */ 
/*     */           
/* 854 */           TimeZone tz = TimeZone.getTimeZone(tzId);
/*     */ 
/*     */           
/* 857 */           TzInfo standard = new TzInfo(tz, false);
/* 858 */           TzInfo tzInfo = standard;
/* 859 */           for (int i = 1; i < zoneNames.length; i++) {
/* 860 */             switch (i) {
/*     */               
/*     */               case 3:
/* 863 */                 tzInfo = new TzInfo(tz, true);
/*     */                 break;
/*     */               case 5:
/* 866 */                 tzInfo = standard;
/*     */                 break;
/*     */             } 
/*     */ 
/*     */             
/* 871 */             if (zoneNames[i] != null) {
/* 872 */               String key = zoneNames[i].toLowerCase(locale);
/*     */ 
/*     */               
/* 875 */               if (sorted.add(key)) {
/* 876 */                 this.tzNames.put(key, tzInfo);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 883 */       for (String zoneName : sorted) {
/* 884 */         FastDateParser.simpleQuote(sb.append('|'), zoneName);
/*     */       }
/* 886 */       sb.append(")");
/* 887 */       createPattern(sb);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String timeZone) {
/* 895 */       TimeZone tz = FastTimeZone.getGmtTimeZone(timeZone);
/* 896 */       if (tz != null) {
/* 897 */         cal.setTimeZone(tz);
/*     */       } else {
/* 899 */         String lowerCase = timeZone.toLowerCase(this.locale);
/* 900 */         TzInfo tzInfo = this.tzNames.get(lowerCase);
/* 901 */         if (tzInfo == null)
/*     */         {
/* 903 */           tzInfo = this.tzNames.get(lowerCase + '.');
/*     */         }
/* 905 */         cal.set(16, tzInfo.dstOffset);
/* 906 */         cal.set(15, tzInfo.zone.getRawOffset());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ISO8601TimeZoneStrategy
/*     */     extends PatternStrategy
/*     */   {
/*     */     ISO8601TimeZoneStrategy(String pattern) {
/* 919 */       createPattern(pattern);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/* 927 */       cal.setTimeZone(FastTimeZone.getGmtTimeZone(value));
/*     */     }
/*     */     
/* 930 */     private static final FastDateParser.Strategy ISO_8601_1_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}))");
/* 931 */     private static final FastDateParser.Strategy ISO_8601_2_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}\\d{2}))");
/* 932 */     private static final FastDateParser.Strategy ISO_8601_3_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}(?::)\\d{2}))");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static FastDateParser.Strategy getStrategy(int tokenLen) {
/* 942 */       switch (tokenLen) {
/*     */         case 1:
/* 944 */           return ISO_8601_1_STRATEGY;
/*     */         case 2:
/* 946 */           return ISO_8601_2_STRATEGY;
/*     */         case 3:
/* 948 */           return ISO_8601_3_STRATEGY;
/*     */       } 
/* 950 */       throw new IllegalArgumentException("invalid number of X");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 955 */   private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2)
/*     */     {
/*     */       int modify(FastDateParser parser, int iValue) {
/* 958 */         return iValue - 1;
/*     */       }
/*     */     };
/*     */   
/* 962 */   private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
/* 963 */   private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
/* 964 */   private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
/* 965 */   private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
/* 966 */   private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
/* 967 */   private static final Strategy DAY_OF_WEEK_STRATEGY = new NumberStrategy(7)
/*     */     {
/*     */       int modify(FastDateParser parser, int iValue) {
/* 970 */         return (iValue == 7) ? 1 : (iValue + 1);
/*     */       }
/*     */     };
/*     */   
/* 974 */   private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
/* 975 */   private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
/* 976 */   private static final Strategy HOUR24_OF_DAY_STRATEGY = new NumberStrategy(11)
/*     */     {
/*     */       int modify(FastDateParser parser, int iValue) {
/* 979 */         return (iValue == 24) ? 0 : iValue;
/*     */       }
/*     */     };
/*     */   
/* 983 */   private static final Strategy HOUR12_STRATEGY = new NumberStrategy(10)
/*     */     {
/*     */       int modify(FastDateParser parser, int iValue) {
/* 986 */         return (iValue == 12) ? 0 : iValue;
/*     */       }
/*     */     };
/*     */   
/* 990 */   private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
/* 991 */   private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
/* 992 */   private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
/* 993 */   private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\FastDateParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */