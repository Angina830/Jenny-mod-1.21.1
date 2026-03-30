/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TypeWrappedSerializer
/*    */   extends JsonSerializer<Object>
/*    */   implements ContextualSerializer
/*    */ {
/*    */   protected final TypeSerializer _typeSerializer;
/*    */   protected final JsonSerializer<Object> _serializer;
/*    */   
/*    */   public TypeWrappedSerializer(TypeSerializer typeSer, JsonSerializer<?> ser) {
/* 26 */     this._typeSerializer = typeSer;
/* 27 */     this._serializer = (JsonSerializer)ser;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 32 */     this._serializer.serializeWithType(value, g, provider, this._typeSerializer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(Object value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 41 */     this._serializer.serializeWithType(value, g, provider, typeSer);
/*    */   }
/*    */   
/*    */   public Class<Object> handledType() {
/* 45 */     return Object.class;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/* 58 */     JsonSerializer<?> ser = this._serializer;
/* 59 */     if (ser instanceof ContextualSerializer) {
/* 60 */       ser = provider.handleSecondaryContextualization(ser, property);
/*    */     }
/* 62 */     if (ser == this._serializer) {
/* 63 */       return this;
/*    */     }
/* 65 */     return new TypeWrappedSerializer(this._typeSerializer, ser);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonSerializer<Object> valueSerializer() {
/* 75 */     return this._serializer;
/*    */   }
/*    */   
/*    */   public TypeSerializer typeSerializer() {
/* 79 */     return this._typeSerializer;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\TypeWrappedSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */