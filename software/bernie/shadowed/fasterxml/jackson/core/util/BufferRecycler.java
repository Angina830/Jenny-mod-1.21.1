/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
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
/*     */ public class BufferRecycler
/*     */ {
/*     */   public static final int BYTE_READ_IO_BUFFER = 0;
/*     */   public static final int BYTE_WRITE_ENCODING_BUFFER = 1;
/*     */   public static final int BYTE_WRITE_CONCAT_BUFFER = 2;
/*     */   public static final int BYTE_BASE64_CODEC_BUFFER = 3;
/*     */   public static final int CHAR_TOKEN_BUFFER = 0;
/*     */   public static final int CHAR_CONCAT_BUFFER = 1;
/*     */   public static final int CHAR_TEXT_BUFFER = 2;
/*     */   public static final int CHAR_NAME_COPY_BUFFER = 3;
/*  45 */   private static final int[] BYTE_BUFFER_LENGTHS = new int[] { 8000, 8000, 2000, 2000 };
/*  46 */   private static final int[] CHAR_BUFFER_LENGTHS = new int[] { 4000, 4000, 200, 200 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final byte[][] _byteBuffers;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final char[][] _charBuffers;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferRecycler() {
/*  62 */     this(4, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BufferRecycler(int bbCount, int cbCount) {
/*  72 */     this._byteBuffers = new byte[bbCount][];
/*  73 */     this._charBuffers = new char[cbCount][];
/*     */   }
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
/*     */   public final byte[] allocByteBuffer(int ix) {
/*  86 */     return allocByteBuffer(ix, 0);
/*     */   }
/*     */   
/*     */   public byte[] allocByteBuffer(int ix, int minSize) {
/*  90 */     int DEF_SIZE = byteBufferLength(ix);
/*  91 */     if (minSize < DEF_SIZE) {
/*  92 */       minSize = DEF_SIZE;
/*     */     }
/*  94 */     byte[] buffer = this._byteBuffers[ix];
/*  95 */     if (buffer == null || buffer.length < minSize) {
/*  96 */       buffer = balloc(minSize);
/*     */     } else {
/*  98 */       this._byteBuffers[ix] = null;
/*     */     } 
/* 100 */     return buffer;
/*     */   }
/*     */   
/*     */   public final void releaseByteBuffer(int ix, byte[] buffer) {
/* 104 */     this._byteBuffers[ix] = buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final char[] allocCharBuffer(int ix) {
/* 114 */     return allocCharBuffer(ix, 0);
/*     */   }
/*     */   
/*     */   public char[] allocCharBuffer(int ix, int minSize) {
/* 118 */     int DEF_SIZE = charBufferLength(ix);
/* 119 */     if (minSize < DEF_SIZE) {
/* 120 */       minSize = DEF_SIZE;
/*     */     }
/* 122 */     char[] buffer = this._charBuffers[ix];
/* 123 */     if (buffer == null || buffer.length < minSize) {
/* 124 */       buffer = calloc(minSize);
/*     */     } else {
/* 126 */       this._charBuffers[ix] = null;
/*     */     } 
/* 128 */     return buffer;
/*     */   }
/*     */   
/*     */   public void releaseCharBuffer(int ix, char[] buffer) {
/* 132 */     this._charBuffers[ix] = buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int byteBufferLength(int ix) {
/* 142 */     return BYTE_BUFFER_LENGTHS[ix];
/*     */   }
/*     */   
/*     */   protected int charBufferLength(int ix) {
/* 146 */     return CHAR_BUFFER_LENGTHS[ix];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] balloc(int size) {
/* 155 */     return new byte[size]; } protected char[] calloc(int size) {
/* 156 */     return new char[size];
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\BufferRecycler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */