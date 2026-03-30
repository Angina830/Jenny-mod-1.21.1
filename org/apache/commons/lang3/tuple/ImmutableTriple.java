/*     */ package org.apache.commons.lang3.tuple;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImmutableTriple<L, M, R>
/*     */   extends Triple<L, M, R>
/*     */ {
/*  45 */   public static final ImmutableTriple<?, ?, ?>[] EMPTY_ARRAY = (ImmutableTriple<?, ?, ?>[])new ImmutableTriple[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final ImmutableTriple NULL = of(null, null, null);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   public final L left;
/*     */ 
/*     */   
/*     */   public final M middle;
/*     */ 
/*     */   
/*     */   public final R right;
/*     */ 
/*     */ 
/*     */   
/*     */   public static <L, M, R> ImmutableTriple<L, M, R>[] emptyArray() {
/*  69 */     return (ImmutableTriple<L, M, R>[])EMPTY_ARRAY;
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
/*     */   
/*     */   public static <L, M, R> ImmutableTriple<L, M, R> nullTriple() {
/*  82 */     return NULL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <L, M, R> ImmutableTriple<L, M, R> of(L left, M middle, R right) {
/* 100 */     return new ImmutableTriple<>(left, middle, right);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableTriple(L left, M middle, R right) {
/* 119 */     this.left = left;
/* 120 */     this.middle = middle;
/* 121 */     this.right = right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L getLeft() {
/* 130 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public M getMiddle() {
/* 138 */     return this.middle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R getRight() {
/* 146 */     return this.right;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\tuple\ImmutableTriple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */