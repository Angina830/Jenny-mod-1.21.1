/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.ResolvedType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArrayType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _componentType;
/*     */   protected final Object _emptyArray;
/*     */   
/*     */   protected ArrayType(JavaType componentType, TypeBindings bindings, Object emptyInstance, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  33 */     super(emptyInstance.getClass(), bindings, (JavaType)null, (JavaType[])null, componentType.hashCode(), valueHandler, typeHandler, asStatic);
/*     */ 
/*     */     
/*  36 */     this._componentType = componentType;
/*  37 */     this._emptyArray = emptyInstance;
/*     */   }
/*     */   
/*     */   public static ArrayType construct(JavaType componentType, TypeBindings bindings) {
/*  41 */     return construct(componentType, bindings, (Object)null, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayType construct(JavaType componentType, TypeBindings bindings, Object valueHandler, Object typeHandler) {
/*  47 */     Object emptyInstance = Array.newInstance(componentType.getRawClass(), 0);
/*  48 */     return new ArrayType(componentType, bindings, emptyInstance, valueHandler, typeHandler, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/*  53 */     Object emptyInstance = Array.newInstance(contentType.getRawClass(), 0);
/*  54 */     return new ArrayType(contentType, this._bindings, emptyInstance, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayType withTypeHandler(Object h) {
/*  61 */     if (h == this._typeHandler) {
/*  62 */       return this;
/*     */     }
/*  64 */     return new ArrayType(this._componentType, this._bindings, this._emptyArray, this._valueHandler, h, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayType withContentTypeHandler(Object h) {
/*  70 */     if (h == this._componentType.getTypeHandler()) {
/*  71 */       return this;
/*     */     }
/*  73 */     return new ArrayType(this._componentType.withTypeHandler(h), this._bindings, this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayType withValueHandler(Object h) {
/*  79 */     if (h == this._valueHandler) {
/*  80 */       return this;
/*     */     }
/*  82 */     return new ArrayType(this._componentType, this._bindings, this._emptyArray, h, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayType withContentValueHandler(Object h) {
/*  87 */     if (h == this._componentType.getValueHandler()) {
/*  88 */       return this;
/*     */     }
/*  90 */     return new ArrayType(this._componentType.withValueHandler(h), this._bindings, this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayType withStaticTyping() {
/*  96 */     if (this._asStatic) {
/*  97 */       return this;
/*     */     }
/*  99 */     return new ArrayType(this._componentType.withStaticTyping(), this._bindings, this._emptyArray, this._valueHandler, this._typeHandler, true);
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
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/* 116 */     return _reportUnsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> contentClass, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   private JavaType _reportUnsupported() {
/* 128 */     throw new UnsupportedOperationException("Cannot narrow or widen array types");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArrayType() {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbstract() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConcrete() {
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasGenericTypes() {
/* 159 */     return this._componentType.hasGenericTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContainerType() {
/* 169 */     return true;
/*     */   }
/*     */   public JavaType getContentType() {
/* 172 */     return this._componentType;
/*     */   }
/*     */   
/*     */   public Object getContentValueHandler() {
/* 176 */     return this._componentType.getValueHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContentTypeHandler() {
/* 181 */     return this._componentType.getTypeHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHandlers() {
/* 186 */     return (super.hasHandlers() || this._componentType.hasHandlers());
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder sb) {
/* 191 */     sb.append('[');
/* 192 */     return this._componentType.getGenericSignature(sb);
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder sb) {
/* 197 */     sb.append('[');
/* 198 */     return this._componentType.getErasedSignature(sb);
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
/*     */   public String toString() {
/* 210 */     return "[array type, component type: " + this._componentType + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 216 */     if (o == this) return true; 
/* 217 */     if (o == null) return false; 
/* 218 */     if (o.getClass() != getClass()) return false;
/*     */     
/* 220 */     ArrayType other = (ArrayType)o;
/* 221 */     return this._componentType.equals(other._componentType);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\ArrayType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */