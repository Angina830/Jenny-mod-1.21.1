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
/*    */ public interface FailableObjIntConsumer<T, E extends Throwable>
/*    */ {
/*    */   public static final FailableObjIntConsumer NOP = (t, u) -> {
/*    */     
/*    */     };
/*    */   
/*    */   void accept(T paramT, int paramInt) throws E;
/*    */   
/*    */   static <T, E extends Throwable> FailableObjIntConsumer<T, E> nop() {
/* 44 */     return NOP;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableObjIntConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */