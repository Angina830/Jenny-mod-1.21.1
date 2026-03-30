/*    */ package org.apache.commons.lang3.function;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface FailableBiFunction<T, U, R, E extends Throwable>
/*    */ {
/*    */   public static final FailableBiFunction NOP = (t, u) -> null;
/*    */   
/*    */   static <T, U, R, E extends Throwable> FailableBiFunction<T, U, R, E> nop() {
/* 50 */     return NOP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default <V> FailableBiFunction<T, U, V, E> andThen(FailableFunction<? super R, ? extends V, E> after) {
/* 62 */     Objects.requireNonNull(after);
/* 63 */     return (t, u) -> after.apply(apply((T)t, (U)u));
/*    */   }
/*    */   
/*    */   R apply(T paramT, U paramU) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableBiFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */