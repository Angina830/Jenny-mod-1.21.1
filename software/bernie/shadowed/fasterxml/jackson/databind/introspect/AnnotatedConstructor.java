/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Type;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AnnotatedConstructor
/*     */   extends AnnotatedWithParams
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Constructor<?> _constructor;
/*     */   protected Serialization _serialization;
/*     */   
/*     */   public AnnotatedConstructor(TypeResolutionContext ctxt, Constructor<?> constructor, AnnotationMap classAnn, AnnotationMap[] paramAnn) {
/*  32 */     super(ctxt, classAnn, paramAnn);
/*  33 */     if (constructor == null) {
/*  34 */       throw new IllegalArgumentException("Null constructor not allowed");
/*     */     }
/*  36 */     this._constructor = constructor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedConstructor(Serialization ser) {
/*  45 */     super((TypeResolutionContext)null, (AnnotationMap)null, (AnnotationMap[])null);
/*  46 */     this._constructor = null;
/*  47 */     this._serialization = ser;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedConstructor withAnnotations(AnnotationMap ann) {
/*  52 */     return new AnnotatedConstructor(this._typeContext, this._constructor, ann, this._paramAnnotations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Constructor<?> getAnnotated() {
/*  62 */     return this._constructor;
/*     */   }
/*     */   public int getModifiers() {
/*  65 */     return this._constructor.getModifiers();
/*     */   }
/*     */   public String getName() {
/*  68 */     return this._constructor.getName();
/*     */   }
/*     */   
/*     */   public JavaType getType() {
/*  72 */     return this._typeContext.resolveType(getRawType());
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> getRawType() {
/*  77 */     return this._constructor.getDeclaringClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterCount() {
/*  88 */     return (this._constructor.getParameterTypes()).length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getRawParameterType(int index) {
/*  94 */     Class<?>[] types = this._constructor.getParameterTypes();
/*  95 */     return (index >= types.length) ? null : types[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getParameterType(int index) {
/* 100 */     Type[] types = this._constructor.getGenericParameterTypes();
/* 101 */     if (index >= types.length) {
/* 102 */       return null;
/*     */     }
/* 104 */     return this._typeContext.resolveType(types[index]);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Type getGenericParameterType(int index) {
/* 110 */     Type[] types = this._constructor.getGenericParameterTypes();
/* 111 */     if (index >= types.length) {
/* 112 */       return null;
/*     */     }
/* 114 */     return types[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object call() throws Exception {
/* 119 */     return this._constructor.newInstance(new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object call(Object[] args) throws Exception {
/* 124 */     return this._constructor.newInstance(args);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object call1(Object arg) throws Exception {
/* 129 */     return this._constructor.newInstance(new Object[] { arg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/* 139 */     return this._constructor.getDeclaringClass();
/*     */   }
/*     */   public Member getMember() {
/* 142 */     return this._constructor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object pojo, Object value) throws UnsupportedOperationException {
/* 148 */     throw new UnsupportedOperationException("Cannot call setValue() on constructor of " + getDeclaringClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(Object pojo) throws UnsupportedOperationException {
/* 156 */     throw new UnsupportedOperationException("Cannot call getValue() on constructor of " + getDeclaringClass().getName());
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
/* 168 */     return "[constructor for " + getName() + ", annotations: " + this._annotations + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 173 */     return this._constructor.getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 178 */     if (o == this) return true; 
/* 179 */     return (ClassUtil.hasClass(o, getClass()) && ((AnnotatedConstructor)o)._constructor == this._constructor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 190 */     return new AnnotatedConstructor(new Serialization(this._constructor));
/*     */   }
/*     */   
/*     */   Object readResolve() {
/* 194 */     Class<?> clazz = this._serialization.clazz;
/*     */     try {
/* 196 */       Constructor<?> ctor = clazz.getDeclaredConstructor(this._serialization.args);
/*     */       
/* 198 */       if (!ctor.isAccessible()) {
/* 199 */         ClassUtil.checkAndFixAccess(ctor, false);
/*     */       }
/* 201 */       return new AnnotatedConstructor(null, ctor, null, null);
/* 202 */     } catch (Exception e) {
/* 203 */       throw new IllegalArgumentException("Could not find constructor with " + this._serialization.args.length + " args from Class '" + clazz.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Serialization
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     protected Class<?> clazz;
/*     */     
/*     */     protected Class<?>[] args;
/*     */ 
/*     */     
/*     */     public Serialization(Constructor<?> ctor) {
/* 221 */       this.clazz = ctor.getDeclaringClass();
/* 222 */       this.args = ctor.getParameterTypes();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedConstructor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */