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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public final class StringSerializer
/*    */   extends StdScalarSerializer<Object>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public StringSerializer() {
/* 31 */     super(String.class, false);
/*    */   }
/*    */   
/*    */   public boolean isEmpty(SerializerProvider prov, Object value) {
/* 35 */     String str = (String)value;
/* 36 */     return (str.length() == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 41 */     gen.writeString((String)value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 49 */     gen.writeString((String)value);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 54 */     return (JsonNode)createSchemaNode("string", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 59 */     visitStringFormat(visitor, typeHint);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StringSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */