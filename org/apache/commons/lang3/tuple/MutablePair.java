/*     */ package org.apache.commons.lang3.tuple;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ public class MutablePair<L, R>
/*     */   extends Pair<L, R>
/*     */ {
/*  41 */   public static final MutablePair<?, ?>[] EMPTY_ARRAY = (MutablePair<?, ?>[])new MutablePair[0];
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 4954918890077093841L;
/*     */ 
/*     */ 
/*     */   
/*     */   public L left;
/*     */ 
/*     */   
/*     */   public R right;
/*     */ 
/*     */ 
/*     */   
/*     */   public static <L, R> MutablePair<L, R>[] emptyArray() {
/*  57 */     return (MutablePair<L, R>[])EMPTY_ARRAY;
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
/*     */   public static <L, R> MutablePair<L, R> of(L left, R right) {
/*  73 */     return new MutablePair<>(left, right);
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
/*     */   public static <L, R> MutablePair<L, R> of(Map.Entry<L, R> pair) {
/*     */     L left;
/*     */     R right;
/*  90 */     if (pair != null) {
/*  91 */       left = pair.getKey();
/*  92 */       right = pair.getValue();
/*     */     } else {
/*  94 */       left = null;
/*  95 */       right = null;
/*     */     } 
/*  97 */     return new MutablePair<>(left, right);
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
/*     */   public MutablePair() {}
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
/*     */   public MutablePair(L left, R right) {
/* 121 */     this.left = left;
/* 122 */     this.right = right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L getLeft() {
/* 131 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R getRight() {
/* 139 */     return this.right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeft(L left) {
/* 148 */     this.left = left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRight(R right) {
/* 157 */     this.right = right;
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
/*     */   public R setValue(R value) {
/* 169 */     R result = getRight();
/* 170 */     setRight(value);
/* 171 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\tuple\MutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */