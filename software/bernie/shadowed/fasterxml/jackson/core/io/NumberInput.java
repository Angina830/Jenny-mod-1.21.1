/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NumberInput
/*     */ {
/*     */   public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
/*     */   static final long L_BILLION = 1000000000L;
/*  18 */   static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
/*  19 */   static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(char[] ch, int off, int len) {
/*  30 */     int num = ch[off] - 48;
/*     */     
/*  32 */     if (len > 4) {
/*  33 */       num = num * 10 + ch[++off] - 48;
/*  34 */       num = num * 10 + ch[++off] - 48;
/*  35 */       num = num * 10 + ch[++off] - 48;
/*  36 */       num = num * 10 + ch[++off] - 48;
/*  37 */       len -= 4;
/*  38 */       if (len > 4) {
/*  39 */         num = num * 10 + ch[++off] - 48;
/*  40 */         num = num * 10 + ch[++off] - 48;
/*  41 */         num = num * 10 + ch[++off] - 48;
/*  42 */         num = num * 10 + ch[++off] - 48;
/*  43 */         return num;
/*     */       } 
/*     */     } 
/*  46 */     if (len > 1) {
/*  47 */       num = num * 10 + ch[++off] - 48;
/*  48 */       if (len > 2) {
/*  49 */         num = num * 10 + ch[++off] - 48;
/*  50 */         if (len > 3) {
/*  51 */           num = num * 10 + ch[++off] - 48;
/*     */         }
/*     */       } 
/*     */     } 
/*  55 */     return num;
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
/*     */   public static int parseInt(String s) {
/*  68 */     char c = s.charAt(0);
/*  69 */     int len = s.length();
/*  70 */     boolean neg = (c == '-');
/*  71 */     int offset = 1;
/*     */ 
/*     */     
/*  74 */     if (neg) {
/*  75 */       if (len == 1 || len > 10) {
/*  76 */         return Integer.parseInt(s);
/*     */       }
/*  78 */       c = s.charAt(offset++);
/*     */     }
/*  80 */     else if (len > 9) {
/*  81 */       return Integer.parseInt(s);
/*     */     } 
/*     */     
/*  84 */     if (c > '9' || c < '0') {
/*  85 */       return Integer.parseInt(s);
/*     */     }
/*  87 */     int num = c - 48;
/*  88 */     if (offset < len) {
/*  89 */       c = s.charAt(offset++);
/*  90 */       if (c > '9' || c < '0') {
/*  91 */         return Integer.parseInt(s);
/*     */       }
/*  93 */       num = num * 10 + c - 48;
/*  94 */       if (offset < len) {
/*  95 */         c = s.charAt(offset++);
/*  96 */         if (c > '9' || c < '0') {
/*  97 */           return Integer.parseInt(s);
/*     */         }
/*  99 */         num = num * 10 + c - 48;
/*     */         
/* 101 */         if (offset < len) {
/*     */           do {
/* 103 */             c = s.charAt(offset++);
/* 104 */             if (c > '9' || c < '0') {
/* 105 */               return Integer.parseInt(s);
/*     */             }
/* 107 */             num = num * 10 + c - 48;
/* 108 */           } while (offset < len);
/*     */         }
/*     */       } 
/*     */     } 
/* 112 */     return neg ? -num : num;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLong(char[] ch, int off, int len) {
/* 118 */     int len1 = len - 9;
/* 119 */     long val = parseInt(ch, off, len1) * 1000000000L;
/* 120 */     return val + parseInt(ch, off + len1, 9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLong(String s) {
/* 128 */     int length = s.length();
/* 129 */     if (length <= 9) {
/* 130 */       return parseInt(s);
/*     */     }
/*     */     
/* 133 */     return Long.parseLong(s);
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
/*     */   public static boolean inLongRange(char[] ch, int off, int len, boolean negative) {
/* 148 */     String cmpStr = negative ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
/* 149 */     int cmpLen = cmpStr.length();
/* 150 */     if (len < cmpLen) return true; 
/* 151 */     if (len > cmpLen) return false;
/*     */     
/* 153 */     for (int i = 0; i < cmpLen; i++) {
/* 154 */       int diff = ch[off + i] - cmpStr.charAt(i);
/* 155 */       if (diff != 0) {
/* 156 */         return (diff < 0);
/*     */       }
/*     */     } 
/* 159 */     return true;
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
/*     */   public static boolean inLongRange(String s, boolean negative) {
/* 171 */     String cmp = negative ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
/* 172 */     int cmpLen = cmp.length();
/* 173 */     int alen = s.length();
/* 174 */     if (alen < cmpLen) return true; 
/* 175 */     if (alen > cmpLen) return false;
/*     */ 
/*     */     
/* 178 */     for (int i = 0; i < cmpLen; i++) {
/* 179 */       int diff = s.charAt(i) - cmp.charAt(i);
/* 180 */       if (diff != 0) {
/* 181 */         return (diff < 0);
/*     */       }
/*     */     } 
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseAsInt(String s, int def) {
/* 189 */     if (s == null) {
/* 190 */       return def;
/*     */     }
/* 192 */     s = s.trim();
/* 193 */     int len = s.length();
/* 194 */     if (len == 0) {
/* 195 */       return def;
/*     */     }
/*     */     
/* 198 */     int i = 0;
/* 199 */     if (i < len) {
/* 200 */       char c = s.charAt(0);
/* 201 */       if (c == '+') {
/* 202 */         s = s.substring(1);
/* 203 */         len = s.length();
/* 204 */       } else if (c == '-') {
/* 205 */         i++;
/*     */       } 
/*     */     } 
/* 208 */     for (; i < len; i++) {
/* 209 */       char c = s.charAt(i);
/*     */       
/* 211 */       if (c > '9' || c < '0') {
/*     */         try {
/* 213 */           return (int)parseDouble(s);
/* 214 */         } catch (NumberFormatException e) {
/* 215 */           return def;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     try {
/* 220 */       return Integer.parseInt(s);
/* 221 */     } catch (NumberFormatException e) {
/* 222 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static long parseAsLong(String s, long def) {
/* 227 */     if (s == null) {
/* 228 */       return def;
/*     */     }
/* 230 */     s = s.trim();
/* 231 */     int len = s.length();
/* 232 */     if (len == 0) {
/* 233 */       return def;
/*     */     }
/*     */     
/* 236 */     int i = 0;
/* 237 */     if (i < len) {
/* 238 */       char c = s.charAt(0);
/* 239 */       if (c == '+') {
/* 240 */         s = s.substring(1);
/* 241 */         len = s.length();
/* 242 */       } else if (c == '-') {
/* 243 */         i++;
/*     */       } 
/*     */     } 
/* 246 */     for (; i < len; i++) {
/* 247 */       char c = s.charAt(i);
/*     */       
/* 249 */       if (c > '9' || c < '0') {
/*     */         try {
/* 251 */           return (long)parseDouble(s);
/* 252 */         } catch (NumberFormatException e) {
/* 253 */           return def;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     try {
/* 258 */       return Long.parseLong(s);
/* 259 */     } catch (NumberFormatException e) {
/* 260 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double parseAsDouble(String s, double def) {
/* 265 */     if (s == null) return def; 
/* 266 */     s = s.trim();
/* 267 */     int len = s.length();
/* 268 */     if (len == 0) {
/* 269 */       return def;
/*     */     }
/*     */     try {
/* 272 */       return parseDouble(s);
/* 273 */     } catch (NumberFormatException e) {
/* 274 */       return def;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(String s) throws NumberFormatException {
/* 282 */     if ("2.2250738585072012e-308".equals(s)) {
/* 283 */       return Double.MIN_VALUE;
/*     */     }
/* 285 */     return Double.parseDouble(s);
/*     */   }
/*     */   public static BigDecimal parseBigDecimal(String s) throws NumberFormatException {
/*     */     
/* 289 */     try { return new BigDecimal(s); } catch (NumberFormatException e)
/* 290 */     { throw _badBD(s); }
/*     */   
/*     */   }
/*     */   
/*     */   public static BigDecimal parseBigDecimal(char[] b) throws NumberFormatException {
/* 295 */     return parseBigDecimal(b, 0, b.length);
/*     */   }
/*     */   public static BigDecimal parseBigDecimal(char[] b, int off, int len) throws NumberFormatException {
/*     */     
/* 299 */     try { return new BigDecimal(b, off, len); } catch (NumberFormatException e)
/* 300 */     { throw _badBD(new String(b, off, len)); }
/*     */   
/*     */   }
/*     */   
/*     */   private static NumberFormatException _badBD(String s) {
/* 305 */     return new NumberFormatException("Value \"" + s + "\" can not be represented as BigDecimal");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\NumberInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */