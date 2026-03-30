/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.apache.commons.lang3.reflect.TypeUtils;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Diff<T>
/*     */   extends Pair<T, T>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Type type;
/*     */   private final String fieldName;
/*     */   
/*     */   protected Diff(String fieldName) {
/*  59 */     this.type = (Type)ObjectUtils.defaultIfNull(
/*  60 */         TypeUtils.getTypeArguments(getClass(), Diff.class).get(Diff.class
/*  61 */           .getTypeParameters()[0]), Object.class);
/*  62 */     this.fieldName = fieldName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getType() {
/*  73 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFieldName() {
/*  84 */     return this.fieldName;
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
/*     */   public final String toString() {
/* 101 */     return String.format("[%s: %s, %s]", new Object[] { this.fieldName, getLeft(), getRight() });
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
/*     */   public final T setValue(T value) {
/* 115 */     throw new UnsupportedOperationException("Cannot alter Diff object.");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\Diff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */