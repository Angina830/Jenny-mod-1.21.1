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
/*    */ public interface FailableDoubleUnaryOperator<E extends Throwable>
/*    */ {
/*    */   public static final FailableDoubleUnaryOperator NOP = t -> 0.0D;
/*    */   
/*    */   static <E extends Throwable> FailableDoubleUnaryOperator<E> identity() {
/* 42 */     return t -> t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <E extends Throwable> FailableDoubleUnaryOperator<E> nop() {
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
/*    */ 
/*    */   
/*    */   default FailableDoubleUnaryOperator<E> andThen(FailableDoubleUnaryOperator<E> after) {
/* 66 */     Objects.requireNonNull(after);
/* 67 */     return t -> after.applyAsDouble(applyAsDouble(t));
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
/*    */   
/*    */   default FailableDoubleUnaryOperator<E> compose(FailableDoubleUnaryOperator<E> before) {
/* 90 */     Objects.requireNonNull(before);
/* 91 */     return v -> applyAsDouble(before.applyAsDouble(v));
/*    */   }
/*    */   
/*    */   double applyAsDouble(double paramDouble) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableDoubleUnaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */