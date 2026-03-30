/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatabindContext
/*     */ {
/*     */   private static final int MAX_ERROR_STR_LEN = 500;
/*     */   
/*     */   public abstract MapperConfig<?> getConfig();
/*     */   
/*     */   public abstract AnnotationIntrospector getAnnotationIntrospector();
/*     */   
/*     */   public abstract boolean isEnabled(MapperFeature paramMapperFeature);
/*     */   
/*     */   public abstract boolean canOverrideAccessModifiers();
/*     */   
/*     */   public abstract Class<?> getActiveView();
/*     */   
/*     */   public abstract Locale getLocale();
/*     */   
/*     */   public abstract TimeZone getTimeZone();
/*     */   
/*     */   public abstract JsonFormat.Value getDefaultPropertyFormat(Class<?> paramClass);
/*     */   
/*     */   public abstract Object getAttribute(Object paramObject);
/*     */   
/*     */   public abstract DatabindContext setAttribute(Object paramObject1, Object paramObject2);
/*     */   
/*     */   public JavaType constructType(Type type) {
/* 146 */     if (type == null) {
/* 147 */       return null;
/*     */     }
/* 149 */     return getTypeFactory().constructType(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType constructSpecializedType(JavaType baseType, Class<?> subclass) {
/* 158 */     if (baseType.getRawClass() == subclass) {
/* 159 */       return baseType;
/*     */     }
/* 161 */     return getConfig().constructSpecializedType(baseType, subclass);
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
/*     */   public JavaType resolveSubType(JavaType baseType, String subClass) throws JsonMappingException {
/*     */     Class<?> cls;
/* 176 */     if (subClass.indexOf('<') > 0)
/*     */     {
/* 178 */       return getTypeFactory().constructFromCanonical(subClass);
/*     */     }
/*     */     
/*     */     try {
/* 182 */       cls = getTypeFactory().findClass(subClass);
/* 183 */     } catch (ClassNotFoundException e) {
/* 184 */       return null;
/* 185 */     } catch (Exception e) {
/* 186 */       throw invalidTypeIdException(baseType, subClass, String.format("problem: (%s) %s", new Object[] { e.getClass().getName(), e.getMessage() }));
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (baseType.isTypeOrSuperTypeOf(cls)) {
/* 191 */       return getTypeFactory().constructSpecializedType(baseType, cls);
/*     */     }
/* 193 */     throw invalidTypeIdException(baseType, subClass, "Not a subtype");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract JsonMappingException invalidTypeIdException(JavaType paramJavaType, String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TypeFactory getTypeFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdGenerator<?> objectIdGeneratorInstance(Annotated annotated, ObjectIdInfo objectIdInfo) throws JsonMappingException {
/* 222 */     Class<?> implClass = objectIdInfo.getGeneratorType();
/* 223 */     MapperConfig<?> config = getConfig();
/* 224 */     HandlerInstantiator hi = config.getHandlerInstantiator();
/* 225 */     ObjectIdGenerator<?> gen = (hi == null) ? null : hi.objectIdGeneratorInstance(config, annotated, implClass);
/* 226 */     if (gen == null) {
/* 227 */       gen = (ObjectIdGenerator)ClassUtil.createInstance(implClass, config.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 230 */     return gen.forScope(objectIdInfo.getScope());
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectIdResolver objectIdResolverInstance(Annotated annotated, ObjectIdInfo objectIdInfo) {
/* 235 */     Class<? extends ObjectIdResolver> implClass = objectIdInfo.getResolverType();
/* 236 */     MapperConfig<?> config = getConfig();
/* 237 */     HandlerInstantiator hi = config.getHandlerInstantiator();
/* 238 */     ObjectIdResolver resolver = (hi == null) ? null : hi.resolverIdGeneratorInstance(config, annotated, implClass);
/* 239 */     if (resolver == null) {
/* 240 */       resolver = (ObjectIdResolver)ClassUtil.createInstance(implClass, config.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 243 */     return resolver;
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
/*     */   public Converter<Object, Object> converterInstance(Annotated annotated, Object converterDef) throws JsonMappingException {
/* 257 */     if (converterDef == null) {
/* 258 */       return null;
/*     */     }
/* 260 */     if (converterDef instanceof Converter) {
/* 261 */       return (Converter<Object, Object>)converterDef;
/*     */     }
/* 263 */     if (!(converterDef instanceof Class)) {
/* 264 */       throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + converterDef.getClass().getName() + "; expected type Converter or Class<Converter> instead");
/*     */     }
/*     */     
/* 267 */     Class<?> converterClass = (Class)converterDef;
/*     */     
/* 269 */     if (converterClass == Converter.None.class || ClassUtil.isBogusClass(converterClass)) {
/* 270 */       return null;
/*     */     }
/* 272 */     if (!Converter.class.isAssignableFrom(converterClass)) {
/* 273 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + converterClass.getName() + "; expected Class<Converter>");
/*     */     }
/*     */     
/* 276 */     MapperConfig<?> config = getConfig();
/* 277 */     HandlerInstantiator hi = config.getHandlerInstantiator();
/* 278 */     Converter<?, ?> conv = (hi == null) ? null : hi.converterInstance(config, annotated, converterClass);
/* 279 */     if (conv == null) {
/* 280 */       conv = (Converter<?, ?>)ClassUtil.createInstance(converterClass, config.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 283 */     return (Converter)conv;
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
/*     */   public abstract <T> T reportBadDefinition(JavaType paramJavaType, String paramString) throws JsonMappingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T reportBadDefinition(Class<?> type, String msg) throws JsonMappingException {
/* 305 */     return reportBadDefinition(constructType(type), msg);
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
/*     */   protected final String _format(String msg, Object... msgArgs) {
/* 318 */     if (msgArgs.length > 0) {
/* 319 */       return String.format(msg, msgArgs);
/*     */     }
/* 321 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String _truncate(String desc) {
/* 328 */     if (desc == null) {
/* 329 */       return "";
/*     */     }
/* 331 */     if (desc.length() <= 500) {
/* 332 */       return desc;
/*     */     }
/* 334 */     return desc.substring(0, 500) + "]...[" + desc.substring(desc.length() - 500);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _quotedString(String desc) {
/* 341 */     if (desc == null) {
/* 342 */       return "[N/A]";
/*     */     }
/*     */     
/* 345 */     return String.format("\"%s\"", new Object[] { _truncate(desc) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _colonConcat(String msgBase, String extra) {
/* 352 */     if (extra == null) {
/* 353 */       return msgBase;
/*     */     }
/* 355 */     return msgBase + ": " + extra;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _calcName(Class<?> cls) {
/* 362 */     if (cls.isArray()) {
/* 363 */       return _calcName(cls.getComponentType()) + "[]";
/*     */     }
/* 365 */     return cls.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _desc(String desc) {
/* 372 */     if (desc == null) {
/* 373 */       return "[N/A]";
/*     */     }
/*     */     
/* 376 */     return _truncate(desc);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\DatabindContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */