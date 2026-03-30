/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
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
/*     */ public class ReferenceType
/*     */   extends SimpleType
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _referencedType;
/*     */   protected final JavaType _anchorType;
/*     */   
/*     */   protected ReferenceType(Class<?> cls, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType refType, JavaType anchorType, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  34 */     super(cls, bindings, superClass, superInts, refType.hashCode(), valueHandler, typeHandler, asStatic);
/*     */     
/*  36 */     this._referencedType = refType;
/*  37 */     this._anchorType = (anchorType == null) ? this : anchorType;
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
/*     */   protected ReferenceType(TypeBase base, JavaType refType) {
/*  49 */     super(base);
/*  50 */     this._referencedType = refType;
/*     */     
/*  52 */     this._anchorType = this;
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
/*     */   public static ReferenceType upgradeFrom(JavaType baseType, JavaType refdType) {
/*  65 */     if (refdType == null) {
/*  66 */       throw new IllegalArgumentException("Missing referencedType");
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (baseType instanceof TypeBase) {
/*  71 */       return new ReferenceType((TypeBase)baseType, refdType);
/*     */     }
/*  73 */     throw new IllegalArgumentException("Cannot upgrade from an instance of " + baseType.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceType construct(Class<?> cls, TypeBindings bindings, JavaType superClass, JavaType[] superInts, JavaType refType) {
/*  82 */     return new ReferenceType(cls, bindings, superClass, superInts, refType, null, null, null, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static ReferenceType construct(Class<?> cls, JavaType refType) {
/*  88 */     return new ReferenceType(cls, TypeBindings.emptyBindings(), null, null, null, refType, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/*  95 */     if (this._referencedType == contentType) {
/*  96 */       return this;
/*     */     }
/*  98 */     return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, contentType, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceType withTypeHandler(Object h) {
/* 105 */     if (h == this._typeHandler) {
/* 106 */       return this;
/*     */     }
/* 108 */     return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType, this._anchorType, this._valueHandler, h, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceType withContentTypeHandler(Object h) {
/* 115 */     if (h == this._referencedType.getTypeHandler()) {
/* 116 */       return this;
/*     */     }
/* 118 */     return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType.withTypeHandler(h), this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceType withValueHandler(Object h) {
/* 125 */     if (h == this._valueHandler) {
/* 126 */       return this;
/*     */     }
/* 128 */     return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType, this._anchorType, h, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceType withContentValueHandler(Object h) {
/* 135 */     if (h == this._referencedType.getValueHandler()) {
/* 136 */       return this;
/*     */     }
/* 138 */     JavaType refdType = this._referencedType.withValueHandler(h);
/* 139 */     return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, refdType, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceType withStaticTyping() {
/* 146 */     if (this._asStatic) {
/* 147 */       return this;
/*     */     }
/* 149 */     return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType.withStaticTyping(), this._anchorType, this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 157 */     return new ReferenceType(rawType, this._bindings, superClass, superInterfaces, this._referencedType, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String buildCanonicalName() {
/* 165 */     StringBuilder sb = new StringBuilder();
/* 166 */     sb.append(this._class.getName());
/* 167 */     sb.append('<');
/* 168 */     sb.append(this._referencedType.toCanonical());
/* 169 */     return sb.toString();
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
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/* 183 */     return new ReferenceType(subclass, this._bindings, this._superClass, this._superInterfaces, this._referencedType, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
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
/*     */   public JavaType getContentType() {
/* 196 */     return this._referencedType;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getReferencedType() {
/* 201 */     return this._referencedType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasContentType() {
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReferenceType() {
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder sb) {
/* 216 */     return _classSignature(this._class, sb, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder sb) {
/* 222 */     _classSignature(this._class, sb, false);
/* 223 */     sb.append('<');
/* 224 */     sb = this._referencedType.getGenericSignature(sb);
/* 225 */     sb.append(">;");
/* 226 */     return sb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getAnchorType() {
/* 236 */     return this._anchorType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnchorType() {
/* 244 */     return (this._anchorType == this);
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
/* 256 */     return (new StringBuilder(40)).append("[reference type, class ").append(buildCanonicalName()).append('<').append(this._referencedType).append('>').append(']').toString();
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
/*     */   public boolean equals(Object o) {
/* 269 */     if (o == this) return true; 
/* 270 */     if (o == null) return false; 
/* 271 */     if (o.getClass() != getClass()) return false;
/*     */     
/* 273 */     ReferenceType other = (ReferenceType)o;
/*     */     
/* 275 */     if (other._class != this._class) return false;
/*     */ 
/*     */     
/* 278 */     return this._referencedType.equals(other._referencedType);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\ReferenceType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */