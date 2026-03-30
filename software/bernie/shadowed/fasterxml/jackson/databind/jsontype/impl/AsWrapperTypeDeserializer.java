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
/*     */ public class AsWrapperTypeDeserializer
/*     */   extends TypeDeserializerBase
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public AsWrapperTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
/*  32 */     super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
/*     */   }
/*     */   
/*     */   protected AsWrapperTypeDeserializer(AsWrapperTypeDeserializer src, BeanProperty property) {
/*  36 */     super(src, property);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeDeserializer forProperty(BeanProperty prop) {
/*  41 */     return (prop == this._property) ? this : new AsWrapperTypeDeserializer(this, prop);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion() {
/*  45 */     return JsonTypeInfo.As.WRAPPER_OBJECT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  52 */     return _deserialize(jp, ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  57 */     return _deserialize(jp, ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromScalar(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  62 */     return _deserialize(jp, ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromAny(JsonParser jp, DeserializationContext ctxt) throws IOException {
/*  67 */     return _deserialize(jp, ctxt);
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
/*  85 */     if (p.canReadTypeId()) {
/*  86 */       Object object = p.getTypeId();
/*  87 */       if (object != null) {
/*  88 */         return _deserializeWithNativeTypeId(p, ctxt, object);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     JsonToken t = p.getCurrentToken();
/*  93 */     if (t == JsonToken.START_OBJECT) {
/*     */       
/*  95 */       if (p.nextToken() != JsonToken.FIELD_NAME) {
/*  96 */         ctxt.reportWrongTokenException(baseType(), JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + baseTypeName() + ")", new Object[0]);
/*     */       }
/*     */     }
/*  99 */     else if (t != JsonToken.FIELD_NAME) {
/* 100 */       ctxt.reportWrongTokenException(baseType(), JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + baseTypeName(), new Object[0]);
/*     */     } 
/*     */     
/* 103 */     String typeId = p.getText();
/* 104 */     JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
/* 105 */     p.nextToken();
/*     */ 
/*     */     
/* 108 */     if (this._typeIdVisible && p.getCurrentToken() == JsonToken.START_OBJECT) {
/*     */       
/* 110 */       TokenBuffer tb = new TokenBuffer(null, false);
/* 111 */       tb.writeStartObject();
/* 112 */       tb.writeFieldName(this._typePropertyName);
/* 113 */       tb.writeString(typeId);
/*     */ 
/*     */       
/* 116 */       p.clearCurrentToken();
/* 117 */       jsonParserSequence = JsonParserSequence.createFlattened(false, tb.asParser(p), p);
/* 118 */       jsonParserSequence.nextToken();
/*     */     } 
/*     */     
/* 121 */     Object value = deser.deserialize((JsonParser)jsonParserSequence, ctxt);
/*     */     
/* 123 */     if (jsonParserSequence.nextToken() != JsonToken.END_OBJECT) {
/* 124 */       ctxt.reportWrongTokenException(baseType(), JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value", new Object[0]);
/*     */     }
/*     */     
/* 127 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsWrapperTypeDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */