/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
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
/*     */ public final class UTF8Writer
/*     */   extends Writer
/*     */ {
/*     */   static final int SURR1_FIRST = 55296;
/*     */   static final int SURR1_LAST = 56319;
/*     */   static final int SURR2_FIRST = 56320;
/*     */   static final int SURR2_LAST = 57343;
/*     */   private final IOContext _context;
/*     */   private OutputStream _out;
/*     */   private byte[] _outBuffer;
/*     */   private final int _outBufferEnd;
/*     */   private int _outPtr;
/*     */   private int _surrogate;
/*     */   
/*     */   public UTF8Writer(IOContext ctxt, OutputStream out) {
/*  31 */     this._context = ctxt;
/*  32 */     this._out = out;
/*     */     
/*  34 */     this._outBuffer = ctxt.allocWriteEncodingBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     this._outBufferEnd = this._outBuffer.length - 4;
/*  40 */     this._outPtr = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer append(char c) throws IOException {
/*  47 */     write(c);
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  55 */     if (this._out != null) {
/*  56 */       if (this._outPtr > 0) {
/*  57 */         this._out.write(this._outBuffer, 0, this._outPtr);
/*  58 */         this._outPtr = 0;
/*     */       } 
/*  60 */       OutputStream out = this._out;
/*  61 */       this._out = null;
/*     */       
/*  63 */       byte[] buf = this._outBuffer;
/*  64 */       if (buf != null) {
/*  65 */         this._outBuffer = null;
/*  66 */         this._context.releaseWriteEncodingBuffer(buf);
/*     */       } 
/*     */       
/*  69 */       out.close();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       int code = this._surrogate;
/*  75 */       this._surrogate = 0;
/*  76 */       if (code > 0) {
/*  77 */         illegalSurrogate(code);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/*  86 */     if (this._out != null) {
/*  87 */       if (this._outPtr > 0) {
/*  88 */         this._out.write(this._outBuffer, 0, this._outPtr);
/*  89 */         this._outPtr = 0;
/*     */       } 
/*  91 */       this._out.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(char[] cbuf) throws IOException {
/*  99 */     write(cbuf, 0, cbuf.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(char[] cbuf, int off, int len) throws IOException {
/* 106 */     if (len < 2) {
/* 107 */       if (len == 1) {
/* 108 */         write(cbuf[off]);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 114 */     if (this._surrogate > 0) {
/* 115 */       char second = cbuf[off++];
/* 116 */       len--;
/* 117 */       write(convertSurrogate(second));
/*     */     } 
/*     */ 
/*     */     
/* 121 */     int outPtr = this._outPtr;
/* 122 */     byte[] outBuf = this._outBuffer;
/* 123 */     int outBufLast = this._outBufferEnd;
/*     */ 
/*     */     
/* 126 */     len += off;
/*     */ 
/*     */     
/* 129 */     label45: while (off < len) {
/*     */ 
/*     */ 
/*     */       
/* 133 */       if (outPtr >= outBufLast) {
/* 134 */         this._out.write(outBuf, 0, outPtr);
/* 135 */         outPtr = 0;
/*     */       } 
/*     */       
/* 138 */       int c = cbuf[off++];
/*     */       
/* 140 */       if (c < 128) {
/* 141 */         outBuf[outPtr++] = (byte)c;
/*     */         
/* 143 */         int maxInCount = len - off;
/* 144 */         int maxOutCount = outBufLast - outPtr;
/*     */         
/* 146 */         if (maxInCount > maxOutCount) {
/* 147 */           maxInCount = maxOutCount;
/*     */         }
/* 149 */         maxInCount += off;
/*     */ 
/*     */         
/* 152 */         while (off < maxInCount) {
/*     */ 
/*     */           
/* 155 */           c = cbuf[off++];
/* 156 */           if (c >= 128) {
/*     */             continue label45;
/*     */           }
/* 159 */           outBuf[outPtr++] = (byte)c;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 164 */       if (c < 2048) {
/* 165 */         outBuf[outPtr++] = (byte)(0xC0 | c >> 6);
/* 166 */         outBuf[outPtr++] = (byte)(0x80 | c & 0x3F);
/*     */         continue;
/*     */       } 
/* 169 */       if (c < 55296 || c > 57343) {
/* 170 */         outBuf[outPtr++] = (byte)(0xE0 | c >> 12);
/* 171 */         outBuf[outPtr++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 172 */         outBuf[outPtr++] = (byte)(0x80 | c & 0x3F);
/*     */         
/*     */         continue;
/*     */       } 
/* 176 */       if (c > 56319) {
/* 177 */         this._outPtr = outPtr;
/* 178 */         illegalSurrogate(c);
/*     */       } 
/* 180 */       this._surrogate = c;
/*     */       
/* 182 */       if (off >= len) {
/*     */         break;
/*     */       }
/* 185 */       c = convertSurrogate(cbuf[off++]);
/* 186 */       if (c > 1114111) {
/* 187 */         this._outPtr = outPtr;
/* 188 */         illegalSurrogate(c);
/*     */       } 
/* 190 */       outBuf[outPtr++] = (byte)(0xF0 | c >> 18);
/* 191 */       outBuf[outPtr++] = (byte)(0x80 | c >> 12 & 0x3F);
/* 192 */       outBuf[outPtr++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 193 */       outBuf[outPtr++] = (byte)(0x80 | c & 0x3F);
/*     */     } 
/*     */     
/* 196 */     this._outPtr = outPtr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/* 203 */     if (this._surrogate > 0) {
/* 204 */       c = convertSurrogate(c);
/*     */     }
/* 206 */     else if (c >= 55296 && c <= 57343) {
/*     */       
/* 208 */       if (c > 56319) {
/* 209 */         illegalSurrogate(c);
/*     */       }
/*     */       
/* 212 */       this._surrogate = c;
/*     */       
/*     */       return;
/*     */     } 
/* 216 */     if (this._outPtr >= this._outBufferEnd) {
/* 217 */       this._out.write(this._outBuffer, 0, this._outPtr);
/* 218 */       this._outPtr = 0;
/*     */     } 
/*     */     
/* 221 */     if (c < 128) {
/* 222 */       this._outBuffer[this._outPtr++] = (byte)c;
/*     */     } else {
/* 224 */       int ptr = this._outPtr;
/* 225 */       if (c < 2048) {
/* 226 */         this._outBuffer[ptr++] = (byte)(0xC0 | c >> 6);
/* 227 */         this._outBuffer[ptr++] = (byte)(0x80 | c & 0x3F);
/* 228 */       } else if (c <= 65535) {
/* 229 */         this._outBuffer[ptr++] = (byte)(0xE0 | c >> 12);
/* 230 */         this._outBuffer[ptr++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 231 */         this._outBuffer[ptr++] = (byte)(0x80 | c & 0x3F);
/*     */       } else {
/* 233 */         if (c > 1114111) {
/* 234 */           illegalSurrogate(c);
/*     */         }
/* 236 */         this._outBuffer[ptr++] = (byte)(0xF0 | c >> 18);
/* 237 */         this._outBuffer[ptr++] = (byte)(0x80 | c >> 12 & 0x3F);
/* 238 */         this._outBuffer[ptr++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 239 */         this._outBuffer[ptr++] = (byte)(0x80 | c & 0x3F);
/*     */       } 
/* 241 */       this._outPtr = ptr;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(String str) throws IOException {
/* 248 */     write(str, 0, str.length());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(String str, int off, int len) throws IOException {
/* 254 */     if (len < 2) {
/* 255 */       if (len == 1) {
/* 256 */         write(str.charAt(off));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 262 */     if (this._surrogate > 0) {
/* 263 */       char second = str.charAt(off++);
/* 264 */       len--;
/* 265 */       write(convertSurrogate(second));
/*     */     } 
/*     */ 
/*     */     
/* 269 */     int outPtr = this._outPtr;
/* 270 */     byte[] outBuf = this._outBuffer;
/* 271 */     int outBufLast = this._outBufferEnd;
/*     */ 
/*     */     
/* 274 */     len += off;
/*     */ 
/*     */     
/* 277 */     label45: while (off < len) {
/*     */ 
/*     */ 
/*     */       
/* 281 */       if (outPtr >= outBufLast) {
/* 282 */         this._out.write(outBuf, 0, outPtr);
/* 283 */         outPtr = 0;
/*     */       } 
/*     */       
/* 286 */       int c = str.charAt(off++);
/*     */       
/* 288 */       if (c < 128) {
/* 289 */         outBuf[outPtr++] = (byte)c;
/*     */         
/* 291 */         int maxInCount = len - off;
/* 292 */         int maxOutCount = outBufLast - outPtr;
/*     */         
/* 294 */         if (maxInCount > maxOutCount) {
/* 295 */           maxInCount = maxOutCount;
/*     */         }
/* 297 */         maxInCount += off;
/*     */ 
/*     */         
/* 300 */         while (off < maxInCount) {
/*     */ 
/*     */           
/* 303 */           c = str.charAt(off++);
/* 304 */           if (c >= 128) {
/*     */             continue label45;
/*     */           }
/* 307 */           outBuf[outPtr++] = (byte)c;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 312 */       if (c < 2048) {
/* 313 */         outBuf[outPtr++] = (byte)(0xC0 | c >> 6);
/* 314 */         outBuf[outPtr++] = (byte)(0x80 | c & 0x3F);
/*     */         continue;
/*     */       } 
/* 317 */       if (c < 55296 || c > 57343) {
/* 318 */         outBuf[outPtr++] = (byte)(0xE0 | c >> 12);
/* 319 */         outBuf[outPtr++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 320 */         outBuf[outPtr++] = (byte)(0x80 | c & 0x3F);
/*     */         
/*     */         continue;
/*     */       } 
/* 324 */       if (c > 56319) {
/* 325 */         this._outPtr = outPtr;
/* 326 */         illegalSurrogate(c);
/*     */       } 
/* 328 */       this._surrogate = c;
/*     */       
/* 330 */       if (off >= len) {
/*     */         break;
/*     */       }
/* 333 */       c = convertSurrogate(str.charAt(off++));
/* 334 */       if (c > 1114111) {
/* 335 */         this._outPtr = outPtr;
/* 336 */         illegalSurrogate(c);
/*     */       } 
/* 338 */       outBuf[outPtr++] = (byte)(0xF0 | c >> 18);
/* 339 */       outBuf[outPtr++] = (byte)(0x80 | c >> 12 & 0x3F);
/* 340 */       outBuf[outPtr++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 341 */       outBuf[outPtr++] = (byte)(0x80 | c & 0x3F);
/*     */     } 
/*     */     
/* 344 */     this._outPtr = outPtr;
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
/*     */   
/*     */   protected int convertSurrogate(int secondPart) throws IOException {
/* 359 */     int firstPart = this._surrogate;
/* 360 */     this._surrogate = 0;
/*     */ 
/*     */     
/* 363 */     if (secondPart < 56320 || secondPart > 57343) {
/* 364 */       throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(firstPart) + ", second 0x" + Integer.toHexString(secondPart) + "; illegal combination");
/*     */     }
/* 366 */     return 65536 + (firstPart - 55296 << 10) + secondPart - 56320;
/*     */   }
/*     */   
/*     */   protected static void illegalSurrogate(int code) throws IOException {
/* 370 */     throw new IOException(illegalSurrogateDesc(code));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String illegalSurrogateDesc(int code) {
/* 375 */     if (code > 1114111) {
/* 376 */       return "Illegal character point (0x" + Integer.toHexString(code) + ") to output; max is 0x10FFFF as per RFC 4627";
/*     */     }
/* 378 */     if (code >= 55296) {
/* 379 */       if (code <= 56319) {
/* 380 */         return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(code) + ")";
/*     */       }
/* 382 */       return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(code) + ")";
/*     */     } 
/*     */     
/* 385 */     return "Illegal character point (0x" + Integer.toHexString(code) + ") to output";
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\UTF8Writer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */