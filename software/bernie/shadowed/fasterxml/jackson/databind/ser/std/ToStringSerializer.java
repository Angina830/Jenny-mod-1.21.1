/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*    */ @JacksonStdImpl
/*    */ public class ToStringSerializer
/*    */   extends StdSerializer<Object>
/*    */ {
/* 29 */   public static final ToStringSerializer instance = new ToStringSerializer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToStringSerializer() {
/* 39 */     super(Object.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToStringSerializer(Class<?> handledType) {
/* 47 */     super(handledType, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty(SerializerProvider prov, Object value) {
/* 52 */     return value.toString().isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 59 */     gen.writeString(value.toString());
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(Object value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 78 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_STRING));
/*    */     
/* 80 */     serialize(value, g, provider);
/* 81 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/* 86 */     return (JsonNode)createSchemaNode("string", true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 92 */     visitStringFormat(visitor, typeHint);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ToStringSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */