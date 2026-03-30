/*     */ package org.apache.commons.lang3.mutable;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MutableObject<T>
/*     */   implements Mutable<T>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 86241875189L;
/*     */   private T value;
/*     */   
/*     */   public MutableObject() {}
/*     */   
/*     */   public MutableObject(T value) {
/*  54 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getValue() {
/*  65 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(T value) {
/*  75 */     this.value = value;
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
/*     */   public boolean equals(Object obj) {
/*  93 */     if (obj == null) {
/*  94 */       return false;
/*     */     }
/*  96 */     if (this == obj) {
/*  97 */       return true;
/*     */     }
/*  99 */     if (getClass() == obj.getClass()) {
/* 100 */       MutableObject<?> that = (MutableObject)obj;
/* 101 */       return this.value.equals(that.value);
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return (this.value == null) ? 0 : this.value.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return (this.value == null) ? "null" : this.value.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\mutable\MutableObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */