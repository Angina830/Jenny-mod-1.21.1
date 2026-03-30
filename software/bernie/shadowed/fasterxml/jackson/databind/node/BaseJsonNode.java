/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseJsonNode
/*    */   extends JsonNode
/*    */   implements JsonSerializable
/*    */ {
/*    */   public final JsonNode findPath(String fieldName) {
/* 33 */     JsonNode value = findValue(fieldName);
/* 34 */     if (value == null) {
/* 35 */       return MissingNode.getInstance();
/*    */     }
/* 37 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract int hashCode();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonParser traverse() {
/* 51 */     return (JsonParser)new TreeTraversingParser(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonParser traverse(ObjectCodec codec) {
/* 56 */     return (JsonParser)new TreeTraversingParser(this, codec);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract JsonToken asToken();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonParser.NumberType numberType() {
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public abstract void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException, JsonProcessingException;
/*    */   
/*    */   public abstract void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer) throws IOException, JsonProcessingException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\BaseJsonNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */