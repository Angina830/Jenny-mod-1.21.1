/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Type;
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
/*     */ public abstract class AnnotatedWithParams
/*     */   extends AnnotatedMember
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotationMap[] _paramAnnotations;
/*     */   
/*     */   protected AnnotatedWithParams(TypeResolutionContext ctxt, AnnotationMap annotations, AnnotationMap[] paramAnnotations) {
/*  31 */     super(ctxt, annotations);
/*  32 */     this._paramAnnotations = paramAnnotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedWithParams(AnnotatedWithParams base, AnnotationMap[] paramAnnotations) {
/*  39 */     super(base);
/*  40 */     this._paramAnnotations = paramAnnotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addOrOverrideParam(int paramIndex, Annotation a) {
/*  51 */     AnnotationMap old = this._paramAnnotations[paramIndex];
/*  52 */     if (old == null) {
/*  53 */       old = new AnnotationMap();
/*  54 */       this._paramAnnotations[paramIndex] = old;
/*     */     } 
/*  56 */     old.add(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedParameter replaceParameterAnnotations(int index, AnnotationMap ann) {
/*  65 */     this._paramAnnotations[index] = ann;
/*  66 */     return getParameter(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AnnotationMap getParameterAnnotations(int index) {
/*  77 */     if (this._paramAnnotations != null && 
/*  78 */       index >= 0 && index < this._paramAnnotations.length) {
/*  79 */       return this._paramAnnotations[index];
/*     */     }
/*     */     
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   public final AnnotatedParameter getParameter(int index) {
/*  86 */     return new AnnotatedParameter(this, getParameterType(index), this._typeContext, getParameterAnnotations(index), index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getParameterCount();
/*     */ 
/*     */   
/*     */   public abstract Class<?> getRawParameterType(int paramInt);
/*     */ 
/*     */   
/*     */   public abstract JavaType getParameterType(int paramInt);
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract Type getGenericParameterType(int paramInt);
/*     */ 
/*     */   
/*     */   public final int getAnnotationCount() {
/* 105 */     return this._annotations.size();
/*     */   }
/*     */   
/*     */   public abstract Object call() throws Exception;
/*     */   
/*     */   public abstract Object call(Object[] paramArrayOfObject) throws Exception;
/*     */   
/*     */   public abstract Object call1(Object paramObject) throws Exception;
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedWithParams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */