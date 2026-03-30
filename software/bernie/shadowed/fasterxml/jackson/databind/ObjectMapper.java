/*      */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Type;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.DateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ServiceLoader;
/*      */ import java.util.Set;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonAutoDetect;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.PropertyAccessor;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variants;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.FormatSchema;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonEncoding;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.io.SegmentedStringWriter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.ResolvedType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.TypeReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.BaseSettings;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ConfigOverrides;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ContextAttributes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MutableConfigOverride;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.PackageVersion;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializerFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.Deserializers;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.KeyDeserializers;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.MismatchedInputException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ArrayNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.JsonNodeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.NullNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.POJONode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.node.TreeTraversingParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanSerializerFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.FilterProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.SerializerFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.SimpleType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeModifier;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RootNameLookup;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.StdDateFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectMapper
/*      */   extends ObjectCodec
/*      */   implements Versioned, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 2L;
/*      */   
/*      */   public enum DefaultTyping
/*      */   {
/*  156 */     JAVA_LANG_OBJECT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  166 */     OBJECT_AND_NON_CONCRETE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  175 */     NON_CONCRETE_AND_ARRAYS,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  186 */     NON_FINAL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DefaultTypeResolverBuilder
/*      */     extends StdTypeResolverBuilder
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final ObjectMapper.DefaultTyping _appliesFor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping t) {
/*  211 */       this._appliesFor = t;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
/*  218 */       return useForType(baseType) ? super.buildTypeDeserializer(config, baseType, subtypes) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypeSerializer buildTypeSerializer(SerializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
/*  225 */       return useForType(baseType) ? super.buildTypeSerializer(config, baseType, subtypes) : null;
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
/*      */     public boolean useForType(JavaType t) {
/*  240 */       if (t.isPrimitive()) {
/*  241 */         return false;
/*      */       }
/*      */       
/*  244 */       switch (this._appliesFor) {
/*      */         case NON_CONCRETE_AND_ARRAYS:
/*  246 */           while (t.isArrayType()) {
/*  247 */             t = t.getContentType();
/*      */           }
/*      */ 
/*      */         
/*      */         case OBJECT_AND_NON_CONCRETE:
/*  252 */           while (t.isReferenceType()) {
/*  253 */             t = t.getReferencedType();
/*      */           }
/*  255 */           return (t.isJavaLangObject() || (!t.isConcrete() && !TreeNode.class.isAssignableFrom(t.getRawClass())));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case NON_FINAL:
/*  261 */           while (t.isArrayType()) {
/*  262 */             t = t.getContentType();
/*      */           }
/*      */           
/*  265 */           while (t.isReferenceType()) {
/*  266 */             t = t.getReferencedType();
/*      */           }
/*      */           
/*  269 */           return (!t.isFinal() && !TreeNode.class.isAssignableFrom(t.getRawClass()));
/*      */       } 
/*      */       
/*  272 */       return t.isJavaLangObject();
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
/*  286 */   private static final JavaType JSON_NODE_TYPE = (JavaType)SimpleType.constructUnsafe(JsonNode.class);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR = (AnnotationIntrospector)new JacksonAnnotationIntrospector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  297 */   protected static final BaseSettings DEFAULT_BASE = new BaseSettings(null, DEFAULT_ANNOTATION_INTROSPECTOR, null, TypeFactory.defaultInstance(), null, (DateFormat)StdDateFormat.instance, null, Locale.getDefault(), null, Base64Variants.getDefaultVariant());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonFactory _jsonFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TypeFactory _typeFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InjectableValues _injectableValues;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SubtypeResolver _subtypeResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ConfigOverrides _configOverrides;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SimpleMixInResolver _mixIns;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SerializationConfig _serializationConfig;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DefaultSerializerProvider _serializerProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SerializerFactory _serializerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DeserializationConfig _deserializationConfig;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DefaultDeserializationContext _deserializationContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Set<Object> _registeredModuleTypes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  458 */   protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers = new ConcurrentHashMap<>(64, 0.6F, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper() {
/*  480 */     this(null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper(JsonFactory jf) {
/*  489 */     this(jf, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectMapper(ObjectMapper src) {
/*  499 */     this._jsonFactory = src._jsonFactory.copy();
/*  500 */     this._jsonFactory.setCodec(this);
/*  501 */     this._subtypeResolver = src._subtypeResolver;
/*  502 */     this._typeFactory = src._typeFactory;
/*  503 */     this._injectableValues = src._injectableValues;
/*  504 */     this._configOverrides = src._configOverrides.copy();
/*  505 */     this._mixIns = src._mixIns.copy();
/*      */     
/*  507 */     RootNameLookup rootNames = new RootNameLookup();
/*  508 */     this._serializationConfig = new SerializationConfig(src._serializationConfig, this._mixIns, rootNames, this._configOverrides);
/*      */     
/*  510 */     this._deserializationConfig = new DeserializationConfig(src._deserializationConfig, this._mixIns, rootNames, this._configOverrides);
/*      */     
/*  512 */     this._serializerProvider = src._serializerProvider.copy();
/*  513 */     this._deserializationContext = src._deserializationContext.copy();
/*      */ 
/*      */     
/*  516 */     this._serializerFactory = src._serializerFactory;
/*      */ 
/*      */     
/*  519 */     Set<Object> reg = src._registeredModuleTypes;
/*  520 */     if (reg == null) {
/*  521 */       this._registeredModuleTypes = null;
/*      */     } else {
/*  523 */       this._registeredModuleTypes = new LinkedHashSet(reg);
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
/*      */   public ObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
/*  548 */     if (jf == null) {
/*  549 */       this._jsonFactory = new MappingJsonFactory(this);
/*      */     } else {
/*  551 */       this._jsonFactory = jf;
/*  552 */       if (jf.getCodec() == null) {
/*  553 */         this._jsonFactory.setCodec(this);
/*      */       }
/*      */     } 
/*  556 */     this._subtypeResolver = (SubtypeResolver)new StdSubtypeResolver();
/*  557 */     RootNameLookup rootNames = new RootNameLookup();
/*      */     
/*  559 */     this._typeFactory = TypeFactory.defaultInstance();
/*      */     
/*  561 */     SimpleMixInResolver mixins = new SimpleMixInResolver(null);
/*  562 */     this._mixIns = mixins;
/*  563 */     BaseSettings base = DEFAULT_BASE.withClassIntrospector(defaultClassIntrospector());
/*  564 */     this._configOverrides = new ConfigOverrides();
/*  565 */     this._serializationConfig = new SerializationConfig(base, this._subtypeResolver, mixins, rootNames, this._configOverrides);
/*      */     
/*  567 */     this._deserializationConfig = new DeserializationConfig(base, this._subtypeResolver, mixins, rootNames, this._configOverrides);
/*      */ 
/*      */ 
/*      */     
/*  571 */     boolean needOrder = this._jsonFactory.requiresPropertyOrdering();
/*  572 */     if (needOrder ^ this._serializationConfig.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)) {
/*  573 */       configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, needOrder);
/*      */     }
/*      */     
/*  576 */     this._serializerProvider = (sp == null) ? (DefaultSerializerProvider)new DefaultSerializerProvider.Impl() : sp;
/*  577 */     this._deserializationContext = (dc == null) ? (DefaultDeserializationContext)new DefaultDeserializationContext.Impl((DeserializerFactory)BeanDeserializerFactory.instance) : dc;
/*      */ 
/*      */ 
/*      */     
/*  581 */     this._serializerFactory = (SerializerFactory)BeanSerializerFactory.instance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ClassIntrospector defaultClassIntrospector() {
/*  591 */     return (ClassIntrospector)new BasicClassIntrospector();
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
/*      */   public ObjectMapper copy() {
/*  616 */     _checkInvalidCopy(ObjectMapper.class);
/*  617 */     return new ObjectMapper(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _checkInvalidCopy(Class<?> exp) {
/*  625 */     if (getClass() != exp)
/*      */     {
/*  627 */       throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
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
/*      */   protected ObjectReader _newReader(DeserializationConfig config) {
/*  646 */     return new ObjectReader(this, config);
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
/*      */   protected ObjectReader _newReader(DeserializationConfig config, JavaType valueType, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues) {
/*  658 */     return new ObjectReader(this, config, valueType, valueToUpdate, schema, injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter _newWriter(SerializationConfig config) {
/*  668 */     return new ObjectWriter(this, config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter _newWriter(SerializationConfig config, FormatSchema schema) {
/*  678 */     return new ObjectWriter(this, config, schema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectWriter _newWriter(SerializationConfig config, JavaType rootType, PrettyPrinter pp) {
/*  689 */     return new ObjectWriter(this, config, rootType, pp);
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
/*      */   public Version version() {
/*  704 */     return PackageVersion.VERSION;
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
/*      */   public ObjectMapper registerModule(Module module) {
/*  722 */     if (isEnabled(MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS)) {
/*  723 */       Object typeId = module.getTypeId();
/*  724 */       if (typeId != null) {
/*  725 */         if (this._registeredModuleTypes == null)
/*      */         {
/*      */           
/*  728 */           this._registeredModuleTypes = new LinkedHashSet();
/*      */         }
/*      */         
/*  731 */         if (!this._registeredModuleTypes.add(typeId)) {
/*  732 */           return this;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  741 */     String name = module.getModuleName();
/*  742 */     if (name == null) {
/*  743 */       throw new IllegalArgumentException("Module without defined name");
/*      */     }
/*  745 */     Version version = module.version();
/*  746 */     if (version == null) {
/*  747 */       throw new IllegalArgumentException("Module without defined version");
/*      */     }
/*      */ 
/*      */     
/*  751 */     module.setupModule(new Module.SetupContext()
/*      */         {
/*      */ 
/*      */           
/*      */           public Version getMapperVersion()
/*      */           {
/*  757 */             return ObjectMapper.this.version();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public <C extends ObjectCodec> C getOwner() {
/*  764 */             return (C)ObjectMapper.this;
/*      */           }
/*      */ 
/*      */           
/*      */           public TypeFactory getTypeFactory() {
/*  769 */             return ObjectMapper.this._typeFactory;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean isEnabled(MapperFeature f) {
/*  774 */             return ObjectMapper.this.isEnabled(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean isEnabled(DeserializationFeature f) {
/*  779 */             return ObjectMapper.this.isEnabled(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean isEnabled(SerializationFeature f) {
/*  784 */             return ObjectMapper.this.isEnabled(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean isEnabled(JsonFactory.Feature f) {
/*  789 */             return ObjectMapper.this.isEnabled(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean isEnabled(JsonParser.Feature f) {
/*  794 */             return ObjectMapper.this.isEnabled(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean isEnabled(JsonGenerator.Feature f) {
/*  799 */             return ObjectMapper.this.isEnabled(f);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public MutableConfigOverride configOverride(Class<?> type) {
/*  806 */             return ObjectMapper.this.configOverride(type);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void addDeserializers(Deserializers d) {
/*  813 */             DeserializerFactory df = ObjectMapper.this._deserializationContext._factory.withAdditionalDeserializers(d);
/*  814 */             ObjectMapper.this._deserializationContext = ObjectMapper.this._deserializationContext.with(df);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addKeyDeserializers(KeyDeserializers d) {
/*  819 */             DeserializerFactory df = ObjectMapper.this._deserializationContext._factory.withAdditionalKeyDeserializers(d);
/*  820 */             ObjectMapper.this._deserializationContext = ObjectMapper.this._deserializationContext.with(df);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addBeanDeserializerModifier(BeanDeserializerModifier modifier) {
/*  825 */             DeserializerFactory df = ObjectMapper.this._deserializationContext._factory.withDeserializerModifier(modifier);
/*  826 */             ObjectMapper.this._deserializationContext = ObjectMapper.this._deserializationContext.with(df);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void addSerializers(Serializers s) {
/*  833 */             ObjectMapper.this._serializerFactory = ObjectMapper.this._serializerFactory.withAdditionalSerializers(s);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addKeySerializers(Serializers s) {
/*  838 */             ObjectMapper.this._serializerFactory = ObjectMapper.this._serializerFactory.withAdditionalKeySerializers(s);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addBeanSerializerModifier(BeanSerializerModifier modifier) {
/*  843 */             ObjectMapper.this._serializerFactory = ObjectMapper.this._serializerFactory.withSerializerModifier(modifier);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void addAbstractTypeResolver(AbstractTypeResolver resolver) {
/*  850 */             DeserializerFactory df = ObjectMapper.this._deserializationContext._factory.withAbstractTypeResolver(resolver);
/*  851 */             ObjectMapper.this._deserializationContext = ObjectMapper.this._deserializationContext.with(df);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addTypeModifier(TypeModifier modifier) {
/*  856 */             TypeFactory f = ObjectMapper.this._typeFactory;
/*  857 */             f = f.withModifier(modifier);
/*  858 */             ObjectMapper.this.setTypeFactory(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addValueInstantiators(ValueInstantiators instantiators) {
/*  863 */             DeserializerFactory df = ObjectMapper.this._deserializationContext._factory.withValueInstantiators(instantiators);
/*  864 */             ObjectMapper.this._deserializationContext = ObjectMapper.this._deserializationContext.with(df);
/*      */           }
/*      */ 
/*      */           
/*      */           public void setClassIntrospector(ClassIntrospector ci) {
/*  869 */             ObjectMapper.this._deserializationConfig = (DeserializationConfig)ObjectMapper.this._deserializationConfig.with(ci);
/*  870 */             ObjectMapper.this._serializationConfig = (SerializationConfig)ObjectMapper.this._serializationConfig.with(ci);
/*      */           }
/*      */ 
/*      */           
/*      */           public void insertAnnotationIntrospector(AnnotationIntrospector ai) {
/*  875 */             ObjectMapper.this._deserializationConfig = (DeserializationConfig)ObjectMapper.this._deserializationConfig.withInsertedAnnotationIntrospector(ai);
/*  876 */             ObjectMapper.this._serializationConfig = (SerializationConfig)ObjectMapper.this._serializationConfig.withInsertedAnnotationIntrospector(ai);
/*      */           }
/*      */ 
/*      */           
/*      */           public void appendAnnotationIntrospector(AnnotationIntrospector ai) {
/*  881 */             ObjectMapper.this._deserializationConfig = (DeserializationConfig)ObjectMapper.this._deserializationConfig.withAppendedAnnotationIntrospector(ai);
/*  882 */             ObjectMapper.this._serializationConfig = (SerializationConfig)ObjectMapper.this._serializationConfig.withAppendedAnnotationIntrospector(ai);
/*      */           }
/*      */ 
/*      */           
/*      */           public void registerSubtypes(Class<?>... subtypes) {
/*  887 */             ObjectMapper.this.registerSubtypes(subtypes);
/*      */           }
/*      */ 
/*      */           
/*      */           public void registerSubtypes(NamedType... subtypes) {
/*  892 */             ObjectMapper.this.registerSubtypes(subtypes);
/*      */           }
/*      */ 
/*      */           
/*      */           public void registerSubtypes(Collection<Class<?>> subtypes) {
/*  897 */             ObjectMapper.this.registerSubtypes(subtypes);
/*      */           }
/*      */ 
/*      */           
/*      */           public void setMixInAnnotations(Class<?> target, Class<?> mixinSource) {
/*  902 */             ObjectMapper.this.addMixIn(target, mixinSource);
/*      */           }
/*      */ 
/*      */           
/*      */           public void addDeserializationProblemHandler(DeserializationProblemHandler handler) {
/*  907 */             ObjectMapper.this.addHandler(handler);
/*      */           }
/*      */ 
/*      */           
/*      */           public void setNamingStrategy(PropertyNamingStrategy naming) {
/*  912 */             ObjectMapper.this.setPropertyNamingStrategy(naming);
/*      */           }
/*      */         });
/*  915 */     return this;
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
/*      */   public ObjectMapper registerModules(Module... modules) {
/*  931 */     for (Module module : modules) {
/*  932 */       registerModule(module);
/*      */     }
/*  934 */     return this;
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
/*      */   public ObjectMapper registerModules(Iterable<Module> modules) {
/*  950 */     for (Module module : modules) {
/*  951 */       registerModule(module);
/*      */     }
/*  953 */     return this;
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
/*      */   public static List<Module> findModules() {
/*  966 */     return findModules(null);
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
/*      */   public static List<Module> findModules(ClassLoader classLoader) {
/*  980 */     ArrayList<Module> modules = new ArrayList<>();
/*  981 */     ServiceLoader<Module> loader = secureGetServiceLoader(Module.class, classLoader);
/*  982 */     for (Module module : loader) {
/*  983 */       modules.add(module);
/*      */     }
/*  985 */     return modules;
/*      */   }
/*      */   
/*      */   private static <T> ServiceLoader<T> secureGetServiceLoader(final Class<T> clazz, final ClassLoader classLoader) {
/*  989 */     SecurityManager sm = System.getSecurityManager();
/*  990 */     if (sm == null) {
/*  991 */       return (classLoader == null) ? ServiceLoader.<T>load(clazz) : ServiceLoader.<T>load(clazz, classLoader);
/*      */     }
/*      */     
/*  994 */     return AccessController.<ServiceLoader<T>>doPrivileged((PrivilegedAction)new PrivilegedAction<ServiceLoader<ServiceLoader<T>>>()
/*      */         {
/*      */           public ServiceLoader<T> run() {
/*  997 */             return (classLoader == null) ? ServiceLoader.<T>load(clazz) : ServiceLoader.<T>load(clazz, classLoader);
/*      */           }
/*      */         });
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
/*      */   public ObjectMapper findAndRegisterModules() {
/* 1016 */     return registerModules(findModules());
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
/*      */   public SerializationConfig getSerializationConfig() {
/* 1034 */     return this._serializationConfig;
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
/*      */   public DeserializationConfig getDeserializationConfig() {
/* 1047 */     return this._deserializationConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeserializationContext getDeserializationContext() {
/* 1058 */     return (DeserializationContext)this._deserializationContext;
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
/*      */   public ObjectMapper setSerializerFactory(SerializerFactory f) {
/* 1072 */     this._serializerFactory = f;
/* 1073 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializerFactory getSerializerFactory() {
/* 1084 */     return this._serializerFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setSerializerProvider(DefaultSerializerProvider p) {
/* 1093 */     this._serializerProvider = p;
/* 1094 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializerProvider getSerializerProvider() {
/* 1105 */     return (SerializerProvider)this._serializerProvider;
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
/*      */   public SerializerProvider getSerializerProviderInstance() {
/* 1117 */     return (SerializerProvider)_serializerProvider(this._serializationConfig);
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
/*      */   public ObjectMapper setMixIns(Map<Class<?>, Class<?>> sourceMixins) {
/* 1146 */     this._mixIns.setLocalDefinitions(sourceMixins);
/* 1147 */     return this;
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
/*      */   public ObjectMapper addMixIn(Class<?> target, Class<?> mixinSource) {
/* 1164 */     this._mixIns.addLocalDefinition(target, mixinSource);
/* 1165 */     return this;
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
/*      */   public ObjectMapper setMixInResolver(ClassIntrospector.MixInResolver resolver) {
/* 1178 */     SimpleMixInResolver r = this._mixIns.withOverrides(resolver);
/* 1179 */     if (r != this._mixIns) {
/* 1180 */       this._mixIns = r;
/* 1181 */       this._deserializationConfig = new DeserializationConfig(this._deserializationConfig, r);
/* 1182 */       this._serializationConfig = new SerializationConfig(this._serializationConfig, r);
/*      */     } 
/* 1184 */     return this;
/*      */   }
/*      */   
/*      */   public Class<?> findMixInClassFor(Class<?> cls) {
/* 1188 */     return this._mixIns.findMixInClassFor(cls);
/*      */   }
/*      */ 
/*      */   
/*      */   public int mixInCount() {
/* 1193 */     return this._mixIns.localSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setMixInAnnotations(Map<Class<?>, Class<?>> sourceMixins) {
/* 1201 */     setMixIns(sourceMixins);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void addMixInAnnotations(Class<?> target, Class<?> mixinSource) {
/* 1209 */     addMixIn(target, mixinSource);
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
/*      */   public VisibilityChecker<?> getVisibilityChecker() {
/* 1224 */     return this._serializationConfig.getDefaultVisibilityChecker();
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
/*      */   public ObjectMapper setVisibility(VisibilityChecker<?> vc) {
/* 1238 */     this._configOverrides.setDefaultVisibility(vc);
/* 1239 */     return this;
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
/*      */   public ObjectMapper setVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility) {
/* 1268 */     VisibilityChecker<?> vc = this._configOverrides.getDefaultVisibility();
/* 1269 */     vc = vc.withVisibility(forMethod, visibility);
/* 1270 */     this._configOverrides.setDefaultVisibility(vc);
/* 1271 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubtypeResolver getSubtypeResolver() {
/* 1278 */     return this._subtypeResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setSubtypeResolver(SubtypeResolver str) {
/* 1285 */     this._subtypeResolver = str;
/* 1286 */     this._deserializationConfig = this._deserializationConfig.with(str);
/* 1287 */     this._serializationConfig = this._serializationConfig.with(str);
/* 1288 */     return this;
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
/*      */   public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector ai) {
/* 1302 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(ai);
/* 1303 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(ai);
/* 1304 */     return this;
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
/*      */   public ObjectMapper setAnnotationIntrospectors(AnnotationIntrospector serializerAI, AnnotationIntrospector deserializerAI) {
/* 1324 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(serializerAI);
/* 1325 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(deserializerAI);
/* 1326 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy s) {
/* 1333 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(s);
/* 1334 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(s);
/* 1335 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyNamingStrategy getPropertyNamingStrategy() {
/* 1343 */     return this._serializationConfig.getPropertyNamingStrategy();
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
/*      */   public ObjectMapper setDefaultPrettyPrinter(PrettyPrinter pp) {
/* 1357 */     this._serializationConfig = this._serializationConfig.withDefaultPrettyPrinter(pp);
/* 1358 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setVisibilityChecker(VisibilityChecker<?> vc) {
/* 1366 */     setVisibility(vc);
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
/*      */   public ObjectMapper setSerializationInclusion(JsonInclude.Include incl) {
/* 1385 */     setPropertyInclusion(JsonInclude.Value.construct(incl, incl));
/* 1386 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectMapper setPropertyInclusion(JsonInclude.Value incl) {
/* 1395 */     return setDefaultPropertyInclusion(incl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setDefaultPropertyInclusion(JsonInclude.Value incl) {
/* 1406 */     this._configOverrides.setDefaultInclusion(incl);
/* 1407 */     return this;
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
/*      */   public ObjectMapper setDefaultPropertyInclusion(JsonInclude.Include incl) {
/* 1419 */     this._configOverrides.setDefaultInclusion(JsonInclude.Value.construct(incl, incl));
/* 1420 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setDefaultSetterInfo(JsonSetter.Value v) {
/* 1431 */     this._configOverrides.setDefaultSetterInfo(v);
/* 1432 */     return this;
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
/*      */   public ObjectMapper setDefaultVisibility(JsonAutoDetect.Value vis) {
/* 1444 */     this._configOverrides.setDefaultVisibility((VisibilityChecker)VisibilityChecker.Std.construct(vis));
/* 1445 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setDefaultMergeable(Boolean b) {
/* 1456 */     this._configOverrides.setDefaultMergeable(b);
/* 1457 */     return this;
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
/*      */   public ObjectMapper enableDefaultTyping() {
/* 1479 */     return enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE);
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
/*      */   public ObjectMapper enableDefaultTyping(DefaultTyping dti) {
/* 1495 */     return enableDefaultTyping(dti, JsonTypeInfo.As.WRAPPER_ARRAY);
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
/*      */   public ObjectMapper enableDefaultTyping(DefaultTyping applicability, JsonTypeInfo.As includeAs) {
/* 1521 */     if (includeAs == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
/* 1522 */       throw new IllegalArgumentException("Cannot use includeAs of " + includeAs);
/*      */     }
/*      */     
/* 1525 */     DefaultTypeResolverBuilder defaultTypeResolverBuilder = new DefaultTypeResolverBuilder(applicability);
/*      */     
/* 1527 */     TypeResolverBuilder<?> typeResolverBuilder = defaultTypeResolverBuilder.init(JsonTypeInfo.Id.CLASS, null);
/* 1528 */     typeResolverBuilder = typeResolverBuilder.inclusion(includeAs);
/* 1529 */     return setDefaultTyping(typeResolverBuilder);
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
/*      */   public ObjectMapper enableDefaultTypingAsProperty(DefaultTyping applicability, String propertyName) {
/* 1548 */     DefaultTypeResolverBuilder defaultTypeResolverBuilder = new DefaultTypeResolverBuilder(applicability);
/*      */     
/* 1550 */     TypeResolverBuilder<?> typeResolverBuilder = defaultTypeResolverBuilder.init(JsonTypeInfo.Id.CLASS, null);
/* 1551 */     typeResolverBuilder = typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
/* 1552 */     typeResolverBuilder = typeResolverBuilder.typeProperty(propertyName);
/* 1553 */     return setDefaultTyping(typeResolverBuilder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper disableDefaultTyping() {
/* 1563 */     return setDefaultTyping(null);
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
/*      */   public ObjectMapper setDefaultTyping(TypeResolverBuilder<?> typer) {
/* 1579 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(typer);
/* 1580 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(typer);
/* 1581 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerSubtypes(Class<?>... classes) {
/* 1592 */     getSubtypeResolver().registerSubtypes(classes);
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
/*      */   public void registerSubtypes(NamedType... types) {
/* 1604 */     getSubtypeResolver().registerSubtypes(types);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerSubtypes(Collection<Class<?>> subtypes) {
/* 1611 */     getSubtypeResolver().registerSubtypes(subtypes);
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
/*      */   public MutableConfigOverride configOverride(Class<?> type) {
/* 1638 */     return this._configOverrides.findOrCreateOverride(type);
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
/*      */   public TypeFactory getTypeFactory() {
/* 1651 */     return this._typeFactory;
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
/*      */   public ObjectMapper setTypeFactory(TypeFactory f) {
/* 1663 */     this._typeFactory = f;
/* 1664 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(f);
/* 1665 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(f);
/* 1666 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructType(Type t) {
/* 1675 */     return this._typeFactory.constructType(t);
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
/*      */   public JsonNodeFactory getNodeFactory() {
/* 1695 */     return this._deserializationConfig.getNodeFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setNodeFactory(JsonNodeFactory f) {
/* 1704 */     this._deserializationConfig = this._deserializationConfig.with(f);
/* 1705 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper addHandler(DeserializationProblemHandler h) {
/* 1713 */     this._deserializationConfig = this._deserializationConfig.withHandler(h);
/* 1714 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper clearProblemHandlers() {
/* 1722 */     this._deserializationConfig = this._deserializationConfig.withNoProblemHandlers();
/* 1723 */     return this;
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
/*      */   public ObjectMapper setConfig(DeserializationConfig config) {
/* 1741 */     this._deserializationConfig = config;
/* 1742 */     return this;
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
/*      */   @Deprecated
/*      */   public void setFilters(FilterProvider filterProvider) {
/* 1756 */     this._serializationConfig = this._serializationConfig.withFilters(filterProvider);
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
/*      */   public ObjectMapper setFilterProvider(FilterProvider filterProvider) {
/* 1771 */     this._serializationConfig = this._serializationConfig.withFilters(filterProvider);
/* 1772 */     return this;
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
/*      */   public ObjectMapper setBase64Variant(Base64Variant v) {
/* 1786 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(v);
/* 1787 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(v);
/* 1788 */     return this;
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
/*      */   public ObjectMapper setConfig(SerializationConfig config) {
/* 1806 */     this._serializationConfig = config;
/* 1807 */     return this;
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
/*      */   public JsonFactory getFactory() {
/* 1834 */     return this._jsonFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonFactory getJsonFactory() {
/* 1841 */     return getFactory();
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
/*      */   public ObjectMapper setDateFormat(DateFormat dateFormat) {
/* 1855 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(dateFormat);
/* 1856 */     this._serializationConfig = this._serializationConfig.with(dateFormat);
/* 1857 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateFormat getDateFormat() {
/* 1865 */     return this._serializationConfig.getDateFormat();
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
/*      */   public Object setHandlerInstantiator(HandlerInstantiator hi) {
/* 1877 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(hi);
/* 1878 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(hi);
/* 1879 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setInjectableValues(InjectableValues injectableValues) {
/* 1887 */     this._injectableValues = injectableValues;
/* 1888 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InjectableValues getInjectableValues() {
/* 1895 */     return this._injectableValues;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setLocale(Locale l) {
/* 1903 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(l);
/* 1904 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(l);
/* 1905 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper setTimeZone(TimeZone tz) {
/* 1913 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(tz);
/* 1914 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(tz);
/* 1915 */     return this;
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
/*      */   public boolean isEnabled(MapperFeature f) {
/* 1929 */     return this._serializationConfig.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper configure(MapperFeature f, boolean state) {
/* 1937 */     this._serializationConfig = state ? (SerializationConfig)this._serializationConfig.with(new MapperFeature[] { f }) : (SerializationConfig)this._serializationConfig.without(new MapperFeature[] { f });
/*      */     
/* 1939 */     this._deserializationConfig = state ? (DeserializationConfig)this._deserializationConfig.with(new MapperFeature[] { f }) : (DeserializationConfig)this._deserializationConfig.without(new MapperFeature[] { f });
/*      */     
/* 1941 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper enable(MapperFeature... f) {
/* 1949 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with(f);
/* 1950 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.with(f);
/* 1951 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper disable(MapperFeature... f) {
/* 1959 */     this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.without(f);
/* 1960 */     this._serializationConfig = (SerializationConfig)this._serializationConfig.without(f);
/* 1961 */     return this;
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
/*      */   public boolean isEnabled(SerializationFeature f) {
/* 1975 */     return this._serializationConfig.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper configure(SerializationFeature f, boolean state) {
/* 1983 */     this._serializationConfig = state ? this._serializationConfig.with(f) : this._serializationConfig.without(f);
/*      */     
/* 1985 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper enable(SerializationFeature f) {
/* 1993 */     this._serializationConfig = this._serializationConfig.with(f);
/* 1994 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper enable(SerializationFeature first, SerializationFeature... f) {
/* 2003 */     this._serializationConfig = this._serializationConfig.with(first, f);
/* 2004 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper disable(SerializationFeature f) {
/* 2012 */     this._serializationConfig = this._serializationConfig.without(f);
/* 2013 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper disable(SerializationFeature first, SerializationFeature... f) {
/* 2022 */     this._serializationConfig = this._serializationConfig.without(first, f);
/* 2023 */     return this;
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
/*      */   public boolean isEnabled(DeserializationFeature f) {
/* 2037 */     return this._deserializationConfig.isEnabled(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper configure(DeserializationFeature f, boolean state) {
/* 2045 */     this._deserializationConfig = state ? this._deserializationConfig.with(f) : this._deserializationConfig.without(f);
/*      */     
/* 2047 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper enable(DeserializationFeature feature) {
/* 2055 */     this._deserializationConfig = this._deserializationConfig.with(feature);
/* 2056 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper enable(DeserializationFeature first, DeserializationFeature... f) {
/* 2065 */     this._deserializationConfig = this._deserializationConfig.with(first, f);
/* 2066 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper disable(DeserializationFeature feature) {
/* 2074 */     this._deserializationConfig = this._deserializationConfig.without(feature);
/* 2075 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectMapper disable(DeserializationFeature first, DeserializationFeature... f) {
/* 2084 */     this._deserializationConfig = this._deserializationConfig.without(first, f);
/* 2085 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled(JsonParser.Feature f) {
/* 2095 */     return this._deserializationConfig.isEnabled(f, this._jsonFactory);
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
/*      */   public ObjectMapper configure(JsonParser.Feature f, boolean state) {
/* 2110 */     this._jsonFactory.configure(f, state);
/* 2111 */     return this;
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
/*      */   public ObjectMapper enable(JsonParser.Feature... features) {
/* 2127 */     for (JsonParser.Feature f : features) {
/* 2128 */       this._jsonFactory.enable(f);
/*      */     }
/* 2130 */     return this;
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
/*      */   public ObjectMapper disable(JsonParser.Feature... features) {
/* 2146 */     for (JsonParser.Feature f : features) {
/* 2147 */       this._jsonFactory.disable(f);
/*      */     }
/* 2149 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled(JsonGenerator.Feature f) {
/* 2159 */     return this._serializationConfig.isEnabled(f, this._jsonFactory);
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
/*      */   public ObjectMapper configure(JsonGenerator.Feature f, boolean state) {
/* 2174 */     this._jsonFactory.configure(f, state);
/* 2175 */     return this;
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
/*      */   public ObjectMapper enable(JsonGenerator.Feature... features) {
/* 2191 */     for (JsonGenerator.Feature f : features) {
/* 2192 */       this._jsonFactory.enable(f);
/*      */     }
/* 2194 */     return this;
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
/*      */   public ObjectMapper disable(JsonGenerator.Feature... features) {
/* 2210 */     for (JsonGenerator.Feature f : features) {
/* 2211 */       this._jsonFactory.disable(f);
/*      */     }
/* 2213 */     return this;
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
/*      */   public boolean isEnabled(JsonFactory.Feature f) {
/* 2229 */     return this._jsonFactory.isEnabled(f);
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
/*      */   public <T> T readValue(JsonParser p, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2264 */     return (T)_readValue(getDeserializationConfig(), p, this._typeFactory.constructType(valueType));
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
/*      */   public <T> T readValue(JsonParser p, TypeReference<?> valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 2288 */     return (T)_readValue(getDeserializationConfig(), p, this._typeFactory.constructType(valueTypeRef));
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
/*      */   public final <T> T readValue(JsonParser p, ResolvedType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2311 */     return (T)_readValue(getDeserializationConfig(), p, (JavaType)valueType);
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
/*      */   public <T> T readValue(JsonParser p, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2330 */     return (T)_readValue(getDeserializationConfig(), p, valueType);
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
/*      */   public <T extends TreeNode> T readTree(JsonParser p) throws IOException, JsonProcessingException {
/*      */     NullNode nullNode;
/* 2364 */     DeserializationConfig cfg = getDeserializationConfig();
/* 2365 */     JsonToken t = p.getCurrentToken();
/* 2366 */     if (t == null) {
/* 2367 */       t = p.nextToken();
/* 2368 */       if (t == null) {
/* 2369 */         return null;
/*      */       }
/*      */     } 
/* 2372 */     JsonNode n = (JsonNode)_readValue(cfg, p, JSON_NODE_TYPE);
/* 2373 */     if (n == null) {
/* 2374 */       nullNode = getNodeFactory().nullNode();
/*      */     }
/*      */     
/* 2377 */     return (T)nullNode;
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
/*      */   public <T> MappingIterator<T> readValues(JsonParser p, ResolvedType valueType) throws IOException, JsonProcessingException {
/* 2403 */     return readValues(p, (JavaType)valueType);
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
/*      */   public <T> MappingIterator<T> readValues(JsonParser p, JavaType valueType) throws IOException, JsonProcessingException {
/* 2417 */     DeserializationConfig config = getDeserializationConfig();
/* 2418 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p, config);
/* 2419 */     JsonDeserializer<?> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, valueType);
/*      */     
/* 2421 */     return new MappingIterator<>(valueType, p, (DeserializationContext)defaultDeserializationContext, deser, false, null);
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
/*      */   public <T> MappingIterator<T> readValues(JsonParser p, Class<T> valueType) throws IOException, JsonProcessingException {
/* 2437 */     return readValues(p, this._typeFactory.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> MappingIterator<T> readValues(JsonParser p, TypeReference<?> valueTypeRef) throws IOException, JsonProcessingException {
/* 2447 */     return readValues(p, this._typeFactory.constructType(valueTypeRef));
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
/*      */   public JsonNode readTree(InputStream in) throws IOException {
/* 2485 */     return _readTreeAndClose(this._jsonFactory.createParser(in));
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
/*      */   public JsonNode readTree(Reader r) throws IOException {
/* 2512 */     return _readTreeAndClose(this._jsonFactory.createParser(r));
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
/*      */   public JsonNode readTree(String content) throws IOException {
/* 2539 */     return _readTreeAndClose(this._jsonFactory.createParser(content));
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
/*      */   public JsonNode readTree(byte[] content) throws IOException {
/* 2559 */     return _readTreeAndClose(this._jsonFactory.createParser(content));
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
/*      */   public JsonNode readTree(File file) throws IOException, JsonProcessingException {
/* 2585 */     return _readTreeAndClose(this._jsonFactory.createParser(file));
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
/*      */   public JsonNode readTree(URL source) throws IOException {
/* 2609 */     return _readTreeAndClose(this._jsonFactory.createParser(source));
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
/*      */   public void writeValue(JsonGenerator g, Object value) throws IOException, JsonGenerationException, JsonMappingException {
/* 2627 */     SerializationConfig config = getSerializationConfig();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2635 */     if (config.isEnabled(SerializationFeature.INDENT_OUTPUT) && 
/* 2636 */       g.getPrettyPrinter() == null) {
/* 2637 */       g.setPrettyPrinter(config.constructDefaultPrettyPrinter());
/*      */     }
/*      */     
/* 2640 */     if (config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && value instanceof Closeable) {
/* 2641 */       _writeCloseableValue(g, value, config);
/*      */     } else {
/* 2643 */       _serializerProvider(config).serializeValue(g, value);
/* 2644 */       if (config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 2645 */         g.flush();
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
/*      */   public void writeTree(JsonGenerator jgen, TreeNode rootNode) throws IOException, JsonProcessingException {
/* 2660 */     SerializationConfig config = getSerializationConfig();
/* 2661 */     _serializerProvider(config).serializeValue(jgen, rootNode);
/* 2662 */     if (config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 2663 */       jgen.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTree(JsonGenerator jgen, JsonNode rootNode) throws IOException, JsonProcessingException {
/* 2674 */     SerializationConfig config = getSerializationConfig();
/* 2675 */     _serializerProvider(config).serializeValue(jgen, rootNode);
/* 2676 */     if (config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 2677 */       jgen.flush();
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
/*      */   public ObjectNode createObjectNode() {
/* 2690 */     return this._deserializationConfig.getNodeFactory().objectNode();
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
/*      */   public ArrayNode createArrayNode() {
/* 2702 */     return this._deserializationConfig.getNodeFactory().arrayNode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonParser treeAsTokens(TreeNode n) {
/* 2713 */     return (JsonParser)new TreeTraversingParser((JsonNode)n, this);
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
/*      */   public <T> T treeToValue(TreeNode n, Class<T> valueType) throws JsonProcessingException {
/*      */     try {
/* 2733 */       if (valueType != Object.class && valueType.isAssignableFrom(n.getClass())) {
/* 2734 */         return (T)n;
/*      */       }
/*      */ 
/*      */       
/* 2738 */       if (n.asToken() == JsonToken.VALUE_EMBEDDED_OBJECT && 
/* 2739 */         n instanceof POJONode) {
/* 2740 */         Object ob = ((POJONode)n).getPojo();
/* 2741 */         if (ob == null || valueType.isInstance(ob)) {
/* 2742 */           return (T)ob;
/*      */         }
/*      */       } 
/*      */       
/* 2746 */       return readValue(treeAsTokens(n), valueType);
/* 2747 */     } catch (JsonProcessingException e) {
/* 2748 */       throw e;
/* 2749 */     } catch (IOException e) {
/* 2750 */       throw new IllegalArgumentException(e.getMessage(), e);
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
/*      */   public <T extends JsonNode> T valueToTree(Object fromValue) throws IllegalArgumentException {
/*      */     JsonNode result;
/* 2778 */     if (fromValue == null) return null; 
/* 2779 */     TokenBuffer buf = new TokenBuffer(this, false);
/* 2780 */     if (isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 2781 */       buf = buf.forceUseOfBigDecimal(true);
/*      */     }
/*      */     
/*      */     try {
/* 2785 */       writeValue((JsonGenerator)buf, fromValue);
/* 2786 */       JsonParser p = buf.asParser();
/* 2787 */       result = readTree(p);
/* 2788 */       p.close();
/* 2789 */     } catch (IOException e) {
/* 2790 */       throw new IllegalArgumentException(e.getMessage(), e);
/*      */     } 
/* 2792 */     return (T)result;
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
/*      */   public boolean canSerialize(Class<?> type) {
/* 2817 */     return _serializerProvider(getSerializationConfig()).hasSerializerFor(type, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSerialize(Class<?> type, AtomicReference<Throwable> cause) {
/* 2828 */     return _serializerProvider(getSerializationConfig()).hasSerializerFor(type, cause);
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
/*      */   public boolean canDeserialize(JavaType type) {
/* 2850 */     return createDeserializationContext(null, getDeserializationConfig()).hasValueDeserializerFor(type, null);
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
/*      */   public boolean canDeserialize(JavaType type, AtomicReference<Throwable> cause) {
/* 2863 */     return createDeserializationContext(null, getDeserializationConfig()).hasValueDeserializerFor(type, cause);
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
/*      */   public <T> T readValue(File src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2890 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueType));
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
/*      */   public <T> T readValue(File src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 2909 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueTypeRef));
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
/*      */   public <T> T readValue(File src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2928 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), valueType);
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
/*      */   public <T> T readValue(URL src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2947 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueType));
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
/*      */   public <T> T readValue(URL src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 2966 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueTypeRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(URL src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2973 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), valueType);
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
/*      */   public <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 2992 */     return (T)_readMapAndClose(this._jsonFactory.createParser(content), this._typeFactory.constructType(valueType));
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
/*      */   public <T> T readValue(String content, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 3011 */     return (T)_readMapAndClose(this._jsonFactory.createParser(content), this._typeFactory.constructType(valueTypeRef));
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
/*      */   public <T> T readValue(String content, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3030 */     return (T)_readMapAndClose(this._jsonFactory.createParser(content), valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(Reader src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3037 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(Reader src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 3044 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueTypeRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(Reader src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3051 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3058 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(InputStream src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 3065 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueTypeRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(InputStream src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3072 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(byte[] src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3079 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(byte[] src, int offset, int len, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3087 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src, offset, len), this._typeFactory.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(byte[] src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 3094 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueTypeRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(byte[] src, int offset, int len, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
/* 3102 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src, offset, len), this._typeFactory.constructType(valueTypeRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(byte[] src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3109 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(byte[] src, int offset, int len, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
/* 3117 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src, offset, len), valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(DataInput src, Class<T> valueType) throws IOException {
/* 3123 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), this._typeFactory.constructType(valueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readValue(DataInput src, JavaType valueType) throws IOException {
/* 3130 */     return (T)_readMapAndClose(this._jsonFactory.createParser(src), valueType);
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
/*      */   public void writeValue(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
/* 3147 */     _configAndWriteValue(this._jsonFactory.createGenerator(resultFile, JsonEncoding.UTF8), value);
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
/* 3164 */     _configAndWriteValue(this._jsonFactory.createGenerator(out, JsonEncoding.UTF8), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeValue(DataOutput out, Object value) throws IOException {
/* 3173 */     _configAndWriteValue(this._jsonFactory.createGenerator(out, JsonEncoding.UTF8), value);
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
/* 3189 */     _configAndWriteValue(this._jsonFactory.createGenerator(w), value);
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
/* 3205 */     SegmentedStringWriter sw = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
/*      */     try {
/* 3207 */       _configAndWriteValue(this._jsonFactory.createGenerator((Writer)sw), value);
/* 3208 */     } catch (JsonProcessingException e) {
/* 3209 */       throw e;
/* 3210 */     } catch (IOException e) {
/* 3211 */       throw JsonMappingException.fromUnexpectedIOE(e);
/*      */     } 
/* 3213 */     return sw.getAndClear();
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
/* 3229 */     ByteArrayBuilder bb = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
/*      */     try {
/* 3231 */       _configAndWriteValue(this._jsonFactory.createGenerator((OutputStream)bb, JsonEncoding.UTF8), value);
/* 3232 */     } catch (JsonProcessingException e) {
/* 3233 */       throw e;
/* 3234 */     } catch (IOException e) {
/* 3235 */       throw JsonMappingException.fromUnexpectedIOE(e);
/*      */     } 
/* 3237 */     byte[] result = bb.toByteArray();
/* 3238 */     bb.release();
/* 3239 */     return result;
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
/*      */   public ObjectWriter writer() {
/* 3254 */     return _newWriter(getSerializationConfig());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(SerializationFeature feature) {
/* 3263 */     return _newWriter(getSerializationConfig().with(feature));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(SerializationFeature first, SerializationFeature... other) {
/* 3273 */     return _newWriter(getSerializationConfig().with(first, other));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(DateFormat df) {
/* 3282 */     return _newWriter(getSerializationConfig().with(df));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writerWithView(Class<?> serializationView) {
/* 3290 */     return _newWriter(getSerializationConfig().withView(serializationView));
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
/*      */   public ObjectWriter writerFor(Class<?> rootType) {
/* 3305 */     return _newWriter(getSerializationConfig(), (rootType == null) ? null : this._typeFactory.constructType(rootType), null);
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
/*      */   public ObjectWriter writerFor(TypeReference<?> rootType) {
/* 3322 */     return _newWriter(getSerializationConfig(), (rootType == null) ? null : this._typeFactory.constructType(rootType), null);
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
/*      */   public ObjectWriter writerFor(JavaType rootType) {
/* 3339 */     return _newWriter(getSerializationConfig(), rootType, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(PrettyPrinter pp) {
/* 3348 */     if (pp == null) {
/* 3349 */       pp = ObjectWriter.NULL_PRETTY_PRINTER;
/*      */     }
/* 3351 */     return _newWriter(getSerializationConfig(), null, pp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writerWithDefaultPrettyPrinter() {
/* 3359 */     SerializationConfig config = getSerializationConfig();
/* 3360 */     return _newWriter(config, null, config.getDefaultPrettyPrinter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(FilterProvider filterProvider) {
/* 3369 */     return _newWriter(getSerializationConfig().withFilters(filterProvider));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(FormatSchema schema) {
/* 3380 */     _verifySchemaType(schema);
/* 3381 */     return _newWriter(getSerializationConfig(), schema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(Base64Variant defaultBase64) {
/* 3391 */     return _newWriter((SerializationConfig)getSerializationConfig().with(defaultBase64));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(CharacterEscapes escapes) {
/* 3401 */     return _newWriter(getSerializationConfig()).with(escapes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectWriter writer(ContextAttributes attrs) {
/* 3411 */     return _newWriter(getSerializationConfig().with(attrs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter writerWithType(Class<?> rootType) {
/* 3419 */     return _newWriter(getSerializationConfig(), (rootType == null) ? null : this._typeFactory.constructType(rootType), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter writerWithType(TypeReference<?> rootType) {
/* 3430 */     return _newWriter(getSerializationConfig(), (rootType == null) ? null : this._typeFactory.constructType(rootType), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectWriter writerWithType(JavaType rootType) {
/* 3441 */     return _newWriter(getSerializationConfig(), rootType, null);
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
/*      */   public ObjectReader reader() {
/* 3457 */     return _newReader(getDeserializationConfig()).with(this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader reader(DeserializationFeature feature) {
/* 3468 */     return _newReader(getDeserializationConfig().with(feature));
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
/*      */   public ObjectReader reader(DeserializationFeature first, DeserializationFeature... other) {
/* 3480 */     return _newReader(getDeserializationConfig().with(first, other));
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
/*      */   public ObjectReader readerForUpdating(Object valueToUpdate) {
/* 3494 */     JavaType t = this._typeFactory.constructType(valueToUpdate.getClass());
/* 3495 */     return _newReader(getDeserializationConfig(), t, valueToUpdate, null, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader readerFor(JavaType type) {
/* 3506 */     return _newReader(getDeserializationConfig(), type, null, null, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader readerFor(Class<?> type) {
/* 3517 */     return _newReader(getDeserializationConfig(), this._typeFactory.constructType(type), null, null, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader readerFor(TypeReference<?> type) {
/* 3528 */     return _newReader(getDeserializationConfig(), this._typeFactory.constructType(type), null, null, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader reader(JsonNodeFactory f) {
/* 3537 */     return _newReader(getDeserializationConfig()).with(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader reader(FormatSchema schema) {
/* 3548 */     _verifySchemaType(schema);
/* 3549 */     return _newReader(getDeserializationConfig(), null, null, schema, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader reader(InjectableValues injectableValues) {
/* 3560 */     return _newReader(getDeserializationConfig(), null, null, null, injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader readerWithView(Class<?> view) {
/* 3569 */     return _newReader(getDeserializationConfig().withView(view));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader reader(Base64Variant defaultBase64) {
/* 3579 */     return _newReader((DeserializationConfig)getDeserializationConfig().with(defaultBase64));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectReader reader(ContextAttributes attrs) {
/* 3589 */     return _newReader(getDeserializationConfig().with(attrs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader reader(JavaType type) {
/* 3597 */     return _newReader(getDeserializationConfig(), type, null, null, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader reader(Class<?> type) {
/* 3606 */     return _newReader(getDeserializationConfig(), this._typeFactory.constructType(type), null, null, this._injectableValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ObjectReader reader(TypeReference<?> type) {
/* 3615 */     return _newReader(getDeserializationConfig(), this._typeFactory.constructType(type), null, null, this._injectableValues);
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
/*      */   public <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
/* 3656 */     return (T)_convert(fromValue, this._typeFactory.constructType(toValueType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T convertValue(Object fromValue, TypeReference<?> toValueTypeRef) throws IllegalArgumentException {
/* 3666 */     return (T)_convert(fromValue, this._typeFactory.constructType(toValueTypeRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T convertValue(Object fromValue, JavaType toValueType) throws IllegalArgumentException {
/* 3676 */     return (T)_convert(fromValue, toValueType);
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
/*      */   protected Object _convert(Object fromValue, JavaType toValueType) throws IllegalArgumentException {
/* 3693 */     if (fromValue != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3698 */       Class<?> targetType = toValueType.getRawClass();
/* 3699 */       if (targetType != Object.class && !toValueType.hasGenericTypes() && targetType.isAssignableFrom(fromValue.getClass()))
/*      */       {
/*      */         
/* 3702 */         return fromValue;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 3707 */     TokenBuffer buf = new TokenBuffer(this, false);
/* 3708 */     if (isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 3709 */       buf = buf.forceUseOfBigDecimal(true);
/*      */     }
/*      */     
/*      */     try {
/*      */       Object result;
/* 3714 */       SerializationConfig config = getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);
/*      */       
/* 3716 */       _serializerProvider(config).serializeValue((JsonGenerator)buf, fromValue);
/*      */ 
/*      */       
/* 3719 */       JsonParser p = buf.asParser();
/*      */ 
/*      */       
/* 3722 */       DeserializationConfig deserConfig = getDeserializationConfig();
/* 3723 */       JsonToken t = _initForReading(p, toValueType);
/* 3724 */       if (t == JsonToken.VALUE_NULL) {
/* 3725 */         DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p, deserConfig);
/* 3726 */         result = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, toValueType).getNullValue((DeserializationContext)defaultDeserializationContext);
/* 3727 */       } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
/* 3728 */         result = null;
/*      */       } else {
/* 3730 */         DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p, deserConfig);
/* 3731 */         JsonDeserializer<Object> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, toValueType);
/*      */         
/* 3733 */         result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/*      */       } 
/* 3735 */       p.close();
/* 3736 */       return result;
/* 3737 */     } catch (IOException e) {
/* 3738 */       throw new IllegalArgumentException(e.getMessage(), e);
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
/*      */   public <T> T updateValue(T valueToUpdate, Object overrides) throws JsonMappingException {
/* 3781 */     T result = valueToUpdate;
/* 3782 */     if (valueToUpdate != null && overrides != null) {
/* 3783 */       TokenBuffer buf = new TokenBuffer(this, false);
/* 3784 */       if (isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 3785 */         buf = buf.forceUseOfBigDecimal(true);
/*      */       }
/*      */       try {
/* 3788 */         SerializationConfig config = getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);
/*      */         
/* 3790 */         _serializerProvider(config).serializeValue((JsonGenerator)buf, overrides);
/* 3791 */         JsonParser p = buf.asParser();
/* 3792 */         result = readerForUpdating(valueToUpdate).readValue(p);
/* 3793 */         p.close();
/* 3794 */       } catch (IOException e) {
/* 3795 */         if (e instanceof JsonMappingException) {
/* 3796 */           throw (JsonMappingException)e;
/*      */         }
/*      */         
/* 3799 */         throw JsonMappingException.fromUnexpectedIOE(e);
/*      */       } 
/*      */     } 
/* 3802 */     return result;
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
/*      */   @Deprecated
/*      */   public JsonSchema generateJsonSchema(Class<?> t) throws JsonMappingException {
/* 3824 */     return _serializerProvider(getSerializationConfig()).generateJsonSchema(t);
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
/*      */   public void acceptJsonFormatVisitor(Class<?> type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
/* 3841 */     acceptJsonFormatVisitor(this._typeFactory.constructType(type), visitor);
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
/*      */   public void acceptJsonFormatVisitor(JavaType type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
/* 3859 */     if (type == null) {
/* 3860 */       throw new IllegalArgumentException("type must be provided");
/*      */     }
/* 3862 */     _serializerProvider(getSerializationConfig()).acceptJsonFormatVisitor(type, visitor);
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
/*      */   protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
/* 3876 */     return this._serializerProvider.createInstance(config, this._serializerFactory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _configAndWriteValue(JsonGenerator g, Object value) throws IOException {
/* 3886 */     SerializationConfig cfg = getSerializationConfig();
/* 3887 */     cfg.initialize(g);
/* 3888 */     if (cfg.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && value instanceof Closeable) {
/* 3889 */       _configAndWriteCloseable(g, value, cfg);
/*      */       return;
/*      */     } 
/*      */     try {
/* 3893 */       _serializerProvider(cfg).serializeValue(g, value);
/* 3894 */     } catch (Exception e) {
/* 3895 */       ClassUtil.closeOnFailAndThrowAsIOE(g, e);
/*      */       return;
/*      */     } 
/* 3898 */     g.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _configAndWriteCloseable(JsonGenerator g, Object value, SerializationConfig cfg) throws IOException {
/* 3908 */     Closeable toClose = (Closeable)value;
/*      */     try {
/* 3910 */       _serializerProvider(cfg).serializeValue(g, value);
/* 3911 */       Closeable tmpToClose = toClose;
/* 3912 */       toClose = null;
/* 3913 */       tmpToClose.close();
/* 3914 */     } catch (Exception e) {
/* 3915 */       ClassUtil.closeOnFailAndThrowAsIOE(g, toClose, e);
/*      */       return;
/*      */     } 
/* 3918 */     g.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void _writeCloseableValue(JsonGenerator g, Object value, SerializationConfig cfg) throws IOException {
/* 3928 */     Closeable toClose = (Closeable)value;
/*      */     try {
/* 3930 */       _serializerProvider(cfg).serializeValue(g, value);
/* 3931 */       if (cfg.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 3932 */         g.flush();
/*      */       }
/* 3934 */     } catch (Exception e) {
/* 3935 */       ClassUtil.closeOnFailAndThrowAsIOE(null, toClose, e);
/*      */       return;
/*      */     } 
/* 3938 */     toClose.close();
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
/*      */   protected Object _readValue(DeserializationConfig cfg, JsonParser p, JavaType valueType) throws IOException {
/*      */     Object result;
/* 3959 */     JsonToken t = _initForReading(p, valueType);
/* 3960 */     DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p, cfg);
/* 3961 */     if (t == JsonToken.VALUE_NULL) {
/*      */       
/* 3963 */       result = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, valueType).getNullValue((DeserializationContext)defaultDeserializationContext);
/* 3964 */     } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
/* 3965 */       result = null;
/*      */     } else {
/* 3967 */       JsonDeserializer<Object> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, valueType);
/*      */       
/* 3969 */       if (cfg.useRootWrapping()) {
/* 3970 */         result = _unwrapAndDeserialize(p, (DeserializationContext)defaultDeserializationContext, cfg, valueType, deser);
/*      */       } else {
/* 3972 */         result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/*      */       } 
/*      */     } 
/*      */     
/* 3976 */     p.clearCurrentToken();
/* 3977 */     if (cfg.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 3978 */       _verifyNoTrailingTokens(p, (DeserializationContext)defaultDeserializationContext, valueType);
/*      */     }
/* 3980 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _readMapAndClose(JsonParser p0, JavaType valueType) throws IOException {
/* 3986 */     try (JsonParser p = p0) {
/*      */       Object result;
/* 3988 */       JsonToken t = _initForReading(p, valueType);
/* 3989 */       DeserializationConfig cfg = getDeserializationConfig();
/* 3990 */       DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p, cfg);
/* 3991 */       if (t == JsonToken.VALUE_NULL) {
/*      */         
/* 3993 */         result = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, valueType).getNullValue((DeserializationContext)defaultDeserializationContext);
/* 3994 */       } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
/* 3995 */         result = null;
/*      */       } else {
/* 3997 */         JsonDeserializer<Object> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, valueType);
/* 3998 */         if (cfg.useRootWrapping()) {
/* 3999 */           result = _unwrapAndDeserialize(p, (DeserializationContext)defaultDeserializationContext, cfg, valueType, deser);
/*      */         } else {
/* 4001 */           result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/*      */         } 
/* 4003 */         defaultDeserializationContext.checkUnresolvedObjectId();
/*      */       } 
/* 4005 */       if (cfg.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 4006 */         _verifyNoTrailingTokens(p, (DeserializationContext)defaultDeserializationContext, valueType);
/*      */       }
/* 4008 */       return result;
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
/*      */   protected JsonNode _readTreeAndClose(JsonParser p0) throws IOException {
/* 4020 */     try (JsonParser p = p0) {
/* 4021 */       Object result; JavaType valueType = JSON_NODE_TYPE;
/*      */       
/* 4023 */       DeserializationConfig cfg = getDeserializationConfig();
/*      */ 
/*      */ 
/*      */       
/* 4027 */       cfg.initialize(p);
/* 4028 */       JsonToken t = p.getCurrentToken();
/* 4029 */       if (t == null) {
/* 4030 */         t = p.nextToken();
/* 4031 */         if (t == null) {
/* 4032 */           return null;
/*      */         }
/*      */       } 
/* 4035 */       if (t == JsonToken.VALUE_NULL) {
/* 4036 */         return (JsonNode)cfg.getNodeFactory().nullNode();
/*      */       }
/* 4038 */       DefaultDeserializationContext defaultDeserializationContext = createDeserializationContext(p, cfg);
/* 4039 */       JsonDeserializer<Object> deser = _findRootDeserializer((DeserializationContext)defaultDeserializationContext, valueType);
/*      */       
/* 4041 */       if (cfg.useRootWrapping()) {
/* 4042 */         result = _unwrapAndDeserialize(p, (DeserializationContext)defaultDeserializationContext, cfg, valueType, deser);
/*      */       } else {
/* 4044 */         result = deser.deserialize(p, (DeserializationContext)defaultDeserializationContext);
/* 4045 */         if (cfg.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 4046 */           _verifyNoTrailingTokens(p, (DeserializationContext)defaultDeserializationContext, valueType);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 4051 */       return (JsonNode)result;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _unwrapAndDeserialize(JsonParser p, DeserializationContext ctxt, DeserializationConfig config, JavaType rootType, JsonDeserializer<Object> deser) throws IOException {
/* 4060 */     PropertyName expRootName = config.findRootName(rootType);
/*      */     
/* 4062 */     String expSimpleName = expRootName.getSimpleName();
/* 4063 */     if (p.getCurrentToken() != JsonToken.START_OBJECT) {
/* 4064 */       ctxt.reportWrongTokenException(rootType, JsonToken.START_OBJECT, "Current token not START_OBJECT (needed to unwrap root name '%s'), but %s", new Object[] { expSimpleName, p.getCurrentToken() });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4069 */     if (p.nextToken() != JsonToken.FIELD_NAME) {
/* 4070 */       ctxt.reportWrongTokenException(rootType, JsonToken.FIELD_NAME, "Current token not FIELD_NAME (to contain expected root name '" + expSimpleName + "'), but " + p.getCurrentToken(), new Object[0]);
/*      */     }
/*      */ 
/*      */     
/* 4074 */     String actualName = p.getCurrentName();
/* 4075 */     if (!expSimpleName.equals(actualName)) {
/* 4076 */       ctxt.reportInputMismatch(rootType, "Root name '%s' does not match expected ('%s') for type %s", new Object[] { actualName, expSimpleName });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4081 */     p.nextToken();
/* 4082 */     Object result = deser.deserialize(p, ctxt);
/*      */     
/* 4084 */     if (p.nextToken() != JsonToken.END_OBJECT) {
/* 4085 */       ctxt.reportWrongTokenException(rootType, JsonToken.END_OBJECT, "Current token not END_OBJECT (to match wrapper object with root name '%s'), but %s", new Object[] { expSimpleName, p.getCurrentToken() });
/*      */     }
/*      */ 
/*      */     
/* 4089 */     if (config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
/* 4090 */       _verifyNoTrailingTokens(p, ctxt, rootType);
/*      */     }
/* 4092 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DefaultDeserializationContext createDeserializationContext(JsonParser p, DeserializationConfig cfg) {
/* 4102 */     return this._deserializationContext.createInstance(cfg, p, this._injectableValues);
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
/*      */   protected JsonToken _initForReading(JsonParser p, JavaType targetType) throws IOException {
/* 4122 */     this._deserializationConfig.initialize(p);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4127 */     JsonToken t = p.getCurrentToken();
/* 4128 */     if (t == null) {
/*      */       
/* 4130 */       t = p.nextToken();
/* 4131 */       if (t == null)
/*      */       {
/*      */         
/* 4134 */         throw MismatchedInputException.from(p, targetType, "No content to map due to end-of-input");
/*      */       }
/*      */     } 
/*      */     
/* 4138 */     return t;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   protected JsonToken _initForReading(JsonParser p) throws IOException {
/* 4143 */     return _initForReading(p, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void _verifyNoTrailingTokens(JsonParser p, DeserializationContext ctxt, JavaType bindType) throws IOException {
/* 4153 */     JsonToken t = p.nextToken();
/* 4154 */     if (t != null) {
/* 4155 */       Class<?> bt = ClassUtil.rawClass(bindType);
/* 4156 */       ctxt.reportTrailingTokens(bt, p, t);
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
/*      */   protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext ctxt, JavaType valueType) throws JsonMappingException {
/* 4174 */     JsonDeserializer<Object> deser = this._rootDeserializers.get(valueType);
/* 4175 */     if (deser != null) {
/* 4176 */       return deser;
/*      */     }
/*      */     
/* 4179 */     deser = ctxt.findRootValueDeserializer(valueType);
/* 4180 */     if (deser == null) {
/* 4181 */       return ctxt.<JsonDeserializer<Object>>reportBadDefinition(valueType, "Cannot find a deserializer for type " + valueType);
/*      */     }
/*      */     
/* 4184 */     this._rootDeserializers.put(valueType, deser);
/* 4185 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _verifySchemaType(FormatSchema schema) {
/* 4193 */     if (schema != null && 
/* 4194 */       !this._jsonFactory.canUseSchema(schema))
/* 4195 */       throw new IllegalArgumentException("Cannot use FormatSchema of type " + schema.getClass().getName() + " for format " + this._jsonFactory.getFormatName()); 
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ObjectMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */