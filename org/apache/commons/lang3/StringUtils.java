/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.text.Normalizer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StringUtils
/*      */ {
/*      */   private static final int STRING_BUILDER_SIZE = 256;
/*      */   public static final String SPACE = " ";
/*      */   public static final String EMPTY = "";
/*      */   public static final String LF = "\n";
/*      */   public static final String CR = "\r";
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */   private static final int PAD_LIMIT = 8192;
/*  185 */   private static final Pattern STRIP_ACCENTS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String abbreviate(String str, int maxWidth) {
/*  221 */     return abbreviate(str, "...", 0, maxWidth);
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
/*      */   public static String abbreviate(String str, int offset, int maxWidth) {
/*  260 */     return abbreviate(str, "...", offset, maxWidth);
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
/*      */   public static String abbreviate(String str, String abbrevMarker, int maxWidth) {
/*  300 */     return abbreviate(str, abbrevMarker, 0, maxWidth);
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
/*      */   public static String abbreviate(String str, String abbrevMarker, int offset, int maxWidth) {
/*  340 */     if (isNotEmpty(str) && "".equals(abbrevMarker) && maxWidth > 0)
/*  341 */       return substring(str, 0, maxWidth); 
/*  342 */     if (isAnyEmpty(new CharSequence[] { str, abbrevMarker })) {
/*  343 */       return str;
/*      */     }
/*  345 */     int abbrevMarkerLength = abbrevMarker.length();
/*  346 */     int minAbbrevWidth = abbrevMarkerLength + 1;
/*  347 */     int minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;
/*      */     
/*  349 */     if (maxWidth < minAbbrevWidth) {
/*  350 */       throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", new Object[] { Integer.valueOf(minAbbrevWidth) }));
/*      */     }
/*  352 */     if (str.length() <= maxWidth) {
/*  353 */       return str;
/*      */     }
/*  355 */     if (offset > str.length()) {
/*  356 */       offset = str.length();
/*      */     }
/*  358 */     if (str.length() - offset < maxWidth - abbrevMarkerLength) {
/*  359 */       offset = str.length() - maxWidth - abbrevMarkerLength;
/*      */     }
/*  361 */     if (offset <= abbrevMarkerLength + 1) {
/*  362 */       return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
/*      */     }
/*  364 */     if (maxWidth < minAbbrevWidthOffset) {
/*  365 */       throw new IllegalArgumentException(String.format("Minimum abbreviation width with offset is %d", new Object[] { Integer.valueOf(minAbbrevWidthOffset) }));
/*      */     }
/*  367 */     if (offset + maxWidth - abbrevMarkerLength < str.length()) {
/*  368 */       return abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength);
/*      */     }
/*  370 */     return abbrevMarker + str.substring(str.length() - maxWidth - abbrevMarkerLength);
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
/*      */   public static String abbreviateMiddle(String str, String middle, int length) {
/*  403 */     if (isAnyEmpty(new CharSequence[] { str, middle }) || length >= str.length() || length < middle.length() + 2) {
/*  404 */       return str;
/*      */     }
/*      */     
/*  407 */     int targetSting = length - middle.length();
/*  408 */     int startOffset = targetSting / 2 + targetSting % 2;
/*  409 */     int endOffset = str.length() - targetSting / 2;
/*      */     
/*  411 */     return str.substring(0, startOffset) + middle + str
/*      */       
/*  413 */       .substring(endOffset);
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
/*      */   private static String appendIfMissing(String str, CharSequence suffix, boolean ignoreCase, CharSequence... suffixes) {
/*  428 */     if (str == null || isEmpty(suffix) || endsWith(str, suffix, ignoreCase)) {
/*  429 */       return str;
/*      */     }
/*  431 */     if (ArrayUtils.isNotEmpty(suffixes)) {
/*  432 */       for (CharSequence s : suffixes) {
/*  433 */         if (endsWith(str, s, ignoreCase)) {
/*  434 */           return str;
/*      */         }
/*      */       } 
/*      */     }
/*  438 */     return str + suffix.toString();
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
/*      */   public static String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
/*  476 */     return appendIfMissing(str, suffix, false, suffixes);
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
/*      */   public static String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
/*  514 */     return appendIfMissing(str, suffix, true, suffixes);
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
/*      */   public static String capitalize(String str) {
/*  539 */     int strLen = length(str);
/*  540 */     if (strLen == 0) {
/*  541 */       return str;
/*      */     }
/*      */     
/*  544 */     int firstCodepoint = str.codePointAt(0);
/*  545 */     int newCodePoint = Character.toTitleCase(firstCodepoint);
/*  546 */     if (firstCodepoint == newCodePoint)
/*      */     {
/*  548 */       return str;
/*      */     }
/*      */     
/*  551 */     int[] newCodePoints = new int[strLen];
/*  552 */     int outOffset = 0;
/*  553 */     newCodePoints[outOffset++] = newCodePoint; int inOffset;
/*  554 */     for (inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
/*  555 */       int codepoint = str.codePointAt(inOffset);
/*  556 */       newCodePoints[outOffset++] = codepoint;
/*  557 */       inOffset += Character.charCount(codepoint);
/*      */     } 
/*  559 */     return new String(newCodePoints, 0, outOffset);
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
/*      */   public static String center(String str, int size) {
/*  588 */     return center(str, size, ' ');
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
/*      */   public static String center(String str, int size, char padChar) {
/*  616 */     if (str == null || size <= 0) {
/*  617 */       return str;
/*      */     }
/*  619 */     int strLen = str.length();
/*  620 */     int pads = size - strLen;
/*  621 */     if (pads <= 0) {
/*  622 */       return str;
/*      */     }
/*  624 */     str = leftPad(str, strLen + pads / 2, padChar);
/*  625 */     str = rightPad(str, size, padChar);
/*  626 */     return str;
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
/*      */   public static String center(String str, int size, String padStr) {
/*  656 */     if (str == null || size <= 0) {
/*  657 */       return str;
/*      */     }
/*  659 */     if (isEmpty(padStr)) {
/*  660 */       padStr = " ";
/*      */     }
/*  662 */     int strLen = str.length();
/*  663 */     int pads = size - strLen;
/*  664 */     if (pads <= 0) {
/*  665 */       return str;
/*      */     }
/*  667 */     str = leftPad(str, strLen + pads / 2, padStr);
/*  668 */     str = rightPad(str, size, padStr);
/*  669 */     return str;
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
/*      */   public static String chomp(String str) {
/*  700 */     if (isEmpty(str)) {
/*  701 */       return str;
/*      */     }
/*      */     
/*  704 */     if (str.length() == 1) {
/*  705 */       char ch = str.charAt(0);
/*  706 */       if (ch == '\r' || ch == '\n') {
/*  707 */         return "";
/*      */       }
/*  709 */       return str;
/*      */     } 
/*      */     
/*  712 */     int lastIdx = str.length() - 1;
/*  713 */     char last = str.charAt(lastIdx);
/*      */     
/*  715 */     if (last == '\n') {
/*  716 */       if (str.charAt(lastIdx - 1) == '\r') {
/*  717 */         lastIdx--;
/*      */       }
/*  719 */     } else if (last != '\r') {
/*  720 */       lastIdx++;
/*      */     } 
/*  722 */     return str.substring(0, lastIdx);
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
/*      */   @Deprecated
/*      */   public static String chomp(String str, String separator) {
/*  754 */     return removeEnd(str, separator);
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
/*      */   public static String chop(String str) {
/*  783 */     if (str == null) {
/*  784 */       return null;
/*      */     }
/*  786 */     int strLen = str.length();
/*  787 */     if (strLen < 2) {
/*  788 */       return "";
/*      */     }
/*  790 */     int lastIdx = strLen - 1;
/*  791 */     String ret = str.substring(0, lastIdx);
/*  792 */     char last = str.charAt(lastIdx);
/*  793 */     if (last == '\n' && ret.charAt(lastIdx - 1) == '\r') {
/*  794 */       return ret.substring(0, lastIdx - 1);
/*      */     }
/*  796 */     return ret;
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
/*      */   public static int compare(String str1, String str2) {
/*  834 */     return compare(str1, str2, true);
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
/*      */   public static int compare(String str1, String str2, boolean nullIsLess) {
/*  872 */     if (str1 == str2) {
/*  873 */       return 0;
/*      */     }
/*  875 */     if (str1 == null) {
/*  876 */       return nullIsLess ? -1 : 1;
/*      */     }
/*  878 */     if (str2 == null) {
/*  879 */       return nullIsLess ? 1 : -1;
/*      */     }
/*  881 */     return str1.compareTo(str2);
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
/*      */   
/*      */   public static int compareIgnoreCase(String str1, String str2) {
/*  922 */     return compareIgnoreCase(str1, str2, true);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compareIgnoreCase(String str1, String str2, boolean nullIsLess) {
/*  965 */     if (str1 == str2) {
/*  966 */       return 0;
/*      */     }
/*  968 */     if (str1 == null) {
/*  969 */       return nullIsLess ? -1 : 1;
/*      */     }
/*  971 */     if (str2 == null) {
/*  972 */       return nullIsLess ? 1 : -1;
/*      */     }
/*  974 */     return str1.compareToIgnoreCase(str2);
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
/*      */   public static boolean contains(CharSequence seq, CharSequence searchSeq) {
/* 1000 */     if (seq == null || searchSeq == null) {
/* 1001 */       return false;
/*      */     }
/* 1003 */     return (CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0);
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
/*      */   public static boolean contains(CharSequence seq, int searchChar) {
/* 1029 */     if (isEmpty(seq)) {
/* 1030 */       return false;
/*      */     }
/* 1032 */     return (CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0);
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
/*      */   public static boolean containsAny(CharSequence cs, char... searchChars) {
/* 1063 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1064 */       return false;
/*      */     }
/* 1066 */     int csLength = cs.length();
/* 1067 */     int searchLength = searchChars.length;
/* 1068 */     int csLast = csLength - 1;
/* 1069 */     int searchLast = searchLength - 1;
/* 1070 */     for (int i = 0; i < csLength; i++) {
/* 1071 */       char ch = cs.charAt(i);
/* 1072 */       for (int j = 0; j < searchLength; j++) {
/* 1073 */         if (searchChars[j] == ch) {
/* 1074 */           if (Character.isHighSurrogate(ch)) {
/* 1075 */             if (j == searchLast)
/*      */             {
/* 1077 */               return true;
/*      */             }
/* 1079 */             if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1080 */               return true;
/*      */             }
/*      */           } else {
/*      */             
/* 1084 */             return true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1089 */     return false;
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
/*      */   public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
/* 1124 */     if (searchChars == null) {
/* 1125 */       return false;
/*      */     }
/* 1127 */     return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
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
/*      */   public static boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
/* 1156 */     if (isEmpty(cs) || ArrayUtils.isEmpty((Object[])searchCharSequences)) {
/* 1157 */       return false;
/*      */     }
/* 1159 */     for (CharSequence searchCharSequence : searchCharSequences) {
/* 1160 */       if (contains(cs, searchCharSequence)) {
/* 1161 */         return true;
/*      */       }
/*      */     } 
/* 1164 */     return false;
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
/*      */   public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1192 */     if (str == null || searchStr == null) {
/* 1193 */       return false;
/*      */     }
/* 1195 */     int len = searchStr.length();
/* 1196 */     int max = str.length() - len;
/* 1197 */     for (int i = 0; i <= max; i++) {
/* 1198 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) {
/* 1199 */         return true;
/*      */       }
/*      */     } 
/* 1202 */     return false;
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
/*      */   public static boolean containsNone(CharSequence cs, char... searchChars) {
/* 1231 */     if (cs == null || searchChars == null) {
/* 1232 */       return true;
/*      */     }
/* 1234 */     int csLen = cs.length();
/* 1235 */     int csLast = csLen - 1;
/* 1236 */     int searchLen = searchChars.length;
/* 1237 */     int searchLast = searchLen - 1;
/* 1238 */     for (int i = 0; i < csLen; i++) {
/* 1239 */       char ch = cs.charAt(i);
/* 1240 */       for (int j = 0; j < searchLen; j++) {
/* 1241 */         if (searchChars[j] == ch) {
/* 1242 */           if (Character.isHighSurrogate(ch)) {
/* 1243 */             if (j == searchLast)
/*      */             {
/* 1245 */               return false;
/*      */             }
/* 1247 */             if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1248 */               return false;
/*      */             }
/*      */           } else {
/*      */             
/* 1252 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1257 */     return true;
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
/*      */   public static boolean containsNone(CharSequence cs, String invalidChars) {
/* 1284 */     if (cs == null || invalidChars == null) {
/* 1285 */       return true;
/*      */     }
/* 1287 */     return containsNone(cs, invalidChars.toCharArray());
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
/*      */   public static boolean containsOnly(CharSequence cs, char... valid) {
/* 1316 */     if (valid == null || cs == null) {
/* 1317 */       return false;
/*      */     }
/* 1319 */     if (cs.length() == 0) {
/* 1320 */       return true;
/*      */     }
/* 1322 */     if (valid.length == 0) {
/* 1323 */       return false;
/*      */     }
/* 1325 */     return (indexOfAnyBut(cs, valid) == -1);
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
/*      */   public static boolean containsOnly(CharSequence cs, String validChars) {
/* 1352 */     if (cs == null || validChars == null) {
/* 1353 */       return false;
/*      */     }
/* 1355 */     return containsOnly(cs, validChars.toCharArray());
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
/*      */   public static boolean containsWhitespace(CharSequence seq) {
/* 1370 */     if (isEmpty(seq)) {
/* 1371 */       return false;
/*      */     }
/* 1373 */     int strLen = seq.length();
/* 1374 */     for (int i = 0; i < strLen; i++) {
/* 1375 */       if (Character.isWhitespace(seq.charAt(i))) {
/* 1376 */         return true;
/*      */       }
/*      */     } 
/* 1379 */     return false;
/*      */   }
/*      */   
/*      */   private static void convertRemainingAccentCharacters(StringBuilder decomposed) {
/* 1383 */     for (int i = 0; i < decomposed.length(); i++) {
/* 1384 */       if (decomposed.charAt(i) == 'Ł') {
/* 1385 */         decomposed.deleteCharAt(i);
/* 1386 */         decomposed.insert(i, 'L');
/* 1387 */       } else if (decomposed.charAt(i) == 'ł') {
/* 1388 */         decomposed.deleteCharAt(i);
/* 1389 */         decomposed.insert(i, 'l');
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int countMatches(CharSequence str, char ch) {
/* 1414 */     if (isEmpty(str)) {
/* 1415 */       return 0;
/*      */     }
/* 1417 */     int count = 0;
/*      */     
/* 1419 */     for (int i = 0; i < str.length(); i++) {
/* 1420 */       if (ch == str.charAt(i)) {
/* 1421 */         count++;
/*      */       }
/*      */     } 
/* 1424 */     return count;
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
/*      */   public static int countMatches(CharSequence str, CharSequence sub) {
/* 1450 */     if (isEmpty(str) || isEmpty(sub)) {
/* 1451 */       return 0;
/*      */     }
/* 1453 */     int count = 0;
/* 1454 */     int idx = 0;
/* 1455 */     while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != -1) {
/* 1456 */       count++;
/* 1457 */       idx += sub.length();
/*      */     } 
/* 1459 */     return count;
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
/*      */   public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
/* 1483 */     return isBlank((CharSequence)str) ? defaultStr : str;
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
/*      */   public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
/* 1505 */     return isEmpty((CharSequence)str) ? defaultStr : str;
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
/*      */   public static String defaultString(String str) {
/* 1525 */     return defaultString(str, "");
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
/*      */   public static String defaultString(String str, String defaultStr) {
/* 1546 */     return (str == null) ? defaultStr : str;
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
/*      */   public static String deleteWhitespace(String str) {
/* 1566 */     if (isEmpty(str)) {
/* 1567 */       return str;
/*      */     }
/* 1569 */     int sz = str.length();
/* 1570 */     char[] chs = new char[sz];
/* 1571 */     int count = 0;
/* 1572 */     for (int i = 0; i < sz; i++) {
/* 1573 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 1574 */         chs[count++] = str.charAt(i);
/*      */       }
/*      */     } 
/* 1577 */     if (count == sz) {
/* 1578 */       return str;
/*      */     }
/* 1580 */     return new String(chs, 0, count);
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
/*      */   public static String difference(String str1, String str2) {
/* 1614 */     if (str1 == null) {
/* 1615 */       return str2;
/*      */     }
/* 1617 */     if (str2 == null) {
/* 1618 */       return str1;
/*      */     }
/* 1620 */     int at = indexOfDifference(str1, str2);
/* 1621 */     if (at == -1) {
/* 1622 */       return "";
/*      */     }
/* 1624 */     return str2.substring(at);
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
/*      */   public static boolean endsWith(CharSequence str, CharSequence suffix) {
/* 1652 */     return endsWith(str, suffix, false);
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
/*      */   private static boolean endsWith(CharSequence str, CharSequence suffix, boolean ignoreCase) {
/* 1667 */     if (str == null || suffix == null) {
/* 1668 */       return (str == suffix);
/*      */     }
/* 1670 */     if (suffix.length() > str.length()) {
/* 1671 */       return false;
/*      */     }
/* 1673 */     int strOffset = str.length() - suffix.length();
/* 1674 */     return CharSequenceUtils.regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
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
/*      */   public static boolean endsWithAny(CharSequence sequence, CharSequence... searchStrings) {
/* 1699 */     if (isEmpty(sequence) || ArrayUtils.isEmpty((Object[])searchStrings)) {
/* 1700 */       return false;
/*      */     }
/* 1702 */     for (CharSequence searchString : searchStrings) {
/* 1703 */       if (endsWith(sequence, searchString)) {
/* 1704 */         return true;
/*      */       }
/*      */     } 
/* 1707 */     return false;
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
/*      */   public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
/* 1734 */     return endsWith(str, suffix, true);
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
/*      */   public static boolean equals(CharSequence cs1, CharSequence cs2) {
/* 1762 */     if (cs1 == cs2) {
/* 1763 */       return true;
/*      */     }
/* 1765 */     if (cs1 == null || cs2 == null) {
/* 1766 */       return false;
/*      */     }
/* 1768 */     if (cs1.length() != cs2.length()) {
/* 1769 */       return false;
/*      */     }
/* 1771 */     if (cs1 instanceof String && cs2 instanceof String) {
/* 1772 */       return cs1.equals(cs2);
/*      */     }
/*      */     
/* 1775 */     int length = cs1.length();
/* 1776 */     for (int i = 0; i < length; i++) {
/* 1777 */       if (cs1.charAt(i) != cs2.charAt(i)) {
/* 1778 */         return false;
/*      */       }
/*      */     } 
/* 1781 */     return true;
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
/*      */   public static boolean equalsAny(CharSequence string, CharSequence... searchStrings) {
/* 1804 */     if (ArrayUtils.isNotEmpty(searchStrings)) {
/* 1805 */       for (CharSequence next : searchStrings) {
/* 1806 */         if (equals(string, next)) {
/* 1807 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/* 1811 */     return false;
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
/*      */   public static boolean equalsAnyIgnoreCase(CharSequence string, CharSequence... searchStrings) {
/* 1834 */     if (ArrayUtils.isNotEmpty(searchStrings)) {
/* 1835 */       for (CharSequence next : searchStrings) {
/* 1836 */         if (equalsIgnoreCase(string, next)) {
/* 1837 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/* 1841 */     return false;
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
/*      */   public static boolean equalsIgnoreCase(CharSequence cs1, CharSequence cs2) {
/* 1866 */     if (cs1 == cs2) {
/* 1867 */       return true;
/*      */     }
/* 1869 */     if (cs1 == null || cs2 == null) {
/* 1870 */       return false;
/*      */     }
/* 1872 */     if (cs1.length() != cs2.length()) {
/* 1873 */       return false;
/*      */     }
/* 1875 */     return CharSequenceUtils.regionMatches(cs1, true, 0, cs2, 0, cs1.length());
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
/*      */   @SafeVarargs
/*      */   public static <T extends CharSequence> T firstNonBlank(T... values) {
/* 1905 */     if (values != null) {
/* 1906 */       for (T val : values) {
/* 1907 */         if (isNotBlank((CharSequence)val)) {
/* 1908 */           return val;
/*      */         }
/*      */       } 
/*      */     }
/* 1912 */     return null;
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
/*      */   @SafeVarargs
/*      */   public static <T extends CharSequence> T firstNonEmpty(T... values) {
/* 1940 */     if (values != null) {
/* 1941 */       for (T val : values) {
/* 1942 */         if (isNotEmpty((CharSequence)val)) {
/* 1943 */           return val;
/*      */         }
/*      */       } 
/*      */     }
/* 1947 */     return null;
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
/*      */   public static byte[] getBytes(String string, Charset charset) {
/* 1960 */     return (string == null) ? ArrayUtils.EMPTY_BYTE_ARRAY : string.getBytes(Charsets.toCharset(charset));
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
/*      */   public static byte[] getBytes(String string, String charset) throws UnsupportedEncodingException {
/* 1974 */     return (string == null) ? ArrayUtils.EMPTY_BYTE_ARRAY : string.getBytes(Charsets.toCharsetName(charset));
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
/*      */   public static String getCommonPrefix(String... strs) {
/* 2011 */     if (ArrayUtils.isEmpty((Object[])strs)) {
/* 2012 */       return "";
/*      */     }
/* 2014 */     int smallestIndexOfDiff = indexOfDifference((CharSequence[])strs);
/* 2015 */     if (smallestIndexOfDiff == -1) {
/*      */       
/* 2017 */       if (strs[0] == null) {
/* 2018 */         return "";
/*      */       }
/* 2020 */       return strs[0];
/* 2021 */     }  if (smallestIndexOfDiff == 0)
/*      */     {
/* 2023 */       return "";
/*      */     }
/*      */     
/* 2026 */     return strs[0].substring(0, smallestIndexOfDiff);
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
/*      */   public static String getDigits(String str) {
/* 2053 */     if (isEmpty(str)) {
/* 2054 */       return str;
/*      */     }
/* 2056 */     int sz = str.length();
/* 2057 */     StringBuilder strDigits = new StringBuilder(sz);
/* 2058 */     for (int i = 0; i < sz; i++) {
/* 2059 */       char tempChar = str.charAt(i);
/* 2060 */       if (Character.isDigit(tempChar)) {
/* 2061 */         strDigits.append(tempChar);
/*      */       }
/*      */     } 
/* 2064 */     return strDigits.toString();
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
/*      */   @Deprecated
/*      */   public static int getFuzzyDistance(CharSequence term, CharSequence query, Locale locale) {
/* 2098 */     if (term == null || query == null)
/* 2099 */       throw new IllegalArgumentException("Strings must not be null"); 
/* 2100 */     if (locale == null) {
/* 2101 */       throw new IllegalArgumentException("Locale must not be null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2108 */     String termLowerCase = term.toString().toLowerCase(locale);
/* 2109 */     String queryLowerCase = query.toString().toLowerCase(locale);
/*      */ 
/*      */     
/* 2112 */     int score = 0;
/*      */ 
/*      */ 
/*      */     
/* 2116 */     int termIndex = 0;
/*      */ 
/*      */     
/* 2119 */     int previousMatchingCharacterIndex = Integer.MIN_VALUE;
/*      */     
/* 2121 */     for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
/* 2122 */       char queryChar = queryLowerCase.charAt(queryIndex);
/*      */       
/* 2124 */       boolean termCharacterMatchFound = false;
/* 2125 */       for (; termIndex < termLowerCase.length() && !termCharacterMatchFound; termIndex++) {
/* 2126 */         char termChar = termLowerCase.charAt(termIndex);
/*      */         
/* 2128 */         if (queryChar == termChar) {
/*      */           
/* 2130 */           score++;
/*      */ 
/*      */ 
/*      */           
/* 2134 */           if (previousMatchingCharacterIndex + 1 == termIndex) {
/* 2135 */             score += 2;
/*      */           }
/*      */           
/* 2138 */           previousMatchingCharacterIndex = termIndex;
/*      */ 
/*      */ 
/*      */           
/* 2142 */           termCharacterMatchFound = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2147 */     return score;
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
/*      */   public static <T extends CharSequence> T getIfBlank(T str, Supplier<T> defaultSupplier) {
/* 2176 */     return isBlank((CharSequence)str) ? ((defaultSupplier == null) ? null : defaultSupplier.get()) : str;
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
/*      */   public static <T extends CharSequence> T getIfEmpty(T str, Supplier<T> defaultSupplier) {
/* 2204 */     return isEmpty((CharSequence)str) ? ((defaultSupplier == null) ? null : defaultSupplier.get()) : str;
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
/*      */   @Deprecated
/*      */   public static double getJaroWinklerDistance(CharSequence first, CharSequence second) {
/* 2244 */     double DEFAULT_SCALING_FACTOR = 0.1D;
/*      */     
/* 2246 */     if (first == null || second == null) {
/* 2247 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */     
/* 2250 */     int[] mtp = matches(first, second);
/* 2251 */     double m = mtp[0];
/* 2252 */     if (m == 0.0D) {
/* 2253 */       return 0.0D;
/*      */     }
/* 2255 */     double j = (m / first.length() + m / second.length() + (m - mtp[1]) / m) / 3.0D;
/* 2256 */     double jw = (j < 0.7D) ? j : (j + Math.min(0.1D, 1.0D / mtp[3]) * mtp[2] * (1.0D - j));
/* 2257 */     return Math.round(jw * 100.0D) / 100.0D;
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
/*      */   
/*      */   @Deprecated
/*      */   public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
/* 2299 */     if (s == null || t == null) {
/* 2300 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */     
/* 2303 */     int n = s.length();
/* 2304 */     int m = t.length();
/*      */     
/* 2306 */     if (n == 0)
/* 2307 */       return m; 
/* 2308 */     if (m == 0) {
/* 2309 */       return n;
/*      */     }
/*      */     
/* 2312 */     if (n > m) {
/*      */       
/* 2314 */       CharSequence tmp = s;
/* 2315 */       s = t;
/* 2316 */       t = tmp;
/* 2317 */       n = m;
/* 2318 */       m = t.length();
/*      */     } 
/*      */     
/* 2321 */     int[] p = new int[n + 1];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2331 */     for (i = 0; i <= n; i++) {
/* 2332 */       p[i] = i;
/*      */     }
/*      */     
/* 2335 */     for (int j = 1; j <= m; j++) {
/* 2336 */       int upper_left = p[0];
/* 2337 */       char t_j = t.charAt(j - 1);
/* 2338 */       p[0] = j;
/*      */       
/* 2340 */       for (i = 1; i <= n; i++) {
/* 2341 */         int upper = p[i];
/* 2342 */         int cost = (s.charAt(i - 1) == t_j) ? 0 : 1;
/*      */         
/* 2344 */         p[i] = Math.min(Math.min(p[i - 1] + 1, p[i] + 1), upper_left + cost);
/* 2345 */         upper_left = upper;
/*      */       } 
/*      */     } 
/*      */     
/* 2349 */     return p[n];
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
/*      */   @Deprecated
/*      */   public static int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
/* 2389 */     if (s == null || t == null) {
/* 2390 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/* 2392 */     if (threshold < 0) {
/* 2393 */       throw new IllegalArgumentException("Threshold must not be negative");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2440 */     int n = s.length();
/* 2441 */     int m = t.length();
/*      */ 
/*      */     
/* 2444 */     if (n == 0)
/* 2445 */       return (m <= threshold) ? m : -1; 
/* 2446 */     if (m == 0)
/* 2447 */       return (n <= threshold) ? n : -1; 
/* 2448 */     if (Math.abs(n - m) > threshold)
/*      */     {
/* 2450 */       return -1;
/*      */     }
/*      */     
/* 2453 */     if (n > m) {
/*      */       
/* 2455 */       CharSequence tmp = s;
/* 2456 */       s = t;
/* 2457 */       t = tmp;
/* 2458 */       n = m;
/* 2459 */       m = t.length();
/*      */     } 
/*      */     
/* 2462 */     int[] p = new int[n + 1];
/* 2463 */     int[] d = new int[n + 1];
/*      */ 
/*      */ 
/*      */     
/* 2467 */     int boundary = Math.min(n, threshold) + 1;
/* 2468 */     for (int i = 0; i < boundary; i++) {
/* 2469 */       p[i] = i;
/*      */     }
/*      */ 
/*      */     
/* 2473 */     Arrays.fill(p, boundary, p.length, 2147483647);
/* 2474 */     Arrays.fill(d, 2147483647);
/*      */ 
/*      */     
/* 2477 */     for (int j = 1; j <= m; j++) {
/* 2478 */       char t_j = t.charAt(j - 1);
/* 2479 */       d[0] = j;
/*      */ 
/*      */       
/* 2482 */       int min = Math.max(1, j - threshold);
/* 2483 */       int max = (j > Integer.MAX_VALUE - threshold) ? n : Math.min(n, j + threshold);
/*      */ 
/*      */       
/* 2486 */       if (min > max) {
/* 2487 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 2491 */       if (min > 1) {
/* 2492 */         d[min - 1] = Integer.MAX_VALUE;
/*      */       }
/*      */ 
/*      */       
/* 2496 */       for (int k = min; k <= max; k++) {
/* 2497 */         if (s.charAt(k - 1) == t_j) {
/*      */           
/* 2499 */           d[k] = p[k - 1];
/*      */         } else {
/*      */           
/* 2502 */           d[k] = 1 + Math.min(Math.min(d[k - 1], p[k]), p[k - 1]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2507 */       int[] _d = p;
/* 2508 */       p = d;
/* 2509 */       d = _d;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2514 */     if (p[n] <= threshold) {
/* 2515 */       return p[n];
/*      */     }
/* 2517 */     return -1;
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
/*      */   public static int indexOf(CharSequence seq, CharSequence searchSeq) {
/* 2545 */     if (seq == null || searchSeq == null) {
/* 2546 */       return -1;
/*      */     }
/* 2548 */     return CharSequenceUtils.indexOf(seq, searchSeq, 0);
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
/*      */   public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
/* 2585 */     if (seq == null || searchSeq == null) {
/* 2586 */       return -1;
/*      */     }
/* 2588 */     return CharSequenceUtils.indexOf(seq, searchSeq, startPos);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(CharSequence seq, int searchChar) {
/* 2631 */     if (isEmpty(seq)) {
/* 2632 */       return -1;
/*      */     }
/* 2634 */     return CharSequenceUtils.indexOf(seq, searchChar, 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(CharSequence seq, int searchChar, int startPos) {
/* 2691 */     if (isEmpty(seq)) {
/* 2692 */       return -1;
/*      */     }
/* 2694 */     return CharSequenceUtils.indexOf(seq, searchChar, startPos);
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
/*      */   public static int indexOfAny(CharSequence cs, char... searchChars) {
/* 2723 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 2724 */       return -1;
/*      */     }
/* 2726 */     int csLen = cs.length();
/* 2727 */     int csLast = csLen - 1;
/* 2728 */     int searchLen = searchChars.length;
/* 2729 */     int searchLast = searchLen - 1;
/* 2730 */     for (int i = 0; i < csLen; i++) {
/* 2731 */       char ch = cs.charAt(i);
/* 2732 */       for (int j = 0; j < searchLen; j++) {
/* 2733 */         if (searchChars[j] == ch) {
/* 2734 */           if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) {
/*      */             
/* 2736 */             if (searchChars[j + 1] == cs.charAt(i + 1)) {
/* 2737 */               return i;
/*      */             }
/*      */           } else {
/* 2740 */             return i;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2745 */     return -1;
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
/*      */   public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
/* 2778 */     if (str == null || searchStrs == null) {
/* 2779 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 2783 */     int ret = Integer.MAX_VALUE;
/*      */     
/* 2785 */     int tmp = 0;
/* 2786 */     for (CharSequence search : searchStrs) {
/* 2787 */       if (search != null) {
/*      */ 
/*      */         
/* 2790 */         tmp = CharSequenceUtils.indexOf(str, search, 0);
/* 2791 */         if (tmp != -1)
/*      */         {
/*      */ 
/*      */           
/* 2795 */           if (tmp < ret)
/* 2796 */             ret = tmp; 
/*      */         }
/*      */       } 
/*      */     } 
/* 2800 */     return (ret == Integer.MAX_VALUE) ? -1 : ret;
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
/*      */   public static int indexOfAny(CharSequence cs, String searchChars) {
/* 2827 */     if (isEmpty(cs) || isEmpty(searchChars)) {
/* 2828 */       return -1;
/*      */     }
/* 2830 */     return indexOfAny(cs, searchChars.toCharArray());
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
/*      */   public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
/* 2860 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 2861 */       return -1;
/*      */     }
/* 2863 */     int csLen = cs.length();
/* 2864 */     int csLast = csLen - 1;
/* 2865 */     int searchLen = searchChars.length;
/* 2866 */     int searchLast = searchLen - 1;
/*      */     
/* 2868 */     for (int i = 0; i < csLen; i++) {
/* 2869 */       char ch = cs.charAt(i);
/* 2870 */       int j = 0; while (true) { if (j < searchLen) {
/* 2871 */           if (searchChars[j] == ch && (
/* 2872 */             i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch) || 
/* 2873 */             searchChars[j + 1] == cs.charAt(i + 1))) {
/*      */             break;
/*      */           }
/*      */           
/*      */           j++;
/*      */           
/*      */           continue;
/*      */         } 
/* 2881 */         return i; }
/*      */     
/* 2883 */     }  return -1;
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
/*      */   public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
/* 2910 */     if (isEmpty(seq) || isEmpty(searchChars)) {
/* 2911 */       return -1;
/*      */     }
/* 2913 */     int strLen = seq.length();
/* 2914 */     for (int i = 0; i < strLen; i++) {
/* 2915 */       char ch = seq.charAt(i);
/* 2916 */       boolean chFound = (CharSequenceUtils.indexOf(searchChars, ch, 0) >= 0);
/* 2917 */       if (i + 1 < strLen && Character.isHighSurrogate(ch)) {
/* 2918 */         char ch2 = seq.charAt(i + 1);
/* 2919 */         if (chFound && CharSequenceUtils.indexOf(searchChars, ch2, 0) < 0) {
/* 2920 */           return i;
/*      */         }
/*      */       }
/* 2923 */       else if (!chFound) {
/* 2924 */         return i;
/*      */       } 
/*      */     } 
/*      */     
/* 2928 */     return -1;
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
/*      */   public static int indexOfDifference(CharSequence... css) {
/* 2964 */     if (ArrayUtils.getLength(css) <= 1) {
/* 2965 */       return -1;
/*      */     }
/* 2967 */     boolean anyStringNull = false;
/* 2968 */     boolean allStringsNull = true;
/* 2969 */     int arrayLen = css.length;
/* 2970 */     int shortestStrLen = Integer.MAX_VALUE;
/* 2971 */     int longestStrLen = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2976 */     for (CharSequence cs : css) {
/* 2977 */       if (cs == null) {
/* 2978 */         anyStringNull = true;
/* 2979 */         shortestStrLen = 0;
/*      */       } else {
/* 2981 */         allStringsNull = false;
/* 2982 */         shortestStrLen = Math.min(cs.length(), shortestStrLen);
/* 2983 */         longestStrLen = Math.max(cs.length(), longestStrLen);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2988 */     if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
/* 2989 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 2993 */     if (shortestStrLen == 0) {
/* 2994 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2998 */     int firstDiff = -1;
/* 2999 */     for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
/* 3000 */       char comparisonChar = css[0].charAt(stringPos);
/* 3001 */       for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
/* 3002 */         if (css[arrayPos].charAt(stringPos) != comparisonChar) {
/* 3003 */           firstDiff = stringPos;
/*      */           break;
/*      */         } 
/*      */       } 
/* 3007 */       if (firstDiff != -1) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 3012 */     if (firstDiff == -1 && shortestStrLen != longestStrLen)
/*      */     {
/*      */ 
/*      */       
/* 3016 */       return shortestStrLen;
/*      */     }
/* 3018 */     return firstDiff;
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
/*      */   public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
/* 3047 */     if (cs1 == cs2) {
/* 3048 */       return -1;
/*      */     }
/* 3050 */     if (cs1 == null || cs2 == null) {
/* 3051 */       return 0;
/*      */     }
/*      */     int i;
/* 3054 */     for (i = 0; i < cs1.length() && i < cs2.length() && 
/* 3055 */       cs1.charAt(i) == cs2.charAt(i); i++);
/*      */ 
/*      */ 
/*      */     
/* 3059 */     if (i < cs2.length() || i < cs1.length()) {
/* 3060 */       return i;
/*      */     }
/* 3062 */     return -1;
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
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 3091 */     return indexOfIgnoreCase(str, searchStr, 0);
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
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 3127 */     if (str == null || searchStr == null) {
/* 3128 */       return -1;
/*      */     }
/* 3130 */     if (startPos < 0) {
/* 3131 */       startPos = 0;
/*      */     }
/* 3133 */     int endLimit = str.length() - searchStr.length() + 1;
/* 3134 */     if (startPos > endLimit) {
/* 3135 */       return -1;
/*      */     }
/* 3137 */     if (searchStr.length() == 0) {
/* 3138 */       return startPos;
/*      */     }
/* 3140 */     for (int i = startPos; i < endLimit; i++) {
/* 3141 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
/* 3142 */         return i;
/*      */       }
/*      */     } 
/* 3145 */     return -1;
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
/*      */   public static boolean isAllBlank(CharSequence... css) {
/* 3170 */     if (ArrayUtils.isEmpty((Object[])css)) {
/* 3171 */       return true;
/*      */     }
/* 3173 */     for (CharSequence cs : css) {
/* 3174 */       if (isNotBlank(cs)) {
/* 3175 */         return false;
/*      */       }
/*      */     } 
/* 3178 */     return true;
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
/*      */   public static boolean isAllEmpty(CharSequence... css) {
/* 3201 */     if (ArrayUtils.isEmpty((Object[])css)) {
/* 3202 */       return true;
/*      */     }
/* 3204 */     for (CharSequence cs : css) {
/* 3205 */       if (isNotEmpty(cs)) {
/* 3206 */         return false;
/*      */       }
/*      */     } 
/* 3209 */     return true;
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
/*      */   public static boolean isAllLowerCase(CharSequence cs) {
/* 3235 */     if (isEmpty(cs)) {
/* 3236 */       return false;
/*      */     }
/* 3238 */     int sz = cs.length();
/* 3239 */     for (int i = 0; i < sz; i++) {
/* 3240 */       if (!Character.isLowerCase(cs.charAt(i))) {
/* 3241 */         return false;
/*      */       }
/*      */     } 
/* 3244 */     return true;
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
/*      */   public static boolean isAllUpperCase(CharSequence cs) {
/* 3270 */     if (isEmpty(cs)) {
/* 3271 */       return false;
/*      */     }
/* 3273 */     int sz = cs.length();
/* 3274 */     for (int i = 0; i < sz; i++) {
/* 3275 */       if (!Character.isUpperCase(cs.charAt(i))) {
/* 3276 */         return false;
/*      */       }
/*      */     } 
/* 3279 */     return true;
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
/*      */   public static boolean isAlpha(CharSequence cs) {
/* 3305 */     if (isEmpty(cs)) {
/* 3306 */       return false;
/*      */     }
/* 3308 */     int sz = cs.length();
/* 3309 */     for (int i = 0; i < sz; i++) {
/* 3310 */       if (!Character.isLetter(cs.charAt(i))) {
/* 3311 */         return false;
/*      */       }
/*      */     } 
/* 3314 */     return true;
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
/*      */   public static boolean isAlphanumeric(CharSequence cs) {
/* 3340 */     if (isEmpty(cs)) {
/* 3341 */       return false;
/*      */     }
/* 3343 */     int sz = cs.length();
/* 3344 */     for (int i = 0; i < sz; i++) {
/* 3345 */       if (!Character.isLetterOrDigit(cs.charAt(i))) {
/* 3346 */         return false;
/*      */       }
/*      */     } 
/* 3349 */     return true;
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
/*      */   public static boolean isAlphanumericSpace(CharSequence cs) {
/* 3375 */     if (cs == null) {
/* 3376 */       return false;
/*      */     }
/* 3378 */     int sz = cs.length();
/* 3379 */     for (int i = 0; i < sz; i++) {
/* 3380 */       if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 3381 */         return false;
/*      */       }
/*      */     } 
/* 3384 */     return true;
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
/*      */   public static boolean isAlphaSpace(CharSequence cs) {
/* 3410 */     if (cs == null) {
/* 3411 */       return false;
/*      */     }
/* 3413 */     int sz = cs.length();
/* 3414 */     for (int i = 0; i < sz; i++) {
/* 3415 */       if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 3416 */         return false;
/*      */       }
/*      */     } 
/* 3419 */     return true;
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
/*      */   public static boolean isAnyBlank(CharSequence... css) {
/* 3446 */     if (ArrayUtils.isEmpty((Object[])css)) {
/* 3447 */       return false;
/*      */     }
/* 3449 */     for (CharSequence cs : css) {
/* 3450 */       if (isBlank(cs)) {
/* 3451 */         return true;
/*      */       }
/*      */     } 
/* 3454 */     return false;
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
/*      */   public static boolean isAnyEmpty(CharSequence... css) {
/* 3478 */     if (ArrayUtils.isEmpty((Object[])css)) {
/* 3479 */       return false;
/*      */     }
/* 3481 */     for (CharSequence cs : css) {
/* 3482 */       if (isEmpty(cs)) {
/* 3483 */         return true;
/*      */       }
/*      */     } 
/* 3486 */     return false;
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
/*      */   public static boolean isAsciiPrintable(CharSequence cs) {
/* 3516 */     if (cs == null) {
/* 3517 */       return false;
/*      */     }
/* 3519 */     int sz = cs.length();
/* 3520 */     for (int i = 0; i < sz; i++) {
/* 3521 */       if (!CharUtils.isAsciiPrintable(cs.charAt(i))) {
/* 3522 */         return false;
/*      */       }
/*      */     } 
/* 3525 */     return true;
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
/*      */   public static boolean isBlank(CharSequence cs) {
/* 3550 */     int strLen = length(cs);
/* 3551 */     if (strLen == 0) {
/* 3552 */       return true;
/*      */     }
/* 3554 */     for (int i = 0; i < strLen; i++) {
/* 3555 */       if (!Character.isWhitespace(cs.charAt(i))) {
/* 3556 */         return false;
/*      */       }
/*      */     } 
/* 3559 */     return true;
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
/*      */   public static boolean isEmpty(CharSequence cs) {
/* 3584 */     return (cs == null || cs.length() == 0);
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
/*      */   public static boolean isMixedCase(CharSequence cs) {
/* 3610 */     if (isEmpty(cs) || cs.length() == 1) {
/* 3611 */       return false;
/*      */     }
/* 3613 */     boolean containsUppercase = false;
/* 3614 */     boolean containsLowercase = false;
/* 3615 */     int sz = cs.length();
/* 3616 */     for (int i = 0; i < sz; i++) {
/* 3617 */       if (containsUppercase && containsLowercase)
/* 3618 */         return true; 
/* 3619 */       if (Character.isUpperCase(cs.charAt(i))) {
/* 3620 */         containsUppercase = true;
/* 3621 */       } else if (Character.isLowerCase(cs.charAt(i))) {
/* 3622 */         containsLowercase = true;
/*      */       } 
/*      */     } 
/* 3625 */     return (containsUppercase && containsLowercase);
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
/*      */   public static boolean isNoneBlank(CharSequence... css) {
/* 3652 */     return !isAnyBlank(css);
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
/*      */   public static boolean isNoneEmpty(CharSequence... css) {
/* 3676 */     return !isAnyEmpty(css);
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
/*      */   public static boolean isNotBlank(CharSequence cs) {
/* 3699 */     return !isBlank(cs);
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
/*      */   public static boolean isNotEmpty(CharSequence cs) {
/* 3718 */     return !isEmpty(cs);
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
/*      */   public static boolean isNumeric(CharSequence cs) {
/* 3753 */     if (isEmpty(cs)) {
/* 3754 */       return false;
/*      */     }
/* 3756 */     int sz = cs.length();
/* 3757 */     for (int i = 0; i < sz; i++) {
/* 3758 */       if (!Character.isDigit(cs.charAt(i))) {
/* 3759 */         return false;
/*      */       }
/*      */     } 
/* 3762 */     return true;
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
/*      */   public static boolean isNumericSpace(CharSequence cs) {
/* 3792 */     if (cs == null) {
/* 3793 */       return false;
/*      */     }
/* 3795 */     int sz = cs.length();
/* 3796 */     for (int i = 0; i < sz; i++) {
/* 3797 */       if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 3798 */         return false;
/*      */       }
/*      */     } 
/* 3801 */     return true;
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
/*      */   public static boolean isWhitespace(CharSequence cs) {
/* 3827 */     if (cs == null) {
/* 3828 */       return false;
/*      */     }
/* 3830 */     int sz = cs.length();
/* 3831 */     for (int i = 0; i < sz; i++) {
/* 3832 */       if (!Character.isWhitespace(cs.charAt(i))) {
/* 3833 */         return false;
/*      */       }
/*      */     } 
/* 3836 */     return true;
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
/*      */   public static String join(byte[] array, char separator) {
/* 3865 */     if (array == null) {
/* 3866 */       return null;
/*      */     }
/* 3868 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(byte[] array, char separator, int startIndex, int endIndex) {
/* 3903 */     if (array == null) {
/* 3904 */       return null;
/*      */     }
/* 3906 */     int noOfItems = endIndex - startIndex;
/* 3907 */     if (noOfItems <= 0) {
/* 3908 */       return "";
/*      */     }
/* 3910 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 3911 */     buf.append(array[startIndex]);
/* 3912 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 3913 */       buf.append(separator);
/* 3914 */       buf.append(array[i]);
/*      */     } 
/* 3916 */     return buf.toString();
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
/*      */   public static String join(char[] array, char separator) {
/* 3945 */     if (array == null) {
/* 3946 */       return null;
/*      */     }
/* 3948 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(char[] array, char separator, int startIndex, int endIndex) {
/* 3983 */     if (array == null) {
/* 3984 */       return null;
/*      */     }
/* 3986 */     int noOfItems = endIndex - startIndex;
/* 3987 */     if (noOfItems <= 0) {
/* 3988 */       return "";
/*      */     }
/* 3990 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 3991 */     buf.append(array[startIndex]);
/* 3992 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 3993 */       buf.append(separator);
/* 3994 */       buf.append(array[i]);
/*      */     } 
/* 3996 */     return buf.toString();
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
/*      */   public static String join(double[] array, char separator) {
/* 4025 */     if (array == null) {
/* 4026 */       return null;
/*      */     }
/* 4028 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(double[] array, char separator, int startIndex, int endIndex) {
/* 4063 */     if (array == null) {
/* 4064 */       return null;
/*      */     }
/* 4066 */     int noOfItems = endIndex - startIndex;
/* 4067 */     if (noOfItems <= 0) {
/* 4068 */       return "";
/*      */     }
/* 4070 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 4071 */     buf.append(array[startIndex]);
/* 4072 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4073 */       buf.append(separator);
/* 4074 */       buf.append(array[i]);
/*      */     } 
/* 4076 */     return buf.toString();
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
/*      */   public static String join(float[] array, char separator) {
/* 4105 */     if (array == null) {
/* 4106 */       return null;
/*      */     }
/* 4108 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(float[] array, char separator, int startIndex, int endIndex) {
/* 4143 */     if (array == null) {
/* 4144 */       return null;
/*      */     }
/* 4146 */     int noOfItems = endIndex - startIndex;
/* 4147 */     if (noOfItems <= 0) {
/* 4148 */       return "";
/*      */     }
/* 4150 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 4151 */     buf.append(array[startIndex]);
/* 4152 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4153 */       buf.append(separator);
/* 4154 */       buf.append(array[i]);
/*      */     } 
/* 4156 */     return buf.toString();
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
/*      */   public static String join(int[] array, char separator) {
/* 4185 */     if (array == null) {
/* 4186 */       return null;
/*      */     }
/* 4188 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(int[] array, char separator, int startIndex, int endIndex) {
/* 4223 */     if (array == null) {
/* 4224 */       return null;
/*      */     }
/* 4226 */     int noOfItems = endIndex - startIndex;
/* 4227 */     if (noOfItems <= 0) {
/* 4228 */       return "";
/*      */     }
/* 4230 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 4231 */     buf.append(array[startIndex]);
/* 4232 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4233 */       buf.append(separator);
/* 4234 */       buf.append(array[i]);
/*      */     } 
/* 4236 */     return buf.toString();
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
/*      */   public static String join(Iterable<?> iterable, char separator) {
/* 4254 */     if (iterable == null) {
/* 4255 */       return null;
/*      */     }
/* 4257 */     return join(iterable.iterator(), separator);
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
/*      */   public static String join(Iterable<?> iterable, String separator) {
/* 4275 */     if (iterable == null) {
/* 4276 */       return null;
/*      */     }
/* 4278 */     return join(iterable.iterator(), separator);
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
/*      */   public static String join(Iterator<?> iterator, char separator) {
/* 4298 */     if (iterator == null) {
/* 4299 */       return null;
/*      */     }
/* 4301 */     if (!iterator.hasNext()) {
/* 4302 */       return "";
/*      */     }
/* 4304 */     Object first = iterator.next();
/* 4305 */     if (!iterator.hasNext()) {
/* 4306 */       return Objects.toString(first, "");
/*      */     }
/*      */ 
/*      */     
/* 4310 */     StringBuilder buf = new StringBuilder(256);
/* 4311 */     if (first != null) {
/* 4312 */       buf.append(first);
/*      */     }
/*      */     
/* 4315 */     while (iterator.hasNext()) {
/* 4316 */       buf.append(separator);
/* 4317 */       Object obj = iterator.next();
/* 4318 */       if (obj != null) {
/* 4319 */         buf.append(obj);
/*      */       }
/*      */     } 
/*      */     
/* 4323 */     return buf.toString();
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
/*      */   public static String join(Iterator<?> iterator, String separator) {
/* 4342 */     if (iterator == null) {
/* 4343 */       return null;
/*      */     }
/* 4345 */     if (!iterator.hasNext()) {
/* 4346 */       return "";
/*      */     }
/* 4348 */     Object first = iterator.next();
/* 4349 */     if (!iterator.hasNext()) {
/* 4350 */       return Objects.toString(first, "");
/*      */     }
/*      */ 
/*      */     
/* 4354 */     StringBuilder buf = new StringBuilder(256);
/* 4355 */     if (first != null) {
/* 4356 */       buf.append(first);
/*      */     }
/*      */     
/* 4359 */     while (iterator.hasNext()) {
/* 4360 */       if (separator != null) {
/* 4361 */         buf.append(separator);
/*      */       }
/* 4363 */       Object obj = iterator.next();
/* 4364 */       if (obj != null) {
/* 4365 */         buf.append(obj);
/*      */       }
/*      */     } 
/* 4368 */     return buf.toString();
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
/*      */   public static String join(List<?> list, char separator, int startIndex, int endIndex) {
/* 4398 */     if (list == null) {
/* 4399 */       return null;
/*      */     }
/* 4401 */     int noOfItems = endIndex - startIndex;
/* 4402 */     if (noOfItems <= 0) {
/* 4403 */       return "";
/*      */     }
/* 4405 */     List<?> subList = list.subList(startIndex, endIndex);
/* 4406 */     return join(subList.iterator(), separator);
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
/*      */   public static String join(List<?> list, String separator, int startIndex, int endIndex) {
/* 4436 */     if (list == null) {
/* 4437 */       return null;
/*      */     }
/* 4439 */     int noOfItems = endIndex - startIndex;
/* 4440 */     if (noOfItems <= 0) {
/* 4441 */       return "";
/*      */     }
/* 4443 */     List<?> subList = list.subList(startIndex, endIndex);
/* 4444 */     return join(subList.iterator(), separator);
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
/*      */   public static String join(long[] array, char separator) {
/* 4474 */     if (array == null) {
/* 4475 */       return null;
/*      */     }
/* 4477 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(long[] array, char separator, int startIndex, int endIndex) {
/* 4512 */     if (array == null) {
/* 4513 */       return null;
/*      */     }
/* 4515 */     int noOfItems = endIndex - startIndex;
/* 4516 */     if (noOfItems <= 0) {
/* 4517 */       return "";
/*      */     }
/* 4519 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 4520 */     buf.append(array[startIndex]);
/* 4521 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4522 */       buf.append(separator);
/* 4523 */       buf.append(array[i]);
/*      */     } 
/* 4525 */     return buf.toString();
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
/*      */   public static String join(Object[] array, char separator) {
/* 4551 */     if (array == null) {
/* 4552 */       return null;
/*      */     }
/* 4554 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(Object[] array, char separator, int startIndex, int endIndex) {
/* 4584 */     if (array == null) {
/* 4585 */       return null;
/*      */     }
/* 4587 */     int noOfItems = endIndex - startIndex;
/* 4588 */     if (noOfItems <= 0) {
/* 4589 */       return "";
/*      */     }
/* 4591 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 4592 */     if (array[startIndex] != null) {
/* 4593 */       buf.append(array[startIndex]);
/*      */     }
/* 4595 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4596 */       buf.append(separator);
/* 4597 */       if (array[i] != null) {
/* 4598 */         buf.append(array[i]);
/*      */       }
/*      */     } 
/* 4601 */     return buf.toString();
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
/*      */   public static String join(Object[] array, String separator) {
/* 4628 */     if (array == null) {
/* 4629 */       return null;
/*      */     }
/* 4631 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(Object[] array, String separator, int startIndex, int endIndex) {
/* 4670 */     if (array == null) {
/* 4671 */       return null;
/*      */     }
/* 4673 */     if (separator == null) {
/* 4674 */       separator = "";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4679 */     int noOfItems = endIndex - startIndex;
/* 4680 */     if (noOfItems <= 0) {
/* 4681 */       return "";
/*      */     }
/*      */     
/* 4684 */     StringBuilder buf = newStringBuilder(noOfItems);
/*      */     
/* 4686 */     if (array[startIndex] != null) {
/* 4687 */       buf.append(array[startIndex]);
/*      */     }
/*      */     
/* 4690 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4691 */       buf.append(separator);
/*      */       
/* 4693 */       if (array[i] != null) {
/* 4694 */         buf.append(array[i]);
/*      */       }
/*      */     } 
/* 4697 */     return buf.toString();
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
/*      */   public static String join(short[] array, char separator) {
/* 4726 */     if (array == null) {
/* 4727 */       return null;
/*      */     }
/* 4729 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(short[] array, char separator, int startIndex, int endIndex) {
/* 4764 */     if (array == null) {
/* 4765 */       return null;
/*      */     }
/* 4767 */     int noOfItems = endIndex - startIndex;
/* 4768 */     if (noOfItems <= 0) {
/* 4769 */       return "";
/*      */     }
/* 4771 */     StringBuilder buf = newStringBuilder(noOfItems);
/* 4772 */     buf.append(array[startIndex]);
/* 4773 */     for (int i = startIndex + 1; i < endIndex; i++) {
/* 4774 */       buf.append(separator);
/* 4775 */       buf.append(array[i]);
/*      */     } 
/* 4777 */     return buf.toString();
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
/*      */   @SafeVarargs
/*      */   public static <T> String join(T... elements) {
/* 4807 */     return join((Object[])elements, (String)null);
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
/*      */   public static String joinWith(String separator, Object... objects) {
/* 4831 */     if (objects == null) {
/* 4832 */       throw new IllegalArgumentException("Object varargs must not be null");
/*      */     }
/*      */     
/* 4835 */     String sanitizedSeparator = defaultString(separator);
/*      */     
/* 4837 */     StringBuilder result = new StringBuilder();
/*      */     
/* 4839 */     Iterator<Object> iterator = Arrays.<Object>asList(objects).iterator();
/* 4840 */     while (iterator.hasNext()) {
/* 4841 */       String value = Objects.toString(iterator.next(), "");
/* 4842 */       result.append(value);
/*      */       
/* 4844 */       if (iterator.hasNext()) {
/* 4845 */         result.append(sanitizedSeparator);
/*      */       }
/*      */     } 
/*      */     
/* 4849 */     return result.toString();
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
/*      */   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
/* 4876 */     if (seq == null || searchSeq == null) {
/* 4877 */       return -1;
/*      */     }
/* 4879 */     return CharSequenceUtils.lastIndexOf(seq, searchSeq, seq.length());
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
/*      */   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
/* 4918 */     if (seq == null || searchSeq == null) {
/* 4919 */       return -1;
/*      */     }
/* 4921 */     return CharSequenceUtils.lastIndexOf(seq, searchSeq, startPos);
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
/*      */   public static int lastIndexOf(CharSequence seq, int searchChar) {
/* 4961 */     if (isEmpty(seq)) {
/* 4962 */       return -1;
/*      */     }
/* 4964 */     return CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
/* 5012 */     if (isEmpty(seq)) {
/* 5013 */       return -1;
/*      */     }
/* 5015 */     return CharSequenceUtils.lastIndexOf(seq, searchChar, startPos);
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
/*      */   public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
/* 5045 */     if (str == null || searchStrs == null) {
/* 5046 */       return -1;
/*      */     }
/* 5048 */     int ret = -1;
/* 5049 */     int tmp = 0;
/* 5050 */     for (CharSequence search : searchStrs) {
/* 5051 */       if (search != null) {
/*      */ 
/*      */         
/* 5054 */         tmp = CharSequenceUtils.lastIndexOf(str, search, str.length());
/* 5055 */         if (tmp > ret)
/* 5056 */           ret = tmp; 
/*      */       } 
/*      */     } 
/* 5059 */     return ret;
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 5086 */     if (str == null || searchStr == null) {
/* 5087 */       return -1;
/*      */     }
/* 5089 */     return lastIndexOfIgnoreCase(str, searchStr, str.length());
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 5125 */     if (str == null || searchStr == null) {
/* 5126 */       return -1;
/*      */     }
/* 5128 */     if (startPos > str.length() - searchStr.length()) {
/* 5129 */       startPos = str.length() - searchStr.length();
/*      */     }
/* 5131 */     if (startPos < 0) {
/* 5132 */       return -1;
/*      */     }
/* 5134 */     if (searchStr.length() == 0) {
/* 5135 */       return startPos;
/*      */     }
/*      */     
/* 5138 */     for (int i = startPos; i >= 0; i--) {
/* 5139 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
/* 5140 */         return i;
/*      */       }
/*      */     } 
/* 5143 */     return -1;
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
/*      */   public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
/* 5181 */     return ordinalIndexOf(str, searchStr, ordinal, true);
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
/*      */   public static String left(String str, int len) {
/* 5207 */     if (str == null) {
/* 5208 */       return null;
/*      */     }
/* 5210 */     if (len < 0) {
/* 5211 */       return "";
/*      */     }
/* 5213 */     if (str.length() <= len) {
/* 5214 */       return str;
/*      */     }
/* 5216 */     return str.substring(0, len);
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
/*      */   public static String leftPad(String str, int size) {
/* 5239 */     return leftPad(str, size, ' ');
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
/*      */   public static String leftPad(String str, int size, char padChar) {
/* 5264 */     if (str == null) {
/* 5265 */       return null;
/*      */     }
/* 5267 */     int pads = size - str.length();
/* 5268 */     if (pads <= 0) {
/* 5269 */       return str;
/*      */     }
/* 5271 */     if (pads > 8192) {
/* 5272 */       return leftPad(str, size, String.valueOf(padChar));
/*      */     }
/* 5274 */     return repeat(padChar, pads).concat(str);
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
/*      */   public static String leftPad(String str, int size, String padStr) {
/* 5301 */     if (str == null) {
/* 5302 */       return null;
/*      */     }
/* 5304 */     if (isEmpty(padStr)) {
/* 5305 */       padStr = " ";
/*      */     }
/* 5307 */     int padLen = padStr.length();
/* 5308 */     int strLen = str.length();
/* 5309 */     int pads = size - strLen;
/* 5310 */     if (pads <= 0) {
/* 5311 */       return str;
/*      */     }
/* 5313 */     if (padLen == 1 && pads <= 8192) {
/* 5314 */       return leftPad(str, size, padStr.charAt(0));
/*      */     }
/*      */     
/* 5317 */     if (pads == padLen)
/* 5318 */       return padStr.concat(str); 
/* 5319 */     if (pads < padLen) {
/* 5320 */       return padStr.substring(0, pads).concat(str);
/*      */     }
/* 5322 */     char[] padding = new char[pads];
/* 5323 */     char[] padChars = padStr.toCharArray();
/* 5324 */     for (int i = 0; i < pads; i++) {
/* 5325 */       padding[i] = padChars[i % padLen];
/*      */     }
/* 5327 */     return (new String(padding)).concat(str);
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
/*      */   public static int length(CharSequence cs) {
/* 5343 */     return (cs == null) ? 0 : cs.length();
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
/*      */   public static String lowerCase(String str) {
/* 5366 */     if (str == null) {
/* 5367 */       return null;
/*      */     }
/* 5369 */     return str.toLowerCase();
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
/*      */   public static String lowerCase(String str, Locale locale) {
/* 5389 */     if (str == null) {
/* 5390 */       return null;
/*      */     }
/* 5392 */     return str.toLowerCase(locale);
/*      */   }
/*      */   
/*      */   private static int[] matches(CharSequence first, CharSequence second) {
/*      */     CharSequence max, min;
/* 5397 */     if (first.length() > second.length()) {
/* 5398 */       max = first;
/* 5399 */       min = second;
/*      */     } else {
/* 5401 */       max = second;
/* 5402 */       min = first;
/*      */     } 
/* 5404 */     int range = Math.max(max.length() / 2 - 1, 0);
/* 5405 */     int[] matchIndexes = new int[min.length()];
/* 5406 */     Arrays.fill(matchIndexes, -1);
/* 5407 */     boolean[] matchFlags = new boolean[max.length()];
/* 5408 */     int matches = 0;
/* 5409 */     for (int mi = 0; mi < min.length(); mi++) {
/* 5410 */       char c1 = min.charAt(mi);
/* 5411 */       for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
/* 5412 */         if (!matchFlags[xi] && c1 == max.charAt(xi)) {
/* 5413 */           matchIndexes[mi] = xi;
/* 5414 */           matchFlags[xi] = true;
/* 5415 */           matches++;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 5420 */     char[] ms1 = new char[matches];
/* 5421 */     char[] ms2 = new char[matches]; int i, si;
/* 5422 */     for (i = 0, si = 0; i < min.length(); i++) {
/* 5423 */       if (matchIndexes[i] != -1) {
/* 5424 */         ms1[si] = min.charAt(i);
/* 5425 */         si++;
/*      */       } 
/*      */     } 
/* 5428 */     for (i = 0, si = 0; i < max.length(); i++) {
/* 5429 */       if (matchFlags[i]) {
/* 5430 */         ms2[si] = max.charAt(i);
/* 5431 */         si++;
/*      */       } 
/*      */     } 
/* 5434 */     int transpositions = 0;
/* 5435 */     for (int j = 0; j < ms1.length; j++) {
/* 5436 */       if (ms1[j] != ms2[j]) {
/* 5437 */         transpositions++;
/*      */       }
/*      */     } 
/* 5440 */     int prefix = 0;
/* 5441 */     for (int k = 0; k < min.length() && 
/* 5442 */       first.charAt(k) == second.charAt(k); k++) {
/* 5443 */       prefix++;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 5448 */     return new int[] { matches, transpositions / 2, prefix, max.length() };
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
/*      */   public static String mid(String str, int pos, int len) {
/* 5477 */     if (str == null) {
/* 5478 */       return null;
/*      */     }
/* 5480 */     if (len < 0 || pos > str.length()) {
/* 5481 */       return "";
/*      */     }
/* 5483 */     if (pos < 0) {
/* 5484 */       pos = 0;
/*      */     }
/* 5486 */     if (str.length() <= pos + len) {
/* 5487 */       return str.substring(pos);
/*      */     }
/* 5489 */     return str.substring(pos, pos + len);
/*      */   }
/*      */   
/*      */   private static StringBuilder newStringBuilder(int noOfItems) {
/* 5493 */     return new StringBuilder(noOfItems * 16);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalizeSpace(String str) {
/* 5540 */     if (isEmpty(str)) {
/* 5541 */       return str;
/*      */     }
/* 5543 */     int size = str.length();
/* 5544 */     char[] newChars = new char[size];
/* 5545 */     int count = 0;
/* 5546 */     int whitespacesCount = 0;
/* 5547 */     boolean startWhitespaces = true;
/* 5548 */     for (int i = 0; i < size; i++) {
/* 5549 */       char actualChar = str.charAt(i);
/* 5550 */       boolean isWhitespace = Character.isWhitespace(actualChar);
/* 5551 */       if (isWhitespace) {
/* 5552 */         if (whitespacesCount == 0 && !startWhitespaces) {
/* 5553 */           newChars[count++] = " ".charAt(0);
/*      */         }
/* 5555 */         whitespacesCount++;
/*      */       } else {
/* 5557 */         startWhitespaces = false;
/* 5558 */         newChars[count++] = (actualChar == ' ') ? ' ' : actualChar;
/* 5559 */         whitespacesCount = 0;
/*      */       } 
/*      */     } 
/* 5562 */     if (startWhitespaces) {
/* 5563 */       return "";
/*      */     }
/* 5565 */     return (new String(newChars, 0, count - ((whitespacesCount > 0) ? 1 : 0))).trim();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
/* 5619 */     return ordinalIndexOf(str, searchStr, ordinal, false);
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
/*      */   private static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal, boolean lastIndex) {
/* 5638 */     if (str == null || searchStr == null || ordinal <= 0) {
/* 5639 */       return -1;
/*      */     }
/* 5641 */     if (searchStr.length() == 0) {
/* 5642 */       return lastIndex ? str.length() : 0;
/*      */     }
/* 5644 */     int found = 0;
/*      */ 
/*      */     
/* 5647 */     int index = lastIndex ? str.length() : -1;
/*      */     while (true) {
/* 5649 */       if (lastIndex) {
/* 5650 */         index = CharSequenceUtils.lastIndexOf(str, searchStr, index - 1);
/*      */       } else {
/* 5652 */         index = CharSequenceUtils.indexOf(str, searchStr, index + 1);
/*      */       } 
/* 5654 */       if (index < 0) {
/* 5655 */         return index;
/*      */       }
/* 5657 */       found++;
/* 5658 */       if (found >= ordinal) {
/* 5659 */         return index;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String overlay(String str, String overlay, int start, int end) {
/* 5694 */     if (str == null) {
/* 5695 */       return null;
/*      */     }
/* 5697 */     if (overlay == null) {
/* 5698 */       overlay = "";
/*      */     }
/* 5700 */     int len = str.length();
/* 5701 */     if (start < 0) {
/* 5702 */       start = 0;
/*      */     }
/* 5704 */     if (start > len) {
/* 5705 */       start = len;
/*      */     }
/* 5707 */     if (end < 0) {
/* 5708 */       end = 0;
/*      */     }
/* 5710 */     if (end > len) {
/* 5711 */       end = len;
/*      */     }
/* 5713 */     if (start > end) {
/* 5714 */       int temp = start;
/* 5715 */       start = end;
/* 5716 */       end = temp;
/*      */     } 
/* 5718 */     return str.substring(0, start) + overlay + str
/*      */       
/* 5720 */       .substring(end);
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
/*      */   private static String prependIfMissing(String str, CharSequence prefix, boolean ignoreCase, CharSequence... prefixes) {
/* 5735 */     if (str == null || isEmpty(prefix) || startsWith(str, prefix, ignoreCase)) {
/* 5736 */       return str;
/*      */     }
/* 5738 */     if (ArrayUtils.isNotEmpty(prefixes)) {
/* 5739 */       for (CharSequence p : prefixes) {
/* 5740 */         if (startsWith(str, p, ignoreCase)) {
/* 5741 */           return str;
/*      */         }
/*      */       } 
/*      */     }
/* 5745 */     return prefix.toString() + str;
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
/*      */   public static String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
/* 5783 */     return prependIfMissing(str, prefix, false, prefixes);
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
/*      */   public static String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
/* 5821 */     return prependIfMissing(str, prefix, true, prefixes);
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
/*      */   public static String remove(String str, char remove) {
/* 5844 */     if (isEmpty(str) || str.indexOf(remove) == -1) {
/* 5845 */       return str;
/*      */     }
/* 5847 */     char[] chars = str.toCharArray();
/* 5848 */     int pos = 0;
/* 5849 */     for (int i = 0; i < chars.length; i++) {
/* 5850 */       if (chars[i] != remove) {
/* 5851 */         chars[pos++] = chars[i];
/*      */       }
/*      */     } 
/* 5854 */     return new String(chars, 0, pos);
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
/*      */   public static String remove(String str, String remove) {
/* 5881 */     if (isEmpty(str) || isEmpty(remove)) {
/* 5882 */       return str;
/*      */     }
/* 5884 */     return replace(str, remove, "", -1);
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
/*      */   public static String removeAll(String text, String regex) {
/* 5934 */     return RegExUtils.removeAll(text, regex);
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
/*      */   public static String removeEnd(String str, String remove) {
/* 5962 */     if (isEmpty(str) || isEmpty(remove)) {
/* 5963 */       return str;
/*      */     }
/* 5965 */     if (str.endsWith(remove)) {
/* 5966 */       return str.substring(0, str.length() - remove.length());
/*      */     }
/* 5968 */     return str;
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
/*      */   public static String removeEndIgnoreCase(String str, String remove) {
/* 5998 */     if (isEmpty(str) || isEmpty(remove)) {
/* 5999 */       return str;
/*      */     }
/* 6001 */     if (endsWithIgnoreCase(str, remove)) {
/* 6002 */       return str.substring(0, str.length() - remove.length());
/*      */     }
/* 6004 */     return str;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String removeFirst(String text, String regex) {
/* 6053 */     return replaceFirst(text, regex, "");
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
/*      */   public static String removeIgnoreCase(String str, String remove) {
/* 6090 */     if (isEmpty(str) || isEmpty(remove)) {
/* 6091 */       return str;
/*      */     }
/* 6093 */     return replaceIgnoreCase(str, remove, "", -1);
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
/*      */   @Deprecated
/*      */   public static String removePattern(String source, String regex) {
/* 6130 */     return RegExUtils.removePattern(source, regex);
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
/*      */   public static String removeStart(String str, String remove) {
/* 6160 */     if (isEmpty(str) || isEmpty(remove)) {
/* 6161 */       return str;
/*      */     }
/* 6163 */     if (str.startsWith(remove)) {
/* 6164 */       return str.substring(remove.length());
/*      */     }
/* 6166 */     return str;
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
/*      */   public static String removeStartIgnoreCase(String str, String remove) {
/* 6195 */     if (isEmpty(str) || isEmpty(remove)) {
/* 6196 */       return str;
/*      */     }
/* 6198 */     if (startsWithIgnoreCase(str, remove)) {
/* 6199 */       return str.substring(remove.length());
/*      */     }
/* 6201 */     return str;
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
/*      */   public static String repeat(char ch, int repeat) {
/* 6227 */     if (repeat <= 0) {
/* 6228 */       return "";
/*      */     }
/* 6230 */     char[] buf = new char[repeat];
/* 6231 */     for (int i = repeat - 1; i >= 0; i--) {
/* 6232 */       buf[i] = ch;
/*      */     }
/* 6234 */     return new String(buf);
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
/*      */   public static String repeat(String str, int repeat) {
/*      */     char ch0, ch1, output2[];
/*      */     int i;
/* 6260 */     if (str == null) {
/* 6261 */       return null;
/*      */     }
/* 6263 */     if (repeat <= 0) {
/* 6264 */       return "";
/*      */     }
/* 6266 */     int inputLength = str.length();
/* 6267 */     if (repeat == 1 || inputLength == 0) {
/* 6268 */       return str;
/*      */     }
/* 6270 */     if (inputLength == 1 && repeat <= 8192) {
/* 6271 */       return repeat(str.charAt(0), repeat);
/*      */     }
/*      */     
/* 6274 */     int outputLength = inputLength * repeat;
/* 6275 */     switch (inputLength) {
/*      */       case 1:
/* 6277 */         return repeat(str.charAt(0), repeat);
/*      */       case 2:
/* 6279 */         ch0 = str.charAt(0);
/* 6280 */         ch1 = str.charAt(1);
/* 6281 */         output2 = new char[outputLength];
/* 6282 */         for (i = repeat * 2 - 2; i >= 0; i--, i--) {
/* 6283 */           output2[i] = ch0;
/* 6284 */           output2[i + 1] = ch1;
/*      */         } 
/* 6286 */         return new String(output2);
/*      */     } 
/* 6288 */     StringBuilder buf = new StringBuilder(outputLength);
/* 6289 */     for (int j = 0; j < repeat; j++) {
/* 6290 */       buf.append(str);
/*      */     }
/* 6292 */     return buf.toString();
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
/*      */   public static String repeat(String str, String separator, int repeat) {
/* 6320 */     if (str == null || separator == null) {
/* 6321 */       return repeat(str, repeat);
/*      */     }
/*      */     
/* 6324 */     String result = repeat(str + separator, repeat);
/* 6325 */     return removeEnd(result, separator);
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
/*      */   public static String replace(String text, String searchString, String replacement) {
/* 6352 */     return replace(text, searchString, replacement, -1);
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
/*      */   public static String replace(String text, String searchString, String replacement, int max) {
/* 6384 */     return replace(text, searchString, replacement, max, false);
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
/*      */   private static String replace(String text, String searchString, String replacement, int max, boolean ignoreCase) {
/* 6419 */     if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
/* 6420 */       return text;
/*      */     }
/* 6422 */     if (ignoreCase) {
/* 6423 */       searchString = searchString.toLowerCase();
/*      */     }
/* 6425 */     int start = 0;
/* 6426 */     int end = ignoreCase ? indexOfIgnoreCase(text, searchString, start) : indexOf(text, searchString, start);
/* 6427 */     if (end == -1) {
/* 6428 */       return text;
/*      */     }
/* 6430 */     int replLength = searchString.length();
/* 6431 */     int increase = Math.max(replacement.length() - replLength, 0);
/* 6432 */     increase *= (max < 0) ? 16 : Math.min(max, 64);
/* 6433 */     StringBuilder buf = new StringBuilder(text.length() + increase);
/* 6434 */     while (end != -1) {
/* 6435 */       buf.append(text, start, end).append(replacement);
/* 6436 */       start = end + replLength;
/* 6437 */       if (--max == 0) {
/*      */         break;
/*      */       }
/* 6440 */       end = ignoreCase ? indexOfIgnoreCase(text, searchString, start) : indexOf(text, searchString, start);
/*      */     } 
/* 6442 */     buf.append(text, start, text.length());
/* 6443 */     return buf.toString();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static String replaceAll(String text, String regex, String replacement) {
/* 6498 */     return RegExUtils.replaceAll(text, regex, replacement);
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
/*      */   public static String replaceChars(String str, char searchChar, char replaceChar) {
/* 6524 */     if (str == null) {
/* 6525 */       return null;
/*      */     }
/* 6527 */     return str.replace(searchChar, replaceChar);
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
/*      */   public static String replaceChars(String str, String searchChars, String replaceChars) {
/* 6567 */     if (isEmpty(str) || isEmpty(searchChars)) {
/* 6568 */       return str;
/*      */     }
/* 6570 */     if (replaceChars == null) {
/* 6571 */       replaceChars = "";
/*      */     }
/* 6573 */     boolean modified = false;
/* 6574 */     int replaceCharsLength = replaceChars.length();
/* 6575 */     int strLength = str.length();
/* 6576 */     StringBuilder buf = new StringBuilder(strLength);
/* 6577 */     for (int i = 0; i < strLength; i++) {
/* 6578 */       char ch = str.charAt(i);
/* 6579 */       int index = searchChars.indexOf(ch);
/* 6580 */       if (index >= 0) {
/* 6581 */         modified = true;
/* 6582 */         if (index < replaceCharsLength) {
/* 6583 */           buf.append(replaceChars.charAt(index));
/*      */         }
/*      */       } else {
/* 6586 */         buf.append(ch);
/*      */       } 
/*      */     } 
/* 6589 */     if (modified) {
/* 6590 */       return buf.toString();
/*      */     }
/* 6592 */     return str;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replaceEach(String text, String[] searchList, String[] replacementList) {
/* 6635 */     return replaceEach(text, searchList, replacementList, false, 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
/* 6695 */     if (timeToLive < 0) {
/* 6696 */       Set<String> searchSet = new HashSet<>(Arrays.asList(searchList));
/* 6697 */       Set<String> replacementSet = new HashSet<>(Arrays.asList(replacementList));
/* 6698 */       searchSet.retainAll(replacementSet);
/* 6699 */       if (searchSet.size() > 0) {
/* 6700 */         throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 6705 */     if (isEmpty(text) || ArrayUtils.isEmpty((Object[])searchList) || ArrayUtils.isEmpty((Object[])replacementList) || (ArrayUtils.isNotEmpty(searchList) && timeToLive == -1)) {
/* 6706 */       return text;
/*      */     }
/*      */     
/* 6709 */     int searchLength = searchList.length;
/* 6710 */     int replacementLength = replacementList.length;
/*      */ 
/*      */     
/* 6713 */     if (searchLength != replacementLength) {
/* 6714 */       throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6721 */     boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
/*      */ 
/*      */     
/* 6724 */     int textIndex = -1;
/* 6725 */     int replaceIndex = -1;
/* 6726 */     int tempIndex = -1;
/*      */ 
/*      */ 
/*      */     
/* 6730 */     for (int i = 0; i < searchLength; i++) {
/* 6731 */       if (!noMoreMatchesForReplIndex[i] && !isEmpty(searchList[i]) && replacementList[i] != null) {
/*      */ 
/*      */         
/* 6734 */         tempIndex = text.indexOf(searchList[i]);
/*      */ 
/*      */         
/* 6737 */         if (tempIndex == -1) {
/* 6738 */           noMoreMatchesForReplIndex[i] = true;
/*      */         }
/* 6740 */         else if (textIndex == -1 || tempIndex < textIndex) {
/* 6741 */           textIndex = tempIndex;
/* 6742 */           replaceIndex = i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6749 */     if (textIndex == -1) {
/* 6750 */       return text;
/*      */     }
/*      */     
/* 6753 */     int start = 0;
/*      */ 
/*      */     
/* 6756 */     int increase = 0;
/*      */ 
/*      */     
/* 6759 */     for (int j = 0; j < searchList.length; j++) {
/* 6760 */       if (searchList[j] != null && replacementList[j] != null) {
/*      */ 
/*      */         
/* 6763 */         int greater = replacementList[j].length() - searchList[j].length();
/* 6764 */         if (greater > 0) {
/* 6765 */           increase += 3 * greater;
/*      */         }
/*      */       } 
/*      */     } 
/* 6769 */     increase = Math.min(increase, text.length() / 5);
/*      */     
/* 6771 */     StringBuilder buf = new StringBuilder(text.length() + increase);
/*      */     
/* 6773 */     while (textIndex != -1) {
/*      */       int m;
/* 6775 */       for (m = start; m < textIndex; m++) {
/* 6776 */         buf.append(text.charAt(m));
/*      */       }
/* 6778 */       buf.append(replacementList[replaceIndex]);
/*      */       
/* 6780 */       start = textIndex + searchList[replaceIndex].length();
/*      */       
/* 6782 */       textIndex = -1;
/* 6783 */       replaceIndex = -1;
/* 6784 */       tempIndex = -1;
/*      */ 
/*      */       
/* 6787 */       for (m = 0; m < searchLength; m++) {
/* 6788 */         if (!noMoreMatchesForReplIndex[m] && searchList[m] != null && 
/* 6789 */           !searchList[m].isEmpty() && replacementList[m] != null) {
/*      */ 
/*      */           
/* 6792 */           tempIndex = text.indexOf(searchList[m], start);
/*      */ 
/*      */           
/* 6795 */           if (tempIndex == -1) {
/* 6796 */             noMoreMatchesForReplIndex[m] = true;
/*      */           }
/* 6798 */           else if (textIndex == -1 || tempIndex < textIndex) {
/* 6799 */             textIndex = tempIndex;
/* 6800 */             replaceIndex = m;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6807 */     int textLength = text.length();
/* 6808 */     for (int k = start; k < textLength; k++) {
/* 6809 */       buf.append(text.charAt(k));
/*      */     }
/* 6811 */     String result = buf.toString();
/* 6812 */     if (!repeat) {
/* 6813 */       return result;
/*      */     }
/*      */     
/* 6816 */     return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
/* 6864 */     int timeToLive = (searchList == null) ? 0 : searchList.length;
/* 6865 */     return replaceEach(text, searchList, replacementList, true, timeToLive);
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
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static String replaceFirst(String text, String regex, String replacement) {
/* 6918 */     return RegExUtils.replaceFirst(text, regex, replacement);
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
/*      */   public static String replaceIgnoreCase(String text, String searchString, String replacement) {
/* 6946 */     return replaceIgnoreCase(text, searchString, replacement, -1);
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
/*      */   public static String replaceIgnoreCase(String text, String searchString, String replacement, int max) {
/* 6979 */     return replace(text, searchString, replacement, max, true);
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
/*      */   public static String replaceOnce(String text, String searchString, String replacement) {
/* 7008 */     return replace(text, searchString, replacement, 1);
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
/*      */   public static String replaceOnceIgnoreCase(String text, String searchString, String replacement) {
/* 7037 */     return replaceIgnoreCase(text, searchString, replacement, 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String replacePattern(String source, String regex, String replacement) {
/* 7083 */     return RegExUtils.replacePattern(source, regex, replacement);
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
/*      */   public static String reverse(String str) {
/* 7103 */     if (str == null) {
/* 7104 */       return null;
/*      */     }
/* 7106 */     return (new StringBuilder(str)).reverse().toString();
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
/*      */   public static String reverseDelimited(String str, char separatorChar) {
/* 7129 */     if (str == null) {
/* 7130 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 7134 */     String[] strs = split(str, separatorChar);
/* 7135 */     ArrayUtils.reverse((Object[])strs);
/* 7136 */     return join((Object[])strs, separatorChar);
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
/*      */   public static String right(String str, int len) {
/* 7160 */     if (str == null) {
/* 7161 */       return null;
/*      */     }
/* 7163 */     if (len < 0) {
/* 7164 */       return "";
/*      */     }
/* 7166 */     if (str.length() <= len) {
/* 7167 */       return str;
/*      */     }
/* 7169 */     return str.substring(str.length() - len);
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
/*      */   public static String rightPad(String str, int size) {
/* 7192 */     return rightPad(str, size, ' ');
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
/*      */   public static String rightPad(String str, int size, char padChar) {
/* 7217 */     if (str == null) {
/* 7218 */       return null;
/*      */     }
/* 7220 */     int pads = size - str.length();
/* 7221 */     if (pads <= 0) {
/* 7222 */       return str;
/*      */     }
/* 7224 */     if (pads > 8192) {
/* 7225 */       return rightPad(str, size, String.valueOf(padChar));
/*      */     }
/* 7227 */     return str.concat(repeat(padChar, pads));
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
/*      */   public static String rightPad(String str, int size, String padStr) {
/* 7254 */     if (str == null) {
/* 7255 */       return null;
/*      */     }
/* 7257 */     if (isEmpty(padStr)) {
/* 7258 */       padStr = " ";
/*      */     }
/* 7260 */     int padLen = padStr.length();
/* 7261 */     int strLen = str.length();
/* 7262 */     int pads = size - strLen;
/* 7263 */     if (pads <= 0) {
/* 7264 */       return str;
/*      */     }
/* 7266 */     if (padLen == 1 && pads <= 8192) {
/* 7267 */       return rightPad(str, size, padStr.charAt(0));
/*      */     }
/*      */     
/* 7270 */     if (pads == padLen)
/* 7271 */       return str.concat(padStr); 
/* 7272 */     if (pads < padLen) {
/* 7273 */       return str.concat(padStr.substring(0, pads));
/*      */     }
/* 7275 */     char[] padding = new char[pads];
/* 7276 */     char[] padChars = padStr.toCharArray();
/* 7277 */     for (int i = 0; i < pads; i++) {
/* 7278 */       padding[i] = padChars[i % padLen];
/*      */     }
/* 7280 */     return str.concat(new String(padding));
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
/*      */   public static String rotate(String str, int shift) {
/* 7313 */     if (str == null) {
/* 7314 */       return null;
/*      */     }
/*      */     
/* 7317 */     int strLen = str.length();
/* 7318 */     if (shift == 0 || strLen == 0 || shift % strLen == 0) {
/* 7319 */       return str;
/*      */     }
/*      */     
/* 7322 */     StringBuilder builder = new StringBuilder(strLen);
/* 7323 */     int offset = -(shift % strLen);
/* 7324 */     builder.append(substring(str, offset));
/* 7325 */     builder.append(substring(str, 0, offset));
/* 7326 */     return builder.toString();
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
/*      */   public static String[] split(String str) {
/* 7354 */     return split(str, null, -1);
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
/*      */   public static String[] split(String str, char separatorChar) {
/* 7382 */     return splitWorker(str, separatorChar, false);
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
/*      */   public static String[] split(String str, String separatorChars) {
/* 7411 */     return splitWorker(str, separatorChars, -1, false);
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
/*      */   public static String[] split(String str, String separatorChars, int max) {
/* 7445 */     return splitWorker(str, separatorChars, max, false);
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
/*      */   public static String[] splitByCharacterType(String str) {
/* 7468 */     return splitByCharacterType(str, false);
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
/*      */   private static String[] splitByCharacterType(String str, boolean camelCase) {
/* 7486 */     if (str == null) {
/* 7487 */       return null;
/*      */     }
/* 7489 */     if (str.isEmpty()) {
/* 7490 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 7492 */     char[] c = str.toCharArray();
/* 7493 */     List<String> list = new ArrayList<>();
/* 7494 */     int tokenStart = 0;
/* 7495 */     int currentType = Character.getType(c[tokenStart]);
/* 7496 */     for (int pos = tokenStart + 1; pos < c.length; pos++) {
/* 7497 */       int type = Character.getType(c[pos]);
/* 7498 */       if (type != currentType) {
/*      */ 
/*      */         
/* 7501 */         if (camelCase && type == 2 && currentType == 1) {
/* 7502 */           int newTokenStart = pos - 1;
/* 7503 */           if (newTokenStart != tokenStart) {
/* 7504 */             list.add(new String(c, tokenStart, newTokenStart - tokenStart));
/* 7505 */             tokenStart = newTokenStart;
/*      */           } 
/*      */         } else {
/* 7508 */           list.add(new String(c, tokenStart, pos - tokenStart));
/* 7509 */           tokenStart = pos;
/*      */         } 
/* 7511 */         currentType = type;
/*      */       } 
/* 7513 */     }  list.add(new String(c, tokenStart, c.length - tokenStart));
/* 7514 */     return list.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*      */   public static String[] splitByCharacterTypeCamelCase(String str) {
/* 7542 */     return splitByCharacterType(str, true);
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
/*      */   public static String[] splitByWholeSeparator(String str, String separator) {
/* 7569 */     return splitByWholeSeparatorWorker(str, separator, -1, false);
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
/*      */   public static String[] splitByWholeSeparator(String str, String separator, int max) {
/* 7600 */     return splitByWholeSeparatorWorker(str, separator, max, false);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
/* 7629 */     return splitByWholeSeparatorWorker(str, separator, -1, true);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
/* 7662 */     return splitByWholeSeparatorWorker(str, separator, max, true);
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
/*      */   private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
/* 7681 */     if (str == null) {
/* 7682 */       return null;
/*      */     }
/*      */     
/* 7685 */     int len = str.length();
/*      */     
/* 7687 */     if (len == 0) {
/* 7688 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/*      */     
/* 7691 */     if (separator == null || "".equals(separator))
/*      */     {
/* 7693 */       return splitWorker(str, null, max, preserveAllTokens);
/*      */     }
/*      */     
/* 7696 */     int separatorLength = separator.length();
/*      */     
/* 7698 */     ArrayList<String> substrings = new ArrayList<>();
/* 7699 */     int numberOfSubstrings = 0;
/* 7700 */     int beg = 0;
/* 7701 */     int end = 0;
/* 7702 */     while (end < len) {
/* 7703 */       end = str.indexOf(separator, beg);
/*      */       
/* 7705 */       if (end > -1) {
/* 7706 */         if (end > beg) {
/* 7707 */           numberOfSubstrings++;
/*      */           
/* 7709 */           if (numberOfSubstrings == max) {
/* 7710 */             end = len;
/* 7711 */             substrings.add(str.substring(beg));
/*      */             
/*      */             continue;
/*      */           } 
/* 7715 */           substrings.add(str.substring(beg, end));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 7720 */           beg = end + separatorLength;
/*      */           
/*      */           continue;
/*      */         } 
/* 7724 */         if (preserveAllTokens) {
/* 7725 */           numberOfSubstrings++;
/* 7726 */           if (numberOfSubstrings == max) {
/* 7727 */             end = len;
/* 7728 */             substrings.add(str.substring(beg));
/*      */           } else {
/* 7730 */             substrings.add("");
/*      */           } 
/*      */         } 
/* 7733 */         beg = end + separatorLength;
/*      */         
/*      */         continue;
/*      */       } 
/* 7737 */       substrings.add(str.substring(beg));
/* 7738 */       end = len;
/*      */     } 
/*      */ 
/*      */     
/* 7742 */     return substrings.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*      */   public static String[] splitPreserveAllTokens(String str) {
/* 7771 */     return splitWorker(str, null, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, char separatorChar) {
/* 7807 */     return splitWorker(str, separatorChar, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, String separatorChars) {
/* 7844 */     return splitWorker(str, separatorChars, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
/* 7884 */     return splitWorker(str, separatorChars, max, true);
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
/*      */   private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
/* 7902 */     if (str == null) {
/* 7903 */       return null;
/*      */     }
/* 7905 */     int len = str.length();
/* 7906 */     if (len == 0) {
/* 7907 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 7909 */     List<String> list = new ArrayList<>();
/* 7910 */     int i = 0, start = 0;
/* 7911 */     boolean match = false;
/* 7912 */     boolean lastMatch = false;
/* 7913 */     while (i < len) {
/* 7914 */       if (str.charAt(i) == separatorChar) {
/* 7915 */         if (match || preserveAllTokens) {
/* 7916 */           list.add(str.substring(start, i));
/* 7917 */           match = false;
/* 7918 */           lastMatch = true;
/*      */         } 
/* 7920 */         start = ++i;
/*      */         continue;
/*      */       } 
/* 7923 */       lastMatch = false;
/* 7924 */       match = true;
/* 7925 */       i++;
/*      */     } 
/* 7927 */     if (match || (preserveAllTokens && lastMatch)) {
/* 7928 */       list.add(str.substring(start, i));
/*      */     }
/* 7930 */     return list.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*      */   private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
/* 7952 */     if (str == null) {
/* 7953 */       return null;
/*      */     }
/* 7955 */     int len = str.length();
/* 7956 */     if (len == 0) {
/* 7957 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 7959 */     List<String> list = new ArrayList<>();
/* 7960 */     int sizePlus1 = 1;
/* 7961 */     int i = 0, start = 0;
/* 7962 */     boolean match = false;
/* 7963 */     boolean lastMatch = false;
/* 7964 */     if (separatorChars == null) {
/*      */       
/* 7966 */       while (i < len) {
/* 7967 */         if (Character.isWhitespace(str.charAt(i))) {
/* 7968 */           if (match || preserveAllTokens) {
/* 7969 */             lastMatch = true;
/* 7970 */             if (sizePlus1++ == max) {
/* 7971 */               i = len;
/* 7972 */               lastMatch = false;
/*      */             } 
/* 7974 */             list.add(str.substring(start, i));
/* 7975 */             match = false;
/*      */           } 
/* 7977 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 7980 */         lastMatch = false;
/* 7981 */         match = true;
/* 7982 */         i++;
/*      */       } 
/* 7984 */     } else if (separatorChars.length() == 1) {
/*      */       
/* 7986 */       char sep = separatorChars.charAt(0);
/* 7987 */       while (i < len) {
/* 7988 */         if (str.charAt(i) == sep) {
/* 7989 */           if (match || preserveAllTokens) {
/* 7990 */             lastMatch = true;
/* 7991 */             if (sizePlus1++ == max) {
/* 7992 */               i = len;
/* 7993 */               lastMatch = false;
/*      */             } 
/* 7995 */             list.add(str.substring(start, i));
/* 7996 */             match = false;
/*      */           } 
/* 7998 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 8001 */         lastMatch = false;
/* 8002 */         match = true;
/* 8003 */         i++;
/*      */       } 
/*      */     } else {
/*      */       
/* 8007 */       while (i < len) {
/* 8008 */         if (separatorChars.indexOf(str.charAt(i)) >= 0) {
/* 8009 */           if (match || preserveAllTokens) {
/* 8010 */             lastMatch = true;
/* 8011 */             if (sizePlus1++ == max) {
/* 8012 */               i = len;
/* 8013 */               lastMatch = false;
/*      */             } 
/* 8015 */             list.add(str.substring(start, i));
/* 8016 */             match = false;
/*      */           } 
/* 8018 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 8021 */         lastMatch = false;
/* 8022 */         match = true;
/* 8023 */         i++;
/*      */       } 
/*      */     } 
/* 8026 */     if (match || (preserveAllTokens && lastMatch)) {
/* 8027 */       list.add(str.substring(start, i));
/*      */     }
/* 8029 */     return list.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*      */   public static boolean startsWith(CharSequence str, CharSequence prefix) {
/* 8055 */     return startsWith(str, prefix, false);
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
/*      */   private static boolean startsWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
/* 8070 */     if (str == null || prefix == null) {
/* 8071 */       return (str == prefix);
/*      */     }
/* 8073 */     if (prefix.length() > str.length()) {
/* 8074 */       return false;
/*      */     }
/* 8076 */     return CharSequenceUtils.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
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
/*      */   public static boolean startsWithAny(CharSequence sequence, CharSequence... searchStrings) {
/* 8102 */     if (isEmpty(sequence) || ArrayUtils.isEmpty((Object[])searchStrings)) {
/* 8103 */       return false;
/*      */     }
/* 8105 */     for (CharSequence searchString : searchStrings) {
/* 8106 */       if (startsWith(sequence, searchString)) {
/* 8107 */         return true;
/*      */       }
/*      */     } 
/* 8110 */     return false;
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
/*      */   public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
/* 8136 */     return startsWith(str, prefix, true);
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
/*      */   public static String strip(String str) {
/* 8164 */     return strip(str, null);
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
/*      */   public static String strip(String str, String stripChars) {
/* 8194 */     if (isEmpty(str)) {
/* 8195 */       return str;
/*      */     }
/* 8197 */     str = stripStart(str, stripChars);
/* 8198 */     return stripEnd(str, stripChars);
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
/*      */   public static String stripAccents(String input) {
/* 8220 */     if (input == null) {
/* 8221 */       return null;
/*      */     }
/* 8223 */     StringBuilder decomposed = new StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFD));
/* 8224 */     convertRemainingAccentCharacters(decomposed);
/*      */     
/* 8226 */     return STRIP_ACCENTS_PATTERN.matcher(decomposed).replaceAll("");
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
/*      */   public static String[] stripAll(String... strs) {
/* 8251 */     return stripAll(strs, null);
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
/*      */   public static String[] stripAll(String[] strs, String stripChars) {
/* 8280 */     int strsLen = ArrayUtils.getLength(strs);
/* 8281 */     if (strsLen == 0) {
/* 8282 */       return strs;
/*      */     }
/* 8284 */     String[] newArr = new String[strsLen];
/* 8285 */     for (int i = 0; i < strsLen; i++) {
/* 8286 */       newArr[i] = strip(strs[i], stripChars);
/*      */     }
/* 8288 */     return newArr;
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
/*      */   public static String stripEnd(String str, String stripChars) {
/* 8317 */     int end = length(str);
/* 8318 */     if (end == 0) {
/* 8319 */       return str;
/*      */     }
/*      */     
/* 8322 */     if (stripChars == null) {
/* 8323 */       while (end != 0 && Character.isWhitespace(str.charAt(end - 1)))
/* 8324 */         end--; 
/*      */     } else {
/* 8326 */       if (stripChars.isEmpty()) {
/* 8327 */         return str;
/*      */       }
/* 8329 */       while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
/* 8330 */         end--;
/*      */       }
/*      */     } 
/* 8333 */     return str.substring(0, end);
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
/*      */   public static String stripStart(String str, String stripChars) {
/* 8361 */     int strLen = length(str);
/* 8362 */     if (strLen == 0) {
/* 8363 */       return str;
/*      */     }
/* 8365 */     int start = 0;
/* 8366 */     if (stripChars == null) {
/* 8367 */       while (start != strLen && Character.isWhitespace(str.charAt(start)))
/* 8368 */         start++; 
/*      */     } else {
/* 8370 */       if (stripChars.isEmpty()) {
/* 8371 */         return str;
/*      */       }
/* 8373 */       while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
/* 8374 */         start++;
/*      */       }
/*      */     } 
/* 8377 */     return str.substring(start);
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
/*      */   public static String stripToEmpty(String str) {
/* 8403 */     return (str == null) ? "" : strip(str, null);
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
/*      */   public static String stripToNull(String str) {
/* 8430 */     if (str == null) {
/* 8431 */       return null;
/*      */     }
/* 8433 */     str = strip(str, null);
/* 8434 */     return str.isEmpty() ? null : str;
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
/*      */   public static String substring(String str, int start) {
/* 8464 */     if (str == null) {
/* 8465 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 8469 */     if (start < 0) {
/* 8470 */       start = str.length() + start;
/*      */     }
/*      */     
/* 8473 */     if (start < 0) {
/* 8474 */       start = 0;
/*      */     }
/* 8476 */     if (start > str.length()) {
/* 8477 */       return "";
/*      */     }
/*      */     
/* 8480 */     return str.substring(start);
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
/*      */   public static String substring(String str, int start, int end) {
/* 8519 */     if (str == null) {
/* 8520 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 8524 */     if (end < 0) {
/* 8525 */       end = str.length() + end;
/*      */     }
/* 8527 */     if (start < 0) {
/* 8528 */       start = str.length() + start;
/*      */     }
/*      */ 
/*      */     
/* 8532 */     if (end > str.length()) {
/* 8533 */       end = str.length();
/*      */     }
/*      */ 
/*      */     
/* 8537 */     if (start > end) {
/* 8538 */       return "";
/*      */     }
/*      */     
/* 8541 */     if (start < 0) {
/* 8542 */       start = 0;
/*      */     }
/* 8544 */     if (end < 0) {
/* 8545 */       end = 0;
/*      */     }
/*      */     
/* 8548 */     return str.substring(start, end);
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
/*      */   public static String substringAfter(String str, int separator) {
/* 8577 */     if (isEmpty(str)) {
/* 8578 */       return str;
/*      */     }
/* 8580 */     int pos = str.indexOf(separator);
/* 8581 */     if (pos == -1) {
/* 8582 */       return "";
/*      */     }
/* 8584 */     return str.substring(pos + 1);
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
/*      */   public static String substringAfter(String str, String separator) {
/* 8616 */     if (isEmpty(str)) {
/* 8617 */       return str;
/*      */     }
/* 8619 */     if (separator == null) {
/* 8620 */       return "";
/*      */     }
/* 8622 */     int pos = str.indexOf(separator);
/* 8623 */     if (pos == -1) {
/* 8624 */       return "";
/*      */     }
/* 8626 */     return str.substring(pos + separator.length());
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
/*      */   public static String substringAfterLast(String str, int separator) {
/* 8659 */     if (isEmpty(str)) {
/* 8660 */       return str;
/*      */     }
/* 8662 */     int pos = str.lastIndexOf(separator);
/* 8663 */     if (pos == -1 || pos == str.length() - 1) {
/* 8664 */       return "";
/*      */     }
/* 8666 */     return str.substring(pos + 1);
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
/*      */   public static String substringAfterLast(String str, String separator) {
/* 8699 */     if (isEmpty(str)) {
/* 8700 */       return str;
/*      */     }
/* 8702 */     if (isEmpty(separator)) {
/* 8703 */       return "";
/*      */     }
/* 8705 */     int pos = str.lastIndexOf(separator);
/* 8706 */     if (pos == -1 || pos == str.length() - separator.length()) {
/* 8707 */       return "";
/*      */     }
/* 8709 */     return str.substring(pos + separator.length());
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
/*      */   public static String substringBefore(String str, String separator) {
/* 8742 */     if (isEmpty(str) || separator == null) {
/* 8743 */       return str;
/*      */     }
/* 8745 */     if (separator.isEmpty()) {
/* 8746 */       return "";
/*      */     }
/* 8748 */     int pos = str.indexOf(separator);
/* 8749 */     if (pos == -1) {
/* 8750 */       return str;
/*      */     }
/* 8752 */     return str.substring(0, pos);
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
/*      */   public static String substringBeforeLast(String str, String separator) {
/* 8783 */     if (isEmpty(str) || isEmpty(separator)) {
/* 8784 */       return str;
/*      */     }
/* 8786 */     int pos = str.lastIndexOf(separator);
/* 8787 */     if (pos == -1) {
/* 8788 */       return str;
/*      */     }
/* 8790 */     return str.substring(0, pos);
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
/*      */   public static String substringBetween(String str, String tag) {
/* 8817 */     return substringBetween(str, tag, tag);
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
/*      */   public static String substringBetween(String str, String open, String close) {
/* 8851 */     if (!ObjectUtils.allNotNull(new Object[] { str, open, close })) {
/* 8852 */       return null;
/*      */     }
/* 8854 */     int start = str.indexOf(open);
/* 8855 */     if (start != -1) {
/* 8856 */       int end = str.indexOf(close, start + open.length());
/* 8857 */       if (end != -1) {
/* 8858 */         return str.substring(start + open.length(), end);
/*      */       }
/*      */     } 
/* 8861 */     return null;
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
/*      */   public static String[] substringsBetween(String str, String open, String close) {
/* 8887 */     if (str == null || isEmpty(open) || isEmpty(close)) {
/* 8888 */       return null;
/*      */     }
/* 8890 */     int strLen = str.length();
/* 8891 */     if (strLen == 0) {
/* 8892 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 8894 */     int closeLen = close.length();
/* 8895 */     int openLen = open.length();
/* 8896 */     List<String> list = new ArrayList<>();
/* 8897 */     int pos = 0;
/* 8898 */     while (pos < strLen - closeLen) {
/* 8899 */       int start = str.indexOf(open, pos);
/* 8900 */       if (start < 0) {
/*      */         break;
/*      */       }
/* 8903 */       start += openLen;
/* 8904 */       int end = str.indexOf(close, start);
/* 8905 */       if (end < 0) {
/*      */         break;
/*      */       }
/* 8908 */       list.add(str.substring(start, end));
/* 8909 */       pos = end + closeLen;
/*      */     } 
/* 8911 */     if (list.isEmpty()) {
/* 8912 */       return null;
/*      */     }
/* 8914 */     return list.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*      */   public static String swapCase(String str) {
/* 8945 */     if (isEmpty(str)) {
/* 8946 */       return str;
/*      */     }
/*      */     
/* 8949 */     int strLen = str.length();
/* 8950 */     int[] newCodePoints = new int[strLen];
/* 8951 */     int outOffset = 0; int i;
/* 8952 */     for (i = 0; i < strLen; ) {
/* 8953 */       int newCodePoint, oldCodepoint = str.codePointAt(i);
/*      */       
/* 8955 */       if (Character.isUpperCase(oldCodepoint) || Character.isTitleCase(oldCodepoint)) {
/* 8956 */         newCodePoint = Character.toLowerCase(oldCodepoint);
/* 8957 */       } else if (Character.isLowerCase(oldCodepoint)) {
/* 8958 */         newCodePoint = Character.toUpperCase(oldCodepoint);
/*      */       } else {
/* 8960 */         newCodePoint = oldCodepoint;
/*      */       } 
/* 8962 */       newCodePoints[outOffset++] = newCodePoint;
/* 8963 */       i += Character.charCount(newCodePoint);
/*      */     } 
/* 8965 */     return new String(newCodePoints, 0, outOffset);
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
/*      */   public static int[] toCodePoints(CharSequence str) {
/* 8985 */     if (str == null) {
/* 8986 */       return null;
/*      */     }
/* 8988 */     if (str.length() == 0) {
/* 8989 */       return ArrayUtils.EMPTY_INT_ARRAY;
/*      */     }
/*      */     
/* 8992 */     String s = str.toString();
/* 8993 */     int[] result = new int[s.codePointCount(0, s.length())];
/* 8994 */     int index = 0;
/* 8995 */     for (int i = 0; i < result.length; i++) {
/* 8996 */       result[i] = s.codePointAt(index);
/* 8997 */       index += Character.charCount(result[i]);
/*      */     } 
/* 8999 */     return result;
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
/*      */   public static String toEncodedString(byte[] bytes, Charset charset) {
/* 9016 */     return new String(bytes, Charsets.toCharset(charset));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toRootLowerCase(String source) {
/* 9027 */     return (source == null) ? null : source.toLowerCase(Locale.ROOT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toRootUpperCase(String source) {
/* 9038 */     return (source == null) ? null : source.toUpperCase(Locale.ROOT);
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
/*      */   @Deprecated
/*      */   public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
/* 9058 */     return (charsetName != null) ? new String(bytes, charsetName) : new String(bytes, Charset.defaultCharset());
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
/*      */   public static String trim(String str) {
/* 9087 */     return (str == null) ? null : str.trim();
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
/*      */   public static String trimToEmpty(String str) {
/* 9112 */     return (str == null) ? "" : str.trim();
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
/*      */   public static String trimToNull(String str) {
/* 9138 */     String ts = trim(str);
/* 9139 */     return isEmpty(ts) ? null : ts;
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
/*      */   public static String truncate(String str, int maxWidth) {
/* 9175 */     return truncate(str, 0, maxWidth);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String truncate(String str, int offset, int maxWidth) {
/* 9239 */     if (offset < 0) {
/* 9240 */       throw new IllegalArgumentException("offset cannot be negative");
/*      */     }
/* 9242 */     if (maxWidth < 0) {
/* 9243 */       throw new IllegalArgumentException("maxWith cannot be negative");
/*      */     }
/* 9245 */     if (str == null) {
/* 9246 */       return null;
/*      */     }
/* 9248 */     if (offset > str.length()) {
/* 9249 */       return "";
/*      */     }
/* 9251 */     if (str.length() > maxWidth) {
/* 9252 */       int ix = Math.min(offset + maxWidth, str.length());
/* 9253 */       return str.substring(offset, ix);
/*      */     } 
/* 9255 */     return str.substring(offset);
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
/*      */   public static String uncapitalize(String str) {
/* 9280 */     int strLen = length(str);
/* 9281 */     if (strLen == 0) {
/* 9282 */       return str;
/*      */     }
/*      */     
/* 9285 */     int firstCodepoint = str.codePointAt(0);
/* 9286 */     int newCodePoint = Character.toLowerCase(firstCodepoint);
/* 9287 */     if (firstCodepoint == newCodePoint)
/*      */     {
/* 9289 */       return str;
/*      */     }
/*      */     
/* 9292 */     int[] newCodePoints = new int[strLen];
/* 9293 */     int outOffset = 0;
/* 9294 */     newCodePoints[outOffset++] = newCodePoint; int inOffset;
/* 9295 */     for (inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
/* 9296 */       int codepoint = str.codePointAt(inOffset);
/* 9297 */       newCodePoints[outOffset++] = codepoint;
/* 9298 */       inOffset += Character.charCount(codepoint);
/*      */     } 
/* 9300 */     return new String(newCodePoints, 0, outOffset);
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
/*      */   public static String unwrap(String str, char wrapChar) {
/* 9330 */     if (isEmpty(str) || wrapChar == '\000' || str.length() == 1) {
/* 9331 */       return str;
/*      */     }
/*      */     
/* 9334 */     if (str.charAt(0) == wrapChar && str.charAt(str.length() - 1) == wrapChar) {
/* 9335 */       int startIndex = 0;
/* 9336 */       int endIndex = str.length() - 1;
/*      */       
/* 9338 */       return str.substring(1, endIndex);
/*      */     } 
/*      */     
/* 9341 */     return str;
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
/*      */   public static String unwrap(String str, String wrapToken) {
/* 9372 */     if (isEmpty(str) || isEmpty(wrapToken) || str.length() == 1) {
/* 9373 */       return str;
/*      */     }
/*      */     
/* 9376 */     if (startsWith(str, wrapToken) && endsWith(str, wrapToken)) {
/* 9377 */       int startIndex = str.indexOf(wrapToken);
/* 9378 */       int endIndex = str.lastIndexOf(wrapToken);
/* 9379 */       int wrapLength = wrapToken.length();
/*      */       
/* 9381 */       if (startIndex != -1 && endIndex != -1) {
/* 9382 */         return str.substring(startIndex + wrapLength, endIndex);
/*      */       }
/*      */     } 
/*      */     
/* 9386 */     return str;
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
/*      */   public static String upperCase(String str) {
/* 9411 */     if (str == null) {
/* 9412 */       return null;
/*      */     }
/* 9414 */     return str.toUpperCase();
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
/*      */   public static String upperCase(String str, Locale locale) {
/* 9434 */     if (str == null) {
/* 9435 */       return null;
/*      */     }
/* 9437 */     return str.toUpperCase(locale);
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
/*      */   public static String valueOf(char[] value) {
/* 9449 */     return (value == null) ? null : String.valueOf(value);
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
/*      */   public static String wrap(String str, char wrapWith) {
/* 9475 */     if (isEmpty(str) || wrapWith == '\000') {
/* 9476 */       return str;
/*      */     }
/*      */     
/* 9479 */     return wrapWith + str + wrapWith;
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
/*      */   public static String wrap(String str, String wrapWith) {
/* 9513 */     if (isEmpty(str) || isEmpty(wrapWith)) {
/* 9514 */       return str;
/*      */     }
/*      */     
/* 9517 */     return wrapWith.concat(str).concat(wrapWith);
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
/*      */   public static String wrapIfMissing(String str, char wrapWith) {
/* 9548 */     if (isEmpty(str) || wrapWith == '\000') {
/* 9549 */       return str;
/*      */     }
/* 9551 */     boolean wrapStart = (str.charAt(0) != wrapWith);
/* 9552 */     boolean wrapEnd = (str.charAt(str.length() - 1) != wrapWith);
/* 9553 */     if (!wrapStart && !wrapEnd) {
/* 9554 */       return str;
/*      */     }
/*      */     
/* 9557 */     StringBuilder builder = new StringBuilder(str.length() + 2);
/* 9558 */     if (wrapStart) {
/* 9559 */       builder.append(wrapWith);
/*      */     }
/* 9561 */     builder.append(str);
/* 9562 */     if (wrapEnd) {
/* 9563 */       builder.append(wrapWith);
/*      */     }
/* 9565 */     return builder.toString();
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
/*      */   public static String wrapIfMissing(String str, String wrapWith) {
/* 9600 */     if (isEmpty(str) || isEmpty(wrapWith)) {
/* 9601 */       return str;
/*      */     }
/*      */     
/* 9604 */     boolean wrapStart = !str.startsWith(wrapWith);
/* 9605 */     boolean wrapEnd = !str.endsWith(wrapWith);
/* 9606 */     if (!wrapStart && !wrapEnd) {
/* 9607 */       return str;
/*      */     }
/*      */     
/* 9610 */     StringBuilder builder = new StringBuilder(str.length() + wrapWith.length() + wrapWith.length());
/* 9611 */     if (wrapStart) {
/* 9612 */       builder.append(wrapWith);
/*      */     }
/* 9614 */     builder.append(str);
/* 9615 */     if (wrapEnd) {
/* 9616 */       builder.append(wrapWith);
/*      */     }
/* 9618 */     return builder.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */