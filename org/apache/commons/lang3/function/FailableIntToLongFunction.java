/*    */ package org.apache.commons.lang3.function;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface FailableIntToLongFunction<E extends Throwable>
/*    */ {
/*    */   public static final FailableIntToLongFunction NOP = t -> 0L;
/*    */   
/*    */   long applyAsLong(int paramInt) throws E;
/*    */   
/*    */   static <E extends Throwable> FailableIntToLongFunction<E> nop() {
/* 42 */     return NOP;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableIntToLongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */