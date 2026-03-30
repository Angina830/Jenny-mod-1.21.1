/*     */ package org.apache.commons.lang3.compare;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComparableUtils
/*     */ {
/*     */   public static class ComparableCheckBuilder<A extends Comparable<A>>
/*     */   {
/*     */     private final A a;
/*     */     
/*     */     private ComparableCheckBuilder(A a) {
/*  42 */       this.a = a;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean between(A b, A c) {
/*  53 */       return (betweenOrdered(b, c) || betweenOrdered(c, b));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean betweenExclusive(A b, A c) {
/*  64 */       return (betweenOrderedExclusive(b, c) || betweenOrderedExclusive(c, b));
/*     */     }
/*     */     
/*     */     private boolean betweenOrdered(A b, A c) {
/*  68 */       return (greaterThanOrEqualTo(b) && lessThanOrEqualTo(c));
/*     */     }
/*     */     
/*     */     private boolean betweenOrderedExclusive(A b, A c) {
/*  72 */       return (greaterThan(b) && lessThan(c));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equalTo(A b) {
/*  82 */       return (this.a.compareTo(b) == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean greaterThan(A b) {
/*  92 */       return (this.a.compareTo(b) > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean greaterThanOrEqualTo(A b) {
/* 102 */       return (this.a.compareTo(b) >= 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean lessThan(A b) {
/* 112 */       return (this.a.compareTo(b) < 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean lessThanOrEqualTo(A b) {
/* 122 */       return (this.a.compareTo(b) <= 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> Predicate<A> between(A b, A c) {
/* 135 */     return a -> is(a).between(b, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> Predicate<A> betweenExclusive(A b, A c) {
/* 147 */     return a -> is(a).betweenExclusive(b, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> Predicate<A> ge(A b) {
/* 159 */     return a -> is(a).greaterThanOrEqualTo(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> Predicate<A> gt(A b) {
/* 170 */     return a -> is(a).greaterThan(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> ComparableCheckBuilder<A> is(A a) {
/* 181 */     return new ComparableCheckBuilder<>((Comparable)a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> Predicate<A> le(A b) {
/* 193 */     return a -> is(a).lessThanOrEqualTo(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Comparable<A>> Predicate<A> lt(A b) {
/* 204 */     return a -> is(a).lessThan(b);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\compare\ComparableUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */