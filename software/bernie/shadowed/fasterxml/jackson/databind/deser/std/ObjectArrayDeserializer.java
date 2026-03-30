/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
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
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class ObjectArrayDeserializer
/*     */   extends ContainerDeserializerBase<Object[]>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   protected static final Object[] NO_OBJECTS = new Object[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _untyped;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Class<?> _elementClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<Object> _elementDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TypeDeserializer _elementTypeDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayDeserializer(JavaType arrayType, JsonDeserializer<Object> elemDeser, TypeDeserializer elemTypeDeser) {
/*  64 */     super(arrayType, (NullValueProvider)null, (Boolean)null);
/*  65 */     this._elementClass = arrayType.getContentType().getRawClass();
/*  66 */     this._untyped = (this._elementClass == Object.class);
/*  67 */     this._elementDeserializer = elemDeser;
/*  68 */     this._elementTypeDeserializer = elemTypeDeser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectArrayDeserializer(ObjectArrayDeserializer base, JsonDeserializer<Object> elemDeser, TypeDeserializer elemTypeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
/*  75 */     super(base, nuller, unwrapSingle);
/*  76 */     this._elementClass = base._elementClass;
/*  77 */     this._untyped = base._untyped;
/*     */     
/*  79 */     this._elementDeserializer = elemDeser;
/*  80 */     this._elementTypeDeserializer = elemTypeDeser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayDeserializer withDeserializer(TypeDeserializer elemTypeDeser, JsonDeserializer<?> elemDeser) {
/*  89 */     return withResolved(elemTypeDeser, elemDeser, this._nullProvider, this._unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayDeserializer withResolved(TypeDeserializer elemTypeDeser, JsonDeserializer<?> elemDeser, NullValueProvider nuller, Boolean unwrapSingle) {
/* 100 */     if (unwrapSingle == this._unwrapSingle && nuller == this._nullProvider && elemDeser == this._elementDeserializer && elemTypeDeser == this._elementTypeDeserializer)
/*     */     {
/*     */       
/* 103 */       return this;
/*     */     }
/* 105 */     return new ObjectArrayDeserializer(this, (JsonDeserializer)elemDeser, elemTypeDeser, nuller, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachable() {
/* 114 */     return (this._elementDeserializer == null && this._elementTypeDeserializer == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 121 */     JsonDeserializer<?> valueDeser = this._elementDeserializer;
/* 122 */     Boolean unwrapSingle = findFormatFeature(ctxt, property, this._containerType.getRawClass(), JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
/*     */ 
/*     */     
/* 125 */     valueDeser = findConvertingContentDeserializer(ctxt, property, valueDeser);
/* 126 */     JavaType vt = this._containerType.getContentType();
/* 127 */     if (valueDeser == null) {
/* 128 */       valueDeser = ctxt.findContextualValueDeserializer(vt, property);
/*     */     } else {
/* 130 */       valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, vt);
/*     */     } 
/* 132 */     TypeDeserializer elemTypeDeser = this._elementTypeDeserializer;
/* 133 */     if (elemTypeDeser != null) {
/* 134 */       elemTypeDeser = elemTypeDeser.forProperty(property);
/*     */     }
/* 136 */     NullValueProvider nuller = findContentNullProvider(ctxt, property, valueDeser);
/* 137 */     return withResolved(elemTypeDeser, valueDeser, nuller, unwrapSingle);
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
/* 148 */     return this._elementDeserializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessPattern getEmptyAccessPattern() {
/* 154 */     return AccessPattern.CONSTANT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/* 160 */     return NO_OBJECTS;
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
/*     */   public Object[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */     Object[] result;
/* 174 */     if (!p.isExpectedStartArrayToken()) {
/* 175 */       return handleNonArray(p, ctxt);
/*     */     }
/*     */     
/* 178 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 179 */     Object[] chunk = buffer.resetAndStart();
/* 180 */     int ix = 0;
/*     */     
/* 182 */     TypeDeserializer typeDeser = this._elementTypeDeserializer;
/*     */     try {
/*     */       JsonToken t;
/* 185 */       while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */         Object value;
/*     */ 
/*     */         
/* 189 */         if (t == JsonToken.VALUE_NULL) {
/* 190 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 193 */           value = this._nullProvider.getNullValue(ctxt);
/* 194 */         } else if (typeDeser == null) {
/* 195 */           value = this._elementDeserializer.deserialize(p, ctxt);
/*     */         } else {
/* 197 */           value = this._elementDeserializer.deserializeWithType(p, ctxt, typeDeser);
/*     */         } 
/* 199 */         if (ix >= chunk.length) {
/* 200 */           chunk = buffer.appendCompletedChunk(chunk);
/* 201 */           ix = 0;
/*     */         } 
/* 203 */         chunk[ix++] = value;
/*     */       } 
/* 205 */     } catch (Exception e) {
/* 206 */       throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (this._untyped) {
/* 212 */       result = buffer.completeAndClearBuffer(chunk, ix);
/*     */     } else {
/* 214 */       result = buffer.completeAndClearBuffer(chunk, ix, this._elementClass);
/*     */     } 
/* 216 */     ctxt.returnObjectBuffer(buffer);
/* 217 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 227 */     return (Object[])typeDeserializer.deserializeTypedFromArray(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] deserialize(JsonParser p, DeserializationContext ctxt, Object[] intoValue) throws IOException {
/*     */     Object[] result;
/* 234 */     if (!p.isExpectedStartArrayToken()) {
/* 235 */       Object[] arr = handleNonArray(p, ctxt);
/* 236 */       if (arr == null) {
/* 237 */         return intoValue;
/*     */       }
/* 239 */       int offset = intoValue.length;
/* 240 */       Object[] arrayOfObject1 = new Object[offset + arr.length];
/* 241 */       System.arraycopy(intoValue, 0, arrayOfObject1, 0, offset);
/* 242 */       System.arraycopy(arr, 0, arrayOfObject1, offset, arr.length);
/* 243 */       return arrayOfObject1;
/*     */     } 
/*     */     
/* 246 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 247 */     int ix = intoValue.length;
/* 248 */     Object[] chunk = buffer.resetAndStart(intoValue, ix);
/*     */     
/* 250 */     TypeDeserializer typeDeser = this._elementTypeDeserializer;
/*     */     try {
/*     */       JsonToken t;
/* 253 */       while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */         Object value;
/*     */         
/* 256 */         if (t == JsonToken.VALUE_NULL) {
/* 257 */           if (this._skipNullValues) {
/*     */             continue;
/*     */           }
/* 260 */           value = this._nullProvider.getNullValue(ctxt);
/* 261 */         } else if (typeDeser == null) {
/* 262 */           value = this._elementDeserializer.deserialize(p, ctxt);
/*     */         } else {
/* 264 */           value = this._elementDeserializer.deserializeWithType(p, ctxt, typeDeser);
/*     */         } 
/* 266 */         if (ix >= chunk.length) {
/* 267 */           chunk = buffer.appendCompletedChunk(chunk);
/* 268 */           ix = 0;
/*     */         } 
/* 270 */         chunk[ix++] = value;
/*     */       } 
/* 272 */     } catch (Exception e) {
/* 273 */       throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 278 */     if (this._untyped) {
/* 279 */       result = buffer.completeAndClearBuffer(chunk, ix);
/*     */     } else {
/* 281 */       result = buffer.completeAndClearBuffer(chunk, ix, this._elementClass);
/*     */     } 
/* 283 */     ctxt.returnObjectBuffer(buffer);
/* 284 */     return result;
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
/*     */   protected Byte[] deserializeFromBase64(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 297 */     byte[] b = p.getBinaryValue(ctxt.getBase64Variant());
/*     */     
/* 299 */     Byte[] result = new Byte[b.length];
/* 300 */     for (int i = 0, len = b.length; i < len; i++) {
/* 301 */       result[i] = Byte.valueOf(b[i]);
/*     */     }
/* 303 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] handleNonArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */     Object value, result[];
/* 310 */     if (p.hasToken(JsonToken.VALUE_STRING) && ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
/*     */       
/* 312 */       String str = p.getText();
/* 313 */       if (str.length() == 0) {
/* 314 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 319 */     boolean canWrap = (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
/*     */ 
/*     */     
/* 322 */     if (!canWrap) {
/*     */       
/* 324 */       JsonToken jsonToken = p.getCurrentToken();
/* 325 */       if (jsonToken == JsonToken.VALUE_STRING && this._elementClass == Byte.class)
/*     */       {
/*     */         
/* 328 */         return (Object[])deserializeFromBase64(p, ctxt);
/*     */       }
/* 330 */       return (Object[])ctxt.handleUnexpectedToken(this._containerType.getRawClass(), p);
/*     */     } 
/* 332 */     JsonToken t = p.getCurrentToken();
/*     */ 
/*     */     
/* 335 */     if (t == JsonToken.VALUE_NULL) {
/*     */       
/* 337 */       if (this._skipNullValues) {
/* 338 */         return NO_OBJECTS;
/*     */       }
/* 340 */       value = this._nullProvider.getNullValue(ctxt);
/* 341 */     } else if (this._elementTypeDeserializer == null) {
/* 342 */       value = this._elementDeserializer.deserialize(p, ctxt);
/*     */     } else {
/* 344 */       value = this._elementDeserializer.deserializeWithType(p, ctxt, this._elementTypeDeserializer);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 349 */     if (this._untyped) {
/* 350 */       result = new Object[1];
/*     */     } else {
/* 352 */       result = (Object[])Array.newInstance(this._elementClass, 1);
/*     */     } 
/* 354 */     result[0] = value;
/* 355 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\ObjectArrayDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */