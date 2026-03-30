/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.CharConversionException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
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
/*     */ public class UTF32Reader
/*     */   extends Reader
/*     */ {
/*     */   protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
/*     */   protected static final char NC = '\000';
/*     */   protected final IOContext _context;
/*     */   protected InputStream _in;
/*     */   protected byte[] _buffer;
/*     */   protected int _ptr;
/*     */   protected int _length;
/*     */   protected final boolean _bigEndian;
/*  37 */   protected char _surrogate = Character.MIN_VALUE;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _charCount;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _byteCount;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _managedBuffers;
/*     */ 
/*     */ 
/*     */   
/*     */   protected char[] _tmpBuf;
/*     */ 
/*     */ 
/*     */   
/*     */   public UTF32Reader(IOContext ctxt, InputStream in, byte[] buf, int ptr, int len, boolean isBigEndian) {
/*  58 */     this._context = ctxt;
/*  59 */     this._in = in;
/*  60 */     this._buffer = buf;
/*  61 */     this._ptr = ptr;
/*  62 */     this._length = len;
/*  63 */     this._bigEndian = isBigEndian;
/*  64 */     this._managedBuffers = (in != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  75 */     InputStream in = this._in;
/*     */     
/*  77 */     if (in != null) {
/*  78 */       this._in = null;
/*  79 */       freeBuffers();
/*  80 */       in.close();
/*     */     } 
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
/*     */   public int read() throws IOException {
/*  93 */     if (this._tmpBuf == null) {
/*  94 */       this._tmpBuf = new char[1];
/*     */     }
/*  96 */     if (read(this._tmpBuf, 0, 1) < 1) {
/*  97 */       return -1;
/*     */     }
/*  99 */     return this._tmpBuf[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(char[] cbuf, int start, int len) throws IOException {
/* 105 */     if (this._buffer == null) return -1; 
/* 106 */     if (len < 1) return len;
/*     */     
/* 108 */     if (start < 0 || start + len > cbuf.length) {
/* 109 */       reportBounds(cbuf, start, len);
/*     */     }
/*     */     
/* 112 */     int outPtr = start;
/* 113 */     int outEnd = len + start;
/*     */ 
/*     */     
/* 116 */     if (this._surrogate != '\000') {
/* 117 */       cbuf[outPtr++] = this._surrogate;
/* 118 */       this._surrogate = Character.MIN_VALUE;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 123 */       int left = this._length - this._ptr;
/* 124 */       if (left < 4 && 
/* 125 */         !loadMore(left)) {
/*     */         
/* 127 */         if (left == 0) {
/* 128 */           return -1;
/*     */         }
/* 130 */         reportUnexpectedEOF(this._length - this._ptr, 4);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 136 */     int _lastValidInputStart = this._length - 3;
/*     */ 
/*     */     
/* 139 */     while (outPtr < outEnd) {
/* 140 */       int hi, lo, ptr = this._ptr;
/*     */ 
/*     */       
/* 143 */       if (this._bigEndian) {
/* 144 */         hi = this._buffer[ptr] << 8 | this._buffer[ptr + 1] & 0xFF;
/* 145 */         lo = (this._buffer[ptr + 2] & 0xFF) << 8 | this._buffer[ptr + 3] & 0xFF;
/*     */       } else {
/* 147 */         lo = this._buffer[ptr] & 0xFF | (this._buffer[ptr + 1] & 0xFF) << 8;
/* 148 */         hi = this._buffer[ptr + 2] & 0xFF | this._buffer[ptr + 3] << 8;
/*     */       } 
/* 150 */       this._ptr += 4;
/*     */ 
/*     */ 
/*     */       
/* 154 */       if (hi != 0) {
/* 155 */         hi &= 0xFFFF;
/* 156 */         int ch = hi - 1 << 16 | lo;
/* 157 */         if (hi > 16) {
/* 158 */           reportInvalid(ch, outPtr - start, String.format(" (above 0x%08x)", new Object[] { Integer.valueOf(1114111) }));
/*     */         }
/*     */         
/* 161 */         cbuf[outPtr++] = (char)(55296 + (ch >> 10));
/*     */         
/* 163 */         lo = 0xDC00 | ch & 0x3FF;
/*     */         
/* 165 */         if (outPtr >= outEnd) {
/* 166 */           this._surrogate = (char)ch;
/*     */           break;
/*     */         } 
/*     */       } 
/* 170 */       cbuf[outPtr++] = (char)lo;
/* 171 */       if (this._ptr > _lastValidInputStart) {
/*     */         break;
/*     */       }
/*     */     } 
/* 175 */     int actualLen = outPtr - start;
/* 176 */     this._charCount += actualLen;
/* 177 */     return actualLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reportUnexpectedEOF(int gotBytes, int needed) throws IOException {
/* 187 */     int bytePos = this._byteCount + gotBytes, charPos = this._charCount;
/*     */     
/* 189 */     throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + gotBytes + ", needed " + needed + ", at char #" + charPos + ", byte #" + bytePos + ")");
/*     */   }
/*     */   
/*     */   private void reportInvalid(int value, int offset, String msg) throws IOException {
/* 193 */     int bytePos = this._byteCount + this._ptr - 1, charPos = this._charCount + offset;
/*     */     
/* 195 */     throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(value) + msg + " at char #" + charPos + ", byte #" + bytePos + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loadMore(int available) throws IOException {
/* 205 */     this._byteCount += this._length - available;
/*     */ 
/*     */     
/* 208 */     if (available > 0) {
/* 209 */       if (this._ptr > 0) {
/* 210 */         System.arraycopy(this._buffer, this._ptr, this._buffer, 0, available);
/* 211 */         this._ptr = 0;
/*     */       } 
/* 213 */       this._length = available;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 218 */       this._ptr = 0;
/* 219 */       int count = (this._in == null) ? -1 : this._in.read(this._buffer);
/* 220 */       if (count < 1) {
/* 221 */         this._length = 0;
/* 222 */         if (count < 0) {
/* 223 */           if (this._managedBuffers) {
/* 224 */             freeBuffers();
/*     */           }
/* 226 */           return false;
/*     */         } 
/*     */         
/* 229 */         reportStrangeStream();
/*     */       } 
/* 231 */       this._length = count;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     while (this._length < 4) {
/* 238 */       int count = (this._in == null) ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
/* 239 */       if (count < 1) {
/* 240 */         if (count < 0) {
/* 241 */           if (this._managedBuffers) {
/* 242 */             freeBuffers();
/*     */           }
/* 244 */           reportUnexpectedEOF(this._length, 4);
/*     */         } 
/*     */         
/* 247 */         reportStrangeStream();
/*     */       } 
/* 249 */       this._length += count;
/*     */     } 
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void freeBuffers() {
/* 260 */     byte[] buf = this._buffer;
/* 261 */     if (buf != null) {
/* 262 */       this._buffer = null;
/* 263 */       this._context.releaseReadIOBuffer(buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void reportBounds(char[] cbuf, int start, int len) throws IOException {
/* 268 */     throw new ArrayIndexOutOfBoundsException("read(buf," + start + "," + len + "), cbuf[" + cbuf.length + "]");
/*     */   }
/*     */   
/*     */   private void reportStrangeStream() throws IOException {
/* 272 */     throw new IOException("Strange I/O stream, returned 0 bytes on read");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\UTF32Reader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */