/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeKey
/*     */ {
/*     */   protected int _hashCode;
/*     */   protected Class<?> _class;
/*     */   protected JavaType _type;
/*     */   protected boolean _isTyped;
/*     */   
/*     */   public TypeKey() {}
/*     */   
/*     */   public TypeKey(TypeKey src) {
/*  28 */     this._hashCode = src._hashCode;
/*  29 */     this._class = src._class;
/*  30 */     this._type = src._type;
/*  31 */     this._isTyped = src._isTyped;
/*     */   }
/*     */   
/*     */   public TypeKey(Class<?> key, boolean typed) {
/*  35 */     this._class = key;
/*  36 */     this._type = null;
/*  37 */     this._isTyped = typed;
/*  38 */     this._hashCode = typed ? typedHash(key) : untypedHash(key);
/*     */   }
/*     */   
/*     */   public TypeKey(JavaType key, boolean typed) {
/*  42 */     this._type = key;
/*  43 */     this._class = null;
/*  44 */     this._isTyped = typed;
/*  45 */     this._hashCode = typed ? typedHash(key) : untypedHash(key);
/*     */   }
/*     */   
/*     */   public static final int untypedHash(Class<?> cls) {
/*  49 */     return cls.getName().hashCode();
/*     */   }
/*     */   
/*     */   public static final int typedHash(Class<?> cls) {
/*  53 */     return cls.getName().hashCode() + 1;
/*     */   }
/*     */   
/*     */   public static final int untypedHash(JavaType type) {
/*  57 */     return type.hashCode() - 1;
/*     */   }
/*     */   
/*     */   public static final int typedHash(JavaType type) {
/*  61 */     return type.hashCode() - 2;
/*     */   }
/*     */   
/*     */   public final void resetTyped(Class<?> cls) {
/*  65 */     this._type = null;
/*  66 */     this._class = cls;
/*  67 */     this._isTyped = true;
/*  68 */     this._hashCode = typedHash(cls);
/*     */   }
/*     */   
/*     */   public final void resetUntyped(Class<?> cls) {
/*  72 */     this._type = null;
/*  73 */     this._class = cls;
/*  74 */     this._isTyped = false;
/*  75 */     this._hashCode = untypedHash(cls);
/*     */   }
/*     */   
/*     */   public final void resetTyped(JavaType type) {
/*  79 */     this._type = type;
/*  80 */     this._class = null;
/*  81 */     this._isTyped = true;
/*  82 */     this._hashCode = typedHash(type);
/*     */   }
/*     */   
/*     */   public final void resetUntyped(JavaType type) {
/*  86 */     this._type = type;
/*  87 */     this._class = null;
/*  88 */     this._isTyped = false;
/*  89 */     this._hashCode = untypedHash(type);
/*     */   }
/*     */   
/*     */   public boolean isTyped() {
/*  93 */     return this._isTyped;
/*     */   }
/*     */   
/*     */   public Class<?> getRawType() {
/*  97 */     return this._class;
/*     */   }
/*     */   
/*     */   public JavaType getType() {
/* 101 */     return this._type;
/*     */   }
/*     */   public final int hashCode() {
/* 104 */     return this._hashCode;
/*     */   }
/*     */   public final String toString() {
/* 107 */     if (this._class != null) {
/* 108 */       return "{class: " + this._class.getName() + ", typed? " + this._isTyped + "}";
/*     */     }
/* 110 */     return "{type: " + this._type + ", typed? " + this._isTyped + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/* 116 */     if (o == null) return false; 
/* 117 */     if (o == this) return true; 
/* 118 */     if (o.getClass() != getClass()) {
/* 119 */       return false;
/*     */     }
/* 121 */     TypeKey other = (TypeKey)o;
/* 122 */     if (other._isTyped == this._isTyped) {
/* 123 */       if (this._class != null) {
/* 124 */         return (other._class == this._class);
/*     */       }
/* 126 */       return this._type.equals(other._type);
/*     */     } 
/* 128 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\TypeKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */