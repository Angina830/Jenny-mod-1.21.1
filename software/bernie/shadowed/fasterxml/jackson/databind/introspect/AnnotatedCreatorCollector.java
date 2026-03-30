/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
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
/*     */ final class AnnotatedCreatorCollector
/*     */   extends CollectorBase
/*     */ {
/*     */   private final TypeResolutionContext _typeContext;
/*     */   private AnnotatedConstructor _defaultConstructor;
/*     */   
/*     */   AnnotatedCreatorCollector(AnnotationIntrospector intr, TypeResolutionContext tc) {
/*  37 */     super(intr);
/*  38 */     this._typeContext = tc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedClass.Creators collectCreators(AnnotationIntrospector intr, TypeResolutionContext tc, JavaType type, Class<?> primaryMixIn) {
/*  46 */     return (new AnnotatedCreatorCollector(intr, tc)).collect(type, primaryMixIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotatedClass.Creators collect(JavaType type, Class<?> primaryMixIn) {
/*  56 */     List<AnnotatedConstructor> constructors = _findPotentialConstructors(type, primaryMixIn);
/*  57 */     List<AnnotatedMethod> factories = _findPotentialFactories(type, primaryMixIn);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (this._intr != null) {
/*  64 */       if (this._defaultConstructor != null && 
/*  65 */         this._intr.hasIgnoreMarker(this._defaultConstructor)) {
/*  66 */         this._defaultConstructor = null;
/*     */       }
/*     */       
/*     */       int i;
/*  70 */       for (i = constructors.size(); --i >= 0;) {
/*  71 */         if (this._intr.hasIgnoreMarker(constructors.get(i))) {
/*  72 */           constructors.remove(i);
/*     */         }
/*     */       } 
/*  75 */       for (i = factories.size(); --i >= 0;) {
/*  76 */         if (this._intr.hasIgnoreMarker(factories.get(i))) {
/*  77 */           factories.remove(i);
/*     */         }
/*     */       } 
/*     */     } 
/*  81 */     return new AnnotatedClass.Creators(this._defaultConstructor, constructors, factories);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<AnnotatedConstructor> _findPotentialConstructors(JavaType type, Class<?> primaryMixIn) {
/*     */     List<AnnotatedConstructor> result;
/*     */     int ctorCount;
/*  92 */     ClassUtil.Ctor defaultCtor = null;
/*  93 */     List<ClassUtil.Ctor> ctors = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (!type.isEnumType()) {
/* 101 */       ClassUtil.Ctor[] declaredCtors = ClassUtil.getConstructors(type.getRawClass());
/* 102 */       for (ClassUtil.Ctor ctor : declaredCtors) {
/* 103 */         if (isIncludableConstructor(ctor.getConstructor()))
/*     */         {
/*     */           
/* 106 */           if (ctor.getParamCount() == 0) {
/* 107 */             defaultCtor = ctor;
/*     */           } else {
/* 109 */             if (ctors == null) {
/* 110 */               ctors = new ArrayList<>();
/*     */             }
/* 112 */             ctors.add(ctor);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     if (ctors == null) {
/* 119 */       result = Collections.emptyList();
/*     */       
/* 121 */       if (defaultCtor == null) {
/* 122 */         return result;
/*     */       }
/* 124 */       ctorCount = 0;
/*     */     } else {
/* 126 */       ctorCount = ctors.size();
/* 127 */       result = new ArrayList<>(ctorCount);
/* 128 */       for (int j = 0; j < ctorCount; j++) {
/* 129 */         result.add(null);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 134 */     if (primaryMixIn != null) {
/* 135 */       MemberKey[] ctorKeys = null;
/* 136 */       for (ClassUtil.Ctor mixinCtor : ClassUtil.getConstructors(primaryMixIn)) {
/* 137 */         if (mixinCtor.getParamCount() == 0) {
/* 138 */           if (defaultCtor != null) {
/* 139 */             this._defaultConstructor = constructDefaultConstructor(defaultCtor, mixinCtor);
/* 140 */             defaultCtor = null;
/*     */           }
/*     */         
/*     */         }
/* 144 */         else if (ctors != null) {
/* 145 */           if (ctorKeys == null) {
/* 146 */             ctorKeys = new MemberKey[ctorCount];
/* 147 */             for (int k = 0; k < ctorCount; k++) {
/* 148 */               ctorKeys[k] = new MemberKey(((ClassUtil.Ctor)ctors.get(k)).getConstructor());
/*     */             }
/*     */           } 
/* 151 */           MemberKey key = new MemberKey(mixinCtor.getConstructor());
/*     */           
/* 153 */           for (int j = 0; j < ctorCount; j++) {
/* 154 */             if (key.equals(ctorKeys[j])) {
/* 155 */               result.set(j, constructNonDefaultConstructor(ctors.get(j), mixinCtor));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     if (defaultCtor != null) {
/* 165 */       this._defaultConstructor = constructDefaultConstructor(defaultCtor, (ClassUtil.Ctor)null);
/*     */     }
/* 167 */     for (int i = 0; i < ctorCount; i++) {
/* 168 */       AnnotatedConstructor ctor = result.get(i);
/* 169 */       if (ctor == null) {
/* 170 */         result.set(i, constructNonDefaultConstructor(ctors.get(i), (ClassUtil.Ctor)null));
/*     */       }
/*     */     } 
/*     */     
/* 174 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<AnnotatedMethod> _findPotentialFactories(JavaType type, Class<?> primaryMixIn) {
/* 179 */     List<Method> candidates = null;
/*     */ 
/*     */     
/* 182 */     for (Method m : ClassUtil.getClassMethods(type.getRawClass())) {
/* 183 */       if (Modifier.isStatic(m.getModifiers())) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         if (candidates == null) {
/* 189 */           candidates = new ArrayList<>();
/*     */         }
/* 191 */         candidates.add(m);
/*     */       } 
/*     */     } 
/* 194 */     if (candidates == null) {
/* 195 */       return Collections.emptyList();
/*     */     }
/* 197 */     int factoryCount = candidates.size();
/* 198 */     List<AnnotatedMethod> result = new ArrayList<>(factoryCount); int i;
/* 199 */     for (i = 0; i < factoryCount; i++) {
/* 200 */       result.add(null);
/*     */     }
/*     */     
/* 203 */     if (primaryMixIn != null) {
/* 204 */       MemberKey[] methodKeys = null;
/* 205 */       for (Method mixinFactory : ClassUtil.getDeclaredMethods(primaryMixIn)) {
/* 206 */         if (Modifier.isStatic(mixinFactory.getModifiers())) {
/*     */ 
/*     */           
/* 209 */           if (methodKeys == null) {
/* 210 */             methodKeys = new MemberKey[factoryCount];
/* 211 */             for (int k = 0; k < factoryCount; k++) {
/* 212 */               methodKeys[k] = new MemberKey(candidates.get(k));
/*     */             }
/*     */           } 
/* 215 */           MemberKey key = new MemberKey(mixinFactory);
/* 216 */           for (int j = 0; j < factoryCount; j++) {
/* 217 */             if (key.equals(methodKeys[j])) {
/* 218 */               result.set(j, constructFactoryCreator(candidates.get(j), mixinFactory));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 226 */     for (i = 0; i < factoryCount; i++) {
/* 227 */       AnnotatedMethod factory = result.get(i);
/* 228 */       if (factory == null) {
/* 229 */         result.set(i, constructFactoryCreator(candidates.get(i), (Method)null));
/*     */       }
/*     */     } 
/*     */     
/* 233 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedConstructor constructDefaultConstructor(ClassUtil.Ctor ctor, ClassUtil.Ctor mixin) {
/* 239 */     if (this._intr == null) {
/* 240 */       return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), _emptyAnnotationMap(), NO_ANNOTATION_MAPS);
/*     */     }
/*     */     
/* 243 */     return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), collectAnnotations(ctor, mixin), collectAnnotations(ctor.getConstructor().getParameterAnnotations(), (mixin == null) ? (Annotation[][])null : mixin.getConstructor().getParameterAnnotations()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedConstructor constructNonDefaultConstructor(ClassUtil.Ctor ctor, ClassUtil.Ctor mixin) {
/*     */     AnnotationMap[] resolvedAnnotations;
/* 252 */     int paramCount = ctor.getParamCount();
/* 253 */     if (this._intr == null) {
/* 254 */       return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), _emptyAnnotationMap(), _emptyAnnotationMaps(paramCount));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (paramCount == 0) {
/* 263 */       return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), collectAnnotations(ctor, mixin), NO_ANNOTATION_MAPS);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     Annotation[][] paramAnns = ctor.getParameterAnnotations();
/* 270 */     if (paramCount != paramAnns.length) {
/*     */ 
/*     */ 
/*     */       
/* 274 */       resolvedAnnotations = null;
/* 275 */       Class<?> dc = ctor.getDeclaringClass();
/*     */       
/* 277 */       if (dc.isEnum() && paramCount == paramAnns.length + 2) {
/* 278 */         Annotation[][] old = paramAnns;
/* 279 */         paramAnns = new Annotation[old.length + 2][];
/* 280 */         System.arraycopy(old, 0, paramAnns, 2, old.length);
/* 281 */         resolvedAnnotations = collectAnnotations(paramAnns, (Annotation[][])null);
/* 282 */       } else if (dc.isMemberClass()) {
/*     */         
/* 284 */         if (paramCount == paramAnns.length + 1) {
/*     */           
/* 286 */           Annotation[][] old = paramAnns;
/* 287 */           paramAnns = new Annotation[old.length + 1][];
/* 288 */           System.arraycopy(old, 0, paramAnns, 1, old.length);
/* 289 */           paramAnns[0] = NO_ANNOTATIONS;
/* 290 */           resolvedAnnotations = collectAnnotations(paramAnns, (Annotation[][])null);
/*     */         } 
/*     */       } 
/* 293 */       if (resolvedAnnotations == null) {
/* 294 */         throw new IllegalStateException(String.format("Internal error: constructor for %s has mismatch: %d parameters; %d sets of annotations", new Object[] { ctor.getDeclaringClass().getName(), Integer.valueOf(paramCount), Integer.valueOf(paramAnns.length) }));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 299 */       resolvedAnnotations = collectAnnotations(paramAnns, (mixin == null) ? (Annotation[][])null : mixin.getParameterAnnotations());
/*     */     } 
/*     */     
/* 302 */     return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), collectAnnotations(ctor, mixin), resolvedAnnotations);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMethod constructFactoryCreator(Method m, Method mixin) {
/* 308 */     int paramCount = (m.getParameterTypes()).length;
/* 309 */     if (this._intr == null) {
/* 310 */       return new AnnotatedMethod(this._typeContext, m, _emptyAnnotationMap(), _emptyAnnotationMaps(paramCount));
/*     */     }
/*     */     
/* 313 */     if (paramCount == 0) {
/* 314 */       return new AnnotatedMethod(this._typeContext, m, collectAnnotations(m, mixin), NO_ANNOTATION_MAPS);
/*     */     }
/*     */     
/* 317 */     return new AnnotatedMethod(this._typeContext, m, collectAnnotations(m, mixin), collectAnnotations(m.getParameterAnnotations(), (mixin == null) ? (Annotation[][])null : mixin.getParameterAnnotations()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationMap[] collectAnnotations(Annotation[][] mainAnns, Annotation[][] mixinAnns) {
/* 323 */     int count = mainAnns.length;
/* 324 */     AnnotationMap[] result = new AnnotationMap[count];
/* 325 */     for (int i = 0; i < count; i++) {
/* 326 */       AnnotationCollector c = collectAnnotations(AnnotationCollector.emptyCollector(), mainAnns[i]);
/*     */       
/* 328 */       if (mixinAnns != null) {
/* 329 */         c = collectAnnotations(c, mixinAnns[i]);
/*     */       }
/* 331 */       result[i] = c.asAnnotationMap();
/*     */     } 
/* 333 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationMap collectAnnotations(ClassUtil.Ctor main, ClassUtil.Ctor mixin) {
/* 339 */     AnnotationCollector c = collectAnnotations(main.getConstructor().getDeclaredAnnotations());
/* 340 */     if (mixin != null) {
/* 341 */       c = collectAnnotations(c, mixin.getConstructor().getDeclaredAnnotations());
/*     */     }
/* 343 */     return c.asAnnotationMap();
/*     */   }
/*     */   
/*     */   private final AnnotationMap collectAnnotations(AnnotatedElement main, AnnotatedElement mixin) {
/* 347 */     AnnotationCollector c = collectAnnotations(main.getDeclaredAnnotations());
/* 348 */     if (mixin != null) {
/* 349 */       c = collectAnnotations(c, mixin.getDeclaredAnnotations());
/*     */     }
/* 351 */     return c.asAnnotationMap();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isIncludableConstructor(Constructor<?> c) {
/* 356 */     return !c.isSynthetic();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedCreatorCollector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */