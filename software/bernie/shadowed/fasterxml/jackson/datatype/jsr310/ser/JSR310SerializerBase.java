/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StdSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class JSR310SerializerBase<T>
/*    */   extends StdSerializer<T>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected JSR310SerializerBase(Class<?> supportedType) {
/* 22 */     super(supportedType, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(T value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 29 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer
/* 30 */         .typeId(value, serializationShape(provider)));
/* 31 */     serialize(value, g, provider);
/* 32 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */   
/*    */   protected abstract JsonToken serializationShape(SerializerProvider paramSerializerProvider);
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\JSR310SerializerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */