/*      */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.base.ParserBase;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.TextBuffer;
/*      */ 
/*      */ public class ReaderBasedJsonParser extends ParserBase {
/*   22 */   protected static final int FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
/*      */ 
/*      */ 
/*      */   
/*   26 */   protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Reader _reader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _inputBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _bufferRecyclable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectCodec _objectCodec;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final CharsToNameCanonicalizer _symbols;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _hashSeed;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _tokenIncomplete;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _nameStartOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _nameStartRow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _nameStartCol;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReaderBasedJsonParser(IOContext ctxt, int features, Reader r, ObjectCodec codec, CharsToNameCanonicalizer st, char[] inputBuffer, int start, int end, boolean bufferRecyclable) {
/*  118 */     super(ctxt, features);
/*  119 */     this._reader = r;
/*  120 */     this._inputBuffer = inputBuffer;
/*  121 */     this._inputPtr = start;
/*  122 */     this._inputEnd = end;
/*  123 */     this._objectCodec = codec;
/*  124 */     this._symbols = st;
/*  125 */     this._hashSeed = st.hashSeed();
/*  126 */     this._bufferRecyclable = bufferRecyclable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReaderBasedJsonParser(IOContext ctxt, int features, Reader r, ObjectCodec codec, CharsToNameCanonicalizer st) {
/*  136 */     super(ctxt, features);
/*  137 */     this._reader = r;
/*  138 */     this._inputBuffer = ctxt.allocTokenBuffer();
/*  139 */     this._inputPtr = 0;
/*  140 */     this._inputEnd = 0;
/*  141 */     this._objectCodec = codec;
/*  142 */     this._symbols = st;
/*  143 */     this._hashSeed = st.hashSeed();
/*  144 */     this._bufferRecyclable = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectCodec getCodec() {
/*  153 */     return this._objectCodec; } public void setCodec(ObjectCodec c) {
/*  154 */     this._objectCodec = c;
/*      */   }
/*      */   
/*      */   public int releaseBuffered(Writer w) throws IOException {
/*  158 */     int count = this._inputEnd - this._inputPtr;
/*  159 */     if (count < 1) return 0;
/*      */     
/*  161 */     int origPtr = this._inputPtr;
/*  162 */     w.write(this._inputBuffer, origPtr, count);
/*  163 */     return count;
/*      */   }
/*      */   public Object getInputSource() {
/*  166 */     return this._reader;
/*      */   }
/*      */   @Deprecated
/*      */   protected char getNextChar(String eofMsg) throws IOException {
/*  170 */     return getNextChar(eofMsg, (JsonToken)null);
/*      */   }
/*      */   
/*      */   protected char getNextChar(String eofMsg, JsonToken forToken) throws IOException {
/*  174 */     if (this._inputPtr >= this._inputEnd && 
/*  175 */       !_loadMore()) {
/*  176 */       _reportInvalidEOF(eofMsg, forToken);
/*      */     }
/*      */     
/*  179 */     return this._inputBuffer[this._inputPtr++];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _closeInput() throws IOException {
/*  191 */     if (this._reader != null) {
/*  192 */       if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
/*  193 */         this._reader.close();
/*      */       }
/*  195 */       this._reader = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _releaseBuffers() throws IOException {
/*  207 */     super._releaseBuffers();
/*      */     
/*  209 */     this._symbols.release();
/*      */     
/*  211 */     if (this._bufferRecyclable) {
/*  212 */       char[] buf = this._inputBuffer;
/*  213 */       if (buf != null) {
/*  214 */         this._inputBuffer = null;
/*  215 */         this._ioContext.releaseTokenBuffer(buf);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _loadMoreGuaranteed() throws IOException {
/*  227 */     if (!_loadMore()) _reportInvalidEOF();
/*      */   
/*      */   }
/*      */   
/*      */   protected boolean _loadMore() throws IOException {
/*  232 */     int bufSize = this._inputEnd;
/*      */     
/*  234 */     this._currInputProcessed += bufSize;
/*  235 */     this._currInputRowStart -= bufSize;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  240 */     this._nameStartOffset -= bufSize;
/*      */     
/*  242 */     if (this._reader != null) {
/*  243 */       int count = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
/*  244 */       if (count > 0) {
/*  245 */         this._inputPtr = 0;
/*  246 */         this._inputEnd = count;
/*  247 */         return true;
/*      */       } 
/*      */       
/*  250 */       _closeInput();
/*      */       
/*  252 */       if (count == 0) {
/*  253 */         throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
/*      */       }
/*      */     } 
/*  256 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getText() throws IOException {
/*  274 */     JsonToken t = this._currToken;
/*  275 */     if (t == JsonToken.VALUE_STRING) {
/*  276 */       if (this._tokenIncomplete) {
/*  277 */         this._tokenIncomplete = false;
/*  278 */         _finishString();
/*      */       } 
/*  280 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  282 */     return _getText2(t);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getText(Writer writer) throws IOException {
/*  288 */     JsonToken t = this._currToken;
/*  289 */     if (t == JsonToken.VALUE_STRING) {
/*  290 */       if (this._tokenIncomplete) {
/*  291 */         this._tokenIncomplete = false;
/*  292 */         _finishString();
/*      */       } 
/*  294 */       return this._textBuffer.contentsToWriter(writer);
/*      */     } 
/*  296 */     if (t == JsonToken.FIELD_NAME) {
/*  297 */       String n = this._parsingContext.getCurrentName();
/*  298 */       writer.write(n);
/*  299 */       return n.length();
/*      */     } 
/*  301 */     if (t != null) {
/*  302 */       if (t.isNumeric()) {
/*  303 */         return this._textBuffer.contentsToWriter(writer);
/*      */       }
/*  305 */       char[] ch = t.asCharArray();
/*  306 */       writer.write(ch);
/*  307 */       return ch.length;
/*      */     } 
/*  309 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getValueAsString() throws IOException {
/*  318 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  319 */       if (this._tokenIncomplete) {
/*  320 */         this._tokenIncomplete = false;
/*  321 */         _finishString();
/*      */       } 
/*  323 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  325 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  326 */       return getCurrentName();
/*      */     }
/*  328 */     return super.getValueAsString(null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getValueAsString(String defValue) throws IOException {
/*  334 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  335 */       if (this._tokenIncomplete) {
/*  336 */         this._tokenIncomplete = false;
/*  337 */         _finishString();
/*      */       } 
/*  339 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  341 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  342 */       return getCurrentName();
/*      */     }
/*  344 */     return super.getValueAsString(defValue);
/*      */   }
/*      */   
/*      */   protected final String _getText2(JsonToken t) {
/*  348 */     if (t == null) {
/*  349 */       return null;
/*      */     }
/*  351 */     switch (t.id()) {
/*      */       case 5:
/*  353 */         return this._parsingContext.getCurrentName();
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*  359 */         return this._textBuffer.contentsAsString();
/*      */     } 
/*  361 */     return t.asString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final char[] getTextCharacters() throws IOException {
/*  368 */     if (this._currToken != null) {
/*  369 */       switch (this._currToken.id()) {
/*      */         case 5:
/*  371 */           if (!this._nameCopied) {
/*  372 */             String name = this._parsingContext.getCurrentName();
/*  373 */             int nameLen = name.length();
/*  374 */             if (this._nameCopyBuffer == null) {
/*  375 */               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(nameLen);
/*  376 */             } else if (this._nameCopyBuffer.length < nameLen) {
/*  377 */               this._nameCopyBuffer = new char[nameLen];
/*      */             } 
/*  379 */             name.getChars(0, nameLen, this._nameCopyBuffer, 0);
/*  380 */             this._nameCopied = true;
/*      */           } 
/*  382 */           return this._nameCopyBuffer;
/*      */         case 6:
/*  384 */           if (this._tokenIncomplete) {
/*  385 */             this._tokenIncomplete = false;
/*  386 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  391 */           return this._textBuffer.getTextBuffer();
/*      */       } 
/*  393 */       return this._currToken.asCharArray();
/*      */     } 
/*      */     
/*  396 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getTextLength() throws IOException {
/*  402 */     if (this._currToken != null) {
/*  403 */       switch (this._currToken.id()) {
/*      */         case 5:
/*  405 */           return this._parsingContext.getCurrentName().length();
/*      */         case 6:
/*  407 */           if (this._tokenIncomplete) {
/*  408 */             this._tokenIncomplete = false;
/*  409 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  414 */           return this._textBuffer.size();
/*      */       } 
/*  416 */       return (this._currToken.asCharArray()).length;
/*      */     } 
/*      */     
/*  419 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getTextOffset() throws IOException {
/*  426 */     if (this._currToken != null) {
/*  427 */       switch (this._currToken.id()) {
/*      */         case 5:
/*  429 */           return 0;
/*      */         case 6:
/*  431 */           if (this._tokenIncomplete) {
/*  432 */             this._tokenIncomplete = false;
/*  433 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  438 */           return this._textBuffer.getTextOffset();
/*      */       } 
/*      */     
/*      */     }
/*  442 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
/*  448 */     if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
/*  449 */       return this._binaryValue;
/*      */     }
/*  451 */     if (this._currToken != JsonToken.VALUE_STRING) {
/*  452 */       _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
/*      */     }
/*      */     
/*  455 */     if (this._tokenIncomplete) {
/*      */       try {
/*  457 */         this._binaryValue = _decodeBase64(b64variant);
/*  458 */       } catch (IllegalArgumentException iae) {
/*  459 */         throw _constructError("Failed to decode VALUE_STRING as base64 (" + b64variant + "): " + iae.getMessage());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  464 */       this._tokenIncomplete = false;
/*      */     }
/*  466 */     else if (this._binaryValue == null) {
/*      */       
/*  468 */       ByteArrayBuilder builder = _getByteArrayBuilder();
/*  469 */       _decodeBase64(getText(), builder, b64variant);
/*  470 */       this._binaryValue = builder.toByteArray();
/*      */     } 
/*      */     
/*  473 */     return this._binaryValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
/*  480 */     if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
/*  481 */       byte[] b = getBinaryValue(b64variant);
/*  482 */       out.write(b);
/*  483 */       return b.length;
/*      */     } 
/*      */     
/*  486 */     byte[] buf = this._ioContext.allocBase64Buffer();
/*      */     try {
/*  488 */       return _readBinary(b64variant, out, buf);
/*      */     } finally {
/*  490 */       this._ioContext.releaseBase64Buffer(buf);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int _readBinary(Base64Variant b64variant, OutputStream out, byte[] buffer) throws IOException {
/*  496 */     int outputPtr = 0;
/*  497 */     int outputEnd = buffer.length - 3;
/*  498 */     int outputCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  504 */       if (this._inputPtr >= this._inputEnd) {
/*  505 */         _loadMoreGuaranteed();
/*      */       }
/*  507 */       char ch = this._inputBuffer[this._inputPtr++];
/*  508 */       if (ch > ' ') {
/*  509 */         int bits = b64variant.decodeBase64Char(ch);
/*  510 */         if (bits < 0) {
/*  511 */           if (ch == '"') {
/*      */             break;
/*      */           }
/*  514 */           bits = _decodeBase64Escape(b64variant, ch, 0);
/*  515 */           if (bits < 0) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  521 */         if (outputPtr > outputEnd) {
/*  522 */           outputCount += outputPtr;
/*  523 */           out.write(buffer, 0, outputPtr);
/*  524 */           outputPtr = 0;
/*      */         } 
/*      */         
/*  527 */         int decodedData = bits;
/*      */ 
/*      */ 
/*      */         
/*  531 */         if (this._inputPtr >= this._inputEnd) {
/*  532 */           _loadMoreGuaranteed();
/*      */         }
/*  534 */         ch = this._inputBuffer[this._inputPtr++];
/*  535 */         bits = b64variant.decodeBase64Char(ch);
/*  536 */         if (bits < 0) {
/*  537 */           bits = _decodeBase64Escape(b64variant, ch, 1);
/*      */         }
/*  539 */         decodedData = decodedData << 6 | bits;
/*      */ 
/*      */         
/*  542 */         if (this._inputPtr >= this._inputEnd) {
/*  543 */           _loadMoreGuaranteed();
/*      */         }
/*  545 */         ch = this._inputBuffer[this._inputPtr++];
/*  546 */         bits = b64variant.decodeBase64Char(ch);
/*      */ 
/*      */         
/*  549 */         if (bits < 0) {
/*  550 */           if (bits != -2) {
/*      */             
/*  552 */             if (ch == '"' && !b64variant.usesPadding()) {
/*  553 */               decodedData >>= 4;
/*  554 */               buffer[outputPtr++] = (byte)decodedData;
/*      */               break;
/*      */             } 
/*  557 */             bits = _decodeBase64Escape(b64variant, ch, 2);
/*      */           } 
/*  559 */           if (bits == -2) {
/*      */             
/*  561 */             if (this._inputPtr >= this._inputEnd) {
/*  562 */               _loadMoreGuaranteed();
/*      */             }
/*  564 */             ch = this._inputBuffer[this._inputPtr++];
/*  565 */             if (!b64variant.usesPaddingChar(ch)) {
/*  566 */               throw reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
/*      */             }
/*      */             
/*  569 */             decodedData >>= 4;
/*  570 */             buffer[outputPtr++] = (byte)decodedData;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  575 */         decodedData = decodedData << 6 | bits;
/*      */         
/*  577 */         if (this._inputPtr >= this._inputEnd) {
/*  578 */           _loadMoreGuaranteed();
/*      */         }
/*  580 */         ch = this._inputBuffer[this._inputPtr++];
/*  581 */         bits = b64variant.decodeBase64Char(ch);
/*  582 */         if (bits < 0) {
/*  583 */           if (bits != -2) {
/*      */             
/*  585 */             if (ch == '"' && !b64variant.usesPadding()) {
/*  586 */               decodedData >>= 2;
/*  587 */               buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  588 */               buffer[outputPtr++] = (byte)decodedData;
/*      */               break;
/*      */             } 
/*  591 */             bits = _decodeBase64Escape(b64variant, ch, 3);
/*      */           } 
/*  593 */           if (bits == -2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  600 */             decodedData >>= 2;
/*  601 */             buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  602 */             buffer[outputPtr++] = (byte)decodedData;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  607 */         decodedData = decodedData << 6 | bits;
/*  608 */         buffer[outputPtr++] = (byte)(decodedData >> 16);
/*  609 */         buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  610 */         buffer[outputPtr++] = (byte)decodedData;
/*      */       } 
/*  612 */     }  this._tokenIncomplete = false;
/*  613 */     if (outputPtr > 0) {
/*  614 */       outputCount += outputPtr;
/*  615 */       out.write(buffer, 0, outputPtr);
/*      */     } 
/*  617 */     return outputCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonToken nextToken() throws IOException {
/*      */     JsonToken t;
/*  637 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  638 */       return _nextAfterName();
/*      */     }
/*      */ 
/*      */     
/*  642 */     this._numTypesValid = 0;
/*  643 */     if (this._tokenIncomplete) {
/*  644 */       _skipString();
/*      */     }
/*  646 */     int i = _skipWSOrEnd();
/*  647 */     if (i < 0) {
/*      */ 
/*      */       
/*  650 */       close();
/*  651 */       return this._currToken = null;
/*      */     } 
/*      */     
/*  654 */     this._binaryValue = null;
/*      */ 
/*      */     
/*  657 */     if (i == 93 || i == 125) {
/*  658 */       _closeScope(i);
/*  659 */       return this._currToken;
/*      */     } 
/*      */ 
/*      */     
/*  663 */     if (this._parsingContext.expectComma()) {
/*  664 */       i = _skipComma(i);
/*      */ 
/*      */       
/*  667 */       if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (
/*  668 */         i == 93 || i == 125)) {
/*  669 */         _closeScope(i);
/*  670 */         return this._currToken;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  678 */     boolean inObject = this._parsingContext.inObject();
/*  679 */     if (inObject) {
/*      */       
/*  681 */       _updateNameLocation();
/*  682 */       String name = (i == 34) ? _parseName() : _handleOddName(i);
/*  683 */       this._parsingContext.setCurrentName(name);
/*  684 */       this._currToken = JsonToken.FIELD_NAME;
/*  685 */       i = _skipColon();
/*      */     } 
/*  687 */     _updateLocation();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  693 */     switch (i) {
/*      */       case 34:
/*  695 */         this._tokenIncomplete = true;
/*  696 */         t = JsonToken.VALUE_STRING;
/*      */         break;
/*      */       case 91:
/*  699 */         if (!inObject) {
/*  700 */           this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*      */         }
/*  702 */         t = JsonToken.START_ARRAY;
/*      */         break;
/*      */       case 123:
/*  705 */         if (!inObject) {
/*  706 */           this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */         }
/*  708 */         t = JsonToken.START_OBJECT;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 125:
/*  713 */         _reportUnexpectedChar(i, "expected a value");
/*      */       case 116:
/*  715 */         _matchTrue();
/*  716 */         t = JsonToken.VALUE_TRUE;
/*      */         break;
/*      */       case 102:
/*  719 */         _matchFalse();
/*  720 */         t = JsonToken.VALUE_FALSE;
/*      */         break;
/*      */       case 110:
/*  723 */         _matchNull();
/*  724 */         t = JsonToken.VALUE_NULL;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 45:
/*  732 */         t = _parseNegNumber();
/*      */         break;
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*  744 */         t = _parsePosNumber(i);
/*      */         break;
/*      */       default:
/*  747 */         t = _handleOddValue(i);
/*      */         break;
/*      */     } 
/*      */     
/*  751 */     if (inObject) {
/*  752 */       this._nextToken = t;
/*  753 */       return this._currToken;
/*      */     } 
/*  755 */     this._currToken = t;
/*  756 */     return t;
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _nextAfterName() {
/*  761 */     this._nameCopied = false;
/*  762 */     JsonToken t = this._nextToken;
/*  763 */     this._nextToken = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  768 */     if (t == JsonToken.START_ARRAY) {
/*  769 */       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  770 */     } else if (t == JsonToken.START_OBJECT) {
/*  771 */       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */     } 
/*  773 */     return this._currToken = t;
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishToken() throws IOException {
/*  778 */     if (this._tokenIncomplete) {
/*  779 */       this._tokenIncomplete = false;
/*  780 */       _finishString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nextFieldName(SerializableString sstr) throws IOException {
/*  796 */     this._numTypesValid = 0;
/*  797 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  798 */       _nextAfterName();
/*  799 */       return false;
/*      */     } 
/*  801 */     if (this._tokenIncomplete) {
/*  802 */       _skipString();
/*      */     }
/*  804 */     int i = _skipWSOrEnd();
/*  805 */     if (i < 0) {
/*  806 */       close();
/*  807 */       this._currToken = null;
/*  808 */       return false;
/*      */     } 
/*  810 */     this._binaryValue = null;
/*      */ 
/*      */     
/*  813 */     if (i == 93 || i == 125) {
/*  814 */       _closeScope(i);
/*  815 */       return false;
/*      */     } 
/*      */     
/*  818 */     if (this._parsingContext.expectComma()) {
/*  819 */       i = _skipComma(i);
/*      */ 
/*      */       
/*  822 */       if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (
/*  823 */         i == 93 || i == 125)) {
/*  824 */         _closeScope(i);
/*  825 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  830 */     if (!this._parsingContext.inObject()) {
/*  831 */       _updateLocation();
/*  832 */       _nextTokenNotInObject(i);
/*  833 */       return false;
/*      */     } 
/*      */     
/*  836 */     _updateNameLocation();
/*  837 */     if (i == 34) {
/*      */       
/*  839 */       char[] nameChars = sstr.asQuotedChars();
/*  840 */       int len = nameChars.length;
/*      */ 
/*      */       
/*  843 */       if (this._inputPtr + len + 4 < this._inputEnd) {
/*      */         
/*  845 */         int end = this._inputPtr + len;
/*  846 */         if (this._inputBuffer[end] == '"') {
/*  847 */           int offset = 0;
/*  848 */           int ptr = this._inputPtr;
/*      */           while (true) {
/*  850 */             if (ptr == end) {
/*  851 */               this._parsingContext.setCurrentName(sstr.getValue());
/*  852 */               _isNextTokenNameYes(_skipColonFast(ptr + 1));
/*  853 */               return true;
/*      */             } 
/*  855 */             if (nameChars[offset] != this._inputBuffer[ptr]) {
/*      */               break;
/*      */             }
/*  858 */             offset++;
/*  859 */             ptr++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  864 */     return _isNextTokenNameMaybe(i, sstr.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextFieldName() throws IOException {
/*  872 */     this._numTypesValid = 0;
/*  873 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  874 */       _nextAfterName();
/*  875 */       return null;
/*      */     } 
/*  877 */     if (this._tokenIncomplete) {
/*  878 */       _skipString();
/*      */     }
/*  880 */     int i = _skipWSOrEnd();
/*  881 */     if (i < 0) {
/*  882 */       close();
/*  883 */       this._currToken = null;
/*  884 */       return null;
/*      */     } 
/*  886 */     this._binaryValue = null;
/*  887 */     if (i == 93 || i == 125) {
/*  888 */       _closeScope(i);
/*  889 */       return null;
/*      */     } 
/*  891 */     if (this._parsingContext.expectComma()) {
/*  892 */       i = _skipComma(i);
/*  893 */       if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (
/*  894 */         i == 93 || i == 125)) {
/*  895 */         _closeScope(i);
/*  896 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/*  900 */     if (!this._parsingContext.inObject()) {
/*  901 */       _updateLocation();
/*  902 */       _nextTokenNotInObject(i);
/*  903 */       return null;
/*      */     } 
/*      */     
/*  906 */     _updateNameLocation();
/*  907 */     String name = (i == 34) ? _parseName() : _handleOddName(i);
/*  908 */     this._parsingContext.setCurrentName(name);
/*  909 */     this._currToken = JsonToken.FIELD_NAME;
/*  910 */     i = _skipColon();
/*      */     
/*  912 */     _updateLocation();
/*  913 */     if (i == 34) {
/*  914 */       this._tokenIncomplete = true;
/*  915 */       this._nextToken = JsonToken.VALUE_STRING;
/*  916 */       return name;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  923 */     switch (i)
/*      */     { case 45:
/*  925 */         t = _parseNegNumber();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  961 */         this._nextToken = t;
/*  962 */         return name;case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return name;case 102: _matchFalse(); t = JsonToken.VALUE_FALSE; this._nextToken = t; return name;case 110: _matchNull(); t = JsonToken.VALUE_NULL; this._nextToken = t; return name;case 116: _matchTrue(); t = JsonToken.VALUE_TRUE; this._nextToken = t; return name;case 91: t = JsonToken.START_ARRAY; this._nextToken = t; return name;case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return name; }  JsonToken t = _handleOddValue(i); this._nextToken = t; return name;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _isNextTokenNameYes(int i) throws IOException {
/*  967 */     this._currToken = JsonToken.FIELD_NAME;
/*  968 */     _updateLocation();
/*      */     
/*  970 */     switch (i) {
/*      */       case 34:
/*  972 */         this._tokenIncomplete = true;
/*  973 */         this._nextToken = JsonToken.VALUE_STRING;
/*      */         return;
/*      */       case 91:
/*  976 */         this._nextToken = JsonToken.START_ARRAY;
/*      */         return;
/*      */       case 123:
/*  979 */         this._nextToken = JsonToken.START_OBJECT;
/*      */         return;
/*      */       case 116:
/*  982 */         _matchToken("true", 1);
/*  983 */         this._nextToken = JsonToken.VALUE_TRUE;
/*      */         return;
/*      */       case 102:
/*  986 */         _matchToken("false", 1);
/*  987 */         this._nextToken = JsonToken.VALUE_FALSE;
/*      */         return;
/*      */       case 110:
/*  990 */         _matchToken("null", 1);
/*  991 */         this._nextToken = JsonToken.VALUE_NULL;
/*      */         return;
/*      */       case 45:
/*  994 */         this._nextToken = _parseNegNumber();
/*      */         return;
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/* 1006 */         this._nextToken = _parsePosNumber(i);
/*      */         return;
/*      */     } 
/* 1009 */     this._nextToken = _handleOddValue(i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _isNextTokenNameMaybe(int i, String nameToMatch) throws IOException {
/* 1015 */     String name = (i == 34) ? _parseName() : _handleOddName(i);
/* 1016 */     this._parsingContext.setCurrentName(name);
/* 1017 */     this._currToken = JsonToken.FIELD_NAME;
/* 1018 */     i = _skipColon();
/* 1019 */     _updateLocation();
/* 1020 */     if (i == 34) {
/* 1021 */       this._tokenIncomplete = true;
/* 1022 */       this._nextToken = JsonToken.VALUE_STRING;
/* 1023 */       return nameToMatch.equals(name);
/*      */     } 
/*      */ 
/*      */     
/* 1027 */     switch (i)
/*      */     { case 45:
/* 1029 */         t = _parseNegNumber();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1065 */         this._nextToken = t;
/* 1066 */         return nameToMatch.equals(name);case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return nameToMatch.equals(name);case 102: _matchFalse(); t = JsonToken.VALUE_FALSE; this._nextToken = t; return nameToMatch.equals(name);case 110: _matchNull(); t = JsonToken.VALUE_NULL; this._nextToken = t; return nameToMatch.equals(name);case 116: _matchTrue(); t = JsonToken.VALUE_TRUE; this._nextToken = t; return nameToMatch.equals(name);case 91: t = JsonToken.START_ARRAY; this._nextToken = t; return nameToMatch.equals(name);case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return nameToMatch.equals(name); }  JsonToken t = _handleOddValue(i); this._nextToken = t; return nameToMatch.equals(name);
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _nextTokenNotInObject(int i) throws IOException {
/* 1071 */     if (i == 34) {
/* 1072 */       this._tokenIncomplete = true;
/* 1073 */       return this._currToken = JsonToken.VALUE_STRING;
/*      */     } 
/* 1075 */     switch (i) {
/*      */       case 91:
/* 1077 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1078 */         return this._currToken = JsonToken.START_ARRAY;
/*      */       case 123:
/* 1080 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/* 1081 */         return this._currToken = JsonToken.START_OBJECT;
/*      */       case 116:
/* 1083 */         _matchToken("true", 1);
/* 1084 */         return this._currToken = JsonToken.VALUE_TRUE;
/*      */       case 102:
/* 1086 */         _matchToken("false", 1);
/* 1087 */         return this._currToken = JsonToken.VALUE_FALSE;
/*      */       case 110:
/* 1089 */         _matchToken("null", 1);
/* 1090 */         return this._currToken = JsonToken.VALUE_NULL;
/*      */       case 45:
/* 1092 */         return this._currToken = _parseNegNumber();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/* 1107 */         return this._currToken = _parsePosNumber(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 44:
/*      */       case 93:
/* 1118 */         if (isEnabled(JsonParser.Feature.ALLOW_MISSING_VALUES)) {
/* 1119 */           this._inputPtr--;
/* 1120 */           return this._currToken = JsonToken.VALUE_NULL;
/*      */         }  break;
/*      */     } 
/* 1123 */     return this._currToken = _handleOddValue(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String nextTextValue() throws IOException {
/* 1130 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1131 */       this._nameCopied = false;
/* 1132 */       JsonToken t = this._nextToken;
/* 1133 */       this._nextToken = null;
/* 1134 */       this._currToken = t;
/* 1135 */       if (t == JsonToken.VALUE_STRING) {
/* 1136 */         if (this._tokenIncomplete) {
/* 1137 */           this._tokenIncomplete = false;
/* 1138 */           _finishString();
/*      */         } 
/* 1140 */         return this._textBuffer.contentsAsString();
/*      */       } 
/* 1142 */       if (t == JsonToken.START_ARRAY) {
/* 1143 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1144 */       } else if (t == JsonToken.START_OBJECT) {
/* 1145 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1147 */       return null;
/*      */     } 
/*      */     
/* 1150 */     return (nextToken() == JsonToken.VALUE_STRING) ? getText() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int nextIntValue(int defaultValue) throws IOException {
/* 1157 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1158 */       this._nameCopied = false;
/* 1159 */       JsonToken t = this._nextToken;
/* 1160 */       this._nextToken = null;
/* 1161 */       this._currToken = t;
/* 1162 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 1163 */         return getIntValue();
/*      */       }
/* 1165 */       if (t == JsonToken.START_ARRAY) {
/* 1166 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1167 */       } else if (t == JsonToken.START_OBJECT) {
/* 1168 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1170 */       return defaultValue;
/*      */     } 
/*      */     
/* 1173 */     return (nextToken() == JsonToken.VALUE_NUMBER_INT) ? getIntValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long nextLongValue(long defaultValue) throws IOException {
/* 1180 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1181 */       this._nameCopied = false;
/* 1182 */       JsonToken t = this._nextToken;
/* 1183 */       this._nextToken = null;
/* 1184 */       this._currToken = t;
/* 1185 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 1186 */         return getLongValue();
/*      */       }
/* 1188 */       if (t == JsonToken.START_ARRAY) {
/* 1189 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1190 */       } else if (t == JsonToken.START_OBJECT) {
/* 1191 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1193 */       return defaultValue;
/*      */     } 
/*      */     
/* 1196 */     return (nextToken() == JsonToken.VALUE_NUMBER_INT) ? getLongValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Boolean nextBooleanValue() throws IOException {
/* 1203 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1204 */       this._nameCopied = false;
/* 1205 */       JsonToken jsonToken = this._nextToken;
/* 1206 */       this._nextToken = null;
/* 1207 */       this._currToken = jsonToken;
/* 1208 */       if (jsonToken == JsonToken.VALUE_TRUE) {
/* 1209 */         return Boolean.TRUE;
/*      */       }
/* 1211 */       if (jsonToken == JsonToken.VALUE_FALSE) {
/* 1212 */         return Boolean.FALSE;
/*      */       }
/* 1214 */       if (jsonToken == JsonToken.START_ARRAY) {
/* 1215 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1216 */       } else if (jsonToken == JsonToken.START_OBJECT) {
/* 1217 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1219 */       return null;
/*      */     } 
/* 1221 */     JsonToken t = nextToken();
/* 1222 */     if (t != null) {
/* 1223 */       int id = t.id();
/* 1224 */       if (id == 9) return Boolean.TRUE; 
/* 1225 */       if (id == 10) return Boolean.FALSE; 
/*      */     } 
/* 1227 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonToken _parsePosNumber(int ch) throws IOException {
/* 1258 */     int ptr = this._inputPtr;
/* 1259 */     int startPtr = ptr - 1;
/* 1260 */     int inputLen = this._inputEnd;
/*      */ 
/*      */     
/* 1263 */     if (ch == 48) {
/* 1264 */       return _parseNumber2(false, startPtr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1273 */     int intLen = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1278 */       if (ptr >= inputLen) {
/* 1279 */         this._inputPtr = startPtr;
/* 1280 */         return _parseNumber2(false, startPtr);
/*      */       } 
/* 1282 */       ch = this._inputBuffer[ptr++];
/* 1283 */       if (ch < 48 || ch > 57) {
/*      */         break;
/*      */       }
/* 1286 */       intLen++;
/*      */     } 
/* 1288 */     if (ch == 46 || ch == 101 || ch == 69) {
/* 1289 */       this._inputPtr = ptr;
/* 1290 */       return _parseFloat(ch, startPtr, ptr, false, intLen);
/*      */     } 
/*      */ 
/*      */     
/* 1294 */     this._inputPtr = --ptr;
/*      */     
/* 1296 */     if (this._parsingContext.inRoot()) {
/* 1297 */       _verifyRootSpace(ch);
/*      */     }
/* 1299 */     int len = ptr - startPtr;
/* 1300 */     this._textBuffer.resetWithShared(this._inputBuffer, startPtr, len);
/* 1301 */     return resetInt(false, intLen);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _parseFloat(int ch, int startPtr, int ptr, boolean neg, int intLen) throws IOException {
/* 1307 */     int inputLen = this._inputEnd;
/* 1308 */     int fractLen = 0;
/*      */ 
/*      */     
/* 1311 */     if (ch == 46) {
/*      */       
/*      */       while (true) {
/* 1314 */         if (ptr >= inputLen) {
/* 1315 */           return _parseNumber2(neg, startPtr);
/*      */         }
/* 1317 */         ch = this._inputBuffer[ptr++];
/* 1318 */         if (ch < 48 || ch > 57) {
/*      */           break;
/*      */         }
/* 1321 */         fractLen++;
/*      */       } 
/*      */       
/* 1324 */       if (fractLen == 0) {
/* 1325 */         reportUnexpectedNumberChar(ch, "Decimal point not followed by a digit");
/*      */       }
/*      */     } 
/* 1328 */     int expLen = 0;
/* 1329 */     if (ch == 101 || ch == 69) {
/* 1330 */       if (ptr >= inputLen) {
/* 1331 */         this._inputPtr = startPtr;
/* 1332 */         return _parseNumber2(neg, startPtr);
/*      */       } 
/*      */       
/* 1335 */       ch = this._inputBuffer[ptr++];
/* 1336 */       if (ch == 45 || ch == 43) {
/* 1337 */         if (ptr >= inputLen) {
/* 1338 */           this._inputPtr = startPtr;
/* 1339 */           return _parseNumber2(neg, startPtr);
/*      */         } 
/* 1341 */         ch = this._inputBuffer[ptr++];
/*      */       } 
/* 1343 */       while (ch <= 57 && ch >= 48) {
/* 1344 */         expLen++;
/* 1345 */         if (ptr >= inputLen) {
/* 1346 */           this._inputPtr = startPtr;
/* 1347 */           return _parseNumber2(neg, startPtr);
/*      */         } 
/* 1349 */         ch = this._inputBuffer[ptr++];
/*      */       } 
/*      */       
/* 1352 */       if (expLen == 0) {
/* 1353 */         reportUnexpectedNumberChar(ch, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     } 
/*      */     
/* 1357 */     this._inputPtr = --ptr;
/*      */     
/* 1359 */     if (this._parsingContext.inRoot()) {
/* 1360 */       _verifyRootSpace(ch);
/*      */     }
/* 1362 */     int len = ptr - startPtr;
/* 1363 */     this._textBuffer.resetWithShared(this._inputBuffer, startPtr, len);
/*      */     
/* 1365 */     return resetFloat(neg, intLen, fractLen, expLen);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final JsonToken _parseNegNumber() throws IOException {
/* 1370 */     int ptr = this._inputPtr;
/* 1371 */     int startPtr = ptr - 1;
/* 1372 */     int inputLen = this._inputEnd;
/*      */     
/* 1374 */     if (ptr >= inputLen) {
/* 1375 */       return _parseNumber2(true, startPtr);
/*      */     }
/* 1377 */     int ch = this._inputBuffer[ptr++];
/*      */     
/* 1379 */     if (ch > 57 || ch < 48) {
/* 1380 */       this._inputPtr = ptr;
/* 1381 */       return _handleInvalidNumberStart(ch, true);
/*      */     } 
/*      */     
/* 1384 */     if (ch == 48) {
/* 1385 */       return _parseNumber2(true, startPtr);
/*      */     }
/* 1387 */     int intLen = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1392 */       if (ptr >= inputLen) {
/* 1393 */         return _parseNumber2(true, startPtr);
/*      */       }
/* 1395 */       ch = this._inputBuffer[ptr++];
/* 1396 */       if (ch < 48 || ch > 57) {
/*      */         break;
/*      */       }
/* 1399 */       intLen++;
/*      */     } 
/*      */     
/* 1402 */     if (ch == 46 || ch == 101 || ch == 69) {
/* 1403 */       this._inputPtr = ptr;
/* 1404 */       return _parseFloat(ch, startPtr, ptr, true, intLen);
/*      */     } 
/*      */     
/* 1407 */     this._inputPtr = --ptr;
/* 1408 */     if (this._parsingContext.inRoot()) {
/* 1409 */       _verifyRootSpace(ch);
/*      */     }
/* 1411 */     int len = ptr - startPtr;
/* 1412 */     this._textBuffer.resetWithShared(this._inputBuffer, startPtr, len);
/* 1413 */     return resetInt(true, intLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _parseNumber2(boolean neg, int startPtr) throws IOException {
/* 1425 */     this._inputPtr = neg ? (startPtr + 1) : startPtr;
/* 1426 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1427 */     int outPtr = 0;
/*      */ 
/*      */     
/* 1430 */     if (neg) {
/* 1431 */       outBuf[outPtr++] = '-';
/*      */     }
/*      */ 
/*      */     
/* 1435 */     int intLen = 0;
/* 1436 */     char c = (this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
/*      */     
/* 1438 */     if (c == '0') {
/* 1439 */       c = _verifyNoLeadingZeroes();
/*      */     }
/* 1441 */     boolean eof = false;
/*      */ 
/*      */ 
/*      */     
/* 1445 */     while (c >= '0' && c <= '9') {
/* 1446 */       intLen++;
/* 1447 */       if (outPtr >= outBuf.length) {
/* 1448 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1449 */         outPtr = 0;
/*      */       } 
/* 1451 */       outBuf[outPtr++] = c;
/* 1452 */       if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/*      */         
/* 1454 */         c = Character.MIN_VALUE;
/* 1455 */         eof = true;
/*      */         break;
/*      */       } 
/* 1458 */       c = this._inputBuffer[this._inputPtr++];
/*      */     } 
/*      */     
/* 1461 */     if (intLen == 0) {
/* 1462 */       return _handleInvalidNumberStart(c, neg);
/*      */     }
/*      */     
/* 1465 */     int fractLen = 0;
/*      */     
/* 1467 */     if (c == '.') {
/* 1468 */       if (outPtr >= outBuf.length) {
/* 1469 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1470 */         outPtr = 0;
/*      */       } 
/* 1472 */       outBuf[outPtr++] = c;
/*      */ 
/*      */       
/*      */       while (true) {
/* 1476 */         if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1477 */           eof = true;
/*      */           break;
/*      */         } 
/* 1480 */         c = this._inputBuffer[this._inputPtr++];
/* 1481 */         if (c < '0' || c > '9') {
/*      */           break;
/*      */         }
/* 1484 */         fractLen++;
/* 1485 */         if (outPtr >= outBuf.length) {
/* 1486 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1487 */           outPtr = 0;
/*      */         } 
/* 1489 */         outBuf[outPtr++] = c;
/*      */       } 
/*      */       
/* 1492 */       if (fractLen == 0) {
/* 1493 */         reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
/*      */       }
/*      */     } 
/*      */     
/* 1497 */     int expLen = 0;
/* 1498 */     if (c == 'e' || c == 'E') {
/* 1499 */       if (outPtr >= outBuf.length) {
/* 1500 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1501 */         outPtr = 0;
/*      */       } 
/* 1503 */       outBuf[outPtr++] = c;
/*      */       
/* 1505 */       c = (this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : getNextChar("expected a digit for number exponent");
/*      */ 
/*      */       
/* 1508 */       if (c == '-' || c == '+') {
/* 1509 */         if (outPtr >= outBuf.length) {
/* 1510 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1511 */           outPtr = 0;
/*      */         } 
/* 1513 */         outBuf[outPtr++] = c;
/*      */         
/* 1515 */         c = (this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : getNextChar("expected a digit for number exponent");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1520 */       while (c <= '9' && c >= '0') {
/* 1521 */         expLen++;
/* 1522 */         if (outPtr >= outBuf.length) {
/* 1523 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1524 */           outPtr = 0;
/*      */         } 
/* 1526 */         outBuf[outPtr++] = c;
/* 1527 */         if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1528 */           eof = true;
/*      */           break;
/*      */         } 
/* 1531 */         c = this._inputBuffer[this._inputPtr++];
/*      */       } 
/*      */       
/* 1534 */       if (expLen == 0) {
/* 1535 */         reportUnexpectedNumberChar(c, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1540 */     if (!eof) {
/* 1541 */       this._inputPtr--;
/* 1542 */       if (this._parsingContext.inRoot()) {
/* 1543 */         _verifyRootSpace(c);
/*      */       }
/*      */     } 
/* 1546 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1548 */     return reset(neg, intLen, fractLen, expLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final char _verifyNoLeadingZeroes() throws IOException {
/* 1558 */     if (this._inputPtr < this._inputEnd) {
/* 1559 */       char ch = this._inputBuffer[this._inputPtr];
/*      */       
/* 1561 */       if (ch < '0' || ch > '9') {
/* 1562 */         return '0';
/*      */       }
/*      */     } 
/*      */     
/* 1566 */     return _verifyNLZ2();
/*      */   }
/*      */ 
/*      */   
/*      */   private char _verifyNLZ2() throws IOException {
/* 1571 */     if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1572 */       return '0';
/*      */     }
/* 1574 */     char ch = this._inputBuffer[this._inputPtr];
/* 1575 */     if (ch < '0' || ch > '9') {
/* 1576 */       return '0';
/*      */     }
/* 1578 */     if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1579 */       reportInvalidNumber("Leading zeroes not allowed");
/*      */     }
/*      */     
/* 1582 */     this._inputPtr++;
/* 1583 */     if (ch == '0') {
/* 1584 */       while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 1585 */         ch = this._inputBuffer[this._inputPtr];
/* 1586 */         if (ch < '0' || ch > '9') {
/* 1587 */           return '0';
/*      */         }
/* 1589 */         this._inputPtr++;
/* 1590 */         if (ch != '0') {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/* 1595 */     return ch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _handleInvalidNumberStart(int ch, boolean negative) throws IOException {
/* 1604 */     if (ch == 73) {
/* 1605 */       if (this._inputPtr >= this._inputEnd && 
/* 1606 */         !_loadMore()) {
/* 1607 */         _reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
/*      */       }
/*      */       
/* 1610 */       ch = this._inputBuffer[this._inputPtr++];
/* 1611 */       if (ch == 78) {
/* 1612 */         String match = negative ? "-INF" : "+INF";
/* 1613 */         _matchToken(match, 3);
/* 1614 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1615 */           return resetAsNaN(match, negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */         }
/* 1617 */         _reportError("Non-standard token '" + match + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/* 1618 */       } else if (ch == 110) {
/* 1619 */         String match = negative ? "-Infinity" : "+Infinity";
/* 1620 */         _matchToken(match, 3);
/* 1621 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1622 */           return resetAsNaN(match, negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */         }
/* 1624 */         _reportError("Non-standard token '" + match + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */       } 
/*      */     } 
/* 1627 */     reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 1628 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _verifyRootSpace(int ch) throws IOException {
/* 1641 */     this._inputPtr++;
/* 1642 */     switch (ch) {
/*      */       case 9:
/*      */       case 32:
/*      */         return;
/*      */       case 13:
/* 1647 */         _skipCR();
/*      */         return;
/*      */       case 10:
/* 1650 */         this._currInputRow++;
/* 1651 */         this._currInputRowStart = this._inputPtr;
/*      */         return;
/*      */     } 
/* 1654 */     _reportMissingRootWS(ch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String _parseName() throws IOException {
/* 1667 */     int ptr = this._inputPtr;
/* 1668 */     int hash = this._hashSeed;
/* 1669 */     int[] codes = _icLatin1;
/*      */     
/* 1671 */     while (ptr < this._inputEnd) {
/* 1672 */       int ch = this._inputBuffer[ptr];
/* 1673 */       if (ch < codes.length && codes[ch] != 0) {
/* 1674 */         if (ch == 34) {
/* 1675 */           int i = this._inputPtr;
/* 1676 */           this._inputPtr = ptr + 1;
/* 1677 */           return this._symbols.findSymbol(this._inputBuffer, i, ptr - i, hash);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1681 */       hash = hash * 33 + ch;
/* 1682 */       ptr++;
/*      */     } 
/* 1684 */     int start = this._inputPtr;
/* 1685 */     this._inputPtr = ptr;
/* 1686 */     return _parseName2(start, hash, 34);
/*      */   }
/*      */ 
/*      */   
/*      */   private String _parseName2(int startPtr, int hash, int endChar) throws IOException {
/* 1691 */     this._textBuffer.resetWithShared(this._inputBuffer, startPtr, this._inputPtr - startPtr);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1696 */     char[] outBuf = this._textBuffer.getCurrentSegment();
/* 1697 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/*      */     
/*      */     while (true) {
/* 1700 */       if (this._inputPtr >= this._inputEnd && 
/* 1701 */         !_loadMore()) {
/* 1702 */         _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*      */       }
/*      */       
/* 1705 */       char c = this._inputBuffer[this._inputPtr++];
/* 1706 */       int i = c;
/* 1707 */       if (i <= 92) {
/* 1708 */         if (i == 92) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1713 */           c = _decodeEscaped();
/* 1714 */         } else if (i <= endChar) {
/* 1715 */           if (i == endChar) {
/*      */             break;
/*      */           }
/* 1718 */           if (i < 32) {
/* 1719 */             _throwUnquotedSpace(i, "name");
/*      */           }
/*      */         } 
/*      */       }
/* 1723 */       hash = hash * 33 + c;
/*      */       
/* 1725 */       outBuf[outPtr++] = c;
/*      */ 
/*      */       
/* 1728 */       if (outPtr >= outBuf.length) {
/* 1729 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1730 */         outPtr = 0;
/*      */       } 
/*      */     } 
/* 1733 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1735 */     TextBuffer tb = this._textBuffer;
/* 1736 */     char[] buf = tb.getTextBuffer();
/* 1737 */     int start = tb.getTextOffset();
/* 1738 */     int len = tb.size();
/* 1739 */     return this._symbols.findSymbol(buf, start, len, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _handleOddName(int i) throws IOException {
/*      */     boolean firstOk;
/* 1752 */     if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1753 */       return _parseAposName();
/*      */     }
/*      */     
/* 1756 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1757 */       _reportUnexpectedChar(i, "was expecting double-quote to start field name");
/*      */     }
/* 1759 */     int[] codes = CharTypes.getInputCodeLatin1JsNames();
/* 1760 */     int maxCode = codes.length;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1765 */     if (i < maxCode) {
/* 1766 */       firstOk = (codes[i] == 0);
/*      */     } else {
/* 1768 */       firstOk = Character.isJavaIdentifierPart((char)i);
/*      */     } 
/* 1770 */     if (!firstOk) {
/* 1771 */       _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
/*      */     }
/* 1773 */     int ptr = this._inputPtr;
/* 1774 */     int hash = this._hashSeed;
/* 1775 */     int inputLen = this._inputEnd;
/*      */     
/* 1777 */     if (ptr < inputLen) {
/*      */       do {
/* 1779 */         int ch = this._inputBuffer[ptr];
/* 1780 */         if (ch < maxCode) {
/* 1781 */           if (codes[ch] != 0) {
/* 1782 */             int j = this._inputPtr - 1;
/* 1783 */             this._inputPtr = ptr;
/* 1784 */             return this._symbols.findSymbol(this._inputBuffer, j, ptr - j, hash);
/*      */           } 
/* 1786 */         } else if (!Character.isJavaIdentifierPart((char)ch)) {
/* 1787 */           int j = this._inputPtr - 1;
/* 1788 */           this._inputPtr = ptr;
/* 1789 */           return this._symbols.findSymbol(this._inputBuffer, j, ptr - j, hash);
/*      */         } 
/* 1791 */         hash = hash * 33 + ch;
/* 1792 */         ++ptr;
/* 1793 */       } while (ptr < inputLen);
/*      */     }
/* 1795 */     int start = this._inputPtr - 1;
/* 1796 */     this._inputPtr = ptr;
/* 1797 */     return _handleOddName2(start, hash, codes);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _parseAposName() throws IOException {
/* 1803 */     int ptr = this._inputPtr;
/* 1804 */     int hash = this._hashSeed;
/* 1805 */     int inputLen = this._inputEnd;
/*      */     
/* 1807 */     if (ptr < inputLen) {
/* 1808 */       int[] codes = _icLatin1;
/* 1809 */       int maxCode = codes.length;
/*      */       
/*      */       do {
/* 1812 */         int ch = this._inputBuffer[ptr];
/* 1813 */         if (ch == 39) {
/* 1814 */           int i = this._inputPtr;
/* 1815 */           this._inputPtr = ptr + 1;
/* 1816 */           return this._symbols.findSymbol(this._inputBuffer, i, ptr - i, hash);
/*      */         } 
/* 1818 */         if (ch < maxCode && codes[ch] != 0) {
/*      */           break;
/*      */         }
/* 1821 */         hash = hash * 33 + ch;
/* 1822 */         ++ptr;
/* 1823 */       } while (ptr < inputLen);
/*      */     } 
/*      */     
/* 1826 */     int start = this._inputPtr;
/* 1827 */     this._inputPtr = ptr;
/*      */     
/* 1829 */     return _parseName2(start, hash, 39);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _handleOddValue(int i) throws IOException {
/* 1839 */     switch (i) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 39:
/* 1846 */         if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1847 */           return _handleApos();
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 93:
/* 1855 */         if (!this._parsingContext.inArray()) {
/*      */           break;
/*      */         }
/*      */       
/*      */       case 44:
/* 1860 */         if (isEnabled(JsonParser.Feature.ALLOW_MISSING_VALUES)) {
/* 1861 */           this._inputPtr--;
/* 1862 */           return JsonToken.VALUE_NULL;
/*      */         } 
/*      */         break;
/*      */       case 78:
/* 1866 */         _matchToken("NaN", 1);
/* 1867 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1868 */           return resetAsNaN("NaN", Double.NaN);
/*      */         }
/* 1870 */         _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */         break;
/*      */       case 73:
/* 1873 */         _matchToken("Infinity", 1);
/* 1874 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1875 */           return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
/*      */         }
/* 1877 */         _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */         break;
/*      */       case 43:
/* 1880 */         if (this._inputPtr >= this._inputEnd && 
/* 1881 */           !_loadMore()) {
/* 1882 */           _reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
/*      */         }
/*      */         
/* 1885 */         return _handleInvalidNumberStart(this._inputBuffer[this._inputPtr++], false);
/*      */     } 
/*      */     
/* 1888 */     if (Character.isJavaIdentifierStart(i)) {
/* 1889 */       _reportInvalidToken("" + (char)i, "('true', 'false' or 'null')");
/*      */     }
/*      */     
/* 1892 */     _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 1893 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _handleApos() throws IOException {
/* 1898 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1899 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/*      */     
/*      */     while (true) {
/* 1902 */       if (this._inputPtr >= this._inputEnd && 
/* 1903 */         !_loadMore()) {
/* 1904 */         _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
/*      */       }
/*      */ 
/*      */       
/* 1908 */       char c = this._inputBuffer[this._inputPtr++];
/* 1909 */       int i = c;
/* 1910 */       if (i <= 92) {
/* 1911 */         if (i == 92) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1916 */           c = _decodeEscaped();
/* 1917 */         } else if (i <= 39) {
/* 1918 */           if (i == 39) {
/*      */             break;
/*      */           }
/* 1921 */           if (i < 32) {
/* 1922 */             _throwUnquotedSpace(i, "string value");
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1927 */       if (outPtr >= outBuf.length) {
/* 1928 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1929 */         outPtr = 0;
/*      */       } 
/*      */       
/* 1932 */       outBuf[outPtr++] = c;
/*      */     } 
/* 1934 */     this._textBuffer.setCurrentLength(outPtr);
/* 1935 */     return JsonToken.VALUE_STRING;
/*      */   }
/*      */ 
/*      */   
/*      */   private String _handleOddName2(int startPtr, int hash, int[] codes) throws IOException {
/* 1940 */     this._textBuffer.resetWithShared(this._inputBuffer, startPtr, this._inputPtr - startPtr);
/* 1941 */     char[] outBuf = this._textBuffer.getCurrentSegment();
/* 1942 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/* 1943 */     int maxCode = codes.length;
/*      */ 
/*      */     
/* 1946 */     while (this._inputPtr < this._inputEnd || 
/* 1947 */       _loadMore()) {
/*      */ 
/*      */ 
/*      */       
/* 1951 */       char c = this._inputBuffer[this._inputPtr];
/* 1952 */       int i = c;
/* 1953 */       if ((i <= maxCode) ? (
/* 1954 */         codes[i] != 0) : 
/*      */ 
/*      */         
/* 1957 */         !Character.isJavaIdentifierPart(c)) {
/*      */         break;
/*      */       }
/* 1960 */       this._inputPtr++;
/* 1961 */       hash = hash * 33 + i;
/*      */       
/* 1963 */       outBuf[outPtr++] = c;
/*      */ 
/*      */       
/* 1966 */       if (outPtr >= outBuf.length) {
/* 1967 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1968 */         outPtr = 0;
/*      */       } 
/*      */     } 
/* 1971 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1973 */     TextBuffer tb = this._textBuffer;
/* 1974 */     char[] buf = tb.getTextBuffer();
/* 1975 */     int start = tb.getTextOffset();
/* 1976 */     int len = tb.size();
/*      */     
/* 1978 */     return this._symbols.findSymbol(buf, start, len, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _finishString() throws IOException {
/* 1989 */     int ptr = this._inputPtr;
/* 1990 */     int inputLen = this._inputEnd;
/*      */     
/* 1992 */     if (ptr < inputLen) {
/* 1993 */       int[] codes = _icLatin1;
/* 1994 */       int maxCode = codes.length;
/*      */       
/*      */       do {
/* 1997 */         int ch = this._inputBuffer[ptr];
/* 1998 */         if (ch < maxCode && codes[ch] != 0) {
/* 1999 */           if (ch == 34) {
/* 2000 */             this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, ptr - this._inputPtr);
/* 2001 */             this._inputPtr = ptr + 1;
/*      */             
/*      */             return;
/*      */           } 
/*      */           break;
/*      */         } 
/* 2007 */         ++ptr;
/* 2008 */       } while (ptr < inputLen);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2014 */     this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, ptr - this._inputPtr);
/* 2015 */     this._inputPtr = ptr;
/* 2016 */     _finishString2();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _finishString2() throws IOException {
/* 2021 */     char[] outBuf = this._textBuffer.getCurrentSegment();
/* 2022 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/* 2023 */     int[] codes = _icLatin1;
/* 2024 */     int maxCode = codes.length;
/*      */     
/*      */     while (true) {
/* 2027 */       if (this._inputPtr >= this._inputEnd && 
/* 2028 */         !_loadMore()) {
/* 2029 */         _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
/*      */       }
/*      */ 
/*      */       
/* 2033 */       char c = this._inputBuffer[this._inputPtr++];
/* 2034 */       int i = c;
/* 2035 */       if (i < maxCode && codes[i] != 0) {
/* 2036 */         if (i == 34)
/*      */           break; 
/* 2038 */         if (i == 92) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2043 */           c = _decodeEscaped();
/* 2044 */         } else if (i < 32) {
/* 2045 */           _throwUnquotedSpace(i, "string value");
/*      */         } 
/*      */       } 
/*      */       
/* 2049 */       if (outPtr >= outBuf.length) {
/* 2050 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 2051 */         outPtr = 0;
/*      */       } 
/*      */       
/* 2054 */       outBuf[outPtr++] = c;
/*      */     } 
/* 2056 */     this._textBuffer.setCurrentLength(outPtr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _skipString() throws IOException {
/* 2066 */     this._tokenIncomplete = false;
/*      */     
/* 2068 */     int inPtr = this._inputPtr;
/* 2069 */     int inLen = this._inputEnd;
/* 2070 */     char[] inBuf = this._inputBuffer;
/*      */     
/*      */     while (true) {
/* 2073 */       if (inPtr >= inLen) {
/* 2074 */         this._inputPtr = inPtr;
/* 2075 */         if (!_loadMore()) {
/* 2076 */           _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
/*      */         }
/*      */         
/* 2079 */         inPtr = this._inputPtr;
/* 2080 */         inLen = this._inputEnd;
/*      */       } 
/* 2082 */       char c = inBuf[inPtr++];
/* 2083 */       int i = c;
/* 2084 */       if (i <= 92) {
/* 2085 */         if (i == 92) {
/*      */ 
/*      */           
/* 2088 */           this._inputPtr = inPtr;
/* 2089 */           _decodeEscaped();
/* 2090 */           inPtr = this._inputPtr;
/* 2091 */           inLen = this._inputEnd; continue;
/* 2092 */         }  if (i <= 34) {
/* 2093 */           if (i == 34) {
/* 2094 */             this._inputPtr = inPtr;
/*      */             break;
/*      */           } 
/* 2097 */           if (i < 32) {
/* 2098 */             this._inputPtr = inPtr;
/* 2099 */             _throwUnquotedSpace(i, "string value");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _skipCR() throws IOException {
/* 2117 */     if ((this._inputPtr < this._inputEnd || _loadMore()) && 
/* 2118 */       this._inputBuffer[this._inputPtr] == '\n') {
/* 2119 */       this._inputPtr++;
/*      */     }
/*      */     
/* 2122 */     this._currInputRow++;
/* 2123 */     this._currInputRowStart = this._inputPtr;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipColon() throws IOException {
/* 2128 */     if (this._inputPtr + 4 >= this._inputEnd) {
/* 2129 */       return _skipColon2(false);
/*      */     }
/* 2131 */     char c = this._inputBuffer[this._inputPtr];
/* 2132 */     if (c == ':') {
/* 2133 */       int i = this._inputBuffer[++this._inputPtr];
/* 2134 */       if (i > 32) {
/* 2135 */         if (i == 47 || i == 35) {
/* 2136 */           return _skipColon2(true);
/*      */         }
/* 2138 */         this._inputPtr++;
/* 2139 */         return i;
/*      */       } 
/* 2141 */       if (i == 32 || i == 9) {
/* 2142 */         i = this._inputBuffer[++this._inputPtr];
/* 2143 */         if (i > 32) {
/* 2144 */           if (i == 47 || i == 35) {
/* 2145 */             return _skipColon2(true);
/*      */           }
/* 2147 */           this._inputPtr++;
/* 2148 */           return i;
/*      */         } 
/*      */       } 
/* 2151 */       return _skipColon2(true);
/*      */     } 
/* 2153 */     if (c == ' ' || c == '\t') {
/* 2154 */       c = this._inputBuffer[++this._inputPtr];
/*      */     }
/* 2156 */     if (c == ':') {
/* 2157 */       int i = this._inputBuffer[++this._inputPtr];
/* 2158 */       if (i > 32) {
/* 2159 */         if (i == 47 || i == 35) {
/* 2160 */           return _skipColon2(true);
/*      */         }
/* 2162 */         this._inputPtr++;
/* 2163 */         return i;
/*      */       } 
/* 2165 */       if (i == 32 || i == 9) {
/* 2166 */         i = this._inputBuffer[++this._inputPtr];
/* 2167 */         if (i > 32) {
/* 2168 */           if (i == 47 || i == 35) {
/* 2169 */             return _skipColon2(true);
/*      */           }
/* 2171 */           this._inputPtr++;
/* 2172 */           return i;
/*      */         } 
/*      */       } 
/* 2175 */       return _skipColon2(true);
/*      */     } 
/* 2177 */     return _skipColon2(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipColon2(boolean gotColon) throws IOException {
/* 2182 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2183 */       int i = this._inputBuffer[this._inputPtr++];
/* 2184 */       if (i > 32) {
/* 2185 */         if (i == 47) {
/* 2186 */           _skipComment();
/*      */           continue;
/*      */         } 
/* 2189 */         if (i == 35 && 
/* 2190 */           _skipYAMLComment()) {
/*      */           continue;
/*      */         }
/*      */         
/* 2194 */         if (gotColon) {
/* 2195 */           return i;
/*      */         }
/* 2197 */         if (i != 58) {
/* 2198 */           _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
/*      */         }
/* 2200 */         gotColon = true;
/*      */         continue;
/*      */       } 
/* 2203 */       if (i < 32) {
/* 2204 */         if (i == 10) {
/* 2205 */           this._currInputRow++;
/* 2206 */           this._currInputRowStart = this._inputPtr; continue;
/* 2207 */         }  if (i == 13) {
/* 2208 */           _skipCR(); continue;
/* 2209 */         }  if (i != 9) {
/* 2210 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2214 */     _reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
/*      */     
/* 2216 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _skipColonFast(int ptr) throws IOException {
/* 2222 */     int i = this._inputBuffer[ptr++];
/* 2223 */     if (i == 58) {
/* 2224 */       i = this._inputBuffer[ptr++];
/* 2225 */       if (i > 32) {
/* 2226 */         if (i != 47 && i != 35) {
/* 2227 */           this._inputPtr = ptr;
/* 2228 */           return i;
/*      */         } 
/* 2230 */       } else if (i == 32 || i == 9) {
/* 2231 */         i = this._inputBuffer[ptr++];
/* 2232 */         if (i > 32 && 
/* 2233 */           i != 47 && i != 35) {
/* 2234 */           this._inputPtr = ptr;
/* 2235 */           return i;
/*      */         } 
/*      */       } 
/*      */       
/* 2239 */       this._inputPtr = ptr - 1;
/* 2240 */       return _skipColon2(true);
/*      */     } 
/* 2242 */     if (i == 32 || i == 9) {
/* 2243 */       i = this._inputBuffer[ptr++];
/*      */     }
/* 2245 */     boolean gotColon = (i == 58);
/* 2246 */     if (gotColon) {
/* 2247 */       i = this._inputBuffer[ptr++];
/* 2248 */       if (i > 32) {
/* 2249 */         if (i != 47 && i != 35) {
/* 2250 */           this._inputPtr = ptr;
/* 2251 */           return i;
/*      */         } 
/* 2253 */       } else if (i == 32 || i == 9) {
/* 2254 */         i = this._inputBuffer[ptr++];
/* 2255 */         if (i > 32 && 
/* 2256 */           i != 47 && i != 35) {
/* 2257 */           this._inputPtr = ptr;
/* 2258 */           return i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2263 */     this._inputPtr = ptr - 1;
/* 2264 */     return _skipColon2(gotColon);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _skipComma(int i) throws IOException {
/* 2270 */     if (i != 44) {
/* 2271 */       _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */     }
/* 2273 */     while (this._inputPtr < this._inputEnd) {
/* 2274 */       i = this._inputBuffer[this._inputPtr++];
/* 2275 */       if (i > 32) {
/* 2276 */         if (i == 47 || i == 35) {
/* 2277 */           this._inputPtr--;
/* 2278 */           return _skipAfterComma2();
/*      */         } 
/* 2280 */         return i;
/*      */       } 
/* 2282 */       if (i < 32) {
/* 2283 */         if (i == 10) {
/* 2284 */           this._currInputRow++;
/* 2285 */           this._currInputRowStart = this._inputPtr; continue;
/* 2286 */         }  if (i == 13) {
/* 2287 */           _skipCR(); continue;
/* 2288 */         }  if (i != 9) {
/* 2289 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2293 */     return _skipAfterComma2();
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipAfterComma2() throws IOException {
/* 2298 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2299 */       int i = this._inputBuffer[this._inputPtr++];
/* 2300 */       if (i > 32) {
/* 2301 */         if (i == 47) {
/* 2302 */           _skipComment();
/*      */           continue;
/*      */         } 
/* 2305 */         if (i == 35 && 
/* 2306 */           _skipYAMLComment()) {
/*      */           continue;
/*      */         }
/*      */         
/* 2310 */         return i;
/*      */       } 
/* 2312 */       if (i < 32) {
/* 2313 */         if (i == 10) {
/* 2314 */           this._currInputRow++;
/* 2315 */           this._currInputRowStart = this._inputPtr; continue;
/* 2316 */         }  if (i == 13) {
/* 2317 */           _skipCR(); continue;
/* 2318 */         }  if (i != 9) {
/* 2319 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2323 */     throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _skipWSOrEnd() throws IOException {
/* 2330 */     if (this._inputPtr >= this._inputEnd && 
/* 2331 */       !_loadMore()) {
/* 2332 */       return _eofAsNextChar();
/*      */     }
/*      */     
/* 2335 */     int i = this._inputBuffer[this._inputPtr++];
/* 2336 */     if (i > 32) {
/* 2337 */       if (i == 47 || i == 35) {
/* 2338 */         this._inputPtr--;
/* 2339 */         return _skipWSOrEnd2();
/*      */       } 
/* 2341 */       return i;
/*      */     } 
/* 2343 */     if (i != 32) {
/* 2344 */       if (i == 10) {
/* 2345 */         this._currInputRow++;
/* 2346 */         this._currInputRowStart = this._inputPtr;
/* 2347 */       } else if (i == 13) {
/* 2348 */         _skipCR();
/* 2349 */       } else if (i != 9) {
/* 2350 */         _throwInvalidSpace(i);
/*      */       } 
/*      */     }
/*      */     
/* 2354 */     while (this._inputPtr < this._inputEnd) {
/* 2355 */       i = this._inputBuffer[this._inputPtr++];
/* 2356 */       if (i > 32) {
/* 2357 */         if (i == 47 || i == 35) {
/* 2358 */           this._inputPtr--;
/* 2359 */           return _skipWSOrEnd2();
/*      */         } 
/* 2361 */         return i;
/*      */       } 
/* 2363 */       if (i != 32) {
/* 2364 */         if (i == 10) {
/* 2365 */           this._currInputRow++;
/* 2366 */           this._currInputRowStart = this._inputPtr; continue;
/* 2367 */         }  if (i == 13) {
/* 2368 */           _skipCR(); continue;
/* 2369 */         }  if (i != 9) {
/* 2370 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2374 */     return _skipWSOrEnd2();
/*      */   }
/*      */ 
/*      */   
/*      */   private int _skipWSOrEnd2() throws IOException {
/*      */     while (true) {
/* 2380 */       if (this._inputPtr >= this._inputEnd && 
/* 2381 */         !_loadMore()) {
/* 2382 */         return _eofAsNextChar();
/*      */       }
/*      */       
/* 2385 */       int i = this._inputBuffer[this._inputPtr++];
/* 2386 */       if (i > 32) {
/* 2387 */         if (i == 47) {
/* 2388 */           _skipComment();
/*      */           continue;
/*      */         } 
/* 2391 */         if (i == 35 && 
/* 2392 */           _skipYAMLComment()) {
/*      */           continue;
/*      */         }
/*      */         
/* 2396 */         return i;
/* 2397 */       }  if (i != 32) {
/* 2398 */         if (i == 10) {
/* 2399 */           this._currInputRow++;
/* 2400 */           this._currInputRowStart = this._inputPtr; continue;
/* 2401 */         }  if (i == 13) {
/* 2402 */           _skipCR(); continue;
/* 2403 */         }  if (i != 9) {
/* 2404 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void _skipComment() throws IOException {
/* 2412 */     if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 2413 */       _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
/*      */     }
/*      */     
/* 2416 */     if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 2417 */       _reportInvalidEOF(" in a comment", null);
/*      */     }
/* 2419 */     char c = this._inputBuffer[this._inputPtr++];
/* 2420 */     if (c == '/') {
/* 2421 */       _skipLine();
/* 2422 */     } else if (c == '*') {
/* 2423 */       _skipCComment();
/*      */     } else {
/* 2425 */       _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void _skipCComment() throws IOException {
/* 2432 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2433 */       int i = this._inputBuffer[this._inputPtr++];
/* 2434 */       if (i <= 42) {
/* 2435 */         if (i == 42) {
/* 2436 */           if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/*      */             break;
/*      */           }
/* 2439 */           if (this._inputBuffer[this._inputPtr] == '/') {
/* 2440 */             this._inputPtr++;
/*      */             return;
/*      */           } 
/*      */           continue;
/*      */         } 
/* 2445 */         if (i < 32) {
/* 2446 */           if (i == 10) {
/* 2447 */             this._currInputRow++;
/* 2448 */             this._currInputRowStart = this._inputPtr; continue;
/* 2449 */           }  if (i == 13) {
/* 2450 */             _skipCR(); continue;
/* 2451 */           }  if (i != 9) {
/* 2452 */             _throwInvalidSpace(i);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 2457 */     _reportInvalidEOF(" in a comment", null);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean _skipYAMLComment() throws IOException {
/* 2462 */     if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
/* 2463 */       return false;
/*      */     }
/* 2465 */     _skipLine();
/* 2466 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void _skipLine() throws IOException {
/* 2472 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2473 */       int i = this._inputBuffer[this._inputPtr++];
/* 2474 */       if (i < 32) {
/* 2475 */         if (i == 10) {
/* 2476 */           this._currInputRow++;
/* 2477 */           this._currInputRowStart = this._inputPtr; break;
/*      */         } 
/* 2479 */         if (i == 13) {
/* 2480 */           _skipCR(); break;
/*      */         } 
/* 2482 */         if (i != 9) {
/* 2483 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected char _decodeEscaped() throws IOException {
/* 2492 */     if (this._inputPtr >= this._inputEnd && 
/* 2493 */       !_loadMore()) {
/* 2494 */       _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
/*      */     }
/*      */     
/* 2497 */     char c = this._inputBuffer[this._inputPtr++];
/*      */     
/* 2499 */     switch (c) {
/*      */       
/*      */       case 'b':
/* 2502 */         return '\b';
/*      */       case 't':
/* 2504 */         return '\t';
/*      */       case 'n':
/* 2506 */         return '\n';
/*      */       case 'f':
/* 2508 */         return '\f';
/*      */       case 'r':
/* 2510 */         return '\r';
/*      */ 
/*      */       
/*      */       case '"':
/*      */       case '/':
/*      */       case '\\':
/* 2516 */         return c;
/*      */       
/*      */       case 'u':
/*      */         break;
/*      */       
/*      */       default:
/* 2522 */         return _handleUnrecognizedCharacterEscape(c);
/*      */     } 
/*      */ 
/*      */     
/* 2526 */     int value = 0;
/* 2527 */     for (int i = 0; i < 4; i++) {
/* 2528 */       if (this._inputPtr >= this._inputEnd && 
/* 2529 */         !_loadMore()) {
/* 2530 */         _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
/*      */       }
/*      */       
/* 2533 */       int ch = this._inputBuffer[this._inputPtr++];
/* 2534 */       int digit = CharTypes.charToHex(ch);
/* 2535 */       if (digit < 0) {
/* 2536 */         _reportUnexpectedChar(ch, "expected a hex-digit for character escape sequence");
/*      */       }
/* 2538 */       value = value << 4 | digit;
/*      */     } 
/* 2540 */     return (char)value;
/*      */   }
/*      */   
/*      */   private final void _matchTrue() throws IOException {
/* 2544 */     int ptr = this._inputPtr;
/* 2545 */     if (ptr + 3 < this._inputEnd) {
/* 2546 */       char[] b = this._inputBuffer;
/* 2547 */       if (b[ptr] == 'r' && b[++ptr] == 'u' && b[++ptr] == 'e') {
/* 2548 */         char c = b[++ptr];
/* 2549 */         if (c < '0' || c == ']' || c == '}') {
/* 2550 */           this._inputPtr = ptr;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2556 */     _matchToken("true", 1);
/*      */   }
/*      */   
/*      */   private final void _matchFalse() throws IOException {
/* 2560 */     int ptr = this._inputPtr;
/* 2561 */     if (ptr + 4 < this._inputEnd) {
/* 2562 */       char[] b = this._inputBuffer;
/* 2563 */       if (b[ptr] == 'a' && b[++ptr] == 'l' && b[++ptr] == 's' && b[++ptr] == 'e') {
/* 2564 */         char c = b[++ptr];
/* 2565 */         if (c < '0' || c == ']' || c == '}') {
/* 2566 */           this._inputPtr = ptr;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2572 */     _matchToken("false", 1);
/*      */   }
/*      */   
/*      */   private final void _matchNull() throws IOException {
/* 2576 */     int ptr = this._inputPtr;
/* 2577 */     if (ptr + 3 < this._inputEnd) {
/* 2578 */       char[] b = this._inputBuffer;
/* 2579 */       if (b[ptr] == 'u' && b[++ptr] == 'l' && b[++ptr] == 'l') {
/* 2580 */         char c = b[++ptr];
/* 2581 */         if (c < '0' || c == ']' || c == '}') {
/* 2582 */           this._inputPtr = ptr;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2588 */     _matchToken("null", 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _matchToken(String matchStr, int i) throws IOException {
/* 2596 */     int len = matchStr.length();
/* 2597 */     if (this._inputPtr + len >= this._inputEnd) {
/* 2598 */       _matchToken2(matchStr, i);
/*      */       
/*      */       return;
/*      */     } 
/*      */     while (true) {
/* 2603 */       if (this._inputBuffer[this._inputPtr] != matchStr.charAt(i)) {
/* 2604 */         _reportInvalidToken(matchStr.substring(0, i));
/*      */       }
/* 2606 */       this._inputPtr++;
/* 2607 */       if (++i >= len) {
/* 2608 */         int ch = this._inputBuffer[this._inputPtr];
/* 2609 */         if (ch >= 48 && ch != 93 && ch != 125)
/* 2610 */           _checkMatchEnd(matchStr, i, ch); 
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private final void _matchToken2(String matchStr, int i) throws IOException {
/* 2616 */     int len = matchStr.length();
/*      */     do {
/* 2618 */       if ((this._inputPtr >= this._inputEnd && !_loadMore()) || this._inputBuffer[this._inputPtr] != matchStr.charAt(i))
/*      */       {
/* 2620 */         _reportInvalidToken(matchStr.substring(0, i));
/*      */       }
/* 2622 */       this._inputPtr++;
/* 2623 */     } while (++i < len);
/*      */ 
/*      */     
/* 2626 */     if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/*      */       return;
/*      */     }
/* 2629 */     int ch = this._inputBuffer[this._inputPtr];
/* 2630 */     if (ch >= 48 && ch != 93 && ch != 125) {
/* 2631 */       _checkMatchEnd(matchStr, i, ch);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _checkMatchEnd(String matchStr, int i, int c) throws IOException {
/* 2637 */     char ch = (char)c;
/* 2638 */     if (Character.isJavaIdentifierPart(ch)) {
/* 2639 */       _reportInvalidToken(matchStr.substring(0, i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] _decodeBase64(Base64Variant b64variant) throws IOException {
/* 2656 */     ByteArrayBuilder builder = _getByteArrayBuilder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2663 */       if (this._inputPtr >= this._inputEnd) {
/* 2664 */         _loadMoreGuaranteed();
/*      */       }
/* 2666 */       char ch = this._inputBuffer[this._inputPtr++];
/* 2667 */       if (ch > ' ') {
/* 2668 */         int bits = b64variant.decodeBase64Char(ch);
/* 2669 */         if (bits < 0) {
/* 2670 */           if (ch == '"') {
/* 2671 */             return builder.toByteArray();
/*      */           }
/* 2673 */           bits = _decodeBase64Escape(b64variant, ch, 0);
/* 2674 */           if (bits < 0) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 2678 */         int decodedData = bits;
/*      */ 
/*      */ 
/*      */         
/* 2682 */         if (this._inputPtr >= this._inputEnd) {
/* 2683 */           _loadMoreGuaranteed();
/*      */         }
/* 2685 */         ch = this._inputBuffer[this._inputPtr++];
/* 2686 */         bits = b64variant.decodeBase64Char(ch);
/* 2687 */         if (bits < 0) {
/* 2688 */           bits = _decodeBase64Escape(b64variant, ch, 1);
/*      */         }
/* 2690 */         decodedData = decodedData << 6 | bits;
/*      */ 
/*      */         
/* 2693 */         if (this._inputPtr >= this._inputEnd) {
/* 2694 */           _loadMoreGuaranteed();
/*      */         }
/* 2696 */         ch = this._inputBuffer[this._inputPtr++];
/* 2697 */         bits = b64variant.decodeBase64Char(ch);
/*      */ 
/*      */         
/* 2700 */         if (bits < 0) {
/* 2701 */           if (bits != -2) {
/*      */             
/* 2703 */             if (ch == '"' && !b64variant.usesPadding()) {
/* 2704 */               decodedData >>= 4;
/* 2705 */               builder.append(decodedData);
/* 2706 */               return builder.toByteArray();
/*      */             } 
/* 2708 */             bits = _decodeBase64Escape(b64variant, ch, 2);
/*      */           } 
/* 2710 */           if (bits == -2) {
/*      */             
/* 2712 */             if (this._inputPtr >= this._inputEnd) {
/* 2713 */               _loadMoreGuaranteed();
/*      */             }
/* 2715 */             ch = this._inputBuffer[this._inputPtr++];
/* 2716 */             if (!b64variant.usesPaddingChar(ch)) {
/* 2717 */               throw reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
/*      */             }
/*      */             
/* 2720 */             decodedData >>= 4;
/* 2721 */             builder.append(decodedData);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 2727 */         decodedData = decodedData << 6 | bits;
/*      */         
/* 2729 */         if (this._inputPtr >= this._inputEnd) {
/* 2730 */           _loadMoreGuaranteed();
/*      */         }
/* 2732 */         ch = this._inputBuffer[this._inputPtr++];
/* 2733 */         bits = b64variant.decodeBase64Char(ch);
/* 2734 */         if (bits < 0) {
/* 2735 */           if (bits != -2) {
/*      */             
/* 2737 */             if (ch == '"' && !b64variant.usesPadding()) {
/* 2738 */               decodedData >>= 2;
/* 2739 */               builder.appendTwoBytes(decodedData);
/* 2740 */               return builder.toByteArray();
/*      */             } 
/* 2742 */             bits = _decodeBase64Escape(b64variant, ch, 3);
/*      */           } 
/* 2744 */           if (bits == -2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2750 */             decodedData >>= 2;
/* 2751 */             builder.appendTwoBytes(decodedData);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 2757 */         decodedData = decodedData << 6 | bits;
/* 2758 */         builder.appendThreeBytes(decodedData);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonLocation getTokenLocation() {
/* 2771 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 2772 */       long total = this._currInputProcessed + this._nameStartOffset - 1L;
/* 2773 */       return new JsonLocation(_getSourceReference(), -1L, total, this._nameStartRow, this._nameStartCol);
/*      */     } 
/*      */     
/* 2776 */     return new JsonLocation(_getSourceReference(), -1L, this._tokenInputTotal - 1L, this._tokenInputRow, this._tokenInputCol);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonLocation getCurrentLocation() {
/* 2782 */     int col = this._inputPtr - this._currInputRowStart + 1;
/* 2783 */     return new JsonLocation(_getSourceReference(), -1L, this._currInputProcessed + this._inputPtr, this._currInputRow, col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _updateLocation() {
/* 2791 */     int ptr = this._inputPtr;
/* 2792 */     this._tokenInputTotal = this._currInputProcessed + ptr;
/* 2793 */     this._tokenInputRow = this._currInputRow;
/* 2794 */     this._tokenInputCol = ptr - this._currInputRowStart;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _updateNameLocation() {
/* 2800 */     int ptr = this._inputPtr;
/* 2801 */     this._nameStartOffset = ptr;
/* 2802 */     this._nameStartRow = this._currInputRow;
/* 2803 */     this._nameStartCol = ptr - this._currInputRowStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidToken(String matchedPart) throws IOException {
/* 2813 */     _reportInvalidToken(matchedPart, "'null', 'true', 'false' or NaN");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidToken(String matchedPart, String msg) throws IOException {
/* 2822 */     StringBuilder sb = new StringBuilder(matchedPart);
/* 2823 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2824 */       char c = this._inputBuffer[this._inputPtr];
/* 2825 */       if (!Character.isJavaIdentifierPart(c)) {
/*      */         break;
/*      */       }
/* 2828 */       this._inputPtr++;
/* 2829 */       sb.append(c);
/* 2830 */       if (sb.length() >= 256) {
/* 2831 */         sb.append("...");
/*      */         break;
/*      */       } 
/*      */     } 
/* 2835 */     _reportError("Unrecognized token '%s': was expecting %s", sb, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _closeScope(int i) throws JsonParseException {
/* 2845 */     if (i == 93) {
/* 2846 */       _updateLocation();
/* 2847 */       if (!this._parsingContext.inArray()) {
/* 2848 */         _reportMismatchedEndMarker(i, '}');
/*      */       }
/* 2850 */       this._parsingContext = this._parsingContext.clearAndGetParent();
/* 2851 */       this._currToken = JsonToken.END_ARRAY;
/*      */     } 
/* 2853 */     if (i == 125) {
/* 2854 */       _updateLocation();
/* 2855 */       if (!this._parsingContext.inObject()) {
/* 2856 */         _reportMismatchedEndMarker(i, ']');
/*      */       }
/* 2858 */       this._parsingContext = this._parsingContext.clearAndGetParent();
/* 2859 */       this._currToken = JsonToken.END_OBJECT;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\ReaderBasedJsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */