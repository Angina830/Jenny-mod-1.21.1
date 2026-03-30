/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolvedRecursiveType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected JavaType _referencedType;
/*     */   
/*     */   public ResolvedRecursiveType(Class<?> erasedType, TypeBindings bindings) {
/*  17 */     super(erasedType, bindings, null, null, 0, null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference(JavaType ref) {
/*  23 */     if (this._referencedType != null) {
/*  24 */       throw new IllegalStateException("Trying to re-set self reference; old value = " + this._referencedType + ", new = " + ref);
/*     */     }
/*  26 */     this._referencedType = ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getSuperClass() {
/*  31 */     if (this._referencedType != null) {
/*  32 */       return this._referencedType.getSuperClass();
/*     */     }
/*  34 */     return super.getSuperClass();
/*     */   }
/*     */   public JavaType getSelfReferencedType() {
/*  37 */     return this._referencedType;
/*     */   }
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder sb) {
/*  41 */     return this._referencedType.getGenericSignature(sb);
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder sb) {
/*  46 */     return this._referencedType.getErasedSignature(sb);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withContentType(JavaType contentType) {
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withTypeHandler(Object h) {
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withContentTypeHandler(Object h) {
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withValueHandler(Object h) {
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withContentValueHandler(Object h) {
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType withStaticTyping() {
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JavaType _narrow(Class<?> subclass) {
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isContainerType() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  98 */     StringBuilder sb = (new StringBuilder(40)).append("[recursive type; ");
/*     */     
/* 100 */     if (this._referencedType == null) {
/* 101 */       sb.append("UNRESOLVED");
/*     */     }
/*     */     else {
/*     */       
/* 105 */       sb.append(this._referencedType.getRawClass().getName());
/*     */     } 
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 112 */     if (o == this) return true; 
/* 113 */     if (o == null) return false; 
/* 114 */     if (o.getClass() == getClass())
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 119 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\ResolvedRecursiveType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */