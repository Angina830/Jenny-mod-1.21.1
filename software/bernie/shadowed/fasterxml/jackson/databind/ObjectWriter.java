/*      */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*      */ import java.io.Closeable;
/*      */ import java.io.DataOutput;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.text.DateFormat;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.FormatFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.FormatSchema;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonEncoding;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.SegmentedStringWriter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.TypeReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ContextAttributes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.PackageVersion;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.FilterProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ public class ObjectWriter implements Versioned, Serializable {
/*   42 */   protected static final PrettyPrinter NULL_PRETTY_PRINTER = (PrettyPrinter)new MinimalPrettyPrinter();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final SerializationConfig _config;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final DefaultSerializerProvider _serializerProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final SerializerFactory _serializerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonFactory _generatorFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final GeneratorSettings _generatorSettings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Prefetch _prefetch;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter(ObjectMapper mapper, SerializationConfig config, JavaType rootType, PrettyPrinter pp) {
/*  100 */     this._config = config;
/*  101 */     this._serializerProvider = mapper._serializerProvider;
/*  102 */     this._serializerFactory = mapper._serializerFactory;
/*  103 */     this._generatorFactory = mapper._jsonFactory;
/*  104 */     this._generatorSettings = (pp == null) ? GeneratorSettings.empty : new GeneratorSettings(pp, null, null, null);
/*      */ 
/*      */ 
/*      */     
/*  108 */     if (rootType == null || rootType.hasRawClass(Object.class)) {
/*  109 */       this._prefetch = Prefetch.empty;
/*      */     } else {
/*  111 */       rootType = rootType.withStaticTyping();
/*  112 */       this._prefetch = Prefetch.empty.forRootType(this, rootType);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter(ObjectMapper mapper, SerializationConfig config) {
/*  121 */     this._config = config;
/*  122 */     this._serializerProvider = mapper._serializerProvider;
/*  123 */     this._serializerFactory = mapper._serializerFactory;
/*  124 */     this._generatorFactory = mapper._jsonFactory;
/*      */     
/*  126 */     this._generatorSettings = GeneratorSettings.empty;
/*  127 */     this._prefetch = Prefetch.empty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter(ObjectMapper mapper, SerializationConfig config, FormatSchema s) {
/*  136 */     this._config = config;
/*      */     
/*  138 */     this._serializerProvider = mapper._serializerProvider;
/*  139 */     this._serializerFactory = mapper._serializerFactory;
/*  140 */     this._generatorFactory = mapper._jsonFactory;
/*      */     
/*  142 */     this._generatorSettings = (s == null) ? GeneratorSettings.empty : new GeneratorSettings(null, s, null, null);
/*      */     
/*  144 */     this._prefetch = Prefetch.empty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter(ObjectWriter base, SerializationConfig config, GeneratorSettings genSettings, Prefetch prefetch) {
/*  153 */     this._config = config;
/*      */     
/*  155 */     this._serializerProvider = base._serializerProvider;
/*  156 */     this._serializerFactory = base._serializerFactory;
/*  157 */     this._generatorFactory = base._generatorFactory;
/*      */     
/*  159 */     this._generatorSettings = genSettings;
/*  160 */     this._prefetch = prefetch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter(ObjectWriter base, SerializationConfig config) {
/*  168 */     this._config = config;
/*      */     
/*  170 */     this._serializerProvider = base._serializerProvider;
/*  171 */     this._serializerFactory = base._serializerFactory;
/*  172 */     this._generatorFactory = base._generatorFactory;
/*      */     
/*  174 */     this._generatorSettings = base._generatorSettings;
/*  175 */     this._prefetch = base._prefetch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter(ObjectWriter base, JsonFactory f) {
/*  184 */     this._config = (SerializationConfig)base._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, f.requiresPropertyOrdering());
/*      */ 
/*      */     
/*  187 */     this._serializerProvider = base._serializerProvider;
/*  188 */     this._serializerFactory = base._serializerFactory;
/*  189 */     this._generatorFactory = f;
/*      */     
/*  191 */     this._generatorSettings = base._generatorSettings;
/*  192 */     this._prefetch = base._prefetch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Version version() {
/*  201 */     return PackageVersion.VERSION;
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
/*      */   protected ObjectWriter _new(ObjectWriter base, JsonFactory f) {
/*  218 */     return new ObjectWriter(base, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter _new(ObjectWriter base, SerializationConfig config) {
/*  227 */     if (config == this._config) {
/*  228 */       return this;
/*      */     }
/*  230 */     return new ObjectWriter(base, config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter _new(GeneratorSettings genSettings, Prefetch prefetch) {
/*  241 */     if (this._generatorSettings == genSettings && this._prefetch == prefetch) {
/*  242 */       return this;
/*      */     }
/*  244 */     return new ObjectWriter(this, this._config, genSettings, prefetch);
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
/*      */   protected SequenceWriter _newSequenceWriter(boolean wrapInArray, JsonGenerator gen, boolean managedInput) throws IOException {
/*  258 */     _configureGenerator(gen);
/*  259 */     return (new SequenceWriter(_serializerProvider(), gen, managedInput, this._prefetch)).init(wrapInArray);
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
/*      */   public ObjectWriter with(SerializationFeature feature) {
/*  275 */     return _new(this, this._config.with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(SerializationFeature first, SerializationFeature... other) {
/*  283 */     return _new(this, this._config.with(first, other));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withFeatures(SerializationFeature... features) {
/*  291 */     return _new(this, this._config.withFeatures(features));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter without(SerializationFeature feature) {
/*  299 */     return _new(this, this._config.without(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter without(SerializationFeature first, SerializationFeature... other) {
/*  307 */     return _new(this, this._config.without(first, other));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withoutFeatures(SerializationFeature... features) {
/*  315 */     return _new(this, this._config.withoutFeatures(features));
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
/*      */   public ObjectWriter with(JsonGenerator.Feature feature) {
/*  328 */     return _new(this, this._config.with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withFeatures(JsonGenerator.Feature... features) {
/*  335 */     return _new(this, this._config.withFeatures(features));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter without(JsonGenerator.Feature feature) {
/*  342 */     return _new(this, this._config.without(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withoutFeatures(JsonGenerator.Feature... features) {
/*  349 */     return _new(this, this._config.withoutFeatures(features));
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
/*      */   public ObjectWriter with(FormatFeature feature) {
/*  362 */     return _new(this, this._config.with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withFeatures(FormatFeature... features) {
/*  369 */     return _new(this, this._config.withFeatures(features));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter without(FormatFeature feature) {
/*  376 */     return _new(this, this._config.without(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withoutFeatures(FormatFeature... features) {
/*  383 */     return _new(this, this._config.withoutFeatures(features));
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
/*      */   public ObjectWriter forType(JavaType rootType) {
/*  403 */     return _new(this._generatorSettings, this._prefetch.forRootType(this, rootType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter forType(Class<?> rootType) {
/*  414 */     if (rootType == Object.class) {
/*  415 */       return forType((JavaType)null);
/*      */     }
/*  417 */     return forType(this._config.constructType(rootType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter forType(TypeReference<?> rootType) {
/*  428 */     return forType(this._config.getTypeFactory().constructType(rootType.getType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter withType(JavaType rootType) {
/*  436 */     return forType(rootType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter withType(Class<?> rootType) {
/*  444 */     return forType(rootType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter withType(TypeReference<?> rootType) {
/*  452 */     return forType(rootType);
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
/*      */   public ObjectWriter with(DateFormat df) {
/*  470 */     return _new(this, this._config.with(df));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withDefaultPrettyPrinter() {
/*  478 */     return with(this._config.getDefaultPrettyPrinter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(FilterProvider filterProvider) {
/*  486 */     if (filterProvider == this._config.getFilterProvider()) {
/*  487 */       return this;
/*      */     }
/*  489 */     return _new(this, this._config.withFilters(filterProvider));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(PrettyPrinter pp) {
/*  497 */     return _new(this._generatorSettings.with(pp), this._prefetch);
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
/*      */   public ObjectWriter withRootName(String rootName) {
/*  512 */     return _new(this, (SerializationConfig)this._config.withRootName(rootName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withRootName(PropertyName rootName) {
/*  519 */     return _new(this, this._config.withRootName(rootName));
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
/*      */   public ObjectWriter withoutRootName() {
/*  533 */     return _new(this, this._config.withRootName(PropertyName.NO_NAME));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(FormatSchema schema) {
/*  544 */     _verifySchemaType(schema);
/*  545 */     return _new(this._generatorSettings.with(schema), this._prefetch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter withSchema(FormatSchema schema) {
/*  553 */     return with(schema);
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
/*      */   public ObjectWriter withView(Class<?> view) {
/*  565 */     return _new(this, this._config.withView(view));
/*      */   }
/*      */   
/*      */   public ObjectWriter with(Locale l) {
/*  569 */     return _new(this, (SerializationConfig)this._config.with(l));
/*      */   }
/*      */   
/*      */   public ObjectWriter with(TimeZone tz) {
/*  573 */     return _new(this, (SerializationConfig)this._config.with(tz));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(Base64Variant b64variant) {
/*  583 */     return _new(this, (SerializationConfig)this._config.with(b64variant));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(CharacterEscapes escapes) {
/*  590 */     return _new(this._generatorSettings.with(escapes), this._prefetch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(JsonFactory f) {
/*  597 */     return (f == this._generatorFactory) ? this : _new(this, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter with(ContextAttributes attrs) {
/*  604 */     return _new(this, this._config.with(attrs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withAttributes(Map<?, ?> attrs) {
/*  614 */     return _new(this, (SerializationConfig)this._config.withAttributes(attrs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withAttribute(Object key, Object value) {
/*  621 */     return _new(this, (SerializationConfig)this._config.withAttribute(key, value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withoutAttribute(Object key) {
/*  628 */     return _new(this, (SerializationConfig)this._config.withoutAttribute(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withRootValueSeparator(String sep) {
/*  635 */     return _new(this._generatorSettings.withRootValueSeparator(sep), this._prefetch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter withRootValueSeparator(SerializableString sep) {
/*  642 */     return _new(this._generatorSettings.withRootValueSeparator(sep), this._prefetch);
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
/*      */   public SequenceWriter writeValues(File out) throws IOException {
/*  665 */     return _newSequenceWriter(false, this._generatorFactory.createGenerator(out, JsonEncoding.UTF8), true);
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
/*      */   public SequenceWriter writeValues(JsonGenerator gen) throws IOException {
/*  685 */     _configureGenerator(gen);
/*  686 */     return _newSequenceWriter(false, gen, false);
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
/*      */   public SequenceWriter writeValues(Writer out) throws IOException {
/*  703 */     return _newSequenceWriter(false, this._generatorFactory.createGenerator(out), true);
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
/*      */   public SequenceWriter writeValues(OutputStream out) throws IOException {
/*  721 */     return _newSequenceWriter(false, this._generatorFactory.createGenerator(out, JsonEncoding.UTF8), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SequenceWriter writeValues(DataOutput out) throws IOException {
/*  729 */     return _newSequenceWriter(false, this._generatorFactory.createGenerator(out), true);
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
/*      */   public SequenceWriter writeValuesAsArray(File out) throws IOException {
/*  749 */     return _newSequenceWriter(true, this._generatorFactory.createGenerator(out, JsonEncoding.UTF8), true);
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
/*      */   public SequenceWriter writeValuesAsArray(JsonGenerator gen) throws IOException {
/*  770 */     return _newSequenceWriter(true, gen, false);
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
/*      */   public SequenceWriter writeValuesAsArray(Writer out) throws IOException {
/*  789 */     return _newSequenceWriter(true, this._generatorFactory.createGenerator(out), true);
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
/*      */   public SequenceWriter writeValuesAsArray(OutputStream out) throws IOException {
/*  808 */     return _newSequenceWriter(true, this._generatorFactory.createGenerator(out, JsonEncoding.UTF8), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SequenceWriter writeValuesAsArray(DataOutput out) throws IOException {
/*  816 */     return _newSequenceWriter(true, this._generatorFactory.createGenerator(out), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled(SerializationFeature f) {
/*  826 */     return this._config.isEnabled(f);
/*      */   }
/*      */   
/*      */   public boolean isEnabled(MapperFeature f) {
/*  830 */     return this._config.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isEnabled(JsonParser.Feature f) {
/*  838 */     return this._generatorFactory.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled(JsonGenerator.Feature f) {
/*  845 */     return this._generatorFactory.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializationConfig getConfig() {
/*  852 */     return this._config;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory getFactory() {
/*  859 */     return this._generatorFactory;
/*      */   }
/*      */   
/*      */   public TypeFactory getTypeFactory() {
/*  863 */     return this._config.getTypeFactory();
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
/*      */   public boolean hasPrefetchedSerializer() {
/*  875 */     return this._prefetch.hasSerializer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContextAttributes getAttributes() {
/*  882 */     return this._config.getAttributes();
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
/*      */   public void writeValue(JsonGenerator gen, Object value) throws IOException {
/*  897 */     _configureGenerator(gen);
/*  898 */     if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && value instanceof Closeable) {
/*      */ 
/*      */       
/*  901 */       Closeable toClose = (Closeable)value;
/*      */       try {
/*  903 */         this._prefetch.serialize(gen, value, _serializerProvider());
/*  904 */         if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/*  905 */           gen.flush();
/*      */         }
/*  907 */       } catch (Exception e) {
/*  908 */         ClassUtil.closeOnFailAndThrowAsIOE(null, toClose, e);
/*      */         return;
/*      */       } 
/*  911 */       toClose.close();
/*      */     } else {
/*  913 */       this._prefetch.serialize(gen, value, _serializerProvider());
/*  914 */       if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/*  915 */         gen.flush();
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
/*      */   public void writeValue(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
/*  933 */     _configAndWriteValue(this._generatorFactory.createGenerator(resultFile, JsonEncoding.UTF8), value);
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
/*      */   public void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
/*  950 */     _configAndWriteValue(this._generatorFactory.createGenerator(out, JsonEncoding.UTF8), value);
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
/*      */   public void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
/*  966 */     _configAndWriteValue(this._generatorFactory.createGenerator(w), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeValue(DataOutput out, Object value) throws IOException {
/*  975 */     _configAndWriteValue(this._generatorFactory.createGenerator(out), value);
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
/*      */   public String writeValueAsString(Object value) throws JsonProcessingException {
/*  991 */     SegmentedStringWriter sw = new SegmentedStringWriter(this._generatorFactory._getBufferRecycler());
/*      */     try {
/*  993 */       _configAndWriteValue(this._generatorFactory.createGenerator((Writer)sw), value);
/*  994 */     } catch (JsonProcessingException e) {
/*  995 */       throw e;
/*  996 */     } catch (IOException e) {
/*  997 */       throw JsonMappingException.fromUnexpectedIOE(e);
/*      */     } 
/*  999 */     return sw.getAndClear();
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
/*      */   public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
/* 1015 */     ByteArrayBuilder bb = new ByteArrayBuilder(this._generatorFactory._getBufferRecycler());
/*      */     try {
/* 1017 */       _configAndWriteValue(this._generatorFactory.createGenerator((OutputStream)bb, JsonEncoding.UTF8), value);
/* 1018 */     } catch (JsonProcessingException e) {
/* 1019 */       throw e;
/* 1020 */     } catch (IOException e) {
/* 1021 */       throw JsonMappingException.fromUnexpectedIOE(e);
/*      */     } 
/* 1023 */     byte[] result = bb.toByteArray();
/* 1024 */     bb.release();
/* 1025 */     return result;
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
/*      */   public void acceptJsonFormatVisitor(JavaType type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
/* 1048 */     if (type == null) {
/* 1049 */       throw new IllegalArgumentException("type must be provided");
/*      */     }
/* 1051 */     _serializerProvider().acceptJsonFormatVisitor(type, visitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void acceptJsonFormatVisitor(Class<?> rawType, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
/* 1058 */     acceptJsonFormatVisitor(this._config.constructType(rawType), visitor);
/*      */   }
/*      */   
/*      */   public boolean canSerialize(Class<?> type) {
/* 1062 */     return _serializerProvider().hasSerializerFor(type, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSerialize(Class<?> type, AtomicReference<Throwable> cause) {
/* 1072 */     return _serializerProvider().hasSerializerFor(type, cause);
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
/*      */   protected DefaultSerializerProvider _serializerProvider() {
/* 1086 */     return this._serializerProvider.createInstance(this._config, this._serializerFactory);
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
/*      */   protected void _verifySchemaType(FormatSchema schema) {
/* 1100 */     if (schema != null && 
/* 1101 */       !this._generatorFactory.canUseSchema(schema)) {
/* 1102 */       throw new IllegalArgumentException("Cannot use FormatSchema of type " + schema.getClass().getName() + " for format " + this._generatorFactory.getFormatName());
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
/*      */   protected final void _configAndWriteValue(JsonGenerator gen, Object value) throws IOException {
/* 1114 */     _configureGenerator(gen);
/* 1115 */     if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && value instanceof Closeable) {
/* 1116 */       _writeCloseable(gen, value);
/*      */       return;
/*      */     } 
/*      */     try {
/* 1120 */       this._prefetch.serialize(gen, value, _serializerProvider());
/* 1121 */     } catch (Exception e) {
/* 1122 */       ClassUtil.closeOnFailAndThrowAsIOE(gen, e);
/*      */       return;
/*      */     } 
/* 1125 */     gen.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeCloseable(JsonGenerator gen, Object value) throws IOException {
/* 1135 */     Closeable toClose = (Closeable)value;
/*      */     try {
/* 1137 */       this._prefetch.serialize(gen, value, _serializerProvider());
/* 1138 */       Closeable tmpToClose = toClose;
/* 1139 */       toClose = null;
/* 1140 */       tmpToClose.close();
/* 1141 */     } catch (Exception e) {
/* 1142 */       ClassUtil.closeOnFailAndThrowAsIOE(gen, toClose, e);
/*      */       return;
/*      */     } 
/* 1145 */     gen.close();
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
/*      */   protected final void _configureGenerator(JsonGenerator gen) {
/* 1158 */     this._config.initialize(gen);
/* 1159 */     this._generatorSettings.initialize(gen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class GeneratorSettings
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1180 */     public static final GeneratorSettings empty = new GeneratorSettings(null, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final PrettyPrinter prettyPrinter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final FormatSchema schema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final CharacterEscapes characterEscapes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final SerializableString rootValueSeparator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public GeneratorSettings(PrettyPrinter pp, FormatSchema sch, CharacterEscapes esc, SerializableString rootSep) {
/* 1211 */       this.prettyPrinter = pp;
/* 1212 */       this.schema = sch;
/* 1213 */       this.characterEscapes = esc;
/* 1214 */       this.rootValueSeparator = rootSep;
/*      */     }
/*      */ 
/*      */     
/*      */     public GeneratorSettings with(PrettyPrinter pp) {
/* 1219 */       if (pp == null) {
/* 1220 */         pp = ObjectWriter.NULL_PRETTY_PRINTER;
/*      */       }
/* 1222 */       return (pp == this.prettyPrinter) ? this : new GeneratorSettings(pp, this.schema, this.characterEscapes, this.rootValueSeparator);
/*      */     }
/*      */ 
/*      */     
/*      */     public GeneratorSettings with(FormatSchema sch) {
/* 1227 */       return (this.schema == sch) ? this : new GeneratorSettings(this.prettyPrinter, sch, this.characterEscapes, this.rootValueSeparator);
/*      */     }
/*      */ 
/*      */     
/*      */     public GeneratorSettings with(CharacterEscapes esc) {
/* 1232 */       return (this.characterEscapes == esc) ? this : new GeneratorSettings(this.prettyPrinter, this.schema, esc, this.rootValueSeparator);
/*      */     }
/*      */ 
/*      */     
/*      */     public GeneratorSettings withRootValueSeparator(String sep) {
/* 1237 */       if (sep == null) {
/* 1238 */         if (this.rootValueSeparator == null) {
/* 1239 */           return this;
/*      */         }
/* 1241 */         return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, null);
/*      */       } 
/* 1243 */       if (sep.equals(_rootValueSeparatorAsString())) {
/* 1244 */         return this;
/*      */       }
/* 1246 */       return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, (SerializableString)new SerializedString(sep));
/*      */     }
/*      */ 
/*      */     
/*      */     public GeneratorSettings withRootValueSeparator(SerializableString sep) {
/* 1251 */       if (sep == null) {
/* 1252 */         if (this.rootValueSeparator == null) {
/* 1253 */           return this;
/*      */         }
/* 1255 */         return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, null);
/*      */       } 
/* 1257 */       if (sep.equals(this.rootValueSeparator)) {
/* 1258 */         return this;
/*      */       }
/* 1260 */       return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, sep);
/*      */     }
/*      */     
/*      */     private final String _rootValueSeparatorAsString() {
/* 1264 */       return (this.rootValueSeparator == null) ? null : this.rootValueSeparator.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void initialize(JsonGenerator gen) {
/* 1272 */       PrettyPrinter pp = this.prettyPrinter;
/* 1273 */       if (this.prettyPrinter != null) {
/* 1274 */         if (pp == ObjectWriter.NULL_PRETTY_PRINTER) {
/* 1275 */           gen.setPrettyPrinter(null);
/*      */         } else {
/* 1277 */           if (pp instanceof Instantiatable) {
/* 1278 */             pp = (PrettyPrinter)((Instantiatable)pp).createInstance();
/*      */           }
/* 1280 */           gen.setPrettyPrinter(pp);
/*      */         } 
/*      */       }
/* 1283 */       if (this.characterEscapes != null) {
/* 1284 */         gen.setCharacterEscapes(this.characterEscapes);
/*      */       }
/* 1286 */       if (this.schema != null) {
/* 1287 */         gen.setSchema(this.schema);
/*      */       }
/* 1289 */       if (this.rootValueSeparator != null) {
/* 1290 */         gen.setRootValueSeparator(this.rootValueSeparator);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Prefetch
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1307 */     public static final Prefetch empty = new Prefetch(null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final JavaType rootType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final JsonSerializer<Object> valueSerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final TypeSerializer typeSerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Prefetch(JavaType rootT, JsonSerializer<Object> ser, TypeSerializer typeSer) {
/* 1333 */       this.rootType = rootT;
/* 1334 */       this.valueSerializer = ser;
/* 1335 */       this.typeSerializer = typeSer;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Prefetch forRootType(ObjectWriter parent, JavaType newType) {
/* 1341 */       boolean noType = (newType == null || newType.isJavaLangObject());
/*      */       
/* 1343 */       if (noType) {
/* 1344 */         if (this.rootType == null || this.valueSerializer == null) {
/* 1345 */           return this;
/*      */         }
/* 1347 */         return new Prefetch(null, null, this.typeSerializer);
/*      */       } 
/* 1349 */       if (newType.equals(this.rootType)) {
/* 1350 */         return this;
/*      */       }
/* 1352 */       if (parent.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH)) {
/* 1353 */         DefaultSerializerProvider prov = parent._serializerProvider();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1359 */           JsonSerializer<Object> ser = prov.findTypedValueSerializer(newType, true, null);
/*      */           
/* 1361 */           if (ser instanceof TypeWrappedSerializer) {
/* 1362 */             return new Prefetch(newType, null, ((TypeWrappedSerializer)ser).typeSerializer());
/*      */           }
/*      */           
/* 1365 */           return new Prefetch(newType, ser, null);
/* 1366 */         } catch (JsonProcessingException e) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1371 */       return new Prefetch(newType, null, this.typeSerializer);
/*      */     }
/*      */     
/*      */     public final JsonSerializer<Object> getValueSerializer() {
/* 1375 */       return this.valueSerializer;
/*      */     }
/*      */     
/*      */     public final TypeSerializer getTypeSerializer() {
/* 1379 */       return this.typeSerializer;
/*      */     }
/*      */     
/*      */     public boolean hasSerializer() {
/* 1383 */       return (this.valueSerializer != null || this.typeSerializer != null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serialize(JsonGenerator gen, Object value, DefaultSerializerProvider prov) throws IOException {
/* 1389 */       if (this.typeSerializer != null) {
/* 1390 */         prov.serializePolymorphic(gen, value, this.rootType, this.valueSerializer, this.typeSerializer);
/* 1391 */       } else if (this.valueSerializer != null) {
/* 1392 */         prov.serializeValue(gen, value, this.rootType, this.valueSerializer);
/* 1393 */       } else if (this.rootType != null) {
/* 1394 */         prov.serializeValue(gen, value, this.rootType);
/*      */       } else {
/* 1396 */         prov.serializeValue(gen, value);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ObjectWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */