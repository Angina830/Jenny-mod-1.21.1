/*     */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharConversionException;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonEncoding;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.format.InputAccessor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.format.MatchStrength;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.MergedStream;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.UTF32Reader;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
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
/*     */ public final class ByteSourceJsonBootstrapper
/*     */ {
/*     */   public static final byte UTF8_BOM_1 = -17;
/*     */   public static final byte UTF8_BOM_2 = -69;
/*     */   public static final byte UTF8_BOM_3 = -65;
/*     */   private final IOContext _context;
/*     */   private final InputStream _in;
/*     */   private final byte[] _inputBuffer;
/*     */   private int _inputPtr;
/*     */   private int _inputEnd;
/*     */   private final boolean _bufferRecyclable;
/*     */   private boolean _bigEndian = true;
/*     */   private int _bytesPerChar;
/*     */   
/*     */   public ByteSourceJsonBootstrapper(IOContext ctxt, InputStream in) {
/*  88 */     this._context = ctxt;
/*  89 */     this._in = in;
/*  90 */     this._inputBuffer = ctxt.allocReadIOBuffer();
/*  91 */     this._inputEnd = this._inputPtr = 0;
/*     */     
/*  93 */     this._bufferRecyclable = true;
/*     */   }
/*     */   
/*     */   public ByteSourceJsonBootstrapper(IOContext ctxt, byte[] inputBuffer, int inputStart, int inputLen) {
/*  97 */     this._context = ctxt;
/*  98 */     this._in = null;
/*  99 */     this._inputBuffer = inputBuffer;
/* 100 */     this._inputPtr = inputStart;
/* 101 */     this._inputEnd = inputStart + inputLen;
/*     */ 
/*     */     
/* 104 */     this._bufferRecyclable = false;
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
/*     */   public JsonEncoding detectEncoding() throws IOException {
/*     */     JsonEncoding enc;
/* 120 */     boolean foundEncoding = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (ensureLoaded(4)) {
/* 130 */       int quad = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[this._inputPtr + 1] & 0xFF) << 16 | (this._inputBuffer[this._inputPtr + 2] & 0xFF) << 8 | this._inputBuffer[this._inputPtr + 3] & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       if (handleBOM(quad)) {
/* 136 */         foundEncoding = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 144 */       else if (checkUTF32(quad)) {
/* 145 */         foundEncoding = true;
/* 146 */       } else if (checkUTF16(quad >>> 16)) {
/* 147 */         foundEncoding = true;
/*     */       }
/*     */     
/* 150 */     } else if (ensureLoaded(2)) {
/* 151 */       int i16 = (this._inputBuffer[this._inputPtr] & 0xFF) << 8 | this._inputBuffer[this._inputPtr + 1] & 0xFF;
/*     */       
/* 153 */       if (checkUTF16(i16)) {
/* 154 */         foundEncoding = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (!foundEncoding)
/* 162 */     { enc = JsonEncoding.UTF8; }
/*     */     else
/* 164 */     { switch (this._bytesPerChar) { case 1:
/* 165 */           enc = JsonEncoding.UTF8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 174 */           this._context.setEncoding(enc);
/* 175 */           return enc;case 2: enc = this._bigEndian ? JsonEncoding.UTF16_BE : JsonEncoding.UTF16_LE; this._context.setEncoding(enc); return enc;case 4: enc = this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE; this._context.setEncoding(enc); return enc; }  throw new RuntimeException("Internal error"); }  this._context.setEncoding(enc); return enc;
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
/*     */   public static int skipUTF8BOM(DataInput input) throws IOException {
/* 187 */     int b = input.readUnsignedByte();
/* 188 */     if (b != 239) {
/* 189 */       return b;
/*     */     }
/*     */ 
/*     */     
/* 193 */     b = input.readUnsignedByte();
/* 194 */     if (b != 187) {
/* 195 */       throw new IOException("Unexpected byte 0x" + Integer.toHexString(b) + " following 0xEF; should get 0xBB as part of UTF-8 BOM");
/*     */     }
/*     */     
/* 198 */     b = input.readUnsignedByte();
/* 199 */     if (b != 191) {
/* 200 */       throw new IOException("Unexpected byte 0x" + Integer.toHexString(b) + " following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
/*     */     }
/*     */     
/* 203 */     return input.readUnsignedByte();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader constructReader() throws IOException {
/*     */     InputStream in;
/*     */     MergedStream mergedStream;
/* 215 */     JsonEncoding enc = this._context.getEncoding();
/* 216 */     switch (enc.bits()) {
/*     */ 
/*     */       
/*     */       case 8:
/*     */       case 16:
/* 221 */         in = this._in;
/*     */         
/* 223 */         if (in == null) {
/* 224 */           in = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 229 */         else if (this._inputPtr < this._inputEnd) {
/* 230 */           mergedStream = new MergedStream(this._context, in, this._inputBuffer, this._inputPtr, this._inputEnd);
/*     */         } 
/*     */         
/* 233 */         return new InputStreamReader((InputStream)mergedStream, enc.getJavaName());
/*     */       
/*     */       case 32:
/* 236 */         return (Reader)new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
/*     */     } 
/*     */     
/* 239 */     throw new RuntimeException("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonParser constructParser(int parserFeatures, ObjectCodec codec, ByteQuadsCanonicalizer rootByteSymbols, CharsToNameCanonicalizer rootCharSymbols, int factoryFeatures) throws IOException {
/* 246 */     JsonEncoding enc = detectEncoding();
/*     */     
/* 248 */     if (enc == JsonEncoding.UTF8)
/*     */     {
/*     */ 
/*     */       
/* 252 */       if (JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(factoryFeatures)) {
/* 253 */         ByteQuadsCanonicalizer can = rootByteSymbols.makeChild(factoryFeatures);
/* 254 */         return (JsonParser)new UTF8StreamJsonParser(this._context, parserFeatures, this._in, codec, can, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
/*     */       } 
/*     */     }
/*     */     
/* 258 */     return (JsonParser)new ReaderBasedJsonParser(this._context, parserFeatures, constructReader(), codec, rootCharSymbols.makeChild(factoryFeatures));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatchStrength hasJSONFormat(InputAccessor acc) throws IOException {
/* 279 */     if (!acc.hasMoreBytes()) {
/* 280 */       return MatchStrength.INCONCLUSIVE;
/*     */     }
/* 282 */     byte b = acc.nextByte();
/*     */     
/* 284 */     if (b == -17) {
/* 285 */       if (!acc.hasMoreBytes()) {
/* 286 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 288 */       if (acc.nextByte() != -69) {
/* 289 */         return MatchStrength.NO_MATCH;
/*     */       }
/* 291 */       if (!acc.hasMoreBytes()) {
/* 292 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 294 */       if (acc.nextByte() != -65) {
/* 295 */         return MatchStrength.NO_MATCH;
/*     */       }
/* 297 */       if (!acc.hasMoreBytes()) {
/* 298 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 300 */       b = acc.nextByte();
/*     */     } 
/*     */     
/* 303 */     int ch = skipSpace(acc, b);
/* 304 */     if (ch < 0) {
/* 305 */       return MatchStrength.INCONCLUSIVE;
/*     */     }
/*     */     
/* 308 */     if (ch == 123) {
/*     */       
/* 310 */       ch = skipSpace(acc);
/* 311 */       if (ch < 0) {
/* 312 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 314 */       if (ch == 34 || ch == 125) {
/* 315 */         return MatchStrength.SOLID_MATCH;
/*     */       }
/*     */       
/* 318 */       return MatchStrength.NO_MATCH;
/*     */     } 
/*     */ 
/*     */     
/* 322 */     if (ch == 91) {
/* 323 */       ch = skipSpace(acc);
/* 324 */       if (ch < 0) {
/* 325 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/*     */       
/* 328 */       if (ch == 93 || ch == 91) {
/* 329 */         return MatchStrength.SOLID_MATCH;
/*     */       }
/* 331 */       return MatchStrength.SOLID_MATCH;
/*     */     } 
/*     */     
/* 334 */     MatchStrength strength = MatchStrength.WEAK_MATCH;
/*     */ 
/*     */     
/* 337 */     if (ch == 34) {
/* 338 */       return strength;
/*     */     }
/* 340 */     if (ch <= 57 && ch >= 48) {
/* 341 */       return strength;
/*     */     }
/* 343 */     if (ch == 45) {
/* 344 */       ch = skipSpace(acc);
/* 345 */       if (ch < 0) {
/* 346 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 348 */       return (ch <= 57 && ch >= 48) ? strength : MatchStrength.NO_MATCH;
/*     */     } 
/*     */     
/* 351 */     if (ch == 110) {
/* 352 */       return tryMatch(acc, "ull", strength);
/*     */     }
/* 354 */     if (ch == 116) {
/* 355 */       return tryMatch(acc, "rue", strength);
/*     */     }
/* 357 */     if (ch == 102) {
/* 358 */       return tryMatch(acc, "alse", strength);
/*     */     }
/* 360 */     return MatchStrength.NO_MATCH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static MatchStrength tryMatch(InputAccessor acc, String matchStr, MatchStrength fullMatchStrength) throws IOException {
/* 366 */     for (int i = 0, len = matchStr.length(); i < len; i++) {
/* 367 */       if (!acc.hasMoreBytes()) {
/* 368 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 370 */       if (acc.nextByte() != matchStr.charAt(i)) {
/* 371 */         return MatchStrength.NO_MATCH;
/*     */       }
/*     */     } 
/* 374 */     return fullMatchStrength;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int skipSpace(InputAccessor acc) throws IOException {
/* 379 */     if (!acc.hasMoreBytes()) {
/* 380 */       return -1;
/*     */     }
/* 382 */     return skipSpace(acc, acc.nextByte());
/*     */   }
/*     */ 
/*     */   
/*     */   private static int skipSpace(InputAccessor acc, byte b) throws IOException {
/*     */     while (true) {
/* 388 */       int ch = b & 0xFF;
/* 389 */       if (ch != 32 && ch != 13 && ch != 10 && ch != 9) {
/* 390 */         return ch;
/*     */       }
/* 392 */       if (!acc.hasMoreBytes()) {
/* 393 */         return -1;
/*     */       }
/* 395 */       b = acc.nextByte();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean handleBOM(int quad) throws IOException {
/* 414 */     switch (quad) {
/*     */       case 65279:
/* 416 */         this._bigEndian = true;
/* 417 */         this._inputPtr += 4;
/* 418 */         this._bytesPerChar = 4;
/* 419 */         return true;
/*     */       case -131072:
/* 421 */         this._inputPtr += 4;
/* 422 */         this._bytesPerChar = 4;
/* 423 */         this._bigEndian = false;
/* 424 */         return true;
/*     */       case 65534:
/* 426 */         reportWeirdUCS4("2143");
/*     */         break;
/*     */       case -16842752:
/* 429 */         reportWeirdUCS4("3412");
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 434 */     int msw = quad >>> 16;
/* 435 */     if (msw == 65279) {
/* 436 */       this._inputPtr += 2;
/* 437 */       this._bytesPerChar = 2;
/* 438 */       this._bigEndian = true;
/* 439 */       return true;
/*     */     } 
/* 441 */     if (msw == 65534) {
/* 442 */       this._inputPtr += 2;
/* 443 */       this._bytesPerChar = 2;
/* 444 */       this._bigEndian = false;
/* 445 */       return true;
/*     */     } 
/*     */     
/* 448 */     if (quad >>> 8 == 15711167) {
/* 449 */       this._inputPtr += 3;
/* 450 */       this._bytesPerChar = 1;
/* 451 */       this._bigEndian = true;
/* 452 */       return true;
/*     */     } 
/* 454 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkUTF32(int quad) throws IOException {
/* 462 */     if (quad >> 8 == 0) {
/* 463 */       this._bigEndian = true;
/* 464 */     } else if ((quad & 0xFFFFFF) == 0) {
/* 465 */       this._bigEndian = false;
/* 466 */     } else if ((quad & 0xFF00FFFF) == 0) {
/* 467 */       reportWeirdUCS4("3412");
/* 468 */     } else if ((quad & 0xFFFF00FF) == 0) {
/* 469 */       reportWeirdUCS4("2143");
/*     */     } else {
/*     */       
/* 472 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 476 */     this._bytesPerChar = 4;
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkUTF16(int i16) {
/* 482 */     if ((i16 & 0xFF00) == 0) {
/* 483 */       this._bigEndian = true;
/* 484 */     } else if ((i16 & 0xFF) == 0) {
/* 485 */       this._bigEndian = false;
/*     */     } else {
/* 487 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 491 */     this._bytesPerChar = 2;
/* 492 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reportWeirdUCS4(String type) throws IOException {
/* 502 */     throw new CharConversionException("Unsupported UCS-4 endianness (" + type + ") detected");
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
/*     */   protected boolean ensureLoaded(int minimum) throws IOException {
/* 515 */     int gotten = this._inputEnd - this._inputPtr;
/* 516 */     while (gotten < minimum) {
/*     */       int count;
/*     */       
/* 519 */       if (this._in == null) {
/* 520 */         count = -1;
/*     */       } else {
/* 522 */         count = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
/*     */       } 
/* 524 */       if (count < 1) {
/* 525 */         return false;
/*     */       }
/* 527 */       this._inputEnd += count;
/* 528 */       gotten += count;
/*     */     } 
/* 530 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\ByteSourceJsonBootstrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */