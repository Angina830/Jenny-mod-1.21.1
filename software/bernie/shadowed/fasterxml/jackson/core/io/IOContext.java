/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonEncoding;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.BufferRecycler;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.TextBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IOContext
/*     */ {
/*     */   protected final Object _sourceRef;
/*     */   protected JsonEncoding _encoding;
/*     */   protected final boolean _managedResource;
/*     */   protected final BufferRecycler _bufferRecycler;
/*     */   protected byte[] _readIOBuffer;
/*     */   protected byte[] _writeEncodingBuffer;
/*     */   protected byte[] _base64Buffer;
/*     */   protected char[] _tokenCBuffer;
/*     */   protected char[] _concatCBuffer;
/*     */   protected char[] _nameCopyBuffer;
/*     */   
/*     */   public IOContext(BufferRecycler br, Object sourceRef, boolean managedResource) {
/* 103 */     this._bufferRecycler = br;
/* 104 */     this._sourceRef = sourceRef;
/* 105 */     this._managedResource = managedResource;
/*     */   }
/*     */   
/*     */   public void setEncoding(JsonEncoding enc) {
/* 109 */     this._encoding = enc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IOContext withEncoding(JsonEncoding enc) {
/* 116 */     this._encoding = enc;
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSourceReference() {
/* 126 */     return this._sourceRef;
/* 127 */   } public JsonEncoding getEncoding() { return this._encoding; } public boolean isResourceManaged() {
/* 128 */     return this._managedResource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBuffer constructTextBuffer() {
/* 137 */     return new TextBuffer(this._bufferRecycler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] allocReadIOBuffer() {
/* 146 */     _verifyAlloc(this._readIOBuffer);
/* 147 */     return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] allocReadIOBuffer(int minSize) {
/* 154 */     _verifyAlloc(this._readIOBuffer);
/* 155 */     return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0, minSize);
/*     */   }
/*     */   
/*     */   public byte[] allocWriteEncodingBuffer() {
/* 159 */     _verifyAlloc(this._writeEncodingBuffer);
/* 160 */     return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] allocWriteEncodingBuffer(int minSize) {
/* 167 */     _verifyAlloc(this._writeEncodingBuffer);
/* 168 */     return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1, minSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] allocBase64Buffer() {
/* 175 */     _verifyAlloc(this._base64Buffer);
/* 176 */     return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] allocBase64Buffer(int minSize) {
/* 183 */     _verifyAlloc(this._base64Buffer);
/* 184 */     return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3, minSize);
/*     */   }
/*     */   
/*     */   public char[] allocTokenBuffer() {
/* 188 */     _verifyAlloc(this._tokenCBuffer);
/* 189 */     return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] allocTokenBuffer(int minSize) {
/* 196 */     _verifyAlloc(this._tokenCBuffer);
/* 197 */     return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0, minSize);
/*     */   }
/*     */   
/*     */   public char[] allocConcatBuffer() {
/* 201 */     _verifyAlloc(this._concatCBuffer);
/* 202 */     return this._concatCBuffer = this._bufferRecycler.allocCharBuffer(1);
/*     */   }
/*     */   
/*     */   public char[] allocNameCopyBuffer(int minSize) {
/* 206 */     _verifyAlloc(this._nameCopyBuffer);
/* 207 */     return this._nameCopyBuffer = this._bufferRecycler.allocCharBuffer(3, minSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseReadIOBuffer(byte[] buf) {
/* 215 */     if (buf != null) {
/*     */ 
/*     */ 
/*     */       
/* 219 */       _verifyRelease(buf, this._readIOBuffer);
/* 220 */       this._readIOBuffer = null;
/* 221 */       this._bufferRecycler.releaseByteBuffer(0, buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseWriteEncodingBuffer(byte[] buf) {
/* 226 */     if (buf != null) {
/*     */ 
/*     */ 
/*     */       
/* 230 */       _verifyRelease(buf, this._writeEncodingBuffer);
/* 231 */       this._writeEncodingBuffer = null;
/* 232 */       this._bufferRecycler.releaseByteBuffer(1, buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseBase64Buffer(byte[] buf) {
/* 237 */     if (buf != null) {
/* 238 */       _verifyRelease(buf, this._base64Buffer);
/* 239 */       this._base64Buffer = null;
/* 240 */       this._bufferRecycler.releaseByteBuffer(3, buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseTokenBuffer(char[] buf) {
/* 245 */     if (buf != null) {
/* 246 */       _verifyRelease(buf, this._tokenCBuffer);
/* 247 */       this._tokenCBuffer = null;
/* 248 */       this._bufferRecycler.releaseCharBuffer(0, buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseConcatBuffer(char[] buf) {
/* 253 */     if (buf != null) {
/*     */       
/* 255 */       _verifyRelease(buf, this._concatCBuffer);
/* 256 */       this._concatCBuffer = null;
/* 257 */       this._bufferRecycler.releaseCharBuffer(1, buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseNameCopyBuffer(char[] buf) {
/* 262 */     if (buf != null) {
/*     */       
/* 264 */       _verifyRelease(buf, this._nameCopyBuffer);
/* 265 */       this._nameCopyBuffer = null;
/* 266 */       this._bufferRecycler.releaseCharBuffer(3, buf);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void _verifyAlloc(Object buffer) {
/* 277 */     if (buffer != null) throw new IllegalStateException("Trying to call same allocXxx() method second time");
/*     */   
/*     */   }
/*     */   
/*     */   protected final void _verifyRelease(byte[] toRelease, byte[] src) {
/* 282 */     if (toRelease != src && toRelease.length < src.length) throw wrongBuf();
/*     */   
/*     */   }
/*     */   
/*     */   protected final void _verifyRelease(char[] toRelease, char[] src) {
/* 287 */     if (toRelease != src && toRelease.length < src.length) throw wrongBuf();
/*     */   
/*     */   }
/*     */   
/*     */   private IllegalArgumentException wrongBuf() {
/* 292 */     return new IllegalArgumentException("Trying to release buffer smaller than original");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\IOContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */