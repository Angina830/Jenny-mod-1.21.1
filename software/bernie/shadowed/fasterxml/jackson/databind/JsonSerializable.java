/*    */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
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
/*    */ public interface JsonSerializable
/*    */ {
/*    */   void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException;
/*    */   
/*    */   void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer) throws IOException;
/*    */   
/*    */   public static abstract class Base
/*    */     implements JsonSerializable
/*    */   {
/*    */     public boolean isEmpty(SerializerProvider serializers) {
/* 61 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\JsonSerializable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */