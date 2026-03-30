/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
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
/*     */ @JacksonStdImpl
/*     */ public class CollectionDeserializer
/*     */   extends ContainerDeserializerBase<Collection<Object>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = -1L;
/*     */   protected final JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final JsonDeserializer<Object> _delegateDeserializer;
/*     */   
/*     */   public CollectionDeserializer(JavaType collectionType, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser, ValueInstantiator valueInstantiator) {
/*  73 */     this(collectionType, valueDeser, valueTypeDeser, valueInstantiator, (JsonDeserializer<Object>)null, (NullValueProvider)null, (Boolean)null);
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
/*     */   protected CollectionDeserializer(JavaType collectionType, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser, ValueInstantiator valueInstantiator, JsonDeserializer<Object> delegateDeser, NullValueProvider nuller, Boolean unwrapSingle) {
/*  86 */     super(collectionType, nuller, unwrapSingle);
/*  87 */     this._valueDeserializer = valueDeser;
/*  88 */     this._valueTypeDeserializer = valueTypeDeser;
/*  89 */     this._valueInstantiator = valueInstantiator;
/*  90 */     this._delegateDeserializer = delegateDeser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CollectionDeserializer(CollectionDeserializer src) {
/*  99 */     super(src);
/* 100 */     this._valueDeserializer = src._valueDeserializer;
/* 101 */     this._valueTypeDeserializer = src._valueTypeDeserializer;
/* 102 */     this._valueInstantiator = src._valueInstantiator;
/* 103 */     this._delegateDeserializer = src._delegateDeserializer;
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
/*     */   protected CollectionDeserializer withResolved(JsonDeserializer<?> dd, JsonDeserializer<?> vd, TypeDeserializer vtd, NullValueProvider nuller, Boolean unwrapSingle) {
/* 116 */     return new CollectionDeserializer(this._containerType, (JsonDeserializer)vd, vtd, this._valueInstantiator, (JsonDeserializer)dd, nuller, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachable() {
/* 126 */     return (this._valueDeserializer == null && this._valueTypeDeserializer == null && this._delegateDeserializer == null);
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
/*     */   public CollectionDeserializer createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 148 */     JsonDeserializer<Object> delegateDeser = null;
/* 149 */     if (this._valueInstantiator != null) {
/* 150 */       if (this._valueInstantiator.canCreateUsingDelegate()) {
/* 151 */         JavaType delegateType = this._valueInstantiator.getDelegateType(ctxt.getConfig());
/* 152 */         if (delegateType == null) {
/* 153 */           ctxt.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", new Object[] { this._containerType, this._valueInstantiator.getClass().getName() }));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 158 */         delegateDeser = findDeserializer(ctxt, delegateType, property);
/* 159 */       } else if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
/* 160 */         JavaType delegateType = this._valueInstantiator.getArrayDelegateType(ctxt.getConfig());
/* 161 */         if (delegateType == null) {
/* 162 */           ctxt.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", new Object[] { this._containerType, this._valueInstantiator.getClass().getName() }));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 167 */         delegateDeser = findDeserializer(ctxt, delegateType, property);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 173 */     Boolean unwrapSingle = findFormatFeature(ctxt, property, Collection.class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
/*     */ 
/*     */     
/* 176 */     JsonDeserializer<?> valueDeser = this._valueDeserializer;
/*     */ 
/*     */     
/* 179 */     valueDeser = findConvertingContentDeserializer(ctxt, property, valueDeser);
/* 180 */     JavaType vt = this._containerType.getContentType();
/* 181 */     if (valueDeser == null) {
/* 182 */       valueDeser = ctxt.findContextualValueDeserializer(vt, property);
/*     */     } else {
/* 184 */       valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, vt);
/*     */     } 
/*     */     
/* 187 */     TypeDeserializer valueTypeDeser = this._valueTypeDeserializer;
/* 188 */     if (valueTypeDeser != null) {
/* 189 */       valueTypeDeser = valueTypeDeser.forProperty(property);
/*     */     }
/* 191 */     NullValueProvider nuller = findContentNullProvider(ctxt, property, valueDeser);
/* 192 */     if (unwrapSingle != this._unwrapSingle || nuller != this._nullProvider || delegateDeser != this._delegateDeserializer || valueDeser != this._valueDeserializer || valueTypeDeser != this._valueTypeDeserializer)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       return withResolved(delegateDeser, valueDeser, valueTypeDeser, nuller, unwrapSingle);
/*     */     }
/*     */     
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> getContentDeserializer() {
/* 212 */     return this._valueDeserializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueInstantiator getValueInstantiator() {
/* 217 */     return this._valueInstantiator;
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
/*     */   public Collection<Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 231 */     if (this._delegateDeserializer != null) {
/* 232 */       return (Collection<Object>)this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     if (p.hasToken(JsonToken.VALUE_STRING)) {
/* 239 */       String str = p.getText();
/* 240 */       if (str.length() == 0) {
/* 241 */         return (Collection<Object>)this._valueInstantiator.createFromString(ctxt, str);
/*     */       }
/*     */     } 
/* 244 */     return deserialize(p, ctxt, createDefaultInstance(ctxt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collection<Object> createDefaultInstance(DeserializationContext ctxt) throws IOException {
/* 254 */     return (Collection<Object>)this._valueInstantiator.createUsingDefault(ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Object> deserialize(JsonParser p, DeserializationContext ctxt, Collection<Object> result) throws IOException {
/* 263 */     if (!p.isExpectedStartArrayToken()) {
/* 264 */       return handleNonArray(p, ctxt, result);
/*     */     }
/*     */     
/* 267 */     p.setCurrentValue(result);
/*     */     
/* 269 */     JsonDeserializer<Object> valueDes = this._valueDeserializer;
/* 270 */     TypeDeserializer typeDeser = this._valueTypeDeserializer;
/* 271 */     CollectionReferringAccumulator referringAccumulator = (valueDes.getObjectIdReader() == null) ? null : new CollectionReferringAccumulator(this._containerType.getContentType().getRawClass(), result);
/*     */ 
/*     */     
/*     */     JsonToken t;
/*     */     
/* 276 */     while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */       try {
/*     */         Object value;
/* 279 */         if (t == JsonToken.VALUE_NULL) {
/* 280 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 283 */           value = this._nullProvider.getNullValue(ctxt);
/* 284 */         } else if (typeDeser == null) {
/* 285 */           value = valueDes.deserialize(p, ctxt);
/*     */         } else {
/* 287 */           value = valueDes.deserializeWithType(p, ctxt, typeDeser);
/*     */         } 
/* 289 */         if (referringAccumulator != null) {
/* 290 */           referringAccumulator.add(value); continue;
/*     */         } 
/* 292 */         result.add(value);
/*     */       }
/* 294 */       catch (UnresolvedForwardReference reference) {
/* 295 */         if (referringAccumulator == null) {
/* 296 */           throw JsonMappingException.from(p, "Unresolved forward reference but no identity info", reference);
/*     */         }
/*     */         
/* 299 */         ReadableObjectId.Referring ref = referringAccumulator.handleUnresolvedReference(reference);
/* 300 */         reference.getRoid().appendReferring(ref);
/* 301 */       } catch (Exception e) {
/* 302 */         boolean wrap = (ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS));
/* 303 */         if (!wrap) {
/* 304 */           ClassUtil.throwIfRTE(e);
/*     */         }
/* 306 */         throw JsonMappingException.wrapWithPath(e, result, result.size());
/*     */       } 
/*     */     } 
/* 309 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 318 */     return typeDeserializer.deserializeTypedFromArray(p, ctxt);
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
/*     */   protected final Collection<Object> handleNonArray(JsonParser p, DeserializationContext ctxt, Collection<Object> result) throws IOException {
/*     */     Object value;
/* 332 */     boolean canWrap = (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
/*     */ 
/*     */     
/* 335 */     if (!canWrap) {
/* 336 */       return (Collection<Object>)ctxt.handleUnexpectedToken(this._containerType.getRawClass(), p);
/*     */     }
/* 338 */     JsonDeserializer<Object> valueDes = this._valueDeserializer;
/* 339 */     TypeDeserializer typeDeser = this._valueTypeDeserializer;
/* 340 */     JsonToken t = p.getCurrentToken();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 345 */       if (t == JsonToken.VALUE_NULL) {
/*     */         
/* 347 */         if (this._skipNullValues) {
/* 348 */           return result;
/*     */         }
/* 350 */         value = this._nullProvider.getNullValue(ctxt);
/* 351 */       } else if (typeDeser == null) {
/* 352 */         value = valueDes.deserialize(p, ctxt);
/*     */       } else {
/* 354 */         value = valueDes.deserializeWithType(p, ctxt, typeDeser);
/*     */       } 
/* 356 */     } catch (Exception e) {
/*     */       
/* 358 */       throw JsonMappingException.wrapWithPath(e, Object.class, result.size());
/*     */     } 
/* 360 */     result.add(value);
/* 361 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class CollectionReferringAccumulator
/*     */   {
/*     */     private final Class<?> _elementType;
/*     */     
/*     */     private final Collection<Object> _result;
/*     */     
/* 371 */     private List<CollectionDeserializer.CollectionReferring> _accumulator = new ArrayList<>();
/*     */     
/*     */     public CollectionReferringAccumulator(Class<?> elementType, Collection<Object> result) {
/* 374 */       this._elementType = elementType;
/* 375 */       this._result = result;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(Object value) {
/* 380 */       if (this._accumulator.isEmpty()) {
/* 381 */         this._result.add(value);
/*     */       } else {
/* 383 */         CollectionDeserializer.CollectionReferring ref = this._accumulator.get(this._accumulator.size() - 1);
/* 384 */         ref.next.add(value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference reference) {
/* 390 */       CollectionDeserializer.CollectionReferring id = new CollectionDeserializer.CollectionReferring(this, reference, this._elementType);
/* 391 */       this._accumulator.add(id);
/* 392 */       return id;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resolveForwardReference(Object id, Object value) throws IOException {
/* 397 */       Iterator<CollectionDeserializer.CollectionReferring> iterator = this._accumulator.iterator();
/*     */ 
/*     */ 
/*     */       
/* 401 */       Collection<Object> previous = this._result;
/* 402 */       while (iterator.hasNext()) {
/* 403 */         CollectionDeserializer.CollectionReferring ref = iterator.next();
/* 404 */         if (ref.hasId(id)) {
/* 405 */           iterator.remove();
/* 406 */           previous.add(value);
/* 407 */           previous.addAll(ref.next);
/*     */           return;
/*     */         } 
/* 410 */         previous = ref.next;
/*     */       } 
/*     */       
/* 413 */       throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + id + "] that wasn't previously seen as unresolved.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class CollectionReferring
/*     */     extends ReadableObjectId.Referring
/*     */   {
/*     */     private final CollectionDeserializer.CollectionReferringAccumulator _parent;
/*     */ 
/*     */     
/* 425 */     public final List<Object> next = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/*     */     CollectionReferring(CollectionDeserializer.CollectionReferringAccumulator parent, UnresolvedForwardReference reference, Class<?> contentType) {
/* 430 */       super(reference, contentType);
/* 431 */       this._parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleResolvedForwardReference(Object id, Object value) throws IOException {
/* 436 */       this._parent.resolveForwardReference(id, value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\CollectionDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */