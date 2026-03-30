/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
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
/*     */ public final class Base64Variant
/*     */   implements Serializable
/*     */ {
/*     */   private static final int INT_SPACE = 32;
/*     */   private static final long serialVersionUID = 1L;
/*     */   static final char PADDING_CHAR_NONE = '\000';
/*     */   public static final int BASE64_VALUE_INVALID = -1;
/*     */   public static final int BASE64_VALUE_PADDING = -2;
/*  55 */   private final transient int[] _asciiToBase64 = new int[128];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private final transient char[] _base64ToAsciiC = new char[64];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private final transient byte[] _base64ToAsciiB = new byte[64];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String _name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient boolean _usesPadding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient char _paddingChar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient int _maxLineLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64Variant(String name, String base64Alphabet, boolean usesPadding, char paddingChar, int maxLineLength) {
/* 113 */     this._name = name;
/* 114 */     this._usesPadding = usesPadding;
/* 115 */     this._paddingChar = paddingChar;
/* 116 */     this._maxLineLength = maxLineLength;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     int alphaLen = base64Alphabet.length();
/* 122 */     if (alphaLen != 64) {
/* 123 */       throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + alphaLen + ")");
/*     */     }
/*     */ 
/*     */     
/* 127 */     base64Alphabet.getChars(0, alphaLen, this._base64ToAsciiC, 0);
/* 128 */     Arrays.fill(this._asciiToBase64, -1);
/* 129 */     for (int i = 0; i < alphaLen; i++) {
/* 130 */       char alpha = this._base64ToAsciiC[i];
/* 131 */       this._base64ToAsciiB[i] = (byte)alpha;
/* 132 */       this._asciiToBase64[alpha] = i;
/*     */     } 
/*     */ 
/*     */     
/* 136 */     if (usesPadding) {
/* 137 */       this._asciiToBase64[paddingChar] = -2;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64Variant(Base64Variant base, String name, int maxLineLength) {
/* 148 */     this(base, name, base._usesPadding, base._paddingChar, maxLineLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64Variant(Base64Variant base, String name, boolean usesPadding, char paddingChar, int maxLineLength) {
/* 158 */     this._name = name;
/* 159 */     byte[] srcB = base._base64ToAsciiB;
/* 160 */     System.arraycopy(srcB, 0, this._base64ToAsciiB, 0, srcB.length);
/* 161 */     char[] srcC = base._base64ToAsciiC;
/* 162 */     System.arraycopy(srcC, 0, this._base64ToAsciiC, 0, srcC.length);
/* 163 */     int[] srcV = base._asciiToBase64;
/* 164 */     System.arraycopy(srcV, 0, this._asciiToBase64, 0, srcV.length);
/*     */     
/* 166 */     this._usesPadding = usesPadding;
/* 167 */     this._paddingChar = paddingChar;
/* 168 */     this._maxLineLength = maxLineLength;
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
/*     */   protected Object readResolve() {
/* 182 */     return Base64Variants.valueOf(this._name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 191 */     return this._name;
/*     */   }
/* 193 */   public boolean usesPadding() { return this._usesPadding; }
/* 194 */   public boolean usesPaddingChar(char c) { return (c == this._paddingChar); }
/* 195 */   public boolean usesPaddingChar(int ch) { return (ch == this._paddingChar); }
/* 196 */   public char getPaddingChar() { return this._paddingChar; } public byte getPaddingByte() {
/* 197 */     return (byte)this._paddingChar;
/*     */   } public int getMaxLineLength() {
/* 199 */     return this._maxLineLength;
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
/*     */   public int decodeBase64Char(char c) {
/* 212 */     int ch = c;
/* 213 */     return (ch <= 127) ? this._asciiToBase64[ch] : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int decodeBase64Char(int ch) {
/* 218 */     return (ch <= 127) ? this._asciiToBase64[ch] : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int decodeBase64Byte(byte b) {
/* 223 */     int ch = b;
/*     */     
/* 225 */     if (ch < 0) {
/* 226 */       return -1;
/*     */     }
/* 228 */     return this._asciiToBase64[ch];
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
/*     */   public char encodeBase64BitsAsChar(int value) {
/* 242 */     return this._base64ToAsciiC[value];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encodeBase64Chunk(int b24, char[] buffer, int ptr) {
/* 251 */     buffer[ptr++] = this._base64ToAsciiC[b24 >> 18 & 0x3F];
/* 252 */     buffer[ptr++] = this._base64ToAsciiC[b24 >> 12 & 0x3F];
/* 253 */     buffer[ptr++] = this._base64ToAsciiC[b24 >> 6 & 0x3F];
/* 254 */     buffer[ptr++] = this._base64ToAsciiC[b24 & 0x3F];
/* 255 */     return ptr;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeBase64Chunk(StringBuilder sb, int b24) {
/* 260 */     sb.append(this._base64ToAsciiC[b24 >> 18 & 0x3F]);
/* 261 */     sb.append(this._base64ToAsciiC[b24 >> 12 & 0x3F]);
/* 262 */     sb.append(this._base64ToAsciiC[b24 >> 6 & 0x3F]);
/* 263 */     sb.append(this._base64ToAsciiC[b24 & 0x3F]);
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
/*     */   public int encodeBase64Partial(int bits, int outputBytes, char[] buffer, int outPtr) {
/* 276 */     buffer[outPtr++] = this._base64ToAsciiC[bits >> 18 & 0x3F];
/* 277 */     buffer[outPtr++] = this._base64ToAsciiC[bits >> 12 & 0x3F];
/* 278 */     if (this._usesPadding) {
/* 279 */       buffer[outPtr++] = (outputBytes == 2) ? this._base64ToAsciiC[bits >> 6 & 0x3F] : this._paddingChar;
/*     */       
/* 281 */       buffer[outPtr++] = this._paddingChar;
/*     */     }
/* 283 */     else if (outputBytes == 2) {
/* 284 */       buffer[outPtr++] = this._base64ToAsciiC[bits >> 6 & 0x3F];
/*     */     } 
/*     */     
/* 287 */     return outPtr;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeBase64Partial(StringBuilder sb, int bits, int outputBytes) {
/* 292 */     sb.append(this._base64ToAsciiC[bits >> 18 & 0x3F]);
/* 293 */     sb.append(this._base64ToAsciiC[bits >> 12 & 0x3F]);
/* 294 */     if (this._usesPadding) {
/* 295 */       sb.append((outputBytes == 2) ? this._base64ToAsciiC[bits >> 6 & 0x3F] : this._paddingChar);
/*     */       
/* 297 */       sb.append(this._paddingChar);
/*     */     }
/* 299 */     else if (outputBytes == 2) {
/* 300 */       sb.append(this._base64ToAsciiC[bits >> 6 & 0x3F]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte encodeBase64BitsAsByte(int value) {
/* 308 */     return this._base64ToAsciiB[value];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encodeBase64Chunk(int b24, byte[] buffer, int ptr) {
/* 317 */     buffer[ptr++] = this._base64ToAsciiB[b24 >> 18 & 0x3F];
/* 318 */     buffer[ptr++] = this._base64ToAsciiB[b24 >> 12 & 0x3F];
/* 319 */     buffer[ptr++] = this._base64ToAsciiB[b24 >> 6 & 0x3F];
/* 320 */     buffer[ptr++] = this._base64ToAsciiB[b24 & 0x3F];
/* 321 */     return ptr;
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
/*     */   public int encodeBase64Partial(int bits, int outputBytes, byte[] buffer, int outPtr) {
/* 334 */     buffer[outPtr++] = this._base64ToAsciiB[bits >> 18 & 0x3F];
/* 335 */     buffer[outPtr++] = this._base64ToAsciiB[bits >> 12 & 0x3F];
/* 336 */     if (this._usesPadding) {
/* 337 */       byte pb = (byte)this._paddingChar;
/* 338 */       buffer[outPtr++] = (outputBytes == 2) ? this._base64ToAsciiB[bits >> 6 & 0x3F] : pb;
/*     */       
/* 340 */       buffer[outPtr++] = pb;
/*     */     }
/* 342 */     else if (outputBytes == 2) {
/* 343 */       buffer[outPtr++] = this._base64ToAsciiB[bits >> 6 & 0x3F];
/*     */     } 
/*     */     
/* 346 */     return outPtr;
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
/*     */   public String encode(byte[] input) {
/* 365 */     return encode(input, false);
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
/*     */   public String encode(byte[] input, boolean addQuotes) {
/* 378 */     int inputEnd = input.length;
/*     */ 
/*     */ 
/*     */     
/* 382 */     int outputLen = inputEnd + (inputEnd >> 2) + (inputEnd >> 3);
/* 383 */     StringBuilder sb = new StringBuilder(outputLen);
/*     */     
/* 385 */     if (addQuotes) {
/* 386 */       sb.append('"');
/*     */     }
/*     */     
/* 389 */     int chunksBeforeLF = getMaxLineLength() >> 2;
/*     */ 
/*     */     
/* 392 */     int inputPtr = 0;
/* 393 */     int safeInputEnd = inputEnd - 3;
/*     */     
/* 395 */     while (inputPtr <= safeInputEnd) {
/*     */       
/* 397 */       int b24 = input[inputPtr++] << 8;
/* 398 */       b24 |= input[inputPtr++] & 0xFF;
/* 399 */       b24 = b24 << 8 | input[inputPtr++] & 0xFF;
/* 400 */       encodeBase64Chunk(sb, b24);
/* 401 */       if (--chunksBeforeLF <= 0) {
/*     */         
/* 403 */         sb.append('\\');
/* 404 */         sb.append('n');
/* 405 */         chunksBeforeLF = getMaxLineLength() >> 2;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 410 */     int inputLeft = inputEnd - inputPtr;
/* 411 */     if (inputLeft > 0) {
/* 412 */       int b24 = input[inputPtr++] << 16;
/* 413 */       if (inputLeft == 2) {
/* 414 */         b24 |= (input[inputPtr++] & 0xFF) << 8;
/*     */       }
/* 416 */       encodeBase64Partial(sb, b24, inputLeft);
/*     */     } 
/*     */     
/* 419 */     if (addQuotes) {
/* 420 */       sb.append('"');
/*     */     }
/* 422 */     return sb.toString();
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
/*     */   public byte[] decode(String input) throws IllegalArgumentException {
/* 438 */     ByteArrayBuilder b = new ByteArrayBuilder();
/* 439 */     decode(input, b);
/* 440 */     return b.toByteArray();
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
/*     */   public void decode(String str, ByteArrayBuilder builder) throws IllegalArgumentException {
/* 458 */     int ptr = 0;
/* 459 */     int len = str.length();
/*     */     
/* 461 */     while (ptr < len) {
/*     */ 
/*     */       
/*     */       do {
/* 465 */         ch = str.charAt(ptr++);
/* 466 */       } while (ptr < len && ch <= ' ');
/* 467 */       int bits = decodeBase64Char(ch);
/* 468 */       if (bits < 0) {
/* 469 */         _reportInvalidBase64(ch, 0, null);
/*     */       }
/* 471 */       int decodedData = bits;
/*     */       
/* 473 */       if (ptr >= len) {
/* 474 */         _reportBase64EOF();
/*     */       }
/* 476 */       char ch = str.charAt(ptr++);
/* 477 */       bits = decodeBase64Char(ch);
/* 478 */       if (bits < 0) {
/* 479 */         _reportInvalidBase64(ch, 1, null);
/*     */       }
/* 481 */       decodedData = decodedData << 6 | bits;
/*     */       
/* 483 */       if (ptr >= len) {
/*     */         
/* 485 */         if (!usesPadding()) {
/* 486 */           decodedData >>= 4;
/* 487 */           builder.append(decodedData);
/*     */           break;
/*     */         } 
/* 490 */         _reportBase64EOF();
/*     */       } 
/* 492 */       ch = str.charAt(ptr++);
/* 493 */       bits = decodeBase64Char(ch);
/*     */ 
/*     */       
/* 496 */       if (bits < 0) {
/* 497 */         if (bits != -2) {
/* 498 */           _reportInvalidBase64(ch, 2, null);
/*     */         }
/*     */         
/* 501 */         if (ptr >= len) {
/* 502 */           _reportBase64EOF();
/*     */         }
/* 504 */         ch = str.charAt(ptr++);
/* 505 */         if (!usesPaddingChar(ch)) {
/* 506 */           _reportInvalidBase64(ch, 3, "expected padding character '" + getPaddingChar() + "'");
/*     */         }
/*     */         
/* 509 */         decodedData >>= 4;
/* 510 */         builder.append(decodedData);
/*     */         
/*     */         continue;
/*     */       } 
/* 514 */       decodedData = decodedData << 6 | bits;
/*     */       
/* 516 */       if (ptr >= len) {
/*     */         
/* 518 */         if (!usesPadding()) {
/* 519 */           decodedData >>= 2;
/* 520 */           builder.appendTwoBytes(decodedData);
/*     */           break;
/*     */         } 
/* 523 */         _reportBase64EOF();
/*     */       } 
/* 525 */       ch = str.charAt(ptr++);
/* 526 */       bits = decodeBase64Char(ch);
/* 527 */       if (bits < 0) {
/* 528 */         if (bits != -2) {
/* 529 */           _reportInvalidBase64(ch, 3, null);
/*     */         }
/* 531 */         decodedData >>= 2;
/* 532 */         builder.appendTwoBytes(decodedData);
/*     */         continue;
/*     */       } 
/* 535 */       decodedData = decodedData << 6 | bits;
/* 536 */       builder.appendThreeBytes(decodedData);
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
/*     */   public String toString() {
/* 548 */     return this._name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 553 */     return (o == this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 558 */     return this._name.hashCode();
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
/*     */   protected void _reportInvalidBase64(char ch, int bindex, String msg) throws IllegalArgumentException {
/*     */     String base;
/* 575 */     if (ch <= ' ') {
/* 576 */       base = "Illegal white space character (code 0x" + Integer.toHexString(ch) + ") as character #" + (bindex + 1) + " of 4-char base64 unit: can only used between units";
/* 577 */     } else if (usesPaddingChar(ch)) {
/* 578 */       base = "Unexpected padding character ('" + getPaddingChar() + "') as character #" + (bindex + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
/* 579 */     } else if (!Character.isDefined(ch) || Character.isISOControl(ch)) {
/*     */       
/* 581 */       base = "Illegal character (code 0x" + Integer.toHexString(ch) + ") in base64 content";
/*     */     } else {
/* 583 */       base = "Illegal character '" + ch + "' (code 0x" + Integer.toHexString(ch) + ") in base64 content";
/*     */     } 
/* 585 */     if (msg != null) {
/* 586 */       base = base + ": " + msg;
/*     */     }
/* 588 */     throw new IllegalArgumentException(base);
/*     */   }
/*     */   
/*     */   protected void _reportBase64EOF() throws IllegalArgumentException {
/* 592 */     throw new IllegalArgumentException("Unexpected end-of-String in base64 content");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\Base64Variant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */