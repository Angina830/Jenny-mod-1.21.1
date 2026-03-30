/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
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
/*     */ public class AnnotatedClassResolver
/*     */ {
/*  25 */   private static final Annotations NO_ANNOTATIONS = AnnotationCollector.emptyAnnotations();
/*     */   
/*     */   private final MapperConfig<?> _config;
/*     */   
/*     */   private final AnnotationIntrospector _intr;
/*     */   private final ClassIntrospector.MixInResolver _mixInResolver;
/*     */   private final TypeBindings _bindings;
/*     */   private final JavaType _type;
/*     */   private final Class<?> _class;
/*     */   private final Class<?> _primaryMixin;
/*     */   
/*     */   AnnotatedClassResolver(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r) {
/*  37 */     this._config = config;
/*  38 */     this._type = type;
/*  39 */     this._class = type.getRawClass();
/*  40 */     this._mixInResolver = r;
/*  41 */     this._bindings = type.getBindings();
/*  42 */     this._intr = config.isAnnotationProcessingEnabled() ? config.getAnnotationIntrospector() : null;
/*     */     
/*  44 */     this._primaryMixin = this._config.findMixInClassFor(this._class);
/*     */   }
/*     */   
/*     */   AnnotatedClassResolver(MapperConfig<?> config, Class<?> cls, ClassIntrospector.MixInResolver r) {
/*  48 */     this._config = config;
/*  49 */     this._type = null;
/*  50 */     this._class = cls;
/*  51 */     this._mixInResolver = r;
/*  52 */     this._bindings = TypeBindings.emptyBindings();
/*  53 */     if (config == null) {
/*  54 */       this._intr = null;
/*  55 */       this._primaryMixin = null;
/*     */     } else {
/*  57 */       this._intr = config.isAnnotationProcessingEnabled() ? config.getAnnotationIntrospector() : null;
/*     */       
/*  59 */       this._primaryMixin = this._config.findMixInClassFor(this._class);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedClass resolve(MapperConfig<?> config, JavaType forType, ClassIntrospector.MixInResolver r) {
/*  66 */     if (forType.isArrayType() && skippableArray(config, forType.getRawClass())) {
/*  67 */       return createArrayType(config, forType.getRawClass());
/*     */     }
/*  69 */     return (new AnnotatedClassResolver(config, forType, r)).resolveFully();
/*     */   }
/*     */   
/*     */   public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig<?> config, Class<?> forType) {
/*  73 */     return resolveWithoutSuperTypes(config, forType, (ClassIntrospector.MixInResolver)config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig<?> config, JavaType forType, ClassIntrospector.MixInResolver r) {
/*  79 */     if (forType.isArrayType() && skippableArray(config, forType.getRawClass())) {
/*  80 */       return createArrayType(config, forType.getRawClass());
/*     */     }
/*  82 */     return (new AnnotatedClassResolver(config, forType, r)).resolveWithoutSuperTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig<?> config, Class<?> forType, ClassIntrospector.MixInResolver r) {
/*  88 */     if (forType.isArray() && skippableArray(config, forType)) {
/*  89 */       return createArrayType(config, forType);
/*     */     }
/*  91 */     return (new AnnotatedClassResolver(config, forType, r)).resolveWithoutSuperTypes();
/*     */   }
/*     */   
/*     */   private static boolean skippableArray(MapperConfig<?> config, Class<?> type) {
/*  95 */     return (config == null || config.findMixInClassFor(type) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static AnnotatedClass createPrimordial(Class<?> raw) {
/* 104 */     return new AnnotatedClass(raw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static AnnotatedClass createArrayType(MapperConfig<?> config, Class<?> raw) {
/* 112 */     return new AnnotatedClass(raw);
/*     */   }
/*     */   
/*     */   AnnotatedClass resolveFully() {
/* 116 */     List<JavaType> superTypes = ClassUtil.findSuperTypes(this._type, null, false);
/* 117 */     return new AnnotatedClass(this._type, this._class, superTypes, this._primaryMixin, resolveClassAnnotations(superTypes), this._bindings, this._intr, this._mixInResolver, this._config.getTypeFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotatedClass resolveWithoutSuperTypes() {
/* 124 */     List<JavaType> superTypes = Collections.emptyList();
/* 125 */     return new AnnotatedClass(null, this._class, superTypes, this._primaryMixin, resolveClassAnnotations(superTypes), this._bindings, this._intr, (ClassIntrospector.MixInResolver)this._config, this._config.getTypeFactory());
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
/*     */   private Annotations resolveClassAnnotations(List<JavaType> superTypes) {
/* 144 */     if (this._intr == null) {
/* 145 */       return NO_ANNOTATIONS;
/*     */     }
/* 147 */     AnnotationCollector resolvedCA = AnnotationCollector.emptyCollector();
/*     */     
/* 149 */     if (this._primaryMixin != null) {
/* 150 */       resolvedCA = _addClassMixIns(resolvedCA, this._class, this._primaryMixin);
/*     */     }
/*     */     
/* 153 */     resolvedCA = _addAnnotationsIfNotPresent(resolvedCA, ClassUtil.findClassAnnotations(this._class));
/*     */ 
/*     */ 
/*     */     
/* 157 */     for (JavaType type : superTypes) {
/*     */       
/* 159 */       if (this._mixInResolver != null) {
/* 160 */         Class<?> cls = type.getRawClass();
/* 161 */         resolvedCA = _addClassMixIns(resolvedCA, cls, this._mixInResolver.findMixInClassFor(cls));
/*     */       } 
/*     */       
/* 164 */       resolvedCA = _addAnnotationsIfNotPresent(resolvedCA, ClassUtil.findClassAnnotations(type.getRawClass()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (this._mixInResolver != null) {
/* 174 */       resolvedCA = _addClassMixIns(resolvedCA, Object.class, this._mixInResolver.findMixInClassFor(Object.class));
/*     */     }
/*     */     
/* 177 */     return resolvedCA.asAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationCollector _addClassMixIns(AnnotationCollector annotations, Class<?> target, Class<?> mixin) {
/* 183 */     if (mixin != null) {
/*     */       
/* 185 */       annotations = _addAnnotationsIfNotPresent(annotations, ClassUtil.findClassAnnotations(mixin));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       for (Class<?> parent : (Iterable<Class<?>>)ClassUtil.findSuperClasses(mixin, target, false)) {
/* 193 */         annotations = _addAnnotationsIfNotPresent(annotations, ClassUtil.findClassAnnotations(parent));
/*     */       }
/*     */     } 
/* 196 */     return annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationCollector _addAnnotationsIfNotPresent(AnnotationCollector c, Annotation[] anns) {
/* 202 */     if (anns != null) {
/* 203 */       for (Annotation ann : anns) {
/*     */         
/* 205 */         if (!c.isPresent(ann)) {
/* 206 */           c = c.addOrOverride(ann);
/* 207 */           if (this._intr.isAnnotationBundle(ann)) {
/* 208 */             c = _addFromBundleIfNotPresent(c, ann);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 213 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationCollector _addFromBundleIfNotPresent(AnnotationCollector c, Annotation bundle) {
/* 219 */     for (Annotation ann : ClassUtil.findClassAnnotations(bundle.annotationType())) {
/*     */       
/* 221 */       if (!(ann instanceof java.lang.annotation.Target) && !(ann instanceof java.lang.annotation.Retention))
/*     */       {
/*     */         
/* 224 */         if (!c.isPresent(ann)) {
/* 225 */           c = c.addOrOverride(ann);
/* 226 */           if (this._intr.isAnnotationBundle(ann))
/* 227 */             c = _addFromBundleIfNotPresent(c, ann); 
/*     */         } 
/*     */       }
/*     */     } 
/* 231 */     return c;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedClassResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */