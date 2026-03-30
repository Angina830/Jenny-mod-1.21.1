/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class UntypedObjectDeserializer
/*     */   extends StdDeserializer<Object>
/*     */   implements ResolvableDeserializer, ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  39 */   protected static final Object[] NO_OBJECTS = new Object[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<Object> _mapDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<Object> _listDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<Object> _stringDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<Object> _numberDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType _listType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType _mapType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _nonMerging;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UntypedObjectDeserializer() {
/*  81 */     this((JavaType)null, (JavaType)null);
/*     */   }
/*     */   
/*     */   public UntypedObjectDeserializer(JavaType listType, JavaType mapType) {
/*  85 */     super(Object.class);
/*  86 */     this._listType = listType;
/*  87 */     this._mapType = mapType;
/*  88 */     this._nonMerging = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UntypedObjectDeserializer(UntypedObjectDeserializer base, JsonDeserializer<?> mapDeser, JsonDeserializer<?> listDeser, JsonDeserializer<?> stringDeser, JsonDeserializer<?> numberDeser) {
/*  96 */     super(Object.class);
/*  97 */     this._mapDeserializer = (JsonDeserializer)mapDeser;
/*  98 */     this._listDeserializer = (JsonDeserializer)listDeser;
/*  99 */     this._stringDeserializer = (JsonDeserializer)stringDeser;
/* 100 */     this._numberDeserializer = (JsonDeserializer)numberDeser;
/* 101 */     this._listType = base._listType;
/* 102 */     this._mapType = base._mapType;
/* 103 */     this._nonMerging = base._nonMerging;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UntypedObjectDeserializer(UntypedObjectDeserializer base, boolean nonMerging) {
/* 112 */     super(Object.class);
/* 113 */     this._mapDeserializer = base._mapDeserializer;
/* 114 */     this._listDeserializer = base._listDeserializer;
/* 115 */     this._stringDeserializer = base._stringDeserializer;
/* 116 */     this._numberDeserializer = base._numberDeserializer;
/* 117 */     this._listType = base._listType;
/* 118 */     this._mapType = base._mapType;
/* 119 */     this._nonMerging = nonMerging;
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
/*     */   public void resolve(DeserializationContext ctxt) throws JsonMappingException {
/* 137 */     JavaType obType = ctxt.constructType(Object.class);
/* 138 */     JavaType stringType = ctxt.constructType(String.class);
/* 139 */     TypeFactory tf = ctxt.getTypeFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (this._listType == null) {
/* 152 */       this._listDeserializer = _clearIfStdImpl(_findCustomDeser(ctxt, (JavaType)tf.constructCollectionType(List.class, obType)));
/*     */     } else {
/*     */       
/* 155 */       this._listDeserializer = _findCustomDeser(ctxt, this._listType);
/*     */     } 
/* 157 */     if (this._mapType == null) {
/* 158 */       this._mapDeserializer = _clearIfStdImpl(_findCustomDeser(ctxt, (JavaType)tf.constructMapType(Map.class, stringType, obType)));
/*     */     } else {
/*     */       
/* 161 */       this._mapDeserializer = _findCustomDeser(ctxt, this._mapType);
/*     */     } 
/* 163 */     this._stringDeserializer = _clearIfStdImpl(_findCustomDeser(ctxt, stringType));
/* 164 */     this._numberDeserializer = _clearIfStdImpl(_findCustomDeser(ctxt, tf.constructType(Number.class)));
/*     */ 
/*     */ 
/*     */     
/* 168 */     JavaType unknown = TypeFactory.unknownType();
/* 169 */     this._mapDeserializer = ctxt.handleSecondaryContextualization(this._mapDeserializer, null, unknown);
/* 170 */     this._listDeserializer = ctxt.handleSecondaryContextualization(this._listDeserializer, null, unknown);
/* 171 */     this._stringDeserializer = ctxt.handleSecondaryContextualization(this._stringDeserializer, null, unknown);
/* 172 */     this._numberDeserializer = ctxt.handleSecondaryContextualization(this._numberDeserializer, null, unknown);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<Object> _findCustomDeser(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
/* 180 */     return ctxt.findNonContextualValueDeserializer(type);
/*     */   }
/*     */   
/*     */   protected JsonDeserializer<Object> _clearIfStdImpl(JsonDeserializer<Object> deser) {
/* 184 */     return ClassUtil.isJacksonStdImpl(deser) ? null : deser;
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
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 196 */     boolean preventMerge = (property == null && Boolean.FALSE.equals(ctxt.getConfig().getDefaultMergeable(Object.class)));
/*     */ 
/*     */ 
/*     */     
/* 200 */     if (this._stringDeserializer == null && this._numberDeserializer == null && this._mapDeserializer == null && this._listDeserializer == null && getClass() == UntypedObjectDeserializer.class)
/*     */     {
/*     */       
/* 203 */       return Vanilla.instance(preventMerge);
/*     */     }
/* 205 */     if (preventMerge != this._nonMerging) {
/* 206 */       return new UntypedObjectDeserializer(this, preventMerge);
/*     */     }
/* 208 */     return this;
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
/*     */   public boolean isCachable() {
/* 227 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 233 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 239 */     switch (p.getCurrentTokenId()) {
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 5:
/* 245 */         if (this._mapDeserializer != null) {
/* 246 */           return this._mapDeserializer.deserialize(p, ctxt);
/*     */         }
/* 248 */         return mapObject(p, ctxt);
/*     */       case 3:
/* 250 */         if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
/* 251 */           return mapArrayToArray(p, ctxt);
/*     */         }
/* 253 */         if (this._listDeserializer != null) {
/* 254 */           return this._listDeserializer.deserialize(p, ctxt);
/*     */         }
/* 256 */         return mapArray(p, ctxt);
/*     */       case 12:
/* 258 */         return p.getEmbeddedObject();
/*     */       case 6:
/* 260 */         if (this._stringDeserializer != null) {
/* 261 */           return this._stringDeserializer.deserialize(p, ctxt);
/*     */         }
/* 263 */         return p.getText();
/*     */       
/*     */       case 7:
/* 266 */         if (this._numberDeserializer != null) {
/* 267 */           return this._numberDeserializer.deserialize(p, ctxt);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 272 */         if (ctxt.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
/* 273 */           return _coerceIntegral(p, ctxt);
/*     */         }
/* 275 */         return p.getNumberValue();
/*     */       
/*     */       case 8:
/* 278 */         if (this._numberDeserializer != null) {
/* 279 */           return this._numberDeserializer.deserialize(p, ctxt);
/*     */         }
/*     */         
/* 282 */         if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 283 */           return p.getDecimalValue();
/*     */         }
/*     */         
/* 286 */         return p.getNumberValue();
/*     */       
/*     */       case 9:
/* 289 */         return Boolean.TRUE;
/*     */       case 10:
/* 291 */         return Boolean.FALSE;
/*     */       
/*     */       case 11:
/* 294 */         return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 299 */     return ctxt.handleUnexpectedToken(Object.class, p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 306 */     switch (p.getCurrentTokenId()) {
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 3:
/*     */       case 5:
/* 312 */         return typeDeserializer.deserializeTypedFromAny(p, ctxt);
/*     */       
/*     */       case 12:
/* 315 */         return p.getEmbeddedObject();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 321 */         if (this._stringDeserializer != null) {
/* 322 */           return this._stringDeserializer.deserialize(p, ctxt);
/*     */         }
/* 324 */         return p.getText();
/*     */       
/*     */       case 7:
/* 327 */         if (this._numberDeserializer != null) {
/* 328 */           return this._numberDeserializer.deserialize(p, ctxt);
/*     */         }
/*     */         
/* 331 */         if (ctxt.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
/* 332 */           return _coerceIntegral(p, ctxt);
/*     */         }
/* 334 */         return p.getNumberValue();
/*     */       
/*     */       case 8:
/* 337 */         if (this._numberDeserializer != null) {
/* 338 */           return this._numberDeserializer.deserialize(p, ctxt);
/*     */         }
/* 340 */         if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 341 */           return p.getDecimalValue();
/*     */         }
/* 343 */         return p.getNumberValue();
/*     */       
/*     */       case 9:
/* 346 */         return Boolean.TRUE;
/*     */       case 10:
/* 348 */         return Boolean.FALSE;
/*     */       
/*     */       case 11:
/* 351 */         return null;
/*     */     } 
/*     */     
/* 354 */     return ctxt.handleUnexpectedToken(Object.class, p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt, Object intoValue) throws IOException {
/* 362 */     if (this._nonMerging) {
/* 363 */       return deserialize(p, ctxt);
/*     */     }
/*     */     
/* 366 */     switch (p.getCurrentTokenId()) {
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 5:
/* 372 */         if (this._mapDeserializer != null) {
/* 373 */           return this._mapDeserializer.deserialize(p, ctxt, intoValue);
/*     */         }
/* 375 */         if (intoValue instanceof Map) {
/* 376 */           return mapObject(p, ctxt, (Map<Object, Object>)intoValue);
/*     */         }
/* 378 */         return mapObject(p, ctxt);
/*     */       case 3:
/* 380 */         if (this._listDeserializer != null) {
/* 381 */           return this._listDeserializer.deserialize(p, ctxt, intoValue);
/*     */         }
/* 383 */         if (intoValue instanceof Collection) {
/* 384 */           return mapArray(p, ctxt, (Collection<Object>)intoValue);
/*     */         }
/* 386 */         if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
/* 387 */           return mapArrayToArray(p, ctxt);
/*     */         }
/* 389 */         return mapArray(p, ctxt);
/*     */       case 12:
/* 391 */         return p.getEmbeddedObject();
/*     */       case 6:
/* 393 */         if (this._stringDeserializer != null) {
/* 394 */           return this._stringDeserializer.deserialize(p, ctxt, intoValue);
/*     */         }
/* 396 */         return p.getText();
/*     */       
/*     */       case 7:
/* 399 */         if (this._numberDeserializer != null) {
/* 400 */           return this._numberDeserializer.deserialize(p, ctxt, intoValue);
/*     */         }
/* 402 */         if (ctxt.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
/* 403 */           return _coerceIntegral(p, ctxt);
/*     */         }
/* 405 */         return p.getNumberValue();
/*     */       
/*     */       case 8:
/* 408 */         if (this._numberDeserializer != null) {
/* 409 */           return this._numberDeserializer.deserialize(p, ctxt, intoValue);
/*     */         }
/* 411 */         if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 412 */           return p.getDecimalValue();
/*     */         }
/* 414 */         return p.getNumberValue();
/*     */       case 9:
/* 416 */         return Boolean.TRUE;
/*     */       case 10:
/* 418 */         return Boolean.FALSE;
/*     */ 
/*     */       
/*     */       case 11:
/* 422 */         return null;
/*     */     } 
/*     */ 
/*     */     
/* 426 */     return deserialize(p, ctxt);
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
/*     */   protected Object mapArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 441 */     if (p.nextToken() == JsonToken.END_ARRAY) {
/* 442 */       return new ArrayList(2);
/*     */     }
/* 444 */     Object value = deserialize(p, ctxt);
/* 445 */     if (p.nextToken() == JsonToken.END_ARRAY) {
/* 446 */       ArrayList<Object> l = new ArrayList(2);
/* 447 */       l.add(value);
/* 448 */       return l;
/*     */     } 
/* 450 */     Object value2 = deserialize(p, ctxt);
/* 451 */     if (p.nextToken() == JsonToken.END_ARRAY) {
/* 452 */       ArrayList<Object> l = new ArrayList(2);
/* 453 */       l.add(value);
/* 454 */       l.add(value2);
/* 455 */       return l;
/*     */     } 
/* 457 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 458 */     Object[] values = buffer.resetAndStart();
/* 459 */     int ptr = 0;
/* 460 */     values[ptr++] = value;
/* 461 */     values[ptr++] = value2;
/* 462 */     int totalSize = ptr;
/*     */     do {
/* 464 */       value = deserialize(p, ctxt);
/* 465 */       totalSize++;
/* 466 */       if (ptr >= values.length) {
/* 467 */         values = buffer.appendCompletedChunk(values);
/* 468 */         ptr = 0;
/*     */       } 
/* 470 */       values[ptr++] = value;
/* 471 */     } while (p.nextToken() != JsonToken.END_ARRAY);
/*     */     
/* 473 */     ArrayList<Object> result = new ArrayList(totalSize);
/* 474 */     buffer.completeAndClearBuffer(values, ptr, result);
/* 475 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object mapArray(JsonParser p, DeserializationContext ctxt, Collection<Object> result) throws IOException {
/* 483 */     while (p.nextToken() != JsonToken.END_ARRAY) {
/* 484 */       result.add(deserialize(p, ctxt));
/*     */     }
/* 486 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object mapObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */     String key1;
/* 496 */     JsonToken t = p.getCurrentToken();
/*     */     
/* 498 */     if (t == JsonToken.START_OBJECT) {
/* 499 */       key1 = p.nextFieldName();
/* 500 */     } else if (t == JsonToken.FIELD_NAME) {
/* 501 */       key1 = p.getCurrentName();
/*     */     } else {
/* 503 */       if (t != JsonToken.END_OBJECT) {
/* 504 */         return ctxt.handleUnexpectedToken(handledType(), p);
/*     */       }
/* 506 */       key1 = null;
/*     */     } 
/* 508 */     if (key1 == null)
/*     */     {
/* 510 */       return new LinkedHashMap<>(2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 515 */     p.nextToken();
/* 516 */     Object value1 = deserialize(p, ctxt);
/*     */     
/* 518 */     String key2 = p.nextFieldName();
/* 519 */     if (key2 == null) {
/*     */       
/* 521 */       LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(2);
/* 522 */       linkedHashMap.put(key1, value1);
/* 523 */       return linkedHashMap;
/*     */     } 
/* 525 */     p.nextToken();
/* 526 */     Object value2 = deserialize(p, ctxt);
/*     */     
/* 528 */     String key = p.nextFieldName();
/*     */     
/* 530 */     if (key == null) {
/* 531 */       LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(4);
/* 532 */       linkedHashMap.put(key1, value1);
/* 533 */       linkedHashMap.put(key2, value2);
/* 534 */       return linkedHashMap;
/*     */     } 
/*     */     
/* 537 */     LinkedHashMap<String, Object> result = new LinkedHashMap<>();
/* 538 */     result.put(key1, value1);
/* 539 */     result.put(key2, value2);
/*     */     
/*     */     do {
/* 542 */       p.nextToken();
/* 543 */       result.put(key, deserialize(p, ctxt));
/* 544 */     } while ((key = p.nextFieldName()) != null);
/* 545 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] mapArrayToArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 554 */     if (p.nextToken() == JsonToken.END_ARRAY) {
/* 555 */       return NO_OBJECTS;
/*     */     }
/* 557 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 558 */     Object[] values = buffer.resetAndStart();
/* 559 */     int ptr = 0;
/*     */     while (true) {
/* 561 */       Object value = deserialize(p, ctxt);
/* 562 */       if (ptr >= values.length) {
/* 563 */         values = buffer.appendCompletedChunk(values);
/* 564 */         ptr = 0;
/*     */       } 
/* 566 */       values[ptr++] = value;
/* 567 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 568 */         return buffer.completeAndClearBuffer(values, ptr);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object mapObject(JsonParser p, DeserializationContext ctxt, Map<Object, Object> m) throws IOException {
/* 574 */     JsonToken t = p.getCurrentToken();
/* 575 */     if (t == JsonToken.START_OBJECT) {
/* 576 */       t = p.nextToken();
/*     */     }
/* 578 */     if (t == JsonToken.END_OBJECT) {
/* 579 */       return m;
/*     */     }
/*     */     
/* 582 */     String key = p.getCurrentName(); while (true) {
/*     */       Object newV;
/* 584 */       p.nextToken();
/*     */       
/* 586 */       Object old = m.get(key);
/*     */ 
/*     */       
/* 589 */       if (old != null) {
/* 590 */         newV = deserialize(p, ctxt, old);
/*     */       } else {
/* 592 */         newV = deserialize(p, ctxt);
/*     */       } 
/* 594 */       if (newV != old) {
/* 595 */         m.put(key, newV);
/*     */       }
/* 597 */       if ((key = p.nextFieldName()) == null) {
/* 598 */         return m;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class Vanilla
/*     */     extends StdDeserializer<Object>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/* 614 */     public static final Vanilla std = new Vanilla();
/*     */ 
/*     */     
/*     */     protected final boolean _nonMerging;
/*     */ 
/*     */     
/*     */     public Vanilla() {
/* 621 */       this(false);
/*     */     }
/*     */     protected Vanilla(boolean nonMerging) {
/* 624 */       super(Object.class);
/* 625 */       this._nonMerging = nonMerging;
/*     */     }
/*     */     
/*     */     public static Vanilla instance(boolean nonMerging) {
/* 629 */       if (nonMerging) {
/* 630 */         return new Vanilla(true);
/*     */       }
/* 632 */       return std;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean supportsUpdate(DeserializationConfig config) {
/* 639 */       return this._nonMerging ? Boolean.FALSE : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       JsonToken t;
/* 645 */       switch (p.getCurrentTokenId()) {
/*     */         
/*     */         case 1:
/* 648 */           t = p.nextToken();
/* 649 */           if (t == JsonToken.END_OBJECT) {
/* 650 */             return new LinkedHashMap<>(2);
/*     */           }
/*     */         
/*     */         case 5:
/* 654 */           return mapObject(p, ctxt);
/*     */         
/*     */         case 3:
/* 657 */           t = p.nextToken();
/* 658 */           if (t == JsonToken.END_ARRAY) {
/* 659 */             if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
/* 660 */               return UntypedObjectDeserializer.NO_OBJECTS;
/*     */             }
/* 662 */             return new ArrayList(2);
/*     */           } 
/*     */           
/* 665 */           if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
/* 666 */             return mapArrayToArray(p, ctxt);
/*     */           }
/* 668 */           return mapArray(p, ctxt);
/*     */         case 12:
/* 670 */           return p.getEmbeddedObject();
/*     */         case 6:
/* 672 */           return p.getText();
/*     */         
/*     */         case 7:
/* 675 */           if (ctxt.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
/* 676 */             return _coerceIntegral(p, ctxt);
/*     */           }
/* 678 */           return p.getNumberValue();
/*     */         
/*     */         case 8:
/* 681 */           if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 682 */             return p.getDecimalValue();
/*     */           }
/* 684 */           return p.getNumberValue();
/*     */         
/*     */         case 9:
/* 687 */           return Boolean.TRUE;
/*     */         case 10:
/* 689 */           return Boolean.FALSE;
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 694 */           return new LinkedHashMap<>(2);
/*     */         
/*     */         case 11:
/* 697 */           return null;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 702 */       return ctxt.handleUnexpectedToken(Object.class, p);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 708 */       switch (p.getCurrentTokenId()) {
/*     */         case 1:
/*     */         case 3:
/*     */         case 5:
/* 712 */           return typeDeserializer.deserializeTypedFromAny(p, ctxt);
/*     */         
/*     */         case 6:
/* 715 */           return p.getText();
/*     */         
/*     */         case 7:
/* 718 */           if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
/* 719 */             return p.getBigIntegerValue();
/*     */           }
/* 721 */           return p.getNumberValue();
/*     */         
/*     */         case 8:
/* 724 */           if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 725 */             return p.getDecimalValue();
/*     */           }
/* 727 */           return p.getNumberValue();
/*     */         
/*     */         case 9:
/* 730 */           return Boolean.TRUE;
/*     */         case 10:
/* 732 */           return Boolean.FALSE;
/*     */         case 12:
/* 734 */           return p.getEmbeddedObject();
/*     */         
/*     */         case 11:
/* 737 */           return null;
/*     */       } 
/*     */       
/* 740 */       return ctxt.handleUnexpectedToken(Object.class, p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object deserialize(JsonParser p, DeserializationContext ctxt, Object intoValue) throws IOException {
/*     */       JsonToken t;
/* 748 */       if (this._nonMerging) {
/* 749 */         return deserialize(p, ctxt);
/*     */       }
/*     */       
/* 752 */       switch (p.getCurrentTokenId()) {
/*     */         case 2:
/*     */         case 4:
/* 755 */           return intoValue;
/*     */         
/*     */         case 1:
/* 758 */           t = p.nextToken();
/* 759 */           if (t == JsonToken.END_OBJECT) {
/* 760 */             return intoValue;
/*     */           }
/*     */         
/*     */         case 5:
/* 764 */           if (intoValue instanceof Map) {
/* 765 */             Map<Object, Object> m = (Map<Object, Object>)intoValue;
/*     */             
/* 767 */             String key = p.getCurrentName(); while (true) {
/*     */               Object newV;
/* 769 */               p.nextToken();
/*     */               
/* 771 */               Object old = m.get(key);
/*     */               
/* 773 */               if (old != null) {
/* 774 */                 newV = deserialize(p, ctxt, old);
/*     */               } else {
/* 776 */                 newV = deserialize(p, ctxt);
/*     */               } 
/* 778 */               if (newV != old) {
/* 779 */                 m.put(key, newV);
/*     */               }
/* 781 */               if ((key = p.nextFieldName()) == null)
/* 782 */                 return intoValue; 
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 787 */           t = p.nextToken();
/* 788 */           if (t == JsonToken.END_ARRAY) {
/* 789 */             return intoValue;
/*     */           }
/*     */ 
/*     */           
/* 793 */           if (intoValue instanceof Collection) {
/* 794 */             Collection<Object> c = (Collection<Object>)intoValue;
/*     */             
/*     */             while (true) {
/* 797 */               c.add(deserialize(p, ctxt));
/* 798 */               if (p.nextToken() == JsonToken.END_ARRAY) {
/* 799 */                 return intoValue;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 806 */       return deserialize(p, ctxt);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object mapArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 811 */       Object value = deserialize(p, ctxt);
/* 812 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 813 */         ArrayList<Object> l = new ArrayList(2);
/* 814 */         l.add(value);
/* 815 */         return l;
/*     */       } 
/* 817 */       Object value2 = deserialize(p, ctxt);
/* 818 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 819 */         ArrayList<Object> l = new ArrayList(2);
/* 820 */         l.add(value);
/* 821 */         l.add(value2);
/* 822 */         return l;
/*     */       } 
/* 824 */       ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 825 */       Object[] values = buffer.resetAndStart();
/* 826 */       int ptr = 0;
/* 827 */       values[ptr++] = value;
/* 828 */       values[ptr++] = value2;
/* 829 */       int totalSize = ptr;
/*     */       do {
/* 831 */         value = deserialize(p, ctxt);
/* 832 */         totalSize++;
/* 833 */         if (ptr >= values.length) {
/* 834 */           values = buffer.appendCompletedChunk(values);
/* 835 */           ptr = 0;
/*     */         } 
/* 837 */         values[ptr++] = value;
/* 838 */       } while (p.nextToken() != JsonToken.END_ARRAY);
/*     */       
/* 840 */       ArrayList<Object> result = new ArrayList(totalSize);
/* 841 */       buffer.completeAndClearBuffer(values, ptr, result);
/* 842 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object[] mapArrayToArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 849 */       ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 850 */       Object[] values = buffer.resetAndStart();
/* 851 */       int ptr = 0;
/*     */       while (true) {
/* 853 */         Object value = deserialize(p, ctxt);
/* 854 */         if (ptr >= values.length) {
/* 855 */           values = buffer.appendCompletedChunk(values);
/* 856 */           ptr = 0;
/*     */         } 
/* 858 */         values[ptr++] = value;
/* 859 */         if (p.nextToken() == JsonToken.END_ARRAY) {
/* 860 */           return buffer.completeAndClearBuffer(values, ptr);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object mapObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 869 */       String key1 = p.getText();
/* 870 */       p.nextToken();
/* 871 */       Object value1 = deserialize(p, ctxt);
/*     */       
/* 873 */       String key2 = p.nextFieldName();
/* 874 */       if (key2 == null) {
/* 875 */         LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(2);
/* 876 */         linkedHashMap.put(key1, value1);
/* 877 */         return linkedHashMap;
/*     */       } 
/* 879 */       p.nextToken();
/* 880 */       Object value2 = deserialize(p, ctxt);
/*     */       
/* 882 */       String key = p.nextFieldName();
/* 883 */       if (key == null) {
/* 884 */         LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(4);
/* 885 */         linkedHashMap.put(key1, value1);
/* 886 */         linkedHashMap.put(key2, value2);
/* 887 */         return linkedHashMap;
/*     */       } 
/*     */       
/* 890 */       LinkedHashMap<String, Object> result = new LinkedHashMap<>();
/* 891 */       result.put(key1, value1);
/* 892 */       result.put(key2, value2);
/*     */       do {
/* 894 */         p.nextToken();
/* 895 */         result.put(key, deserialize(p, ctxt));
/* 896 */       } while ((key = p.nextFieldName()) != null);
/* 897 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\UntypedObjectDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */