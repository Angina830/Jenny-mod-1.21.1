/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.BeanUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class MapEntrySerializer
/*     */   extends ContainerSerializer<Map.Entry<?, ?>>
/*     */   implements ContextualSerializer
/*     */ {
/*  33 */   public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanProperty _property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _valueTypeIsStatic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _entryType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _keyType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _valueType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonSerializer<Object> _keySerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonSerializer<Object> _valueSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PropertySerializerMap _dynamicValueSerializers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object _suppressableValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _suppressNulls;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapEntrySerializer(JavaType type, JavaType keyType, JavaType valueType, boolean staticTyping, TypeSerializer vts, BeanProperty property) {
/* 111 */     super(type);
/* 112 */     this._entryType = type;
/* 113 */     this._keyType = keyType;
/* 114 */     this._valueType = valueType;
/* 115 */     this._valueTypeIsStatic = staticTyping;
/* 116 */     this._valueTypeSerializer = vts;
/* 117 */     this._property = property;
/* 118 */     this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
/* 119 */     this._suppressableValue = null;
/* 120 */     this._suppressNulls = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected MapEntrySerializer(MapEntrySerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> keySer, JsonSerializer<?> valueSer) {
/* 128 */     this(src, property, vts, keySer, valueSer, src._suppressableValue, src._suppressNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapEntrySerializer(MapEntrySerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> keySer, JsonSerializer<?> valueSer, Object suppressableValue, boolean suppressNulls) {
/* 138 */     super(Map.class, false);
/* 139 */     this._entryType = src._entryType;
/* 140 */     this._keyType = src._keyType;
/* 141 */     this._valueType = src._valueType;
/* 142 */     this._valueTypeIsStatic = src._valueTypeIsStatic;
/* 143 */     this._valueTypeSerializer = src._valueTypeSerializer;
/* 144 */     this._keySerializer = (JsonSerializer)keySer;
/* 145 */     this._valueSerializer = (JsonSerializer)valueSer;
/* 146 */     this._dynamicValueSerializers = src._dynamicValueSerializers;
/* 147 */     this._property = src._property;
/* 148 */     this._suppressableValue = suppressableValue;
/* 149 */     this._suppressNulls = suppressNulls;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/* 154 */     return new MapEntrySerializer(this, this._property, vts, this._keySerializer, this._valueSerializer, this._suppressableValue, this._suppressNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapEntrySerializer withResolved(BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Object suppressableValue, boolean suppressNulls) {
/* 164 */     return new MapEntrySerializer(this, property, this._valueTypeSerializer, keySerializer, valueSerializer, suppressableValue, suppressNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapEntrySerializer withContentInclusion(Object suppressableValue, boolean suppressNulls) {
/* 173 */     if (this._suppressableValue == suppressableValue && this._suppressNulls == suppressNulls)
/*     */     {
/* 175 */       return this;
/*     */     }
/* 177 */     return new MapEntrySerializer(this, this._property, this._valueTypeSerializer, this._keySerializer, this._valueSerializer, suppressableValue, suppressNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/* 185 */     JsonSerializer<?> ser = null;
/* 186 */     JsonSerializer<?> keySer = null;
/* 187 */     AnnotationIntrospector intr = provider.getAnnotationIntrospector();
/* 188 */     AnnotatedMember propertyAcc = (property == null) ? null : property.getMember();
/*     */ 
/*     */     
/* 191 */     if (propertyAcc != null && intr != null) {
/* 192 */       Object serDef = intr.findKeySerializer((Annotated)propertyAcc);
/* 193 */       if (serDef != null) {
/* 194 */         keySer = provider.serializerInstance((Annotated)propertyAcc, serDef);
/*     */       }
/* 196 */       serDef = intr.findContentSerializer((Annotated)propertyAcc);
/* 197 */       if (serDef != null) {
/* 198 */         ser = provider.serializerInstance((Annotated)propertyAcc, serDef);
/*     */       }
/*     */     } 
/* 201 */     if (ser == null) {
/* 202 */       ser = this._valueSerializer;
/*     */     }
/*     */     
/* 205 */     ser = findContextualConvertingSerializer(provider, property, ser);
/* 206 */     if (ser == null)
/*     */     {
/*     */ 
/*     */       
/* 210 */       if (this._valueTypeIsStatic && !this._valueType.isJavaLangObject()) {
/* 211 */         ser = provider.findValueSerializer(this._valueType, property);
/*     */       }
/*     */     }
/* 214 */     if (keySer == null) {
/* 215 */       keySer = this._keySerializer;
/*     */     }
/* 217 */     if (keySer == null) {
/* 218 */       keySer = provider.findKeySerializer(this._keyType, property);
/*     */     } else {
/* 220 */       keySer = provider.handleSecondaryContextualization(keySer, property);
/*     */     } 
/*     */     
/* 223 */     Object valueToSuppress = this._suppressableValue;
/* 224 */     boolean suppressNulls = this._suppressNulls;
/* 225 */     if (property != null) {
/* 226 */       JsonInclude.Value inclV = property.findPropertyInclusion((MapperConfig)provider.getConfig(), null);
/* 227 */       if (inclV != null) {
/* 228 */         JsonInclude.Include incl = inclV.getContentInclusion();
/* 229 */         if (incl != JsonInclude.Include.USE_DEFAULTS) {
/* 230 */           MapEntrySerializer mser; switch (incl) {
/*     */             case NON_DEFAULT:
/* 232 */               valueToSuppress = BeanUtil.getDefaultValue(this._valueType);
/* 233 */               suppressNulls = true;
/* 234 */               if (valueToSuppress != null && 
/* 235 */                 valueToSuppress.getClass().isArray()) {
/* 236 */                 valueToSuppress = ArrayBuilders.getArrayComparator(valueToSuppress);
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 272 */               mser = withResolved(property, keySer, ser, valueToSuppress, suppressNulls);
/*     */ 
/*     */               
/* 275 */               return (JsonSerializer<?>)mser;case NON_ABSENT: suppressNulls = true; valueToSuppress = this._valueType.isReferenceType() ? MARKER_FOR_EMPTY : null; mser = withResolved(property, keySer, ser, valueToSuppress, suppressNulls); return (JsonSerializer<?>)mser;case NON_EMPTY: suppressNulls = true; valueToSuppress = MARKER_FOR_EMPTY; mser = withResolved(property, keySer, ser, valueToSuppress, suppressNulls); return (JsonSerializer<?>)mser;case CUSTOM: valueToSuppress = provider.includeFilterInstance(null, mser.getContentFilter()); if (valueToSuppress == null) { suppressNulls = true; } else { suppressNulls = provider.includeFilterSuppressNulls(valueToSuppress); }  mser = withResolved(property, keySer, ser, valueToSuppress, suppressNulls); return (JsonSerializer<?>)mser;case NON_NULL: valueToSuppress = null; suppressNulls = true; mser = withResolved(property, keySer, ser, valueToSuppress, suppressNulls); return (JsonSerializer<?>)mser;
/*     */           } 
/*     */           valueToSuppress = null;
/*     */           suppressNulls = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     return (JsonSerializer<?>)withResolved(property, keySer, ser, valueToSuppress, suppressNulls);
/*     */   }
/*     */   
/*     */   public JavaType getContentType() {
/* 286 */     return this._valueType;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer() {
/* 291 */     return this._valueSerializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSingleElement(Map.Entry<?, ?> value) {
/* 296 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, Map.Entry<?, ?> entry) {
/* 302 */     Object value = entry.getValue();
/* 303 */     if (value == null) {
/* 304 */       return this._suppressNulls;
/*     */     }
/* 306 */     if (this._suppressableValue == null) {
/* 307 */       return false;
/*     */     }
/* 309 */     JsonSerializer<Object> valueSer = this._valueSerializer;
/* 310 */     if (valueSer == null) {
/*     */ 
/*     */       
/* 313 */       Class<?> cc = value.getClass();
/* 314 */       valueSer = this._dynamicValueSerializers.serializerFor(cc.getClass());
/* 315 */       if (valueSer == null) {
/*     */         try {
/* 317 */           valueSer = _findAndAddDynamic(this._dynamicValueSerializers, cc, prov);
/* 318 */         } catch (JsonMappingException e) {
/* 319 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/* 323 */     if (this._suppressableValue == MARKER_FOR_EMPTY) {
/* 324 */       return valueSer.isEmpty(prov, value);
/*     */     }
/* 326 */     return this._suppressableValue.equals(value);
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
/*     */   public void serialize(Map.Entry<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 339 */     gen.writeStartObject(value);
/* 340 */     serializeDynamic(value, gen, provider);
/* 341 */     gen.writeEndObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(Map.Entry<?, ?> value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 349 */     g.setCurrentValue(value);
/* 350 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_OBJECT));
/*     */     
/* 352 */     serializeDynamic(value, g, provider);
/* 353 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeDynamic(Map.Entry<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*     */     JsonSerializer<Object> keySerializer, valueSer;
/* 360 */     TypeSerializer vts = this._valueTypeSerializer;
/* 361 */     Object keyElem = value.getKey();
/*     */ 
/*     */     
/* 364 */     if (keyElem == null) {
/* 365 */       keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
/*     */     } else {
/* 367 */       keySerializer = this._keySerializer;
/*     */     } 
/*     */     
/* 370 */     Object valueElem = value.getValue();
/*     */ 
/*     */     
/* 373 */     if (valueElem == null) {
/* 374 */       if (this._suppressNulls) {
/*     */         return;
/*     */       }
/* 377 */       valueSer = provider.getDefaultNullValueSerializer();
/*     */     } else {
/* 379 */       valueSer = this._valueSerializer;
/* 380 */       if (valueSer == null) {
/* 381 */         Class<?> cc = valueElem.getClass();
/* 382 */         valueSer = this._dynamicValueSerializers.serializerFor(cc);
/* 383 */         if (valueSer == null) {
/* 384 */           if (this._valueType.hasGenericTypes()) {
/* 385 */             valueSer = _findAndAddDynamic(this._dynamicValueSerializers, provider.constructSpecializedType(this._valueType, cc), provider);
/*     */           } else {
/*     */             
/* 388 */             valueSer = _findAndAddDynamic(this._dynamicValueSerializers, cc, provider);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 393 */       if (this._suppressableValue != null) {
/* 394 */         if (this._suppressableValue == MARKER_FOR_EMPTY && 
/* 395 */           valueSer.isEmpty(provider, valueElem)) {
/*     */           return;
/*     */         }
/* 398 */         if (this._suppressableValue.equals(valueElem)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 403 */     keySerializer.serialize(keyElem, gen, provider);
/*     */     try {
/* 405 */       if (vts == null) {
/* 406 */         valueSer.serialize(valueElem, gen, provider);
/*     */       } else {
/* 408 */         valueSer.serializeWithType(valueElem, gen, provider, vts);
/*     */       } 
/* 410 */     } catch (Exception e) {
/* 411 */       String keyDesc = "" + keyElem;
/* 412 */       wrapAndThrow(provider, e, value, keyDesc);
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
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
/* 425 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/* 426 */     if (map != result.map) {
/* 427 */       this._dynamicValueSerializers = result.map;
/*     */     }
/* 429 */     return result.serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
/* 435 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/* 436 */     if (map != result.map) {
/* 437 */       this._dynamicValueSerializers = result.map;
/*     */     }
/* 439 */     return result.serializer;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\MapEntrySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */