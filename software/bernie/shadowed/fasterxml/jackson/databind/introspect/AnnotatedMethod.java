/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AnnotatedMethod
/*     */   extends AnnotatedWithParams
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final transient Method _method;
/*     */   protected Class<?>[] _paramClasses;
/*     */   protected Serialization _serialization;
/*     */   
/*     */   public AnnotatedMethod(TypeResolutionContext ctxt, Method method, AnnotationMap classAnn, AnnotationMap[] paramAnnotations) {
/*  37 */     super(ctxt, classAnn, paramAnnotations);
/*  38 */     if (method == null) {
/*  39 */       throw new IllegalArgumentException("Cannot construct AnnotatedMethod with null Method");
/*     */     }
/*  41 */     this._method = method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMethod(Serialization ser) {
/*  50 */     super((TypeResolutionContext)null, (AnnotationMap)null, (AnnotationMap[])null);
/*  51 */     this._method = null;
/*  52 */     this._serialization = ser;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMethod withAnnotations(AnnotationMap ann) {
/*  57 */     return new AnnotatedMethod(this._typeContext, this._method, ann, this._paramAnnotations);
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getAnnotated() {
/*  62 */     return this._method;
/*     */   }
/*     */   public int getModifiers() {
/*  65 */     return this._method.getModifiers();
/*     */   }
/*     */   public String getName() {
/*  68 */     return this._method.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getType() {
/*  77 */     return this._typeContext.resolveType(this._method.getGenericReturnType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getRawType() {
/*  87 */     return this._method.getReturnType();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Type getGenericType() {
/*  93 */     return this._method.getGenericReturnType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object call() throws Exception {
/* 104 */     return this._method.invoke((Object)null, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object call(Object[] args) throws Exception {
/* 109 */     return this._method.invoke((Object)null, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object call1(Object arg) throws Exception {
/* 114 */     return this._method.invoke((Object)null, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public final Object callOn(Object pojo) throws Exception {
/* 118 */     return this._method.invoke(pojo, (Object[])null);
/*     */   }
/*     */   
/*     */   public final Object callOnWith(Object pojo, Object... args) throws Exception {
/* 122 */     return this._method.invoke(pojo, args);
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
/* 133 */     return (getRawParameterTypes()).length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getRawParameterType(int index) {
/* 139 */     Class<?>[] types = getRawParameterTypes();
/* 140 */     return (index >= types.length) ? null : types[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getParameterType(int index) {
/* 145 */     Type[] types = this._method.getGenericParameterTypes();
/* 146 */     if (index >= types.length) {
/* 147 */       return null;
/*     */     }
/* 149 */     return this._typeContext.resolveType(types[index]);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Type getGenericParameterType(int index) {
/* 155 */     Type[] types = getGenericParameterTypes();
/* 156 */     if (index >= types.length) {
/* 157 */       return null;
/*     */     }
/* 159 */     return types[index];
/*     */   }
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/* 163 */     return this._method.getDeclaringClass();
/*     */   }
/*     */   public Method getMember() {
/* 166 */     return this._method;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(Object pojo, Object value) throws IllegalArgumentException {
/*     */     try {
/* 172 */       this._method.invoke(pojo, new Object[] { value });
/* 173 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 174 */       throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(Object pojo) throws IllegalArgumentException {
/*     */     try {
/* 183 */       return this._method.invoke(pojo, (Object[])null);
/* 184 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 185 */       throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + e.getMessage(), e);
/*     */     } 
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
/*     */   public String getFullName() {
/* 198 */     return String.format("%s(%d params)", new Object[] { super.getFullName(), Integer.valueOf(getParameterCount()) });
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?>[] getRawParameterTypes() {
/* 203 */     if (this._paramClasses == null) {
/* 204 */       this._paramClasses = this._method.getParameterTypes();
/*     */     }
/* 206 */     return this._paramClasses;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Type[] getGenericParameterTypes() {
/* 211 */     return this._method.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */   public Class<?> getRawReturnType() {
/* 215 */     return this._method.getReturnType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasReturnType() {
/* 226 */     Class<?> rt = getRawReturnType();
/* 227 */     return (rt != void.class && rt != Void.class);
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
/* 238 */     return "[method " + getFullName() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 243 */     return this._method.getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 248 */     if (o == this) return true; 
/* 249 */     return (ClassUtil.hasClass(o, getClass()) && ((AnnotatedMethod)o)._method == this._method);
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
/* 260 */     return new AnnotatedMethod(new Serialization(this._method));
/*     */   }
/*     */   
/*     */   Object readResolve() {
/* 264 */     Class<?> clazz = this._serialization.clazz;
/*     */     try {
/* 266 */       Method m = clazz.getDeclaredMethod(this._serialization.name, this._serialization.args);
/*     */ 
/*     */       
/* 269 */       if (!m.isAccessible()) {
/* 270 */         ClassUtil.checkAndFixAccess(m, false);
/*     */       }
/* 272 */       return new AnnotatedMethod(null, m, null, null);
/* 273 */     } catch (Exception e) {
/* 274 */       throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + clazz.getName());
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
/*     */     protected Class<?> clazz;
/*     */     
/*     */     protected String name;
/*     */     
/*     */     protected Class<?>[] args;
/*     */ 
/*     */     
/*     */     public Serialization(Method setter) {
/* 293 */       this.clazz = setter.getDeclaringClass();
/* 294 */       this.name = setter.getName();
/* 295 */       this.args = setter.getParameterTypes();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedMethod.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */