/*     */ package software.bernie.shadowed.fasterxml.jackson.core.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.JsonGeneratorDelegate;
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
/*     */ public class FilteringGeneratorDelegate
/*     */   extends JsonGeneratorDelegate
/*     */ {
/*     */   protected TokenFilter rootFilter;
/*     */   protected boolean _allowMultipleMatches;
/*     */   protected boolean _includePath;
/*     */   @Deprecated
/*     */   protected boolean _includeImmediateParent;
/*     */   protected TokenFilterContext _filterContext;
/*     */   protected TokenFilter _itemFilter;
/*     */   protected int _matchCount;
/*     */   
/*     */   public FilteringGeneratorDelegate(JsonGenerator d, TokenFilter f, boolean includePath, boolean allowMultipleMatches) {
/*  96 */     super(d, false);
/*  97 */     this.rootFilter = f;
/*     */     
/*  99 */     this._itemFilter = f;
/* 100 */     this._filterContext = TokenFilterContext.createRootContext(f);
/* 101 */     this._includePath = includePath;
/* 102 */     this._allowMultipleMatches = allowMultipleMatches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenFilter getFilter() {
/* 111 */     return this.rootFilter;
/*     */   }
/*     */   public JsonStreamContext getFilterContext() {
/* 114 */     return this._filterContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMatchCount() {
/* 122 */     return this._matchCount;
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
/*     */   public JsonStreamContext getOutputContext() {
/* 137 */     return this._filterContext;
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
/*     */   public void writeStartArray() throws IOException {
/* 150 */     if (this._itemFilter == null) {
/* 151 */       this._filterContext = this._filterContext.createChildArrayContext(null, false);
/*     */       return;
/*     */     } 
/* 154 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 155 */       this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
/* 156 */       this.delegate.writeStartArray();
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     this._itemFilter = this._filterContext.checkValue(this._itemFilter);
/* 161 */     if (this._itemFilter == null) {
/* 162 */       this._filterContext = this._filterContext.createChildArrayContext(null, false);
/*     */       return;
/*     */     } 
/* 165 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 166 */       this._itemFilter = this._itemFilter.filterStartArray();
/*     */     }
/* 168 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 169 */       _checkParentPath();
/* 170 */       this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
/* 171 */       this.delegate.writeStartArray();
/*     */     } else {
/* 173 */       this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartArray(int size) throws IOException {
/* 180 */     if (this._itemFilter == null) {
/* 181 */       this._filterContext = this._filterContext.createChildArrayContext(null, false);
/*     */       return;
/*     */     } 
/* 184 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 185 */       this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
/* 186 */       this.delegate.writeStartArray(size);
/*     */       return;
/*     */     } 
/* 189 */     this._itemFilter = this._filterContext.checkValue(this._itemFilter);
/* 190 */     if (this._itemFilter == null) {
/* 191 */       this._filterContext = this._filterContext.createChildArrayContext(null, false);
/*     */       return;
/*     */     } 
/* 194 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 195 */       this._itemFilter = this._itemFilter.filterStartArray();
/*     */     }
/* 197 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 198 */       _checkParentPath();
/* 199 */       this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
/* 200 */       this.delegate.writeStartArray(size);
/*     */     } else {
/* 202 */       this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndArray() throws IOException {
/* 209 */     this._filterContext = this._filterContext.closeArray(this.delegate);
/*     */     
/* 211 */     if (this._filterContext != null) {
/* 212 */       this._itemFilter = this._filterContext.getFilter();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartObject() throws IOException {
/* 219 */     if (this._itemFilter == null) {
/* 220 */       this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, false);
/*     */       return;
/*     */     } 
/* 223 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 224 */       this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
/* 225 */       this.delegate.writeStartObject();
/*     */       
/*     */       return;
/*     */     } 
/* 229 */     TokenFilter f = this._filterContext.checkValue(this._itemFilter);
/* 230 */     if (f == null) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     if (f != TokenFilter.INCLUDE_ALL) {
/* 235 */       f = f.filterStartObject();
/*     */     }
/* 237 */     if (f == TokenFilter.INCLUDE_ALL) {
/* 238 */       _checkParentPath();
/* 239 */       this._filterContext = this._filterContext.createChildObjectContext(f, true);
/* 240 */       this.delegate.writeStartObject();
/*     */     } else {
/* 242 */       this._filterContext = this._filterContext.createChildObjectContext(f, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartObject(Object forValue) throws IOException {
/* 249 */     if (this._itemFilter == null) {
/* 250 */       this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, false);
/*     */       return;
/*     */     } 
/* 253 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 254 */       this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
/* 255 */       this.delegate.writeStartObject(forValue);
/*     */       
/*     */       return;
/*     */     } 
/* 259 */     TokenFilter f = this._filterContext.checkValue(this._itemFilter);
/* 260 */     if (f == null) {
/*     */       return;
/*     */     }
/*     */     
/* 264 */     if (f != TokenFilter.INCLUDE_ALL) {
/* 265 */       f = f.filterStartObject();
/*     */     }
/* 267 */     if (f == TokenFilter.INCLUDE_ALL) {
/* 268 */       _checkParentPath();
/* 269 */       this._filterContext = this._filterContext.createChildObjectContext(f, true);
/* 270 */       this.delegate.writeStartObject(forValue);
/*     */     } else {
/* 272 */       this._filterContext = this._filterContext.createChildObjectContext(f, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndObject() throws IOException {
/* 279 */     this._filterContext = this._filterContext.closeObject(this.delegate);
/* 280 */     if (this._filterContext != null) {
/* 281 */       this._itemFilter = this._filterContext.getFilter();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFieldName(String name) throws IOException {
/* 288 */     TokenFilter state = this._filterContext.setFieldName(name);
/* 289 */     if (state == null) {
/* 290 */       this._itemFilter = null;
/*     */       return;
/*     */     } 
/* 293 */     if (state == TokenFilter.INCLUDE_ALL) {
/* 294 */       this._itemFilter = state;
/* 295 */       this.delegate.writeFieldName(name);
/*     */       return;
/*     */     } 
/* 298 */     state = state.includeProperty(name);
/* 299 */     this._itemFilter = state;
/* 300 */     if (state == TokenFilter.INCLUDE_ALL) {
/* 301 */       _checkPropertyParentPath();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFieldName(SerializableString name) throws IOException {
/* 308 */     TokenFilter state = this._filterContext.setFieldName(name.getValue());
/* 309 */     if (state == null) {
/* 310 */       this._itemFilter = null;
/*     */       return;
/*     */     } 
/* 313 */     if (state == TokenFilter.INCLUDE_ALL) {
/* 314 */       this._itemFilter = state;
/* 315 */       this.delegate.writeFieldName(name);
/*     */       return;
/*     */     } 
/* 318 */     state = state.includeProperty(name.getValue());
/* 319 */     this._itemFilter = state;
/* 320 */     if (state == TokenFilter.INCLUDE_ALL) {
/* 321 */       _checkPropertyParentPath();
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
/*     */   public void writeString(String value) throws IOException {
/* 334 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 337 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 338 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 339 */       if (state == null) {
/*     */         return;
/*     */       }
/* 342 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 343 */         !state.includeString(value)) {
/*     */         return;
/*     */       }
/*     */       
/* 347 */       _checkParentPath();
/*     */     } 
/* 349 */     this.delegate.writeString(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeString(char[] text, int offset, int len) throws IOException {
/* 355 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 358 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 359 */       String value = new String(text, offset, len);
/* 360 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 361 */       if (state == null) {
/*     */         return;
/*     */       }
/* 364 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 365 */         !state.includeString(value)) {
/*     */         return;
/*     */       }
/*     */       
/* 369 */       _checkParentPath();
/*     */     } 
/* 371 */     this.delegate.writeString(text, offset, len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeString(SerializableString value) throws IOException {
/* 377 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 380 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 381 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 382 */       if (state == null) {
/*     */         return;
/*     */       }
/* 385 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 386 */         !state.includeString(value.getValue())) {
/*     */         return;
/*     */       }
/*     */       
/* 390 */       _checkParentPath();
/*     */     } 
/* 392 */     this.delegate.writeString(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
/* 398 */     if (_checkRawValueWrite()) {
/* 399 */       this.delegate.writeRawUTF8String(text, offset, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
/* 407 */     if (_checkRawValueWrite()) {
/* 408 */       this.delegate.writeUTF8String(text, offset, length);
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
/*     */   public void writeRaw(String text) throws IOException {
/* 421 */     if (_checkRawValueWrite()) {
/* 422 */       this.delegate.writeRaw(text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRaw(String text, int offset, int len) throws IOException {
/* 429 */     if (_checkRawValueWrite()) {
/* 430 */       this.delegate.writeRaw(text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRaw(SerializableString text) throws IOException {
/* 437 */     if (_checkRawValueWrite()) {
/* 438 */       this.delegate.writeRaw(text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRaw(char[] text, int offset, int len) throws IOException {
/* 445 */     if (_checkRawValueWrite()) {
/* 446 */       this.delegate.writeRaw(text, offset, len);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRaw(char c) throws IOException {
/* 453 */     if (_checkRawValueWrite()) {
/* 454 */       this.delegate.writeRaw(c);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRawValue(String text) throws IOException {
/* 461 */     if (_checkRawValueWrite()) {
/* 462 */       this.delegate.writeRaw(text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRawValue(String text, int offset, int len) throws IOException {
/* 469 */     if (_checkRawValueWrite()) {
/* 470 */       this.delegate.writeRaw(text, offset, len);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRawValue(char[] text, int offset, int len) throws IOException {
/* 477 */     if (_checkRawValueWrite()) {
/* 478 */       this.delegate.writeRaw(text, offset, len);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException {
/* 485 */     if (_checkBinaryWrite()) {
/* 486 */       this.delegate.writeBinary(b64variant, data, offset, len);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) throws IOException {
/* 493 */     if (_checkBinaryWrite()) {
/* 494 */       return this.delegate.writeBinary(b64variant, data, dataLength);
/*     */     }
/* 496 */     return -1;
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
/*     */   public void writeNumber(short v) throws IOException {
/* 508 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 511 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 512 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 513 */       if (state == null) {
/*     */         return;
/*     */       }
/* 516 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 517 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 521 */       _checkParentPath();
/*     */     } 
/* 523 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(int v) throws IOException {
/* 529 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 532 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 533 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 534 */       if (state == null) {
/*     */         return;
/*     */       }
/* 537 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 538 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 542 */       _checkParentPath();
/*     */     } 
/* 544 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(long v) throws IOException {
/* 550 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 553 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 554 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 555 */       if (state == null) {
/*     */         return;
/*     */       }
/* 558 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 559 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 563 */       _checkParentPath();
/*     */     } 
/* 565 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(BigInteger v) throws IOException {
/* 571 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 574 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 575 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 576 */       if (state == null) {
/*     */         return;
/*     */       }
/* 579 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 580 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 584 */       _checkParentPath();
/*     */     } 
/* 586 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(double v) throws IOException {
/* 592 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 595 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 596 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 597 */       if (state == null) {
/*     */         return;
/*     */       }
/* 600 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 601 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 605 */       _checkParentPath();
/*     */     } 
/* 607 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(float v) throws IOException {
/* 613 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 616 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 617 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 618 */       if (state == null) {
/*     */         return;
/*     */       }
/* 621 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 622 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 626 */       _checkParentPath();
/*     */     } 
/* 628 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(BigDecimal v) throws IOException {
/* 634 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 637 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 638 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 639 */       if (state == null) {
/*     */         return;
/*     */       }
/* 642 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 643 */         !state.includeNumber(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 647 */       _checkParentPath();
/*     */     } 
/* 649 */     this.delegate.writeNumber(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(String encodedValue) throws IOException, UnsupportedOperationException {
/* 655 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 658 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 659 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 660 */       if (state == null) {
/*     */         return;
/*     */       }
/* 663 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 664 */         !state.includeRawValue()) {
/*     */         return;
/*     */       }
/*     */       
/* 668 */       _checkParentPath();
/*     */     } 
/* 670 */     this.delegate.writeNumber(encodedValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBoolean(boolean v) throws IOException {
/* 676 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 679 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 680 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 681 */       if (state == null) {
/*     */         return;
/*     */       }
/* 684 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 685 */         !state.includeBoolean(v)) {
/*     */         return;
/*     */       }
/*     */       
/* 689 */       _checkParentPath();
/*     */     } 
/* 691 */     this.delegate.writeBoolean(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNull() throws IOException {
/* 697 */     if (this._itemFilter == null) {
/*     */       return;
/*     */     }
/* 700 */     if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
/* 701 */       TokenFilter state = this._filterContext.checkValue(this._itemFilter);
/* 702 */       if (state == null) {
/*     */         return;
/*     */       }
/* 705 */       if (state != TokenFilter.INCLUDE_ALL && 
/* 706 */         !state.includeNull()) {
/*     */         return;
/*     */       }
/*     */       
/* 710 */       _checkParentPath();
/*     */     } 
/* 712 */     this.delegate.writeNull();
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
/*     */   public void writeOmittedField(String fieldName) throws IOException {
/* 724 */     if (this._itemFilter != null) {
/* 725 */       this.delegate.writeOmittedField(fieldName);
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
/*     */   public void writeObjectId(Object id) throws IOException {
/* 740 */     if (this._itemFilter != null) {
/* 741 */       this.delegate.writeObjectId(id);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeObjectRef(Object id) throws IOException {
/* 747 */     if (this._itemFilter != null) {
/* 748 */       this.delegate.writeObjectRef(id);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTypeId(Object id) throws IOException {
/* 754 */     if (this._itemFilter != null) {
/* 755 */       this.delegate.writeTypeId(id);
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
/*     */   protected void _checkParentPath() throws IOException {
/* 834 */     this._matchCount++;
/*     */     
/* 836 */     if (this._includePath) {
/* 837 */       this._filterContext.writePath(this.delegate);
/*     */     }
/*     */     
/* 840 */     if (!this._allowMultipleMatches)
/*     */     {
/* 842 */       this._filterContext.skipParentChecks();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _checkPropertyParentPath() throws IOException {
/* 853 */     this._matchCount++;
/* 854 */     if (this._includePath) {
/* 855 */       this._filterContext.writePath(this.delegate);
/* 856 */     } else if (this._includeImmediateParent) {
/*     */ 
/*     */       
/* 859 */       this._filterContext.writeImmediatePath(this.delegate);
/*     */     } 
/*     */ 
/*     */     
/* 863 */     if (!this._allowMultipleMatches)
/*     */     {
/* 865 */       this._filterContext.skipParentChecks();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean _checkBinaryWrite() throws IOException {
/* 871 */     if (this._itemFilter == null) {
/* 872 */       return false;
/*     */     }
/* 874 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 875 */       return true;
/*     */     }
/* 877 */     if (this._itemFilter.includeBinary()) {
/* 878 */       _checkParentPath();
/* 879 */       return true;
/*     */     } 
/* 881 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean _checkRawValueWrite() throws IOException {
/* 886 */     if (this._itemFilter == null) {
/* 887 */       return false;
/*     */     }
/* 889 */     if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
/* 890 */       return true;
/*     */     }
/* 892 */     if (this._itemFilter.includeRawValue()) {
/* 893 */       _checkParentPath();
/* 894 */       return true;
/*     */     } 
/* 896 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\filter\FilteringGeneratorDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */