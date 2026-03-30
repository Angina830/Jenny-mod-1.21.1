/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public final class StringCollectionDeserializer
/*     */   extends ContainerDeserializerBase<Collection<String>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JsonDeserializer<String> _valueDeserializer;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final JsonDeserializer<Object> _delegateDeserializer;
/*     */   
/*     */   public StringCollectionDeserializer(JavaType collectionType, JsonDeserializer<?> valueDeser, ValueInstantiator valueInstantiator) {
/*  60 */     this(collectionType, valueInstantiator, (JsonDeserializer<?>)null, valueDeser, (NullValueProvider)valueDeser, (Boolean)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringCollectionDeserializer(JavaType collectionType, ValueInstantiator valueInstantiator, JsonDeserializer<?> delegateDeser, JsonDeserializer<?> valueDeser, NullValueProvider nuller, Boolean unwrapSingle) {
/*  69 */     super(collectionType, nuller, unwrapSingle);
/*  70 */     this._valueDeserializer = (JsonDeserializer)valueDeser;
/*  71 */     this._valueInstantiator = valueInstantiator;
/*  72 */     this._delegateDeserializer = (JsonDeserializer)delegateDeser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringCollectionDeserializer withResolved(JsonDeserializer<?> delegateDeser, JsonDeserializer<?> valueDeser, NullValueProvider nuller, Boolean unwrapSingle) {
/*  79 */     if (this._unwrapSingle == unwrapSingle && this._nullProvider == nuller && this._valueDeserializer == valueDeser && this._delegateDeserializer == delegateDeser)
/*     */     {
/*  81 */       return this;
/*     */     }
/*  83 */     return new StringCollectionDeserializer(this._containerType, this._valueInstantiator, delegateDeser, valueDeser, nuller, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachable() {
/*  91 */     return (this._valueDeserializer == null && this._delegateDeserializer == null);
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
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 104 */     JsonDeserializer<Object> delegate = null;
/* 105 */     if (this._valueInstantiator != null) {
/* 106 */       AnnotatedWithParams delegateCreator = this._valueInstantiator.getDelegateCreator();
/* 107 */       if (delegateCreator != null) {
/* 108 */         JavaType delegateType = this._valueInstantiator.getDelegateType(ctxt.getConfig());
/* 109 */         delegate = findDeserializer(ctxt, delegateType, property);
/*     */       } 
/*     */     } 
/* 112 */     JsonDeserializer<?> valueDeser = this._valueDeserializer;
/* 113 */     JavaType valueType = this._containerType.getContentType();
/* 114 */     if (valueDeser == null) {
/*     */       
/* 116 */       valueDeser = findConvertingContentDeserializer(ctxt, property, valueDeser);
/* 117 */       if (valueDeser == null)
/*     */       {
/* 119 */         valueDeser = ctxt.findContextualValueDeserializer(valueType, property);
/*     */       }
/*     */     } else {
/* 122 */       valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, valueType);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     Boolean unwrapSingle = findFormatFeature(ctxt, property, Collection.class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
/*     */     
/* 128 */     NullValueProvider nuller = findContentNullProvider(ctxt, property, valueDeser);
/* 129 */     if (isDefaultDeserializer(valueDeser)) {
/* 130 */       valueDeser = null;
/*     */     }
/* 132 */     return withResolved(delegate, valueDeser, nuller, unwrapSingle);
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
/*     */   public JsonDeserializer<Object> getContentDeserializer() {
/* 144 */     JsonDeserializer<?> deser = this._valueDeserializer;
/* 145 */     return (JsonDeserializer)deser;
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueInstantiator getValueInstantiator() {
/* 150 */     return this._valueInstantiator;
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
/*     */   public Collection<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 164 */     if (this._delegateDeserializer != null) {
/* 165 */       return (Collection<String>)this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
/*     */     }
/*     */     
/* 168 */     Collection<String> result = (Collection<String>)this._valueInstantiator.createUsingDefault(ctxt);
/* 169 */     return deserialize(p, ctxt, result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> deserialize(JsonParser p, DeserializationContext ctxt, Collection<String> result) throws IOException {
/* 178 */     if (!p.isExpectedStartArrayToken()) {
/* 179 */       return handleNonArray(p, ctxt, result);
/*     */     }
/*     */     
/* 182 */     if (this._valueDeserializer != null) {
/* 183 */       return deserializeUsingCustom(p, ctxt, result, this._valueDeserializer);
/*     */     }
/*     */     
/*     */     try {
/*     */       while (true) {
/* 188 */         String value = p.nextTextValue();
/* 189 */         if (value != null) {
/* 190 */           result.add(value);
/*     */           continue;
/*     */         } 
/* 193 */         JsonToken t = p.getCurrentToken();
/* 194 */         if (t == JsonToken.END_ARRAY) {
/*     */           break;
/*     */         }
/* 197 */         if (t == JsonToken.VALUE_NULL) {
/* 198 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 201 */           value = (String)this._nullProvider.getNullValue(ctxt);
/*     */         } else {
/* 203 */           value = _parseString(p, ctxt);
/*     */         } 
/* 205 */         result.add(value);
/*     */       } 
/* 207 */     } catch (Exception e) {
/* 208 */       throw JsonMappingException.wrapWithPath(e, result, result.size());
/*     */     } 
/* 210 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<String> deserializeUsingCustom(JsonParser p, DeserializationContext ctxt, Collection<String> result, JsonDeserializer<String> deser) throws IOException {
/*     */     while (true) {
/*     */       String value;
/* 223 */       if (p.nextTextValue() == null) {
/* 224 */         JsonToken t = p.getCurrentToken();
/* 225 */         if (t == JsonToken.END_ARRAY) {
/*     */           break;
/*     */         }
/*     */         
/* 229 */         if (t == JsonToken.VALUE_NULL) {
/* 230 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 233 */           value = (String)this._nullProvider.getNullValue(ctxt);
/*     */         } else {
/* 235 */           value = (String)deser.deserialize(p, ctxt);
/*     */         } 
/*     */       } else {
/* 238 */         value = (String)deser.deserialize(p, ctxt);
/*     */       } 
/* 240 */       result.add(value);
/*     */     } 
/* 242 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 249 */     return typeDeserializer.deserializeTypedFromArray(p, ctxt);
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
/*     */   private final Collection<String> handleNonArray(JsonParser p, DeserializationContext ctxt, Collection<String> result) throws IOException {
/*     */     String value;
/* 262 */     boolean canWrap = (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
/*     */ 
/*     */     
/* 265 */     if (!canWrap) {
/* 266 */       return (Collection<String>)ctxt.handleUnexpectedToken(this._containerType.getRawClass(), p);
/*     */     }
/*     */     
/* 269 */     JsonDeserializer<String> valueDes = this._valueDeserializer;
/* 270 */     JsonToken t = p.getCurrentToken();
/*     */ 
/*     */ 
/*     */     
/* 274 */     if (t == JsonToken.VALUE_NULL) {
/*     */       
/* 276 */       if (this._skipNullValues) {
/* 277 */         return result;
/*     */       }
/* 279 */       value = (String)this._nullProvider.getNullValue(ctxt);
/*     */     } else {
/* 281 */       value = (valueDes == null) ? _parseString(p, ctxt) : (String)valueDes.deserialize(p, ctxt);
/*     */     } 
/* 283 */     result.add(value);
/* 284 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StringCollectionDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */