/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StdSerializer;
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
/*    */ public class FailingSerializer
/*    */   extends StdSerializer<Object>
/*    */ {
/*    */   protected final String _msg;
/*    */   
/*    */   public FailingSerializer(String msg) {
/* 28 */     super(Object.class);
/* 29 */     this._msg = msg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 35 */     provider.reportMappingProblem(this._msg, new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) {}
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\FailingSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */