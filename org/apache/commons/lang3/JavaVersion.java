/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import org.apache.commons.lang3.math.NumberUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum JavaVersion
/*     */ {
/*  33 */   JAVA_0_9(1.5F, "0.9"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   JAVA_1_1(1.1F, "1.1"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   JAVA_1_2(1.2F, "1.2"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   JAVA_1_3(1.3F, "1.3"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   JAVA_1_4(1.4F, "1.4"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   JAVA_1_5(1.5F, "1.5"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   JAVA_1_6(1.6F, "1.6"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   JAVA_1_7(1.7F, "1.7"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   JAVA_1_8(1.8F, "1.8"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   JAVA_1_9(9.0F, "9"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   JAVA_9(9.0F, "9"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   JAVA_10(10.0F, "10"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   JAVA_11(11.0F, "11"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   JAVA_12(12.0F, "12"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   JAVA_13(13.0F, "13"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   JAVA_14(14.0F, "14"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   JAVA_15(15.0F, "15"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   JAVA_16(16.0F, "16"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   JAVA_RECENT(maxVersion(), Float.toString(maxVersion()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final float value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JavaVersion(float value, String name) {
/* 161 */     this.value = value;
/* 162 */     this.name = name;
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
/*     */   public boolean atLeast(JavaVersion requiredVersion) {
/* 176 */     return (this.value >= requiredVersion.value);
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
/*     */   public boolean atMost(JavaVersion requiredVersion) {
/* 191 */     return (this.value <= requiredVersion.value);
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
/*     */   static JavaVersion getJavaVersion(String nom) {
/* 205 */     return get(nom);
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
/*     */   static JavaVersion get(String nom) {
/* 218 */     if (nom == null)
/* 219 */       return null; 
/* 220 */     if ("0.9".equals(nom))
/* 221 */       return JAVA_0_9; 
/* 222 */     if ("1.1".equals(nom))
/* 223 */       return JAVA_1_1; 
/* 224 */     if ("1.2".equals(nom))
/* 225 */       return JAVA_1_2; 
/* 226 */     if ("1.3".equals(nom))
/* 227 */       return JAVA_1_3; 
/* 228 */     if ("1.4".equals(nom))
/* 229 */       return JAVA_1_4; 
/* 230 */     if ("1.5".equals(nom))
/* 231 */       return JAVA_1_5; 
/* 232 */     if ("1.6".equals(nom))
/* 233 */       return JAVA_1_6; 
/* 234 */     if ("1.7".equals(nom))
/* 235 */       return JAVA_1_7; 
/* 236 */     if ("1.8".equals(nom))
/* 237 */       return JAVA_1_8; 
/* 238 */     if ("9".equals(nom))
/* 239 */       return JAVA_9; 
/* 240 */     if ("10".equals(nom))
/* 241 */       return JAVA_10; 
/* 242 */     if ("11".equals(nom))
/* 243 */       return JAVA_11; 
/* 244 */     if ("12".equals(nom))
/* 245 */       return JAVA_12; 
/* 246 */     if ("13".equals(nom))
/* 247 */       return JAVA_13; 
/* 248 */     if ("14".equals(nom))
/* 249 */       return JAVA_14; 
/* 250 */     if ("15".equals(nom))
/* 251 */       return JAVA_15; 
/* 252 */     if ("16".equals(nom)) {
/* 253 */       return JAVA_16;
/*     */     }
/* 255 */     float v = toFloatVersion(nom);
/* 256 */     if (v - 1.0D < 1.0D) {
/* 257 */       int firstComma = Math.max(nom.indexOf('.'), nom.indexOf(','));
/* 258 */       int end = Math.max(nom.length(), nom.indexOf(',', firstComma));
/* 259 */       if (Float.parseFloat(nom.substring(firstComma + 1, end)) > 0.9F) {
/* 260 */         return JAVA_RECENT;
/*     */       }
/* 262 */     } else if (v > 10.0F) {
/* 263 */       return JAVA_RECENT;
/*     */     } 
/* 265 */     return null;
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
/*     */   public String toString() {
/* 278 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float maxVersion() {
/* 287 */     float v = toFloatVersion(System.getProperty("java.specification.version", "99.0"));
/* 288 */     if (v > 0.0F) {
/* 289 */       return v;
/*     */     }
/* 291 */     return 99.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float toFloatVersion(String value) {
/* 301 */     int defaultReturnValue = -1;
/* 302 */     if (value.contains(".")) {
/* 303 */       String[] toParse = value.split("\\.");
/* 304 */       if (toParse.length >= 2) {
/* 305 */         return NumberUtils.toFloat(toParse[0] + '.' + toParse[1], -1.0F);
/*     */       }
/*     */     } else {
/* 308 */       return NumberUtils.toFloat(value, -1.0F);
/*     */     } 
/* 310 */     return -1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\JavaVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */