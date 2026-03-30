/*      */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Writer;
/*      */ import java.util.Arrays;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.base.ParserBase;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
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
/*      */ public class UTF8DataInputJsonParser
/*      */   extends ParserBase
/*      */ {
/*      */   static final byte BYTE_LF = 10;
/*   41 */   private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
/*      */ 
/*      */ 
/*      */   
/*   45 */   protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
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
/*   74 */   protected int[] _quadBuffer = new int[16];
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
/*      */   private int _quad1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DataInput _inputData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   protected int _nextByte = -1;
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
/*      */   public UTF8DataInputJsonParser(IOContext ctxt, int features, DataInput inputData, ObjectCodec codec, ByteQuadsCanonicalizer sym, int firstByte) {
/*  112 */     super(ctxt, features);
/*  113 */     this._objectCodec = codec;
/*  114 */     this._symbols = sym;
/*  115 */     this._inputData = inputData;
/*  116 */     this._nextByte = firstByte;
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectCodec getCodec() {
/*  121 */     return this._objectCodec;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCodec(ObjectCodec c) {
/*  126 */     this._objectCodec = c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int releaseBuffered(OutputStream out) throws IOException {
/*  137 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getInputSource() {
/*  142 */     return this._inputData;
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
/*      */   protected void _closeInput() throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _releaseBuffers() throws IOException {
/*  163 */     super._releaseBuffers();
/*      */     
/*  165 */     this._symbols.release();
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
/*  177 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  178 */       if (this._tokenIncomplete) {
/*  179 */         this._tokenIncomplete = false;
/*  180 */         return _finishAndReturnString();
/*      */       } 
/*  182 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  184 */     return _getText2(this._currToken);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getText(Writer writer) throws IOException {
/*  190 */     JsonToken t = this._currToken;
/*  191 */     if (t == JsonToken.VALUE_STRING) {
/*  192 */       if (this._tokenIncomplete) {
/*  193 */         this._tokenIncomplete = false;
/*  194 */         _finishString();
/*      */       } 
/*  196 */       return this._textBuffer.contentsToWriter(writer);
/*      */     } 
/*  198 */     if (t == JsonToken.FIELD_NAME) {
/*  199 */       String n = this._parsingContext.getCurrentName();
/*  200 */       writer.write(n);
/*  201 */       return n.length();
/*      */     } 
/*  203 */     if (t != null) {
/*  204 */       if (t.isNumeric()) {
/*  205 */         return this._textBuffer.contentsToWriter(writer);
/*      */       }
/*  207 */       char[] ch = t.asCharArray();
/*  208 */       writer.write(ch);
/*  209 */       return ch.length;
/*      */     } 
/*  211 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValueAsString() throws IOException {
/*  218 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  219 */       if (this._tokenIncomplete) {
/*  220 */         this._tokenIncomplete = false;
/*  221 */         return _finishAndReturnString();
/*      */       } 
/*  223 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  225 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  226 */       return getCurrentName();
/*      */     }
/*  228 */     return super.getValueAsString(null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValueAsString(String defValue) throws IOException {
/*  234 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  235 */       if (this._tokenIncomplete) {
/*  236 */         this._tokenIncomplete = false;
/*  237 */         return _finishAndReturnString();
/*      */       } 
/*  239 */       return this._textBuffer.contentsAsString();
/*      */     } 
/*  241 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  242 */       return getCurrentName();
/*      */     }
/*  244 */     return super.getValueAsString(defValue);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getValueAsInt() throws IOException {
/*  250 */     JsonToken t = this._currToken;
/*  251 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/*      */       
/*  253 */       if ((this._numTypesValid & 0x1) == 0) {
/*  254 */         if (this._numTypesValid == 0) {
/*  255 */           return _parseIntValue();
/*      */         }
/*  257 */         if ((this._numTypesValid & 0x1) == 0) {
/*  258 */           convertNumberToInt();
/*      */         }
/*      */       } 
/*  261 */       return this._numberInt;
/*      */     } 
/*  263 */     return super.getValueAsInt(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getValueAsInt(int defValue) throws IOException {
/*  269 */     JsonToken t = this._currToken;
/*  270 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/*      */       
/*  272 */       if ((this._numTypesValid & 0x1) == 0) {
/*  273 */         if (this._numTypesValid == 0) {
/*  274 */           return _parseIntValue();
/*      */         }
/*  276 */         if ((this._numTypesValid & 0x1) == 0) {
/*  277 */           convertNumberToInt();
/*      */         }
/*      */       } 
/*  280 */       return this._numberInt;
/*      */     } 
/*  282 */     return super.getValueAsInt(defValue);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final String _getText2(JsonToken t) {
/*  287 */     if (t == null) {
/*  288 */       return null;
/*      */     }
/*  290 */     switch (t.id()) {
/*      */       case 5:
/*  292 */         return this._parsingContext.getCurrentName();
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*  298 */         return this._textBuffer.contentsAsString();
/*      */     } 
/*  300 */     return t.asString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getTextCharacters() throws IOException {
/*  307 */     if (this._currToken != null) {
/*  308 */       switch (this._currToken.id()) {
/*      */         
/*      */         case 5:
/*  311 */           if (!this._nameCopied) {
/*  312 */             String name = this._parsingContext.getCurrentName();
/*  313 */             int nameLen = name.length();
/*  314 */             if (this._nameCopyBuffer == null) {
/*  315 */               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(nameLen);
/*  316 */             } else if (this._nameCopyBuffer.length < nameLen) {
/*  317 */               this._nameCopyBuffer = new char[nameLen];
/*      */             } 
/*  319 */             name.getChars(0, nameLen, this._nameCopyBuffer, 0);
/*  320 */             this._nameCopied = true;
/*      */           } 
/*  322 */           return this._nameCopyBuffer;
/*      */         
/*      */         case 6:
/*  325 */           if (this._tokenIncomplete) {
/*  326 */             this._tokenIncomplete = false;
/*  327 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  332 */           return this._textBuffer.getTextBuffer();
/*      */       } 
/*      */       
/*  335 */       return this._currToken.asCharArray();
/*      */     } 
/*      */     
/*  338 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextLength() throws IOException {
/*  344 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  345 */       if (this._tokenIncomplete) {
/*  346 */         this._tokenIncomplete = false;
/*  347 */         _finishString();
/*      */       } 
/*  349 */       return this._textBuffer.size();
/*      */     } 
/*  351 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  352 */       return this._parsingContext.getCurrentName().length();
/*      */     }
/*  354 */     if (this._currToken != null) {
/*  355 */       if (this._currToken.isNumeric()) {
/*  356 */         return this._textBuffer.size();
/*      */       }
/*  358 */       return (this._currToken.asCharArray()).length;
/*      */     } 
/*  360 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextOffset() throws IOException {
/*  367 */     if (this._currToken != null) {
/*  368 */       switch (this._currToken.id()) {
/*      */         case 5:
/*  370 */           return 0;
/*      */         case 6:
/*  372 */           if (this._tokenIncomplete) {
/*  373 */             this._tokenIncomplete = false;
/*  374 */             _finishString();
/*      */           } 
/*      */         
/*      */         case 7:
/*      */         case 8:
/*  379 */           return this._textBuffer.getTextOffset();
/*      */       } 
/*      */     
/*      */     }
/*  383 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
/*  389 */     if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null))
/*      */     {
/*  391 */       _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  396 */     if (this._tokenIncomplete) {
/*      */       try {
/*  398 */         this._binaryValue = _decodeBase64(b64variant);
/*  399 */       } catch (IllegalArgumentException iae) {
/*  400 */         throw _constructError("Failed to decode VALUE_STRING as base64 (" + b64variant + "): " + iae.getMessage());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  405 */       this._tokenIncomplete = false;
/*      */     }
/*  407 */     else if (this._binaryValue == null) {
/*      */       
/*  409 */       ByteArrayBuilder builder = _getByteArrayBuilder();
/*  410 */       _decodeBase64(getText(), builder, b64variant);
/*  411 */       this._binaryValue = builder.toByteArray();
/*      */     } 
/*      */     
/*  414 */     return this._binaryValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
/*  421 */     if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
/*  422 */       byte[] b = getBinaryValue(b64variant);
/*  423 */       out.write(b);
/*  424 */       return b.length;
/*      */     } 
/*      */     
/*  427 */     byte[] buf = this._ioContext.allocBase64Buffer();
/*      */     try {
/*  429 */       return _readBinary(b64variant, out, buf);
/*      */     } finally {
/*  431 */       this._ioContext.releaseBase64Buffer(buf);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _readBinary(Base64Variant b64variant, OutputStream out, byte[] buffer) throws IOException {
/*  438 */     int outputPtr = 0;
/*  439 */     int outputEnd = buffer.length - 3;
/*  440 */     int outputCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  446 */       int ch = this._inputData.readUnsignedByte();
/*  447 */       if (ch > 32) {
/*  448 */         int bits = b64variant.decodeBase64Char(ch);
/*  449 */         if (bits < 0) {
/*  450 */           if (ch == 34) {
/*      */             break;
/*      */           }
/*  453 */           bits = _decodeBase64Escape(b64variant, ch, 0);
/*  454 */           if (bits < 0) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  460 */         if (outputPtr > outputEnd) {
/*  461 */           outputCount += outputPtr;
/*  462 */           out.write(buffer, 0, outputPtr);
/*  463 */           outputPtr = 0;
/*      */         } 
/*      */         
/*  466 */         int decodedData = bits;
/*      */ 
/*      */         
/*  469 */         ch = this._inputData.readUnsignedByte();
/*  470 */         bits = b64variant.decodeBase64Char(ch);
/*  471 */         if (bits < 0) {
/*  472 */           bits = _decodeBase64Escape(b64variant, ch, 1);
/*      */         }
/*  474 */         decodedData = decodedData << 6 | bits;
/*      */ 
/*      */         
/*  477 */         ch = this._inputData.readUnsignedByte();
/*  478 */         bits = b64variant.decodeBase64Char(ch);
/*      */ 
/*      */         
/*  481 */         if (bits < 0) {
/*  482 */           if (bits != -2) {
/*      */             
/*  484 */             if (ch == 34 && !b64variant.usesPadding()) {
/*  485 */               decodedData >>= 4;
/*  486 */               buffer[outputPtr++] = (byte)decodedData;
/*      */               break;
/*      */             } 
/*  489 */             bits = _decodeBase64Escape(b64variant, ch, 2);
/*      */           } 
/*  491 */           if (bits == -2) {
/*      */             
/*  493 */             ch = this._inputData.readUnsignedByte();
/*  494 */             if (!b64variant.usesPaddingChar(ch)) {
/*  495 */               throw reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
/*      */             }
/*      */             
/*  498 */             decodedData >>= 4;
/*  499 */             buffer[outputPtr++] = (byte)decodedData;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  504 */         decodedData = decodedData << 6 | bits;
/*      */         
/*  506 */         ch = this._inputData.readUnsignedByte();
/*  507 */         bits = b64variant.decodeBase64Char(ch);
/*  508 */         if (bits < 0) {
/*  509 */           if (bits != -2) {
/*      */             
/*  511 */             if (ch == 34 && !b64variant.usesPadding()) {
/*  512 */               decodedData >>= 2;
/*  513 */               buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  514 */               buffer[outputPtr++] = (byte)decodedData;
/*      */               break;
/*      */             } 
/*  517 */             bits = _decodeBase64Escape(b64variant, ch, 3);
/*      */           } 
/*  519 */           if (bits == -2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  526 */             decodedData >>= 2;
/*  527 */             buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  528 */             buffer[outputPtr++] = (byte)decodedData;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  533 */         decodedData = decodedData << 6 | bits;
/*  534 */         buffer[outputPtr++] = (byte)(decodedData >> 16);
/*  535 */         buffer[outputPtr++] = (byte)(decodedData >> 8);
/*  536 */         buffer[outputPtr++] = (byte)decodedData;
/*      */       } 
/*  538 */     }  this._tokenIncomplete = false;
/*  539 */     if (outputPtr > 0) {
/*  540 */       outputCount += outputPtr;
/*  541 */       out.write(buffer, 0, outputPtr);
/*      */     } 
/*  543 */     return outputCount;
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
/*  563 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  564 */       return _nextAfterName();
/*      */     }
/*      */ 
/*      */     
/*  568 */     this._numTypesValid = 0;
/*  569 */     if (this._tokenIncomplete) {
/*  570 */       _skipString();
/*      */     }
/*  572 */     int i = _skipWSOrEnd();
/*  573 */     if (i < 0) {
/*      */       
/*  575 */       close();
/*  576 */       return this._currToken = null;
/*      */     } 
/*      */     
/*  579 */     this._binaryValue = null;
/*  580 */     this._tokenInputRow = this._currInputRow;
/*      */ 
/*      */     
/*  583 */     if (i == 93 || i == 125) {
/*  584 */       _closeScope(i);
/*  585 */       return this._currToken;
/*      */     } 
/*      */ 
/*      */     
/*  589 */     if (this._parsingContext.expectComma()) {
/*  590 */       if (i != 44) {
/*  591 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */       }
/*  593 */       i = _skipWS();
/*      */ 
/*      */       
/*  596 */       if (JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (
/*  597 */         i == 93 || i == 125)) {
/*  598 */         _closeScope(i);
/*  599 */         return this._currToken;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  608 */     if (!this._parsingContext.inObject()) {
/*  609 */       return _nextTokenNotInObject(i);
/*      */     }
/*      */     
/*  612 */     String n = _parseName(i);
/*  613 */     this._parsingContext.setCurrentName(n);
/*  614 */     this._currToken = JsonToken.FIELD_NAME;
/*      */     
/*  616 */     i = _skipColon();
/*      */ 
/*      */     
/*  619 */     if (i == 34) {
/*  620 */       this._tokenIncomplete = true;
/*  621 */       this._nextToken = JsonToken.VALUE_STRING;
/*  622 */       return this._currToken;
/*      */     } 
/*      */ 
/*      */     
/*  626 */     switch (i)
/*      */     { case 45:
/*  628 */         t = _parseNegNumber();
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
/*      */ 
/*      */         
/*  669 */         this._nextToken = t;
/*  670 */         return this._currToken;case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return this._currToken;case 102: _matchToken("false", 1); t = JsonToken.VALUE_FALSE; this._nextToken = t; return this._currToken;case 110: _matchToken("null", 1); t = JsonToken.VALUE_NULL; this._nextToken = t; return this._currToken;case 116: _matchToken("true", 1); t = JsonToken.VALUE_TRUE; this._nextToken = t; return this._currToken;case 91: t = JsonToken.START_ARRAY; this._nextToken = t; return this._currToken;case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return this._currToken; }  JsonToken t = _handleUnexpectedValue(i); this._nextToken = t; return this._currToken;
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _nextTokenNotInObject(int i) throws IOException {
/*  675 */     if (i == 34) {
/*  676 */       this._tokenIncomplete = true;
/*  677 */       return this._currToken = JsonToken.VALUE_STRING;
/*      */     } 
/*  679 */     switch (i) {
/*      */       case 91:
/*  681 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  682 */         return this._currToken = JsonToken.START_ARRAY;
/*      */       case 123:
/*  684 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*  685 */         return this._currToken = JsonToken.START_OBJECT;
/*      */       case 116:
/*  687 */         _matchToken("true", 1);
/*  688 */         return this._currToken = JsonToken.VALUE_TRUE;
/*      */       case 102:
/*  690 */         _matchToken("false", 1);
/*  691 */         return this._currToken = JsonToken.VALUE_FALSE;
/*      */       case 110:
/*  693 */         _matchToken("null", 1);
/*  694 */         return this._currToken = JsonToken.VALUE_NULL;
/*      */       case 45:
/*  696 */         return this._currToken = _parseNegNumber();
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
/*  711 */         return this._currToken = _parsePosNumber(i);
/*      */     } 
/*  713 */     return this._currToken = _handleUnexpectedValue(i);
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _nextAfterName() {
/*  718 */     this._nameCopied = false;
/*  719 */     JsonToken t = this._nextToken;
/*  720 */     this._nextToken = null;
/*      */ 
/*      */     
/*  723 */     if (t == JsonToken.START_ARRAY) {
/*  724 */       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  725 */     } else if (t == JsonToken.START_OBJECT) {
/*  726 */       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */     } 
/*  728 */     return this._currToken = t;
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishToken() throws IOException {
/*  733 */     if (this._tokenIncomplete) {
/*  734 */       this._tokenIncomplete = false;
/*  735 */       _finishString();
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
/*      */   
/*      */   public String nextFieldName() throws IOException {
/*  753 */     this._numTypesValid = 0;
/*  754 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  755 */       _nextAfterName();
/*  756 */       return null;
/*      */     } 
/*  758 */     if (this._tokenIncomplete) {
/*  759 */       _skipString();
/*      */     }
/*  761 */     int i = _skipWS();
/*  762 */     this._binaryValue = null;
/*  763 */     this._tokenInputRow = this._currInputRow;
/*      */     
/*  765 */     if (i == 93 || i == 125) {
/*  766 */       _closeScope(i);
/*  767 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  771 */     if (this._parsingContext.expectComma()) {
/*  772 */       if (i != 44) {
/*  773 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */       }
/*  775 */       i = _skipWS();
/*      */ 
/*      */       
/*  778 */       if (JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (
/*  779 */         i == 93 || i == 125)) {
/*  780 */         _closeScope(i);
/*  781 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  786 */     if (!this._parsingContext.inObject()) {
/*  787 */       _nextTokenNotInObject(i);
/*  788 */       return null;
/*      */     } 
/*      */     
/*  791 */     String nameStr = _parseName(i);
/*  792 */     this._parsingContext.setCurrentName(nameStr);
/*  793 */     this._currToken = JsonToken.FIELD_NAME;
/*      */     
/*  795 */     i = _skipColon();
/*  796 */     if (i == 34) {
/*  797 */       this._tokenIncomplete = true;
/*  798 */       this._nextToken = JsonToken.VALUE_STRING;
/*  799 */       return nameStr;
/*      */     } 
/*      */     
/*  802 */     switch (i)
/*      */     { case 45:
/*  804 */         t = _parseNegNumber();
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
/*  840 */         this._nextToken = t;
/*  841 */         return nameStr;case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: t = _parsePosNumber(i); this._nextToken = t; return nameStr;case 102: _matchToken("false", 1); t = JsonToken.VALUE_FALSE; this._nextToken = t; return nameStr;case 110: _matchToken("null", 1); t = JsonToken.VALUE_NULL; this._nextToken = t; return nameStr;case 116: _matchToken("true", 1); t = JsonToken.VALUE_TRUE; this._nextToken = t; return nameStr;case 91: t = JsonToken.START_ARRAY; this._nextToken = t; return nameStr;case 123: t = JsonToken.START_OBJECT; this._nextToken = t; return nameStr; }  JsonToken t = _handleUnexpectedValue(i); this._nextToken = t; return nameStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextTextValue() throws IOException {
/*  848 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  849 */       this._nameCopied = false;
/*  850 */       JsonToken t = this._nextToken;
/*  851 */       this._nextToken = null;
/*  852 */       this._currToken = t;
/*  853 */       if (t == JsonToken.VALUE_STRING) {
/*  854 */         if (this._tokenIncomplete) {
/*  855 */           this._tokenIncomplete = false;
/*  856 */           return _finishAndReturnString();
/*      */         } 
/*  858 */         return this._textBuffer.contentsAsString();
/*      */       } 
/*  860 */       if (t == JsonToken.START_ARRAY) {
/*  861 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  862 */       } else if (t == JsonToken.START_OBJECT) {
/*  863 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/*  865 */       return null;
/*      */     } 
/*  867 */     return (nextToken() == JsonToken.VALUE_STRING) ? getText() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextIntValue(int defaultValue) throws IOException {
/*  874 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  875 */       this._nameCopied = false;
/*  876 */       JsonToken t = this._nextToken;
/*  877 */       this._nextToken = null;
/*  878 */       this._currToken = t;
/*  879 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/*  880 */         return getIntValue();
/*      */       }
/*  882 */       if (t == JsonToken.START_ARRAY) {
/*  883 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  884 */       } else if (t == JsonToken.START_OBJECT) {
/*  885 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/*  887 */       return defaultValue;
/*      */     } 
/*  889 */     return (nextToken() == JsonToken.VALUE_NUMBER_INT) ? getIntValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long nextLongValue(long defaultValue) throws IOException {
/*  896 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  897 */       this._nameCopied = false;
/*  898 */       JsonToken t = this._nextToken;
/*  899 */       this._nextToken = null;
/*  900 */       this._currToken = t;
/*  901 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/*  902 */         return getLongValue();
/*      */       }
/*  904 */       if (t == JsonToken.START_ARRAY) {
/*  905 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  906 */       } else if (t == JsonToken.START_OBJECT) {
/*  907 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/*  909 */       return defaultValue;
/*      */     } 
/*  911 */     return (nextToken() == JsonToken.VALUE_NUMBER_INT) ? getLongValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean nextBooleanValue() throws IOException {
/*  918 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  919 */       this._nameCopied = false;
/*  920 */       JsonToken jsonToken = this._nextToken;
/*  921 */       this._nextToken = null;
/*  922 */       this._currToken = jsonToken;
/*  923 */       if (jsonToken == JsonToken.VALUE_TRUE) {
/*  924 */         return Boolean.TRUE;
/*      */       }
/*  926 */       if (jsonToken == JsonToken.VALUE_FALSE) {
/*  927 */         return Boolean.FALSE;
/*      */       }
/*  929 */       if (jsonToken == JsonToken.START_ARRAY) {
/*  930 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  931 */       } else if (jsonToken == JsonToken.START_OBJECT) {
/*  932 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       } 
/*  934 */       return null;
/*      */     } 
/*      */     
/*  937 */     JsonToken t = nextToken();
/*  938 */     if (t == JsonToken.VALUE_TRUE) {
/*  939 */       return Boolean.TRUE;
/*      */     }
/*  941 */     if (t == JsonToken.VALUE_FALSE) {
/*  942 */       return Boolean.FALSE;
/*      */     }
/*  944 */     return null;
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
/*      */   protected JsonToken _parsePosNumber(int c) throws IOException {
/*      */     int outPtr;
/*  970 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  975 */     if (c == 48) {
/*  976 */       c = _handleLeadingZeroes();
/*  977 */       if (c <= 57 && c >= 48) {
/*  978 */         outPtr = 0;
/*      */       } else {
/*  980 */         outBuf[0] = '0';
/*  981 */         outPtr = 1;
/*      */       } 
/*      */     } else {
/*  984 */       outBuf[0] = (char)c;
/*  985 */       c = this._inputData.readUnsignedByte();
/*  986 */       outPtr = 1;
/*      */     } 
/*  988 */     int intLen = outPtr;
/*      */ 
/*      */     
/*  991 */     while (c <= 57 && c >= 48) {
/*  992 */       intLen++;
/*  993 */       outBuf[outPtr++] = (char)c;
/*  994 */       c = this._inputData.readUnsignedByte();
/*      */     } 
/*  996 */     if (c == 46 || c == 101 || c == 69) {
/*  997 */       return _parseFloat(outBuf, outPtr, c, false, intLen);
/*      */     }
/*  999 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1001 */     if (this._parsingContext.inRoot()) {
/* 1002 */       _verifyRootSpace();
/*      */     } else {
/* 1004 */       this._nextByte = c;
/*      */     } 
/*      */     
/* 1007 */     return resetInt(false, intLen);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _parseNegNumber() throws IOException {
/* 1012 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1013 */     int outPtr = 0;
/*      */ 
/*      */     
/* 1016 */     outBuf[outPtr++] = '-';
/* 1017 */     int c = this._inputData.readUnsignedByte();
/* 1018 */     outBuf[outPtr++] = (char)c;
/*      */     
/* 1020 */     if (c <= 48) {
/*      */       
/* 1022 */       if (c == 48) {
/* 1023 */         c = _handleLeadingZeroes();
/*      */       } else {
/* 1025 */         return _handleInvalidNumberStart(c, true);
/*      */       } 
/*      */     } else {
/* 1028 */       if (c > 57) {
/* 1029 */         return _handleInvalidNumberStart(c, true);
/*      */       }
/* 1031 */       c = this._inputData.readUnsignedByte();
/*      */     } 
/*      */     
/* 1034 */     int intLen = 1;
/*      */ 
/*      */     
/* 1037 */     while (c <= 57 && c >= 48) {
/* 1038 */       intLen++;
/* 1039 */       outBuf[outPtr++] = (char)c;
/* 1040 */       c = this._inputData.readUnsignedByte();
/*      */     } 
/* 1042 */     if (c == 46 || c == 101 || c == 69) {
/* 1043 */       return _parseFloat(outBuf, outPtr, c, true, intLen);
/*      */     }
/* 1045 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1047 */     this._nextByte = c;
/* 1048 */     if (this._parsingContext.inRoot()) {
/* 1049 */       _verifyRootSpace();
/*      */     }
/*      */     
/* 1052 */     return resetInt(true, intLen);
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
/*      */   private final int _handleLeadingZeroes() throws IOException {
/* 1064 */     int ch = this._inputData.readUnsignedByte();
/*      */     
/* 1066 */     if (ch < 48 || ch > 57) {
/* 1067 */       return ch;
/*      */     }
/*      */     
/* 1070 */     if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1071 */       reportInvalidNumber("Leading zeroes not allowed");
/*      */     }
/*      */     
/* 1074 */     while (ch == 48) {
/* 1075 */       ch = this._inputData.readUnsignedByte();
/*      */     }
/* 1077 */     return ch;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _parseFloat(char[] outBuf, int outPtr, int c, boolean negative, int integerPartLength) throws IOException {
/* 1083 */     int fractLen = 0;
/*      */ 
/*      */     
/* 1086 */     if (c == 46) {
/* 1087 */       outBuf[outPtr++] = (char)c;
/*      */ 
/*      */       
/*      */       while (true) {
/* 1091 */         c = this._inputData.readUnsignedByte();
/* 1092 */         if (c < 48 || c > 57) {
/*      */           break;
/*      */         }
/* 1095 */         fractLen++;
/* 1096 */         if (outPtr >= outBuf.length) {
/* 1097 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1098 */           outPtr = 0;
/*      */         } 
/* 1100 */         outBuf[outPtr++] = (char)c;
/*      */       } 
/*      */       
/* 1103 */       if (fractLen == 0) {
/* 1104 */         reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
/*      */       }
/*      */     } 
/*      */     
/* 1108 */     int expLen = 0;
/* 1109 */     if (c == 101 || c == 69) {
/* 1110 */       if (outPtr >= outBuf.length) {
/* 1111 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1112 */         outPtr = 0;
/*      */       } 
/* 1114 */       outBuf[outPtr++] = (char)c;
/* 1115 */       c = this._inputData.readUnsignedByte();
/*      */       
/* 1117 */       if (c == 45 || c == 43) {
/* 1118 */         if (outPtr >= outBuf.length) {
/* 1119 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1120 */           outPtr = 0;
/*      */         } 
/* 1122 */         outBuf[outPtr++] = (char)c;
/* 1123 */         c = this._inputData.readUnsignedByte();
/*      */       } 
/* 1125 */       while (c <= 57 && c >= 48) {
/* 1126 */         expLen++;
/* 1127 */         if (outPtr >= outBuf.length) {
/* 1128 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1129 */           outPtr = 0;
/*      */         } 
/* 1131 */         outBuf[outPtr++] = (char)c;
/* 1132 */         c = this._inputData.readUnsignedByte();
/*      */       } 
/*      */       
/* 1135 */       if (expLen == 0) {
/* 1136 */         reportUnexpectedNumberChar(c, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1142 */     this._nextByte = c;
/* 1143 */     if (this._parsingContext.inRoot()) {
/* 1144 */       _verifyRootSpace();
/*      */     }
/* 1146 */     this._textBuffer.setCurrentLength(outPtr);
/*      */ 
/*      */     
/* 1149 */     return resetFloat(negative, integerPartLength, fractLen, expLen);
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
/*      */   private final void _verifyRootSpace() throws IOException {
/* 1162 */     int ch = this._nextByte;
/* 1163 */     if (ch <= 32) {
/* 1164 */       this._nextByte = -1;
/* 1165 */       if (ch == 13 || ch == 10) {
/* 1166 */         this._currInputRow++;
/*      */       }
/*      */       return;
/*      */     } 
/* 1170 */     _reportMissingRootWS(ch);
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
/* 1181 */     if (i != 34) {
/* 1182 */       return _handleOddName(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1190 */     int[] codes = _icLatin1;
/*      */     
/* 1192 */     int q = this._inputData.readUnsignedByte();
/*      */     
/* 1194 */     if (codes[q] == 0) {
/* 1195 */       i = this._inputData.readUnsignedByte();
/* 1196 */       if (codes[i] == 0) {
/* 1197 */         q = q << 8 | i;
/* 1198 */         i = this._inputData.readUnsignedByte();
/* 1199 */         if (codes[i] == 0) {
/* 1200 */           q = q << 8 | i;
/* 1201 */           i = this._inputData.readUnsignedByte();
/* 1202 */           if (codes[i] == 0) {
/* 1203 */             q = q << 8 | i;
/* 1204 */             i = this._inputData.readUnsignedByte();
/* 1205 */             if (codes[i] == 0) {
/* 1206 */               this._quad1 = q;
/* 1207 */               return _parseMediumName(i);
/*      */             } 
/* 1209 */             if (i == 34) {
/* 1210 */               return findName(q, 4);
/*      */             }
/* 1212 */             return parseName(q, i, 4);
/*      */           } 
/* 1214 */           if (i == 34) {
/* 1215 */             return findName(q, 3);
/*      */           }
/* 1217 */           return parseName(q, i, 3);
/*      */         } 
/* 1219 */         if (i == 34) {
/* 1220 */           return findName(q, 2);
/*      */         }
/* 1222 */         return parseName(q, i, 2);
/*      */       } 
/* 1224 */       if (i == 34) {
/* 1225 */         return findName(q, 1);
/*      */       }
/* 1227 */       return parseName(q, i, 1);
/*      */     } 
/* 1229 */     if (q == 34) {
/* 1230 */       return "";
/*      */     }
/* 1232 */     return parseName(0, q, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String _parseMediumName(int q2) throws IOException {
/* 1237 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1240 */     int i = this._inputData.readUnsignedByte();
/* 1241 */     if (codes[i] != 0) {
/* 1242 */       if (i == 34) {
/* 1243 */         return findName(this._quad1, q2, 1);
/*      */       }
/* 1245 */       return parseName(this._quad1, q2, i, 1);
/*      */     } 
/* 1247 */     q2 = q2 << 8 | i;
/* 1248 */     i = this._inputData.readUnsignedByte();
/* 1249 */     if (codes[i] != 0) {
/* 1250 */       if (i == 34) {
/* 1251 */         return findName(this._quad1, q2, 2);
/*      */       }
/* 1253 */       return parseName(this._quad1, q2, i, 2);
/*      */     } 
/* 1255 */     q2 = q2 << 8 | i;
/* 1256 */     i = this._inputData.readUnsignedByte();
/* 1257 */     if (codes[i] != 0) {
/* 1258 */       if (i == 34) {
/* 1259 */         return findName(this._quad1, q2, 3);
/*      */       }
/* 1261 */       return parseName(this._quad1, q2, i, 3);
/*      */     } 
/* 1263 */     q2 = q2 << 8 | i;
/* 1264 */     i = this._inputData.readUnsignedByte();
/* 1265 */     if (codes[i] != 0) {
/* 1266 */       if (i == 34) {
/* 1267 */         return findName(this._quad1, q2, 4);
/*      */       }
/* 1269 */       return parseName(this._quad1, q2, i, 4);
/*      */     } 
/* 1271 */     return _parseMediumName2(i, q2);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String _parseMediumName2(int q3, int q2) throws IOException {
/* 1276 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1279 */     int i = this._inputData.readUnsignedByte();
/* 1280 */     if (codes[i] != 0) {
/* 1281 */       if (i == 34) {
/* 1282 */         return findName(this._quad1, q2, q3, 1);
/*      */       }
/* 1284 */       return parseName(this._quad1, q2, q3, i, 1);
/*      */     } 
/* 1286 */     q3 = q3 << 8 | i;
/* 1287 */     i = this._inputData.readUnsignedByte();
/* 1288 */     if (codes[i] != 0) {
/* 1289 */       if (i == 34) {
/* 1290 */         return findName(this._quad1, q2, q3, 2);
/*      */       }
/* 1292 */       return parseName(this._quad1, q2, q3, i, 2);
/*      */     } 
/* 1294 */     q3 = q3 << 8 | i;
/* 1295 */     i = this._inputData.readUnsignedByte();
/* 1296 */     if (codes[i] != 0) {
/* 1297 */       if (i == 34) {
/* 1298 */         return findName(this._quad1, q2, q3, 3);
/*      */       }
/* 1300 */       return parseName(this._quad1, q2, q3, i, 3);
/*      */     } 
/* 1302 */     q3 = q3 << 8 | i;
/* 1303 */     i = this._inputData.readUnsignedByte();
/* 1304 */     if (codes[i] != 0) {
/* 1305 */       if (i == 34) {
/* 1306 */         return findName(this._quad1, q2, q3, 4);
/*      */       }
/* 1308 */       return parseName(this._quad1, q2, q3, i, 4);
/*      */     } 
/* 1310 */     return _parseLongName(i, q2, q3);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String _parseLongName(int q, int q2, int q3) throws IOException {
/* 1315 */     this._quadBuffer[0] = this._quad1;
/* 1316 */     this._quadBuffer[1] = q2;
/* 1317 */     this._quadBuffer[2] = q3;
/*      */ 
/*      */     
/* 1320 */     int[] codes = _icLatin1;
/* 1321 */     int qlen = 3;
/*      */     
/*      */     while (true) {
/* 1324 */       int i = this._inputData.readUnsignedByte();
/* 1325 */       if (codes[i] != 0) {
/* 1326 */         if (i == 34) {
/* 1327 */           return findName(this._quadBuffer, qlen, q, 1);
/*      */         }
/* 1329 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 1);
/*      */       } 
/*      */       
/* 1332 */       q = q << 8 | i;
/* 1333 */       i = this._inputData.readUnsignedByte();
/* 1334 */       if (codes[i] != 0) {
/* 1335 */         if (i == 34) {
/* 1336 */           return findName(this._quadBuffer, qlen, q, 2);
/*      */         }
/* 1338 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 2);
/*      */       } 
/*      */       
/* 1341 */       q = q << 8 | i;
/* 1342 */       i = this._inputData.readUnsignedByte();
/* 1343 */       if (codes[i] != 0) {
/* 1344 */         if (i == 34) {
/* 1345 */           return findName(this._quadBuffer, qlen, q, 3);
/*      */         }
/* 1347 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 3);
/*      */       } 
/*      */       
/* 1350 */       q = q << 8 | i;
/* 1351 */       i = this._inputData.readUnsignedByte();
/* 1352 */       if (codes[i] != 0) {
/* 1353 */         if (i == 34) {
/* 1354 */           return findName(this._quadBuffer, qlen, q, 4);
/*      */         }
/* 1356 */         return parseEscapedName(this._quadBuffer, qlen, q, i, 4);
/*      */       } 
/*      */ 
/*      */       
/* 1360 */       if (qlen >= this._quadBuffer.length) {
/* 1361 */         this._quadBuffer = _growArrayBy(this._quadBuffer, qlen);
/*      */       }
/* 1363 */       this._quadBuffer[qlen++] = q;
/* 1364 */       q = i;
/*      */     } 
/*      */   }
/*      */   
/*      */   private final String parseName(int q1, int ch, int lastQuadBytes) throws IOException {
/* 1369 */     return parseEscapedName(this._quadBuffer, 0, q1, ch, lastQuadBytes);
/*      */   }
/*      */   
/*      */   private final String parseName(int q1, int q2, int ch, int lastQuadBytes) throws IOException {
/* 1373 */     this._quadBuffer[0] = q1;
/* 1374 */     return parseEscapedName(this._quadBuffer, 1, q2, ch, lastQuadBytes);
/*      */   }
/*      */   
/*      */   private final String parseName(int q1, int q2, int q3, int ch, int lastQuadBytes) throws IOException {
/* 1378 */     this._quadBuffer[0] = q1;
/* 1379 */     this._quadBuffer[1] = q2;
/* 1380 */     return parseEscapedName(this._quadBuffer, 2, q3, ch, lastQuadBytes);
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
/*      */   protected final String parseEscapedName(int[] quads, int qlen, int currQuad, int ch, int currQuadBytes) throws IOException {
/* 1397 */     int[] codes = _icLatin1;
/*      */     
/*      */     while (true) {
/* 1400 */       if (codes[ch] != 0) {
/* 1401 */         if (ch == 34) {
/*      */           break;
/*      */         }
/*      */         
/* 1405 */         if (ch != 92) {
/*      */           
/* 1407 */           _throwUnquotedSpace(ch, "name");
/*      */         } else {
/*      */           
/* 1410 */           ch = _decodeEscaped();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1417 */         if (ch > 127) {
/*      */           
/* 1419 */           if (currQuadBytes >= 4) {
/* 1420 */             if (qlen >= quads.length) {
/* 1421 */               this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */             }
/* 1423 */             quads[qlen++] = currQuad;
/* 1424 */             currQuad = 0;
/* 1425 */             currQuadBytes = 0;
/*      */           } 
/* 1427 */           if (ch < 2048) {
/* 1428 */             currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 1429 */             currQuadBytes++;
/*      */           } else {
/*      */             
/* 1432 */             currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/* 1433 */             currQuadBytes++;
/*      */             
/* 1435 */             if (currQuadBytes >= 4) {
/* 1436 */               if (qlen >= quads.length) {
/* 1437 */                 this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */               }
/* 1439 */               quads[qlen++] = currQuad;
/* 1440 */               currQuad = 0;
/* 1441 */               currQuadBytes = 0;
/*      */             } 
/* 1443 */             currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 1444 */             currQuadBytes++;
/*      */           } 
/*      */           
/* 1447 */           ch = 0x80 | ch & 0x3F;
/*      */         } 
/*      */       } 
/*      */       
/* 1451 */       if (currQuadBytes < 4) {
/* 1452 */         currQuadBytes++;
/* 1453 */         currQuad = currQuad << 8 | ch;
/*      */       } else {
/* 1455 */         if (qlen >= quads.length) {
/* 1456 */           this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */         }
/* 1458 */         quads[qlen++] = currQuad;
/* 1459 */         currQuad = ch;
/* 1460 */         currQuadBytes = 1;
/*      */       } 
/* 1462 */       ch = this._inputData.readUnsignedByte();
/*      */     } 
/*      */     
/* 1465 */     if (currQuadBytes > 0) {
/* 1466 */       if (qlen >= quads.length) {
/* 1467 */         this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */       }
/* 1469 */       quads[qlen++] = pad(currQuad, currQuadBytes);
/*      */     } 
/* 1471 */     String name = this._symbols.findName(quads, qlen);
/* 1472 */     if (name == null) {
/* 1473 */       name = addName(quads, qlen, currQuadBytes);
/*      */     }
/* 1475 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _handleOddName(int ch) throws IOException {
/* 1486 */     if (ch == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1487 */       return _parseAposName();
/*      */     }
/* 1489 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1490 */       char c = (char)_decodeCharForError(ch);
/* 1491 */       _reportUnexpectedChar(c, "was expecting double-quote to start field name");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1497 */     int[] codes = CharTypes.getInputCodeUtf8JsNames();
/*      */     
/* 1499 */     if (codes[ch] != 0) {
/* 1500 */       _reportUnexpectedChar(ch, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1507 */     int[] quads = this._quadBuffer;
/* 1508 */     int qlen = 0;
/* 1509 */     int currQuad = 0;
/* 1510 */     int currQuadBytes = 0;
/*      */ 
/*      */     
/*      */     do {
/* 1514 */       if (currQuadBytes < 4) {
/* 1515 */         currQuadBytes++;
/* 1516 */         currQuad = currQuad << 8 | ch;
/*      */       } else {
/* 1518 */         if (qlen >= quads.length) {
/* 1519 */           this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */         }
/* 1521 */         quads[qlen++] = currQuad;
/* 1522 */         currQuad = ch;
/* 1523 */         currQuadBytes = 1;
/*      */       } 
/* 1525 */       ch = this._inputData.readUnsignedByte();
/* 1526 */     } while (codes[ch] == 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1531 */     this._nextByte = ch;
/* 1532 */     if (currQuadBytes > 0) {
/* 1533 */       if (qlen >= quads.length) {
/* 1534 */         this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */       }
/* 1536 */       quads[qlen++] = currQuad;
/*      */     } 
/* 1538 */     String name = this._symbols.findName(quads, qlen);
/* 1539 */     if (name == null) {
/* 1540 */       name = addName(quads, qlen, currQuadBytes);
/*      */     }
/* 1542 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _parseAposName() throws IOException {
/* 1553 */     int ch = this._inputData.readUnsignedByte();
/* 1554 */     if (ch == 39) {
/* 1555 */       return "";
/*      */     }
/* 1557 */     int[] quads = this._quadBuffer;
/* 1558 */     int qlen = 0;
/* 1559 */     int currQuad = 0;
/* 1560 */     int currQuadBytes = 0;
/*      */ 
/*      */ 
/*      */     
/* 1564 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1567 */     while (ch != 39) {
/*      */ 
/*      */ 
/*      */       
/* 1571 */       if (ch != 34 && codes[ch] != 0) {
/* 1572 */         if (ch != 92) {
/*      */ 
/*      */           
/* 1575 */           _throwUnquotedSpace(ch, "name");
/*      */         } else {
/*      */           
/* 1578 */           ch = _decodeEscaped();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1584 */         if (ch > 127) {
/*      */           
/* 1586 */           if (currQuadBytes >= 4) {
/* 1587 */             if (qlen >= quads.length) {
/* 1588 */               this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */             }
/* 1590 */             quads[qlen++] = currQuad;
/* 1591 */             currQuad = 0;
/* 1592 */             currQuadBytes = 0;
/*      */           } 
/* 1594 */           if (ch < 2048) {
/* 1595 */             currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 1596 */             currQuadBytes++;
/*      */           } else {
/*      */             
/* 1599 */             currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/* 1600 */             currQuadBytes++;
/*      */             
/* 1602 */             if (currQuadBytes >= 4) {
/* 1603 */               if (qlen >= quads.length) {
/* 1604 */                 this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */               }
/* 1606 */               quads[qlen++] = currQuad;
/* 1607 */               currQuad = 0;
/* 1608 */               currQuadBytes = 0;
/*      */             } 
/* 1610 */             currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 1611 */             currQuadBytes++;
/*      */           } 
/*      */           
/* 1614 */           ch = 0x80 | ch & 0x3F;
/*      */         } 
/*      */       } 
/*      */       
/* 1618 */       if (currQuadBytes < 4) {
/* 1619 */         currQuadBytes++;
/* 1620 */         currQuad = currQuad << 8 | ch;
/*      */       } else {
/* 1622 */         if (qlen >= quads.length) {
/* 1623 */           this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */         }
/* 1625 */         quads[qlen++] = currQuad;
/* 1626 */         currQuad = ch;
/* 1627 */         currQuadBytes = 1;
/*      */       } 
/* 1629 */       ch = this._inputData.readUnsignedByte();
/*      */     } 
/*      */     
/* 1632 */     if (currQuadBytes > 0) {
/* 1633 */       if (qlen >= quads.length) {
/* 1634 */         this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */       }
/* 1636 */       quads[qlen++] = pad(currQuad, currQuadBytes);
/*      */     } 
/* 1638 */     String name = this._symbols.findName(quads, qlen);
/* 1639 */     if (name == null) {
/* 1640 */       name = addName(quads, qlen, currQuadBytes);
/*      */     }
/* 1642 */     return name;
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
/* 1653 */     q1 = pad(q1, lastQuadBytes);
/*      */     
/* 1655 */     String name = this._symbols.findName(q1);
/* 1656 */     if (name != null) {
/* 1657 */       return name;
/*      */     }
/*      */     
/* 1660 */     this._quadBuffer[0] = q1;
/* 1661 */     return addName(this._quadBuffer, 1, lastQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String findName(int q1, int q2, int lastQuadBytes) throws JsonParseException {
/* 1666 */     q2 = pad(q2, lastQuadBytes);
/*      */     
/* 1668 */     String name = this._symbols.findName(q1, q2);
/* 1669 */     if (name != null) {
/* 1670 */       return name;
/*      */     }
/*      */     
/* 1673 */     this._quadBuffer[0] = q1;
/* 1674 */     this._quadBuffer[1] = q2;
/* 1675 */     return addName(this._quadBuffer, 2, lastQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String findName(int q1, int q2, int q3, int lastQuadBytes) throws JsonParseException {
/* 1680 */     q3 = pad(q3, lastQuadBytes);
/* 1681 */     String name = this._symbols.findName(q1, q2, q3);
/* 1682 */     if (name != null) {
/* 1683 */       return name;
/*      */     }
/* 1685 */     int[] quads = this._quadBuffer;
/* 1686 */     quads[0] = q1;
/* 1687 */     quads[1] = q2;
/* 1688 */     quads[2] = pad(q3, lastQuadBytes);
/* 1689 */     return addName(quads, 3, lastQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private final String findName(int[] quads, int qlen, int lastQuad, int lastQuadBytes) throws JsonParseException {
/* 1694 */     if (qlen >= quads.length) {
/* 1695 */       this._quadBuffer = quads = _growArrayBy(quads, quads.length);
/*      */     }
/* 1697 */     quads[qlen++] = pad(lastQuad, lastQuadBytes);
/* 1698 */     String name = this._symbols.findName(quads, qlen);
/* 1699 */     if (name == null) {
/* 1700 */       return addName(quads, qlen, lastQuadBytes);
/*      */     }
/* 1702 */     return name;
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
/* 1718 */     int lastQuad, byteLen = (qlen << 2) - 4 + lastQuadBytes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1727 */     if (lastQuadBytes < 4) {
/* 1728 */       lastQuad = quads[qlen - 1];
/*      */       
/* 1730 */       quads[qlen - 1] = lastQuad << 4 - lastQuadBytes << 3;
/*      */     } else {
/* 1732 */       lastQuad = 0;
/*      */     } 
/*      */ 
/*      */     
/* 1736 */     char[] cbuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1737 */     int cix = 0;
/*      */     
/* 1739 */     for (int ix = 0; ix < byteLen; ) {
/* 1740 */       int ch = quads[ix >> 2];
/* 1741 */       int byteIx = ix & 0x3;
/* 1742 */       ch = ch >> 3 - byteIx << 3 & 0xFF;
/* 1743 */       ix++;
/*      */       
/* 1745 */       if (ch > 127) {
/*      */         int needed;
/* 1747 */         if ((ch & 0xE0) == 192) {
/* 1748 */           ch &= 0x1F;
/* 1749 */           needed = 1;
/* 1750 */         } else if ((ch & 0xF0) == 224) {
/* 1751 */           ch &= 0xF;
/* 1752 */           needed = 2;
/* 1753 */         } else if ((ch & 0xF8) == 240) {
/* 1754 */           ch &= 0x7;
/* 1755 */           needed = 3;
/*      */         } else {
/* 1757 */           _reportInvalidInitial(ch);
/* 1758 */           needed = ch = 1;
/*      */         } 
/* 1760 */         if (ix + needed > byteLen) {
/* 1761 */           _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
/*      */         }
/*      */ 
/*      */         
/* 1765 */         int ch2 = quads[ix >> 2];
/* 1766 */         byteIx = ix & 0x3;
/* 1767 */         ch2 >>= 3 - byteIx << 3;
/* 1768 */         ix++;
/*      */         
/* 1770 */         if ((ch2 & 0xC0) != 128) {
/* 1771 */           _reportInvalidOther(ch2);
/*      */         }
/* 1773 */         ch = ch << 6 | ch2 & 0x3F;
/* 1774 */         if (needed > 1) {
/* 1775 */           ch2 = quads[ix >> 2];
/* 1776 */           byteIx = ix & 0x3;
/* 1777 */           ch2 >>= 3 - byteIx << 3;
/* 1778 */           ix++;
/*      */           
/* 1780 */           if ((ch2 & 0xC0) != 128) {
/* 1781 */             _reportInvalidOther(ch2);
/*      */           }
/* 1783 */           ch = ch << 6 | ch2 & 0x3F;
/* 1784 */           if (needed > 2) {
/* 1785 */             ch2 = quads[ix >> 2];
/* 1786 */             byteIx = ix & 0x3;
/* 1787 */             ch2 >>= 3 - byteIx << 3;
/* 1788 */             ix++;
/* 1789 */             if ((ch2 & 0xC0) != 128) {
/* 1790 */               _reportInvalidOther(ch2 & 0xFF);
/*      */             }
/* 1792 */             ch = ch << 6 | ch2 & 0x3F;
/*      */           } 
/*      */         } 
/* 1795 */         if (needed > 2) {
/* 1796 */           ch -= 65536;
/* 1797 */           if (cix >= cbuf.length) {
/* 1798 */             cbuf = this._textBuffer.expandCurrentSegment();
/*      */           }
/* 1800 */           cbuf[cix++] = (char)(55296 + (ch >> 10));
/* 1801 */           ch = 0xDC00 | ch & 0x3FF;
/*      */         } 
/*      */       } 
/* 1804 */       if (cix >= cbuf.length) {
/* 1805 */         cbuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1807 */       cbuf[cix++] = (char)ch;
/*      */     } 
/*      */ 
/*      */     
/* 1811 */     String baseName = new String(cbuf, 0, cix);
/*      */     
/* 1813 */     if (lastQuadBytes < 4) {
/* 1814 */       quads[qlen - 1] = lastQuad;
/*      */     }
/* 1816 */     return this._symbols.addName(baseName, quads, qlen);
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
/*      */   protected void _finishString() throws IOException {
/* 1828 */     int outPtr = 0;
/* 1829 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1830 */     int[] codes = _icUTF8;
/* 1831 */     int outEnd = outBuf.length;
/*      */     
/*      */     while (true) {
/* 1834 */       int c = this._inputData.readUnsignedByte();
/* 1835 */       if (codes[c] != 0) {
/* 1836 */         if (c == 34) {
/* 1837 */           this._textBuffer.setCurrentLength(outPtr);
/*      */           return;
/*      */         } 
/* 1840 */         _finishString2(outBuf, outPtr, c);
/*      */         return;
/*      */       } 
/* 1843 */       outBuf[outPtr++] = (char)c;
/* 1844 */       if (outPtr >= outEnd) {
/* 1845 */         _finishString2(outBuf, outPtr, this._inputData.readUnsignedByte());
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   } private String _finishAndReturnString() throws IOException {
/* 1850 */     int outPtr = 0;
/* 1851 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1852 */     int[] codes = _icUTF8;
/* 1853 */     int outEnd = outBuf.length;
/*      */     
/*      */     while (true) {
/* 1856 */       int c = this._inputData.readUnsignedByte();
/* 1857 */       if (codes[c] != 0) {
/* 1858 */         if (c == 34) {
/* 1859 */           return this._textBuffer.setCurrentAndReturn(outPtr);
/*      */         }
/* 1861 */         _finishString2(outBuf, outPtr, c);
/* 1862 */         return this._textBuffer.contentsAsString();
/*      */       } 
/* 1864 */       outBuf[outPtr++] = (char)c;
/* 1865 */       if (outPtr >= outEnd) {
/* 1866 */         _finishString2(outBuf, outPtr, this._inputData.readUnsignedByte());
/* 1867 */         return this._textBuffer.contentsAsString();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _finishString2(char[] outBuf, int outPtr, int c) throws IOException {
/* 1874 */     int[] codes = _icUTF8;
/* 1875 */     int outEnd = outBuf.length;
/*      */ 
/*      */     
/* 1878 */     for (;; c = this._inputData.readUnsignedByte()) {
/*      */       
/* 1880 */       while (codes[c] == 0) {
/* 1881 */         if (outPtr >= outEnd) {
/* 1882 */           outBuf = this._textBuffer.finishCurrentSegment();
/* 1883 */           outPtr = 0;
/* 1884 */           outEnd = outBuf.length;
/*      */         } 
/* 1886 */         outBuf[outPtr++] = (char)c;
/* 1887 */         c = this._inputData.readUnsignedByte();
/*      */       } 
/*      */       
/* 1890 */       if (c == 34) {
/*      */         break;
/*      */       }
/* 1893 */       switch (codes[c]) {
/*      */         case 1:
/* 1895 */           c = _decodeEscaped();
/*      */           break;
/*      */         case 2:
/* 1898 */           c = _decodeUtf8_2(c);
/*      */           break;
/*      */         case 3:
/* 1901 */           c = _decodeUtf8_3(c);
/*      */           break;
/*      */         case 4:
/* 1904 */           c = _decodeUtf8_4(c);
/*      */           
/* 1906 */           outBuf[outPtr++] = (char)(0xD800 | c >> 10);
/* 1907 */           if (outPtr >= outBuf.length) {
/* 1908 */             outBuf = this._textBuffer.finishCurrentSegment();
/* 1909 */             outPtr = 0;
/* 1910 */             outEnd = outBuf.length;
/*      */           } 
/* 1912 */           c = 0xDC00 | c & 0x3FF;
/*      */           break;
/*      */         
/*      */         default:
/* 1916 */           if (c < 32) {
/* 1917 */             _throwUnquotedSpace(c, "string value");
/*      */             break;
/*      */           } 
/* 1920 */           _reportInvalidChar(c);
/*      */           break;
/*      */       } 
/*      */       
/* 1924 */       if (outPtr >= outBuf.length) {
/* 1925 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 1926 */         outPtr = 0;
/* 1927 */         outEnd = outBuf.length;
/*      */       } 
/*      */       
/* 1930 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 1932 */     this._textBuffer.setCurrentLength(outPtr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _skipString() throws IOException {
/* 1942 */     this._tokenIncomplete = false;
/*      */ 
/*      */     
/* 1945 */     int[] codes = _icUTF8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1953 */       int c = this._inputData.readUnsignedByte();
/* 1954 */       if (codes[c] != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1959 */         if (c == 34) {
/*      */           break;
/*      */         }
/*      */         
/* 1963 */         switch (codes[c]) {
/*      */           case 1:
/* 1965 */             _decodeEscaped();
/*      */             continue;
/*      */           case 2:
/* 1968 */             _skipUtf8_2();
/*      */             continue;
/*      */           case 3:
/* 1971 */             _skipUtf8_3();
/*      */             continue;
/*      */           case 4:
/* 1974 */             _skipUtf8_4();
/*      */             continue;
/*      */         } 
/* 1977 */         if (c < 32) {
/* 1978 */           _throwUnquotedSpace(c, "string value");
/*      */           continue;
/*      */         } 
/* 1981 */         _reportInvalidChar(c);
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
/*      */   protected JsonToken _handleUnexpectedValue(int c) throws IOException {
/* 1995 */     switch (c) {
/*      */       case 93:
/* 1997 */         if (!this._parsingContext.inArray()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 44:
/* 2005 */         if (isEnabled(JsonParser.Feature.ALLOW_MISSING_VALUES)) {
/*      */           
/* 2007 */           this._nextByte = c;
/* 2008 */           return JsonToken.VALUE_NULL;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 125:
/* 2014 */         _reportUnexpectedChar(c, "expected a value");
/*      */       case 39:
/* 2016 */         if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 2017 */           return _handleApos();
/*      */         }
/*      */         break;
/*      */       case 78:
/* 2021 */         _matchToken("NaN", 1);
/* 2022 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2023 */           return resetAsNaN("NaN", Double.NaN);
/*      */         }
/* 2025 */         _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */         break;
/*      */       case 73:
/* 2028 */         _matchToken("Infinity", 1);
/* 2029 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2030 */           return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
/*      */         }
/* 2032 */         _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */         break;
/*      */       case 43:
/* 2035 */         return _handleInvalidNumberStart(this._inputData.readUnsignedByte(), false);
/*      */     } 
/*      */     
/* 2038 */     if (Character.isJavaIdentifierStart(c)) {
/* 2039 */       _reportInvalidToken(c, "" + (char)c, "('true', 'false' or 'null')");
/*      */     }
/*      */     
/* 2042 */     _reportUnexpectedChar(c, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 2043 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _handleApos() throws IOException {
/* 2048 */     int c = 0;
/*      */     
/* 2050 */     int outPtr = 0;
/* 2051 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/*      */ 
/*      */     
/* 2054 */     int[] codes = _icUTF8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     label35: while (true) {
/* 2061 */       int outEnd = outBuf.length;
/* 2062 */       if (outPtr >= outBuf.length) {
/* 2063 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 2064 */         outPtr = 0;
/* 2065 */         outEnd = outBuf.length;
/*      */       } 
/*      */       while (true)
/* 2068 */       { c = this._inputData.readUnsignedByte();
/* 2069 */         if (c == 39) {
/*      */           break;
/*      */         }
/* 2072 */         if (codes[c] != 0)
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */           
/* 2078 */           switch (codes[c]) {
/*      */             case 1:
/* 2080 */               c = _decodeEscaped();
/*      */               break;
/*      */             case 2:
/* 2083 */               c = _decodeUtf8_2(c);
/*      */               break;
/*      */             case 3:
/* 2086 */               c = _decodeUtf8_3(c);
/*      */               break;
/*      */             case 4:
/* 2089 */               c = _decodeUtf8_4(c);
/*      */               
/* 2091 */               outBuf[outPtr++] = (char)(0xD800 | c >> 10);
/* 2092 */               if (outPtr >= outBuf.length) {
/* 2093 */                 outBuf = this._textBuffer.finishCurrentSegment();
/* 2094 */                 outPtr = 0;
/*      */               } 
/* 2096 */               c = 0xDC00 | c & 0x3FF;
/*      */               break;
/*      */             
/*      */             default:
/* 2100 */               if (c < 32) {
/* 2101 */                 _throwUnquotedSpace(c, "string value");
/*      */               }
/*      */               
/* 2104 */               _reportInvalidChar(c);
/*      */               break;
/*      */           } 
/* 2107 */           if (outPtr >= outBuf.length) {
/* 2108 */             outBuf = this._textBuffer.finishCurrentSegment();
/* 2109 */             outPtr = 0;
/*      */           } 
/*      */           
/* 2112 */           outBuf[outPtr++] = (char)c; continue; }  outBuf[outPtr++] = (char)c; if (outPtr >= outEnd)
/*      */           continue label35;  }  break;
/* 2114 */     }  this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 2116 */     return JsonToken.VALUE_STRING;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _handleInvalidNumberStart(int ch, boolean neg) throws IOException {
/* 2126 */     while (ch == 73) {
/* 2127 */       String match; ch = this._inputData.readUnsignedByte();
/*      */       
/* 2129 */       if (ch == 78) {
/* 2130 */         match = neg ? "-INF" : "+INF";
/* 2131 */       } else if (ch == 110) {
/* 2132 */         match = neg ? "-Infinity" : "+Infinity";
/*      */       } else {
/*      */         break;
/*      */       } 
/* 2136 */       _matchToken(match, 3);
/* 2137 */       if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2138 */         return resetAsNaN(match, neg ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */       }
/* 2140 */       _reportError("Non-standard token '" + match + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */     } 
/* 2142 */     reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 2143 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _matchToken(String matchStr, int i) throws IOException {
/* 2148 */     int len = matchStr.length();
/*      */     do {
/* 2150 */       int j = this._inputData.readUnsignedByte();
/* 2151 */       if (j == matchStr.charAt(i))
/* 2152 */         continue;  _reportInvalidToken(j, matchStr.substring(0, i));
/*      */     }
/* 2154 */     while (++i < len);
/*      */     
/* 2156 */     int ch = this._inputData.readUnsignedByte();
/* 2157 */     if (ch >= 48 && ch != 93 && ch != 125) {
/* 2158 */       _checkMatchEnd(matchStr, i, ch);
/*      */     }
/* 2160 */     this._nextByte = ch;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _checkMatchEnd(String matchStr, int i, int ch) throws IOException {
/* 2165 */     char c = (char)_decodeCharForError(ch);
/* 2166 */     if (Character.isJavaIdentifierPart(c)) {
/* 2167 */       _reportInvalidToken(c, matchStr.substring(0, i));
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
/* 2179 */     int i = this._nextByte;
/* 2180 */     if (i < 0) {
/* 2181 */       i = this._inputData.readUnsignedByte();
/*      */     } else {
/* 2183 */       this._nextByte = -1;
/*      */     } 
/*      */     while (true) {
/* 2186 */       if (i > 32) {
/* 2187 */         if (i == 47 || i == 35) {
/* 2188 */           return _skipWSComment(i);
/*      */         }
/* 2190 */         return i;
/*      */       } 
/*      */ 
/*      */       
/* 2194 */       if (i == 13 || i == 10) {
/* 2195 */         this._currInputRow++;
/*      */       }
/*      */       
/* 2198 */       i = this._inputData.readUnsignedByte();
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
/*      */   private final int _skipWSOrEnd() throws IOException {
/* 2210 */     int i = this._nextByte;
/* 2211 */     if (i < 0) {
/*      */       try {
/* 2213 */         i = this._inputData.readUnsignedByte();
/* 2214 */       } catch (EOFException e) {
/* 2215 */         return _eofAsNextChar();
/*      */       } 
/*      */     } else {
/* 2218 */       this._nextByte = -1;
/*      */     } 
/*      */     while (true) {
/* 2221 */       if (i > 32) {
/* 2222 */         if (i == 47 || i == 35) {
/* 2223 */           return _skipWSComment(i);
/*      */         }
/* 2225 */         return i;
/*      */       } 
/*      */ 
/*      */       
/* 2229 */       if (i == 13 || i == 10) {
/* 2230 */         this._currInputRow++;
/*      */       }
/*      */       
/*      */       try {
/* 2234 */         i = this._inputData.readUnsignedByte();
/* 2235 */       } catch (EOFException e) {
/* 2236 */         return _eofAsNextChar();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipWSComment(int i) throws IOException {
/*      */     while (true) {
/* 2244 */       if (i > 32) {
/* 2245 */         if (i == 47) {
/* 2246 */           _skipComment();
/* 2247 */         } else if (i == 35) {
/* 2248 */           if (!_skipYAMLComment()) {
/* 2249 */             return i;
/*      */           }
/*      */         } else {
/* 2252 */           return i;
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 2257 */       else if (i == 13 || i == 10) {
/* 2258 */         this._currInputRow++;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2266 */       i = this._inputData.readUnsignedByte();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipColon() throws IOException {
/* 2272 */     int i = this._nextByte;
/* 2273 */     if (i < 0) {
/* 2274 */       i = this._inputData.readUnsignedByte();
/*      */     } else {
/* 2276 */       this._nextByte = -1;
/*      */     } 
/*      */     
/* 2279 */     if (i == 58) {
/* 2280 */       i = this._inputData.readUnsignedByte();
/* 2281 */       if (i > 32) {
/* 2282 */         if (i == 47 || i == 35) {
/* 2283 */           return _skipColon2(i, true);
/*      */         }
/* 2285 */         return i;
/*      */       } 
/* 2287 */       if (i == 32 || i == 9) {
/* 2288 */         i = this._inputData.readUnsignedByte();
/* 2289 */         if (i > 32) {
/* 2290 */           if (i == 47 || i == 35) {
/* 2291 */             return _skipColon2(i, true);
/*      */           }
/* 2293 */           return i;
/*      */         } 
/*      */       } 
/* 2296 */       return _skipColon2(i, true);
/*      */     } 
/* 2298 */     if (i == 32 || i == 9) {
/* 2299 */       i = this._inputData.readUnsignedByte();
/*      */     }
/* 2301 */     if (i == 58) {
/* 2302 */       i = this._inputData.readUnsignedByte();
/* 2303 */       if (i > 32) {
/* 2304 */         if (i == 47 || i == 35) {
/* 2305 */           return _skipColon2(i, true);
/*      */         }
/* 2307 */         return i;
/*      */       } 
/* 2309 */       if (i == 32 || i == 9) {
/* 2310 */         i = this._inputData.readUnsignedByte();
/* 2311 */         if (i > 32) {
/* 2312 */           if (i == 47 || i == 35) {
/* 2313 */             return _skipColon2(i, true);
/*      */           }
/* 2315 */           return i;
/*      */         } 
/*      */       } 
/* 2318 */       return _skipColon2(i, true);
/*      */     } 
/* 2320 */     return _skipColon2(i, false);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _skipColon2(int i, boolean gotColon) throws IOException {
/* 2325 */     for (;; i = this._inputData.readUnsignedByte()) {
/* 2326 */       if (i > 32) {
/* 2327 */         if (i == 47) {
/* 2328 */           _skipComment();
/*      */         
/*      */         }
/* 2331 */         else if (i != 35 || 
/* 2332 */           !_skipYAMLComment()) {
/*      */ 
/*      */ 
/*      */           
/* 2336 */           if (gotColon) {
/* 2337 */             return i;
/*      */           }
/* 2339 */           if (i != 58) {
/* 2340 */             _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
/*      */           }
/* 2342 */           gotColon = true;
/*      */         }
/*      */       
/*      */       }
/* 2346 */       else if (i == 13 || i == 10) {
/* 2347 */         this._currInputRow++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipComment() throws IOException {
/* 2355 */     if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 2356 */       _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
/*      */     }
/* 2358 */     int c = this._inputData.readUnsignedByte();
/* 2359 */     if (c == 47) {
/* 2360 */       _skipLine();
/* 2361 */     } else if (c == 42) {
/* 2362 */       _skipCComment();
/*      */     } else {
/* 2364 */       _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipCComment() throws IOException {
/* 2371 */     int[] codes = CharTypes.getInputCodeComment();
/* 2372 */     int i = this._inputData.readUnsignedByte();
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2377 */       int code = codes[i];
/* 2378 */       if (code != 0)
/* 2379 */         switch (code) {
/*      */           case 42:
/* 2381 */             i = this._inputData.readUnsignedByte();
/* 2382 */             if (i == 47) {
/*      */               return;
/*      */             }
/*      */             continue;
/*      */           case 10:
/*      */           case 13:
/* 2388 */             this._currInputRow++;
/*      */             break;
/*      */           case 2:
/* 2391 */             _skipUtf8_2();
/*      */             break;
/*      */           case 3:
/* 2394 */             _skipUtf8_3();
/*      */             break;
/*      */           case 4:
/* 2397 */             _skipUtf8_4();
/*      */             break;
/*      */           
/*      */           default:
/* 2401 */             _reportInvalidChar(i);
/*      */             break;
/*      */         }  
/* 2404 */       i = this._inputData.readUnsignedByte();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final boolean _skipYAMLComment() throws IOException {
/* 2410 */     if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
/* 2411 */       return false;
/*      */     }
/* 2413 */     _skipLine();
/* 2414 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipLine() throws IOException {
/* 2424 */     int[] codes = CharTypes.getInputCodeComment();
/*      */     while (true) {
/* 2426 */       int i = this._inputData.readUnsignedByte();
/* 2427 */       int code = codes[i];
/* 2428 */       if (code != 0) {
/* 2429 */         switch (code) {
/*      */           case 10:
/*      */           case 13:
/* 2432 */             this._currInputRow++;
/*      */             return;
/*      */           case 42:
/*      */             continue;
/*      */           case 2:
/* 2437 */             _skipUtf8_2();
/*      */             continue;
/*      */           case 3:
/* 2440 */             _skipUtf8_3();
/*      */             continue;
/*      */           case 4:
/* 2443 */             _skipUtf8_4();
/*      */             continue;
/*      */         } 
/* 2446 */         if (code < 0)
/*      */         {
/* 2448 */           _reportInvalidChar(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char _decodeEscaped() throws IOException {
/* 2458 */     int c = this._inputData.readUnsignedByte();
/*      */     
/* 2460 */     switch (c) {
/*      */       
/*      */       case 98:
/* 2463 */         return '\b';
/*      */       case 116:
/* 2465 */         return '\t';
/*      */       case 110:
/* 2467 */         return '\n';
/*      */       case 102:
/* 2469 */         return '\f';
/*      */       case 114:
/* 2471 */         return '\r';
/*      */ 
/*      */       
/*      */       case 34:
/*      */       case 47:
/*      */       case 92:
/* 2477 */         return (char)c;
/*      */       
/*      */       case 117:
/*      */         break;
/*      */       
/*      */       default:
/* 2483 */         return _handleUnrecognizedCharacterEscape((char)_decodeCharForError(c));
/*      */     } 
/*      */ 
/*      */     
/* 2487 */     int value = 0;
/* 2488 */     for (int i = 0; i < 4; i++) {
/* 2489 */       int ch = this._inputData.readUnsignedByte();
/* 2490 */       int digit = CharTypes.charToHex(ch);
/* 2491 */       if (digit < 0) {
/* 2492 */         _reportUnexpectedChar(ch, "expected a hex-digit for character escape sequence");
/*      */       }
/* 2494 */       value = value << 4 | digit;
/*      */     } 
/* 2496 */     return (char)value;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int _decodeCharForError(int firstByte) throws IOException {
/* 2501 */     int c = firstByte & 0xFF;
/* 2502 */     if (c > 127) {
/*      */       int needed;
/*      */ 
/*      */       
/* 2506 */       if ((c & 0xE0) == 192) {
/* 2507 */         c &= 0x1F;
/* 2508 */         needed = 1;
/* 2509 */       } else if ((c & 0xF0) == 224) {
/* 2510 */         c &= 0xF;
/* 2511 */         needed = 2;
/* 2512 */       } else if ((c & 0xF8) == 240) {
/*      */         
/* 2514 */         c &= 0x7;
/* 2515 */         needed = 3;
/*      */       } else {
/* 2517 */         _reportInvalidInitial(c & 0xFF);
/* 2518 */         needed = 1;
/*      */       } 
/*      */       
/* 2521 */       int d = this._inputData.readUnsignedByte();
/* 2522 */       if ((d & 0xC0) != 128) {
/* 2523 */         _reportInvalidOther(d & 0xFF);
/*      */       }
/* 2525 */       c = c << 6 | d & 0x3F;
/*      */       
/* 2527 */       if (needed > 1) {
/* 2528 */         d = this._inputData.readUnsignedByte();
/* 2529 */         if ((d & 0xC0) != 128) {
/* 2530 */           _reportInvalidOther(d & 0xFF);
/*      */         }
/* 2532 */         c = c << 6 | d & 0x3F;
/* 2533 */         if (needed > 2) {
/* 2534 */           d = this._inputData.readUnsignedByte();
/* 2535 */           if ((d & 0xC0) != 128) {
/* 2536 */             _reportInvalidOther(d & 0xFF);
/*      */           }
/* 2538 */           c = c << 6 | d & 0x3F;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2542 */     return c;
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
/* 2553 */     int d = this._inputData.readUnsignedByte();
/* 2554 */     if ((d & 0xC0) != 128) {
/* 2555 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2557 */     return (c & 0x1F) << 6 | d & 0x3F;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _decodeUtf8_3(int c1) throws IOException {
/* 2562 */     c1 &= 0xF;
/* 2563 */     int d = this._inputData.readUnsignedByte();
/* 2564 */     if ((d & 0xC0) != 128) {
/* 2565 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2567 */     int c = c1 << 6 | d & 0x3F;
/* 2568 */     d = this._inputData.readUnsignedByte();
/* 2569 */     if ((d & 0xC0) != 128) {
/* 2570 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2572 */     c = c << 6 | d & 0x3F;
/* 2573 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _decodeUtf8_4(int c) throws IOException {
/* 2582 */     int d = this._inputData.readUnsignedByte();
/* 2583 */     if ((d & 0xC0) != 128) {
/* 2584 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2586 */     c = (c & 0x7) << 6 | d & 0x3F;
/* 2587 */     d = this._inputData.readUnsignedByte();
/* 2588 */     if ((d & 0xC0) != 128) {
/* 2589 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2591 */     c = c << 6 | d & 0x3F;
/* 2592 */     d = this._inputData.readUnsignedByte();
/* 2593 */     if ((d & 0xC0) != 128) {
/* 2594 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2600 */     return (c << 6 | d & 0x3F) - 65536;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _skipUtf8_2() throws IOException {
/* 2605 */     int c = this._inputData.readUnsignedByte();
/* 2606 */     if ((c & 0xC0) != 128) {
/* 2607 */       _reportInvalidOther(c & 0xFF);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _skipUtf8_3() throws IOException {
/* 2617 */     int c = this._inputData.readUnsignedByte();
/* 2618 */     if ((c & 0xC0) != 128) {
/* 2619 */       _reportInvalidOther(c & 0xFF);
/*      */     }
/* 2621 */     c = this._inputData.readUnsignedByte();
/* 2622 */     if ((c & 0xC0) != 128) {
/* 2623 */       _reportInvalidOther(c & 0xFF);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _skipUtf8_4() throws IOException {
/* 2629 */     int d = this._inputData.readUnsignedByte();
/* 2630 */     if ((d & 0xC0) != 128) {
/* 2631 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2633 */     d = this._inputData.readUnsignedByte();
/* 2634 */     if ((d & 0xC0) != 128) {
/* 2635 */       _reportInvalidOther(d & 0xFF);
/*      */     }
/* 2637 */     d = this._inputData.readUnsignedByte();
/* 2638 */     if ((d & 0xC0) != 128) {
/* 2639 */       _reportInvalidOther(d & 0xFF);
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
/*      */   protected void _reportInvalidToken(int ch, String matchedPart) throws IOException {
/* 2651 */     _reportInvalidToken(ch, matchedPart, "'null', 'true', 'false' or NaN");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidToken(int ch, String matchedPart, String msg) throws IOException {
/* 2657 */     StringBuilder sb = new StringBuilder(matchedPart);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2664 */       char c = (char)_decodeCharForError(ch);
/* 2665 */       if (!Character.isJavaIdentifierPart(c)) {
/*      */         break;
/*      */       }
/* 2668 */       sb.append(c);
/* 2669 */       ch = this._inputData.readUnsignedByte();
/*      */     } 
/* 2671 */     _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidChar(int c) throws JsonParseException {
/* 2678 */     if (c < 32) {
/* 2679 */       _throwInvalidSpace(c);
/*      */     }
/* 2681 */     _reportInvalidInitial(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportInvalidInitial(int mask) throws JsonParseException {
/* 2687 */     _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(mask));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void _reportInvalidOther(int mask) throws JsonParseException {
/* 2693 */     _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(mask));
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] _growArrayBy(int[] arr, int more) {
/* 2698 */     if (arr == null) {
/* 2699 */       return new int[more];
/*      */     }
/* 2701 */     return Arrays.copyOf(arr, arr.length + more);
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
/* 2717 */     ByteArrayBuilder builder = _getByteArrayBuilder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2724 */       int ch = this._inputData.readUnsignedByte();
/* 2725 */       if (ch > 32) {
/* 2726 */         int bits = b64variant.decodeBase64Char(ch);
/* 2727 */         if (bits < 0) {
/* 2728 */           if (ch == 34) {
/* 2729 */             return builder.toByteArray();
/*      */           }
/* 2731 */           bits = _decodeBase64Escape(b64variant, ch, 0);
/* 2732 */           if (bits < 0) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 2736 */         int decodedData = bits;
/*      */ 
/*      */         
/* 2739 */         ch = this._inputData.readUnsignedByte();
/* 2740 */         bits = b64variant.decodeBase64Char(ch);
/* 2741 */         if (bits < 0) {
/* 2742 */           bits = _decodeBase64Escape(b64variant, ch, 1);
/*      */         }
/* 2744 */         decodedData = decodedData << 6 | bits;
/*      */         
/* 2746 */         ch = this._inputData.readUnsignedByte();
/* 2747 */         bits = b64variant.decodeBase64Char(ch);
/*      */ 
/*      */         
/* 2750 */         if (bits < 0) {
/* 2751 */           if (bits != -2) {
/*      */             
/* 2753 */             if (ch == 34 && !b64variant.usesPadding()) {
/* 2754 */               decodedData >>= 4;
/* 2755 */               builder.append(decodedData);
/* 2756 */               return builder.toByteArray();
/*      */             } 
/* 2758 */             bits = _decodeBase64Escape(b64variant, ch, 2);
/*      */           } 
/* 2760 */           if (bits == -2) {
/* 2761 */             ch = this._inputData.readUnsignedByte();
/* 2762 */             if (!b64variant.usesPaddingChar(ch)) {
/* 2763 */               throw reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
/*      */             }
/*      */             
/* 2766 */             decodedData >>= 4;
/* 2767 */             builder.append(decodedData);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 2772 */         decodedData = decodedData << 6 | bits;
/*      */         
/* 2774 */         ch = this._inputData.readUnsignedByte();
/* 2775 */         bits = b64variant.decodeBase64Char(ch);
/* 2776 */         if (bits < 0) {
/* 2777 */           if (bits != -2) {
/*      */             
/* 2779 */             if (ch == 34 && !b64variant.usesPadding()) {
/* 2780 */               decodedData >>= 2;
/* 2781 */               builder.appendTwoBytes(decodedData);
/* 2782 */               return builder.toByteArray();
/*      */             } 
/* 2784 */             bits = _decodeBase64Escape(b64variant, ch, 3);
/*      */           } 
/* 2786 */           if (bits == -2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2793 */             decodedData >>= 2;
/* 2794 */             builder.appendTwoBytes(decodedData);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 2799 */         decodedData = decodedData << 6 | bits;
/* 2800 */         builder.appendThreeBytes(decodedData);
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
/*      */   public JsonLocation getTokenLocation() {
/* 2812 */     return new JsonLocation(_getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonLocation getCurrentLocation() {
/* 2817 */     return new JsonLocation(_getSourceReference(), -1L, -1L, this._currInputRow, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _closeScope(int i) throws JsonParseException {
/* 2827 */     if (i == 93) {
/* 2828 */       if (!this._parsingContext.inArray()) {
/* 2829 */         _reportMismatchedEndMarker(i, '}');
/*      */       }
/* 2831 */       this._parsingContext = this._parsingContext.clearAndGetParent();
/* 2832 */       this._currToken = JsonToken.END_ARRAY;
/*      */     } 
/* 2834 */     if (i == 125) {
/* 2835 */       if (!this._parsingContext.inObject()) {
/* 2836 */         _reportMismatchedEndMarker(i, ']');
/*      */       }
/* 2838 */       this._parsingContext = this._parsingContext.clearAndGetParent();
/* 2839 */       this._currToken = JsonToken.END_OBJECT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int pad(int q, int bytes) {
/* 2847 */     return (bytes == 4) ? q : (q | -1 << bytes << 3);
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\UTF8DataInputJsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */