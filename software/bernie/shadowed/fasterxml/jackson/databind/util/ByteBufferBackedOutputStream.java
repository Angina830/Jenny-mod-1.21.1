/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ByteBufferBackedOutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   protected final ByteBuffer _b;
/*    */   
/*    */   public ByteBufferBackedOutputStream(ByteBuffer buf) {
/* 13 */     this._b = buf;
/*    */   }
/* 15 */   public void write(int b) throws IOException { this._b.put((byte)b); } public void write(byte[] bytes, int off, int len) throws IOException {
/* 16 */     this._b.put(bytes, off, len);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ByteBufferBackedOutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */