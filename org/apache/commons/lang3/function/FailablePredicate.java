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
/*    */ @FunctionalInterface
/*    */ public interface FailablePredicate<T, E extends Throwable>
/*    */ {
/*    */   public static final FailablePredicate FALSE = t -> false;
/*    */   public static final FailablePredicate TRUE = t -> true;
/*    */   
/*    */   static <T, E extends Throwable> FailablePredicate<T, E> falsePredicate() {
/* 49 */     return FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <T, E extends Throwable> FailablePredicate<T, E> truePredicate() {
/* 60 */     return TRUE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailablePredicate<T, E> and(FailablePredicate<? super T, E> other) {
/* 71 */     Objects.requireNonNull(other);
/* 72 */     return t -> (test((T)t) && other.test(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailablePredicate<T, E> negate() {
/* 81 */     return t -> !test((T)t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailablePredicate<T, E> or(FailablePredicate<? super T, E> other) {
/* 92 */     Objects.requireNonNull(other);
/* 93 */     return t -> (test((T)t) || other.test(t));
/*    */   }
/*    */   
/*    */   boolean test(T paramT) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailablePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */