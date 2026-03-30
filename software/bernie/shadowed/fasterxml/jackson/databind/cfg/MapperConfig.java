/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.TypeReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
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
/*     */ public abstract class MapperConfig<T extends MapperConfig<T>>
/*     */   implements ClassIntrospector.MixInResolver, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*  45 */   protected static final JsonInclude.Value EMPTY_INCLUDE = JsonInclude.Value.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected static final JsonFormat.Value EMPTY_FORMAT = JsonFormat.Value.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _mapperFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BaseSettings _base;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapperConfig(BaseSettings base, int mapperFeatures) {
/*  70 */     this._base = base;
/*  71 */     this._mapperFeatures = mapperFeatures;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapperConfig(MapperConfig<T> src, int mapperFeatures) {
/*  76 */     this._base = src._base;
/*  77 */     this._mapperFeatures = mapperFeatures;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapperConfig(MapperConfig<T> src, BaseSettings base) {
/*  82 */     this._base = base;
/*  83 */     this._mapperFeatures = src._mapperFeatures;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapperConfig(MapperConfig<T> src) {
/*  88 */     this._base = src._base;
/*  89 */     this._mapperFeatures = src._mapperFeatures;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <F extends Enum<F> & ConfigFeature> int collectFeatureDefaults(Class<F> enumClass) {
/*  98 */     int flags = 0;
/*  99 */     for (Enum enum_ : (Enum[])enumClass.getEnumConstants()) {
/* 100 */       if (((ConfigFeature)enum_).enabledByDefault()) {
/* 101 */         flags |= ((ConfigFeature)enum_).getMask();
/*     */       }
/*     */     } 
/* 104 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T with(MapperFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T without(MapperFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T with(MapperFeature paramMapperFeature, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isEnabled(MapperFeature f) {
/* 141 */     return ((this._mapperFeatures & f.getMask()) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasMapperFeatures(int featureMask) {
/* 151 */     return ((this._mapperFeatures & featureMask) == featureMask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isAnnotationProcessingEnabled() {
/* 161 */     return isEnabled(MapperFeature.USE_ANNOTATIONS);
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
/*     */   public final boolean canOverrideAccessModifiers() {
/* 176 */     return isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean shouldSortPropertiesAlphabetically() {
/* 184 */     return isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
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
/*     */   public abstract boolean useRootWrapping();
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
/*     */   public SerializableString compileString(String src) {
/* 216 */     return (SerializableString)new SerializedString(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassIntrospector getClassIntrospector() {
/* 226 */     return this._base.getClassIntrospector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationIntrospector getAnnotationIntrospector() {
/* 236 */     if (isEnabled(MapperFeature.USE_ANNOTATIONS)) {
/* 237 */       return this._base.getAnnotationIntrospector();
/*     */     }
/* 239 */     return (AnnotationIntrospector)NopAnnotationIntrospector.instance;
/*     */   }
/*     */   
/*     */   public final PropertyNamingStrategy getPropertyNamingStrategy() {
/* 243 */     return this._base.getPropertyNamingStrategy();
/*     */   }
/*     */   
/*     */   public final HandlerInstantiator getHandlerInstantiator() {
/* 247 */     return this._base.getHandlerInstantiator();
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
/*     */   public final TypeResolverBuilder<?> getDefaultTyper(JavaType baseType) {
/* 263 */     return this._base.getTypeResolverBuilder();
/*     */   }
/*     */   
/*     */   public abstract SubtypeResolver getSubtypeResolver();
/*     */   
/*     */   public final TypeFactory getTypeFactory() {
/* 269 */     return this._base.getTypeFactory();
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
/*     */   public final JavaType constructType(Class<?> cls) {
/* 281 */     return getTypeFactory().constructType(cls);
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
/*     */   public final JavaType constructType(TypeReference<?> valueTypeRef) {
/* 293 */     return getTypeFactory().constructType(valueTypeRef.getType());
/*     */   }
/*     */   
/*     */   public JavaType constructSpecializedType(JavaType baseType, Class<?> subclass) {
/* 297 */     return getTypeFactory().constructSpecializedType(baseType, subclass);
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
/*     */   public BeanDescription introspectClassAnnotations(Class<?> cls) {
/* 311 */     return introspectClassAnnotations(constructType(cls));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDescription introspectClassAnnotations(JavaType type) {
/* 319 */     return getClassIntrospector().forClassAnnotations(this, type, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDescription introspectDirectClassAnnotations(Class<?> cls) {
/* 328 */     return introspectDirectClassAnnotations(constructType(cls));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final BeanDescription introspectDirectClassAnnotations(JavaType type) {
/* 337 */     return getClassIntrospector().forDirectClassAnnotations(this, type, this);
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
/*     */   public abstract ConfigOverride findConfigOverride(Class<?> paramClass);
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
/*     */   public abstract ConfigOverride getConfigOverride(Class<?> paramClass);
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
/*     */   public abstract JsonInclude.Value getDefaultPropertyInclusion();
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
/*     */   public abstract JsonInclude.Value getDefaultPropertyInclusion(Class<?> paramClass);
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
/*     */   public JsonInclude.Value getDefaultPropertyInclusion(Class<?> baseType, JsonInclude.Value defaultIncl) {
/* 403 */     JsonInclude.Value v = getConfigOverride(baseType).getInclude();
/* 404 */     if (v != null) {
/* 405 */       return v;
/*     */     }
/* 407 */     return defaultIncl;
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
/*     */   public abstract JsonInclude.Value getDefaultInclusion(Class<?> paramClass1, Class<?> paramClass2);
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
/*     */   public JsonInclude.Value getDefaultInclusion(Class<?> baseType, Class<?> propertyType, JsonInclude.Value defaultIncl) {
/* 440 */     JsonInclude.Value baseOverride = getConfigOverride(baseType).getInclude();
/* 441 */     JsonInclude.Value propOverride = getConfigOverride(propertyType).getIncludeAsProperty();
/*     */     
/* 443 */     JsonInclude.Value result = JsonInclude.Value.mergeAll(new JsonInclude.Value[] { defaultIncl, baseOverride, propOverride });
/* 444 */     return result;
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
/*     */   public abstract JsonFormat.Value getDefaultPropertyFormat(Class<?> paramClass);
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
/*     */   public abstract JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> paramClass);
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
/*     */   public abstract JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> paramClass, AnnotatedClass paramAnnotatedClass);
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
/*     */   public abstract VisibilityChecker<?> getDefaultVisibilityChecker();
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
/*     */   public abstract VisibilityChecker<?> getDefaultVisibilityChecker(Class<?> paramClass, AnnotatedClass paramAnnotatedClass);
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
/*     */   public abstract JsonSetter.Value getDefaultSetterInfo();
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
/*     */   public abstract Boolean getDefaultMergeable();
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
/*     */   public abstract Boolean getDefaultMergeable(Class<?> paramClass);
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
/*     */   public final DateFormat getDateFormat() {
/* 553 */     return this._base.getDateFormat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Locale getLocale() {
/* 560 */     return this._base.getLocale();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TimeZone getTimeZone() {
/* 567 */     return this._base.getTimeZone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Class<?> getActiveView();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64Variant getBase64Variant() {
/* 582 */     return this._base.getBase64Variant();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ContextAttributes getAttributes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract PropertyName findRootName(JavaType paramJavaType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract PropertyName findRootName(Class<?> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeResolverBuilder<?> typeResolverBuilderInstance(Annotated annotated, Class<? extends TypeResolverBuilder<?>> builderClass) {
/* 617 */     HandlerInstantiator hi = getHandlerInstantiator();
/* 618 */     if (hi != null) {
/* 619 */       TypeResolverBuilder<?> builder = hi.typeResolverBuilderInstance(this, annotated, builderClass);
/* 620 */       if (builder != null) {
/* 621 */         return builder;
/*     */       }
/*     */     } 
/* 624 */     return (TypeResolverBuilder)ClassUtil.createInstance(builderClass, canOverrideAccessModifiers());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeIdResolver typeIdResolverInstance(Annotated annotated, Class<? extends TypeIdResolver> resolverClass) {
/* 634 */     HandlerInstantiator hi = getHandlerInstantiator();
/* 635 */     if (hi != null) {
/* 636 */       TypeIdResolver builder = hi.typeIdResolverInstance(this, annotated, resolverClass);
/* 637 */       if (builder != null) {
/* 638 */         return builder;
/*     */       }
/*     */     } 
/* 641 */     return (TypeIdResolver)ClassUtil.createInstance(resolverClass, canOverrideAccessModifiers());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\MapperConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */