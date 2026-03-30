/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.JsonParserSequence;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsArrayTypeDeserializer
/*     */   extends TypeDeserializerBase
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public AsArrayTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
/*  32 */     super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
/*     */   }
/*     */   
/*     */   public AsArrayTypeDeserializer(AsArrayTypeDeserializer src, BeanProperty property) {
/*  36 */     super(src, property);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeDeserializer forProperty(BeanProperty prop) {
/*  42 */     return (prop == this._property) ? this : new AsArrayTypeDeserializer(this, prop);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion() {
/*  46 */     return JsonTypeInfo.As.WRAPPER_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  53 */     return _deserialize(jp, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  61 */     return _deserialize(jp, ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromScalar(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  66 */     return _deserialize(jp, ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromAny(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  71 */     return _deserialize(jp, ctxt);
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
/*     */   protected Object _deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */     JsonParserSequence jsonParserSequence;
/*  89 */     if (p.canReadTypeId()) {
/*  90 */       Object object = p.getTypeId();
/*  91 */       if (object != null) {
/*  92 */         return _deserializeWithNativeTypeId(p, ctxt, object);
/*     */       }
/*     */     } 
/*  95 */     boolean hadStartArray = p.isExpectedStartArrayToken();
/*  96 */     String typeId = _locateTypeId(p, ctxt);
/*  97 */     JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
/*     */     
/*  99 */     if (this._typeIdVisible && !_usesExternalId() && p.getCurrentToken() == JsonToken.START_OBJECT) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       TokenBuffer tb = new TokenBuffer(null, false);
/* 107 */       tb.writeStartObject();
/* 108 */       tb.writeFieldName(this._typePropertyName);
/* 109 */       tb.writeString(typeId);
/*     */ 
/*     */       
/* 112 */       p.clearCurrentToken();
/* 113 */       jsonParserSequence = JsonParserSequence.createFlattened(false, tb.asParser(p), p);
/* 114 */       jsonParserSequence.nextToken();
/*     */     } 
/* 116 */     Object value = deser.deserialize((JsonParser)jsonParserSequence, ctxt);
/*     */     
/* 118 */     if (hadStartArray && jsonParserSequence.nextToken() != JsonToken.END_ARRAY) {
/* 119 */       ctxt.reportWrongTokenException(baseType(), JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String _locateTypeId(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 131 */     if (!p.isExpectedStartArrayToken()) {
/*     */ 
/*     */       
/* 134 */       if (this._defaultImpl != null) {
/* 135 */         return this._idResolver.idFromBaseType();
/*     */       }
/* 137 */       ctxt.reportWrongTokenException(baseType(), JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + baseTypeName(), new Object[0]);
/*     */       
/* 139 */       return null;
/*     */     } 
/*     */     
/* 142 */     JsonToken t = p.nextToken();
/* 143 */     if (t == JsonToken.VALUE_STRING) {
/* 144 */       String result = p.getText();
/* 145 */       p.nextToken();
/* 146 */       return result;
/*     */     } 
/* 148 */     if (this._defaultImpl != null) {
/* 149 */       return this._idResolver.idFromBaseType();
/*     */     }
/* 151 */     ctxt.reportWrongTokenException(baseType(), JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of %s)", new Object[] { baseTypeName() });
/*     */     
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _usesExternalId() {
/* 160 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsArrayTypeDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */