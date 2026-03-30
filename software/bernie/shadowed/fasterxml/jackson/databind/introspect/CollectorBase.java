/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CollectorBase
/*     */ {
/*  13 */   protected static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
/*  14 */   protected static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
/*     */   
/*     */   protected final AnnotationIntrospector _intr;
/*     */   
/*     */   protected CollectorBase(AnnotationIntrospector intr) {
/*  19 */     this._intr = intr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnnotationCollector collectAnnotations(Annotation[] anns) {
/*  25 */     AnnotationCollector c = AnnotationCollector.emptyCollector();
/*  26 */     for (int i = 0, end = anns.length; i < end; i++) {
/*  27 */       Annotation ann = anns[i];
/*  28 */       c = c.addOrOverride(ann);
/*  29 */       if (this._intr.isAnnotationBundle(ann)) {
/*  30 */         c = collectFromBundle(c, ann);
/*     */       }
/*     */     } 
/*  33 */     return c;
/*     */   }
/*     */   
/*     */   protected final AnnotationCollector collectAnnotations(AnnotationCollector c, Annotation[] anns) {
/*  37 */     for (int i = 0, end = anns.length; i < end; i++) {
/*  38 */       Annotation ann = anns[i];
/*  39 */       c = c.addOrOverride(ann);
/*  40 */       if (this._intr.isAnnotationBundle(ann)) {
/*  41 */         c = collectFromBundle(c, ann);
/*     */       }
/*     */     } 
/*  44 */     return c;
/*     */   }
/*     */   
/*     */   protected final AnnotationCollector collectFromBundle(AnnotationCollector c, Annotation bundle) {
/*  48 */     Annotation[] anns = ClassUtil.findClassAnnotations(bundle.annotationType());
/*  49 */     for (int i = 0, end = anns.length; i < end; i++) {
/*  50 */       Annotation ann = anns[i];
/*     */       
/*  52 */       if (!_ignorableAnnotation(ann))
/*     */       {
/*     */         
/*  55 */         if (this._intr.isAnnotationBundle(ann)) {
/*     */           
/*  57 */           if (!c.isPresent(ann)) {
/*  58 */             c = c.addOrOverride(ann);
/*  59 */             c = collectFromBundle(c, ann);
/*     */           } 
/*     */         } else {
/*  62 */           c = c.addOrOverride(ann);
/*     */         }  } 
/*     */     } 
/*  65 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnnotationCollector collectDefaultAnnotations(AnnotationCollector c, Annotation[] anns) {
/*  72 */     for (int i = 0, end = anns.length; i < end; i++) {
/*  73 */       Annotation ann = anns[i];
/*  74 */       if (!c.isPresent(ann)) {
/*  75 */         c = c.addOrOverride(ann);
/*  76 */         if (this._intr.isAnnotationBundle(ann)) {
/*  77 */           c = collectDefaultFromBundle(c, ann);
/*     */         }
/*     */       } 
/*     */     } 
/*  81 */     return c;
/*     */   }
/*     */   
/*     */   protected final AnnotationCollector collectDefaultFromBundle(AnnotationCollector c, Annotation bundle) {
/*  85 */     Annotation[] anns = ClassUtil.findClassAnnotations(bundle.annotationType());
/*  86 */     for (int i = 0, end = anns.length; i < end; i++) {
/*  87 */       Annotation ann = anns[i];
/*     */       
/*  89 */       if (!_ignorableAnnotation(ann))
/*     */       {
/*     */ 
/*     */         
/*  93 */         if (!c.isPresent(ann)) {
/*  94 */           c = c.addOrOverride(ann);
/*  95 */           if (this._intr.isAnnotationBundle(ann))
/*  96 */             c = collectFromBundle(c, ann); 
/*     */         } 
/*     */       }
/*     */     } 
/* 100 */     return c;
/*     */   }
/*     */   
/*     */   protected static final boolean _ignorableAnnotation(Annotation a) {
/* 104 */     return (a instanceof java.lang.annotation.Target || a instanceof java.lang.annotation.Retention);
/*     */   }
/*     */   
/*     */   static AnnotationMap _emptyAnnotationMap() {
/* 108 */     return new AnnotationMap();
/*     */   }
/*     */   
/*     */   static AnnotationMap[] _emptyAnnotationMaps(int count) {
/* 112 */     if (count == 0) {
/* 113 */       return NO_ANNOTATION_MAPS;
/*     */     }
/* 115 */     AnnotationMap[] maps = new AnnotationMap[count];
/* 116 */     for (int i = 0; i < count; i++) {
/* 117 */       maps[i] = _emptyAnnotationMap();
/*     */     }
/* 119 */     return maps;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\CollectorBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */