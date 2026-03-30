/*     */ package software.bernie.shadowed.fasterxml.jackson.core.json.async;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.base.ParserBase;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.json.JsonReadContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NonBlockingJsonParserBase
/*     */   extends ParserBase
/*     */ {
/*     */   protected static final int MAJOR_INITIAL = 0;
/*     */   protected static final int MAJOR_ROOT = 1;
/*     */   protected static final int MAJOR_OBJECT_FIELD_FIRST = 2;
/*     */   protected static final int MAJOR_OBJECT_FIELD_NEXT = 3;
/*     */   protected static final int MAJOR_OBJECT_VALUE = 4;
/*     */   protected static final int MAJOR_ARRAY_ELEMENT_FIRST = 5;
/*     */   protected static final int MAJOR_ARRAY_ELEMENT_NEXT = 6;
/*     */   protected static final int MAJOR_CLOSED = 7;
/*     */   protected static final int MINOR_ROOT_BOM = 1;
/*     */   protected static final int MINOR_ROOT_NEED_SEPARATOR = 2;
/*     */   protected static final int MINOR_ROOT_GOT_SEPARATOR = 3;
/*     */   protected static final int MINOR_FIELD_LEADING_WS = 4;
/*     */   protected static final int MINOR_FIELD_LEADING_COMMA = 5;
/*     */   protected static final int MINOR_FIELD_NAME = 7;
/*     */   protected static final int MINOR_FIELD_NAME_ESCAPE = 8;
/*     */   protected static final int MINOR_FIELD_APOS_NAME = 9;
/*     */   protected static final int MINOR_FIELD_UNQUOTED_NAME = 10;
/*     */   protected static final int MINOR_VALUE_LEADING_WS = 12;
/*     */   protected static final int MINOR_VALUE_EXPECTING_COMMA = 13;
/*     */   protected static final int MINOR_VALUE_EXPECTING_COLON = 14;
/*     */   protected static final int MINOR_VALUE_WS_AFTER_COMMA = 15;
/*     */   protected static final int MINOR_VALUE_TOKEN_NULL = 16;
/*     */   protected static final int MINOR_VALUE_TOKEN_TRUE = 17;
/*     */   protected static final int MINOR_VALUE_TOKEN_FALSE = 18;
/*     */   protected static final int MINOR_VALUE_TOKEN_NON_STD = 19;
/*     */   protected static final int MINOR_NUMBER_MINUS = 23;
/*     */   protected static final int MINOR_NUMBER_ZERO = 24;
/*     */   protected static final int MINOR_NUMBER_MINUSZERO = 25;
/*     */   protected static final int MINOR_NUMBER_INTEGER_DIGITS = 26;
/*     */   protected static final int MINOR_NUMBER_FRACTION_DIGITS = 30;
/*     */   protected static final int MINOR_NUMBER_EXPONENT_MARKER = 31;
/*     */   protected static final int MINOR_NUMBER_EXPONENT_DIGITS = 32;
/*     */   protected static final int MINOR_VALUE_STRING = 40;
/*     */   protected static final int MINOR_VALUE_STRING_ESCAPE = 41;
/*     */   protected static final int MINOR_VALUE_STRING_UTF8_2 = 42;
/*     */   protected static final int MINOR_VALUE_STRING_UTF8_3 = 43;
/*     */   protected static final int MINOR_VALUE_STRING_UTF8_4 = 44;
/*     */   protected static final int MINOR_VALUE_APOS_STRING = 45;
/*     */   protected static final int MINOR_VALUE_TOKEN_ERROR = 50;
/*     */   protected static final int MINOR_COMMENT_LEADING_SLASH = 51;
/*     */   protected static final int MINOR_COMMENT_CLOSING_ASTERISK = 52;
/*     */   protected static final int MINOR_COMMENT_C = 53;
/*     */   protected static final int MINOR_COMMENT_CPP = 54;
/*     */   protected static final int MINOR_COMMENT_YAML = 55;
/*     */   protected final ByteQuadsCanonicalizer _symbols;
/* 146 */   protected int[] _quadBuffer = new int[8];
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _quadLength;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _quad1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _pending32;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _pendingBytes;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _quoted32;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _quotedDigits;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _majorState;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _majorStateAfterValue;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _minorState;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _minorStateAfterSplit;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _endOfInput = false;
/*     */ 
/*     */   
/*     */   protected static final int NON_STD_TOKEN_NAN = 0;
/*     */ 
/*     */   
/*     */   protected static final int NON_STD_TOKEN_INFINITY = 1;
/*     */ 
/*     */   
/*     */   protected static final int NON_STD_TOKEN_PLUS_INFINITY = 2;
/*     */ 
/*     */   
/*     */   protected static final int NON_STD_TOKEN_MINUS_INFINITY = 3;
/*     */ 
/*     */   
/* 205 */   protected static final String[] NON_STD_TOKENS = new String[] { "NaN", "Infinity", "+Infinity", "-Infinity" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   protected static final double[] NON_STD_TOKEN_VALUES = new double[] { Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _nonStdTokenType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   protected int _currBufferStart = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   protected int _currInputRowAlt = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonBlockingJsonParserBase(IOContext ctxt, int parserFeatures, ByteQuadsCanonicalizer sym) {
/* 252 */     super(ctxt, parserFeatures);
/* 253 */     this._symbols = sym;
/* 254 */     this._currToken = null;
/* 255 */     this._majorState = 0;
/* 256 */     this._majorStateAfterValue = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectCodec getCodec() {
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCodec(ObjectCodec c) {
/* 266 */     throw new UnsupportedOperationException("Can not use ObjectMapper with non-blocking parser");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canParseAsync() {
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteQuadsCanonicalizer symbolTableForTests() {
/* 282 */     return this._symbols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int releaseBuffered(OutputStream paramOutputStream) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _releaseBuffers() throws IOException {
/* 297 */     super._releaseBuffers();
/*     */     
/* 299 */     this._symbols.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getInputSource() {
/* 306 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _closeInput() throws IOException {
/* 314 */     this._currBufferStart = 0;
/* 315 */     this._inputEnd = 0;
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
/*     */   public boolean hasTextCharacters() {
/* 327 */     if (this._currToken == JsonToken.VALUE_STRING)
/*     */     {
/* 329 */       return this._textBuffer.hasTextAsCharacters();
/*     */     }
/* 331 */     if (this._currToken == JsonToken.FIELD_NAME)
/*     */     {
/* 333 */       return this._nameCopied;
/*     */     }
/*     */     
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonLocation getCurrentLocation() {
/* 342 */     int col = this._inputPtr - this._currInputRowStart + 1;
/*     */     
/* 344 */     int row = Math.max(this._currInputRow, this._currInputRowAlt);
/* 345 */     return new JsonLocation(_getSourceReference(), this._currInputProcessed + (this._inputPtr - this._currBufferStart), -1L, row, col);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonLocation getTokenLocation() {
/* 353 */     return new JsonLocation(_getSourceReference(), this._tokenInputTotal, -1L, this._tokenInputRow, this._tokenInputCol);
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
/*     */   public String getText() throws IOException {
/* 372 */     if (this._currToken == JsonToken.VALUE_STRING) {
/* 373 */       return this._textBuffer.contentsAsString();
/*     */     }
/* 375 */     return _getText2(this._currToken);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final String _getText2(JsonToken t) {
/* 380 */     if (t == null) {
/* 381 */       return null;
/*     */     }
/* 383 */     switch (t.id()) {
/*     */       case -1:
/* 385 */         return null;
/*     */       case 5:
/* 387 */         return this._parsingContext.getCurrentName();
/*     */       
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 392 */         return this._textBuffer.contentsAsString();
/*     */     } 
/* 394 */     return t.asString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getText(Writer writer) throws IOException {
/* 401 */     JsonToken t = this._currToken;
/* 402 */     if (t == JsonToken.VALUE_STRING) {
/* 403 */       return this._textBuffer.contentsToWriter(writer);
/*     */     }
/* 405 */     if (t == JsonToken.FIELD_NAME) {
/* 406 */       String n = this._parsingContext.getCurrentName();
/* 407 */       writer.write(n);
/* 408 */       return n.length();
/*     */     } 
/* 410 */     if (t != null) {
/* 411 */       if (t.isNumeric()) {
/* 412 */         return this._textBuffer.contentsToWriter(writer);
/*     */       }
/* 414 */       if (t == JsonToken.NOT_AVAILABLE) {
/* 415 */         _reportError("Current token not available: can not call this method");
/*     */       }
/* 417 */       char[] ch = t.asCharArray();
/* 418 */       writer.write(ch);
/* 419 */       return ch.length;
/*     */     } 
/* 421 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueAsString() throws IOException {
/* 430 */     if (this._currToken == JsonToken.VALUE_STRING) {
/* 431 */       return this._textBuffer.contentsAsString();
/*     */     }
/* 433 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 434 */       return getCurrentName();
/*     */     }
/* 436 */     return super.getValueAsString(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueAsString(String defValue) throws IOException {
/* 443 */     if (this._currToken == JsonToken.VALUE_STRING) {
/* 444 */       return this._textBuffer.contentsAsString();
/*     */     }
/* 446 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 447 */       return getCurrentName();
/*     */     }
/* 449 */     return super.getValueAsString(defValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getTextCharacters() throws IOException {
/* 455 */     if (this._currToken != null) {
/* 456 */       switch (this._currToken.id()) {
/*     */         
/*     */         case 5:
/* 459 */           if (!this._nameCopied) {
/* 460 */             String name = this._parsingContext.getCurrentName();
/* 461 */             int nameLen = name.length();
/* 462 */             if (this._nameCopyBuffer == null) {
/* 463 */               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(nameLen);
/* 464 */             } else if (this._nameCopyBuffer.length < nameLen) {
/* 465 */               this._nameCopyBuffer = new char[nameLen];
/*     */             } 
/* 467 */             name.getChars(0, nameLen, this._nameCopyBuffer, 0);
/* 468 */             this._nameCopied = true;
/*     */           } 
/* 470 */           return this._nameCopyBuffer;
/*     */ 
/*     */         
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/* 476 */           return this._textBuffer.getTextBuffer();
/*     */       } 
/*     */       
/* 479 */       return this._currToken.asCharArray();
/*     */     } 
/*     */     
/* 482 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextLength() throws IOException {
/* 488 */     if (this._currToken != null) {
/* 489 */       switch (this._currToken.id()) {
/*     */         
/*     */         case 5:
/* 492 */           return this._parsingContext.getCurrentName().length();
/*     */         
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/* 497 */           return this._textBuffer.size();
/*     */       } 
/*     */       
/* 500 */       return (this._currToken.asCharArray()).length;
/*     */     } 
/*     */     
/* 503 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextOffset() throws IOException {
/* 510 */     if (this._currToken != null) {
/* 511 */       switch (this._currToken.id()) {
/*     */         case 5:
/* 513 */           return 0;
/*     */         
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/* 518 */           return this._textBuffer.getTextOffset();
/*     */       } 
/*     */     
/*     */     }
/* 522 */     return 0;
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
/*     */   public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
/* 534 */     if (this._currToken != JsonToken.VALUE_STRING) {
/* 535 */       _reportError("Current token (%s) not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary", this._currToken);
/*     */     }
/*     */     
/* 538 */     if (this._binaryValue == null) {
/*     */       
/* 540 */       ByteArrayBuilder builder = _getByteArrayBuilder();
/* 541 */       _decodeBase64(getText(), builder, b64variant);
/* 542 */       this._binaryValue = builder.toByteArray();
/*     */     } 
/* 544 */     return this._binaryValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
/* 550 */     byte[] b = getBinaryValue(b64variant);
/* 551 */     out.write(b);
/* 552 */     return b.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEmbeddedObject() throws IOException {
/* 558 */     if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 559 */       return this._binaryValue;
/*     */     }
/* 561 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonToken _startArrayScope() throws IOException {
/* 572 */     this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
/* 573 */     this._majorState = 5;
/* 574 */     this._majorStateAfterValue = 6;
/* 575 */     return this._currToken = JsonToken.START_ARRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final JsonToken _startObjectScope() throws IOException {
/* 580 */     this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
/* 581 */     this._majorState = 2;
/* 582 */     this._majorStateAfterValue = 3;
/* 583 */     return this._currToken = JsonToken.START_OBJECT;
/*     */   }
/*     */   
/*     */   protected final JsonToken _closeArrayScope() throws IOException {
/*     */     int st;
/* 588 */     if (!this._parsingContext.inArray()) {
/* 589 */       _reportMismatchedEndMarker(93, '}');
/*     */     }
/* 591 */     JsonReadContext ctxt = this._parsingContext.getParent();
/* 592 */     this._parsingContext = ctxt;
/*     */     
/* 594 */     if (ctxt.inObject()) {
/* 595 */       st = 3;
/* 596 */     } else if (ctxt.inArray()) {
/* 597 */       st = 6;
/*     */     } else {
/* 599 */       st = 1;
/*     */     } 
/* 601 */     this._majorState = st;
/* 602 */     this._majorStateAfterValue = st;
/* 603 */     return this._currToken = JsonToken.END_ARRAY;
/*     */   }
/*     */   
/*     */   protected final JsonToken _closeObjectScope() throws IOException {
/*     */     int st;
/* 608 */     if (!this._parsingContext.inObject()) {
/* 609 */       _reportMismatchedEndMarker(125, ']');
/*     */     }
/* 611 */     JsonReadContext ctxt = this._parsingContext.getParent();
/* 612 */     this._parsingContext = ctxt;
/*     */     
/* 614 */     if (ctxt.inObject()) {
/* 615 */       st = 3;
/* 616 */     } else if (ctxt.inArray()) {
/* 617 */       st = 6;
/*     */     } else {
/* 619 */       st = 1;
/*     */     } 
/* 621 */     this._majorState = st;
/* 622 */     this._majorStateAfterValue = st;
/* 623 */     return this._currToken = JsonToken.END_OBJECT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String _findName(int q1, int lastQuadBytes) throws JsonParseException {
/* 634 */     q1 = _padLastQuad(q1, lastQuadBytes);
/*     */     
/* 636 */     String name = this._symbols.findName(q1);
/* 637 */     if (name != null) {
/* 638 */       return name;
/*     */     }
/*     */     
/* 641 */     this._quadBuffer[0] = q1;
/* 642 */     return _addName(this._quadBuffer, 1, lastQuadBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final String _findName(int q1, int q2, int lastQuadBytes) throws JsonParseException {
/* 647 */     q2 = _padLastQuad(q2, lastQuadBytes);
/*     */     
/* 649 */     String name = this._symbols.findName(q1, q2);
/* 650 */     if (name != null) {
/* 651 */       return name;
/*     */     }
/*     */     
/* 654 */     this._quadBuffer[0] = q1;
/* 655 */     this._quadBuffer[1] = q2;
/* 656 */     return _addName(this._quadBuffer, 2, lastQuadBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final String _findName(int q1, int q2, int q3, int lastQuadBytes) throws JsonParseException {
/* 661 */     q3 = _padLastQuad(q3, lastQuadBytes);
/* 662 */     String name = this._symbols.findName(q1, q2, q3);
/* 663 */     if (name != null) {
/* 664 */       return name;
/*     */     }
/* 666 */     int[] quads = this._quadBuffer;
/* 667 */     quads[0] = q1;
/* 668 */     quads[1] = q2;
/* 669 */     quads[2] = _padLastQuad(q3, lastQuadBytes);
/* 670 */     return _addName(quads, 3, lastQuadBytes);
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
/*     */   protected final String _addName(int[] quads, int qlen, int lastQuadBytes) throws JsonParseException {
/* 686 */     int lastQuad, byteLen = (qlen << 2) - 4 + lastQuadBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 695 */     if (lastQuadBytes < 4) {
/* 696 */       lastQuad = quads[qlen - 1];
/*     */       
/* 698 */       quads[qlen - 1] = lastQuad << 4 - lastQuadBytes << 3;
/*     */     } else {
/* 700 */       lastQuad = 0;
/*     */     } 
/*     */ 
/*     */     
/* 704 */     char[] cbuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 705 */     int cix = 0;
/*     */     
/* 707 */     for (int ix = 0; ix < byteLen; ) {
/* 708 */       int ch = quads[ix >> 2];
/* 709 */       int byteIx = ix & 0x3;
/* 710 */       ch = ch >> 3 - byteIx << 3 & 0xFF;
/* 711 */       ix++;
/*     */       
/* 713 */       if (ch > 127) {
/*     */         int needed;
/* 715 */         if ((ch & 0xE0) == 192) {
/* 716 */           ch &= 0x1F;
/* 717 */           needed = 1;
/* 718 */         } else if ((ch & 0xF0) == 224) {
/* 719 */           ch &= 0xF;
/* 720 */           needed = 2;
/* 721 */         } else if ((ch & 0xF8) == 240) {
/* 722 */           ch &= 0x7;
/* 723 */           needed = 3;
/*     */         } else {
/* 725 */           _reportInvalidInitial(ch);
/* 726 */           needed = ch = 1;
/*     */         } 
/* 728 */         if (ix + needed > byteLen) {
/* 729 */           _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*     */         }
/*     */ 
/*     */         
/* 733 */         int ch2 = quads[ix >> 2];
/* 734 */         byteIx = ix & 0x3;
/* 735 */         ch2 >>= 3 - byteIx << 3;
/* 736 */         ix++;
/*     */         
/* 738 */         if ((ch2 & 0xC0) != 128) {
/* 739 */           _reportInvalidOther(ch2);
/*     */         }
/* 741 */         ch = ch << 6 | ch2 & 0x3F;
/* 742 */         if (needed > 1) {
/* 743 */           ch2 = quads[ix >> 2];
/* 744 */           byteIx = ix & 0x3;
/* 745 */           ch2 >>= 3 - byteIx << 3;
/* 746 */           ix++;
/*     */           
/* 748 */           if ((ch2 & 0xC0) != 128) {
/* 749 */             _reportInvalidOther(ch2);
/*     */           }
/* 751 */           ch = ch << 6 | ch2 & 0x3F;
/* 752 */           if (needed > 2) {
/* 753 */             ch2 = quads[ix >> 2];
/* 754 */             byteIx = ix & 0x3;
/* 755 */             ch2 >>= 3 - byteIx << 3;
/* 756 */             ix++;
/* 757 */             if ((ch2 & 0xC0) != 128) {
/* 758 */               _reportInvalidOther(ch2 & 0xFF);
/*     */             }
/* 760 */             ch = ch << 6 | ch2 & 0x3F;
/*     */           } 
/*     */         } 
/* 763 */         if (needed > 2) {
/* 764 */           ch -= 65536;
/* 765 */           if (cix >= cbuf.length) {
/* 766 */             cbuf = this._textBuffer.expandCurrentSegment();
/*     */           }
/* 768 */           cbuf[cix++] = (char)(55296 + (ch >> 10));
/* 769 */           ch = 0xDC00 | ch & 0x3FF;
/*     */         } 
/*     */       } 
/* 772 */       if (cix >= cbuf.length) {
/* 773 */         cbuf = this._textBuffer.expandCurrentSegment();
/*     */       }
/* 775 */       cbuf[cix++] = (char)ch;
/*     */     } 
/*     */ 
/*     */     
/* 779 */     String baseName = new String(cbuf, 0, cix);
/*     */     
/* 781 */     if (lastQuadBytes < 4) {
/* 782 */       quads[qlen - 1] = lastQuad;
/*     */     }
/* 784 */     return this._symbols.addName(baseName, quads, qlen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int _padLastQuad(int q, int bytes) {
/* 791 */     return (bytes == 4) ? q : (q | -1 << bytes << 3);
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
/*     */   protected final JsonToken _eofAsNextToken() throws IOException {
/* 805 */     this._majorState = 7;
/* 806 */     if (!this._parsingContext.inRoot()) {
/* 807 */       _handleEOF();
/*     */     }
/* 809 */     close();
/* 810 */     return this._currToken = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final JsonToken _fieldComplete(String name) throws IOException {
/* 815 */     this._majorState = 4;
/* 816 */     this._parsingContext.setCurrentName(name);
/* 817 */     return this._currToken = JsonToken.FIELD_NAME;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final JsonToken _valueComplete(JsonToken t) throws IOException {
/* 822 */     this._majorState = this._majorStateAfterValue;
/* 823 */     this._currToken = t;
/* 824 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final JsonToken _valueCompleteInt(int value, String asText) throws IOException {
/* 829 */     this._textBuffer.resetWithString(asText);
/* 830 */     this._intLength = asText.length();
/* 831 */     this._numTypesValid = 1;
/* 832 */     this._numberInt = value;
/* 833 */     this._majorState = this._majorStateAfterValue;
/* 834 */     JsonToken t = JsonToken.VALUE_NUMBER_INT;
/* 835 */     this._currToken = t;
/* 836 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final JsonToken _valueNonStdNumberComplete(int type) throws IOException {
/* 841 */     String tokenStr = NON_STD_TOKENS[type];
/* 842 */     this._textBuffer.resetWithString(tokenStr);
/* 843 */     if (!isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 844 */       _reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", tokenStr);
/*     */     }
/*     */     
/* 847 */     this._intLength = 0;
/* 848 */     this._numTypesValid = 8;
/* 849 */     this._numberDouble = NON_STD_TOKEN_VALUES[type];
/* 850 */     this._majorState = this._majorStateAfterValue;
/* 851 */     return this._currToken = JsonToken.VALUE_NUMBER_FLOAT;
/*     */   }
/*     */   
/*     */   protected final String _nonStdToken(int type) {
/* 855 */     return NON_STD_TOKENS[type];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void _updateTokenLocation() {
/* 866 */     this._tokenInputRow = Math.max(this._currInputRow, this._currInputRowAlt);
/* 867 */     int ptr = this._inputPtr;
/* 868 */     this._tokenInputCol = ptr - this._currInputRowStart;
/* 869 */     this._tokenInputTotal = this._currInputProcessed + (ptr - this._currBufferStart);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _reportInvalidChar(int c) throws JsonParseException {
/* 874 */     if (c < 32) {
/* 875 */       _throwInvalidSpace(c);
/*     */     }
/* 877 */     _reportInvalidInitial(c);
/*     */   }
/*     */   
/*     */   protected void _reportInvalidInitial(int mask) throws JsonParseException {
/* 881 */     _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(mask));
/*     */   }
/*     */   
/*     */   protected void _reportInvalidOther(int mask, int ptr) throws JsonParseException {
/* 885 */     this._inputPtr = ptr;
/* 886 */     _reportInvalidOther(mask);
/*     */   }
/*     */   
/*     */   protected void _reportInvalidOther(int mask) throws JsonParseException {
/* 890 */     _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(mask));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\async\NonBlockingJsonParserBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */