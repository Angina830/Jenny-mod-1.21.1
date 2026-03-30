/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public final class MergedStream
/*     */   extends InputStream
/*     */ {
/*     */   private final IOContext _ctxt;
/*     */   private final InputStream _in;
/*     */   private byte[] _b;
/*     */   private int _ptr;
/*     */   private final int _end;
/*     */   
/*     */   public MergedStream(IOContext ctxt, InputStream in, byte[] buf, int start, int end) {
/*  26 */     this._ctxt = ctxt;
/*  27 */     this._in = in;
/*  28 */     this._b = buf;
/*  29 */     this._ptr = start;
/*  30 */     this._end = end;
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/*  35 */     if (this._b != null) {
/*  36 */       return this._end - this._ptr;
/*     */     }
/*  38 */     return this._in.available();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*  42 */     _free();
/*  43 */     this._in.close();
/*     */   }
/*     */   
/*     */   public void mark(int readlimit) {
/*  47 */     if (this._b == null) this._in.mark(readlimit);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean markSupported() {
/*  52 */     return (this._b == null && this._in.markSupported());
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  56 */     if (this._b != null) {
/*  57 */       int c = this._b[this._ptr++] & 0xFF;
/*  58 */       if (this._ptr >= this._end) {
/*  59 */         _free();
/*     */       }
/*  61 */       return c;
/*     */     } 
/*  63 */     return this._in.read();
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*  67 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/*  72 */     if (this._b != null) {
/*  73 */       int avail = this._end - this._ptr;
/*  74 */       if (len > avail) {
/*  75 */         len = avail;
/*     */       }
/*  77 */       System.arraycopy(this._b, this._ptr, b, off, len);
/*  78 */       this._ptr += len;
/*  79 */       if (this._ptr >= this._end) {
/*  80 */         _free();
/*     */       }
/*  82 */       return len;
/*     */     } 
/*  84 */     return this._in.read(b, off, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/*  89 */     if (this._b == null) this._in.reset();
/*     */   
/*     */   }
/*     */   
/*     */   public long skip(long n) throws IOException {
/*  94 */     long count = 0L;
/*     */     
/*  96 */     if (this._b != null) {
/*  97 */       int amount = this._end - this._ptr;
/*     */       
/*  99 */       if (amount > n) {
/* 100 */         this._ptr += (int)n;
/* 101 */         return n;
/*     */       } 
/* 103 */       _free();
/* 104 */       count += amount;
/* 105 */       n -= amount;
/*     */     } 
/*     */     
/* 108 */     if (n > 0L) count += this._in.skip(n); 
/* 109 */     return count;
/*     */   }
/*     */   
/*     */   private void _free() {
/* 113 */     byte[] buf = this._b;
/* 114 */     if (buf != null) {
/* 115 */       this._b = null;
/* 116 */       if (this._ctxt != null)
/* 117 */         this._ctxt.releaseReadIOBuffer(buf); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\MergedStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */