/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.EnumMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumMapDeserializer
/*     */   extends ContainerDeserializerBase<EnumMap<?, ?>>
/*     */   implements ContextualDeserializer, ResolvableDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<?> _enumClass;
/*     */   protected KeyDeserializer _keyDeserializer;
/*     */   protected JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected JsonDeserializer<Object> _delegateDeserializer;
/*     */   protected PropertyBasedCreator _propertyBasedCreator;
/*     */   
/*     */   public EnumMapDeserializer(JavaType mapType, ValueInstantiator valueInst, KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer vtd, NullValueProvider nuller) {
/*  77 */     super(mapType, nuller, (Boolean)null);
/*  78 */     this._enumClass = mapType.getKeyType().getRawClass();
/*  79 */     this._keyDeserializer = keyDeser;
/*  80 */     this._valueDeserializer = (JsonDeserializer)valueDeser;
/*  81 */     this._valueTypeDeserializer = vtd;
/*  82 */     this._valueInstantiator = valueInst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumMapDeserializer(EnumMapDeserializer base, KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer vtd, NullValueProvider nuller) {
/*  92 */     super(base, nuller, base._unwrapSingle);
/*  93 */     this._enumClass = base._enumClass;
/*  94 */     this._keyDeserializer = keyDeser;
/*  95 */     this._valueDeserializer = (JsonDeserializer)valueDeser;
/*  96 */     this._valueTypeDeserializer = vtd;
/*     */     
/*  98 */     this._valueInstantiator = base._valueInstantiator;
/*  99 */     this._delegateDeserializer = base._delegateDeserializer;
/* 100 */     this._propertyBasedCreator = base._propertyBasedCreator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EnumMapDeserializer(JavaType mapType, KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer vtd) {
/* 107 */     this(mapType, (ValueInstantiator)null, keyDeser, valueDeser, vtd, (NullValueProvider)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumMapDeserializer withResolved(KeyDeserializer keyDeserializer, JsonDeserializer<?> valueDeserializer, TypeDeserializer valueTypeDeser, NullValueProvider nuller) {
/* 114 */     if (keyDeserializer == this._keyDeserializer && nuller == this._nullProvider && valueDeserializer == this._valueDeserializer && valueTypeDeser == this._valueTypeDeserializer)
/*     */     {
/* 116 */       return this;
/*     */     }
/* 118 */     return new EnumMapDeserializer(this, keyDeserializer, valueDeserializer, valueTypeDeser, nuller);
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
/*     */   public void resolve(DeserializationContext ctxt) throws JsonMappingException {
/* 132 */     if (this._valueInstantiator != null) {
/* 133 */       if (this._valueInstantiator.canCreateUsingDelegate()) {
/* 134 */         JavaType delegateType = this._valueInstantiator.getDelegateType(ctxt.getConfig());
/* 135 */         if (delegateType == null) {
/* 136 */           ctxt.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", new Object[] { this._containerType, this._valueInstantiator.getClass().getName() }));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         this._delegateDeserializer = findDeserializer(ctxt, delegateType, null);
/* 146 */       } else if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
/* 147 */         JavaType delegateType = this._valueInstantiator.getArrayDelegateType(ctxt.getConfig());
/* 148 */         if (delegateType == null) {
/* 149 */           ctxt.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", new Object[] { this._containerType, this._valueInstantiator.getClass().getName() }));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 154 */         this._delegateDeserializer = findDeserializer(ctxt, delegateType, null);
/* 155 */       } else if (this._valueInstantiator.canCreateFromObjectWith()) {
/* 156 */         SettableBeanProperty[] creatorProps = this._valueInstantiator.getFromObjectArguments(ctxt.getConfig());
/* 157 */         this._propertyBasedCreator = PropertyBasedCreator.construct(ctxt, this._valueInstantiator, creatorProps, ctxt.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
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
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 173 */     KeyDeserializer keyDeser = this._keyDeserializer;
/* 174 */     if (keyDeser == null) {
/* 175 */       keyDeser = ctxt.findKeyDeserializer(this._containerType.getKeyType(), property);
/*     */     }
/* 177 */     JsonDeserializer<?> valueDeser = this._valueDeserializer;
/* 178 */     JavaType vt = this._containerType.getContentType();
/* 179 */     if (valueDeser == null) {
/* 180 */       valueDeser = ctxt.findContextualValueDeserializer(vt, property);
/*     */     } else {
/* 182 */       valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, vt);
/*     */     } 
/* 184 */     TypeDeserializer vtd = this._valueTypeDeserializer;
/* 185 */     if (vtd != null) {
/* 186 */       vtd = vtd.forProperty(property);
/*     */     }
/* 188 */     return withResolved(keyDeser, valueDeser, vtd, findContentNullProvider(ctxt, property, valueDeser));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachable() {
/* 198 */     return (this._valueDeserializer == null && this._keyDeserializer == null && this._valueTypeDeserializer == null);
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
/*     */   public JsonDeserializer<Object> getContentDeserializer() {
/* 211 */     return this._valueDeserializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/* 217 */     return constructMap(ctxt);
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
/*     */   public EnumMap<?, ?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 230 */     if (this._propertyBasedCreator != null) {
/* 231 */       return _deserializeUsingProperties(p, ctxt);
/*     */     }
/* 233 */     if (this._delegateDeserializer != null) {
/* 234 */       return (EnumMap<?, ?>)this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
/*     */     }
/*     */ 
/*     */     
/* 238 */     JsonToken t = p.getCurrentToken();
/* 239 */     if (t != JsonToken.START_OBJECT && t != JsonToken.FIELD_NAME && t != JsonToken.END_OBJECT) {
/*     */       
/* 241 */       if (t == JsonToken.VALUE_STRING) {
/* 242 */         return (EnumMap<?, ?>)this._valueInstantiator.createFromString(ctxt, p.getText());
/*     */       }
/*     */       
/* 245 */       return _deserializeFromEmpty(p, ctxt);
/*     */     } 
/* 247 */     EnumMap<?, ?> result = constructMap(ctxt);
/* 248 */     return deserialize(p, ctxt, result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumMap<?, ?> deserialize(JsonParser p, DeserializationContext ctxt, EnumMap<Enum<?>, Object> result) throws IOException {
/* 257 */     p.setCurrentValue(result);
/*     */     
/* 259 */     JsonDeserializer<Object> valueDes = this._valueDeserializer;
/* 260 */     TypeDeserializer typeDeser = this._valueTypeDeserializer;
/*     */     
/*     */     String keyName;
/* 263 */     while ((keyName = p.nextFieldName()) != null) {
/*     */       Object value;
/* 265 */       Enum<?> key = (Enum)this._keyDeserializer.deserializeKey(keyName, ctxt);
/* 266 */       if (key == null) {
/* 267 */         if (!ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 268 */           return (EnumMap<?, ?>)ctxt.handleWeirdStringValue(this._enumClass, keyName, "value not one of declared Enum instance names for %s", new Object[] { this._containerType.getKeyType() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 274 */         p.nextToken();
/* 275 */         p.skipChildren();
/*     */         
/*     */         continue;
/*     */       } 
/* 279 */       JsonToken t = p.nextToken();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 285 */         if (t == JsonToken.VALUE_NULL) {
/* 286 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 289 */           value = this._nullProvider.getNullValue(ctxt);
/* 290 */         } else if (typeDeser == null) {
/* 291 */           value = valueDes.deserialize(p, ctxt);
/*     */         } else {
/* 293 */           value = valueDes.deserializeWithType(p, ctxt, typeDeser);
/*     */         } 
/* 295 */       } catch (Exception e) {
/* 296 */         return (EnumMap<?, ?>)wrapAndThrow(e, result, keyName);
/*     */       } 
/* 298 */       result.put(key, value);
/*     */     } 
/* 300 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 309 */     return typeDeserializer.deserializeTypedFromObject(p, ctxt);
/*     */   }
/*     */   
/*     */   protected EnumMap<?, ?> constructMap(DeserializationContext ctxt) throws JsonMappingException {
/* 313 */     if (this._valueInstantiator == null) {
/* 314 */       return new EnumMap<>(this._enumClass);
/*     */     }
/*     */     try {
/* 317 */       if (!this._valueInstantiator.canCreateUsingDefault()) {
/* 318 */         return (EnumMap<?, ?>)ctxt.handleMissingInstantiator(handledType(), getValueInstantiator(), null, "no default constructor found", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 322 */       return (EnumMap<?, ?>)this._valueInstantiator.createUsingDefault(ctxt);
/* 323 */     } catch (IOException e) {
/* 324 */       return (EnumMap<?, ?>)ClassUtil.throwAsMappingException(ctxt, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumMap<?, ?> _deserializeUsingProperties(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 330 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/*     */     
/* 332 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, null);
/*     */ 
/*     */     
/* 335 */     if (p.isExpectedStartObjectToken()) {
/* 336 */       str = p.nextFieldName();
/* 337 */     } else if (p.hasToken(JsonToken.FIELD_NAME)) {
/* 338 */       str = p.getCurrentName();
/*     */     } else {
/* 340 */       str = null;
/*     */     } 
/*     */     String str;
/* 343 */     for (; str != null; str = p.nextFieldName()) {
/* 344 */       Object value; JsonToken t = p.nextToken();
/*     */       
/* 346 */       SettableBeanProperty prop = creator.findCreatorProperty(str);
/* 347 */       if (prop != null) {
/*     */         
/* 349 */         if (buffer.assignParameter(prop, prop.deserialize(p, ctxt))) {
/*     */           EnumMap<?, ?> result;
/*     */           try {
/* 352 */             result = (EnumMap<?, ?>)creator.build(ctxt, buffer);
/* 353 */           } catch (Exception e) {
/* 354 */             return (EnumMap<?, ?>)wrapAndThrow(e, this._containerType.getRawClass(), str);
/*     */           } 
/* 356 */           return deserialize(p, ctxt, result);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 362 */       Enum<?> key = (Enum)this._keyDeserializer.deserializeKey(str, ctxt);
/* 363 */       if (key == null) {
/* 364 */         if (!ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 365 */           return (EnumMap<?, ?>)ctxt.handleWeirdStringValue(this._enumClass, str, "value not one of declared Enum instance names for %s", new Object[] { this._containerType.getKeyType() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 371 */         p.nextToken();
/* 372 */         p.skipChildren();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       try {
/* 378 */         if (t == JsonToken.VALUE_NULL) {
/* 379 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 382 */           value = this._nullProvider.getNullValue(ctxt);
/* 383 */         } else if (this._valueTypeDeserializer == null) {
/* 384 */           value = this._valueDeserializer.deserialize(p, ctxt);
/*     */         } else {
/* 386 */           value = this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */         } 
/* 388 */       } catch (Exception e) {
/* 389 */         wrapAndThrow(e, this._containerType.getRawClass(), str);
/* 390 */         return null;
/*     */       } 
/* 392 */       buffer.bufferMapProperty(key, value);
/*     */       
/*     */       continue;
/*     */     } 
/*     */     try {
/* 397 */       return (EnumMap<?, ?>)creator.build(ctxt, buffer);
/* 398 */     } catch (Exception e) {
/* 399 */       wrapAndThrow(e, this._containerType.getRawClass(), str);
/* 400 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\EnumMapDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */