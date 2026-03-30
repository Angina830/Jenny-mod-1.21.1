/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable;
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
/*    */ @JacksonStdImpl
/*    */ public class SerializableSerializer
/*    */   extends StdSerializer<JsonSerializable>
/*    */ {
/* 25 */   public static final SerializableSerializer instance = new SerializableSerializer();
/*    */   protected SerializableSerializer() {
/* 27 */     super(JsonSerializable.class);
/*    */   }
/*    */   
/*    */   public boolean isEmpty(SerializerProvider serializers, JsonSerializable value) {
/* 31 */     if (value instanceof JsonSerializable.Base) {
/* 32 */       return ((JsonSerializable.Base)value).isEmpty(serializers);
/*    */     }
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(JsonSerializable value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
/* 39 */     value.serialize(gen, serializers);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void serializeWithType(JsonSerializable value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
/* 45 */     value.serializeWithType(gen, serializers, typeSer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 52 */     visitor.expectAnyFormat(typeHint);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\SerializableSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */