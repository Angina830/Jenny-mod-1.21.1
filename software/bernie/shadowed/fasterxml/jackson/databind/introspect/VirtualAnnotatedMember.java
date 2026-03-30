/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
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
/*     */ public class VirtualAnnotatedMember
/*     */   extends AnnotatedMember
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<?> _declaringClass;
/*     */   protected final JavaType _type;
/*     */   protected final String _name;
/*     */   
/*     */   public VirtualAnnotatedMember(TypeResolutionContext typeContext, Class<?> declaringClass, String name, JavaType type) {
/*  37 */     super(typeContext, null);
/*  38 */     this._declaringClass = declaringClass;
/*  39 */     this._type = type;
/*  40 */     this._name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Annotated withAnnotations(AnnotationMap fallback) {
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field getAnnotated() {
/*  55 */     return null;
/*     */   }
/*     */   public int getModifiers() {
/*  58 */     return 0;
/*     */   }
/*     */   public String getName() {
/*  61 */     return this._name;
/*     */   }
/*     */   
/*     */   public Class<?> getRawType() {
/*  65 */     return this._type.getRawClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getType() {
/*  70 */     return this._type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/*  80 */     return this._declaringClass;
/*     */   }
/*     */   public Member getMember() {
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public void setValue(Object pojo, Object value) throws IllegalArgumentException {
/*  87 */     throw new IllegalArgumentException("Cannot set virtual property '" + this._name + "'");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue(Object pojo) throws IllegalArgumentException {
/*  92 */     throw new IllegalArgumentException("Cannot get virtual property '" + this._name + "'");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAnnotationCount() {
/* 101 */     return 0;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 105 */     return this._name.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 110 */     if (o == this) return true; 
/* 111 */     if (!ClassUtil.hasClass(o, getClass())) {
/* 112 */       return false;
/*     */     }
/* 114 */     VirtualAnnotatedMember other = (VirtualAnnotatedMember)o;
/* 115 */     return (other._declaringClass == this._declaringClass && other._name.equals(this._name));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return "[virtual " + getFullName() + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\VirtualAnnotatedMember.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */