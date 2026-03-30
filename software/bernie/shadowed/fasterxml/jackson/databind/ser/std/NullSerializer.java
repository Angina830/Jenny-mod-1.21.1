/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
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
/*    */ @JacksonStdImpl
/*    */ public class NullSerializer
/*    */   extends StdSerializer<Object>
/*    */ {
/* 24 */   public static final NullSerializer instance = new NullSerializer();
/*    */   private NullSerializer() {
/* 26 */     super(Object.class);
/*    */   }
/*    */   
/*    */   public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 30 */     gen.writeNull();
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
/*    */   public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
/* 44 */     gen.writeNull();
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/* 49 */     return (JsonNode)createSchemaNode("null");
/*    */   }
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 54 */     visitor.expectNullFormat(typeHint);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\NullSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */