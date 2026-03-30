/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiffResult<T>
/*     */   implements Iterable<Diff<?>>
/*     */ {
/*     */   public static final String OBJECTS_SAME_STRING = "";
/*     */   private static final String DIFFERS_STRING = "differs from";
/*     */   private final List<Diff<?>> diffs;
/*     */   private final T lhs;
/*     */   private final T rhs;
/*     */   private final ToStringStyle style;
/*     */   
/*     */   DiffResult(T lhs, T rhs, List<Diff<?>> diffs, ToStringStyle style) {
/*  76 */     Validate.notNull(lhs, "Left hand object cannot be null", new Object[0]);
/*  77 */     Validate.notNull(rhs, "Right hand object cannot be null", new Object[0]);
/*  78 */     Validate.notNull(diffs, "List of differences cannot be null", new Object[0]);
/*     */     
/*  80 */     this.diffs = diffs;
/*  81 */     this.lhs = lhs;
/*  82 */     this.rhs = rhs;
/*     */     
/*  84 */     if (style == null) {
/*  85 */       this.style = ToStringStyle.DEFAULT_STYLE;
/*     */     } else {
/*  87 */       this.style = style;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getLeft() {
/*  98 */     return this.lhs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getRight() {
/* 108 */     return this.rhs;
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
/*     */   public List<Diff<?>> getDiffs() {
/* 120 */     return Collections.unmodifiableList(this.diffs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfDiffs() {
/* 131 */     return this.diffs.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToStringStyle getToStringStyle() {
/* 142 */     return this.style;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 178 */     return toString(this.style);
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
/*     */   public String toString(ToStringStyle style) {
/* 193 */     if (this.diffs.isEmpty()) {
/* 194 */       return "";
/*     */     }
/*     */     
/* 197 */     ToStringBuilder lhsBuilder = new ToStringBuilder(this.lhs, style);
/* 198 */     ToStringBuilder rhsBuilder = new ToStringBuilder(this.rhs, style);
/*     */     
/* 200 */     for (Diff<?> diff : this.diffs) {
/* 201 */       lhsBuilder.append(diff.getFieldName(), diff.getLeft());
/* 202 */       rhsBuilder.append(diff.getFieldName(), diff.getRight());
/*     */     } 
/*     */     
/* 205 */     return String.format("%s %s %s", new Object[] { lhsBuilder.build(), "differs from", rhsBuilder
/* 206 */           .build() });
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
/*     */   public Iterator<Diff<?>> iterator() {
/* 218 */     return this.diffs.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\DiffResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */