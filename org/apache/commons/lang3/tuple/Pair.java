/*     */ package org.apache.commons.lang3.tuple;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
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
/*     */ public abstract class Pair<L, R>
/*     */   implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4954918890077093841L;
/*     */   
/*     */   private static final class PairAdapter<L, R>
/*     */     extends Pair<L, R>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public L getLeft() {
/*  49 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public R getRight() {
/*  54 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public R setValue(R value) {
/*  59 */       return null;
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
/*  75 */   public static final Pair<?, ?>[] EMPTY_ARRAY = (Pair<?, ?>[])new PairAdapter[0];
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
/*     */   public static <L, R> Pair<L, R>[] emptyArray() {
/*  88 */     return (Pair<L, R>[])EMPTY_ARRAY;
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
/*     */   public static <L, R> Pair<L, R> of(L left, R right) {
/* 104 */     return ImmutablePair.of(left, right);
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
/*     */   public static <L, R> Pair<L, R> of(Map.Entry<L, R> pair) {
/* 120 */     return ImmutablePair.of(pair);
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
/*     */   public int compareTo(Pair<L, R> other) {
/* 133 */     return (new CompareToBuilder()).append(getLeft(), other.getLeft())
/* 134 */       .append(getRight(), other.getRight()).toComparison();
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
/* 145 */     if (obj == this) {
/* 146 */       return true;
/*     */     }
/* 148 */     if (obj instanceof Map.Entry) {
/* 149 */       Map.Entry<?, ?> other = (Map.Entry<?, ?>)obj;
/* 150 */       return (Objects.equals(getKey(), other.getKey()) && 
/* 151 */         Objects.equals(getValue(), other.getValue()));
/*     */     } 
/* 153 */     return false;
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
/*     */   public final L getKey() {
/* 166 */     return getLeft();
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
/*     */   public R getValue() {
/* 198 */     return getRight();
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
/*     */   public int hashCode() {
/* 210 */     return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 220 */     return "(" + getLeft() + ',' + getRight() + ')';
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
/* 235 */     return String.format(format, new Object[] { getLeft(), getRight() });
/*     */   }
/*     */   
/*     */   public abstract L getLeft();
/*     */   
/*     */   public abstract R getRight();
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\tuple\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */