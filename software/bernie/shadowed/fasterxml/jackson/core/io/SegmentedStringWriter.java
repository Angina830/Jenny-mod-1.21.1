/*    */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.util.BufferRecycler;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.util.TextBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SegmentedStringWriter
/*    */   extends Writer
/*    */ {
/*    */   private final TextBuffer _buffer;
/*    */   
/*    */   public SegmentedStringWriter(BufferRecycler br) {
/* 22 */     this._buffer = new TextBuffer(br);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Writer append(char c) {
/* 33 */     write(c);
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Writer append(CharSequence csq) {
/* 39 */     String str = csq.toString();
/* 40 */     this._buffer.append(str, 0, str.length());
/* 41 */     return this;
/*    */   }
/*    */   public void close() {}
/*    */   
/*    */   public Writer append(CharSequence csq, int start, int end) {
/* 46 */     String str = csq.subSequence(start, end).toString();
/* 47 */     this._buffer.append(str, 0, str.length());
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public void write(char[] cbuf) {
/* 55 */     this._buffer.append(cbuf, 0, cbuf.length);
/*    */   }
/*    */   public void write(char[] cbuf, int off, int len) {
/* 58 */     this._buffer.append(cbuf, off, len);
/*    */   }
/*    */   public void write(int c) {
/* 61 */     this._buffer.append((char)c);
/*    */   }
/*    */   public void write(String str) {
/* 64 */     this._buffer.append(str, 0, str.length());
/*    */   }
/*    */   public void write(String str, int off, int len) {
/* 67 */     this._buffer.append(str, off, len);
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
/*    */   public String getAndClear() {
/* 83 */     String result = this._buffer.contentsAsString();
/* 84 */     this._buffer.releaseBuffers();
/* 85 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\SegmentedStringWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */