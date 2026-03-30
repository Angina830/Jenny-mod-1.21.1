/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.WritableObjectId;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DefaultSerializerProvider
/*     */   extends SerializerProvider
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Map<Object, WritableObjectId> _seenObjectIds;
/*     */   protected transient ArrayList<ObjectIdGenerator<?>> _objectIdGenerators;
/*     */   protected transient JsonGenerator _generator;
/*     */   
/*     */   protected DefaultSerializerProvider() {}
/*     */   
/*     */   protected DefaultSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
/*  70 */     super(src, config, f);
/*     */   }
/*     */   
/*     */   protected DefaultSerializerProvider(DefaultSerializerProvider src) {
/*  74 */     super(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract DefaultSerializerProvider createInstance(SerializationConfig paramSerializationConfig, SerializerFactory paramSerializerFactory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSerializerProvider copy() {
/*  94 */     throw new IllegalStateException("DefaultSerializerProvider sub-class not overriding copy()");
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
/*     */   public JsonSerializer<Object> serializerInstance(Annotated annotated, Object serDef) throws JsonMappingException {
/*     */     JsonSerializer<?> ser;
/* 107 */     if (serDef == null) {
/* 108 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 112 */     if (serDef instanceof JsonSerializer) {
/* 113 */       ser = (JsonSerializer)serDef;
/*     */     }
/*     */     else {
/*     */       
/* 117 */       if (!(serDef instanceof Class)) {
/* 118 */         reportBadDefinition(annotated.getType(), "AnnotationIntrospector returned serializer definition of type " + serDef.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
/*     */       }
/*     */ 
/*     */       
/* 122 */       Class<?> serClass = (Class)serDef;
/*     */       
/* 124 */       if (serClass == JsonSerializer.None.class || ClassUtil.isBogusClass(serClass)) {
/* 125 */         return null;
/*     */       }
/* 127 */       if (!JsonSerializer.class.isAssignableFrom(serClass)) {
/* 128 */         reportBadDefinition(annotated.getType(), "AnnotationIntrospector returned Class " + serClass.getName() + "; expected Class<JsonSerializer>");
/*     */       }
/*     */ 
/*     */       
/* 132 */       HandlerInstantiator hi = this._config.getHandlerInstantiator();
/* 133 */       ser = (hi == null) ? null : hi.serializerInstance(this._config, annotated, serClass);
/* 134 */       if (ser == null) {
/* 135 */         ser = (JsonSerializer)ClassUtil.createInstance(serClass, this._config.canOverrideAccessModifiers());
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return _handleResolvable(ser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object includeFilterInstance(BeanPropertyDefinition forProperty, Class<?> filterClass) {
/* 146 */     if (filterClass == null) {
/* 147 */       return null;
/*     */     }
/* 149 */     HandlerInstantiator hi = this._config.getHandlerInstantiator();
/* 150 */     Object filter = (hi == null) ? null : hi.includeFilterInstance(this._config, forProperty, filterClass);
/* 151 */     if (filter == null) {
/* 152 */       filter = ClassUtil.createInstance(filterClass, this._config.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 155 */     return filter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean includeFilterSuppressNulls(Object filter) throws JsonMappingException {
/* 161 */     if (filter == null) {
/* 162 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 167 */       return filter.equals((Object)null);
/* 168 */     } catch (Throwable t) {
/* 169 */       String msg = String.format("Problem determining whether filter of type '%s' should filter out `null` values: (%s) %s", new Object[] { filter.getClass().getName(), t.getClass().getName(), t.getMessage() });
/*     */ 
/*     */       
/* 172 */       reportBadDefinition(filter.getClass(), msg, t);
/* 173 */       return false;
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
/*     */   public WritableObjectId findObjectId(Object forPojo, ObjectIdGenerator<?> generatorType) {
/* 186 */     if (this._seenObjectIds == null) {
/* 187 */       this._seenObjectIds = _createObjectIdMap();
/*     */     } else {
/* 189 */       WritableObjectId writableObjectId = this._seenObjectIds.get(forPojo);
/* 190 */       if (writableObjectId != null) {
/* 191 */         return writableObjectId;
/*     */       }
/*     */     } 
/*     */     
/* 195 */     ObjectIdGenerator<?> generator = null;
/*     */     
/* 197 */     if (this._objectIdGenerators == null) {
/* 198 */       this._objectIdGenerators = new ArrayList<>(8);
/*     */     } else {
/* 200 */       for (int i = 0, len = this._objectIdGenerators.size(); i < len; i++) {
/* 201 */         ObjectIdGenerator<?> gen = this._objectIdGenerators.get(i);
/* 202 */         if (gen.canUseFor(generatorType)) {
/* 203 */           generator = gen;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 208 */     if (generator == null) {
/* 209 */       generator = generatorType.newForSerialization(this);
/* 210 */       this._objectIdGenerators.add(generator);
/*     */     } 
/* 212 */     WritableObjectId oid = new WritableObjectId(generator);
/* 213 */     this._seenObjectIds.put(forPojo, oid);
/* 214 */     return oid;
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
/*     */   protected Map<Object, WritableObjectId> _createObjectIdMap() {
/* 229 */     if (isEnabled(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID)) {
/* 230 */       return new HashMap<>();
/*     */     }
/* 232 */     return new IdentityHashMap<>();
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
/*     */   public boolean hasSerializerFor(Class<?> cls, AtomicReference<Throwable> cause) {
/* 253 */     if (cls == Object.class && 
/* 254 */       !this._config.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
/* 255 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 260 */       JsonSerializer<?> ser = _findExplicitUntypedSerializer(cls);
/* 261 */       return (ser != null);
/* 262 */     } catch (JsonMappingException e) {
/* 263 */       if (cause != null) {
/* 264 */         cause.set(e);
/*     */       }
/* 266 */     } catch (RuntimeException e) {
/* 267 */       if (cause == null) {
/* 268 */         throw e;
/*     */       }
/* 270 */       cause.set(e);
/*     */     } 
/* 272 */     return false;
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
/*     */   public JsonGenerator getGenerator() {
/* 284 */     return this._generator;
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
/*     */   public void serializeValue(JsonGenerator gen, Object value) throws IOException {
/* 301 */     this._generator = gen;
/* 302 */     if (value == null) {
/* 303 */       _serializeNull(gen);
/*     */       return;
/*     */     } 
/* 306 */     Class<?> cls = value.getClass();
/*     */     
/* 308 */     JsonSerializer<Object> ser = findTypedValueSerializer(cls, true, null);
/* 309 */     PropertyName rootName = this._config.getFullRootName();
/* 310 */     if (rootName == null) {
/* 311 */       if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
/* 312 */         _serialize(gen, value, ser, this._config.findRootName(cls));
/*     */         return;
/*     */       } 
/* 315 */     } else if (!rootName.isEmpty()) {
/* 316 */       _serialize(gen, value, ser, rootName);
/*     */       return;
/*     */     } 
/* 319 */     _serialize(gen, value, ser);
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
/*     */   public void serializeValue(JsonGenerator gen, Object value, JavaType rootType) throws IOException {
/* 335 */     this._generator = gen;
/* 336 */     if (value == null) {
/* 337 */       _serializeNull(gen);
/*     */       
/*     */       return;
/*     */     } 
/* 341 */     if (!rootType.getRawClass().isAssignableFrom(value.getClass())) {
/* 342 */       _reportIncompatibleRootType(value, rootType);
/*     */     }
/*     */     
/* 345 */     JsonSerializer<Object> ser = findTypedValueSerializer(rootType, true, null);
/* 346 */     PropertyName rootName = this._config.getFullRootName();
/* 347 */     if (rootName == null) {
/* 348 */       if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
/* 349 */         _serialize(gen, value, ser, this._config.findRootName(rootType));
/*     */         return;
/*     */       } 
/* 352 */     } else if (!rootName.isEmpty()) {
/* 353 */       _serialize(gen, value, ser, rootName);
/*     */       return;
/*     */     } 
/* 356 */     _serialize(gen, value, ser);
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
/*     */   public void serializeValue(JsonGenerator gen, Object value, JavaType rootType, JsonSerializer<Object> ser) throws IOException {
/* 374 */     this._generator = gen;
/* 375 */     if (value == null) {
/* 376 */       _serializeNull(gen);
/*     */       
/*     */       return;
/*     */     } 
/* 380 */     if (rootType != null && !rootType.getRawClass().isAssignableFrom(value.getClass())) {
/* 381 */       _reportIncompatibleRootType(value, rootType);
/*     */     }
/*     */     
/* 384 */     if (ser == null) {
/* 385 */       ser = findTypedValueSerializer(rootType, true, null);
/*     */     }
/* 387 */     PropertyName rootName = this._config.getFullRootName();
/* 388 */     if (rootName == null) {
/* 389 */       if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
/* 390 */         rootName = (rootType == null) ? this._config.findRootName(value.getClass()) : this._config.findRootName(rootType);
/*     */ 
/*     */         
/* 393 */         _serialize(gen, value, ser, rootName);
/*     */         return;
/*     */       } 
/* 396 */     } else if (!rootName.isEmpty()) {
/* 397 */       _serialize(gen, value, ser, rootName);
/*     */       return;
/*     */     } 
/* 400 */     _serialize(gen, value, ser);
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
/*     */   public void serializePolymorphic(JsonGenerator gen, Object value, JavaType rootType, JsonSerializer<Object> valueSer, TypeSerializer typeSer) throws IOException {
/*     */     boolean wrap;
/* 413 */     this._generator = gen;
/* 414 */     if (value == null) {
/* 415 */       _serializeNull(gen);
/*     */       
/*     */       return;
/*     */     } 
/* 419 */     if (rootType != null && !rootType.getRawClass().isAssignableFrom(value.getClass())) {
/* 420 */       _reportIncompatibleRootType(value, rootType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 427 */     if (valueSer == null) {
/* 428 */       if (rootType != null && rootType.isContainerType()) {
/* 429 */         valueSer = findValueSerializer(rootType, null);
/*     */       } else {
/* 431 */         valueSer = findValueSerializer(value.getClass(), null);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 436 */     PropertyName rootName = this._config.getFullRootName();
/* 437 */     if (rootName == null) {
/* 438 */       wrap = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
/* 439 */       if (wrap) {
/* 440 */         gen.writeStartObject();
/* 441 */         PropertyName pname = this._config.findRootName(value.getClass());
/* 442 */         gen.writeFieldName(pname.simpleAsEncoded((MapperConfig)this._config));
/*     */       } 
/* 444 */     } else if (rootName.isEmpty()) {
/* 445 */       wrap = false;
/*     */     } else {
/* 447 */       wrap = true;
/* 448 */       gen.writeStartObject();
/* 449 */       gen.writeFieldName(rootName.getSimpleName());
/*     */     } 
/*     */     try {
/* 452 */       valueSer.serializeWithType(value, gen, this, typeSer);
/* 453 */       if (wrap) {
/* 454 */         gen.writeEndObject();
/*     */       }
/* 456 */     } catch (Exception e) {
/* 457 */       throw _wrapAsIOE(gen, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void _serialize(JsonGenerator gen, Object value, JsonSerializer<Object> ser, PropertyName rootName) throws IOException {
/*     */     try {
/* 466 */       gen.writeStartObject();
/* 467 */       gen.writeFieldName(rootName.simpleAsEncoded((MapperConfig)this._config));
/* 468 */       ser.serialize(value, gen, this);
/* 469 */       gen.writeEndObject();
/* 470 */     } catch (Exception e) {
/* 471 */       throw _wrapAsIOE(gen, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void _serialize(JsonGenerator gen, Object value, JsonSerializer<Object> ser) throws IOException {
/*     */     try {
/* 480 */       ser.serialize(value, gen, this);
/* 481 */     } catch (Exception e) {
/* 482 */       throw _wrapAsIOE(gen, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _serializeNull(JsonGenerator gen) throws IOException {
/* 493 */     JsonSerializer<Object> ser = getDefaultNullValueSerializer();
/*     */     try {
/* 495 */       ser.serialize(null, gen, this);
/* 496 */     } catch (Exception e) {
/* 497 */       throw _wrapAsIOE(gen, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private IOException _wrapAsIOE(JsonGenerator g, Exception e) {
/* 502 */     if (e instanceof IOException) {
/* 503 */       return (IOException)e;
/*     */     }
/* 505 */     String msg = e.getMessage();
/* 506 */     if (msg == null) {
/* 507 */       msg = "[no message for " + e.getClass().getName() + "]";
/*     */     }
/* 509 */     return (IOException)new JsonMappingException((Closeable)g, msg, e);
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
/*     */   public int cachedSerializersCount() {
/* 530 */     return this._serializerCache.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushCachedSerializers() {
/* 540 */     this._serializerCache.flush();
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
/*     */   public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
/* 559 */     if (javaType == null) {
/* 560 */       throw new IllegalArgumentException("A class must be provided");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 565 */     visitor.setProvider(this);
/* 566 */     findValueSerializer(javaType, null).acceptJsonFormatVisitor(visitor, javaType);
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
/*     */   @Deprecated
/*     */   public JsonSchema generateJsonSchema(Class<?> type) throws JsonMappingException {
/* 585 */     JsonSerializer<Object> ser = findValueSerializer(type, null);
/* 586 */     JsonNode schemaNode = (ser instanceof SchemaAware) ? ((SchemaAware)ser).getSchema(this, null) : JsonSchema.getDefaultSchemaNode();
/*     */     
/* 588 */     if (!(schemaNode instanceof ObjectNode)) {
/* 589 */       throw new IllegalArgumentException("Class " + type.getName() + " would not be serialized as a JSON object and therefore has no schema");
/*     */     }
/*     */     
/* 592 */     return new JsonSchema((ObjectNode)schemaNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Impl
/*     */     extends DefaultSerializerProvider
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*     */     public Impl() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Impl(Impl src) {
/* 610 */       super(src);
/*     */     }
/*     */     
/*     */     protected Impl(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
/* 614 */       super(src, config, f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public DefaultSerializerProvider copy() {
/* 620 */       if (getClass() != Impl.class) {
/* 621 */         return super.copy();
/*     */       }
/* 623 */       return new Impl(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Impl createInstance(SerializationConfig config, SerializerFactory jsf) {
/* 628 */       return new Impl(this, config, jsf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\DefaultSerializerProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */