/*     */ package software.bernie.shadowed.fasterxml.jackson.core.format;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
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
/*     */ public interface InputAccessor
/*     */ {
/*     */   boolean hasMoreBytes() throws IOException;
/*     */   
/*     */   byte nextByte() throws IOException;
/*     */   
/*     */   void reset();
/*     */   
/*     */   public static class Std
/*     */     implements InputAccessor
/*     */   {
/*     */     protected final InputStream _in;
/*     */     protected final byte[] _buffer;
/*     */     protected final int _bufferedStart;
/*     */     protected int _bufferedEnd;
/*     */     protected int _ptr;
/*     */     
/*     */     public Std(InputStream in, byte[] buffer) {
/*  66 */       this._in = in;
/*  67 */       this._buffer = buffer;
/*  68 */       this._bufferedStart = 0;
/*  69 */       this._ptr = 0;
/*  70 */       this._bufferedEnd = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(byte[] inputDocument) {
/*  79 */       this._in = null;
/*  80 */       this._buffer = inputDocument;
/*     */       
/*  82 */       this._bufferedStart = 0;
/*  83 */       this._bufferedEnd = inputDocument.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(byte[] inputDocument, int start, int len) {
/*  94 */       this._in = null;
/*  95 */       this._buffer = inputDocument;
/*  96 */       this._ptr = start;
/*  97 */       this._bufferedStart = start;
/*  98 */       this._bufferedEnd = start + len;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasMoreBytes() throws IOException {
/* 104 */       if (this._ptr < this._bufferedEnd) {
/* 105 */         return true;
/*     */       }
/* 107 */       if (this._in == null) {
/* 108 */         return false;
/*     */       }
/* 110 */       int amount = this._buffer.length - this._ptr;
/* 111 */       if (amount < 1) {
/* 112 */         return false;
/*     */       }
/* 114 */       int count = this._in.read(this._buffer, this._ptr, amount);
/* 115 */       if (count <= 0) {
/* 116 */         return false;
/*     */       }
/* 118 */       this._bufferedEnd += count;
/* 119 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte nextByte() throws IOException {
/* 126 */       if (this._ptr >= this._bufferedEnd && 
/* 127 */         !hasMoreBytes()) {
/* 128 */         throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
/*     */       }
/*     */       
/* 131 */       return this._buffer[this._ptr++];
/*     */     }
/*     */ 
/*     */     
/*     */     public void reset() {
/* 136 */       this._ptr = this._bufferedStart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DataFormatMatcher createMatcher(JsonFactory match, MatchStrength matchStrength) {
/* 147 */       return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, match, matchStrength);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\format\InputAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */