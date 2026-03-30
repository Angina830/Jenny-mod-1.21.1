/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TypeSerializerBase
/*    */   extends TypeSerializer
/*    */ {
/*    */   protected final TypeIdResolver _idResolver;
/*    */   protected final BeanProperty _property;
/*    */   
/*    */   protected TypeSerializerBase(TypeIdResolver idRes, BeanProperty property) {
/* 22 */     this._idResolver = idRes;
/* 23 */     this._property = property;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract JsonTypeInfo.As getTypeInclusion();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPropertyName() {
/* 36 */     return null;
/*    */   }
/*    */   public TypeIdResolver getTypeIdResolver() {
/* 39 */     return this._idResolver;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WritableTypeId writeTypePrefix(JsonGenerator g, WritableTypeId idMetadata) throws IOException {
/* 45 */     _generateTypeId(idMetadata);
/* 46 */     return g.writeTypePrefix(idMetadata);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WritableTypeId writeTypeSuffix(JsonGenerator g, WritableTypeId idMetadata) throws IOException {
/* 53 */     return g.writeTypeSuffix(idMetadata);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void _generateTypeId(WritableTypeId idMetadata) {
/* 62 */     Object id = idMetadata.id;
/* 63 */     if (id == null) {
/* 64 */       Object value = idMetadata.forValue;
/* 65 */       Class<?> typeForId = idMetadata.forValueType;
/* 66 */       if (typeForId == null) {
/* 67 */         id = idFromValue(value);
/*    */       } else {
/* 69 */         id = idFromValueAndType(value, typeForId);
/*    */       } 
/* 71 */       idMetadata.id = id;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String idFromValue(Object value) {
/* 82 */     String id = this._idResolver.idFromValue(value);
/* 83 */     if (id == null) {
/* 84 */       handleMissingId(value);
/*    */     }
/* 86 */     return id;
/*    */   }
/*    */   
/*    */   protected String idFromValueAndType(Object value, Class<?> type) {
/* 90 */     String id = this._idResolver.idFromValueAndType(value, type);
/* 91 */     if (id == null) {
/* 92 */       handleMissingId(value);
/*    */     }
/* 94 */     return id;
/*    */   }
/*    */   
/*    */   protected void handleMissingId(Object value) {}
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\TypeSerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */