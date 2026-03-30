/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.SimpleType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.LRUMap;
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
/*     */ public class BasicClassIntrospector
/*     */   extends ClassIntrospector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   protected static final BasicBeanDescription STRING_DESC = BasicBeanDescription.forOtherUse(null, (JavaType)SimpleType.constructUnsafe(String.class), AnnotatedClassResolver.createPrimordial(String.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   protected static final BasicBeanDescription BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, (JavaType)SimpleType.constructUnsafe(boolean.class), AnnotatedClassResolver.createPrimordial(boolean.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected static final BasicBeanDescription INT_DESC = BasicBeanDescription.forOtherUse(null, (JavaType)SimpleType.constructUnsafe(int.class), AnnotatedClassResolver.createPrimordial(int.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   protected static final BasicBeanDescription LONG_DESC = BasicBeanDescription.forOtherUse(null, (JavaType)SimpleType.constructUnsafe(long.class), AnnotatedClassResolver.createPrimordial(long.class));
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
/*  67 */   protected final LRUMap<JavaType, BasicBeanDescription> _cachedFCA = new LRUMap(16, 64);
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
/*     */   public BasicBeanDescription forSerialization(SerializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
/*  81 */     BasicBeanDescription desc = _findStdTypeDesc(type);
/*  82 */     if (desc == null) {
/*     */ 
/*     */       
/*  85 */       desc = _findStdJdkCollectionDesc((MapperConfig<?>)cfg, type);
/*  86 */       if (desc == null) {
/*  87 */         desc = BasicBeanDescription.forSerialization(collectProperties((MapperConfig<?>)cfg, type, r, true, "set"));
/*     */       }
/*     */ 
/*     */       
/*  91 */       this._cachedFCA.putIfAbsent(type, desc);
/*     */     } 
/*  93 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBeanDescription forDeserialization(DeserializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
/* 101 */     BasicBeanDescription desc = _findStdTypeDesc(type);
/* 102 */     if (desc == null) {
/*     */ 
/*     */       
/* 105 */       desc = _findStdJdkCollectionDesc((MapperConfig<?>)cfg, type);
/* 106 */       if (desc == null) {
/* 107 */         desc = BasicBeanDescription.forDeserialization(collectProperties((MapperConfig<?>)cfg, type, r, false, "set"));
/*     */       }
/*     */ 
/*     */       
/* 111 */       this._cachedFCA.putIfAbsent(type, desc);
/*     */     } 
/* 113 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
/* 122 */     BasicBeanDescription desc = BasicBeanDescription.forDeserialization(collectPropertiesWithBuilder((MapperConfig<?>)cfg, type, r, false));
/*     */ 
/*     */     
/* 125 */     this._cachedFCA.putIfAbsent(type, desc);
/* 126 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBeanDescription forCreation(DeserializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
/* 133 */     BasicBeanDescription desc = _findStdTypeDesc(type);
/* 134 */     if (desc == null) {
/*     */ 
/*     */ 
/*     */       
/* 138 */       desc = _findStdJdkCollectionDesc((MapperConfig<?>)cfg, type);
/* 139 */       if (desc == null) {
/* 140 */         desc = BasicBeanDescription.forDeserialization(collectProperties((MapperConfig<?>)cfg, type, r, false, "set"));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 145 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBeanDescription forClassAnnotations(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r) {
/* 152 */     BasicBeanDescription desc = _findStdTypeDesc(type);
/* 153 */     if (desc == null) {
/* 154 */       desc = (BasicBeanDescription)this._cachedFCA.get(type);
/* 155 */       if (desc == null) {
/* 156 */         desc = BasicBeanDescription.forOtherUse(config, type, _resolveAnnotatedClass(config, type, r));
/*     */         
/* 158 */         this._cachedFCA.put(type, desc);
/*     */       } 
/*     */     } 
/* 161 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r) {
/* 168 */     BasicBeanDescription desc = _findStdTypeDesc(type);
/* 169 */     if (desc == null) {
/* 170 */       desc = BasicBeanDescription.forOtherUse(config, type, _resolveAnnotatedWithoutSuperTypes(config, type, r));
/*     */     }
/*     */     
/* 173 */     return desc;
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
/*     */   protected POJOPropertiesCollector collectProperties(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r, boolean forSerialization, String mutatorPrefix) {
/* 186 */     return constructPropertyCollector(config, _resolveAnnotatedClass(config, type, r), type, forSerialization, mutatorPrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r, boolean forSerialization) {
/* 194 */     AnnotatedClass ac = _resolveAnnotatedClass(config, type, r);
/* 195 */     AnnotationIntrospector ai = config.isAnnotationProcessingEnabled() ? config.getAnnotationIntrospector() : null;
/* 196 */     JsonPOJOBuilder.Value builderConfig = (ai == null) ? null : ai.findPOJOBuilderConfig(ac);
/* 197 */     String mutatorPrefix = (builderConfig == null) ? "with" : builderConfig.withPrefix;
/* 198 */     return constructPropertyCollector(config, ac, type, forSerialization, mutatorPrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> config, AnnotatedClass ac, JavaType type, boolean forSerialization, String mutatorPrefix) {
/* 208 */     return new POJOPropertiesCollector(config, forSerialization, type, ac, mutatorPrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicBeanDescription _findStdTypeDesc(JavaType type) {
/* 217 */     Class<?> cls = type.getRawClass();
/* 218 */     if (cls.isPrimitive()) {
/* 219 */       if (cls == boolean.class) {
/* 220 */         return BOOLEAN_DESC;
/*     */       }
/* 222 */       if (cls == int.class) {
/* 223 */         return INT_DESC;
/*     */       }
/* 225 */       if (cls == long.class) {
/* 226 */         return LONG_DESC;
/*     */       }
/*     */     }
/* 229 */     else if (cls == String.class) {
/* 230 */       return STRING_DESC;
/*     */     } 
/*     */     
/* 233 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _isStdJDKCollection(JavaType type) {
/* 243 */     if (!type.isContainerType() || type.isArrayType()) {
/* 244 */       return false;
/*     */     }
/* 246 */     Class<?> raw = type.getRawClass();
/* 247 */     String pkgName = ClassUtil.getPackageName(raw);
/* 248 */     if (pkgName != null && (
/* 249 */       pkgName.startsWith("java.lang") || pkgName.startsWith("java.util")))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 254 */       if (Collection.class.isAssignableFrom(raw) || Map.class.isAssignableFrom(raw))
/*     */       {
/* 256 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasicBeanDescription _findStdJdkCollectionDesc(MapperConfig<?> cfg, JavaType type) {
/* 265 */     if (_isStdJDKCollection(type)) {
/* 266 */       return BasicBeanDescription.forOtherUse(cfg, type, _resolveAnnotatedClass(cfg, type, (ClassIntrospector.MixInResolver)cfg));
/*     */     }
/*     */     
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedClass _resolveAnnotatedClass(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r) {
/* 277 */     return AnnotatedClassResolver.resolve(config, type, r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedClass _resolveAnnotatedWithoutSuperTypes(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r) {
/* 285 */     return AnnotatedClassResolver.resolveWithoutSuperTypes(config, type, r);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\BasicClassIntrospector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */