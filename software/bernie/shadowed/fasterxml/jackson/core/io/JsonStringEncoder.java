/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
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
/*     */ public final class JsonStringEncoder
/*     */ {
/*  19 */   private static final char[] HC = CharTypes.copyHexChars();
/*     */   
/*  21 */   private static final byte[] HB = CharTypes.copyHexBytes();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SURR1_FIRST = 55296;
/*     */ 
/*     */   
/*     */   private static final int SURR1_LAST = 56319;
/*     */ 
/*     */   
/*     */   private static final int SURR2_FIRST = 56320;
/*     */ 
/*     */   
/*     */   private static final int SURR2_LAST = 57343;
/*     */ 
/*     */   
/*  37 */   protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder = new ThreadLocal<SoftReference<JsonStringEncoder>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TextBuffer _text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteArrayBuilder _bytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final char[] _qbuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonStringEncoder() {
/*  64 */     this._qbuf = new char[6];
/*  65 */     this._qbuf[0] = '\\';
/*  66 */     this._qbuf[2] = '0';
/*  67 */     this._qbuf[3] = '0';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonStringEncoder getInstance() {
/*  75 */     SoftReference<JsonStringEncoder> ref = _threadEncoder.get();
/*  76 */     JsonStringEncoder enc = (ref == null) ? null : ref.get();
/*     */     
/*  78 */     if (enc == null) {
/*  79 */       enc = new JsonStringEncoder();
/*  80 */       _threadEncoder.set(new SoftReference<JsonStringEncoder>(enc));
/*     */     } 
/*  82 */     return enc;
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
/*     */   public char[] quoteAsString(String input) {
/*  97 */     TextBuffer textBuffer = this._text;
/*  98 */     if (textBuffer == null)
/*     */     {
/* 100 */       this._text = textBuffer = new TextBuffer(null);
/*     */     }
/* 102 */     char[] outputBuffer = textBuffer.emptyAndGetCurrentSegment();
/* 103 */     int[] escCodes = CharTypes.get7BitOutputEscapes();
/* 104 */     int escCodeCount = escCodes.length;
/* 105 */     int inPtr = 0;
/* 106 */     int inputLen = input.length();
/* 107 */     int outPtr = 0;
/*     */ 
/*     */     
/* 110 */     while (inPtr < inputLen) {
/*     */       
/*     */       label30: while (true) {
/* 113 */         char d, c = input.charAt(inPtr);
/* 114 */         if (c < escCodeCount && escCodes[c] != 0)
/*     */         
/*     */         { 
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
/* 127 */           d = input.charAt(inPtr++);
/* 128 */           int escCode = escCodes[d];
/* 129 */           int length = (escCode < 0) ? _appendNumeric(d, this._qbuf) : _appendNamed(escCode, this._qbuf);
/*     */ 
/*     */ 
/*     */           
/* 133 */           if (outPtr + length > outputBuffer.length) {
/* 134 */             int first = outputBuffer.length - outPtr;
/* 135 */             if (first > 0) {
/* 136 */               System.arraycopy(this._qbuf, 0, outputBuffer, outPtr, first);
/*     */               
/* 138 */               outputBuffer = textBuffer.finishCurrentSegment();
/* 139 */               int second = length - first;
/* 140 */               System.arraycopy(this._qbuf, first, outputBuffer, 0, second);
/* 141 */               outPtr = second; continue;
/*     */             }  break label30;
/* 143 */           }  System.arraycopy(this._qbuf, 0, outputBuffer, outPtr, length);
/* 144 */           outPtr += length; continue; }  if (outPtr >= outputBuffer.length) { outputBuffer = textBuffer.finishCurrentSegment(); outPtr = 0; }  outputBuffer[outPtr++] = d; if (++inPtr >= inputLen)
/*     */           break; 
/*     */       } 
/* 147 */     }  textBuffer.setCurrentLength(outPtr);
/* 148 */     return textBuffer.contentsAsArray();
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
/*     */   public void quoteAsString(CharSequence input, StringBuilder output) {
/* 160 */     int[] escCodes = CharTypes.get7BitOutputEscapes();
/* 161 */     int escCodeCount = escCodes.length;
/* 162 */     int inPtr = 0;
/* 163 */     int inputLen = input.length();
/*     */ 
/*     */     
/* 166 */     while (inPtr < inputLen) {
/*     */       
/*     */       while (true) {
/* 169 */         char d, c = input.charAt(inPtr);
/* 170 */         if (c < escCodeCount && escCodes[c] != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 179 */           d = input.charAt(inPtr++);
/* 180 */           int escCode = escCodes[d];
/* 181 */           if (escCode < 0); int length = _appendNamed(escCode, this._qbuf);
/*     */ 
/*     */ 
/*     */           
/* 185 */           output.append(this._qbuf, 0, length);
/*     */           continue;
/*     */         } 
/*     */         output.append(d);
/*     */         if (++inPtr >= inputLen)
/*     */           break; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] quoteAsUTF8(String text) {
/* 196 */     ByteArrayBuilder bb = this._bytes;
/* 197 */     if (bb == null)
/*     */     {
/* 199 */       this._bytes = bb = new ByteArrayBuilder(null);
/*     */     }
/* 201 */     int inputPtr = 0;
/* 202 */     int inputEnd = text.length();
/* 203 */     int outputPtr = 0;
/* 204 */     byte[] outputBuffer = bb.resetAndGetFirstSegment();
/*     */ 
/*     */     
/* 207 */     while (inputPtr < inputEnd) {
/* 208 */       int[] escCodes = CharTypes.get7BitOutputEscapes();
/*     */ 
/*     */       
/*     */       label52: while (true)
/* 212 */       { int ch = text.charAt(inputPtr);
/* 213 */         if (ch > 127 || escCodes[ch] != 0)
/*     */         
/*     */         { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 225 */           if (outputPtr >= outputBuffer.length) {
/* 226 */             outputBuffer = bb.finishCurrentSegment();
/* 227 */             outputPtr = 0;
/*     */           } 
/*     */           
/* 230 */           ch = text.charAt(inputPtr++);
/* 231 */           if (ch <= 127) {
/* 232 */             int escape = escCodes[ch];
/*     */             
/* 234 */             outputPtr = _appendByte(ch, escape, bb, outputPtr);
/* 235 */             outputBuffer = bb.getCurrentSegment();
/*     */             continue;
/*     */           } 
/* 238 */           if (ch <= 2047) {
/* 239 */             outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 240 */             ch = 0x80 | ch & 0x3F;
/*     */           
/*     */           }
/* 243 */           else if (ch < 55296 || ch > 57343) {
/* 244 */             outputBuffer[outputPtr++] = (byte)(0xE0 | ch >> 12);
/* 245 */             if (outputPtr >= outputBuffer.length) {
/* 246 */               outputBuffer = bb.finishCurrentSegment();
/* 247 */               outputPtr = 0;
/*     */             } 
/* 249 */             outputBuffer[outputPtr++] = (byte)(0x80 | ch >> 6 & 0x3F);
/* 250 */             ch = 0x80 | ch & 0x3F;
/*     */           } else {
/* 252 */             if (ch > 56319) {
/* 253 */               _illegal(ch);
/*     */             }
/*     */             
/* 256 */             if (inputPtr >= inputEnd) {
/* 257 */               _illegal(ch);
/*     */             }
/* 259 */             ch = _convert(ch, text.charAt(inputPtr++));
/* 260 */             if (ch > 1114111) {
/* 261 */               _illegal(ch);
/*     */             }
/* 263 */             outputBuffer[outputPtr++] = (byte)(0xF0 | ch >> 18);
/* 264 */             if (outputPtr >= outputBuffer.length) {
/* 265 */               outputBuffer = bb.finishCurrentSegment();
/* 266 */               outputPtr = 0;
/*     */             } 
/* 268 */             outputBuffer[outputPtr++] = (byte)(0x80 | ch >> 12 & 0x3F);
/* 269 */             if (outputPtr >= outputBuffer.length) {
/* 270 */               outputBuffer = bb.finishCurrentSegment();
/* 271 */               outputPtr = 0;
/*     */             } 
/* 273 */             outputBuffer[outputPtr++] = (byte)(0x80 | ch >> 6 & 0x3F);
/* 274 */             ch = 0x80 | ch & 0x3F;
/*     */           } 
/*     */           
/* 277 */           if (outputPtr >= outputBuffer.length)
/* 278 */           { outputBuffer = bb.finishCurrentSegment();
/* 279 */             outputPtr = 0;
/*     */             
/* 281 */             outputBuffer[outputPtr++] = (byte)ch; continue; }  break label52; }  if (outputPtr >= outputBuffer.length) { outputBuffer = bb.finishCurrentSegment(); outputPtr = 0; }  outputBuffer[outputPtr++] = (byte)ch; if (++inputPtr >= inputEnd)
/*     */           break;  } 
/* 283 */     }  return this._bytes.completeAndCoalesce(outputPtr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encodeAsUTF8(String text) {
/* 293 */     ByteArrayBuilder byteBuilder = this._bytes;
/* 294 */     if (byteBuilder == null)
/*     */     {
/* 296 */       this._bytes = byteBuilder = new ByteArrayBuilder(null);
/*     */     }
/* 298 */     int inputPtr = 0;
/* 299 */     int inputEnd = text.length();
/* 300 */     int outputPtr = 0;
/* 301 */     byte[] outputBuffer = byteBuilder.resetAndGetFirstSegment();
/* 302 */     int outputEnd = outputBuffer.length;
/*     */ 
/*     */     
/* 305 */     label48: while (inputPtr < inputEnd) {
/* 306 */       int c = text.charAt(inputPtr++);
/*     */ 
/*     */       
/* 309 */       while (c <= 127) {
/* 310 */         if (outputPtr >= outputEnd) {
/* 311 */           outputBuffer = byteBuilder.finishCurrentSegment();
/* 312 */           outputEnd = outputBuffer.length;
/* 313 */           outputPtr = 0;
/*     */         } 
/* 315 */         outputBuffer[outputPtr++] = (byte)c;
/* 316 */         if (inputPtr >= inputEnd) {
/*     */           break label48;
/*     */         }
/* 319 */         c = text.charAt(inputPtr++);
/*     */       } 
/*     */ 
/*     */       
/* 323 */       if (outputPtr >= outputEnd) {
/* 324 */         outputBuffer = byteBuilder.finishCurrentSegment();
/* 325 */         outputEnd = outputBuffer.length;
/* 326 */         outputPtr = 0;
/*     */       } 
/* 328 */       if (c < 2048) {
/* 329 */         outputBuffer[outputPtr++] = (byte)(0xC0 | c >> 6);
/*     */       
/*     */       }
/* 332 */       else if (c < 55296 || c > 57343) {
/* 333 */         outputBuffer[outputPtr++] = (byte)(0xE0 | c >> 12);
/* 334 */         if (outputPtr >= outputEnd) {
/* 335 */           outputBuffer = byteBuilder.finishCurrentSegment();
/* 336 */           outputEnd = outputBuffer.length;
/* 337 */           outputPtr = 0;
/*     */         } 
/* 339 */         outputBuffer[outputPtr++] = (byte)(0x80 | c >> 6 & 0x3F);
/*     */       } else {
/* 341 */         if (c > 56319) {
/* 342 */           _illegal(c);
/*     */         }
/*     */         
/* 345 */         if (inputPtr >= inputEnd) {
/* 346 */           _illegal(c);
/*     */         }
/* 348 */         c = _convert(c, text.charAt(inputPtr++));
/* 349 */         if (c > 1114111) {
/* 350 */           _illegal(c);
/*     */         }
/* 352 */         outputBuffer[outputPtr++] = (byte)(0xF0 | c >> 18);
/* 353 */         if (outputPtr >= outputEnd) {
/* 354 */           outputBuffer = byteBuilder.finishCurrentSegment();
/* 355 */           outputEnd = outputBuffer.length;
/* 356 */           outputPtr = 0;
/*     */         } 
/* 358 */         outputBuffer[outputPtr++] = (byte)(0x80 | c >> 12 & 0x3F);
/* 359 */         if (outputPtr >= outputEnd) {
/* 360 */           outputBuffer = byteBuilder.finishCurrentSegment();
/* 361 */           outputEnd = outputBuffer.length;
/* 362 */           outputPtr = 0;
/*     */         } 
/* 364 */         outputBuffer[outputPtr++] = (byte)(0x80 | c >> 6 & 0x3F);
/*     */       } 
/*     */       
/* 367 */       if (outputPtr >= outputEnd) {
/* 368 */         outputBuffer = byteBuilder.finishCurrentSegment();
/* 369 */         outputEnd = outputBuffer.length;
/* 370 */         outputPtr = 0;
/*     */       } 
/* 372 */       outputBuffer[outputPtr++] = (byte)(0x80 | c & 0x3F);
/*     */     } 
/* 374 */     return this._bytes.completeAndCoalesce(outputPtr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _appendNumeric(int value, char[] qbuf) {
/* 384 */     qbuf[1] = 'u';
/*     */     
/* 386 */     qbuf[4] = HC[value >> 4];
/* 387 */     qbuf[5] = HC[value & 0xF];
/* 388 */     return 6;
/*     */   }
/*     */   
/*     */   private int _appendNamed(int esc, char[] qbuf) {
/* 392 */     qbuf[1] = (char)esc;
/* 393 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   private int _appendByte(int ch, int esc, ByteArrayBuilder bb, int ptr) {
/* 398 */     bb.setCurrentSegmentLength(ptr);
/* 399 */     bb.append(92);
/* 400 */     if (esc < 0) {
/* 401 */       bb.append(117);
/* 402 */       if (ch > 255) {
/* 403 */         int hi = ch >> 8;
/* 404 */         bb.append(HB[hi >> 4]);
/* 405 */         bb.append(HB[hi & 0xF]);
/* 406 */         ch &= 0xFF;
/*     */       } else {
/* 408 */         bb.append(48);
/* 409 */         bb.append(48);
/*     */       } 
/* 411 */       bb.append(HB[ch >> 4]);
/* 412 */       bb.append(HB[ch & 0xF]);
/*     */     } else {
/* 414 */       bb.append((byte)esc);
/*     */     } 
/* 416 */     return bb.getCurrentSegmentLength();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int _convert(int p1, int p2) {
/* 421 */     if (p2 < 56320 || p2 > 57343) {
/* 422 */       throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(p1) + ", second 0x" + Integer.toHexString(p2) + "; illegal combination");
/*     */     }
/* 424 */     return 65536 + (p1 - 55296 << 10) + p2 - 56320;
/*     */   }
/*     */   
/*     */   private static void _illegal(int c) {
/* 428 */     throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(c));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\JsonStringEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */