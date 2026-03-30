/*      */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberOutput;
/*      */ 
/*      */ public class UTF8JsonGenerator
/*      */   extends JsonGeneratorImpl {
/*      */   private static final byte BYTE_u = 117;
/*      */   private static final byte BYTE_0 = 48;
/*      */   private static final byte BYTE_LBRACKET = 91;
/*      */   private static final byte BYTE_RBRACKET = 93;
/*      */   private static final byte BYTE_LCURLY = 123;
/*      */   private static final byte BYTE_RCURLY = 125;
/*      */   private static final byte BYTE_BACKSLASH = 92;
/*      */   private static final byte BYTE_COMMA = 44;
/*      */   private static final byte BYTE_COLON = 58;
/*      */   private static final int MAX_BYTES_TO_BUFFER = 512;
/*   32 */   private static final byte[] HEX_CHARS = CharTypes.copyHexBytes();
/*      */   
/*   34 */   private static final byte[] NULL_BYTES = new byte[] { 110, 117, 108, 108 };
/*   35 */   private static final byte[] TRUE_BYTES = new byte[] { 116, 114, 117, 101 };
/*   36 */   private static final byte[] FALSE_BYTES = new byte[] { 102, 97, 108, 115, 101 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final OutputStream _outputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   protected byte _quoteChar = 34;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] _outputBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _outputTail;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _outputEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _outputMaxContiguous;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _charBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _charBufferLength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] _entityBuffer;
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
/*      */   public UTF8JsonGenerator(IOContext ctxt, int features, ObjectCodec codec, OutputStream out) {
/*  119 */     super(ctxt, features, codec);
/*  120 */     this._outputStream = out;
/*  121 */     this._bufferRecyclable = true;
/*  122 */     this._outputBuffer = ctxt.allocWriteEncodingBuffer();
/*  123 */     this._outputEnd = this._outputBuffer.length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  129 */     this._outputMaxContiguous = this._outputEnd >> 3;
/*  130 */     this._charBuffer = ctxt.allocConcatBuffer();
/*  131 */     this._charBufferLength = this._charBuffer.length;
/*      */ 
/*      */     
/*  134 */     if (isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
/*  135 */       setHighestNonEscapedChar(127);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UTF8JsonGenerator(IOContext ctxt, int features, ObjectCodec codec, OutputStream out, byte[] outputBuffer, int outputOffset, boolean bufferRecyclable) {
/*  144 */     super(ctxt, features, codec);
/*  145 */     this._outputStream = out;
/*  146 */     this._bufferRecyclable = bufferRecyclable;
/*  147 */     this._outputTail = outputOffset;
/*  148 */     this._outputBuffer = outputBuffer;
/*  149 */     this._outputEnd = this._outputBuffer.length;
/*      */     
/*  151 */     this._outputMaxContiguous = this._outputEnd >> 3;
/*  152 */     this._charBuffer = ctxt.allocConcatBuffer();
/*  153 */     this._charBufferLength = this._charBuffer.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getOutputTarget() {
/*  164 */     return this._outputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOutputBuffered() {
/*  170 */     return this._outputTail;
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
/*      */   public void writeFieldName(String name) throws IOException {
/*  182 */     if (this._cfgPrettyPrinter != null) {
/*  183 */       _writePPFieldName(name);
/*      */       return;
/*      */     } 
/*  186 */     int status = this._writeContext.writeFieldName(name);
/*  187 */     if (status == 4) {
/*  188 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  190 */     if (status == 1) {
/*  191 */       if (this._outputTail >= this._outputEnd) {
/*  192 */         _flushBuffer();
/*      */       }
/*  194 */       this._outputBuffer[this._outputTail++] = 44;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  199 */     if (this._cfgUnqNames) {
/*  200 */       _writeStringSegments(name, false);
/*      */       return;
/*      */     } 
/*  203 */     int len = name.length();
/*      */     
/*  205 */     if (len > this._charBufferLength) {
/*  206 */       _writeStringSegments(name, true);
/*      */       return;
/*      */     } 
/*  209 */     if (this._outputTail >= this._outputEnd) {
/*  210 */       _flushBuffer();
/*      */     }
/*  212 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  214 */     if (len <= this._outputMaxContiguous) {
/*  215 */       if (this._outputTail + len > this._outputEnd) {
/*  216 */         _flushBuffer();
/*      */       }
/*  218 */       _writeStringSegment(name, 0, len);
/*      */     } else {
/*  220 */       _writeStringSegments(name, 0, len);
/*      */     } 
/*      */     
/*  223 */     if (this._outputTail >= this._outputEnd) {
/*  224 */       _flushBuffer();
/*      */     }
/*  226 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFieldName(SerializableString name) throws IOException {
/*  232 */     if (this._cfgPrettyPrinter != null) {
/*  233 */       _writePPFieldName(name);
/*      */       return;
/*      */     } 
/*  236 */     int status = this._writeContext.writeFieldName(name.getValue());
/*  237 */     if (status == 4) {
/*  238 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  240 */     if (status == 1) {
/*  241 */       if (this._outputTail >= this._outputEnd) {
/*  242 */         _flushBuffer();
/*      */       }
/*  244 */       this._outputBuffer[this._outputTail++] = 44;
/*      */     } 
/*  246 */     if (this._cfgUnqNames) {
/*  247 */       _writeUnq(name);
/*      */       return;
/*      */     } 
/*  250 */     if (this._outputTail >= this._outputEnd) {
/*  251 */       _flushBuffer();
/*      */     }
/*  253 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  254 */     int len = name.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/*  255 */     if (len < 0) {
/*  256 */       _writeBytes(name.asQuotedUTF8());
/*      */     } else {
/*  258 */       this._outputTail += len;
/*      */     } 
/*  260 */     if (this._outputTail >= this._outputEnd) {
/*  261 */       _flushBuffer();
/*      */     }
/*  263 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */   
/*      */   private final void _writeUnq(SerializableString name) throws IOException {
/*  267 */     int len = name.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/*  268 */     if (len < 0) {
/*  269 */       _writeBytes(name.asQuotedUTF8());
/*      */     } else {
/*  271 */       this._outputTail += len;
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
/*      */   public final void writeStartArray() throws IOException {
/*  284 */     _verifyValueWrite("start an array");
/*  285 */     this._writeContext = this._writeContext.createChildArrayContext();
/*  286 */     if (this._cfgPrettyPrinter != null) {
/*  287 */       this._cfgPrettyPrinter.writeStartArray((JsonGenerator)this);
/*      */     } else {
/*  289 */       if (this._outputTail >= this._outputEnd) {
/*  290 */         _flushBuffer();
/*      */       }
/*  292 */       this._outputBuffer[this._outputTail++] = 91;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeEndArray() throws IOException {
/*  299 */     if (!this._writeContext.inArray()) {
/*  300 */       _reportError("Current context not Array but " + this._writeContext.typeDesc());
/*      */     }
/*  302 */     if (this._cfgPrettyPrinter != null) {
/*  303 */       this._cfgPrettyPrinter.writeEndArray((JsonGenerator)this, this._writeContext.getEntryCount());
/*      */     } else {
/*  305 */       if (this._outputTail >= this._outputEnd) {
/*  306 */         _flushBuffer();
/*      */       }
/*  308 */       this._outputBuffer[this._outputTail++] = 93;
/*      */     } 
/*  310 */     this._writeContext = this._writeContext.clearAndGetParent();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeStartObject() throws IOException {
/*  316 */     _verifyValueWrite("start an object");
/*  317 */     this._writeContext = this._writeContext.createChildObjectContext();
/*  318 */     if (this._cfgPrettyPrinter != null) {
/*  319 */       this._cfgPrettyPrinter.writeStartObject((JsonGenerator)this);
/*      */     } else {
/*  321 */       if (this._outputTail >= this._outputEnd) {
/*  322 */         _flushBuffer();
/*      */       }
/*  324 */       this._outputBuffer[this._outputTail++] = 123;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartObject(Object forValue) throws IOException {
/*  331 */     _verifyValueWrite("start an object");
/*  332 */     JsonWriteContext ctxt = this._writeContext.createChildObjectContext();
/*  333 */     this._writeContext = ctxt;
/*  334 */     if (forValue != null) {
/*  335 */       ctxt.setCurrentValue(forValue);
/*      */     }
/*  337 */     if (this._cfgPrettyPrinter != null) {
/*  338 */       this._cfgPrettyPrinter.writeStartObject((JsonGenerator)this);
/*      */     } else {
/*  340 */       if (this._outputTail >= this._outputEnd) {
/*  341 */         _flushBuffer();
/*      */       }
/*  343 */       this._outputBuffer[this._outputTail++] = 123;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeEndObject() throws IOException {
/*  350 */     if (!this._writeContext.inObject()) {
/*  351 */       _reportError("Current context not Object but " + this._writeContext.typeDesc());
/*      */     }
/*  353 */     if (this._cfgPrettyPrinter != null) {
/*  354 */       this._cfgPrettyPrinter.writeEndObject((JsonGenerator)this, this._writeContext.getEntryCount());
/*      */     } else {
/*  356 */       if (this._outputTail >= this._outputEnd) {
/*  357 */         _flushBuffer();
/*      */       }
/*  359 */       this._outputBuffer[this._outputTail++] = 125;
/*      */     } 
/*  361 */     this._writeContext = this._writeContext.clearAndGetParent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _writePPFieldName(String name) throws IOException {
/*  370 */     int status = this._writeContext.writeFieldName(name);
/*  371 */     if (status == 4) {
/*  372 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  374 */     if (status == 1) {
/*  375 */       this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
/*      */     } else {
/*  377 */       this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
/*      */     } 
/*  379 */     if (this._cfgUnqNames) {
/*  380 */       _writeStringSegments(name, false);
/*      */       return;
/*      */     } 
/*  383 */     int len = name.length();
/*  384 */     if (len > this._charBufferLength) {
/*  385 */       _writeStringSegments(name, true);
/*      */       return;
/*      */     } 
/*  388 */     if (this._outputTail >= this._outputEnd) {
/*  389 */       _flushBuffer();
/*      */     }
/*  391 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  392 */     name.getChars(0, len, this._charBuffer, 0);
/*      */     
/*  394 */     if (len <= this._outputMaxContiguous) {
/*  395 */       if (this._outputTail + len > this._outputEnd) {
/*  396 */         _flushBuffer();
/*      */       }
/*  398 */       _writeStringSegment(this._charBuffer, 0, len);
/*      */     } else {
/*  400 */       _writeStringSegments(this._charBuffer, 0, len);
/*      */     } 
/*  402 */     if (this._outputTail >= this._outputEnd) {
/*  403 */       _flushBuffer();
/*      */     }
/*  405 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _writePPFieldName(SerializableString name) throws IOException {
/*  410 */     int status = this._writeContext.writeFieldName(name.getValue());
/*  411 */     if (status == 4) {
/*  412 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  414 */     if (status == 1) {
/*  415 */       this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
/*      */     } else {
/*  417 */       this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
/*      */     } 
/*      */     
/*  420 */     boolean addQuotes = !this._cfgUnqNames;
/*  421 */     if (addQuotes) {
/*  422 */       if (this._outputTail >= this._outputEnd) {
/*  423 */         _flushBuffer();
/*      */       }
/*  425 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     } 
/*  427 */     _writeBytes(name.asQuotedUTF8());
/*  428 */     if (addQuotes) {
/*  429 */       if (this._outputTail >= this._outputEnd) {
/*  430 */         _flushBuffer();
/*      */       }
/*  432 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
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
/*      */   public void writeString(String text) throws IOException {
/*  445 */     _verifyValueWrite("write a string");
/*  446 */     if (text == null) {
/*  447 */       _writeNull();
/*      */       
/*      */       return;
/*      */     } 
/*  451 */     int len = text.length();
/*  452 */     if (len > this._outputMaxContiguous) {
/*  453 */       _writeStringSegments(text, true);
/*      */       return;
/*      */     } 
/*  456 */     if (this._outputTail + len >= this._outputEnd) {
/*  457 */       _flushBuffer();
/*      */     }
/*  459 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  460 */     _writeStringSegment(text, 0, len);
/*  461 */     if (this._outputTail >= this._outputEnd) {
/*  462 */       _flushBuffer();
/*      */     }
/*  464 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(Reader reader, int len) throws IOException {
/*  469 */     _verifyValueWrite("write a string");
/*  470 */     if (reader == null) {
/*  471 */       _reportError("null reader");
/*      */     }
/*      */     
/*  474 */     int toRead = (len >= 0) ? len : Integer.MAX_VALUE;
/*      */     
/*  476 */     char[] buf = this._charBuffer;
/*      */ 
/*      */     
/*  479 */     if (this._outputTail + len >= this._outputEnd) {
/*  480 */       _flushBuffer();
/*      */     }
/*  482 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */ 
/*      */     
/*  485 */     while (toRead > 0) {
/*  486 */       int toReadNow = Math.min(toRead, buf.length);
/*  487 */       int numRead = reader.read(buf, 0, toReadNow);
/*  488 */       if (numRead <= 0) {
/*      */         break;
/*      */       }
/*  491 */       if (this._outputTail + len >= this._outputEnd) {
/*  492 */         _flushBuffer();
/*      */       }
/*  494 */       _writeStringSegments(buf, 0, numRead);
/*      */       
/*  496 */       toRead -= numRead;
/*      */     } 
/*      */ 
/*      */     
/*  500 */     if (this._outputTail + len >= this._outputEnd) {
/*  501 */       _flushBuffer();
/*      */     }
/*  503 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  505 */     if (toRead > 0 && len >= 0) {
/*  506 */       _reportError("Didn't read enough from reader");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeString(char[] text, int offset, int len) throws IOException {
/*  513 */     _verifyValueWrite("write a string");
/*  514 */     if (this._outputTail >= this._outputEnd) {
/*  515 */       _flushBuffer();
/*      */     }
/*  517 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  519 */     if (len <= this._outputMaxContiguous) {
/*  520 */       if (this._outputTail + len > this._outputEnd) {
/*  521 */         _flushBuffer();
/*      */       }
/*  523 */       _writeStringSegment(text, offset, len);
/*      */     } else {
/*  525 */       _writeStringSegments(text, offset, len);
/*      */     } 
/*      */     
/*  528 */     if (this._outputTail >= this._outputEnd) {
/*  529 */       _flushBuffer();
/*      */     }
/*  531 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeString(SerializableString text) throws IOException {
/*  537 */     _verifyValueWrite("write a string");
/*  538 */     if (this._outputTail >= this._outputEnd) {
/*  539 */       _flushBuffer();
/*      */     }
/*  541 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  542 */     int len = text.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/*  543 */     if (len < 0) {
/*  544 */       _writeBytes(text.asQuotedUTF8());
/*      */     } else {
/*  546 */       this._outputTail += len;
/*      */     } 
/*  548 */     if (this._outputTail >= this._outputEnd) {
/*  549 */       _flushBuffer();
/*      */     }
/*  551 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
/*  557 */     _verifyValueWrite("write a string");
/*  558 */     if (this._outputTail >= this._outputEnd) {
/*  559 */       _flushBuffer();
/*      */     }
/*  561 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  562 */     _writeBytes(text, offset, length);
/*  563 */     if (this._outputTail >= this._outputEnd) {
/*  564 */       _flushBuffer();
/*      */     }
/*  566 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUTF8String(byte[] text, int offset, int len) throws IOException {
/*  572 */     _verifyValueWrite("write a string");
/*  573 */     if (this._outputTail >= this._outputEnd) {
/*  574 */       _flushBuffer();
/*      */     }
/*  576 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  578 */     if (len <= this._outputMaxContiguous) {
/*  579 */       _writeUTF8Segment(text, offset, len);
/*      */     } else {
/*  581 */       _writeUTF8Segments(text, offset, len);
/*      */     } 
/*  583 */     if (this._outputTail >= this._outputEnd) {
/*  584 */       _flushBuffer();
/*      */     }
/*  586 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(String text) throws IOException {
/*  597 */     int len = text.length();
/*  598 */     char[] buf = this._charBuffer;
/*  599 */     if (len <= buf.length) {
/*  600 */       text.getChars(0, len, buf, 0);
/*  601 */       writeRaw(buf, 0, len);
/*      */     } else {
/*  603 */       writeRaw(text, 0, len);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(String text, int offset, int len) throws IOException {
/*  610 */     char[] buf = this._charBuffer;
/*  611 */     int cbufLen = buf.length;
/*      */ 
/*      */     
/*  614 */     if (len <= cbufLen) {
/*  615 */       text.getChars(offset, offset + len, buf, 0);
/*  616 */       writeRaw(buf, 0, len);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  623 */     int maxChunk = Math.min(cbufLen, (this._outputEnd >> 2) + (this._outputEnd >> 4));
/*      */     
/*  625 */     int maxBytes = maxChunk * 3;
/*      */     
/*  627 */     while (len > 0) {
/*  628 */       int len2 = Math.min(maxChunk, len);
/*  629 */       text.getChars(offset, offset + len2, buf, 0);
/*  630 */       if (this._outputTail + maxBytes > this._outputEnd) {
/*  631 */         _flushBuffer();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  639 */       if (len2 > 1) {
/*  640 */         char ch = buf[len2 - 1];
/*  641 */         if (ch >= '?' && ch <= '?') {
/*  642 */           len2--;
/*      */         }
/*      */       } 
/*  645 */       _writeRawSegment(buf, 0, len2);
/*  646 */       offset += len2;
/*  647 */       len -= len2;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(SerializableString text) throws IOException {
/*  654 */     byte[] raw = text.asUnquotedUTF8();
/*  655 */     if (raw.length > 0) {
/*  656 */       _writeBytes(raw);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRawValue(SerializableString text) throws IOException {
/*  663 */     _verifyValueWrite("write a raw (unencoded) value");
/*  664 */     byte[] raw = text.asUnquotedUTF8();
/*  665 */     if (raw.length > 0) {
/*  666 */       _writeBytes(raw);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeRaw(char[] cbuf, int offset, int len) throws IOException {
/*  676 */     int len3 = len + len + len;
/*  677 */     if (this._outputTail + len3 > this._outputEnd) {
/*      */       
/*  679 */       if (this._outputEnd < len3) {
/*  680 */         _writeSegmentedRaw(cbuf, offset, len);
/*      */         
/*      */         return;
/*      */       } 
/*  684 */       _flushBuffer();
/*      */     } 
/*      */     
/*  687 */     len += offset;
/*      */ 
/*      */ 
/*      */     
/*  691 */     while (offset < len) {
/*      */       
/*      */       while (true) {
/*  694 */         int ch = cbuf[offset];
/*  695 */         if (ch > 127) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  703 */           ch = cbuf[offset++];
/*  704 */           if (ch < 2048) {
/*  705 */             this._outputBuffer[this._outputTail++] = (byte)(0xC0 | ch >> 6);
/*  706 */             this._outputBuffer[this._outputTail++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */           } 
/*  708 */           offset = _outputRawMultiByteChar(ch, cbuf, offset, len);
/*      */           continue;
/*      */         } 
/*      */         this._outputBuffer[this._outputTail++] = (byte)ch;
/*      */         if (++offset >= len)
/*      */           break; 
/*      */       } 
/*      */     }  } public void writeRaw(char ch) throws IOException {
/*  716 */     if (this._outputTail + 3 >= this._outputEnd) {
/*  717 */       _flushBuffer();
/*      */     }
/*  719 */     byte[] bbuf = this._outputBuffer;
/*  720 */     if (ch <= '') {
/*  721 */       bbuf[this._outputTail++] = (byte)ch;
/*  722 */     } else if (ch < 'ࠀ') {
/*  723 */       bbuf[this._outputTail++] = (byte)(0xC0 | ch >> 6);
/*  724 */       bbuf[this._outputTail++] = (byte)(0x80 | ch & 0x3F);
/*      */     } else {
/*  726 */       _outputRawMultiByteChar(ch, (char[])null, 0, 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeSegmentedRaw(char[] cbuf, int offset, int len) throws IOException {
/*  736 */     int end = this._outputEnd;
/*  737 */     byte[] bbuf = this._outputBuffer;
/*  738 */     int inputEnd = offset + len;
/*      */ 
/*      */     
/*  741 */     while (offset < inputEnd) {
/*      */       
/*      */       while (true) {
/*  744 */         int ch = cbuf[offset];
/*  745 */         if (ch >= 128) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  757 */           if (this._outputTail + 3 >= this._outputEnd) {
/*  758 */             _flushBuffer();
/*      */           }
/*  760 */           ch = cbuf[offset++];
/*  761 */           if (ch < 2048) {
/*  762 */             bbuf[this._outputTail++] = (byte)(0xC0 | ch >> 6);
/*  763 */             bbuf[this._outputTail++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */           } 
/*  765 */           offset = _outputRawMultiByteChar(ch, cbuf, offset, inputEnd);
/*      */           continue;
/*      */         } 
/*      */         if (this._outputTail >= end) {
/*      */           _flushBuffer();
/*      */         }
/*      */         bbuf[this._outputTail++] = (byte)ch;
/*      */         if (++offset >= inputEnd) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeRawSegment(char[] cbuf, int offset, int end) throws IOException {
/*  782 */     while (offset < end) {
/*      */       
/*      */       while (true) {
/*  785 */         int ch = cbuf[offset];
/*  786 */         if (ch > 127) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  794 */           ch = cbuf[offset++];
/*  795 */           if (ch < 2048) {
/*  796 */             this._outputBuffer[this._outputTail++] = (byte)(0xC0 | ch >> 6);
/*  797 */             this._outputBuffer[this._outputTail++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */           } 
/*  799 */           offset = _outputRawMultiByteChar(ch, cbuf, offset, end);
/*      */           continue;
/*      */         } 
/*      */         this._outputBuffer[this._outputTail++] = (byte)ch;
/*      */         if (++offset >= end) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException, JsonGenerationException {
/*  815 */     _verifyValueWrite("write a binary value");
/*      */     
/*  817 */     if (this._outputTail >= this._outputEnd) {
/*  818 */       _flushBuffer();
/*      */     }
/*  820 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  821 */     _writeBinary(b64variant, data, offset, offset + len);
/*      */     
/*  823 */     if (this._outputTail >= this._outputEnd) {
/*  824 */       _flushBuffer();
/*      */     }
/*  826 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) throws IOException, JsonGenerationException {
/*      */     int bytes;
/*  834 */     _verifyValueWrite("write a binary value");
/*      */     
/*  836 */     if (this._outputTail >= this._outputEnd) {
/*  837 */       _flushBuffer();
/*      */     }
/*  839 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  840 */     byte[] encodingBuffer = this._ioContext.allocBase64Buffer();
/*      */     
/*      */     try {
/*  843 */       if (dataLength < 0) {
/*  844 */         bytes = _writeBinary(b64variant, data, encodingBuffer);
/*      */       } else {
/*  846 */         int missing = _writeBinary(b64variant, data, encodingBuffer, dataLength);
/*  847 */         if (missing > 0) {
/*  848 */           _reportError("Too few bytes available: missing " + missing + " bytes (out of " + dataLength + ")");
/*      */         }
/*  850 */         bytes = dataLength;
/*      */       } 
/*      */     } finally {
/*  853 */       this._ioContext.releaseBase64Buffer(encodingBuffer);
/*      */     } 
/*      */     
/*  856 */     if (this._outputTail >= this._outputEnd) {
/*  857 */       _flushBuffer();
/*      */     }
/*  859 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  860 */     return bytes;
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
/*      */   public void writeNumber(short s) throws IOException {
/*  872 */     _verifyValueWrite("write a number");
/*      */     
/*  874 */     if (this._outputTail + 6 >= this._outputEnd) {
/*  875 */       _flushBuffer();
/*      */     }
/*  877 */     if (this._cfgNumbersAsStrings) {
/*  878 */       _writeQuotedShort(s);
/*      */       return;
/*      */     } 
/*  881 */     this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private final void _writeQuotedShort(short s) throws IOException {
/*  885 */     if (this._outputTail + 8 >= this._outputEnd) {
/*  886 */       _flushBuffer();
/*      */     }
/*  888 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  889 */     this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
/*  890 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(int i) throws IOException {
/*  896 */     _verifyValueWrite("write a number");
/*      */     
/*  898 */     if (this._outputTail + 11 >= this._outputEnd) {
/*  899 */       _flushBuffer();
/*      */     }
/*  901 */     if (this._cfgNumbersAsStrings) {
/*  902 */       _writeQuotedInt(i);
/*      */       return;
/*      */     } 
/*  905 */     this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeQuotedInt(int i) throws IOException {
/*  910 */     if (this._outputTail + 13 >= this._outputEnd) {
/*  911 */       _flushBuffer();
/*      */     }
/*  913 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  914 */     this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
/*  915 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(long l) throws IOException {
/*  921 */     _verifyValueWrite("write a number");
/*  922 */     if (this._cfgNumbersAsStrings) {
/*  923 */       _writeQuotedLong(l);
/*      */       return;
/*      */     } 
/*  926 */     if (this._outputTail + 21 >= this._outputEnd)
/*      */     {
/*  928 */       _flushBuffer();
/*      */     }
/*  930 */     this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeQuotedLong(long l) throws IOException {
/*  935 */     if (this._outputTail + 23 >= this._outputEnd) {
/*  936 */       _flushBuffer();
/*      */     }
/*  938 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  939 */     this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
/*  940 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(BigInteger value) throws IOException {
/*  946 */     _verifyValueWrite("write a number");
/*  947 */     if (value == null) {
/*  948 */       _writeNull();
/*  949 */     } else if (this._cfgNumbersAsStrings) {
/*  950 */       _writeQuotedRaw(value.toString());
/*      */     } else {
/*  952 */       writeRaw(value.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(double d) throws IOException {
/*  960 */     if (this._cfgNumbersAsStrings || ((Double.isNaN(d) || Double.isInfinite(d)) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
/*      */ 
/*      */       
/*  963 */       writeString(String.valueOf(d));
/*      */       
/*      */       return;
/*      */     } 
/*  967 */     _verifyValueWrite("write a number");
/*  968 */     writeRaw(String.valueOf(d));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(float f) throws IOException {
/*  974 */     if (this._cfgNumbersAsStrings || ((Float.isNaN(f) || Float.isInfinite(f)) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
/*      */ 
/*      */ 
/*      */       
/*  978 */       writeString(String.valueOf(f));
/*      */       
/*      */       return;
/*      */     } 
/*  982 */     _verifyValueWrite("write a number");
/*  983 */     writeRaw(String.valueOf(f));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(BigDecimal value) throws IOException {
/*  990 */     _verifyValueWrite("write a number");
/*  991 */     if (value == null) {
/*  992 */       _writeNull();
/*  993 */     } else if (this._cfgNumbersAsStrings) {
/*  994 */       _writeQuotedRaw(_asString(value));
/*      */     } else {
/*  996 */       writeRaw(_asString(value));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(String encodedValue) throws IOException {
/* 1003 */     _verifyValueWrite("write a number");
/* 1004 */     if (this._cfgNumbersAsStrings) {
/* 1005 */       _writeQuotedRaw(encodedValue);
/*      */     } else {
/* 1007 */       writeRaw(encodedValue);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeQuotedRaw(String value) throws IOException {
/* 1013 */     if (this._outputTail >= this._outputEnd) {
/* 1014 */       _flushBuffer();
/*      */     }
/* 1016 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/* 1017 */     writeRaw(value);
/* 1018 */     if (this._outputTail >= this._outputEnd) {
/* 1019 */       _flushBuffer();
/*      */     }
/* 1021 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBoolean(boolean state) throws IOException {
/* 1027 */     _verifyValueWrite("write a boolean value");
/* 1028 */     if (this._outputTail + 5 >= this._outputEnd) {
/* 1029 */       _flushBuffer();
/*      */     }
/* 1031 */     byte[] keyword = state ? TRUE_BYTES : FALSE_BYTES;
/* 1032 */     int len = keyword.length;
/* 1033 */     System.arraycopy(keyword, 0, this._outputBuffer, this._outputTail, len);
/* 1034 */     this._outputTail += len;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNull() throws IOException {
/* 1040 */     _verifyValueWrite("write a null");
/* 1041 */     _writeNull();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyValueWrite(String typeMsg) throws IOException {
/*      */     byte b;
/* 1053 */     int status = this._writeContext.writeValue();
/* 1054 */     if (this._cfgPrettyPrinter != null) {
/*      */       
/* 1056 */       _verifyPrettyValueWrite(typeMsg, status);
/*      */       
/*      */       return;
/*      */     } 
/* 1060 */     switch (status) {
/*      */       default:
/*      */         return;
/*      */       
/*      */       case 1:
/* 1065 */         b = 44;
/*      */         break;
/*      */       case 2:
/* 1068 */         b = 58;
/*      */         break;
/*      */       case 3:
/* 1071 */         if (this._rootValueSeparator != null) {
/* 1072 */           byte[] raw = this._rootValueSeparator.asUnquotedUTF8();
/* 1073 */           if (raw.length > 0) {
/* 1074 */             _writeBytes(raw);
/*      */           }
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 1079 */         _reportCantWriteValueExpectName(typeMsg);
/*      */         return;
/*      */     } 
/* 1082 */     if (this._outputTail >= this._outputEnd) {
/* 1083 */       _flushBuffer();
/*      */     }
/* 1085 */     this._outputBuffer[this._outputTail++] = b;
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
/*      */   public void flush() throws IOException {
/* 1097 */     _flushBuffer();
/* 1098 */     if (this._outputStream != null && 
/* 1099 */       isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/* 1100 */       this._outputStream.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 1108 */     super.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1114 */     if (this._outputBuffer != null && isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
/*      */       while (true) {
/*      */         
/* 1117 */         JsonStreamContext ctxt = getOutputContext();
/* 1118 */         if (ctxt.inArray()) {
/* 1119 */           writeEndArray(); continue;
/* 1120 */         }  if (ctxt.inObject()) {
/* 1121 */           writeEndObject();
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     }
/* 1127 */     _flushBuffer();
/* 1128 */     this._outputTail = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1136 */     if (this._outputStream != null) {
/* 1137 */       if (this._ioContext.isResourceManaged() || isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
/* 1138 */         this._outputStream.close();
/* 1139 */       } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/*      */         
/* 1141 */         this._outputStream.flush();
/*      */       } 
/*      */     }
/*      */     
/* 1145 */     _releaseBuffers();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _releaseBuffers() {
/* 1151 */     byte[] buf = this._outputBuffer;
/* 1152 */     if (buf != null && this._bufferRecyclable) {
/* 1153 */       this._outputBuffer = null;
/* 1154 */       this._ioContext.releaseWriteEncodingBuffer(buf);
/*      */     } 
/* 1156 */     char[] cbuf = this._charBuffer;
/* 1157 */     if (cbuf != null) {
/* 1158 */       this._charBuffer = null;
/* 1159 */       this._ioContext.releaseConcatBuffer(cbuf);
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
/*      */   private final void _writeBytes(byte[] bytes) throws IOException {
/* 1171 */     int len = bytes.length;
/* 1172 */     if (this._outputTail + len > this._outputEnd) {
/* 1173 */       _flushBuffer();
/*      */       
/* 1175 */       if (len > 512) {
/* 1176 */         this._outputStream.write(bytes, 0, len);
/*      */         return;
/*      */       } 
/*      */     } 
/* 1180 */     System.arraycopy(bytes, 0, this._outputBuffer, this._outputTail, len);
/* 1181 */     this._outputTail += len;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeBytes(byte[] bytes, int offset, int len) throws IOException {
/* 1186 */     if (this._outputTail + len > this._outputEnd) {
/* 1187 */       _flushBuffer();
/*      */       
/* 1189 */       if (len > 512) {
/* 1190 */         this._outputStream.write(bytes, offset, len);
/*      */         return;
/*      */       } 
/*      */     } 
/* 1194 */     System.arraycopy(bytes, offset, this._outputBuffer, this._outputTail, len);
/* 1195 */     this._outputTail += len;
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
/*      */   private final void _writeStringSegments(String text, boolean addQuotes) throws IOException {
/* 1213 */     if (addQuotes) {
/* 1214 */       if (this._outputTail >= this._outputEnd) {
/* 1215 */         _flushBuffer();
/*      */       }
/* 1217 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     } 
/*      */     
/* 1220 */     int left = text.length();
/* 1221 */     int offset = 0;
/*      */     
/* 1223 */     while (left > 0) {
/* 1224 */       int len = Math.min(this._outputMaxContiguous, left);
/* 1225 */       if (this._outputTail + len > this._outputEnd) {
/* 1226 */         _flushBuffer();
/*      */       }
/* 1228 */       _writeStringSegment(text, offset, len);
/* 1229 */       offset += len;
/* 1230 */       left -= len;
/*      */     } 
/*      */     
/* 1233 */     if (addQuotes) {
/* 1234 */       if (this._outputTail >= this._outputEnd) {
/* 1235 */         _flushBuffer();
/*      */       }
/* 1237 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
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
/*      */   private final void _writeStringSegments(char[] cbuf, int offset, int totalLen) throws IOException {
/*      */     do {
/* 1250 */       int len = Math.min(this._outputMaxContiguous, totalLen);
/* 1251 */       if (this._outputTail + len > this._outputEnd) {
/* 1252 */         _flushBuffer();
/*      */       }
/* 1254 */       _writeStringSegment(cbuf, offset, len);
/* 1255 */       offset += len;
/* 1256 */       totalLen -= len;
/* 1257 */     } while (totalLen > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeStringSegments(String text, int offset, int totalLen) throws IOException {
/*      */     do {
/* 1263 */       int len = Math.min(this._outputMaxContiguous, totalLen);
/* 1264 */       if (this._outputTail + len > this._outputEnd) {
/* 1265 */         _flushBuffer();
/*      */       }
/* 1267 */       _writeStringSegment(text, offset, len);
/* 1268 */       offset += len;
/* 1269 */       totalLen -= len;
/* 1270 */     } while (totalLen > 0);
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
/*      */   private final void _writeStringSegment(char[] cbuf, int offset, int len) throws IOException {
/* 1293 */     len += offset;
/*      */     
/* 1295 */     int outputPtr = this._outputTail;
/* 1296 */     byte[] outputBuffer = this._outputBuffer;
/* 1297 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1299 */     while (offset < len) {
/* 1300 */       int ch = cbuf[offset];
/*      */       
/* 1302 */       if (ch > 127 || escCodes[ch] != 0) {
/*      */         break;
/*      */       }
/* 1305 */       outputBuffer[outputPtr++] = (byte)ch;
/* 1306 */       offset++;
/*      */     } 
/* 1308 */     this._outputTail = outputPtr;
/* 1309 */     if (offset < len)
/*      */     {
/* 1311 */       if (this._characterEscapes != null) {
/* 1312 */         _writeCustomStringSegment2(cbuf, offset, len);
/*      */       }
/* 1314 */       else if (this._maximumNonEscapedChar == 0) {
/* 1315 */         _writeStringSegment2(cbuf, offset, len);
/*      */       } else {
/* 1317 */         _writeStringSegmentASCII2(cbuf, offset, len);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeStringSegment(String text, int offset, int len) throws IOException {
/* 1327 */     len += offset;
/*      */     
/* 1329 */     int outputPtr = this._outputTail;
/* 1330 */     byte[] outputBuffer = this._outputBuffer;
/* 1331 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1333 */     while (offset < len) {
/* 1334 */       int ch = text.charAt(offset);
/*      */       
/* 1336 */       if (ch > 127 || escCodes[ch] != 0) {
/*      */         break;
/*      */       }
/* 1339 */       outputBuffer[outputPtr++] = (byte)ch;
/* 1340 */       offset++;
/*      */     } 
/* 1342 */     this._outputTail = outputPtr;
/* 1343 */     if (offset < len) {
/* 1344 */       if (this._characterEscapes != null) {
/* 1345 */         _writeCustomStringSegment2(text, offset, len);
/* 1346 */       } else if (this._maximumNonEscapedChar == 0) {
/* 1347 */         _writeStringSegment2(text, offset, len);
/*      */       } else {
/* 1349 */         _writeStringSegmentASCII2(text, offset, len);
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
/*      */   private final void _writeStringSegment2(char[] cbuf, int offset, int end) throws IOException {
/* 1361 */     if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
/* 1362 */       _flushBuffer();
/*      */     }
/*      */     
/* 1365 */     int outputPtr = this._outputTail;
/*      */     
/* 1367 */     byte[] outputBuffer = this._outputBuffer;
/* 1368 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1370 */     while (offset < end) {
/* 1371 */       int ch = cbuf[offset++];
/* 1372 */       if (ch <= 127) {
/* 1373 */         if (escCodes[ch] == 0) {
/* 1374 */           outputBuffer[outputPtr++] = (byte)ch;
/*      */           continue;
/*      */         } 
/* 1377 */         int escape = escCodes[ch];
/* 1378 */         if (escape > 0) {
/* 1379 */           outputBuffer[outputPtr++] = 92;
/* 1380 */           outputBuffer[outputPtr++] = (byte)escape;
/*      */           continue;
/*      */         } 
/* 1383 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         
/*      */         continue;
/*      */       } 
/* 1387 */       if (ch <= 2047) {
/* 1388 */         outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 1389 */         outputBuffer[outputPtr++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */       } 
/* 1391 */       outputPtr = _outputMultiByteChar(ch, outputPtr);
/*      */     } 
/*      */     
/* 1394 */     this._outputTail = outputPtr;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeStringSegment2(String text, int offset, int end) throws IOException {
/* 1399 */     if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
/* 1400 */       _flushBuffer();
/*      */     }
/*      */     
/* 1403 */     int outputPtr = this._outputTail;
/*      */     
/* 1405 */     byte[] outputBuffer = this._outputBuffer;
/* 1406 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1408 */     while (offset < end) {
/* 1409 */       int ch = text.charAt(offset++);
/* 1410 */       if (ch <= 127) {
/* 1411 */         if (escCodes[ch] == 0) {
/* 1412 */           outputBuffer[outputPtr++] = (byte)ch;
/*      */           continue;
/*      */         } 
/* 1415 */         int escape = escCodes[ch];
/* 1416 */         if (escape > 0) {
/* 1417 */           outputBuffer[outputPtr++] = 92;
/* 1418 */           outputBuffer[outputPtr++] = (byte)escape;
/*      */           continue;
/*      */         } 
/* 1421 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         
/*      */         continue;
/*      */       } 
/* 1425 */       if (ch <= 2047) {
/* 1426 */         outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 1427 */         outputBuffer[outputPtr++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */       } 
/* 1429 */       outputPtr = _outputMultiByteChar(ch, outputPtr);
/*      */     } 
/*      */     
/* 1432 */     this._outputTail = outputPtr;
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
/*      */   private final void _writeStringSegmentASCII2(char[] cbuf, int offset, int end) throws IOException {
/* 1449 */     if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
/* 1450 */       _flushBuffer();
/*      */     }
/*      */     
/* 1453 */     int outputPtr = this._outputTail;
/*      */     
/* 1455 */     byte[] outputBuffer = this._outputBuffer;
/* 1456 */     int[] escCodes = this._outputEscapes;
/* 1457 */     int maxUnescaped = this._maximumNonEscapedChar;
/*      */     
/* 1459 */     while (offset < end) {
/* 1460 */       int ch = cbuf[offset++];
/* 1461 */       if (ch <= 127) {
/* 1462 */         if (escCodes[ch] == 0) {
/* 1463 */           outputBuffer[outputPtr++] = (byte)ch;
/*      */           continue;
/*      */         } 
/* 1466 */         int escape = escCodes[ch];
/* 1467 */         if (escape > 0) {
/* 1468 */           outputBuffer[outputPtr++] = 92;
/* 1469 */           outputBuffer[outputPtr++] = (byte)escape;
/*      */           continue;
/*      */         } 
/* 1472 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         
/*      */         continue;
/*      */       } 
/* 1476 */       if (ch > maxUnescaped) {
/* 1477 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         continue;
/*      */       } 
/* 1480 */       if (ch <= 2047) {
/* 1481 */         outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 1482 */         outputBuffer[outputPtr++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */       } 
/* 1484 */       outputPtr = _outputMultiByteChar(ch, outputPtr);
/*      */     } 
/*      */     
/* 1487 */     this._outputTail = outputPtr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeStringSegmentASCII2(String text, int offset, int end) throws IOException {
/* 1493 */     if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
/* 1494 */       _flushBuffer();
/*      */     }
/*      */     
/* 1497 */     int outputPtr = this._outputTail;
/*      */     
/* 1499 */     byte[] outputBuffer = this._outputBuffer;
/* 1500 */     int[] escCodes = this._outputEscapes;
/* 1501 */     int maxUnescaped = this._maximumNonEscapedChar;
/*      */     
/* 1503 */     while (offset < end) {
/* 1504 */       int ch = text.charAt(offset++);
/* 1505 */       if (ch <= 127) {
/* 1506 */         if (escCodes[ch] == 0) {
/* 1507 */           outputBuffer[outputPtr++] = (byte)ch;
/*      */           continue;
/*      */         } 
/* 1510 */         int escape = escCodes[ch];
/* 1511 */         if (escape > 0) {
/* 1512 */           outputBuffer[outputPtr++] = 92;
/* 1513 */           outputBuffer[outputPtr++] = (byte)escape;
/*      */           continue;
/*      */         } 
/* 1516 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         
/*      */         continue;
/*      */       } 
/* 1520 */       if (ch > maxUnescaped) {
/* 1521 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         continue;
/*      */       } 
/* 1524 */       if (ch <= 2047) {
/* 1525 */         outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 1526 */         outputBuffer[outputPtr++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */       } 
/* 1528 */       outputPtr = _outputMultiByteChar(ch, outputPtr);
/*      */     } 
/*      */     
/* 1531 */     this._outputTail = outputPtr;
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
/*      */   private final void _writeCustomStringSegment2(char[] cbuf, int offset, int end) throws IOException {
/* 1548 */     if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
/* 1549 */       _flushBuffer();
/*      */     }
/* 1551 */     int outputPtr = this._outputTail;
/*      */     
/* 1553 */     byte[] outputBuffer = this._outputBuffer;
/* 1554 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1556 */     int maxUnescaped = (this._maximumNonEscapedChar <= 0) ? 65535 : this._maximumNonEscapedChar;
/* 1557 */     CharacterEscapes customEscapes = this._characterEscapes;
/*      */     
/* 1559 */     while (offset < end) {
/* 1560 */       int ch = cbuf[offset++];
/* 1561 */       if (ch <= 127) {
/* 1562 */         if (escCodes[ch] == 0) {
/* 1563 */           outputBuffer[outputPtr++] = (byte)ch;
/*      */           continue;
/*      */         } 
/* 1566 */         int escape = escCodes[ch];
/* 1567 */         if (escape > 0) {
/* 1568 */           outputBuffer[outputPtr++] = 92;
/* 1569 */           outputBuffer[outputPtr++] = (byte)escape; continue;
/* 1570 */         }  if (escape == -2) {
/* 1571 */           SerializableString serializableString = customEscapes.getEscapeSequence(ch);
/* 1572 */           if (serializableString == null) {
/* 1573 */             _reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(ch) + ", although was supposed to have one");
/*      */           }
/*      */           
/* 1576 */           outputPtr = _writeCustomEscape(outputBuffer, outputPtr, serializableString, end - offset);
/*      */           continue;
/*      */         } 
/* 1579 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         
/*      */         continue;
/*      */       } 
/* 1583 */       if (ch > maxUnescaped) {
/* 1584 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         continue;
/*      */       } 
/* 1587 */       SerializableString esc = customEscapes.getEscapeSequence(ch);
/* 1588 */       if (esc != null) {
/* 1589 */         outputPtr = _writeCustomEscape(outputBuffer, outputPtr, esc, end - offset);
/*      */         continue;
/*      */       } 
/* 1592 */       if (ch <= 2047) {
/* 1593 */         outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 1594 */         outputBuffer[outputPtr++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */       } 
/* 1596 */       outputPtr = _outputMultiByteChar(ch, outputPtr);
/*      */     } 
/*      */     
/* 1599 */     this._outputTail = outputPtr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeCustomStringSegment2(String text, int offset, int end) throws IOException {
/* 1605 */     if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
/* 1606 */       _flushBuffer();
/*      */     }
/* 1608 */     int outputPtr = this._outputTail;
/*      */     
/* 1610 */     byte[] outputBuffer = this._outputBuffer;
/* 1611 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1613 */     int maxUnescaped = (this._maximumNonEscapedChar <= 0) ? 65535 : this._maximumNonEscapedChar;
/* 1614 */     CharacterEscapes customEscapes = this._characterEscapes;
/*      */     
/* 1616 */     while (offset < end) {
/* 1617 */       int ch = text.charAt(offset++);
/* 1618 */       if (ch <= 127) {
/* 1619 */         if (escCodes[ch] == 0) {
/* 1620 */           outputBuffer[outputPtr++] = (byte)ch;
/*      */           continue;
/*      */         } 
/* 1623 */         int escape = escCodes[ch];
/* 1624 */         if (escape > 0) {
/* 1625 */           outputBuffer[outputPtr++] = 92;
/* 1626 */           outputBuffer[outputPtr++] = (byte)escape; continue;
/* 1627 */         }  if (escape == -2) {
/* 1628 */           SerializableString serializableString = customEscapes.getEscapeSequence(ch);
/* 1629 */           if (serializableString == null) {
/* 1630 */             _reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(ch) + ", although was supposed to have one");
/*      */           }
/*      */           
/* 1633 */           outputPtr = _writeCustomEscape(outputBuffer, outputPtr, serializableString, end - offset);
/*      */           continue;
/*      */         } 
/* 1636 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         
/*      */         continue;
/*      */       } 
/* 1640 */       if (ch > maxUnescaped) {
/* 1641 */         outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */         continue;
/*      */       } 
/* 1644 */       SerializableString esc = customEscapes.getEscapeSequence(ch);
/* 1645 */       if (esc != null) {
/* 1646 */         outputPtr = _writeCustomEscape(outputBuffer, outputPtr, esc, end - offset);
/*      */         continue;
/*      */       } 
/* 1649 */       if (ch <= 2047) {
/* 1650 */         outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
/* 1651 */         outputBuffer[outputPtr++] = (byte)(0x80 | ch & 0x3F); continue;
/*      */       } 
/* 1653 */       outputPtr = _outputMultiByteChar(ch, outputPtr);
/*      */     } 
/*      */     
/* 1656 */     this._outputTail = outputPtr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _writeCustomEscape(byte[] outputBuffer, int outputPtr, SerializableString esc, int remainingChars) throws IOException, JsonGenerationException {
/* 1662 */     byte[] raw = esc.asUnquotedUTF8();
/* 1663 */     int len = raw.length;
/* 1664 */     if (len > 6) {
/* 1665 */       return _handleLongCustomEscape(outputBuffer, outputPtr, this._outputEnd, raw, remainingChars);
/*      */     }
/*      */     
/* 1668 */     System.arraycopy(raw, 0, outputBuffer, outputPtr, len);
/* 1669 */     return outputPtr + len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _handleLongCustomEscape(byte[] outputBuffer, int outputPtr, int outputEnd, byte[] raw, int remainingChars) throws IOException, JsonGenerationException {
/* 1676 */     int len = raw.length;
/* 1677 */     if (outputPtr + len > outputEnd) {
/* 1678 */       this._outputTail = outputPtr;
/* 1679 */       _flushBuffer();
/* 1680 */       outputPtr = this._outputTail;
/* 1681 */       if (len > outputBuffer.length) {
/* 1682 */         this._outputStream.write(raw, 0, len);
/* 1683 */         return outputPtr;
/*      */       } 
/* 1685 */       System.arraycopy(raw, 0, outputBuffer, outputPtr, len);
/* 1686 */       outputPtr += len;
/*      */     } 
/*      */     
/* 1689 */     if (outputPtr + 6 * remainingChars > outputEnd) {
/* 1690 */       _flushBuffer();
/* 1691 */       return this._outputTail;
/*      */     } 
/* 1693 */     return outputPtr;
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
/*      */   private final void _writeUTF8Segments(byte[] utf8, int offset, int totalLen) throws IOException, JsonGenerationException {
/*      */     do {
/* 1711 */       int len = Math.min(this._outputMaxContiguous, totalLen);
/* 1712 */       _writeUTF8Segment(utf8, offset, len);
/* 1713 */       offset += len;
/* 1714 */       totalLen -= len;
/* 1715 */     } while (totalLen > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeUTF8Segment(byte[] utf8, int offset, int len) throws IOException, JsonGenerationException {
/* 1722 */     int[] escCodes = this._outputEscapes;
/*      */     
/* 1724 */     for (int ptr = offset, end = offset + len; ptr < end; ) {
/*      */       
/* 1726 */       int ch = utf8[ptr++];
/* 1727 */       if (ch >= 0 && escCodes[ch] != 0) {
/* 1728 */         _writeUTF8Segment2(utf8, offset, len);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1734 */     if (this._outputTail + len > this._outputEnd) {
/* 1735 */       _flushBuffer();
/*      */     }
/* 1737 */     System.arraycopy(utf8, offset, this._outputBuffer, this._outputTail, len);
/* 1738 */     this._outputTail += len;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeUTF8Segment2(byte[] utf8, int offset, int len) throws IOException, JsonGenerationException {
/* 1744 */     int outputPtr = this._outputTail;
/*      */ 
/*      */     
/* 1747 */     if (outputPtr + len * 6 > this._outputEnd) {
/* 1748 */       _flushBuffer();
/* 1749 */       outputPtr = this._outputTail;
/*      */     } 
/*      */     
/* 1752 */     byte[] outputBuffer = this._outputBuffer;
/* 1753 */     int[] escCodes = this._outputEscapes;
/* 1754 */     len += offset;
/*      */     
/* 1756 */     while (offset < len) {
/* 1757 */       byte b = utf8[offset++];
/* 1758 */       int ch = b;
/* 1759 */       if (ch < 0 || escCodes[ch] == 0) {
/* 1760 */         outputBuffer[outputPtr++] = b;
/*      */         continue;
/*      */       } 
/* 1763 */       int escape = escCodes[ch];
/* 1764 */       if (escape > 0) {
/* 1765 */         outputBuffer[outputPtr++] = 92;
/* 1766 */         outputBuffer[outputPtr++] = (byte)escape;
/*      */         continue;
/*      */       } 
/* 1769 */       outputPtr = _writeGenericEscape(ch, outputPtr);
/*      */     } 
/*      */     
/* 1772 */     this._outputTail = outputPtr;
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
/*      */   protected final void _writeBinary(Base64Variant b64variant, byte[] input, int inputPtr, int inputEnd) throws IOException, JsonGenerationException {
/* 1786 */     int safeInputEnd = inputEnd - 3;
/*      */     
/* 1788 */     int safeOutputEnd = this._outputEnd - 6;
/* 1789 */     int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */ 
/*      */     
/* 1792 */     while (inputPtr <= safeInputEnd) {
/* 1793 */       if (this._outputTail > safeOutputEnd) {
/* 1794 */         _flushBuffer();
/*      */       }
/*      */       
/* 1797 */       int b24 = input[inputPtr++] << 8;
/* 1798 */       b24 |= input[inputPtr++] & 0xFF;
/* 1799 */       b24 = b24 << 8 | input[inputPtr++] & 0xFF;
/* 1800 */       this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
/* 1801 */       if (--chunksBeforeLF <= 0) {
/*      */         
/* 1803 */         this._outputBuffer[this._outputTail++] = 92;
/* 1804 */         this._outputBuffer[this._outputTail++] = 110;
/* 1805 */         chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1810 */     int inputLeft = inputEnd - inputPtr;
/* 1811 */     if (inputLeft > 0) {
/* 1812 */       if (this._outputTail > safeOutputEnd) {
/* 1813 */         _flushBuffer();
/*      */       }
/* 1815 */       int b24 = input[inputPtr++] << 16;
/* 1816 */       if (inputLeft == 2) {
/* 1817 */         b24 |= (input[inputPtr++] & 0xFF) << 8;
/*      */       }
/* 1819 */       this._outputTail = b64variant.encodeBase64Partial(b24, inputLeft, this._outputBuffer, this._outputTail);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _writeBinary(Base64Variant b64variant, InputStream data, byte[] readBuffer, int bytesLeft) throws IOException, JsonGenerationException {
/* 1828 */     int inputPtr = 0;
/* 1829 */     int inputEnd = 0;
/* 1830 */     int lastFullOffset = -3;
/*      */ 
/*      */     
/* 1833 */     int safeOutputEnd = this._outputEnd - 6;
/* 1834 */     int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */     
/* 1836 */     while (bytesLeft > 2) {
/* 1837 */       if (inputPtr > lastFullOffset) {
/* 1838 */         inputEnd = _readMore(data, readBuffer, inputPtr, inputEnd, bytesLeft);
/* 1839 */         inputPtr = 0;
/* 1840 */         if (inputEnd < 3) {
/*      */           break;
/*      */         }
/* 1843 */         lastFullOffset = inputEnd - 3;
/*      */       } 
/* 1845 */       if (this._outputTail > safeOutputEnd) {
/* 1846 */         _flushBuffer();
/*      */       }
/* 1848 */       int b24 = readBuffer[inputPtr++] << 8;
/* 1849 */       b24 |= readBuffer[inputPtr++] & 0xFF;
/* 1850 */       b24 = b24 << 8 | readBuffer[inputPtr++] & 0xFF;
/* 1851 */       bytesLeft -= 3;
/* 1852 */       this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
/* 1853 */       if (--chunksBeforeLF <= 0) {
/* 1854 */         this._outputBuffer[this._outputTail++] = 92;
/* 1855 */         this._outputBuffer[this._outputTail++] = 110;
/* 1856 */         chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1861 */     if (bytesLeft > 0) {
/* 1862 */       inputEnd = _readMore(data, readBuffer, inputPtr, inputEnd, bytesLeft);
/* 1863 */       inputPtr = 0;
/* 1864 */       if (inputEnd > 0) {
/* 1865 */         int amount; if (this._outputTail > safeOutputEnd) {
/* 1866 */           _flushBuffer();
/*      */         }
/* 1868 */         int b24 = readBuffer[inputPtr++] << 16;
/*      */         
/* 1870 */         if (inputPtr < inputEnd) {
/* 1871 */           b24 |= (readBuffer[inputPtr] & 0xFF) << 8;
/* 1872 */           amount = 2;
/*      */         } else {
/* 1874 */           amount = 1;
/*      */         } 
/* 1876 */         this._outputTail = b64variant.encodeBase64Partial(b24, amount, this._outputBuffer, this._outputTail);
/* 1877 */         bytesLeft -= amount;
/*      */       } 
/*      */     } 
/* 1880 */     return bytesLeft;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _writeBinary(Base64Variant b64variant, InputStream data, byte[] readBuffer) throws IOException, JsonGenerationException {
/* 1888 */     int inputPtr = 0;
/* 1889 */     int inputEnd = 0;
/* 1890 */     int lastFullOffset = -3;
/* 1891 */     int bytesDone = 0;
/*      */ 
/*      */     
/* 1894 */     int safeOutputEnd = this._outputEnd - 6;
/* 1895 */     int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1899 */       if (inputPtr > lastFullOffset) {
/* 1900 */         inputEnd = _readMore(data, readBuffer, inputPtr, inputEnd, readBuffer.length);
/* 1901 */         inputPtr = 0;
/* 1902 */         if (inputEnd < 3) {
/*      */           break;
/*      */         }
/* 1905 */         lastFullOffset = inputEnd - 3;
/*      */       } 
/* 1907 */       if (this._outputTail > safeOutputEnd) {
/* 1908 */         _flushBuffer();
/*      */       }
/*      */       
/* 1911 */       int b24 = readBuffer[inputPtr++] << 8;
/* 1912 */       b24 |= readBuffer[inputPtr++] & 0xFF;
/* 1913 */       b24 = b24 << 8 | readBuffer[inputPtr++] & 0xFF;
/* 1914 */       bytesDone += 3;
/* 1915 */       this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
/* 1916 */       if (--chunksBeforeLF <= 0) {
/* 1917 */         this._outputBuffer[this._outputTail++] = 92;
/* 1918 */         this._outputBuffer[this._outputTail++] = 110;
/* 1919 */         chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1924 */     if (inputPtr < inputEnd) {
/* 1925 */       if (this._outputTail > safeOutputEnd) {
/* 1926 */         _flushBuffer();
/*      */       }
/* 1928 */       int b24 = readBuffer[inputPtr++] << 16;
/* 1929 */       int amount = 1;
/* 1930 */       if (inputPtr < inputEnd) {
/* 1931 */         b24 |= (readBuffer[inputPtr] & 0xFF) << 8;
/* 1932 */         amount = 2;
/*      */       } 
/* 1934 */       bytesDone += amount;
/* 1935 */       this._outputTail = b64variant.encodeBase64Partial(b24, amount, this._outputBuffer, this._outputTail);
/*      */     } 
/* 1937 */     return bytesDone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _readMore(InputStream in, byte[] readBuffer, int inputPtr, int inputEnd, int maxRead) throws IOException {
/* 1945 */     int i = 0;
/* 1946 */     while (inputPtr < inputEnd) {
/* 1947 */       readBuffer[i++] = readBuffer[inputPtr++];
/*      */     }
/* 1949 */     inputPtr = 0;
/* 1950 */     inputEnd = i;
/* 1951 */     maxRead = Math.min(maxRead, readBuffer.length);
/*      */     
/*      */     do {
/* 1954 */       int length = maxRead - inputEnd;
/* 1955 */       if (length == 0) {
/*      */         break;
/*      */       }
/* 1958 */       int count = in.read(readBuffer, inputEnd, length);
/* 1959 */       if (count < 0) {
/* 1960 */         return inputEnd;
/*      */       }
/* 1962 */       inputEnd += count;
/* 1963 */     } while (inputEnd < 3);
/* 1964 */     return inputEnd;
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
/*      */   private final int _outputRawMultiByteChar(int ch, char[] cbuf, int inputOffset, int inputEnd) throws IOException {
/* 1982 */     if (ch >= 55296 && 
/* 1983 */       ch <= 57343) {
/*      */       
/* 1985 */       if (inputOffset >= inputEnd || cbuf == null) {
/* 1986 */         _reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", new Object[] { Integer.valueOf(ch) }));
/*      */       }
/*      */       
/* 1989 */       _outputSurrogates(ch, cbuf[inputOffset]);
/* 1990 */       return inputOffset + 1;
/*      */     } 
/*      */     
/* 1993 */     byte[] bbuf = this._outputBuffer;
/* 1994 */     bbuf[this._outputTail++] = (byte)(0xE0 | ch >> 12);
/* 1995 */     bbuf[this._outputTail++] = (byte)(0x80 | ch >> 6 & 0x3F);
/* 1996 */     bbuf[this._outputTail++] = (byte)(0x80 | ch & 0x3F);
/* 1997 */     return inputOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _outputSurrogates(int surr1, int surr2) throws IOException {
/* 2002 */     int c = _decodeSurrogate(surr1, surr2);
/* 2003 */     if (this._outputTail + 4 > this._outputEnd) {
/* 2004 */       _flushBuffer();
/*      */     }
/* 2006 */     byte[] bbuf = this._outputBuffer;
/* 2007 */     bbuf[this._outputTail++] = (byte)(0xF0 | c >> 18);
/* 2008 */     bbuf[this._outputTail++] = (byte)(0x80 | c >> 12 & 0x3F);
/* 2009 */     bbuf[this._outputTail++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 2010 */     bbuf[this._outputTail++] = (byte)(0x80 | c & 0x3F);
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
/*      */   private final int _outputMultiByteChar(int ch, int outputPtr) throws IOException {
/* 2024 */     byte[] bbuf = this._outputBuffer;
/* 2025 */     if (ch >= 55296 && ch <= 57343) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2030 */       bbuf[outputPtr++] = 92;
/* 2031 */       bbuf[outputPtr++] = 117;
/*      */       
/* 2033 */       bbuf[outputPtr++] = HEX_CHARS[ch >> 12 & 0xF];
/* 2034 */       bbuf[outputPtr++] = HEX_CHARS[ch >> 8 & 0xF];
/* 2035 */       bbuf[outputPtr++] = HEX_CHARS[ch >> 4 & 0xF];
/* 2036 */       bbuf[outputPtr++] = HEX_CHARS[ch & 0xF];
/*      */     } else {
/*      */       
/* 2039 */       bbuf[outputPtr++] = (byte)(0xE0 | ch >> 12);
/* 2040 */       bbuf[outputPtr++] = (byte)(0x80 | ch >> 6 & 0x3F);
/* 2041 */       bbuf[outputPtr++] = (byte)(0x80 | ch & 0x3F);
/*      */     } 
/* 2043 */     return outputPtr;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _writeNull() throws IOException {
/* 2048 */     if (this._outputTail + 4 >= this._outputEnd) {
/* 2049 */       _flushBuffer();
/*      */     }
/* 2051 */     System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
/* 2052 */     this._outputTail += 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _writeGenericEscape(int charToEscape, int outputPtr) throws IOException {
/* 2062 */     byte[] bbuf = this._outputBuffer;
/* 2063 */     bbuf[outputPtr++] = 92;
/* 2064 */     bbuf[outputPtr++] = 117;
/* 2065 */     if (charToEscape > 255) {
/* 2066 */       int hi = charToEscape >> 8 & 0xFF;
/* 2067 */       bbuf[outputPtr++] = HEX_CHARS[hi >> 4];
/* 2068 */       bbuf[outputPtr++] = HEX_CHARS[hi & 0xF];
/* 2069 */       charToEscape &= 0xFF;
/*      */     } else {
/* 2071 */       bbuf[outputPtr++] = 48;
/* 2072 */       bbuf[outputPtr++] = 48;
/*      */     } 
/*      */     
/* 2075 */     bbuf[outputPtr++] = HEX_CHARS[charToEscape >> 4];
/* 2076 */     bbuf[outputPtr++] = HEX_CHARS[charToEscape & 0xF];
/* 2077 */     return outputPtr;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _flushBuffer() throws IOException {
/* 2082 */     int len = this._outputTail;
/* 2083 */     if (len > 0) {
/* 2084 */       this._outputTail = 0;
/* 2085 */       this._outputStream.write(this._outputBuffer, 0, len);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\UTF8JsonGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */