/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Member;
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
/*     */ public abstract class AnnotatedMember
/*     */   extends Annotated
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final transient TypeResolutionContext _typeContext;
/*     */   protected final transient AnnotationMap _annotations;
/*     */   
/*     */   protected AnnotatedMember(TypeResolutionContext ctxt, AnnotationMap annotations) {
/*  36 */     this._typeContext = ctxt;
/*  37 */     this._annotations = annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMember(AnnotatedMember base) {
/*  46 */     this._typeContext = base._typeContext;
/*  47 */     this._annotations = base._annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Annotated withAnnotations(AnnotationMap paramAnnotationMap);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Class<?> getDeclaringClass();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Member getMember();
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFullName() {
/*  66 */     return getDeclaringClass().getName() + "#" + getName();
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
/*     */   @Deprecated
/*     */   public TypeResolutionContext getTypeContext() {
/*  79 */     return this._typeContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public final <A extends Annotation> A getAnnotation(Class<A> acls) {
/*  84 */     if (this._annotations == null) {
/*  85 */       return null;
/*     */     }
/*  87 */     return this._annotations.get(acls);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasAnnotation(Class<?> acls) {
/*  92 */     if (this._annotations == null) {
/*  93 */       return false;
/*     */     }
/*  95 */     return this._annotations.has(acls);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOneOf(Class<? extends Annotation>[] annoClasses) {
/* 100 */     if (this._annotations == null) {
/* 101 */       return false;
/*     */     }
/* 103 */     return this._annotations.hasOneOf(annoClasses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationMap getAllAnnotations() {
/* 111 */     return this._annotations;
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
/*     */   public final void fixAccess(boolean force) {
/* 127 */     Member m = getMember();
/* 128 */     if (m != null)
/* 129 */       ClassUtil.checkAndFixAccess(m, force); 
/*     */   }
/*     */   
/*     */   public abstract void setValue(Object paramObject1, Object paramObject2) throws UnsupportedOperationException, IllegalArgumentException;
/*     */   
/*     */   public abstract Object getValue(Object paramObject) throws UnsupportedOperationException, IllegalArgumentException;
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedMember.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */