/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.lang.reflect.TypeVariable;
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
/*     */ public final class MapType
/*     */   extends MapLikeType
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private MapType(Class<?> mapType, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType keyT, JavaType valueT, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  23 */     super(mapType, bindings, superClass, superInts, keyT, valueT, valueHandler, typeHandler, asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapType(TypeBase base, JavaType keyT, JavaType valueT) {
/*  31 */     super(base, keyT, valueT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MapType construct(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType keyT, JavaType valueT) {
/*  40 */     return new MapType(rawType, bindings, superClass, superInts, keyT, valueT, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static MapType construct(Class<?> rawType, JavaType keyT, JavaType valueT) {
/*     */     TypeBindings bindings;
/*  48 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])rawType.getTypeParameters();
/*     */     
/*  50 */     if (arrayOfTypeVariable == null || arrayOfTypeVariable.length != 2) {
/*  51 */       bindings = TypeBindings.emptyBindings();
/*     */     } else {
/*  53 */       bindings = TypeBindings.create(rawType, keyT, valueT);
/*     */     } 
/*     */     
/*  56 */     return new MapType(rawType, bindings, _bogusSuperClass(rawType), null, keyT, valueT, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/*  63 */     return new MapType(subclass, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withTypeHandler(Object h) {
/*  70 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, h, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withContentTypeHandler(Object h) {
/*  77 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withTypeHandler(h), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withValueHandler(Object h) {
/*  84 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, h, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withContentValueHandler(Object h) {
/*  90 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withValueHandler(h), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withStaticTyping() {
/*  97 */     if (this._asStatic) {
/*  98 */       return this;
/*     */     }
/* 100 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withStaticTyping(), this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/* 107 */     if (this._valueType == contentType) {
/* 108 */       return this;
/*     */     }
/* 110 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, contentType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withKeyType(JavaType keyType) {
/* 116 */     if (keyType == this._keyType) {
/* 117 */       return this;
/*     */     }
/* 119 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 126 */     return new MapType(rawType, bindings, superClass, superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
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
/*     */   public MapType withKeyTypeHandler(Object h) {
/* 140 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withTypeHandler(h), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType withKeyValueHandler(Object h) {
/* 147 */     return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withValueHandler(h), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
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
/*     */   public String toString() {
/* 161 */     return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\MapType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */