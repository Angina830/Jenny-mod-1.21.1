/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.sql.Time;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class SqlTimeSerializer extends StdScalarSerializer<Time> {
/*    */   public SqlTimeSerializer() {
/* 17 */     super(Time.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(Time value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 22 */     g.writeString(value.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 27 */     return (JsonNode)createSchemaNode("string", true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 34 */     visitStringFormat(visitor, typeHint, JsonValueFormat.DATE_TIME);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\SqlTimeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */