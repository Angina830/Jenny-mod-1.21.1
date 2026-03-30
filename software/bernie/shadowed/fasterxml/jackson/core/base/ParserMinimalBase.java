/*     */ package software.bernie.shadowed.fasterxml.jackson.core.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.JsonEOFException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ParserMinimalBase
/*     */   extends JsonParser
/*     */ {
/*     */   protected static final int INT_TAB = 9;
/*     */   protected static final int INT_LF = 10;
/*     */   protected static final int INT_CR = 13;
/*     */   protected static final int INT_SPACE = 32;
/*     */   protected static final int INT_LBRACKET = 91;
/*     */   protected static final int INT_RBRACKET = 93;
/*     */   protected static final int INT_LCURLY = 123;
/*     */   protected static final int INT_RCURLY = 125;
/*     */   protected static final int INT_QUOTE = 34;
/*     */   protected static final int INT_APOS = 39;
/*     */   protected static final int INT_BACKSLASH = 92;
/*     */   protected static final int INT_SLASH = 47;
/*     */   protected static final int INT_ASTERISK = 42;
/*     */   protected static final int INT_COLON = 58;
/*     */   protected static final int INT_COMMA = 44;
/*     */   protected static final int INT_HASH = 35;
/*     */   protected static final int INT_0 = 48;
/*     */   protected static final int INT_9 = 57;
/*     */   protected static final int INT_MINUS = 45;
/*     */   protected static final int INT_PLUS = 43;
/*     */   protected static final int INT_PERIOD = 46;
/*     */   protected static final int INT_e = 101;
/*     */   protected static final int INT_E = 69;
/*     */   protected static final char CHAR_NULL = '\000';
/*  62 */   protected static final byte[] NO_BYTES = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   protected static final int[] NO_INTS = new int[0];
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int NR_UNKNOWN = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int NR_INT = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int NR_LONG = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int NR_BIGINT = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int NR_DOUBLE = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int NR_BIGDECIMAL = 16;
/*     */ 
/*     */   
/*     */   protected static final int NR_FLOAT = 32;
/*     */ 
/*     */   
/*  97 */   protected static final BigInteger BI_MIN_INT = BigInteger.valueOf(-2147483648L);
/*  98 */   protected static final BigInteger BI_MAX_INT = BigInteger.valueOf(2147483647L);
/*     */   
/* 100 */   protected static final BigInteger BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
/* 101 */   protected static final BigInteger BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
/*     */   
/* 103 */   protected static final BigDecimal BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
/* 104 */   protected static final BigDecimal BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
/*     */   
/* 106 */   protected static final BigDecimal BD_MIN_INT = new BigDecimal(BI_MIN_INT);
/* 107 */   protected static final BigDecimal BD_MAX_INT = new BigDecimal(BI_MAX_INT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final long MIN_INT_L = -2147483648L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final long MAX_INT_L = 2147483647L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double MIN_LONG_D = -9.223372036854776E18D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double MAX_LONG_D = 9.223372036854776E18D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double MIN_INT_D = -2.147483648E9D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double MAX_INT_D = 2.147483647E9D;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int MAX_ERROR_TOKEN_LENGTH = 256;
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonToken _currToken;
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonToken _lastClearedToken;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ParserMinimalBase() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ParserMinimalBase(int features) {
/* 160 */     super(features);
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
/*     */   public abstract JsonToken nextToken() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken currentToken() {
/* 186 */     return this._currToken;
/*     */   } public int currentTokenId() {
/* 188 */     JsonToken t = this._currToken;
/* 189 */     return (t == null) ? 0 : t.id();
/*     */   }
/*     */   public JsonToken getCurrentToken() {
/* 192 */     return this._currToken;
/*     */   } public int getCurrentTokenId() {
/* 194 */     JsonToken t = this._currToken;
/* 195 */     return (t == null) ? 0 : t.id();
/*     */   }
/*     */   public boolean hasCurrentToken() {
/* 198 */     return (this._currToken != null);
/*     */   } public boolean hasTokenId(int id) {
/* 200 */     JsonToken t = this._currToken;
/* 201 */     if (t == null) {
/* 202 */       return (0 == id);
/*     */     }
/* 204 */     return (t.id() == id);
/*     */   }
/*     */   
/*     */   public boolean hasToken(JsonToken t) {
/* 208 */     return (this._currToken == t);
/*     */   }
/*     */   
/* 211 */   public boolean isExpectedStartArrayToken() { return (this._currToken == JsonToken.START_ARRAY); } public boolean isExpectedStartObjectToken() {
/* 212 */     return (this._currToken == JsonToken.START_OBJECT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken nextValue() throws IOException {
/* 218 */     JsonToken t = nextToken();
/* 219 */     if (t == JsonToken.FIELD_NAME) {
/* 220 */       t = nextToken();
/*     */     }
/* 222 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonParser skipChildren() throws IOException {
/* 228 */     if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY)
/*     */     {
/* 230 */       return this;
/*     */     }
/* 232 */     int open = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 237 */       JsonToken t = nextToken();
/* 238 */       if (t == null) {
/* 239 */         _handleEOF();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 244 */         return this;
/*     */       } 
/* 246 */       if (t.isStructStart()) {
/* 247 */         open++; continue;
/* 248 */       }  if (t.isStructEnd() && 
/* 249 */         --open == 0) {
/* 250 */         return this;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void _handleEOF() throws JsonParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getCurrentName() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void close() throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isClosed();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract JsonStreamContext getParsingContext();
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCurrentToken() {
/* 281 */     if (this._currToken != null) {
/* 282 */       this._lastClearedToken = this._currToken;
/* 283 */       this._currToken = null;
/*     */     } 
/*     */   }
/*     */   public JsonToken getLastClearedToken() {
/* 287 */     return this._lastClearedToken;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void overrideCurrentName(String paramString);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getText() throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract char[] getTextCharacters() throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean hasTextCharacters();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getTextLength() throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getTextOffset() throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] getBinaryValue(Base64Variant paramBase64Variant) throws IOException;
/*     */ 
/*     */   
/*     */   public boolean getValueAsBoolean(boolean defaultValue) throws IOException {
/* 320 */     JsonToken t = this._currToken;
/* 321 */     if (t != null) {
/* 322 */       String str; Object value; switch (t.id()) {
/*     */         case 6:
/* 324 */           str = getText().trim();
/* 325 */           if ("true".equals(str)) {
/* 326 */             return true;
/*     */           }
/* 328 */           if ("false".equals(str)) {
/* 329 */             return false;
/*     */           }
/* 331 */           if (_hasTextualNull(str)) {
/* 332 */             return false;
/*     */           }
/*     */           break;
/*     */         case 7:
/* 336 */           return (getIntValue() != 0);
/*     */         case 9:
/* 338 */           return true;
/*     */         case 10:
/*     */         case 11:
/* 341 */           return false;
/*     */         case 12:
/* 343 */           value = getEmbeddedObject();
/* 344 */           if (value instanceof Boolean) {
/* 345 */             return ((Boolean)value).booleanValue();
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 351 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueAsInt() throws IOException {
/* 357 */     JsonToken t = this._currToken;
/* 358 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 359 */       return getIntValue();
/*     */     }
/* 361 */     return getValueAsInt(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueAsInt(int defaultValue) throws IOException {
/* 367 */     JsonToken t = this._currToken;
/* 368 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 369 */       return getIntValue();
/*     */     }
/* 371 */     if (t != null) {
/* 372 */       String str; Object value; switch (t.id()) {
/*     */         case 6:
/* 374 */           str = getText();
/* 375 */           if (_hasTextualNull(str)) {
/* 376 */             return 0;
/*     */           }
/* 378 */           return NumberInput.parseAsInt(str, defaultValue);
/*     */         case 9:
/* 380 */           return 1;
/*     */         case 10:
/* 382 */           return 0;
/*     */         case 11:
/* 384 */           return 0;
/*     */         case 12:
/* 386 */           value = getEmbeddedObject();
/* 387 */           if (value instanceof Number)
/* 388 */             return ((Number)value).intValue(); 
/*     */           break;
/*     */       } 
/*     */     } 
/* 392 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getValueAsLong() throws IOException {
/* 398 */     JsonToken t = this._currToken;
/* 399 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 400 */       return getLongValue();
/*     */     }
/* 402 */     return getValueAsLong(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getValueAsLong(long defaultValue) throws IOException {
/* 408 */     JsonToken t = this._currToken;
/* 409 */     if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 410 */       return getLongValue();
/*     */     }
/* 412 */     if (t != null) {
/* 413 */       String str; Object value; switch (t.id()) {
/*     */         case 6:
/* 415 */           str = getText();
/* 416 */           if (_hasTextualNull(str)) {
/* 417 */             return 0L;
/*     */           }
/* 419 */           return NumberInput.parseAsLong(str, defaultValue);
/*     */         case 9:
/* 421 */           return 1L;
/*     */         case 10:
/*     */         case 11:
/* 424 */           return 0L;
/*     */         case 12:
/* 426 */           value = getEmbeddedObject();
/* 427 */           if (value instanceof Number)
/* 428 */             return ((Number)value).longValue(); 
/*     */           break;
/*     */       } 
/*     */     } 
/* 432 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValueAsDouble(double defaultValue) throws IOException {
/* 438 */     JsonToken t = this._currToken;
/* 439 */     if (t != null) {
/* 440 */       String str; Object value; switch (t.id()) {
/*     */         case 6:
/* 442 */           str = getText();
/* 443 */           if (_hasTextualNull(str)) {
/* 444 */             return 0.0D;
/*     */           }
/* 446 */           return NumberInput.parseAsDouble(str, defaultValue);
/*     */         case 7:
/*     */         case 8:
/* 449 */           return getDoubleValue();
/*     */         case 9:
/* 451 */           return 1.0D;
/*     */         case 10:
/*     */         case 11:
/* 454 */           return 0.0D;
/*     */         case 12:
/* 456 */           value = getEmbeddedObject();
/* 457 */           if (value instanceof Number)
/* 458 */             return ((Number)value).doubleValue(); 
/*     */           break;
/*     */       } 
/*     */     } 
/* 462 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueAsString() throws IOException {
/* 467 */     if (this._currToken == JsonToken.VALUE_STRING) {
/* 468 */       return getText();
/*     */     }
/* 470 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 471 */       return getCurrentName();
/*     */     }
/* 473 */     return getValueAsString((String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueAsString(String defaultValue) throws IOException {
/* 478 */     if (this._currToken == JsonToken.VALUE_STRING) {
/* 479 */       return getText();
/*     */     }
/* 481 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 482 */       return getCurrentName();
/*     */     }
/* 484 */     if (this._currToken == null || this._currToken == JsonToken.VALUE_NULL || !this._currToken.isScalarValue()) {
/* 485 */       return defaultValue;
/*     */     }
/* 487 */     return getText();
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
/*     */   protected void _decodeBase64(String str, ByteArrayBuilder builder, Base64Variant b64variant) throws IOException {
/*     */     try {
/* 503 */       b64variant.decode(str, builder);
/* 504 */     } catch (IllegalArgumentException e) {
/* 505 */       _reportError(e.getMessage());
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
/*     */   protected boolean _hasTextualNull(String value) {
/* 522 */     return "null".equals(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reportUnexpectedNumberChar(int ch, String comment) throws JsonParseException {
/* 531 */     String msg = String.format("Unexpected character (%s) in numeric value", new Object[] { _getCharDesc(ch) });
/* 532 */     if (comment != null) {
/* 533 */       msg = msg + ": " + comment;
/*     */     }
/* 535 */     _reportError(msg);
/*     */   }
/*     */   
/*     */   protected void reportInvalidNumber(String msg) throws JsonParseException {
/* 539 */     _reportError("Invalid numeric value: " + msg);
/*     */   }
/*     */   
/*     */   protected void reportOverflowInt() throws IOException {
/* 543 */     _reportError(String.format("Numeric value (%s) out of range of int (%d - %s)", new Object[] { getText(), Integer.valueOf(-2147483648), Integer.valueOf(2147483647) }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reportOverflowLong() throws IOException {
/* 548 */     _reportError(String.format("Numeric value (%s) out of range of long (%d - %s)", new Object[] { getText(), Long.valueOf(Long.MIN_VALUE), Long.valueOf(Long.MAX_VALUE) }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _reportUnexpectedChar(int ch, String comment) throws JsonParseException {
/* 554 */     if (ch < 0) {
/* 555 */       _reportInvalidEOF();
/*     */     }
/* 557 */     String msg = String.format("Unexpected character (%s)", new Object[] { _getCharDesc(ch) });
/* 558 */     if (comment != null) {
/* 559 */       msg = msg + ": " + comment;
/*     */     }
/* 561 */     _reportError(msg);
/*     */   }
/*     */   
/*     */   protected void _reportInvalidEOF() throws JsonParseException {
/* 565 */     _reportInvalidEOF(" in " + this._currToken, this._currToken);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _reportInvalidEOFInValue(JsonToken type) throws JsonParseException {
/*     */     String msg;
/* 573 */     if (type == JsonToken.VALUE_STRING) {
/* 574 */       msg = " in a String value";
/* 575 */     } else if (type == JsonToken.VALUE_NUMBER_INT || type == JsonToken.VALUE_NUMBER_FLOAT) {
/*     */       
/* 577 */       msg = " in a Number value";
/*     */     } else {
/* 579 */       msg = " in a value";
/*     */     } 
/* 581 */     _reportInvalidEOF(msg, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _reportInvalidEOF(String msg, JsonToken currToken) throws JsonParseException {
/* 588 */     throw new JsonEOFException(this, currToken, "Unexpected end-of-input" + msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void _reportInvalidEOFInValue() throws JsonParseException {
/* 596 */     _reportInvalidEOF(" in a value");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void _reportInvalidEOF(String msg) throws JsonParseException {
/* 604 */     throw new JsonEOFException(this, null, "Unexpected end-of-input" + msg);
/*     */   }
/*     */   
/*     */   protected void _reportMissingRootWS(int ch) throws JsonParseException {
/* 608 */     _reportUnexpectedChar(ch, "Expected space separating root-level values");
/*     */   }
/*     */   
/*     */   protected void _throwInvalidSpace(int i) throws JsonParseException {
/* 612 */     char c = (char)i;
/* 613 */     String msg = "Illegal character (" + _getCharDesc(c) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens";
/* 614 */     _reportError(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _throwUnquotedSpace(int i, String ctxtDesc) throws JsonParseException {
/* 624 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || i > 32) {
/* 625 */       char c = (char)i;
/* 626 */       String msg = "Illegal unquoted character (" + _getCharDesc(c) + "): has to be escaped using backslash to be included in " + ctxtDesc;
/* 627 */       _reportError(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected char _handleUnrecognizedCharacterEscape(char ch) throws JsonProcessingException {
/* 633 */     if (isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
/* 634 */       return ch;
/*     */     }
/*     */     
/* 637 */     if (ch == '\'' && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 638 */       return ch;
/*     */     }
/* 640 */     _reportError("Unrecognized character escape " + _getCharDesc(ch));
/* 641 */     return ch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String _getCharDesc(int ch) {
/* 652 */     char c = (char)ch;
/* 653 */     if (Character.isISOControl(c)) {
/* 654 */       return "(CTRL-CHAR, code " + ch + ")";
/*     */     }
/* 656 */     if (ch > 255) {
/* 657 */       return "'" + c + "' (code " + ch + " / 0x" + Integer.toHexString(ch) + ")";
/*     */     }
/* 659 */     return "'" + c + "' (code " + ch + ")";
/*     */   }
/*     */   
/*     */   protected final void _reportError(String msg) throws JsonParseException {
/* 663 */     throw _constructError(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void _reportError(String msg, Object arg) throws JsonParseException {
/* 668 */     throw _constructError(String.format(msg, new Object[] { arg }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void _reportError(String msg, Object arg1, Object arg2) throws JsonParseException {
/* 673 */     throw _constructError(String.format(msg, new Object[] { arg1, arg2 }));
/*     */   }
/*     */   
/*     */   protected final void _wrapError(String msg, Throwable t) throws JsonParseException {
/* 677 */     throw _constructError(msg, t);
/*     */   }
/*     */   
/*     */   protected final void _throwInternal() {
/* 681 */     VersionUtil.throwInternal();
/*     */   }
/*     */   
/*     */   protected final JsonParseException _constructError(String msg, Throwable t) {
/* 685 */     return new JsonParseException(this, msg, t);
/*     */   }
/*     */   
/*     */   protected static byte[] _asciiBytes(String str) {
/* 689 */     byte[] b = new byte[str.length()];
/* 690 */     for (int i = 0, len = str.length(); i < len; i++) {
/* 691 */       b[i] = (byte)str.charAt(i);
/*     */     }
/* 693 */     return b;
/*     */   }
/*     */   
/*     */   protected static String _ascii(byte[] b) {
/*     */     try {
/* 698 */       return new String(b, "US-ASCII");
/* 699 */     } catch (IOException e) {
/* 700 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\base\ParserMinimalBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */