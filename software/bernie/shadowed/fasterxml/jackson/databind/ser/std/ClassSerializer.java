/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassSerializer
/*    */   extends StdScalarSerializer<Class<?>>
/*    */ {
/*    */   public ClassSerializer() {
/* 22 */     super(Class.class, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(Class<?> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 27 */     g.writeString(value.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 33 */     return (JsonNode)createSchemaNode("string", true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 40 */     visitStringFormat(visitor, typeHint);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ClassSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */