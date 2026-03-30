/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Collection;
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
/*     */ public class CollectionLikeType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _elementType;
/*     */   
/*     */   protected CollectionLikeType(Class<?> collT, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType elemT, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  34 */     super(collT, bindings, superClass, superInts, elemT.hashCode(), valueHandler, typeHandler, asStatic);
/*     */     
/*  36 */     this._elementType = elemT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CollectionLikeType(TypeBase base, JavaType elemT) {
/*  44 */     super(base);
/*  45 */     this._elementType = elemT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CollectionLikeType construct(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType elemT) {
/*  53 */     return new CollectionLikeType(rawType, bindings, superClass, superInts, elemT, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static CollectionLikeType construct(Class<?> rawType, JavaType elemT) {
/*     */     TypeBindings bindings;
/*  65 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])rawType.getTypeParameters();
/*     */     
/*  67 */     if (arrayOfTypeVariable == null || arrayOfTypeVariable.length != 1) {
/*  68 */       bindings = TypeBindings.emptyBindings();
/*     */     } else {
/*  70 */       bindings = TypeBindings.create(rawType, elemT);
/*     */     } 
/*  72 */     return new CollectionLikeType(rawType, bindings, _bogusSuperClass(rawType), null, elemT, null, null, false);
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
/*     */   public static CollectionLikeType upgradeFrom(JavaType baseType, JavaType elementType) {
/*  86 */     if (baseType instanceof TypeBase) {
/*  87 */       return new CollectionLikeType((TypeBase)baseType, elementType);
/*     */     }
/*  89 */     throw new IllegalArgumentException("Cannot upgrade from an instance of " + baseType.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/*  95 */     return new CollectionLikeType(subclass, this._bindings, this._superClass, this._superInterfaces, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/* 102 */     if (this._elementType == contentType) {
/* 103 */       return this;
/*     */     }
/* 105 */     return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, contentType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionLikeType withTypeHandler(Object h) {
/* 111 */     return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType, this._valueHandler, h, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionLikeType withContentTypeHandler(Object h) {
/* 118 */     return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withTypeHandler(h), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionLikeType withValueHandler(Object h) {
/* 125 */     return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType, h, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionLikeType withContentValueHandler(Object h) {
/* 131 */     return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withValueHandler(h), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withHandlersFrom(JavaType src) {
/* 138 */     JavaType type = super.withHandlersFrom(src);
/* 139 */     JavaType srcCt = src.getContentType();
/* 140 */     if (srcCt != null) {
/* 141 */       JavaType ct = this._elementType.withHandlersFrom(srcCt);
/* 142 */       if (ct != this._elementType) {
/* 143 */         type = type.withContentType(ct);
/*     */       }
/*     */     } 
/* 146 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   public CollectionLikeType withStaticTyping() {
/* 151 */     if (this._asStatic) {
/* 152 */       return this;
/*     */     }
/* 154 */     return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 162 */     return new CollectionLikeType(rawType, bindings, superClass, superInterfaces, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
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
/*     */   public boolean isContainerType() {
/* 174 */     return true;
/*     */   }
/*     */   public boolean isCollectionLikeType() {
/* 177 */     return true;
/*     */   }
/*     */   public JavaType getContentType() {
/* 180 */     return this._elementType;
/*     */   }
/*     */   
/*     */   public Object getContentValueHandler() {
/* 184 */     return this._elementType.getValueHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContentTypeHandler() {
/* 189 */     return this._elementType.getTypeHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHandlers() {
/* 194 */     return (super.hasHandlers() || this._elementType.hasHandlers());
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder sb) {
/* 199 */     return _classSignature(this._class, sb, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder sb) {
/* 204 */     _classSignature(this._class, sb, false);
/* 205 */     sb.append('<');
/* 206 */     this._elementType.getGenericSignature(sb);
/* 207 */     sb.append(">;");
/* 208 */     return sb;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String buildCanonicalName() {
/* 213 */     StringBuilder sb = new StringBuilder();
/* 214 */     sb.append(this._class.getName());
/* 215 */     if (this._elementType != null) {
/* 216 */       sb.append('<');
/* 217 */       sb.append(this._elementType.toCanonical());
/* 218 */       sb.append('>');
/*     */     } 
/* 220 */     return sb.toString();
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
/*     */   public boolean isTrueCollectionType() {
/* 236 */     return Collection.class.isAssignableFrom(this._class);
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
/*     */   public boolean equals(Object o) {
/* 248 */     if (o == this) return true; 
/* 249 */     if (o == null) return false; 
/* 250 */     if (o.getClass() != getClass()) return false;
/*     */     
/* 252 */     CollectionLikeType other = (CollectionLikeType)o;
/* 253 */     return (this._class == other._class && this._elementType.equals(other._elementType));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 259 */     return "[collection-like type; class " + this._class.getName() + ", contains " + this._elementType + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\CollectionLikeType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */