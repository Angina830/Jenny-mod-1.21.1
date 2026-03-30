/*      */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
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
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ 
/*      */ public class UTF8StreamJsonParser
/*      */   extends ParserBase
/*      */ {
/*      */   static final byte BYTE_LF = 10;
/*   24 */   private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
/*      */ 
/*      */ 
/*      */   
/*   28 */   protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
/*      */   
/*   30 */   protected static final int FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*      */   protected final ByteQuadsCanonicalizer _symbols;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   protected int[] _quadBuffer = new int[16];
/*      */ 
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
/*      */   private int _quad1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _nameStartOffset;
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
/*      */   protected InputStream _inputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] _inputBuffer;
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
/*      */   
/*      */   public UTF8StreamJsonParser(IOContext ctxt, int features, InputStream in, ObjectCodec codec, ByteQuadsCanonicalizer sym, byte[] inputBuffer, int start, int end, boolean bufferRecyclable) {
/*  134 */     super(ctxt, features);
/*  135 */     this._inputStream = in;
/*  136 */     this._objectCodec = codec;
/*  137 */     this._symbols = sym;
/*  138 */     this._inputBuffer = inputBuffer;
/*  139 */     this._inputPtr = start;
/*  140 */     this._inputEnd = end;
/*  141 */     this._currInputRowStart = start;
/*      */     
/*  143 */     this._currInputProcessed = -start;
/*  144 */     this._bufferRecyclable = bufferRecyclable;
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectCodec getCodec() {
/*  149 */     return this._objectCodec;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCodec(ObjectCodec c) {
/*  154 */     this._objectCodec = c;
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
/*      */   public int releaseBuffered(OutputStream out) throws IOException {
/*  166 */     int count = this._inputEnd - this._inputPtr;
/*  167 */     if (count < 1) {
/*  168 */       return 0;
/*      */     }
/*      */     
/*  171 */     int origPtr = this._inputPtr;
/*  172 */     out.write(this._inputBuffer, origPtr, count);
/*  173 */     return count;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getInputSource() {
/*  178 */     return this._inputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _loadMore() throws IOException {
/*  189 */     int bufSize = this._inputEnd;
/*      */     
/*  191 */     this._currInputProcessed += this._inputEnd;
/*  192 */     this._currInputRowStart -= this._inputEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  197 */     this._nameStartOffset -= bufSize;
/*      */     
/*  199 */     if (this._inputStream != null) {
/*  200 */       int space = this._inputBuffer.length;
/*  201 */       if (space == 0) {
/*  202 */         return false;
/*      */       }
/*      */       
/*  205 */       int count = this._inputStream.read(this._inputBuffer, 0, space);
/*  206 */       if (count > 0) {
/*  207 */         this._inputPtr = 0;
/*  208 */         this._inputEnd = count;
/*  209 */         return true;
/*      */       } 
/*      */       
/*  212 */       _closeInput();
/*      */       
/*  214 */       if (count == 0) {
/*  215 */         throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
/*      */       }
/*      */     } 
/*  218 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _closeInput() throws IOException {
/*  226 */     if (this._inputStream != null) {
/*  227 */       if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
/*  228 */         this._inputStream.close();
/*      */       }
/*  230 */       this._inputStream = null;
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
/*      */   protected void _releaseBuffers() throws IOException {
/*  243 */     super._releaseBuffers();
/*      */     
/*  245 */     this._symbols.release();
/*  246 */     if (this._bufferRecyclable) {
/*  247 */       byte[] buf = this._inputBuffer;
/*  248 */       if (buf != null) {
/*      */ 
/*      */         
/*  251 */         this._inputBuffer = NO_BYTES;
/*  252 */         this._ioContext.releaseReadIOBuffer(buf);
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
/*      */   public String getText() throws IOException {
/*  266 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  267 */       if (this._tokenIncomplete) {
/*  268 */         this._tokenIncomplete = false;
/*  269 */         return _finishAndReturnString();
/*      */       } 
/*  271 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  273 */     return _getText2(this._currToken);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getText(Writer writer) throws IOException {
/*  279 */     JsonToken t = this._currToken;
/*  280 */     if (t == JsonToken.VALUE_STRING) {
/*  281 */       if (this._tokenIncomplete) {
/*  282 */         this._tokenIncomplete = false;
/*  283 */         _finishString();
/*      */       } 
/*  285 */       return this._textBuffer.contentsToWriter(writer);
/*      */     } 
/*  287 */     if (t == JsonToken.FIELD_NAME) {
/*  288 */       String n = this._parsingContext.getCurrentName();
/*  289 */       writer.write(n);
/*  290 */       return n.length();
/*      */     } 
/*  292 */     if (t != null) {
/*  293 */       if (t.isNumeric()) {
/*  294 */         return this._textBuffer.contentsToWriter(writer);
/*      */       }
/*  296 */       char[] ch = t.asCharArray();
/*  297 */       writer.write(ch);
/*  298 */       return ch.length;
/*      */     } 
/*  300 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValueAsString() throws IOException {
/*  309 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  310 */       if (this._tokenIncomplete) {
/*  311 */         this._tokenIncomplete = false;
/*  312 */         return _finishAndReturnString();
/*      */       } 
/*  314 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  316 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  317 */       return getCurrentName();
/*      */     }
/*  319 */     return super.getValueAsString(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValueAsString(String defValue) throws IOException {
/*  326 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  327 */       if (this._tokenIncomplete) {
/*  328 */         this._tokenIncomplete = false;
/*  329 */         return _finishAndReturnString();
/*      */       } 
/*  331 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  333 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  334 */       return getCurrentName();
/*      */     }
/*  336 */     return super.getValueAsString(defValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getValueAsInt() throws IOException {
/*  343 */     JsonToken t = this._currToken;
/*  344 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/*      */       
/*  346 */       if ((this._numTypesValid & 0x1) == 0) {
/*  347 */         if (this._numTypesValid == 0) {
/*  348 */           return _parseIntValue();
/*      */         }
/*  350 */         if ((this._numTypesValid & 0x1) == 0) {
/*  351 */           convertNumberToInt();
/*      */         }
/*      */       } 
/*  354 */       return this._numberInt;
/*      */     } 
/*  356 */     return super.getValueAsInt(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getValueAsInt(int defValue) throws IOException {
/*  363 */     JsonToken t = this._currToken;
/*  364 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/*      */       
/*  366 */       if ((this._numTypesValid & 0x1) == 0) {
/*  367 */         if (this._numTypesValid == 0) {
/*  368 */           return _parseIntValue();
/*      */         }
/*  370 */         if ((this._numTypesValid & 0x1) == 0) {
/*  371 */           convertNumberToInt();
/*      */         }
/*      */       } 
/*  374 */       return this._numberInt;
/*      */     } 
/*  376 */     return super.getValueAsInt(defValue);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final String _getText2(JsonToken t) {
/*  381 */     if (t == null) {
/*  382 */       return null;
/*      */     }
/*  384 */     switch (t.id()) {
/*      */       case 5:
/*  386 */         return this._parsingContext.getCurrentName();
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*  392 */         return this._textBuffer.contentsAsString();
/*      */     } 
/*  394 */     return t.asString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getTextCharacters() throws IOException {
/*  401 */     if (this._currToken != null) {
/*  402 */       switch (this._currToken.id()) {
/*      */         
/*      */         case 5:
/*  405 */           if (!this._nameCopied) {
/*  406 */             String name = this._parsingContext.getCurrentName();
/*  407 */             int nameLen = name.length();
/*  408 */             if (this._nameCopyBuffer == null) {
/*  409 */               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(nameLen);
/*  410 */             } else if (this._nameCopyBuffer.length < nameLen) {
/*  411 */               this._nameCopyBuffer = new char[nameLen];
/*      */             } 
/*  413 */             name.getChars(0, nameLen, this._nameCopyBuffer, 0);
/*  414 */             this._nameCopied = true;
/*      */           } 
/*  416 */           return this._nameCopyBuffer;
/*      */         
/*      */         case 6:
/*  419 */           if (this._tokenIncomplete) {
/*  420 */             this._tokenIncomplete = false;
/*  421 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  426 */           return this._textBuffer.getTextBuffer();
/*      */       } 
/*      */       
/*  429 */       return this._currToken.asCharArray();
/*      */     } 
/*      */     
/*  432 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextLength() throws IOException {
/*  438 */     if (this._currToken != null) {
/*  439 */       switch (this._currToken.id()) {
/*      */         
/*      */         case 5:
/*  442 */           return this._parsingContext.getCurrentName().length();
/*      */         case 6:
/*  444 */           if (this._tokenIncomplete) {
/*  445 */             this._tokenIncomplete = false;
/*  446 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  451 */           return this._textBuffer.size();
/*      */       } 
/*      */       
/*  454 */       return (this._currToken.asCharArray()).length;
/*      */     } 
/*      */     
/*  457 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextOffset() throws IOException {
/*  464 */     if (this._currToken != null) {
/*  465 */       switch (this._currToken.id()) {
/*      */         case 5:
/*  467 */           return 0;
/*      */         case 6:
/*  469 */           if (this._tokenIncomplete) {
/*  470 */             this._tokenIncomplete = false;
/*  471 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  476 */           return this._textBuffer.getTextOffset();
/*      */       } 
/*      */     
/*      */     }
/*  480 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
/*  486 */     if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null))
/*      */     {
/*  488 */       _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  493 */     if (this._tokenIncomplete) {
/*      */       try {
/*  495 */         this._binaryValue = _decodeBase64(b64variant);
/*  496 */       } catch (IllegalArgumentException iae) {
/*  497 */         throw _constructError("Failed to decode VALUE_STRING as base64 (" + b64variant + "): " + iae.getMessage());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  502 */       this._tokenIncomplete = false;
/*      */     }
/*  504 */     else if (this._binaryValue == null) {
/*      */       
/*  506 */       ByteArrayBuilder builder = _getByteArrayBuilder();
/*  507 */       _decodeBase64(getText(), builder, b64variant);
/*  508 */       this._binaryValue = builder.toByteArray();
/*      */     } 
/*      */     
/*  511 */     return this._binaryValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
/*  518 */     if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
/*  519 */       byte[] b = getBinaryValue(b64variant);
/*  520 */       out.write(b);
/*  521 */       return b.length;
/*      */     } 
/*      */     
/*  524 */     byte[] buf = this._ioContext.allocBase64Buffer();
/*      */     try {
/*  526 */       return _readBinary(b64variant, out, buf);
/*      */     } finally {
/*  528 */       this._ioContext.releaseBase64Buffer(buf);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _readBinary(Base64Variant b64variant, OutputStream out, byte[] buffer) throws IOException {
/*  535 */     int outputPtr = 0;
/*  536 */     int outputEnd = buffer.length - 3;
/*  537 */     int outputCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  543 */       if (this._inputPtr >= this._inputEnd) {
/*  544 */         _loadMoreGuaranteed();
/*      */       }
/*  546 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  547 */       if (ch > 32) {
/*  548 */         int bits = b64variant.decodeBase64Char(ch);
/*  549 */         if (bits < 0) {
/*  550 */           if (ch == 34) {
/*      */             break;
/*      */           }
/*  553 */           bits = _decodeBase64Escape(b64variant, ch, 0);
/*  554 */           if (bits < 0) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  560 */         if (outputPtr > outputEnd) {
/*  561 */           outputCount += outputPtr;
/*  562 */           out.write(buffer, 0, outputPtr);
/*  563 */           outputPtr = 0;
/*      */         } 
/*      */         
/*  566 */         int decodedData = bits;
/*      */ 
/*      */ 
/*      */         
/*  570 */         if (this._inputPtr >= this._inputEnd) {
/*  571 */           _loadMoreGuaranteed();
/*      */         }
/*  573 */         ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  574 */         bits = b64variant.decodeBase64Char(ch);
/*  575 */         if (bits < 0) {
/*  576 */           bits = _decodeBase64Escape(b64variant, ch, 1);
/*      */         }
/*  578 */         decodedData = decodedData << 6 | bits;
/*      */ 
/*      */         
/*  581 */         if (this._inputPtr >= this._inputEnd) {
/*  582 */           _loadMoreGuaranteed();
/*      */         }
/*  584 */         ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  585 */         bits = b64variant.decodeBase64Char(ch);
/*      */ 
/*      */         
/*  588 */         if (bits < 0) {
/*  589 */           if (bits != -2) {
/*      */             
/*  591 */             if (ch == 34 && !b64variant.usesPadding()) {
/*  592 */               decodedData >>= 4;
/*  593 */               buffer[outputPtr++] = (byte)decodedData;
/*      */               break;
/*      */             } 
/*  596 */             bits = _decodeBase64Escape(b64variant, ch, 2);
/*      */           } 
/*  598 */           if (bits == -2) {
/*      */             
/*  600 */             if (this._inputPtr >= this._inputEnd) {
/*  601 */               _loadMoreGuaranteed();
/*      */             }
/*  603 */             ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  604 */             if (!b64variant.usesPaddingChar(ch)) {
/*  605 */               throw reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
/*      */             }
/*      */             
/*  608 */             decodedData >>= 4;
/*  609 */             buffer[outputPtr++] = (byte)decodedData;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  614 */         decodedData = decodedData << 6 | bits;
/*      */         
/*  616 */         if (this._inputPtr >= this._inputEnd) {
/*  617 */           _loadMoreGuaranteed();
/*      */         }
/*  619 */         ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  620 */         bits = b64variant.decodeBase64Char(ch);
/*  621 */         if (bits < 0) {
/*  622 */           if (bits != -2) {
/*      */             
/*  624 */             if (ch == 34 && !b64variant.usesPadding()) {
/*  625 */               decodedData >>= 2;
/*  626 */               buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  627 */               buffer[outputPtr++] = (byte)decodedData;
/*      */               break;
/*      */             } 
/*  630 */             bits = _decodeBase64Escape(b64variant, ch, 3);
/*      */           } 
/*  632 */           if (bits == -2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  639 */             decodedData >>= 2;
/*  640 */             buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  641 */             buffer[outputPtr++] = (byte)decodedData;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  646 */         decodedData = decodedData << 6 | bits;
/*  647 */         buffer[outputPtr++] = (byte)(decodedData >> 16);
/*  648 */         buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  649 */         buffer[outputPtr++] = (byte)decodedData;
/*      */       } 
/*  651 */     }  this._tokenIncomplete = false;
/*  652 */     if (outputPtr > 0) {
/*  653 */       outputCount += outputPtr;
/*  654 */       out.write(buffer, 0, outputPtr);
/*      */     } 
/*  656 */     return outputCount;
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
/*      */   public JsonToken nextToken() throws IOException {
/*  676 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  677 */       return _nextAfterName();
/*      */     }
/*      */ 
/*      */     
/*  681 */     this._numTypesValid = 0;
/*  682 */     if (this._tokenIncomplete) {
/*  683 */       _skipString();
/*      */     }
/*  685 */     int i = _skipWSOrEnd();
/*  686 */     if (i < 0) {
/*      */       
/*  688 */       close();
/*  689 */       return this._currToken = null;
/*      */     } 
/*      */     
/*  692 */     this._binaryValue = null;
/*      */ 
/*      */     
/*  695 */     if (i == 93) {
/*  696 */       _closeArrayScope();
/*  697 */       return this._currToken = JsonToken.END_ARRAY;
/*      */     } 
/*  699 */     if (i == 125) {
/*  700 */       _closeObjectScope();
/*  701 */       return this._currToken = JsonToken.END_OBJECT;
/*      */     } 
/*      */ 
/*      */     
/*  705 */     if (this._parsingContext.expectComma()) {
/*  706 */       if (i != 44) {
/*  707 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */       }
/*  709 */       i = _skipWS();
/*      */       
/*  711 */       if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (
/*  712 */         i == 93 || i == 125)) {
/*  713 */         return _closeScope(i);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  721 */     if (!this._parsingContext.inObject()) {
/*  722 */       _updateLocation();
/*  723 */       return _nextTokenNotInObject(i);
/*      */     } 
/*      */     
/*  726 */     _updateNameLocation();
/*  727 */     String n = _parseName(i);
/*  728 */     this._parsingContext.setCurrentName(n);
/*  729 */     this._currToken = JsonToken.FIELD_NAME;
/*      */     
/*  731 */     i = _skipColon();
/*  732 */     _updateLocation();
/*      */ 
/*      */     
/*  735 */     if (i == 34) {
/*  736 */       this._tokenIncomplete = true;
/*  737 */       this._nextToken = JsonToken.VALUE_STRING;
/*  738 */       return this._currToken;
/*      */     } 
/*      */ 
/*      */     
/*  742 */     switch (i)
/*      */     { case 45:
/*  744 */         t = _parseNegNumber();
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
/*      */ 
/*      */ 
/*      */         
/*  783 */         this._nextToken = t;
/*  784 */         return this._currToken;case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return this._currToken;case 102: _matchFalse(); t = JsonToken.VALUE_FALSE; this._nextToken = t; return this._currToken;case 110: _matchNull(); t = JsonToken.VALUE_NULL; this._nextToken = t; return this._currToken;case 116: _matchTrue(); t = JsonToken.VALUE_TRUE; this._nextToken = t; return this._currToken;case 91: t = JsonToken.START_ARRAY; this._nextToken = t; return this._currToken;case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return this._currToken; }  JsonToken t = _handleUnexpectedValue(i); this._nextToken = t; return this._currToken;
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _nextTokenNotInObject(int i) throws IOException {
/*  789 */     if (i == 34) {
/*  790 */       this._tokenIncomplete = true;
/*  791 */       return this._currToken = JsonToken.VALUE_STRING;
/*      */     } 
/*  793 */     switch (i) {
/*      */       case 91:
/*  795 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  796 */         return this._currToken = JsonToken.START_ARRAY;
/*      */       case 123:
/*  798 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*  799 */         return this._currToken = JsonToken.START_OBJECT;
/*      */       case 116:
/*  801 */         _matchTrue();
/*  802 */         return this._currToken = JsonToken.VALUE_TRUE;
/*      */       case 102:
/*  804 */         _matchFalse();
/*  805 */         return this._currToken = JsonToken.VALUE_FALSE;
/*      */       case 110:
/*  807 */         _matchNull();
/*  808 */         return this._currToken = JsonToken.VALUE_NULL;
/*      */       case 45:
/*  810 */         return this._currToken = _parseNegNumber();
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
/*  824 */         return this._currToken = _parsePosNumber(i);
/*      */     } 
/*  826 */     return this._currToken = _handleUnexpectedValue(i);
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _nextAfterName() {
/*  831 */     this._nameCopied = false;
/*  832 */     JsonToken t = this._nextToken;
/*  833 */     this._nextToken = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     if (t == JsonToken.START_ARRAY) {
/*  839 */       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  840 */     } else if (t == JsonToken.START_OBJECT) {
/*  841 */       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */     } 
/*  843 */     return this._currToken = t;
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishToken() throws IOException {
/*  848 */     if (this._tokenIncomplete) {
/*  849 */       this._tokenIncomplete = false;
/*  850 */       _finishString();
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
/*      */   public boolean nextFieldName(SerializableString str) throws IOException {
/*  864 */     this._numTypesValid = 0;
/*  865 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  866 */       _nextAfterName();
/*  867 */       return false;
/*      */     } 
/*  869 */     if (this._tokenIncomplete) {
/*  870 */       _skipString();
/*      */     }
/*  872 */     int i = _skipWSOrEnd();
/*  873 */     if (i < 0) {
/*  874 */       close();
/*  875 */       this._currToken = null;
/*  876 */       return false;
/*      */     } 
/*  878 */     this._binaryValue = null;
/*      */ 
/*      */     
/*  881 */     if (i == 93) {
/*  882 */       _closeArrayScope();
/*  883 */       this._currToken = JsonToken.END_ARRAY;
/*  884 */       return false;
/*      */     } 
/*  886 */     if (i == 125) {
/*  887 */       _closeObjectScope();
/*  888 */       this._currToken = JsonToken.END_OBJECT;
/*  889 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  893 */     if (this._parsingContext.expectComma()) {
/*  894 */       if (i != 44) {
/*  895 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */       }
/*  897 */       i = _skipWS();
/*      */ 
/*      */       
/*  900 */       if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (
/*  901 */         i == 93 || i == 125)) {
/*  902 */         _closeScope(i);
/*  903 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  907 */     if (!this._parsingContext.inObject()) {
/*  908 */       _updateLocation();
/*  909 */       _nextTokenNotInObject(i);
/*  910 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  914 */     _updateNameLocation();
/*  915 */     if (i == 34) {
/*      */       
/*  917 */       byte[] nameBytes = str.asQuotedUTF8();
/*  918 */       int len = nameBytes.length;
/*      */ 
/*      */       
/*  921 */       if (this._inputPtr + len + 4 < this._inputEnd) {
/*      */         
/*  923 */         int end = this._inputPtr + len;
/*  924 */         if (this._inputBuffer[end] == 34) {
/*  925 */           int offset = 0;
/*  926 */           int ptr = this._inputPtr;
/*      */           while (true) {
/*  928 */             if (ptr == end) {
/*  929 */               this._parsingContext.setCurrentName(str.getValue());
/*  930 */               i = _skipColonFast(ptr + 1);
/*  931 */               _isNextTokenNameYes(i);
/*  932 */               return true;
/*      */             } 
/*  934 */             if (nameBytes[offset] != this._inputBuffer[ptr]) {
/*      */               break;
/*      */             }
/*  937 */             offset++;
/*  938 */             ptr++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  943 */     return _isNextTokenNameMaybe(i, str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextFieldName() throws IOException {
/*  951 */     this._numTypesValid = 0;
/*  952 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  953 */       _nextAfterName();
/*  954 */       return null;
/*      */     } 
/*  956 */     if (this._tokenIncomplete) {
/*  957 */       _skipString();
/*      */     }
/*  959 */     int i = _skipWSOrEnd();
/*  960 */     if (i < 0) {
/*  961 */       close();
/*  962 */       this._currToken = null;
/*  963 */       return null;
/*      */     } 
/*  965 */     this._binaryValue = null;
/*      */     
/*  967 */     if (i == 93) {
/*  968 */       _closeArrayScope();
/*  969 */       this._currToken = JsonToken.END_ARRAY;
/*  970 */       return null;
/*      */     } 
/*  972 */     if (i == 125) {
/*  973 */       _closeObjectScope();
/*  974 */       this._currToken = JsonToken.END_OBJECT;
/*  975 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  979 */     if (this._parsingContext.expectComma()) {
/*  980 */       if (i != 44) {
/*  981 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */       }
/*  983 */       i = _skipWS();
/*      */       
/*  985 */       if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (
/*  986 */         i == 93 || i == 125)) {
/*  987 */         _closeScope(i);
/*  988 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  993 */     if (!this._parsingContext.inObject()) {
/*  994 */       _updateLocation();
/*  995 */       _nextTokenNotInObject(i);
/*  996 */       return null;
/*      */     } 
/*      */     
/*  999 */     _updateNameLocation();
/* 1000 */     String nameStr = _parseName(i);
/* 1001 */     this._parsingContext.setCurrentName(nameStr);
/* 1002 */     this._currToken = JsonToken.FIELD_NAME;
/*      */     
/* 1004 */     i = _skipColon();
/* 1005 */     _updateLocation();
/* 1006 */     if (i == 34) {
/* 1007 */       this._tokenIncomplete = true;
/* 1008 */       this._nextToken = JsonToken.VALUE_STRING;
/* 1009 */       return nameStr;
/*      */     } 
/*      */     
/* 1012 */     switch (i)
/*      */     { case 45:
/* 1014 */         t = _parseNegNumber();
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
/* 1050 */         this._nextToken = t;
/* 1051 */         return nameStr;case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return nameStr;case 102: _matchFalse(); t = JsonToken.VALUE_FALSE; this._nextToken = t; return nameStr;case 110: _matchNull(); t = JsonToken.VALUE_NULL; this._nextToken = t; return nameStr;case 116: _matchTrue(); t = JsonToken.VALUE_TRUE; this._nextToken = t; return nameStr;case 91: t = JsonToken.START_ARRAY; this._nextToken = t; return nameStr;case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return nameStr; }  JsonToken t = _handleUnexpectedValue(i); this._nextToken = t; return nameStr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _skipColonFast(int ptr) throws IOException {
/* 1057 */     int i = this._inputBuffer[ptr++];
/* 1058 */     if (i == 58) {
/* 1059 */       i = this._inputBuffer[ptr++];
/* 1060 */       if (i > 32) {
/* 1061 */         if (i != 47 && i != 35) {
/* 1062 */           this._inputPtr = ptr;
/* 1063 */           return i;
/*      */         } 
/* 1065 */       } else if (i == 32 || i == 9) {
/* 1066 */         i = this._inputBuffer[ptr++];
/* 1067 */         if (i > 32 && 
/* 1068 */           i != 47 && i != 35) {
/* 1069 */           this._inputPtr = ptr;
/* 1070 */           return i;
/*      */         } 
/*      */       } 
/*      */       
/* 1074 */       this._inputPtr = ptr - 1;
/* 1075 */       return _skipColon2(true);
/*      */     } 
/* 1077 */     if (i == 32 || i == 9) {
/* 1078 */       i = this._inputBuffer[ptr++];
/*      */     }
/* 1080 */     if (i == 58) {
/* 1081 */       i = this._inputBuffer[ptr++];
/* 1082 */       if (i > 32) {
/* 1083 */         if (i != 47 && i != 35) {
/* 1084 */           this._inputPtr = ptr;
/* 1085 */           return i;
/*      */         } 
/* 1087 */       } else if (i == 32 || i == 9) {
/* 1088 */         i = this._inputBuffer[ptr++];
/* 1089 */         if (i > 32 && 
/* 1090 */           i != 47 && i != 35) {
/* 1091 */           this._inputPtr = ptr;
/* 1092 */           return i;
/*      */         } 
/*      */       } 
/*      */       
/* 1096 */       this._inputPtr = ptr - 1;
/* 1097 */       return _skipColon2(true);
/*      */     } 
/* 1099 */     this._inputPtr = ptr - 1;
/* 1100 */     return _skipColon2(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _isNextTokenNameYes(int i) throws IOException {
/* 1105 */     this._currToken = JsonToken.FIELD_NAME;
/* 1106 */     _updateLocation();
/*      */     
/* 1108 */     switch (i) {
/*      */       case 34:
/* 1110 */         this._tokenIncomplete = true;
/* 1111 */         this._nextToken = JsonToken.VALUE_STRING;
/*      */         return;
/*      */       case 91:
/* 1114 */         this._nextToken = JsonToken.START_ARRAY;
/*      */         return;
/*      */       case 123:
/* 1117 */         this._nextToken = JsonToken.START_OBJECT;
/*      */         return;
/*      */       case 116:
/* 1120 */         _matchTrue();
/* 1121 */         this._nextToken = JsonToken.VALUE_TRUE;
/*      */         return;
/*      */       case 102:
/* 1124 */         _matchFalse();
/* 1125 */         this._nextToken = JsonToken.VALUE_FALSE;
/*      */         return;
/*      */       case 110:
/* 1128 */         _matchNull();
/* 1129 */         this._nextToken = JsonToken.VALUE_NULL;
/*      */         return;
/*      */       case 45:
/* 1132 */         this._nextToken = _parseNegNumber();
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
/* 1144 */         this._nextToken = _parsePosNumber(i);
/*      */         return;
/*      */     } 
/* 1147 */     this._nextToken = _handleUnexpectedValue(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean _isNextTokenNameMaybe(int i, SerializableString str) throws IOException {
/* 1154 */     String n = _parseName(i);
/* 1155 */     this._parsingContext.setCurrentName(n);
/* 1156 */     boolean match = n.equals(str.getValue());
/* 1157 */     this._currToken = JsonToken.FIELD_NAME;
/* 1158 */     i = _skipColon();
/* 1159 */     _updateLocation();
/*      */ 
/*      */     
/* 1162 */     if (i == 34) {
/* 1163 */       this._tokenIncomplete = true;
/* 1164 */       this._nextToken = JsonToken.VALUE_STRING;
/* 1165 */       return match;
/*      */     } 
/*      */ 
/*      */     
/* 1169 */     switch (i)
/*      */     { case 91:
/* 1171 */         t = JsonToken.START_ARRAY;
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
/* 1206 */         this._nextToken = t;
/* 1207 */         return match;case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return match;case 116: _matchTrue(); t = JsonToken.VALUE_TRUE; this._nextToken = t; return match;case 102: _matchFalse(); t = JsonToken.VALUE_FALSE; this._nextToken = t; return match;case 110: _matchNull(); t = JsonToken.VALUE_NULL; this._nextToken = t; return match;case 45: t = _parseNegNumber(); this._nextToken = t; return match;case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return match; }  JsonToken t = _handleUnexpectedValue(i); this._nextToken = t; return match;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextTextValue() throws IOException {
/* 1214 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1215 */       this._nameCopied = false;
/* 1216 */       JsonToken t = this._nextToken;
/* 1217 */       this._nextToken = null;
/* 1218 */       this._currToken = t;
/* 1219 */       if (t == JsonToken.VALUE_STRING) {
/* 1220 */         if (this._tokenIncomplete) {
/* 1221 */           this._tokenIncomplete = false;
/* 1222 */           return _finishAndReturnString();
/*      */         } 
/* 1224 */         return this._textBuffer.contentsAsString();
/*      */       } 
/* 1226 */       if (t == JsonToken.START_ARRAY) {
/* 1227 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1228 */       } else if (t == JsonToken.START_OBJECT) {
/* 1229 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1231 */       return null;
/*      */     } 
/*      */     
/* 1234 */     return (nextToken() == JsonToken.VALUE_STRING) ? getText() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextIntValue(int defaultValue) throws IOException {
/* 1241 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1242 */       this._nameCopied = false;
/* 1243 */       JsonToken t = this._nextToken;
/* 1244 */       this._nextToken = null;
/* 1245 */       this._currToken = t;
/* 1246 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 1247 */         return getIntValue();
/*      */       }
/* 1249 */       if (t == JsonToken.START_ARRAY) {
/* 1250 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1251 */       } else if (t == JsonToken.START_OBJECT) {
/* 1252 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1254 */       return defaultValue;
/*      */     } 
/*      */     
/* 1257 */     return (nextToken() == JsonToken.VALUE_NUMBER_INT) ? getIntValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long nextLongValue(long defaultValue) throws IOException {
/* 1264 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1265 */       this._nameCopied = false;
/* 1266 */       JsonToken t = this._nextToken;
/* 1267 */       this._nextToken = null;
/* 1268 */       this._currToken = t;
/* 1269 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 1270 */         return getLongValue();
/*      */       }
/* 1272 */       if (t == JsonToken.START_ARRAY) {
/* 1273 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1274 */       } else if (t == JsonToken.START_OBJECT) {
/* 1275 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1277 */       return defaultValue;
/*      */     } 
/*      */     
/* 1280 */     return (nextToken() == JsonToken.VALUE_NUMBER_INT) ? getLongValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean nextBooleanValue() throws IOException {
/* 1287 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1288 */       this._nameCopied = false;
/* 1289 */       JsonToken jsonToken = this._nextToken;
/* 1290 */       this._nextToken = null;
/* 1291 */       this._currToken = jsonToken;
/* 1292 */       if (jsonToken == JsonToken.VALUE_TRUE) {
/* 1293 */         return Boolean.TRUE;
/*      */       }
/* 1295 */       if (jsonToken == JsonToken.VALUE_FALSE) {
/* 1296 */         return Boolean.FALSE;
/*      */       }
/* 1298 */       if (jsonToken == JsonToken.START_ARRAY) {
/* 1299 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1300 */       } else if (jsonToken == JsonToken.START_OBJECT) {
/* 1301 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/* 1303 */       return null;
/*      */     } 
/*      */     
/* 1306 */     JsonToken t = nextToken();
/* 1307 */     if (t == JsonToken.VALUE_TRUE) {
/* 1308 */       return Boolean.TRUE;
/*      */     }
/* 1310 */     if (t == JsonToken.VALUE_FALSE) {
/* 1311 */       return Boolean.FALSE;
/*      */     }
/* 1313 */     return null;
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
/*      */   protected JsonToken _parsePosNumber(int c) throws IOException {
/* 1339 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/*      */     
/* 1341 */     if (c == 48) {
/* 1342 */       c = _verifyNoLeadingZeroes();
/*      */     }
/*      */     
/* 1345 */     outBuf[0] = (char)c;
/* 1346 */     int intLen = 1;
/* 1347 */     int outPtr = 1;
/*      */ 
/*      */     
/* 1350 */     int end = Math.min(this._inputEnd, this._inputPtr + outBuf.length - 1);
/*      */     
/*      */     while (true) {
/* 1353 */       if (this._inputPtr >= end) {
/* 1354 */         return _parseNumber2(outBuf, outPtr, false, intLen);
/*      */       }
/* 1356 */       c = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1357 */       if (c < 48 || c > 57) {
/*      */         break;
/*      */       }
/* 1360 */       intLen++;
/* 1361 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 1363 */     if (c == 46 || c == 101 || c == 69) {
/* 1364 */       return _parseFloat(outBuf, outPtr, c, false, intLen);
/*      */     }
/* 1366 */     this._inputPtr--;
/* 1367 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1369 */     if (this._parsingContext.inRoot()) {
/* 1370 */       _verifyRootSpace(c);
/*      */     }
/*      */     
/* 1373 */     return resetInt(false, intLen);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _parseNegNumber() throws IOException {
/* 1378 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1379 */     int outPtr = 0;
/*      */ 
/*      */     
/* 1382 */     outBuf[outPtr++] = '-';
/*      */     
/* 1384 */     if (this._inputPtr >= this._inputEnd) {
/* 1385 */       _loadMoreGuaranteed();
/*      */     }
/* 1387 */     int c = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */     
/* 1389 */     if (c <= 48) {
/*      */       
/* 1391 */       if (c != 48) {
/* 1392 */         return _handleInvalidNumberStart(c, true);
/*      */       }
/* 1394 */       c = _verifyNoLeadingZeroes();
/* 1395 */     } else if (c > 57) {
/* 1396 */       return _handleInvalidNumberStart(c, true);
/*      */     } 
/*      */ 
/*      */     
/* 1400 */     outBuf[outPtr++] = (char)c;
/* 1401 */     int intLen = 1;
/*      */ 
/*      */ 
/*      */     
/* 1405 */     int end = Math.min(this._inputEnd, this._inputPtr + outBuf.length - outPtr);
/*      */     
/*      */     while (true) {
/* 1408 */       if (this._inputPtr >= end)
/*      */       {
/* 1410 */         return _parseNumber2(outBuf, outPtr, true, intLen);
/*      */       }
/* 1412 */       c = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1413 */       if (c < 48 || c > 57) {
/*      */         break;
/*      */       }
/* 1416 */       intLen++;
/* 1417 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 1419 */     if (c == 46 || c == 101 || c == 69) {
/* 1420 */       return _parseFloat(outBuf, outPtr, c, true, intLen);
/*      */     }
/*      */     
/* 1423 */     this._inputPtr--;
/* 1424 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1426 */     if (this._parsingContext.inRoot()) {
/* 1427 */       _verifyRootSpace(c);
/*      */     }
/*      */ 
/*      */     
/* 1431 */     return resetInt(true, intLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _parseNumber2(char[] outBuf, int outPtr, boolean negative, int intPartLength) throws IOException {
/*      */     while (true) {
/* 1443 */       if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1444 */         this._textBuffer.setCurrentLength(outPtr);
/* 1445 */         return resetInt(negative, intPartLength);
/*      */       } 
/* 1447 */       int c = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1448 */       if (c > 57 || c < 48) {
/* 1449 */         if (c == 46 || c == 101 || c == 69) {
/* 1450 */           return _parseFloat(outBuf, outPtr, c, negative, intPartLength);
/*      */         }
/*      */         break;
/*      */       } 
/* 1454 */       if (outPtr >= outBuf.length) {
/* 1455 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1456 */         outPtr = 0;
/*      */       } 
/* 1458 */       outBuf[outPtr++] = (char)c;
/* 1459 */       intPartLength++;
/*      */     } 
/* 1461 */     this._inputPtr--;
/* 1462 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1464 */     if (this._parsingContext.inRoot()) {
/* 1465 */       _verifyRootSpace(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */     }
/*      */ 
/*      */     
/* 1469 */     return resetInt(negative, intPartLength);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _verifyNoLeadingZeroes() throws IOException {
/* 1480 */     if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1481 */       return 48;
/*      */     }
/* 1483 */     int ch = this._inputBuffer[this._inputPtr] & 0xFF;
/*      */     
/* 1485 */     if (ch < 48 || ch > 57) {
/* 1486 */       return 48;
/*      */     }
/*      */     
/* 1489 */     if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1490 */       reportInvalidNumber("Leading zeroes not allowed");
/*      */     }
/*      */     
/* 1493 */     this._inputPtr++;
/* 1494 */     if (ch == 48) {
/* 1495 */       while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 1496 */         ch = this._inputBuffer[this._inputPtr] & 0xFF;
/* 1497 */         if (ch < 48 || ch > 57) {
/* 1498 */           return 48;
/*      */         }
/* 1500 */         this._inputPtr++;
/* 1501 */         if (ch != 48) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/* 1506 */     return ch;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _parseFloat(char[] outBuf, int outPtr, int c, boolean negative, int integerPartLength) throws IOException {
/* 1512 */     int fractLen = 0;
/* 1513 */     boolean eof = false;
/*      */ 
/*      */     
/* 1516 */     if (c == 46) {
/* 1517 */       if (outPtr >= outBuf.length) {
/* 1518 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1519 */         outPtr = 0;
/*      */       } 
/* 1521 */       outBuf[outPtr++] = (char)c;
/*      */ 
/*      */       
/*      */       while (true) {
/* 1525 */         if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1526 */           eof = true;
/*      */           break;
/*      */         } 
/* 1529 */         c = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1530 */         if (c < 48 || c > 57) {
/*      */           break;
/*      */         }
/* 1533 */         fractLen++;
/* 1534 */         if (outPtr >= outBuf.length) {
/* 1535 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1536 */           outPtr = 0;
/*      */         } 
/* 1538 */         outBuf[outPtr++] = (char)c;
/*      */       } 
/*      */       
/* 1541 */       if (fractLen == 0) {
/* 1542 */         reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
/*      */       }
/*      */     } 
/*      */     
/* 1546 */     int expLen = 0;
/* 1547 */     if (c == 101 || c == 69) {
/* 1548 */       if (outPtr >= outBuf.length) {
/* 1549 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1550 */         outPtr = 0;
/*      */       } 
/* 1552 */       outBuf[outPtr++] = (char)c;
/*      */       
/* 1554 */       if (this._inputPtr >= this._inputEnd) {
/* 1555 */         _loadMoreGuaranteed();
/*      */       }
/* 1557 */       c = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */       
/* 1559 */       if (c == 45 || c == 43) {
/* 1560 */         if (outPtr >= outBuf.length) {
/* 1561 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1562 */           outPtr = 0;
/*      */         } 
/* 1564 */         outBuf[outPtr++] = (char)c;
/*      */         
/* 1566 */         if (this._inputPtr >= this._inputEnd) {
/* 1567 */           _loadMoreGuaranteed();
/*      */         }
/* 1569 */         c = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */       } 
/*      */ 
/*      */       
/* 1573 */       while (c >= 48 && c <= 57) {
/* 1574 */         expLen++;
/* 1575 */         if (outPtr >= outBuf.length) {
/* 1576 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1577 */           outPtr = 0;
/*      */         } 
/* 1579 */         outBuf[outPtr++] = (char)c;
/* 1580 */         if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 1581 */           eof = true;
/*      */           break;
/*      */         } 
/* 1584 */         c = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */       } 
/*      */       
/* 1587 */       if (expLen == 0) {
/* 1588 */         reportUnexpectedNumberChar(c, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1593 */     if (!eof) {
/* 1594 */       this._inputPtr--;
/*      */       
/* 1596 */       if (this._parsingContext.inRoot()) {
/* 1597 */         _verifyRootSpace(c);
/*      */       }
/*      */     } 
/* 1600 */     this._textBuffer.setCurrentLength(outPtr);
/*      */ 
/*      */     
/* 1603 */     return resetFloat(negative, integerPartLength, fractLen, expLen);
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
/* 1616 */     this._inputPtr++;
/*      */     
/* 1618 */     switch (ch) {
/*      */       case 9:
/*      */       case 32:
/*      */         return;
/*      */       case 13:
/* 1623 */         _skipCR();
/*      */         return;
/*      */       case 10:
/* 1626 */         this._currInputRow++;
/* 1627 */         this._currInputRowStart = this._inputPtr;
/*      */         return;
/*      */     } 
/* 1630 */     _reportMissingRootWS(ch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String _parseName(int i) throws IOException {
/* 1641 */     if (i != 34) {
/* 1642 */       return _handleOddName(i);
/*      */     }
/*      */     
/* 1645 */     if (this._inputPtr + 13 > this._inputEnd) {
/* 1646 */       return slowParseName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1655 */     byte[] input = this._inputBuffer;
/* 1656 */     int[] codes = _icLatin1;
/*      */     
/* 1658 */     int q = input[this._inputPtr++] & 0xFF;
/*      */     
/* 1660 */     if (codes[q] == 0) {
/* 1661 */       i = input[this._inputPtr++] & 0xFF;
/* 1662 */       if (codes[i] == 0) {
/* 1663 */         q = q << 8 | i;
/* 1664 */         i = input[this._inputPtr++] & 0xFF;
/* 1665 */         if (codes[i] == 0) {
/* 1666 */           q = q << 8 | i;
/* 1667 */           i = input[this._inputPtr++] & 0xFF;
/* 1668 */           if (codes[i] == 0) {
/* 1669 */             q = q << 8 | i;
/* 1670 */             i = input[this._inputPtr++] & 0xFF;
/* 1671 */             if (codes[i] == 0) {
/* 1672 */               this._quad1 = q;
/* 1673 */               return parseMediumName(i);
/*      */             } 
/* 1675 */             if (i == 34) {
/* 1676 */               return findName(q, 4);
/*      */             }
/* 1678 */             return parseName(q, i, 4);
/*      */           } 
/* 1680 */           if (i == 34) {
/* 1681 */             return findName(q, 3);
/*      */           }
/* 1683 */           return parseName(q, i, 3);
/*      */         } 
/* 1685 */         if (i == 34) {
/* 1686 */           return findName(q, 2);
/*      */         }
/* 1688 */         return parseName(q, i, 2);
/*      */       } 
/* 1690 */       if (i == 34) {
/* 1691 */         return findName(q, 1);
/*      */       }
/* 1693 */       return parseName(q, i, 1);
/*      */     } 
/* 1695 */     if (q == 34) {
/* 1696 */       return "";
/*      */     }
/* 1698 */     return parseName(0, q, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final String parseMediumName(int q2) throws IOException {
/* 1703 */     byte[] input = this._inputBuffer;
/* 1704 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1707 */     int i = input[this._inputPtr++] & 0xFF;
/* 1708 */     if (codes[i] != 0) {
/* 1709 */       if (i == 34) {
/* 1710 */         return findName(this._quad1, q2, 1);
/*      */       }
/* 1712 */       return parseName(this._quad1, q2, i, 1);
/*      */     } 
/* 1714 */     q2 = q2 << 8 | i;
/* 1715 */     i = input[this._inputPtr++] & 0xFF;
/* 1716 */     if (codes[i] != 0) {
/* 1717 */       if (i == 34) {
/* 1718 */         return findName(this._quad1, q2, 2);
/*      */       }
/* 1720 */       return parseName(this._quad1, q2, i, 2);
/*      */     } 
/* 1722 */     q2 = q2 << 8 | i;
/* 1723 */     i = input[this._inputPtr++] & 0xFF;
/* 1724 */     if (codes[i] != 0) {
/* 1725 */       if (i == 34) {
/* 1726 */         return findName(this._quad1, q2, 3);
/*      */       }
/* 1728 */       return parseName(this._quad1, q2, i, 3);
/*      */     } 
/* 1730 */     q2 = q2 << 8 | i;
/* 1731 */     i = input[this._inputPtr++] & 0xFF;
/* 1732 */     if (codes[i] != 0) {
/* 1733 */       if (i == 34) {
/* 1734 */         return findName(this._quad1, q2, 4);
/*      */       }
/* 1736 */       return parseName(this._quad1, q2, i, 4);
/*      */     } 
/* 1738 */     return parseMediumName2(i, q2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String parseMediumName2(int q3, int q2) throws IOException {
/* 1746 */     byte[] input = this._inputBuffer;
/* 1747 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1750 */     int i = input[this._inputPtr++] & 0xFF;
/* 1751 */     if (codes[i] != 0) {
/* 1752 */       if (i == 34) {
/* 1753 */         return findName(this._quad1, q2, q3, 1);
/*      */       }
/* 1755 */       return parseName(this._quad1, q2, q3, i, 1);
/*      */     } 
/* 1757 */     q3 = q3 << 8 | i;
/* 1758 */     i = input[this._inputPtr++] & 0xFF;
/* 1759 */     if (codes[i] != 0) {
/* 1760 */       if (i == 34) {
/* 1761 */         return findName(this._quad1, q2, q3, 2);
/*      */       }
/* 1763 */       return parseName(this._quad1, q2, q3, i, 2);
/*      */     } 
/* 1765 */     q3 = q3 << 8 | i;
/* 1766 */     i = input[this._inputPtr++] & 0xFF;
/* 1767 */     if (codes[i] != 0) {
/* 1768 */       if (i == 34) {
/* 1769 */         return findName(this._quad1, q2, q3, 3);
/*      */       }
/* 1771 */       return parseName(this._quad1, q2, q3, i, 3);
/*      */     } 
/* 1773 */     q3 = q3 << 8 | i;
/* 1774 */     i = input[this._inputPtr++] & 0xFF;
/* 1775 */     if (codes[i] != 0) {
/* 1776 */       if (i == 34) {
/* 1777 */         return findName(this._quad1, q2, q3, 4);
/*      */       }
/* 1779 */       return parseName(this._quad1, q2, q3, i, 4);
/*      */     } 
/* 1781 */     return parseLongName(i, q2, q3);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final String parseLongName(int q, int q2, int q3) throws IOException {
/* 1786 */     this._quadBuffer[0] = this._quad1;
/* 1787 */     this._quadBuffer[1] = q2;
/* 1788 */     this._quadBuffer[2] = q3;
/*      */ 
/*      */     
/* 1791 */     byte[] input = this._inputBuffer;
/* 1792 */     int[] codes = _icLatin1;
/* 1793 */     int qlen = 3;
/*      */     
/* 1795 */     while (this._inputPtr + 4 <= this._inputEnd) {
/* 1796 */       int i = input[this._inputPtr++] & 0xFF;
/* 1797 */       if (codes[i] != 0) {
/* 1798 */         if (i == 34) {
/* 1799 */           return findName(this._quadBuffer, qlen, q, 1);
/*      */         }
/* 1801 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 1);
/*      */       } 
/*      */       
/* 1804 */       q = q << 8 | i;
/* 1805 */       i = input[this._inputPtr++] & 0xFF;
/* 1806 */       if (codes[i] != 0) {
/* 1807 */         if (i == 34) {
/* 1808 */           return findName(this._quadBuffer, qlen, q, 2);
/*      */         }
/* 1810 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 2);
/*      */       } 
/*      */       
/* 1813 */       q = q << 8 | i;
/* 1814 */       i = input[this._inputPtr++] & 0xFF;
/* 1815 */       if (codes[i] != 0) {
/* 1816 */         if (i == 34) {
/* 1817 */           return findName(this._quadBuffer, qlen, q, 3);
/*      */         }
/* 1819 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 3);
/*      */       } 
/*      */       
/* 1822 */       q = q << 8 | i;
/* 1823 */       i = input[this._inputPtr++] & 0xFF;
/* 1824 */       if (codes[i] != 0) {
/* 1825 */         if (i == 34) {
/* 1826 */           return findName(this._quadBuffer, qlen, q, 4);
/*      */         }
/* 1828 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 4);
/*      */       } 
/*      */ 
/*      */       
/* 1832 */       if (qlen >= this._quadBuffer.length) {
/* 1833 */         this._quadBuffer = growArrayBy(this._quadBuffer, qlen);
/*      */       }
/* 1835 */       this._quadBuffer[qlen++] = q;
/* 1836 */       q = i;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1843 */     return parseEscapedName(this._quadBuffer, qlen, 0, q, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String slowParseName() throws IOException {
/* 1853 */     if (this._inputPtr >= this._inputEnd && 
/* 1854 */       !_loadMore()) {
/* 1855 */       _reportInvalidEOF(": was expecting closing '\"' for name", JsonToken.FIELD_NAME);
/*      */     }
/*      */     
/* 1858 */     int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1859 */     if (i == 34) {
/* 1860 */       return "";
/*      */     }
/* 1862 */     return parseEscapedName(this._quadBuffer, 0, 0, i, 0);
/*      */   }
/*      */   
/*      */   private final String parseName(int q1, int ch, int lastQuadBytes) throws IOException {
/* 1866 */     return parseEscapedName(this._quadBuffer, 0, q1, ch, lastQuadBytes);
/*      */   }
/*      */   
/*      */   private final String parseName(int q1, int q2, int ch, int lastQuadBytes) throws IOException {
/* 1870 */     this._quadBuffer[0] = q1;
/* 1871 */     return parseEscapedName(this._quadBuffer, 1, q2, ch, lastQuadBytes);
/*      */   }
/*      */   
/*      */   private final String parseName(int q1, int q2, int q3, int ch, int lastQuadBytes) throws IOException {
/* 1875 */     this._quadBuffer[0] = q1;
/* 1876 */     this._quadBuffer[1] = q2;
/* 1877 */     return parseEscapedName(this._quadBuffer, 2, q3, ch, lastQuadBytes);
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
/*      */   protected final String parseEscapedName(int[] quads, int qlen, int currQuad, int ch, int currQuadBytes) throws IOException {
/* 1892 */     int[] codes = _icLatin1;
/*      */     
/*      */     while (true) {
/* 1895 */       if (codes[ch] != 0) {
/* 1896 */         if (ch == 34) {
/*      */           break;
/*      */         }
/*      */         
/* 1900 */         if (ch != 92) {
/*      */           
/* 1902 */           _throwUnquotedSpace(ch, "name");
/*      */         } else {
/*      */           
/* 1905 */           ch = _decodeEscaped();
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1910 */         if (ch > 127) {
/*      */           
/* 1912 */           if (currQuadBytes >= 4) {
/* 1913 */             if (qlen >= quads.length) {
/* 1914 */               this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */             }
/* 1916 */             quads[qlen++] = currQuad;
/* 1917 */             currQuad = 0;
/* 1918 */             currQuadBytes = 0;
/*      */           } 
/* 1920 */           if (ch < 2048) {
/* 1921 */             currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 1922 */             currQuadBytes++;
/*      */           } else {
/*      */             
/* 1925 */             currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/* 1926 */             currQuadBytes++;
/*      */             
/* 1928 */             if (currQuadBytes >= 4) {
/* 1929 */               if (qlen >= quads.length) {
/* 1930 */                 this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */               }
/* 1932 */               quads[qlen++] = currQuad;
/* 1933 */               currQuad = 0;
/* 1934 */               currQuadBytes = 0;
/*      */             } 
/* 1936 */             currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 1937 */             currQuadBytes++;
/*      */           } 
/*      */           
/* 1940 */           ch = 0x80 | ch & 0x3F;
/*      */         } 
/*      */       } 
/*      */       
/* 1944 */       if (currQuadBytes < 4) {
/* 1945 */         currQuadBytes++;
/* 1946 */         currQuad = currQuad << 8 | ch;
/*      */       } else {
/* 1948 */         if (qlen >= quads.length) {
/* 1949 */           this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */         }
/* 1951 */         quads[qlen++] = currQuad;
/* 1952 */         currQuad = ch;
/* 1953 */         currQuadBytes = 1;
/*      */       } 
/* 1955 */       if (this._inputPtr >= this._inputEnd && 
/* 1956 */         !_loadMore()) {
/* 1957 */         _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*      */       }
/*      */       
/* 1960 */       ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */     } 
/*      */     
/* 1963 */     if (currQuadBytes > 0) {
/* 1964 */       if (qlen >= quads.length) {
/* 1965 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 1967 */       quads[qlen++] = _padLastQuad(currQuad, currQuadBytes);
/*      */     } 
/* 1969 */     String name = this._symbols.findName(quads, qlen);
/* 1970 */     if (name == null) {
/* 1971 */       name = addName(quads, qlen, currQuadBytes);
/*      */     }
/* 1973 */     return name;
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
/*      */   protected String _handleOddName(int ch) throws IOException {
/* 1985 */     if (ch == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1986 */       return _parseAposName();
/*      */     }
/*      */     
/* 1989 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1990 */       char c = (char)_decodeCharForError(ch);
/* 1991 */       _reportUnexpectedChar(c, "was expecting double-quote to start field name");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1997 */     int[] codes = CharTypes.getInputCodeUtf8JsNames();
/*      */     
/* 1999 */     if (codes[ch] != 0) {
/* 2000 */       _reportUnexpectedChar(ch, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2006 */     int[] quads = this._quadBuffer;
/* 2007 */     int qlen = 0;
/* 2008 */     int currQuad = 0;
/* 2009 */     int currQuadBytes = 0;
/*      */ 
/*      */     
/*      */     while (true) {
/* 2013 */       if (currQuadBytes < 4) {
/* 2014 */         currQuadBytes++;
/* 2015 */         currQuad = currQuad << 8 | ch;
/*      */       } else {
/* 2017 */         if (qlen >= quads.length) {
/* 2018 */           this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */         }
/* 2020 */         quads[qlen++] = currQuad;
/* 2021 */         currQuad = ch;
/* 2022 */         currQuadBytes = 1;
/*      */       } 
/* 2024 */       if (this._inputPtr >= this._inputEnd && 
/* 2025 */         !_loadMore()) {
/* 2026 */         _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*      */       }
/*      */       
/* 2029 */       ch = this._inputBuffer[this._inputPtr] & 0xFF;
/* 2030 */       if (codes[ch] != 0) {
/*      */         break;
/*      */       }
/* 2033 */       this._inputPtr++;
/*      */     } 
/*      */     
/* 2036 */     if (currQuadBytes > 0) {
/* 2037 */       if (qlen >= quads.length) {
/* 2038 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2040 */       quads[qlen++] = currQuad;
/*      */     } 
/* 2042 */     String name = this._symbols.findName(quads, qlen);
/* 2043 */     if (name == null) {
/* 2044 */       name = addName(quads, qlen, currQuadBytes);
/*      */     }
/* 2046 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _parseAposName() throws IOException {
/* 2056 */     if (this._inputPtr >= this._inputEnd && 
/* 2057 */       !_loadMore()) {
/* 2058 */       _reportInvalidEOF(": was expecting closing ''' for field name", JsonToken.FIELD_NAME);
/*      */     }
/*      */     
/* 2061 */     int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2062 */     if (ch == 39) {
/* 2063 */       return "";
/*      */     }
/* 2065 */     int[] quads = this._quadBuffer;
/* 2066 */     int qlen = 0;
/* 2067 */     int currQuad = 0;
/* 2068 */     int currQuadBytes = 0;
/*      */ 
/*      */ 
/*      */     
/* 2072 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 2075 */     while (ch != 39) {
/*      */ 
/*      */ 
/*      */       
/* 2079 */       if (codes[ch] != 0 && ch != 34) {
/* 2080 */         if (ch != 92) {
/*      */ 
/*      */           
/* 2083 */           _throwUnquotedSpace(ch, "name");
/*      */         } else {
/*      */           
/* 2086 */           ch = _decodeEscaped();
/*      */         } 
/*      */         
/* 2089 */         if (ch > 127) {
/*      */           
/* 2091 */           if (currQuadBytes >= 4) {
/* 2092 */             if (qlen >= quads.length) {
/* 2093 */               this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */             }
/* 2095 */             quads[qlen++] = currQuad;
/* 2096 */             currQuad = 0;
/* 2097 */             currQuadBytes = 0;
/*      */           } 
/* 2099 */           if (ch < 2048) {
/* 2100 */             currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 2101 */             currQuadBytes++;
/*      */           } else {
/*      */             
/* 2104 */             currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/* 2105 */             currQuadBytes++;
/*      */             
/* 2107 */             if (currQuadBytes >= 4) {
/* 2108 */               if (qlen >= quads.length) {
/* 2109 */                 this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */               }
/* 2111 */               quads[qlen++] = currQuad;
/* 2112 */               currQuad = 0;
/* 2113 */               currQuadBytes = 0;
/*      */             } 
/* 2115 */             currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 2116 */             currQuadBytes++;
/*      */           } 
/*      */           
/* 2119 */           ch = 0x80 | ch & 0x3F;
/*      */         } 
/*      */       } 
/*      */       
/* 2123 */       if (currQuadBytes < 4) {
/* 2124 */         currQuadBytes++;
/* 2125 */         currQuad = currQuad << 8 | ch;
/*      */       } else {
/* 2127 */         if (qlen >= quads.length) {
/* 2128 */           this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */         }
/* 2130 */         quads[qlen++] = currQuad;
/* 2131 */         currQuad = ch;
/* 2132 */         currQuadBytes = 1;
/*      */       } 
/* 2134 */       if (this._inputPtr >= this._inputEnd && 
/* 2135 */         !_loadMore()) {
/* 2136 */         _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*      */       }
/*      */       
/* 2139 */       ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */     } 
/*      */     
/* 2142 */     if (currQuadBytes > 0) {
/* 2143 */       if (qlen >= quads.length) {
/* 2144 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2146 */       quads[qlen++] = _padLastQuad(currQuad, currQuadBytes);
/*      */     } 
/* 2148 */     String name = this._symbols.findName(quads, qlen);
/* 2149 */     if (name == null) {
/* 2150 */       name = addName(quads, qlen, currQuadBytes);
/*      */     }
/* 2152 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String findName(int q1, int lastQuadBytes) throws JsonParseException {
/* 2163 */     q1 = _padLastQuad(q1, lastQuadBytes);
/*      */     
/* 2165 */     String name = this._symbols.findName(q1);
/* 2166 */     if (name != null) {
/* 2167 */       return name;
/*      */     }
/*      */     
/* 2170 */     this._quadBuffer[0] = q1;
/* 2171 */     return addName(this._quadBuffer, 1, lastQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String findName(int q1, int q2, int lastQuadBytes) throws JsonParseException {
/* 2176 */     q2 = _padLastQuad(q2, lastQuadBytes);
/*      */     
/* 2178 */     String name = this._symbols.findName(q1, q2);
/* 2179 */     if (name != null) {
/* 2180 */       return name;
/*      */     }
/*      */     
/* 2183 */     this._quadBuffer[0] = q1;
/* 2184 */     this._quadBuffer[1] = q2;
/* 2185 */     return addName(this._quadBuffer, 2, lastQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String findName(int q1, int q2, int q3, int lastQuadBytes) throws JsonParseException {
/* 2190 */     q3 = _padLastQuad(q3, lastQuadBytes);
/* 2191 */     String name = this._symbols.findName(q1, q2, q3);
/* 2192 */     if (name != null) {
/* 2193 */       return name;
/*      */     }
/* 2195 */     int[] quads = this._quadBuffer;
/* 2196 */     quads[0] = q1;
/* 2197 */     quads[1] = q2;
/* 2198 */     quads[2] = _padLastQuad(q3, lastQuadBytes);
/* 2199 */     return addName(quads, 3, lastQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String findName(int[] quads, int qlen, int lastQuad, int lastQuadBytes) throws JsonParseException {
/* 2204 */     if (qlen >= quads.length) {
/* 2205 */       this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */     }
/* 2207 */     quads[qlen++] = _padLastQuad(lastQuad, lastQuadBytes);
/* 2208 */     String name = this._symbols.findName(quads, qlen);
/* 2209 */     if (name == null) {
/* 2210 */       return addName(quads, qlen, lastQuadBytes);
/*      */     }
/* 2212 */     return name;
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
/*      */   private final String addName(int[] quads, int qlen, int lastQuadBytes) throws JsonParseException {
/* 2228 */     int lastQuad, byteLen = (qlen << 2) - 4 + lastQuadBytes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2237 */     if (lastQuadBytes < 4) {
/* 2238 */       lastQuad = quads[qlen - 1];
/*      */       
/* 2240 */       quads[qlen - 1] = lastQuad << 4 - lastQuadBytes << 3;
/*      */     } else {
/* 2242 */       lastQuad = 0;
/*      */     } 
/*      */ 
/*      */     
/* 2246 */     char[] cbuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 2247 */     int cix = 0;
/*      */     
/* 2249 */     for (int ix = 0; ix < byteLen; ) {
/* 2250 */       int ch = quads[ix >> 2];
/* 2251 */       int byteIx = ix & 0x3;
/* 2252 */       ch = ch >> 3 - byteIx << 3 & 0xFF;
/* 2253 */       ix++;
/*      */       
/* 2255 */       if (ch > 127) {
/*      */         int needed;
/* 2257 */         if ((ch & 0xE0) == 192) {
/* 2258 */           ch &= 0x1F;
/* 2259 */           needed = 1;
/* 2260 */         } else if ((ch & 0xF0) == 224) {
/* 2261 */           ch &= 0xF;
/* 2262 */           needed = 2;
/* 2263 */         } else if ((ch & 0xF8) == 240) {
/* 2264 */           ch &= 0x7;
/* 2265 */           needed = 3;
/*      */         } else {
/* 2267 */           _reportInvalidInitial(ch);
/* 2268 */           needed = ch = 1;
/*      */         } 
/* 2270 */         if (ix + needed > byteLen) {
/* 2271 */           _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*      */         }
/*      */ 
/*      */         
/* 2275 */         int ch2 = quads[ix >> 2];
/* 2276 */         byteIx = ix & 0x3;
/* 2277 */         ch2 >>= 3 - byteIx << 3;
/* 2278 */         ix++;
/*      */         
/* 2280 */         if ((ch2 & 0xC0) != 128) {
/* 2281 */           _reportInvalidOther(ch2);
/*      */         }
/* 2283 */         ch = ch << 6 | ch2 & 0x3F;
/* 2284 */         if (needed > 1) {
/* 2285 */           ch2 = quads[ix >> 2];
/* 2286 */           byteIx = ix & 0x3;
/* 2287 */           ch2 >>= 3 - byteIx << 3;
/* 2288 */           ix++;
/*      */           
/* 2290 */           if ((ch2 & 0xC0) != 128) {
/* 2291 */             _reportInvalidOther(ch2);
/*      */           }
/* 2293 */           ch = ch << 6 | ch2 & 0x3F;
/* 2294 */           if (needed > 2) {
/* 2295 */             ch2 = quads[ix >> 2];
/* 2296 */             byteIx = ix & 0x3;
/* 2297 */             ch2 >>= 3 - byteIx << 3;
/* 2298 */             ix++;
/* 2299 */             if ((ch2 & 0xC0) != 128) {
/* 2300 */               _reportInvalidOther(ch2 & 0xFF);
/*      */             }
/* 2302 */             ch = ch << 6 | ch2 & 0x3F;
/*      */           } 
/*      */         } 
/* 2305 */         if (needed > 2) {
/* 2306 */           ch -= 65536;
/* 2307 */           if (cix >= cbuf.length) {
/* 2308 */             cbuf = this._textBuffer.expandCurrentSegment();
/*      */           }
/* 2310 */           cbuf[cix++] = (char)(55296 + (ch >> 10));
/* 2311 */           ch = 0xDC00 | ch & 0x3FF;
/*      */         } 
/*      */       } 
/* 2314 */       if (cix >= cbuf.length) {
/* 2315 */         cbuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 2317 */       cbuf[cix++] = (char)ch;
/*      */     } 
/*      */ 
/*      */     
/* 2321 */     String baseName = new String(cbuf, 0, cix);
/*      */     
/* 2323 */     if (lastQuadBytes < 4) {
/* 2324 */       quads[qlen - 1] = lastQuad;
/*      */     }
/* 2326 */     return this._symbols.addName(baseName, quads, qlen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int _padLastQuad(int q, int bytes) {
/* 2333 */     return (bytes == 4) ? q : (q | -1 << bytes << 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _loadMoreGuaranteed() throws IOException {
/* 2343 */     if (!_loadMore()) _reportInvalidEOF();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _finishString() throws IOException {
/* 2350 */     int ptr = this._inputPtr;
/* 2351 */     if (ptr >= this._inputEnd) {
/* 2352 */       _loadMoreGuaranteed();
/* 2353 */       ptr = this._inputPtr;
/*      */     } 
/* 2355 */     int outPtr = 0;
/* 2356 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 2357 */     int[] codes = _icUTF8;
/*      */     
/* 2359 */     int max = Math.min(this._inputEnd, ptr + outBuf.length);
/* 2360 */     byte[] inputBuffer = this._inputBuffer;
/* 2361 */     while (ptr < max) {
/* 2362 */       int c = inputBuffer[ptr] & 0xFF;
/* 2363 */       if (codes[c] != 0) {
/* 2364 */         if (c == 34) {
/* 2365 */           this._inputPtr = ptr + 1;
/* 2366 */           this._textBuffer.setCurrentLength(outPtr);
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       } 
/* 2371 */       ptr++;
/* 2372 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 2374 */     this._inputPtr = ptr;
/* 2375 */     _finishString2(outBuf, outPtr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _finishAndReturnString() throws IOException {
/* 2384 */     int ptr = this._inputPtr;
/* 2385 */     if (ptr >= this._inputEnd) {
/* 2386 */       _loadMoreGuaranteed();
/* 2387 */       ptr = this._inputPtr;
/*      */     } 
/* 2389 */     int outPtr = 0;
/* 2390 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 2391 */     int[] codes = _icUTF8;
/*      */     
/* 2393 */     int max = Math.min(this._inputEnd, ptr + outBuf.length);
/* 2394 */     byte[] inputBuffer = this._inputBuffer;
/* 2395 */     while (ptr < max) {
/* 2396 */       int c = inputBuffer[ptr] & 0xFF;
/* 2397 */       if (codes[c] != 0) {
/* 2398 */         if (c == 34) {
/* 2399 */           this._inputPtr = ptr + 1;
/* 2400 */           return this._textBuffer.setCurrentAndReturn(outPtr);
/*      */         } 
/*      */         break;
/*      */       } 
/* 2404 */       ptr++;
/* 2405 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 2407 */     this._inputPtr = ptr;
/* 2408 */     _finishString2(outBuf, outPtr);
/* 2409 */     return this._textBuffer.contentsAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _finishString2(char[] outBuf, int outPtr) throws IOException {
/* 2418 */     int[] codes = _icUTF8;
/* 2419 */     byte[] inputBuffer = this._inputBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2426 */       int ptr = this._inputPtr;
/* 2427 */       if (ptr >= this._inputEnd) {
/* 2428 */         _loadMoreGuaranteed();
/* 2429 */         ptr = this._inputPtr;
/*      */       } 
/* 2431 */       if (outPtr >= outBuf.length) {
/* 2432 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 2433 */         outPtr = 0;
/*      */       } 
/* 2435 */       int max = Math.min(this._inputEnd, ptr + outBuf.length - outPtr);
/* 2436 */       while (ptr < max) {
/* 2437 */         int c = inputBuffer[ptr++] & 0xFF;
/* 2438 */         if (codes[c] != 0) {
/* 2439 */           this._inputPtr = ptr;
/*      */         } else {
/*      */           
/* 2442 */           outBuf[outPtr++] = (char)c;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 2447 */         if (c == 34) {
/*      */           break;
/*      */         }
/*      */         
/* 2451 */         switch (codes[c]) {
/*      */           case 1:
/* 2453 */             c = _decodeEscaped();
/*      */             break;
/*      */           case 2:
/* 2456 */             c = _decodeUtf8_2(c);
/*      */             break;
/*      */           case 3:
/* 2459 */             if (this._inputEnd - this._inputPtr >= 2) {
/* 2460 */               c = _decodeUtf8_3fast(c); break;
/*      */             } 
/* 2462 */             c = _decodeUtf8_3(c);
/*      */             break;
/*      */           
/*      */           case 4:
/* 2466 */             c = _decodeUtf8_4(c);
/*      */             
/* 2468 */             outBuf[outPtr++] = (char)(0xD800 | c >> 10);
/* 2469 */             if (outPtr >= outBuf.length) {
/* 2470 */               outBuf = this._textBuffer.finishCurrentSegment();
/* 2471 */               outPtr = 0;
/*      */             } 
/* 2473 */             c = 0xDC00 | c & 0x3FF;
/*      */             break;
/*      */           
/*      */           default:
/* 2477 */             if (c < 32) {
/*      */               
/* 2479 */               _throwUnquotedSpace(c, "string value");
/*      */               break;
/*      */             } 
/* 2482 */             _reportInvalidChar(c);
/*      */             break;
/*      */         } 
/*      */         
/* 2486 */         if (outPtr >= outBuf.length) {
/* 2487 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 2488 */           outPtr = 0;
/*      */         } 
/*      */         
/* 2491 */         outBuf[outPtr++] = (char)c;
/*      */       }  this._inputPtr = ptr;
/* 2493 */     }  this._textBuffer.setCurrentLength(outPtr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _skipString() throws IOException {
/* 2503 */     this._tokenIncomplete = false;
/*      */ 
/*      */     
/* 2506 */     int[] codes = _icUTF8;
/* 2507 */     byte[] inputBuffer = this._inputBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2515 */       int ptr = this._inputPtr;
/* 2516 */       int max = this._inputEnd;
/* 2517 */       if (ptr >= max) {
/* 2518 */         _loadMoreGuaranteed();
/* 2519 */         ptr = this._inputPtr;
/* 2520 */         max = this._inputEnd;
/*      */       } 
/* 2522 */       while (ptr < max) {
/* 2523 */         int c = inputBuffer[ptr++] & 0xFF;
/* 2524 */         if (codes[c] != 0) {
/* 2525 */           this._inputPtr = ptr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2532 */           if (c != 34) {
/*      */ 
/*      */ 
/*      */             
/* 2536 */             switch (codes[c]) {
/*      */               case 1:
/* 2538 */                 _decodeEscaped();
/*      */                 continue;
/*      */               case 2:
/* 2541 */                 _skipUtf8_2();
/*      */                 continue;
/*      */               case 3:
/* 2544 */                 _skipUtf8_3();
/*      */                 continue;
/*      */               case 4:
/* 2547 */                 _skipUtf8_4(c);
/*      */                 continue;
/*      */             } 
/* 2550 */             if (c < 32) {
/* 2551 */               _throwUnquotedSpace(c, "string value");
/*      */               continue;
/*      */             } 
/* 2554 */             _reportInvalidChar(c);
/*      */             continue;
/*      */           } 
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       this._inputPtr = ptr;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _handleUnexpectedValue(int c) throws IOException {
/* 2567 */     switch (c) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 93:
/* 2576 */         if (!this._parsingContext.inArray()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 44:
/* 2584 */         if (isEnabled(JsonParser.Feature.ALLOW_MISSING_VALUES)) {
/* 2585 */           this._inputPtr--;
/* 2586 */           return JsonToken.VALUE_NULL;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 125:
/* 2592 */         _reportUnexpectedChar(c, "expected a value");
/*      */       case 39:
/* 2594 */         if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 2595 */           return _handleApos();
/*      */         }
/*      */         break;
/*      */       case 78:
/* 2599 */         _matchToken("NaN", 1);
/* 2600 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2601 */           return resetAsNaN("NaN", Double.NaN);
/*      */         }
/* 2603 */         _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */         break;
/*      */       case 73:
/* 2606 */         _matchToken("Infinity", 1);
/* 2607 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2608 */           return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
/*      */         }
/* 2610 */         _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */         break;
/*      */       case 43:
/* 2613 */         if (this._inputPtr >= this._inputEnd && 
/* 2614 */           !_loadMore()) {
/* 2615 */           _reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
/*      */         }
/*      */         
/* 2618 */         return _handleInvalidNumberStart(this._inputBuffer[this._inputPtr++] & 0xFF, false);
/*      */     } 
/*      */     
/* 2621 */     if (Character.isJavaIdentifierStart(c)) {
/* 2622 */       _reportInvalidToken("" + (char)c, "('true', 'false' or 'null')");
/*      */     }
/*      */     
/* 2625 */     _reportUnexpectedChar(c, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 2626 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _handleApos() throws IOException {
/* 2631 */     int c = 0;
/*      */     
/* 2633 */     int outPtr = 0;
/* 2634 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/*      */ 
/*      */     
/* 2637 */     int[] codes = _icUTF8;
/* 2638 */     byte[] inputBuffer = this._inputBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true)
/* 2645 */     { if (this._inputPtr >= this._inputEnd) {
/* 2646 */         _loadMoreGuaranteed();
/*      */       }
/* 2648 */       if (outPtr >= outBuf.length) {
/* 2649 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 2650 */         outPtr = 0;
/*      */       } 
/* 2652 */       int max = this._inputEnd;
/*      */       
/* 2654 */       int max2 = this._inputPtr + outBuf.length - outPtr;
/* 2655 */       if (max2 < max) {
/* 2656 */         max = max2;
/*      */       }
/*      */       
/* 2659 */       while (this._inputPtr < max)
/* 2660 */       { c = inputBuffer[this._inputPtr++] & 0xFF;
/* 2661 */         if (c == 39 || codes[c] != 0)
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2669 */           if (c != 39) {
/*      */ 
/*      */ 
/*      */             
/* 2673 */             switch (codes[c]) {
/*      */               case 1:
/* 2675 */                 c = _decodeEscaped();
/*      */                 break;
/*      */               case 2:
/* 2678 */                 c = _decodeUtf8_2(c);
/*      */                 break;
/*      */               case 3:
/* 2681 */                 if (this._inputEnd - this._inputPtr >= 2) {
/* 2682 */                   c = _decodeUtf8_3fast(c); break;
/*      */                 } 
/* 2684 */                 c = _decodeUtf8_3(c);
/*      */                 break;
/*      */               
/*      */               case 4:
/* 2688 */                 c = _decodeUtf8_4(c);
/*      */                 
/* 2690 */                 outBuf[outPtr++] = (char)(0xD800 | c >> 10);
/* 2691 */                 if (outPtr >= outBuf.length) {
/* 2692 */                   outBuf = this._textBuffer.finishCurrentSegment();
/* 2693 */                   outPtr = 0;
/*      */                 } 
/* 2695 */                 c = 0xDC00 | c & 0x3FF;
/*      */                 break;
/*      */               
/*      */               default:
/* 2699 */                 if (c < 32) {
/* 2700 */                   _throwUnquotedSpace(c, "string value");
/*      */                 }
/*      */                 
/* 2703 */                 _reportInvalidChar(c);
/*      */                 break;
/*      */             } 
/* 2706 */             if (outPtr >= outBuf.length) {
/* 2707 */               outBuf = this._textBuffer.finishCurrentSegment();
/* 2708 */               outPtr = 0;
/*      */             } 
/*      */             
/* 2711 */             outBuf[outPtr++] = (char)c; continue;
/*      */           } 
/* 2713 */           this._textBuffer.setCurrentLength(outPtr);
/*      */           
/* 2715 */           return JsonToken.VALUE_STRING; }  outBuf[outPtr++] = (char)c; }  }  this._textBuffer.setCurrentLength(outPtr); return JsonToken.VALUE_STRING;
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
/*      */   protected JsonToken _handleInvalidNumberStart(int ch, boolean neg) throws IOException {
/* 2730 */     while (ch == 73) {
/* 2731 */       String match; if (this._inputPtr >= this._inputEnd && 
/* 2732 */         !_loadMore()) {
/* 2733 */         _reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_FLOAT);
/*      */       }
/*      */       
/* 2736 */       ch = this._inputBuffer[this._inputPtr++];
/*      */       
/* 2738 */       if (ch == 78) {
/* 2739 */         match = neg ? "-INF" : "+INF";
/* 2740 */       } else if (ch == 110) {
/* 2741 */         match = neg ? "-Infinity" : "+Infinity";
/*      */       } else {
/*      */         break;
/*      */       } 
/* 2745 */       _matchToken(match, 3);
/* 2746 */       if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2747 */         return resetAsNaN(match, neg ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */       }
/* 2749 */       _reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", match);
/*      */     } 
/*      */     
/* 2752 */     reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 2753 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _matchTrue() throws IOException {
/* 2759 */     int ptr = this._inputPtr;
/* 2760 */     if (ptr + 3 < this._inputEnd) {
/* 2761 */       byte[] buf = this._inputBuffer;
/* 2762 */       if (buf[ptr++] == 114 && buf[ptr++] == 117 && buf[ptr++] == 101) {
/*      */ 
/*      */         
/* 2765 */         int ch = buf[ptr] & 0xFF;
/* 2766 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 2767 */           this._inputPtr = ptr;
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2772 */     _matchToken2("true", 1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _matchFalse() throws IOException {
/* 2777 */     int ptr = this._inputPtr;
/* 2778 */     if (ptr + 4 < this._inputEnd) {
/* 2779 */       byte[] buf = this._inputBuffer;
/* 2780 */       if (buf[ptr++] == 97 && buf[ptr++] == 108 && buf[ptr++] == 115 && buf[ptr++] == 101) {
/*      */ 
/*      */ 
/*      */         
/* 2784 */         int ch = buf[ptr] & 0xFF;
/* 2785 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 2786 */           this._inputPtr = ptr;
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2791 */     _matchToken2("false", 1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _matchNull() throws IOException {
/* 2796 */     int ptr = this._inputPtr;
/* 2797 */     if (ptr + 3 < this._inputEnd) {
/* 2798 */       byte[] buf = this._inputBuffer;
/* 2799 */       if (buf[ptr++] == 117 && buf[ptr++] == 108 && buf[ptr++] == 108) {
/*      */ 
/*      */         
/* 2802 */         int ch = buf[ptr] & 0xFF;
/* 2803 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 2804 */           this._inputPtr = ptr;
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2809 */     _matchToken2("null", 1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _matchToken(String matchStr, int i) throws IOException {
/* 2814 */     int len = matchStr.length();
/* 2815 */     if (this._inputPtr + len >= this._inputEnd) {
/* 2816 */       _matchToken2(matchStr, i);
/*      */       return;
/*      */     } 
/*      */     do {
/* 2820 */       if (this._inputBuffer[this._inputPtr] != matchStr.charAt(i)) {
/* 2821 */         _reportInvalidToken(matchStr.substring(0, i));
/*      */       }
/* 2823 */       this._inputPtr++;
/* 2824 */     } while (++i < len);
/*      */     
/* 2826 */     int ch = this._inputBuffer[this._inputPtr] & 0xFF;
/* 2827 */     if (ch >= 48 && ch != 93 && ch != 125) {
/* 2828 */       _checkMatchEnd(matchStr, i, ch);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _matchToken2(String matchStr, int i) throws IOException {
/* 2834 */     int len = matchStr.length();
/*      */     do {
/* 2836 */       if ((this._inputPtr >= this._inputEnd && !_loadMore()) || this._inputBuffer[this._inputPtr] != matchStr.charAt(i))
/*      */       {
/* 2838 */         _reportInvalidToken(matchStr.substring(0, i));
/*      */       }
/* 2840 */       this._inputPtr++;
/* 2841 */     } while (++i < len);
/*      */ 
/*      */     
/* 2844 */     if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/*      */       return;
/*      */     }
/* 2847 */     int ch = this._inputBuffer[this._inputPtr] & 0xFF;
/* 2848 */     if (ch >= 48 && ch != 93 && ch != 125) {
/* 2849 */       _checkMatchEnd(matchStr, i, ch);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _checkMatchEnd(String matchStr, int i, int ch) throws IOException {
/* 2855 */     char c = (char)_decodeCharForError(ch);
/* 2856 */     if (Character.isJavaIdentifierPart(c)) {
/* 2857 */       _reportInvalidToken(matchStr.substring(0, i));
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
/*      */   private final int _skipWS() throws IOException {
/* 2869 */     while (this._inputPtr < this._inputEnd) {
/* 2870 */       int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2871 */       if (i > 32) {
/* 2872 */         if (i == 47 || i == 35) {
/* 2873 */           this._inputPtr--;
/* 2874 */           return _skipWS2();
/*      */         } 
/* 2876 */         return i;
/*      */       } 
/* 2878 */       if (i != 32) {
/* 2879 */         if (i == 10) {
/* 2880 */           this._currInputRow++;
/* 2881 */           this._currInputRowStart = this._inputPtr; continue;
/* 2882 */         }  if (i == 13) {
/* 2883 */           _skipCR(); continue;
/* 2884 */         }  if (i != 9) {
/* 2885 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2889 */     return _skipWS2();
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipWS2() throws IOException {
/* 2894 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2895 */       int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2896 */       if (i > 32) {
/* 2897 */         if (i == 47) {
/* 2898 */           _skipComment();
/*      */           continue;
/*      */         } 
/* 2901 */         if (i == 35 && 
/* 2902 */           _skipYAMLComment()) {
/*      */           continue;
/*      */         }
/*      */         
/* 2906 */         return i;
/*      */       } 
/* 2908 */       if (i != 32) {
/* 2909 */         if (i == 10) {
/* 2910 */           this._currInputRow++;
/* 2911 */           this._currInputRowStart = this._inputPtr; continue;
/* 2912 */         }  if (i == 13) {
/* 2913 */           _skipCR(); continue;
/* 2914 */         }  if (i != 9) {
/* 2915 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2919 */     throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _skipWSOrEnd() throws IOException {
/* 2926 */     if (this._inputPtr >= this._inputEnd && 
/* 2927 */       !_loadMore()) {
/* 2928 */       return _eofAsNextChar();
/*      */     }
/*      */     
/* 2931 */     int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2932 */     if (i > 32) {
/* 2933 */       if (i == 47 || i == 35) {
/* 2934 */         this._inputPtr--;
/* 2935 */         return _skipWSOrEnd2();
/*      */       } 
/* 2937 */       return i;
/*      */     } 
/* 2939 */     if (i != 32) {
/* 2940 */       if (i == 10) {
/* 2941 */         this._currInputRow++;
/* 2942 */         this._currInputRowStart = this._inputPtr;
/* 2943 */       } else if (i == 13) {
/* 2944 */         _skipCR();
/* 2945 */       } else if (i != 9) {
/* 2946 */         _throwInvalidSpace(i);
/*      */       } 
/*      */     }
/*      */     
/* 2950 */     while (this._inputPtr < this._inputEnd) {
/* 2951 */       i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2952 */       if (i > 32) {
/* 2953 */         if (i == 47 || i == 35) {
/* 2954 */           this._inputPtr--;
/* 2955 */           return _skipWSOrEnd2();
/*      */         } 
/* 2957 */         return i;
/*      */       } 
/* 2959 */       if (i != 32) {
/* 2960 */         if (i == 10) {
/* 2961 */           this._currInputRow++;
/* 2962 */           this._currInputRowStart = this._inputPtr; continue;
/* 2963 */         }  if (i == 13) {
/* 2964 */           _skipCR(); continue;
/* 2965 */         }  if (i != 9) {
/* 2966 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 2970 */     return _skipWSOrEnd2();
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipWSOrEnd2() throws IOException {
/* 2975 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 2976 */       int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2977 */       if (i > 32) {
/* 2978 */         if (i == 47) {
/* 2979 */           _skipComment();
/*      */           continue;
/*      */         } 
/* 2982 */         if (i == 35 && 
/* 2983 */           _skipYAMLComment()) {
/*      */           continue;
/*      */         }
/*      */         
/* 2987 */         return i;
/* 2988 */       }  if (i != 32) {
/* 2989 */         if (i == 10) {
/* 2990 */           this._currInputRow++;
/* 2991 */           this._currInputRowStart = this._inputPtr; continue;
/* 2992 */         }  if (i == 13) {
/* 2993 */           _skipCR(); continue;
/* 2994 */         }  if (i != 9) {
/* 2995 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3000 */     return _eofAsNextChar();
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipColon() throws IOException {
/* 3005 */     if (this._inputPtr + 4 >= this._inputEnd) {
/* 3006 */       return _skipColon2(false);
/*      */     }
/*      */     
/* 3009 */     int i = this._inputBuffer[this._inputPtr];
/* 3010 */     if (i == 58) {
/* 3011 */       i = this._inputBuffer[++this._inputPtr];
/* 3012 */       if (i > 32) {
/* 3013 */         if (i == 47 || i == 35) {
/* 3014 */           return _skipColon2(true);
/*      */         }
/* 3016 */         this._inputPtr++;
/* 3017 */         return i;
/*      */       } 
/* 3019 */       if (i == 32 || i == 9) {
/* 3020 */         i = this._inputBuffer[++this._inputPtr];
/* 3021 */         if (i > 32) {
/* 3022 */           if (i == 47 || i == 35) {
/* 3023 */             return _skipColon2(true);
/*      */           }
/* 3025 */           this._inputPtr++;
/* 3026 */           return i;
/*      */         } 
/*      */       } 
/* 3029 */       return _skipColon2(true);
/*      */     } 
/* 3031 */     if (i == 32 || i == 9) {
/* 3032 */       i = this._inputBuffer[++this._inputPtr];
/*      */     }
/* 3034 */     if (i == 58) {
/* 3035 */       i = this._inputBuffer[++this._inputPtr];
/* 3036 */       if (i > 32) {
/* 3037 */         if (i == 47 || i == 35) {
/* 3038 */           return _skipColon2(true);
/*      */         }
/* 3040 */         this._inputPtr++;
/* 3041 */         return i;
/*      */       } 
/* 3043 */       if (i == 32 || i == 9) {
/* 3044 */         i = this._inputBuffer[++this._inputPtr];
/* 3045 */         if (i > 32) {
/* 3046 */           if (i == 47 || i == 35) {
/* 3047 */             return _skipColon2(true);
/*      */           }
/* 3049 */           this._inputPtr++;
/* 3050 */           return i;
/*      */         } 
/*      */       } 
/* 3053 */       return _skipColon2(true);
/*      */     } 
/* 3055 */     return _skipColon2(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipColon2(boolean gotColon) throws IOException {
/* 3060 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 3061 */       int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */       
/* 3063 */       if (i > 32) {
/* 3064 */         if (i == 47) {
/* 3065 */           _skipComment();
/*      */           continue;
/*      */         } 
/* 3068 */         if (i == 35 && 
/* 3069 */           _skipYAMLComment()) {
/*      */           continue;
/*      */         }
/*      */         
/* 3073 */         if (gotColon) {
/* 3074 */           return i;
/*      */         }
/* 3076 */         if (i != 58) {
/* 3077 */           _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
/*      */         }
/* 3079 */         gotColon = true; continue;
/* 3080 */       }  if (i != 32) {
/* 3081 */         if (i == 10) {
/* 3082 */           this._currInputRow++;
/* 3083 */           this._currInputRowStart = this._inputPtr; continue;
/* 3084 */         }  if (i == 13) {
/* 3085 */           _skipCR(); continue;
/* 3086 */         }  if (i != 9) {
/* 3087 */           _throwInvalidSpace(i);
/*      */         }
/*      */       } 
/*      */     } 
/* 3091 */     _reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
/*      */     
/* 3093 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _skipComment() throws IOException {
/* 3098 */     if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 3099 */       _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
/*      */     }
/*      */     
/* 3102 */     if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/* 3103 */       _reportInvalidEOF(" in a comment", null);
/*      */     }
/* 3105 */     int c = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3106 */     if (c == 47) {
/* 3107 */       _skipLine();
/* 3108 */     } else if (c == 42) {
/* 3109 */       _skipCComment();
/*      */     } else {
/* 3111 */       _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipCComment() throws IOException {
/* 3118 */     int[] codes = CharTypes.getInputCodeComment();
/*      */ 
/*      */ 
/*      */     
/* 3122 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 3123 */       int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3124 */       int code = codes[i];
/* 3125 */       if (code != 0) {
/* 3126 */         switch (code) {
/*      */           case 42:
/* 3128 */             if (this._inputPtr >= this._inputEnd && !_loadMore()) {
/*      */               break;
/*      */             }
/* 3131 */             if (this._inputBuffer[this._inputPtr] == 47) {
/* 3132 */               this._inputPtr++;
/*      */               return;
/*      */             } 
/*      */             continue;
/*      */           case 10:
/* 3137 */             this._currInputRow++;
/* 3138 */             this._currInputRowStart = this._inputPtr;
/*      */             continue;
/*      */           case 13:
/* 3141 */             _skipCR();
/*      */             continue;
/*      */           case 2:
/* 3144 */             _skipUtf8_2();
/*      */             continue;
/*      */           case 3:
/* 3147 */             _skipUtf8_3();
/*      */             continue;
/*      */           case 4:
/* 3150 */             _skipUtf8_4(i);
/*      */             continue;
/*      */         } 
/*      */         
/* 3154 */         _reportInvalidChar(i);
/*      */       } 
/*      */     } 
/*      */     
/* 3158 */     _reportInvalidEOF(" in a comment", null);
/*      */   }
/*      */ 
/*      */   
/*      */   private final boolean _skipYAMLComment() throws IOException {
/* 3163 */     if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
/* 3164 */       return false;
/*      */     }
/* 3166 */     _skipLine();
/* 3167 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipLine() throws IOException {
/* 3177 */     int[] codes = CharTypes.getInputCodeComment();
/* 3178 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 3179 */       int i = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3180 */       int code = codes[i];
/* 3181 */       if (code != 0) {
/* 3182 */         switch (code) {
/*      */           case 10:
/* 3184 */             this._currInputRow++;
/* 3185 */             this._currInputRowStart = this._inputPtr;
/*      */             return;
/*      */           case 13:
/* 3188 */             _skipCR();
/*      */             return;
/*      */           case 42:
/*      */             continue;
/*      */           case 2:
/* 3193 */             _skipUtf8_2();
/*      */             continue;
/*      */           case 3:
/* 3196 */             _skipUtf8_3();
/*      */             continue;
/*      */           case 4:
/* 3199 */             _skipUtf8_4(i);
/*      */             continue;
/*      */         } 
/* 3202 */         if (code < 0)
/*      */         {
/* 3204 */           _reportInvalidChar(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char _decodeEscaped() throws IOException {
/* 3214 */     if (this._inputPtr >= this._inputEnd && 
/* 3215 */       !_loadMore()) {
/* 3216 */       _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
/*      */     }
/*      */     
/* 3219 */     int c = this._inputBuffer[this._inputPtr++];
/*      */     
/* 3221 */     switch (c) {
/*      */       
/*      */       case 98:
/* 3224 */         return '\b';
/*      */       case 116:
/* 3226 */         return '\t';
/*      */       case 110:
/* 3228 */         return '\n';
/*      */       case 102:
/* 3230 */         return '\f';
/*      */       case 114:
/* 3232 */         return '\r';
/*      */ 
/*      */       
/*      */       case 34:
/*      */       case 47:
/*      */       case 92:
/* 3238 */         return (char)c;
/*      */       
/*      */       case 117:
/*      */         break;
/*      */       
/*      */       default:
/* 3244 */         return _handleUnrecognizedCharacterEscape((char)_decodeCharForError(c));
/*      */     } 
/*      */ 
/*      */     
/* 3248 */     int value = 0;
/* 3249 */     for (int i = 0; i < 4; i++) {
/* 3250 */       if (this._inputPtr >= this._inputEnd && 
/* 3251 */         !_loadMore()) {
/* 3252 */         _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
/*      */       }
/*      */       
/* 3255 */       int ch = this._inputBuffer[this._inputPtr++];
/* 3256 */       int digit = CharTypes.charToHex(ch);
/* 3257 */       if (digit < 0) {
/* 3258 */         _reportUnexpectedChar(ch, "expected a hex-digit for character escape sequence");
/*      */       }
/* 3260 */       value = value << 4 | digit;
/*      */     } 
/* 3262 */     return (char)value;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int _decodeCharForError(int firstByte) throws IOException {
/* 3267 */     int c = firstByte & 0xFF;
/* 3268 */     if (c > 127) {
/*      */       int needed;
/*      */ 
/*      */       
/* 3272 */       if ((c & 0xE0) == 192) {
/* 3273 */         c &= 0x1F;
/* 3274 */         needed = 1;
/* 3275 */       } else if ((c & 0xF0) == 224) {
/* 3276 */         c &= 0xF;
/* 3277 */         needed = 2;
/* 3278 */       } else if ((c & 0xF8) == 240) {
/*      */         
/* 3280 */         c &= 0x7;
/* 3281 */         needed = 3;
/*      */       } else {
/* 3283 */         _reportInvalidInitial(c & 0xFF);
/* 3284 */         needed = 1;
/*      */       } 
/*      */       
/* 3287 */       int d = nextByte();
/* 3288 */       if ((d & 0xC0) != 128) {
/* 3289 */         _reportInvalidOther(d & 0xFF);
/*      */       }
/* 3291 */       c = c << 6 | d & 0x3F;
/*      */       
/* 3293 */       if (needed > 1) {
/* 3294 */         d = nextByte();
/* 3295 */         if ((d & 0xC0) != 128) {
/* 3296 */           _reportInvalidOther(d & 0xFF);
/*      */         }
/* 3298 */         c = c << 6 | d & 0x3F;
/* 3299 */         if (needed > 2) {
/* 3300 */           d = nextByte();
/* 3301 */           if ((d & 0xC0) != 128) {
/* 3302 */             _reportInvalidOther(d & 0xFF);
/*      */           }
/* 3304 */           c = c << 6 | d & 0x3F;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3308 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _decodeUtf8_2(int c) throws IOException {
/* 3319 */     if (this._inputPtr >= this._inputEnd) {
/* 3320 */       _loadMoreGuaranteed();
/*      */     }
/* 3322 */     int d = this._inputBuffer[this._inputPtr++];
/* 3323 */     if ((d & 0xC0) != 128) {
/* 3324 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3326 */     return (c & 0x1F) << 6 | d & 0x3F;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _decodeUtf8_3(int c1) throws IOException {
/* 3331 */     if (this._inputPtr >= this._inputEnd) {
/* 3332 */       _loadMoreGuaranteed();
/*      */     }
/* 3334 */     c1 &= 0xF;
/* 3335 */     int d = this._inputBuffer[this._inputPtr++];
/* 3336 */     if ((d & 0xC0) != 128) {
/* 3337 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3339 */     int c = c1 << 6 | d & 0x3F;
/* 3340 */     if (this._inputPtr >= this._inputEnd) {
/* 3341 */       _loadMoreGuaranteed();
/*      */     }
/* 3343 */     d = this._inputBuffer[this._inputPtr++];
/* 3344 */     if ((d & 0xC0) != 128) {
/* 3345 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3347 */     c = c << 6 | d & 0x3F;
/* 3348 */     return c;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _decodeUtf8_3fast(int c1) throws IOException {
/* 3353 */     c1 &= 0xF;
/* 3354 */     int d = this._inputBuffer[this._inputPtr++];
/* 3355 */     if ((d & 0xC0) != 128) {
/* 3356 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3358 */     int c = c1 << 6 | d & 0x3F;
/* 3359 */     d = this._inputBuffer[this._inputPtr++];
/* 3360 */     if ((d & 0xC0) != 128) {
/* 3361 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3363 */     c = c << 6 | d & 0x3F;
/* 3364 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _decodeUtf8_4(int c) throws IOException {
/* 3373 */     if (this._inputPtr >= this._inputEnd) {
/* 3374 */       _loadMoreGuaranteed();
/*      */     }
/* 3376 */     int d = this._inputBuffer[this._inputPtr++];
/* 3377 */     if ((d & 0xC0) != 128) {
/* 3378 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3380 */     c = (c & 0x7) << 6 | d & 0x3F;
/*      */     
/* 3382 */     if (this._inputPtr >= this._inputEnd) {
/* 3383 */       _loadMoreGuaranteed();
/*      */     }
/* 3385 */     d = this._inputBuffer[this._inputPtr++];
/* 3386 */     if ((d & 0xC0) != 128) {
/* 3387 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3389 */     c = c << 6 | d & 0x3F;
/* 3390 */     if (this._inputPtr >= this._inputEnd) {
/* 3391 */       _loadMoreGuaranteed();
/*      */     }
/* 3393 */     d = this._inputBuffer[this._inputPtr++];
/* 3394 */     if ((d & 0xC0) != 128) {
/* 3395 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3401 */     return (c << 6 | d & 0x3F) - 65536;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _skipUtf8_2() throws IOException {
/* 3406 */     if (this._inputPtr >= this._inputEnd) {
/* 3407 */       _loadMoreGuaranteed();
/*      */     }
/* 3409 */     int c = this._inputBuffer[this._inputPtr++];
/* 3410 */     if ((c & 0xC0) != 128) {
/* 3411 */       _reportInvalidOther(c & 0xFF, this._inputPtr);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipUtf8_3() throws IOException {
/* 3420 */     if (this._inputPtr >= this._inputEnd) {
/* 3421 */       _loadMoreGuaranteed();
/*      */     }
/*      */     
/* 3424 */     int c = this._inputBuffer[this._inputPtr++];
/* 3425 */     if ((c & 0xC0) != 128) {
/* 3426 */       _reportInvalidOther(c & 0xFF, this._inputPtr);
/*      */     }
/* 3428 */     if (this._inputPtr >= this._inputEnd) {
/* 3429 */       _loadMoreGuaranteed();
/*      */     }
/* 3431 */     c = this._inputBuffer[this._inputPtr++];
/* 3432 */     if ((c & 0xC0) != 128) {
/* 3433 */       _reportInvalidOther(c & 0xFF, this._inputPtr);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _skipUtf8_4(int c) throws IOException {
/* 3439 */     if (this._inputPtr >= this._inputEnd) {
/* 3440 */       _loadMoreGuaranteed();
/*      */     }
/* 3442 */     int d = this._inputBuffer[this._inputPtr++];
/* 3443 */     if ((d & 0xC0) != 128) {
/* 3444 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3446 */     if (this._inputPtr >= this._inputEnd) {
/* 3447 */       _loadMoreGuaranteed();
/*      */     }
/* 3449 */     d = this._inputBuffer[this._inputPtr++];
/* 3450 */     if ((d & 0xC0) != 128) {
/* 3451 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 3453 */     if (this._inputPtr >= this._inputEnd) {
/* 3454 */       _loadMoreGuaranteed();
/*      */     }
/* 3456 */     d = this._inputBuffer[this._inputPtr++];
/* 3457 */     if ((d & 0xC0) != 128) {
/* 3458 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
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
/*      */   protected final void _skipCR() throws IOException {
/* 3474 */     if ((this._inputPtr < this._inputEnd || _loadMore()) && 
/* 3475 */       this._inputBuffer[this._inputPtr] == 10) {
/* 3476 */       this._inputPtr++;
/*      */     }
/*      */     
/* 3479 */     this._currInputRow++;
/* 3480 */     this._currInputRowStart = this._inputPtr;
/*      */   }
/*      */ 
/*      */   
/*      */   private int nextByte() throws IOException {
/* 3485 */     if (this._inputPtr >= this._inputEnd) {
/* 3486 */       _loadMoreGuaranteed();
/*      */     }
/* 3488 */     return this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidToken(String matchedPart, int ptr) throws IOException {
/* 3498 */     this._inputPtr = ptr;
/* 3499 */     _reportInvalidToken(matchedPart, "'null', 'true', 'false' or NaN");
/*      */   }
/*      */   
/*      */   protected void _reportInvalidToken(String matchedPart) throws IOException {
/* 3503 */     _reportInvalidToken(matchedPart, "'null', 'true', 'false' or NaN");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidToken(String matchedPart, String msg) throws IOException {
/* 3512 */     StringBuilder sb = new StringBuilder(matchedPart);
/* 3513 */     while (this._inputPtr < this._inputEnd || _loadMore()) {
/* 3514 */       int i = this._inputBuffer[this._inputPtr++];
/* 3515 */       char c = (char)_decodeCharForError(i);
/* 3516 */       if (!Character.isJavaIdentifierPart(c)) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 3521 */       sb.append(c);
/* 3522 */       if (sb.length() >= 256) {
/* 3523 */         sb.append("...");
/*      */         break;
/*      */       } 
/*      */     } 
/* 3527 */     _reportError("Unrecognized token '%s': was expecting %s", sb, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidChar(int c) throws JsonParseException {
/* 3533 */     if (c < 32) {
/* 3534 */       _throwInvalidSpace(c);
/*      */     }
/* 3536 */     _reportInvalidInitial(c);
/*      */   }
/*      */   
/*      */   protected void _reportInvalidInitial(int mask) throws JsonParseException {
/* 3540 */     _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(mask));
/*      */   }
/*      */   
/*      */   protected void _reportInvalidOther(int mask) throws JsonParseException {
/* 3544 */     _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(mask));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidOther(int mask, int ptr) throws JsonParseException {
/* 3550 */     this._inputPtr = ptr;
/* 3551 */     _reportInvalidOther(mask);
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
/*      */   protected final byte[] _decodeBase64(Base64Variant b64variant) throws IOException {
/* 3567 */     ByteArrayBuilder builder = _getByteArrayBuilder();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 3573 */       if (this._inputPtr >= this._inputEnd) {
/* 3574 */         _loadMoreGuaranteed();
/*      */       }
/* 3576 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3577 */       if (ch > 32) {
/* 3578 */         int bits = b64variant.decodeBase64Char(ch);
/* 3579 */         if (bits < 0) {
/* 3580 */           if (ch == 34) {
/* 3581 */             return builder.toByteArray();
/*      */           }
/* 3583 */           bits = _decodeBase64Escape(b64variant, ch, 0);
/* 3584 */           if (bits < 0) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 3588 */         int decodedData = bits;
/*      */ 
/*      */ 
/*      */         
/* 3592 */         if (this._inputPtr >= this._inputEnd) {
/* 3593 */           _loadMoreGuaranteed();
/*      */         }
/* 3595 */         ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3596 */         bits = b64variant.decodeBase64Char(ch);
/* 3597 */         if (bits < 0) {
/* 3598 */           bits = _decodeBase64Escape(b64variant, ch, 1);
/*      */         }
/* 3600 */         decodedData = decodedData << 6 | bits;
/*      */ 
/*      */         
/* 3603 */         if (this._inputPtr >= this._inputEnd) {
/* 3604 */           _loadMoreGuaranteed();
/*      */         }
/* 3606 */         ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3607 */         bits = b64variant.decodeBase64Char(ch);
/*      */ 
/*      */         
/* 3610 */         if (bits < 0) {
/* 3611 */           if (bits != -2) {
/*      */             
/* 3613 */             if (ch == 34 && !b64variant.usesPadding()) {
/* 3614 */               decodedData >>= 4;
/* 3615 */               builder.append(decodedData);
/* 3616 */               return builder.toByteArray();
/*      */             } 
/* 3618 */             bits = _decodeBase64Escape(b64variant, ch, 2);
/*      */           } 
/* 3620 */           if (bits == -2) {
/*      */             
/* 3622 */             if (this._inputPtr >= this._inputEnd) {
/* 3623 */               _loadMoreGuaranteed();
/*      */             }
/* 3625 */             ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3626 */             if (!b64variant.usesPaddingChar(ch)) {
/* 3627 */               throw reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
/*      */             }
/*      */             
/* 3630 */             decodedData >>= 4;
/* 3631 */             builder.append(decodedData);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 3636 */         decodedData = decodedData << 6 | bits;
/*      */         
/* 3638 */         if (this._inputPtr >= this._inputEnd) {
/* 3639 */           _loadMoreGuaranteed();
/*      */         }
/* 3641 */         ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 3642 */         bits = b64variant.decodeBase64Char(ch);
/* 3643 */         if (bits < 0) {
/* 3644 */           if (bits != -2) {
/*      */             
/* 3646 */             if (ch == 34 && !b64variant.usesPadding()) {
/* 3647 */               decodedData >>= 2;
/* 3648 */               builder.appendTwoBytes(decodedData);
/* 3649 */               return builder.toByteArray();
/*      */             } 
/* 3651 */             bits = _decodeBase64Escape(b64variant, ch, 3);
/*      */           } 
/* 3653 */           if (bits == -2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3660 */             decodedData >>= 2;
/* 3661 */             builder.appendTwoBytes(decodedData);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 3666 */         decodedData = decodedData << 6 | bits;
/* 3667 */         builder.appendThreeBytes(decodedData);
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
/*      */   public JsonLocation getTokenLocation() {
/* 3681 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 3682 */       long total = this._currInputProcessed + (this._nameStartOffset - 1);
/* 3683 */       return new JsonLocation(_getSourceReference(), total, -1L, this._nameStartRow, this._nameStartCol);
/*      */     } 
/*      */     
/* 3686 */     return new JsonLocation(_getSourceReference(), this._tokenInputTotal - 1L, -1L, this._tokenInputRow, this._tokenInputCol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonLocation getCurrentLocation() {
/* 3694 */     int col = this._inputPtr - this._currInputRowStart + 1;
/* 3695 */     return new JsonLocation(_getSourceReference(), this._currInputProcessed + this._inputPtr, -1L, this._currInputRow, col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _updateLocation() {
/* 3703 */     this._tokenInputRow = this._currInputRow;
/* 3704 */     int ptr = this._inputPtr;
/* 3705 */     this._tokenInputTotal = this._currInputProcessed + ptr;
/* 3706 */     this._tokenInputCol = ptr - this._currInputRowStart;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _updateNameLocation() {
/* 3712 */     this._nameStartRow = this._currInputRow;
/* 3713 */     int ptr = this._inputPtr;
/* 3714 */     this._nameStartOffset = ptr;
/* 3715 */     this._nameStartCol = ptr - this._currInputRowStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _closeScope(int i) throws JsonParseException {
/* 3725 */     if (i == 125) {
/* 3726 */       _closeObjectScope();
/* 3727 */       return this._currToken = JsonToken.END_OBJECT;
/*      */     } 
/* 3729 */     _closeArrayScope();
/* 3730 */     return this._currToken = JsonToken.END_ARRAY;
/*      */   }
/*      */   
/*      */   private final void _closeArrayScope() throws JsonParseException {
/* 3734 */     _updateLocation();
/* 3735 */     if (!this._parsingContext.inArray()) {
/* 3736 */       _reportMismatchedEndMarker(93, '}');
/*      */     }
/* 3738 */     this._parsingContext = this._parsingContext.clearAndGetParent();
/*      */   }
/*      */   
/*      */   private final void _closeObjectScope() throws JsonParseException {
/* 3742 */     _updateLocation();
/* 3743 */     if (!this._parsingContext.inObject()) {
/* 3744 */       _reportMismatchedEndMarker(125, ']');
/*      */     }
/* 3746 */     this._parsingContext = this._parsingContext.clearAndGetParent();
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\UTF8StreamJsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */