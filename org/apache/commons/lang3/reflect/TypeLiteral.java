/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeLiteral<T>
/*     */   implements Typed<T>
/*     */ {
/*  78 */   private static final TypeVariable<Class<TypeLiteral>> T = (TypeVariable)TypeLiteral.class.getTypeParameters()[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public final Type value = (Type)Validate.notNull(TypeUtils.getTypeArguments(getClass(), TypeLiteral.class).get(T), "%s does not assign type parameter %s", new Object[] {
/*  93 */         getClass(), TypeUtils.toLongString(T)
/*     */       });
/*  95 */   private final String toString = String.format("%s<%s>", new Object[] { TypeLiteral.class.getSimpleName(), TypeUtils.toString(this.value) });
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object obj) {
/* 100 */     if (obj == this) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (!(obj instanceof TypeLiteral)) {
/* 104 */       return false;
/*     */     }
/* 106 */     TypeLiteral<?> other = (TypeLiteral)obj;
/* 107 */     return TypeUtils.equals(this.value, other.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 112 */     return 0x250 | this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return this.toString;
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 122 */     return this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\reflect\TypeLiteral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */