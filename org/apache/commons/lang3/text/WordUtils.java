/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ @Deprecated
/*     */ public class WordUtils
/*     */ {
/*     */   public static String wrap(String str, int wrapLength) {
/* 104 */     return wrap(str, wrapLength, null, false);
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
/*     */   public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords) {
/* 181 */     return wrap(str, wrapLength, newLineStr, wrapLongWords, " ");
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
/*     */   public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords, String wrapOn) {
/* 275 */     if (str == null) {
/* 276 */       return null;
/*     */     }
/* 278 */     if (newLineStr == null) {
/* 279 */       newLineStr = System.lineSeparator();
/*     */     }
/* 281 */     if (wrapLength < 1) {
/* 282 */       wrapLength = 1;
/*     */     }
/* 284 */     if (StringUtils.isBlank(wrapOn)) {
/* 285 */       wrapOn = " ";
/*     */     }
/* 287 */     Pattern patternToWrapOn = Pattern.compile(wrapOn);
/* 288 */     int inputLineLength = str.length();
/* 289 */     int offset = 0;
/* 290 */     StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);
/*     */     
/* 292 */     while (offset < inputLineLength) {
/* 293 */       int spaceToWrapAt = -1;
/* 294 */       Matcher matcher = patternToWrapOn.matcher(str
/* 295 */           .substring(offset, Math.min((int)Math.min(2147483647L, (offset + wrapLength) + 1L), inputLineLength)));
/* 296 */       if (matcher.find()) {
/* 297 */         if (matcher.start() == 0) {
/* 298 */           offset += matcher.end();
/*     */           continue;
/*     */         } 
/* 301 */         spaceToWrapAt = matcher.start() + offset;
/*     */       } 
/*     */ 
/*     */       
/* 305 */       if (inputLineLength - offset <= wrapLength) {
/*     */         break;
/*     */       }
/*     */       
/* 309 */       while (matcher.find()) {
/* 310 */         spaceToWrapAt = matcher.start() + offset;
/*     */       }
/*     */       
/* 313 */       if (spaceToWrapAt >= offset) {
/*     */         
/* 315 */         wrappedLine.append(str, offset, spaceToWrapAt);
/* 316 */         wrappedLine.append(newLineStr);
/* 317 */         offset = spaceToWrapAt + 1;
/*     */         
/*     */         continue;
/*     */       } 
/* 321 */       if (wrapLongWords) {
/*     */         
/* 323 */         wrappedLine.append(str, offset, wrapLength + offset);
/* 324 */         wrappedLine.append(newLineStr);
/* 325 */         offset += wrapLength;
/*     */         continue;
/*     */       } 
/* 328 */       matcher = patternToWrapOn.matcher(str.substring(offset + wrapLength));
/* 329 */       if (matcher.find()) {
/* 330 */         spaceToWrapAt = matcher.start() + offset + wrapLength;
/*     */       }
/*     */       
/* 333 */       if (spaceToWrapAt >= 0) {
/* 334 */         wrappedLine.append(str, offset, spaceToWrapAt);
/* 335 */         wrappedLine.append(newLineStr);
/* 336 */         offset = spaceToWrapAt + 1; continue;
/*     */       } 
/* 338 */       wrappedLine.append(str, offset, str.length());
/* 339 */       offset = inputLineLength;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 346 */     wrappedLine.append(str, offset, str.length());
/*     */     
/* 348 */     return wrappedLine.toString();
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
/*     */   public static String capitalize(String str) {
/* 376 */     return capitalize(str, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String capitalize(String str, char... delimiters) {
/* 409 */     int delimLen = (delimiters == null) ? -1 : delimiters.length;
/* 410 */     if (StringUtils.isEmpty(str) || delimLen == 0) {
/* 411 */       return str;
/*     */     }
/* 413 */     char[] buffer = str.toCharArray();
/* 414 */     boolean capitalizeNext = true;
/* 415 */     for (int i = 0; i < buffer.length; i++) {
/* 416 */       char ch = buffer[i];
/* 417 */       if (isDelimiter(ch, delimiters)) {
/* 418 */         capitalizeNext = true;
/* 419 */       } else if (capitalizeNext) {
/* 420 */         buffer[i] = Character.toTitleCase(ch);
/* 421 */         capitalizeNext = false;
/*     */       } 
/*     */     } 
/* 424 */     return new String(buffer);
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
/*     */   public static String capitalizeFully(String str) {
/* 448 */     return capitalizeFully(str, null);
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
/*     */   
/*     */   public static String capitalizeFully(String str, char... delimiters) {
/* 478 */     int delimLen = (delimiters == null) ? -1 : delimiters.length;
/* 479 */     if (StringUtils.isEmpty(str) || delimLen == 0) {
/* 480 */       return str;
/*     */     }
/* 482 */     str = str.toLowerCase();
/* 483 */     return capitalize(str, delimiters);
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
/*     */   public static String uncapitalize(String str) {
/* 505 */     return uncapitalize(str, null);
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
/*     */   public static String uncapitalize(String str, char... delimiters) {
/* 534 */     int delimLen = (delimiters == null) ? -1 : delimiters.length;
/* 535 */     if (StringUtils.isEmpty(str) || delimLen == 0) {
/* 536 */       return str;
/*     */     }
/* 538 */     char[] buffer = str.toCharArray();
/* 539 */     boolean uncapitalizeNext = true;
/* 540 */     for (int i = 0; i < buffer.length; i++) {
/* 541 */       char ch = buffer[i];
/* 542 */       if (isDelimiter(ch, delimiters)) {
/* 543 */         uncapitalizeNext = true;
/* 544 */       } else if (uncapitalizeNext) {
/* 545 */         buffer[i] = Character.toLowerCase(ch);
/* 546 */         uncapitalizeNext = false;
/*     */       } 
/*     */     } 
/* 549 */     return new String(buffer);
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
/*     */   public static String swapCase(String str) {
/* 576 */     if (StringUtils.isEmpty(str)) {
/* 577 */       return str;
/*     */     }
/* 579 */     char[] buffer = str.toCharArray();
/*     */     
/* 581 */     boolean whitespace = true;
/*     */     
/* 583 */     for (int i = 0; i < buffer.length; i++) {
/* 584 */       char ch = buffer[i];
/* 585 */       if (Character.isUpperCase(ch) || Character.isTitleCase(ch)) {
/* 586 */         buffer[i] = Character.toLowerCase(ch);
/* 587 */         whitespace = false;
/* 588 */       } else if (Character.isLowerCase(ch)) {
/* 589 */         if (whitespace) {
/* 590 */           buffer[i] = Character.toTitleCase(ch);
/* 591 */           whitespace = false;
/*     */         } else {
/* 593 */           buffer[i] = Character.toUpperCase(ch);
/*     */         } 
/*     */       } else {
/* 596 */         whitespace = Character.isWhitespace(ch);
/*     */       } 
/*     */     } 
/* 599 */     return new String(buffer);
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
/*     */   public static String initials(String str) {
/* 625 */     return initials(str, null);
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
/*     */   
/*     */   public static String initials(String str, char... delimiters) {
/* 655 */     if (StringUtils.isEmpty(str)) {
/* 656 */       return str;
/*     */     }
/* 658 */     if (delimiters != null && delimiters.length == 0) {
/* 659 */       return "";
/*     */     }
/* 661 */     int strLen = str.length();
/* 662 */     char[] buf = new char[strLen / 2 + 1];
/* 663 */     int count = 0;
/* 664 */     boolean lastWasGap = true;
/* 665 */     for (int i = 0; i < strLen; i++) {
/* 666 */       char ch = str.charAt(i);
/*     */       
/* 668 */       if (isDelimiter(ch, delimiters)) {
/* 669 */         lastWasGap = true;
/* 670 */       } else if (lastWasGap) {
/* 671 */         buf[count++] = ch;
/* 672 */         lastWasGap = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 677 */     return new String(buf, 0, count);
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
/*     */   public static boolean containsAllWords(CharSequence word, CharSequence... words) {
/* 705 */     if (StringUtils.isEmpty(word) || ArrayUtils.isEmpty((Object[])words)) {
/* 706 */       return false;
/*     */     }
/* 708 */     for (CharSequence w : words) {
/* 709 */       if (StringUtils.isBlank(w)) {
/* 710 */         return false;
/*     */       }
/* 712 */       Pattern p = Pattern.compile(".*\\b" + w + "\\b.*");
/* 713 */       if (!p.matcher(word).matches()) {
/* 714 */         return false;
/*     */       }
/*     */     } 
/* 717 */     return true;
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
/*     */   private static boolean isDelimiter(char ch, char[] delimiters) {
/* 729 */     if (delimiters == null) {
/* 730 */       return Character.isWhitespace(ch);
/*     */     }
/* 732 */     for (char delimiter : delimiters) {
/* 733 */       if (ch == delimiter) {
/* 734 */         return true;
/*     */       }
/*     */     } 
/* 737 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\WordUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */