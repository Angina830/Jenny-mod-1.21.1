/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.reflect.AnnotatedElement;
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
/*     */ public final class AnnotatedParameter
/*     */   extends AnnotatedMember
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedWithParams _owner;
/*     */   protected final JavaType _type;
/*     */   protected final int _index;
/*     */   
/*     */   public AnnotatedParameter(AnnotatedWithParams owner, JavaType type, TypeResolutionContext typeContext, AnnotationMap annotations, int index) {
/*  45 */     super(typeContext, annotations);
/*  46 */     this._owner = owner;
/*  47 */     this._type = type;
/*  48 */     this._index = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedParameter withAnnotations(AnnotationMap ann) {
/*  53 */     if (ann == this._annotations) {
/*  54 */       return this;
/*     */     }
/*  56 */     return this._owner.replaceParameterAnnotations(this._index, ann);
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
/*     */   public AnnotatedElement getAnnotated() {
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModifiers() {
/*  77 */     return this._owner.getModifiers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  84 */     return "";
/*     */   }
/*     */   
/*     */   public Class<?> getRawType() {
/*  88 */     return this._type.getRawClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getType() {
/*  93 */     return this._type;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Type getGenericType() {
/*  99 */     return this._owner.getGenericParameterType(this._index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/* 110 */     return this._owner.getDeclaringClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Member getMember() {
/* 118 */     return this._owner.getMember();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object pojo, Object value) throws UnsupportedOperationException {
/* 124 */     throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + getDeclaringClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(Object pojo) throws UnsupportedOperationException {
/* 131 */     throw new UnsupportedOperationException("Cannot call getValue() on constructor parameter of " + getDeclaringClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getParameterType() {
/* 141 */     return (Type)this._type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getOwner() {
/* 149 */     return this._owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 156 */     return this._index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 166 */     return this._owner.hashCode() + this._index;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 171 */     if (o == this) return true; 
/* 172 */     if (!ClassUtil.hasClass(o, getClass())) {
/* 173 */       return false;
/*     */     }
/* 175 */     AnnotatedParameter other = (AnnotatedParameter)o;
/* 176 */     return (other._owner.equals(this._owner) && other._index == this._index);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 181 */     return "[parameter #" + getIndex() + ", annotations: " + this._annotations + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedParameter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */