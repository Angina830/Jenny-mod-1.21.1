/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharSequenceUtils
/*     */ {
/*     */   private static final int NOT_FOUND = -1;
/*     */   static final int TO_STRING_LIMIT = 16;
/*     */   
/*     */   public static CharSequence subSequence(CharSequence cs, int start) {
/*  57 */     return (cs == null) ? null : cs.subSequence(start, cs.length());
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
/*     */   static int indexOf(CharSequence cs, int searchChar, int start) {
/* 100 */     if (cs instanceof String) {
/* 101 */       return ((String)cs).indexOf(searchChar, start);
/*     */     }
/* 103 */     int sz = cs.length();
/* 104 */     if (start < 0) {
/* 105 */       start = 0;
/*     */     }
/* 107 */     if (searchChar < 65536) {
/* 108 */       for (int i = start; i < sz; i++) {
/* 109 */         if (cs.charAt(i) == searchChar) {
/* 110 */           return i;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 115 */     if (searchChar <= 1114111) {
/* 116 */       char[] chars = Character.toChars(searchChar);
/* 117 */       for (int i = start; i < sz - 1; i++) {
/* 118 */         char high = cs.charAt(i);
/* 119 */         char low = cs.charAt(i + 1);
/* 120 */         if (high == chars[0] && low == chars[1]) {
/* 121 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 125 */     return -1;
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
/*     */   static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
/* 137 */     if (cs instanceof String)
/* 138 */       return ((String)cs).indexOf(searchChar.toString(), start); 
/* 139 */     if (cs instanceof StringBuilder)
/* 140 */       return ((StringBuilder)cs).indexOf(searchChar.toString(), start); 
/* 141 */     if (cs instanceof StringBuffer) {
/* 142 */       return ((StringBuffer)cs).indexOf(searchChar.toString(), start);
/*     */     }
/* 144 */     return cs.toString().indexOf(searchChar.toString(), start);
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
/*     */   static int lastIndexOf(CharSequence cs, int searchChar, int start) {
/* 183 */     if (cs instanceof String) {
/* 184 */       return ((String)cs).lastIndexOf(searchChar, start);
/*     */     }
/* 186 */     int sz = cs.length();
/* 187 */     if (start < 0) {
/* 188 */       return -1;
/*     */     }
/* 190 */     if (start >= sz) {
/* 191 */       start = sz - 1;
/*     */     }
/* 193 */     if (searchChar < 65536) {
/* 194 */       for (int i = start; i >= 0; i--) {
/* 195 */         if (cs.charAt(i) == searchChar) {
/* 196 */           return i;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 202 */     if (searchChar <= 1114111) {
/* 203 */       char[] chars = Character.toChars(searchChar);
/*     */       
/* 205 */       if (start == sz - 1) {
/* 206 */         return -1;
/*     */       }
/* 208 */       for (int i = start; i >= 0; i--) {
/* 209 */         char high = cs.charAt(i);
/* 210 */         char low = cs.charAt(i + 1);
/* 211 */         if (chars[0] == high && chars[1] == low) {
/* 212 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 216 */     return -1;
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
/*     */   static int lastIndexOf(CharSequence cs, CharSequence searchChar, int start) {
/* 230 */     if (searchChar instanceof String) {
/* 231 */       if (cs instanceof String)
/* 232 */         return ((String)cs).lastIndexOf((String)searchChar, start); 
/* 233 */       if (cs instanceof StringBuilder)
/* 234 */         return ((StringBuilder)cs).lastIndexOf((String)searchChar, start); 
/* 235 */       if (cs instanceof StringBuffer) {
/* 236 */         return ((StringBuffer)cs).lastIndexOf((String)searchChar, start);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     int len1 = cs.length();
/* 241 */     int len2 = searchChar.length();
/*     */     
/* 243 */     if (start > len1) {
/* 244 */       start = len1;
/*     */     }
/*     */     
/* 247 */     if (start < 0 || len2 < 0 || len2 > len1) {
/* 248 */       return -1;
/*     */     }
/*     */     
/* 251 */     if (len2 == 0) {
/* 252 */       return start;
/*     */     }
/*     */     
/* 255 */     if (len2 <= 16) {
/* 256 */       if (cs instanceof String)
/* 257 */         return ((String)cs).lastIndexOf(searchChar.toString(), start); 
/* 258 */       if (cs instanceof StringBuilder)
/* 259 */         return ((StringBuilder)cs).lastIndexOf(searchChar.toString(), start); 
/* 260 */       if (cs instanceof StringBuffer) {
/* 261 */         return ((StringBuffer)cs).lastIndexOf(searchChar.toString(), start);
/*     */       }
/*     */     } 
/*     */     
/* 265 */     if (start + len2 > len1) {
/* 266 */       start = len1 - len2;
/*     */     }
/*     */     
/* 269 */     char char0 = searchChar.charAt(0);
/*     */     
/* 271 */     int i = start;
/*     */     while (true) {
/* 273 */       while (cs.charAt(i) != char0) {
/* 274 */         i--;
/* 275 */         if (i < 0) {
/* 276 */           return -1;
/*     */         }
/*     */       } 
/* 279 */       if (checkLaterThan1(cs, searchChar, len2, i)) {
/* 280 */         return i;
/*     */       }
/* 282 */       i--;
/* 283 */       if (i < 0) {
/* 284 */         return -1;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean checkLaterThan1(CharSequence cs, CharSequence searchChar, int len2, int start1) {
/* 290 */     for (int i = 1, j = len2 - 1; i <= j; i++, j--) {
/* 291 */       if (cs.charAt(start1 + i) != searchChar.charAt(i) || cs
/*     */         
/* 293 */         .charAt(start1 + j) != searchChar.charAt(j))
/*     */       {
/* 295 */         return false;
/*     */       }
/*     */     } 
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] toCharArray(CharSequence source) {
/* 309 */     int len = StringUtils.length(source);
/* 310 */     if (len == 0) {
/* 311 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*     */     }
/* 313 */     if (source instanceof String) {
/* 314 */       return ((String)source).toCharArray();
/*     */     }
/* 316 */     char[] array = new char[len];
/* 317 */     for (int i = 0; i < len; i++) {
/* 318 */       array[i] = source.charAt(i);
/*     */     }
/* 320 */     return array;
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
/*     */   static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
/* 336 */     if (cs instanceof String && substring instanceof String) {
/* 337 */       return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
/*     */     }
/* 339 */     int index1 = thisStart;
/* 340 */     int index2 = start;
/* 341 */     int tmpLen = length;
/*     */ 
/*     */     
/* 344 */     int srcLen = cs.length() - thisStart;
/* 345 */     int otherLen = substring.length() - start;
/*     */ 
/*     */     
/* 348 */     if (thisStart < 0 || start < 0 || length < 0) {
/* 349 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 353 */     if (srcLen < length || otherLen < length) {
/* 354 */       return false;
/*     */     }
/*     */     
/* 357 */     while (tmpLen-- > 0) {
/* 358 */       char c1 = cs.charAt(index1++);
/* 359 */       char c2 = substring.charAt(index2++);
/*     */       
/* 361 */       if (c1 == c2) {
/*     */         continue;
/*     */       }
/*     */       
/* 365 */       if (!ignoreCase) {
/* 366 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 370 */       char u1 = Character.toUpperCase(c1);
/* 371 */       char u2 = Character.toUpperCase(c2);
/* 372 */       if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
/* 373 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 377 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\CharSequenceUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */