/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonInject;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.CreatorCollector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.EnumDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.MapDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ArrayType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.CollectionType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapLikeType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ReferenceType;
/*      */ 
/*      */ public abstract class BasicDeserializerFactory extends DeserializerFactory implements Serializable {
/*   39 */   private static final Class<?> CLASS_OBJECT = Object.class;
/*   40 */   private static final Class<?> CLASS_STRING = String.class;
/*   41 */   private static final Class<?> CLASS_CHAR_BUFFER = CharSequence.class;
/*   42 */   private static final Class<?> CLASS_ITERABLE = Iterable.class;
/*   43 */   private static final Class<?> CLASS_MAP_ENTRY = Map.Entry.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   49 */   protected static final PropertyName UNWRAPPED_CREATOR_PARAM_NAME = new PropertyName("@JsonUnwrapped");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   56 */   static final HashMap<String, Class<? extends Map>> _mapFallbacks = new HashMap<>();
/*      */   
/*      */   static {
/*   59 */     _mapFallbacks.put(Map.class.getName(), LinkedHashMap.class);
/*   60 */     _mapFallbacks.put(ConcurrentMap.class.getName(), ConcurrentHashMap.class);
/*   61 */     _mapFallbacks.put(SortedMap.class.getName(), TreeMap.class);
/*      */     
/*   63 */     _mapFallbacks.put(NavigableMap.class.getName(), TreeMap.class);
/*   64 */     _mapFallbacks.put(ConcurrentNavigableMap.class.getName(), ConcurrentSkipListMap.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   static final HashMap<String, Class<? extends Collection>> _collectionFallbacks = new HashMap<>(); protected final DeserializerFactoryConfig _factoryConfig;
/*      */   
/*      */   static {
/*   76 */     _collectionFallbacks.put(Collection.class.getName(), ArrayList.class);
/*   77 */     _collectionFallbacks.put(List.class.getName(), ArrayList.class);
/*   78 */     _collectionFallbacks.put(Set.class.getName(), HashSet.class);
/*   79 */     _collectionFallbacks.put(SortedSet.class.getName(), TreeSet.class);
/*   80 */     _collectionFallbacks.put(Queue.class.getName(), LinkedList.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   87 */     _collectionFallbacks.put("java.util.Deque", LinkedList.class);
/*   88 */     _collectionFallbacks.put("java.util.NavigableSet", TreeSet.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasicDeserializerFactory(DeserializerFactoryConfig config) {
/*  110 */     this._factoryConfig = config;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeserializerFactoryConfig getFactoryConfig() {
/*  121 */     return this._factoryConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DeserializerFactory withAdditionalDeserializers(Deserializers additional) {
/*  138 */     return withConfig(this._factoryConfig.withAdditionalDeserializers(additional));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers additional) {
/*  147 */     return withConfig(this._factoryConfig.withAdditionalKeyDeserializers(additional));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier modifier) {
/*  156 */     return withConfig(this._factoryConfig.withDeserializerModifier(modifier));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver resolver) {
/*  165 */     return withConfig(this._factoryConfig.withAbstractTypeResolver(resolver));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DeserializerFactory withValueInstantiators(ValueInstantiators instantiators) {
/*  174 */     return withConfig(this._factoryConfig.withValueInstantiators(instantiators));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType mapAbstractType(DeserializationConfig config, JavaType type) throws JsonMappingException {
/*      */     while (true) {
/*  188 */       JavaType next = _mapAbstractType2(config, type);
/*  189 */       if (next == null) {
/*  190 */         return type;
/*      */       }
/*      */ 
/*      */       
/*  194 */       Class<?> prevCls = type.getRawClass();
/*  195 */       Class<?> nextCls = next.getRawClass();
/*  196 */       if (prevCls == nextCls || !prevCls.isAssignableFrom(nextCls)) {
/*  197 */         throw new IllegalArgumentException("Invalid abstract type resolution from " + type + " to " + next + ": latter is not a subtype of former");
/*      */       }
/*  199 */       type = next;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JavaType _mapAbstractType2(DeserializationConfig config, JavaType type) throws JsonMappingException {
/*  210 */     Class<?> currClass = type.getRawClass();
/*  211 */     if (this._factoryConfig.hasAbstractTypeResolvers()) {
/*  212 */       for (AbstractTypeResolver resolver : this._factoryConfig.abstractTypeResolvers()) {
/*  213 */         JavaType concrete = resolver.findTypeMapping(config, type);
/*  214 */         if (ClassUtil.rawClass(concrete) != currClass) {
/*  215 */           return concrete;
/*      */         }
/*      */       } 
/*      */     }
/*  219 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueInstantiator findValueInstantiator(DeserializationContext ctxt, BeanDescription beanDesc) throws JsonMappingException {
/*  238 */     DeserializationConfig config = ctxt.getConfig();
/*      */     
/*  240 */     ValueInstantiator instantiator = null;
/*      */     
/*  242 */     AnnotatedClass ac = beanDesc.getClassInfo();
/*  243 */     Object instDef = ctxt.getAnnotationIntrospector().findValueInstantiator(ac);
/*  244 */     if (instDef != null) {
/*  245 */       instantiator = _valueInstantiatorInstance(config, (Annotated)ac, instDef);
/*      */     }
/*  247 */     if (instantiator == null) {
/*      */ 
/*      */       
/*  250 */       instantiator = _findStdValueInstantiator(config, beanDesc);
/*  251 */       if (instantiator == null) {
/*  252 */         instantiator = _constructDefaultValueInstantiator(ctxt, beanDesc);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  257 */     if (this._factoryConfig.hasValueInstantiators()) {
/*  258 */       for (ValueInstantiators insts : this._factoryConfig.valueInstantiators()) {
/*  259 */         instantiator = insts.findValueInstantiator(config, beanDesc, instantiator);
/*      */         
/*  261 */         if (instantiator == null) {
/*  262 */           ctxt.reportBadTypeDefinition(beanDesc, "Broken registered ValueInstantiators (of type %s): returned null ValueInstantiator", new Object[] { insts.getClass().getName() });
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  270 */     if (instantiator.getIncompleteParameter() != null) {
/*  271 */       AnnotatedParameter nonAnnotatedParam = instantiator.getIncompleteParameter();
/*  272 */       AnnotatedWithParams ctor = nonAnnotatedParam.getOwner();
/*  273 */       throw new IllegalArgumentException("Argument #" + nonAnnotatedParam.getIndex() + " of constructor " + ctor + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
/*      */     } 
/*      */     
/*  276 */     return instantiator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueInstantiator _findStdValueInstantiator(DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
/*  283 */     if (beanDesc.getBeanClass() == JsonLocation.class) {
/*  284 */       return (ValueInstantiator)new JsonLocationInstantiator();
/*      */     }
/*  286 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ValueInstantiator _constructDefaultValueInstantiator(DeserializationContext ctxt, BeanDescription beanDesc) throws JsonMappingException {
/*  297 */     CreatorCollector creators = new CreatorCollector(beanDesc, (MapperConfig)ctxt.getConfig());
/*  298 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/*      */ 
/*      */     
/*  301 */     DeserializationConfig config = ctxt.getConfig();
/*  302 */     VisibilityChecker<?> vchecker = config.getDefaultVisibilityChecker(beanDesc.getBeanClass(), beanDesc.getClassInfo());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  313 */     Map<AnnotatedWithParams, BeanPropertyDefinition[]> creatorDefs = _findCreatorsFromProperties(ctxt, beanDesc);
/*      */ 
/*      */ 
/*      */     
/*  317 */     _addDeserializerFactoryMethods(ctxt, beanDesc, vchecker, intr, creators, creatorDefs);
/*      */     
/*  319 */     if (beanDesc.getType().isConcrete()) {
/*  320 */       _addDeserializerConstructors(ctxt, beanDesc, vchecker, intr, creators, creatorDefs);
/*      */     }
/*  322 */     return creators.constructValueInstantiator(config);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<AnnotatedWithParams, BeanPropertyDefinition[]> _findCreatorsFromProperties(DeserializationContext ctxt, BeanDescription beanDesc) throws JsonMappingException {
/*  328 */     Map<AnnotatedWithParams, BeanPropertyDefinition[]> result = (Map)Collections.emptyMap();
/*  329 */     for (BeanPropertyDefinition propDef : beanDesc.findProperties()) {
/*  330 */       Iterator<AnnotatedParameter> it = propDef.getConstructorParameters();
/*  331 */       while (it.hasNext()) {
/*  332 */         AnnotatedParameter param = it.next();
/*  333 */         AnnotatedWithParams owner = param.getOwner();
/*  334 */         BeanPropertyDefinition[] defs = result.get(owner);
/*  335 */         int index = param.getIndex();
/*      */         
/*  337 */         if (defs == null) {
/*  338 */           if (result.isEmpty()) {
/*  339 */             result = (Map)new LinkedHashMap<>();
/*      */           }
/*  341 */           defs = new BeanPropertyDefinition[owner.getParameterCount()];
/*  342 */           result.put(owner, defs);
/*      */         }
/*  344 */         else if (defs[index] != null) {
/*  345 */           throw new IllegalStateException("Conflict: parameter #" + index + " of " + owner + " bound to more than one property; " + defs[index] + " vs " + propDef);
/*      */         } 
/*      */ 
/*      */         
/*  349 */         defs[index] = propDef;
/*      */       } 
/*      */     } 
/*  352 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueInstantiator _valueInstantiatorInstance(DeserializationConfig config, Annotated annotated, Object instDef) throws JsonMappingException {
/*  359 */     if (instDef == null) {
/*  360 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  365 */     if (instDef instanceof ValueInstantiator) {
/*  366 */       return (ValueInstantiator)instDef;
/*      */     }
/*  368 */     if (!(instDef instanceof Class)) {
/*  369 */       throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + instDef.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
/*      */     }
/*      */ 
/*      */     
/*  373 */     Class<?> instClass = (Class)instDef;
/*  374 */     if (ClassUtil.isBogusClass(instClass)) {
/*  375 */       return null;
/*      */     }
/*  377 */     if (!ValueInstantiator.class.isAssignableFrom(instClass)) {
/*  378 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + instClass.getName() + "; expected Class<ValueInstantiator>");
/*      */     }
/*      */     
/*  381 */     HandlerInstantiator hi = config.getHandlerInstantiator();
/*  382 */     if (hi != null) {
/*  383 */       ValueInstantiator inst = hi.valueInstantiatorInstance((MapperConfig)config, annotated, instClass);
/*  384 */       if (inst != null) {
/*  385 */         return inst;
/*      */       }
/*      */     } 
/*  388 */     return (ValueInstantiator)ClassUtil.createInstance(instClass, config.canOverrideAccessModifiers());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _addDeserializerConstructors(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, Map<AnnotatedWithParams, BeanPropertyDefinition[]> creatorParams) throws JsonMappingException {
/*  401 */     AnnotatedConstructor defaultCtor = beanDesc.findDefaultConstructor();
/*  402 */     if (defaultCtor != null && (
/*  403 */       !creators.hasDefaultCreator() || _hasCreatorAnnotation(ctxt, (Annotated)defaultCtor))) {
/*  404 */       creators.setDefaultCreator((AnnotatedWithParams)defaultCtor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  411 */     boolean isNonStaticInnerClass = beanDesc.isNonStaticInnerClass();
/*  412 */     if (isNonStaticInnerClass) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  418 */     List<AnnotatedConstructor> implicitCtors = null;
/*  419 */     for (AnnotatedConstructor ctor : beanDesc.getConstructors()) {
/*  420 */       JsonCreator.Mode creatorMode = intr.findCreatorAnnotation((MapperConfig)ctxt.getConfig(), (Annotated)ctor);
/*  421 */       boolean isCreator = (creatorMode != null && creatorMode != JsonCreator.Mode.DISABLED);
/*  422 */       BeanPropertyDefinition[] propDefs = creatorParams.get(ctor);
/*  423 */       int argCount = ctor.getParameterCount();
/*      */ 
/*      */       
/*  426 */       if (argCount == 1) {
/*  427 */         BeanPropertyDefinition argDef = (propDefs == null) ? null : propDefs[0];
/*  428 */         boolean useProps = _checkIfCreatorPropertyBased(intr, (AnnotatedWithParams)ctor, argDef, creatorMode);
/*      */         
/*  430 */         if (useProps) {
/*  431 */           SettableBeanProperty[] arrayOfSettableBeanProperty = new SettableBeanProperty[1];
/*  432 */           PropertyName name = (argDef == null) ? null : argDef.getFullName();
/*  433 */           AnnotatedParameter arg = ctor.getParameter(0);
/*  434 */           arrayOfSettableBeanProperty[0] = constructCreatorProperty(ctxt, beanDesc, name, 0, arg, intr.findInjectableValue((AnnotatedMember)arg));
/*      */           
/*  436 */           creators.addPropertyCreator((AnnotatedWithParams)ctor, isCreator, arrayOfSettableBeanProperty); continue;
/*      */         } 
/*  438 */         _handleSingleArgumentConstructor(ctxt, beanDesc, vchecker, intr, creators, ctor, isCreator, vchecker.isCreatorVisible((AnnotatedMember)ctor));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  443 */         if (argDef != null) {
/*  444 */           ((POJOPropertyBuilder)argDef).removeConstructors();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  455 */       AnnotatedParameter nonAnnotatedParam = null;
/*  456 */       SettableBeanProperty[] properties = new SettableBeanProperty[argCount];
/*  457 */       int explicitNameCount = 0;
/*  458 */       int implicitWithCreatorCount = 0;
/*  459 */       int injectCount = 0;
/*      */       
/*  461 */       for (int i = 0; i < argCount; i++) {
/*  462 */         AnnotatedParameter param = ctor.getParameter(i);
/*  463 */         BeanPropertyDefinition propDef = (propDefs == null) ? null : propDefs[i];
/*  464 */         JacksonInject.Value injectId = intr.findInjectableValue((AnnotatedMember)param);
/*  465 */         PropertyName name = (propDef == null) ? null : propDef.getFullName();
/*      */         
/*  467 */         if (propDef != null && propDef.isExplicitlyNamed()) {
/*  468 */           explicitNameCount++;
/*  469 */           properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
/*      */         
/*      */         }
/*  472 */         else if (injectId != null) {
/*  473 */           injectCount++;
/*  474 */           properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
/*      */         } else {
/*      */           
/*  477 */           NameTransformer unwrapper = intr.findUnwrappingNameTransformer((AnnotatedMember)param);
/*  478 */           if (unwrapper != null) {
/*  479 */             _reportUnwrappedCreatorProperty(ctxt, beanDesc, param);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  487 */           else if (isCreator && name != null && !name.isEmpty()) {
/*  488 */             implicitWithCreatorCount++;
/*  489 */             properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
/*      */           
/*      */           }
/*  492 */           else if (nonAnnotatedParam == null) {
/*  493 */             nonAnnotatedParam = param;
/*      */           } 
/*      */         } 
/*      */       } 
/*  497 */       int namedCount = explicitNameCount + implicitWithCreatorCount;
/*      */       
/*  499 */       if (isCreator || explicitNameCount > 0 || injectCount > 0) {
/*      */         
/*  501 */         if (namedCount + injectCount == argCount) {
/*  502 */           creators.addPropertyCreator((AnnotatedWithParams)ctor, isCreator, properties);
/*      */           continue;
/*      */         } 
/*  505 */         if (explicitNameCount == 0 && injectCount + 1 == argCount) {
/*      */           
/*  507 */           creators.addDelegatingCreator((AnnotatedWithParams)ctor, isCreator, properties);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  513 */         PropertyName impl = _findImplicitParamName(nonAnnotatedParam, intr);
/*  514 */         if (impl == null || impl.isEmpty()) {
/*      */           
/*  516 */           int ix = nonAnnotatedParam.getIndex();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  524 */           throw new IllegalArgumentException("Argument #" + ix + " of constructor " + ctor + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  529 */       if (!creators.hasDefaultCreator()) {
/*  530 */         if (implicitCtors == null) {
/*  531 */           implicitCtors = new LinkedList<>();
/*      */         }
/*  533 */         implicitCtors.add(ctor);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  538 */     if (implicitCtors != null && !creators.hasDelegatingCreator() && !creators.hasPropertyBasedCreator())
/*      */     {
/*  540 */       _checkImplicitlyNamedConstructors(ctxt, beanDesc, vchecker, intr, creators, implicitCtors);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _checkImplicitlyNamedConstructors(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, List<AnnotatedConstructor> implicitCtors) throws JsonMappingException {
/*  550 */     AnnotatedConstructor found = null;
/*  551 */     SettableBeanProperty[] foundProps = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  558 */     label33: for (AnnotatedConstructor ctor : implicitCtors) {
/*  559 */       if (!vchecker.isCreatorVisible((AnnotatedMember)ctor)) {
/*      */         continue;
/*      */       }
/*      */       
/*  563 */       int argCount = ctor.getParameterCount();
/*  564 */       SettableBeanProperty[] properties = new SettableBeanProperty[argCount];
/*  565 */       for (int i = 0; i < argCount; ) {
/*  566 */         AnnotatedParameter param = ctor.getParameter(i);
/*  567 */         PropertyName name = _findParamName(param, intr);
/*      */ 
/*      */         
/*  570 */         if (name != null) { if (name.isEmpty()) {
/*      */             continue label33;
/*      */           }
/*  573 */           properties[i] = constructCreatorProperty(ctxt, beanDesc, name, param.getIndex(), param, null); i++; }
/*      */          continue label33;
/*      */       } 
/*  576 */       if (found != null) {
/*  577 */         found = null;
/*      */         break;
/*      */       } 
/*  580 */       found = ctor;
/*  581 */       foundProps = properties;
/*      */     } 
/*      */     
/*  584 */     if (found != null) {
/*  585 */       creators.addPropertyCreator((AnnotatedWithParams)found, false, foundProps);
/*  586 */       BasicBeanDescription bbd = (BasicBeanDescription)beanDesc;
/*      */       
/*  588 */       for (SettableBeanProperty prop : foundProps) {
/*  589 */         PropertyName pn = prop.getFullName();
/*  590 */         if (!bbd.hasProperty(pn)) {
/*  591 */           SimpleBeanPropertyDefinition simpleBeanPropertyDefinition = SimpleBeanPropertyDefinition.construct((MapperConfig)ctxt.getConfig(), prop.getMember(), pn);
/*      */           
/*  593 */           bbd.addProperty((BeanPropertyDefinition)simpleBeanPropertyDefinition);
/*      */         } 
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
/*      */   protected boolean _handleSingleArgumentConstructor(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, AnnotatedConstructor ctor, boolean isCreator, boolean isVisible) throws JsonMappingException {
/*  606 */     Class<?> type = ctor.getRawParameterType(0);
/*  607 */     if (type == String.class || type == CharSequence.class) {
/*  608 */       if (isCreator || isVisible) {
/*  609 */         creators.addStringCreator((AnnotatedWithParams)ctor, isCreator);
/*      */       }
/*  611 */       return true;
/*      */     } 
/*  613 */     if (type == int.class || type == Integer.class) {
/*  614 */       if (isCreator || isVisible) {
/*  615 */         creators.addIntCreator((AnnotatedWithParams)ctor, isCreator);
/*      */       }
/*  617 */       return true;
/*      */     } 
/*  619 */     if (type == long.class || type == Long.class) {
/*  620 */       if (isCreator || isVisible) {
/*  621 */         creators.addLongCreator((AnnotatedWithParams)ctor, isCreator);
/*      */       }
/*  623 */       return true;
/*      */     } 
/*  625 */     if (type == double.class || type == Double.class) {
/*  626 */       if (isCreator || isVisible) {
/*  627 */         creators.addDoubleCreator((AnnotatedWithParams)ctor, isCreator);
/*      */       }
/*  629 */       return true;
/*      */     } 
/*  631 */     if (type == boolean.class || type == Boolean.class) {
/*  632 */       if (isCreator || isVisible) {
/*  633 */         creators.addBooleanCreator((AnnotatedWithParams)ctor, isCreator);
/*      */       }
/*  635 */       return true;
/*      */     } 
/*      */     
/*  638 */     if (isCreator) {
/*  639 */       creators.addDelegatingCreator((AnnotatedWithParams)ctor, isCreator, null);
/*  640 */       return true;
/*      */     } 
/*  642 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _addDeserializerFactoryMethods(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, Map<AnnotatedWithParams, BeanPropertyDefinition[]> creatorParams) throws JsonMappingException {
/*  651 */     DeserializationConfig config = ctxt.getConfig();
/*  652 */     for (AnnotatedMethod factory : beanDesc.getFactoryMethods()) {
/*  653 */       JsonCreator.Mode creatorMode = intr.findCreatorAnnotation((MapperConfig)ctxt.getConfig(), (Annotated)factory);
/*  654 */       boolean isCreator = (creatorMode != null && creatorMode != JsonCreator.Mode.DISABLED);
/*  655 */       int argCount = factory.getParameterCount();
/*      */       
/*  657 */       if (argCount == 0) {
/*  658 */         if (isCreator) {
/*  659 */           creators.setDefaultCreator((AnnotatedWithParams)factory);
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  664 */       BeanPropertyDefinition[] propDefs = creatorParams.get(factory);
/*      */       
/*  666 */       if (argCount == 1) {
/*  667 */         BeanPropertyDefinition argDef = (propDefs == null) ? null : propDefs[0];
/*  668 */         boolean useProps = _checkIfCreatorPropertyBased(intr, (AnnotatedWithParams)factory, argDef, creatorMode);
/*  669 */         if (!useProps) {
/*  670 */           _handleSingleArgumentFactory(config, beanDesc, vchecker, intr, creators, factory, isCreator);
/*      */ 
/*      */ 
/*      */           
/*  674 */           if (argDef != null) {
/*  675 */             ((POJOPropertyBuilder)argDef).removeConstructors();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*  682 */       } else if (!isCreator) {
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*  687 */       AnnotatedParameter nonAnnotatedParam = null;
/*  688 */       SettableBeanProperty[] properties = new SettableBeanProperty[argCount];
/*  689 */       int implicitNameCount = 0;
/*  690 */       int explicitNameCount = 0;
/*  691 */       int injectCount = 0;
/*      */       
/*  693 */       for (int i = 0; i < argCount; i++) {
/*  694 */         AnnotatedParameter param = factory.getParameter(i);
/*  695 */         BeanPropertyDefinition propDef = (propDefs == null) ? null : propDefs[i];
/*  696 */         JacksonInject.Value injectable = intr.findInjectableValue((AnnotatedMember)param);
/*  697 */         PropertyName name = (propDef == null) ? null : propDef.getFullName();
/*      */         
/*  699 */         if (propDef != null && propDef.isExplicitlyNamed()) {
/*  700 */           explicitNameCount++;
/*  701 */           properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectable);
/*      */         
/*      */         }
/*  704 */         else if (injectable != null) {
/*  705 */           injectCount++;
/*  706 */           properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectable);
/*      */         } else {
/*      */           
/*  709 */           NameTransformer unwrapper = intr.findUnwrappingNameTransformer((AnnotatedMember)param);
/*  710 */           if (unwrapper != null) {
/*  711 */             _reportUnwrappedCreatorProperty(ctxt, beanDesc, param);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  719 */           else if (isCreator && 
/*  720 */             name != null && !name.isEmpty()) {
/*  721 */             implicitNameCount++;
/*  722 */             properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  738 */           else if (nonAnnotatedParam == null) {
/*  739 */             nonAnnotatedParam = param;
/*      */           } 
/*      */         } 
/*  742 */       }  int namedCount = explicitNameCount + implicitNameCount;
/*      */ 
/*      */       
/*  745 */       if (isCreator || explicitNameCount > 0 || injectCount > 0) {
/*      */         
/*  747 */         if (namedCount + injectCount == argCount) {
/*  748 */           creators.addPropertyCreator((AnnotatedWithParams)factory, isCreator, properties); continue;
/*  749 */         }  if (explicitNameCount == 0 && injectCount + 1 == argCount) {
/*      */           
/*  751 */           creators.addDelegatingCreator((AnnotatedWithParams)factory, isCreator, properties); continue;
/*      */         } 
/*  753 */         throw new IllegalArgumentException("Argument #" + nonAnnotatedParam.getIndex() + " of factory method " + factory + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
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
/*      */   protected boolean _handleSingleArgumentFactory(DeserializationConfig config, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, AnnotatedMethod factory, boolean isCreator) throws JsonMappingException {
/*  766 */     Class<?> type = factory.getRawParameterType(0);
/*      */     
/*  768 */     if (type == String.class || type == CharSequence.class) {
/*  769 */       if (isCreator || vchecker.isCreatorVisible((AnnotatedMember)factory)) {
/*  770 */         creators.addStringCreator((AnnotatedWithParams)factory, isCreator);
/*      */       }
/*  772 */       return true;
/*      */     } 
/*  774 */     if (type == int.class || type == Integer.class) {
/*  775 */       if (isCreator || vchecker.isCreatorVisible((AnnotatedMember)factory)) {
/*  776 */         creators.addIntCreator((AnnotatedWithParams)factory, isCreator);
/*      */       }
/*  778 */       return true;
/*      */     } 
/*  780 */     if (type == long.class || type == Long.class) {
/*  781 */       if (isCreator || vchecker.isCreatorVisible((AnnotatedMember)factory)) {
/*  782 */         creators.addLongCreator((AnnotatedWithParams)factory, isCreator);
/*      */       }
/*  784 */       return true;
/*      */     } 
/*  786 */     if (type == double.class || type == Double.class) {
/*  787 */       if (isCreator || vchecker.isCreatorVisible((AnnotatedMember)factory)) {
/*  788 */         creators.addDoubleCreator((AnnotatedWithParams)factory, isCreator);
/*      */       }
/*  790 */       return true;
/*      */     } 
/*  792 */     if (type == boolean.class || type == Boolean.class) {
/*  793 */       if (isCreator || vchecker.isCreatorVisible((AnnotatedMember)factory)) {
/*  794 */         creators.addBooleanCreator((AnnotatedWithParams)factory, isCreator);
/*      */       }
/*  796 */       return true;
/*      */     } 
/*  798 */     if (isCreator) {
/*  799 */       creators.addDelegatingCreator((AnnotatedWithParams)factory, isCreator, null);
/*  800 */       return true;
/*      */     } 
/*  802 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportUnwrappedCreatorProperty(DeserializationContext ctxt, BeanDescription beanDesc, AnnotatedParameter param) throws JsonMappingException {
/*  811 */     ctxt.reportBadDefinition(beanDesc.getType(), String.format("Cannot define Creator parameter %d as `@JsonUnwrapped`: combination not yet supported", new Object[] { Integer.valueOf(param.getIndex()) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SettableBeanProperty constructCreatorProperty(DeserializationContext ctxt, BeanDescription beanDesc, PropertyName name, int index, AnnotatedParameter param, JacksonInject.Value injectable) throws JsonMappingException {
/*      */     PropertyMetadata metadata;
/*  827 */     DeserializationConfig config = ctxt.getConfig();
/*  828 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/*      */ 
/*      */     
/*  831 */     if (intr == null) {
/*  832 */       metadata = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
/*      */     } else {
/*  834 */       Boolean b = intr.hasRequiredMarker((AnnotatedMember)param);
/*  835 */       String desc = intr.findPropertyDescription((Annotated)param);
/*  836 */       Integer idx = intr.findPropertyIndex((Annotated)param);
/*  837 */       String def = intr.findPropertyDefaultValue((Annotated)param);
/*  838 */       metadata = PropertyMetadata.construct(b, desc, idx, def);
/*      */     } 
/*      */     
/*  841 */     JavaType type = resolveMemberAndTypeAnnotations(ctxt, (AnnotatedMember)param, param.getType());
/*  842 */     BeanProperty.Std property = new BeanProperty.Std(name, type, intr.findWrapperName((Annotated)param), (AnnotatedMember)param, metadata);
/*      */ 
/*      */     
/*  845 */     TypeDeserializer typeDeser = (TypeDeserializer)type.getTypeHandler();
/*      */     
/*  847 */     if (typeDeser == null) {
/*  848 */       typeDeser = findTypeDeserializer(config, type);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  853 */     Object injectableValueId = (injectable == null) ? null : injectable.getId();
/*      */     
/*  855 */     SettableBeanProperty prop = new CreatorProperty(name, type, property.getWrapperName(), typeDeser, beanDesc.getClassAnnotations(), param, index, injectableValueId, metadata);
/*      */ 
/*      */     
/*  858 */     JsonDeserializer<?> deser = findDeserializerFromAnnotation(ctxt, (Annotated)param);
/*  859 */     if (deser == null) {
/*  860 */       deser = (JsonDeserializer)type.getValueHandler();
/*      */     }
/*  862 */     if (deser != null) {
/*      */       
/*  864 */       deser = ctxt.handlePrimaryContextualization(deser, (BeanProperty)prop, type);
/*  865 */       prop = prop.withValueDeserializer(deser);
/*      */     } 
/*  867 */     return prop;
/*      */   }
/*      */ 
/*      */   
/*      */   protected PropertyName _findParamName(AnnotatedParameter param, AnnotationIntrospector intr) {
/*  872 */     if (param != null && intr != null) {
/*  873 */       PropertyName name = intr.findNameForDeserialization((Annotated)param);
/*  874 */       if (name != null) {
/*  875 */         return name;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  880 */       String str = intr.findImplicitPropertyName((AnnotatedMember)param);
/*  881 */       if (str != null && !str.isEmpty()) {
/*  882 */         return PropertyName.construct(str);
/*      */       }
/*      */     } 
/*  885 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected PropertyName _findImplicitParamName(AnnotatedParameter param, AnnotationIntrospector intr) {
/*  890 */     String str = intr.findImplicitPropertyName((AnnotatedMember)param);
/*  891 */     if (str != null && !str.isEmpty()) {
/*  892 */       return PropertyName.construct(str);
/*      */     }
/*  894 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _checkIfCreatorPropertyBased(AnnotationIntrospector intr, AnnotatedWithParams creator, BeanPropertyDefinition propDef, JsonCreator.Mode creatorMode) {
/*  901 */     if (creatorMode == JsonCreator.Mode.PROPERTIES) {
/*  902 */       return true;
/*      */     }
/*  904 */     if (creatorMode == JsonCreator.Mode.DELEGATING) {
/*  905 */       return false;
/*      */     }
/*      */     
/*  908 */     if ((propDef != null && propDef.isExplicitlyNamed()) || intr.findInjectableValue((AnnotatedMember)creator.getParameter(0)) != null)
/*      */     {
/*  910 */       return true;
/*      */     }
/*  912 */     if (propDef != null) {
/*      */ 
/*      */       
/*  915 */       String implName = propDef.getName();
/*  916 */       if (implName != null && !implName.isEmpty() && 
/*  917 */         propDef.couldSerialize()) {
/*  918 */         return true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  923 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createArrayDeserializer(DeserializationContext ctxt, ArrayType type, BeanDescription beanDesc) throws JsonMappingException {
/*      */     ObjectArrayDeserializer objectArrayDeserializer;
/*      */     JsonDeserializer<?> jsonDeserializer1;
/*  937 */     DeserializationConfig config = ctxt.getConfig();
/*  938 */     JavaType elemType = type.getContentType();
/*      */ 
/*      */     
/*  941 */     JsonDeserializer<Object> contentDeser = (JsonDeserializer<Object>)elemType.getValueHandler();
/*      */     
/*  943 */     TypeDeserializer elemTypeDeser = (TypeDeserializer)elemType.getTypeHandler();
/*      */     
/*  945 */     if (elemTypeDeser == null) {
/*  946 */       elemTypeDeser = findTypeDeserializer(config, elemType);
/*      */     }
/*      */     
/*  949 */     JsonDeserializer<?> deser = _findCustomArrayDeserializer(type, config, beanDesc, elemTypeDeser, contentDeser);
/*      */     
/*  951 */     if (deser == null) {
/*  952 */       if (contentDeser == null) {
/*  953 */         Class<?> raw = elemType.getRawClass();
/*  954 */         if (elemType.isPrimitive()) {
/*  955 */           return PrimitiveArrayDeserializers.forType(raw);
/*      */         }
/*  957 */         if (raw == String.class) {
/*  958 */           return (JsonDeserializer<?>)StringArrayDeserializer.instance;
/*      */         }
/*      */       } 
/*  961 */       objectArrayDeserializer = new ObjectArrayDeserializer((JavaType)type, contentDeser, elemTypeDeser);
/*      */     } 
/*      */     
/*  964 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/*  965 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/*  966 */         jsonDeserializer1 = mod.modifyArrayDeserializer(config, type, beanDesc, (JsonDeserializer<?>)objectArrayDeserializer);
/*      */       }
/*      */     }
/*  969 */     return jsonDeserializer1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createCollectionDeserializer(DeserializationContext ctxt, CollectionType type, BeanDescription beanDesc) throws JsonMappingException {
/*      */     EnumSetDeserializer enumSetDeserializer;
/*      */     CollectionDeserializer collectionDeserializer;
/*      */     JsonDeserializer<?> jsonDeserializer1;
/*  983 */     JavaType contentType = type.getContentType();
/*      */     
/*  985 */     JsonDeserializer<Object> contentDeser = (JsonDeserializer<Object>)contentType.getValueHandler();
/*  986 */     DeserializationConfig config = ctxt.getConfig();
/*      */ 
/*      */     
/*  989 */     TypeDeserializer contentTypeDeser = (TypeDeserializer)contentType.getTypeHandler();
/*      */     
/*  991 */     if (contentTypeDeser == null) {
/*  992 */       contentTypeDeser = findTypeDeserializer(config, contentType);
/*      */     }
/*      */     
/*  995 */     JsonDeserializer<?> deser = _findCustomCollectionDeserializer(type, config, beanDesc, contentTypeDeser, contentDeser);
/*      */     
/*  997 */     if (deser == null) {
/*  998 */       Class<?> collectionClass = type.getRawClass();
/*  999 */       if (contentDeser == null)
/*      */       {
/* 1001 */         if (EnumSet.class.isAssignableFrom(collectionClass)) {
/* 1002 */           enumSetDeserializer = new EnumSetDeserializer(contentType, null);
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
/* 1016 */     if (enumSetDeserializer == null) {
/* 1017 */       if (type.isInterface() || type.isAbstract()) {
/* 1018 */         CollectionType implType = _mapAbstractCollectionType((JavaType)type, config);
/* 1019 */         if (implType == null) {
/*      */           
/* 1021 */           if (type.getTypeHandler() == null) {
/* 1022 */             throw new IllegalArgumentException("Cannot find a deserializer for non-concrete Collection type " + type);
/*      */           }
/* 1024 */           jsonDeserializer1 = AbstractDeserializer.constructForNonPOJO(beanDesc);
/*      */         } else {
/* 1026 */           type = implType;
/*      */           
/* 1028 */           beanDesc = config.introspectForCreation((JavaType)type);
/*      */         } 
/*      */       } 
/* 1031 */       if (jsonDeserializer1 == null) {
/* 1032 */         ValueInstantiator inst = findValueInstantiator(ctxt, beanDesc);
/* 1033 */         if (!inst.canCreateUsingDefault())
/*      */         {
/* 1035 */           if (type.hasRawClass(ArrayBlockingQueue.class)) {
/* 1036 */             return (JsonDeserializer<?>)new ArrayBlockingQueueDeserializer((JavaType)type, contentDeser, contentTypeDeser, inst);
/*      */           }
/*      */         }
/*      */         
/* 1040 */         if (contentType.hasRawClass(String.class)) {
/*      */           
/* 1042 */           StringCollectionDeserializer stringCollectionDeserializer = new StringCollectionDeserializer((JavaType)type, contentDeser, inst);
/*      */         } else {
/* 1044 */           collectionDeserializer = new CollectionDeserializer((JavaType)type, contentDeser, contentTypeDeser, inst);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1049 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1050 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1051 */         jsonDeserializer1 = mod.modifyCollectionDeserializer(config, type, beanDesc, (JsonDeserializer<?>)collectionDeserializer);
/*      */       }
/*      */     }
/* 1054 */     return jsonDeserializer1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected CollectionType _mapAbstractCollectionType(JavaType type, DeserializationConfig config) {
/* 1059 */     Class<?> collectionClass = type.getRawClass();
/* 1060 */     collectionClass = _collectionFallbacks.get(collectionClass.getName());
/* 1061 */     if (collectionClass == null) {
/* 1062 */       return null;
/*      */     }
/* 1064 */     return (CollectionType)config.constructSpecializedType(type, collectionClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext ctxt, CollectionLikeType type, BeanDescription beanDesc) throws JsonMappingException {
/* 1073 */     JavaType contentType = type.getContentType();
/*      */     
/* 1075 */     JsonDeserializer<Object> contentDeser = (JsonDeserializer<Object>)contentType.getValueHandler();
/* 1076 */     DeserializationConfig config = ctxt.getConfig();
/*      */ 
/*      */     
/* 1079 */     TypeDeserializer contentTypeDeser = (TypeDeserializer)contentType.getTypeHandler();
/*      */     
/* 1081 */     if (contentTypeDeser == null) {
/* 1082 */       contentTypeDeser = findTypeDeserializer(config, contentType);
/*      */     }
/* 1084 */     JsonDeserializer<?> deser = _findCustomCollectionLikeDeserializer(type, config, beanDesc, contentTypeDeser, contentDeser);
/*      */     
/* 1086 */     if (deser != null)
/*      */     {
/* 1088 */       if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1089 */         for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1090 */           deser = mod.modifyCollectionLikeDeserializer(config, type, beanDesc, deser);
/*      */         }
/*      */       }
/*      */     }
/* 1094 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createMapDeserializer(DeserializationContext ctxt, MapType type, BeanDescription beanDesc) throws JsonMappingException {
/*      */     MapDeserializer mapDeserializer;
/*      */     JsonDeserializer<?> jsonDeserializer1;
/* 1108 */     DeserializationConfig config = ctxt.getConfig();
/* 1109 */     JavaType keyType = type.getKeyType();
/* 1110 */     JavaType contentType = type.getContentType();
/*      */ 
/*      */ 
/*      */     
/* 1114 */     JsonDeserializer<Object> contentDeser = (JsonDeserializer<Object>)contentType.getValueHandler();
/*      */ 
/*      */     
/* 1117 */     KeyDeserializer keyDes = (KeyDeserializer)keyType.getValueHandler();
/*      */     
/* 1119 */     TypeDeserializer contentTypeDeser = (TypeDeserializer)contentType.getTypeHandler();
/*      */     
/* 1121 */     if (contentTypeDeser == null) {
/* 1122 */       contentTypeDeser = findTypeDeserializer(config, contentType);
/*      */     }
/*      */ 
/*      */     
/* 1126 */     JsonDeserializer<?> deser = _findCustomMapDeserializer(type, config, beanDesc, keyDes, contentTypeDeser, contentDeser);
/*      */ 
/*      */     
/* 1129 */     if (deser == null) {
/*      */       EnumMapDeserializer enumMapDeserializer;
/* 1131 */       Class<?> mapClass = type.getRawClass();
/* 1132 */       if (EnumMap.class.isAssignableFrom(mapClass)) {
/*      */         ValueInstantiator inst;
/*      */ 
/*      */ 
/*      */         
/* 1137 */         if (mapClass == EnumMap.class) {
/* 1138 */           inst = null;
/*      */         } else {
/* 1140 */           inst = findValueInstantiator(ctxt, beanDesc);
/*      */         } 
/* 1142 */         Class<?> kt = keyType.getRawClass();
/* 1143 */         if (kt == null || !kt.isEnum()) {
/* 1144 */           throw new IllegalArgumentException("Cannot construct EnumMap; generic (key) type not available");
/*      */         }
/* 1146 */         enumMapDeserializer = new EnumMapDeserializer((JavaType)type, inst, null, contentDeser, contentTypeDeser, null);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1161 */       if (enumMapDeserializer == null) {
/* 1162 */         if (type.isInterface() || type.isAbstract()) {
/*      */           
/* 1164 */           Class<? extends Map> fallback = _mapFallbacks.get(mapClass.getName());
/* 1165 */           if (fallback != null) {
/* 1166 */             mapClass = fallback;
/* 1167 */             type = (MapType)config.constructSpecializedType((JavaType)type, mapClass);
/*      */             
/* 1169 */             beanDesc = config.introspectForCreation((JavaType)type);
/*      */           } else {
/*      */             
/* 1172 */             if (type.getTypeHandler() == null) {
/* 1173 */               throw new IllegalArgumentException("Cannot find a deserializer for non-concrete Map type " + type);
/*      */             }
/* 1175 */             jsonDeserializer1 = AbstractDeserializer.constructForNonPOJO(beanDesc);
/*      */           } 
/*      */         } 
/* 1178 */         if (jsonDeserializer1 == null) {
/* 1179 */           ValueInstantiator inst = findValueInstantiator(ctxt, beanDesc);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1184 */           MapDeserializer md = new MapDeserializer((JavaType)type, inst, keyDes, contentDeser, contentTypeDeser);
/* 1185 */           JsonIgnoreProperties.Value ignorals = config.getDefaultPropertyIgnorals(Map.class, beanDesc.getClassInfo());
/*      */           
/* 1187 */           Set<String> ignored = (ignorals == null) ? null : ignorals.findIgnoredForDeserialization();
/*      */           
/* 1189 */           md.setIgnorableProperties(ignored);
/* 1190 */           mapDeserializer = md;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1194 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1195 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1196 */         jsonDeserializer1 = mod.modifyMapDeserializer(config, type, beanDesc, (JsonDeserializer<?>)mapDeserializer);
/*      */       }
/*      */     }
/* 1199 */     return jsonDeserializer1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext ctxt, MapLikeType type, BeanDescription beanDesc) throws JsonMappingException {
/* 1208 */     JavaType keyType = type.getKeyType();
/* 1209 */     JavaType contentType = type.getContentType();
/* 1210 */     DeserializationConfig config = ctxt.getConfig();
/*      */ 
/*      */ 
/*      */     
/* 1214 */     JsonDeserializer<Object> contentDeser = (JsonDeserializer<Object>)contentType.getValueHandler();
/*      */ 
/*      */     
/* 1217 */     KeyDeserializer keyDes = (KeyDeserializer)keyType.getValueHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1224 */     TypeDeserializer contentTypeDeser = (TypeDeserializer)contentType.getTypeHandler();
/*      */     
/* 1226 */     if (contentTypeDeser == null) {
/* 1227 */       contentTypeDeser = findTypeDeserializer(config, contentType);
/*      */     }
/* 1229 */     JsonDeserializer<?> deser = _findCustomMapLikeDeserializer(type, config, beanDesc, keyDes, contentTypeDeser, contentDeser);
/*      */     
/* 1231 */     if (deser != null)
/*      */     {
/* 1233 */       if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1234 */         for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1235 */           deser = mod.modifyMapLikeDeserializer(config, type, beanDesc, deser);
/*      */         }
/*      */       }
/*      */     }
/* 1239 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createEnumDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/*      */     EnumDeserializer enumDeserializer;
/*      */     JsonDeserializer<?> jsonDeserializer1;
/* 1256 */     DeserializationConfig config = ctxt.getConfig();
/* 1257 */     Class<?> enumClass = type.getRawClass();
/*      */     
/* 1259 */     JsonDeserializer<?> deser = _findCustomEnumDeserializer(enumClass, config, beanDesc);
/*      */     
/* 1261 */     if (deser == null) {
/* 1262 */       ValueInstantiator valueInstantiator = _constructDefaultValueInstantiator(ctxt, beanDesc);
/* 1263 */       SettableBeanProperty[] creatorProps = (valueInstantiator == null) ? null : valueInstantiator.getFromObjectArguments(ctxt.getConfig());
/*      */ 
/*      */       
/* 1266 */       for (AnnotatedMethod factory : beanDesc.getFactoryMethods()) {
/* 1267 */         if (_hasCreatorAnnotation(ctxt, (Annotated)factory)) {
/* 1268 */           if (factory.getParameterCount() == 0) {
/* 1269 */             deser = EnumDeserializer.deserializerForNoArgsCreator(config, enumClass, factory);
/*      */             break;
/*      */           } 
/* 1272 */           Class<?> returnType = factory.getRawReturnType();
/*      */           
/* 1274 */           if (returnType.isAssignableFrom(enumClass)) {
/* 1275 */             deser = EnumDeserializer.deserializerForCreator(config, enumClass, factory, valueInstantiator, creatorProps);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1282 */       if (deser == null) {
/* 1283 */         enumDeserializer = new EnumDeserializer(constructEnumResolver(enumClass, config, beanDesc.findJsonValueAccessor()), Boolean.valueOf(config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1290 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1291 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1292 */         jsonDeserializer1 = mod.modifyEnumDeserializer(config, type, beanDesc, (JsonDeserializer<?>)enumDeserializer);
/*      */       }
/*      */     }
/* 1295 */     return jsonDeserializer1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createTreeDeserializer(DeserializationConfig config, JavaType nodeType, BeanDescription beanDesc) throws JsonMappingException {
/* 1304 */     Class<? extends JsonNode> nodeClass = nodeType.getRawClass();
/*      */     
/* 1306 */     JsonDeserializer<?> custom = _findCustomTreeNodeDeserializer(nodeClass, config, beanDesc);
/*      */     
/* 1308 */     if (custom != null) {
/* 1309 */       return custom;
/*      */     }
/* 1311 */     return JsonNodeDeserializer.getDeserializer(nodeClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createReferenceDeserializer(DeserializationContext ctxt, ReferenceType type, BeanDescription beanDesc) throws JsonMappingException {
/* 1319 */     JavaType contentType = type.getContentType();
/*      */     
/* 1321 */     JsonDeserializer<Object> contentDeser = (JsonDeserializer<Object>)contentType.getValueHandler();
/* 1322 */     DeserializationConfig config = ctxt.getConfig();
/*      */     
/* 1324 */     TypeDeserializer contentTypeDeser = (TypeDeserializer)contentType.getTypeHandler();
/* 1325 */     if (contentTypeDeser == null) {
/* 1326 */       contentTypeDeser = findTypeDeserializer(config, contentType);
/*      */     }
/* 1328 */     JsonDeserializer<?> deser = _findCustomReferenceDeserializer(type, config, beanDesc, contentTypeDeser, contentDeser);
/*      */ 
/*      */     
/* 1331 */     if (deser == null)
/*      */     {
/* 1333 */       if (type.isTypeOrSubTypeOf(AtomicReference.class)) {
/* 1334 */         ValueInstantiator inst; Class<?> rawType = type.getRawClass();
/*      */         
/* 1336 */         if (rawType == AtomicReference.class) {
/* 1337 */           inst = null;
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1343 */           inst = findValueInstantiator(ctxt, beanDesc);
/*      */         } 
/* 1345 */         return (JsonDeserializer<?>)new AtomicReferenceDeserializer((JavaType)type, inst, contentTypeDeser, contentDeser);
/*      */       } 
/*      */     }
/* 1348 */     if (deser != null)
/*      */     {
/* 1350 */       if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1351 */         for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1352 */           deser = mod.modifyReferenceDeserializer(config, type, beanDesc, deser);
/*      */         }
/*      */       }
/*      */     }
/* 1356 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeDeserializer findTypeDeserializer(DeserializationConfig config, JavaType baseType) throws JsonMappingException {
/* 1370 */     BeanDescription bean = config.introspectClassAnnotations(baseType.getRawClass());
/* 1371 */     AnnotatedClass ac = bean.getClassInfo();
/* 1372 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 1373 */     TypeResolverBuilder<?> b = ai.findTypeResolver((MapperConfig)config, ac, baseType);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1378 */     Collection<NamedType> subtypes = null;
/* 1379 */     if (b == null) {
/* 1380 */       b = config.getDefaultTyper(baseType);
/* 1381 */       if (b == null) {
/* 1382 */         return null;
/*      */       }
/*      */     } else {
/* 1385 */       subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByTypeId((MapperConfig)config, ac);
/*      */     } 
/*      */ 
/*      */     
/* 1389 */     if (b.getDefaultImpl() == null && baseType.isAbstract()) {
/* 1390 */       JavaType defaultType = mapAbstractType(config, baseType);
/* 1391 */       if (defaultType != null && !defaultType.hasRawClass(baseType.getRawClass())) {
/* 1392 */         b = b.defaultImpl(defaultType.getRawClass());
/*      */       }
/*      */     } 
/* 1395 */     return b.buildTypeDeserializer(config, baseType, subtypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> findOptionalStdDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 1407 */     return OptionalHandlerFactory.instance.findDeserializer(type, ctxt.getConfig(), beanDesc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyDeserializer createKeyDeserializer(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
/* 1421 */     DeserializationConfig config = ctxt.getConfig();
/* 1422 */     KeyDeserializer deser = null;
/* 1423 */     if (this._factoryConfig.hasKeyDeserializers()) {
/* 1424 */       BeanDescription beanDesc = config.introspectClassAnnotations(type.getRawClass());
/* 1425 */       for (KeyDeserializers d : this._factoryConfig.keyDeserializers()) {
/* 1426 */         deser = d.findKeyDeserializer(type, config, beanDesc);
/* 1427 */         if (deser != null) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1433 */     if (deser == null) {
/* 1434 */       if (type.isEnumType()) {
/* 1435 */         deser = _createEnumKeyDeserializer(ctxt, type);
/*      */       } else {
/* 1437 */         deser = StdKeyDeserializers.findStringBasedKeyDeserializer(config, type);
/*      */       } 
/*      */     }
/*      */     
/* 1441 */     if (deser != null && 
/* 1442 */       this._factoryConfig.hasDeserializerModifiers()) {
/* 1443 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 1444 */         deser = mod.modifyKeyDeserializer(config, type, deser);
/*      */       }
/*      */     }
/*      */     
/* 1448 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
/* 1455 */     DeserializationConfig config = ctxt.getConfig();
/* 1456 */     Class<?> enumClass = type.getRawClass();
/*      */     
/* 1458 */     BeanDescription beanDesc = config.introspect(type);
/*      */     
/* 1460 */     KeyDeserializer des = findKeyDeserializerFromAnnotation(ctxt, (Annotated)beanDesc.getClassInfo());
/* 1461 */     if (des != null) {
/* 1462 */       return des;
/*      */     }
/*      */     
/* 1465 */     JsonDeserializer<?> custom = _findCustomEnumDeserializer(enumClass, config, beanDesc);
/* 1466 */     if (custom != null) {
/* 1467 */       return StdKeyDeserializers.constructDelegatingKeyDeserializer(config, type, custom);
/*      */     }
/* 1469 */     JsonDeserializer<?> valueDesForKey = findDeserializerFromAnnotation(ctxt, (Annotated)beanDesc.getClassInfo());
/* 1470 */     if (valueDesForKey != null) {
/* 1471 */       return StdKeyDeserializers.constructDelegatingKeyDeserializer(config, type, valueDesForKey);
/*      */     }
/*      */     
/* 1474 */     EnumResolver enumRes = constructEnumResolver(enumClass, config, beanDesc.findJsonValueAccessor());
/*      */     
/* 1476 */     for (AnnotatedMethod factory : beanDesc.getFactoryMethods()) {
/* 1477 */       if (_hasCreatorAnnotation(ctxt, (Annotated)factory)) {
/* 1478 */         int argCount = factory.getParameterCount();
/* 1479 */         if (argCount == 1) {
/* 1480 */           Class<?> returnType = factory.getRawReturnType();
/*      */           
/* 1482 */           if (returnType.isAssignableFrom(enumClass)) {
/*      */             
/* 1484 */             if (factory.getRawParameterType(0) != String.class) {
/* 1485 */               throw new IllegalArgumentException("Parameter #0 type for factory method (" + factory + ") not suitable, must be java.lang.String");
/*      */             }
/* 1487 */             if (config.canOverrideAccessModifiers()) {
/* 1488 */               ClassUtil.checkAndFixAccess(factory.getMember(), ctxt.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*      */             }
/*      */             
/* 1491 */             return StdKeyDeserializers.constructEnumKeyDeserializer(enumRes, factory);
/*      */           } 
/*      */         } 
/* 1494 */         throw new IllegalArgumentException("Unsuitable method (" + factory + ") decorated with @JsonCreator (for Enum type " + enumClass.getName() + ")");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1499 */     return StdKeyDeserializers.constructEnumKeyDeserializer(enumRes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig config, JavaType baseType, AnnotatedMember annotated) throws JsonMappingException {
/* 1525 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 1526 */     TypeResolverBuilder<?> b = ai.findPropertyTypeResolver((MapperConfig)config, annotated, baseType);
/*      */     
/* 1528 */     if (b == null) {
/* 1529 */       return findTypeDeserializer(config, baseType);
/*      */     }
/*      */     
/* 1532 */     Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByTypeId((MapperConfig)config, annotated, baseType);
/*      */     
/* 1534 */     return b.buildTypeDeserializer(config, baseType, subtypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig config, JavaType containerType, AnnotatedMember propertyEntity) throws JsonMappingException {
/* 1552 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 1553 */     TypeResolverBuilder<?> b = ai.findPropertyContentTypeResolver((MapperConfig)config, propertyEntity, containerType);
/* 1554 */     JavaType contentType = containerType.getContentType();
/*      */     
/* 1556 */     if (b == null) {
/* 1557 */       return findTypeDeserializer(config, contentType);
/*      */     }
/*      */     
/* 1560 */     Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByTypeId((MapperConfig)config, propertyEntity, contentType);
/*      */     
/* 1562 */     return b.buildTypeDeserializer(config, contentType, subtypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> findDefaultDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 1576 */     Class<?> rawType = type.getRawClass();
/*      */     
/* 1578 */     if (rawType == CLASS_OBJECT) {
/*      */       JavaType lt, mt;
/* 1580 */       DeserializationConfig config = ctxt.getConfig();
/*      */ 
/*      */       
/* 1583 */       if (this._factoryConfig.hasAbstractTypeResolvers()) {
/* 1584 */         lt = _findRemappedType(config, List.class);
/* 1585 */         mt = _findRemappedType(config, Map.class);
/*      */       } else {
/* 1587 */         lt = mt = null;
/*      */       } 
/* 1589 */       return (JsonDeserializer<?>)new UntypedObjectDeserializer(lt, mt);
/*      */     } 
/* 1591 */     if (rawType == CLASS_STRING || rawType == CLASS_CHAR_BUFFER) {
/* 1592 */       return (JsonDeserializer<?>)StringDeserializer.instance;
/*      */     }
/* 1594 */     if (rawType == CLASS_ITERABLE) {
/*      */       
/* 1596 */       TypeFactory tf = ctxt.getTypeFactory();
/* 1597 */       JavaType[] tps = tf.findTypeParameters(type, CLASS_ITERABLE);
/* 1598 */       JavaType elemType = (tps == null || tps.length != 1) ? TypeFactory.unknownType() : tps[0];
/* 1599 */       CollectionType ct = tf.constructCollectionType(Collection.class, elemType);
/*      */       
/* 1601 */       return createCollectionDeserializer(ctxt, ct, beanDesc);
/*      */     } 
/* 1603 */     if (rawType == CLASS_MAP_ENTRY) {
/*      */       
/* 1605 */       JavaType kt = type.containedTypeOrUnknown(0);
/* 1606 */       JavaType vt = type.containedTypeOrUnknown(1);
/* 1607 */       TypeDeserializer vts = (TypeDeserializer)vt.getTypeHandler();
/* 1608 */       if (vts == null) {
/* 1609 */         vts = findTypeDeserializer(ctxt.getConfig(), vt);
/*      */       }
/* 1611 */       JsonDeserializer<Object> valueDeser = (JsonDeserializer<Object>)vt.getValueHandler();
/* 1612 */       KeyDeserializer keyDes = (KeyDeserializer)kt.getValueHandler();
/* 1613 */       return (JsonDeserializer<?>)new MapEntryDeserializer(type, keyDes, valueDeser, vts);
/*      */     } 
/* 1615 */     String clsName = rawType.getName();
/* 1616 */     if (rawType.isPrimitive() || clsName.startsWith("java.")) {
/*      */       
/* 1618 */       JsonDeserializer<?> jsonDeserializer = NumberDeserializers.find(rawType, clsName);
/* 1619 */       if (jsonDeserializer == null) {
/* 1620 */         jsonDeserializer = DateDeserializers.find(rawType, clsName);
/*      */       }
/* 1622 */       if (jsonDeserializer != null) {
/* 1623 */         return jsonDeserializer;
/*      */       }
/*      */     } 
/*      */     
/* 1627 */     if (rawType == TokenBuffer.class) {
/* 1628 */       return (JsonDeserializer<?>)new TokenBufferDeserializer();
/*      */     }
/* 1630 */     JsonDeserializer<?> deser = findOptionalStdDeserializer(ctxt, type, beanDesc);
/* 1631 */     if (deser != null) {
/* 1632 */       return deser;
/*      */     }
/* 1634 */     return JdkDeserializers.find(rawType, clsName);
/*      */   }
/*      */   
/*      */   protected JavaType _findRemappedType(DeserializationConfig config, Class<?> rawType) throws JsonMappingException {
/* 1638 */     JavaType type = mapAbstractType(config, config.constructType(rawType));
/* 1639 */     return (type == null || type.hasRawClass(rawType)) ? null : type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends JsonNode> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
/* 1652 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1653 */       JsonDeserializer<?> deser = d.findTreeNodeDeserializer(type, config, beanDesc);
/* 1654 */       if (deser != null) {
/* 1655 */         return deser;
/*      */       }
/*      */     } 
/* 1658 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomReferenceDeserializer(ReferenceType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer contentTypeDeserializer, JsonDeserializer<?> contentDeserializer) throws JsonMappingException {
/* 1666 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1667 */       JsonDeserializer<?> deser = d.findReferenceDeserializer(type, config, beanDesc, contentTypeDeserializer, contentDeserializer);
/*      */       
/* 1669 */       if (deser != null) {
/* 1670 */         return deser;
/*      */       }
/*      */     } 
/* 1673 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> _findCustomBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
/* 1681 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1682 */       JsonDeserializer<?> deser = d.findBeanDeserializer(type, config, beanDesc);
/* 1683 */       if (deser != null) {
/* 1684 */         return (JsonDeserializer)deser;
/*      */       }
/*      */     } 
/* 1687 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomArrayDeserializer(ArrayType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
/* 1695 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1696 */       JsonDeserializer<?> deser = d.findArrayDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
/*      */       
/* 1698 */       if (deser != null) {
/* 1699 */         return deser;
/*      */       }
/*      */     } 
/* 1702 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
/* 1710 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1711 */       JsonDeserializer<?> deser = d.findCollectionDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
/*      */       
/* 1713 */       if (deser != null) {
/* 1714 */         return deser;
/*      */       }
/*      */     } 
/* 1717 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
/* 1725 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1726 */       JsonDeserializer<?> deser = d.findCollectionLikeDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
/*      */       
/* 1728 */       if (deser != null) {
/* 1729 */         return deser;
/*      */       }
/*      */     } 
/* 1732 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
/* 1739 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1740 */       JsonDeserializer<?> deser = d.findEnumDeserializer(type, config, beanDesc);
/* 1741 */       if (deser != null) {
/* 1742 */         return deser;
/*      */       }
/*      */     } 
/* 1745 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomMapDeserializer(MapType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
/* 1754 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1755 */       JsonDeserializer<?> deser = d.findMapDeserializer(type, config, beanDesc, keyDeserializer, elementTypeDeserializer, elementDeserializer);
/*      */       
/* 1757 */       if (deser != null) {
/* 1758 */         return deser;
/*      */       }
/*      */     } 
/* 1761 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
/* 1770 */     for (Deserializers d : this._factoryConfig.deserializers()) {
/* 1771 */       JsonDeserializer<?> deser = d.findMapLikeDeserializer(type, config, beanDesc, keyDeserializer, elementTypeDeserializer, elementDeserializer);
/*      */       
/* 1773 */       if (deser != null) {
/* 1774 */         return deser;
/*      */       }
/*      */     } 
/* 1777 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext ctxt, Annotated ann) throws JsonMappingException {
/* 1798 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 1799 */     if (intr != null) {
/* 1800 */       Object deserDef = intr.findDeserializer(ann);
/* 1801 */       if (deserDef != null) {
/* 1802 */         return ctxt.deserializerInstance(ann, deserDef);
/*      */       }
/*      */     } 
/* 1805 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyDeserializer findKeyDeserializerFromAnnotation(DeserializationContext ctxt, Annotated ann) throws JsonMappingException {
/* 1817 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 1818 */     if (intr != null) {
/* 1819 */       Object deserDef = intr.findKeyDeserializer(ann);
/* 1820 */       if (deserDef != null) {
/* 1821 */         return ctxt.keyDeserializerInstance(ann, deserDef);
/*      */       }
/*      */     } 
/* 1824 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> findContentDeserializerFromAnnotation(DeserializationContext ctxt, Annotated ann) throws JsonMappingException {
/* 1834 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 1835 */     if (intr != null) {
/* 1836 */       Object deserDef = intr.findContentDeserializer(ann);
/* 1837 */       if (deserDef != null) {
/* 1838 */         return ctxt.deserializerInstance(ann, deserDef);
/*      */       }
/*      */     } 
/* 1841 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType resolveMemberAndTypeAnnotations(DeserializationContext ctxt, AnnotatedMember member, JavaType type) throws JsonMappingException {
/*      */     MapLikeType mapLikeType;
/* 1857 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 1858 */     if (intr == null) {
/* 1859 */       return type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1865 */     if (type.isMapLikeType()) {
/* 1866 */       JavaType keyType = type.getKeyType();
/* 1867 */       if (keyType != null) {
/* 1868 */         Object kdDef = intr.findKeyDeserializer((Annotated)member);
/* 1869 */         KeyDeserializer kd = ctxt.keyDeserializerInstance((Annotated)member, kdDef);
/* 1870 */         if (kd != null) {
/* 1871 */           mapLikeType = ((MapLikeType)type).withKeyValueHandler(kd);
/* 1872 */           keyType = mapLikeType.getKeyType();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1877 */     if (mapLikeType.hasContentType()) {
/* 1878 */       Object cdDef = intr.findContentDeserializer((Annotated)member);
/* 1879 */       JsonDeserializer<?> cd = ctxt.deserializerInstance((Annotated)member, cdDef);
/* 1880 */       if (cd != null) {
/* 1881 */         javaType = mapLikeType.withContentValueHandler(cd);
/*      */       }
/* 1883 */       TypeDeserializer contentTypeDeser = findPropertyContentTypeDeserializer(ctxt.getConfig(), javaType, member);
/*      */       
/* 1885 */       if (contentTypeDeser != null) {
/* 1886 */         javaType = javaType.withContentTypeHandler(contentTypeDeser);
/*      */       }
/*      */     } 
/* 1889 */     TypeDeserializer valueTypeDeser = findPropertyTypeDeserializer(ctxt.getConfig(), javaType, member);
/*      */     
/* 1891 */     if (valueTypeDeser != null) {
/* 1892 */       javaType = javaType.withTypeHandler(valueTypeDeser);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1900 */     JavaType javaType = intr.refineDeserializationType((MapperConfig)ctxt.getConfig(), (Annotated)member, javaType);
/* 1901 */     return javaType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected EnumResolver constructEnumResolver(Class<?> enumClass, DeserializationConfig config, AnnotatedMember jsonValueAccessor) {
/* 1907 */     if (jsonValueAccessor != null) {
/* 1908 */       if (config.canOverrideAccessModifiers()) {
/* 1909 */         ClassUtil.checkAndFixAccess(jsonValueAccessor.getMember(), config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*      */       }
/*      */       
/* 1912 */       return EnumResolver.constructUnsafeUsingMethod(enumClass, jsonValueAccessor, config.getAnnotationIntrospector());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1917 */     return EnumResolver.constructUnsafe(enumClass, config.getAnnotationIntrospector());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasCreatorAnnotation(DeserializationContext ctxt, Annotated ann) {
/* 1925 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 1926 */     if (intr != null) {
/* 1927 */       JsonCreator.Mode mode = intr.findCreatorAnnotation((MapperConfig)ctxt.getConfig(), ann);
/* 1928 */       return (mode != null && mode != JsonCreator.Mode.DISABLED);
/*      */     } 
/* 1930 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   protected JavaType modifyTypeByAnnotation(DeserializationContext ctxt, Annotated a, JavaType type) throws JsonMappingException {
/* 1950 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 1951 */     if (intr == null) {
/* 1952 */       return type;
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
/* 1984 */     return intr.refineDeserializationType((MapperConfig)ctxt.getConfig(), a, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected JavaType resolveType(DeserializationContext ctxt, BeanDescription beanDesc, JavaType type, AnnotatedMember member) throws JsonMappingException {
/* 1995 */     return resolveMemberAndTypeAnnotations(ctxt, member, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected AnnotatedMethod _findJsonValueFor(DeserializationConfig config, JavaType enumType) {
/* 2004 */     if (enumType == null) {
/* 2005 */       return null;
/*      */     }
/* 2007 */     BeanDescription beanDesc = config.introspect(enumType);
/* 2008 */     return beanDesc.findJsonValueMethod();
/*      */   }
/*      */   
/*      */   protected abstract DeserializerFactory withConfig(DeserializerFactoryConfig paramDeserializerFactoryConfig);
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\BasicDeserializerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */