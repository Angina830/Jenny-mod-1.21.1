/*      */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Type;
/*      */ import java.net.URL;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.FormatFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.FormatSchema;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonPointer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.filter.FilteringParserDelegate;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.filter.JsonPointerBasedFilter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.filter.TokenFilter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.ResolvedType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.TypeReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ContextAttributes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.PackageVersion;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DataFormatReaders;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.JsonNodeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.TreeTraversingParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.SimpleType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ public class ObjectReader extends ObjectCodec implements Versioned, Serializable {
/*      */   private static final long serialVersionUID = 2L;
/*   49 */   private static final JavaType JSON_NODE_TYPE = (JavaType)SimpleType.constructUnsafe(JsonNode.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final DeserializationConfig _config;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final DefaultDeserializationContext _context;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonFactory _parserFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _unwrapRoot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TokenFilter _filter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JavaType _valueType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonDeserializer<Object> _rootDeserializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object _valueToUpdate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final FormatSchema _schema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final InjectableValues _injectableValues;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final DataFormatReaders _dataFormatReaders;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader(ObjectMapper mapper, DeserializationConfig config) {
/*  168 */     this(mapper, config, null, null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader(ObjectMapper mapper, DeserializationConfig config, JavaType valueType, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues) {
/*  179 */     this._config = config;
/*  180 */     this._context = mapper._deserializationContext;
/*  181 */     this._rootDeserializers = mapper._rootDeserializers;
/*  182 */     this._parserFactory = mapper._jsonFactory;
/*  183 */     this._valueType = valueType;
/*  184 */     this._valueToUpdate = valueToUpdate;
/*  185 */     this._schema = schema;
/*  186 */     this._injectableValues = injectableValues;
/*  187 */     this._unwrapRoot = config.useRootWrapping();
/*      */     
/*  189 */     this._rootDeserializer = _prefetchRootDeserializer(valueType);
/*  190 */     this._dataFormatReaders = null;
/*  191 */     this._filter = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader(ObjectReader base, DeserializationConfig config, JavaType valueType, JsonDeserializer<Object> rootDeser, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues, DataFormatReaders dataFormatReaders) {
/*  202 */     this._config = config;
/*  203 */     this._context = base._context;
/*      */     
/*  205 */     this._rootDeserializers = base._rootDeserializers;
/*  206 */     this._parserFactory = base._parserFactory;
/*      */     
/*  208 */     this._valueType = valueType;
/*  209 */     this._rootDeserializer = rootDeser;
/*  210 */     this._valueToUpdate = valueToUpdate;
/*  211 */     this._schema = schema;
/*  212 */     this._injectableValues = injectableValues;
/*  213 */     this._unwrapRoot = config.useRootWrapping();
/*  214 */     this._dataFormatReaders = dataFormatReaders;
/*  215 */     this._filter = base._filter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader(ObjectReader base, DeserializationConfig config) {
/*  223 */     this._config = config;
/*  224 */     this._context = base._context;
/*      */     
/*  226 */     this._rootDeserializers = base._rootDeserializers;
/*  227 */     this._parserFactory = base._parserFactory;
/*      */     
/*  229 */     this._valueType = base._valueType;
/*  230 */     this._rootDeserializer = base._rootDeserializer;
/*  231 */     this._valueToUpdate = base._valueToUpdate;
/*  232 */     this._schema = base._schema;
/*  233 */     this._injectableValues = base._injectableValues;
/*  234 */     this._unwrapRoot = config.useRootWrapping();
/*  235 */     this._dataFormatReaders = base._dataFormatReaders;
/*  236 */     this._filter = base._filter;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader(ObjectReader base, JsonFactory f) {
/*  242 */     this._config = (DeserializationConfig)base._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, f.requiresPropertyOrdering());
/*      */     
/*  244 */     this._context = base._context;
/*      */     
/*  246 */     this._rootDeserializers = base._rootDeserializers;
/*  247 */     this._parserFactory = f;
/*      */     
/*  249 */     this._valueType = base._valueType;
/*  250 */     this._rootDeserializer = base._rootDeserializer;
/*  251 */     this._valueToUpdate = base._valueToUpdate;
/*  252 */     this._schema = base._schema;
/*  253 */     this._injectableValues = base._injectableValues;
/*  254 */     this._unwrapRoot = base._unwrapRoot;
/*  255 */     this._dataFormatReaders = base._dataFormatReaders;
/*  256 */     this._filter = base._filter;
/*      */   }
/*      */   
/*      */   protected ObjectReader(ObjectReader base, TokenFilter filter) {
/*  260 */     this._config = base._config;
/*  261 */     this._context = base._context;
/*  262 */     this._rootDeserializers = base._rootDeserializers;
/*  263 */     this._parserFactory = base._parserFactory;
/*  264 */     this._valueType = base._valueType;
/*  265 */     this._rootDeserializer = base._rootDeserializer;
/*  266 */     this._valueToUpdate = base._valueToUpdate;
/*  267 */     this._schema = base._schema;
/*  268 */     this._injectableValues = base._injectableValues;
/*  269 */     this._unwrapRoot = base._unwrapRoot;
/*  270 */     this._dataFormatReaders = base._dataFormatReaders;
/*  271 */     this._filter = filter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Version version() {
/*  280 */     return PackageVersion.VERSION;
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
/*      */   protected ObjectReader _new(ObjectReader base, JsonFactory f) {
/*  297 */     return new ObjectReader(base, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader _new(ObjectReader base, DeserializationConfig config) {
/*  306 */     return new ObjectReader(base, config);
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
/*      */   protected ObjectReader _new(ObjectReader base, DeserializationConfig config, JavaType valueType, JsonDeserializer<Object> rootDeser, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues, DataFormatReaders dataFormatReaders) {
/*  318 */     return new ObjectReader(base, config, valueType, rootDeser, valueToUpdate, schema, injectableValues, dataFormatReaders);
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
/*      */   protected <T> MappingIterator<T> _newIterator(JsonParser p, DeserializationContext ctxt, JsonDeserializer<?> deser, boolean parserManaged) {
/*  331 */     return new MappingIterator<>(this._valueType, p, ctxt, deser, parserManaged, this._valueToUpdate);
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
/*      */   protected JsonToken _initForReading(DeserializationContext ctxt, JsonParser p) throws IOException {
/*  344 */     if (this._schema != null) {
/*  345 */       p.setSchema(this._schema);
/*      */     }
/*  347 */     this._config.initialize(p);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  353 */     JsonToken t = p.getCurrentToken();
/*  354 */     if (t == null) {
/*  355 */       t = p.nextToken();
/*  356 */       if (t == null)
/*      */       {
/*  358 */         ctxt.reportInputMismatch(this._valueType, "No content to map due to end-of-input", new Object[0]);
/*      */       }
/*      */     } 
/*      */     
/*  362 */     return t;
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
/*      */   protected void _initForMultiRead(DeserializationContext ctxt, JsonParser p) throws IOException {
/*  377 */     if (this._schema != null) {
/*  378 */       p.setSchema(this._schema);
/*      */     }
/*  380 */     this._config.initialize(p);
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
/*      */   public ObjectReader with(DeserializationFeature feature) {
/*  394 */     return _with(this._config.with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader with(DeserializationFeature first, DeserializationFeature... other) {
/*  404 */     return _with(this._config.with(first, other));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withFeatures(DeserializationFeature... features) {
/*  412 */     return _with(this._config.withFeatures(features));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader without(DeserializationFeature feature) {
/*  420 */     return _with(this._config.without(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader without(DeserializationFeature first, DeserializationFeature... other) {
/*  429 */     return _with(this._config.without(first, other));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withoutFeatures(DeserializationFeature... features) {
/*  437 */     return _with(this._config.withoutFeatures(features));
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
/*      */   public ObjectReader with(JsonParser.Feature feature) {
/*  451 */     return _with(this._config.with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withFeatures(JsonParser.Feature... features) {
/*  459 */     return _with(this._config.withFeatures(features));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader without(JsonParser.Feature feature) {
/*  467 */     return _with(this._config.without(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withoutFeatures(JsonParser.Feature... features) {
/*  475 */     return _with(this._config.withoutFeatures(features));
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
/*      */   public ObjectReader with(FormatFeature feature) {
/*  491 */     return _with(this._config.with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withFeatures(FormatFeature... features) {
/*  501 */     return _with(this._config.withFeatures(features));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader without(FormatFeature feature) {
/*  511 */     return _with(this._config.without(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withoutFeatures(FormatFeature... features) {
/*  521 */     return _with(this._config.withoutFeatures(features));
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
/*      */   public ObjectReader at(String value) {
/*  536 */     return new ObjectReader(this, (TokenFilter)new JsonPointerBasedFilter(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader at(JsonPointer pointer) {
/*  545 */     return new ObjectReader(this, (TokenFilter)new JsonPointerBasedFilter(pointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader with(DeserializationConfig config) {
/*  556 */     return _with(config);
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
/*      */   public ObjectReader with(InjectableValues injectableValues) {
/*  568 */     if (this._injectableValues == injectableValues) {
/*  569 */       return this;
/*      */     }
/*  571 */     return _new(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, injectableValues, this._dataFormatReaders);
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
/*      */   public ObjectReader with(JsonNodeFactory f) {
/*  585 */     return _with(this._config.with(f));
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
/*      */   public ObjectReader with(JsonFactory f) {
/*  600 */     if (f == this._parserFactory) {
/*  601 */       return this;
/*      */     }
/*  603 */     ObjectReader r = _new(this, f);
/*      */     
/*  605 */     if (f.getCodec() == null) {
/*  606 */       f.setCodec(r);
/*      */     }
/*  608 */     return r;
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
/*      */   public ObjectReader withRootName(String rootName) {
/*  621 */     return _with((DeserializationConfig)this._config.withRootName(rootName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withRootName(PropertyName rootName) {
/*  628 */     return _with(this._config.withRootName(rootName));
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
/*      */   public ObjectReader withoutRootName() {
/*  642 */     return _with(this._config.withRootName(PropertyName.NO_NAME));
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
/*      */   public ObjectReader with(FormatSchema schema) {
/*  655 */     if (this._schema == schema) {
/*  656 */       return this;
/*      */     }
/*  658 */     _verifySchemaType(schema);
/*  659 */     return _new(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, schema, this._injectableValues, this._dataFormatReaders);
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
/*      */   public ObjectReader forType(JavaType valueType) {
/*  674 */     if (valueType != null && valueType.equals(this._valueType)) {
/*  675 */       return this;
/*      */     }
/*  677 */     JsonDeserializer<Object> rootDeser = _prefetchRootDeserializer(valueType);
/*      */     
/*  679 */     DataFormatReaders det = this._dataFormatReaders;
/*  680 */     if (det != null) {
/*  681 */       det = det.withType(valueType);
/*      */     }
/*  683 */     return _new(this, this._config, valueType, rootDeser, this._valueToUpdate, this._schema, this._injectableValues, det);
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
/*      */   public ObjectReader forType(Class<?> valueType) {
/*  697 */     return forType(this._config.constructType(valueType));
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
/*      */   public ObjectReader forType(TypeReference<?> valueTypeRef) {
/*  710 */     return forType(this._config.getTypeFactory().constructType(valueTypeRef.getType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader withType(JavaType valueType) {
/*  718 */     return forType(valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader withType(Class<?> valueType) {
/*  726 */     return forType(this._config.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader withType(Type valueType) {
/*  734 */     return forType(this._config.getTypeFactory().constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader withType(TypeReference<?> valueTypeRef) {
/*  742 */     return forType(this._config.getTypeFactory().constructType(valueTypeRef.getType()));
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
/*      */   public ObjectReader withValueToUpdate(Object value) {
/*      */     JavaType t;
/*  755 */     if (value == this._valueToUpdate) return this; 
/*  756 */     if (value == null)
/*      */     {
/*      */       
/*  759 */       return _new(this, this._config, this._valueType, this._rootDeserializer, null, this._schema, this._injectableValues, this._dataFormatReaders);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  768 */     if (this._valueType == null) {
/*  769 */       t = this._config.constructType(value.getClass());
/*      */     } else {
/*  771 */       t = this._valueType;
/*      */     } 
/*  773 */     return _new(this, this._config, t, this._rootDeserializer, value, this._schema, this._injectableValues, this._dataFormatReaders);
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
/*      */   public ObjectReader withView(Class<?> activeView) {
/*  785 */     return _with(this._config.withView(activeView));
/*      */   }
/*      */   
/*      */   public ObjectReader with(Locale l) {
/*  789 */     return _with((DeserializationConfig)this._config.with(l));
/*      */   }
/*      */   
/*      */   public ObjectReader with(TimeZone tz) {
/*  793 */     return _with((DeserializationConfig)this._config.with(tz));
/*      */   }
/*      */   
/*      */   public ObjectReader withHandler(DeserializationProblemHandler h) {
/*  797 */     return _with(this._config.withHandler(h));
/*      */   }
/*      */   
/*      */   public ObjectReader with(Base64Variant defaultBase64) {
/*  801 */     return _with((DeserializationConfig)this._config.with(defaultBase64));
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
/*      */   public ObjectReader withFormatDetection(ObjectReader... readers) {
/*  827 */     return withFormatDetection(new DataFormatReaders(readers));
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
/*      */   public ObjectReader withFormatDetection(DataFormatReaders readers) {
/*  846 */     return _new(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, this._injectableValues, readers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader with(ContextAttributes attrs) {
/*  854 */     return _with(this._config.with(attrs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withAttributes(Map<?, ?> attrs) {
/*  861 */     return _with((DeserializationConfig)this._config.withAttributes(attrs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withAttribute(Object key, Object value) {
/*  868 */     return _with((DeserializationConfig)this._config.withAttribute(key, value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader withoutAttribute(Object key) {
/*  875 */     return _with((DeserializationConfig)this._config.withoutAttribute(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectReader _with(DeserializationConfig newConfig) {
/*  885 */     if (newConfig == this._config) {
/*  886 */       return this;
/*      */     }
/*  888 */     ObjectReader r = _new(this, newConfig);
/*  889 */     if (this._dataFormatReaders != null) {
/*  890 */       r = r.withFormatDetection(this._dataFormatReaders.with(newConfig));
/*      */     }
/*  892 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled(DeserializationFeature f) {
/*  902 */     return this._config.isEnabled(f);
/*      */   }
/*      */   
/*      */   public boolean isEnabled(MapperFeature f) {
/*  906 */     return this._config.isEnabled(f);
/*      */   }
/*      */   
/*      */   public boolean isEnabled(JsonParser.Feature f) {
/*  910 */     return this._parserFactory.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeserializationConfig getConfig() {
/*  917 */     return this._config;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFactory getFactory() {
/*  925 */     return this._parserFactory;
/*      */   }
/*      */   
/*      */   public TypeFactory getTypeFactory() {
/*  929 */     return this._config.getTypeFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContextAttributes getAttributes() {
/*  936 */     return this._config.getAttributes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InjectableValues getInjectableValues() {
/*  943 */     return this._injectableValues;
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
/*      */   public <T> T readValue(JsonParser p) throws IOException {
/*  965 */     return (T)_bind(p, this._valueToUpdate);
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
/*      */   public <T> T readValue(JsonParser p, Class<T> valueType) throws IOException {
/*  982 */     return forType(valueType).readValue(p);
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
/*      */   public <T> T readValue(JsonParser p, TypeReference<?> valueTypeRef) throws IOException {
/*  999 */     return forType(valueTypeRef).readValue(p);
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
/*      */   public <T> T readValue(JsonParser p, ResolvedType valueType) throws IOException {
/* 1015 */     return forType((JavaType)valueType).readValue(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(JsonParser p, JavaType valueType) throws IOException {
/* 1026 */     return forType(valueType).readValue(p);
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
/*      */   public <T> Iterator<T> readValues(JsonParser p, Class<T> valueType) throws IOException {
/* 1050 */     return forType(valueType).readValues(p);
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
/*      */   public <T> Iterator<T> readValues(JsonParser p, TypeReference<?> valueTypeRef) throws IOException {
/* 1074 */     return forType(valueTypeRef).readValues(p);
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
/*      */   public <T> Iterator<T> readValues(JsonParser p, ResolvedType valueType) throws IOException {
/* 1098 */     return readValues(p, (JavaType)valueType);
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
/*      */   public <T> Iterator<T> readValues(JsonParser p, JavaType valueType) throws IOException {
/* 1121 */     return forType(valueType).readValues(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonNode createArrayNode() {
/* 1132 */     return (JsonNode)this._config.getNodeFactory().arrayNode();
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonNode createObjectNode() {
/* 1137 */     return (JsonNode)this._config.getNodeFactory().objectNode();
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonParser treeAsTokens(TreeNode n) {
/* 1142 */     return (JsonParser)new TreeTraversingParser((JsonNode)n, this);
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
/*      */   public <T extends TreeNode> T readTree(JsonParser p) throws IOException {
/* 1159 */     return (T)_bindAsTree(p);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeTree(JsonGenerator g, TreeNode rootNode) {
/* 1164 */     throw new UnsupportedOperationException();
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
/*      */   public <T> T readValue(InputStream src) throws IOException {
/* 1182 */     if (this._dataFormatReaders != null) {
/* 1183 */       return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(src), false);
/*      */     }
/* 1185 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> T readValue(Reader src) throws IOException {
/* 1197 */     if (this._dataFormatReaders != null) {
/* 1198 */       _reportUndetectableSource(src);
/*      */     }
/* 1200 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> T readValue(String src) throws IOException {
/* 1212 */     if (this._dataFormatReaders != null) {
/* 1213 */       _reportUndetectableSource(src);
/*      */     }
/*      */     
/* 1216 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> T readValue(byte[] src) throws IOException {
/* 1228 */     if (this._dataFormatReaders != null) {
/* 1229 */       return (T)_detectBindAndClose(src, 0, src.length);
/*      */     }
/* 1231 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> T readValue(byte[] src, int offset, int length) throws IOException {
/* 1244 */     if (this._dataFormatReaders != null) {
/* 1245 */       return (T)_detectBindAndClose(src, offset, length);
/*      */     }
/* 1247 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src, offset, length), false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(File src) throws IOException {
/* 1255 */     if (this._dataFormatReaders != null) {
/* 1256 */       return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(_inputStream(src)), true);
/*      */     }
/*      */     
/* 1259 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> T readValue(URL src) throws IOException {
/* 1272 */     if (this._dataFormatReaders != null) {
/* 1273 */       return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(_inputStream(src)), true);
/*      */     }
/* 1275 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> T readValue(JsonNode src) throws IOException {
/* 1289 */     if (this._dataFormatReaders != null) {
/* 1290 */       _reportUndetectableSource(src);
/*      */     }
/* 1292 */     return (T)_bindAndClose(_considerFilter(treeAsTokens(src), false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(DataInput src) throws IOException {
/* 1301 */     if (this._dataFormatReaders != null) {
/* 1302 */       _reportUndetectableSource(src);
/*      */     }
/* 1304 */     return (T)_bindAndClose(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public JsonNode readTree(InputStream in) throws IOException {
/* 1318 */     if (this._dataFormatReaders != null) {
/* 1319 */       return _detectBindAndCloseAsTree(in);
/*      */     }
/* 1321 */     return _bindAndCloseAsTree(_considerFilter(this._parserFactory.createParser(in), false));
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
/*      */   public JsonNode readTree(Reader r) throws IOException {
/* 1335 */     if (this._dataFormatReaders != null) {
/* 1336 */       _reportUndetectableSource(r);
/*      */     }
/* 1338 */     return _bindAndCloseAsTree(_considerFilter(this._parserFactory.createParser(r), false));
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
/*      */   public JsonNode readTree(String json) throws IOException {
/* 1352 */     if (this._dataFormatReaders != null) {
/* 1353 */       _reportUndetectableSource(json);
/*      */     }
/* 1355 */     return _bindAndCloseAsTree(_considerFilter(this._parserFactory.createParser(json), false));
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonNode readTree(DataInput src) throws IOException {
/* 1360 */     if (this._dataFormatReaders != null) {
/* 1361 */       _reportUndetectableSource(src);
/*      */     }
/* 1363 */     return _bindAndCloseAsTree(_considerFilter(this._parserFactory.createParser(src), false));
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
/*      */   public <T> MappingIterator<T> readValues(JsonParser p) throws IOException {
/* 1386 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/*      */     
/* 1388 */     return _newIterator(p, (DeserializationContext)defaultDeserializationContext, _findRootDeserializer((DeserializationContext)defaultDeserializationContext), false);
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
/*      */   public <T> MappingIterator<T> readValues(InputStream src) throws IOException {
/* 1414 */     if (this._dataFormatReaders != null) {
/* 1415 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(src), false);
/*      */     }
/*      */     
/* 1418 */     return _bindAndReadValues(_considerFilter(this._parserFactory.createParser(src), true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> MappingIterator<T> readValues(Reader src) throws IOException {
/* 1428 */     if (this._dataFormatReaders != null) {
/* 1429 */       _reportUndetectableSource(src);
/*      */     }
/* 1431 */     JsonParser p = _considerFilter(this._parserFactory.createParser(src), true);
/* 1432 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/* 1433 */     _initForMultiRead((DeserializationContext)defaultDeserializationContext, p);
/* 1434 */     p.nextToken();
/* 1435 */     return _newIterator(p, (DeserializationContext)defaultDeserializationContext, _findRootDeserializer((DeserializationContext)defaultDeserializationContext), true);
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
/*      */   public <T> MappingIterator<T> readValues(String json) throws IOException {
/* 1447 */     if (this._dataFormatReaders != null) {
/* 1448 */       _reportUndetectableSource(json);
/*      */     }
/* 1450 */     JsonParser p = _considerFilter(this._parserFactory.createParser(json), true);
/* 1451 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/* 1452 */     _initForMultiRead((DeserializationContext)defaultDeserializationContext, p);
/* 1453 */     p.nextToken();
/* 1454 */     return _newIterator(p, (DeserializationContext)defaultDeserializationContext, _findRootDeserializer((DeserializationContext)defaultDeserializationContext), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> MappingIterator<T> readValues(byte[] src, int offset, int length) throws IOException {
/* 1463 */     if (this._dataFormatReaders != null) {
/* 1464 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(src, offset, length), false);
/*      */     }
/* 1466 */     return _bindAndReadValues(_considerFilter(this._parserFactory.createParser(src, offset, length), true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final <T> MappingIterator<T> readValues(byte[] src) throws IOException {
/* 1475 */     return readValues(src, 0, src.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> MappingIterator<T> readValues(File src) throws IOException {
/* 1484 */     if (this._dataFormatReaders != null) {
/* 1485 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(_inputStream(src)), false);
/*      */     }
/*      */     
/* 1488 */     return _bindAndReadValues(_considerFilter(this._parserFactory.createParser(src), true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> MappingIterator<T> readValues(URL src) throws IOException {
/* 1499 */     if (this._dataFormatReaders != null) {
/* 1500 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(_inputStream(src)), true);
/*      */     }
/*      */     
/* 1503 */     return _bindAndReadValues(_considerFilter(this._parserFactory.createParser(src), true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> MappingIterator<T> readValues(DataInput src) throws IOException {
/* 1511 */     if (this._dataFormatReaders != null) {
/* 1512 */       _reportUndetectableSource(src);
/*      */     }
/* 1514 */     return _bindAndReadValues(_considerFilter(this._parserFactory.createParser(src), true));
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
/*      */   public <T> T treeToValue(TreeNode n, Class<T> valueType) throws JsonProcessingException {
/*      */     try {
/* 1527 */       return readValue(treeAsTokens(n), valueType);
/* 1528 */     } catch (JsonProcessingException e) {
/* 1529 */       throw e;
/* 1530 */     } catch (IOException e) {
/* 1531 */       throw JsonMappingException.fromUnexpectedIOE(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeValue(JsonGenerator gen, Object value) throws IOException {
/* 1537 */     throw new UnsupportedOperationException("Not implemented for ObjectReader");
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
/*      */   protected Object _bind(JsonParser p, Object valueToUpdate) throws IOException {
/*      */     Object result;
/* 1555 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/* 1556 */     JsonToken t = _initForReading((DeserializationContext)defaultDeserializationContext, p);
/* 1557 */     if (t == JsonToken.VALUE_NULL) {
/* 1558 */       if (valueToUpdate == null) {
/* 1559 */         result = _findRootDeserializer((DeserializationContext)defaultDeserializationContext).getNullValue((DeserializationContext)defaultDeserializationContext);
/*      */       } else {
/* 1561 */         result = valueToUpdate;
/*      */       } 
/* 1563 */     } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
/* 1564 */       result = valueToUpdate;
/*      */     } else {
/* 1566 */       JsonDeserializer<Object> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext);
/* 1567 */       if (this._unwrapRoot) {
/* 1568 */         result = _unwrapAndDeserialize(p, (DeserializationContext)defaultDeserializationContext, this._valueType, deser);
/*      */       }
/* 1570 */       else if (valueToUpdate == null) {
/* 1571 */         result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/*      */       }
/*      */       else {
/*      */         
/* 1575 */         result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext, valueToUpdate);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1580 */     p.clearCurrentToken();
/* 1581 */     if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 1582 */       _verifyNoTrailingTokens(p, (DeserializationContext)defaultDeserializationContext, this._valueType);
/*      */     }
/* 1584 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Object _bindAndClose(JsonParser p0) throws IOException {
/* 1589 */     try (JsonParser p = p0) {
/*      */       Object result;
/*      */       
/* 1592 */       DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/* 1593 */       JsonToken t = _initForReading((DeserializationContext)defaultDeserializationContext, p);
/* 1594 */       if (t == JsonToken.VALUE_NULL) {
/* 1595 */         if (this._valueToUpdate == null) {
/* 1596 */           result = _findRootDeserializer((DeserializationContext)defaultDeserializationContext).getNullValue((DeserializationContext)defaultDeserializationContext);
/*      */         } else {
/* 1598 */           result = this._valueToUpdate;
/*      */         } 
/* 1600 */       } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
/* 1601 */         result = this._valueToUpdate;
/*      */       } else {
/* 1603 */         JsonDeserializer<Object> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext);
/* 1604 */         if (this._unwrapRoot) {
/* 1605 */           result = _unwrapAndDeserialize(p, (DeserializationContext)defaultDeserializationContext, this._valueType, deser);
/*      */         }
/* 1607 */         else if (this._valueToUpdate == null) {
/* 1608 */           result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/*      */         } else {
/* 1610 */           deser.deserialize(p, (DeserializationContext)defaultDeserializationContext, this._valueToUpdate);
/* 1611 */           result = this._valueToUpdate;
/*      */         } 
/*      */       } 
/*      */       
/* 1615 */       if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 1616 */         _verifyNoTrailingTokens(p, (DeserializationContext)defaultDeserializationContext, this._valueType);
/*      */       }
/* 1618 */       return result;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final JsonNode _bindAndCloseAsTree(JsonParser p0) throws IOException {
/* 1623 */     try (JsonParser p = p0) {
/* 1624 */       return _bindAsTree(p);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonNode _bindAsTree(JsonParser p) throws IOException {
/*      */     Object result;
/* 1633 */     this._config.initialize(p);
/* 1634 */     if (this._schema != null) {
/* 1635 */       p.setSchema(this._schema);
/*      */     }
/*      */     
/* 1638 */     JsonToken t = p.getCurrentToken();
/* 1639 */     if (t == null) {
/* 1640 */       t = p.nextToken();
/* 1641 */       if (t == null) {
/* 1642 */         return null;
/*      */       }
/*      */     } 
/* 1645 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/* 1646 */     if (t == JsonToken.VALUE_NULL) {
/* 1647 */       return (JsonNode)defaultDeserializationContext.getNodeFactory().nullNode();
/*      */     }
/* 1649 */     JsonDeserializer<Object> deser = _findTreeDeserializer((DeserializationContext)defaultDeserializationContext);
/*      */     
/* 1651 */     if (this._unwrapRoot) {
/* 1652 */       result = _unwrapAndDeserialize(p, (DeserializationContext)defaultDeserializationContext, JSON_NODE_TYPE, deser);
/*      */     } else {
/* 1654 */       result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/* 1655 */       if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 1656 */         _verifyNoTrailingTokens(p, (DeserializationContext)defaultDeserializationContext, JSON_NODE_TYPE);
/*      */       }
/*      */     } 
/* 1659 */     return (JsonNode)result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <T> MappingIterator<T> _bindAndReadValues(JsonParser p) throws IOException {
/* 1667 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p);
/* 1668 */     _initForMultiRead((DeserializationContext)defaultDeserializationContext, p);
/* 1669 */     p.nextToken();
/* 1670 */     return _newIterator(p, (DeserializationContext)defaultDeserializationContext, _findRootDeserializer((DeserializationContext)defaultDeserializationContext), true);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Object _unwrapAndDeserialize(JsonParser p, DeserializationContext ctxt, JavaType rootType, JsonDeserializer<Object> deser) throws IOException {
/*      */     Object result;
/* 1676 */     PropertyName expRootName = this._config.findRootName(rootType);
/*      */     
/* 1678 */     String expSimpleName = expRootName.getSimpleName();
/*      */     
/* 1680 */     if (p.getCurrentToken() != JsonToken.START_OBJECT) {
/* 1681 */       ctxt.reportWrongTokenException(rootType, JsonToken.START_OBJECT, "Current token not START_OBJECT (needed to unwrap root name '%s'), but %s", new Object[] { expSimpleName, p.getCurrentToken() });
/*      */     }
/*      */ 
/*      */     
/* 1685 */     if (p.nextToken() != JsonToken.FIELD_NAME) {
/* 1686 */       ctxt.reportWrongTokenException(rootType, JsonToken.FIELD_NAME, "Current token not FIELD_NAME (to contain expected root name '%s'), but %s", new Object[] { expSimpleName, p.getCurrentToken() });
/*      */     }
/*      */ 
/*      */     
/* 1690 */     String actualName = p.getCurrentName();
/* 1691 */     if (!expSimpleName.equals(actualName)) {
/* 1692 */       ctxt.reportInputMismatch(rootType, "Root name '%s' does not match expected ('%s') for type %s", new Object[] { actualName, expSimpleName, rootType });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1697 */     p.nextToken();
/*      */     
/* 1699 */     if (this._valueToUpdate == null) {
/* 1700 */       result = deser.deserialize(p, ctxt);
/*      */     } else {
/* 1702 */       deser.deserialize(p, ctxt, this._valueToUpdate);
/* 1703 */       result = this._valueToUpdate;
/*      */     } 
/*      */     
/* 1706 */     if (p.nextToken() != JsonToken.END_OBJECT) {
/* 1707 */       ctxt.reportWrongTokenException(rootType, JsonToken.END_OBJECT, "Current token not END_OBJECT (to match wrapper object with root name '%s'), but %s", new Object[] { expSimpleName, p.getCurrentToken() });
/*      */     }
/*      */ 
/*      */     
/* 1711 */     if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 1712 */       _verifyNoTrailingTokens(p, ctxt, this._valueType);
/*      */     }
/* 1714 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonParser _considerFilter(JsonParser p, boolean multiValue) {
/* 1723 */     return (this._filter == null || FilteringParserDelegate.class.isInstance(p)) ? p : (JsonParser)new FilteringParserDelegate(p, this._filter, false, multiValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyNoTrailingTokens(JsonParser p, DeserializationContext ctxt, JavaType bindType) throws IOException {
/* 1734 */     JsonToken t = p.nextToken();
/* 1735 */     if (t != null) {
/* 1736 */       Class<?> bt = ClassUtil.rawClass(bindType);
/* 1737 */       if (bt == null && 
/* 1738 */         this._valueToUpdate != null) {
/* 1739 */         bt = this._valueToUpdate.getClass();
/*      */       }
/*      */       
/* 1742 */       ctxt.reportTrailingTokens(bt, p, t);
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
/*      */   protected Object _detectBindAndClose(byte[] src, int offset, int length) throws IOException {
/* 1755 */     DataFormatReaders.Match match = this._dataFormatReaders.findFormat(src, offset, length);
/* 1756 */     if (!match.hasMatch()) {
/* 1757 */       _reportUnkownFormat(this._dataFormatReaders, match);
/*      */     }
/* 1759 */     JsonParser p = match.createParserWithMatch();
/* 1760 */     return match.getReader()._bindAndClose(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _detectBindAndClose(DataFormatReaders.Match match, boolean forceClosing) throws IOException {
/* 1767 */     if (!match.hasMatch()) {
/* 1768 */       _reportUnkownFormat(this._dataFormatReaders, match);
/*      */     }
/* 1770 */     JsonParser p = match.createParserWithMatch();
/*      */ 
/*      */     
/* 1773 */     if (forceClosing) {
/* 1774 */       p.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
/*      */     }
/*      */     
/* 1777 */     return match.getReader()._bindAndClose(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <T> MappingIterator<T> _detectBindAndReadValues(DataFormatReaders.Match match, boolean forceClosing) throws IOException {
/* 1784 */     if (!match.hasMatch()) {
/* 1785 */       _reportUnkownFormat(this._dataFormatReaders, match);
/*      */     }
/* 1787 */     JsonParser p = match.createParserWithMatch();
/*      */ 
/*      */     
/* 1790 */     if (forceClosing) {
/* 1791 */       p.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
/*      */     }
/*      */     
/* 1794 */     return match.getReader()._bindAndReadValues(p);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonNode _detectBindAndCloseAsTree(InputStream in) throws IOException {
/* 1800 */     DataFormatReaders.Match match = this._dataFormatReaders.findFormat(in);
/* 1801 */     if (!match.hasMatch()) {
/* 1802 */       _reportUnkownFormat(this._dataFormatReaders, match);
/*      */     }
/* 1804 */     JsonParser p = match.createParserWithMatch();
/* 1805 */     p.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
/* 1806 */     return match.getReader()._bindAndCloseAsTree(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportUnkownFormat(DataFormatReaders detector, DataFormatReaders.Match match) throws JsonProcessingException {
/* 1817 */     throw new JsonParseException(null, "Cannot detect format from input, does not look like any of detectable formats " + detector.toString());
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
/*      */   protected void _verifySchemaType(FormatSchema schema) {
/* 1832 */     if (schema != null && 
/* 1833 */       !this._parserFactory.canUseSchema(schema)) {
/* 1834 */       throw new IllegalArgumentException("Cannot use FormatSchema of type " + schema.getClass().getName() + " for format " + this._parserFactory.getFormatName());
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
/*      */   protected DefaultDeserializationContext createDeserializationContext(JsonParser p) {
/* 1846 */     return this._context.createInstance(this._config, p, this._injectableValues);
/*      */   }
/*      */   
/*      */   protected InputStream _inputStream(URL src) throws IOException {
/* 1850 */     return src.openStream();
/*      */   }
/*      */   
/*      */   protected InputStream _inputStream(File f) throws IOException {
/* 1854 */     return new FileInputStream(f);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportUndetectableSource(Object src) throws JsonProcessingException {
/* 1860 */     throw new JsonParseException(null, "Cannot use source of type " + src.getClass().getName() + " with format auto-detection: must be byte- not char-based");
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
/*      */   protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext ctxt) throws JsonMappingException {
/* 1876 */     if (this._rootDeserializer != null) {
/* 1877 */       return this._rootDeserializer;
/*      */     }
/*      */ 
/*      */     
/* 1881 */     JavaType t = this._valueType;
/* 1882 */     if (t == null) {
/* 1883 */       ctxt.reportBadDefinition((JavaType)null, "No value type configured for ObjectReader");
/*      */     }
/*      */ 
/*      */     
/* 1887 */     JsonDeserializer<Object> deser = this._rootDeserializers.get(t);
/* 1888 */     if (deser != null) {
/* 1889 */       return deser;
/*      */     }
/*      */     
/* 1892 */     deser = ctxt.findRootValueDeserializer(t);
/* 1893 */     if (deser == null) {
/* 1894 */       ctxt.reportBadDefinition(t, "Cannot find a deserializer for type " + t);
/*      */     }
/* 1896 */     this._rootDeserializers.put(t, deser);
/* 1897 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> _findTreeDeserializer(DeserializationContext ctxt) throws JsonMappingException {
/* 1906 */     JsonDeserializer<Object> deser = this._rootDeserializers.get(JSON_NODE_TYPE);
/* 1907 */     if (deser == null) {
/*      */       
/* 1909 */       deser = ctxt.findRootValueDeserializer(JSON_NODE_TYPE);
/* 1910 */       if (deser == null) {
/* 1911 */         ctxt.reportBadDefinition(JSON_NODE_TYPE, "Cannot find a deserializer for type " + JSON_NODE_TYPE);
/*      */       }
/*      */       
/* 1914 */       this._rootDeserializers.put(JSON_NODE_TYPE, deser);
/*      */     } 
/* 1916 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> _prefetchRootDeserializer(JavaType valueType) {
/* 1926 */     if (valueType == null || !this._config.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH)) {
/* 1927 */       return null;
/*      */     }
/*      */     
/* 1930 */     JsonDeserializer<Object> deser = this._rootDeserializers.get(valueType);
/* 1931 */     if (deser == null) {
/*      */       
/*      */       try {
/* 1934 */         DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(null);
/* 1935 */         deser = defaultDeserializationContext.findRootValueDeserializer(valueType);
/* 1936 */         if (deser != null) {
/* 1937 */           this._rootDeserializers.put(valueType, deser);
/*      */         }
/* 1939 */         return deser;
/* 1940 */       } catch (JsonProcessingException e) {}
/*      */     }
/*      */ 
/*      */     
/* 1944 */     return deser;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ObjectReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */