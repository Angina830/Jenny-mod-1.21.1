/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AnnotationMap
/*     */   implements Annotations
/*     */ {
/*     */   protected HashMap<Class<?>, Annotation> _annotations;
/*     */   
/*     */   public AnnotationMap() {}
/*     */   
/*     */   public static AnnotationMap of(Class<?> type, Annotation value) {
/*  21 */     HashMap<Class<?>, Annotation> ann = new HashMap<>(4);
/*  22 */     ann.put(type, value);
/*  23 */     return new AnnotationMap(ann);
/*     */   }
/*     */   
/*     */   AnnotationMap(HashMap<Class<?>, Annotation> a) {
/*  27 */     this._annotations = a;
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
/*     */   public <A extends Annotation> A get(Class<A> cls) {
/*  40 */     if (this._annotations == null) {
/*  41 */       return null;
/*     */     }
/*  43 */     return (A)this._annotations.get(cls);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean has(Class<?> cls) {
/*  49 */     if (this._annotations == null) {
/*  50 */       return false;
/*     */     }
/*  52 */     return this._annotations.containsKey(cls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOneOf(Class<? extends Annotation>[] annoClasses) {
/*  63 */     if (this._annotations != null) {
/*  64 */       for (int i = 0, end = annoClasses.length; i < end; i++) {
/*  65 */         if (this._annotations.containsKey(annoClasses[i])) {
/*  66 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*  70 */     return false;
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
/*     */   public Iterable<Annotation> annotations() {
/*  83 */     if (this._annotations == null || this._annotations.size() == 0) {
/*  84 */       return Collections.emptyList();
/*     */     }
/*  86 */     return this._annotations.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AnnotationMap merge(AnnotationMap primary, AnnotationMap secondary) {
/*  91 */     if (primary == null || primary._annotations == null || primary._annotations.isEmpty()) {
/*  92 */       return secondary;
/*     */     }
/*  94 */     if (secondary == null || secondary._annotations == null || secondary._annotations.isEmpty()) {
/*  95 */       return primary;
/*     */     }
/*  97 */     HashMap<Class<?>, Annotation> annotations = new HashMap<>();
/*     */     
/*  99 */     for (Annotation ann : secondary._annotations.values()) {
/* 100 */       annotations.put(ann.annotationType(), ann);
/*     */     }
/*     */     
/* 103 */     for (Annotation ann : primary._annotations.values()) {
/* 104 */       annotations.put(ann.annotationType(), ann);
/*     */     }
/* 106 */     return new AnnotationMap(annotations);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 111 */     return (this._annotations == null) ? 0 : this._annotations.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addIfNotPresent(Annotation ann) {
/* 120 */     if (this._annotations == null || !this._annotations.containsKey(ann.annotationType())) {
/* 121 */       _add(ann);
/* 122 */       return true;
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Annotation ann) {
/* 134 */     return _add(ann);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     if (this._annotations == null) {
/* 140 */       return "[null]";
/*     */     }
/* 142 */     return this._annotations.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _add(Annotation ann) {
/* 152 */     if (this._annotations == null) {
/* 153 */       this._annotations = new HashMap<>();
/*     */     }
/* 155 */     Annotation previous = this._annotations.put(ann.annotationType(), ann);
/* 156 */     return (previous == null || !previous.equals(ann));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotationMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */