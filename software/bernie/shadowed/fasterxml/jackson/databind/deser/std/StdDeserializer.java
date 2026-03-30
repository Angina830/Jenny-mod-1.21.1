/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Map;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.Nulls;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerBase;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.NullsAsEmptyProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.NullsFailProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
/*      */ 
/*      */ public abstract class StdDeserializer<T>
/*      */   extends JsonDeserializer<T>
/*      */   implements Serializable {
/*      */   private static final long serialVersionUID = 1L;
/*   43 */   protected static final int F_MASK_INT_COERCIONS = DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.getMask() | DeserializationFeature.USE_LONG_FOR_INTS.getMask();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   48 */   protected static final int F_MASK_ACCEPT_ARRAYS = DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS.getMask() | DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT.getMask();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> _valueClass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StdDeserializer(Class<?> vc) {
/*   62 */     this._valueClass = vc;
/*      */   }
/*      */   
/*      */   protected StdDeserializer(JavaType valueType) {
/*   66 */     this._valueClass = valueType.getRawClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StdDeserializer(StdDeserializer<?> src) {
/*   76 */     this._valueClass = src._valueClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> handledType() {
/*   86 */     return this._valueClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final Class<?> getValueClass() {
/*   98 */     return this._valueClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType getValueType() {
/*  105 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDefaultDeserializer(JsonDeserializer<?> deserializer) {
/*  114 */     return ClassUtil.isJacksonStdImpl(deserializer);
/*      */   }
/*      */   
/*      */   protected boolean isDefaultKeyDeserializer(KeyDeserializer keyDeser) {
/*  118 */     return ClassUtil.isJacksonStdImpl(keyDeser);
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
/*      */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/*  135 */     return typeDeserializer.deserializeTypedFromAny(p, ctxt);
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
/*      */   protected final boolean _parseBooleanPrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  148 */     JsonToken t = p.getCurrentToken();
/*  149 */     if (t == JsonToken.VALUE_TRUE) return true; 
/*  150 */     if (t == JsonToken.VALUE_FALSE) return false; 
/*  151 */     if (t == JsonToken.VALUE_NULL) {
/*  152 */       _verifyNullForPrimitive(ctxt);
/*  153 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  157 */     if (t == JsonToken.VALUE_NUMBER_INT) {
/*  158 */       return _parseBooleanFromInt(p, ctxt);
/*      */     }
/*      */     
/*  161 */     if (t == JsonToken.VALUE_STRING) {
/*  162 */       String text = p.getText().trim();
/*      */       
/*  164 */       if ("true".equals(text) || "True".equals(text)) {
/*  165 */         return true;
/*      */       }
/*  167 */       if ("false".equals(text) || "False".equals(text)) {
/*  168 */         return false;
/*      */       }
/*  170 */       if (_isEmptyOrTextualNull(text)) {
/*  171 */         _verifyNullForPrimitiveCoercion(ctxt, text);
/*  172 */         return false;
/*      */       } 
/*  174 */       Boolean b = (Boolean)ctxt.handleWeirdStringValue(this._valueClass, text, "only \"true\" or \"false\" recognized", new Object[0]);
/*      */       
/*  176 */       return Boolean.TRUE.equals(b);
/*      */     } 
/*      */     
/*  179 */     if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  180 */       p.nextToken();
/*  181 */       boolean parsed = _parseBooleanPrimitive(p, ctxt);
/*  182 */       _verifyEndArrayForSingle(p, ctxt);
/*  183 */       return parsed;
/*      */     } 
/*      */     
/*  186 */     return ((Boolean)ctxt.handleUnexpectedToken(this._valueClass, p)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _parseBooleanFromInt(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  196 */     _verifyNumberForScalarCoercion(ctxt, p);
/*      */ 
/*      */     
/*  199 */     return !"0".equals(p.getText());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final byte _parseBytePrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  205 */     int value = _parseIntPrimitive(p, ctxt);
/*      */     
/*  207 */     if (_byteOverflow(value)) {
/*  208 */       Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, String.valueOf(value), "overflow, value cannot be represented as 8-bit value", new Object[0]);
/*      */       
/*  210 */       return _nonNullNumber(v).byteValue();
/*      */     } 
/*  212 */     return (byte)value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final short _parseShortPrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  218 */     int value = _parseIntPrimitive(p, ctxt);
/*      */     
/*  220 */     if (_shortOverflow(value)) {
/*  221 */       Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, String.valueOf(value), "overflow, value cannot be represented as 16-bit value", new Object[0]);
/*      */       
/*  223 */       return _nonNullNumber(v).shortValue();
/*      */     } 
/*  225 */     return (short)value;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final int _parseIntPrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     String text;
/*  231 */     if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/*  232 */       return p.getIntValue();
/*      */     }
/*  234 */     switch (p.getCurrentTokenId()) {
/*      */       case 6:
/*  236 */         text = p.getText().trim();
/*  237 */         if (_isEmptyOrTextualNull(text)) {
/*  238 */           _verifyNullForPrimitiveCoercion(ctxt, text);
/*  239 */           return 0;
/*      */         } 
/*  241 */         return _parseIntPrimitive(ctxt, text);
/*      */       case 8:
/*  243 */         if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/*  244 */           _failDoubleToIntCoercion(p, ctxt, "int");
/*      */         }
/*  246 */         return p.getValueAsInt();
/*      */       case 11:
/*  248 */         _verifyNullForPrimitive(ctxt);
/*  249 */         return 0;
/*      */       case 3:
/*  251 */         if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  252 */           p.nextToken();
/*  253 */           int parsed = _parseIntPrimitive(p, ctxt);
/*  254 */           _verifyEndArrayForSingle(p, ctxt);
/*  255 */           return parsed;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  261 */     return ((Number)ctxt.handleUnexpectedToken(this._valueClass, p)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _parseIntPrimitive(DeserializationContext ctxt, String text) throws IOException {
/*      */     try {
/*  270 */       if (text.length() > 9) {
/*  271 */         long l = Long.parseLong(text);
/*  272 */         if (_intOverflow(l)) {
/*  273 */           Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, text, "Overflow: numeric value (%s) out of range of int (%d -%d)", new Object[] { text, Integer.valueOf(-2147483648), Integer.valueOf(2147483647) });
/*      */ 
/*      */           
/*  276 */           return _nonNullNumber(v).intValue();
/*      */         } 
/*  278 */         return (int)l;
/*      */       } 
/*  280 */       return NumberInput.parseInt(text);
/*  281 */     } catch (IllegalArgumentException iae) {
/*  282 */       Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid int value", new Object[0]);
/*      */       
/*  284 */       return _nonNullNumber(v).intValue();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final long _parseLongPrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     String text;
/*  291 */     if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/*  292 */       return p.getLongValue();
/*      */     }
/*  294 */     switch (p.getCurrentTokenId()) {
/*      */       case 6:
/*  296 */         text = p.getText().trim();
/*  297 */         if (_isEmptyOrTextualNull(text)) {
/*  298 */           _verifyNullForPrimitiveCoercion(ctxt, text);
/*  299 */           return 0L;
/*      */         } 
/*  301 */         return _parseLongPrimitive(ctxt, text);
/*      */       case 8:
/*  303 */         if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
/*  304 */           _failDoubleToIntCoercion(p, ctxt, "long");
/*      */         }
/*  306 */         return p.getValueAsLong();
/*      */       case 11:
/*  308 */         _verifyNullForPrimitive(ctxt);
/*  309 */         return 0L;
/*      */       case 3:
/*  311 */         if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  312 */           p.nextToken();
/*  313 */           long parsed = _parseLongPrimitive(p, ctxt);
/*  314 */           _verifyEndArrayForSingle(p, ctxt);
/*  315 */           return parsed;
/*      */         } 
/*      */         break;
/*      */     } 
/*  319 */     return ((Number)ctxt.handleUnexpectedToken(this._valueClass, p)).longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final long _parseLongPrimitive(DeserializationContext ctxt, String text) throws IOException {
/*      */     try {
/*  328 */       return NumberInput.parseLong(text);
/*  329 */     } catch (IllegalArgumentException iae) {
/*      */       
/*  331 */       Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid long value", new Object[0]);
/*      */       
/*  333 */       return _nonNullNumber(v).longValue();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final float _parseFloatPrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     String text;
/*  340 */     if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
/*  341 */       return p.getFloatValue();
/*      */     }
/*  343 */     switch (p.getCurrentTokenId()) {
/*      */       case 6:
/*  345 */         text = p.getText().trim();
/*  346 */         if (_isEmptyOrTextualNull(text)) {
/*  347 */           _verifyNullForPrimitiveCoercion(ctxt, text);
/*  348 */           return 0.0F;
/*      */         } 
/*  350 */         return _parseFloatPrimitive(ctxt, text);
/*      */       case 7:
/*  352 */         return p.getFloatValue();
/*      */       case 11:
/*  354 */         _verifyNullForPrimitive(ctxt);
/*  355 */         return 0.0F;
/*      */       case 3:
/*  357 */         if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  358 */           p.nextToken();
/*  359 */           float parsed = _parseFloatPrimitive(p, ctxt);
/*  360 */           _verifyEndArrayForSingle(p, ctxt);
/*  361 */           return parsed;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/*  366 */     return ((Number)ctxt.handleUnexpectedToken(this._valueClass, p)).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final float _parseFloatPrimitive(DeserializationContext ctxt, String text) throws IOException {
/*  375 */     switch (text.charAt(0)) {
/*      */       case 'I':
/*  377 */         if (_isPosInf(text)) {
/*  378 */           return Float.POSITIVE_INFINITY;
/*      */         }
/*      */         break;
/*      */       case 'N':
/*  382 */         if (_isNaN(text)) return Float.NaN; 
/*      */         break;
/*      */       case '-':
/*  385 */         if (_isNegInf(text)) {
/*  386 */           return Float.NEGATIVE_INFINITY;
/*      */         }
/*      */         break;
/*      */     } 
/*      */     try {
/*  391 */       return Float.parseFloat(text);
/*  392 */     } catch (IllegalArgumentException iae) {
/*  393 */       Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid float value", new Object[0]);
/*      */       
/*  395 */       return _nonNullNumber(v).floatValue();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final double _parseDoublePrimitive(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     String text;
/*  401 */     if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
/*  402 */       return p.getDoubleValue();
/*      */     }
/*  404 */     switch (p.getCurrentTokenId()) {
/*      */       case 6:
/*  406 */         text = p.getText().trim();
/*  407 */         if (_isEmptyOrTextualNull(text)) {
/*  408 */           _verifyNullForPrimitiveCoercion(ctxt, text);
/*  409 */           return 0.0D;
/*      */         } 
/*  411 */         return _parseDoublePrimitive(ctxt, text);
/*      */       case 7:
/*  413 */         return p.getDoubleValue();
/*      */       case 11:
/*  415 */         _verifyNullForPrimitive(ctxt);
/*  416 */         return 0.0D;
/*      */       case 3:
/*  418 */         if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  419 */           p.nextToken();
/*  420 */           double parsed = _parseDoublePrimitive(p, ctxt);
/*  421 */           _verifyEndArrayForSingle(p, ctxt);
/*  422 */           return parsed;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/*  427 */     return ((Number)ctxt.handleUnexpectedToken(this._valueClass, p)).doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final double _parseDoublePrimitive(DeserializationContext ctxt, String text) throws IOException {
/*  436 */     switch (text.charAt(0)) {
/*      */       case 'I':
/*  438 */         if (_isPosInf(text)) {
/*  439 */           return Double.POSITIVE_INFINITY;
/*      */         }
/*      */         break;
/*      */       case 'N':
/*  443 */         if (_isNaN(text)) {
/*  444 */           return Double.NaN;
/*      */         }
/*      */         break;
/*      */       case '-':
/*  448 */         if (_isNegInf(text)) {
/*  449 */           return Double.NEGATIVE_INFINITY;
/*      */         }
/*      */         break;
/*      */     } 
/*      */     try {
/*  454 */       return parseDouble(text);
/*  455 */     } catch (IllegalArgumentException iae) {
/*  456 */       Number v = (Number)ctxt.handleWeirdStringValue(this._valueClass, text, "not a valid double value (as String to convert)", new Object[0]);
/*      */       
/*  458 */       return _nonNullNumber(v).doubleValue();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Date _parseDate(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     long l;
/*  464 */     switch (p.getCurrentTokenId()) {
/*      */       case 6:
/*  466 */         return _parseDate(p.getText().trim(), ctxt);
/*      */ 
/*      */       
/*      */       case 7:
/*      */         try {
/*  471 */           l = p.getLongValue();
/*  472 */         } catch (JsonParseException e) {
/*  473 */           Number v = (Number)ctxt.handleWeirdNumberValue(this._valueClass, p.getNumberValue(), "not a valid 64-bit long for creating `java.util.Date`", new Object[0]);
/*      */           
/*  475 */           l = v.longValue();
/*      */         } 
/*  477 */         return new Date(l);
/*      */       
/*      */       case 11:
/*  480 */         return (Date)getNullValue(ctxt);
/*      */       case 3:
/*  482 */         return _parseDateFromArray(p, ctxt);
/*      */     } 
/*  484 */     return (Date)ctxt.handleUnexpectedToken(this._valueClass, p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Date _parseDateFromArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     JsonToken t;
/*  492 */     if (ctxt.hasSomeOfFeatures(F_MASK_ACCEPT_ARRAYS)) {
/*  493 */       t = p.nextToken();
/*  494 */       if (t == JsonToken.END_ARRAY && 
/*  495 */         ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
/*  496 */         return (Date)getNullValue(ctxt);
/*      */       }
/*      */       
/*  499 */       if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  500 */         Date parsed = _parseDate(p, ctxt);
/*  501 */         _verifyEndArrayForSingle(p, ctxt);
/*  502 */         return parsed;
/*      */       } 
/*      */     } else {
/*  505 */       t = p.getCurrentToken();
/*      */     } 
/*  507 */     return (Date)ctxt.handleUnexpectedToken(this._valueClass, t, p, null, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Date _parseDate(String value, DeserializationContext ctxt) throws IOException {
/*      */     try {
/*  518 */       if (_isEmptyOrTextualNull(value)) {
/*  519 */         return (Date)getNullValue(ctxt);
/*      */       }
/*  521 */       return ctxt.parseDate(value);
/*  522 */     } catch (IllegalArgumentException iae) {
/*  523 */       return (Date)ctxt.handleWeirdStringValue(this._valueClass, value, "not a valid representation (error: %s)", new Object[] { iae.getMessage() });
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
/*      */   protected static final double parseDouble(String numStr) throws NumberFormatException {
/*  535 */     if ("2.2250738585072012e-308".equals(numStr)) {
/*  536 */       return 2.2250738585072014E-308D;
/*      */     }
/*  538 */     return Double.parseDouble(numStr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String _parseString(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  549 */     JsonToken t = p.getCurrentToken();
/*  550 */     if (t == JsonToken.VALUE_STRING) {
/*  551 */       return p.getText();
/*      */     }
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
/*  564 */     String value = p.getValueAsString();
/*  565 */     if (value != null) {
/*  566 */       return value;
/*      */     }
/*  568 */     return (String)ctxt.handleUnexpectedToken(String.class, p);
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
/*      */   protected T _deserializeFromEmpty(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  581 */     JsonToken t = p.getCurrentToken();
/*  582 */     if (t == JsonToken.START_ARRAY) {
/*  583 */       if (ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
/*  584 */         t = p.nextToken();
/*  585 */         if (t == JsonToken.END_ARRAY) {
/*  586 */           return null;
/*      */         }
/*  588 */         return (T)ctxt.handleUnexpectedToken(handledType(), p);
/*      */       } 
/*  590 */     } else if (t == JsonToken.VALUE_STRING && 
/*  591 */       ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
/*  592 */       String str = p.getText().trim();
/*  593 */       if (str.isEmpty()) {
/*  594 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  598 */     return (T)ctxt.handleUnexpectedToken(handledType(), p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasTextualNull(String value) {
/*  609 */     return "null".equals(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _isEmptyOrTextualNull(String value) {
/*  616 */     return (value.isEmpty() || "null".equals(value));
/*      */   }
/*      */   
/*      */   protected final boolean _isNegInf(String text) {
/*  620 */     return ("-Infinity".equals(text) || "-INF".equals(text));
/*      */   }
/*      */   
/*      */   protected final boolean _isPosInf(String text) {
/*  624 */     return ("Infinity".equals(text) || "INF".equals(text));
/*      */   }
/*      */   protected final boolean _isNaN(String text) {
/*  627 */     return "NaN".equals(text);
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
/*      */   protected T _deserializeFromArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     JsonToken t;
/*  655 */     if (ctxt.hasSomeOfFeatures(F_MASK_ACCEPT_ARRAYS)) {
/*  656 */       t = p.nextToken();
/*  657 */       if (t == JsonToken.END_ARRAY && 
/*  658 */         ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
/*  659 */         return (T)getNullValue(ctxt);
/*      */       }
/*      */       
/*  662 */       if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  663 */         T parsed = (T)deserialize(p, ctxt);
/*  664 */         if (p.nextToken() != JsonToken.END_ARRAY) {
/*  665 */           handleMissingEndArrayForSingle(p, ctxt);
/*      */         }
/*  667 */         return parsed;
/*      */       } 
/*      */     } else {
/*  670 */       t = p.getCurrentToken();
/*      */     } 
/*      */     
/*  673 */     T result = (T)ctxt.handleUnexpectedToken(this._valueClass, t, p, null, new Object[0]);
/*  674 */     return result;
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
/*      */   protected T _deserializeWrappedValue(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  689 */     if (p.hasToken(JsonToken.START_ARRAY)) {
/*  690 */       String msg = String.format("Cannot deserialize instance of %s out of %s token: nested Arrays not allowed with %s", new Object[] { ClassUtil.nameOf(this._valueClass), JsonToken.START_ARRAY, "DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS" });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  695 */       T result = (T)ctxt.handleUnexpectedToken(this._valueClass, p.getCurrentToken(), p, msg, new Object[0]);
/*  696 */       return result;
/*      */     } 
/*  698 */     return (T)deserialize(p, ctxt);
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
/*      */   protected void _failDoubleToIntCoercion(JsonParser p, DeserializationContext ctxt, String type) throws IOException {
/*  710 */     ctxt.reportInputMismatch(handledType(), "Cannot coerce a floating-point value ('%s') into %s (enable `DeserializationFeature.ACCEPT_FLOAT_AS_INT` to allow)", new Object[] { p.getValueAsString(), type });
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
/*      */   protected Object _coerceIntegral(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  728 */     int feats = ctxt.getDeserializationFeatures();
/*  729 */     if (DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.enabledIn(feats)) {
/*  730 */       return p.getBigIntegerValue();
/*      */     }
/*  732 */     if (DeserializationFeature.USE_LONG_FOR_INTS.enabledIn(feats)) {
/*  733 */       return Long.valueOf(p.getLongValue());
/*      */     }
/*  735 */     return p.getBigIntegerValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _coerceNullToken(DeserializationContext ctxt, boolean isPrimitive) throws JsonMappingException {
/*  746 */     if (isPrimitive) {
/*  747 */       _verifyNullForPrimitive(ctxt);
/*      */     }
/*  749 */     return getNullValue(ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _coerceTextualNull(DeserializationContext ctxt, boolean isPrimitive) throws JsonMappingException {
/*      */     DeserializationFeature deserializationFeature;
/*      */     boolean enable;
/*  762 */     if (!ctxt.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
/*  763 */       MapperFeature mapperFeature = MapperFeature.ALLOW_COERCION_OF_SCALARS;
/*  764 */       enable = true;
/*  765 */     } else if (isPrimitive && ctxt.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
/*  766 */       deserializationFeature = DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
/*  767 */       enable = false;
/*      */     } else {
/*  769 */       return getNullValue(ctxt);
/*      */     } 
/*  771 */     _reportFailedNullCoerce(ctxt, enable, (Enum<?>)deserializationFeature, "String \"null\"");
/*  772 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _coerceEmptyString(DeserializationContext ctxt, boolean isPrimitive) throws JsonMappingException {
/*      */     DeserializationFeature deserializationFeature;
/*      */     boolean enable;
/*  785 */     if (!ctxt.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
/*  786 */       MapperFeature mapperFeature = MapperFeature.ALLOW_COERCION_OF_SCALARS;
/*  787 */       enable = true;
/*  788 */     } else if (isPrimitive && ctxt.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
/*  789 */       deserializationFeature = DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
/*  790 */       enable = false;
/*      */     } else {
/*  792 */       return getNullValue(ctxt);
/*      */     } 
/*  794 */     _reportFailedNullCoerce(ctxt, enable, (Enum<?>)deserializationFeature, "empty String (\"\")");
/*  795 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyNullForPrimitive(DeserializationContext ctxt) throws JsonMappingException {
/*  801 */     if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
/*  802 */       ctxt.reportInputMismatch(this, "Cannot coerce `null` %s (disable `DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES` to allow)", new Object[] { _coercedTypeDesc() });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyNullForPrimitiveCoercion(DeserializationContext ctxt, String str) throws JsonMappingException {
/*      */     DeserializationFeature deserializationFeature;
/*      */     boolean enable;
/*  815 */     if (!ctxt.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
/*  816 */       MapperFeature mapperFeature = MapperFeature.ALLOW_COERCION_OF_SCALARS;
/*  817 */       enable = true;
/*  818 */     } else if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
/*  819 */       deserializationFeature = DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
/*  820 */       enable = false;
/*      */     } else {
/*      */       return;
/*      */     } 
/*  824 */     String strDesc = str.isEmpty() ? "empty String (\"\")" : String.format("String \"%s\"", new Object[] { str });
/*  825 */     _reportFailedNullCoerce(ctxt, enable, (Enum<?>)deserializationFeature, strDesc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyNullForScalarCoercion(DeserializationContext ctxt, String str) throws JsonMappingException {
/*  832 */     if (!ctxt.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
/*  833 */       String strDesc = str.isEmpty() ? "empty String (\"\")" : String.format("String \"%s\"", new Object[] { str });
/*  834 */       _reportFailedNullCoerce(ctxt, true, (Enum<?>)MapperFeature.ALLOW_COERCION_OF_SCALARS, strDesc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _verifyStringForScalarCoercion(DeserializationContext ctxt, String str) throws JsonMappingException {
/*  841 */     MapperFeature feat = MapperFeature.ALLOW_COERCION_OF_SCALARS;
/*  842 */     if (!ctxt.isEnabled(feat)) {
/*  843 */       ctxt.reportInputMismatch(this, "Cannot coerce String \"%s\" %s (enable `%s.%s` to allow)", new Object[] { str, _coercedTypeDesc(), feat.getClass().getSimpleName(), feat.name() });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _verifyNumberForScalarCoercion(DeserializationContext ctxt, JsonParser p) throws IOException {
/*  851 */     MapperFeature feat = MapperFeature.ALLOW_COERCION_OF_SCALARS;
/*  852 */     if (!ctxt.isEnabled(feat)) {
/*      */ 
/*      */       
/*  855 */       String valueDesc = p.getText();
/*  856 */       ctxt.reportInputMismatch(this, "Cannot coerce Number (%s) %s (enable `%s.%s` to allow)", new Object[] { valueDesc, _coercedTypeDesc(), feat.getClass().getSimpleName(), feat.name() });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportFailedNullCoerce(DeserializationContext ctxt, boolean state, Enum<?> feature, String inputDesc) throws JsonMappingException {
/*  864 */     String enableDesc = state ? "enable" : "disable";
/*  865 */     ctxt.reportInputMismatch(this, "Cannot coerce %s to Null value %s (%s `%s.%s` to allow)", new Object[] { inputDesc, _coercedTypeDesc(), enableDesc, feature.getClass().getSimpleName(), feature.name() });
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
/*      */   protected String _coercedTypeDesc() {
/*      */     boolean structured;
/*      */     String typeDesc;
/*  882 */     JavaType t = getValueType();
/*  883 */     if (t != null && !t.isPrimitive()) {
/*  884 */       structured = (t.isContainerType() || t.isReferenceType());
/*      */       
/*  886 */       typeDesc = "'" + t.toString() + "'";
/*      */     } else {
/*  888 */       Class<?> cls = handledType();
/*  889 */       structured = (cls.isArray() || Collection.class.isAssignableFrom(cls) || Map.class.isAssignableFrom(cls));
/*      */       
/*  891 */       typeDesc = ClassUtil.nameOf(cls);
/*      */     } 
/*  893 */     if (structured) {
/*  894 */       return "as content of type " + typeDesc;
/*      */     }
/*  896 */     return "for type " + typeDesc;
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
/*      */   protected JsonDeserializer<Object> findDeserializer(DeserializationContext ctxt, JavaType type, BeanProperty property) throws JsonMappingException {
/*  918 */     return ctxt.findContextualValueDeserializer(type, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _isIntNumber(String text) {
/*  927 */     int len = text.length();
/*  928 */     if (len > 0) {
/*  929 */       char c = text.charAt(0);
/*      */       
/*  931 */       int i = (c == '-' || c == '+') ? 1 : 0;
/*  932 */       for (; i < len; i++) {
/*  933 */         int ch = text.charAt(i);
/*  934 */         if (ch > 57 || ch < 48) {
/*  935 */           return false;
/*      */         }
/*      */       } 
/*  938 */       return true;
/*      */     } 
/*  940 */     return false;
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
/*      */   protected JsonDeserializer<?> findConvertingContentDeserializer(DeserializationContext ctxt, BeanProperty prop, JsonDeserializer<?> existingDeserializer) throws JsonMappingException {
/*  963 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/*  964 */     if (_neitherNull(intr, prop)) {
/*  965 */       AnnotatedMember member = prop.getMember();
/*  966 */       if (member != null) {
/*  967 */         Object convDef = intr.findDeserializationContentConverter(member);
/*  968 */         if (convDef != null) {
/*  969 */           Converter<Object, Object> conv = ctxt.converterInstance((Annotated)prop.getMember(), convDef);
/*  970 */           JavaType delegateType = conv.getInputType(ctxt.getTypeFactory());
/*  971 */           if (existingDeserializer == null) {
/*  972 */             existingDeserializer = ctxt.findContextualValueDeserializer(delegateType, prop);
/*      */           }
/*  974 */           return new StdDelegatingDeserializer(conv, delegateType, existingDeserializer);
/*      */         } 
/*      */       } 
/*      */     } 
/*  978 */     return existingDeserializer;
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
/*      */   protected JsonFormat.Value findFormatOverrides(DeserializationContext ctxt, BeanProperty prop, Class<?> typeForDefaults) {
/*  999 */     if (prop != null) {
/* 1000 */       return prop.findPropertyFormat((MapperConfig)ctxt.getConfig(), typeForDefaults);
/*      */     }
/*      */     
/* 1003 */     return ctxt.getDefaultPropertyFormat(typeForDefaults);
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
/*      */   protected Boolean findFormatFeature(DeserializationContext ctxt, BeanProperty prop, Class<?> typeForDefaults, JsonFormat.Feature feat) {
/* 1019 */     JsonFormat.Value format = findFormatOverrides(ctxt, prop, typeForDefaults);
/* 1020 */     if (format != null) {
/* 1021 */       return format.getFeature(feat);
/*      */     }
/* 1023 */     return null;
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
/*      */   protected final NullValueProvider findValueNullProvider(DeserializationContext ctxt, SettableBeanProperty prop, PropertyMetadata propMetadata) throws JsonMappingException {
/* 1037 */     if (prop != null) {
/* 1038 */       return _findNullProvider(ctxt, (BeanProperty)prop, propMetadata.getValueNulls(), prop.getValueDeserializer());
/*      */     }
/*      */     
/* 1041 */     return null;
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
/*      */   protected NullValueProvider findContentNullProvider(DeserializationContext ctxt, BeanProperty prop, JsonDeserializer<?> valueDeser) throws JsonMappingException {
/* 1056 */     Nulls nulls = findContentNullStyle(ctxt, prop);
/* 1057 */     if (nulls == Nulls.SKIP) {
/* 1058 */       return (NullValueProvider)NullsConstantProvider.skipper();
/*      */     }
/* 1060 */     NullValueProvider prov = _findNullProvider(ctxt, prop, nulls, valueDeser);
/* 1061 */     if (prov != null) {
/* 1062 */       return prov;
/*      */     }
/* 1064 */     return (NullValueProvider)valueDeser;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Nulls findContentNullStyle(DeserializationContext ctxt, BeanProperty prop) throws JsonMappingException {
/* 1070 */     if (prop != null) {
/* 1071 */       return prop.getMetadata().getContentNulls();
/*      */     }
/* 1073 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final NullValueProvider _findNullProvider(DeserializationContext ctxt, BeanProperty prop, Nulls nulls, JsonDeserializer<?> valueDeser) throws JsonMappingException {
/* 1081 */     if (nulls == Nulls.FAIL) {
/* 1082 */       if (prop == null) {
/* 1083 */         return (NullValueProvider)NullsFailProvider.constructForRootValue(ctxt.constructType(valueDeser.handledType()));
/*      */       }
/* 1085 */       return (NullValueProvider)NullsFailProvider.constructForProperty(prop);
/*      */     } 
/* 1087 */     if (nulls == Nulls.AS_EMPTY) {
/*      */ 
/*      */       
/* 1090 */       if (valueDeser == null) {
/* 1091 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1097 */       if (valueDeser instanceof BeanDeserializerBase) {
/* 1098 */         ValueInstantiator vi = ((BeanDeserializerBase)valueDeser).getValueInstantiator();
/* 1099 */         if (!vi.canCreateUsingDefault()) {
/* 1100 */           JavaType type = prop.getType();
/* 1101 */           ctxt.reportBadDefinition(type, String.format("Cannot create empty instance of %s, no default Creator", new Object[] { type }));
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1107 */       AccessPattern access = valueDeser.getEmptyAccessPattern();
/* 1108 */       if (access == AccessPattern.ALWAYS_NULL) {
/* 1109 */         return (NullValueProvider)NullsConstantProvider.nuller();
/*      */       }
/* 1111 */       if (access == AccessPattern.CONSTANT) {
/* 1112 */         return (NullValueProvider)NullsConstantProvider.forValue(valueDeser.getEmptyValue(ctxt));
/*      */       }
/*      */       
/* 1115 */       return (NullValueProvider)new NullsAsEmptyProvider(valueDeser);
/*      */     } 
/* 1117 */     if (nulls == Nulls.SKIP) {
/* 1118 */       return (NullValueProvider)NullsConstantProvider.skipper();
/*      */     }
/* 1120 */     return null;
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
/*      */   protected void handleUnknownProperty(JsonParser p, DeserializationContext ctxt, Object<?> instanceOrClass, String propName) throws IOException {
/* 1147 */     if (instanceOrClass == null) {
/* 1148 */       instanceOrClass = (Object<?>)handledType();
/*      */     }
/*      */     
/* 1151 */     if (ctxt.handleUnknownProperty(p, this, instanceOrClass, propName)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1157 */     p.skipChildren();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleMissingEndArrayForSingle(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1163 */     ctxt.reportWrongTokenException(this, JsonToken.END_ARRAY, "Attempted to unwrap '%s' value from an array (with `DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS`) but it contains more than one value", new Object[] { handledType().getName() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _verifyEndArrayForSingle(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1172 */     JsonToken t = p.nextToken();
/* 1173 */     if (t != JsonToken.END_ARRAY) {
/* 1174 */       handleMissingEndArrayForSingle(p, ctxt);
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
/*      */   protected static final boolean _neitherNull(Object a, Object b) {
/* 1188 */     return (a != null && b != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _byteOverflow(int value) {
/* 1197 */     return (value < -128 || value > 255);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _shortOverflow(int value) {
/* 1204 */     return (value < -32768 || value > 32767);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _intOverflow(long value) {
/* 1211 */     return (value < -2147483648L || value > 2147483647L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Number _nonNullNumber(Number n) {
/* 1218 */     if (n == null) {
/* 1219 */       n = Integer.valueOf(0);
/*      */     }
/* 1221 */     return n;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StdDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */