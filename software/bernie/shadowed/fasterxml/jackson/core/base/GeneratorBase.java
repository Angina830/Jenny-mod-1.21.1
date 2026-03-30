/*     */ package software.bernie.shadowed.fasterxml.jackson.core.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigDecimal;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.json.DupDetector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.json.JsonWriteContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.json.PackageVersion;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GeneratorBase
/*     */   extends JsonGenerator
/*     */ {
/*     */   public static final int SURR1_FIRST = 55296;
/*     */   public static final int SURR1_LAST = 56319;
/*     */   public static final int SURR2_FIRST = 56320;
/*     */   public static final int SURR2_LAST = 57343;
/*  31 */   protected static final int DERIVED_FEATURES_MASK = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | JsonGenerator.Feature.ESCAPE_NON_ASCII.getMask() | JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.getMask();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String WRITE_BINARY = "write a binary value";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String WRITE_BOOLEAN = "write a boolean value";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String WRITE_NULL = "write a null";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String WRITE_NUMBER = "write a number";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String WRITE_RAW = "write a raw (unencoded) value";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String WRITE_STRING = "write a string";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int MAX_BIG_DECIMAL_SCALE = 9999;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectCodec _objectCodec;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _features;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _cfgNumbersAsStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonWriteContext _writeContext;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _closed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GeneratorBase(int features, ObjectCodec codec) {
/* 105 */     this._features = features;
/* 106 */     this._objectCodec = codec;
/* 107 */     DupDetector dups = JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(features) ? DupDetector.rootDetector(this) : null;
/*     */     
/* 109 */     this._writeContext = JsonWriteContext.createRootContext(dups);
/* 110 */     this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GeneratorBase(int features, ObjectCodec codec, JsonWriteContext ctxt) {
/* 118 */     this._features = features;
/* 119 */     this._objectCodec = codec;
/* 120 */     this._writeContext = ctxt;
/* 121 */     this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Version version() {
/* 129 */     return PackageVersion.VERSION;
/*     */   }
/*     */   
/*     */   public Object getCurrentValue() {
/* 133 */     return this._writeContext.getCurrentValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentValue(Object v) {
/* 138 */     this._writeContext.setCurrentValue(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isEnabled(JsonGenerator.Feature f) {
/* 148 */     return ((this._features & f.getMask()) != 0); } public int getFeatureMask() {
/* 149 */     return this._features;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGenerator enable(JsonGenerator.Feature f) {
/* 155 */     int mask = f.getMask();
/* 156 */     this._features |= mask;
/* 157 */     if ((mask & DERIVED_FEATURES_MASK) != 0)
/*     */     {
/* 159 */       if (f == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
/* 160 */         this._cfgNumbersAsStrings = true;
/* 161 */       } else if (f == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
/* 162 */         setHighestNonEscapedChar(127);
/* 163 */       } else if (f == JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION && 
/* 164 */         this._writeContext.getDupDetector() == null) {
/* 165 */         this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
/*     */       } 
/*     */     }
/*     */     
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator disable(JsonGenerator.Feature f) {
/* 174 */     int mask = f.getMask();
/* 175 */     this._features &= mask ^ 0xFFFFFFFF;
/* 176 */     if ((mask & DERIVED_FEATURES_MASK) != 0) {
/* 177 */       if (f == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
/* 178 */         this._cfgNumbersAsStrings = false;
/* 179 */       } else if (f == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
/* 180 */         setHighestNonEscapedChar(0);
/* 181 */       } else if (f == JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION) {
/* 182 */         this._writeContext = this._writeContext.withDupDetector(null);
/*     */       } 
/*     */     }
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonGenerator setFeatureMask(int newMask) {
/* 191 */     int changed = newMask ^ this._features;
/* 192 */     this._features = newMask;
/* 193 */     if (changed != 0) {
/* 194 */       _checkStdFeatureChanges(newMask, changed);
/*     */     }
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator overrideStdFeatures(int values, int mask) {
/* 201 */     int oldState = this._features;
/* 202 */     int newState = oldState & (mask ^ 0xFFFFFFFF) | values & mask;
/* 203 */     int changed = oldState ^ newState;
/* 204 */     if (changed != 0) {
/* 205 */       this._features = newState;
/* 206 */       _checkStdFeatureChanges(newState, changed);
/*     */     } 
/* 208 */     return this;
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
/*     */   protected void _checkStdFeatureChanges(int newFeatureFlags, int changedFeatures) {
/* 222 */     if ((changedFeatures & DERIVED_FEATURES_MASK) == 0) {
/*     */       return;
/*     */     }
/* 225 */     this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(newFeatureFlags);
/* 226 */     if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(changedFeatures)) {
/* 227 */       if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(newFeatureFlags)) {
/* 228 */         setHighestNonEscapedChar(127);
/*     */       } else {
/* 230 */         setHighestNonEscapedChar(0);
/*     */       } 
/*     */     }
/* 233 */     if (JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(changedFeatures)) {
/* 234 */       if (JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(newFeatureFlags)) {
/* 235 */         if (this._writeContext.getDupDetector() == null) {
/* 236 */           this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
/*     */         }
/*     */       } else {
/* 239 */         this._writeContext = this._writeContext.withDupDetector(null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator useDefaultPrettyPrinter() {
/* 246 */     if (getPrettyPrinter() != null) {
/* 247 */       return this;
/*     */     }
/* 249 */     return setPrettyPrinter(_constructDefaultPrettyPrinter());
/*     */   }
/*     */   
/*     */   public JsonGenerator setCodec(ObjectCodec oc) {
/* 253 */     this._objectCodec = oc;
/* 254 */     return this;
/*     */   }
/*     */   public ObjectCodec getCodec() {
/* 257 */     return this._objectCodec;
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
/*     */   public JsonStreamContext getOutputContext() {
/* 270 */     return (JsonStreamContext)this._writeContext;
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
/*     */   public void writeStartObject(Object forValue) throws IOException {
/* 286 */     writeStartObject();
/* 287 */     if (this._writeContext != null && forValue != null) {
/* 288 */       this._writeContext.setCurrentValue(forValue);
/*     */     }
/* 290 */     setCurrentValue(forValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFieldName(SerializableString name) throws IOException {
/* 300 */     writeFieldName(name.getValue());
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
/*     */   public void writeString(SerializableString text) throws IOException {
/* 315 */     writeString(text.getValue());
/*     */   }
/*     */   
/*     */   public void writeRawValue(String text) throws IOException {
/* 319 */     _verifyValueWrite("write raw value");
/* 320 */     writeRaw(text);
/*     */   }
/*     */   
/*     */   public void writeRawValue(String text, int offset, int len) throws IOException {
/* 324 */     _verifyValueWrite("write raw value");
/* 325 */     writeRaw(text, offset, len);
/*     */   }
/*     */   
/*     */   public void writeRawValue(char[] text, int offset, int len) throws IOException {
/* 329 */     _verifyValueWrite("write raw value");
/* 330 */     writeRaw(text, offset, len);
/*     */   }
/*     */   
/*     */   public void writeRawValue(SerializableString text) throws IOException {
/* 334 */     _verifyValueWrite("write raw value");
/* 335 */     writeRaw(text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) throws IOException {
/* 341 */     _reportUnsupportedOperation();
/* 342 */     return 0;
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
/*     */   public void writeObject(Object value) throws IOException {
/* 371 */     if (value == null) {
/*     */       
/* 373 */       writeNull();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 380 */       if (this._objectCodec != null) {
/* 381 */         this._objectCodec.writeValue(this, value);
/*     */         return;
/*     */       } 
/* 384 */       _writeSimpleObject(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTree(TreeNode rootNode) throws IOException {
/* 391 */     if (rootNode == null) {
/* 392 */       writeNull();
/*     */     } else {
/* 394 */       if (this._objectCodec == null) {
/* 395 */         throw new IllegalStateException("No ObjectCodec defined");
/*     */       }
/* 397 */       this._objectCodec.writeValue(this, rootNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void flush() throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 408 */     this._closed = true; } public boolean isClosed() {
/* 409 */     return this._closed;
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
/*     */   protected abstract void _releaseBuffers();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void _verifyValueWrite(String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PrettyPrinter _constructDefaultPrettyPrinter() {
/* 440 */     return (PrettyPrinter)new DefaultPrettyPrinter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _asString(BigDecimal value) throws IOException {
/* 450 */     if (JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._features)) {
/*     */       
/* 452 */       int scale = value.scale();
/* 453 */       if (scale < -9999 || scale > 9999) {
/* 454 */         _reportError(String.format("Attempt to write plain `java.math.BigDecimal` (see JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) with illegal scale (%d): needs to be between [-%d, %d]", new Object[] { Integer.valueOf(scale), Integer.valueOf(9999), Integer.valueOf(9999) }));
/*     */       }
/*     */ 
/*     */       
/* 458 */       return value.toPlainString();
/*     */     } 
/* 460 */     return value.toString();
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
/*     */   protected final int _decodeSurrogate(int surr1, int surr2) throws IOException {
/* 475 */     if (surr2 < 56320 || surr2 > 57343) {
/* 476 */       String msg = "Incomplete surrogate pair: first char 0x" + Integer.toHexString(surr1) + ", second 0x" + Integer.toHexString(surr2);
/* 477 */       _reportError(msg);
/*     */     } 
/* 479 */     int c = 65536 + (surr1 - 55296 << 10) + surr2 - 56320;
/* 480 */     return c;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\base\GeneratorBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */