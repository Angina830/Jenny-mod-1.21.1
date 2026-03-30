/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
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
/*     */ public class MapEntryDeserializer
/*     */   extends ContainerDeserializerBase<Map.Entry<Object, Object>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final KeyDeserializer _keyDeserializer;
/*     */   protected final JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   
/*     */   public MapEntryDeserializer(JavaType type, KeyDeserializer keyDeser, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser) {
/*  58 */     super(type);
/*  59 */     if (type.containedTypeCount() != 2) {
/*  60 */       throw new IllegalArgumentException("Missing generic type information for " + type);
/*     */     }
/*  62 */     this._keyDeserializer = keyDeser;
/*  63 */     this._valueDeserializer = valueDeser;
/*  64 */     this._valueTypeDeserializer = valueTypeDeser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapEntryDeserializer(MapEntryDeserializer src) {
/*  73 */     super(src);
/*  74 */     this._keyDeserializer = src._keyDeserializer;
/*  75 */     this._valueDeserializer = src._valueDeserializer;
/*  76 */     this._valueTypeDeserializer = src._valueTypeDeserializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapEntryDeserializer(MapEntryDeserializer src, KeyDeserializer keyDeser, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser) {
/*  83 */     super(src);
/*  84 */     this._keyDeserializer = keyDeser;
/*  85 */     this._valueDeserializer = valueDeser;
/*  86 */     this._valueTypeDeserializer = valueTypeDeser;
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
/*     */   protected MapEntryDeserializer withResolved(KeyDeserializer keyDeser, TypeDeserializer valueTypeDeser, JsonDeserializer<?> valueDeser) {
/*  98 */     if (this._keyDeserializer == keyDeser && this._valueDeserializer == valueDeser && this._valueTypeDeserializer == valueTypeDeser)
/*     */     {
/* 100 */       return this;
/*     */     }
/* 102 */     return new MapEntryDeserializer(this, keyDeser, (JsonDeserializer)valueDeser, valueTypeDeser);
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
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 120 */     KeyDeserializer kd = this._keyDeserializer;
/* 121 */     if (kd == null) {
/* 122 */       kd = ctxt.findKeyDeserializer(this._containerType.containedType(0), property);
/*     */     }
/* 124 */     else if (kd instanceof ContextualKeyDeserializer) {
/* 125 */       kd = ((ContextualKeyDeserializer)kd).createContextual(ctxt, property);
/*     */     } 
/*     */     
/* 128 */     JsonDeserializer<?> vd = this._valueDeserializer;
/* 129 */     vd = findConvertingContentDeserializer(ctxt, property, vd);
/* 130 */     JavaType contentType = this._containerType.containedType(1);
/* 131 */     if (vd == null) {
/* 132 */       vd = ctxt.findContextualValueDeserializer(contentType, property);
/*     */     } else {
/* 134 */       vd = ctxt.handleSecondaryContextualization(vd, property, contentType);
/*     */     } 
/* 136 */     TypeDeserializer vtd = this._valueTypeDeserializer;
/* 137 */     if (vtd != null) {
/* 138 */       vtd = vtd.forProperty(property);
/*     */     }
/* 140 */     return withResolved(kd, vtd, vd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getContentType() {
/* 151 */     return this._containerType.containedType(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> getContentDeserializer() {
/* 156 */     return this._valueDeserializer;
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
/*     */   public Map.Entry<Object, Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 170 */     JsonToken t = p.getCurrentToken();
/* 171 */     if (t != JsonToken.START_OBJECT && t != JsonToken.FIELD_NAME && t != JsonToken.END_OBJECT)
/*     */     {
/*     */       
/* 174 */       return _deserializeFromEmpty(p, ctxt);
/*     */     }
/* 176 */     if (t == JsonToken.START_OBJECT) {
/* 177 */       t = p.nextToken();
/*     */     }
/* 179 */     if (t != JsonToken.FIELD_NAME) {
/* 180 */       if (t == JsonToken.END_OBJECT) {
/* 181 */         return (Map.Entry<Object, Object>)ctxt.reportInputMismatch(this, "Cannot deserialize a Map.Entry out of empty JSON Object", new Object[0]);
/*     */       }
/*     */       
/* 184 */       return (Map.Entry<Object, Object>)ctxt.handleUnexpectedToken(handledType(), p);
/*     */     } 
/*     */     
/* 187 */     KeyDeserializer keyDes = this._keyDeserializer;
/* 188 */     JsonDeserializer<Object> valueDes = this._valueDeserializer;
/* 189 */     TypeDeserializer typeDeser = this._valueTypeDeserializer;
/*     */     
/* 191 */     String keyStr = p.getCurrentName();
/* 192 */     Object key = keyDes.deserializeKey(keyStr, ctxt);
/* 193 */     Object value = null;
/*     */     
/* 195 */     t = p.nextToken();
/*     */     
/*     */     try {
/* 198 */       if (t == JsonToken.VALUE_NULL) {
/* 199 */         value = valueDes.getNullValue(ctxt);
/* 200 */       } else if (typeDeser == null) {
/* 201 */         value = valueDes.deserialize(p, ctxt);
/*     */       } else {
/* 203 */         value = valueDes.deserializeWithType(p, ctxt, typeDeser);
/*     */       } 
/* 205 */     } catch (Exception e) {
/* 206 */       wrapAndThrow(e, Map.Entry.class, keyStr);
/*     */     } 
/*     */ 
/*     */     
/* 210 */     t = p.nextToken();
/* 211 */     if (t != JsonToken.END_OBJECT) {
/* 212 */       if (t == JsonToken.FIELD_NAME) {
/* 213 */         ctxt.reportInputMismatch(this, "Problem binding JSON into Map.Entry: more than one entry in JSON (second field: '%s')", new Object[] { p.getCurrentName() });
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 218 */         ctxt.reportInputMismatch(this, "Problem binding JSON into Map.Entry: unexpected content after JSON Object entry: " + t, new Object[0]);
/*     */       } 
/*     */       
/* 221 */       return null;
/*     */     } 
/* 223 */     return new AbstractMap.SimpleEntry<>(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map.Entry<Object, Object> deserialize(JsonParser p, DeserializationContext ctxt, Map.Entry<Object, Object> result) throws IOException {
/* 230 */     throw new IllegalStateException("Cannot update Map.Entry values");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 239 */     return typeDeserializer.deserializeTypedFromObject(p, ctxt);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\MapEntryDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */