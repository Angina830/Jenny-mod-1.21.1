/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.HashSet;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberDeserializers
/*     */ {
/*  24 */   private static final HashSet<String> _classNames = new HashSet<>();
/*     */   
/*     */   static {
/*  27 */     Class<?>[] numberTypes = new Class[] { Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, Number.class, BigDecimal.class, BigInteger.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     for (Class<?> cls : numberTypes) {
/*  40 */       _classNames.add(cls.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public static JsonDeserializer<?> find(Class<?> rawType, String clsName) {
/*  45 */     if (rawType.isPrimitive()) {
/*  46 */       if (rawType == int.class) {
/*  47 */         return IntegerDeserializer.primitiveInstance;
/*     */       }
/*  49 */       if (rawType == boolean.class) {
/*  50 */         return BooleanDeserializer.primitiveInstance;
/*     */       }
/*  52 */       if (rawType == long.class) {
/*  53 */         return LongDeserializer.primitiveInstance;
/*     */       }
/*  55 */       if (rawType == double.class) {
/*  56 */         return DoubleDeserializer.primitiveInstance;
/*     */       }
/*  58 */       if (rawType == char.class) {
/*  59 */         return CharacterDeserializer.primitiveInstance;
/*     */       }
/*  61 */       if (rawType == byte.class) {
/*  62 */         return ByteDeserializer.primitiveInstance;
/*     */       }
/*  64 */       if (rawType == short.class) {
/*  65 */         return ShortDeserializer.primitiveInstance;
/*     */       }
/*  67 */       if (rawType == float.class) {
/*  68 */         return FloatDeserializer.primitiveInstance;
/*     */       }
/*  70 */     } else if (_classNames.contains(clsName)) {
/*     */       
/*  72 */       if (rawType == Integer.class) {
/*  73 */         return IntegerDeserializer.wrapperInstance;
/*     */       }
/*  75 */       if (rawType == Boolean.class) {
/*  76 */         return BooleanDeserializer.wrapperInstance;
/*     */       }
/*  78 */       if (rawType == Long.class) {
/*  79 */         return LongDeserializer.wrapperInstance;
/*     */       }
/*  81 */       if (rawType == Double.class) {
/*  82 */         return DoubleDeserializer.wrapperInstance;
/*     */       }
/*  84 */       if (rawType == Character.class) {
/*  85 */         return CharacterDeserializer.wrapperInstance;
/*     */       }
/*  87 */       if (rawType == Byte.class) {
/*  88 */         return ByteDeserializer.wrapperInstance;
/*     */       }
/*  90 */       if (rawType == Short.class) {
/*  91 */         return ShortDeserializer.wrapperInstance;
/*     */       }
/*  93 */       if (rawType == Float.class) {
/*  94 */         return FloatDeserializer.wrapperInstance;
/*     */       }
/*  96 */       if (rawType == Number.class) {
/*  97 */         return NumberDeserializer.instance;
/*     */       }
/*  99 */       if (rawType == BigDecimal.class) {
/* 100 */         return BigDecimalDeserializer.instance;
/*     */       }
/* 102 */       if (rawType == BigInteger.class) {
/* 103 */         return BigIntegerDeserializer.instance;
/*     */       }
/*     */     } else {
/* 106 */       return null;
/*     */     } 
/*     */     
/* 109 */     throw new IllegalArgumentException("Internal error: can't find deserializer for " + rawType.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static abstract class PrimitiveOrWrapperDeserializer<T>
/*     */     extends StdScalarDeserializer<T>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     protected final T _nullValue;
/*     */ 
/*     */     
/*     */     protected final T _emptyValue;
/*     */ 
/*     */     
/*     */     protected final boolean _primitive;
/*     */ 
/*     */ 
/*     */     
/*     */     protected PrimitiveOrWrapperDeserializer(Class<T> vc, T nvl, T empty) {
/* 132 */       super(vc);
/* 133 */       this._nullValue = nvl;
/* 134 */       this._emptyValue = empty;
/* 135 */       this._primitive = vc.isPrimitive();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessPattern getNullAccessPattern() {
/* 142 */       if (this._primitive) {
/* 143 */         return AccessPattern.DYNAMIC;
/*     */       }
/* 145 */       if (this._nullValue == null) {
/* 146 */         return AccessPattern.ALWAYS_NULL;
/*     */       }
/* 148 */       return AccessPattern.CONSTANT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final T getNullValue(DeserializationContext ctxt) throws JsonMappingException {
/* 155 */       if (this._primitive && ctxt.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
/* 156 */         ctxt.reportInputMismatch(this, "Cannot map `null` into type %s (set DeserializationConfig.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES to 'false' to allow)", new Object[] { handledType().toString() });
/*     */       }
/*     */ 
/*     */       
/* 160 */       return this._nullValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/* 165 */       return this._emptyValue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class BooleanDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Boolean>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/* 181 */     static final BooleanDeserializer primitiveInstance = new BooleanDeserializer((Class)boolean.class, Boolean.FALSE);
/* 182 */     static final BooleanDeserializer wrapperInstance = new BooleanDeserializer(Boolean.class, null);
/*     */ 
/*     */     
/*     */     public BooleanDeserializer(Class<Boolean> cls, Boolean nvl) {
/* 186 */       super(cls, nvl, Boolean.FALSE);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 192 */       JsonToken t = p.getCurrentToken();
/* 193 */       if (t == JsonToken.VALUE_TRUE) {
/* 194 */         return Boolean.TRUE;
/*     */       }
/* 196 */       if (t == JsonToken.VALUE_FALSE) {
/* 197 */         return Boolean.FALSE;
/*     */       }
/* 199 */       return _parseBoolean(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 209 */       JsonToken t = p.getCurrentToken();
/* 210 */       if (t == JsonToken.VALUE_TRUE) {
/* 211 */         return Boolean.TRUE;
/*     */       }
/* 213 */       if (t == JsonToken.VALUE_FALSE) {
/* 214 */         return Boolean.FALSE;
/*     */       }
/* 216 */       return _parseBoolean(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected final Boolean _parseBoolean(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 222 */       JsonToken t = p.getCurrentToken();
/* 223 */       if (t == JsonToken.VALUE_NULL) {
/* 224 */         return (Boolean)_coerceNullToken(ctxt, this._primitive);
/*     */       }
/* 226 */       if (t == JsonToken.START_ARRAY) {
/* 227 */         return _deserializeFromArray(p, ctxt);
/*     */       }
/*     */       
/* 230 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 231 */         return Boolean.valueOf(_parseBooleanFromInt(p, ctxt));
/*     */       }
/*     */       
/* 234 */       if (t == JsonToken.VALUE_STRING) {
/* 235 */         String text = p.getText().trim();
/*     */         
/* 237 */         if ("true".equals(text) || "True".equals(text)) {
/* 238 */           _verifyStringForScalarCoercion(ctxt, text);
/* 239 */           return Boolean.TRUE;
/*     */         } 
/* 241 */         if ("false".equals(text) || "False".equals(text)) {
/* 242 */           _verifyStringForScalarCoercion(ctxt, text);
/* 243 */           return Boolean.FALSE;
/*     */         } 
/* 245 */         if (text.length() == 0) {
/* 246 */           return (Boolean)_coerceEmptyString(ctxt, this._primitive);
/*     */         }
/* 248 */         if (_hasTextualNull(text)) {
/* 249 */           return (Boolean)_coerceTextualNull(ctxt, this._primitive);
/*     */         }
/* 251 */         return (Boolean)ctxt.handleWeirdStringValue(this._valueClass, text, "only \"true\" or \"false\" recognized", new Object[0]);
/*     */       } 
/*     */ 
/*     */       
/* 255 */       if (t == JsonToken.VALUE_TRUE) {
/* 256 */         return Boolean.TRUE;
/*     */       }
/* 258 */       if (t == JsonToken.VALUE_FALSE) {
/* 259 */         return Boolean.FALSE;
/*     */       }
/*     */       
/* 262 */       return (Boolean)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class ByteDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Byte>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 272 */     static final ByteDeserializer primitiveInstance = new ByteDeserializer((Class)byte.class, Byte.valueOf((byte)0));
/* 273 */     static final ByteDeserializer wrapperInstance = new ByteDeserializer(Byte.class, null);
/*     */ 
/*     */     
/*     */     public ByteDeserializer(Class<Byte> cls, Byte nvl) {
/* 277 */       super(cls, nvl, Byte.valueOf((byte)0));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Byte deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 283 */       if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/* 284 */         return Byte.valueOf(p.getByteValue());
/*     */       }
/* 286 */       return _parseByte(p, ctxt);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Byte _parseByte(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 291 */       JsonToken t = p.getCurrentToken();
/* 292 */       if (t == JsonToken.VALUE_STRING) {
/* 293 */         int value; String text = p.getText().trim();
/* 294 */         if (_hasTextualNull(text)) {
/* 295 */           return (Byte)_coerceTextualNull(ctxt, this._primitive);
/*     */         }
/* 297 */         int len = text.length();
/* 298 */         if (len == 0) {
/* 299 */           return (Byte)_coerceEmptyString(ctxt, this._primitive);
/*     */         }
/* 301 */         _verifyStringForScalarCoercion(ctxt, text);
/*     */         
/*     */         try {
/* 304 */           value = NumberInput.parseInt(text);
/* 305 */         } catch (IllegalArgumentException iae) {
/* 306 */           return (Byte)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid Byte value", new Object[0]);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 311 */         if (_byteOverflow(value)) {
/* 312 */           return (Byte)ctxt.handleWeirdStringValue(this._valueClass, text, "overflow, value cannot be represented as 8-bit value", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/* 316 */         return Byte.valueOf((byte)value);
/*     */       } 
/* 318 */       if (t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 319 */         if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/* 320 */           _failDoubleToIntCoercion(p, ctxt, "Byte");
/*     */         }
/* 322 */         return Byte.valueOf(p.getByteValue());
/*     */       } 
/* 324 */       if (t == JsonToken.VALUE_NULL) {
/* 325 */         return (Byte)_coerceNullToken(ctxt, this._primitive);
/*     */       }
/*     */       
/* 328 */       if (t == JsonToken.START_ARRAY) {
/* 329 */         return _deserializeFromArray(p, ctxt);
/*     */       }
/* 331 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 332 */         return Byte.valueOf(p.getByteValue());
/*     */       }
/* 334 */       return (Byte)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class ShortDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Short>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 344 */     static final ShortDeserializer primitiveInstance = new ShortDeserializer((Class)short.class, Short.valueOf((short)0));
/* 345 */     static final ShortDeserializer wrapperInstance = new ShortDeserializer(Short.class, null);
/*     */ 
/*     */     
/*     */     public ShortDeserializer(Class<Short> cls, Short nvl) {
/* 349 */       super(cls, nvl, Short.valueOf((short)0));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Short deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 356 */       return _parseShort(p, ctxt);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Short _parseShort(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 361 */       JsonToken t = p.getCurrentToken();
/* 362 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 363 */         return Short.valueOf(p.getShortValue());
/*     */       }
/* 365 */       if (t == JsonToken.VALUE_STRING) {
/* 366 */         int value; String text = p.getText().trim();
/* 367 */         int len = text.length();
/* 368 */         if (len == 0) {
/* 369 */           return (Short)_coerceEmptyString(ctxt, this._primitive);
/*     */         }
/* 371 */         if (_hasTextualNull(text)) {
/* 372 */           return (Short)_coerceTextualNull(ctxt, this._primitive);
/*     */         }
/* 374 */         _verifyStringForScalarCoercion(ctxt, text);
/*     */         
/*     */         try {
/* 377 */           value = NumberInput.parseInt(text);
/* 378 */         } catch (IllegalArgumentException iae) {
/* 379 */           return (Short)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid Short value", new Object[0]);
/*     */         } 
/*     */ 
/*     */         
/* 383 */         if (_shortOverflow(value)) {
/* 384 */           return (Short)ctxt.handleWeirdStringValue(this._valueClass, text, "overflow, value cannot be represented as 16-bit value", new Object[0]);
/*     */         }
/*     */         
/* 387 */         return Short.valueOf((short)value);
/*     */       } 
/* 389 */       if (t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 390 */         if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/* 391 */           _failDoubleToIntCoercion(p, ctxt, "Short");
/*     */         }
/* 393 */         return Short.valueOf(p.getShortValue());
/*     */       } 
/* 395 */       if (t == JsonToken.VALUE_NULL) {
/* 396 */         return (Short)_coerceNullToken(ctxt, this._primitive);
/*     */       }
/* 398 */       if (t == JsonToken.START_ARRAY) {
/* 399 */         return _deserializeFromArray(p, ctxt);
/*     */       }
/* 401 */       return (Short)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class CharacterDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Character>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 411 */     static final CharacterDeserializer primitiveInstance = new CharacterDeserializer((Class)char.class, Character.valueOf(false));
/* 412 */     static final CharacterDeserializer wrapperInstance = new CharacterDeserializer(Character.class, null);
/*     */ 
/*     */     
/*     */     public CharacterDeserializer(Class<Character> cls, Character nvl) {
/* 416 */       super(cls, nvl, Character.valueOf(false));
/*     */     }
/*     */ 
/*     */     
/*     */     public Character deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       int value;
/*     */       String text;
/* 423 */       switch (p.getCurrentTokenId()) {
/*     */         case 7:
/* 425 */           _verifyNumberForScalarCoercion(ctxt, p);
/* 426 */           value = p.getIntValue();
/* 427 */           if (value >= 0 && value <= 65535) {
/* 428 */             return Character.valueOf((char)value);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 6:
/* 433 */           text = p.getText();
/* 434 */           if (text.length() == 1) {
/* 435 */             return Character.valueOf(text.charAt(0));
/*     */           }
/*     */           
/* 438 */           if (text.length() == 0) {
/* 439 */             return (Character)_coerceEmptyString(ctxt, this._primitive);
/*     */           }
/*     */           break;
/*     */         case 11:
/* 443 */           return (Character)_coerceNullToken(ctxt, this._primitive);
/*     */         case 3:
/* 445 */           return _deserializeFromArray(p, ctxt);
/*     */       } 
/*     */       
/* 448 */       return (Character)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class IntegerDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Integer>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 458 */     static final IntegerDeserializer primitiveInstance = new IntegerDeserializer((Class)int.class, Integer.valueOf(0));
/* 459 */     static final IntegerDeserializer wrapperInstance = new IntegerDeserializer(Integer.class, null);
/*     */     
/*     */     public IntegerDeserializer(Class<Integer> cls, Integer nvl) {
/* 462 */       super(cls, nvl, Integer.valueOf(0));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCachable() {
/* 467 */       return true;
/*     */     }
/*     */     
/*     */     public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 471 */       if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/* 472 */         return Integer.valueOf(p.getIntValue());
/*     */       }
/* 474 */       return _parseInteger(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Integer deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 483 */       if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/* 484 */         return Integer.valueOf(p.getIntValue());
/*     */       }
/* 486 */       return _parseInteger(p, ctxt);
/*     */     }
/*     */     protected final Integer _parseInteger(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       String text;
/*     */       int len;
/* 491 */       switch (p.getCurrentTokenId()) {
/*     */         
/*     */         case 7:
/* 494 */           return Integer.valueOf(p.getIntValue());
/*     */         case 8:
/* 496 */           if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/* 497 */             _failDoubleToIntCoercion(p, ctxt, "Integer");
/*     */           }
/* 499 */           return Integer.valueOf(p.getValueAsInt());
/*     */         case 6:
/* 501 */           text = p.getText().trim();
/* 502 */           len = text.length();
/* 503 */           if (len == 0) {
/* 504 */             return (Integer)_coerceEmptyString(ctxt, this._primitive);
/*     */           }
/* 506 */           if (_hasTextualNull(text)) {
/* 507 */             return (Integer)_coerceTextualNull(ctxt, this._primitive);
/*     */           }
/* 509 */           _verifyStringForScalarCoercion(ctxt, text);
/*     */           try {
/* 511 */             if (len > 9) {
/* 512 */               long l = Long.parseLong(text);
/* 513 */               if (_intOverflow(l)) {
/* 514 */                 return (Integer)ctxt.handleWeirdStringValue(this._valueClass, text, String.format("Overflow: numeric value (%s) out of range of Integer (%d - %d)", new Object[] { text, Integer.valueOf(-2147483648), Integer.valueOf(2147483647) }), new Object[0]);
/*     */               }
/*     */ 
/*     */               
/* 518 */               return Integer.valueOf((int)l);
/*     */             } 
/* 520 */             return Integer.valueOf(NumberInput.parseInt(text));
/* 521 */           } catch (IllegalArgumentException iae) {
/* 522 */             return (Integer)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid Integer value", new Object[0]);
/*     */           } 
/*     */         
/*     */         case 11:
/* 526 */           return (Integer)_coerceNullToken(ctxt, this._primitive);
/*     */         case 3:
/* 528 */           return _deserializeFromArray(p, ctxt);
/*     */       } 
/*     */       
/* 531 */       return (Integer)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class LongDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Long>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 541 */     static final LongDeserializer primitiveInstance = new LongDeserializer((Class)long.class, Long.valueOf(0L));
/* 542 */     static final LongDeserializer wrapperInstance = new LongDeserializer(Long.class, null);
/*     */     
/*     */     public LongDeserializer(Class<Long> cls, Long nvl) {
/* 545 */       super(cls, nvl, Long.valueOf(0L));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCachable() {
/* 550 */       return true;
/*     */     }
/*     */     
/*     */     public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 554 */       if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/* 555 */         return Long.valueOf(p.getLongValue());
/*     */       }
/* 557 */       return _parseLong(p, ctxt);
/*     */     }
/*     */     
/*     */     protected final Long _parseLong(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       String text;
/* 562 */       switch (p.getCurrentTokenId()) {
/*     */         
/*     */         case 7:
/* 565 */           return Long.valueOf(p.getLongValue());
/*     */         case 8:
/* 567 */           if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/* 568 */             _failDoubleToIntCoercion(p, ctxt, "Long");
/*     */           }
/* 570 */           return Long.valueOf(p.getValueAsLong());
/*     */         case 6:
/* 572 */           text = p.getText().trim();
/* 573 */           if (text.length() == 0) {
/* 574 */             return (Long)_coerceEmptyString(ctxt, this._primitive);
/*     */           }
/* 576 */           if (_hasTextualNull(text)) {
/* 577 */             return (Long)_coerceTextualNull(ctxt, this._primitive);
/*     */           }
/* 579 */           _verifyStringForScalarCoercion(ctxt, text);
/*     */           
/*     */           try {
/* 582 */             return Long.valueOf(NumberInput.parseLong(text));
/* 583 */           } catch (IllegalArgumentException iae) {
/* 584 */             return (Long)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid Long value", new Object[0]);
/*     */           } 
/*     */         
/*     */         case 11:
/* 588 */           return (Long)_coerceNullToken(ctxt, this._primitive);
/*     */         case 3:
/* 590 */           return _deserializeFromArray(p, ctxt);
/*     */       } 
/*     */       
/* 593 */       return (Long)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class FloatDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Float>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 603 */     static final FloatDeserializer primitiveInstance = new FloatDeserializer((Class)float.class, Float.valueOf(0.0F));
/* 604 */     static final FloatDeserializer wrapperInstance = new FloatDeserializer(Float.class, null);
/*     */     
/*     */     public FloatDeserializer(Class<Float> cls, Float nvl) {
/* 607 */       super(cls, nvl, Float.valueOf(0.0F));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Float deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 613 */       return _parseFloat(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final Float _parseFloat(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 620 */       JsonToken t = p.getCurrentToken();
/*     */       
/* 622 */       if (t == JsonToken.VALUE_NUMBER_FLOAT || t == JsonToken.VALUE_NUMBER_INT) {
/* 623 */         return Float.valueOf(p.getFloatValue());
/*     */       }
/*     */       
/* 626 */       if (t == JsonToken.VALUE_STRING) {
/* 627 */         String text = p.getText().trim();
/* 628 */         if (text.length() == 0) {
/* 629 */           return (Float)_coerceEmptyString(ctxt, this._primitive);
/*     */         }
/* 631 */         if (_hasTextualNull(text)) {
/* 632 */           return (Float)_coerceTextualNull(ctxt, this._primitive);
/*     */         }
/* 634 */         switch (text.charAt(0)) {
/*     */           case 'I':
/* 636 */             if (_isPosInf(text)) {
/* 637 */               return Float.valueOf(Float.POSITIVE_INFINITY);
/*     */             }
/*     */             break;
/*     */           case 'N':
/* 641 */             if (_isNaN(text)) {
/* 642 */               return Float.valueOf(Float.NaN);
/*     */             }
/*     */             break;
/*     */           case '-':
/* 646 */             if (_isNegInf(text)) {
/* 647 */               return Float.valueOf(Float.NEGATIVE_INFINITY);
/*     */             }
/*     */             break;
/*     */         } 
/* 651 */         _verifyStringForScalarCoercion(ctxt, text);
/*     */         try {
/* 653 */           return Float.valueOf(Float.parseFloat(text));
/* 654 */         } catch (IllegalArgumentException iae) {
/* 655 */           return (Float)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid Float value", new Object[0]);
/*     */         } 
/*     */       } 
/* 658 */       if (t == JsonToken.VALUE_NULL) {
/* 659 */         return (Float)_coerceNullToken(ctxt, this._primitive);
/*     */       }
/* 661 */       if (t == JsonToken.START_ARRAY) {
/* 662 */         return _deserializeFromArray(p, ctxt);
/*     */       }
/*     */       
/* 665 */       return (Float)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class DoubleDeserializer
/*     */     extends PrimitiveOrWrapperDeserializer<Double>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 675 */     static final DoubleDeserializer primitiveInstance = new DoubleDeserializer((Class)double.class, Double.valueOf(0.0D));
/* 676 */     static final DoubleDeserializer wrapperInstance = new DoubleDeserializer(Double.class, null);
/*     */     
/*     */     public DoubleDeserializer(Class<Double> cls, Double nvl) {
/* 679 */       super(cls, nvl, Double.valueOf(0.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 684 */       return _parseDouble(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Double deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 693 */       return _parseDouble(p, ctxt);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final Double _parseDouble(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 698 */       JsonToken t = p.getCurrentToken();
/* 699 */       if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 700 */         return Double.valueOf(p.getDoubleValue());
/*     */       }
/* 702 */       if (t == JsonToken.VALUE_STRING) {
/* 703 */         String text = p.getText().trim();
/* 704 */         if (text.length() == 0) {
/* 705 */           return (Double)_coerceEmptyString(ctxt, this._primitive);
/*     */         }
/* 707 */         if (_hasTextualNull(text)) {
/* 708 */           return (Double)_coerceTextualNull(ctxt, this._primitive);
/*     */         }
/* 710 */         switch (text.charAt(0)) {
/*     */           case 'I':
/* 712 */             if (_isPosInf(text)) {
/* 713 */               return Double.valueOf(Double.POSITIVE_INFINITY);
/*     */             }
/*     */             break;
/*     */           case 'N':
/* 717 */             if (_isNaN(text)) {
/* 718 */               return Double.valueOf(Double.NaN);
/*     */             }
/*     */             break;
/*     */           case '-':
/* 722 */             if (_isNegInf(text)) {
/* 723 */               return Double.valueOf(Double.NEGATIVE_INFINITY);
/*     */             }
/*     */             break;
/*     */         } 
/* 727 */         _verifyStringForScalarCoercion(ctxt, text);
/*     */         try {
/* 729 */           return Double.valueOf(parseDouble(text));
/* 730 */         } catch (IllegalArgumentException iae) {
/* 731 */           return (Double)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid Double value", new Object[0]);
/*     */         } 
/*     */       } 
/* 734 */       if (t == JsonToken.VALUE_NULL) {
/* 735 */         return (Double)_coerceNullToken(ctxt, this._primitive);
/*     */       }
/* 737 */       if (t == JsonToken.START_ARRAY) {
/* 738 */         return _deserializeFromArray(p, ctxt);
/*     */       }
/*     */       
/* 741 */       return (Double)ctxt.handleUnexpectedToken(this._valueClass, p);
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
/*     */   @JacksonStdImpl
/*     */   public static class NumberDeserializer
/*     */     extends StdScalarDeserializer<Object>
/*     */   {
/* 760 */     public static final NumberDeserializer instance = new NumberDeserializer();
/*     */     
/*     */     public NumberDeserializer() {
/* 763 */       super(Number.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       String text;
/* 769 */       switch (p.getCurrentTokenId()) {
/*     */         case 7:
/* 771 */           if (ctxt.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
/* 772 */             return _coerceIntegral(p, ctxt);
/*     */           }
/* 774 */           return p.getNumberValue();
/*     */         
/*     */         case 8:
/* 777 */           if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS))
/*     */           {
/* 779 */             if (!p.isNaN()) {
/* 780 */               return p.getDecimalValue();
/*     */             }
/*     */           }
/* 783 */           return p.getNumberValue();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 6:
/* 789 */           text = p.getText().trim();
/* 790 */           if (text.length() == 0)
/*     */           {
/* 792 */             return getNullValue(ctxt);
/*     */           }
/* 794 */           if (_hasTextualNull(text))
/*     */           {
/* 796 */             return getNullValue(ctxt);
/*     */           }
/* 798 */           if (_isPosInf(text)) {
/* 799 */             return Double.valueOf(Double.POSITIVE_INFINITY);
/*     */           }
/* 801 */           if (_isNegInf(text)) {
/* 802 */             return Double.valueOf(Double.NEGATIVE_INFINITY);
/*     */           }
/* 804 */           if (_isNaN(text)) {
/* 805 */             return Double.valueOf(Double.NaN);
/*     */           }
/* 807 */           _verifyStringForScalarCoercion(ctxt, text);
/*     */           try {
/* 809 */             if (!_isIntNumber(text)) {
/* 810 */               if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 811 */                 return new BigDecimal(text);
/*     */               }
/* 813 */               return Double.valueOf(text);
/*     */             } 
/* 815 */             if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
/* 816 */               return new BigInteger(text);
/*     */             }
/* 818 */             long value = Long.parseLong(text);
/* 819 */             if (!ctxt.isEnabled(DeserializationFeature.USE_LONG_FOR_INTS) && 
/* 820 */               value <= 2147483647L && value >= -2147483648L) {
/* 821 */               return Integer.valueOf((int)value);
/*     */             }
/*     */             
/* 824 */             return Long.valueOf(value);
/* 825 */           } catch (IllegalArgumentException iae) {
/* 826 */             return ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid number", new Object[0]);
/*     */           } 
/*     */         
/*     */         case 3:
/* 830 */           return _deserializeFromArray(p, ctxt);
/*     */       } 
/*     */       
/* 833 */       return ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 847 */       switch (p.getCurrentTokenId()) {
/*     */         
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/* 852 */           return deserialize(p, ctxt);
/*     */       } 
/* 854 */       return typeDeserializer.deserializeTypedFromScalar(p, ctxt);
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
/*     */   @JacksonStdImpl
/*     */   public static class BigIntegerDeserializer
/*     */     extends StdScalarDeserializer<BigInteger>
/*     */   {
/* 874 */     public static final BigIntegerDeserializer instance = new BigIntegerDeserializer();
/*     */     public BigIntegerDeserializer() {
/* 876 */       super(BigInteger.class);
/*     */     }
/*     */     
/*     */     public Object getEmptyValue(DeserializationContext ctxt) {
/* 880 */       return BigInteger.ZERO;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BigInteger deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       String text;
/* 887 */       switch (p.getCurrentTokenId()) {
/*     */         case 7:
/* 889 */           switch (p.getNumberType()) {
/*     */             case INT:
/*     */             case LONG:
/*     */             case BIG_INTEGER:
/* 893 */               return p.getBigIntegerValue();
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 897 */           if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/* 898 */             _failDoubleToIntCoercion(p, ctxt, "java.math.BigInteger");
/*     */           }
/* 900 */           return p.getDecimalValue().toBigInteger();
/*     */         case 3:
/* 902 */           return _deserializeFromArray(p, ctxt);
/*     */         case 6:
/* 904 */           text = p.getText().trim();
/*     */           
/* 906 */           if (_isEmptyOrTextualNull(text)) {
/* 907 */             _verifyNullForScalarCoercion(ctxt, text);
/* 908 */             return (BigInteger)getNullValue(ctxt);
/*     */           } 
/* 910 */           _verifyStringForScalarCoercion(ctxt, text);
/*     */           try {
/* 912 */             return new BigInteger(text);
/* 913 */           } catch (IllegalArgumentException iae) {
/* 914 */             return (BigInteger)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid representation", new Object[0]);
/*     */           } 
/*     */       } 
/*     */       
/* 918 */       return (BigInteger)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class BigDecimalDeserializer
/*     */     extends StdScalarDeserializer<BigDecimal>
/*     */   {
/* 927 */     public static final BigDecimalDeserializer instance = new BigDecimalDeserializer();
/*     */     public BigDecimalDeserializer() {
/* 929 */       super(BigDecimal.class);
/*     */     }
/*     */     
/*     */     public Object getEmptyValue(DeserializationContext ctxt) {
/* 933 */       return BigDecimal.ZERO;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */       String text;
/* 940 */       switch (p.getCurrentTokenId()) {
/*     */         case 7:
/*     */         case 8:
/* 943 */           return p.getDecimalValue();
/*     */         case 6:
/* 945 */           text = p.getText().trim();
/*     */           
/* 947 */           if (_isEmptyOrTextualNull(text)) {
/* 948 */             _verifyNullForScalarCoercion(ctxt, text);
/* 949 */             return (BigDecimal)getNullValue(ctxt);
/*     */           } 
/* 951 */           _verifyStringForScalarCoercion(ctxt, text);
/*     */           try {
/* 953 */             return new BigDecimal(text);
/* 954 */           } catch (IllegalArgumentException iae) {
/* 955 */             return (BigDecimal)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid representation", new Object[0]);
/*     */           } 
/*     */         case 3:
/* 958 */           return _deserializeFromArray(p, ctxt);
/*     */       } 
/*     */       
/* 961 */       return (BigDecimal)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\NumberDeserializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */