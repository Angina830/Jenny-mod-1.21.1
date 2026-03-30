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
/*    */ public interface FailableIntPredicate<E extends Throwable>
/*    */ {
/*    */   public static final FailableIntPredicate FALSE = t -> false;
/*    */   public static final FailableIntPredicate TRUE = t -> true;
/*    */   
/*    */   static <E extends Throwable> FailableIntPredicate<E> falsePredicate() {
/* 47 */     return FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <E extends Throwable> FailableIntPredicate<E> truePredicate() {
/* 57 */     return TRUE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableIntPredicate<E> and(FailableIntPredicate<E> other) {
/* 68 */     Objects.requireNonNull(other);
/* 69 */     return t -> (test(t) && other.test(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableIntPredicate<E> negate() {
/* 78 */     return t -> !test(t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableIntPredicate<E> or(FailableIntPredicate<E> other) {
/* 89 */     Objects.requireNonNull(other);
/* 90 */     return t -> (test(t) || other.test(t));
/*    */   }
/*    */   
/*    */   boolean test(int paramInt) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableIntPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */