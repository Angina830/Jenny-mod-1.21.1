/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.FormatSchema;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonGeneratorDelegate
/*     */   extends JsonGenerator
/*     */ {
/*     */   protected JsonGenerator delegate;
/*     */   protected boolean delegateCopyMethods;
/*     */   
/*     */   public JsonGeneratorDelegate(JsonGenerator d) {
/*  33 */     this(d, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGeneratorDelegate(JsonGenerator d, boolean delegateCopyMethods) {
/*  42 */     this.delegate = d;
/*  43 */     this.delegateCopyMethods = delegateCopyMethods;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getCurrentValue() {
/*  48 */     return this.delegate.getCurrentValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentValue(Object v) {
/*  53 */     this.delegate.setCurrentValue(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGenerator getDelegate() {
/*  62 */     return this.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectCodec getCodec() {
/*  70 */     return this.delegate.getCodec();
/*     */   }
/*     */   public JsonGenerator setCodec(ObjectCodec oc) {
/*  73 */     this.delegate.setCodec(oc);
/*  74 */     return this;
/*     */   }
/*     */   
/*  77 */   public void setSchema(FormatSchema schema) { this.delegate.setSchema(schema); }
/*  78 */   public FormatSchema getSchema() { return this.delegate.getSchema(); }
/*  79 */   public Version version() { return this.delegate.version(); }
/*  80 */   public Object getOutputTarget() { return this.delegate.getOutputTarget(); } public int getOutputBuffered() {
/*  81 */     return this.delegate.getOutputBuffered();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseSchema(FormatSchema schema) {
/*  90 */     return this.delegate.canUseSchema(schema);
/*     */   }
/*     */   public boolean canWriteTypeId() {
/*  93 */     return this.delegate.canWriteTypeId();
/*     */   }
/*     */   public boolean canWriteObjectId() {
/*  96 */     return this.delegate.canWriteObjectId();
/*     */   }
/*     */   public boolean canWriteBinaryNatively() {
/*  99 */     return this.delegate.canWriteBinaryNatively();
/*     */   }
/*     */   public boolean canOmitFields() {
/* 102 */     return this.delegate.canOmitFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGenerator enable(JsonGenerator.Feature f) {
/* 112 */     this.delegate.enable(f);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator disable(JsonGenerator.Feature f) {
/* 118 */     this.delegate.disable(f);
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isEnabled(JsonGenerator.Feature f) {
/* 123 */     return this.delegate.isEnabled(f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFeatureMask() {
/* 129 */     return this.delegate.getFeatureMask();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public JsonGenerator setFeatureMask(int mask) {
/* 134 */     this.delegate.setFeatureMask(mask);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator overrideStdFeatures(int values, int mask) {
/* 140 */     this.delegate.overrideStdFeatures(values, mask);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator overrideFormatFeatures(int values, int mask) {
/* 146 */     this.delegate.overrideFormatFeatures(values, mask);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGenerator setPrettyPrinter(PrettyPrinter pp) {
/* 158 */     this.delegate.setPrettyPrinter(pp);
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public PrettyPrinter getPrettyPrinter() {
/* 163 */     return this.delegate.getPrettyPrinter();
/*     */   }
/*     */   public JsonGenerator useDefaultPrettyPrinter() {
/* 166 */     this.delegate.useDefaultPrettyPrinter();
/* 167 */     return this;
/*     */   }
/*     */   public JsonGenerator setHighestNonEscapedChar(int charCode) {
/* 170 */     this.delegate.setHighestNonEscapedChar(charCode);
/* 171 */     return this;
/*     */   }
/*     */   public int getHighestEscapedChar() {
/* 174 */     return this.delegate.getHighestEscapedChar();
/*     */   }
/*     */   public CharacterEscapes getCharacterEscapes() {
/* 177 */     return this.delegate.getCharacterEscapes();
/*     */   }
/*     */   public JsonGenerator setCharacterEscapes(CharacterEscapes esc) {
/* 180 */     this.delegate.setCharacterEscapes(esc);
/* 181 */     return this;
/*     */   }
/*     */   public JsonGenerator setRootValueSeparator(SerializableString sep) {
/* 184 */     this.delegate.setRootValueSeparator(sep);
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartArray() throws IOException {
/* 194 */     this.delegate.writeStartArray();
/*     */   }
/*     */   public void writeStartArray(int size) throws IOException {
/* 197 */     this.delegate.writeStartArray(size);
/*     */   }
/*     */   public void writeEndArray() throws IOException {
/* 200 */     this.delegate.writeEndArray();
/*     */   }
/*     */   public void writeStartObject() throws IOException {
/* 203 */     this.delegate.writeStartObject();
/*     */   }
/*     */   public void writeStartObject(Object forValue) throws IOException {
/* 206 */     this.delegate.writeStartObject(forValue);
/*     */   }
/*     */   public void writeEndObject() throws IOException {
/* 209 */     this.delegate.writeEndObject();
/*     */   }
/*     */   
/*     */   public void writeFieldName(String name) throws IOException {
/* 213 */     this.delegate.writeFieldName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFieldName(SerializableString name) throws IOException {
/* 218 */     this.delegate.writeFieldName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFieldId(long id) throws IOException {
/* 223 */     this.delegate.writeFieldId(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeArray(int[] array, int offset, int length) throws IOException {
/* 228 */     this.delegate.writeArray(array, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeArray(long[] array, int offset, int length) throws IOException {
/* 233 */     this.delegate.writeArray(array, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeArray(double[] array, int offset, int length) throws IOException {
/* 238 */     this.delegate.writeArray(array, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeString(String text) throws IOException {
/* 248 */     this.delegate.writeString(text);
/*     */   }
/*     */   
/*     */   public void writeString(Reader reader, int len) throws IOException {
/* 252 */     this.delegate.writeString(reader, len);
/*     */   }
/*     */   
/*     */   public void writeString(char[] text, int offset, int len) throws IOException {
/* 256 */     this.delegate.writeString(text, offset, len);
/*     */   }
/*     */   public void writeString(SerializableString text) throws IOException {
/* 259 */     this.delegate.writeString(text);
/*     */   }
/*     */   public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
/* 262 */     this.delegate.writeRawUTF8String(text, offset, length);
/*     */   }
/*     */   public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
/* 265 */     this.delegate.writeUTF8String(text, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeRaw(String text) throws IOException {
/* 274 */     this.delegate.writeRaw(text);
/*     */   }
/*     */   public void writeRaw(String text, int offset, int len) throws IOException {
/* 277 */     this.delegate.writeRaw(text, offset, len);
/*     */   }
/*     */   public void writeRaw(SerializableString raw) throws IOException {
/* 280 */     this.delegate.writeRaw(raw);
/*     */   }
/*     */   public void writeRaw(char[] text, int offset, int len) throws IOException {
/* 283 */     this.delegate.writeRaw(text, offset, len);
/*     */   }
/*     */   public void writeRaw(char c) throws IOException {
/* 286 */     this.delegate.writeRaw(c);
/*     */   }
/*     */   public void writeRawValue(String text) throws IOException {
/* 289 */     this.delegate.writeRawValue(text);
/*     */   }
/*     */   public void writeRawValue(String text, int offset, int len) throws IOException {
/* 292 */     this.delegate.writeRawValue(text, offset, len);
/*     */   }
/*     */   public void writeRawValue(char[] text, int offset, int len) throws IOException {
/* 295 */     this.delegate.writeRawValue(text, offset, len);
/*     */   }
/*     */   public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException {
/* 298 */     this.delegate.writeBinary(b64variant, data, offset, len);
/*     */   }
/*     */   public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) throws IOException {
/* 301 */     return this.delegate.writeBinary(b64variant, data, dataLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNumber(short v) throws IOException {
/* 310 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(int v) throws IOException {
/* 313 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(long v) throws IOException {
/* 316 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(BigInteger v) throws IOException {
/* 319 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(double v) throws IOException {
/* 322 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(float v) throws IOException {
/* 325 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(BigDecimal v) throws IOException {
/* 328 */     this.delegate.writeNumber(v);
/*     */   }
/*     */   public void writeNumber(String encodedValue) throws IOException, UnsupportedOperationException {
/* 331 */     this.delegate.writeNumber(encodedValue);
/*     */   }
/*     */   public void writeBoolean(boolean state) throws IOException {
/* 334 */     this.delegate.writeBoolean(state);
/*     */   }
/*     */   public void writeNull() throws IOException {
/* 337 */     this.delegate.writeNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeOmittedField(String fieldName) throws IOException {
/* 346 */     this.delegate.writeOmittedField(fieldName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeObjectId(Object id) throws IOException {
/* 355 */     this.delegate.writeObjectId(id);
/*     */   }
/*     */   public void writeObjectRef(Object id) throws IOException {
/* 358 */     this.delegate.writeObjectRef(id);
/*     */   }
/*     */   public void writeTypeId(Object id) throws IOException {
/* 361 */     this.delegate.writeTypeId(id);
/*     */   }
/*     */   public void writeEmbeddedObject(Object object) throws IOException {
/* 364 */     this.delegate.writeEmbeddedObject(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeObject(Object pojo) throws IOException {
/* 374 */     if (this.delegateCopyMethods) {
/* 375 */       this.delegate.writeObject(pojo);
/*     */       return;
/*     */     } 
/* 378 */     if (pojo == null) {
/* 379 */       writeNull();
/*     */     } else {
/* 381 */       ObjectCodec c = getCodec();
/* 382 */       if (c != null) {
/* 383 */         c.writeValue(this, pojo);
/*     */         return;
/*     */       } 
/* 386 */       _writeSimpleObject(pojo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTree(TreeNode tree) throws IOException {
/* 392 */     if (this.delegateCopyMethods) {
/* 393 */       this.delegate.writeTree(tree);
/*     */       
/*     */       return;
/*     */     } 
/* 397 */     if (tree == null) {
/* 398 */       writeNull();
/*     */     } else {
/* 400 */       ObjectCodec c = getCodec();
/* 401 */       if (c == null) {
/* 402 */         throw new IllegalStateException("No ObjectCodec defined");
/*     */       }
/* 404 */       c.writeTree(this, tree);
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
/*     */   public void copyCurrentEvent(JsonParser p) throws IOException {
/* 424 */     if (this.delegateCopyMethods) { this.delegate.copyCurrentEvent(p); }
/* 425 */     else { super.copyCurrentEvent(p); }
/*     */   
/*     */   }
/*     */   
/*     */   public void copyCurrentStructure(JsonParser p) throws IOException {
/* 430 */     if (this.delegateCopyMethods) { this.delegate.copyCurrentStructure(p); }
/* 431 */     else { super.copyCurrentStructure(p); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonStreamContext getOutputContext() {
/* 440 */     return this.delegate.getOutputContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 448 */     this.delegate.flush(); } public void close() throws IOException {
/* 449 */     this.delegate.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 457 */     return this.delegate.isClosed();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\JsonGeneratorDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */