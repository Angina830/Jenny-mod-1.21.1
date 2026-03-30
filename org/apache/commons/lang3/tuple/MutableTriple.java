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
/*     */ public class MutableTriple<L, M, R>
/*     */   extends Triple<L, M, R>
/*     */ {
/*  40 */   public static final MutableTriple<?, ?, ?>[] EMPTY_ARRAY = (MutableTriple<?, ?, ?>[])new MutableTriple[0];
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   public L left;
/*     */ 
/*     */   
/*     */   public M middle;
/*     */ 
/*     */   
/*     */   public R right;
/*     */ 
/*     */ 
/*     */   
/*     */   public static <L, M, R> MutableTriple<L, M, R>[] emptyArray() {
/*  57 */     return (MutableTriple<L, M, R>[])EMPTY_ARRAY;
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
/*     */   public static <L, M, R> MutableTriple<L, M, R> of(L left, M middle, R right) {
/*  75 */     return new MutableTriple<>(left, middle, right);
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
/*     */   public MutableTriple() {}
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
/*     */   public MutableTriple(L left, M middle, R right) {
/* 101 */     this.left = left;
/* 102 */     this.middle = middle;
/* 103 */     this.right = right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L getLeft() {
/* 112 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public M getMiddle() {
/* 120 */     return this.middle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R getRight() {
/* 128 */     return this.right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeft(L left) {
/* 137 */     this.left = left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMiddle(M middle) {
/* 146 */     this.middle = middle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRight(R right) {
/* 155 */     this.right = right;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\tuple\MutableTriple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */