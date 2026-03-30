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
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
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
/*    */ @JacksonStdImpl
/*    */ public class ByteArraySerializer
/*    */   extends StdSerializer<byte[]>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ByteArraySerializer() {
/* 36 */     super((Class)byte[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty(SerializerProvider prov, byte[] value) {
/* 41 */     return (value.length == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(byte[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 48 */     g.writeBinary(provider.getConfig().getBase64Variant(), value, 0, value.length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(byte[] value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 58 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_EMBEDDED_OBJECT));
/*    */     
/* 60 */     g.writeBinary(provider.getConfig().getBase64Variant(), value, 0, value.length);
/*    */     
/* 62 */     typeSer.writeTypeSuffix(g, typeIdDef);
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
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 75 */     ObjectNode o = createSchemaNode("array", true);
/* 76 */     ObjectNode itemSchema = createSchemaNode("byte");
/* 77 */     return o.set("items", (JsonNode)itemSchema);
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
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 90 */     JsonArrayFormatVisitor v2 = visitor.expectArrayFormat(typeHint);
/* 91 */     if (v2 != null)
/* 92 */       v2.itemsFormat(JsonFormatTypes.INTEGER); 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ByteArraySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */