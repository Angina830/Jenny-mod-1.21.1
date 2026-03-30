/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ValueInjector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
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
/*     */ public class BeanDeserializerBuilder
/*     */ {
/*     */   protected final DeserializationConfig _config;
/*     */   protected final DeserializationContext _context;
/*     */   protected final BeanDescription _beanDesc;
/*  54 */   protected final Map<String, SettableBeanProperty> _properties = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<ValueInjector> _injectables;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HashMap<String, SettableBeanProperty> _backRefProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HashSet<String> _ignorableProps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ValueInstantiator _valueInstantiator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectIdReader _objectIdReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SettableAnyProperty _anySetter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _ignoreAllUnknown;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMethod _buildMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonPOJOBuilder.Value _builderConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDeserializerBuilder(BeanDescription beanDesc, DeserializationContext ctxt) {
/* 117 */     this._beanDesc = beanDesc;
/* 118 */     this._context = ctxt;
/* 119 */     this._config = ctxt.getConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanDeserializerBuilder(BeanDeserializerBuilder src) {
/* 128 */     this._beanDesc = src._beanDesc;
/* 129 */     this._context = src._context;
/* 130 */     this._config = src._config;
/*     */ 
/*     */     
/* 133 */     this._properties.putAll(src._properties);
/* 134 */     this._injectables = _copy(src._injectables);
/* 135 */     this._backRefProperties = _copy(src._backRefProperties);
/*     */     
/* 137 */     this._ignorableProps = src._ignorableProps;
/* 138 */     this._valueInstantiator = src._valueInstantiator;
/* 139 */     this._objectIdReader = src._objectIdReader;
/*     */     
/* 141 */     this._anySetter = src._anySetter;
/* 142 */     this._ignoreAllUnknown = src._ignoreAllUnknown;
/*     */     
/* 144 */     this._buildMethod = src._buildMethod;
/* 145 */     this._builderConfig = src._builderConfig;
/*     */   }
/*     */   
/*     */   private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> src) {
/* 149 */     return (src == null) ? null : new HashMap<>(src);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> List<T> _copy(List<T> src) {
/* 154 */     return (src == null) ? null : new ArrayList<>(src);
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
/*     */   public void addOrReplaceProperty(SettableBeanProperty prop, boolean allowOverride) {
/* 167 */     this._properties.put(prop.getName(), prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(SettableBeanProperty prop) {
/* 177 */     SettableBeanProperty old = this._properties.put(prop.getName(), prop);
/* 178 */     if (old != null && old != prop) {
/* 179 */       throw new IllegalArgumentException("Duplicate property '" + prop.getName() + "' for " + this._beanDesc.getType());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackReferenceProperty(String referenceName, SettableBeanProperty prop) {
/* 190 */     if (this._backRefProperties == null) {
/* 191 */       this._backRefProperties = new HashMap<>(4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 196 */     prop.fixAccess(this._config);
/* 197 */     this._backRefProperties.put(referenceName, prop);
/*     */     
/* 199 */     if (this._properties != null) {
/* 200 */       this._properties.remove(prop.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInjectable(PropertyName propName, JavaType propType, Annotations contextAnnotations, AnnotatedMember member, Object valueId) {
/* 210 */     if (this._injectables == null) {
/* 211 */       this._injectables = new ArrayList<>();
/*     */     }
/* 213 */     boolean fixAccess = this._config.canOverrideAccessModifiers();
/* 214 */     boolean forceAccess = (fixAccess && this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/* 215 */     if (fixAccess) {
/* 216 */       member.fixAccess(forceAccess);
/*     */     }
/* 218 */     this._injectables.add(new ValueInjector(propName, propType, member, valueId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIgnorable(String propName) {
/* 227 */     if (this._ignorableProps == null) {
/* 228 */       this._ignorableProps = new HashSet<>();
/*     */     }
/* 230 */     this._ignorableProps.add(propName);
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
/*     */   public void addCreatorProperty(SettableBeanProperty prop) {
/* 245 */     addProperty(prop);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnySetter(SettableAnyProperty s) {
/* 250 */     if (this._anySetter != null && s != null) {
/* 251 */       throw new IllegalStateException("_anySetter already set to non-null");
/*     */     }
/* 253 */     this._anySetter = s;
/*     */   }
/*     */   
/*     */   public void setIgnoreUnknownProperties(boolean ignore) {
/* 257 */     this._ignoreAllUnknown = ignore;
/*     */   }
/*     */   
/*     */   public void setValueInstantiator(ValueInstantiator inst) {
/* 261 */     this._valueInstantiator = inst;
/*     */   }
/*     */   
/*     */   public void setObjectIdReader(ObjectIdReader r) {
/* 265 */     this._objectIdReader = r;
/*     */   }
/*     */   
/*     */   public void setPOJOBuilder(AnnotatedMethod buildMethod, JsonPOJOBuilder.Value config) {
/* 269 */     this._buildMethod = buildMethod;
/* 270 */     this._builderConfig = config;
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
/*     */   public Iterator<SettableBeanProperty> getProperties() {
/* 288 */     return this._properties.values().iterator();
/*     */   }
/*     */   
/*     */   public SettableBeanProperty findProperty(PropertyName propertyName) {
/* 292 */     return this._properties.get(propertyName.getSimpleName());
/*     */   }
/*     */   
/*     */   public boolean hasProperty(PropertyName propertyName) {
/* 296 */     return (findProperty(propertyName) != null);
/*     */   }
/*     */   
/*     */   public SettableBeanProperty removeProperty(PropertyName name) {
/* 300 */     return this._properties.remove(name.getSimpleName());
/*     */   }
/*     */   
/*     */   public SettableAnyProperty getAnySetter() {
/* 304 */     return this._anySetter;
/*     */   }
/*     */   
/*     */   public ValueInstantiator getValueInstantiator() {
/* 308 */     return this._valueInstantiator;
/*     */   }
/*     */   
/*     */   public List<ValueInjector> getInjectables() {
/* 312 */     return this._injectables;
/*     */   }
/*     */   
/*     */   public ObjectIdReader getObjectIdReader() {
/* 316 */     return this._objectIdReader;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod getBuildMethod() {
/* 320 */     return this._buildMethod;
/*     */   }
/*     */   
/*     */   public JsonPOJOBuilder.Value getBuilderConfig() {
/* 324 */     return this._builderConfig;
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
/*     */   public JsonDeserializer<?> build() {
/* 339 */     Collection<SettableBeanProperty> props = this._properties.values();
/* 340 */     _fixAccess(props);
/*     */     
/* 342 */     BeanPropertyMap propertyMap = BeanPropertyMap.construct(props, this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), _collectAliases(props));
/*     */ 
/*     */     
/* 345 */     propertyMap.assignIndexes();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     boolean anyViews = !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
/* 351 */     if (!anyViews) {
/* 352 */       for (SettableBeanProperty prop : props) {
/* 353 */         if (prop.hasViews()) {
/* 354 */           anyViews = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 361 */     if (this._objectIdReader != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 366 */       ObjectIdValueProperty prop = new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED);
/* 367 */       propertyMap = propertyMap.withProperty((SettableBeanProperty)prop);
/*     */     } 
/*     */     
/* 370 */     return (JsonDeserializer<?>)new BeanDeserializer(this, this._beanDesc, propertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, anyViews);
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
/*     */   public AbstractDeserializer buildAbstract() {
/* 383 */     return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties, this._properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> buildBuilderBased(JavaType valueType, String expBuildMethodName) throws JsonMappingException {
/* 394 */     if (this._buildMethod == null) {
/*     */       
/* 396 */       if (!expBuildMethodName.isEmpty()) {
/* 397 */         this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Builder class %s does not have build method (name: '%s')", new Object[] { this._beanDesc.getBeanClass().getName(), expBuildMethodName }));
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 404 */       Class<?> rawBuildType = this._buildMethod.getRawReturnType();
/* 405 */       Class<?> rawValueType = valueType.getRawClass();
/* 406 */       if (rawBuildType != rawValueType && !rawBuildType.isAssignableFrom(rawValueType) && !rawValueType.isAssignableFrom(rawBuildType))
/*     */       {
/*     */         
/* 409 */         this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Build method '%s' has wrong return type (%s), not compatible with POJO type (%s)", new Object[] { this._buildMethod.getFullName(), rawBuildType.getName(), valueType.getRawClass().getName() }));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 417 */     Collection<SettableBeanProperty> props = this._properties.values();
/* 418 */     _fixAccess(props);
/* 419 */     BeanPropertyMap propertyMap = BeanPropertyMap.construct(props, this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), _collectAliases(props));
/*     */ 
/*     */     
/* 422 */     propertyMap.assignIndexes();
/*     */     
/* 424 */     boolean anyViews = !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
/*     */     
/* 426 */     if (!anyViews) {
/* 427 */       for (SettableBeanProperty prop : props) {
/* 428 */         if (prop.hasViews()) {
/* 429 */           anyViews = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 435 */     if (this._objectIdReader != null) {
/*     */ 
/*     */       
/* 438 */       ObjectIdValueProperty prop = new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED);
/*     */       
/* 440 */       propertyMap = propertyMap.withProperty((SettableBeanProperty)prop);
/*     */     } 
/*     */     
/* 443 */     return (JsonDeserializer<?>)new BuilderBasedDeserializer(this, this._beanDesc, valueType, propertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, anyViews);
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
/*     */   protected void _fixAccess(Collection<SettableBeanProperty> mainProps) {
/* 468 */     for (SettableBeanProperty prop : mainProps)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 475 */       prop.fixAccess(this._config);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 486 */     if (this._anySetter != null) {
/* 487 */       this._anySetter.fixAccess(this._config);
/*     */     }
/* 489 */     if (this._buildMethod != null) {
/* 490 */       this._buildMethod.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, List<PropertyName>> _collectAliases(Collection<SettableBeanProperty> props) {
/* 496 */     Map<String, List<PropertyName>> mapping = null;
/* 497 */     AnnotationIntrospector intr = this._config.getAnnotationIntrospector();
/* 498 */     if (intr != null) {
/* 499 */       for (SettableBeanProperty prop : props) {
/* 500 */         List<PropertyName> aliases = intr.findPropertyAliases((Annotated)prop.getMember());
/* 501 */         if (aliases == null || aliases.isEmpty()) {
/*     */           continue;
/*     */         }
/* 504 */         if (mapping == null) {
/* 505 */           mapping = new HashMap<>();
/*     */         }
/* 507 */         mapping.put(prop.getName(), aliases);
/*     */       } 
/*     */     }
/* 510 */     if (mapping == null) {
/* 511 */       return Collections.emptyMap();
/*     */     }
/* 513 */     return mapping;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\BeanDeserializerBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */