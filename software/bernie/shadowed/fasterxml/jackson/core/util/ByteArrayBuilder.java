/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.util.LinkedList;
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
/*     */ public final class ByteArrayBuilder
/*     */   extends OutputStream
/*     */ {
/*  31 */   public static final byte[] NO_BYTES = new byte[0];
/*     */ 
/*     */   
/*     */   private static final int INITIAL_BLOCK_SIZE = 500;
/*     */ 
/*     */   
/*     */   private static final int MAX_BLOCK_SIZE = 262144;
/*     */ 
/*     */   
/*     */   static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
/*     */   
/*     */   private final BufferRecycler _bufferRecycler;
/*     */   
/*  44 */   private final LinkedList<byte[]> _pastBlocks = (LinkedList)new LinkedList<byte>();
/*     */   
/*     */   private int _pastLen;
/*     */   private byte[] _currBlock;
/*     */   private int _currBlockPtr;
/*     */   
/*     */   public ByteArrayBuilder() {
/*  51 */     this((BufferRecycler)null); }
/*  52 */   public ByteArrayBuilder(BufferRecycler br) { this(br, 500); } public ByteArrayBuilder(int firstBlockSize) {
/*  53 */     this(null, firstBlockSize);
/*     */   }
/*     */   public ByteArrayBuilder(BufferRecycler br, int firstBlockSize) {
/*  56 */     this._bufferRecycler = br;
/*  57 */     this._currBlock = (br == null) ? new byte[firstBlockSize] : br.allocByteBuffer(2);
/*     */   }
/*     */   
/*     */   public void reset() {
/*  61 */     this._pastLen = 0;
/*  62 */     this._currBlockPtr = 0;
/*     */     
/*  64 */     if (!this._pastBlocks.isEmpty()) {
/*  65 */       this._pastBlocks.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  73 */     return this._pastLen + this._currBlockPtr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  82 */     reset();
/*  83 */     if (this._bufferRecycler != null && this._currBlock != null) {
/*  84 */       this._bufferRecycler.releaseByteBuffer(2, this._currBlock);
/*  85 */       this._currBlock = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void append(int i) {
/*  90 */     if (this._currBlockPtr >= this._currBlock.length) {
/*  91 */       _allocMore();
/*     */     }
/*  93 */     this._currBlock[this._currBlockPtr++] = (byte)i;
/*     */   }
/*     */   
/*     */   public void appendTwoBytes(int b16) {
/*  97 */     if (this._currBlockPtr + 1 < this._currBlock.length) {
/*  98 */       this._currBlock[this._currBlockPtr++] = (byte)(b16 >> 8);
/*  99 */       this._currBlock[this._currBlockPtr++] = (byte)b16;
/*     */     } else {
/* 101 */       append(b16 >> 8);
/* 102 */       append(b16);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void appendThreeBytes(int b24) {
/* 107 */     if (this._currBlockPtr + 2 < this._currBlock.length) {
/* 108 */       this._currBlock[this._currBlockPtr++] = (byte)(b24 >> 16);
/* 109 */       this._currBlock[this._currBlockPtr++] = (byte)(b24 >> 8);
/* 110 */       this._currBlock[this._currBlockPtr++] = (byte)b24;
/*     */     } else {
/* 112 */       append(b24 >> 16);
/* 113 */       append(b24 >> 8);
/* 114 */       append(b24);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFourBytes(int b32) {
/* 122 */     if (this._currBlockPtr + 3 < this._currBlock.length) {
/* 123 */       this._currBlock[this._currBlockPtr++] = (byte)(b32 >> 24);
/* 124 */       this._currBlock[this._currBlockPtr++] = (byte)(b32 >> 16);
/* 125 */       this._currBlock[this._currBlockPtr++] = (byte)(b32 >> 8);
/* 126 */       this._currBlock[this._currBlockPtr++] = (byte)b32;
/*     */     } else {
/* 128 */       append(b32 >> 24);
/* 129 */       append(b32 >> 16);
/* 130 */       append(b32 >> 8);
/* 131 */       append(b32);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 141 */     int totalLen = this._pastLen + this._currBlockPtr;
/*     */     
/* 143 */     if (totalLen == 0) {
/* 144 */       return NO_BYTES;
/*     */     }
/* 146 */     byte[] result = new byte[totalLen];
/* 147 */     int offset = 0;
/*     */     
/* 149 */     for (byte[] block : this._pastBlocks) {
/* 150 */       int len = block.length;
/* 151 */       System.arraycopy(block, 0, result, offset, len);
/* 152 */       offset += len;
/*     */     } 
/* 154 */     System.arraycopy(this._currBlock, 0, result, offset, this._currBlockPtr);
/* 155 */     offset += this._currBlockPtr;
/* 156 */     if (offset != totalLen) {
/* 157 */       throw new RuntimeException("Internal error: total len assumed to be " + totalLen + ", copied " + offset + " bytes");
/*     */     }
/*     */     
/* 160 */     if (!this._pastBlocks.isEmpty()) {
/* 161 */       reset();
/*     */     }
/* 163 */     return result;
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
/*     */   
/*     */   public byte[] resetAndGetFirstSegment() {
/* 177 */     reset();
/* 178 */     return this._currBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] finishCurrentSegment() {
/* 187 */     _allocMore();
/* 188 */     return this._currBlock;
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
/*     */   public byte[] completeAndCoalesce(int lastBlockLength) {
/* 201 */     this._currBlockPtr = lastBlockLength;
/* 202 */     return toByteArray();
/*     */   }
/*     */   
/* 205 */   public byte[] getCurrentSegment() { return this._currBlock; }
/* 206 */   public void setCurrentSegmentLength(int len) { this._currBlockPtr = len; } public int getCurrentSegmentLength() {
/* 207 */     return this._currBlockPtr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) {
/* 217 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) {
/*     */     while (true) {
/* 224 */       int max = this._currBlock.length - this._currBlockPtr;
/* 225 */       int toCopy = Math.min(max, len);
/* 226 */       if (toCopy > 0) {
/* 227 */         System.arraycopy(b, off, this._currBlock, this._currBlockPtr, toCopy);
/* 228 */         off += toCopy;
/* 229 */         this._currBlockPtr += toCopy;
/* 230 */         len -= toCopy;
/*     */       } 
/* 232 */       if (len <= 0)
/* 233 */         break;  _allocMore();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) {
/* 239 */     append(b);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void _allocMore() {
/* 253 */     int newPastLen = this._pastLen + this._currBlock.length;
/*     */ 
/*     */ 
/*     */     
/* 257 */     if (newPastLen < 0) {
/* 258 */       throw new IllegalStateException("Maximum Java array size (2GB) exceeded by `ByteArrayBuilder`");
/*     */     }
/*     */     
/* 261 */     this._pastLen = newPastLen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     int newSize = Math.max(this._pastLen >> 1, 1000);
/*     */     
/* 271 */     if (newSize > 262144) {
/* 272 */       newSize = 262144;
/*     */     }
/* 274 */     this._pastBlocks.add(this._currBlock);
/* 275 */     this._currBlock = new byte[newSize];
/* 276 */     this._currBlockPtr = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\ByteArrayBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */