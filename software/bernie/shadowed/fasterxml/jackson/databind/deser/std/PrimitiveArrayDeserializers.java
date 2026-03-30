/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.Nulls;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variants;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.NullsFailProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidNullException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PrimitiveArrayDeserializers<T>
/*     */   extends StdDeserializer<T>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   protected final Boolean _unwrapSingle;
/*     */   private transient Object _emptyValue;
/*     */   protected final NullValueProvider _nuller;
/*     */   
/*     */   protected PrimitiveArrayDeserializers(Class<T> cls) {
/*  54 */     super(cls);
/*  55 */     this._unwrapSingle = null;
/*  56 */     this._nuller = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PrimitiveArrayDeserializers(PrimitiveArrayDeserializers<?> base, NullValueProvider nuller, Boolean unwrapSingle) {
/*  64 */     super(base._valueClass);
/*  65 */     this._unwrapSingle = unwrapSingle;
/*  66 */     this._nuller = nuller;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonDeserializer<?> forType(Class<?> rawType) {
/*  72 */     if (rawType == int.class) {
/*  73 */       return IntDeser.instance;
/*     */     }
/*  75 */     if (rawType == long.class) {
/*  76 */       return LongDeser.instance;
/*     */     }
/*     */     
/*  79 */     if (rawType == byte.class) {
/*  80 */       return new ByteDeser();
/*     */     }
/*  82 */     if (rawType == short.class) {
/*  83 */       return new ShortDeser();
/*     */     }
/*  85 */     if (rawType == float.class) {
/*  86 */       return new FloatDeser();
/*     */     }
/*  88 */     if (rawType == double.class) {
/*  89 */       return new DoubleDeser();
/*     */     }
/*  91 */     if (rawType == boolean.class) {
/*  92 */       return new BooleanDeser();
/*     */     }
/*  94 */     if (rawType == char.class) {
/*  95 */       return new CharDeser();
/*     */     }
/*  97 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/*     */     NullsFailProvider nullsFailProvider;
/* 104 */     Boolean unwrapSingle = findFormatFeature(ctxt, property, this._valueClass, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
/*     */     
/* 106 */     NullValueProvider nuller = null;
/*     */     
/* 108 */     Nulls nullStyle = findContentNullStyle(ctxt, property);
/* 109 */     if (nullStyle == Nulls.SKIP) {
/* 110 */       NullsConstantProvider nullsConstantProvider = NullsConstantProvider.skipper();
/* 111 */     } else if (nullStyle == Nulls.FAIL) {
/* 112 */       if (property == null) {
/* 113 */         nullsFailProvider = NullsFailProvider.constructForRootValue(ctxt.constructType(this._valueClass));
/*     */       } else {
/* 115 */         nullsFailProvider = NullsFailProvider.constructForProperty(property);
/*     */       } 
/*     */     } 
/* 118 */     if (unwrapSingle == this._unwrapSingle && nullsFailProvider == this._nuller) {
/* 119 */       return this;
/*     */     }
/* 121 */     return withResolved((NullValueProvider)nullsFailProvider, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T _concat(T paramT1, T paramT2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T handleSingleElementUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PrimitiveArrayDeserializers<?> withResolved(NullValueProvider paramNullValueProvider, Boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T _constructEmpty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 155 */     return Boolean.TRUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessPattern getEmptyAccessPattern() {
/* 161 */     return AccessPattern.CONSTANT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/* 166 */     Object empty = this._emptyValue;
/* 167 */     if (empty == null) {
/* 168 */       this._emptyValue = empty = _constructEmpty();
/*     */     }
/* 170 */     return empty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 179 */     return typeDeserializer.deserializeTypedFromArray(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T deserialize(JsonParser p, DeserializationContext ctxt, T existing) throws IOException {
/* 185 */     T newValue = (T)deserialize(p, ctxt);
/* 186 */     if (existing == null) {
/* 187 */       return newValue;
/*     */     }
/* 189 */     int len = Array.getLength(existing);
/* 190 */     if (len == 0) {
/* 191 */       return newValue;
/*     */     }
/* 193 */     return _concat(existing, newValue);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T handleNonArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 228 */     if (p.hasToken(JsonToken.VALUE_STRING) && ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT))
/*     */     {
/* 230 */       if (p.getText().length() == 0) {
/* 231 */         return null;
/*     */       }
/*     */     }
/* 234 */     boolean canWrap = (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
/*     */ 
/*     */     
/* 237 */     if (canWrap) {
/* 238 */       return handleSingleElementUnwrapped(p, ctxt);
/*     */     }
/* 240 */     return (T)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _failOnNull(DeserializationContext ctxt) throws IOException {
/* 245 */     throw InvalidNullException.from(ctxt, null, ctxt.constructType(this._valueClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class CharDeser
/*     */     extends PrimitiveArrayDeserializers<char[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*     */     public CharDeser() {
/* 260 */       super((Class)char[].class);
/*     */     } protected CharDeser(CharDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 262 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 269 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected char[] _constructEmpty() {
/* 274 */       return new char[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public char[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 284 */       JsonToken t = p.getCurrentToken();
/* 285 */       if (t == JsonToken.VALUE_STRING) {
/*     */         
/* 287 */         char[] buffer = p.getTextCharacters();
/* 288 */         int offset = p.getTextOffset();
/* 289 */         int len = p.getTextLength();
/*     */         
/* 291 */         char[] result = new char[len];
/* 292 */         System.arraycopy(buffer, offset, result, 0, len);
/* 293 */         return result;
/*     */       } 
/* 295 */       if (p.isExpectedStartArrayToken()) {
/*     */         
/* 297 */         StringBuilder sb = new StringBuilder(64);
/* 298 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           String str;
/* 300 */           if (t == JsonToken.VALUE_STRING) {
/* 301 */             str = p.getText();
/* 302 */           } else if (t == JsonToken.VALUE_NULL) {
/* 303 */             if (this._nuller != null) {
/* 304 */               this._nuller.getNullValue(ctxt);
/*     */               continue;
/*     */             } 
/* 307 */             _verifyNullForPrimitive(ctxt);
/* 308 */             str = "\000";
/*     */           } else {
/* 310 */             CharSequence cs = (CharSequence)ctxt.handleUnexpectedToken(char.class, p);
/* 311 */             str = cs.toString();
/*     */           } 
/* 313 */           if (str.length() != 1) {
/* 314 */             ctxt.reportInputMismatch(this, "Cannot convert a JSON String of length %d into a char element of char array", new Object[] { Integer.valueOf(str.length()) });
/*     */           }
/*     */           
/* 317 */           sb.append(str.charAt(0));
/*     */         } 
/* 319 */         return sb.toString().toCharArray();
/*     */       } 
/*     */       
/* 322 */       if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 323 */         Object ob = p.getEmbeddedObject();
/* 324 */         if (ob == null) return null; 
/* 325 */         if (ob instanceof char[]) {
/* 326 */           return (char[])ob;
/*     */         }
/* 328 */         if (ob instanceof String) {
/* 329 */           return ((String)ob).toCharArray();
/*     */         }
/*     */         
/* 332 */         if (ob instanceof byte[]) {
/* 333 */           return Base64Variants.getDefaultVariant().encode((byte[])ob, false).toCharArray();
/*     */         }
/*     */       } 
/*     */       
/* 337 */       return (char[])ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected char[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 344 */       return (char[])ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */ 
/*     */     
/*     */     protected char[] _concat(char[] oldValue, char[] newValue) {
/* 349 */       int len1 = oldValue.length;
/* 350 */       int len2 = newValue.length;
/* 351 */       char[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 352 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 353 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class BooleanDeser
/*     */     extends PrimitiveArrayDeserializers<boolean[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*     */     public BooleanDeser() {
/* 369 */       super((Class)boolean[].class);
/*     */     } protected BooleanDeser(BooleanDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 371 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 377 */       return new BooleanDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean[] _constructEmpty() {
/* 382 */       return new boolean[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 389 */       if (!p.isExpectedStartArrayToken()) {
/* 390 */         return handleNonArray(p, ctxt);
/*     */       }
/* 392 */       ArrayBuilders.BooleanBuilder builder = ctxt.getArrayBuilders().getBooleanBuilder();
/* 393 */       boolean[] chunk = (boolean[])builder.resetAndStart();
/* 394 */       int ix = 0;
/*     */       
/*     */       try {
/*     */         JsonToken t;
/* 398 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           boolean value;
/* 400 */           if (t == JsonToken.VALUE_TRUE) {
/* 401 */             value = true;
/* 402 */           } else if (t == JsonToken.VALUE_FALSE) {
/* 403 */             value = false;
/* 404 */           } else if (t == JsonToken.VALUE_NULL) {
/* 405 */             if (this._nuller != null) {
/* 406 */               this._nuller.getNullValue(ctxt);
/*     */               continue;
/*     */             } 
/* 409 */             _verifyNullForPrimitive(ctxt);
/* 410 */             value = false;
/*     */           } else {
/* 412 */             value = _parseBooleanPrimitive(p, ctxt);
/*     */           } 
/* 414 */           if (ix >= chunk.length) {
/* 415 */             chunk = (boolean[])builder.appendCompletedChunk(chunk, ix);
/* 416 */             ix = 0;
/*     */           } 
/* 418 */           chunk[ix++] = value;
/*     */         } 
/* 420 */       } catch (Exception e) {
/* 421 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 423 */       return (boolean[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 429 */       return new boolean[] { _parseBooleanPrimitive(p, ctxt) };
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean[] _concat(boolean[] oldValue, boolean[] newValue) {
/* 434 */       int len1 = oldValue.length;
/* 435 */       int len2 = newValue.length;
/* 436 */       boolean[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 437 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 438 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class ByteDeser
/*     */     extends PrimitiveArrayDeserializers<byte[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     public ByteDeser() {
/* 452 */       super((Class)byte[].class);
/*     */     } protected ByteDeser(ByteDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 454 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 460 */       return new ByteDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected byte[] _constructEmpty() {
/* 465 */       return new byte[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 471 */       JsonToken t = p.getCurrentToken();
/*     */ 
/*     */       
/* 474 */       if (t == JsonToken.VALUE_STRING) {
/*     */         try {
/* 476 */           return p.getBinaryValue(ctxt.getBase64Variant());
/* 477 */         } catch (JsonParseException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 482 */           String msg = e.getOriginalMessage();
/* 483 */           if (msg.contains("base64")) {
/* 484 */             return (byte[])ctxt.handleWeirdStringValue(byte[].class, p.getText(), msg, new Object[0]);
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 490 */       if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 491 */         Object ob = p.getEmbeddedObject();
/* 492 */         if (ob == null) return null; 
/* 493 */         if (ob instanceof byte[]) {
/* 494 */           return (byte[])ob;
/*     */         }
/*     */       } 
/* 497 */       if (!p.isExpectedStartArrayToken()) {
/* 498 */         return handleNonArray(p, ctxt);
/*     */       }
/* 500 */       ArrayBuilders.ByteBuilder builder = ctxt.getArrayBuilders().getByteBuilder();
/* 501 */       byte[] chunk = (byte[])builder.resetAndStart();
/* 502 */       int ix = 0;
/*     */       
/*     */       try {
/* 505 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           byte value;
/*     */           
/* 508 */           if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/*     */             
/* 510 */             value = p.getByteValue();
/*     */           
/*     */           }
/* 513 */           else if (t == JsonToken.VALUE_NULL) {
/* 514 */             if (this._nuller != null) {
/* 515 */               this._nuller.getNullValue(ctxt);
/*     */               continue;
/*     */             } 
/* 518 */             _verifyNullForPrimitive(ctxt);
/* 519 */             value = 0;
/*     */           } else {
/* 521 */             value = _parseBytePrimitive(p, ctxt);
/*     */           } 
/*     */           
/* 524 */           if (ix >= chunk.length) {
/* 525 */             chunk = (byte[])builder.appendCompletedChunk(chunk, ix);
/* 526 */             ix = 0;
/*     */           } 
/* 528 */           chunk[ix++] = value;
/*     */         } 
/* 530 */       } catch (Exception e) {
/* 531 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 533 */       return (byte[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected byte[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       byte value;
/* 541 */       JsonToken t = p.getCurrentToken();
/* 542 */       if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/*     */         
/* 544 */         value = p.getByteValue();
/*     */       } else {
/*     */         
/* 547 */         if (t == JsonToken.VALUE_NULL) {
/* 548 */           if (this._nuller != null) {
/* 549 */             this._nuller.getNullValue(ctxt);
/* 550 */             return (byte[])getEmptyValue(ctxt);
/*     */           } 
/* 552 */           _verifyNullForPrimitive(ctxt);
/* 553 */           return null;
/*     */         } 
/* 555 */         Number n = (Number)ctxt.handleUnexpectedToken(this._valueClass.getComponentType(), p);
/* 556 */         value = n.byteValue();
/*     */       } 
/* 558 */       return new byte[] { value };
/*     */     }
/*     */ 
/*     */     
/*     */     protected byte[] _concat(byte[] oldValue, byte[] newValue) {
/* 563 */       int len1 = oldValue.length;
/* 564 */       int len2 = newValue.length;
/* 565 */       byte[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 566 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 567 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class ShortDeser
/*     */     extends PrimitiveArrayDeserializers<short[]> {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public ShortDeser() {
/* 577 */       super((Class)short[].class);
/*     */     } protected ShortDeser(ShortDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 579 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 585 */       return new ShortDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected short[] _constructEmpty() {
/* 590 */       return new short[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public short[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 596 */       if (!p.isExpectedStartArrayToken()) {
/* 597 */         return handleNonArray(p, ctxt);
/*     */       }
/* 599 */       ArrayBuilders.ShortBuilder builder = ctxt.getArrayBuilders().getShortBuilder();
/* 600 */       short[] chunk = (short[])builder.resetAndStart();
/* 601 */       int ix = 0;
/*     */       
/*     */       try {
/*     */         JsonToken t;
/* 605 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           short value;
/* 607 */           if (t == JsonToken.VALUE_NULL) {
/* 608 */             if (this._nuller != null) {
/* 609 */               this._nuller.getNullValue(ctxt);
/*     */               continue;
/*     */             } 
/* 612 */             _verifyNullForPrimitive(ctxt);
/* 613 */             value = 0;
/*     */           } else {
/* 615 */             value = _parseShortPrimitive(p, ctxt);
/*     */           } 
/* 617 */           if (ix >= chunk.length) {
/* 618 */             chunk = (short[])builder.appendCompletedChunk(chunk, ix);
/* 619 */             ix = 0;
/*     */           } 
/* 621 */           chunk[ix++] = value;
/*     */         } 
/* 623 */       } catch (Exception e) {
/* 624 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 626 */       return (short[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected short[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 632 */       return new short[] { _parseShortPrimitive(p, ctxt) };
/*     */     }
/*     */ 
/*     */     
/*     */     protected short[] _concat(short[] oldValue, short[] newValue) {
/* 637 */       int len1 = oldValue.length;
/* 638 */       int len2 = newValue.length;
/* 639 */       short[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 640 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 641 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class IntDeser
/*     */     extends PrimitiveArrayDeserializers<int[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 651 */     public static final IntDeser instance = new IntDeser();
/*     */     public IntDeser() {
/* 653 */       super((Class)int[].class);
/*     */     } protected IntDeser(IntDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 655 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 661 */       return new IntDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int[] _constructEmpty() {
/* 666 */       return new int[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 672 */       if (!p.isExpectedStartArrayToken()) {
/* 673 */         return handleNonArray(p, ctxt);
/*     */       }
/* 675 */       ArrayBuilders.IntBuilder builder = ctxt.getArrayBuilders().getIntBuilder();
/* 676 */       int[] chunk = (int[])builder.resetAndStart();
/* 677 */       int ix = 0;
/*     */       
/*     */       try {
/*     */         JsonToken t;
/* 681 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           int value;
/* 683 */           if (t == JsonToken.VALUE_NUMBER_INT) {
/* 684 */             value = p.getIntValue();
/* 685 */           } else if (t == JsonToken.VALUE_NULL) {
/* 686 */             if (this._nuller != null) {
/* 687 */               this._nuller.getNullValue(ctxt);
/*     */               continue;
/*     */             } 
/* 690 */             _verifyNullForPrimitive(ctxt);
/* 691 */             value = 0;
/*     */           } else {
/* 693 */             value = _parseIntPrimitive(p, ctxt);
/*     */           } 
/* 695 */           if (ix >= chunk.length) {
/* 696 */             chunk = (int[])builder.appendCompletedChunk(chunk, ix);
/* 697 */             ix = 0;
/*     */           } 
/* 699 */           chunk[ix++] = value;
/*     */         } 
/* 701 */       } catch (Exception e) {
/* 702 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 704 */       return (int[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 710 */       return new int[] { _parseIntPrimitive(p, ctxt) };
/*     */     }
/*     */ 
/*     */     
/*     */     protected int[] _concat(int[] oldValue, int[] newValue) {
/* 715 */       int len1 = oldValue.length;
/* 716 */       int len2 = newValue.length;
/* 717 */       int[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 718 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 719 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class LongDeser
/*     */     extends PrimitiveArrayDeserializers<long[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 729 */     public static final LongDeser instance = new LongDeser();
/*     */     public LongDeser() {
/* 731 */       super((Class)long[].class);
/*     */     } protected LongDeser(LongDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 733 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 739 */       return new LongDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected long[] _constructEmpty() {
/* 744 */       return new long[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 750 */       if (!p.isExpectedStartArrayToken()) {
/* 751 */         return handleNonArray(p, ctxt);
/*     */       }
/* 753 */       ArrayBuilders.LongBuilder builder = ctxt.getArrayBuilders().getLongBuilder();
/* 754 */       long[] chunk = (long[])builder.resetAndStart();
/* 755 */       int ix = 0;
/*     */       
/*     */       try {
/*     */         JsonToken t;
/* 759 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           long value;
/* 761 */           if (t == JsonToken.VALUE_NUMBER_INT) {
/* 762 */             value = p.getLongValue();
/* 763 */           } else if (t == JsonToken.VALUE_NULL) {
/* 764 */             if (this._nuller != null) {
/* 765 */               this._nuller.getNullValue(ctxt);
/*     */               continue;
/*     */             } 
/* 768 */             _verifyNullForPrimitive(ctxt);
/* 769 */             value = 0L;
/*     */           } else {
/* 771 */             value = _parseLongPrimitive(p, ctxt);
/*     */           } 
/* 773 */           if (ix >= chunk.length) {
/* 774 */             chunk = (long[])builder.appendCompletedChunk(chunk, ix);
/* 775 */             ix = 0;
/*     */           } 
/* 777 */           chunk[ix++] = value;
/*     */         } 
/* 779 */       } catch (Exception e) {
/* 780 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 782 */       return (long[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected long[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 788 */       return new long[] { _parseLongPrimitive(p, ctxt) };
/*     */     }
/*     */ 
/*     */     
/*     */     protected long[] _concat(long[] oldValue, long[] newValue) {
/* 793 */       int len1 = oldValue.length;
/* 794 */       int len2 = newValue.length;
/* 795 */       long[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 796 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 797 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class FloatDeser
/*     */     extends PrimitiveArrayDeserializers<float[]> {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public FloatDeser() {
/* 807 */       super((Class)float[].class);
/*     */     } protected FloatDeser(FloatDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 809 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 815 */       return new FloatDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected float[] _constructEmpty() {
/* 820 */       return new float[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 826 */       if (!p.isExpectedStartArrayToken()) {
/* 827 */         return handleNonArray(p, ctxt);
/*     */       }
/* 829 */       ArrayBuilders.FloatBuilder builder = ctxt.getArrayBuilders().getFloatBuilder();
/* 830 */       float[] chunk = (float[])builder.resetAndStart();
/* 831 */       int ix = 0;
/*     */       
/*     */       try {
/*     */         JsonToken t;
/* 835 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/*     */           
/* 837 */           if (t == JsonToken.VALUE_NULL && 
/* 838 */             this._nuller != null) {
/* 839 */             this._nuller.getNullValue(ctxt);
/*     */             
/*     */             continue;
/*     */           } 
/* 843 */           float value = _parseFloatPrimitive(p, ctxt);
/* 844 */           if (ix >= chunk.length) {
/* 845 */             chunk = (float[])builder.appendCompletedChunk(chunk, ix);
/* 846 */             ix = 0;
/*     */           } 
/* 848 */           chunk[ix++] = value;
/*     */         } 
/* 850 */       } catch (Exception e) {
/* 851 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 853 */       return (float[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected float[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 859 */       return new float[] { _parseFloatPrimitive(p, ctxt) };
/*     */     }
/*     */ 
/*     */     
/*     */     protected float[] _concat(float[] oldValue, float[] newValue) {
/* 864 */       int len1 = oldValue.length;
/* 865 */       int len2 = newValue.length;
/* 866 */       float[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 867 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 868 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class DoubleDeser
/*     */     extends PrimitiveArrayDeserializers<double[]> {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public DoubleDeser() {
/* 878 */       super((Class)double[].class);
/*     */     } protected DoubleDeser(DoubleDeser base, NullValueProvider nuller, Boolean unwrapSingle) {
/* 880 */       super(base, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nuller, Boolean unwrapSingle) {
/* 886 */       return new DoubleDeser(this, nuller, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected double[] _constructEmpty() {
/* 891 */       return new double[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 897 */       if (!p.isExpectedStartArrayToken()) {
/* 898 */         return handleNonArray(p, ctxt);
/*     */       }
/* 900 */       ArrayBuilders.DoubleBuilder builder = ctxt.getArrayBuilders().getDoubleBuilder();
/* 901 */       double[] chunk = (double[])builder.resetAndStart();
/* 902 */       int ix = 0;
/*     */       
/*     */       try {
/*     */         JsonToken t;
/* 906 */         while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
/* 907 */           if (t == JsonToken.VALUE_NULL && 
/* 908 */             this._nuller != null) {
/* 909 */             this._nuller.getNullValue(ctxt);
/*     */             
/*     */             continue;
/*     */           } 
/* 913 */           double value = _parseDoublePrimitive(p, ctxt);
/* 914 */           if (ix >= chunk.length) {
/* 915 */             chunk = (double[])builder.appendCompletedChunk(chunk, ix);
/* 916 */             ix = 0;
/*     */           } 
/* 918 */           chunk[ix++] = value;
/*     */         } 
/* 920 */       } catch (Exception e) {
/* 921 */         throw JsonMappingException.wrapWithPath(e, chunk, builder.bufferedSize() + ix);
/*     */       } 
/* 923 */       return (double[])builder.completeAndClearBuffer(chunk, ix);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected double[] handleSingleElementUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 929 */       return new double[] { _parseDoublePrimitive(p, ctxt) };
/*     */     }
/*     */ 
/*     */     
/*     */     protected double[] _concat(double[] oldValue, double[] newValue) {
/* 934 */       int len1 = oldValue.length;
/* 935 */       int len2 = newValue.length;
/* 936 */       double[] result = Arrays.copyOf(oldValue, len1 + len2);
/* 937 */       System.arraycopy(newValue, 0, result, len1, len2);
/* 938 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\PrimitiveArrayDeserializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */