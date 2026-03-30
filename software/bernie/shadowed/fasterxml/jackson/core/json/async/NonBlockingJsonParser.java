/*      */ package software.bernie.shadowed.fasterxml.jackson.core.json.async;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.async.ByteArrayFeeder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.async.NonBlockingInputFeeder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*      */ 
/*      */ 
/*      */ public class NonBlockingJsonParser
/*      */   extends NonBlockingJsonParserBase
/*      */   implements ByteArrayFeeder
/*      */ {
/*   19 */   private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
/*      */ 
/*      */ 
/*      */   
/*   23 */   protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   34 */   protected byte[] _inputBuffer = NO_BYTES;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _origBufferLen;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NonBlockingJsonParser(IOContext ctxt, int parserFeatures, ByteQuadsCanonicalizer sym) {
/*   57 */     super(ctxt, parserFeatures, sym);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteArrayFeeder getNonBlockingInputFeeder() {
/*   68 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean needMoreInput() {
/*   73 */     return (this._inputPtr >= this._inputEnd && !this._endOfInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void feedInput(byte[] buf, int start, int end) throws IOException {
/*   80 */     if (this._inputPtr < this._inputEnd) {
/*   81 */       _reportError("Still have %d undecoded bytes, should not call 'feedInput'", Integer.valueOf(this._inputEnd - this._inputPtr));
/*      */     }
/*   83 */     if (end < start) {
/*   84 */       _reportError("Input end (%d) may not be before start (%d)", Integer.valueOf(end), Integer.valueOf(start));
/*      */     }
/*      */     
/*   87 */     if (this._endOfInput) {
/*   88 */       _reportError("Already closed, can not feed more input");
/*      */     }
/*      */     
/*   91 */     this._currInputProcessed += this._origBufferLen;
/*      */ 
/*      */     
/*   94 */     this._currInputRowStart = start - this._inputEnd - this._currInputRowStart;
/*      */ 
/*      */     
/*   97 */     this._inputBuffer = buf;
/*   98 */     this._inputPtr = start;
/*   99 */     this._inputEnd = end;
/*  100 */     this._origBufferLen = end - start;
/*      */   }
/*      */ 
/*      */   
/*      */   public void endOfInput() {
/*  105 */     this._endOfInput = true;
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
/*      */   public int releaseBuffered(OutputStream out) throws IOException {
/*  127 */     int avail = this._inputEnd - this._inputPtr;
/*  128 */     if (avail > 0) {
/*  129 */       out.write(this._inputBuffer, this._inputPtr, avail);
/*      */     }
/*  131 */     return avail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char _decodeEscaped() throws IOException {
/*  138 */     VersionUtil.throwInternal();
/*  139 */     return ' ';
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
/*      */   public JsonToken nextToken() throws IOException {
/*  153 */     if (this._inputPtr >= this._inputEnd) {
/*  154 */       if (this._closed) {
/*  155 */         return null;
/*      */       }
/*      */       
/*  158 */       if (this._endOfInput) {
/*      */ 
/*      */         
/*  161 */         if (this._currToken == JsonToken.NOT_AVAILABLE) {
/*  162 */           return _finishTokenWithEOF();
/*      */         }
/*  164 */         return _eofAsNextToken();
/*      */       } 
/*  166 */       return JsonToken.NOT_AVAILABLE;
/*      */     } 
/*      */     
/*  169 */     if (this._currToken == JsonToken.NOT_AVAILABLE) {
/*  170 */       return _finishToken();
/*      */     }
/*      */ 
/*      */     
/*  174 */     this._numTypesValid = 0;
/*  175 */     this._tokenInputTotal = this._currInputProcessed + this._inputPtr;
/*      */     
/*  177 */     this._binaryValue = null;
/*  178 */     int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */     
/*  180 */     switch (this._majorState) {
/*      */       case 0:
/*  182 */         return _startDocument(ch);
/*      */       
/*      */       case 1:
/*  185 */         return _startValue(ch);
/*      */       
/*      */       case 2:
/*  188 */         return _startFieldName(ch);
/*      */       case 3:
/*  190 */         return _startFieldNameAfterComma(ch);
/*      */       
/*      */       case 4:
/*  193 */         return _startValueExpectColon(ch);
/*      */       
/*      */       case 5:
/*  196 */         return _startValue(ch);
/*      */       
/*      */       case 6:
/*  199 */         return _startValueExpectComma(ch);
/*      */     } 
/*      */ 
/*      */     
/*  203 */     VersionUtil.throwInternal();
/*  204 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonToken _finishToken() throws IOException {
/*      */     int c;
/*  215 */     switch (this._minorState) {
/*      */       case 1:
/*  217 */         return _finishBOM(this._pending32);
/*      */       case 4:
/*  219 */         return _startFieldName(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       case 5:
/*  221 */         return _startFieldNameAfterComma(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */ 
/*      */       
/*      */       case 7:
/*  225 */         return _parseEscapedName(this._quadLength, this._pending32, this._pendingBytes);
/*      */       case 8:
/*  227 */         return _finishFieldWithEscape();
/*      */       case 9:
/*  229 */         return _finishAposName(this._quadLength, this._pending32, this._pendingBytes);
/*      */       case 10:
/*  231 */         return _finishUnquotedName(this._quadLength, this._pending32, this._pendingBytes);
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/*  236 */         return _startValue(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       case 15:
/*  238 */         return _startValueAfterComma(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       case 13:
/*  240 */         return _startValueExpectComma(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       case 14:
/*  242 */         return _startValueExpectColon(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       
/*      */       case 16:
/*  245 */         return _finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
/*      */       case 17:
/*  247 */         return _finishKeywordToken("true", this._pending32, JsonToken.VALUE_TRUE);
/*      */       case 18:
/*  249 */         return _finishKeywordToken("false", this._pending32, JsonToken.VALUE_FALSE);
/*      */       case 19:
/*  251 */         return _finishNonStdToken(this._nonStdTokenType, this._pending32);
/*      */       
/*      */       case 23:
/*  254 */         return _finishNumberMinus(this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       case 24:
/*  256 */         return _finishNumberLeadingZeroes();
/*      */       case 25:
/*  258 */         return _finishNumberLeadingNegZeroes();
/*      */       case 26:
/*  260 */         return _finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
/*      */       
/*      */       case 30:
/*  263 */         return _finishFloatFraction();
/*      */       case 31:
/*  265 */         return _finishFloatExponent(true, this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       case 32:
/*  267 */         return _finishFloatExponent(false, this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */       
/*      */       case 40:
/*  270 */         return _finishRegularString();
/*      */       case 42:
/*  272 */         this._textBuffer.append((char)_decodeUTF8_2(this._pending32, this._inputBuffer[this._inputPtr++]));
/*  273 */         if (this._minorStateAfterSplit == 45) {
/*  274 */           return _finishAposString();
/*      */         }
/*  276 */         return _finishRegularString();
/*      */       case 43:
/*  278 */         if (!_decodeSplitUTF8_3(this._pending32, this._pendingBytes, this._inputBuffer[this._inputPtr++])) {
/*  279 */           return JsonToken.NOT_AVAILABLE;
/*      */         }
/*  281 */         if (this._minorStateAfterSplit == 45) {
/*  282 */           return _finishAposString();
/*      */         }
/*  284 */         return _finishRegularString();
/*      */       case 44:
/*  286 */         if (!_decodeSplitUTF8_4(this._pending32, this._pendingBytes, this._inputBuffer[this._inputPtr++])) {
/*  287 */           return JsonToken.NOT_AVAILABLE;
/*      */         }
/*  289 */         if (this._minorStateAfterSplit == 45) {
/*  290 */           return _finishAposString();
/*      */         }
/*  292 */         return _finishRegularString();
/*      */ 
/*      */       
/*      */       case 41:
/*  296 */         c = _decodeSplitEscaped(this._quoted32, this._quotedDigits);
/*  297 */         if (c < 0) {
/*  298 */           return JsonToken.NOT_AVAILABLE;
/*      */         }
/*  300 */         this._textBuffer.append((char)c);
/*      */         
/*  302 */         if (this._minorStateAfterSplit == 45) {
/*  303 */           return _finishAposString();
/*      */         }
/*  305 */         return _finishRegularString();
/*      */       
/*      */       case 45:
/*  308 */         return _finishAposString();
/*      */       
/*      */       case 50:
/*  311 */         return _finishErrorToken();
/*      */ 
/*      */ 
/*      */       
/*      */       case 51:
/*  316 */         return _startSlashComment(this._pending32);
/*      */       case 52:
/*  318 */         return _finishCComment(this._pending32, true);
/*      */       case 53:
/*  320 */         return _finishCComment(this._pending32, false);
/*      */       case 54:
/*  322 */         return _finishCppComment(this._pending32);
/*      */       case 55:
/*  324 */         return _finishHashComment(this._pending32);
/*      */     } 
/*  326 */     VersionUtil.throwInternal();
/*  327 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonToken _finishTokenWithEOF() throws IOException {
/*      */     int len;
/*  339 */     JsonToken t = this._currToken;
/*  340 */     switch (this._minorState) {
/*      */       case 3:
/*  342 */         return _eofAsNextToken();
/*      */       case 12:
/*  344 */         return _eofAsNextToken();
/*      */ 
/*      */       
/*      */       case 16:
/*  348 */         return _finishKeywordTokenWithEOF("null", this._pending32, JsonToken.VALUE_NULL);
/*      */       case 17:
/*  350 */         return _finishKeywordTokenWithEOF("true", this._pending32, JsonToken.VALUE_TRUE);
/*      */       case 18:
/*  352 */         return _finishKeywordTokenWithEOF("false", this._pending32, JsonToken.VALUE_FALSE);
/*      */       case 19:
/*  354 */         return _finishNonStdTokenWithEOF(this._nonStdTokenType, this._pending32);
/*      */       case 50:
/*  356 */         return _finishErrorTokenWithEOF();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 24:
/*      */       case 25:
/*  363 */         return _valueCompleteInt(0, "0");
/*      */ 
/*      */       
/*      */       case 26:
/*  367 */         len = this._textBuffer.getCurrentSegmentSize();
/*  368 */         if (this._numberNegative) {
/*  369 */           len--;
/*      */         }
/*  371 */         this._intLength = len;
/*      */         
/*  373 */         return _valueComplete(JsonToken.VALUE_NUMBER_INT);
/*      */       
/*      */       case 30:
/*  376 */         this._expLength = 0;
/*      */       
/*      */       case 32:
/*  379 */         return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
/*      */       
/*      */       case 31:
/*  382 */         _reportInvalidEOF(": was expecting fraction after exponent marker", JsonToken.VALUE_NUMBER_FLOAT);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 52:
/*      */       case 53:
/*  390 */         _reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
/*      */ 
/*      */       
/*      */       case 54:
/*      */       case 55:
/*  395 */         return _eofAsNextToken();
/*      */     } 
/*      */ 
/*      */     
/*  399 */     _reportInvalidEOF(": was expecting rest of token (internal state: " + this._minorState + ")", this._currToken);
/*  400 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _startDocument(int ch) throws IOException {
/*  411 */     ch &= 0xFF;
/*      */ 
/*      */     
/*  414 */     if (ch == 239 && this._minorState != 1) {
/*  415 */       return _finishBOM(1);
/*      */     }
/*      */ 
/*      */     
/*  419 */     while (ch <= 32) {
/*  420 */       if (ch != 32) {
/*  421 */         if (ch == 10) {
/*  422 */           this._currInputRow++;
/*  423 */           this._currInputRowStart = this._inputPtr;
/*  424 */         } else if (ch == 13) {
/*  425 */           this._currInputRowAlt++;
/*  426 */           this._currInputRowStart = this._inputPtr;
/*  427 */         } else if (ch != 9) {
/*  428 */           _throwInvalidSpace(ch);
/*      */         } 
/*      */       }
/*  431 */       if (this._inputPtr >= this._inputEnd) {
/*  432 */         this._minorState = 3;
/*  433 */         if (this._closed) {
/*  434 */           return null;
/*      */         }
/*      */         
/*  437 */         if (this._endOfInput) {
/*  438 */           return _eofAsNextToken();
/*      */         }
/*  440 */         return JsonToken.NOT_AVAILABLE;
/*      */       } 
/*  442 */       ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*      */     } 
/*  444 */     return _startValue(ch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _finishBOM(int bytesHandled) throws IOException {
/*  453 */     while (this._inputPtr < this._inputEnd) {
/*  454 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  455 */       switch (bytesHandled) {
/*      */ 
/*      */         
/*      */         case 3:
/*  459 */           this._currInputProcessed -= 3L;
/*  460 */           return _startDocument(ch);
/*      */         case 2:
/*  462 */           if (ch != 191) {
/*  463 */             _reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", Integer.valueOf(ch));
/*      */           }
/*      */           break;
/*      */         case 1:
/*  467 */           if (ch != 187) {
/*  468 */             _reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", Integer.valueOf(ch));
/*      */           }
/*      */           break;
/*      */       } 
/*  472 */       bytesHandled++;
/*      */     } 
/*  474 */     this._pending32 = bytesHandled;
/*  475 */     this._minorState = 1;
/*  476 */     return this._currToken = JsonToken.NOT_AVAILABLE;
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
/*      */   private final JsonToken _startFieldName(int ch) throws IOException {
/*  492 */     if (ch <= 32) {
/*  493 */       ch = _skipWS(ch);
/*  494 */       if (ch <= 0) {
/*  495 */         this._minorState = 4;
/*  496 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  499 */     _updateTokenLocation();
/*  500 */     if (ch != 34) {
/*  501 */       if (ch == 125) {
/*  502 */         return _closeObjectScope();
/*      */       }
/*  504 */       return _handleOddName(ch);
/*      */     } 
/*      */     
/*  507 */     if (this._inputPtr + 13 <= this._inputEnd) {
/*  508 */       String n = _fastParseName();
/*  509 */       if (n != null) {
/*  510 */         return _fieldComplete(n);
/*      */       }
/*      */     } 
/*  513 */     return _parseEscapedName(0, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _startFieldNameAfterComma(int ch) throws IOException {
/*  519 */     if (ch <= 32) {
/*  520 */       ch = _skipWS(ch);
/*  521 */       if (ch <= 0) {
/*  522 */         this._minorState = 5;
/*  523 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  526 */     if (ch != 44) {
/*  527 */       if (ch == 125) {
/*  528 */         return _closeObjectScope();
/*      */       }
/*  530 */       if (ch == 35) {
/*  531 */         return _finishHashComment(5);
/*      */       }
/*  533 */       if (ch == 47) {
/*  534 */         return _startSlashComment(5);
/*      */       }
/*  536 */       _reportUnexpectedChar(ch, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */     } 
/*  538 */     int ptr = this._inputPtr;
/*  539 */     if (ptr >= this._inputEnd) {
/*  540 */       this._minorState = 4;
/*  541 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/*  543 */     ch = this._inputBuffer[ptr];
/*  544 */     this._inputPtr = ptr + 1;
/*  545 */     if (ch <= 32) {
/*  546 */       ch = _skipWS(ch);
/*  547 */       if (ch <= 0) {
/*  548 */         this._minorState = 4;
/*  549 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  552 */     _updateTokenLocation();
/*  553 */     if (ch != 34) {
/*  554 */       if (ch == 125 && 
/*  555 */         JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features)) {
/*  556 */         return _closeObjectScope();
/*      */       }
/*      */       
/*  559 */       return _handleOddName(ch);
/*      */     } 
/*      */     
/*  562 */     if (this._inputPtr + 13 <= this._inputEnd) {
/*  563 */       String n = _fastParseName();
/*  564 */       if (n != null) {
/*  565 */         return _fieldComplete(n);
/*      */       }
/*      */     } 
/*  568 */     return _parseEscapedName(0, 0, 0);
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
/*      */   private final JsonToken _startValue(int ch) throws IOException {
/*  585 */     if (ch <= 32) {
/*  586 */       ch = _skipWS(ch);
/*  587 */       if (ch <= 0) {
/*  588 */         this._minorState = 12;
/*  589 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  592 */     _updateTokenLocation();
/*  593 */     if (ch == 34) {
/*  594 */       return _startString();
/*      */     }
/*  596 */     switch (ch) {
/*      */       case 35:
/*  598 */         return _finishHashComment(12);
/*      */       case 45:
/*  600 */         return _startNegativeNumber();
/*      */       case 47:
/*  602 */         return _startSlashComment(12);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 48:
/*  608 */         return _startNumberLeadingZero();
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*  618 */         return _startPositiveNumber(ch);
/*      */       case 102:
/*  620 */         return _startFalseToken();
/*      */       case 110:
/*  622 */         return _startNullToken();
/*      */       case 116:
/*  624 */         return _startTrueToken();
/*      */       case 91:
/*  626 */         return _startArrayScope();
/*      */       case 93:
/*  628 */         return _closeArrayScope();
/*      */       case 123:
/*  630 */         return _startObjectScope();
/*      */       case 125:
/*  632 */         return _closeObjectScope();
/*      */     } 
/*      */     
/*  635 */     return _startUnexpectedValue(false, ch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _startValueExpectComma(int ch) throws IOException {
/*  645 */     if (ch <= 32) {
/*  646 */       ch = _skipWS(ch);
/*  647 */       if (ch <= 0) {
/*  648 */         this._minorState = 13;
/*  649 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  652 */     if (ch != 44) {
/*  653 */       if (ch == 93) {
/*  654 */         return _closeArrayScope();
/*      */       }
/*  656 */       if (ch == 125) {
/*  657 */         return _closeObjectScope();
/*      */       }
/*  659 */       if (ch == 47) {
/*  660 */         return _startSlashComment(13);
/*      */       }
/*  662 */       if (ch == 35) {
/*  663 */         return _finishHashComment(13);
/*      */       }
/*  665 */       _reportUnexpectedChar(ch, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
/*      */     } 
/*  667 */     int ptr = this._inputPtr;
/*  668 */     if (ptr >= this._inputEnd) {
/*  669 */       this._minorState = 15;
/*  670 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/*  672 */     ch = this._inputBuffer[ptr];
/*  673 */     this._inputPtr = ptr + 1;
/*  674 */     if (ch <= 32) {
/*  675 */       ch = _skipWS(ch);
/*  676 */       if (ch <= 0) {
/*  677 */         this._minorState = 15;
/*  678 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  681 */     _updateTokenLocation();
/*  682 */     if (ch == 34) {
/*  683 */       return _startString();
/*      */     }
/*  685 */     switch (ch) {
/*      */       case 35:
/*  687 */         return _finishHashComment(15);
/*      */       case 45:
/*  689 */         return _startNegativeNumber();
/*      */       case 47:
/*  691 */         return _startSlashComment(15);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 48:
/*  697 */         return _startNumberLeadingZero();
/*      */       case 49: case 50: case 51: case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*  704 */         return _startPositiveNumber(ch);
/*      */       case 102:
/*  706 */         return _startFalseToken();
/*      */       case 110:
/*  708 */         return _startNullToken();
/*      */       case 116:
/*  710 */         return _startTrueToken();
/*      */       case 91:
/*  712 */         return _startArrayScope();
/*      */       
/*      */       case 93:
/*  715 */         if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
/*  716 */           return _closeArrayScope();
/*      */         }
/*      */         break;
/*      */       case 123:
/*  720 */         return _startObjectScope();
/*      */       
/*      */       case 125:
/*  723 */         if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
/*  724 */           return _closeObjectScope();
/*      */         }
/*      */         break;
/*      */     } 
/*      */     
/*  729 */     return _startUnexpectedValue(true, ch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _startValueExpectColon(int ch) throws IOException {
/*  740 */     if (ch <= 32) {
/*  741 */       ch = _skipWS(ch);
/*  742 */       if (ch <= 0) {
/*  743 */         this._minorState = 14;
/*  744 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  747 */     if (ch != 58) {
/*  748 */       if (ch == 47) {
/*  749 */         return _startSlashComment(14);
/*      */       }
/*  751 */       if (ch == 35) {
/*  752 */         return _finishHashComment(14);
/*      */       }
/*      */       
/*  755 */       _reportUnexpectedChar(ch, "was expecting a colon to separate field name and value");
/*      */     } 
/*  757 */     int ptr = this._inputPtr;
/*  758 */     if (ptr >= this._inputEnd) {
/*  759 */       this._minorState = 12;
/*  760 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/*  762 */     ch = this._inputBuffer[ptr];
/*  763 */     this._inputPtr = ptr + 1;
/*  764 */     if (ch <= 32) {
/*  765 */       ch = _skipWS(ch);
/*  766 */       if (ch <= 0) {
/*  767 */         this._minorState = 12;
/*  768 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  771 */     _updateTokenLocation();
/*  772 */     if (ch == 34) {
/*  773 */       return _startString();
/*      */     }
/*  775 */     switch (ch) {
/*      */       case 35:
/*  777 */         return _finishHashComment(12);
/*      */       case 45:
/*  779 */         return _startNegativeNumber();
/*      */       case 47:
/*  781 */         return _startSlashComment(12);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 48:
/*  787 */         return _startNumberLeadingZero();
/*      */       case 49: case 50: case 51: case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*  794 */         return _startPositiveNumber(ch);
/*      */       case 102:
/*  796 */         return _startFalseToken();
/*      */       case 110:
/*  798 */         return _startNullToken();
/*      */       case 116:
/*  800 */         return _startTrueToken();
/*      */       case 91:
/*  802 */         return _startArrayScope();
/*      */       case 123:
/*  804 */         return _startObjectScope();
/*      */     } 
/*      */     
/*  807 */     return _startUnexpectedValue(false, ch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _startValueAfterComma(int ch) throws IOException {
/*  815 */     if (ch <= 32) {
/*  816 */       ch = _skipWS(ch);
/*  817 */       if (ch <= 0) {
/*  818 */         this._minorState = 15;
/*  819 */         return this._currToken;
/*      */       } 
/*      */     } 
/*  822 */     _updateTokenLocation();
/*  823 */     if (ch == 34) {
/*  824 */       return _startString();
/*      */     }
/*  826 */     switch (ch) {
/*      */       case 35:
/*  828 */         return _finishHashComment(15);
/*      */       case 45:
/*  830 */         return _startNegativeNumber();
/*      */       case 47:
/*  832 */         return _startSlashComment(15);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 48:
/*  838 */         return _startNumberLeadingZero();
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*  848 */         return _startPositiveNumber(ch);
/*      */       case 102:
/*  850 */         return _startFalseToken();
/*      */       case 110:
/*  852 */         return _startNullToken();
/*      */       case 116:
/*  854 */         return _startTrueToken();
/*      */       case 91:
/*  856 */         return _startArrayScope();
/*      */       
/*      */       case 93:
/*  859 */         if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
/*  860 */           return _closeArrayScope();
/*      */         }
/*      */         break;
/*      */       case 123:
/*  864 */         return _startObjectScope();
/*      */       
/*      */       case 125:
/*  867 */         if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
/*  868 */           return _closeObjectScope();
/*      */         }
/*      */         break;
/*      */     } 
/*      */     
/*  873 */     return _startUnexpectedValue(true, ch);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _startUnexpectedValue(boolean leadingComma, int ch) throws IOException {
/*  878 */     switch (ch) {
/*      */       case 93:
/*  880 */         if (!this._parsingContext.inArray()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 44:
/*  888 */         if (isEnabled(JsonParser.Feature.ALLOW_MISSING_VALUES)) {
/*  889 */           this._inputPtr--;
/*  890 */           return _valueComplete(JsonToken.VALUE_NULL);
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 39:
/*  898 */         if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/*  899 */           return _startAposString();
/*      */         }
/*      */         break;
/*      */       case 43:
/*  903 */         return _finishNonStdToken(2, 1);
/*      */       case 78:
/*  905 */         return _finishNonStdToken(0, 1);
/*      */       case 73:
/*  907 */         return _finishNonStdToken(1, 1);
/*      */     } 
/*      */     
/*  910 */     _reportUnexpectedChar(ch, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/*  911 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _skipWS(int ch) throws IOException {
/*      */     while (true) {
/*  923 */       if (ch != 32) {
/*  924 */         if (ch == 10) {
/*  925 */           this._currInputRow++;
/*  926 */           this._currInputRowStart = this._inputPtr;
/*  927 */         } else if (ch == 13) {
/*  928 */           this._currInputRowAlt++;
/*  929 */           this._currInputRowStart = this._inputPtr;
/*  930 */         } else if (ch != 9) {
/*  931 */           _throwInvalidSpace(ch);
/*      */         } 
/*      */       }
/*  934 */       if (this._inputPtr >= this._inputEnd) {
/*  935 */         this._currToken = JsonToken.NOT_AVAILABLE;
/*  936 */         return 0;
/*      */       } 
/*  938 */       ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  939 */       if (ch > 32)
/*  940 */         return ch; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private final JsonToken _startSlashComment(int fromMinorState) throws IOException {
/*  945 */     if (!JsonParser.Feature.ALLOW_COMMENTS.enabledIn(this._features)) {
/*  946 */       _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
/*      */     }
/*      */ 
/*      */     
/*  950 */     if (this._inputPtr >= this._inputEnd) {
/*  951 */       this._pending32 = fromMinorState;
/*  952 */       this._minorState = 51;
/*  953 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/*  955 */     int ch = this._inputBuffer[this._inputPtr++];
/*  956 */     if (ch == 42) {
/*  957 */       return _finishCComment(fromMinorState, false);
/*      */     }
/*  959 */     if (ch == 47) {
/*  960 */       return _finishCppComment(fromMinorState);
/*      */     }
/*  962 */     _reportUnexpectedChar(ch & 0xFF, "was expecting either '*' or '/' for a comment");
/*  963 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _finishHashComment(int fromMinorState) throws IOException {
/*  969 */     if (!JsonParser.Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
/*  970 */       _reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
/*      */     }
/*      */     while (true) {
/*  973 */       if (this._inputPtr >= this._inputEnd) {
/*  974 */         this._minorState = 55;
/*  975 */         this._pending32 = fromMinorState;
/*  976 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/*  978 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/*  979 */       if (ch < 32) {
/*  980 */         if (ch == 10) {
/*  981 */           this._currInputRow++;
/*  982 */           this._currInputRowStart = this._inputPtr; break;
/*      */         } 
/*  984 */         if (ch == 13) {
/*  985 */           this._currInputRowAlt++;
/*  986 */           this._currInputRowStart = this._inputPtr; break;
/*      */         } 
/*  988 */         if (ch != 9) {
/*  989 */           _throwInvalidSpace(ch);
/*      */         }
/*      */       } 
/*      */     } 
/*  993 */     return _startAfterComment(fromMinorState);
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _finishCppComment(int fromMinorState) throws IOException {
/*      */     while (true) {
/*  999 */       if (this._inputPtr >= this._inputEnd) {
/* 1000 */         this._minorState = 54;
/* 1001 */         this._pending32 = fromMinorState;
/* 1002 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1004 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1005 */       if (ch < 32) {
/* 1006 */         if (ch == 10) {
/* 1007 */           this._currInputRow++;
/* 1008 */           this._currInputRowStart = this._inputPtr; break;
/*      */         } 
/* 1010 */         if (ch == 13) {
/* 1011 */           this._currInputRowAlt++;
/* 1012 */           this._currInputRowStart = this._inputPtr; break;
/*      */         } 
/* 1014 */         if (ch != 9) {
/* 1015 */           _throwInvalidSpace(ch);
/*      */         }
/*      */       } 
/*      */     } 
/* 1019 */     return _startAfterComment(fromMinorState);
/*      */   }
/*      */ 
/*      */   
/*      */   private final JsonToken _finishCComment(int fromMinorState, boolean gotStar) throws IOException {
/*      */     while (true) {
/* 1025 */       if (this._inputPtr >= this._inputEnd) {
/* 1026 */         this._minorState = gotStar ? 52 : 53;
/* 1027 */         this._pending32 = fromMinorState;
/* 1028 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1030 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1031 */       if (ch < 32)
/* 1032 */       { if (ch == 10) {
/* 1033 */           this._currInputRow++;
/* 1034 */           this._currInputRowStart = this._inputPtr;
/* 1035 */         } else if (ch == 13) {
/* 1036 */           this._currInputRowAlt++;
/* 1037 */           this._currInputRowStart = this._inputPtr;
/* 1038 */         } else if (ch != 9) {
/* 1039 */           _throwInvalidSpace(ch);
/*      */         }  }
/* 1041 */       else { if (ch == 42) {
/* 1042 */           gotStar = true; continue;
/*      */         } 
/* 1044 */         if (ch == 47 && 
/* 1045 */           gotStar) {
/*      */           break;
/*      */         } }
/*      */       
/* 1049 */       gotStar = false;
/*      */     } 
/* 1051 */     return _startAfterComment(fromMinorState);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _startAfterComment(int fromMinorState) throws IOException {
/* 1057 */     if (this._inputPtr >= this._inputEnd) {
/* 1058 */       this._minorState = fromMinorState;
/* 1059 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/* 1061 */     int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1062 */     switch (fromMinorState) {
/*      */       case 4:
/* 1064 */         return _startFieldName(ch);
/*      */       case 5:
/* 1066 */         return _startFieldNameAfterComma(ch);
/*      */       case 12:
/* 1068 */         return _startValue(ch);
/*      */       case 13:
/* 1070 */         return _startValueExpectComma(ch);
/*      */       case 14:
/* 1072 */         return _startValueExpectColon(ch);
/*      */       case 15:
/* 1074 */         return _startValueAfterComma(ch);
/*      */     } 
/*      */     
/* 1077 */     VersionUtil.throwInternal();
/* 1078 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _startFalseToken() throws IOException {
/* 1089 */     int ptr = this._inputPtr;
/* 1090 */     if (ptr + 4 < this._inputEnd) {
/* 1091 */       byte[] buf = this._inputBuffer;
/* 1092 */       if (buf[ptr++] == 97 && buf[ptr++] == 108 && buf[ptr++] == 115 && buf[ptr++] == 101) {
/*      */ 
/*      */ 
/*      */         
/* 1096 */         int ch = buf[ptr] & 0xFF;
/* 1097 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 1098 */           this._inputPtr = ptr;
/* 1099 */           return _valueComplete(JsonToken.VALUE_FALSE);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1103 */     this._minorState = 18;
/* 1104 */     return _finishKeywordToken("false", 1, JsonToken.VALUE_FALSE);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _startTrueToken() throws IOException {
/* 1109 */     int ptr = this._inputPtr;
/* 1110 */     if (ptr + 3 < this._inputEnd) {
/* 1111 */       byte[] buf = this._inputBuffer;
/* 1112 */       if (buf[ptr++] == 114 && buf[ptr++] == 117 && buf[ptr++] == 101) {
/*      */ 
/*      */         
/* 1115 */         int ch = buf[ptr] & 0xFF;
/* 1116 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 1117 */           this._inputPtr = ptr;
/* 1118 */           return _valueComplete(JsonToken.VALUE_TRUE);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1122 */     this._minorState = 17;
/* 1123 */     return _finishKeywordToken("true", 1, JsonToken.VALUE_TRUE);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _startNullToken() throws IOException {
/* 1128 */     int ptr = this._inputPtr;
/* 1129 */     if (ptr + 3 < this._inputEnd) {
/* 1130 */       byte[] buf = this._inputBuffer;
/* 1131 */       if (buf[ptr++] == 117 && buf[ptr++] == 108 && buf[ptr++] == 108) {
/*      */ 
/*      */         
/* 1134 */         int ch = buf[ptr] & 0xFF;
/* 1135 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 1136 */           this._inputPtr = ptr;
/* 1137 */           return _valueComplete(JsonToken.VALUE_NULL);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1141 */     this._minorState = 16;
/* 1142 */     return _finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _finishKeywordToken(String expToken, int matched, JsonToken result) throws IOException {
/* 1148 */     int end = expToken.length();
/*      */     
/*      */     while (true) {
/* 1151 */       if (this._inputPtr >= this._inputEnd) {
/* 1152 */         this._pending32 = matched;
/* 1153 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1155 */       int ch = this._inputBuffer[this._inputPtr];
/* 1156 */       if (matched == end) {
/* 1157 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 1158 */           return _valueComplete(result);
/*      */         }
/*      */         break;
/*      */       } 
/* 1162 */       if (ch != expToken.charAt(matched)) {
/*      */         break;
/*      */       }
/* 1165 */       matched++;
/* 1166 */       this._inputPtr++;
/*      */     } 
/* 1168 */     this._minorState = 50;
/* 1169 */     this._textBuffer.resetWithCopy(expToken, 0, matched);
/* 1170 */     return _finishErrorToken();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _finishKeywordTokenWithEOF(String expToken, int matched, JsonToken result) throws IOException {
/* 1176 */     if (matched == expToken.length()) {
/* 1177 */       return this._currToken = result;
/*      */     }
/* 1179 */     this._textBuffer.resetWithCopy(expToken, 0, matched);
/* 1180 */     return _finishErrorTokenWithEOF();
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishNonStdToken(int type, int matched) throws IOException {
/* 1185 */     String expToken = _nonStdToken(type);
/* 1186 */     int end = expToken.length();
/*      */     
/*      */     while (true) {
/* 1189 */       if (this._inputPtr >= this._inputEnd) {
/* 1190 */         this._nonStdTokenType = type;
/* 1191 */         this._pending32 = matched;
/* 1192 */         this._minorState = 19;
/* 1193 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1195 */       int ch = this._inputBuffer[this._inputPtr];
/* 1196 */       if (matched == end) {
/* 1197 */         if (ch < 48 || ch == 93 || ch == 125) {
/* 1198 */           return _valueNonStdNumberComplete(type);
/*      */         }
/*      */         break;
/*      */       } 
/* 1202 */       if (ch != expToken.charAt(matched)) {
/*      */         break;
/*      */       }
/* 1205 */       matched++;
/* 1206 */       this._inputPtr++;
/*      */     } 
/* 1208 */     this._minorState = 50;
/* 1209 */     this._textBuffer.resetWithCopy(expToken, 0, matched);
/* 1210 */     return _finishErrorToken();
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishNonStdTokenWithEOF(int type, int matched) throws IOException {
/* 1215 */     String expToken = _nonStdToken(type);
/* 1216 */     if (matched == expToken.length()) {
/* 1217 */       return _valueNonStdNumberComplete(type);
/*      */     }
/* 1219 */     this._textBuffer.resetWithCopy(expToken, 0, matched);
/* 1220 */     return _finishErrorTokenWithEOF();
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishErrorToken() throws IOException {
/* 1225 */     while (this._inputPtr < this._inputEnd) {
/* 1226 */       int i = this._inputBuffer[this._inputPtr++];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1231 */       char ch = (char)i;
/* 1232 */       if (Character.isJavaIdentifierPart(ch)) {
/*      */ 
/*      */         
/* 1235 */         this._textBuffer.append(ch);
/* 1236 */         if (this._textBuffer.size() < 256) {
/*      */           continue;
/*      */         }
/*      */       } 
/* 1240 */       return _reportErrorToken(this._textBuffer.contentsAsString());
/*      */     } 
/* 1242 */     return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishErrorTokenWithEOF() throws IOException {
/* 1247 */     return _reportErrorToken(this._textBuffer.contentsAsString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _reportErrorToken(String actualToken) throws IOException {
/* 1253 */     _reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), "'null', 'true' or 'false'");
/*      */     
/* 1255 */     return JsonToken.NOT_AVAILABLE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _startPositiveNumber(int ch) throws IOException {
/* 1266 */     this._numberNegative = false;
/* 1267 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1268 */     outBuf[0] = (char)ch;
/*      */     
/* 1270 */     if (this._inputPtr >= this._inputEnd) {
/* 1271 */       this._minorState = 26;
/* 1272 */       this._textBuffer.setCurrentLength(1);
/* 1273 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/*      */     
/* 1276 */     int outPtr = 1;
/*      */     
/* 1278 */     ch = this._inputBuffer[this._inputPtr] & 0xFF;
/*      */     while (true) {
/* 1280 */       if (ch < 48) {
/* 1281 */         if (ch == 46) {
/* 1282 */           this._intLength = outPtr;
/* 1283 */           this._inputPtr++;
/* 1284 */           return _startFloat(outBuf, outPtr, ch);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1288 */       if (ch > 57) {
/* 1289 */         if (ch == 101 || ch == 69) {
/* 1290 */           this._intLength = outPtr;
/* 1291 */           this._inputPtr++;
/* 1292 */           return _startFloat(outBuf, outPtr, ch);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1296 */       if (outPtr >= outBuf.length)
/*      */       {
/*      */         
/* 1299 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1301 */       outBuf[outPtr++] = (char)ch;
/* 1302 */       if (++this._inputPtr >= this._inputEnd) {
/* 1303 */         this._minorState = 26;
/* 1304 */         this._textBuffer.setCurrentLength(outPtr);
/* 1305 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1307 */       ch = this._inputBuffer[this._inputPtr] & 0xFF;
/*      */     } 
/* 1309 */     this._intLength = outPtr;
/* 1310 */     this._textBuffer.setCurrentLength(outPtr);
/* 1311 */     return _valueComplete(JsonToken.VALUE_NUMBER_INT);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _startNegativeNumber() throws IOException {
/* 1316 */     this._numberNegative = true;
/* 1317 */     if (this._inputPtr >= this._inputEnd) {
/* 1318 */       this._minorState = 23;
/* 1319 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/* 1321 */     int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1322 */     if (ch <= 48) {
/* 1323 */       if (ch == 48) {
/* 1324 */         return _finishNumberLeadingNegZeroes();
/*      */       }
/*      */       
/* 1327 */       reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 1328 */     } else if (ch > 57) {
/* 1329 */       if (ch == 73) {
/* 1330 */         return _finishNonStdToken(3, 2);
/*      */       }
/* 1332 */       reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/*      */     } 
/* 1334 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1335 */     outBuf[0] = '-';
/* 1336 */     outBuf[1] = (char)ch;
/* 1337 */     if (this._inputPtr >= this._inputEnd) {
/* 1338 */       this._minorState = 26;
/* 1339 */       this._textBuffer.setCurrentLength(2);
/* 1340 */       this._intLength = 1;
/* 1341 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/* 1343 */     ch = this._inputBuffer[this._inputPtr];
/* 1344 */     int outPtr = 2;
/*      */     
/*      */     while (true) {
/* 1347 */       if (ch < 48) {
/* 1348 */         if (ch == 46) {
/* 1349 */           this._intLength = outPtr - 1;
/* 1350 */           this._inputPtr++;
/* 1351 */           return _startFloat(outBuf, outPtr, ch);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1355 */       if (ch > 57) {
/* 1356 */         if (ch == 101 || ch == 69) {
/* 1357 */           this._intLength = outPtr - 1;
/* 1358 */           this._inputPtr++;
/* 1359 */           return _startFloat(outBuf, outPtr, ch);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1363 */       if (outPtr >= outBuf.length)
/*      */       {
/* 1365 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1367 */       outBuf[outPtr++] = (char)ch;
/* 1368 */       if (++this._inputPtr >= this._inputEnd) {
/* 1369 */         this._minorState = 26;
/* 1370 */         this._textBuffer.setCurrentLength(outPtr);
/* 1371 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1373 */       ch = this._inputBuffer[this._inputPtr] & 0xFF;
/*      */     } 
/* 1375 */     this._intLength = outPtr - 1;
/* 1376 */     this._textBuffer.setCurrentLength(outPtr);
/* 1377 */     return _valueComplete(JsonToken.VALUE_NUMBER_INT);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _startNumberLeadingZero() throws IOException {
/* 1382 */     int ptr = this._inputPtr;
/* 1383 */     if (ptr >= this._inputEnd) {
/* 1384 */       this._minorState = 24;
/* 1385 */       return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1392 */     int ch = this._inputBuffer[ptr++] & 0xFF;
/*      */     
/* 1394 */     if (ch < 48) {
/* 1395 */       if (ch == 46) {
/* 1396 */         this._inputPtr = ptr;
/* 1397 */         this._intLength = 1;
/* 1398 */         char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1399 */         outBuf[0] = '0';
/* 1400 */         return _startFloat(outBuf, 1, ch);
/*      */       } 
/* 1402 */     } else if (ch > 57) {
/* 1403 */       if (ch == 101 || ch == 69) {
/* 1404 */         this._inputPtr = ptr;
/* 1405 */         this._intLength = 1;
/* 1406 */         char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1407 */         outBuf[0] = '0';
/* 1408 */         return _startFloat(outBuf, 1, ch);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1413 */       if (ch != 93 && ch != 125) {
/* 1414 */         reportUnexpectedNumberChar(ch, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1419 */       return _finishNumberLeadingZeroes();
/*      */     } 
/*      */     
/* 1422 */     return _valueCompleteInt(0, "0");
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishNumberMinus(int ch) throws IOException {
/* 1427 */     if (ch <= 48) {
/* 1428 */       if (ch == 48) {
/* 1429 */         return _finishNumberLeadingNegZeroes();
/*      */       }
/* 1431 */       reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 1432 */     } else if (ch > 57) {
/* 1433 */       if (ch == 73) {
/* 1434 */         return _finishNonStdToken(3, 2);
/*      */       }
/* 1436 */       reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
/*      */     } 
/* 1438 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 1439 */     outBuf[0] = '-';
/* 1440 */     outBuf[1] = (char)ch;
/* 1441 */     this._intLength = 1;
/* 1442 */     return _finishNumberIntegralPart(outBuf, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _finishNumberLeadingZeroes() throws IOException {
/*      */     while (true) {
/* 1450 */       if (this._inputPtr >= this._inputEnd) {
/* 1451 */         this._minorState = 24;
/* 1452 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1454 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1455 */       if (ch < 48) {
/* 1456 */         if (ch == 46) {
/* 1457 */           char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1458 */           arrayOfChar[0] = '0';
/* 1459 */           this._intLength = 1;
/* 1460 */           return _startFloat(arrayOfChar, 1, ch);
/*      */         }  break;
/* 1462 */       }  if (ch > 57) {
/* 1463 */         if (ch == 101 || ch == 69) {
/* 1464 */           char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1465 */           arrayOfChar[0] = '0';
/* 1466 */           this._intLength = 1;
/* 1467 */           return _startFloat(arrayOfChar, 1, ch);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1472 */         if (ch != 93 && ch != 125) {
/* 1473 */           reportUnexpectedNumberChar(ch, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1479 */       if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1480 */         reportInvalidNumber("Leading zeroes not allowed");
/*      */       }
/* 1482 */       if (ch == 48) {
/*      */         continue;
/*      */       }
/* 1485 */       char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/*      */       
/* 1487 */       outBuf[0] = (char)ch;
/* 1488 */       this._intLength = 1;
/* 1489 */       return _finishNumberIntegralPart(outBuf, 1);
/*      */     } 
/* 1491 */     this._inputPtr--;
/* 1492 */     return _valueCompleteInt(0, "0");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
/*      */     while (true) {
/* 1501 */       if (this._inputPtr >= this._inputEnd) {
/* 1502 */         this._minorState = 25;
/* 1503 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1505 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1506 */       if (ch < 48) {
/* 1507 */         if (ch == 46) {
/* 1508 */           char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1509 */           arrayOfChar[0] = '-';
/* 1510 */           arrayOfChar[1] = '0';
/* 1511 */           this._intLength = 1;
/* 1512 */           return _startFloat(arrayOfChar, 2, ch);
/*      */         }  break;
/* 1514 */       }  if (ch > 57) {
/* 1515 */         if (ch == 101 || ch == 69) {
/* 1516 */           char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1517 */           arrayOfChar[0] = '-';
/* 1518 */           arrayOfChar[1] = '0';
/* 1519 */           this._intLength = 1;
/* 1520 */           return _startFloat(arrayOfChar, 2, ch);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1525 */         if (ch != 93 && ch != 125) {
/* 1526 */           reportUnexpectedNumberChar(ch, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1532 */       if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1533 */         reportInvalidNumber("Leading zeroes not allowed");
/*      */       }
/* 1535 */       if (ch == 48) {
/*      */         continue;
/*      */       }
/* 1538 */       char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/*      */       
/* 1540 */       outBuf[0] = '-';
/* 1541 */       outBuf[1] = (char)ch;
/* 1542 */       this._intLength = 1;
/* 1543 */       return _finishNumberIntegralPart(outBuf, 2);
/*      */     } 
/* 1545 */     this._inputPtr--;
/* 1546 */     return _valueCompleteInt(0, "0");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _finishNumberIntegralPart(char[] outBuf, int outPtr) throws IOException {
/* 1552 */     int negMod = this._numberNegative ? -1 : 0;
/*      */     
/*      */     while (true) {
/* 1555 */       if (this._inputPtr >= this._inputEnd) {
/* 1556 */         this._minorState = 26;
/* 1557 */         this._textBuffer.setCurrentLength(outPtr);
/* 1558 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1560 */       int ch = this._inputBuffer[this._inputPtr] & 0xFF;
/* 1561 */       if (ch < 48) {
/* 1562 */         if (ch == 46) {
/* 1563 */           this._intLength = outPtr + negMod;
/* 1564 */           this._inputPtr++;
/* 1565 */           return _startFloat(outBuf, outPtr, ch);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1569 */       if (ch > 57) {
/* 1570 */         if (ch == 101 || ch == 69) {
/* 1571 */           this._intLength = outPtr + negMod;
/* 1572 */           this._inputPtr++;
/* 1573 */           return _startFloat(outBuf, outPtr, ch);
/*      */         } 
/*      */         break;
/*      */       } 
/* 1577 */       this._inputPtr++;
/* 1578 */       if (outPtr >= outBuf.length)
/*      */       {
/*      */         
/* 1581 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1583 */       outBuf[outPtr++] = (char)ch;
/*      */     } 
/* 1585 */     this._intLength = outPtr + negMod;
/* 1586 */     this._textBuffer.setCurrentLength(outPtr);
/* 1587 */     return _valueComplete(JsonToken.VALUE_NUMBER_INT);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _startFloat(char[] outBuf, int outPtr, int ch) throws IOException {
/* 1592 */     int fractLen = 0;
/* 1593 */     if (ch == 46) {
/* 1594 */       if (outPtr >= outBuf.length) {
/* 1595 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1597 */       outBuf[outPtr++] = '.';
/*      */       while (true) {
/* 1599 */         if (this._inputPtr >= this._inputEnd) {
/* 1600 */           this._textBuffer.setCurrentLength(outPtr);
/* 1601 */           this._minorState = 30;
/* 1602 */           this._fractLength = fractLen;
/* 1603 */           return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */         } 
/* 1605 */         ch = this._inputBuffer[this._inputPtr++];
/* 1606 */         if (ch < 48 || ch > 57) {
/* 1607 */           ch &= 0xFF;
/*      */           
/* 1609 */           if (fractLen == 0) {
/* 1610 */             reportUnexpectedNumberChar(ch, "Decimal point not followed by a digit");
/*      */           }
/*      */           break;
/*      */         } 
/* 1614 */         if (outPtr >= outBuf.length) {
/* 1615 */           outBuf = this._textBuffer.expandCurrentSegment();
/*      */         }
/* 1617 */         outBuf[outPtr++] = (char)ch;
/* 1618 */         fractLen++;
/*      */       } 
/*      */     } 
/* 1621 */     this._fractLength = fractLen;
/* 1622 */     int expLen = 0;
/* 1623 */     if (ch == 101 || ch == 69) {
/* 1624 */       if (outPtr >= outBuf.length) {
/* 1625 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1627 */       outBuf[outPtr++] = (char)ch;
/* 1628 */       if (this._inputPtr >= this._inputEnd) {
/* 1629 */         this._textBuffer.setCurrentLength(outPtr);
/* 1630 */         this._minorState = 31;
/* 1631 */         this._expLength = 0;
/* 1632 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1634 */       ch = this._inputBuffer[this._inputPtr++];
/* 1635 */       if (ch == 45 || ch == 43) {
/* 1636 */         if (outPtr >= outBuf.length) {
/* 1637 */           outBuf = this._textBuffer.expandCurrentSegment();
/*      */         }
/* 1639 */         outBuf[outPtr++] = (char)ch;
/* 1640 */         if (this._inputPtr >= this._inputEnd) {
/* 1641 */           this._textBuffer.setCurrentLength(outPtr);
/* 1642 */           this._minorState = 32;
/* 1643 */           this._expLength = 0;
/* 1644 */           return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */         } 
/* 1646 */         ch = this._inputBuffer[this._inputPtr++];
/*      */       } 
/* 1648 */       while (ch >= 48 && ch <= 57) {
/* 1649 */         expLen++;
/* 1650 */         if (outPtr >= outBuf.length) {
/* 1651 */           outBuf = this._textBuffer.expandCurrentSegment();
/*      */         }
/* 1653 */         outBuf[outPtr++] = (char)ch;
/* 1654 */         if (this._inputPtr >= this._inputEnd) {
/* 1655 */           this._textBuffer.setCurrentLength(outPtr);
/* 1656 */           this._minorState = 32;
/* 1657 */           this._expLength = expLen;
/* 1658 */           return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */         } 
/* 1660 */         ch = this._inputBuffer[this._inputPtr++];
/*      */       } 
/*      */       
/* 1663 */       ch &= 0xFF;
/* 1664 */       if (expLen == 0) {
/* 1665 */         reportUnexpectedNumberChar(ch, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     } 
/*      */     
/* 1669 */     this._inputPtr--;
/* 1670 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1672 */     this._expLength = expLen;
/* 1673 */     return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishFloatFraction() throws IOException {
/* 1678 */     int fractLen = this._fractLength;
/* 1679 */     char[] outBuf = this._textBuffer.getBufferWithoutReset();
/* 1680 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/*      */     
/*      */     int ch;
/*      */     
/* 1684 */     while ((ch = this._inputBuffer[this._inputPtr++]) >= 48 && ch <= 57) {
/* 1685 */       fractLen++;
/* 1686 */       if (outPtr >= outBuf.length) {
/* 1687 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1689 */       outBuf[outPtr++] = (char)ch;
/* 1690 */       if (this._inputPtr >= this._inputEnd) {
/* 1691 */         this._textBuffer.setCurrentLength(outPtr);
/* 1692 */         this._fractLength = fractLen;
/* 1693 */         return JsonToken.NOT_AVAILABLE;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1699 */     if (fractLen == 0) {
/* 1700 */       reportUnexpectedNumberChar(ch, "Decimal point not followed by a digit");
/*      */     }
/* 1702 */     this._fractLength = fractLen;
/* 1703 */     this._textBuffer.setCurrentLength(outPtr);
/*      */ 
/*      */     
/* 1706 */     if (ch == 101 || ch == 69) {
/* 1707 */       this._textBuffer.append((char)ch);
/* 1708 */       this._expLength = 0;
/* 1709 */       if (this._inputPtr >= this._inputEnd) {
/* 1710 */         this._minorState = 31;
/* 1711 */         return JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1713 */       this._minorState = 32;
/* 1714 */       return _finishFloatExponent(true, this._inputBuffer[this._inputPtr++] & 0xFF);
/*      */     } 
/*      */ 
/*      */     
/* 1718 */     this._inputPtr--;
/* 1719 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1721 */     this._expLength = 0;
/* 1722 */     return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JsonToken _finishFloatExponent(boolean checkSign, int ch) throws IOException {
/* 1727 */     if (checkSign) {
/* 1728 */       this._minorState = 32;
/* 1729 */       if (ch == 45 || ch == 43) {
/* 1730 */         this._textBuffer.append((char)ch);
/* 1731 */         if (this._inputPtr >= this._inputEnd) {
/* 1732 */           this._minorState = 32;
/* 1733 */           this._expLength = 0;
/* 1734 */           return JsonToken.NOT_AVAILABLE;
/*      */         } 
/* 1736 */         ch = this._inputBuffer[this._inputPtr++];
/*      */       } 
/*      */     } 
/*      */     
/* 1740 */     char[] outBuf = this._textBuffer.getBufferWithoutReset();
/* 1741 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/* 1742 */     int expLen = this._expLength;
/*      */     
/* 1744 */     while (ch >= 48 && ch <= 57) {
/* 1745 */       expLen++;
/* 1746 */       if (outPtr >= outBuf.length) {
/* 1747 */         outBuf = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 1749 */       outBuf[outPtr++] = (char)ch;
/* 1750 */       if (this._inputPtr >= this._inputEnd) {
/* 1751 */         this._textBuffer.setCurrentLength(outPtr);
/* 1752 */         this._expLength = expLen;
/* 1753 */         return JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1755 */       ch = this._inputBuffer[this._inputPtr++];
/*      */     } 
/*      */     
/* 1758 */     ch &= 0xFF;
/* 1759 */     if (expLen == 0) {
/* 1760 */       reportUnexpectedNumberChar(ch, "Exponent indicator not followed by a digit");
/*      */     }
/*      */     
/* 1763 */     this._inputPtr--;
/* 1764 */     this._textBuffer.setCurrentLength(outPtr);
/*      */     
/* 1766 */     this._expLength = expLen;
/* 1767 */     return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
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
/*      */   private final String _fastParseName() throws IOException {
/* 1783 */     byte[] input = this._inputBuffer;
/* 1784 */     int[] codes = _icLatin1;
/* 1785 */     int ptr = this._inputPtr;
/*      */     
/* 1787 */     int q0 = input[ptr++] & 0xFF;
/* 1788 */     if (codes[q0] == 0) {
/* 1789 */       int i = input[ptr++] & 0xFF;
/* 1790 */       if (codes[i] == 0) {
/* 1791 */         int q = q0 << 8 | i;
/* 1792 */         i = input[ptr++] & 0xFF;
/* 1793 */         if (codes[i] == 0) {
/* 1794 */           q = q << 8 | i;
/* 1795 */           i = input[ptr++] & 0xFF;
/* 1796 */           if (codes[i] == 0) {
/* 1797 */             q = q << 8 | i;
/* 1798 */             i = input[ptr++] & 0xFF;
/* 1799 */             if (codes[i] == 0) {
/* 1800 */               this._quad1 = q;
/* 1801 */               return _parseMediumName(ptr, i);
/*      */             } 
/* 1803 */             if (i == 34) {
/* 1804 */               this._inputPtr = ptr;
/* 1805 */               return _findName(q, 4);
/*      */             } 
/* 1807 */             return null;
/*      */           } 
/* 1809 */           if (i == 34) {
/* 1810 */             this._inputPtr = ptr;
/* 1811 */             return _findName(q, 3);
/*      */           } 
/* 1813 */           return null;
/*      */         } 
/* 1815 */         if (i == 34) {
/* 1816 */           this._inputPtr = ptr;
/* 1817 */           return _findName(q, 2);
/*      */         } 
/* 1819 */         return null;
/*      */       } 
/* 1821 */       if (i == 34) {
/* 1822 */         this._inputPtr = ptr;
/* 1823 */         return _findName(q0, 1);
/*      */       } 
/* 1825 */       return null;
/*      */     } 
/* 1827 */     if (q0 == 34) {
/* 1828 */       this._inputPtr = ptr;
/* 1829 */       return "";
/*      */     } 
/* 1831 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private final String _parseMediumName(int ptr, int q2) throws IOException {
/* 1836 */     byte[] input = this._inputBuffer;
/* 1837 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1840 */     int i = input[ptr++] & 0xFF;
/* 1841 */     if (codes[i] == 0) {
/* 1842 */       q2 = q2 << 8 | i;
/* 1843 */       i = input[ptr++] & 0xFF;
/* 1844 */       if (codes[i] == 0) {
/* 1845 */         q2 = q2 << 8 | i;
/* 1846 */         i = input[ptr++] & 0xFF;
/* 1847 */         if (codes[i] == 0) {
/* 1848 */           q2 = q2 << 8 | i;
/* 1849 */           i = input[ptr++] & 0xFF;
/* 1850 */           if (codes[i] == 0) {
/* 1851 */             return _parseMediumName2(ptr, i, q2);
/*      */           }
/* 1853 */           if (i == 34) {
/* 1854 */             this._inputPtr = ptr;
/* 1855 */             return _findName(this._quad1, q2, 4);
/*      */           } 
/* 1857 */           return null;
/*      */         } 
/* 1859 */         if (i == 34) {
/* 1860 */           this._inputPtr = ptr;
/* 1861 */           return _findName(this._quad1, q2, 3);
/*      */         } 
/* 1863 */         return null;
/*      */       } 
/* 1865 */       if (i == 34) {
/* 1866 */         this._inputPtr = ptr;
/* 1867 */         return _findName(this._quad1, q2, 2);
/*      */       } 
/* 1869 */       return null;
/*      */     } 
/* 1871 */     if (i == 34) {
/* 1872 */       this._inputPtr = ptr;
/* 1873 */       return _findName(this._quad1, q2, 1);
/*      */     } 
/* 1875 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private final String _parseMediumName2(int ptr, int q3, int q2) throws IOException {
/* 1880 */     byte[] input = this._inputBuffer;
/* 1881 */     int[] codes = _icLatin1;
/*      */ 
/*      */     
/* 1884 */     int i = input[ptr++] & 0xFF;
/* 1885 */     if (codes[i] != 0) {
/* 1886 */       if (i == 34) {
/* 1887 */         this._inputPtr = ptr;
/* 1888 */         return _findName(this._quad1, q2, q3, 1);
/*      */       } 
/* 1890 */       return null;
/*      */     } 
/* 1892 */     q3 = q3 << 8 | i;
/* 1893 */     i = input[ptr++] & 0xFF;
/* 1894 */     if (codes[i] != 0) {
/* 1895 */       if (i == 34) {
/* 1896 */         this._inputPtr = ptr;
/* 1897 */         return _findName(this._quad1, q2, q3, 2);
/*      */       } 
/* 1899 */       return null;
/*      */     } 
/* 1901 */     q3 = q3 << 8 | i;
/* 1902 */     i = input[ptr++] & 0xFF;
/* 1903 */     if (codes[i] != 0) {
/* 1904 */       if (i == 34) {
/* 1905 */         this._inputPtr = ptr;
/* 1906 */         return _findName(this._quad1, q2, q3, 3);
/*      */       } 
/* 1908 */       return null;
/*      */     } 
/* 1910 */     q3 = q3 << 8 | i;
/* 1911 */     i = input[ptr++] & 0xFF;
/* 1912 */     if (i == 34) {
/* 1913 */       this._inputPtr = ptr;
/* 1914 */       return _findName(this._quad1, q2, q3, 4);
/*      */     } 
/*      */     
/* 1917 */     return null;
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
/*      */   private final JsonToken _parseEscapedName(int qlen, int currQuad, int currQuadBytes) throws IOException {
/* 1933 */     int[] quads = this._quadBuffer;
/* 1934 */     int[] codes = _icLatin1;
/*      */     
/*      */     while (true) {
/* 1937 */       if (this._inputPtr >= this._inputEnd) {
/* 1938 */         this._quadLength = qlen;
/* 1939 */         this._pending32 = currQuad;
/* 1940 */         this._pendingBytes = currQuadBytes;
/* 1941 */         this._minorState = 7;
/* 1942 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 1944 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 1945 */       if (codes[ch] == 0) {
/* 1946 */         if (currQuadBytes < 4) {
/* 1947 */           currQuadBytes++;
/* 1948 */           currQuad = currQuad << 8 | ch;
/*      */           continue;
/*      */         } 
/* 1951 */         if (qlen >= quads.length) {
/* 1952 */           this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */         }
/* 1954 */         quads[qlen++] = currQuad;
/* 1955 */         currQuad = ch;
/* 1956 */         currQuadBytes = 1;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1961 */       if (ch == 34) {
/*      */         break;
/*      */       }
/*      */       
/* 1965 */       if (ch != 92) {
/*      */         
/* 1967 */         _throwUnquotedSpace(ch, "name");
/*      */       } else {
/*      */         
/* 1970 */         ch = _decodeCharEscape();
/* 1971 */         if (ch < 0) {
/* 1972 */           this._minorState = 8;
/* 1973 */           this._minorStateAfterSplit = 7;
/* 1974 */           this._quadLength = qlen;
/* 1975 */           this._pending32 = currQuad;
/* 1976 */           this._pendingBytes = currQuadBytes;
/* 1977 */           return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1984 */       if (qlen >= quads.length) {
/* 1985 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 1987 */       if (ch > 127) {
/*      */         
/* 1989 */         if (currQuadBytes >= 4) {
/* 1990 */           quads[qlen++] = currQuad;
/* 1991 */           currQuad = 0;
/* 1992 */           currQuadBytes = 0;
/*      */         } 
/* 1994 */         if (ch < 2048) {
/* 1995 */           currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 1996 */           currQuadBytes++;
/*      */         } else {
/*      */           
/* 1999 */           currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/* 2000 */           currQuadBytes++;
/*      */           
/* 2002 */           if (currQuadBytes >= 4) {
/* 2003 */             quads[qlen++] = currQuad;
/* 2004 */             currQuad = 0;
/* 2005 */             currQuadBytes = 0;
/*      */           } 
/* 2007 */           currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 2008 */           currQuadBytes++;
/*      */         } 
/*      */         
/* 2011 */         ch = 0x80 | ch & 0x3F;
/*      */       } 
/* 2013 */       if (currQuadBytes < 4) {
/* 2014 */         currQuadBytes++;
/* 2015 */         currQuad = currQuad << 8 | ch;
/*      */         continue;
/*      */       } 
/* 2018 */       quads[qlen++] = currQuad;
/* 2019 */       currQuad = ch;
/* 2020 */       currQuadBytes = 1;
/*      */     } 
/*      */     
/* 2023 */     if (currQuadBytes > 0) {
/* 2024 */       if (qlen >= quads.length) {
/* 2025 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2027 */       quads[qlen++] = _padLastQuad(currQuad, currQuadBytes);
/* 2028 */     } else if (qlen == 0) {
/* 2029 */       return _fieldComplete("");
/*      */     } 
/* 2031 */     String name = this._symbols.findName(quads, qlen);
/* 2032 */     if (name == null) {
/* 2033 */       name = _addName(quads, qlen, currQuadBytes);
/*      */     }
/* 2035 */     return _fieldComplete(name);
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
/*      */   private JsonToken _handleOddName(int ch) throws IOException {
/* 2047 */     switch (ch) {
/*      */ 
/*      */       
/*      */       case 35:
/* 2051 */         if (JsonParser.Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
/* 2052 */           return _finishHashComment(4);
/*      */         }
/*      */         break;
/*      */       case 47:
/* 2056 */         return _startSlashComment(4);
/*      */       case 39:
/* 2058 */         if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 2059 */           return _finishAposName(0, 0, 0);
/*      */         }
/*      */         break;
/*      */       case 93:
/* 2063 */         return _closeArrayScope();
/*      */     } 
/*      */     
/* 2066 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/*      */ 
/*      */       
/* 2069 */       char c = (char)ch;
/* 2070 */       _reportUnexpectedChar(c, "was expecting double-quote to start field name");
/*      */     } 
/*      */ 
/*      */     
/* 2074 */     int[] codes = CharTypes.getInputCodeUtf8JsNames();
/*      */     
/* 2076 */     if (codes[ch] != 0) {
/* 2077 */       _reportUnexpectedChar(ch, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
/*      */     }
/*      */     
/* 2080 */     return _finishUnquotedName(0, ch, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JsonToken _finishUnquotedName(int qlen, int currQuad, int currQuadBytes) throws IOException {
/* 2091 */     int[] quads = this._quadBuffer;
/* 2092 */     int[] codes = CharTypes.getInputCodeUtf8JsNames();
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2097 */       if (this._inputPtr >= this._inputEnd) {
/* 2098 */         this._quadLength = qlen;
/* 2099 */         this._pending32 = currQuad;
/* 2100 */         this._pendingBytes = currQuadBytes;
/* 2101 */         this._minorState = 10;
/* 2102 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 2104 */       int ch = this._inputBuffer[this._inputPtr] & 0xFF;
/* 2105 */       if (codes[ch] != 0) {
/*      */         break;
/*      */       }
/* 2108 */       this._inputPtr++;
/*      */       
/* 2110 */       if (currQuadBytes < 4) {
/* 2111 */         currQuadBytes++;
/* 2112 */         currQuad = currQuad << 8 | ch; continue;
/*      */       } 
/* 2114 */       if (qlen >= quads.length) {
/* 2115 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2117 */       quads[qlen++] = currQuad;
/* 2118 */       currQuad = ch;
/* 2119 */       currQuadBytes = 1;
/*      */     } 
/*      */ 
/*      */     
/* 2123 */     if (currQuadBytes > 0) {
/* 2124 */       if (qlen >= quads.length) {
/* 2125 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2127 */       quads[qlen++] = currQuad;
/*      */     } 
/* 2129 */     String name = this._symbols.findName(quads, qlen);
/* 2130 */     if (name == null) {
/* 2131 */       name = _addName(quads, qlen, currQuadBytes);
/*      */     }
/* 2133 */     return _fieldComplete(name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private JsonToken _finishAposName(int qlen, int currQuad, int currQuadBytes) throws IOException {
/* 2139 */     int[] quads = this._quadBuffer;
/* 2140 */     int[] codes = _icLatin1;
/*      */     
/*      */     while (true) {
/* 2143 */       if (this._inputPtr >= this._inputEnd) {
/* 2144 */         this._quadLength = qlen;
/* 2145 */         this._pending32 = currQuad;
/* 2146 */         this._pendingBytes = currQuadBytes;
/* 2147 */         this._minorState = 9;
/* 2148 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 2150 */       int ch = this._inputBuffer[this._inputPtr++] & 0xFF;
/* 2151 */       if (ch == 39) {
/*      */         break;
/*      */       }
/*      */       
/* 2155 */       if (ch != 34 && codes[ch] != 0) {
/* 2156 */         if (ch != 92) {
/*      */           
/* 2158 */           _throwUnquotedSpace(ch, "name");
/*      */         } else {
/*      */           
/* 2161 */           ch = _decodeCharEscape();
/* 2162 */           if (ch < 0) {
/* 2163 */             this._minorState = 8;
/* 2164 */             this._minorStateAfterSplit = 9;
/* 2165 */             this._quadLength = qlen;
/* 2166 */             this._pending32 = currQuad;
/* 2167 */             this._pendingBytes = currQuadBytes;
/* 2168 */             return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */           } 
/*      */         } 
/* 2171 */         if (ch > 127) {
/*      */           
/* 2173 */           if (currQuadBytes >= 4) {
/* 2174 */             if (qlen >= quads.length) {
/* 2175 */               this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */             }
/* 2177 */             quads[qlen++] = currQuad;
/* 2178 */             currQuad = 0;
/* 2179 */             currQuadBytes = 0;
/*      */           } 
/* 2181 */           if (ch < 2048) {
/* 2182 */             currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 2183 */             currQuadBytes++;
/*      */           } else {
/*      */             
/* 2186 */             currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/* 2187 */             currQuadBytes++;
/*      */             
/* 2189 */             if (currQuadBytes >= 4) {
/* 2190 */               if (qlen >= quads.length) {
/* 2191 */                 this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */               }
/* 2193 */               quads[qlen++] = currQuad;
/* 2194 */               currQuad = 0;
/* 2195 */               currQuadBytes = 0;
/*      */             } 
/* 2197 */             currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 2198 */             currQuadBytes++;
/*      */           } 
/*      */           
/* 2201 */           ch = 0x80 | ch & 0x3F;
/*      */         } 
/*      */       } 
/*      */       
/* 2205 */       if (currQuadBytes < 4) {
/* 2206 */         currQuadBytes++;
/* 2207 */         currQuad = currQuad << 8 | ch; continue;
/*      */       } 
/* 2209 */       if (qlen >= quads.length) {
/* 2210 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2212 */       quads[qlen++] = currQuad;
/* 2213 */       currQuad = ch;
/* 2214 */       currQuadBytes = 1;
/*      */     } 
/*      */ 
/*      */     
/* 2218 */     if (currQuadBytes > 0) {
/* 2219 */       if (qlen >= quads.length) {
/* 2220 */         this._quadBuffer = quads = growArrayBy(quads, quads.length);
/*      */       }
/* 2222 */       quads[qlen++] = _padLastQuad(currQuad, currQuadBytes);
/* 2223 */     } else if (qlen == 0) {
/* 2224 */       return _fieldComplete("");
/*      */     } 
/* 2226 */     String name = this._symbols.findName(quads, qlen);
/* 2227 */     if (name == null) {
/* 2228 */       name = _addName(quads, qlen, currQuadBytes);
/*      */     }
/* 2230 */     return _fieldComplete(name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonToken _finishFieldWithEscape() throws IOException {
/* 2236 */     int ch = _decodeSplitEscaped(this._quoted32, this._quotedDigits);
/* 2237 */     if (ch < 0) {
/* 2238 */       this._minorState = 8;
/* 2239 */       return JsonToken.NOT_AVAILABLE;
/*      */     } 
/* 2241 */     if (this._quadLength >= this._quadBuffer.length) {
/* 2242 */       this._quadBuffer = growArrayBy(this._quadBuffer, 32);
/*      */     }
/* 2244 */     int currQuad = this._pending32;
/* 2245 */     int currQuadBytes = this._pendingBytes;
/* 2246 */     if (ch > 127) {
/*      */       
/* 2248 */       if (currQuadBytes >= 4) {
/* 2249 */         this._quadBuffer[this._quadLength++] = currQuad;
/* 2250 */         currQuad = 0;
/* 2251 */         currQuadBytes = 0;
/*      */       } 
/* 2253 */       if (ch < 2048) {
/* 2254 */         currQuad = currQuad << 8 | 0xC0 | ch >> 6;
/* 2255 */         currQuadBytes++;
/*      */       } else {
/*      */         
/* 2258 */         currQuad = currQuad << 8 | 0xE0 | ch >> 12;
/*      */         
/* 2260 */         if (++currQuadBytes >= 4) {
/* 2261 */           this._quadBuffer[this._quadLength++] = currQuad;
/* 2262 */           currQuad = 0;
/* 2263 */           currQuadBytes = 0;
/*      */         } 
/* 2265 */         currQuad = currQuad << 8 | 0x80 | ch >> 6 & 0x3F;
/* 2266 */         currQuadBytes++;
/*      */       } 
/*      */       
/* 2269 */       ch = 0x80 | ch & 0x3F;
/*      */     } 
/* 2271 */     if (currQuadBytes < 4) {
/* 2272 */       currQuadBytes++;
/* 2273 */       currQuad = currQuad << 8 | ch;
/*      */     } else {
/* 2275 */       this._quadBuffer[this._quadLength++] = currQuad;
/* 2276 */       currQuad = ch;
/* 2277 */       currQuadBytes = 1;
/*      */     } 
/* 2279 */     if (this._minorStateAfterSplit == 9) {
/* 2280 */       return _finishAposName(this._quadLength, currQuad, currQuadBytes);
/*      */     }
/* 2282 */     return _parseEscapedName(this._quadLength, currQuad, currQuadBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   private int _decodeSplitEscaped(int value, int bytesRead) throws IOException {
/* 2287 */     if (this._inputPtr >= this._inputEnd) {
/* 2288 */       this._quoted32 = value;
/* 2289 */       this._quotedDigits = bytesRead;
/* 2290 */       return -1;
/*      */     } 
/* 2292 */     int c = this._inputBuffer[this._inputPtr++];
/* 2293 */     if (bytesRead == -1) {
/* 2294 */       char ch; switch (c) {
/*      */         
/*      */         case 98:
/* 2297 */           return 8;
/*      */         case 116:
/* 2299 */           return 9;
/*      */         case 110:
/* 2301 */           return 10;
/*      */         case 102:
/* 2303 */           return 12;
/*      */         case 114:
/* 2305 */           return 13;
/*      */ 
/*      */         
/*      */         case 34:
/*      */         case 47:
/*      */         case 92:
/* 2311 */           return c;
/*      */ 
/*      */ 
/*      */         
/*      */         case 117:
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 2320 */           ch = (char)c;
/* 2321 */           return _handleUnrecognizedCharacterEscape(ch);
/*      */       } 
/*      */       
/* 2324 */       if (this._inputPtr >= this._inputEnd) {
/* 2325 */         this._quotedDigits = 0;
/* 2326 */         this._quoted32 = 0;
/* 2327 */         return -1;
/*      */       } 
/* 2329 */       c = this._inputBuffer[this._inputPtr++];
/* 2330 */       bytesRead = 0;
/*      */     } 
/* 2332 */     c &= 0xFF;
/*      */     while (true) {
/* 2334 */       int digit = CharTypes.charToHex(c);
/* 2335 */       if (digit < 0) {
/* 2336 */         _reportUnexpectedChar(c, "expected a hex-digit for character escape sequence");
/*      */       }
/* 2338 */       value = value << 4 | digit;
/* 2339 */       if (++bytesRead == 4) {
/* 2340 */         return value;
/*      */       }
/* 2342 */       if (this._inputPtr >= this._inputEnd) {
/* 2343 */         this._quotedDigits = bytesRead;
/* 2344 */         this._quoted32 = value;
/* 2345 */         return -1;
/*      */       } 
/* 2347 */       c = this._inputBuffer[this._inputPtr++] & 0xFF;
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
/*      */   protected JsonToken _startString() throws IOException {
/* 2359 */     int ptr = this._inputPtr;
/* 2360 */     int outPtr = 0;
/* 2361 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 2362 */     int[] codes = _icUTF8;
/*      */     
/* 2364 */     int max = Math.min(this._inputEnd, ptr + outBuf.length);
/* 2365 */     byte[] inputBuffer = this._inputBuffer;
/* 2366 */     while (ptr < max) {
/* 2367 */       int c = inputBuffer[ptr] & 0xFF;
/* 2368 */       if (codes[c] != 0) {
/* 2369 */         if (c == 34) {
/* 2370 */           this._inputPtr = ptr + 1;
/* 2371 */           this._textBuffer.setCurrentLength(outPtr);
/* 2372 */           return _valueComplete(JsonToken.VALUE_STRING);
/*      */         } 
/*      */         break;
/*      */       } 
/* 2376 */       ptr++;
/* 2377 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 2379 */     this._textBuffer.setCurrentLength(outPtr);
/* 2380 */     this._inputPtr = ptr;
/* 2381 */     return _finishRegularString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _finishRegularString() throws IOException {
/* 2389 */     int[] codes = _icUTF8;
/* 2390 */     byte[] inputBuffer = this._inputBuffer;
/*      */     
/* 2392 */     char[] outBuf = this._textBuffer.getBufferWithoutReset();
/* 2393 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/* 2394 */     int ptr = this._inputPtr;
/* 2395 */     int safeEnd = this._inputEnd - 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2402 */       if (ptr >= this._inputEnd) {
/* 2403 */         this._inputPtr = ptr;
/* 2404 */         this._minorState = 40;
/* 2405 */         this._textBuffer.setCurrentLength(outPtr);
/* 2406 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 2408 */       if (outPtr >= outBuf.length) {
/* 2409 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 2410 */         outPtr = 0;
/*      */       } 
/* 2412 */       int max = Math.min(this._inputEnd, ptr + outBuf.length - outPtr);
/* 2413 */       while (ptr < max) {
/* 2414 */         int c = inputBuffer[ptr++] & 0xFF;
/* 2415 */         if (codes[c] != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2422 */           if (c == 34) {
/* 2423 */             this._inputPtr = ptr;
/* 2424 */             this._textBuffer.setCurrentLength(outPtr);
/* 2425 */             return _valueComplete(JsonToken.VALUE_STRING);
/*      */           } 
/*      */           
/* 2428 */           if (ptr >= safeEnd) {
/* 2429 */             this._inputPtr = ptr;
/* 2430 */             this._textBuffer.setCurrentLength(outPtr);
/* 2431 */             if (!_decodeSplitMultiByte(c, codes[c], (ptr < this._inputEnd))) {
/* 2432 */               this._minorStateAfterSplit = 40;
/* 2433 */               return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */             } 
/* 2435 */             outBuf = this._textBuffer.getBufferWithoutReset();
/* 2436 */             outPtr = this._textBuffer.getCurrentSegmentSize();
/* 2437 */             ptr = this._inputPtr;
/*      */             
/*      */             continue;
/*      */           } 
/* 2441 */           switch (codes[c]) {
/*      */             case 1:
/* 2443 */               this._inputPtr = ptr;
/* 2444 */               c = _decodeFastCharEscape();
/* 2445 */               ptr = this._inputPtr;
/*      */               break;
/*      */             case 2:
/* 2448 */               c = _decodeUTF8_2(c, this._inputBuffer[ptr++]);
/*      */               break;
/*      */             case 3:
/* 2451 */               c = _decodeUTF8_3(c, this._inputBuffer[ptr++], this._inputBuffer[ptr++]);
/*      */               break;
/*      */             case 4:
/* 2454 */               c = _decodeUTF8_4(c, this._inputBuffer[ptr++], this._inputBuffer[ptr++], this._inputBuffer[ptr++]);
/*      */ 
/*      */               
/* 2457 */               outBuf[outPtr++] = (char)(0xD800 | c >> 10);
/* 2458 */               if (outPtr >= outBuf.length) {
/* 2459 */                 outBuf = this._textBuffer.finishCurrentSegment();
/* 2460 */                 outPtr = 0;
/*      */               } 
/* 2462 */               c = 0xDC00 | c & 0x3FF;
/*      */               break;
/*      */             
/*      */             default:
/* 2466 */               if (c < 32) {
/*      */                 
/* 2468 */                 _throwUnquotedSpace(c, "string value");
/*      */                 break;
/*      */               } 
/* 2471 */               _reportInvalidChar(c);
/*      */               break;
/*      */           } 
/*      */           
/* 2475 */           if (outPtr >= outBuf.length) {
/* 2476 */             outBuf = this._textBuffer.finishCurrentSegment();
/* 2477 */             outPtr = 0;
/*      */           } 
/*      */           
/* 2480 */           outBuf[outPtr++] = (char)c;
/*      */           continue;
/*      */         } 
/*      */         outBuf[outPtr++] = (char)c;
/*      */       } 
/*      */     }  } protected JsonToken _startAposString() throws IOException {
/* 2486 */     int ptr = this._inputPtr;
/* 2487 */     int outPtr = 0;
/* 2488 */     char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
/* 2489 */     int[] codes = _icUTF8;
/*      */     
/* 2491 */     int max = Math.min(this._inputEnd, ptr + outBuf.length);
/* 2492 */     byte[] inputBuffer = this._inputBuffer;
/* 2493 */     while (ptr < max) {
/* 2494 */       int c = inputBuffer[ptr] & 0xFF;
/* 2495 */       if (c == 39) {
/* 2496 */         this._inputPtr = ptr + 1;
/* 2497 */         this._textBuffer.setCurrentLength(outPtr);
/* 2498 */         return _valueComplete(JsonToken.VALUE_STRING);
/*      */       } 
/*      */       
/* 2501 */       if (codes[c] != 0) {
/*      */         break;
/*      */       }
/* 2504 */       ptr++;
/* 2505 */       outBuf[outPtr++] = (char)c;
/*      */     } 
/* 2507 */     this._textBuffer.setCurrentLength(outPtr);
/* 2508 */     this._inputPtr = ptr;
/* 2509 */     return _finishAposString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonToken _finishAposString() throws IOException {
/* 2515 */     int[] codes = _icUTF8;
/* 2516 */     byte[] inputBuffer = this._inputBuffer;
/*      */     
/* 2518 */     char[] outBuf = this._textBuffer.getBufferWithoutReset();
/* 2519 */     int outPtr = this._textBuffer.getCurrentSegmentSize();
/* 2520 */     int ptr = this._inputPtr;
/* 2521 */     int safeEnd = this._inputEnd - 5;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 2527 */       if (ptr >= this._inputEnd) {
/* 2528 */         this._inputPtr = ptr;
/* 2529 */         this._minorState = 45;
/* 2530 */         this._textBuffer.setCurrentLength(outPtr);
/* 2531 */         return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */       } 
/* 2533 */       if (outPtr >= outBuf.length) {
/* 2534 */         outBuf = this._textBuffer.finishCurrentSegment();
/* 2535 */         outPtr = 0;
/*      */       } 
/* 2537 */       int max = Math.min(this._inputEnd, ptr + outBuf.length - outPtr);
/* 2538 */       while (ptr < max) {
/* 2539 */         int c = inputBuffer[ptr++] & 0xFF;
/* 2540 */         if (codes[c] != 0 && c != 34) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2553 */           if (ptr >= safeEnd) {
/* 2554 */             this._inputPtr = ptr;
/* 2555 */             this._textBuffer.setCurrentLength(outPtr);
/* 2556 */             if (!_decodeSplitMultiByte(c, codes[c], (ptr < this._inputEnd))) {
/* 2557 */               this._minorStateAfterSplit = 45;
/* 2558 */               return this._currToken = JsonToken.NOT_AVAILABLE;
/*      */             } 
/* 2560 */             outBuf = this._textBuffer.getBufferWithoutReset();
/* 2561 */             outPtr = this._textBuffer.getCurrentSegmentSize();
/* 2562 */             ptr = this._inputPtr;
/*      */             
/*      */             continue;
/*      */           } 
/* 2566 */           switch (codes[c]) {
/*      */             case 1:
/* 2568 */               this._inputPtr = ptr;
/* 2569 */               c = _decodeFastCharEscape();
/* 2570 */               ptr = this._inputPtr;
/*      */               break;
/*      */             case 2:
/* 2573 */               c = _decodeUTF8_2(c, this._inputBuffer[ptr++]);
/*      */               break;
/*      */             case 3:
/* 2576 */               c = _decodeUTF8_3(c, this._inputBuffer[ptr++], this._inputBuffer[ptr++]);
/*      */               break;
/*      */             case 4:
/* 2579 */               c = _decodeUTF8_4(c, this._inputBuffer[ptr++], this._inputBuffer[ptr++], this._inputBuffer[ptr++]);
/*      */ 
/*      */               
/* 2582 */               outBuf[outPtr++] = (char)(0xD800 | c >> 10);
/* 2583 */               if (outPtr >= outBuf.length) {
/* 2584 */                 outBuf = this._textBuffer.finishCurrentSegment();
/* 2585 */                 outPtr = 0;
/*      */               } 
/* 2587 */               c = 0xDC00 | c & 0x3FF;
/*      */               break;
/*      */             
/*      */             default:
/* 2591 */               if (c < 32) {
/*      */                 
/* 2593 */                 _throwUnquotedSpace(c, "string value");
/*      */                 break;
/*      */               } 
/* 2596 */               _reportInvalidChar(c);
/*      */               break;
/*      */           } 
/*      */           
/* 2600 */           if (outPtr >= outBuf.length) {
/* 2601 */             outBuf = this._textBuffer.finishCurrentSegment();
/* 2602 */             outPtr = 0;
/*      */           } 
/*      */           
/* 2605 */           outBuf[outPtr++] = (char)c; continue;
/*      */         }  if (c == 39) {
/*      */           this._inputPtr = ptr; this._textBuffer.setCurrentLength(outPtr);
/*      */           return _valueComplete(JsonToken.VALUE_STRING);
/*      */         } 
/*      */         outBuf[outPtr++] = (char)c;
/*      */       } 
/* 2612 */     }  } private final boolean _decodeSplitMultiByte(int c, int type, boolean gotNext) throws IOException { switch (type) {
/*      */       case 1:
/* 2614 */         c = _decodeSplitEscaped(0, -1);
/* 2615 */         if (c < 0) {
/* 2616 */           this._minorState = 41;
/* 2617 */           return false;
/*      */         } 
/* 2619 */         this._textBuffer.append((char)c);
/* 2620 */         return true;
/*      */       case 2:
/* 2622 */         if (gotNext) {
/*      */           
/* 2624 */           c = _decodeUTF8_2(c, this._inputBuffer[this._inputPtr++]);
/* 2625 */           this._textBuffer.append((char)c);
/* 2626 */           return true;
/*      */         } 
/* 2628 */         this._minorState = 42;
/* 2629 */         this._pending32 = c;
/* 2630 */         return false;
/*      */       case 3:
/* 2632 */         c &= 0xF;
/* 2633 */         if (gotNext) {
/* 2634 */           return _decodeSplitUTF8_3(c, 1, this._inputBuffer[this._inputPtr++]);
/*      */         }
/* 2636 */         this._minorState = 43;
/* 2637 */         this._pending32 = c;
/* 2638 */         this._pendingBytes = 1;
/* 2639 */         return false;
/*      */       case 4:
/* 2641 */         c &= 0x7;
/* 2642 */         if (gotNext) {
/* 2643 */           return _decodeSplitUTF8_4(c, 1, this._inputBuffer[this._inputPtr++]);
/*      */         }
/* 2645 */         this._pending32 = c;
/* 2646 */         this._pendingBytes = 1;
/* 2647 */         this._minorState = 44;
/* 2648 */         return false;
/*      */     } 
/* 2650 */     if (c < 32) {
/*      */       
/* 2652 */       _throwUnquotedSpace(c, "string value");
/*      */     } else {
/*      */       
/* 2655 */       _reportInvalidChar(c);
/*      */     } 
/* 2657 */     this._textBuffer.append((char)c);
/* 2658 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean _decodeSplitUTF8_3(int prev, int prevCount, int next) throws IOException {
/* 2665 */     if (prevCount == 1) {
/* 2666 */       if ((next & 0xC0) != 128) {
/* 2667 */         _reportInvalidOther(next & 0xFF, this._inputPtr);
/*      */       }
/* 2669 */       prev = prev << 6 | next & 0x3F;
/* 2670 */       if (this._inputPtr >= this._inputEnd) {
/* 2671 */         this._minorState = 43;
/* 2672 */         this._pending32 = prev;
/* 2673 */         this._pendingBytes = 2;
/* 2674 */         return false;
/*      */       } 
/* 2676 */       next = this._inputBuffer[this._inputPtr++];
/*      */     } 
/* 2678 */     if ((next & 0xC0) != 128) {
/* 2679 */       _reportInvalidOther(next & 0xFF, this._inputPtr);
/*      */     }
/* 2681 */     this._textBuffer.append((char)(prev << 6 | next & 0x3F));
/* 2682 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean _decodeSplitUTF8_4(int prev, int prevCount, int next) throws IOException {
/* 2690 */     if (prevCount == 1) {
/* 2691 */       if ((next & 0xC0) != 128) {
/* 2692 */         _reportInvalidOther(next & 0xFF, this._inputPtr);
/*      */       }
/* 2694 */       prev = prev << 6 | next & 0x3F;
/* 2695 */       if (this._inputPtr >= this._inputEnd) {
/* 2696 */         this._minorState = 44;
/* 2697 */         this._pending32 = prev;
/* 2698 */         this._pendingBytes = 2;
/* 2699 */         return false;
/*      */       } 
/* 2701 */       prevCount = 2;
/* 2702 */       next = this._inputBuffer[this._inputPtr++];
/*      */     } 
/* 2704 */     if (prevCount == 2) {
/* 2705 */       if ((next & 0xC0) != 128) {
/* 2706 */         _reportInvalidOther(next & 0xFF, this._inputPtr);
/*      */       }
/* 2708 */       prev = prev << 6 | next & 0x3F;
/* 2709 */       if (this._inputPtr >= this._inputEnd) {
/* 2710 */         this._minorState = 44;
/* 2711 */         this._pending32 = prev;
/* 2712 */         this._pendingBytes = 3;
/* 2713 */         return false;
/*      */       } 
/* 2715 */       next = this._inputBuffer[this._inputPtr++];
/*      */     } 
/* 2717 */     if ((next & 0xC0) != 128) {
/* 2718 */       _reportInvalidOther(next & 0xFF, this._inputPtr);
/*      */     }
/* 2720 */     int c = (prev << 6 | next & 0x3F) - 65536;
/*      */     
/* 2722 */     this._textBuffer.append((char)(0xD800 | c >> 10));
/* 2723 */     c = 0xDC00 | c & 0x3FF;
/*      */     
/* 2725 */     this._textBuffer.append((char)c);
/* 2726 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _decodeCharEscape() throws IOException {
/* 2737 */     int left = this._inputEnd - this._inputPtr;
/* 2738 */     if (left < 5) {
/* 2739 */       return _decodeSplitEscaped(0, -1);
/*      */     }
/* 2741 */     return _decodeFastCharEscape();
/*      */   }
/*      */   
/*      */   private final int _decodeFastCharEscape() throws IOException {
/*      */     char c1;
/* 2746 */     int c = this._inputBuffer[this._inputPtr++];
/* 2747 */     switch (c) {
/*      */       
/*      */       case 98:
/* 2750 */         return 8;
/*      */       case 116:
/* 2752 */         return 9;
/*      */       case 110:
/* 2754 */         return 10;
/*      */       case 102:
/* 2756 */         return 12;
/*      */       case 114:
/* 2758 */         return 13;
/*      */ 
/*      */       
/*      */       case 34:
/*      */       case 47:
/*      */       case 92:
/* 2764 */         return (char)c;
/*      */ 
/*      */ 
/*      */       
/*      */       case 117:
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 2773 */         c1 = (char)c;
/* 2774 */         return _handleUnrecognizedCharacterEscape(c1);
/*      */     } 
/*      */ 
/*      */     
/* 2778 */     int ch = this._inputBuffer[this._inputPtr++];
/* 2779 */     int digit = CharTypes.charToHex(ch);
/* 2780 */     int result = digit;
/*      */     
/* 2782 */     if (digit >= 0) {
/* 2783 */       ch = this._inputBuffer[this._inputPtr++];
/* 2784 */       digit = CharTypes.charToHex(ch);
/* 2785 */       if (digit >= 0) {
/* 2786 */         result = result << 4 | digit;
/* 2787 */         ch = this._inputBuffer[this._inputPtr++];
/* 2788 */         digit = CharTypes.charToHex(ch);
/* 2789 */         if (digit >= 0) {
/* 2790 */           result = result << 4 | digit;
/* 2791 */           ch = this._inputBuffer[this._inputPtr++];
/* 2792 */           digit = CharTypes.charToHex(ch);
/* 2793 */           if (digit >= 0) {
/* 2794 */             return result << 4 | digit;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 2799 */     _reportUnexpectedChar(ch & 0xFF, "expected a hex-digit for character escape sequence");
/* 2800 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _decodeUTF8_2(int c, int d) throws IOException {
/* 2811 */     if ((d & 0xC0) != 128) {
/* 2812 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 2814 */     return (c & 0x1F) << 6 | d & 0x3F;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int _decodeUTF8_3(int c, int d, int e) throws IOException {
/* 2819 */     c &= 0xF;
/* 2820 */     if ((d & 0xC0) != 128) {
/* 2821 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 2823 */     c = c << 6 | d & 0x3F;
/* 2824 */     if ((e & 0xC0) != 128) {
/* 2825 */       _reportInvalidOther(e & 0xFF, this._inputPtr);
/*      */     }
/* 2827 */     return c << 6 | e & 0x3F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _decodeUTF8_4(int c, int d, int e, int f) throws IOException {
/* 2834 */     if ((d & 0xC0) != 128) {
/* 2835 */       _reportInvalidOther(d & 0xFF, this._inputPtr);
/*      */     }
/* 2837 */     c = (c & 0x7) << 6 | d & 0x3F;
/* 2838 */     if ((e & 0xC0) != 128) {
/* 2839 */       _reportInvalidOther(e & 0xFF, this._inputPtr);
/*      */     }
/* 2841 */     c = c << 6 | e & 0x3F;
/* 2842 */     if ((f & 0xC0) != 128) {
/* 2843 */       _reportInvalidOther(f & 0xFF, this._inputPtr);
/*      */     }
/* 2845 */     return (c << 6 | f & 0x3F) - 65536;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\async\NonBlockingJsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */