/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ @Deprecated
/*     */ public abstract class StrMatcher
/*     */ {
/*  41 */   private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('"');
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final StrMatcher NONE_MATCHER = new NoMatcher();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher commaMatcher() {
/*  84 */     return COMMA_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher tabMatcher() {
/*  93 */     return TAB_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher spaceMatcher() {
/* 102 */     return SPACE_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher splitMatcher() {
/* 112 */     return SPLIT_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher trimMatcher() {
/* 121 */     return TRIM_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher singleQuoteMatcher() {
/* 130 */     return SINGLE_QUOTE_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher doubleQuoteMatcher() {
/* 139 */     return DOUBLE_QUOTE_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher quoteMatcher() {
/* 148 */     return QUOTE_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher noneMatcher() {
/* 157 */     return NONE_MATCHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher charMatcher(char ch) {
/* 167 */     return new CharMatcher(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher charSetMatcher(char... chars) {
/* 177 */     if (chars == null || chars.length == 0) {
/* 178 */       return NONE_MATCHER;
/*     */     }
/* 180 */     if (chars.length == 1) {
/* 181 */       return new CharMatcher(chars[0]);
/*     */     }
/* 183 */     return new CharSetMatcher(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher charSetMatcher(String chars) {
/* 193 */     if (StringUtils.isEmpty(chars)) {
/* 194 */       return NONE_MATCHER;
/*     */     }
/* 196 */     if (chars.length() == 1) {
/* 197 */       return new CharMatcher(chars.charAt(0));
/*     */     }
/* 199 */     return new CharSetMatcher(chars.toCharArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrMatcher stringMatcher(String str) {
/* 209 */     if (StringUtils.isEmpty(str)) {
/* 210 */       return NONE_MATCHER;
/*     */     }
/* 212 */     return new StringMatcher(str);
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
/*     */   public abstract int isMatch(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int isMatch(char[] buffer, int pos) {
/* 271 */     return isMatch(buffer, pos, 0, buffer.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class CharSetMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     private final char[] chars;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CharSetMatcher(char[] chars) {
/* 289 */       this.chars = (char[])chars.clone();
/* 290 */       Arrays.sort(this.chars);
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
/*     */ 
/*     */     
/*     */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd) {
/* 304 */       return (Arrays.binarySearch(this.chars, buffer[pos]) >= 0) ? 1 : 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class CharMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     private final char ch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CharMatcher(char ch) {
/* 323 */       this.ch = ch;
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
/*     */ 
/*     */     
/*     */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd) {
/* 337 */       return (this.ch == buffer[pos]) ? 1 : 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class StringMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     private final char[] chars;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     StringMatcher(String str) {
/* 356 */       this.chars = str.toCharArray();
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
/*     */ 
/*     */     
/*     */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd) {
/* 370 */       int len = this.chars.length;
/* 371 */       if (pos + len > bufferEnd) {
/* 372 */         return 0;
/*     */       }
/* 374 */       for (int i = 0; i < this.chars.length; i++, pos++) {
/* 375 */         if (this.chars[i] != buffer[pos]) {
/* 376 */           return 0;
/*     */         }
/*     */       } 
/* 379 */       return len;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 384 */       return super.toString() + ' ' + Arrays.toString(this.chars);
/*     */     }
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
/*     */   static final class NoMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd) {
/* 413 */       return 0;
/*     */     }
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
/*     */   static final class TrimMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd) {
/* 441 */       return (buffer[pos] <= ' ') ? 1 : 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\StrMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */