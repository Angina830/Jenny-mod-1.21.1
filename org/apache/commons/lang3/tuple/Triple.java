/*     */ package org.apache.commons.lang3.tuple;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import org.apache.commons.lang3.builder.CompareToBuilder;
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
/*     */ public abstract class Triple<L, M, R>
/*     */   implements Comparable<Triple<L, M, R>>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final class TripleAdapter<L, M, R>
/*     */     extends Triple<L, M, R>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public L getLeft() {
/*  48 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public M getMiddle() {
/*  53 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public R getRight() {
/*  58 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final Triple<?, ?, ?>[] EMPTY_ARRAY = (Triple<?, ?, ?>[])new TripleAdapter[0];
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
/*     */   public static <L, M, R> Triple<L, M, R>[] emptyArray() {
/*  88 */     return (Triple<L, M, R>[])EMPTY_ARRAY;
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
/*     */   public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
/* 106 */     return new ImmutableTriple<>(left, middle, right);
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
/*     */   public int compareTo(Triple<L, M, R> other) {
/* 120 */     return (new CompareToBuilder()).append(getLeft(), other.getLeft())
/* 121 */       .append(getMiddle(), other.getMiddle())
/* 122 */       .append(getRight(), other.getRight()).toComparison();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 133 */     if (obj == this) {
/* 134 */       return true;
/*     */     }
/* 136 */     if (obj instanceof Triple) {
/* 137 */       Triple<?, ?, ?> other = (Triple<?, ?, ?>)obj;
/* 138 */       return (Objects.equals(getLeft(), other.getLeft()) && 
/* 139 */         Objects.equals(getMiddle(), other.getMiddle()) && 
/* 140 */         Objects.equals(getRight(), other.getRight()));
/*     */     } 
/* 142 */     return false;
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
/*     */   public int hashCode() {
/* 174 */     return Objects.hashCode(getLeft()) ^ Objects.hashCode(getMiddle()) ^ Objects.hashCode(getRight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 184 */     return "(" + getLeft() + "," + getMiddle() + "," + getRight() + ")";
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
/*     */   public String toString(String format) {
/* 199 */     return String.format(format, new Object[] { getLeft(), getMiddle(), getRight() });
/*     */   }
/*     */   
/*     */   public abstract L getLeft();
/*     */   
/*     */   public abstract M getMiddle();
/*     */   
/*     */   public abstract R getRight();
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\tuple\Triple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */