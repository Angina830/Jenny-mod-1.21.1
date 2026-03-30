/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.JsonParserSequence;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
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
/*     */ 
/*     */ public class AsPropertyTypeDeserializer
/*     */   extends AsArrayTypeDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JsonTypeInfo.As _inclusion;
/*     */   
/*     */   public AsPropertyTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
/*  33 */     this(bt, idRes, typePropertyName, typeIdVisible, defaultImpl, JsonTypeInfo.As.PROPERTY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsPropertyTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl, JsonTypeInfo.As inclusion) {
/*  43 */     super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
/*  44 */     this._inclusion = inclusion;
/*     */   }
/*     */   
/*     */   public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer src, BeanProperty property) {
/*  48 */     super(src, property);
/*  49 */     this._inclusion = src._inclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeDeserializer forProperty(BeanProperty prop) {
/*  54 */     return (prop == this._property) ? this : new AsPropertyTypeDeserializer(this, prop);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion() {
/*  58 */     return this._inclusion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeTypedFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  69 */     if (p.canReadTypeId()) {
/*  70 */       Object typeId = p.getTypeId();
/*  71 */       if (typeId != null) {
/*  72 */         return _deserializeWithNativeTypeId(p, ctxt, typeId);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  77 */     JsonToken t = p.getCurrentToken();
/*  78 */     if (t == JsonToken.START_OBJECT) {
/*  79 */       t = p.nextToken();
/*  80 */     } else if (t != JsonToken.FIELD_NAME) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       return _deserializeTypedUsingDefaultImpl(p, ctxt, (TokenBuffer)null);
/*     */     } 
/*     */     
/*  91 */     TokenBuffer tb = null;
/*     */     
/*  93 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/*  94 */       String name = p.getCurrentName();
/*  95 */       p.nextToken();
/*  96 */       if (name.equals(this._typePropertyName)) {
/*  97 */         return _deserializeTypedForId(p, ctxt, tb);
/*     */       }
/*  99 */       if (tb == null) {
/* 100 */         tb = new TokenBuffer(p, ctxt);
/*     */       }
/* 102 */       tb.writeFieldName(name);
/* 103 */       tb.copyCurrentStructure(p);
/*     */     } 
/* 105 */     return _deserializeTypedUsingDefaultImpl(p, ctxt, tb);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _deserializeTypedForId(JsonParser p, DeserializationContext ctxt, TokenBuffer tb) throws IOException {
/*     */     JsonParserSequence jsonParserSequence;
/* 112 */     String typeId = p.getText();
/* 113 */     JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
/* 114 */     if (this._typeIdVisible) {
/* 115 */       if (tb == null) {
/* 116 */         tb = new TokenBuffer(p, ctxt);
/*     */       }
/* 118 */       tb.writeFieldName(p.getCurrentName());
/* 119 */       tb.writeString(typeId);
/*     */     } 
/* 121 */     if (tb != null) {
/*     */ 
/*     */       
/* 124 */       p.clearCurrentToken();
/* 125 */       jsonParserSequence = JsonParserSequence.createFlattened(false, tb.asParser(p), p);
/*     */     } 
/*     */     
/* 128 */     jsonParserSequence.nextToken();
/*     */     
/* 130 */     return deser.deserialize((JsonParser)jsonParserSequence, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _deserializeTypedUsingDefaultImpl(JsonParser p, DeserializationContext ctxt, TokenBuffer tb) throws IOException {
/* 139 */     JsonDeserializer<Object> deser = _findDefaultImplDeserializer(ctxt);
/* 140 */     if (deser == null) {
/*     */       
/* 142 */       Object result = TypeDeserializer.deserializeIfNatural(p, ctxt, this._baseType);
/* 143 */       if (result != null) {
/* 144 */         return result;
/*     */       }
/*     */       
/* 147 */       if (p.isExpectedStartArrayToken()) {
/* 148 */         return super.deserializeTypedFromAny(p, ctxt);
/*     */       }
/* 150 */       if (p.hasToken(JsonToken.VALUE_STRING) && 
/* 151 */         ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
/* 152 */         String str = p.getText().trim();
/* 153 */         if (str.isEmpty()) {
/* 154 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 158 */       String msg = String.format("missing type id property '%s'", new Object[] { this._typePropertyName });
/*     */ 
/*     */       
/* 161 */       if (this._property != null) {
/* 162 */         msg = String.format("%s (for POJO property '%s')", new Object[] { msg, this._property.getName() });
/*     */       }
/* 164 */       JavaType t = _handleMissingTypeId(ctxt, msg);
/* 165 */       if (t == null)
/*     */       {
/* 167 */         return null;
/*     */       }
/*     */       
/* 170 */       deser = ctxt.findContextualValueDeserializer(t, this._property);
/*     */     } 
/* 172 */     if (tb != null) {
/* 173 */       tb.writeEndObject();
/* 174 */       p = tb.asParser(p);
/*     */       
/* 176 */       p.nextToken();
/*     */     } 
/* 178 */     return deser.deserialize(p, ctxt);
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
/*     */   public Object deserializeTypedFromAny(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 190 */     if (p.getCurrentToken() == JsonToken.START_ARRAY) {
/* 191 */       return deserializeTypedFromArray(p, ctxt);
/*     */     }
/* 193 */     return deserializeTypedFromObject(p, ctxt);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsPropertyTypeDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */