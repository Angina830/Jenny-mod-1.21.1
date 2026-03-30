/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomUtils
/*     */ {
/*  40 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean nextBoolean() {
/*  67 */     return RANDOM.nextBoolean();
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
/*     */   public static byte[] nextBytes(int count) {
/*  81 */     Validate.isTrue((count >= 0), "Count cannot be negative.", new Object[0]);
/*     */     
/*  83 */     byte[] result = new byte[count];
/*  84 */     RANDOM.nextBytes(result);
/*  85 */     return result;
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
/*     */   public static int nextInt(int startInclusive, int endExclusive) {
/* 103 */     Validate.isTrue((endExclusive >= startInclusive), "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 105 */     Validate.isTrue((startInclusive >= 0), "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 107 */     if (startInclusive == endExclusive) {
/* 108 */       return startInclusive;
/*     */     }
/*     */     
/* 111 */     return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nextInt() {
/* 122 */     return nextInt(0, 2147483647);
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
/*     */   public static long nextLong(long startInclusive, long endExclusive) {
/* 140 */     Validate.isTrue((endExclusive >= startInclusive), "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 142 */     Validate.isTrue((startInclusive >= 0L), "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 144 */     if (startInclusive == endExclusive) {
/* 145 */       return startInclusive;
/*     */     }
/*     */     
/* 148 */     return (long)nextDouble(startInclusive, endExclusive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long nextLong() {
/* 159 */     return nextLong(0L, Long.MAX_VALUE);
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
/*     */   public static double nextDouble(double startInclusive, double endExclusive) {
/* 177 */     Validate.isTrue((endExclusive >= startInclusive), "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 179 */     Validate.isTrue((startInclusive >= 0.0D), "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 181 */     if (startInclusive == endExclusive) {
/* 182 */       return startInclusive;
/*     */     }
/*     */     
/* 185 */     return startInclusive + (endExclusive - startInclusive) * RANDOM.nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double nextDouble() {
/* 196 */     return nextDouble(0.0D, Double.MAX_VALUE);
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
/*     */   public static float nextFloat(float startInclusive, float endExclusive) {
/* 214 */     Validate.isTrue((endExclusive >= startInclusive), "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 216 */     Validate.isTrue((startInclusive >= 0.0F), "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 218 */     if (startInclusive == endExclusive) {
/* 219 */       return startInclusive;
/*     */     }
/*     */     
/* 222 */     return startInclusive + (endExclusive - startInclusive) * RANDOM.nextFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float nextFloat() {
/* 233 */     return nextFloat(0.0F, Float.MAX_VALUE);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\RandomUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */