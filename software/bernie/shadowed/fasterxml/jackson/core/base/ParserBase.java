/*      */ package software.bernie.shadowed.fasterxml.jackson.core.base;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Arrays;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.DupDetector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.JsonReadContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.PackageVersion;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.TextBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ParserBase
/*      */   extends ParserMinimalBase
/*      */ {
/*      */   protected final IOContext _ioContext;
/*      */   protected boolean _closed;
/*      */   protected int _inputPtr;
/*      */   protected int _inputEnd;
/*      */   protected long _currInputProcessed;
/*   77 */   protected int _currInputRow = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _currInputRowStart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _tokenInputTotal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   protected int _tokenInputRow = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _tokenInputCol;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonReadContext _parsingContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonToken _nextToken;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TextBuffer _textBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _nameCopyBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _nameCopied;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ByteArrayBuilder _byteArrayBuilder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] _binaryValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   protected int _numTypesValid = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _numberInt;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _numberLong;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double _numberDouble;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigInteger _numberBigInt;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigDecimal _numberBigDecimal;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _numberNegative;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _intLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _fractLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _expLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ParserBase(IOContext ctxt, int features) {
/*  232 */     super(features);
/*  233 */     this._ioContext = ctxt;
/*  234 */     this._textBuffer = ctxt.constructTextBuffer();
/*  235 */     DupDetector dups = JsonParser.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(features) ? DupDetector.rootDetector(this) : null;
/*      */     
/*  237 */     this._parsingContext = JsonReadContext.createRootContext(dups);
/*      */   }
/*      */   public Version version() {
/*  240 */     return PackageVersion.VERSION;
/*      */   }
/*      */   
/*      */   public Object getCurrentValue() {
/*  244 */     return this._parsingContext.getCurrentValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCurrentValue(Object v) {
/*  249 */     this._parsingContext.setCurrentValue(v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser enable(JsonParser.Feature f) {
/*  260 */     this._features |= f.getMask();
/*  261 */     if (f == JsonParser.Feature.STRICT_DUPLICATE_DETECTION && 
/*  262 */       this._parsingContext.getDupDetector() == null) {
/*  263 */       this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector(this));
/*      */     }
/*      */     
/*  266 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonParser disable(JsonParser.Feature f) {
/*  271 */     this._features &= f.getMask() ^ 0xFFFFFFFF;
/*  272 */     if (f == JsonParser.Feature.STRICT_DUPLICATE_DETECTION) {
/*  273 */       this._parsingContext = this._parsingContext.withDupDetector(null);
/*      */     }
/*  275 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonParser setFeatureMask(int newMask) {
/*  281 */     int changes = this._features ^ newMask;
/*  282 */     if (changes != 0) {
/*  283 */       this._features = newMask;
/*  284 */       _checkStdFeatureChanges(newMask, changes);
/*      */     } 
/*  286 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonParser overrideStdFeatures(int values, int mask) {
/*  291 */     int oldState = this._features;
/*  292 */     int newState = oldState & (mask ^ 0xFFFFFFFF) | values & mask;
/*  293 */     int changed = oldState ^ newState;
/*  294 */     if (changed != 0) {
/*  295 */       this._features = newState;
/*  296 */       _checkStdFeatureChanges(newState, changed);
/*      */     } 
/*  298 */     return this;
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
/*      */   protected void _checkStdFeatureChanges(int newFeatureFlags, int changedFeatures) {
/*  312 */     int f = JsonParser.Feature.STRICT_DUPLICATE_DETECTION.getMask();
/*      */     
/*  314 */     if ((changedFeatures & f) != 0 && (
/*  315 */       newFeatureFlags & f) != 0) {
/*  316 */       if (this._parsingContext.getDupDetector() == null) {
/*  317 */         this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector(this));
/*      */       } else {
/*  319 */         this._parsingContext = this._parsingContext.withDupDetector(null);
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
/*      */ 
/*      */   
/*      */   public String getCurrentName() throws IOException {
/*  337 */     if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/*  338 */       JsonReadContext parent = this._parsingContext.getParent();
/*  339 */       if (parent != null) {
/*  340 */         return parent.getCurrentName();
/*      */       }
/*      */     } 
/*  343 */     return this._parsingContext.getCurrentName();
/*      */   }
/*      */ 
/*      */   
/*      */   public void overrideCurrentName(String name) {
/*  348 */     JsonReadContext ctxt = this._parsingContext;
/*  349 */     if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/*  350 */       ctxt = ctxt.getParent();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  356 */       ctxt.setCurrentName(name);
/*  357 */     } catch (IOException e) {
/*  358 */       throw new IllegalStateException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void close() throws IOException {
/*  363 */     if (!this._closed) {
/*  364 */       this._closed = true;
/*      */       try {
/*  366 */         _closeInput();
/*      */       }
/*      */       finally {
/*      */         
/*  370 */         _releaseBuffers();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*  375 */   public boolean isClosed() { return this._closed; } public JsonReadContext getParsingContext() {
/*  376 */     return this._parsingContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonLocation getTokenLocation() {
/*  385 */     return new JsonLocation(_getSourceReference(), -1L, getTokenCharacterOffset(), getTokenLineNr(), getTokenColumnNr());
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
/*      */   public JsonLocation getCurrentLocation() {
/*  397 */     int col = this._inputPtr - this._currInputRowStart + 1;
/*  398 */     return new JsonLocation(_getSourceReference(), -1L, this._currInputProcessed + this._inputPtr, this._currInputRow, col);
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
/*      */   public boolean hasTextCharacters() {
/*  411 */     if (this._currToken == JsonToken.VALUE_STRING) return true; 
/*  412 */     if (this._currToken == JsonToken.FIELD_NAME) return this._nameCopied; 
/*  413 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBinaryValue(Base64Variant variant) throws IOException {
/*  420 */     if (this._binaryValue == null) {
/*  421 */       if (this._currToken != JsonToken.VALUE_STRING) {
/*  422 */         _reportError("Current token (" + this._currToken + ") not VALUE_STRING, can not access as binary");
/*      */       }
/*  424 */       ByteArrayBuilder builder = _getByteArrayBuilder();
/*  425 */       _decodeBase64(getText(), builder, variant);
/*  426 */       this._binaryValue = builder.toByteArray();
/*      */     } 
/*  428 */     return this._binaryValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTokenCharacterOffset() {
/*  437 */     return this._tokenInputTotal; } public int getTokenLineNr() {
/*  438 */     return this._tokenInputRow;
/*      */   }
/*      */   public int getTokenColumnNr() {
/*  441 */     int col = this._tokenInputCol;
/*  442 */     return (col < 0) ? col : (col + 1);
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
/*      */   protected void _releaseBuffers() throws IOException {
/*  466 */     this._textBuffer.releaseBuffers();
/*  467 */     char[] buf = this._nameCopyBuffer;
/*  468 */     if (buf != null) {
/*  469 */       this._nameCopyBuffer = null;
/*  470 */       this._ioContext.releaseNameCopyBuffer(buf);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _handleEOF() throws JsonParseException {
/*  481 */     if (!this._parsingContext.inRoot()) {
/*  482 */       String marker = this._parsingContext.inArray() ? "Array" : "Object";
/*  483 */       _reportInvalidEOF(String.format(": expected close marker for %s (start marker at %s)", new Object[] { marker, this._parsingContext.getStartLocation(_getSourceReference()) }), (JsonToken)null);
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
/*      */   protected final int _eofAsNextChar() throws JsonParseException {
/*  495 */     _handleEOF();
/*  496 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteArrayBuilder _getByteArrayBuilder() {
/*  507 */     if (this._byteArrayBuilder == null) {
/*  508 */       this._byteArrayBuilder = new ByteArrayBuilder();
/*      */     } else {
/*  510 */       this._byteArrayBuilder.reset();
/*      */     } 
/*  512 */     return this._byteArrayBuilder;
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
/*      */   protected final JsonToken reset(boolean negative, int intLen, int fractLen, int expLen) {
/*  525 */     if (fractLen < 1 && expLen < 1) {
/*  526 */       return resetInt(negative, intLen);
/*      */     }
/*  528 */     return resetFloat(negative, intLen, fractLen, expLen);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final JsonToken resetInt(boolean negative, int intLen) {
/*  533 */     this._numberNegative = negative;
/*  534 */     this._intLength = intLen;
/*  535 */     this._fractLength = 0;
/*  536 */     this._expLength = 0;
/*  537 */     this._numTypesValid = 0;
/*  538 */     return JsonToken.VALUE_NUMBER_INT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final JsonToken resetFloat(boolean negative, int intLen, int fractLen, int expLen) {
/*  543 */     this._numberNegative = negative;
/*  544 */     this._intLength = intLen;
/*  545 */     this._fractLength = fractLen;
/*  546 */     this._expLength = expLen;
/*  547 */     this._numTypesValid = 0;
/*  548 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final JsonToken resetAsNaN(String valueStr, double value) {
/*  553 */     this._textBuffer.resetWithString(valueStr);
/*  554 */     this._numberDouble = value;
/*  555 */     this._numTypesValid = 8;
/*  556 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNaN() {
/*  561 */     if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT && (
/*  562 */       this._numTypesValid & 0x8) != 0) {
/*      */       
/*  564 */       double d = this._numberDouble;
/*  565 */       return (Double.isNaN(d) || Double.isInfinite(d));
/*      */     } 
/*      */     
/*  568 */     return false;
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
/*      */   public Number getNumberValue() throws IOException {
/*  580 */     if (this._numTypesValid == 0) {
/*  581 */       _parseNumericValue(0);
/*      */     }
/*      */     
/*  584 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/*  585 */       if ((this._numTypesValid & 0x1) != 0) {
/*  586 */         return Integer.valueOf(this._numberInt);
/*      */       }
/*  588 */       if ((this._numTypesValid & 0x2) != 0) {
/*  589 */         return Long.valueOf(this._numberLong);
/*      */       }
/*  591 */       if ((this._numTypesValid & 0x4) != 0) {
/*  592 */         return this._numberBigInt;
/*      */       }
/*      */       
/*  595 */       return this._numberBigDecimal;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  601 */     if ((this._numTypesValid & 0x10) != 0) {
/*  602 */       return this._numberBigDecimal;
/*      */     }
/*  604 */     if ((this._numTypesValid & 0x8) == 0) {
/*  605 */       _throwInternal();
/*      */     }
/*  607 */     return Double.valueOf(this._numberDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser.NumberType getNumberType() throws IOException {
/*  613 */     if (this._numTypesValid == 0) {
/*  614 */       _parseNumericValue(0);
/*      */     }
/*  616 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/*  617 */       if ((this._numTypesValid & 0x1) != 0) {
/*  618 */         return JsonParser.NumberType.INT;
/*      */       }
/*  620 */       if ((this._numTypesValid & 0x2) != 0) {
/*  621 */         return JsonParser.NumberType.LONG;
/*      */       }
/*  623 */       return JsonParser.NumberType.BIG_INTEGER;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     if ((this._numTypesValid & 0x10) != 0) {
/*  633 */       return JsonParser.NumberType.BIG_DECIMAL;
/*      */     }
/*  635 */     return JsonParser.NumberType.DOUBLE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIntValue() throws IOException {
/*  641 */     if ((this._numTypesValid & 0x1) == 0) {
/*  642 */       if (this._numTypesValid == 0) {
/*  643 */         return _parseIntValue();
/*      */       }
/*  645 */       if ((this._numTypesValid & 0x1) == 0) {
/*  646 */         convertNumberToInt();
/*      */       }
/*      */     } 
/*  649 */     return this._numberInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLongValue() throws IOException {
/*  655 */     if ((this._numTypesValid & 0x2) == 0) {
/*  656 */       if (this._numTypesValid == 0) {
/*  657 */         _parseNumericValue(2);
/*      */       }
/*  659 */       if ((this._numTypesValid & 0x2) == 0) {
/*  660 */         convertNumberToLong();
/*      */       }
/*      */     } 
/*  663 */     return this._numberLong;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getBigIntegerValue() throws IOException {
/*  669 */     if ((this._numTypesValid & 0x4) == 0) {
/*  670 */       if (this._numTypesValid == 0) {
/*  671 */         _parseNumericValue(4);
/*      */       }
/*  673 */       if ((this._numTypesValid & 0x4) == 0) {
/*  674 */         convertNumberToBigInteger();
/*      */       }
/*      */     } 
/*  677 */     return this._numberBigInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloatValue() throws IOException {
/*  683 */     double value = getDoubleValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  692 */     return (float)value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDoubleValue() throws IOException {
/*  698 */     if ((this._numTypesValid & 0x8) == 0) {
/*  699 */       if (this._numTypesValid == 0) {
/*  700 */         _parseNumericValue(8);
/*      */       }
/*  702 */       if ((this._numTypesValid & 0x8) == 0) {
/*  703 */         convertNumberToDouble();
/*      */       }
/*      */     } 
/*  706 */     return this._numberDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getDecimalValue() throws IOException {
/*  712 */     if ((this._numTypesValid & 0x10) == 0) {
/*  713 */       if (this._numTypesValid == 0) {
/*  714 */         _parseNumericValue(16);
/*      */       }
/*  716 */       if ((this._numTypesValid & 0x10) == 0) {
/*  717 */         convertNumberToBigDecimal();
/*      */       }
/*      */     } 
/*  720 */     return this._numberBigDecimal;
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
/*      */   protected void _parseNumericValue(int expType) throws IOException {
/*  741 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/*  742 */       int len = this._intLength;
/*      */       
/*  744 */       if (len <= 9) {
/*  745 */         int i = this._textBuffer.contentsAsInt(this._numberNegative);
/*  746 */         this._numberInt = i;
/*  747 */         this._numTypesValid = 1;
/*      */         return;
/*      */       } 
/*  750 */       if (len <= 18) {
/*  751 */         long l = this._textBuffer.contentsAsLong(this._numberNegative);
/*      */         
/*  753 */         if (len == 10) {
/*  754 */           if (this._numberNegative) {
/*  755 */             if (l >= -2147483648L) {
/*  756 */               this._numberInt = (int)l;
/*  757 */               this._numTypesValid = 1;
/*      */               
/*      */               return;
/*      */             } 
/*  761 */           } else if (l <= 2147483647L) {
/*  762 */             this._numberInt = (int)l;
/*  763 */             this._numTypesValid = 1;
/*      */             
/*      */             return;
/*      */           } 
/*      */         }
/*  768 */         this._numberLong = l;
/*  769 */         this._numTypesValid = 2;
/*      */         return;
/*      */       } 
/*  772 */       _parseSlowInt(expType);
/*      */       return;
/*      */     } 
/*  775 */     if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
/*  776 */       _parseSlowFloat(expType);
/*      */       return;
/*      */     } 
/*  779 */     _reportError("Current token (%s) not numeric, can not use numeric value accessors", this._currToken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _parseIntValue() throws IOException {
/*  788 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT && 
/*  789 */       this._intLength <= 9) {
/*  790 */       int i = this._textBuffer.contentsAsInt(this._numberNegative);
/*  791 */       this._numberInt = i;
/*  792 */       this._numTypesValid = 1;
/*  793 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  797 */     _parseNumericValue(1);
/*  798 */     if ((this._numTypesValid & 0x1) == 0) {
/*  799 */       convertNumberToInt();
/*      */     }
/*  801 */     return this._numberInt;
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
/*      */   private void _parseSlowFloat(int expType) throws IOException {
/*      */     try {
/*  814 */       if (expType == 16) {
/*  815 */         this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
/*  816 */         this._numTypesValid = 16;
/*      */       } else {
/*      */         
/*  819 */         this._numberDouble = this._textBuffer.contentsAsDouble();
/*  820 */         this._numTypesValid = 8;
/*      */       } 
/*  822 */     } catch (NumberFormatException nex) {
/*      */       
/*  824 */       _wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", nex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void _parseSlowInt(int expType) throws IOException {
/*  830 */     String numStr = this._textBuffer.contentsAsString();
/*      */     try {
/*  832 */       int len = this._intLength;
/*  833 */       char[] buf = this._textBuffer.getTextBuffer();
/*  834 */       int offset = this._textBuffer.getTextOffset();
/*  835 */       if (this._numberNegative) {
/*  836 */         offset++;
/*      */       }
/*      */       
/*  839 */       if (NumberInput.inLongRange(buf, offset, len, this._numberNegative)) {
/*      */         
/*  841 */         this._numberLong = Long.parseLong(numStr);
/*  842 */         this._numTypesValid = 2;
/*      */       } else {
/*      */         
/*  845 */         this._numberBigInt = new BigInteger(numStr);
/*  846 */         this._numTypesValid = 4;
/*      */       } 
/*  848 */     } catch (NumberFormatException nex) {
/*      */       
/*  850 */       _wrapError("Malformed numeric value '" + numStr + "'", nex);
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
/*      */   protected void convertNumberToInt() throws IOException {
/*  863 */     if ((this._numTypesValid & 0x2) != 0) {
/*      */       
/*  865 */       int result = (int)this._numberLong;
/*  866 */       if (result != this._numberLong) {
/*  867 */         _reportError("Numeric value (" + getText() + ") out of range of int");
/*      */       }
/*  869 */       this._numberInt = result;
/*  870 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  871 */       if (BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0)
/*      */       {
/*  873 */         reportOverflowInt();
/*      */       }
/*  875 */       this._numberInt = this._numberBigInt.intValue();
/*  876 */     } else if ((this._numTypesValid & 0x8) != 0) {
/*      */       
/*  878 */       if (this._numberDouble < -2.147483648E9D || this._numberDouble > 2.147483647E9D) {
/*  879 */         reportOverflowInt();
/*      */       }
/*  881 */       this._numberInt = (int)this._numberDouble;
/*  882 */     } else if ((this._numTypesValid & 0x10) != 0) {
/*  883 */       if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0)
/*      */       {
/*  885 */         reportOverflowInt();
/*      */       }
/*  887 */       this._numberInt = this._numberBigDecimal.intValue();
/*      */     } else {
/*  889 */       _throwInternal();
/*      */     } 
/*  891 */     this._numTypesValid |= 0x1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void convertNumberToLong() throws IOException {
/*  896 */     if ((this._numTypesValid & 0x1) != 0) {
/*  897 */       this._numberLong = this._numberInt;
/*  898 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  899 */       if (BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0)
/*      */       {
/*  901 */         reportOverflowLong();
/*      */       }
/*  903 */       this._numberLong = this._numberBigInt.longValue();
/*  904 */     } else if ((this._numTypesValid & 0x8) != 0) {
/*      */       
/*  906 */       if (this._numberDouble < -9.223372036854776E18D || this._numberDouble > 9.223372036854776E18D) {
/*  907 */         reportOverflowLong();
/*      */       }
/*  909 */       this._numberLong = (long)this._numberDouble;
/*  910 */     } else if ((this._numTypesValid & 0x10) != 0) {
/*  911 */       if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0)
/*      */       {
/*  913 */         reportOverflowLong();
/*      */       }
/*  915 */       this._numberLong = this._numberBigDecimal.longValue();
/*      */     } else {
/*  917 */       _throwInternal();
/*      */     } 
/*  919 */     this._numTypesValid |= 0x2;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void convertNumberToBigInteger() throws IOException {
/*  924 */     if ((this._numTypesValid & 0x10) != 0) {
/*      */       
/*  926 */       this._numberBigInt = this._numberBigDecimal.toBigInteger();
/*  927 */     } else if ((this._numTypesValid & 0x2) != 0) {
/*  928 */       this._numberBigInt = BigInteger.valueOf(this._numberLong);
/*  929 */     } else if ((this._numTypesValid & 0x1) != 0) {
/*  930 */       this._numberBigInt = BigInteger.valueOf(this._numberInt);
/*  931 */     } else if ((this._numTypesValid & 0x8) != 0) {
/*  932 */       this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
/*      */     } else {
/*  934 */       _throwInternal();
/*      */     } 
/*  936 */     this._numTypesValid |= 0x4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertNumberToDouble() throws IOException {
/*  947 */     if ((this._numTypesValid & 0x10) != 0) {
/*  948 */       this._numberDouble = this._numberBigDecimal.doubleValue();
/*  949 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  950 */       this._numberDouble = this._numberBigInt.doubleValue();
/*  951 */     } else if ((this._numTypesValid & 0x2) != 0) {
/*  952 */       this._numberDouble = this._numberLong;
/*  953 */     } else if ((this._numTypesValid & 0x1) != 0) {
/*  954 */       this._numberDouble = this._numberInt;
/*      */     } else {
/*  956 */       _throwInternal();
/*      */     } 
/*  958 */     this._numTypesValid |= 0x8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertNumberToBigDecimal() throws IOException {
/*  969 */     if ((this._numTypesValid & 0x8) != 0) {
/*      */ 
/*      */ 
/*      */       
/*  973 */       this._numberBigDecimal = NumberInput.parseBigDecimal(getText());
/*  974 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  975 */       this._numberBigDecimal = new BigDecimal(this._numberBigInt);
/*  976 */     } else if ((this._numTypesValid & 0x2) != 0) {
/*  977 */       this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
/*  978 */     } else if ((this._numTypesValid & 0x1) != 0) {
/*  979 */       this._numberBigDecimal = BigDecimal.valueOf(this._numberInt);
/*      */     } else {
/*  981 */       _throwInternal();
/*      */     } 
/*  983 */     this._numTypesValid |= 0x10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportMismatchedEndMarker(int actCh, char expCh) throws JsonParseException {
/*  993 */     JsonReadContext ctxt = getParsingContext();
/*  994 */     _reportError(String.format("Unexpected close marker '%s': expected '%c' (for %s starting at %s)", new Object[] { Character.valueOf((char)actCh), Character.valueOf(expCh), ctxt.typeDesc(), ctxt.getStartLocation(_getSourceReference()) }));
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
/*      */   protected char _decodeEscaped() throws IOException {
/* 1011 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _decodeBase64Escape(Base64Variant b64variant, int ch, int index) throws IOException {
/* 1017 */     if (ch != 92) {
/* 1018 */       throw reportInvalidBase64Char(b64variant, ch, index);
/*      */     }
/* 1020 */     int unescaped = _decodeEscaped();
/*      */     
/* 1022 */     if (unescaped <= 32 && 
/* 1023 */       index == 0) {
/* 1024 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1028 */     int bits = b64variant.decodeBase64Char(unescaped);
/* 1029 */     if (bits < 0) {
/* 1030 */       throw reportInvalidBase64Char(b64variant, unescaped, index);
/*      */     }
/* 1032 */     return bits;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final int _decodeBase64Escape(Base64Variant b64variant, char ch, int index) throws IOException {
/* 1037 */     if (ch != '\\') {
/* 1038 */       throw reportInvalidBase64Char(b64variant, ch, index);
/*      */     }
/* 1040 */     char unescaped = _decodeEscaped();
/*      */     
/* 1042 */     if (unescaped <= ' ' && 
/* 1043 */       index == 0) {
/* 1044 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1048 */     int bits = b64variant.decodeBase64Char(unescaped);
/* 1049 */     if (bits < 0) {
/* 1050 */       throw reportInvalidBase64Char(b64variant, unescaped, index);
/*      */     }
/* 1052 */     return bits;
/*      */   }
/*      */   
/*      */   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant b64variant, int ch, int bindex) throws IllegalArgumentException {
/* 1056 */     return reportInvalidBase64Char(b64variant, ch, bindex, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant b64variant, int ch, int bindex, String msg) throws IllegalArgumentException {
/*      */     String base;
/* 1065 */     if (ch <= 32) {
/* 1066 */       base = String.format("Illegal white space character (code 0x%s) as character #%d of 4-char base64 unit: can only used between units", new Object[] { Integer.toHexString(ch), Integer.valueOf(bindex + 1) });
/*      */     }
/* 1068 */     else if (b64variant.usesPaddingChar(ch)) {
/* 1069 */       base = "Unexpected padding character ('" + b64variant.getPaddingChar() + "') as character #" + (bindex + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
/* 1070 */     } else if (!Character.isDefined(ch) || Character.isISOControl(ch)) {
/*      */       
/* 1072 */       base = "Illegal character (code 0x" + Integer.toHexString(ch) + ") in base64 content";
/*      */     } else {
/* 1074 */       base = "Illegal character '" + (char)ch + "' (code 0x" + Integer.toHexString(ch) + ") in base64 content";
/*      */     } 
/* 1076 */     if (msg != null) {
/* 1077 */       base = base + ": " + msg;
/*      */     }
/* 1079 */     return new IllegalArgumentException(base);
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
/*      */   protected Object _getSourceReference() {
/* 1095 */     if (JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION.enabledIn(this._features)) {
/* 1096 */       return this._ioContext.getSourceReference();
/*      */     }
/* 1098 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static int[] growArrayBy(int[] arr, int more) {
/* 1103 */     if (arr == null) {
/* 1104 */       return new int[more];
/*      */     }
/* 1106 */     return Arrays.copyOf(arr, arr.length + more);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void loadMoreGuaranteed() throws IOException {
/* 1118 */     if (!loadMore()) _reportInvalidEOF(); 
/*      */   }
/*      */   @Deprecated
/*      */   protected boolean loadMore() throws IOException {
/* 1122 */     return false;
/*      */   }
/*      */   
/*      */   protected void _finishString() throws IOException {}
/*      */   
/*      */   protected abstract void _closeInput() throws IOException;
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\base\ParserBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */