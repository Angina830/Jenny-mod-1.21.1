/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
/*    */ 
/*    */ public class ByteBufferDeserializer
/*    */   extends StdScalarDeserializer<ByteBuffer> {
/*    */   protected ByteBufferDeserializer() {
/* 14 */     super(ByteBuffer.class);
/*    */   }
/*    */   private static final long serialVersionUID = 1L;
/*    */   public ByteBuffer deserialize(JsonParser parser, DeserializationContext cx) throws IOException {
/* 18 */     byte[] b = parser.getBinaryValue();
/* 19 */     return ByteBuffer.wrap(b);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer deserialize(JsonParser jp, DeserializationContext ctxt, ByteBuffer intoValue) throws IOException {
/* 25 */     ByteBufferBackedOutputStream byteBufferBackedOutputStream = new ByteBufferBackedOutputStream(intoValue);
/* 26 */     jp.readBinaryValue(ctxt.getBase64Variant(), (OutputStream)byteBufferBackedOutputStream);
/* 27 */     byteBufferBackedOutputStream.close();
/* 28 */     return intoValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\ByteBufferDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */