/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
/*    */ 
/*    */ public class ByteBufferSerializer extends StdScalarSerializer<ByteBuffer> {
/*    */   public ByteBufferSerializer() {
/* 16 */     super(ByteBuffer.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(ByteBuffer bbuf, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 22 */     if (bbuf.hasArray()) {
/* 23 */       gen.writeBinary(bbuf.array(), 0, bbuf.limit());
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 28 */     ByteBuffer copy = bbuf.asReadOnlyBuffer();
/* 29 */     if (copy.position() > 0) {
/* 30 */       copy.rewind();
/*    */     }
/* 32 */     ByteBufferBackedInputStream byteBufferBackedInputStream = new ByteBufferBackedInputStream(copy);
/* 33 */     gen.writeBinary((InputStream)byteBufferBackedInputStream, copy.remaining());
/* 34 */     byteBufferBackedInputStream.close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 42 */     JsonArrayFormatVisitor v2 = visitor.expectArrayFormat(typeHint);
/* 43 */     if (v2 != null)
/* 44 */       v2.itemsFormat(JsonFormatTypes.INTEGER); 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ByteBufferSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */