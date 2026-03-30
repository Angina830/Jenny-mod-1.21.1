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
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class TokenBufferSerializer
/*    */   extends StdSerializer<TokenBuffer>
/*    */ {
/*    */   public TokenBufferSerializer() {
/* 28 */     super(TokenBuffer.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(TokenBuffer value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
/* 34 */     value.serialize(jgen);
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
/*    */   public final void serializeWithType(TokenBuffer value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 53 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_EMBEDDED_OBJECT));
/*    */     
/* 55 */     serialize(value, g, provider);
/* 56 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 64 */     return (JsonNode)createSchemaNode("any", true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 73 */     visitor.expectAnyFormat(typeHint);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\TokenBufferSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */