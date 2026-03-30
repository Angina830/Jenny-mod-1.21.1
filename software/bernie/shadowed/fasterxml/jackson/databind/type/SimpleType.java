/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ public class SimpleType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   protected SimpleType(Class<?> cls) {
/*  33 */     this(cls, TypeBindings.emptyBindings(), (JavaType)null, (JavaType[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SimpleType(Class<?> cls, TypeBindings bindings, JavaType superClass, JavaType[] superInts) {
/*  38 */     this(cls, bindings, superClass, superInts, (Object)null, (Object)null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleType(TypeBase base) {
/*  48 */     super(base);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleType(Class<?> cls, TypeBindings bindings, JavaType superClass, JavaType[] superInts, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  55 */     super(cls, bindings, superClass, superInts, 0, valueHandler, typeHandler, asStatic);
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
/*     */   protected SimpleType(Class<?> cls, TypeBindings bindings, JavaType superClass, JavaType[] superInts, int extraHash, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  68 */     super(cls, bindings, superClass, superInts, extraHash, valueHandler, typeHandler, asStatic);
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
/*     */   public static SimpleType constructUnsafe(Class<?> raw) {
/*  82 */     return new SimpleType(raw, null, null, null, null, null, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static SimpleType construct(Class<?> cls) {
/* 106 */     if (Map.class.isAssignableFrom(cls)) {
/* 107 */       throw new IllegalArgumentException("Cannot construct SimpleType for a Map (class: " + cls.getName() + ")");
/*     */     }
/* 109 */     if (Collection.class.isAssignableFrom(cls)) {
/* 110 */       throw new IllegalArgumentException("Cannot construct SimpleType for a Collection (class: " + cls.getName() + ")");
/*     */     }
/*     */     
/* 113 */     if (cls.isArray()) {
/* 114 */       throw new IllegalArgumentException("Cannot construct SimpleType for an array (class: " + cls.getName() + ")");
/*     */     }
/* 116 */     TypeBindings b = TypeBindings.emptyBindings();
/* 117 */     return new SimpleType(cls, b, _buildSuperClass(cls.getSuperclass(), b), null, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/* 125 */     if (this._class == subclass) {
/* 126 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     if (!this._class.isAssignableFrom(subclass))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 137 */       return new SimpleType(subclass, this._bindings, this, this._superInterfaces, this._valueHandler, this._typeHandler, this._asStatic);
/*     */     }
/*     */ 
/*     */     
/* 141 */     Class<?> next = subclass.getSuperclass();
/* 142 */     if (next == this._class) {
/* 143 */       return new SimpleType(subclass, this._bindings, this, this._superInterfaces, this._valueHandler, this._typeHandler, this._asStatic);
/*     */     }
/*     */     
/* 146 */     if (next != null && this._class.isAssignableFrom(next)) {
/* 147 */       JavaType superb = _narrow(next);
/* 148 */       return new SimpleType(subclass, this._bindings, superb, null, this._valueHandler, this._typeHandler, this._asStatic);
/*     */     } 
/*     */ 
/*     */     
/* 152 */     Class<?>[] nextI = subclass.getInterfaces();
/* 153 */     for (Class<?> iface : nextI) {
/* 154 */       if (iface == this._class) {
/* 155 */         return new SimpleType(subclass, this._bindings, null, new JavaType[] { this }, this._valueHandler, this._typeHandler, this._asStatic);
/*     */       }
/*     */       
/* 158 */       if (this._class.isAssignableFrom(iface)) {
/* 159 */         JavaType superb = _narrow(iface);
/* 160 */         return new SimpleType(subclass, this._bindings, null, new JavaType[] { superb }, this._valueHandler, this._typeHandler, this._asStatic);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 165 */     throw new IllegalArgumentException("Internal error: Cannot resolve sub-type for Class " + subclass.getName() + " to " + this._class.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/* 171 */     throw new IllegalArgumentException("Simple types have no content types; cannot call withContentType()");
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleType withTypeHandler(Object h) {
/* 176 */     if (this._typeHandler == h) {
/* 177 */       return this;
/*     */     }
/* 179 */     return new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, this._valueHandler, h, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType withContentTypeHandler(Object h) {
/* 185 */     throw new IllegalArgumentException("Simple types have no content types; cannot call withContenTypeHandler()");
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleType withValueHandler(Object h) {
/* 190 */     if (h == this._valueHandler) {
/* 191 */       return this;
/*     */     }
/* 193 */     return new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, h, this._typeHandler, this._asStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleType withContentValueHandler(Object h) {
/* 199 */     throw new IllegalArgumentException("Simple types have no content types; cannot call withContenValueHandler()");
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleType withStaticTyping() {
/* 204 */     return this._asStatic ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String buildCanonicalName() {
/* 218 */     StringBuilder sb = new StringBuilder();
/* 219 */     sb.append(this._class.getName());
/*     */     
/* 221 */     int count = this._bindings.size();
/* 222 */     if (count > 0) {
/* 223 */       sb.append('<');
/* 224 */       for (int i = 0; i < count; i++) {
/* 225 */         JavaType t = containedType(i);
/* 226 */         if (i > 0) {
/* 227 */           sb.append(',');
/*     */         }
/* 229 */         sb.append(t.toCanonical());
/*     */       } 
/* 231 */       sb.append('>');
/*     */     } 
/* 233 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContainerType() {
/* 243 */     return false;
/*     */   }
/*     */   public boolean hasContentType() {
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder sb) {
/* 250 */     return _classSignature(this._class, sb, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder sb) {
/* 256 */     _classSignature(this._class, sb, false);
/*     */     
/* 258 */     int count = this._bindings.size();
/* 259 */     if (count > 0) {
/* 260 */       sb.append('<');
/* 261 */       for (int i = 0; i < count; i++) {
/* 262 */         sb = containedType(i).getGenericSignature(sb);
/*     */       }
/* 264 */       sb.append('>');
/*     */     } 
/* 266 */     sb.append(';');
/* 267 */     return sb;
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
/*     */   private static JavaType _buildSuperClass(Class<?> superClass, TypeBindings b) {
/* 284 */     if (superClass == null) {
/* 285 */       return null;
/*     */     }
/* 287 */     if (superClass == Object.class) {
/* 288 */       return TypeFactory.unknownType();
/*     */     }
/* 290 */     JavaType superSuper = _buildSuperClass(superClass.getSuperclass(), b);
/* 291 */     return new SimpleType(superClass, b, superSuper, null, null, null, false);
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
/*     */   public String toString() {
/* 304 */     StringBuilder sb = new StringBuilder(40);
/* 305 */     sb.append("[simple type, class ").append(buildCanonicalName()).append(']');
/* 306 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 312 */     if (o == this) return true; 
/* 313 */     if (o == null) return false; 
/* 314 */     if (o.getClass() != getClass()) return false;
/*     */     
/* 316 */     SimpleType other = (SimpleType)o;
/*     */ 
/*     */     
/* 319 */     if (other._class != this._class) return false;
/*     */ 
/*     */     
/* 322 */     TypeBindings b1 = this._bindings;
/* 323 */     TypeBindings b2 = other._bindings;
/* 324 */     return b1.equals(b2);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\SimpleType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */