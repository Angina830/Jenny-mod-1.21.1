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
/*    */ @FunctionalInterface
/*    */ public interface FailableFunction<T, R, E extends Throwable>
/*    */ {
/*    */   public static final FailableFunction NOP = t -> null;
/*    */   
/*    */   static <T, E extends Throwable> FailableFunction<T, T, E> identity() {
/* 46 */     return t -> t;
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
/*    */   static <T, R, E extends Throwable> FailableFunction<T, R, E> nop() {
/* 58 */     return NOP;
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
/*    */   default <V> FailableFunction<T, V, E> andThen(FailableFunction<? super R, ? extends V, E> after) {
/* 70 */     Objects.requireNonNull(after);
/* 71 */     return t -> after.apply(apply((T)t));
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
/*    */   default <V> FailableFunction<V, R, E> compose(FailableFunction<? super V, ? extends T, E> before) {
/* 93 */     Objects.requireNonNull(before);
/* 94 */     return v -> apply((T)before.apply(v));
/*    */   }
/*    */   
/*    */   R apply(T paramT) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */