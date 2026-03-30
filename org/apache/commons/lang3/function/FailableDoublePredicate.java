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
/*    */ public interface FailableDoublePredicate<E extends Throwable>
/*    */ {
/*    */   public static final FailableDoublePredicate FALSE = t -> false;
/*    */   public static final FailableDoublePredicate TRUE = t -> true;
/*    */   
/*    */   static <E extends Throwable> FailableDoublePredicate<E> falsePredicate() {
/* 47 */     return FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <E extends Throwable> FailableDoublePredicate<E> truePredicate() {
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
/*    */   default FailableDoublePredicate<E> and(FailableDoublePredicate<E> other) {
/* 68 */     Objects.requireNonNull(other);
/* 69 */     return t -> (test(t) && other.test(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FailableDoublePredicate<E> negate() {
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
/*    */   default FailableDoublePredicate<E> or(FailableDoublePredicate<E> other) {
/* 89 */     Objects.requireNonNull(other);
/* 90 */     return t -> (test(t) || other.test(t));
/*    */   }
/*    */   
/*    */   boolean test(double paramDouble) throws E;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableDoublePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */