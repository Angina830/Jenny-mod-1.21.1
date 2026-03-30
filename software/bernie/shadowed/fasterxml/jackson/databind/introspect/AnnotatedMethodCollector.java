/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotatedMethodCollector
/*     */   extends CollectorBase
/*     */ {
/*     */   private final ClassIntrospector.MixInResolver _mixInResolver;
/*     */   
/*     */   AnnotatedMethodCollector(AnnotationIntrospector intr, ClassIntrospector.MixInResolver mixins) {
/*  22 */     super(intr);
/*  23 */     this._mixInResolver = (intr == null) ? null : mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedMethodMap collectMethods(AnnotationIntrospector intr, TypeResolutionContext tc, ClassIntrospector.MixInResolver mixins, TypeFactory types, JavaType type, List<JavaType> superTypes, Class<?> primaryMixIn) {
/*  32 */     return (new AnnotatedMethodCollector(intr, mixins)).collect(types, tc, type, superTypes, primaryMixIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotatedMethodMap collect(TypeFactory typeFactory, TypeResolutionContext tc, JavaType mainType, List<JavaType> superTypes, Class<?> primaryMixIn) {
/*  39 */     Map<MemberKey, MethodBuilder> methods = new LinkedHashMap<>();
/*     */ 
/*     */     
/*  42 */     _addMemberMethods(tc, mainType.getRawClass(), methods, primaryMixIn);
/*     */ 
/*     */     
/*  45 */     for (JavaType type : superTypes) {
/*  46 */       Class<?> mixin = (this._mixInResolver == null) ? null : this._mixInResolver.findMixInClassFor(type.getRawClass());
/*  47 */       _addMemberMethods(new TypeResolutionContext.Basic(typeFactory, type.getBindings()), type.getRawClass(), methods, mixin);
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
/*  83 */     if (methods.isEmpty()) {
/*  84 */       return new AnnotatedMethodMap();
/*     */     }
/*  86 */     Map<MemberKey, AnnotatedMethod> actual = new LinkedHashMap<>(methods.size());
/*  87 */     for (Map.Entry<MemberKey, MethodBuilder> entry : methods.entrySet()) {
/*  88 */       AnnotatedMethod am = ((MethodBuilder)entry.getValue()).build();
/*  89 */       if (am != null) {
/*  90 */         actual.put(entry.getKey(), am);
/*     */       }
/*     */     } 
/*  93 */     return new AnnotatedMethodMap(actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void _addMemberMethods(TypeResolutionContext tc, Class<?> cls, Map<MemberKey, MethodBuilder> methods, Class<?> mixInCls) {
/* 100 */     if (mixInCls != null) {
/* 101 */       _addMethodMixIns(tc, cls, methods, mixInCls);
/*     */     }
/* 103 */     if (cls == null) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     for (Method m : ClassUtil.getClassMethods(cls)) {
/* 108 */       if (_isIncludableMemberMethod(m)) {
/*     */ 
/*     */         
/* 111 */         MemberKey key = new MemberKey(m);
/* 112 */         MethodBuilder b = methods.get(key);
/* 113 */         if (b == null) {
/* 114 */           AnnotationCollector c = (this._intr == null) ? AnnotationCollector.emptyCollector() : collectAnnotations(m.getDeclaredAnnotations());
/*     */           
/* 116 */           methods.put(key, new MethodBuilder(tc, m, c));
/*     */         } else {
/* 118 */           if (this._intr != null) {
/* 119 */             b.annotations = collectDefaultAnnotations(b.annotations, m.getDeclaredAnnotations());
/*     */           }
/* 121 */           Method old = b.method;
/* 122 */           if (old == null) {
/* 123 */             b.method = m;
/*     */           }
/* 125 */           else if (Modifier.isAbstract(old.getModifiers()) && !Modifier.isAbstract(m.getModifiers())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 133 */             b.method = m;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _addMethodMixIns(TypeResolutionContext tc, Class<?> targetClass, Map<MemberKey, MethodBuilder> methods, Class<?> mixInCls) {
/* 142 */     if (this._intr == null) {
/*     */       return;
/*     */     }
/* 145 */     for (Class<?> mixin : (Iterable<Class<?>>)ClassUtil.findRawSuperTypes(mixInCls, targetClass, true)) {
/* 146 */       for (Method m : ClassUtil.getDeclaredMethods(mixin)) {
/* 147 */         if (_isIncludableMemberMethod(m)) {
/*     */ 
/*     */           
/* 150 */           MemberKey key = new MemberKey(m);
/* 151 */           MethodBuilder b = methods.get(key);
/* 152 */           Annotation[] anns = m.getDeclaredAnnotations();
/* 153 */           if (b == null) {
/*     */ 
/*     */             
/* 156 */             methods.put(key, new MethodBuilder(tc, null, collectAnnotations(anns)));
/*     */           } else {
/* 158 */             b.annotations = collectDefaultAnnotations(b.annotations, anns);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean _isIncludableMemberMethod(Method m) {
/* 166 */     if (Modifier.isStatic(m.getModifiers()) || m.isSynthetic() || m.isBridge())
/*     */     {
/*     */ 
/*     */       
/* 170 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 174 */     int pcount = (m.getParameterTypes()).length;
/* 175 */     return (pcount <= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class MethodBuilder
/*     */   {
/*     */     public final TypeResolutionContext typeContext;
/*     */     
/*     */     public Method method;
/*     */     public AnnotationCollector annotations;
/*     */     
/*     */     public MethodBuilder(TypeResolutionContext tc, Method m, AnnotationCollector ann) {
/* 187 */       this.typeContext = tc;
/* 188 */       this.method = m;
/* 189 */       this.annotations = ann;
/*     */     }
/*     */     
/*     */     public AnnotatedMethod build() {
/* 193 */       if (this.method == null) {
/* 194 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 198 */       return new AnnotatedMethod(this.typeContext, this.method, this.annotations.asAnnotationMap(), null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedMethodCollector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */