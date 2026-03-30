/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.Validate;
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
/*     */ public class DurationFormatUtils
/*     */ {
/*     */   public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'";
/*     */   
/*     */   public static String formatDurationHMS(long durationMillis) {
/*  83 */     return formatDuration(durationMillis, "HH:mm:ss.SSS");
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
/*     */   public static String formatDurationISO(long durationMillis) {
/*  99 */     return formatDuration(durationMillis, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'", false);
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
/*     */   public static String formatDuration(long durationMillis, String format) {
/* 114 */     return formatDuration(durationMillis, format, true);
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
/*     */   public static String formatDuration(long durationMillis, String format, boolean padWithZeros) {
/* 131 */     Validate.inclusiveBetween(0L, Long.MAX_VALUE, durationMillis, "durationMillis must not be negative");
/*     */     
/* 133 */     Token[] tokens = lexx(format);
/*     */     
/* 135 */     long days = 0L;
/* 136 */     long hours = 0L;
/* 137 */     long minutes = 0L;
/* 138 */     long seconds = 0L;
/* 139 */     long milliseconds = durationMillis;
/*     */     
/* 141 */     if (Token.containsTokenWithValue(tokens, d)) {
/* 142 */       days = milliseconds / 86400000L;
/* 143 */       milliseconds -= days * 86400000L;
/*     */     } 
/* 145 */     if (Token.containsTokenWithValue(tokens, H)) {
/* 146 */       hours = milliseconds / 3600000L;
/* 147 */       milliseconds -= hours * 3600000L;
/*     */     } 
/* 149 */     if (Token.containsTokenWithValue(tokens, m)) {
/* 150 */       minutes = milliseconds / 60000L;
/* 151 */       milliseconds -= minutes * 60000L;
/*     */     } 
/* 153 */     if (Token.containsTokenWithValue(tokens, s)) {
/* 154 */       seconds = milliseconds / 1000L;
/* 155 */       milliseconds -= seconds * 1000L;
/*     */     } 
/*     */     
/* 158 */     return format(tokens, 0L, 0L, days, hours, minutes, seconds, milliseconds, padWithZeros);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatDurationWords(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements) {
/* 181 */     String duration = formatDuration(durationMillis, "d' days 'H' hours 'm' minutes 's' seconds'");
/* 182 */     if (suppressLeadingZeroElements) {
/*     */       
/* 184 */       duration = " " + duration;
/* 185 */       String tmp = StringUtils.replaceOnce(duration, " 0 days", "");
/* 186 */       if (tmp.length() != duration.length()) {
/* 187 */         duration = tmp;
/* 188 */         tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
/* 189 */         if (tmp.length() != duration.length()) {
/* 190 */           duration = tmp;
/* 191 */           tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
/* 192 */           duration = tmp;
/* 193 */           if (tmp.length() != duration.length()) {
/* 194 */             duration = StringUtils.replaceOnce(tmp, " 0 seconds", "");
/*     */           }
/*     */         } 
/*     */       } 
/* 198 */       if (!duration.isEmpty())
/*     */       {
/* 200 */         duration = duration.substring(1);
/*     */       }
/*     */     } 
/* 203 */     if (suppressTrailingZeroElements) {
/* 204 */       String tmp = StringUtils.replaceOnce(duration, " 0 seconds", "");
/* 205 */       if (tmp.length() != duration.length()) {
/* 206 */         duration = tmp;
/* 207 */         tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
/* 208 */         if (tmp.length() != duration.length()) {
/* 209 */           duration = tmp;
/* 210 */           tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
/* 211 */           if (tmp.length() != duration.length()) {
/* 212 */             duration = StringUtils.replaceOnce(tmp, " 0 days", "");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     duration = " " + duration;
/* 219 */     duration = StringUtils.replaceOnce(duration, " 1 seconds", " 1 second");
/* 220 */     duration = StringUtils.replaceOnce(duration, " 1 minutes", " 1 minute");
/* 221 */     duration = StringUtils.replaceOnce(duration, " 1 hours", " 1 hour");
/* 222 */     duration = StringUtils.replaceOnce(duration, " 1 days", " 1 day");
/* 223 */     return duration.trim();
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
/*     */   public static String formatPeriodISO(long startMillis, long endMillis) {
/* 238 */     return formatPeriod(startMillis, endMillis, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'", false, TimeZone.getDefault());
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
/*     */   public static String formatPeriod(long startMillis, long endMillis, String format) {
/* 252 */     return formatPeriod(startMillis, endMillis, format, true, TimeZone.getDefault());
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
/*     */   public static String formatPeriod(long startMillis, long endMillis, String format, boolean padWithZeros, TimeZone timezone) {
/* 281 */     Validate.isTrue((startMillis <= endMillis), "startMillis must not be greater than endMillis", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     Token[] tokens = lexx(format);
/*     */ 
/*     */ 
/*     */     
/* 294 */     Calendar start = Calendar.getInstance(timezone);
/* 295 */     start.setTime(new Date(startMillis));
/* 296 */     Calendar end = Calendar.getInstance(timezone);
/* 297 */     end.setTime(new Date(endMillis));
/*     */ 
/*     */     
/* 300 */     int milliseconds = end.get(14) - start.get(14);
/* 301 */     int seconds = end.get(13) - start.get(13);
/* 302 */     int minutes = end.get(12) - start.get(12);
/* 303 */     int hours = end.get(11) - start.get(11);
/* 304 */     int days = end.get(5) - start.get(5);
/* 305 */     int months = end.get(2) - start.get(2);
/* 306 */     int years = end.get(1) - start.get(1);
/*     */ 
/*     */     
/* 309 */     while (milliseconds < 0) {
/* 310 */       milliseconds += 1000;
/* 311 */       seconds--;
/*     */     } 
/* 313 */     while (seconds < 0) {
/* 314 */       seconds += 60;
/* 315 */       minutes--;
/*     */     } 
/* 317 */     while (minutes < 0) {
/* 318 */       minutes += 60;
/* 319 */       hours--;
/*     */     } 
/* 321 */     while (hours < 0) {
/* 322 */       hours += 24;
/* 323 */       days--;
/*     */     } 
/*     */     
/* 326 */     if (Token.containsTokenWithValue(tokens, M)) {
/* 327 */       while (days < 0) {
/* 328 */         days += start.getActualMaximum(5);
/* 329 */         months--;
/* 330 */         start.add(2, 1);
/*     */       } 
/*     */       
/* 333 */       while (months < 0) {
/* 334 */         months += 12;
/* 335 */         years--;
/*     */       } 
/*     */       
/* 338 */       if (!Token.containsTokenWithValue(tokens, y) && years != 0) {
/* 339 */         while (years != 0) {
/* 340 */           months += 12 * years;
/* 341 */           years = 0;
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 347 */       if (!Token.containsTokenWithValue(tokens, y)) {
/* 348 */         int target = end.get(1);
/* 349 */         if (months < 0)
/*     */         {
/* 351 */           target--;
/*     */         }
/*     */         
/* 354 */         while (start.get(1) != target) {
/* 355 */           days += start.getActualMaximum(6) - start.get(6);
/*     */ 
/*     */           
/* 358 */           if (start instanceof java.util.GregorianCalendar && start
/* 359 */             .get(2) == 1 && start
/* 360 */             .get(5) == 29) {
/* 361 */             days++;
/*     */           }
/*     */           
/* 364 */           start.add(1, 1);
/*     */           
/* 366 */           days += start.get(6);
/*     */         } 
/*     */         
/* 369 */         years = 0;
/*     */       } 
/*     */       
/* 372 */       while (start.get(2) != end.get(2)) {
/* 373 */         days += start.getActualMaximum(5);
/* 374 */         start.add(2, 1);
/*     */       } 
/*     */       
/* 377 */       months = 0;
/*     */       
/* 379 */       while (days < 0) {
/* 380 */         days += start.getActualMaximum(5);
/* 381 */         months--;
/* 382 */         start.add(2, 1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     if (!Token.containsTokenWithValue(tokens, d)) {
/* 392 */       hours += 24 * days;
/* 393 */       days = 0;
/*     */     } 
/* 395 */     if (!Token.containsTokenWithValue(tokens, H)) {
/* 396 */       minutes += 60 * hours;
/* 397 */       hours = 0;
/*     */     } 
/* 399 */     if (!Token.containsTokenWithValue(tokens, m)) {
/* 400 */       seconds += 60 * minutes;
/* 401 */       minutes = 0;
/*     */     } 
/* 403 */     if (!Token.containsTokenWithValue(tokens, s)) {
/* 404 */       milliseconds += 1000 * seconds;
/* 405 */       seconds = 0;
/*     */     } 
/*     */     
/* 408 */     return format(tokens, years, months, days, hours, minutes, seconds, milliseconds, padWithZeros);
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
/*     */ 
/*     */   
/*     */   static String format(Token[] tokens, long years, long months, long days, long hours, long minutes, long seconds, long milliseconds, boolean padWithZeros) {
/* 428 */     StringBuilder buffer = new StringBuilder();
/* 429 */     boolean lastOutputSeconds = false;
/* 430 */     for (Token token : tokens) {
/* 431 */       Object value = token.getValue();
/* 432 */       int count = token.getCount();
/* 433 */       if (value instanceof StringBuilder) {
/* 434 */         buffer.append(value.toString());
/*     */       }
/* 436 */       else if (value.equals(y)) {
/* 437 */         buffer.append(paddedValue(years, padWithZeros, count));
/* 438 */         lastOutputSeconds = false;
/* 439 */       } else if (value.equals(M)) {
/* 440 */         buffer.append(paddedValue(months, padWithZeros, count));
/* 441 */         lastOutputSeconds = false;
/* 442 */       } else if (value.equals(d)) {
/* 443 */         buffer.append(paddedValue(days, padWithZeros, count));
/* 444 */         lastOutputSeconds = false;
/* 445 */       } else if (value.equals(H)) {
/* 446 */         buffer.append(paddedValue(hours, padWithZeros, count));
/* 447 */         lastOutputSeconds = false;
/* 448 */       } else if (value.equals(m)) {
/* 449 */         buffer.append(paddedValue(minutes, padWithZeros, count));
/* 450 */         lastOutputSeconds = false;
/* 451 */       } else if (value.equals(s)) {
/* 452 */         buffer.append(paddedValue(seconds, padWithZeros, count));
/* 453 */         lastOutputSeconds = true;
/* 454 */       } else if (value.equals(S)) {
/* 455 */         if (lastOutputSeconds) {
/*     */           
/* 457 */           int width = padWithZeros ? Math.max(3, count) : 3;
/* 458 */           buffer.append(paddedValue(milliseconds, true, width));
/*     */         } else {
/* 460 */           buffer.append(paddedValue(milliseconds, padWithZeros, count));
/*     */         } 
/* 462 */         lastOutputSeconds = false;
/*     */       } 
/*     */     } 
/*     */     
/* 466 */     return buffer.toString();
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
/*     */   private static String paddedValue(long value, boolean padWithZeros, int count) {
/* 479 */     String longString = Long.toString(value);
/* 480 */     return padWithZeros ? StringUtils.leftPad(longString, count, '0') : longString;
/*     */   }
/*     */   
/* 483 */   static final Object y = "y";
/* 484 */   static final Object M = "M";
/* 485 */   static final Object d = "d";
/* 486 */   static final Object H = "H";
/* 487 */   static final Object m = "m";
/* 488 */   static final Object s = "s";
/* 489 */   static final Object S = "S";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Token[] lexx(String format) {
/* 498 */     ArrayList<Token> list = new ArrayList<>(format.length());
/*     */     
/* 500 */     boolean inLiteral = false;
/*     */ 
/*     */     
/* 503 */     StringBuilder buffer = null;
/* 504 */     Token previous = null;
/* 505 */     for (int i = 0; i < format.length(); i++) {
/* 506 */       char ch = format.charAt(i);
/* 507 */       if (inLiteral && ch != '\'') {
/* 508 */         buffer.append(ch);
/*     */       } else {
/*     */         
/* 511 */         Object value = null;
/* 512 */         switch (ch) {
/*     */           
/*     */           case '\'':
/* 515 */             if (inLiteral) {
/* 516 */               buffer = null;
/* 517 */               inLiteral = false; break;
/*     */             } 
/* 519 */             buffer = new StringBuilder();
/* 520 */             list.add(new Token(buffer));
/* 521 */             inLiteral = true;
/*     */             break;
/*     */           
/*     */           case 'y':
/* 525 */             value = y;
/*     */             break;
/*     */           case 'M':
/* 528 */             value = M;
/*     */             break;
/*     */           case 'd':
/* 531 */             value = d;
/*     */             break;
/*     */           case 'H':
/* 534 */             value = H;
/*     */             break;
/*     */           case 'm':
/* 537 */             value = m;
/*     */             break;
/*     */           case 's':
/* 540 */             value = s;
/*     */             break;
/*     */           case 'S':
/* 543 */             value = S;
/*     */             break;
/*     */           default:
/* 546 */             if (buffer == null) {
/* 547 */               buffer = new StringBuilder();
/* 548 */               list.add(new Token(buffer));
/*     */             } 
/* 550 */             buffer.append(ch);
/*     */             break;
/*     */         } 
/* 553 */         if (value != null) {
/* 554 */           if (previous != null && previous.getValue().equals(value)) {
/* 555 */             previous.increment();
/*     */           } else {
/* 557 */             Token token = new Token(value);
/* 558 */             list.add(token);
/* 559 */             previous = token;
/*     */           } 
/* 561 */           buffer = null;
/*     */         } 
/*     */       } 
/* 564 */     }  if (inLiteral) {
/* 565 */       throw new IllegalArgumentException("Unmatched quote in format: " + format);
/*     */     }
/* 567 */     return list.<Token>toArray(new Token[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Token
/*     */   {
/*     */     private final Object value;
/*     */ 
/*     */ 
/*     */     
/*     */     private int count;
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean containsTokenWithValue(Token[] tokens, Object value) {
/* 584 */       for (Token token : tokens) {
/* 585 */         if (token.getValue() == value) {
/* 586 */           return true;
/*     */         }
/*     */       } 
/* 589 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Token(Object value) {
/* 601 */       this.value = value;
/* 602 */       this.count = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Token(Object value, int count) {
/* 613 */       this.value = value;
/* 614 */       this.count = count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void increment() {
/* 621 */       this.count++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getCount() {
/* 630 */       return this.count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object getValue() {
/* 639 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj2) {
/* 650 */       if (obj2 instanceof Token) {
/* 651 */         Token tok2 = (Token)obj2;
/* 652 */         if (this.value.getClass() != tok2.value.getClass()) {
/* 653 */           return false;
/*     */         }
/* 655 */         if (this.count != tok2.count) {
/* 656 */           return false;
/*     */         }
/* 658 */         if (this.value instanceof StringBuilder)
/* 659 */           return this.value.toString().equals(tok2.value.toString()); 
/* 660 */         if (this.value instanceof Number) {
/* 661 */           return this.value.equals(tok2.value);
/*     */         }
/* 663 */         return (this.value == tok2.value);
/*     */       } 
/*     */       
/* 666 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 678 */       return this.value.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 688 */       return StringUtils.repeat(this.value.toString(), this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\DurationFormatUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */