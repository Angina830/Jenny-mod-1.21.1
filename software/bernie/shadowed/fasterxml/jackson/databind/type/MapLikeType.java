/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapLikeType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _keyType;
/*     */   protected final JavaType _valueType;
/*     */   
/*     */   protected MapLikeType(Class<?> mapType, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType keyT, JavaType valueT, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  39 */     super(mapType, bindings, superClass, superInts, keyT.hashCode() ^ valueT.hashCode(), valueHandler, typeHandler, asStatic);
/*     */     
/*  41 */     this._keyType = keyT;
/*  42 */     this._valueType = valueT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapLikeType(TypeBase base, JavaType keyT, JavaType valueT) {
/*  49 */     super(base);
/*  50 */     this._keyType = keyT;
/*  51 */     this._valueType = valueT;
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
/*     */   public static MapLikeType upgradeFrom(JavaType baseType, JavaType keyT, JavaType valueT) {
/*  65 */     if (baseType instanceof TypeBase) {
/*  66 */       return new MapLikeType((TypeBase)baseType, keyT, valueT);
/*     */     }
/*  68 */     throw new IllegalArgumentException("Cannot upgrade from an instance of " + baseType.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static MapLikeType construct(Class<?> rawType, JavaType keyT, JavaType valueT) {
/*     */     TypeBindings bindings;
/*  78 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])rawType.getTypeParameters();
/*     */     
/*  80 */     if (arrayOfTypeVariable == null || arrayOfTypeVariable.length != 2) {
/*  81 */       bindings = TypeBindings.emptyBindings();
/*     */     } else {
/*  83 */       bindings = TypeBindings.create(rawType, keyT, valueT);
/*     */     } 
/*  85 */     return new MapLikeType(rawType, bindings, _bogusSuperClass(rawType), null, keyT, valueT, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/*  93 */     return new MapLikeType(subclass, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withKeyType(JavaType keyType) {
/* 102 */     if (keyType == this._keyType) {
/* 103 */       return this;
/*     */     }
/* 105 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/* 112 */     if (this._valueType == contentType) {
/* 113 */       return this;
/*     */     }
/* 115 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, contentType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withTypeHandler(Object h) {
/* 122 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, h, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withContentTypeHandler(Object h) {
/* 129 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withTypeHandler(h), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withValueHandler(Object h) {
/* 136 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, h, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withContentValueHandler(Object h) {
/* 143 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withValueHandler(h), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withHandlersFrom(JavaType src) {
/* 150 */     JavaType type = super.withHandlersFrom(src);
/* 151 */     JavaType srcKeyType = src.getKeyType();
/*     */     
/* 153 */     if (type instanceof MapLikeType && 
/* 154 */       srcKeyType != null) {
/* 155 */       JavaType ct = this._keyType.withHandlersFrom(srcKeyType);
/* 156 */       if (ct != this._keyType) {
/* 157 */         type = ((MapLikeType)type).withKeyType(ct);
/*     */       }
/*     */     } 
/*     */     
/* 161 */     JavaType srcCt = src.getContentType();
/* 162 */     if (srcCt != null) {
/* 163 */       JavaType ct = this._valueType.withHandlersFrom(srcCt);
/* 164 */       if (ct != this._valueType) {
/* 165 */         type = type.withContentType(ct);
/*     */       }
/*     */     } 
/* 168 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapLikeType withStaticTyping() {
/* 173 */     if (this._asStatic) {
/* 174 */       return this;
/*     */     }
/* 176 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 184 */     return new MapLikeType(rawType, bindings, superClass, superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String buildCanonicalName() {
/* 190 */     StringBuilder sb = new StringBuilder();
/* 191 */     sb.append(this._class.getName());
/* 192 */     if (this._keyType != null) {
/* 193 */       sb.append('<');
/* 194 */       sb.append(this._keyType.toCanonical());
/* 195 */       sb.append(',');
/* 196 */       sb.append(this._valueType.toCanonical());
/* 197 */       sb.append('>');
/*     */     } 
/* 199 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContainerType() {
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMapLikeType() {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getKeyType() {
/* 220 */     return this._keyType;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getContentType() {
/* 225 */     return this._valueType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContentValueHandler() {
/* 230 */     return this._valueType.getValueHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContentTypeHandler() {
/* 235 */     return this._valueType.getTypeHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHandlers() {
/* 240 */     return (super.hasHandlers() || this._valueType.hasHandlers() || this._keyType.hasHandlers());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder sb) {
/* 246 */     return _classSignature(this._class, sb, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder sb) {
/* 251 */     _classSignature(this._class, sb, false);
/* 252 */     sb.append('<');
/* 253 */     this._keyType.getGenericSignature(sb);
/* 254 */     this._valueType.getGenericSignature(sb);
/* 255 */     sb.append(">;");
/* 256 */     return sb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withKeyTypeHandler(Object h) {
/* 266 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withTypeHandler(h), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MapLikeType withKeyValueHandler(Object h) {
/* 272 */     return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withValueHandler(h), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTrueMapType() {
/* 283 */     return Map.class.isAssignableFrom(this._class);
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
/* 294 */     return String.format("[map-like type; class %s, %s -> %s]", new Object[] { this._class.getName(), this._keyType, this._valueType });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 300 */     if (o == this) return true; 
/* 301 */     if (o == null) return false; 
/* 302 */     if (o.getClass() != getClass()) return false;
/*     */     
/* 304 */     MapLikeType other = (MapLikeType)o;
/* 305 */     return (this._class == other._class && this._keyType.equals(other._keyType) && this._valueType.equals(other._valueType));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\MapLikeType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */