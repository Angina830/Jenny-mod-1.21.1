/*      */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
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
/*      */ public class WriterBasedJsonGenerator extends JsonGeneratorImpl {
/*      */   protected static final int SHORT_WRITE = 32;
/*   22 */   protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Writer _writer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   38 */   protected char _quoteChar = '"';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _outputBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _outputHead;
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
/*      */   protected int _outputEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _entityBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SerializableString _currentEscape;
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
/*      */   
/*      */   public WriterBasedJsonGenerator(IOContext ctxt, int features, ObjectCodec codec, Writer w) {
/*   98 */     super(ctxt, features, codec);
/*   99 */     this._writer = w;
/*  100 */     this._outputBuffer = ctxt.allocConcatBuffer();
/*  101 */     this._outputEnd = this._outputBuffer.length;
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
/*  112 */     return this._writer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOutputBuffered() {
/*  118 */     int len = this._outputTail - this._outputHead;
/*  119 */     return Math.max(0, len);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canWriteFormattedNumbers() {
/*  124 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFieldName(String name) throws IOException {
/*  135 */     int status = this._writeContext.writeFieldName(name);
/*  136 */     if (status == 4) {
/*  137 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  139 */     _writeFieldName(name, (status == 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFieldName(SerializableString name) throws IOException {
/*  146 */     int status = this._writeContext.writeFieldName(name.getValue());
/*  147 */     if (status == 4) {
/*  148 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  150 */     _writeFieldName(name, (status == 1));
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _writeFieldName(String name, boolean commaBefore) throws IOException {
/*  155 */     if (this._cfgPrettyPrinter != null) {
/*  156 */       _writePPFieldName(name, commaBefore);
/*      */       
/*      */       return;
/*      */     } 
/*  160 */     if (this._outputTail + 1 >= this._outputEnd) {
/*  161 */       _flushBuffer();
/*      */     }
/*  163 */     if (commaBefore) {
/*  164 */       this._outputBuffer[this._outputTail++] = ',';
/*      */     }
/*      */     
/*  167 */     if (this._cfgUnqNames) {
/*  168 */       _writeString(name);
/*      */       
/*      */       return;
/*      */     } 
/*  172 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  174 */     _writeString(name);
/*      */     
/*  176 */     if (this._outputTail >= this._outputEnd) {
/*  177 */       _flushBuffer();
/*      */     }
/*  179 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _writeFieldName(SerializableString name, boolean commaBefore) throws IOException {
/*  184 */     if (this._cfgPrettyPrinter != null) {
/*  185 */       _writePPFieldName(name, commaBefore);
/*      */       
/*      */       return;
/*      */     } 
/*  189 */     if (this._outputTail + 1 >= this._outputEnd) {
/*  190 */       _flushBuffer();
/*      */     }
/*  192 */     if (commaBefore) {
/*  193 */       this._outputBuffer[this._outputTail++] = ',';
/*      */     }
/*      */     
/*  196 */     char[] quoted = name.asQuotedChars();
/*  197 */     if (this._cfgUnqNames) {
/*  198 */       writeRaw(quoted, 0, quoted.length);
/*      */       
/*      */       return;
/*      */     } 
/*  202 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  204 */     int qlen = quoted.length;
/*  205 */     if (this._outputTail + qlen + 1 >= this._outputEnd) {
/*  206 */       writeRaw(quoted, 0, qlen);
/*      */       
/*  208 */       if (this._outputTail >= this._outputEnd) {
/*  209 */         _flushBuffer();
/*      */       }
/*  211 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     } else {
/*  213 */       System.arraycopy(quoted, 0, this._outputBuffer, this._outputTail, qlen);
/*  214 */       this._outputTail += qlen;
/*  215 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
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
/*      */   public void writeStartArray() throws IOException {
/*  228 */     _verifyValueWrite("start an array");
/*  229 */     this._writeContext = this._writeContext.createChildArrayContext();
/*  230 */     if (this._cfgPrettyPrinter != null) {
/*  231 */       this._cfgPrettyPrinter.writeStartArray((JsonGenerator)this);
/*      */     } else {
/*  233 */       if (this._outputTail >= this._outputEnd) {
/*  234 */         _flushBuffer();
/*      */       }
/*  236 */       this._outputBuffer[this._outputTail++] = '[';
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEndArray() throws IOException {
/*  243 */     if (!this._writeContext.inArray()) {
/*  244 */       _reportError("Current context not Array but " + this._writeContext.typeDesc());
/*      */     }
/*  246 */     if (this._cfgPrettyPrinter != null) {
/*  247 */       this._cfgPrettyPrinter.writeEndArray((JsonGenerator)this, this._writeContext.getEntryCount());
/*      */     } else {
/*  249 */       if (this._outputTail >= this._outputEnd) {
/*  250 */         _flushBuffer();
/*      */       }
/*  252 */       this._outputBuffer[this._outputTail++] = ']';
/*      */     } 
/*  254 */     this._writeContext = this._writeContext.clearAndGetParent();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartObject(Object forValue) throws IOException {
/*  260 */     _verifyValueWrite("start an object");
/*  261 */     JsonWriteContext ctxt = this._writeContext.createChildObjectContext();
/*  262 */     this._writeContext = ctxt;
/*  263 */     if (forValue != null) {
/*  264 */       ctxt.setCurrentValue(forValue);
/*      */     }
/*  266 */     if (this._cfgPrettyPrinter != null) {
/*  267 */       this._cfgPrettyPrinter.writeStartObject((JsonGenerator)this);
/*      */     } else {
/*  269 */       if (this._outputTail >= this._outputEnd) {
/*  270 */         _flushBuffer();
/*      */       }
/*  272 */       this._outputBuffer[this._outputTail++] = '{';
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartObject() throws IOException {
/*  279 */     _verifyValueWrite("start an object");
/*  280 */     this._writeContext = this._writeContext.createChildObjectContext();
/*  281 */     if (this._cfgPrettyPrinter != null) {
/*  282 */       this._cfgPrettyPrinter.writeStartObject((JsonGenerator)this);
/*      */     } else {
/*  284 */       if (this._outputTail >= this._outputEnd) {
/*  285 */         _flushBuffer();
/*      */       }
/*  287 */       this._outputBuffer[this._outputTail++] = '{';
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEndObject() throws IOException {
/*  294 */     if (!this._writeContext.inObject()) {
/*  295 */       _reportError("Current context not Object but " + this._writeContext.typeDesc());
/*      */     }
/*  297 */     if (this._cfgPrettyPrinter != null) {
/*  298 */       this._cfgPrettyPrinter.writeEndObject((JsonGenerator)this, this._writeContext.getEntryCount());
/*      */     } else {
/*  300 */       if (this._outputTail >= this._outputEnd) {
/*  301 */         _flushBuffer();
/*      */       }
/*  303 */       this._outputBuffer[this._outputTail++] = '}';
/*      */     } 
/*  305 */     this._writeContext = this._writeContext.clearAndGetParent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _writePPFieldName(String name, boolean commaBefore) throws IOException {
/*  314 */     if (commaBefore) {
/*  315 */       this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
/*      */     } else {
/*  317 */       this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
/*      */     } 
/*      */     
/*  320 */     if (this._cfgUnqNames) {
/*  321 */       _writeString(name);
/*      */     } else {
/*  323 */       if (this._outputTail >= this._outputEnd) {
/*  324 */         _flushBuffer();
/*      */       }
/*  326 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  327 */       _writeString(name);
/*  328 */       if (this._outputTail >= this._outputEnd) {
/*  329 */         _flushBuffer();
/*      */       }
/*  331 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _writePPFieldName(SerializableString name, boolean commaBefore) throws IOException {
/*  337 */     if (commaBefore) {
/*  338 */       this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
/*      */     } else {
/*  340 */       this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
/*      */     } 
/*      */     
/*  343 */     char[] quoted = name.asQuotedChars();
/*  344 */     if (this._cfgUnqNames) {
/*  345 */       writeRaw(quoted, 0, quoted.length);
/*      */     } else {
/*  347 */       if (this._outputTail >= this._outputEnd) {
/*  348 */         _flushBuffer();
/*      */       }
/*  350 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  351 */       writeRaw(quoted, 0, quoted.length);
/*  352 */       if (this._outputTail >= this._outputEnd) {
/*  353 */         _flushBuffer();
/*      */       }
/*  355 */       this._outputBuffer[this._outputTail++] = this._quoteChar;
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
/*  368 */     _verifyValueWrite("write a string");
/*  369 */     if (text == null) {
/*  370 */       _writeNull();
/*      */       return;
/*      */     } 
/*  373 */     if (this._outputTail >= this._outputEnd) {
/*  374 */       _flushBuffer();
/*      */     }
/*  376 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  377 */     _writeString(text);
/*      */     
/*  379 */     if (this._outputTail >= this._outputEnd) {
/*  380 */       _flushBuffer();
/*      */     }
/*  382 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(Reader reader, int len) throws IOException {
/*  387 */     _verifyValueWrite("write a string");
/*  388 */     if (reader == null) {
/*  389 */       _reportError("null reader");
/*      */     }
/*  391 */     int toRead = (len >= 0) ? len : Integer.MAX_VALUE;
/*  392 */     char[] buf = _allocateCopyBuffer();
/*      */     
/*  394 */     if (this._outputTail + len >= this._outputEnd) {
/*  395 */       _flushBuffer();
/*      */     }
/*  397 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */ 
/*      */     
/*  400 */     while (toRead > 0) {
/*  401 */       int toReadNow = Math.min(toRead, buf.length);
/*  402 */       int numRead = reader.read(buf, 0, toReadNow);
/*  403 */       if (numRead <= 0) {
/*      */         break;
/*      */       }
/*  406 */       if (this._outputTail + len >= this._outputEnd) {
/*  407 */         _flushBuffer();
/*      */       }
/*  409 */       _writeString(buf, 0, numRead);
/*      */ 
/*      */       
/*  412 */       toRead -= numRead;
/*      */     } 
/*      */     
/*  415 */     if (this._outputTail + len >= this._outputEnd) {
/*  416 */       _flushBuffer();
/*      */     }
/*  418 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  420 */     if (toRead > 0 && len >= 0) {
/*  421 */       _reportError("Didn't read enough from reader");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeString(char[] text, int offset, int len) throws IOException {
/*  428 */     _verifyValueWrite("write a string");
/*  429 */     if (this._outputTail >= this._outputEnd) {
/*  430 */       _flushBuffer();
/*      */     }
/*  432 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  433 */     _writeString(text, offset, len);
/*      */     
/*  435 */     if (this._outputTail >= this._outputEnd) {
/*  436 */       _flushBuffer();
/*      */     }
/*  438 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeString(SerializableString sstr) throws IOException {
/*  444 */     _verifyValueWrite("write a string");
/*  445 */     if (this._outputTail >= this._outputEnd) {
/*  446 */       _flushBuffer();
/*      */     }
/*  448 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */     
/*  450 */     char[] text = sstr.asQuotedChars();
/*  451 */     int len = text.length;
/*      */     
/*  453 */     if (len < 32) {
/*  454 */       int room = this._outputEnd - this._outputTail;
/*  455 */       if (len > room) {
/*  456 */         _flushBuffer();
/*      */       }
/*  458 */       System.arraycopy(text, 0, this._outputBuffer, this._outputTail, len);
/*  459 */       this._outputTail += len;
/*      */     } else {
/*      */       
/*  462 */       _flushBuffer();
/*  463 */       this._writer.write(text, 0, len);
/*      */     } 
/*  465 */     if (this._outputTail >= this._outputEnd) {
/*  466 */       _flushBuffer();
/*      */     }
/*  468 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
/*  474 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
/*  480 */     _reportUnsupportedOperation();
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
/*      */   public void writeRaw(String text) throws IOException {
/*  493 */     int len = text.length();
/*  494 */     int room = this._outputEnd - this._outputTail;
/*      */     
/*  496 */     if (room == 0) {
/*  497 */       _flushBuffer();
/*  498 */       room = this._outputEnd - this._outputTail;
/*      */     } 
/*      */     
/*  501 */     if (room >= len) {
/*  502 */       text.getChars(0, len, this._outputBuffer, this._outputTail);
/*  503 */       this._outputTail += len;
/*      */     } else {
/*  505 */       writeRawLong(text);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(String text, int start, int len) throws IOException {
/*  513 */     int room = this._outputEnd - this._outputTail;
/*      */     
/*  515 */     if (room < len) {
/*  516 */       _flushBuffer();
/*  517 */       room = this._outputEnd - this._outputTail;
/*      */     } 
/*      */     
/*  520 */     if (room >= len) {
/*  521 */       text.getChars(start, start + len, this._outputBuffer, this._outputTail);
/*  522 */       this._outputTail += len;
/*      */     } else {
/*  524 */       writeRawLong(text.substring(start, start + len));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(SerializableString text) throws IOException {
/*  531 */     writeRaw(text.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(char[] text, int offset, int len) throws IOException {
/*  538 */     if (len < 32) {
/*  539 */       int room = this._outputEnd - this._outputTail;
/*  540 */       if (len > room) {
/*  541 */         _flushBuffer();
/*      */       }
/*  543 */       System.arraycopy(text, offset, this._outputBuffer, this._outputTail, len);
/*  544 */       this._outputTail += len;
/*      */       
/*      */       return;
/*      */     } 
/*  548 */     _flushBuffer();
/*  549 */     this._writer.write(text, offset, len);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(char c) throws IOException {
/*  555 */     if (this._outputTail >= this._outputEnd) {
/*  556 */       _flushBuffer();
/*      */     }
/*  558 */     this._outputBuffer[this._outputTail++] = c;
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeRawLong(String text) throws IOException {
/*  563 */     int room = this._outputEnd - this._outputTail;
/*      */     
/*  565 */     text.getChars(0, room, this._outputBuffer, this._outputTail);
/*  566 */     this._outputTail += room;
/*  567 */     _flushBuffer();
/*  568 */     int offset = room;
/*  569 */     int len = text.length() - room;
/*      */     
/*  571 */     while (len > this._outputEnd) {
/*  572 */       int amount = this._outputEnd;
/*  573 */       text.getChars(offset, offset + amount, this._outputBuffer, 0);
/*  574 */       this._outputHead = 0;
/*  575 */       this._outputTail = amount;
/*  576 */       _flushBuffer();
/*  577 */       offset += amount;
/*  578 */       len -= amount;
/*      */     } 
/*      */     
/*  581 */     text.getChars(offset, offset + len, this._outputBuffer, 0);
/*  582 */     this._outputHead = 0;
/*  583 */     this._outputTail = len;
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
/*      */   public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException, JsonGenerationException {
/*  596 */     _verifyValueWrite("write a binary value");
/*      */     
/*  598 */     if (this._outputTail >= this._outputEnd) {
/*  599 */       _flushBuffer();
/*      */     }
/*  601 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  602 */     _writeBinary(b64variant, data, offset, offset + len);
/*      */     
/*  604 */     if (this._outputTail >= this._outputEnd) {
/*  605 */       _flushBuffer();
/*      */     }
/*  607 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) throws IOException, JsonGenerationException {
/*      */     int bytes;
/*  615 */     _verifyValueWrite("write a binary value");
/*      */     
/*  617 */     if (this._outputTail >= this._outputEnd) {
/*  618 */       _flushBuffer();
/*      */     }
/*  620 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  621 */     byte[] encodingBuffer = this._ioContext.allocBase64Buffer();
/*      */     
/*      */     try {
/*  624 */       if (dataLength < 0) {
/*  625 */         bytes = _writeBinary(b64variant, data, encodingBuffer);
/*      */       } else {
/*  627 */         int missing = _writeBinary(b64variant, data, encodingBuffer, dataLength);
/*  628 */         if (missing > 0) {
/*  629 */           _reportError("Too few bytes available: missing " + missing + " bytes (out of " + dataLength + ")");
/*      */         }
/*  631 */         bytes = dataLength;
/*      */       } 
/*      */     } finally {
/*  634 */       this._ioContext.releaseBase64Buffer(encodingBuffer);
/*      */     } 
/*      */     
/*  637 */     if (this._outputTail >= this._outputEnd) {
/*  638 */       _flushBuffer();
/*      */     }
/*  640 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  641 */     return bytes;
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
/*  653 */     _verifyValueWrite("write a number");
/*  654 */     if (this._cfgNumbersAsStrings) {
/*  655 */       _writeQuotedShort(s);
/*      */       
/*      */       return;
/*      */     } 
/*  659 */     if (this._outputTail + 6 >= this._outputEnd) {
/*  660 */       _flushBuffer();
/*      */     }
/*  662 */     this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedShort(short s) throws IOException {
/*  666 */     if (this._outputTail + 8 >= this._outputEnd) {
/*  667 */       _flushBuffer();
/*      */     }
/*  669 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  670 */     this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
/*  671 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(int i) throws IOException {
/*  677 */     _verifyValueWrite("write a number");
/*  678 */     if (this._cfgNumbersAsStrings) {
/*  679 */       _writeQuotedInt(i);
/*      */       
/*      */       return;
/*      */     } 
/*  683 */     if (this._outputTail + 11 >= this._outputEnd) {
/*  684 */       _flushBuffer();
/*      */     }
/*  686 */     this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedInt(int i) throws IOException {
/*  690 */     if (this._outputTail + 13 >= this._outputEnd) {
/*  691 */       _flushBuffer();
/*      */     }
/*  693 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  694 */     this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
/*  695 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(long l) throws IOException {
/*  701 */     _verifyValueWrite("write a number");
/*  702 */     if (this._cfgNumbersAsStrings) {
/*  703 */       _writeQuotedLong(l);
/*      */       return;
/*      */     } 
/*  706 */     if (this._outputTail + 21 >= this._outputEnd)
/*      */     {
/*  708 */       _flushBuffer();
/*      */     }
/*  710 */     this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedLong(long l) throws IOException {
/*  714 */     if (this._outputTail + 23 >= this._outputEnd) {
/*  715 */       _flushBuffer();
/*      */     }
/*  717 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  718 */     this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
/*  719 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(BigInteger value) throws IOException {
/*  727 */     _verifyValueWrite("write a number");
/*  728 */     if (value == null) {
/*  729 */       _writeNull();
/*  730 */     } else if (this._cfgNumbersAsStrings) {
/*  731 */       _writeQuotedRaw(value.toString());
/*      */     } else {
/*  733 */       writeRaw(value.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(double d) throws IOException {
/*  741 */     if (this._cfgNumbersAsStrings || (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Double.isNaN(d) || Double.isInfinite(d)))) {
/*      */ 
/*      */       
/*  744 */       writeString(String.valueOf(d));
/*      */       
/*      */       return;
/*      */     } 
/*  748 */     _verifyValueWrite("write a number");
/*  749 */     writeRaw(String.valueOf(d));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(float f) throws IOException {
/*  755 */     if (this._cfgNumbersAsStrings || (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Float.isNaN(f) || Float.isInfinite(f)))) {
/*      */ 
/*      */       
/*  758 */       writeString(String.valueOf(f));
/*      */       
/*      */       return;
/*      */     } 
/*  762 */     _verifyValueWrite("write a number");
/*  763 */     writeRaw(String.valueOf(f));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(BigDecimal value) throws IOException {
/*  770 */     _verifyValueWrite("write a number");
/*  771 */     if (value == null) {
/*  772 */       _writeNull();
/*  773 */     } else if (this._cfgNumbersAsStrings) {
/*  774 */       _writeQuotedRaw(_asString(value));
/*      */     } else {
/*  776 */       writeRaw(_asString(value));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(String encodedValue) throws IOException {
/*  783 */     _verifyValueWrite("write a number");
/*  784 */     if (this._cfgNumbersAsStrings) {
/*  785 */       _writeQuotedRaw(encodedValue);
/*      */     } else {
/*  787 */       writeRaw(encodedValue);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void _writeQuotedRaw(String value) throws IOException {
/*  793 */     if (this._outputTail >= this._outputEnd) {
/*  794 */       _flushBuffer();
/*      */     }
/*  796 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*  797 */     writeRaw(value);
/*  798 */     if (this._outputTail >= this._outputEnd) {
/*  799 */       _flushBuffer();
/*      */     }
/*  801 */     this._outputBuffer[this._outputTail++] = this._quoteChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBoolean(boolean state) throws IOException {
/*  807 */     _verifyValueWrite("write a boolean value");
/*  808 */     if (this._outputTail + 5 >= this._outputEnd) {
/*  809 */       _flushBuffer();
/*      */     }
/*  811 */     int ptr = this._outputTail;
/*  812 */     char[] buf = this._outputBuffer;
/*  813 */     if (state) {
/*  814 */       buf[ptr] = 't';
/*  815 */       buf[++ptr] = 'r';
/*  816 */       buf[++ptr] = 'u';
/*  817 */       buf[++ptr] = 'e';
/*      */     } else {
/*  819 */       buf[ptr] = 'f';
/*  820 */       buf[++ptr] = 'a';
/*  821 */       buf[++ptr] = 'l';
/*  822 */       buf[++ptr] = 's';
/*  823 */       buf[++ptr] = 'e';
/*      */     } 
/*  825 */     this._outputTail = ptr + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNull() throws IOException {
/*  830 */     _verifyValueWrite("write a null");
/*  831 */     _writeNull();
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
/*      */     char c;
/*  843 */     int status = this._writeContext.writeValue();
/*  844 */     if (this._cfgPrettyPrinter != null) {
/*      */       
/*  846 */       _verifyPrettyValueWrite(typeMsg, status);
/*      */       
/*      */       return;
/*      */     } 
/*  850 */     switch (status) {
/*      */       default:
/*      */         return;
/*      */       
/*      */       case 1:
/*  855 */         c = ',';
/*      */         break;
/*      */       case 2:
/*  858 */         c = ':';
/*      */         break;
/*      */       case 3:
/*  861 */         if (this._rootValueSeparator != null) {
/*  862 */           writeRaw(this._rootValueSeparator.getValue());
/*      */         }
/*      */         return;
/*      */       case 5:
/*  866 */         _reportCantWriteValueExpectName(typeMsg);
/*      */         return;
/*      */     } 
/*  869 */     if (this._outputTail >= this._outputEnd) {
/*  870 */       _flushBuffer();
/*      */     }
/*  872 */     this._outputBuffer[this._outputTail++] = c;
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
/*  884 */     _flushBuffer();
/*  885 */     if (this._writer != null && 
/*  886 */       isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/*  887 */       this._writer.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  895 */     super.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  901 */     if (this._outputBuffer != null && isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
/*      */       while (true) {
/*      */         
/*  904 */         JsonStreamContext ctxt = getOutputContext();
/*  905 */         if (ctxt.inArray()) {
/*  906 */           writeEndArray(); continue;
/*  907 */         }  if (ctxt.inObject()) {
/*  908 */           writeEndObject();
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     }
/*  914 */     _flushBuffer();
/*  915 */     this._outputHead = 0;
/*  916 */     this._outputTail = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  924 */     if (this._writer != null) {
/*  925 */       if (this._ioContext.isResourceManaged() || isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
/*  926 */         this._writer.close();
/*  927 */       } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/*      */         
/*  929 */         this._writer.flush();
/*      */       } 
/*      */     }
/*      */     
/*  933 */     _releaseBuffers();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _releaseBuffers() {
/*  939 */     char[] buf = this._outputBuffer;
/*  940 */     if (buf != null) {
/*  941 */       this._outputBuffer = null;
/*  942 */       this._ioContext.releaseConcatBuffer(buf);
/*      */     } 
/*  944 */     buf = this._charBuffer;
/*  945 */     if (buf != null) {
/*  946 */       this._charBuffer = null;
/*  947 */       this._ioContext.releaseNameCopyBuffer(buf);
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
/*      */   private void _writeString(String text) throws IOException {
/*  964 */     int len = text.length();
/*  965 */     if (len > this._outputEnd) {
/*  966 */       _writeLongString(text);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  972 */     if (this._outputTail + len > this._outputEnd) {
/*  973 */       _flushBuffer();
/*      */     }
/*  975 */     text.getChars(0, len, this._outputBuffer, this._outputTail);
/*      */     
/*  977 */     if (this._characterEscapes != null) {
/*  978 */       _writeStringCustom(len);
/*  979 */     } else if (this._maximumNonEscapedChar != 0) {
/*  980 */       _writeStringASCII(len, this._maximumNonEscapedChar);
/*      */     } else {
/*  982 */       _writeString2(len);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeString2(int len) throws IOException {
/*  989 */     int end = this._outputTail + len;
/*  990 */     int[] escCodes = this._outputEscapes;
/*  991 */     int escLen = escCodes.length;
/*      */ 
/*      */     
/*  994 */     while (this._outputTail < end) {
/*      */       
/*      */       label17: while (true) {
/*      */         
/*  998 */         char c = this._outputBuffer[this._outputTail];
/*  999 */         if (c < escLen && escCodes[c] != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1011 */           int flushLen = this._outputTail - this._outputHead;
/* 1012 */           if (flushLen > 0) {
/* 1013 */             this._writer.write(this._outputBuffer, this._outputHead, flushLen);
/*      */             
/*      */             break label17;
/*      */           } 
/*      */           
/* 1018 */           char c1 = this._outputBuffer[this._outputTail++];
/* 1019 */           _prependOrWriteCharacterEscape(c1, escCodes[c1]);
/*      */           continue;
/*      */         } 
/*      */         if (++this._outputTail >= end) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void _writeLongString(String text) throws IOException {
/* 1030 */     _flushBuffer();
/*      */ 
/*      */     
/* 1033 */     int textLen = text.length();
/* 1034 */     int offset = 0;
/*      */     do {
/* 1036 */       int max = this._outputEnd;
/* 1037 */       int segmentLen = (offset + max > textLen) ? (textLen - offset) : max;
/*      */       
/* 1039 */       text.getChars(offset, offset + segmentLen, this._outputBuffer, 0);
/* 1040 */       if (this._characterEscapes != null) {
/* 1041 */         _writeSegmentCustom(segmentLen);
/* 1042 */       } else if (this._maximumNonEscapedChar != 0) {
/* 1043 */         _writeSegmentASCII(segmentLen, this._maximumNonEscapedChar);
/*      */       } else {
/* 1045 */         _writeSegment(segmentLen);
/*      */       } 
/* 1047 */       offset += segmentLen;
/* 1048 */     } while (offset < textLen);
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
/*      */   private void _writeSegment(int end) throws IOException {
/* 1062 */     int[] escCodes = this._outputEscapes;
/* 1063 */     int escLen = escCodes.length;
/*      */     
/* 1065 */     int ptr = 0;
/* 1066 */     int start = ptr;
/*      */ 
/*      */     
/* 1069 */     while (ptr < end) {
/*      */       char c;
/*      */       
/*      */       do {
/* 1073 */         c = this._outputBuffer[ptr];
/* 1074 */         if (c < escLen && escCodes[c] != 0) {
/*      */           break;
/*      */         }
/* 1077 */       } while (++ptr < end);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1086 */       int flushLen = ptr - start;
/* 1087 */       if (flushLen > 0) {
/* 1088 */         this._writer.write(this._outputBuffer, start, flushLen);
/* 1089 */         if (ptr >= end) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1093 */       ptr++;
/*      */       
/* 1095 */       start = _prependOrWriteCharacterEscape(this._outputBuffer, ptr, end, c, escCodes[c]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeString(char[] text, int offset, int len) throws IOException {
/* 1105 */     if (this._characterEscapes != null) {
/* 1106 */       _writeStringCustom(text, offset, len);
/*      */       return;
/*      */     } 
/* 1109 */     if (this._maximumNonEscapedChar != 0) {
/* 1110 */       _writeStringASCII(text, offset, len, this._maximumNonEscapedChar);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1118 */     len += offset;
/* 1119 */     int[] escCodes = this._outputEscapes;
/* 1120 */     int escLen = escCodes.length;
/* 1121 */     while (offset < len) {
/* 1122 */       int start = offset;
/*      */       
/*      */       do {
/* 1125 */         char c1 = text[offset];
/* 1126 */         if (c1 < escLen && escCodes[c1] != 0) {
/*      */           break;
/*      */         }
/* 1129 */       } while (++offset < len);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1135 */       int newAmount = offset - start;
/* 1136 */       if (newAmount < 32) {
/*      */         
/* 1138 */         if (this._outputTail + newAmount > this._outputEnd) {
/* 1139 */           _flushBuffer();
/*      */         }
/* 1141 */         if (newAmount > 0) {
/* 1142 */           System.arraycopy(text, start, this._outputBuffer, this._outputTail, newAmount);
/* 1143 */           this._outputTail += newAmount;
/*      */         } 
/*      */       } else {
/* 1146 */         _flushBuffer();
/* 1147 */         this._writer.write(text, start, newAmount);
/*      */       } 
/*      */       
/* 1150 */       if (offset >= len) {
/*      */         break;
/*      */       }
/*      */       
/* 1154 */       char c = text[offset++];
/* 1155 */       _appendCharacterEscape(c, escCodes[c]);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeStringASCII(int len, int maxNonEscaped) throws IOException, JsonGenerationException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _outputTail : I
/*      */     //   4: iload_1
/*      */     //   5: iadd
/*      */     //   6: istore_3
/*      */     //   7: aload_0
/*      */     //   8: getfield _outputEscapes : [I
/*      */     //   11: astore #4
/*      */     //   13: aload #4
/*      */     //   15: arraylength
/*      */     //   16: iload_2
/*      */     //   17: iconst_1
/*      */     //   18: iadd
/*      */     //   19: invokestatic min : (II)I
/*      */     //   22: istore #5
/*      */     //   24: iconst_0
/*      */     //   25: istore #6
/*      */     //   27: aload_0
/*      */     //   28: getfield _outputTail : I
/*      */     //   31: iload_3
/*      */     //   32: if_icmpge -> 152
/*      */     //   35: aload_0
/*      */     //   36: getfield _outputBuffer : [C
/*      */     //   39: aload_0
/*      */     //   40: getfield _outputTail : I
/*      */     //   43: caload
/*      */     //   44: istore #7
/*      */     //   46: iload #7
/*      */     //   48: iload #5
/*      */     //   50: if_icmpge -> 68
/*      */     //   53: aload #4
/*      */     //   55: iload #7
/*      */     //   57: iaload
/*      */     //   58: istore #6
/*      */     //   60: iload #6
/*      */     //   62: ifeq -> 80
/*      */     //   65: goto -> 98
/*      */     //   68: iload #7
/*      */     //   70: iload_2
/*      */     //   71: if_icmple -> 80
/*      */     //   74: iconst_m1
/*      */     //   75: istore #6
/*      */     //   77: goto -> 98
/*      */     //   80: aload_0
/*      */     //   81: dup
/*      */     //   82: getfield _outputTail : I
/*      */     //   85: iconst_1
/*      */     //   86: iadd
/*      */     //   87: dup_x1
/*      */     //   88: putfield _outputTail : I
/*      */     //   91: iload_3
/*      */     //   92: if_icmplt -> 35
/*      */     //   95: goto -> 152
/*      */     //   98: aload_0
/*      */     //   99: getfield _outputTail : I
/*      */     //   102: aload_0
/*      */     //   103: getfield _outputHead : I
/*      */     //   106: isub
/*      */     //   107: istore #8
/*      */     //   109: iload #8
/*      */     //   111: ifle -> 131
/*      */     //   114: aload_0
/*      */     //   115: getfield _writer : Ljava/io/Writer;
/*      */     //   118: aload_0
/*      */     //   119: getfield _outputBuffer : [C
/*      */     //   122: aload_0
/*      */     //   123: getfield _outputHead : I
/*      */     //   126: iload #8
/*      */     //   128: invokevirtual write : ([CII)V
/*      */     //   131: aload_0
/*      */     //   132: dup
/*      */     //   133: getfield _outputTail : I
/*      */     //   136: iconst_1
/*      */     //   137: iadd
/*      */     //   138: putfield _outputTail : I
/*      */     //   141: aload_0
/*      */     //   142: iload #7
/*      */     //   144: iload #6
/*      */     //   146: invokespecial _prependOrWriteCharacterEscape : (CI)V
/*      */     //   149: goto -> 27
/*      */     //   152: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1173	-> 0
/*      */     //   #1174	-> 7
/*      */     //   #1175	-> 13
/*      */     //   #1176	-> 24
/*      */     //   #1179	-> 27
/*      */     //   #1184	-> 35
/*      */     //   #1185	-> 46
/*      */     //   #1186	-> 53
/*      */     //   #1187	-> 60
/*      */     //   #1188	-> 65
/*      */     //   #1190	-> 68
/*      */     //   #1191	-> 74
/*      */     //   #1192	-> 77
/*      */     //   #1194	-> 80
/*      */     //   #1195	-> 95
/*      */     //   #1198	-> 98
/*      */     //   #1199	-> 109
/*      */     //   #1200	-> 114
/*      */     //   #1202	-> 131
/*      */     //   #1203	-> 141
/*      */     //   #1204	-> 149
/*      */     //   #1205	-> 152
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   46	103	7	c	C
/*      */     //   109	40	8	flushLen	I
/*      */     //   0	153	0	this	Lsoftware/bernie/shadowed/fasterxml/jackson/core/json/WriterBasedJsonGenerator;
/*      */     //   0	153	1	len	I
/*      */     //   0	153	2	maxNonEscaped	I
/*      */     //   7	146	3	end	I
/*      */     //   13	140	4	escCodes	[I
/*      */     //   24	129	5	escLimit	I
/*      */     //   27	126	6	escCode	I
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
/*      */   private void _writeSegmentASCII(int end, int maxNonEscaped) throws IOException, JsonGenerationException {
/* 1210 */     int[] escCodes = this._outputEscapes;
/* 1211 */     int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
/*      */     
/* 1213 */     int ptr = 0;
/* 1214 */     int escCode = 0;
/* 1215 */     int start = ptr;
/*      */ 
/*      */     
/* 1218 */     while (ptr < end) {
/*      */       char c;
/*      */       
/*      */       do {
/* 1222 */         c = this._outputBuffer[ptr];
/* 1223 */         if (c < escLimit) {
/* 1224 */           escCode = escCodes[c];
/* 1225 */           if (escCode != 0) {
/*      */             break;
/*      */           }
/* 1228 */         } else if (c > maxNonEscaped) {
/* 1229 */           escCode = -1;
/*      */           break;
/*      */         } 
/* 1232 */       } while (++ptr < end);
/*      */ 
/*      */ 
/*      */       
/* 1236 */       int flushLen = ptr - start;
/* 1237 */       if (flushLen > 0) {
/* 1238 */         this._writer.write(this._outputBuffer, start, flushLen);
/* 1239 */         if (ptr >= end) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1243 */       ptr++;
/* 1244 */       start = _prependOrWriteCharacterEscape(this._outputBuffer, ptr, end, c, escCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeStringASCII(char[] text, int offset, int len, int maxNonEscaped) throws IOException, JsonGenerationException {
/* 1252 */     len += offset;
/* 1253 */     int[] escCodes = this._outputEscapes;
/* 1254 */     int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
/*      */     
/* 1256 */     int escCode = 0;
/*      */     
/* 1258 */     while (offset < len) {
/* 1259 */       char c; int start = offset;
/*      */ 
/*      */       
/*      */       do {
/* 1263 */         c = text[offset];
/* 1264 */         if (c < escLimit) {
/* 1265 */           escCode = escCodes[c];
/* 1266 */           if (escCode != 0) {
/*      */             break;
/*      */           }
/* 1269 */         } else if (c > maxNonEscaped) {
/* 1270 */           escCode = -1;
/*      */           break;
/*      */         } 
/* 1273 */       } while (++offset < len);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1279 */       int newAmount = offset - start;
/* 1280 */       if (newAmount < 32) {
/*      */         
/* 1282 */         if (this._outputTail + newAmount > this._outputEnd) {
/* 1283 */           _flushBuffer();
/*      */         }
/* 1285 */         if (newAmount > 0) {
/* 1286 */           System.arraycopy(text, start, this._outputBuffer, this._outputTail, newAmount);
/* 1287 */           this._outputTail += newAmount;
/*      */         } 
/*      */       } else {
/* 1290 */         _flushBuffer();
/* 1291 */         this._writer.write(text, start, newAmount);
/*      */       } 
/*      */       
/* 1294 */       if (offset >= len) {
/*      */         break;
/*      */       }
/*      */       
/* 1298 */       offset++;
/* 1299 */       _appendCharacterEscape(c, escCode);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeStringCustom(int len) throws IOException, JsonGenerationException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _outputTail : I
/*      */     //   4: iload_1
/*      */     //   5: iadd
/*      */     //   6: istore_2
/*      */     //   7: aload_0
/*      */     //   8: getfield _outputEscapes : [I
/*      */     //   11: astore_3
/*      */     //   12: aload_0
/*      */     //   13: getfield _maximumNonEscapedChar : I
/*      */     //   16: iconst_1
/*      */     //   17: if_icmpge -> 26
/*      */     //   20: ldc_w 65535
/*      */     //   23: goto -> 30
/*      */     //   26: aload_0
/*      */     //   27: getfield _maximumNonEscapedChar : I
/*      */     //   30: istore #4
/*      */     //   32: aload_3
/*      */     //   33: arraylength
/*      */     //   34: iload #4
/*      */     //   36: iconst_1
/*      */     //   37: iadd
/*      */     //   38: invokestatic min : (II)I
/*      */     //   41: istore #5
/*      */     //   43: iconst_0
/*      */     //   44: istore #6
/*      */     //   46: aload_0
/*      */     //   47: getfield _characterEscapes : Lsoftware/bernie/shadowed/fasterxml/jackson/core/io/CharacterEscapes;
/*      */     //   50: astore #7
/*      */     //   52: aload_0
/*      */     //   53: getfield _outputTail : I
/*      */     //   56: iload_2
/*      */     //   57: if_icmpge -> 199
/*      */     //   60: aload_0
/*      */     //   61: getfield _outputBuffer : [C
/*      */     //   64: aload_0
/*      */     //   65: getfield _outputTail : I
/*      */     //   68: caload
/*      */     //   69: istore #8
/*      */     //   71: iload #8
/*      */     //   73: iload #5
/*      */     //   75: if_icmpge -> 92
/*      */     //   78: aload_3
/*      */     //   79: iload #8
/*      */     //   81: iaload
/*      */     //   82: istore #6
/*      */     //   84: iload #6
/*      */     //   86: ifeq -> 127
/*      */     //   89: goto -> 145
/*      */     //   92: iload #8
/*      */     //   94: iload #4
/*      */     //   96: if_icmple -> 105
/*      */     //   99: iconst_m1
/*      */     //   100: istore #6
/*      */     //   102: goto -> 145
/*      */     //   105: aload_0
/*      */     //   106: aload #7
/*      */     //   108: iload #8
/*      */     //   110: invokevirtual getEscapeSequence : (I)Lsoftware/bernie/shadowed/fasterxml/jackson/core/SerializableString;
/*      */     //   113: dup_x1
/*      */     //   114: putfield _currentEscape : Lsoftware/bernie/shadowed/fasterxml/jackson/core/SerializableString;
/*      */     //   117: ifnull -> 127
/*      */     //   120: bipush #-2
/*      */     //   122: istore #6
/*      */     //   124: goto -> 145
/*      */     //   127: aload_0
/*      */     //   128: dup
/*      */     //   129: getfield _outputTail : I
/*      */     //   132: iconst_1
/*      */     //   133: iadd
/*      */     //   134: dup_x1
/*      */     //   135: putfield _outputTail : I
/*      */     //   138: iload_2
/*      */     //   139: if_icmplt -> 60
/*      */     //   142: goto -> 199
/*      */     //   145: aload_0
/*      */     //   146: getfield _outputTail : I
/*      */     //   149: aload_0
/*      */     //   150: getfield _outputHead : I
/*      */     //   153: isub
/*      */     //   154: istore #9
/*      */     //   156: iload #9
/*      */     //   158: ifle -> 178
/*      */     //   161: aload_0
/*      */     //   162: getfield _writer : Ljava/io/Writer;
/*      */     //   165: aload_0
/*      */     //   166: getfield _outputBuffer : [C
/*      */     //   169: aload_0
/*      */     //   170: getfield _outputHead : I
/*      */     //   173: iload #9
/*      */     //   175: invokevirtual write : ([CII)V
/*      */     //   178: aload_0
/*      */     //   179: dup
/*      */     //   180: getfield _outputTail : I
/*      */     //   183: iconst_1
/*      */     //   184: iadd
/*      */     //   185: putfield _outputTail : I
/*      */     //   188: aload_0
/*      */     //   189: iload #8
/*      */     //   191: iload #6
/*      */     //   193: invokespecial _prependOrWriteCharacterEscape : (CI)V
/*      */     //   196: goto -> 52
/*      */     //   199: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1317	-> 0
/*      */     //   #1318	-> 7
/*      */     //   #1319	-> 12
/*      */     //   #1320	-> 32
/*      */     //   #1321	-> 43
/*      */     //   #1322	-> 46
/*      */     //   #1325	-> 52
/*      */     //   #1330	-> 60
/*      */     //   #1331	-> 71
/*      */     //   #1332	-> 78
/*      */     //   #1333	-> 84
/*      */     //   #1334	-> 89
/*      */     //   #1336	-> 92
/*      */     //   #1337	-> 99
/*      */     //   #1338	-> 102
/*      */     //   #1340	-> 105
/*      */     //   #1341	-> 120
/*      */     //   #1342	-> 124
/*      */     //   #1345	-> 127
/*      */     //   #1346	-> 142
/*      */     //   #1349	-> 145
/*      */     //   #1350	-> 156
/*      */     //   #1351	-> 161
/*      */     //   #1353	-> 178
/*      */     //   #1354	-> 188
/*      */     //   #1355	-> 196
/*      */     //   #1356	-> 199
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   71	125	8	c	C
/*      */     //   156	40	9	flushLen	I
/*      */     //   0	200	0	this	Lsoftware/bernie/shadowed/fasterxml/jackson/core/json/WriterBasedJsonGenerator;
/*      */     //   0	200	1	len	I
/*      */     //   7	193	2	end	I
/*      */     //   12	188	3	escCodes	[I
/*      */     //   32	168	4	maxNonEscaped	I
/*      */     //   43	157	5	escLimit	I
/*      */     //   46	154	6	escCode	I
/*      */     //   52	148	7	customEscapes	Lsoftware/bernie/shadowed/fasterxml/jackson/core/io/CharacterEscapes;
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
/*      */   private void _writeSegmentCustom(int end) throws IOException, JsonGenerationException {
/* 1361 */     int[] escCodes = this._outputEscapes;
/* 1362 */     int maxNonEscaped = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
/* 1363 */     int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
/* 1364 */     CharacterEscapes customEscapes = this._characterEscapes;
/*      */     
/* 1366 */     int ptr = 0;
/* 1367 */     int escCode = 0;
/* 1368 */     int start = ptr;
/*      */ 
/*      */     
/* 1371 */     while (ptr < end) {
/*      */       char c;
/*      */       
/*      */       do {
/* 1375 */         c = this._outputBuffer[ptr];
/* 1376 */         if (c < escLimit) {
/* 1377 */           escCode = escCodes[c];
/* 1378 */           if (escCode != 0)
/*      */             break; 
/*      */         } else {
/* 1381 */           if (c > maxNonEscaped) {
/* 1382 */             escCode = -1;
/*      */             break;
/*      */           } 
/* 1385 */           if ((this._currentEscape = customEscapes.getEscapeSequence(c)) != null) {
/* 1386 */             escCode = -2;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1390 */       } while (++ptr < end);
/*      */ 
/*      */ 
/*      */       
/* 1394 */       int flushLen = ptr - start;
/* 1395 */       if (flushLen > 0) {
/* 1396 */         this._writer.write(this._outputBuffer, start, flushLen);
/* 1397 */         if (ptr >= end) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1401 */       ptr++;
/* 1402 */       start = _prependOrWriteCharacterEscape(this._outputBuffer, ptr, end, c, escCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void _writeStringCustom(char[] text, int offset, int len) throws IOException, JsonGenerationException {
/* 1409 */     len += offset;
/* 1410 */     int[] escCodes = this._outputEscapes;
/* 1411 */     int maxNonEscaped = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
/* 1412 */     int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
/* 1413 */     CharacterEscapes customEscapes = this._characterEscapes;
/*      */     
/* 1415 */     int escCode = 0;
/*      */     
/* 1417 */     while (offset < len) {
/* 1418 */       char c; int start = offset;
/*      */ 
/*      */       
/*      */       do {
/* 1422 */         c = text[offset];
/* 1423 */         if (c < escLimit) {
/* 1424 */           escCode = escCodes[c];
/* 1425 */           if (escCode != 0)
/*      */             break; 
/*      */         } else {
/* 1428 */           if (c > maxNonEscaped) {
/* 1429 */             escCode = -1;
/*      */             break;
/*      */           } 
/* 1432 */           if ((this._currentEscape = customEscapes.getEscapeSequence(c)) != null) {
/* 1433 */             escCode = -2;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1437 */       } while (++offset < len);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1443 */       int newAmount = offset - start;
/* 1444 */       if (newAmount < 32) {
/*      */         
/* 1446 */         if (this._outputTail + newAmount > this._outputEnd) {
/* 1447 */           _flushBuffer();
/*      */         }
/* 1449 */         if (newAmount > 0) {
/* 1450 */           System.arraycopy(text, start, this._outputBuffer, this._outputTail, newAmount);
/* 1451 */           this._outputTail += newAmount;
/*      */         } 
/*      */       } else {
/* 1454 */         _flushBuffer();
/* 1455 */         this._writer.write(text, start, newAmount);
/*      */       } 
/*      */       
/* 1458 */       if (offset >= len) {
/*      */         break;
/*      */       }
/*      */       
/* 1462 */       offset++;
/* 1463 */       _appendCharacterEscape(c, escCode);
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
/*      */   protected final void _writeBinary(Base64Variant b64variant, byte[] input, int inputPtr, int inputEnd) throws IOException, JsonGenerationException {
/* 1477 */     int safeInputEnd = inputEnd - 3;
/*      */     
/* 1479 */     int safeOutputEnd = this._outputEnd - 6;
/* 1480 */     int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */ 
/*      */     
/* 1483 */     while (inputPtr <= safeInputEnd) {
/* 1484 */       if (this._outputTail > safeOutputEnd) {
/* 1485 */         _flushBuffer();
/*      */       }
/*      */       
/* 1488 */       int b24 = input[inputPtr++] << 8;
/* 1489 */       b24 |= input[inputPtr++] & 0xFF;
/* 1490 */       b24 = b24 << 8 | input[inputPtr++] & 0xFF;
/* 1491 */       this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
/* 1492 */       if (--chunksBeforeLF <= 0) {
/*      */         
/* 1494 */         this._outputBuffer[this._outputTail++] = '\\';
/* 1495 */         this._outputBuffer[this._outputTail++] = 'n';
/* 1496 */         chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1501 */     int inputLeft = inputEnd - inputPtr;
/* 1502 */     if (inputLeft > 0) {
/* 1503 */       if (this._outputTail > safeOutputEnd) {
/* 1504 */         _flushBuffer();
/*      */       }
/* 1506 */       int b24 = input[inputPtr++] << 16;
/* 1507 */       if (inputLeft == 2) {
/* 1508 */         b24 |= (input[inputPtr++] & 0xFF) << 8;
/*      */       }
/* 1510 */       this._outputTail = b64variant.encodeBase64Partial(b24, inputLeft, this._outputBuffer, this._outputTail);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _writeBinary(Base64Variant b64variant, InputStream data, byte[] readBuffer, int bytesLeft) throws IOException, JsonGenerationException {
/* 1519 */     int inputPtr = 0;
/* 1520 */     int inputEnd = 0;
/* 1521 */     int lastFullOffset = -3;
/*      */ 
/*      */     
/* 1524 */     int safeOutputEnd = this._outputEnd - 6;
/* 1525 */     int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */     
/* 1527 */     while (bytesLeft > 2) {
/* 1528 */       if (inputPtr > lastFullOffset) {
/* 1529 */         inputEnd = _readMore(data, readBuffer, inputPtr, inputEnd, bytesLeft);
/* 1530 */         inputPtr = 0;
/* 1531 */         if (inputEnd < 3) {
/*      */           break;
/*      */         }
/* 1534 */         lastFullOffset = inputEnd - 3;
/*      */       } 
/* 1536 */       if (this._outputTail > safeOutputEnd) {
/* 1537 */         _flushBuffer();
/*      */       }
/* 1539 */       int b24 = readBuffer[inputPtr++] << 8;
/* 1540 */       b24 |= readBuffer[inputPtr++] & 0xFF;
/* 1541 */       b24 = b24 << 8 | readBuffer[inputPtr++] & 0xFF;
/* 1542 */       bytesLeft -= 3;
/* 1543 */       this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
/* 1544 */       if (--chunksBeforeLF <= 0) {
/* 1545 */         this._outputBuffer[this._outputTail++] = '\\';
/* 1546 */         this._outputBuffer[this._outputTail++] = 'n';
/* 1547 */         chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1552 */     if (bytesLeft > 0) {
/* 1553 */       inputEnd = _readMore(data, readBuffer, inputPtr, inputEnd, bytesLeft);
/* 1554 */       inputPtr = 0;
/* 1555 */       if (inputEnd > 0) {
/* 1556 */         int amount; if (this._outputTail > safeOutputEnd) {
/* 1557 */           _flushBuffer();
/*      */         }
/* 1559 */         int b24 = readBuffer[inputPtr++] << 16;
/*      */         
/* 1561 */         if (inputPtr < inputEnd) {
/* 1562 */           b24 |= (readBuffer[inputPtr] & 0xFF) << 8;
/* 1563 */           amount = 2;
/*      */         } else {
/* 1565 */           amount = 1;
/*      */         } 
/* 1567 */         this._outputTail = b64variant.encodeBase64Partial(b24, amount, this._outputBuffer, this._outputTail);
/* 1568 */         bytesLeft -= amount;
/*      */       } 
/*      */     } 
/* 1571 */     return bytesLeft;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int _writeBinary(Base64Variant b64variant, InputStream data, byte[] readBuffer) throws IOException, JsonGenerationException {
/* 1579 */     int inputPtr = 0;
/* 1580 */     int inputEnd = 0;
/* 1581 */     int lastFullOffset = -3;
/* 1582 */     int bytesDone = 0;
/*      */ 
/*      */     
/* 1585 */     int safeOutputEnd = this._outputEnd - 6;
/* 1586 */     int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1590 */       if (inputPtr > lastFullOffset) {
/* 1591 */         inputEnd = _readMore(data, readBuffer, inputPtr, inputEnd, readBuffer.length);
/* 1592 */         inputPtr = 0;
/* 1593 */         if (inputEnd < 3) {
/*      */           break;
/*      */         }
/* 1596 */         lastFullOffset = inputEnd - 3;
/*      */       } 
/* 1598 */       if (this._outputTail > safeOutputEnd) {
/* 1599 */         _flushBuffer();
/*      */       }
/*      */       
/* 1602 */       int b24 = readBuffer[inputPtr++] << 8;
/* 1603 */       b24 |= readBuffer[inputPtr++] & 0xFF;
/* 1604 */       b24 = b24 << 8 | readBuffer[inputPtr++] & 0xFF;
/* 1605 */       bytesDone += 3;
/* 1606 */       this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
/* 1607 */       if (--chunksBeforeLF <= 0) {
/* 1608 */         this._outputBuffer[this._outputTail++] = '\\';
/* 1609 */         this._outputBuffer[this._outputTail++] = 'n';
/* 1610 */         chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1615 */     if (inputPtr < inputEnd) {
/* 1616 */       if (this._outputTail > safeOutputEnd) {
/* 1617 */         _flushBuffer();
/*      */       }
/* 1619 */       int b24 = readBuffer[inputPtr++] << 16;
/* 1620 */       int amount = 1;
/* 1621 */       if (inputPtr < inputEnd) {
/* 1622 */         b24 |= (readBuffer[inputPtr] & 0xFF) << 8;
/* 1623 */         amount = 2;
/*      */       } 
/* 1625 */       bytesDone += amount;
/* 1626 */       this._outputTail = b64variant.encodeBase64Partial(b24, amount, this._outputBuffer, this._outputTail);
/*      */     } 
/* 1628 */     return bytesDone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _readMore(InputStream in, byte[] readBuffer, int inputPtr, int inputEnd, int maxRead) throws IOException {
/* 1636 */     int i = 0;
/* 1637 */     while (inputPtr < inputEnd) {
/* 1638 */       readBuffer[i++] = readBuffer[inputPtr++];
/*      */     }
/* 1640 */     inputPtr = 0;
/* 1641 */     inputEnd = i;
/* 1642 */     maxRead = Math.min(maxRead, readBuffer.length);
/*      */     
/*      */     do {
/* 1645 */       int length = maxRead - inputEnd;
/* 1646 */       if (length == 0) {
/*      */         break;
/*      */       }
/* 1649 */       int count = in.read(readBuffer, inputEnd, length);
/* 1650 */       if (count < 0) {
/* 1651 */         return inputEnd;
/*      */       }
/* 1653 */       inputEnd += count;
/* 1654 */     } while (inputEnd < 3);
/* 1655 */     return inputEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeNull() throws IOException {
/* 1666 */     if (this._outputTail + 4 >= this._outputEnd) {
/* 1667 */       _flushBuffer();
/*      */     }
/* 1669 */     int ptr = this._outputTail;
/* 1670 */     char[] buf = this._outputBuffer;
/* 1671 */     buf[ptr] = 'n';
/* 1672 */     buf[++ptr] = 'u';
/* 1673 */     buf[++ptr] = 'l';
/* 1674 */     buf[++ptr] = 'l';
/* 1675 */     this._outputTail = ptr + 1;
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
/*      */   private void _prependOrWriteCharacterEscape(char ch, int escCode) throws IOException, JsonGenerationException {
/*      */     String escape;
/* 1692 */     if (escCode >= 0) {
/* 1693 */       if (this._outputTail >= 2) {
/* 1694 */         int ptr = this._outputTail - 2;
/* 1695 */         this._outputHead = ptr;
/* 1696 */         this._outputBuffer[ptr++] = '\\';
/* 1697 */         this._outputBuffer[ptr] = (char)escCode;
/*      */         
/*      */         return;
/*      */       } 
/* 1701 */       char[] buf = this._entityBuffer;
/* 1702 */       if (buf == null) {
/* 1703 */         buf = _allocateEntityBuffer();
/*      */       }
/* 1705 */       this._outputHead = this._outputTail;
/* 1706 */       buf[1] = (char)escCode;
/* 1707 */       this._writer.write(buf, 0, 2);
/*      */       return;
/*      */     } 
/* 1710 */     if (escCode != -2) {
/* 1711 */       if (this._outputTail >= 6) {
/* 1712 */         char[] arrayOfChar = this._outputBuffer;
/* 1713 */         int ptr = this._outputTail - 6;
/* 1714 */         this._outputHead = ptr;
/* 1715 */         arrayOfChar[ptr] = '\\';
/* 1716 */         arrayOfChar[++ptr] = 'u';
/*      */         
/* 1718 */         if (ch > 'ÿ') {
/* 1719 */           int hi = ch >> 8 & 0xFF;
/* 1720 */           arrayOfChar[++ptr] = HEX_CHARS[hi >> 4];
/* 1721 */           arrayOfChar[++ptr] = HEX_CHARS[hi & 0xF];
/* 1722 */           ch = (char)(ch & 0xFF);
/*      */         } else {
/* 1724 */           arrayOfChar[++ptr] = '0';
/* 1725 */           arrayOfChar[++ptr] = '0';
/*      */         } 
/* 1727 */         arrayOfChar[++ptr] = HEX_CHARS[ch >> 4];
/* 1728 */         arrayOfChar[++ptr] = HEX_CHARS[ch & 0xF];
/*      */         
/*      */         return;
/*      */       } 
/* 1732 */       char[] buf = this._entityBuffer;
/* 1733 */       if (buf == null) {
/* 1734 */         buf = _allocateEntityBuffer();
/*      */       }
/* 1736 */       this._outputHead = this._outputTail;
/* 1737 */       if (ch > 'ÿ') {
/* 1738 */         int hi = ch >> 8 & 0xFF;
/* 1739 */         int lo = ch & 0xFF;
/* 1740 */         buf[10] = HEX_CHARS[hi >> 4];
/* 1741 */         buf[11] = HEX_CHARS[hi & 0xF];
/* 1742 */         buf[12] = HEX_CHARS[lo >> 4];
/* 1743 */         buf[13] = HEX_CHARS[lo & 0xF];
/* 1744 */         this._writer.write(buf, 8, 6);
/*      */       } else {
/* 1746 */         buf[6] = HEX_CHARS[ch >> 4];
/* 1747 */         buf[7] = HEX_CHARS[ch & 0xF];
/* 1748 */         this._writer.write(buf, 2, 6);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1754 */     if (this._currentEscape == null) {
/* 1755 */       escape = this._characterEscapes.getEscapeSequence(ch).getValue();
/*      */     } else {
/* 1757 */       escape = this._currentEscape.getValue();
/* 1758 */       this._currentEscape = null;
/*      */     } 
/* 1760 */     int len = escape.length();
/* 1761 */     if (this._outputTail >= len) {
/* 1762 */       int ptr = this._outputTail - len;
/* 1763 */       this._outputHead = ptr;
/* 1764 */       escape.getChars(0, len, this._outputBuffer, ptr);
/*      */       
/*      */       return;
/*      */     } 
/* 1768 */     this._outputHead = this._outputTail;
/* 1769 */     this._writer.write(escape);
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
/*      */   private int _prependOrWriteCharacterEscape(char[] buffer, int ptr, int end, char ch, int escCode) throws IOException, JsonGenerationException {
/*      */     String escape;
/* 1783 */     if (escCode >= 0) {
/* 1784 */       if (ptr > 1 && ptr < end) {
/* 1785 */         ptr -= 2;
/* 1786 */         buffer[ptr] = '\\';
/* 1787 */         buffer[ptr + 1] = (char)escCode;
/*      */       } else {
/* 1789 */         char[] ent = this._entityBuffer;
/* 1790 */         if (ent == null) {
/* 1791 */           ent = _allocateEntityBuffer();
/*      */         }
/* 1793 */         ent[1] = (char)escCode;
/* 1794 */         this._writer.write(ent, 0, 2);
/*      */       } 
/* 1796 */       return ptr;
/*      */     } 
/* 1798 */     if (escCode != -2) {
/* 1799 */       if (ptr > 5 && ptr < end) {
/* 1800 */         ptr -= 6;
/* 1801 */         buffer[ptr++] = '\\';
/* 1802 */         buffer[ptr++] = 'u';
/*      */         
/* 1804 */         if (ch > 'ÿ') {
/* 1805 */           int hi = ch >> 8 & 0xFF;
/* 1806 */           buffer[ptr++] = HEX_CHARS[hi >> 4];
/* 1807 */           buffer[ptr++] = HEX_CHARS[hi & 0xF];
/* 1808 */           ch = (char)(ch & 0xFF);
/*      */         } else {
/* 1810 */           buffer[ptr++] = '0';
/* 1811 */           buffer[ptr++] = '0';
/*      */         } 
/* 1813 */         buffer[ptr++] = HEX_CHARS[ch >> 4];
/* 1814 */         buffer[ptr] = HEX_CHARS[ch & 0xF];
/* 1815 */         ptr -= 5;
/*      */       } else {
/*      */         
/* 1818 */         char[] ent = this._entityBuffer;
/* 1819 */         if (ent == null) {
/* 1820 */           ent = _allocateEntityBuffer();
/*      */         }
/* 1822 */         this._outputHead = this._outputTail;
/* 1823 */         if (ch > 'ÿ') {
/* 1824 */           int hi = ch >> 8 & 0xFF;
/* 1825 */           int lo = ch & 0xFF;
/* 1826 */           ent[10] = HEX_CHARS[hi >> 4];
/* 1827 */           ent[11] = HEX_CHARS[hi & 0xF];
/* 1828 */           ent[12] = HEX_CHARS[lo >> 4];
/* 1829 */           ent[13] = HEX_CHARS[lo & 0xF];
/* 1830 */           this._writer.write(ent, 8, 6);
/*      */         } else {
/* 1832 */           ent[6] = HEX_CHARS[ch >> 4];
/* 1833 */           ent[7] = HEX_CHARS[ch & 0xF];
/* 1834 */           this._writer.write(ent, 2, 6);
/*      */         } 
/*      */       } 
/* 1837 */       return ptr;
/*      */     } 
/*      */     
/* 1840 */     if (this._currentEscape == null) {
/* 1841 */       escape = this._characterEscapes.getEscapeSequence(ch).getValue();
/*      */     } else {
/* 1843 */       escape = this._currentEscape.getValue();
/* 1844 */       this._currentEscape = null;
/*      */     } 
/* 1846 */     int len = escape.length();
/* 1847 */     if (ptr >= len && ptr < end) {
/* 1848 */       ptr -= len;
/* 1849 */       escape.getChars(0, len, buffer, ptr);
/*      */     } else {
/* 1851 */       this._writer.write(escape);
/*      */     } 
/* 1853 */     return ptr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _appendCharacterEscape(char ch, int escCode) throws IOException, JsonGenerationException {
/*      */     String escape;
/* 1863 */     if (escCode >= 0) {
/* 1864 */       if (this._outputTail + 2 > this._outputEnd) {
/* 1865 */         _flushBuffer();
/*      */       }
/* 1867 */       this._outputBuffer[this._outputTail++] = '\\';
/* 1868 */       this._outputBuffer[this._outputTail++] = (char)escCode;
/*      */       return;
/*      */     } 
/* 1871 */     if (escCode != -2) {
/* 1872 */       if (this._outputTail + 5 >= this._outputEnd) {
/* 1873 */         _flushBuffer();
/*      */       }
/* 1875 */       int ptr = this._outputTail;
/* 1876 */       char[] buf = this._outputBuffer;
/* 1877 */       buf[ptr++] = '\\';
/* 1878 */       buf[ptr++] = 'u';
/*      */       
/* 1880 */       if (ch > 'ÿ') {
/* 1881 */         int hi = ch >> 8 & 0xFF;
/* 1882 */         buf[ptr++] = HEX_CHARS[hi >> 4];
/* 1883 */         buf[ptr++] = HEX_CHARS[hi & 0xF];
/* 1884 */         ch = (char)(ch & 0xFF);
/*      */       } else {
/* 1886 */         buf[ptr++] = '0';
/* 1887 */         buf[ptr++] = '0';
/*      */       } 
/* 1889 */       buf[ptr++] = HEX_CHARS[ch >> 4];
/* 1890 */       buf[ptr++] = HEX_CHARS[ch & 0xF];
/* 1891 */       this._outputTail = ptr;
/*      */       
/*      */       return;
/*      */     } 
/* 1895 */     if (this._currentEscape == null) {
/* 1896 */       escape = this._characterEscapes.getEscapeSequence(ch).getValue();
/*      */     } else {
/* 1898 */       escape = this._currentEscape.getValue();
/* 1899 */       this._currentEscape = null;
/*      */     } 
/* 1901 */     int len = escape.length();
/* 1902 */     if (this._outputTail + len > this._outputEnd) {
/* 1903 */       _flushBuffer();
/* 1904 */       if (len > this._outputEnd) {
/* 1905 */         this._writer.write(escape);
/*      */         return;
/*      */       } 
/*      */     } 
/* 1909 */     escape.getChars(0, len, this._outputBuffer, this._outputTail);
/* 1910 */     this._outputTail += len;
/*      */   }
/*      */ 
/*      */   
/*      */   private char[] _allocateEntityBuffer() {
/* 1915 */     char[] buf = new char[14];
/*      */     
/* 1917 */     buf[0] = '\\';
/*      */     
/* 1919 */     buf[2] = '\\';
/* 1920 */     buf[3] = 'u';
/* 1921 */     buf[4] = '0';
/* 1922 */     buf[5] = '0';
/*      */     
/* 1924 */     buf[8] = '\\';
/* 1925 */     buf[9] = 'u';
/* 1926 */     this._entityBuffer = buf;
/* 1927 */     return buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] _allocateCopyBuffer() {
/* 1934 */     if (this._charBuffer == null) {
/* 1935 */       this._charBuffer = this._ioContext.allocNameCopyBuffer(2000);
/*      */     }
/* 1937 */     return this._charBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _flushBuffer() throws IOException {
/* 1942 */     int len = this._outputTail - this._outputHead;
/* 1943 */     if (len > 0) {
/* 1944 */       int offset = this._outputHead;
/* 1945 */       this._outputTail = this._outputHead = 0;
/* 1946 */       this._writer.write(this._outputBuffer, offset, len);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\WriterBasedJsonGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */