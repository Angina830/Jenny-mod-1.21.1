/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
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
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public final class StringArrayDeserializer
/*     */   extends StdDeserializer<String[]>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*  31 */   private static final String[] NO_STRINGS = new String[0];
/*     */   
/*  33 */   public static final StringArrayDeserializer instance = new StringArrayDeserializer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<String> _elementDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final NullValueProvider _nullProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Boolean _unwrapSingle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _skipNullValues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringArrayDeserializer() {
/*  65 */     this((JsonDeserializer<?>)null, (NullValueProvider)null, (Boolean)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringArrayDeserializer(JsonDeserializer<?> deser, NullValueProvider nuller, Boolean unwrapSingle) {
/*  71 */     super(String[].class);
/*  72 */     this._elementDeserializer = (JsonDeserializer)deser;
/*  73 */     this._nullProvider = nuller;
/*  74 */     this._unwrapSingle = unwrapSingle;
/*  75 */     this._skipNullValues = NullsConstantProvider.isSkipper(nuller);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/*  80 */     return Boolean.TRUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessPattern getEmptyAccessPattern() {
/*  86 */     return AccessPattern.CONSTANT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/*  91 */     return NO_STRINGS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 102 */     JsonDeserializer<?> deser = this._elementDeserializer;
/*     */     
/* 104 */     deser = findConvertingContentDeserializer(ctxt, property, deser);
/* 105 */     JavaType type = ctxt.constructType(String.class);
/* 106 */     if (deser == null) {
/* 107 */       deser = ctxt.findContextualValueDeserializer(type, property);
/*     */     } else {
/* 109 */       deser = ctxt.handleSecondaryContextualization(deser, property, type);
/*     */     } 
/*     */     
/* 112 */     Boolean unwrapSingle = findFormatFeature(ctxt, property, String[].class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
/*     */     
/* 114 */     NullValueProvider nuller = findContentNullProvider(ctxt, property, deser);
/*     */     
/* 116 */     if (deser != null && isDefaultDeserializer(deser)) {
/* 117 */       deser = null;
/*     */     }
/* 119 */     if (this._elementDeserializer == deser && this._unwrapSingle == unwrapSingle && this._nullProvider == nuller)
/*     */     {
/*     */       
/* 122 */       return this;
/*     */     }
/* 124 */     return new StringArrayDeserializer(deser, nuller, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 131 */     if (!p.isExpectedStartArrayToken()) {
/* 132 */       return handleNonArray(p, ctxt);
/*     */     }
/* 134 */     if (this._elementDeserializer != null) {
/* 135 */       return _deserializeCustom(p, ctxt, (String[])null);
/*     */     }
/*     */     
/* 138 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 139 */     Object[] chunk = buffer.resetAndStart();
/*     */     
/* 141 */     int ix = 0;
/*     */     
/*     */     try {
/*     */       while (true) {
/* 145 */         String value = p.nextTextValue();
/* 146 */         if (value == null) {
/* 147 */           JsonToken t = p.getCurrentToken();
/* 148 */           if (t == JsonToken.END_ARRAY) {
/*     */             break;
/*     */           }
/* 151 */           if (t == JsonToken.VALUE_NULL) {
/* 152 */             if (this._skipNullValues) {
/*     */               continue;
/*     */             }
/* 155 */             value = (String)this._nullProvider.getNullValue(ctxt);
/*     */           } else {
/* 157 */             value = _parseString(p, ctxt);
/*     */           } 
/*     */         } 
/* 160 */         if (ix >= chunk.length) {
/* 161 */           chunk = buffer.appendCompletedChunk(chunk);
/* 162 */           ix = 0;
/*     */         } 
/* 164 */         chunk[ix++] = value;
/*     */       } 
/* 166 */     } catch (Exception e) {
/* 167 */       throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
/*     */     } 
/* 169 */     String[] result = (String[])buffer.completeAndClearBuffer(chunk, ix, String.class);
/* 170 */     ctxt.returnObjectBuffer(buffer);
/* 171 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String[] _deserializeCustom(JsonParser p, DeserializationContext ctxt, String[] old) throws IOException {
/*     */     int ix;
/*     */     Object[] chunk;
/* 180 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (old == null) {
/* 185 */       ix = 0;
/* 186 */       chunk = buffer.resetAndStart();
/*     */     } else {
/* 188 */       ix = old.length;
/* 189 */       chunk = buffer.resetAndStart((Object[])old, ix);
/*     */     } 
/*     */     
/* 192 */     JsonDeserializer<String> deser = this._elementDeserializer;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/*     */         String value;
/*     */ 
/*     */ 
/*     */         
/* 202 */         if (p.nextTextValue() == null) {
/* 203 */           JsonToken t = p.getCurrentToken();
/* 204 */           if (t == JsonToken.END_ARRAY) {
/*     */             break;
/*     */           }
/*     */           
/* 208 */           if (t == JsonToken.VALUE_NULL) {
/* 209 */             if (this._skipNullValues) {
/*     */               continue;
/*     */             }
/* 212 */             value = (String)this._nullProvider.getNullValue(ctxt);
/*     */           } else {
/* 214 */             value = (String)deser.deserialize(p, ctxt);
/*     */           } 
/*     */         } else {
/* 217 */           value = (String)deser.deserialize(p, ctxt);
/*     */         } 
/* 219 */         if (ix >= chunk.length) {
/* 220 */           chunk = buffer.appendCompletedChunk(chunk);
/* 221 */           ix = 0;
/*     */         } 
/* 223 */         chunk[ix++] = value;
/*     */       } 
/* 225 */     } catch (Exception e) {
/*     */       
/* 227 */       throw JsonMappingException.wrapWithPath(e, String.class, ix);
/*     */     } 
/* 229 */     String[] result = (String[])buffer.completeAndClearBuffer(chunk, ix, String.class);
/* 230 */     ctxt.returnObjectBuffer(buffer);
/* 231 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 236 */     return typeDeserializer.deserializeTypedFromArray(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] deserialize(JsonParser p, DeserializationContext ctxt, String[] intoValue) throws IOException {
/* 244 */     if (!p.isExpectedStartArrayToken()) {
/* 245 */       String[] arr = handleNonArray(p, ctxt);
/* 246 */       if (arr == null) {
/* 247 */         return intoValue;
/*     */       }
/* 249 */       int offset = intoValue.length;
/* 250 */       String[] arrayOfString1 = new String[offset + arr.length];
/* 251 */       System.arraycopy(intoValue, 0, arrayOfString1, 0, offset);
/* 252 */       System.arraycopy(arr, 0, arrayOfString1, offset, arr.length);
/* 253 */       return arrayOfString1;
/*     */     } 
/*     */     
/* 256 */     if (this._elementDeserializer != null) {
/* 257 */       return _deserializeCustom(p, ctxt, intoValue);
/*     */     }
/* 259 */     ObjectBuffer buffer = ctxt.leaseObjectBuffer();
/* 260 */     int ix = intoValue.length;
/* 261 */     Object[] chunk = buffer.resetAndStart((Object[])intoValue, ix);
/*     */     
/*     */     try {
/*     */       while (true) {
/* 265 */         String value = p.nextTextValue();
/* 266 */         if (value == null) {
/* 267 */           JsonToken t = p.getCurrentToken();
/* 268 */           if (t == JsonToken.END_ARRAY) {
/*     */             break;
/*     */           }
/* 271 */           if (t == JsonToken.VALUE_NULL) {
/*     */             
/* 273 */             if (this._skipNullValues) {
/* 274 */               return NO_STRINGS;
/*     */             }
/* 276 */             value = (String)this._nullProvider.getNullValue(ctxt);
/*     */           } else {
/* 278 */             value = _parseString(p, ctxt);
/*     */           } 
/*     */         } 
/* 281 */         if (ix >= chunk.length) {
/* 282 */           chunk = buffer.appendCompletedChunk(chunk);
/* 283 */           ix = 0;
/*     */         } 
/* 285 */         chunk[ix++] = value;
/*     */       } 
/* 287 */     } catch (Exception e) {
/* 288 */       throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
/*     */     } 
/* 290 */     String[] result = (String[])buffer.completeAndClearBuffer(chunk, ix, String.class);
/* 291 */     ctxt.returnObjectBuffer(buffer);
/* 292 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] handleNonArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 298 */     boolean canWrap = (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
/*     */ 
/*     */     
/* 301 */     if (canWrap) {
/* 302 */       String value = p.hasToken(JsonToken.VALUE_NULL) ? (String)this._nullProvider.getNullValue(ctxt) : _parseString(p, ctxt);
/*     */ 
/*     */       
/* 305 */       return new String[] { value };
/*     */     } 
/* 307 */     if (p.hasToken(JsonToken.VALUE_STRING) && ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
/*     */       
/* 309 */       String str = p.getText();
/* 310 */       if (str.length() == 0) {
/* 311 */         return null;
/*     */       }
/*     */     } 
/* 314 */     return (String[])ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StringArrayDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */