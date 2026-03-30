/*      */ package software.bernie.shadowed.fasterxml.jackson.core;
/*      */ 
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.net.URL;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.format.InputAccessor;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.format.MatchStrength;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.DataOutputAsStream;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.InputDecorator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.OutputDecorator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.UTF8Writer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.PackageVersion;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.ReaderBasedJsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.UTF8JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.BufferRecycler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JsonFactory
/*      */   implements Versioned, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   public static final String FORMAT_NAME_JSON = "JSON";
/*      */   
/*      */   public enum Feature
/*      */   {
/*   77 */     INTERN_FIELD_NAMES(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   87 */     CANONICALIZE_FIELD_NAMES(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     FAIL_ON_SYMBOL_HASH_OVERFLOW(true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  120 */     USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean _defaultState;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int collectDefaults() {
/*  134 */       int flags = 0;
/*  135 */       for (Feature f : values()) {
/*  136 */         if (f.enabledByDefault()) flags |= f.getMask(); 
/*      */       } 
/*  138 */       return flags;
/*      */     }
/*      */     Feature(boolean defaultState) {
/*  141 */       this._defaultState = defaultState;
/*      */     }
/*  143 */     public boolean enabledByDefault() { return this._defaultState; }
/*  144 */     public boolean enabledIn(int flags) { return ((flags & getMask()) != 0); } public int getMask() {
/*  145 */       return 1 << ordinal();
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
/*  163 */   protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = Feature.collectDefaults();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
/*      */   
/*  177 */   private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR = (SerializableString)DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  190 */   protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef = new ThreadLocal<SoftReference<BufferRecycler>>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  198 */   protected final transient CharsToNameCanonicalizer _rootCharSymbols = CharsToNameCanonicalizer.createRoot();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  209 */   protected final transient ByteQuadsCanonicalizer _byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*  229 */   protected int _factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  234 */   protected int _parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  239 */   protected int _generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CharacterEscapes _characterEscapes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InputDecorator _inputDecorator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected OutputDecorator _outputDecorator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  267 */   protected SerializableString _rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory() {
/*  285 */     this(null);
/*      */   } public JsonFactory(ObjectCodec oc) {
/*  287 */     this._objectCodec = oc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonFactory(JsonFactory src, ObjectCodec codec) {
/*  296 */     this._objectCodec = null;
/*  297 */     this._factoryFeatures = src._factoryFeatures;
/*  298 */     this._parserFeatures = src._parserFeatures;
/*  299 */     this._generatorFeatures = src._generatorFeatures;
/*  300 */     this._characterEscapes = src._characterEscapes;
/*  301 */     this._inputDecorator = src._inputDecorator;
/*  302 */     this._outputDecorator = src._outputDecorator;
/*  303 */     this._rootValueSeparator = src._rootValueSeparator;
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
/*      */   public JsonFactory copy() {
/*  327 */     _checkInvalidCopy(JsonFactory.class);
/*      */     
/*  329 */     return new JsonFactory(this, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _checkInvalidCopy(Class<?> exp) {
/*  338 */     if (getClass() != exp) {
/*  339 */       throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
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
/*      */   protected Object readResolve() {
/*  356 */     return new JsonFactory(this, this._objectCodec);
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
/*      */   public boolean requiresPropertyOrdering() {
/*  380 */     return false;
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
/*      */   public boolean canHandleBinaryNatively() {
/*  394 */     return false;
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
/*      */   public boolean canUseCharArrays() {
/*  408 */     return true;
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
/*      */   public boolean canParseAsync() {
/*  421 */     return _isJSONFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<? extends FormatFeature> getFormatReadFeatureType() {
/*  432 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<? extends FormatFeature> getFormatWriteFeatureType() {
/*  443 */     return null;
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
/*      */   public boolean canUseSchema(FormatSchema schema) {
/*  462 */     if (schema == null) {
/*  463 */       return false;
/*      */     }
/*  465 */     String ourFormat = getFormatName();
/*  466 */     return (ourFormat != null && ourFormat.equals(schema.getSchemaType()));
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
/*      */   public String getFormatName() {
/*  482 */     if (getClass() == JsonFactory.class) {
/*  483 */       return "JSON";
/*      */     }
/*  485 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatchStrength hasFormat(InputAccessor acc) throws IOException {
/*  495 */     if (getClass() == JsonFactory.class) {
/*  496 */       return hasJSONFormat(acc);
/*      */     }
/*  498 */     return null;
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
/*      */   public boolean requiresCustomCodec() {
/*  515 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MatchStrength hasJSONFormat(InputAccessor acc) throws IOException {
/*  524 */     return ByteSourceJsonBootstrapper.hasJSONFormat(acc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Version version() {
/*  535 */     return PackageVersion.VERSION;
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
/*      */   public final JsonFactory configure(Feature f, boolean state) {
/*  549 */     return state ? enable(f) : disable(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory enable(Feature f) {
/*  557 */     this._factoryFeatures |= f.getMask();
/*  558 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory disable(Feature f) {
/*  566 */     this._factoryFeatures &= f.getMask() ^ 0xFFFFFFFF;
/*  567 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(Feature f) {
/*  574 */     return ((this._factoryFeatures & f.getMask()) != 0);
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
/*      */   public final JsonFactory configure(JsonParser.Feature f, boolean state) {
/*  588 */     return state ? enable(f) : disable(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory enable(JsonParser.Feature f) {
/*  596 */     this._parserFeatures |= f.getMask();
/*  597 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory disable(JsonParser.Feature f) {
/*  605 */     this._parserFeatures &= f.getMask() ^ 0xFFFFFFFF;
/*  606 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(JsonParser.Feature f) {
/*  613 */     return ((this._parserFeatures & f.getMask()) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputDecorator getInputDecorator() {
/*  621 */     return this._inputDecorator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory setInputDecorator(InputDecorator d) {
/*  628 */     this._inputDecorator = d;
/*  629 */     return this;
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
/*      */   public final JsonFactory configure(JsonGenerator.Feature f, boolean state) {
/*  643 */     return state ? enable(f) : disable(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory enable(JsonGenerator.Feature f) {
/*  652 */     this._generatorFeatures |= f.getMask();
/*  653 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory disable(JsonGenerator.Feature f) {
/*  661 */     this._generatorFeatures &= f.getMask() ^ 0xFFFFFFFF;
/*  662 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(JsonGenerator.Feature f) {
/*  669 */     return ((this._generatorFeatures & f.getMask()) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharacterEscapes getCharacterEscapes() {
/*  676 */     return this._characterEscapes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory setCharacterEscapes(CharacterEscapes esc) {
/*  683 */     this._characterEscapes = esc;
/*  684 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputDecorator getOutputDecorator() {
/*  692 */     return this._outputDecorator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory setOutputDecorator(OutputDecorator d) {
/*  699 */     this._outputDecorator = d;
/*  700 */     return this;
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
/*      */   public JsonFactory setRootValueSeparator(String sep) {
/*  713 */     this._rootValueSeparator = (sep == null) ? null : (SerializableString)new SerializedString(sep);
/*  714 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRootValueSeparator() {
/*  721 */     return (this._rootValueSeparator == null) ? null : this._rootValueSeparator.getValue();
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
/*      */   public JsonFactory setCodec(ObjectCodec oc) {
/*  738 */     this._objectCodec = oc;
/*  739 */     return this;
/*      */   }
/*      */   public ObjectCodec getCodec() {
/*  742 */     return this._objectCodec;
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
/*      */   public JsonParser createParser(File f) throws IOException, JsonParseException {
/*  772 */     IOContext ctxt = _createContext(f, true);
/*  773 */     InputStream in = new FileInputStream(f);
/*  774 */     return _createParser(_decorate(in, ctxt), ctxt);
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
/*      */   public JsonParser createParser(URL url) throws IOException, JsonParseException {
/*  799 */     IOContext ctxt = _createContext(url, true);
/*  800 */     InputStream in = _optimizedStreamFromURL(url);
/*  801 */     return _createParser(_decorate(in, ctxt), ctxt);
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
/*      */   public JsonParser createParser(InputStream in) throws IOException, JsonParseException {
/*  826 */     IOContext ctxt = _createContext(in, false);
/*  827 */     return _createParser(_decorate(in, ctxt), ctxt);
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
/*      */   public JsonParser createParser(Reader r) throws IOException, JsonParseException {
/*  846 */     IOContext ctxt = _createContext(r, false);
/*  847 */     return _createParser(_decorate(r, ctxt), ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser createParser(byte[] data) throws IOException, JsonParseException {
/*  857 */     IOContext ctxt = _createContext(data, true);
/*  858 */     if (this._inputDecorator != null) {
/*  859 */       InputStream in = this._inputDecorator.decorate(ctxt, data, 0, data.length);
/*  860 */       if (in != null) {
/*  861 */         return _createParser(in, ctxt);
/*      */       }
/*      */     } 
/*  864 */     return _createParser(data, 0, data.length, ctxt);
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
/*      */   public JsonParser createParser(byte[] data, int offset, int len) throws IOException, JsonParseException {
/*  878 */     IOContext ctxt = _createContext(data, true);
/*      */     
/*  880 */     if (this._inputDecorator != null) {
/*  881 */       InputStream in = this._inputDecorator.decorate(ctxt, data, offset, len);
/*  882 */       if (in != null) {
/*  883 */         return _createParser(in, ctxt);
/*      */       }
/*      */     } 
/*  886 */     return _createParser(data, offset, len, ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser createParser(String content) throws IOException, JsonParseException {
/*  896 */     int strLen = content.length();
/*      */     
/*  898 */     if (this._inputDecorator != null || strLen > 32768 || !canUseCharArrays())
/*      */     {
/*      */       
/*  901 */       return createParser(new StringReader(content));
/*      */     }
/*  903 */     IOContext ctxt = _createContext(content, true);
/*  904 */     char[] buf = ctxt.allocTokenBuffer(strLen);
/*  905 */     content.getChars(0, strLen, buf, 0);
/*  906 */     return _createParser(buf, 0, strLen, ctxt, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser createParser(char[] content) throws IOException {
/*  916 */     return createParser(content, 0, content.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser createParser(char[] content, int offset, int len) throws IOException {
/*  925 */     if (this._inputDecorator != null) {
/*  926 */       return createParser(new CharArrayReader(content, offset, len));
/*      */     }
/*  928 */     return _createParser(content, offset, len, _createContext(content, true), false);
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
/*      */   public JsonParser createParser(DataInput in) throws IOException {
/*  943 */     IOContext ctxt = _createContext(in, false);
/*  944 */     return _createParser(_decorate(in, ctxt), ctxt);
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
/*      */   public JsonParser createNonBlockingByteArrayParser() throws IOException {
/*  969 */     _requireJSONFactory("Non-blocking source not (yet?) support for this format (%s)");
/*  970 */     IOContext ctxt = _createContext(null, false);
/*  971 */     ByteQuadsCanonicalizer can = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
/*  972 */     return (JsonParser)new NonBlockingJsonParser(ctxt, this._parserFeatures, can);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(File f) throws IOException, JsonParseException {
/* 1002 */     return createParser(f);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(URL url) throws IOException, JsonParseException {
/* 1027 */     return createParser(url);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(InputStream in) throws IOException, JsonParseException {
/* 1053 */     return createParser(in);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(Reader r) throws IOException, JsonParseException {
/* 1072 */     return createParser(r);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(byte[] data) throws IOException, JsonParseException {
/* 1082 */     return createParser(data);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(byte[] data, int offset, int len) throws IOException, JsonParseException {
/* 1097 */     return createParser(data, offset, len);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(String content) throws IOException, JsonParseException {
/* 1108 */     return createParser(content);
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
/*      */   public JsonGenerator createGenerator(OutputStream out, JsonEncoding enc) throws IOException {
/* 1143 */     IOContext ctxt = _createContext(out, false);
/* 1144 */     ctxt.setEncoding(enc);
/* 1145 */     if (enc == JsonEncoding.UTF8) {
/* 1146 */       return _createUTF8Generator(_decorate(out, ctxt), ctxt);
/*      */     }
/* 1148 */     Writer w = _createWriter(out, enc, ctxt);
/* 1149 */     return _createGenerator(_decorate(w, ctxt), ctxt);
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
/*      */   public JsonGenerator createGenerator(OutputStream out) throws IOException {
/* 1161 */     return createGenerator(out, JsonEncoding.UTF8);
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
/*      */   public JsonGenerator createGenerator(Writer w) throws IOException {
/* 1180 */     IOContext ctxt = _createContext(w, false);
/* 1181 */     return _createGenerator(_decorate(w, ctxt), ctxt);
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
/*      */   public JsonGenerator createGenerator(File f, JsonEncoding enc) throws IOException {
/* 1202 */     OutputStream out = new FileOutputStream(f);
/*      */     
/* 1204 */     IOContext ctxt = _createContext(out, true);
/* 1205 */     ctxt.setEncoding(enc);
/* 1206 */     if (enc == JsonEncoding.UTF8) {
/* 1207 */       return _createUTF8Generator(_decorate(out, ctxt), ctxt);
/*      */     }
/* 1209 */     Writer w = _createWriter(out, enc, ctxt);
/* 1210 */     return _createGenerator(_decorate(w, ctxt), ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonGenerator createGenerator(DataOutput out, JsonEncoding enc) throws IOException {
/* 1220 */     return createGenerator(_createDataOutputWrapper(out), enc);
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
/*      */   public JsonGenerator createGenerator(DataOutput out) throws IOException {
/* 1232 */     return createGenerator(_createDataOutputWrapper(out), JsonEncoding.UTF8);
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
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(OutputStream out, JsonEncoding enc) throws IOException {
/* 1265 */     return createGenerator(out, enc);
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
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(Writer out) throws IOException {
/* 1285 */     return createGenerator(out);
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
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(OutputStream out) throws IOException {
/* 1298 */     return createGenerator(out, JsonEncoding.UTF8);
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
/*      */   protected JsonParser _createParser(InputStream in, IOContext ctxt) throws IOException {
/* 1322 */     return (new ByteSourceJsonBootstrapper(ctxt, in)).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
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
/*      */   protected JsonParser _createParser(Reader r, IOContext ctxt) throws IOException {
/* 1339 */     return (JsonParser)new ReaderBasedJsonParser(ctxt, this._parserFeatures, r, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
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
/*      */   protected JsonParser _createParser(char[] data, int offset, int len, IOContext ctxt, boolean recyclable) throws IOException {
/* 1351 */     return (JsonParser)new ReaderBasedJsonParser(ctxt, this._parserFeatures, null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), data, offset, offset + len, recyclable);
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
/*      */   protected JsonParser _createParser(byte[] data, int offset, int len, IOContext ctxt) throws IOException {
/* 1369 */     return (new ByteSourceJsonBootstrapper(ctxt, data, offset, len)).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
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
/*      */   protected JsonParser _createParser(DataInput input, IOContext ctxt) throws IOException {
/* 1382 */     _requireJSONFactory("InputData source not (yet?) support for this format (%s)");
/*      */ 
/*      */     
/* 1385 */     int firstByte = ByteSourceJsonBootstrapper.skipUTF8BOM(input);
/* 1386 */     ByteQuadsCanonicalizer can = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
/* 1387 */     return (JsonParser)new UTF8DataInputJsonParser(ctxt, this._parserFeatures, input, this._objectCodec, can, firstByte);
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
/*      */   protected JsonGenerator _createGenerator(Writer out, IOContext ctxt) throws IOException {
/* 1410 */     WriterBasedJsonGenerator gen = new WriterBasedJsonGenerator(ctxt, this._generatorFeatures, this._objectCodec, out);
/*      */     
/* 1412 */     if (this._characterEscapes != null) {
/* 1413 */       gen.setCharacterEscapes(this._characterEscapes);
/*      */     }
/* 1415 */     SerializableString rootSep = this._rootValueSeparator;
/* 1416 */     if (rootSep != DEFAULT_ROOT_VALUE_SEPARATOR) {
/* 1417 */       gen.setRootValueSeparator(rootSep);
/*      */     }
/* 1419 */     return (JsonGenerator)gen;
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
/*      */   protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt) throws IOException {
/* 1433 */     UTF8JsonGenerator gen = new UTF8JsonGenerator(ctxt, this._generatorFeatures, this._objectCodec, out);
/*      */     
/* 1435 */     if (this._characterEscapes != null) {
/* 1436 */       gen.setCharacterEscapes(this._characterEscapes);
/*      */     }
/* 1438 */     SerializableString rootSep = this._rootValueSeparator;
/* 1439 */     if (rootSep != DEFAULT_ROOT_VALUE_SEPARATOR) {
/* 1440 */       gen.setRootValueSeparator(rootSep);
/*      */     }
/* 1442 */     return (JsonGenerator)gen;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Writer _createWriter(OutputStream out, JsonEncoding enc, IOContext ctxt) throws IOException {
/* 1448 */     if (enc == JsonEncoding.UTF8) {
/* 1449 */       return (Writer)new UTF8Writer(ctxt, out);
/*      */     }
/*      */     
/* 1452 */     return new OutputStreamWriter(out, enc.getJavaName());
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
/*      */   protected final InputStream _decorate(InputStream in, IOContext ctxt) throws IOException {
/* 1465 */     if (this._inputDecorator != null) {
/* 1466 */       InputStream in2 = this._inputDecorator.decorate(ctxt, in);
/* 1467 */       if (in2 != null) {
/* 1468 */         return in2;
/*      */       }
/*      */     } 
/* 1471 */     return in;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Reader _decorate(Reader in, IOContext ctxt) throws IOException {
/* 1478 */     if (this._inputDecorator != null) {
/* 1479 */       Reader in2 = this._inputDecorator.decorate(ctxt, in);
/* 1480 */       if (in2 != null) {
/* 1481 */         return in2;
/*      */       }
/*      */     } 
/* 1484 */     return in;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final DataInput _decorate(DataInput in, IOContext ctxt) throws IOException {
/* 1491 */     if (this._inputDecorator != null) {
/* 1492 */       DataInput in2 = this._inputDecorator.decorate(ctxt, in);
/* 1493 */       if (in2 != null) {
/* 1494 */         return in2;
/*      */       }
/*      */     } 
/* 1497 */     return in;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final OutputStream _decorate(OutputStream out, IOContext ctxt) throws IOException {
/* 1504 */     if (this._outputDecorator != null) {
/* 1505 */       OutputStream out2 = this._outputDecorator.decorate(ctxt, out);
/* 1506 */       if (out2 != null) {
/* 1507 */         return out2;
/*      */       }
/*      */     } 
/* 1510 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Writer _decorate(Writer out, IOContext ctxt) throws IOException {
/* 1517 */     if (this._outputDecorator != null) {
/* 1518 */       Writer out2 = this._outputDecorator.decorate(ctxt, out);
/* 1519 */       if (out2 != null) {
/* 1520 */         return out2;
/*      */       }
/*      */     } 
/* 1523 */     return out;
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
/*      */   public BufferRecycler _getBufferRecycler() {
/* 1544 */     if (Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING.enabledIn(this._factoryFeatures)) {
/* 1545 */       SoftReference<BufferRecycler> ref = _recyclerRef.get();
/* 1546 */       BufferRecycler br = (ref == null) ? null : ref.get();
/*      */       
/* 1548 */       if (br == null) {
/* 1549 */         br = new BufferRecycler();
/* 1550 */         _recyclerRef.set(new SoftReference<BufferRecycler>(br));
/*      */       } 
/* 1552 */       return br;
/*      */     } 
/* 1554 */     return new BufferRecycler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IOContext _createContext(Object srcRef, boolean resourceManaged) {
/* 1562 */     return new IOContext(_getBufferRecycler(), srcRef, resourceManaged);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected OutputStream _createDataOutputWrapper(DataOutput out) {
/* 1569 */     return (OutputStream)new DataOutputAsStream(out);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InputStream _optimizedStreamFromURL(URL url) throws IOException {
/* 1578 */     if ("file".equals(url.getProtocol())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1585 */       String host = url.getHost();
/* 1586 */       if (host == null || host.length() == 0) {
/*      */         
/* 1588 */         String path = url.getPath();
/* 1589 */         if (path.indexOf('%') < 0) {
/* 1590 */           return new FileInputStream(url.getPath());
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1596 */     return url.openStream();
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
/*      */   private final void _requireJSONFactory(String msg) {
/* 1618 */     if (!_isJSONFactory()) {
/* 1619 */       throw new UnsupportedOperationException(String.format(msg, new Object[] { getFormatName() }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean _isJSONFactory() {
/* 1626 */     return (getFormatName() == "JSON");
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\JsonFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */