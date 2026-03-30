/*      */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdResolver;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ContextAttributes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializerCache;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializerFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidDefinitionException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidFormatException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidTypeIdException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.MismatchedInputException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.JsonNodeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.LinkedNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Named;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ObjectBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DeserializationContext
/*      */   extends DatabindContext
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   protected final DeserializerCache _cache;
/*      */   protected final DeserializerFactory _factory;
/*      */   protected final DeserializationConfig _config;
/*      */   protected final int _featureFlags;
/*      */   protected final Class<?> _view;
/*      */   protected transient JsonParser _parser;
/*      */   protected final InjectableValues _injectableValues;
/*      */   protected transient ArrayBuilders _arrayBuilders;
/*      */   protected transient ObjectBuffer _objectBuffer;
/*      */   protected transient DateFormat _dateFormat;
/*      */   protected transient ContextAttributes _attributes;
/*      */   protected LinkedNode<JavaType> _currentType;
/*      */   
/*      */   protected DeserializationContext(DeserializerFactory df) {
/*  150 */     this(df, (DeserializerCache)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected DeserializationContext(DeserializerFactory df, DeserializerCache cache) {
/*  156 */     if (df == null) {
/*  157 */       throw new IllegalArgumentException("Cannot pass null DeserializerFactory");
/*      */     }
/*  159 */     this._factory = df;
/*  160 */     if (cache == null) {
/*  161 */       cache = new DeserializerCache();
/*      */     }
/*  163 */     this._cache = cache;
/*  164 */     this._featureFlags = 0;
/*  165 */     this._config = null;
/*  166 */     this._injectableValues = null;
/*  167 */     this._view = null;
/*  168 */     this._attributes = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected DeserializationContext(DeserializationContext src, DeserializerFactory factory) {
/*  174 */     this._cache = src._cache;
/*  175 */     this._factory = factory;
/*      */     
/*  177 */     this._config = src._config;
/*  178 */     this._featureFlags = src._featureFlags;
/*  179 */     this._view = src._view;
/*  180 */     this._parser = src._parser;
/*  181 */     this._injectableValues = src._injectableValues;
/*  182 */     this._attributes = src._attributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DeserializationContext(DeserializationContext src, DeserializationConfig config, JsonParser p, InjectableValues injectableValues) {
/*  192 */     this._cache = src._cache;
/*  193 */     this._factory = src._factory;
/*      */     
/*  195 */     this._config = config;
/*  196 */     this._featureFlags = config.getDeserializationFeatures();
/*  197 */     this._view = config.getActiveView();
/*  198 */     this._parser = p;
/*  199 */     this._injectableValues = injectableValues;
/*  200 */     this._attributes = config.getAttributes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DeserializationContext(DeserializationContext src) {
/*  207 */     this._cache = new DeserializerCache();
/*  208 */     this._factory = src._factory;
/*      */     
/*  210 */     this._config = src._config;
/*  211 */     this._featureFlags = src._featureFlags;
/*  212 */     this._view = src._view;
/*  213 */     this._injectableValues = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeserializationConfig getConfig() {
/*  223 */     return this._config;
/*      */   }
/*      */   public final Class<?> getActiveView() {
/*  226 */     return this._view;
/*      */   }
/*      */   
/*      */   public final boolean canOverrideAccessModifiers() {
/*  230 */     return this._config.canOverrideAccessModifiers();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(MapperFeature feature) {
/*  235 */     return this._config.isEnabled(feature);
/*      */   }
/*      */ 
/*      */   
/*      */   public final JsonFormat.Value getDefaultPropertyFormat(Class<?> baseType) {
/*  240 */     return this._config.getDefaultPropertyFormat(baseType);
/*      */   }
/*      */ 
/*      */   
/*      */   public final AnnotationIntrospector getAnnotationIntrospector() {
/*  245 */     return this._config.getAnnotationIntrospector();
/*      */   }
/*      */ 
/*      */   
/*      */   public final TypeFactory getTypeFactory() {
/*  250 */     return this._config.getTypeFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  261 */     return this._config.getLocale();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeZone getTimeZone() {
/*  272 */     return this._config.getTimeZone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getAttribute(Object key) {
/*  283 */     return this._attributes.getAttribute(key);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DeserializationContext setAttribute(Object key, Object value) {
/*  289 */     this._attributes = this._attributes.withPerCallAttribute(key, value);
/*  290 */     return this;
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
/*      */   public JavaType getContextualType() {
/*  307 */     return (this._currentType == null) ? null : (JavaType)this._currentType.value();
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
/*      */   public DeserializerFactory getFactory() {
/*  320 */     return this._factory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled(DeserializationFeature feat) {
/*  331 */     return ((this._featureFlags & feat.getMask()) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getDeserializationFeatures() {
/*  341 */     return this._featureFlags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean hasDeserializationFeatures(int featureMask) {
/*  351 */     return ((this._featureFlags & featureMask) == featureMask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean hasSomeOfFeatures(int featureMask) {
/*  361 */     return ((this._featureFlags & featureMask) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonParser getParser() {
/*  372 */     return this._parser;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final Object findInjectableValue(Object valueId, BeanProperty forProperty, Object beanInstance) throws JsonMappingException {
/*  378 */     if (this._injectableValues == null) {
/*  379 */       reportBadDefinition(ClassUtil.classOf(valueId), String.format("No 'injectableValues' configured, cannot inject value with id [%s]", new Object[] { valueId }));
/*      */     }
/*      */     
/*  382 */     return this._injectableValues.findInjectableValue(valueId, this, forProperty, beanInstance);
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
/*      */   public final Base64Variant getBase64Variant() {
/*  394 */     return this._config.getBase64Variant();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonNodeFactory getNodeFactory() {
/*  404 */     return this._config.getNodeFactory();
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
/*      */   public boolean hasValueDeserializerFor(JavaType type, AtomicReference<Throwable> cause) {
/*      */     try {
/*  422 */       return this._cache.hasValueDeserializerFor(this, this._factory, type);
/*  423 */     } catch (JsonMappingException e) {
/*  424 */       if (cause != null) {
/*  425 */         cause.set(e);
/*      */       }
/*  427 */     } catch (RuntimeException e) {
/*  428 */       if (cause == null) {
/*  429 */         throw e;
/*      */       }
/*  431 */       cause.set(e);
/*      */     } 
/*  433 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonDeserializer<Object> findContextualValueDeserializer(JavaType type, BeanProperty prop) throws JsonMappingException {
/*  444 */     JsonDeserializer<Object> deser = this._cache.findValueDeserializer(this, this._factory, type);
/*  445 */     if (deser != null) {
/*  446 */       deser = (JsonDeserializer)handleSecondaryContextualization(deser, prop, type);
/*      */     }
/*  448 */     return deser;
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
/*      */   public final JsonDeserializer<Object> findNonContextualValueDeserializer(JavaType type) throws JsonMappingException {
/*  467 */     return this._cache.findValueDeserializer(this, this._factory, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final JsonDeserializer<Object> findRootValueDeserializer(JavaType type) throws JsonMappingException {
/*  477 */     JsonDeserializer<Object> deser = this._cache.findValueDeserializer(this, this._factory, type);
/*      */     
/*  479 */     if (deser == null) {
/*  480 */       return null;
/*      */     }
/*  482 */     deser = (JsonDeserializer)handleSecondaryContextualization(deser, (BeanProperty)null, type);
/*  483 */     TypeDeserializer typeDeser = this._factory.findTypeDeserializer(this._config, type);
/*  484 */     if (typeDeser != null) {
/*      */       
/*  486 */       typeDeser = typeDeser.forProperty(null);
/*  487 */       return (JsonDeserializer<Object>)new TypeWrappedDeserializer(typeDeser, deser);
/*      */     } 
/*  489 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final KeyDeserializer findKeyDeserializer(JavaType keyType, BeanProperty prop) throws JsonMappingException {
/*  500 */     KeyDeserializer kd = this._cache.findKeyDeserializer(this, this._factory, keyType);
/*      */ 
/*      */     
/*  503 */     if (kd instanceof ContextualKeyDeserializer) {
/*  504 */       kd = ((ContextualKeyDeserializer)kd).createContextual(this, prop);
/*      */     }
/*  506 */     return kd;
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
/*      */   public final JavaType constructType(Class<?> cls) {
/*  543 */     return (cls == null) ? null : this._config.constructType(cls);
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
/*      */   public Class<?> findClass(String className) throws ClassNotFoundException {
/*  557 */     return getTypeFactory().findClass(className);
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
/*      */   public final ObjectBuffer leaseObjectBuffer() {
/*  574 */     ObjectBuffer buf = this._objectBuffer;
/*  575 */     if (buf == null) {
/*  576 */       buf = new ObjectBuffer();
/*      */     } else {
/*  578 */       this._objectBuffer = null;
/*      */     } 
/*  580 */     return buf;
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
/*      */   public final void returnObjectBuffer(ObjectBuffer buf) {
/*  594 */     if (this._objectBuffer == null || buf.initialCapacity() >= this._objectBuffer.initialCapacity())
/*      */     {
/*  596 */       this._objectBuffer = buf;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final ArrayBuilders getArrayBuilders() {
/*  606 */     if (this._arrayBuilders == null) {
/*  607 */       this._arrayBuilders = new ArrayBuilders();
/*      */     }
/*  609 */     return this._arrayBuilders;
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
/*      */   public JsonDeserializer<?> handlePrimaryContextualization(JsonDeserializer<?> deser, BeanProperty prop, JavaType type) throws JsonMappingException {
/*  648 */     if (deser instanceof ContextualDeserializer) {
/*  649 */       this._currentType = new LinkedNode(type, this._currentType);
/*      */       try {
/*  651 */         deser = ((ContextualDeserializer)deser).createContextual(this, prop);
/*      */       } finally {
/*  653 */         this._currentType = this._currentType.next();
/*      */       } 
/*      */     } 
/*  656 */     return deser;
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
/*      */   public JsonDeserializer<?> handleSecondaryContextualization(JsonDeserializer<?> deser, BeanProperty prop, JavaType type) throws JsonMappingException {
/*  679 */     if (deser instanceof ContextualDeserializer) {
/*  680 */       this._currentType = new LinkedNode(type, this._currentType);
/*      */       try {
/*  682 */         deser = ((ContextualDeserializer)deser).createContextual(this, prop);
/*      */       } finally {
/*  684 */         this._currentType = this._currentType.next();
/*      */       } 
/*      */     } 
/*  687 */     return deser;
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
/*      */   public Date parseDate(String dateStr) throws IllegalArgumentException {
/*      */     try {
/*  709 */       DateFormat df = getDateFormat();
/*  710 */       return df.parse(dateStr);
/*  711 */     } catch (ParseException e) {
/*  712 */       throw new IllegalArgumentException(String.format("Failed to parse Date value '%s': %s", new Object[] { dateStr, e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar constructCalendar(Date d) {
/*  723 */     Calendar c = Calendar.getInstance(getTimeZone());
/*  724 */     c.setTime(d);
/*  725 */     return c;
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
/*      */   public <T> T readValue(JsonParser p, Class<T> type) throws IOException {
/*  746 */     return readValue(p, getTypeFactory().constructType(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(JsonParser p, JavaType type) throws IOException {
/*  754 */     JsonDeserializer<Object> deser = findRootValueDeserializer(type);
/*  755 */     if (deser == null) {
/*  756 */       reportBadDefinition(type, "Could not find JsonDeserializer for type " + type);
/*      */     }
/*      */     
/*  759 */     return (T)deser.deserialize(p, this);
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
/*      */   public <T> T readPropertyValue(JsonParser p, BeanProperty prop, Class<T> type) throws IOException {
/*  771 */     return readPropertyValue(p, prop, getTypeFactory().constructType(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readPropertyValue(JsonParser p, BeanProperty prop, JavaType type) throws IOException {
/*  779 */     JsonDeserializer<Object> deser = findContextualValueDeserializer(type, prop);
/*  780 */     if (deser == null) {
/*  781 */       return reportBadDefinition(type, String.format("Could not find JsonDeserializer for type %s (via property %s)", new Object[] { type, ClassUtil.nameOf(prop) }));
/*      */     }
/*      */ 
/*      */     
/*  785 */     return (T)deser.deserialize(p, this);
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
/*      */   public boolean handleUnknownProperty(JsonParser p, JsonDeserializer<?> deser, Object instanceOrClass, String propName) throws IOException {
/*  807 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/*  808 */     while (h != null) {
/*      */       
/*  810 */       if (((DeserializationProblemHandler)h.value()).handleUnknownProperty(this, p, deser, instanceOrClass, propName)) {
/*  811 */         return true;
/*      */       }
/*  813 */       h = h.next();
/*      */     } 
/*      */     
/*  816 */     if (!isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/*  817 */       p.skipChildren();
/*  818 */       return true;
/*      */     } 
/*      */     
/*  821 */     Collection<Object> propIds = (deser == null) ? null : deser.getKnownPropertyNames();
/*  822 */     throw UnrecognizedPropertyException.from(this._parser, instanceOrClass, propName, propIds);
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
/*      */   public Object handleWeirdKey(Class<?> keyClass, String keyValue, String msg, Object... msgArgs) throws IOException {
/*  850 */     msg = _format(msg, msgArgs);
/*  851 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/*  852 */     while (h != null) {
/*      */       
/*  854 */       Object key = ((DeserializationProblemHandler)h.value()).handleWeirdKey(this, keyClass, keyValue, msg);
/*  855 */       if (key != DeserializationProblemHandler.NOT_HANDLED) {
/*      */         
/*  857 */         if (key == null || keyClass.isInstance(key)) {
/*  858 */           return key;
/*      */         }
/*  860 */         throw weirdStringException(keyValue, keyClass, String.format("DeserializationProblemHandler.handleWeirdStringValue() for type %s returned value of type %s", new Object[] { keyClass, key.getClass() }));
/*      */       } 
/*      */ 
/*      */       
/*  864 */       h = h.next();
/*      */     } 
/*  866 */     throw weirdKeyException(keyClass, keyValue, msg);
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
/*      */   public Object handleWeirdStringValue(Class<?> targetClass, String value, String msg, Object... msgArgs) throws IOException {
/*  894 */     msg = _format(msg, msgArgs);
/*  895 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/*  896 */     while (h != null) {
/*      */       
/*  898 */       Object instance = ((DeserializationProblemHandler)h.value()).handleWeirdStringValue(this, targetClass, value, msg);
/*  899 */       if (instance != DeserializationProblemHandler.NOT_HANDLED) {
/*      */         
/*  901 */         if (instance == null || targetClass.isInstance(instance)) {
/*  902 */           return instance;
/*      */         }
/*  904 */         throw weirdStringException(value, targetClass, String.format("DeserializationProblemHandler.handleWeirdStringValue() for type %s returned value of type %s", new Object[] { targetClass, instance.getClass() }));
/*      */       } 
/*      */ 
/*      */       
/*  908 */       h = h.next();
/*      */     } 
/*  910 */     throw weirdStringException(value, targetClass, msg);
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
/*      */   public Object handleWeirdNumberValue(Class<?> targetClass, Number value, String msg, Object... msgArgs) throws IOException {
/*  937 */     msg = _format(msg, msgArgs);
/*  938 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/*  939 */     while (h != null) {
/*      */       
/*  941 */       Object key = ((DeserializationProblemHandler)h.value()).handleWeirdNumberValue(this, targetClass, value, msg);
/*  942 */       if (key != DeserializationProblemHandler.NOT_HANDLED) {
/*      */         
/*  944 */         if (key == null || targetClass.isInstance(key)) {
/*  945 */           return key;
/*      */         }
/*  947 */         throw weirdNumberException(value, targetClass, _format("DeserializationProblemHandler.handleWeirdNumberValue() for type %s returned value of type %s", new Object[] { targetClass, key.getClass() }));
/*      */       } 
/*      */ 
/*      */       
/*  951 */       h = h.next();
/*      */     } 
/*  953 */     throw weirdNumberException(value, targetClass, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object handleWeirdNativeValue(JavaType targetType, Object badValue, JsonParser p) throws IOException {
/*  960 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/*  961 */     Class<?> raw = targetType.getRawClass();
/*  962 */     for (; h != null; h = h.next()) {
/*      */       
/*  964 */       Object goodValue = ((DeserializationProblemHandler)h.value()).handleWeirdNativeValue(this, targetType, badValue, p);
/*  965 */       if (goodValue != DeserializationProblemHandler.NOT_HANDLED) {
/*      */         
/*  967 */         if (goodValue == null || raw.isInstance(goodValue)) {
/*  968 */           return goodValue;
/*      */         }
/*  970 */         throw JsonMappingException.from(p, _format("DeserializationProblemHandler.handleWeirdNativeValue() for type %s returned value of type %s", new Object[] { targetType, goodValue.getClass() }));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  975 */     throw weirdNativeValueException(badValue, raw);
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
/*      */   public Object handleMissingInstantiator(Class<?> instClass, ValueInstantiator valueInst, JsonParser p, String msg, Object... msgArgs) throws IOException {
/* 1000 */     if (p == null) {
/* 1001 */       p = getParser();
/*      */     }
/* 1003 */     msg = _format(msg, msgArgs);
/* 1004 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/* 1005 */     while (h != null) {
/*      */       
/* 1007 */       Object instance = ((DeserializationProblemHandler)h.value()).handleMissingInstantiator(this, instClass, valueInst, p, msg);
/*      */       
/* 1009 */       if (instance != DeserializationProblemHandler.NOT_HANDLED) {
/*      */         
/* 1011 */         if (instance == null || instClass.isInstance(instance)) {
/* 1012 */           return instance;
/*      */         }
/* 1014 */         reportBadDefinition(constructType(instClass), String.format("DeserializationProblemHandler.handleMissingInstantiator() for type %s returned value of type %s", new Object[] { instClass, ClassUtil.classNameOf(instance) }));
/*      */       } 
/*      */ 
/*      */       
/* 1018 */       h = h.next();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     if (valueInst != null && !valueInst.canInstantiate()) {
/* 1025 */       msg = String.format("Cannot construct instance of %s (no Creators, like default construct, exist): %s", new Object[] { ClassUtil.nameOf(instClass), msg });
/*      */       
/* 1027 */       return reportBadDefinition(constructType(instClass), msg);
/*      */     } 
/* 1029 */     msg = String.format("Cannot construct instance of %s (although at least one Creator exists): %s", new Object[] { ClassUtil.nameOf(instClass), msg });
/*      */     
/* 1031 */     return reportInputMismatch(instClass, msg, new Object[0]);
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
/*      */   public Object handleInstantiationProblem(Class<?> instClass, Object argument, Throwable t) throws IOException {
/* 1055 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/* 1056 */     while (h != null) {
/*      */       
/* 1058 */       Object instance = ((DeserializationProblemHandler)h.value()).handleInstantiationProblem(this, instClass, argument, t);
/* 1059 */       if (instance != DeserializationProblemHandler.NOT_HANDLED) {
/*      */         
/* 1061 */         if (instance == null || instClass.isInstance(instance)) {
/* 1062 */           return instance;
/*      */         }
/* 1064 */         reportBadDefinition(constructType(instClass), String.format("DeserializationProblemHandler.handleInstantiationProblem() for type %s returned value of type %s", new Object[] { instClass, ClassUtil.classNameOf(instance) }));
/*      */       } 
/*      */ 
/*      */       
/* 1068 */       h = h.next();
/*      */     } 
/*      */     
/* 1071 */     ClassUtil.throwIfIOE(t);
/* 1072 */     throw instantiationException(instClass, t);
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
/*      */   public Object handleUnexpectedToken(Class<?> instClass, JsonParser p) throws IOException {
/* 1092 */     return handleUnexpectedToken(instClass, p.getCurrentToken(), p, (String)null, new Object[0]);
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
/*      */   public Object handleUnexpectedToken(Class<?> instClass, JsonToken t, JsonParser p, String msg, Object... msgArgs) throws IOException {
/* 1114 */     msg = _format(msg, msgArgs);
/* 1115 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/* 1116 */     while (h != null) {
/* 1117 */       Object instance = ((DeserializationProblemHandler)h.value()).handleUnexpectedToken(this, instClass, t, p, msg);
/*      */       
/* 1119 */       if (instance != DeserializationProblemHandler.NOT_HANDLED) {
/* 1120 */         if (instance == null || instClass.isInstance(instance)) {
/* 1121 */           return instance;
/*      */         }
/* 1123 */         reportBadDefinition(constructType(instClass), String.format("DeserializationProblemHandler.handleUnexpectedToken() for type %s returned value of type %s", new Object[] { instance.getClass() }));
/*      */       } 
/*      */ 
/*      */       
/* 1127 */       h = h.next();
/*      */     } 
/* 1129 */     if (msg == null) {
/* 1130 */       if (t == null) {
/* 1131 */         msg = String.format("Unexpected end-of-input when binding data into %s", new Object[] { _calcName(instClass) });
/*      */       } else {
/*      */         
/* 1134 */         msg = String.format("Cannot deserialize instance of %s out of %s token", new Object[] { _calcName(instClass), t });
/*      */       } 
/*      */     }
/*      */     
/* 1138 */     reportInputMismatch(instClass, msg, new Object[0]);
/* 1139 */     return null;
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
/*      */   public JavaType handleUnknownTypeId(JavaType baseType, String id, TypeIdResolver idResolver, String extraDesc) throws IOException {
/* 1165 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/* 1166 */     while (h != null) {
/*      */       
/* 1168 */       JavaType type = ((DeserializationProblemHandler)h.value()).handleUnknownTypeId(this, baseType, id, idResolver, extraDesc);
/* 1169 */       if (type != null) {
/* 1170 */         if (type.hasRawClass(Void.class)) {
/* 1171 */           return null;
/*      */         }
/*      */         
/* 1174 */         if (type.isTypeOrSubTypeOf(baseType.getRawClass())) {
/* 1175 */           return type;
/*      */         }
/* 1177 */         throw invalidTypeIdException(baseType, id, "problem handler tried to resolve into non-subtype: " + type);
/*      */       } 
/*      */       
/* 1180 */       h = h.next();
/*      */     } 
/*      */     
/* 1183 */     if (!isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) {
/* 1184 */       return null;
/*      */     }
/* 1186 */     throw invalidTypeIdException(baseType, id, extraDesc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType handleMissingTypeId(JavaType baseType, TypeIdResolver idResolver, String extraDesc) throws IOException {
/* 1195 */     LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
/* 1196 */     while (h != null) {
/*      */       
/* 1198 */       JavaType type = ((DeserializationProblemHandler)h.value()).handleMissingTypeId(this, baseType, idResolver, extraDesc);
/* 1199 */       if (type != null) {
/* 1200 */         if (type.hasRawClass(Void.class)) {
/* 1201 */           return null;
/*      */         }
/*      */         
/* 1204 */         if (type.isTypeOrSubTypeOf(baseType.getRawClass())) {
/* 1205 */           return type;
/*      */         }
/* 1207 */         throw invalidTypeIdException(baseType, null, "problem handler tried to resolve into non-subtype: " + type);
/*      */       } 
/*      */       
/* 1210 */       h = h.next();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1217 */     throw missingTypeIdException(baseType, extraDesc);
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
/*      */   public void reportWrongTokenException(JsonDeserializer<?> deser, JsonToken expToken, String msg, Object... msgArgs) throws JsonMappingException {
/* 1241 */     msg = _format(msg, msgArgs);
/* 1242 */     throw wrongTokenException(getParser(), deser.handledType(), expToken, msg);
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
/*      */   public void reportWrongTokenException(JavaType targetType, JsonToken expToken, String msg, Object... msgArgs) throws JsonMappingException {
/* 1259 */     msg = _format(msg, msgArgs);
/* 1260 */     throw wrongTokenException(getParser(), targetType, expToken, msg);
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
/*      */   public void reportWrongTokenException(Class<?> targetType, JsonToken expToken, String msg, Object... msgArgs) throws JsonMappingException {
/* 1277 */     msg = _format(msg, msgArgs);
/* 1278 */     throw wrongTokenException(getParser(), targetType, expToken, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T reportUnresolvedObjectId(ObjectIdReader oidReader, Object bean) throws JsonMappingException {
/* 1287 */     String msg = String.format("No Object Id found for an instance of %s, to assign to property '%s'", new Object[] { ClassUtil.classNameOf(bean), oidReader.propertyName });
/*      */     
/* 1289 */     return reportInputMismatch((BeanProperty)oidReader.idProperty, msg, new Object[0]);
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
/*      */   public <T> T reportInputMismatch(BeanProperty prop, String msg, Object... msgArgs) throws JsonMappingException {
/* 1301 */     msg = _format(msg, msgArgs);
/* 1302 */     JavaType type = (prop == null) ? null : prop.getType();
/* 1303 */     throw MismatchedInputException.from(getParser(), type, msg);
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
/*      */   public <T> T reportInputMismatch(JsonDeserializer<?> src, String msg, Object... msgArgs) throws JsonMappingException {
/* 1315 */     msg = _format(msg, msgArgs);
/* 1316 */     throw MismatchedInputException.from(getParser(), src.handledType(), msg);
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
/*      */   public <T> T reportInputMismatch(Class<?> targetType, String msg, Object... msgArgs) throws JsonMappingException {
/* 1328 */     msg = _format(msg, msgArgs);
/* 1329 */     throw MismatchedInputException.from(getParser(), targetType, msg);
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
/*      */   public <T> T reportInputMismatch(JavaType targetType, String msg, Object... msgArgs) throws JsonMappingException {
/* 1341 */     msg = _format(msg, msgArgs);
/* 1342 */     throw MismatchedInputException.from(getParser(), targetType, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T reportTrailingTokens(Class<?> targetType, JsonParser p, JsonToken trailingToken) throws JsonMappingException {
/* 1348 */     throw MismatchedInputException.from(p, targetType, String.format("Trailing token (of type %s) found after value (bound as %s): not allowed as per `DeserializationFeature.FAIL_ON_TRAILING_TOKENS`", new Object[] { trailingToken, ClassUtil.nameOf(targetType) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void reportWrongTokenException(JsonParser p, JsonToken expToken, String msg, Object... msgArgs) throws JsonMappingException {
/* 1359 */     msg = _format(msg, msgArgs);
/* 1360 */     throw wrongTokenException(p, expToken, msg);
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
/*      */   public void reportUnknownProperty(Object instanceOrClass, String fieldName, JsonDeserializer<?> deser) throws JsonMappingException {
/* 1379 */     if (isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/*      */       
/* 1381 */       Collection<Object> propIds = (deser == null) ? null : deser.getKnownPropertyNames();
/* 1382 */       throw UnrecognizedPropertyException.from(this._parser, instanceOrClass, fieldName, propIds);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void reportMissingContent(String msg, Object... msgArgs) throws JsonMappingException {
/* 1394 */     throw MismatchedInputException.from(getParser(), (JavaType)null, "No content to map due to end-of-input");
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
/*      */   public <T> T reportBadTypeDefinition(BeanDescription bean, String msg, Object... msgArgs) throws JsonMappingException {
/* 1413 */     msg = _format(msg, msgArgs);
/* 1414 */     String beanDesc = ClassUtil.nameOf(bean.getBeanClass());
/* 1415 */     msg = String.format("Invalid type definition for type %s: %s", new Object[] { beanDesc, msg });
/* 1416 */     throw InvalidDefinitionException.from(this._parser, msg, bean, null);
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
/*      */   public <T> T reportBadPropertyDefinition(BeanDescription bean, BeanPropertyDefinition prop, String msg, Object... msgArgs) throws JsonMappingException {
/* 1428 */     msg = _format(msg, msgArgs);
/* 1429 */     String propName = ClassUtil.nameOf((Named)prop);
/* 1430 */     String beanDesc = ClassUtil.nameOf(bean.getBeanClass());
/* 1431 */     msg = String.format("Invalid definition for property %s (of type %s): %s", new Object[] { propName, beanDesc, msg });
/*      */     
/* 1433 */     throw InvalidDefinitionException.from(this._parser, msg, bean, prop);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> T reportBadDefinition(JavaType type, String msg) throws JsonMappingException {
/* 1438 */     throw InvalidDefinitionException.from(this._parser, msg, type);
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
/*      */   public <T> T reportBadMerge(JsonDeserializer<?> deser) throws JsonMappingException {
/* 1454 */     if (isEnabled(MapperFeature.IGNORE_MERGE_FOR_UNMERGEABLE)) {
/* 1455 */       return null;
/*      */     }
/* 1457 */     JavaType type = constructType(deser.handledType());
/* 1458 */     String msg = String.format("Invalid configuration: values of type %s cannot be merged", new Object[] { type });
/* 1459 */     throw InvalidDefinitionException.from(getParser(), msg, type);
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
/*      */   public JsonMappingException wrongTokenException(JsonParser p, JavaType targetType, JsonToken expToken, String extra) {
/* 1482 */     String msg = String.format("Unexpected token (%s), expected %s", new Object[] { p.getCurrentToken(), expToken });
/*      */     
/* 1484 */     msg = _colonConcat(msg, extra);
/* 1485 */     return (JsonMappingException)MismatchedInputException.from(p, targetType, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonMappingException wrongTokenException(JsonParser p, Class<?> targetType, JsonToken expToken, String extra) {
/* 1491 */     String msg = String.format("Unexpected token (%s), expected %s", new Object[] { p.getCurrentToken(), expToken });
/*      */     
/* 1493 */     msg = _colonConcat(msg, extra);
/* 1494 */     return (JsonMappingException)MismatchedInputException.from(p, targetType, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonMappingException wrongTokenException(JsonParser p, JsonToken expToken, String msg) {
/* 1501 */     return wrongTokenException(p, (JavaType)null, expToken, msg);
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
/*      */   public JsonMappingException weirdKeyException(Class<?> keyClass, String keyValue, String msg) {
/* 1514 */     return (JsonMappingException)InvalidFormatException.from(this._parser, String.format("Cannot deserialize Map key of type %s from String %s: %s", new Object[] { ClassUtil.nameOf(keyClass), _quotedString(keyValue), msg }), keyValue, keyClass);
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
/*      */   public JsonMappingException weirdStringException(String value, Class<?> instClass, String msg) {
/* 1535 */     return (JsonMappingException)InvalidFormatException.from(this._parser, String.format("Cannot deserialize value of type %s from String %s: %s", new Object[] { ClassUtil.nameOf(instClass), _quotedString(value), msg }), value, instClass);
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
/*      */   public JsonMappingException weirdNumberException(Number value, Class<?> instClass, String msg) {
/* 1550 */     return (JsonMappingException)InvalidFormatException.from(this._parser, String.format("Cannot deserialize value of type %s from number %s: %s", new Object[] { ClassUtil.nameOf(instClass), String.valueOf(value), msg }), value, instClass);
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
/*      */   public JsonMappingException weirdNativeValueException(Object value, Class<?> instClass) {
/* 1568 */     return (JsonMappingException)InvalidFormatException.from(this._parser, String.format("Cannot deserialize value of type %s from native value (`JsonToken.VALUE_EMBEDDED_OBJECT`) of type %s: incompatible types", new Object[] { ClassUtil.nameOf(instClass), ClassUtil.classNameOf(value) }), value, instClass);
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
/*      */   public JsonMappingException instantiationException(Class<?> instClass, Throwable cause) {
/* 1585 */     JavaType type = constructType(instClass);
/* 1586 */     String msg = String.format("Cannot construct instance of %s, problem: %s", new Object[] { ClassUtil.nameOf(instClass), cause.getMessage() });
/*      */     
/* 1588 */     InvalidDefinitionException e = InvalidDefinitionException.from(this._parser, msg, type);
/* 1589 */     e.initCause(cause);
/* 1590 */     return (JsonMappingException)e;
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
/*      */   public JsonMappingException instantiationException(Class<?> instClass, String msg0) {
/* 1604 */     JavaType type = constructType(instClass);
/* 1605 */     String msg = String.format("Cannot construct instance of %s: %s", new Object[] { ClassUtil.nameOf(instClass), msg0 });
/*      */     
/* 1607 */     return (JsonMappingException)InvalidDefinitionException.from(this._parser, msg, type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonMappingException invalidTypeIdException(JavaType baseType, String typeId, String extraDesc) {
/* 1613 */     String msg = String.format("Could not resolve type id '%s' as a subtype of %s", new Object[] { typeId, baseType });
/*      */     
/* 1615 */     return (JsonMappingException)InvalidTypeIdException.from(this._parser, _colonConcat(msg, extraDesc), baseType, typeId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonMappingException missingTypeIdException(JavaType baseType, String extraDesc) {
/* 1623 */     String msg = String.format("Missing type id when trying to resolve subtype of %s", new Object[] { baseType });
/*      */     
/* 1625 */     return (JsonMappingException)InvalidTypeIdException.from(this._parser, _colonConcat(msg, extraDesc), baseType, null);
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
/*      */   @Deprecated
/*      */   public JsonMappingException unknownTypeException(JavaType type, String id, String extraDesc) {
/* 1643 */     String msg = String.format("Could not resolve type id '%s' into a subtype of %s", new Object[] { id, type });
/*      */     
/* 1645 */     msg = _colonConcat(msg, extraDesc);
/* 1646 */     return (JsonMappingException)MismatchedInputException.from(this._parser, type, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonMappingException endOfInputException(Class<?> instClass) {
/* 1657 */     return (JsonMappingException)MismatchedInputException.from(this._parser, instClass, "Unexpected end-of-input when trying to deserialize a " + instClass.getName());
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
/*      */   public void reportMappingException(String msg, Object... msgArgs) throws JsonMappingException {
/* 1682 */     throw JsonMappingException.from(getParser(), _format(msg, msgArgs));
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
/*      */   @Deprecated
/*      */   public JsonMappingException mappingException(String message) {
/* 1698 */     return JsonMappingException.from(getParser(), message);
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
/*      */   @Deprecated
/*      */   public JsonMappingException mappingException(String msg, Object... msgArgs) {
/* 1714 */     return JsonMappingException.from(getParser(), _format(msg, msgArgs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonMappingException mappingException(Class<?> targetClass) {
/* 1724 */     return mappingException(targetClass, this._parser.getCurrentToken());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonMappingException mappingException(Class<?> targetClass, JsonToken token) {
/* 1732 */     return JsonMappingException.from(this._parser, String.format("Cannot deserialize instance of %s out of %s token", new Object[] { _calcName(targetClass), token }));
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
/*      */   protected DateFormat getDateFormat() {
/* 1745 */     if (this._dateFormat != null) {
/* 1746 */       return this._dateFormat;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1753 */     DateFormat df = this._config.getDateFormat();
/* 1754 */     this._dateFormat = df = (DateFormat)df.clone();
/* 1755 */     return df;
/*      */   }
/*      */   
/*      */   public abstract ReadableObjectId findObjectId(Object paramObject, ObjectIdGenerator<?> paramObjectIdGenerator, ObjectIdResolver paramObjectIdResolver);
/*      */   
/*      */   public abstract void checkUnresolvedObjectId() throws UnresolvedForwardReference;
/*      */   
/*      */   public abstract JsonDeserializer<Object> deserializerInstance(Annotated paramAnnotated, Object paramObject) throws JsonMappingException;
/*      */   
/*      */   public abstract KeyDeserializer keyDeserializerInstance(Annotated paramAnnotated, Object paramObject) throws JsonMappingException;
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\DeserializationContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */