/*      */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.text.DateFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ContextAttributes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidDefinitionException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidTypeIdException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.FilterProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ResolvableSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.SerializerCache;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.SerializerFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.FailingSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.TypeWrappedSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.WritableObjectId;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.NullSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class SerializerProvider
/*      */   extends DatabindContext
/*      */ {
/*      */   protected static final boolean CACHE_UNKNOWN_MAPPINGS = false;
/*   57 */   public static final JsonSerializer<Object> DEFAULT_NULL_KEY_SERIALIZER = (JsonSerializer<Object>)new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   protected static final JsonSerializer<Object> DEFAULT_UNKNOWN_SERIALIZER = (JsonSerializer<Object>)new UnknownSerializer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final SerializationConfig _config;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> _serializationView;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final SerializerFactory _serializerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final SerializerCache _serializerCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ContextAttributes _attributes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   protected JsonSerializer<Object> _unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _keySerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  144 */   protected JsonSerializer<Object> _nullValueSerializer = (JsonSerializer<Object>)NullSerializer.instance;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   protected JsonSerializer<Object> _nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ReadOnlyClassToSerializerMap _knownSerializers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DateFormat _dateFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _stdNullValueSerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializerProvider() {
/*  194 */     this._config = null;
/*  195 */     this._serializerFactory = null;
/*  196 */     this._serializerCache = new SerializerCache();
/*      */     
/*  198 */     this._knownSerializers = null;
/*      */     
/*  200 */     this._serializationView = null;
/*  201 */     this._attributes = null;
/*      */ 
/*      */     
/*  204 */     this._stdNullValueSerializer = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
/*  216 */     this._serializerFactory = f;
/*  217 */     this._config = config;
/*      */     
/*  219 */     this._serializerCache = src._serializerCache;
/*  220 */     this._unknownTypeSerializer = src._unknownTypeSerializer;
/*  221 */     this._keySerializer = src._keySerializer;
/*  222 */     this._nullValueSerializer = src._nullValueSerializer;
/*  223 */     this._nullKeySerializer = src._nullKeySerializer;
/*      */     
/*  225 */     this._stdNullValueSerializer = (this._nullValueSerializer == DEFAULT_NULL_KEY_SERIALIZER);
/*      */     
/*  227 */     this._serializationView = config.getActiveView();
/*  228 */     this._attributes = config.getAttributes();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     this._knownSerializers = this._serializerCache.getReadOnlyLookupMap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SerializerProvider(SerializerProvider src) {
/*  244 */     this._config = null;
/*  245 */     this._serializationView = null;
/*  246 */     this._serializerFactory = null;
/*  247 */     this._knownSerializers = null;
/*      */ 
/*      */     
/*  250 */     this._serializerCache = new SerializerCache();
/*      */     
/*  252 */     this._unknownTypeSerializer = src._unknownTypeSerializer;
/*  253 */     this._keySerializer = src._keySerializer;
/*  254 */     this._nullValueSerializer = src._nullValueSerializer;
/*  255 */     this._nullKeySerializer = src._nullKeySerializer;
/*      */     
/*  257 */     this._stdNullValueSerializer = src._stdNullValueSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultKeySerializer(JsonSerializer<Object> ks) {
/*  274 */     if (ks == null) {
/*  275 */       throw new IllegalArgumentException("Cannot pass null JsonSerializer");
/*      */     }
/*  277 */     this._keySerializer = ks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNullValueSerializer(JsonSerializer<Object> nvs) {
/*  291 */     if (nvs == null) {
/*  292 */       throw new IllegalArgumentException("Cannot pass null JsonSerializer");
/*      */     }
/*  294 */     this._nullValueSerializer = nvs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNullKeySerializer(JsonSerializer<Object> nks) {
/*  308 */     if (nks == null) {
/*  309 */       throw new IllegalArgumentException("Cannot pass null JsonSerializer");
/*      */     }
/*  311 */     this._nullKeySerializer = nks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SerializationConfig getConfig() {
/*  325 */     return this._config;
/*      */   }
/*      */   
/*      */   public final AnnotationIntrospector getAnnotationIntrospector() {
/*  329 */     return this._config.getAnnotationIntrospector();
/*      */   }
/*      */ 
/*      */   
/*      */   public final TypeFactory getTypeFactory() {
/*  334 */     return this._config.getTypeFactory();
/*      */   }
/*      */   
/*      */   public final Class<?> getActiveView() {
/*  338 */     return this._serializationView;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final Class<?> getSerializationView() {
/*  344 */     return this._serializationView;
/*      */   }
/*      */   
/*      */   public final boolean canOverrideAccessModifiers() {
/*  348 */     return this._config.canOverrideAccessModifiers();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(MapperFeature feature) {
/*  353 */     return this._config.isEnabled(feature);
/*      */   }
/*      */ 
/*      */   
/*      */   public final JsonFormat.Value getDefaultPropertyFormat(Class<?> baseType) {
/*  358 */     return this._config.getDefaultPropertyFormat(baseType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonInclude.Value getDefaultPropertyInclusion(Class<?> baseType) {
/*  365 */     return this._config.getDefaultPropertyInclusion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  376 */     return this._config.getLocale();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeZone getTimeZone() {
/*  387 */     return this._config.getTimeZone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getAttribute(Object key) {
/*  398 */     return this._attributes.getAttribute(key);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializerProvider setAttribute(Object key, Object value) {
/*  404 */     this._attributes = this._attributes.withPerCallAttribute(key, value);
/*  405 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(SerializationFeature feature) {
/*  423 */     return this._config.isEnabled(feature);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean hasSerializationFeatures(int featureMask) {
/*  433 */     return this._config.hasSerializationFeatures(featureMask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final FilterProvider getFilterProvider() {
/*  444 */     return this._config.getFilterProvider();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonGenerator getGenerator() {
/*  455 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findValueSerializer(Class<?> valueType, BeanProperty property) throws JsonMappingException {
/*  501 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(valueType);
/*  502 */     if (ser == null) {
/*      */       
/*  504 */       ser = this._serializerCache.untypedValueSerializer(valueType);
/*  505 */       if (ser == null) {
/*      */         
/*  507 */         ser = this._serializerCache.untypedValueSerializer(this._config.constructType(valueType));
/*  508 */         if (ser == null) {
/*      */           
/*  510 */           ser = _createAndCacheUntypedSerializer(valueType);
/*      */           
/*  512 */           if (ser == null) {
/*  513 */             ser = getUnknownTypeSerializer(valueType);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  518 */             return ser;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  524 */     return (JsonSerializer)handleSecondaryContextualization(ser, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
/*  543 */     if (valueType == null) {
/*  544 */       reportMappingProblem("Null passed for `valueType` of `findValueSerializer()`", new Object[0]);
/*      */     }
/*      */     
/*  547 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(valueType);
/*  548 */     if (ser == null) {
/*  549 */       ser = this._serializerCache.untypedValueSerializer(valueType);
/*  550 */       if (ser == null) {
/*  551 */         ser = _createAndCacheUntypedSerializer(valueType);
/*  552 */         if (ser == null) {
/*  553 */           ser = getUnknownTypeSerializer(valueType.getRawClass());
/*      */ 
/*      */ 
/*      */           
/*  557 */           return ser;
/*      */         } 
/*      */       } 
/*      */     } 
/*  561 */     return (JsonSerializer)handleSecondaryContextualization(ser, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findValueSerializer(Class<?> valueType) throws JsonMappingException {
/*  574 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(valueType);
/*  575 */     if (ser == null) {
/*  576 */       ser = this._serializerCache.untypedValueSerializer(valueType);
/*  577 */       if (ser == null) {
/*  578 */         ser = this._serializerCache.untypedValueSerializer(this._config.constructType(valueType));
/*  579 */         if (ser == null) {
/*  580 */           ser = _createAndCacheUntypedSerializer(valueType);
/*  581 */           if (ser == null) {
/*  582 */             ser = getUnknownTypeSerializer(valueType);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  590 */     return ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findValueSerializer(JavaType valueType) throws JsonMappingException {
/*  604 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(valueType);
/*  605 */     if (ser == null) {
/*  606 */       ser = this._serializerCache.untypedValueSerializer(valueType);
/*  607 */       if (ser == null) {
/*  608 */         ser = _createAndCacheUntypedSerializer(valueType);
/*  609 */         if (ser == null) {
/*  610 */           ser = getUnknownTypeSerializer(valueType.getRawClass());
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  617 */     return ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findPrimaryPropertySerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
/*  636 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(valueType);
/*  637 */     if (ser == null) {
/*  638 */       ser = this._serializerCache.untypedValueSerializer(valueType);
/*  639 */       if (ser == null) {
/*  640 */         ser = _createAndCacheUntypedSerializer(valueType);
/*  641 */         if (ser == null) {
/*  642 */           ser = getUnknownTypeSerializer(valueType.getRawClass());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  647 */           return ser;
/*      */         } 
/*      */       } 
/*      */     } 
/*  651 */     return (JsonSerializer)handlePrimaryContextualization(ser, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findPrimaryPropertySerializer(Class<?> valueType, BeanProperty property) throws JsonMappingException {
/*  662 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(valueType);
/*  663 */     if (ser == null) {
/*  664 */       ser = this._serializerCache.untypedValueSerializer(valueType);
/*  665 */       if (ser == null) {
/*  666 */         ser = this._serializerCache.untypedValueSerializer(this._config.constructType(valueType));
/*  667 */         if (ser == null) {
/*  668 */           ser = _createAndCacheUntypedSerializer(valueType);
/*  669 */           if (ser == null) {
/*  670 */             ser = getUnknownTypeSerializer(valueType);
/*      */ 
/*      */ 
/*      */             
/*  674 */             return ser;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  679 */     return (JsonSerializer)handlePrimaryContextualization(ser, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findTypedValueSerializer(Class<?> valueType, boolean cache, BeanProperty property) throws JsonMappingException {
/*      */     TypeWrappedSerializer typeWrappedSerializer;
/*  702 */     JsonSerializer<Object> ser = this._knownSerializers.typedValueSerializer(valueType);
/*  703 */     if (ser != null) {
/*  704 */       return ser;
/*      */     }
/*      */     
/*  707 */     ser = this._serializerCache.typedValueSerializer(valueType);
/*  708 */     if (ser != null) {
/*  709 */       return ser;
/*      */     }
/*      */ 
/*      */     
/*  713 */     ser = findValueSerializer(valueType, property);
/*  714 */     TypeSerializer typeSer = this._serializerFactory.createTypeSerializer(this._config, this._config.constructType(valueType));
/*      */     
/*  716 */     if (typeSer != null) {
/*  717 */       typeSer = typeSer.forProperty(property);
/*  718 */       typeWrappedSerializer = new TypeWrappedSerializer(typeSer, ser);
/*      */     } 
/*  720 */     if (cache) {
/*  721 */       this._serializerCache.addTypedSerializer(valueType, (JsonSerializer)typeWrappedSerializer);
/*      */     }
/*  723 */     return (JsonSerializer<Object>)typeWrappedSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findTypedValueSerializer(JavaType valueType, boolean cache, BeanProperty property) throws JsonMappingException {
/*      */     TypeWrappedSerializer typeWrappedSerializer;
/*  747 */     JsonSerializer<Object> ser = this._knownSerializers.typedValueSerializer(valueType);
/*  748 */     if (ser != null) {
/*  749 */       return ser;
/*      */     }
/*      */     
/*  752 */     ser = this._serializerCache.typedValueSerializer(valueType);
/*  753 */     if (ser != null) {
/*  754 */       return ser;
/*      */     }
/*      */ 
/*      */     
/*  758 */     ser = findValueSerializer(valueType, property);
/*  759 */     TypeSerializer typeSer = this._serializerFactory.createTypeSerializer(this._config, valueType);
/*  760 */     if (typeSer != null) {
/*  761 */       typeSer = typeSer.forProperty(property);
/*  762 */       typeWrappedSerializer = new TypeWrappedSerializer(typeSer, ser);
/*      */     } 
/*  764 */     if (cache) {
/*  765 */       this._serializerCache.addTypedSerializer(valueType, (JsonSerializer)typeWrappedSerializer);
/*      */     }
/*  767 */     return (JsonSerializer<Object>)typeWrappedSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeSerializer findTypeSerializer(JavaType javaType) throws JsonMappingException {
/*  778 */     return this._serializerFactory.createTypeSerializer(this._config, javaType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findKeySerializer(JavaType keyType, BeanProperty property) throws JsonMappingException {
/*  794 */     JsonSerializer<Object> ser = this._serializerFactory.createKeySerializer(this._config, keyType, this._keySerializer);
/*      */     
/*  796 */     return _handleContextualResolvable(ser, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findKeySerializer(Class<?> rawKeyType, BeanProperty property) throws JsonMappingException {
/*  805 */     return findKeySerializer(this._config.constructType(rawKeyType), property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> getDefaultNullKeySerializer() {
/*  818 */     return this._nullKeySerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> getDefaultNullValueSerializer() {
/*  825 */     return this._nullValueSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findNullKeySerializer(JavaType serializationType, BeanProperty property) throws JsonMappingException {
/*  849 */     return this._nullKeySerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
/*  865 */     return this._nullValueSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> unknownType) {
/*  882 */     if (unknownType == Object.class) {
/*  883 */       return this._unknownTypeSerializer;
/*      */     }
/*      */     
/*  886 */     return (JsonSerializer<Object>)new UnknownSerializer(unknownType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUnknownTypeSerializer(JsonSerializer<?> ser) {
/*  897 */     if (ser == this._unknownTypeSerializer || ser == null) {
/*  898 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  902 */     if (isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS) && 
/*  903 */       ser.getClass() == UnknownSerializer.class) {
/*  904 */       return true;
/*      */     }
/*      */     
/*  907 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<?> handlePrimaryContextualization(JsonSerializer<?> ser, BeanProperty property) throws JsonMappingException {
/*  974 */     if (ser != null && 
/*  975 */       ser instanceof ContextualSerializer) {
/*  976 */       ser = ((ContextualSerializer)ser).createContextual(this, property);
/*      */     }
/*      */     
/*  979 */     return ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<?> handleSecondaryContextualization(JsonSerializer<?> ser, BeanProperty property) throws JsonMappingException {
/* 1002 */     if (ser != null && 
/* 1003 */       ser instanceof ContextualSerializer) {
/* 1004 */       ser = ((ContextualSerializer)ser).createContextual(this, property);
/*      */     }
/*      */     
/* 1007 */     return ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void defaultSerializeValue(Object value, JsonGenerator gen) throws IOException {
/* 1025 */     if (value == null) {
/* 1026 */       if (this._stdNullValueSerializer) {
/* 1027 */         gen.writeNull();
/*      */       } else {
/* 1029 */         this._nullValueSerializer.serialize(null, gen, this);
/*      */       } 
/*      */     } else {
/* 1032 */       Class<?> cls = value.getClass();
/* 1033 */       findTypedValueSerializer(cls, true, (BeanProperty)null).serialize(value, gen, this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void defaultSerializeField(String fieldName, Object value, JsonGenerator gen) throws IOException {
/* 1045 */     gen.writeFieldName(fieldName);
/* 1046 */     if (value == null) {
/*      */ 
/*      */ 
/*      */       
/* 1050 */       if (this._stdNullValueSerializer) {
/* 1051 */         gen.writeNull();
/*      */       } else {
/* 1053 */         this._nullValueSerializer.serialize(null, gen, this);
/*      */       } 
/*      */     } else {
/* 1056 */       Class<?> cls = value.getClass();
/* 1057 */       findTypedValueSerializer(cls, true, (BeanProperty)null).serialize(value, gen, this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void defaultSerializeDateValue(long timestamp, JsonGenerator gen) throws IOException {
/* 1071 */     if (isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
/* 1072 */       gen.writeNumber(timestamp);
/*      */     } else {
/* 1074 */       gen.writeString(_dateFormat().format(new Date(timestamp)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void defaultSerializeDateValue(Date date, JsonGenerator gen) throws IOException {
/* 1087 */     if (isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
/* 1088 */       gen.writeNumber(date.getTime());
/*      */     } else {
/* 1090 */       gen.writeString(_dateFormat().format(date));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void defaultSerializeDateKey(long timestamp, JsonGenerator gen) throws IOException {
/* 1101 */     if (isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
/* 1102 */       gen.writeFieldName(String.valueOf(timestamp));
/*      */     } else {
/* 1104 */       gen.writeFieldName(_dateFormat().format(new Date(timestamp)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void defaultSerializeDateKey(Date date, JsonGenerator gen) throws IOException {
/* 1115 */     if (isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
/* 1116 */       gen.writeFieldName(String.valueOf(date.getTime()));
/*      */     } else {
/* 1118 */       gen.writeFieldName(_dateFormat().format(date));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void defaultSerializeNull(JsonGenerator gen) throws IOException {
/* 1124 */     if (this._stdNullValueSerializer) {
/* 1125 */       gen.writeNull();
/*      */     } else {
/* 1127 */       this._nullValueSerializer.serialize(null, gen, this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportMappingProblem(String message, Object... args) throws JsonMappingException {
/* 1145 */     throw mappingException(message, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T reportBadTypeDefinition(BeanDescription bean, String msg, Object... msgArgs) throws JsonMappingException {
/* 1157 */     String beanDesc = "N/A";
/* 1158 */     if (bean != null) {
/* 1159 */       beanDesc = ClassUtil.nameOf(bean.getBeanClass());
/*      */     }
/* 1161 */     msg = String.format("Invalid type definition for type %s: %s", new Object[] { beanDesc, _format(msg, msgArgs) });
/*      */     
/* 1163 */     throw InvalidDefinitionException.from(getGenerator(), msg, bean, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T reportBadPropertyDefinition(BeanDescription bean, BeanPropertyDefinition prop, String message, Object... msgArgs) throws JsonMappingException {
/* 1175 */     message = _format(message, msgArgs);
/* 1176 */     String propName = "N/A";
/* 1177 */     if (prop != null) {
/* 1178 */       propName = _quotedString(prop.getName());
/*      */     }
/* 1180 */     String beanDesc = "N/A";
/* 1181 */     if (bean != null) {
/* 1182 */       beanDesc = ClassUtil.nameOf(bean.getBeanClass());
/*      */     }
/* 1184 */     message = String.format("Invalid definition for property %s (of type %s): %s", new Object[] { propName, beanDesc, message });
/*      */     
/* 1186 */     throw InvalidDefinitionException.from(getGenerator(), message, bean, prop);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> T reportBadDefinition(JavaType type, String msg) throws JsonMappingException {
/* 1191 */     throw InvalidDefinitionException.from(getGenerator(), msg, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T reportBadDefinition(JavaType type, String msg, Throwable cause) throws JsonMappingException {
/* 1199 */     InvalidDefinitionException e = InvalidDefinitionException.from(getGenerator(), msg, type);
/* 1200 */     e.initCause(cause);
/* 1201 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T reportBadDefinition(Class<?> raw, String msg, Throwable cause) throws JsonMappingException {
/* 1209 */     InvalidDefinitionException e = InvalidDefinitionException.from(getGenerator(), msg, constructType(raw));
/* 1210 */     e.initCause(cause);
/* 1211 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportMappingProblem(Throwable t, String message, Object... msgArgs) throws JsonMappingException {
/* 1222 */     message = _format(message, msgArgs);
/* 1223 */     throw JsonMappingException.from(getGenerator(), message, t);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonMappingException invalidTypeIdException(JavaType baseType, String typeId, String extraDesc) {
/* 1229 */     String msg = String.format("Could not resolve type id '%s' as a subtype of %s", new Object[] { typeId, baseType });
/*      */     
/* 1231 */     return (JsonMappingException)InvalidTypeIdException.from(null, _colonConcat(msg, extraDesc), baseType, typeId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonMappingException mappingException(String message, Object... msgArgs) {
/* 1251 */     return JsonMappingException.from(getGenerator(), _format(message, msgArgs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected JsonMappingException mappingException(Throwable t, String message, Object... msgArgs) {
/* 1265 */     return JsonMappingException.from(getGenerator(), _format(message, msgArgs), t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportIncompatibleRootType(Object value, JavaType rootType) throws IOException {
/* 1277 */     if (rootType.isPrimitive()) {
/* 1278 */       Class<?> wrapperType = ClassUtil.wrapperType(rootType.getRawClass());
/*      */       
/* 1280 */       if (wrapperType.isAssignableFrom(value.getClass())) {
/*      */         return;
/*      */       }
/*      */     } 
/* 1284 */     reportBadDefinition(rootType, String.format("Incompatible types: declared root type (%s) vs %s", new Object[] { rootType, ClassUtil.classNameOf(value) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _findExplicitUntypedSerializer(Class<?> runtimeType) throws JsonMappingException {
/* 1300 */     JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(runtimeType);
/* 1301 */     if (ser == null) {
/*      */       
/* 1303 */       ser = this._serializerCache.untypedValueSerializer(runtimeType);
/* 1304 */       if (ser == null) {
/* 1305 */         ser = _createAndCacheUntypedSerializer(runtimeType);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1313 */     if (isUnknownTypeSerializer(ser)) {
/* 1314 */       return null;
/*      */     }
/* 1316 */     return ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _createAndCacheUntypedSerializer(Class<?> rawType) throws JsonMappingException {
/*      */     JsonSerializer<Object> jsonSerializer;
/* 1333 */     JavaType fullType = this._config.constructType(rawType);
/*      */     
/*      */     try {
/* 1336 */       jsonSerializer = _createUntypedSerializer(fullType);
/* 1337 */     } catch (IllegalArgumentException iae) {
/*      */ 
/*      */ 
/*      */       
/* 1341 */       jsonSerializer = null;
/* 1342 */       reportMappingProblem(iae, iae.getMessage(), new Object[0]);
/*      */     } 
/*      */     
/* 1345 */     if (jsonSerializer != null)
/*      */     {
/* 1347 */       this._serializerCache.addAndResolveNonTypedSerializer(rawType, fullType, jsonSerializer, this);
/*      */     }
/* 1349 */     return jsonSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _createAndCacheUntypedSerializer(JavaType type) throws JsonMappingException {
/*      */     JsonSerializer<Object> jsonSerializer;
/*      */     try {
/* 1357 */       jsonSerializer = _createUntypedSerializer(type);
/* 1358 */     } catch (IllegalArgumentException iae) {
/*      */ 
/*      */       
/* 1361 */       jsonSerializer = null;
/* 1362 */       reportMappingProblem(iae, iae.getMessage(), new Object[0]);
/*      */     } 
/*      */     
/* 1365 */     if (jsonSerializer != null)
/*      */     {
/* 1367 */       this._serializerCache.addAndResolveNonTypedSerializer(type, jsonSerializer, this);
/*      */     }
/* 1369 */     return jsonSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _createUntypedSerializer(JavaType type) throws JsonMappingException {
/* 1383 */     synchronized (this._serializerCache) {
/*      */       
/* 1385 */       return this._serializerFactory.createSerializer(this, type);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _handleContextualResolvable(JsonSerializer<?> ser, BeanProperty property) throws JsonMappingException {
/* 1398 */     if (ser instanceof ResolvableSerializer) {
/* 1399 */       ((ResolvableSerializer)ser).resolve(this);
/*      */     }
/* 1401 */     return (JsonSerializer)handleSecondaryContextualization(ser, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _handleResolvable(JsonSerializer<?> ser) throws JsonMappingException {
/* 1408 */     if (ser instanceof ResolvableSerializer) {
/* 1409 */       ((ResolvableSerializer)ser).resolve(this);
/*      */     }
/* 1411 */     return (JsonSerializer)ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final DateFormat _dateFormat() {
/* 1422 */     if (this._dateFormat != null) {
/* 1423 */       return this._dateFormat;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1429 */     DateFormat df = this._config.getDateFormat();
/* 1430 */     this._dateFormat = df = (DateFormat)df.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1439 */     return df;
/*      */   }
/*      */   
/*      */   public abstract WritableObjectId findObjectId(Object paramObject, ObjectIdGenerator<?> paramObjectIdGenerator);
/*      */   
/*      */   public abstract JsonSerializer<Object> serializerInstance(Annotated paramAnnotated, Object paramObject) throws JsonMappingException;
/*      */   
/*      */   public abstract Object includeFilterInstance(BeanPropertyDefinition paramBeanPropertyDefinition, Class<?> paramClass) throws JsonMappingException;
/*      */   
/*      */   public abstract boolean includeFilterSuppressNulls(Object paramObject) throws JsonMappingException;
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\SerializerProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */