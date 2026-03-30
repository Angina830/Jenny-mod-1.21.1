/*     */ package org.apache.commons.lang3.math;
/*     */ 
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
/*     */ public class IEEE754rUtils
/*     */ {
/*     */   public static double min(double... array) {
/*  40 */     Validate.notNull(array, "The Array must not be null", new Object[0]);
/*  41 */     Validate.isTrue((array.length != 0), "Array cannot be empty.", new Object[0]);
/*     */ 
/*     */     
/*  44 */     double min = array[0];
/*  45 */     for (int i = 1; i < array.length; i++) {
/*  46 */       min = min(array[i], min);
/*     */     }
/*     */     
/*  49 */     return min;
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
/*     */   public static float min(float... array) {
/*  62 */     Validate.notNull(array, "The Array must not be null", new Object[0]);
/*  63 */     Validate.isTrue((array.length != 0), "Array cannot be empty.", new Object[0]);
/*     */ 
/*     */     
/*  66 */     float min = array[0];
/*  67 */     for (int i = 1; i < array.length; i++) {
/*  68 */       min = min(array[i], min);
/*     */     }
/*     */     
/*  71 */     return min;
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
/*     */   public static double min(double a, double b, double c) {
/*  85 */     return min(min(a, b), c);
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
/*     */   public static double min(double a, double b) {
/*  98 */     if (Double.isNaN(a)) {
/*  99 */       return b;
/*     */     }
/* 101 */     if (Double.isNaN(b)) {
/* 102 */       return a;
/*     */     }
/* 104 */     return Math.min(a, b);
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
/*     */   public static float min(float a, float b, float c) {
/* 119 */     return min(min(a, b), c);
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
/*     */   public static float min(float a, float b) {
/* 132 */     if (Float.isNaN(a)) {
/* 133 */       return b;
/*     */     }
/* 135 */     if (Float.isNaN(b)) {
/* 136 */       return a;
/*     */     }
/* 138 */     return Math.min(a, b);
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
/*     */   public static double max(double... array) {
/* 152 */     Validate.notNull(array, "The Array must not be null", new Object[0]);
/* 153 */     Validate.isTrue((array.length != 0), "Array cannot be empty.", new Object[0]);
/*     */ 
/*     */     
/* 156 */     double max = array[0];
/* 157 */     for (int j = 1; j < array.length; j++) {
/* 158 */       max = max(array[j], max);
/*     */     }
/*     */     
/* 161 */     return max;
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
/*     */   public static float max(float... array) {
/* 174 */     Validate.notNull(array, "The Array must not be null", new Object[0]);
/* 175 */     Validate.isTrue((array.length != 0), "Array cannot be empty.", new Object[0]);
/*     */ 
/*     */     
/* 178 */     float max = array[0];
/* 179 */     for (int j = 1; j < array.length; j++) {
/* 180 */       max = max(array[j], max);
/*     */     }
/*     */     
/* 183 */     return max;
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
/*     */   public static double max(double a, double b, double c) {
/* 197 */     return max(max(a, b), c);
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
/*     */   public static double max(double a, double b) {
/* 210 */     if (Double.isNaN(a)) {
/* 211 */       return b;
/*     */     }
/* 213 */     if (Double.isNaN(b)) {
/* 214 */       return a;
/*     */     }
/* 216 */     return Math.max(a, b);
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
/*     */   public static float max(float a, float b, float c) {
/* 231 */     return max(max(a, b), c);
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
/*     */   public static float max(float a, float b) {
/* 244 */     if (Float.isNaN(a)) {
/* 245 */       return b;
/*     */     }
/* 247 */     if (Float.isNaN(b)) {
/* 248 */       return a;
/*     */     }
/* 250 */     return Math.max(a, b);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\math\IEEE754rUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */