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
/*    */ public interface FailableBiPredicate<T, U, E extends Throwable>
/*    */ {
/*    */   public static final FailableBiPredicate FALSE = (t, u) -> false;
/*    */   public static final FailableBiPredicate TRUE = (t, u) -> true;
/*    */   
/*    */   static <T, U, E extends Throwable> FailableBiPredicate<T, U, E> falsePredicate() {
/* 51 */     return FALSE;
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
/*    */   static <T, U, E extends Throwable> FailableBiPredicate<T, U, E> truePredicate() {
/* 63 */     return TRUE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableBiPredicate<T, U, E> and(FailableBiPredicate<? super T, ? super U, E> other) {
/* 74 */     Objects.requireNonNull(other);
/* 75 */     return (t, u) -> (test((T)t, (U)u) && other.test(t, u));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableBiPredicate<T, U, E> negate() {
/* 84 */     return (t, u) -> !test((T)t, (U)u);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableBiPredicate<T, U, E> or(FailableBiPredicate<? super T, ? super U, E> other) {
/* 95 */     Objects.requireNonNull(other);
/* 96 */     return (t, u) -> (test((T)t, (U)u) || other.test(t, u));
/*    */   }
/*    */   
/*    */   boolean test(T paramT, U paramU) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableBiPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */