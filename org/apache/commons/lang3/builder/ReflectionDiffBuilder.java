/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.commons.lang3.reflect.FieldUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionDiffBuilder<T>
/*     */   implements Builder<DiffResult<T>>
/*     */ {
/*     */   private final Object left;
/*     */   private final Object right;
/*     */   private final DiffBuilder<T> diffBuilder;
/*     */   
/*     */   public ReflectionDiffBuilder(T lhs, T rhs, ToStringStyle style) {
/*  97 */     this.left = lhs;
/*  98 */     this.right = rhs;
/*  99 */     this.diffBuilder = new DiffBuilder<>(lhs, rhs, style);
/*     */   }
/*     */ 
/*     */   
/*     */   public DiffResult<T> build() {
/* 104 */     if (this.left.equals(this.right)) {
/* 105 */       return this.diffBuilder.build();
/*     */     }
/*     */     
/* 108 */     appendFields(this.left.getClass());
/* 109 */     return this.diffBuilder.build();
/*     */   }
/*     */   
/*     */   private void appendFields(Class<?> clazz) {
/* 113 */     for (Field field : FieldUtils.getAllFields(clazz)) {
/* 114 */       if (accept(field)) {
/*     */         try {
/* 116 */           this.diffBuilder.append(field.getName(), FieldUtils.readField(field, this.left, true), 
/* 117 */               FieldUtils.readField(field, this.right, true));
/* 118 */         } catch (IllegalAccessException ex) {
/*     */ 
/*     */           
/* 121 */           throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean accept(Field field) {
/* 128 */     if (field.getName().indexOf('$') != -1) {
/* 129 */       return false;
/*     */     }
/* 131 */     if (Modifier.isTransient(field.getModifiers())) {
/* 132 */       return false;
/*     */     }
/* 134 */     return !Modifier.isStatic(field.getModifiers());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\ReflectionDiffBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */