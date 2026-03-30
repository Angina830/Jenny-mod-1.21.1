/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
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
/*     */ public abstract class TypeSerializer
/*     */ {
/*     */   public abstract TypeSerializer forProperty(BeanProperty paramBeanProperty);
/*     */   
/*     */   public abstract JsonTypeInfo.As getTypeInclusion();
/*     */   
/*     */   public abstract String getPropertyName();
/*     */   
/*     */   public abstract TypeIdResolver getTypeIdResolver();
/*     */   
/*     */   public WritableTypeId typeId(Object value, JsonToken valueShape) {
/*  78 */     WritableTypeId typeIdDef = new WritableTypeId(value, valueShape);
/*  79 */     switch (getTypeInclusion())
/*     */     { case EXISTING_PROPERTY:
/*  81 */         typeIdDef.include = WritableTypeId.Inclusion.PAYLOAD_PROPERTY;
/*  82 */         typeIdDef.asProperty = getPropertyName();
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
/* 101 */         return typeIdDef;case EXTERNAL_PROPERTY: typeIdDef.include = WritableTypeId.Inclusion.PARENT_PROPERTY; typeIdDef.asProperty = getPropertyName(); return typeIdDef;case PROPERTY: typeIdDef.include = WritableTypeId.Inclusion.METADATA_PROPERTY; typeIdDef.asProperty = getPropertyName(); return typeIdDef;case WRAPPER_ARRAY: typeIdDef.include = WritableTypeId.Inclusion.WRAPPER_ARRAY; return typeIdDef;case WRAPPER_OBJECT: typeIdDef.include = WritableTypeId.Inclusion.WRAPPER_OBJECT; return typeIdDef; }  VersionUtil.throwInternal(); return typeIdDef;
/*     */   }
/*     */ 
/*     */   
/*     */   public WritableTypeId typeId(Object value, JsonToken valueShape, Object id) {
/* 106 */     WritableTypeId typeId = typeId(value, valueShape);
/* 107 */     typeId.id = id;
/* 108 */     return typeId;
/*     */   }
/*     */ 
/*     */   
/*     */   public WritableTypeId typeId(Object value, Class<?> typeForId, JsonToken valueShape) {
/* 113 */     WritableTypeId typeId = typeId(value, valueShape);
/* 114 */     typeId.forValueType = typeForId;
/* 115 */     return typeId;
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
/*     */   public abstract WritableTypeId writeTypePrefix(JsonGenerator paramJsonGenerator, WritableTypeId paramWritableTypeId) throws IOException;
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
/*     */   public abstract WritableTypeId writeTypeSuffix(JsonGenerator paramJsonGenerator, WritableTypeId paramWritableTypeId) throws IOException;
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
/*     */   @Deprecated
/*     */   public void writeTypePrefixForScalar(Object value, JsonGenerator g) throws IOException {
/* 156 */     writeTypePrefix(g, typeId(value, JsonToken.VALUE_STRING));
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
/*     */   @Deprecated
/*     */   public void writeTypePrefixForObject(Object value, JsonGenerator g) throws IOException {
/* 172 */     writeTypePrefix(g, typeId(value, JsonToken.START_OBJECT));
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
/*     */   @Deprecated
/*     */   public void writeTypePrefixForArray(Object value, JsonGenerator g) throws IOException {
/* 188 */     writeTypePrefix(g, typeId(value, JsonToken.START_ARRAY));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeTypeSuffixForScalar(Object value, JsonGenerator g) throws IOException {
/* 199 */     _writeLegacySuffix(g, typeId(value, JsonToken.VALUE_STRING));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeTypeSuffixForObject(Object value, JsonGenerator g) throws IOException {
/* 210 */     _writeLegacySuffix(g, typeId(value, JsonToken.START_OBJECT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeTypeSuffixForArray(Object value, JsonGenerator g) throws IOException {
/* 221 */     _writeLegacySuffix(g, typeId(value, JsonToken.START_ARRAY));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeTypePrefixForScalar(Object value, JsonGenerator g, Class<?> type) throws IOException {
/* 231 */     writeTypePrefix(g, typeId(value, type, JsonToken.VALUE_STRING));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeTypePrefixForObject(Object value, JsonGenerator g, Class<?> type) throws IOException {
/* 241 */     writeTypePrefix(g, typeId(value, type, JsonToken.START_OBJECT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeTypePrefixForArray(Object value, JsonGenerator g, Class<?> type) throws IOException {
/* 251 */     writeTypePrefix(g, typeId(value, type, JsonToken.START_ARRAY));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void writeCustomTypePrefixForScalar(Object value, JsonGenerator g, String typeId) throws IOException {
/* 262 */     writeTypePrefix(g, typeId(value, JsonToken.VALUE_STRING, typeId));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void writeCustomTypePrefixForObject(Object value, JsonGenerator g, String typeId) throws IOException {
/* 267 */     writeTypePrefix(g, typeId(value, JsonToken.START_OBJECT, typeId));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void writeCustomTypePrefixForArray(Object value, JsonGenerator g, String typeId) throws IOException {
/* 272 */     writeTypePrefix(g, typeId(value, JsonToken.START_ARRAY, typeId));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void writeCustomTypeSuffixForScalar(Object value, JsonGenerator g, String typeId) throws IOException {
/* 277 */     _writeLegacySuffix(g, typeId(value, JsonToken.VALUE_STRING, typeId));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void writeCustomTypeSuffixForObject(Object value, JsonGenerator g, String typeId) throws IOException {
/* 282 */     _writeLegacySuffix(g, typeId(value, JsonToken.START_OBJECT, typeId));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void writeCustomTypeSuffixForArray(Object value, JsonGenerator g, String typeId) throws IOException {
/* 287 */     _writeLegacySuffix(g, typeId(value, JsonToken.START_ARRAY, typeId));
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
/*     */   protected final void _writeLegacySuffix(JsonGenerator g, WritableTypeId typeId) throws IOException {
/* 301 */     typeId.wrapperWritten = !g.canWriteTypeId();
/* 302 */     writeTypeSuffix(g, typeId);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\TypeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */