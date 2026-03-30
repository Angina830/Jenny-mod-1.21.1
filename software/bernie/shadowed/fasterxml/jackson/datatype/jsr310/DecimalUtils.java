/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DecimalUtils
/*     */ {
/*  29 */   private static final BigDecimal ONE_BILLION = new BigDecimal(1000000000L);
/*     */ 
/*     */   
/*     */   private DecimalUtils() {
/*  33 */     throw new RuntimeException("DecimalUtils cannot be instantiated.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toDecimal(long seconds, int nanoseconds) {
/*  40 */     StringBuilder sb = (new StringBuilder(20)).append(seconds).append('.');
/*     */ 
/*     */     
/*  43 */     if (nanoseconds == 0L) {
/*     */ 
/*     */       
/*  46 */       if (seconds == 0L) {
/*  47 */         return "0.0";
/*     */       }
/*     */ 
/*     */       
/*  51 */       sb.append("000000000");
/*     */     } else {
/*  53 */       StringBuilder nanoSB = new StringBuilder(9);
/*  54 */       nanoSB.append(nanoseconds);
/*     */       
/*  56 */       int nanosLen = nanoSB.length();
/*  57 */       int prepZeroes = 9 - nanosLen;
/*  58 */       while (prepZeroes > 0) {
/*  59 */         prepZeroes--;
/*  60 */         sb.append('0');
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       sb.append(nanoSB);
/*     */     } 
/*  77 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigDecimal toBigDecimal(long seconds, int nanoseconds) {
/*  85 */     if (nanoseconds == 0L) {
/*     */ 
/*     */       
/*  88 */       if (seconds == 0L) {
/*  89 */         return BigDecimal.ZERO.setScale(1);
/*     */       }
/*  91 */       return BigDecimal.valueOf(seconds).setScale(9);
/*     */     } 
/*  93 */     return new BigDecimal(toDecimal(seconds, nanoseconds));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int extractNanosecondDecimal(BigDecimal value, long integer) {
/* 101 */     return value.subtract(new BigDecimal(integer)).multiply(ONE_BILLION).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\DecimalUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */