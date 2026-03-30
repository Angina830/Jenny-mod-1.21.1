/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.TreeMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.base.ParserMinimalBase;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.JsonWriteContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.PackageVersion;
/*      */ 
/*      */ 
/*      */ public class TokenBuffer
/*      */   extends JsonGenerator
/*      */ {
/*   32 */   protected static final int DEFAULT_GENERATOR_FEATURES = JsonGenerator.Feature.collectDefaults();
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
/*      */   protected JsonStreamContext _parentContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _generatorFeatures;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _closed;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasNativeTypeIds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasNativeObjectIds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _mayHaveNativeIds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _forceBigDecimal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Segment _first;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Segment _last;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _appendAt;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _typeId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _objectId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasNativeId = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonWriteContext _writeContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TokenBuffer(ObjectCodec codec, boolean hasNativeIds) {
/*  151 */     this._objectCodec = codec;
/*  152 */     this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
/*  153 */     this._writeContext = JsonWriteContext.createRootContext(null);
/*      */     
/*  155 */     this._first = this._last = new Segment();
/*  156 */     this._appendAt = 0;
/*  157 */     this._hasNativeTypeIds = hasNativeIds;
/*  158 */     this._hasNativeObjectIds = hasNativeIds;
/*      */     
/*  160 */     this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TokenBuffer(JsonParser p) {
/*  167 */     this(p, (DeserializationContext)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TokenBuffer(JsonParser p, DeserializationContext ctxt) {
/*  175 */     this._objectCodec = p.getCodec();
/*  176 */     this._parentContext = p.getParsingContext();
/*  177 */     this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
/*  178 */     this._writeContext = JsonWriteContext.createRootContext(null);
/*      */     
/*  180 */     this._first = this._last = new Segment();
/*  181 */     this._appendAt = 0;
/*  182 */     this._hasNativeTypeIds = p.canReadTypeId();
/*  183 */     this._hasNativeObjectIds = p.canReadObjectId();
/*  184 */     this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
/*  185 */     this._forceBigDecimal = (ctxt == null) ? false : ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
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
/*      */   public static TokenBuffer asCopyOfValue(JsonParser p) throws IOException {
/*  200 */     TokenBuffer b = new TokenBuffer(p);
/*  201 */     b.copyCurrentStructure(p);
/*  202 */     return b;
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
/*      */   public TokenBuffer overrideParentContext(JsonStreamContext ctxt) {
/*  214 */     this._parentContext = ctxt;
/*  215 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TokenBuffer forceUseOfBigDecimal(boolean b) {
/*  222 */     this._forceBigDecimal = b;
/*  223 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public Version version() {
/*  228 */     return PackageVersion.VERSION;
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
/*      */   public JsonParser asParser() {
/*  242 */     return asParser(this._objectCodec);
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
/*      */   public JsonParser asParserOnFirstToken() throws IOException {
/*  256 */     JsonParser p = asParser(this._objectCodec);
/*  257 */     p.nextToken();
/*  258 */     return p;
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
/*      */   public JsonParser asParser(ObjectCodec codec) {
/*  276 */     return (JsonParser)new Parser(this._first, codec, this._hasNativeTypeIds, this._hasNativeObjectIds, this._parentContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser asParser(JsonParser src) {
/*  285 */     Parser p = new Parser(this._first, src.getCodec(), this._hasNativeTypeIds, this._hasNativeObjectIds, this._parentContext);
/*  286 */     p.setLocation(src.getTokenLocation());
/*  287 */     return (JsonParser)p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonToken firstToken() {
/*  298 */     return this._first.type(0);
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
/*      */   public TokenBuffer append(TokenBuffer other) throws IOException {
/*  318 */     if (!this._hasNativeTypeIds) {
/*  319 */       this._hasNativeTypeIds = other.canWriteTypeId();
/*      */     }
/*  321 */     if (!this._hasNativeObjectIds) {
/*  322 */       this._hasNativeObjectIds = other.canWriteObjectId();
/*      */     }
/*  324 */     this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
/*      */     
/*  326 */     JsonParser p = other.asParser();
/*  327 */     while (p.nextToken() != null) {
/*  328 */       copyCurrentStructure(p);
/*      */     }
/*  330 */     return this;
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
/*      */   public void serialize(JsonGenerator gen) throws IOException {
/*  345 */     Segment segment = this._first;
/*  346 */     int ptr = -1;
/*      */     
/*  348 */     boolean checkIds = this._mayHaveNativeIds;
/*  349 */     boolean hasIds = (checkIds && segment.hasIds());
/*      */     while (true) {
/*      */       Object ob, n, value;
/*  352 */       if (++ptr >= 16) {
/*  353 */         ptr = 0;
/*  354 */         segment = segment.next();
/*  355 */         if (segment == null)
/*  356 */           break;  hasIds = (checkIds && segment.hasIds());
/*      */       } 
/*  358 */       JsonToken t = segment.type(ptr);
/*  359 */       if (t == null)
/*      */         break; 
/*  361 */       if (hasIds) {
/*  362 */         Object id = segment.findObjectId(ptr);
/*  363 */         if (id != null) {
/*  364 */           gen.writeObjectId(id);
/*      */         }
/*  366 */         id = segment.findTypeId(ptr);
/*  367 */         if (id != null) {
/*  368 */           gen.writeTypeId(id);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  373 */       switch (t) {
/*      */         case INT:
/*  375 */           gen.writeStartObject();
/*      */           continue;
/*      */         case BIG_INTEGER:
/*  378 */           gen.writeEndObject();
/*      */           continue;
/*      */         case BIG_DECIMAL:
/*  381 */           gen.writeStartArray();
/*      */           continue;
/*      */         case FLOAT:
/*  384 */           gen.writeEndArray();
/*      */           continue;
/*      */ 
/*      */         
/*      */         case LONG:
/*  389 */           ob = segment.get(ptr);
/*  390 */           if (ob instanceof SerializableString) {
/*  391 */             gen.writeFieldName((SerializableString)ob); continue;
/*      */           } 
/*  393 */           gen.writeFieldName((String)ob);
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case null:
/*  399 */           ob = segment.get(ptr);
/*  400 */           if (ob instanceof SerializableString) {
/*  401 */             gen.writeString((SerializableString)ob); continue;
/*      */           } 
/*  403 */           gen.writeString((String)ob);
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case null:
/*  409 */           n = segment.get(ptr);
/*  410 */           if (n instanceof Integer) {
/*  411 */             gen.writeNumber(((Integer)n).intValue()); continue;
/*  412 */           }  if (n instanceof BigInteger) {
/*  413 */             gen.writeNumber((BigInteger)n); continue;
/*  414 */           }  if (n instanceof Long) {
/*  415 */             gen.writeNumber(((Long)n).longValue()); continue;
/*  416 */           }  if (n instanceof Short) {
/*  417 */             gen.writeNumber(((Short)n).shortValue()); continue;
/*      */           } 
/*  419 */           gen.writeNumber(((Number)n).intValue());
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case null:
/*  425 */           n = segment.get(ptr);
/*  426 */           if (n instanceof Double) {
/*  427 */             gen.writeNumber(((Double)n).doubleValue()); continue;
/*  428 */           }  if (n instanceof BigDecimal) {
/*  429 */             gen.writeNumber((BigDecimal)n); continue;
/*  430 */           }  if (n instanceof Float) {
/*  431 */             gen.writeNumber(((Float)n).floatValue()); continue;
/*  432 */           }  if (n == null) {
/*  433 */             gen.writeNull(); continue;
/*  434 */           }  if (n instanceof String) {
/*  435 */             gen.writeNumber((String)n); continue;
/*      */           } 
/*  437 */           throw new JsonGenerationException(String.format("Unrecognized value type for VALUE_NUMBER_FLOAT: %s, cannot serialize", new Object[] { n.getClass().getName() }), gen);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case null:
/*  444 */           gen.writeBoolean(true);
/*      */           continue;
/*      */         case null:
/*  447 */           gen.writeBoolean(false);
/*      */           continue;
/*      */         case null:
/*  450 */           gen.writeNull();
/*      */           continue;
/*      */         
/*      */         case null:
/*  454 */           value = segment.get(ptr);
/*      */ 
/*      */ 
/*      */           
/*  458 */           if (value instanceof RawValue) {
/*  459 */             ((RawValue)value).serialize(gen); continue;
/*  460 */           }  if (value instanceof software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable) {
/*  461 */             gen.writeObject(value); continue;
/*      */           } 
/*  463 */           gen.writeEmbeddedObject(value);
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/*  468 */       throw new RuntimeException("Internal error: should never end up through this code path");
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
/*      */   public TokenBuffer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  480 */     if (p.getCurrentTokenId() != JsonToken.FIELD_NAME.id()) {
/*  481 */       copyCurrentStructure(p);
/*  482 */       return this;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  489 */     writeStartObject(); JsonToken t;
/*      */     do {
/*  491 */       copyCurrentStructure(p);
/*  492 */     } while ((t = p.nextToken()) == JsonToken.FIELD_NAME);
/*  493 */     if (t != JsonToken.END_OBJECT) {
/*  494 */       ctxt.reportWrongTokenException(TokenBuffer.class, JsonToken.END_OBJECT, "Expected END_OBJECT after copying contents of a JsonParser into TokenBuffer, got " + t, new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  498 */     writeEndObject();
/*  499 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  507 */     int MAX_COUNT = 100;
/*      */     
/*  509 */     StringBuilder sb = new StringBuilder();
/*  510 */     sb.append("[TokenBuffer: ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  517 */     JsonParser jp = asParser();
/*  518 */     int count = 0;
/*  519 */     boolean hasNativeIds = (this._hasNativeTypeIds || this._hasNativeObjectIds);
/*      */ 
/*      */     
/*      */     while (true) {
/*      */       try {
/*  524 */         JsonToken t = jp.nextToken();
/*  525 */         if (t == null)
/*      */           break; 
/*  527 */         if (hasNativeIds) {
/*  528 */           _appendNativeIds(sb);
/*      */         }
/*      */         
/*  531 */         if (count < 100) {
/*  532 */           if (count > 0) {
/*  533 */             sb.append(", ");
/*      */           }
/*  535 */           sb.append(t.toString());
/*  536 */           if (t == JsonToken.FIELD_NAME) {
/*  537 */             sb.append('(');
/*  538 */             sb.append(jp.getCurrentName());
/*  539 */             sb.append(')');
/*      */           } 
/*      */         } 
/*  542 */       } catch (IOException ioe) {
/*  543 */         throw new IllegalStateException(ioe);
/*      */       } 
/*  545 */       count++;
/*      */     } 
/*      */     
/*  548 */     if (count >= 100) {
/*  549 */       sb.append(" ... (truncated ").append(count - 100).append(" entries)");
/*      */     }
/*  551 */     sb.append(']');
/*  552 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _appendNativeIds(StringBuilder sb) {
/*  557 */     Object objectId = this._last.findObjectId(this._appendAt - 1);
/*  558 */     if (objectId != null) {
/*  559 */       sb.append("[objectId=").append(String.valueOf(objectId)).append(']');
/*      */     }
/*  561 */     Object typeId = this._last.findTypeId(this._appendAt - 1);
/*  562 */     if (typeId != null) {
/*  563 */       sb.append("[typeId=").append(String.valueOf(typeId)).append(']');
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
/*      */   public JsonGenerator enable(JsonGenerator.Feature f) {
/*  575 */     this._generatorFeatures |= f.getMask();
/*  576 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonGenerator disable(JsonGenerator.Feature f) {
/*  581 */     this._generatorFeatures &= f.getMask() ^ 0xFFFFFFFF;
/*  582 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled(JsonGenerator.Feature f) {
/*  589 */     return ((this._generatorFeatures & f.getMask()) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFeatureMask() {
/*  594 */     return this._generatorFeatures;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonGenerator setFeatureMask(int mask) {
/*  600 */     this._generatorFeatures = mask;
/*  601 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonGenerator overrideStdFeatures(int values, int mask) {
/*  606 */     int oldState = getFeatureMask();
/*  607 */     this._generatorFeatures = oldState & (mask ^ 0xFFFFFFFF) | values & mask;
/*  608 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonGenerator useDefaultPrettyPrinter() {
/*  614 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonGenerator setCodec(ObjectCodec oc) {
/*  619 */     this._objectCodec = oc;
/*  620 */     return this;
/*      */   }
/*      */   
/*      */   public ObjectCodec getCodec() {
/*  624 */     return this._objectCodec;
/*      */   }
/*      */   public final JsonWriteContext getOutputContext() {
/*  627 */     return this._writeContext;
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
/*      */   public boolean canWriteBinaryNatively() {
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  654 */     this._closed = true;
/*      */   }
/*      */   
/*      */   public boolean isClosed() {
/*  658 */     return this._closed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeStartArray() throws IOException {
/*  669 */     this._writeContext.writeValue();
/*  670 */     _append(JsonToken.START_ARRAY);
/*  671 */     this._writeContext = this._writeContext.createChildArrayContext();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeEndArray() throws IOException {
/*  677 */     _append(JsonToken.END_ARRAY);
/*      */     
/*  679 */     JsonWriteContext c = this._writeContext.getParent();
/*  680 */     if (c != null) {
/*  681 */       this._writeContext = c;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeStartObject() throws IOException {
/*  688 */     this._writeContext.writeValue();
/*  689 */     _append(JsonToken.START_OBJECT);
/*  690 */     this._writeContext = this._writeContext.createChildObjectContext();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartObject(Object forValue) throws IOException {
/*  696 */     this._writeContext.writeValue();
/*  697 */     _append(JsonToken.START_OBJECT);
/*  698 */     JsonWriteContext ctxt = this._writeContext.createChildObjectContext();
/*  699 */     this._writeContext = ctxt;
/*  700 */     if (forValue != null) {
/*  701 */       ctxt.setCurrentValue(forValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeEndObject() throws IOException {
/*  708 */     _append(JsonToken.END_OBJECT);
/*      */     
/*  710 */     JsonWriteContext c = this._writeContext.getParent();
/*  711 */     if (c != null) {
/*  712 */       this._writeContext = c;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeFieldName(String name) throws IOException {
/*  719 */     this._writeContext.writeFieldName(name);
/*  720 */     _append(JsonToken.FIELD_NAME, name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFieldName(SerializableString name) throws IOException {
/*  726 */     this._writeContext.writeFieldName(name.getValue());
/*  727 */     _append(JsonToken.FIELD_NAME, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeString(String text) throws IOException {
/*  738 */     if (text == null) {
/*  739 */       writeNull();
/*      */     } else {
/*  741 */       _appendValue(JsonToken.VALUE_STRING, text);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(char[] text, int offset, int len) throws IOException {
/*  747 */     writeString(new String(text, offset, len));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(SerializableString text) throws IOException {
/*  752 */     if (text == null) {
/*  753 */       writeNull();
/*      */     } else {
/*  755 */       _appendValue(JsonToken.VALUE_STRING, text);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
/*  763 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
/*  770 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRaw(String text) throws IOException {
/*  775 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRaw(String text, int offset, int len) throws IOException {
/*  780 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRaw(SerializableString text) throws IOException {
/*  785 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRaw(char[] text, int offset, int len) throws IOException {
/*  790 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRaw(char c) throws IOException {
/*  795 */     _reportUnsupportedOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRawValue(String text) throws IOException {
/*  800 */     _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new RawValue(text));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRawValue(String text, int offset, int len) throws IOException {
/*  805 */     if (offset > 0 || len != text.length()) {
/*  806 */       text = text.substring(offset, offset + len);
/*      */     }
/*  808 */     _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new RawValue(text));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRawValue(char[] text, int offset, int len) throws IOException {
/*  813 */     _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new String(text, offset, len));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(short i) throws IOException {
/*  824 */     _appendValue(JsonToken.VALUE_NUMBER_INT, Short.valueOf(i));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNumber(int i) throws IOException {
/*  829 */     _appendValue(JsonToken.VALUE_NUMBER_INT, Integer.valueOf(i));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNumber(long l) throws IOException {
/*  834 */     _appendValue(JsonToken.VALUE_NUMBER_INT, Long.valueOf(l));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNumber(double d) throws IOException {
/*  839 */     _appendValue(JsonToken.VALUE_NUMBER_FLOAT, Double.valueOf(d));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNumber(float f) throws IOException {
/*  844 */     _appendValue(JsonToken.VALUE_NUMBER_FLOAT, Float.valueOf(f));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNumber(BigDecimal dec) throws IOException {
/*  849 */     if (dec == null) {
/*  850 */       writeNull();
/*      */     } else {
/*  852 */       _appendValue(JsonToken.VALUE_NUMBER_FLOAT, dec);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNumber(BigInteger v) throws IOException {
/*  858 */     if (v == null) {
/*  859 */       writeNull();
/*      */     } else {
/*  861 */       _appendValue(JsonToken.VALUE_NUMBER_INT, v);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(String encodedValue) throws IOException {
/*  870 */     _appendValue(JsonToken.VALUE_NUMBER_FLOAT, encodedValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBoolean(boolean state) throws IOException {
/*  875 */     _appendValue(state ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNull() throws IOException {
/*  880 */     _appendValue(JsonToken.VALUE_NULL);
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
/*      */   public void writeObject(Object value) throws IOException {
/*  892 */     if (value == null) {
/*  893 */       writeNull();
/*      */       return;
/*      */     } 
/*  896 */     Class<?> raw = value.getClass();
/*  897 */     if (raw == byte[].class || value instanceof RawValue) {
/*  898 */       _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, value);
/*      */       return;
/*      */     } 
/*  901 */     if (this._objectCodec == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  906 */       _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, value);
/*      */     } else {
/*  908 */       this._objectCodec.writeValue(this, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTree(TreeNode node) throws IOException {
/*  915 */     if (node == null) {
/*  916 */       writeNull();
/*      */       
/*      */       return;
/*      */     } 
/*  920 */     if (this._objectCodec == null) {
/*      */       
/*  922 */       _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, node);
/*      */     } else {
/*  924 */       this._objectCodec.writeTree(this, node);
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
/*      */   public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException {
/*  943 */     byte[] copy = new byte[len];
/*  944 */     System.arraycopy(data, offset, copy, 0, len);
/*  945 */     writeObject(copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) {
/*  956 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteTypeId() {
/*  967 */     return this._hasNativeTypeIds;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canWriteObjectId() {
/*  972 */     return this._hasNativeObjectIds;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeTypeId(Object id) {
/*  977 */     this._typeId = id;
/*  978 */     this._hasNativeId = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeObjectId(Object id) {
/*  983 */     this._objectId = id;
/*  984 */     this._hasNativeId = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEmbeddedObject(Object object) throws IOException {
/*  989 */     _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, object);
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
/*      */   public void copyCurrentEvent(JsonParser p) throws IOException {
/* 1001 */     if (this._mayHaveNativeIds) {
/* 1002 */       _checkNativeIds(p);
/*      */     }
/* 1004 */     switch (p.getCurrentToken()) {
/*      */       case INT:
/* 1006 */         writeStartObject();
/*      */         return;
/*      */       case BIG_INTEGER:
/* 1009 */         writeEndObject();
/*      */         return;
/*      */       case BIG_DECIMAL:
/* 1012 */         writeStartArray();
/*      */         return;
/*      */       case FLOAT:
/* 1015 */         writeEndArray();
/*      */         return;
/*      */       case LONG:
/* 1018 */         writeFieldName(p.getCurrentName());
/*      */         return;
/*      */       case null:
/* 1021 */         if (p.hasTextCharacters()) {
/* 1022 */           writeString(p.getTextCharacters(), p.getTextOffset(), p.getTextLength());
/*      */         } else {
/* 1024 */           writeString(p.getText());
/*      */         } 
/*      */         return;
/*      */       case null:
/* 1028 */         switch (p.getNumberType()) {
/*      */           case INT:
/* 1030 */             writeNumber(p.getIntValue());
/*      */             return;
/*      */           case BIG_INTEGER:
/* 1033 */             writeNumber(p.getBigIntegerValue());
/*      */             return;
/*      */         } 
/* 1036 */         writeNumber(p.getLongValue());
/*      */         return;
/*      */       
/*      */       case null:
/* 1040 */         if (this._forceBigDecimal) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1046 */           writeNumber(p.getDecimalValue());
/*      */         } else {
/* 1048 */           switch (p.getNumberType()) {
/*      */             case BIG_DECIMAL:
/* 1050 */               writeNumber(p.getDecimalValue());
/*      */               return;
/*      */             case FLOAT:
/* 1053 */               writeNumber(p.getFloatValue());
/*      */               return;
/*      */           } 
/* 1056 */           writeNumber(p.getDoubleValue());
/*      */         } 
/*      */         return;
/*      */       
/*      */       case null:
/* 1061 */         writeBoolean(true);
/*      */         return;
/*      */       case null:
/* 1064 */         writeBoolean(false);
/*      */         return;
/*      */       case null:
/* 1067 */         writeNull();
/*      */         return;
/*      */       case null:
/* 1070 */         writeObject(p.getEmbeddedObject());
/*      */         return;
/*      */     } 
/* 1073 */     throw new RuntimeException("Internal error: should never end up through this code path");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyCurrentStructure(JsonParser p) throws IOException {
/* 1080 */     JsonToken t = p.getCurrentToken();
/*      */ 
/*      */     
/* 1083 */     if (t == JsonToken.FIELD_NAME) {
/* 1084 */       if (this._mayHaveNativeIds) {
/* 1085 */         _checkNativeIds(p);
/*      */       }
/* 1087 */       writeFieldName(p.getCurrentName());
/* 1088 */       t = p.nextToken();
/*      */     } 
/*      */ 
/*      */     
/* 1092 */     if (this._mayHaveNativeIds) {
/* 1093 */       _checkNativeIds(p);
/*      */     }
/*      */     
/* 1096 */     switch (t) {
/*      */       case BIG_DECIMAL:
/* 1098 */         writeStartArray();
/* 1099 */         while (p.nextToken() != JsonToken.END_ARRAY) {
/* 1100 */           copyCurrentStructure(p);
/*      */         }
/* 1102 */         writeEndArray();
/*      */         return;
/*      */       case INT:
/* 1105 */         writeStartObject();
/* 1106 */         while (p.nextToken() != JsonToken.END_OBJECT) {
/* 1107 */           copyCurrentStructure(p);
/*      */         }
/* 1109 */         writeEndObject();
/*      */         return;
/*      */     } 
/* 1112 */     copyCurrentEvent(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _checkNativeIds(JsonParser jp) throws IOException {
/* 1119 */     if ((this._typeId = jp.getTypeId()) != null) {
/* 1120 */       this._hasNativeId = true;
/*      */     }
/* 1122 */     if ((this._objectId = jp.getObjectId()) != null) {
/* 1123 */       this._hasNativeId = true;
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
/*      */   protected final void _append(JsonToken type) {
/* 1135 */     Segment next = this._hasNativeId ? this._last.append(this._appendAt, type, this._objectId, this._typeId) : this._last.append(this._appendAt, type);
/*      */ 
/*      */     
/* 1138 */     if (next == null) {
/* 1139 */       this._appendAt++;
/*      */     } else {
/* 1141 */       this._last = next;
/* 1142 */       this._appendAt = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void _append(JsonToken type, Object value) {
/* 1148 */     Segment next = this._hasNativeId ? this._last.append(this._appendAt, type, value, this._objectId, this._typeId) : this._last.append(this._appendAt, type, value);
/*      */ 
/*      */     
/* 1151 */     if (next == null) {
/* 1152 */       this._appendAt++;
/*      */     } else {
/* 1154 */       this._last = next;
/* 1155 */       this._appendAt = 1;
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
/*      */   protected final void _appendValue(JsonToken type) {
/* 1167 */     this._writeContext.writeValue();
/* 1168 */     Segment next = this._hasNativeId ? this._last.append(this._appendAt, type, this._objectId, this._typeId) : this._last.append(this._appendAt, type);
/*      */ 
/*      */     
/* 1171 */     if (next == null) {
/* 1172 */       this._appendAt++;
/*      */     } else {
/* 1174 */       this._last = next;
/* 1175 */       this._appendAt = 1;
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
/*      */   protected final void _appendValue(JsonToken type, Object value) {
/* 1187 */     this._writeContext.writeValue();
/* 1188 */     Segment next = this._hasNativeId ? this._last.append(this._appendAt, type, value, this._objectId, this._typeId) : this._last.append(this._appendAt, type, value);
/*      */ 
/*      */     
/* 1191 */     if (next == null) {
/* 1192 */       this._appendAt++;
/*      */     } else {
/* 1194 */       this._last = next;
/* 1195 */       this._appendAt = 1;
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
/*      */   protected void _reportUnsupportedOperation() {
/* 1217 */     throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final class Parser
/*      */     extends ParserMinimalBase
/*      */   {
/*      */     protected ObjectCodec _codec;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean _hasNativeTypeIds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean _hasNativeObjectIds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean _hasNativeIds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected TokenBuffer.Segment _segment;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int _segmentPtr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected TokenBufferReadContext _parsingContext;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean _closed;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ByteArrayBuilder _byteBuilder;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1275 */     protected JsonLocation _location = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Parser(TokenBuffer.Segment firstSeg, ObjectCodec codec, boolean hasNativeTypeIds, boolean hasNativeObjectIds) {
/* 1287 */       this(firstSeg, codec, hasNativeTypeIds, hasNativeObjectIds, (JsonStreamContext)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Parser(TokenBuffer.Segment firstSeg, ObjectCodec codec, boolean hasNativeTypeIds, boolean hasNativeObjectIds, JsonStreamContext parentContext) {
/* 1294 */       super(0);
/* 1295 */       this._segment = firstSeg;
/* 1296 */       this._segmentPtr = -1;
/* 1297 */       this._codec = codec;
/* 1298 */       this._parsingContext = TokenBufferReadContext.createRootContext(parentContext);
/* 1299 */       this._hasNativeTypeIds = hasNativeTypeIds;
/* 1300 */       this._hasNativeObjectIds = hasNativeObjectIds;
/* 1301 */       this._hasNativeIds = hasNativeTypeIds | hasNativeObjectIds;
/*      */     }
/*      */     
/*      */     public void setLocation(JsonLocation l) {
/* 1305 */       this._location = l;
/*      */     }
/*      */     
/*      */     public ObjectCodec getCodec() {
/* 1309 */       return this._codec;
/*      */     }
/*      */     public void setCodec(ObjectCodec c) {
/* 1312 */       this._codec = c;
/*      */     }
/*      */     
/*      */     public Version version() {
/* 1316 */       return PackageVersion.VERSION;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JsonToken peekNextToken() throws IOException {
/* 1328 */       if (this._closed) return null; 
/* 1329 */       TokenBuffer.Segment seg = this._segment;
/* 1330 */       int ptr = this._segmentPtr + 1;
/* 1331 */       if (ptr >= 16) {
/* 1332 */         ptr = 0;
/* 1333 */         seg = (seg == null) ? null : seg.next();
/*      */       } 
/* 1335 */       return (seg == null) ? null : seg.type(ptr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/* 1346 */       if (!this._closed) {
/* 1347 */         this._closed = true;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JsonToken nextToken() throws IOException {
/* 1361 */       if (this._closed || this._segment == null) return null;
/*      */ 
/*      */       
/* 1364 */       if (++this._segmentPtr >= 16) {
/* 1365 */         this._segmentPtr = 0;
/* 1366 */         this._segment = this._segment.next();
/* 1367 */         if (this._segment == null) {
/* 1368 */           return null;
/*      */         }
/*      */       } 
/* 1371 */       this._currToken = this._segment.type(this._segmentPtr);
/*      */       
/* 1373 */       if (this._currToken == JsonToken.FIELD_NAME) {
/* 1374 */         Object ob = _currentObject();
/* 1375 */         String name = (ob instanceof String) ? (String)ob : ob.toString();
/* 1376 */         this._parsingContext.setCurrentName(name);
/* 1377 */       } else if (this._currToken == JsonToken.START_OBJECT) {
/* 1378 */         this._parsingContext = this._parsingContext.createChildObjectContext();
/* 1379 */       } else if (this._currToken == JsonToken.START_ARRAY) {
/* 1380 */         this._parsingContext = this._parsingContext.createChildArrayContext();
/* 1381 */       } else if (this._currToken == JsonToken.END_OBJECT || this._currToken == JsonToken.END_ARRAY) {
/*      */ 
/*      */         
/* 1384 */         this._parsingContext = this._parsingContext.parentOrCopy();
/*      */       } 
/* 1386 */       return this._currToken;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String nextFieldName() throws IOException {
/* 1393 */       if (this._closed || this._segment == null) return null;
/*      */       
/* 1395 */       int ptr = this._segmentPtr + 1;
/* 1396 */       if (ptr < 16 && this._segment.type(ptr) == JsonToken.FIELD_NAME) {
/* 1397 */         this._segmentPtr = ptr;
/* 1398 */         Object ob = this._segment.get(ptr);
/* 1399 */         String name = (ob instanceof String) ? (String)ob : ob.toString();
/* 1400 */         this._parsingContext.setCurrentName(name);
/* 1401 */         return name;
/*      */       } 
/* 1403 */       return (nextToken() == JsonToken.FIELD_NAME) ? getCurrentName() : null;
/*      */     }
/*      */     
/*      */     public boolean isClosed() {
/* 1407 */       return this._closed;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JsonStreamContext getParsingContext() {
/* 1416 */       return this._parsingContext;
/*      */     }
/*      */     public JsonLocation getTokenLocation() {
/* 1419 */       return getCurrentLocation();
/*      */     }
/*      */     
/*      */     public JsonLocation getCurrentLocation() {
/* 1423 */       return (this._location == null) ? JsonLocation.NA : this._location;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getCurrentName() {
/* 1429 */       if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/* 1430 */         JsonStreamContext parent = this._parsingContext.getParent();
/* 1431 */         return parent.getCurrentName();
/*      */       } 
/* 1433 */       return this._parsingContext.getCurrentName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void overrideCurrentName(String name) {
/* 1440 */       JsonStreamContext ctxt = this._parsingContext;
/* 1441 */       if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/* 1442 */         ctxt = ctxt.getParent();
/*      */       }
/* 1444 */       if (ctxt instanceof TokenBufferReadContext) {
/*      */         try {
/* 1446 */           ((TokenBufferReadContext)ctxt).setCurrentName(name);
/* 1447 */         } catch (IOException e) {
/* 1448 */           throw new RuntimeException(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getText() {
/* 1463 */       if (this._currToken == JsonToken.VALUE_STRING || this._currToken == JsonToken.FIELD_NAME) {
/*      */         
/* 1465 */         Object ob = _currentObject();
/* 1466 */         if (ob instanceof String) {
/* 1467 */           return (String)ob;
/*      */         }
/* 1469 */         return ClassUtil.nullOrToString(ob);
/*      */       } 
/* 1471 */       if (this._currToken == null) {
/* 1472 */         return null;
/*      */       }
/* 1474 */       switch (this._currToken) {
/*      */         case null:
/*      */         case null:
/* 1477 */           return ClassUtil.nullOrToString(_currentObject());
/*      */       } 
/* 1479 */       return this._currToken.asString();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char[] getTextCharacters() {
/* 1485 */       String str = getText();
/* 1486 */       return (str == null) ? null : str.toCharArray();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTextLength() {
/* 1491 */       String str = getText();
/* 1492 */       return (str == null) ? 0 : str.length();
/*      */     }
/*      */     
/*      */     public int getTextOffset() {
/* 1496 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasTextCharacters() {
/* 1501 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isNaN() {
/* 1513 */       if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
/* 1514 */         Object value = _currentObject();
/* 1515 */         if (value instanceof Double) {
/* 1516 */           Double v = (Double)value;
/* 1517 */           return (v.isNaN() || v.isInfinite());
/*      */         } 
/* 1519 */         if (value instanceof Float) {
/* 1520 */           Float v = (Float)value;
/* 1521 */           return (v.isNaN() || v.isInfinite());
/*      */         } 
/*      */       } 
/* 1524 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BigInteger getBigIntegerValue() throws IOException {
/* 1530 */       Number n = getNumberValue();
/* 1531 */       if (n instanceof BigInteger) {
/* 1532 */         return (BigInteger)n;
/*      */       }
/* 1534 */       if (getNumberType() == JsonParser.NumberType.BIG_DECIMAL) {
/* 1535 */         return ((BigDecimal)n).toBigInteger();
/*      */       }
/*      */       
/* 1538 */       return BigInteger.valueOf(n.longValue());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BigDecimal getDecimalValue() throws IOException {
/* 1544 */       Number n = getNumberValue();
/* 1545 */       if (n instanceof BigDecimal) {
/* 1546 */         return (BigDecimal)n;
/*      */       }
/* 1548 */       switch (getNumberType()) {
/*      */         case INT:
/*      */         case LONG:
/* 1551 */           return BigDecimal.valueOf(n.longValue());
/*      */         case BIG_INTEGER:
/* 1553 */           return new BigDecimal((BigInteger)n);
/*      */       } 
/*      */ 
/*      */       
/* 1557 */       return BigDecimal.valueOf(n.doubleValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDoubleValue() throws IOException {
/* 1562 */       return getNumberValue().doubleValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloatValue() throws IOException {
/* 1567 */       return getNumberValue().floatValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIntValue() throws IOException {
/* 1574 */       if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/* 1575 */         return ((Number)_currentObject()).intValue();
/*      */       }
/* 1577 */       return getNumberValue().intValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() throws IOException {
/* 1582 */       return getNumberValue().longValue();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JsonParser.NumberType getNumberType() throws IOException {
/* 1588 */       Number n = getNumberValue();
/* 1589 */       if (n instanceof Integer) return JsonParser.NumberType.INT; 
/* 1590 */       if (n instanceof Long) return JsonParser.NumberType.LONG; 
/* 1591 */       if (n instanceof Double) return JsonParser.NumberType.DOUBLE; 
/* 1592 */       if (n instanceof BigDecimal) return JsonParser.NumberType.BIG_DECIMAL; 
/* 1593 */       if (n instanceof BigInteger) return JsonParser.NumberType.BIG_INTEGER; 
/* 1594 */       if (n instanceof Float) return JsonParser.NumberType.FLOAT; 
/* 1595 */       if (n instanceof Short) return JsonParser.NumberType.INT; 
/* 1596 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public final Number getNumberValue() throws IOException {
/* 1601 */       _checkIsNumber();
/* 1602 */       Object value = _currentObject();
/* 1603 */       if (value instanceof Number) {
/* 1604 */         return (Number)value;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1609 */       if (value instanceof String) {
/* 1610 */         String str = (String)value;
/* 1611 */         if (str.indexOf('.') >= 0) {
/* 1612 */           return Double.valueOf(Double.parseDouble(str));
/*      */         }
/* 1614 */         return Long.valueOf(Long.parseLong(str));
/*      */       } 
/* 1616 */       if (value == null) {
/* 1617 */         return null;
/*      */       }
/* 1619 */       throw new IllegalStateException("Internal error: entry should be a Number, but is of type " + value.getClass().getName());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getEmbeddedObject() {
/* 1632 */       if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 1633 */         return _currentObject();
/*      */       }
/* 1635 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
/* 1643 */       if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/*      */         
/* 1645 */         Object ob = _currentObject();
/* 1646 */         if (ob instanceof byte[]) {
/* 1647 */           return (byte[])ob;
/*      */         }
/*      */       } 
/*      */       
/* 1651 */       if (this._currToken != JsonToken.VALUE_STRING) {
/* 1652 */         throw _constructError("Current token (" + this._currToken + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), cannot access as binary");
/*      */       }
/* 1654 */       String str = getText();
/* 1655 */       if (str == null) {
/* 1656 */         return null;
/*      */       }
/* 1658 */       ByteArrayBuilder builder = this._byteBuilder;
/* 1659 */       if (builder == null) {
/* 1660 */         this._byteBuilder = builder = new ByteArrayBuilder(100);
/*      */       } else {
/* 1662 */         this._byteBuilder.reset();
/*      */       } 
/* 1664 */       _decodeBase64(str, builder, b64variant);
/* 1665 */       return builder.toByteArray();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
/* 1671 */       byte[] data = getBinaryValue(b64variant);
/* 1672 */       if (data != null) {
/* 1673 */         out.write(data, 0, data.length);
/* 1674 */         return data.length;
/*      */       } 
/* 1676 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canReadObjectId() {
/* 1687 */       return this._hasNativeObjectIds;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canReadTypeId() {
/* 1692 */       return this._hasNativeTypeIds;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getTypeId() {
/* 1697 */       return this._segment.findTypeId(this._segmentPtr);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getObjectId() {
/* 1702 */       return this._segment.findObjectId(this._segmentPtr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final Object _currentObject() {
/* 1712 */       return this._segment.get(this._segmentPtr);
/*      */     }
/*      */ 
/*      */     
/*      */     protected final void _checkIsNumber() throws JsonParseException {
/* 1717 */       if (this._currToken == null || !this._currToken.isNumeric()) {
/* 1718 */         throw _constructError("Current token (" + this._currToken + ") not numeric, cannot use numeric value accessors");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _handleEOF() throws JsonParseException {
/* 1724 */       _throwInternal();
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
/*      */   protected static final class Segment
/*      */   {
/*      */     public static final int TOKENS_PER_SEGMENT = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1746 */     private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16]; static {
/* 1747 */       JsonToken[] t = JsonToken.values();
/*      */       
/* 1749 */       System.arraycopy(t, 1, TOKEN_TYPES_BY_INDEX, 1, Math.min(15, t.length - 1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Segment _next;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected long _tokenTypes;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1767 */     protected final Object[] _tokens = new Object[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected TreeMap<Integer, Object> _nativeIds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JsonToken type(int index) {
/* 1780 */       long l = this._tokenTypes;
/* 1781 */       if (index > 0) {
/* 1782 */         l >>= index << 2;
/*      */       }
/* 1784 */       int ix = (int)l & 0xF;
/* 1785 */       return TOKEN_TYPES_BY_INDEX[ix];
/*      */     }
/*      */ 
/*      */     
/*      */     public int rawType(int index) {
/* 1790 */       long l = this._tokenTypes;
/* 1791 */       if (index > 0) {
/* 1792 */         l >>= index << 2;
/*      */       }
/* 1794 */       int ix = (int)l & 0xF;
/* 1795 */       return ix;
/*      */     }
/*      */     
/*      */     public Object get(int index) {
/* 1799 */       return this._tokens[index];
/*      */     }
/*      */     public Segment next() {
/* 1802 */       return this._next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasIds() {
/* 1809 */       return (this._nativeIds != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Segment append(int index, JsonToken tokenType) {
/* 1816 */       if (index < 16) {
/* 1817 */         set(index, tokenType);
/* 1818 */         return null;
/*      */       } 
/* 1820 */       this._next = new Segment();
/* 1821 */       this._next.set(0, tokenType);
/* 1822 */       return this._next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Segment append(int index, JsonToken tokenType, Object objectId, Object typeId) {
/* 1828 */       if (index < 16) {
/* 1829 */         set(index, tokenType, objectId, typeId);
/* 1830 */         return null;
/*      */       } 
/* 1832 */       this._next = new Segment();
/* 1833 */       this._next.set(0, tokenType, objectId, typeId);
/* 1834 */       return this._next;
/*      */     }
/*      */ 
/*      */     
/*      */     public Segment append(int index, JsonToken tokenType, Object value) {
/* 1839 */       if (index < 16) {
/* 1840 */         set(index, tokenType, value);
/* 1841 */         return null;
/*      */       } 
/* 1843 */       this._next = new Segment();
/* 1844 */       this._next.set(0, tokenType, value);
/* 1845 */       return this._next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Segment append(int index, JsonToken tokenType, Object value, Object objectId, Object typeId) {
/* 1851 */       if (index < 16) {
/* 1852 */         set(index, tokenType, value, objectId, typeId);
/* 1853 */         return null;
/*      */       } 
/* 1855 */       this._next = new Segment();
/* 1856 */       this._next.set(0, tokenType, value, objectId, typeId);
/* 1857 */       return this._next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void set(int index, JsonToken tokenType) {
/* 1911 */       long typeCode = tokenType.ordinal();
/* 1912 */       if (index > 0) {
/* 1913 */         typeCode <<= index << 2;
/*      */       }
/* 1915 */       this._tokenTypes |= typeCode;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void set(int index, JsonToken tokenType, Object objectId, Object typeId) {
/* 1921 */       long typeCode = tokenType.ordinal();
/* 1922 */       if (index > 0) {
/* 1923 */         typeCode <<= index << 2;
/*      */       }
/* 1925 */       this._tokenTypes |= typeCode;
/* 1926 */       assignNativeIds(index, objectId, typeId);
/*      */     }
/*      */ 
/*      */     
/*      */     private void set(int index, JsonToken tokenType, Object value) {
/* 1931 */       this._tokens[index] = value;
/* 1932 */       long typeCode = tokenType.ordinal();
/* 1933 */       if (index > 0) {
/* 1934 */         typeCode <<= index << 2;
/*      */       }
/* 1936 */       this._tokenTypes |= typeCode;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void set(int index, JsonToken tokenType, Object value, Object objectId, Object typeId) {
/* 1942 */       this._tokens[index] = value;
/* 1943 */       long typeCode = tokenType.ordinal();
/* 1944 */       if (index > 0) {
/* 1945 */         typeCode <<= index << 2;
/*      */       }
/* 1947 */       this._tokenTypes |= typeCode;
/* 1948 */       assignNativeIds(index, objectId, typeId);
/*      */     }
/*      */ 
/*      */     
/*      */     private final void assignNativeIds(int index, Object objectId, Object typeId) {
/* 1953 */       if (this._nativeIds == null) {
/* 1954 */         this._nativeIds = new TreeMap<>();
/*      */       }
/* 1956 */       if (objectId != null) {
/* 1957 */         this._nativeIds.put(Integer.valueOf(_objectIdIndex(index)), objectId);
/*      */       }
/* 1959 */       if (typeId != null) {
/* 1960 */         this._nativeIds.put(Integer.valueOf(_typeIdIndex(index)), typeId);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object findObjectId(int index) {
/* 1968 */       return (this._nativeIds == null) ? null : this._nativeIds.get(Integer.valueOf(_objectIdIndex(index)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object findTypeId(int index) {
/* 1975 */       return (this._nativeIds == null) ? null : this._nativeIds.get(Integer.valueOf(_typeIdIndex(index)));
/*      */     }
/*      */     
/* 1978 */     private final int _typeIdIndex(int i) { return i + i; } private final int _objectIdIndex(int i) {
/* 1979 */       return i + i + 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\TokenBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */