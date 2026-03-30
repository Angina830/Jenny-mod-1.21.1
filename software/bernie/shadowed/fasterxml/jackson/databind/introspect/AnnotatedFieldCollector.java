/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ public class AnnotatedFieldCollector
/*     */   extends CollectorBase
/*     */ {
/*     */   private final TypeFactory _typeFactory;
/*     */   private final ClassIntrospector.MixInResolver _mixInResolver;
/*     */   
/*     */   AnnotatedFieldCollector(AnnotationIntrospector intr, TypeFactory types, ClassIntrospector.MixInResolver mixins) {
/*  26 */     super(intr);
/*  27 */     this._typeFactory = types;
/*  28 */     this._mixInResolver = (intr == null) ? null : mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<AnnotatedField> collectFields(AnnotationIntrospector intr, TypeResolutionContext tc, ClassIntrospector.MixInResolver mixins, TypeFactory types, JavaType type) {
/*  36 */     return (new AnnotatedFieldCollector(intr, types, mixins)).collect(tc, type);
/*     */   }
/*     */ 
/*     */   
/*     */   List<AnnotatedField> collect(TypeResolutionContext tc, JavaType type) {
/*  41 */     Map<String, FieldBuilder> foundFields = _findFields(tc, type, (Map<String, FieldBuilder>)null);
/*  42 */     if (foundFields == null) {
/*  43 */       return Collections.emptyList();
/*     */     }
/*  45 */     List<AnnotatedField> result = new ArrayList<>(foundFields.size());
/*  46 */     for (FieldBuilder b : foundFields.values()) {
/*  47 */       result.add(b.build());
/*     */     }
/*  49 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, FieldBuilder> _findFields(TypeResolutionContext tc, JavaType type, Map<String, FieldBuilder> fields) {
/*  58 */     JavaType parent = type.getSuperClass();
/*  59 */     if (parent == null) {
/*  60 */       return fields;
/*     */     }
/*  62 */     Class<?> cls = type.getRawClass();
/*     */     
/*  64 */     fields = _findFields(new TypeResolutionContext.Basic(this._typeFactory, parent.getBindings()), parent, fields);
/*     */     
/*  66 */     for (Field f : ClassUtil.getDeclaredFields(cls)) {
/*     */       
/*  68 */       if (_isIncludableField(f)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  74 */         if (fields == null) {
/*  75 */           fields = new LinkedHashMap<>();
/*     */         }
/*  77 */         FieldBuilder b = new FieldBuilder(tc, f);
/*  78 */         if (this._intr != null) {
/*  79 */           b.annotations = collectAnnotations(b.annotations, f.getDeclaredAnnotations());
/*     */         }
/*  81 */         fields.put(f.getName(), b);
/*     */       } 
/*     */     } 
/*  84 */     if (this._mixInResolver != null) {
/*  85 */       Class<?> mixin = this._mixInResolver.findMixInClassFor(cls);
/*  86 */       if (mixin != null) {
/*  87 */         _addFieldMixIns(mixin, cls, fields);
/*     */       }
/*     */     } 
/*  90 */     return fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void _addFieldMixIns(Class<?> mixInCls, Class<?> targetClass, Map<String, FieldBuilder> fields) {
/* 101 */     List<Class<?>> parents = ClassUtil.findSuperClasses(mixInCls, targetClass, true);
/* 102 */     for (Class<?> mixin : parents) {
/* 103 */       for (Field mixinField : ClassUtil.getDeclaredFields(mixin)) {
/*     */         
/* 105 */         if (_isIncludableField(mixinField)) {
/*     */ 
/*     */           
/* 108 */           String name = mixinField.getName();
/*     */           
/* 110 */           FieldBuilder b = fields.get(name);
/* 111 */           if (b != null) {
/* 112 */             b.annotations = collectAnnotations(b.annotations, mixinField.getDeclaredAnnotations());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean _isIncludableField(Field f) {
/* 121 */     if (f.isSynthetic()) {
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 126 */     int mods = f.getModifiers();
/* 127 */     if (Modifier.isStatic(mods)) {
/* 128 */       return false;
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   private static final class FieldBuilder
/*     */   {
/*     */     public final TypeResolutionContext typeContext;
/*     */     public final Field field;
/*     */     public AnnotationCollector annotations;
/*     */     
/*     */     public FieldBuilder(TypeResolutionContext tc, Field f) {
/* 140 */       this.typeContext = tc;
/* 141 */       this.field = f;
/* 142 */       this.annotations = AnnotationCollector.emptyCollector();
/*     */     }
/*     */     
/*     */     public AnnotatedField build() {
/* 146 */       return new AnnotatedField(this.typeContext, this.field, this.annotations.asAnnotationMap());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedFieldCollector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */