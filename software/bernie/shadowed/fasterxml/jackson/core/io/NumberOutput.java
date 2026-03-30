/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ public final class NumberOutput
/*     */ {
/*   5 */   private static int MILLION = 1000000;
/*   6 */   private static int BILLION = 1000000000;
/*   7 */   private static long BILLION_L = 1000000000L;
/*     */   
/*   9 */   private static long MIN_INT_AS_LONG = -2147483648L;
/*  10 */   private static long MAX_INT_AS_LONG = 2147483647L;
/*     */   
/*  12 */   static final String SMALLEST_INT = String.valueOf(-2147483648);
/*  13 */   static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   private static final int[] TRIPLET_TO_CHARS = new int[1000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  27 */     int fullIx = 0;
/*  28 */     for (int i1 = 0; i1 < 10; i1++) {
/*  29 */       for (int i2 = 0; i2 < 10; i2++) {
/*  30 */         for (int i3 = 0; i3 < 10; i3++) {
/*  31 */           int enc = i1 + 48 << 16 | i2 + 48 << 8 | i3 + 48;
/*     */ 
/*     */           
/*  34 */           TRIPLET_TO_CHARS[fullIx++] = enc;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*  40 */   private static final String[] sSmallIntStrs = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
/*     */ 
/*     */   
/*  43 */   private static final String[] sSmallIntStrs2 = new String[] { "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int outputInt(int v, char[] b, int off) {
/*  58 */     if (v < 0) {
/*  59 */       if (v == Integer.MIN_VALUE)
/*     */       {
/*     */         
/*  62 */         return _outputSmallestI(b, off);
/*     */       }
/*  64 */       b[off++] = '-';
/*  65 */       v = -v;
/*     */     } 
/*     */     
/*  68 */     if (v < MILLION) {
/*  69 */       if (v < 1000) {
/*  70 */         if (v < 10) {
/*  71 */           b[off] = (char)(48 + v);
/*  72 */           return off + 1;
/*     */         } 
/*  74 */         return _leading3(v, b, off);
/*     */       } 
/*  76 */       int i = v / 1000;
/*  77 */       v -= i * 1000;
/*  78 */       off = _leading3(i, b, off);
/*  79 */       off = _full3(v, b, off);
/*  80 */       return off;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     if (v >= BILLION) {
/*  89 */       v -= BILLION;
/*  90 */       if (v >= BILLION) {
/*  91 */         v -= BILLION;
/*  92 */         b[off++] = '2';
/*     */       } else {
/*  94 */         b[off++] = '1';
/*     */       } 
/*  96 */       return _outputFullBillion(v, b, off);
/*     */     } 
/*  98 */     int newValue = v / 1000;
/*  99 */     int ones = v - newValue * 1000;
/* 100 */     v = newValue;
/* 101 */     newValue /= 1000;
/* 102 */     int thousands = v - newValue * 1000;
/*     */     
/* 104 */     off = _leading3(newValue, b, off);
/* 105 */     off = _full3(thousands, b, off);
/* 106 */     return _full3(ones, b, off);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int outputInt(int v, byte[] b, int off) {
/* 111 */     if (v < 0) {
/* 112 */       if (v == Integer.MIN_VALUE) {
/* 113 */         return _outputSmallestI(b, off);
/*     */       }
/* 115 */       b[off++] = 45;
/* 116 */       v = -v;
/*     */     } 
/*     */     
/* 119 */     if (v < MILLION) {
/* 120 */       if (v < 1000) {
/* 121 */         if (v < 10) {
/* 122 */           b[off++] = (byte)(48 + v);
/*     */         } else {
/* 124 */           off = _leading3(v, b, off);
/*     */         } 
/*     */       } else {
/* 127 */         int i = v / 1000;
/* 128 */         v -= i * 1000;
/* 129 */         off = _leading3(i, b, off);
/* 130 */         off = _full3(v, b, off);
/*     */       } 
/* 132 */       return off;
/*     */     } 
/* 134 */     if (v >= BILLION) {
/* 135 */       v -= BILLION;
/* 136 */       if (v >= BILLION) {
/* 137 */         v -= BILLION;
/* 138 */         b[off++] = 50;
/*     */       } else {
/* 140 */         b[off++] = 49;
/*     */       } 
/* 142 */       return _outputFullBillion(v, b, off);
/*     */     } 
/* 144 */     int newValue = v / 1000;
/* 145 */     int ones = v - newValue * 1000;
/* 146 */     v = newValue;
/* 147 */     newValue /= 1000;
/* 148 */     int thousands = v - newValue * 1000;
/* 149 */     off = _leading3(newValue, b, off);
/* 150 */     off = _full3(thousands, b, off);
/* 151 */     return _full3(ones, b, off);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int outputLong(long v, char[] b, int off) {
/* 160 */     if (v < 0L) {
/* 161 */       if (v > MIN_INT_AS_LONG) {
/* 162 */         return outputInt((int)v, b, off);
/*     */       }
/* 164 */       if (v == Long.MIN_VALUE) {
/* 165 */         return _outputSmallestL(b, off);
/*     */       }
/* 167 */       b[off++] = '-';
/* 168 */       v = -v;
/*     */     }
/* 170 */     else if (v <= MAX_INT_AS_LONG) {
/* 171 */       return outputInt((int)v, b, off);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     long upper = v / BILLION_L;
/* 177 */     v -= upper * BILLION_L;
/*     */ 
/*     */     
/* 180 */     if (upper < BILLION_L) {
/* 181 */       off = _outputUptoBillion((int)upper, b, off);
/*     */     } else {
/*     */       
/* 184 */       long hi = upper / BILLION_L;
/* 185 */       upper -= hi * BILLION_L;
/* 186 */       off = _leading3((int)hi, b, off);
/* 187 */       off = _outputFullBillion((int)upper, b, off);
/*     */     } 
/* 189 */     return _outputFullBillion((int)v, b, off);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int outputLong(long v, byte[] b, int off) {
/* 194 */     if (v < 0L) {
/* 195 */       if (v > MIN_INT_AS_LONG) {
/* 196 */         return outputInt((int)v, b, off);
/*     */       }
/* 198 */       if (v == Long.MIN_VALUE) {
/* 199 */         return _outputSmallestL(b, off);
/*     */       }
/* 201 */       b[off++] = 45;
/* 202 */       v = -v;
/*     */     }
/* 204 */     else if (v <= MAX_INT_AS_LONG) {
/* 205 */       return outputInt((int)v, b, off);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 210 */     long upper = v / BILLION_L;
/* 211 */     v -= upper * BILLION_L;
/*     */ 
/*     */     
/* 214 */     if (upper < BILLION_L) {
/* 215 */       off = _outputUptoBillion((int)upper, b, off);
/*     */     } else {
/*     */       
/* 218 */       long hi = upper / BILLION_L;
/* 219 */       upper -= hi * BILLION_L;
/* 220 */       off = _leading3((int)hi, b, off);
/* 221 */       off = _outputFullBillion((int)upper, b, off);
/*     */     } 
/* 223 */     return _outputFullBillion((int)v, b, off);
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
/*     */   public static String toString(int v) {
/* 238 */     if (v < sSmallIntStrs.length) {
/* 239 */       if (v >= 0) {
/* 240 */         return sSmallIntStrs[v];
/*     */       }
/* 242 */       int v2 = -v - 1;
/* 243 */       if (v2 < sSmallIntStrs2.length) {
/* 244 */         return sSmallIntStrs2[v2];
/*     */       }
/*     */     } 
/* 247 */     return Integer.toString(v);
/*     */   }
/*     */   
/*     */   public static String toString(long v) {
/* 251 */     if (v <= 2147483647L && v >= -2147483648L) {
/* 252 */       return toString((int)v);
/*     */     }
/* 254 */     return Long.toString(v);
/*     */   }
/*     */   
/*     */   public static String toString(double v) {
/* 258 */     return Double.toString(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(float v) {
/* 265 */     return Float.toString(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int _outputUptoBillion(int v, char[] b, int off) {
/* 276 */     if (v < MILLION) {
/* 277 */       if (v < 1000) {
/* 278 */         return _leading3(v, b, off);
/*     */       }
/* 280 */       int i = v / 1000;
/* 281 */       int j = v - i * 1000;
/* 282 */       return _outputUptoMillion(b, off, i, j);
/*     */     } 
/* 284 */     int thousands = v / 1000;
/* 285 */     int ones = v - thousands * 1000;
/* 286 */     int millions = thousands / 1000;
/* 287 */     thousands -= millions * 1000;
/*     */     
/* 289 */     off = _leading3(millions, b, off);
/*     */     
/* 291 */     int enc = TRIPLET_TO_CHARS[thousands];
/* 292 */     b[off++] = (char)(enc >> 16);
/* 293 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 294 */     b[off++] = (char)(enc & 0x7F);
/*     */     
/* 296 */     enc = TRIPLET_TO_CHARS[ones];
/* 297 */     b[off++] = (char)(enc >> 16);
/* 298 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 299 */     b[off++] = (char)(enc & 0x7F);
/*     */     
/* 301 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputFullBillion(int v, char[] b, int off) {
/* 306 */     int thousands = v / 1000;
/* 307 */     int ones = v - thousands * 1000;
/* 308 */     int millions = thousands / 1000;
/*     */     
/* 310 */     int enc = TRIPLET_TO_CHARS[millions];
/* 311 */     b[off++] = (char)(enc >> 16);
/* 312 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 313 */     b[off++] = (char)(enc & 0x7F);
/*     */     
/* 315 */     thousands -= millions * 1000;
/* 316 */     enc = TRIPLET_TO_CHARS[thousands];
/* 317 */     b[off++] = (char)(enc >> 16);
/* 318 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 319 */     b[off++] = (char)(enc & 0x7F);
/*     */     
/* 321 */     enc = TRIPLET_TO_CHARS[ones];
/* 322 */     b[off++] = (char)(enc >> 16);
/* 323 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 324 */     b[off++] = (char)(enc & 0x7F);
/*     */     
/* 326 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputUptoBillion(int v, byte[] b, int off) {
/* 331 */     if (v < MILLION) {
/* 332 */       if (v < 1000) {
/* 333 */         return _leading3(v, b, off);
/*     */       }
/* 335 */       int i = v / 1000;
/* 336 */       int j = v - i * 1000;
/* 337 */       return _outputUptoMillion(b, off, i, j);
/*     */     } 
/* 339 */     int thousands = v / 1000;
/* 340 */     int ones = v - thousands * 1000;
/* 341 */     int millions = thousands / 1000;
/* 342 */     thousands -= millions * 1000;
/*     */     
/* 344 */     off = _leading3(millions, b, off);
/*     */     
/* 346 */     int enc = TRIPLET_TO_CHARS[thousands];
/* 347 */     b[off++] = (byte)(enc >> 16);
/* 348 */     b[off++] = (byte)(enc >> 8);
/* 349 */     b[off++] = (byte)enc;
/*     */     
/* 351 */     enc = TRIPLET_TO_CHARS[ones];
/* 352 */     b[off++] = (byte)(enc >> 16);
/* 353 */     b[off++] = (byte)(enc >> 8);
/* 354 */     b[off++] = (byte)enc;
/*     */     
/* 356 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputFullBillion(int v, byte[] b, int off) {
/* 361 */     int thousands = v / 1000;
/* 362 */     int ones = v - thousands * 1000;
/* 363 */     int millions = thousands / 1000;
/* 364 */     thousands -= millions * 1000;
/*     */     
/* 366 */     int enc = TRIPLET_TO_CHARS[millions];
/* 367 */     b[off++] = (byte)(enc >> 16);
/* 368 */     b[off++] = (byte)(enc >> 8);
/* 369 */     b[off++] = (byte)enc;
/*     */     
/* 371 */     enc = TRIPLET_TO_CHARS[thousands];
/* 372 */     b[off++] = (byte)(enc >> 16);
/* 373 */     b[off++] = (byte)(enc >> 8);
/* 374 */     b[off++] = (byte)enc;
/*     */     
/* 376 */     enc = TRIPLET_TO_CHARS[ones];
/* 377 */     b[off++] = (byte)(enc >> 16);
/* 378 */     b[off++] = (byte)(enc >> 8);
/* 379 */     b[off++] = (byte)enc;
/*     */     
/* 381 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputUptoMillion(char[] b, int off, int thousands, int ones) {
/* 386 */     int enc = TRIPLET_TO_CHARS[thousands];
/* 387 */     if (thousands > 9) {
/* 388 */       if (thousands > 99) {
/* 389 */         b[off++] = (char)(enc >> 16);
/*     */       }
/* 391 */       b[off++] = (char)(enc >> 8 & 0x7F);
/*     */     } 
/* 393 */     b[off++] = (char)(enc & 0x7F);
/*     */     
/* 395 */     enc = TRIPLET_TO_CHARS[ones];
/* 396 */     b[off++] = (char)(enc >> 16);
/* 397 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 398 */     b[off++] = (char)(enc & 0x7F);
/* 399 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputUptoMillion(byte[] b, int off, int thousands, int ones) {
/* 404 */     int enc = TRIPLET_TO_CHARS[thousands];
/* 405 */     if (thousands > 9) {
/* 406 */       if (thousands > 99) {
/* 407 */         b[off++] = (byte)(enc >> 16);
/*     */       }
/* 409 */       b[off++] = (byte)(enc >> 8);
/*     */     } 
/* 411 */     b[off++] = (byte)enc;
/*     */     
/* 413 */     enc = TRIPLET_TO_CHARS[ones];
/* 414 */     b[off++] = (byte)(enc >> 16);
/* 415 */     b[off++] = (byte)(enc >> 8);
/* 416 */     b[off++] = (byte)enc;
/* 417 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _leading3(int t, char[] b, int off) {
/* 422 */     int enc = TRIPLET_TO_CHARS[t];
/* 423 */     if (t > 9) {
/* 424 */       if (t > 99) {
/* 425 */         b[off++] = (char)(enc >> 16);
/*     */       }
/* 427 */       b[off++] = (char)(enc >> 8 & 0x7F);
/*     */     } 
/* 429 */     b[off++] = (char)(enc & 0x7F);
/* 430 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _leading3(int t, byte[] b, int off) {
/* 435 */     int enc = TRIPLET_TO_CHARS[t];
/* 436 */     if (t > 9) {
/* 437 */       if (t > 99) {
/* 438 */         b[off++] = (byte)(enc >> 16);
/*     */       }
/* 440 */       b[off++] = (byte)(enc >> 8);
/*     */     } 
/* 442 */     b[off++] = (byte)enc;
/* 443 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _full3(int t, char[] b, int off) {
/* 448 */     int enc = TRIPLET_TO_CHARS[t];
/* 449 */     b[off++] = (char)(enc >> 16);
/* 450 */     b[off++] = (char)(enc >> 8 & 0x7F);
/* 451 */     b[off++] = (char)(enc & 0x7F);
/* 452 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _full3(int t, byte[] b, int off) {
/* 457 */     int enc = TRIPLET_TO_CHARS[t];
/* 458 */     b[off++] = (byte)(enc >> 16);
/* 459 */     b[off++] = (byte)(enc >> 8);
/* 460 */     b[off++] = (byte)enc;
/* 461 */     return off;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int _outputSmallestL(char[] b, int off) {
/* 468 */     int len = SMALLEST_LONG.length();
/* 469 */     SMALLEST_LONG.getChars(0, len, b, off);
/* 470 */     return off + len;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputSmallestL(byte[] b, int off) {
/* 475 */     int len = SMALLEST_LONG.length();
/* 476 */     for (int i = 0; i < len; i++) {
/* 477 */       b[off++] = (byte)SMALLEST_LONG.charAt(i);
/*     */     }
/* 479 */     return off;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputSmallestI(char[] b, int off) {
/* 484 */     int len = SMALLEST_INT.length();
/* 485 */     SMALLEST_INT.getChars(0, len, b, off);
/* 486 */     return off + len;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _outputSmallestI(byte[] b, int off) {
/* 491 */     int len = SMALLEST_INT.length();
/* 492 */     for (int i = 0; i < len; i++) {
/* 493 */       b[off++] = (byte)SMALLEST_INT.charAt(i);
/*     */     }
/* 495 */     return off;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\NumberOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */