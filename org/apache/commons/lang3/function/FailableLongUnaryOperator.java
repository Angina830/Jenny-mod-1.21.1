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
/*    */ public interface FailableLongUnaryOperator<E extends Throwable>
/*    */ {
/*    */   public static final FailableLongUnaryOperator NOP = t -> 0L;
/*    */   
/*    */   static <E extends Throwable> FailableLongUnaryOperator<E> identity() {
/* 42 */     return t -> t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <E extends Throwable> FailableLongUnaryOperator<E> nop() {
/* 52 */     return NOP;
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
/*    */   default FailableLongUnaryOperator<E> andThen(FailableLongUnaryOperator<E> after) {
/* 64 */     Objects.requireNonNull(after);
/* 65 */     return t -> after.applyAsLong(applyAsLong(t));
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
/*    */   default FailableLongUnaryOperator<E> compose(FailableLongUnaryOperator<E> before) {
/* 86 */     Objects.requireNonNull(before);
/* 87 */     return v -> applyAsLong(before.applyAsLong(v));
/*    */   }
/*    */   
/*    */   long applyAsLong(long paramLong) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableLongUnaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */