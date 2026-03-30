/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyFilter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
/*     */ 
/*     */ public abstract class StdSerializer<T>
/*     */   extends JsonSerializer<T>
/*     */   implements JsonFormatVisitable, SchemaAware, Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   private static final Object KEY_CONTENT_CONVERTER_LOCK = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Class<T> _handledType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdSerializer(Class<T> t) {
/*  59 */     this._handledType = t;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StdSerializer(JavaType type) {
/*  64 */     this._handledType = type.getRawClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdSerializer(Class<?> t, boolean dummy) {
/*  73 */     this._handledType = (Class)t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdSerializer(StdSerializer<?> src) {
/*  81 */     this._handledType = src._handledType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> handledType() {
/*  91 */     return this._handledType;
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
/*     */   public abstract void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException;
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
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 117 */     visitor.expectAnyFormat(typeHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/* 127 */     return (JsonNode)createSchemaNode("string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint, boolean isOptional) throws JsonMappingException {
/* 138 */     ObjectNode schema = (ObjectNode)getSchema(provider, typeHint);
/* 139 */     if (!isOptional) {
/* 140 */       schema.put("required", !isOptional);
/*     */     }
/* 142 */     return (JsonNode)schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectNode createSchemaNode(String type) {
/* 153 */     ObjectNode schema = JsonNodeFactory.instance.objectNode();
/* 154 */     schema.put("type", type);
/* 155 */     return schema;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ObjectNode createSchemaNode(String type, boolean isOptional) {
/* 160 */     ObjectNode schema = createSchemaNode(type);
/* 161 */     if (!isOptional) {
/* 162 */       schema.put("required", !isOptional);
/*     */     }
/* 164 */     return schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void visitStringFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 175 */     visitor.expectStringFormat(typeHint);
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
/*     */   protected void visitStringFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint, JsonValueFormat format) throws JsonMappingException {
/* 189 */     JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
/* 190 */     if (v2 != null) {
/* 191 */       v2.format(format);
/*     */     }
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
/*     */   protected void visitIntFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint, JsonParser.NumberType numberType) throws JsonMappingException {
/* 205 */     JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
/* 206 */     if (_neitherNull(v2, numberType)) {
/* 207 */       v2.numberType(numberType);
/*     */     }
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
/*     */   protected void visitIntFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint, JsonParser.NumberType numberType, JsonValueFormat format) throws JsonMappingException {
/* 222 */     JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
/* 223 */     if (v2 != null) {
/* 224 */       if (numberType != null) {
/* 225 */         v2.numberType(numberType);
/*     */       }
/* 227 */       if (format != null) {
/* 228 */         v2.format(format);
/*     */       }
/*     */     } 
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
/*     */   protected void visitFloatFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint, JsonParser.NumberType numberType) throws JsonMappingException {
/* 243 */     JsonNumberFormatVisitor v2 = visitor.expectNumberFormat(typeHint);
/* 244 */     if (v2 != null) {
/* 245 */       v2.numberType(numberType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void visitArrayFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint, JsonSerializer<?> itemSerializer, JavaType itemType) throws JsonMappingException {
/* 256 */     JsonArrayFormatVisitor v2 = visitor.expectArrayFormat(typeHint);
/* 257 */     if (_neitherNull(v2, itemSerializer)) {
/* 258 */       v2.itemsFormat((JsonFormatVisitable)itemSerializer, itemType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void visitArrayFormat(JsonFormatVisitorWrapper visitor, JavaType typeHint, JsonFormatTypes itemType) throws JsonMappingException {
/* 269 */     JsonArrayFormatVisitor v2 = visitor.expectArrayFormat(typeHint);
/* 270 */     if (v2 != null) {
/* 271 */       v2.itemsFormat(itemType);
/*     */     }
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
/*     */   public void wrapAndThrow(SerializerProvider provider, Throwable t, Object bean, String fieldName) throws IOException {
/* 301 */     while (t instanceof java.lang.reflect.InvocationTargetException && t.getCause() != null) {
/* 302 */       t = t.getCause();
/*     */     }
/*     */     
/* 305 */     ClassUtil.throwIfError(t);
/*     */     
/* 307 */     boolean wrap = (provider == null || provider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS));
/* 308 */     if (t instanceof IOException) {
/* 309 */       if (!wrap || !(t instanceof JsonMappingException)) {
/* 310 */         throw (IOException)t;
/*     */       }
/* 312 */     } else if (!wrap) {
/* 313 */       ClassUtil.throwIfRTE(t);
/*     */     } 
/*     */     
/* 316 */     throw JsonMappingException.wrapWithPath(t, bean, fieldName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wrapAndThrow(SerializerProvider provider, Throwable t, Object bean, int index) throws IOException {
/* 323 */     while (t instanceof java.lang.reflect.InvocationTargetException && t.getCause() != null) {
/* 324 */       t = t.getCause();
/*     */     }
/*     */     
/* 327 */     ClassUtil.throwIfError(t);
/*     */     
/* 329 */     boolean wrap = (provider == null || provider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS));
/* 330 */     if (t instanceof IOException) {
/* 331 */       if (!wrap || !(t instanceof JsonMappingException)) {
/* 332 */         throw (IOException)t;
/*     */       }
/* 334 */     } else if (!wrap) {
/* 335 */       ClassUtil.throwIfRTE(t);
/*     */     } 
/*     */     
/* 338 */     throw JsonMappingException.wrapWithPath(t, bean, index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonSerializer<?> findContextualConvertingSerializer(SerializerProvider provider, BeanProperty property, JsonSerializer<?> existingSerializer) throws JsonMappingException {
/* 364 */     Map<Object, Object> conversions = (Map<Object, Object>)provider.getAttribute(KEY_CONTENT_CONVERTER_LOCK);
/* 365 */     if (conversions != null) {
/* 366 */       Object lock = conversions.get(property);
/* 367 */       if (lock != null) {
/* 368 */         return existingSerializer;
/*     */       }
/*     */     } else {
/* 371 */       conversions = new IdentityHashMap<>();
/* 372 */       provider.setAttribute(KEY_CONTENT_CONVERTER_LOCK, conversions);
/*     */     } 
/* 374 */     conversions.put(property, Boolean.TRUE);
/*     */     try {
/* 376 */       JsonSerializer<?> ser = findConvertingContentSerializer(provider, property, existingSerializer);
/* 377 */       if (ser != null) {
/* 378 */         return provider.handleSecondaryContextualization(ser, property);
/*     */       }
/*     */     } finally {
/* 381 */       conversions.remove(property);
/*     */     } 
/* 383 */     return existingSerializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JsonSerializer<?> findConvertingContentSerializer(SerializerProvider provider, BeanProperty prop, JsonSerializer<?> existingSerializer) throws JsonMappingException {
/* 394 */     AnnotationIntrospector intr = provider.getAnnotationIntrospector();
/* 395 */     if (_neitherNull(intr, prop)) {
/* 396 */       AnnotatedMember m = prop.getMember();
/* 397 */       if (m != null) {
/* 398 */         Object convDef = intr.findSerializationContentConverter(m);
/* 399 */         if (convDef != null) {
/* 400 */           Converter<Object, Object> conv = provider.converterInstance((Annotated)prop.getMember(), convDef);
/* 401 */           JavaType delegateType = conv.getOutputType(provider.getTypeFactory());
/*     */           
/* 403 */           if (existingSerializer == null && !delegateType.isJavaLangObject()) {
/* 404 */             existingSerializer = provider.findValueSerializer(delegateType);
/*     */           }
/* 406 */           return new StdDelegatingSerializer(conv, delegateType, existingSerializer);
/*     */         } 
/*     */       } 
/*     */     } 
/* 410 */     return existingSerializer;
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
/*     */   protected PropertyFilter findPropertyFilter(SerializerProvider provider, Object filterId, Object valueToFilter) throws JsonMappingException {
/* 423 */     FilterProvider filters = provider.getFilterProvider();
/*     */     
/* 425 */     if (filters == null) {
/* 426 */       provider.reportBadDefinition(handledType(), "Cannot resolve PropertyFilter with id '" + filterId + "'; no FilterProvider configured");
/*     */     }
/*     */ 
/*     */     
/* 430 */     return filters.findPropertyFilter(filterId, valueToFilter);
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
/*     */   protected JsonFormat.Value findFormatOverrides(SerializerProvider provider, BeanProperty prop, Class<?> typeForDefaults) {
/* 445 */     if (prop != null) {
/* 446 */       return prop.findPropertyFormat((MapperConfig)provider.getConfig(), typeForDefaults);
/*     */     }
/*     */     
/* 449 */     return provider.getDefaultPropertyFormat(typeForDefaults);
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
/*     */   protected Boolean findFormatFeature(SerializerProvider provider, BeanProperty prop, Class<?> typeForDefaults, JsonFormat.Feature feat) {
/* 464 */     JsonFormat.Value format = findFormatOverrides(provider, prop, typeForDefaults);
/* 465 */     if (format != null) {
/* 466 */       return format.getFeature(feat);
/*     */     }
/* 468 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonInclude.Value findIncludeOverrides(SerializerProvider provider, BeanProperty prop, Class<?> typeForDefaults) {
/* 477 */     if (prop != null) {
/* 478 */       return prop.findPropertyInclusion((MapperConfig)provider.getConfig(), typeForDefaults);
/*     */     }
/*     */     
/* 481 */     return provider.getDefaultPropertyInclusion(typeForDefaults);
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
/*     */   protected JsonSerializer<?> findAnnotatedContentSerializer(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/* 493 */     if (property != null) {
/*     */       
/* 495 */       AnnotatedMember m = property.getMember();
/* 496 */       AnnotationIntrospector intr = serializers.getAnnotationIntrospector();
/* 497 */       if (m != null) {
/* 498 */         Object serDef = intr.findContentSerializer((Annotated)m);
/* 499 */         if (serDef != null) {
/* 500 */           return serializers.serializerInstance((Annotated)m, serDef);
/*     */         }
/*     */       } 
/*     */     } 
/* 504 */     return null;
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
/*     */   protected boolean isDefaultSerializer(JsonSerializer<?> serializer) {
/* 520 */     return ClassUtil.isJacksonStdImpl(serializer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final boolean _neitherNull(Object a, Object b) {
/* 527 */     return (a != null && b != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final boolean _nonEmpty(Collection<?> c) {
/* 534 */     return (c != null && !c.isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StdSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */