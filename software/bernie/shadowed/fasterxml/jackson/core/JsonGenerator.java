/*      */ package software.bernie.shadowed.fasterxml.jackson.core;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.Flushable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JsonGenerator
/*      */   implements Closeable, Flushable, Versioned
/*      */ {
/*      */   protected PrettyPrinter _cfgPrettyPrinter;
/*      */   
/*      */   public abstract JsonGenerator setCodec(ObjectCodec paramObjectCodec);
/*      */   
/*      */   public abstract ObjectCodec getCodec();
/*      */   
/*      */   public abstract Version version();
/*      */   
/*      */   public abstract JsonGenerator enable(Feature paramFeature);
/*      */   
/*      */   public abstract JsonGenerator disable(Feature paramFeature);
/*      */   
/*      */   public enum Feature
/*      */   {
/*   51 */     AUTO_CLOSE_TARGET(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   63 */     AUTO_CLOSE_JSON_CONTENT(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   76 */     FLUSH_PASSED_TO_STREAM(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   89 */     QUOTE_FIELD_NAMES(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     QUOTE_NON_NUMERIC_NUMBERS(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  120 */     WRITE_NUMBERS_AS_STRINGS(false),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  132 */     WRITE_BIGDECIMAL_AS_PLAIN(false),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  149 */     ESCAPE_NON_ASCII(false),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  192 */     STRICT_DUPLICATE_DETECTION(false),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  214 */     IGNORE_UNKNOWN(false);
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean _defaultState;
/*      */ 
/*      */     
/*      */     private final int _mask;
/*      */ 
/*      */ 
/*      */     
/*      */     public static int collectDefaults() {
/*  226 */       int flags = 0;
/*  227 */       for (Feature f : values()) {
/*  228 */         if (f.enabledByDefault()) {
/*  229 */           flags |= f.getMask();
/*      */         }
/*      */       } 
/*  232 */       return flags;
/*      */     }
/*      */     
/*      */     Feature(boolean defaultState) {
/*  236 */       this._defaultState = defaultState;
/*  237 */       this._mask = 1 << ordinal();
/*      */     }
/*      */     public boolean enabledByDefault() {
/*  240 */       return this._defaultState;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean enabledIn(int flags) {
/*  245 */       return ((flags & this._mask) != 0);
/*      */     } public int getMask() {
/*  247 */       return this._mask;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonGenerator configure(Feature f, boolean state) {
/*  322 */     if (state) { enable(f); } else { disable(f); }
/*  323 */      return this;
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
/*      */   public abstract boolean isEnabled(Feature paramFeature);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getFeatureMask();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public abstract JsonGenerator setFeatureMask(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonGenerator overrideStdFeatures(int values, int mask) {
/*  373 */     int oldState = getFeatureMask();
/*  374 */     int newState = oldState & (mask ^ 0xFFFFFFFF) | values & mask;
/*  375 */     return setFeatureMask(newState);
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
/*      */   public int getFormatFeatures() {
/*  387 */     return 0;
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
/*      */   public JsonGenerator overrideFormatFeatures(int values, int mask) {
/*  404 */     throw new IllegalArgumentException("No FormatFeatures defined for generator of type " + getClass().getName());
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
/*      */   public void setSchema(FormatSchema schema) {
/*  434 */     throw new UnsupportedOperationException("Generator of type " + getClass().getName() + " does not support schema of type '" + schema.getSchemaType() + "'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FormatSchema getSchema() {
/*  444 */     return null;
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
/*      */   public JsonGenerator setPrettyPrinter(PrettyPrinter pp) {
/*  464 */     this._cfgPrettyPrinter = pp;
/*  465 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter getPrettyPrinter() {
/*  475 */     return this._cfgPrettyPrinter;
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
/*      */   public abstract JsonGenerator useDefaultPrettyPrinter();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonGenerator setHighestNonEscapedChar(int charCode) {
/*  508 */     return this;
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
/*      */   public int getHighestEscapedChar() {
/*  522 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CharacterEscapes getCharacterEscapes() {
/*  528 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonGenerator setCharacterEscapes(CharacterEscapes esc) {
/*  536 */     return this;
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
/*      */   public JsonGenerator setRootValueSeparator(SerializableString sep) {
/*  550 */     throw new UnsupportedOperationException();
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
/*      */   public Object getOutputTarget() {
/*  575 */     return null;
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
/*      */   public int getOutputBuffered() {
/*  597 */     return -1;
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
/*      */   public Object getCurrentValue() {
/*  614 */     JsonStreamContext ctxt = getOutputContext();
/*  615 */     return (ctxt == null) ? null : ctxt.getCurrentValue();
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
/*      */   public void setCurrentValue(Object v) {
/*  627 */     JsonStreamContext ctxt = getOutputContext();
/*  628 */     if (ctxt != null) {
/*  629 */       ctxt.setCurrentValue(v);
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
/*      */   public boolean canUseSchema(FormatSchema schema) {
/*  647 */     return false;
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
/*      */   public boolean canWriteObjectId() {
/*  663 */     return false;
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
/*      */   public boolean canWriteTypeId() {
/*  679 */     return false;
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
/*      */   public boolean canWriteBinaryNatively() {
/*  691 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canOmitFields() {
/*  701 */     return true;
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
/*      */   public boolean canWriteFormattedNumbers() {
/*  715 */     return false;
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
/*      */   public abstract void writeStartArray() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartArray(int size) throws IOException {
/*  750 */     writeStartArray();
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
/*      */   public abstract void writeEndArray() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeStartObject() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartObject(Object forValue) throws IOException {
/*  790 */     writeStartObject();
/*  791 */     setCurrentValue(forValue);
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
/*      */   public abstract void writeEndObject() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeFieldName(String paramString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeFieldName(SerializableString paramSerializableString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFieldId(long id) throws IOException {
/*  841 */     writeFieldName(Long.toString(id));
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
/*      */   public void writeArray(int[] array, int offset, int length) throws IOException {
/*  863 */     if (array == null) {
/*  864 */       throw new IllegalArgumentException("null array");
/*      */     }
/*  866 */     _verifyOffsets(array.length, offset, length);
/*  867 */     writeStartArray();
/*  868 */     for (int i = offset, end = offset + length; i < end; i++) {
/*  869 */       writeNumber(array[i]);
/*      */     }
/*  871 */     writeEndArray();
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
/*      */   public void writeArray(long[] array, int offset, int length) throws IOException {
/*  887 */     if (array == null) {
/*  888 */       throw new IllegalArgumentException("null array");
/*      */     }
/*  890 */     _verifyOffsets(array.length, offset, length);
/*  891 */     writeStartArray();
/*  892 */     for (int i = offset, end = offset + length; i < end; i++) {
/*  893 */       writeNumber(array[i]);
/*      */     }
/*  895 */     writeEndArray();
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
/*      */   public void writeArray(double[] array, int offset, int length) throws IOException {
/*  911 */     if (array == null) {
/*  912 */       throw new IllegalArgumentException("null array");
/*      */     }
/*  914 */     _verifyOffsets(array.length, offset, length);
/*  915 */     writeStartArray();
/*  916 */     for (int i = offset, end = offset + length; i < end; i++) {
/*  917 */       writeNumber(array[i]);
/*      */     }
/*  919 */     writeEndArray();
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
/*      */   public abstract void writeString(String paramString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeString(Reader reader, int len) throws IOException {
/*  951 */     _reportUnsupportedOperation();
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
/*      */   public abstract void writeString(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeString(SerializableString paramSerializableString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRawUTF8String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeUTF8String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRaw(String paramString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRaw(String paramString, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRaw(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRaw(char paramChar) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(SerializableString raw) throws IOException {
/* 1095 */     writeRaw(raw.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRawValue(String paramString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRawValue(String paramString, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeRawValue(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRawValue(SerializableString raw) throws IOException {
/* 1120 */     writeRawValue(raw.getValue());
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
/*      */   public abstract void writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBinary(byte[] data, int offset, int len) throws IOException {
/* 1153 */     writeBinary(Base64Variants.getDefaultVariant(), data, offset, len);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBinary(byte[] data) throws IOException {
/* 1163 */     writeBinary(Base64Variants.getDefaultVariant(), data, 0, data.length);
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
/*      */   public int writeBinary(InputStream data, int dataLength) throws IOException {
/* 1181 */     return writeBinary(Base64Variants.getDefaultVariant(), data, dataLength);
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
/*      */   public abstract int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNumber(short v) throws IOException {
/* 1225 */     writeNumber(v);
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
/*      */   public abstract void writeNumber(int paramInt) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNumber(long paramLong) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNumber(BigInteger paramBigInteger) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNumber(double paramDouble) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNumber(float paramFloat) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNumber(BigDecimal paramBigDecimal) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNumber(String paramString) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeBoolean(boolean paramBoolean) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNull() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEmbeddedObject(Object object) throws IOException {
/* 1349 */     if (object == null) {
/* 1350 */       writeNull();
/*      */       return;
/*      */     } 
/* 1353 */     if (object instanceof byte[]) {
/* 1354 */       writeBinary((byte[])object);
/*      */       return;
/*      */     } 
/* 1357 */     throw new JsonGenerationException("No native support for writing embedded objects of type " + object.getClass().getName(), this);
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
/*      */   public void writeObjectId(Object id) throws IOException {
/* 1380 */     throw new JsonGenerationException("No native support for writing Object Ids", this);
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
/*      */   public void writeObjectRef(Object id) throws IOException {
/* 1393 */     throw new JsonGenerationException("No native support for writing Object Ids", this);
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
/*      */   public void writeTypeId(Object id) throws IOException {
/* 1408 */     throw new JsonGenerationException("No native support for writing Type Ids", this);
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
/*      */   public WritableTypeId writeTypePrefix(WritableTypeId typeIdDef) throws IOException {
/* 1427 */     Object id = typeIdDef.id;
/*      */     
/* 1429 */     JsonToken valueShape = typeIdDef.valueShape;
/* 1430 */     if (canWriteTypeId()) {
/* 1431 */       typeIdDef.wrapperWritten = false;
/*      */       
/* 1433 */       writeTypeId(id);
/*      */     }
/*      */     else {
/*      */       
/* 1437 */       String idStr = (id instanceof String) ? (String)id : String.valueOf(id);
/* 1438 */       typeIdDef.wrapperWritten = true;
/*      */       
/* 1440 */       WritableTypeId.Inclusion incl = typeIdDef.include;
/*      */       
/* 1442 */       if (valueShape != JsonToken.START_OBJECT && incl.requiresObjectContext())
/*      */       {
/* 1444 */         typeIdDef.include = incl = WritableTypeId.Inclusion.WRAPPER_ARRAY;
/*      */       }
/*      */       
/* 1447 */       switch (incl) {
/*      */         case PARENT_PROPERTY:
/*      */         case PAYLOAD_PROPERTY:
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case METADATA_PROPERTY:
/* 1459 */           writeStartObject(typeIdDef.forValue);
/* 1460 */           writeStringField(typeIdDef.asProperty, idStr);
/* 1461 */           return typeIdDef;
/*      */ 
/*      */         
/*      */         case WRAPPER_OBJECT:
/* 1465 */           writeStartObject();
/* 1466 */           writeFieldName(idStr);
/*      */           break;
/*      */         
/*      */         default:
/* 1470 */           writeStartArray();
/* 1471 */           writeString(idStr);
/*      */           break;
/*      */       } 
/*      */     } 
/* 1475 */     if (valueShape == JsonToken.START_OBJECT) {
/* 1476 */       writeStartObject(typeIdDef.forValue);
/* 1477 */     } else if (valueShape == JsonToken.START_ARRAY) {
/*      */       
/* 1479 */       writeStartArray();
/*      */     } 
/* 1481 */     return typeIdDef;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableTypeId writeTypeSuffix(WritableTypeId typeIdDef) throws IOException {
/* 1489 */     JsonToken valueShape = typeIdDef.valueShape;
/*      */     
/* 1491 */     if (valueShape == JsonToken.START_OBJECT) {
/* 1492 */       writeEndObject();
/* 1493 */     } else if (valueShape == JsonToken.START_ARRAY) {
/* 1494 */       writeEndArray();
/*      */     } 
/*      */     
/* 1497 */     if (typeIdDef.wrapperWritten) {
/* 1498 */       Object id; String idStr; switch (typeIdDef.include) {
/*      */         case WRAPPER_ARRAY:
/* 1500 */           writeEndArray();
/*      */ 
/*      */ 
/*      */         
/*      */         case PARENT_PROPERTY:
/* 1505 */           id = typeIdDef.id;
/* 1506 */           idStr = (id instanceof String) ? (String)id : String.valueOf(id);
/* 1507 */           writeStringField(typeIdDef.asProperty, idStr);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case PAYLOAD_PROPERTY:
/*      */         case METADATA_PROPERTY:
/* 1520 */           return typeIdDef;
/*      */       } 
/*      */       writeEndObject();
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
/*      */   public abstract void writeObject(Object paramObject) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeTree(TreeNode paramTreeNode) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStringField(String fieldName, String value) throws IOException {
/* 1569 */     writeFieldName(fieldName);
/* 1570 */     writeString(value);
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
/*      */   public final void writeBooleanField(String fieldName, boolean value) throws IOException {
/* 1582 */     writeFieldName(fieldName);
/* 1583 */     writeBoolean(value);
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
/*      */   public final void writeNullField(String fieldName) throws IOException {
/* 1595 */     writeFieldName(fieldName);
/* 1596 */     writeNull();
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
/*      */   public final void writeNumberField(String fieldName, int value) throws IOException {
/* 1608 */     writeFieldName(fieldName);
/* 1609 */     writeNumber(value);
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
/*      */   public final void writeNumberField(String fieldName, long value) throws IOException {
/* 1621 */     writeFieldName(fieldName);
/* 1622 */     writeNumber(value);
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
/*      */   public final void writeNumberField(String fieldName, double value) throws IOException {
/* 1634 */     writeFieldName(fieldName);
/* 1635 */     writeNumber(value);
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
/*      */   public final void writeNumberField(String fieldName, float value) throws IOException {
/* 1647 */     writeFieldName(fieldName);
/* 1648 */     writeNumber(value);
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
/*      */   public final void writeNumberField(String fieldName, BigDecimal value) throws IOException {
/* 1661 */     writeFieldName(fieldName);
/* 1662 */     writeNumber(value);
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
/*      */   public final void writeBinaryField(String fieldName, byte[] data) throws IOException {
/* 1675 */     writeFieldName(fieldName);
/* 1676 */     writeBinary(data);
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
/*      */   public final void writeArrayFieldStart(String fieldName) throws IOException {
/* 1693 */     writeFieldName(fieldName);
/* 1694 */     writeStartArray();
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
/*      */   public final void writeObjectFieldStart(String fieldName) throws IOException {
/* 1711 */     writeFieldName(fieldName);
/* 1712 */     writeStartObject();
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
/*      */   public final void writeObjectField(String fieldName, Object pojo) throws IOException {
/* 1725 */     writeFieldName(fieldName);
/* 1726 */     writeObject(pojo);
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
/*      */   public void writeOmittedField(String fieldName) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     JsonParser.NumberType n;
/* 1758 */     JsonToken t = p.currentToken();
/*      */     
/* 1760 */     if (t == null) {
/* 1761 */       _reportError("No current event to copy");
/*      */     }
/* 1763 */     switch (t.id()) {
/*      */       case -1:
/* 1765 */         _reportError("No current event to copy");
/*      */         return;
/*      */       case 1:
/* 1768 */         writeStartObject();
/*      */         return;
/*      */       case 2:
/* 1771 */         writeEndObject();
/*      */         return;
/*      */       case 3:
/* 1774 */         writeStartArray();
/*      */         return;
/*      */       case 4:
/* 1777 */         writeEndArray();
/*      */         return;
/*      */       case 5:
/* 1780 */         writeFieldName(p.getCurrentName());
/*      */         return;
/*      */       case 6:
/* 1783 */         if (p.hasTextCharacters()) {
/* 1784 */           writeString(p.getTextCharacters(), p.getTextOffset(), p.getTextLength());
/*      */         } else {
/* 1786 */           writeString(p.getText());
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 7:
/* 1791 */         n = p.getNumberType();
/* 1792 */         if (n == JsonParser.NumberType.INT) {
/* 1793 */           writeNumber(p.getIntValue());
/* 1794 */         } else if (n == JsonParser.NumberType.BIG_INTEGER) {
/* 1795 */           writeNumber(p.getBigIntegerValue());
/*      */         } else {
/* 1797 */           writeNumber(p.getLongValue());
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 8:
/* 1803 */         n = p.getNumberType();
/* 1804 */         if (n == JsonParser.NumberType.BIG_DECIMAL) {
/* 1805 */           writeNumber(p.getDecimalValue());
/* 1806 */         } else if (n == JsonParser.NumberType.FLOAT) {
/* 1807 */           writeNumber(p.getFloatValue());
/*      */         } else {
/* 1809 */           writeNumber(p.getDoubleValue());
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 9:
/* 1814 */         writeBoolean(true);
/*      */         return;
/*      */       case 10:
/* 1817 */         writeBoolean(false);
/*      */         return;
/*      */       case 11:
/* 1820 */         writeNull();
/*      */         return;
/*      */       case 12:
/* 1823 */         writeObject(p.getEmbeddedObject());
/*      */         return;
/*      */     } 
/* 1826 */     _throwInternal();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyCurrentStructure(JsonParser p) throws IOException {
/* 1862 */     JsonToken t = p.currentToken();
/* 1863 */     if (t == null) {
/* 1864 */       _reportError("No current event to copy");
/*      */     }
/*      */     
/* 1867 */     int id = t.id();
/* 1868 */     if (id == 5) {
/* 1869 */       writeFieldName(p.getCurrentName());
/* 1870 */       t = p.nextToken();
/* 1871 */       id = t.id();
/*      */     } 
/*      */     
/* 1874 */     switch (id) {
/*      */       case 1:
/* 1876 */         writeStartObject();
/* 1877 */         while (p.nextToken() != JsonToken.END_OBJECT) {
/* 1878 */           copyCurrentStructure(p);
/*      */         }
/* 1880 */         writeEndObject();
/*      */         return;
/*      */       case 3:
/* 1883 */         writeStartArray();
/* 1884 */         while (p.nextToken() != JsonToken.END_ARRAY) {
/* 1885 */           copyCurrentStructure(p);
/*      */         }
/* 1887 */         writeEndArray();
/*      */         return;
/*      */     } 
/* 1890 */     copyCurrentEvent(p);
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
/*      */   public abstract JsonStreamContext getOutputContext();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void flush() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isClosed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void close() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportError(String msg) throws JsonGenerationException {
/* 1961 */     throw new JsonGenerationException(msg, this);
/*      */   }
/*      */   protected final void _throwInternal() {
/* 1964 */     VersionUtil.throwInternal();
/*      */   }
/*      */   protected void _reportUnsupportedOperation() {
/* 1967 */     throw new UnsupportedOperationException("Operation not supported by generator of type " + getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyOffsets(int arrayLength, int offset, int length) {
/* 1975 */     if (offset < 0 || offset + length > arrayLength) {
/* 1976 */       throw new IllegalArgumentException(String.format("invalid argument(s) (offset=%d, length=%d) for input array of %d element", new Object[] { Integer.valueOf(offset), Integer.valueOf(length), Integer.valueOf(arrayLength) }));
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
/*      */   protected void _writeSimpleObject(Object value) throws IOException {
/* 1995 */     if (value == null) {
/* 1996 */       writeNull();
/*      */       return;
/*      */     } 
/* 1999 */     if (value instanceof String) {
/* 2000 */       writeString((String)value);
/*      */       return;
/*      */     } 
/* 2003 */     if (value instanceof Number) {
/* 2004 */       Number n = (Number)value;
/* 2005 */       if (n instanceof Integer) {
/* 2006 */         writeNumber(n.intValue()); return;
/*      */       } 
/* 2008 */       if (n instanceof Long) {
/* 2009 */         writeNumber(n.longValue()); return;
/*      */       } 
/* 2011 */       if (n instanceof Double) {
/* 2012 */         writeNumber(n.doubleValue()); return;
/*      */       } 
/* 2014 */       if (n instanceof Float) {
/* 2015 */         writeNumber(n.floatValue()); return;
/*      */       } 
/* 2017 */       if (n instanceof Short) {
/* 2018 */         writeNumber(n.shortValue()); return;
/*      */       } 
/* 2020 */       if (n instanceof Byte) {
/* 2021 */         writeNumber((short)n.byteValue()); return;
/*      */       } 
/* 2023 */       if (n instanceof BigInteger) {
/* 2024 */         writeNumber((BigInteger)n); return;
/*      */       } 
/* 2026 */       if (n instanceof BigDecimal) {
/* 2027 */         writeNumber((BigDecimal)n);
/*      */         
/*      */         return;
/*      */       } 
/* 2031 */       if (n instanceof AtomicInteger) {
/* 2032 */         writeNumber(((AtomicInteger)n).get()); return;
/*      */       } 
/* 2034 */       if (n instanceof AtomicLong) {
/* 2035 */         writeNumber(((AtomicLong)n).get()); return;
/*      */       } 
/*      */     } else {
/* 2038 */       if (value instanceof byte[]) {
/* 2039 */         writeBinary((byte[])value); return;
/*      */       } 
/* 2041 */       if (value instanceof Boolean) {
/* 2042 */         writeBoolean(((Boolean)value).booleanValue()); return;
/*      */       } 
/* 2044 */       if (value instanceof AtomicBoolean) {
/* 2045 */         writeBoolean(((AtomicBoolean)value).get()); return;
/*      */       } 
/*      */     } 
/* 2048 */     throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + value.getClass().getName() + ")");
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\JsonGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */