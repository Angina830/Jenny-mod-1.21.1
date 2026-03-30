/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.FieldProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.SetterlessProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.SimpleBeanPropertyDefinition;
/*     */ 
/*     */ public class BeanDeserializerFactory extends BasicDeserializerFactory implements Serializable {
/*  37 */   private static final Class<?>[] INIT_CAUSE_PARAMS = new Class[] { Throwable.class };
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   protected static final Set<String> DEFAULT_NO_DESER_CLASS_NAMES;
/*     */ 
/*     */   
/*     */   static {
/*  47 */     Set<String> s = new HashSet<>();
/*     */ 
/*     */     
/*  50 */     s.add("org.apache.commons.collections.functors.InvokerTransformer");
/*  51 */     s.add("org.apache.commons.collections.functors.InstantiateTransformer");
/*  52 */     s.add("org.apache.commons.collections4.functors.InvokerTransformer");
/*  53 */     s.add("org.apache.commons.collections4.functors.InstantiateTransformer");
/*  54 */     s.add("org.codehaus.groovy.runtime.ConvertedClosure");
/*  55 */     s.add("org.codehaus.groovy.runtime.MethodClosure");
/*  56 */     s.add("org.springframework.beans.factory.ObjectFactory");
/*  57 */     s.add("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl");
/*  58 */     s.add("org.apache.xalan.xsltc.trax.TemplatesImpl");
/*     */     
/*  60 */     s.add("com.sun.rowset.JdbcRowSetImpl");
/*  61 */     DEFAULT_NO_DESER_CLASS_NAMES = Collections.unmodifiableSet(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected Set<String> _cfgIllegalClassNames = DEFAULT_NO_DESER_CLASS_NAMES;
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
/*  81 */   public static final BeanDeserializerFactory instance = new BeanDeserializerFactory(new DeserializerFactoryConfig());
/*     */ 
/*     */   
/*     */   public BeanDeserializerFactory(DeserializerFactoryConfig config) {
/*  85 */     super(config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactory withConfig(DeserializerFactoryConfig config) {
/*  96 */     if (this._factoryConfig == config) {
/*  97 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     ClassUtil.verifyMustOverride(BeanDeserializerFactory.class, this, "withConfig");
/* 106 */     return new BeanDeserializerFactory(config);
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
/*     */   public JsonDeserializer<Object> createBeanDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 125 */     DeserializationConfig config = ctxt.getConfig();
/*     */     
/* 127 */     JsonDeserializer<Object> custom = _findCustomBeanDeserializer(type, config, beanDesc);
/* 128 */     if (custom != null) {
/* 129 */       return custom;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (type.isThrowable()) {
/* 136 */       return buildThrowableDeserializer(ctxt, type, beanDesc);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (type.isAbstract() && !type.isPrimitive() && !type.isEnumType()) {
/*     */       
/* 145 */       JavaType concreteType = materializeAbstractType(ctxt, type, beanDesc);
/* 146 */       if (concreteType != null) {
/*     */ 
/*     */ 
/*     */         
/* 150 */         beanDesc = config.introspect(concreteType);
/* 151 */         return buildBeanDeserializer(ctxt, concreteType, beanDesc);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 156 */     JsonDeserializer<Object> deser = (JsonDeserializer)findStdDeserializer(ctxt, type, beanDesc);
/* 157 */     if (deser != null) {
/* 158 */       return deser;
/*     */     }
/*     */ 
/*     */     
/* 162 */     if (!isPotentialBeanType(type.getRawClass())) {
/* 163 */       return null;
/*     */     }
/*     */     
/* 166 */     checkIllegalTypes(ctxt, type, beanDesc);
/*     */     
/* 168 */     return buildBeanDeserializer(ctxt, type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> createBuilderBasedDeserializer(DeserializationContext ctxt, JavaType valueType, BeanDescription beanDesc, Class<?> builderClass) throws JsonMappingException {
/* 177 */     JavaType builderType = ctxt.constructType(builderClass);
/* 178 */     BeanDescription builderDesc = ctxt.getConfig().introspectForBuilder(builderType);
/* 179 */     return buildBuilderBasedDeserializer(ctxt, valueType, builderDesc);
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
/*     */   protected JsonDeserializer<?> findStdDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 192 */     JsonDeserializer<?> deser = findDefaultDeserializer(ctxt, type, beanDesc);
/*     */     
/* 194 */     if (deser != null && 
/* 195 */       this._factoryConfig.hasDeserializerModifiers()) {
/* 196 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 197 */         deser = mod.modifyDeserializer(ctxt.getConfig(), beanDesc, deser);
/*     */       }
/*     */     }
/*     */     
/* 201 */     return deser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType materializeAbstractType(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 209 */     for (AbstractTypeResolver r : this._factoryConfig.abstractTypeResolvers()) {
/* 210 */       JavaType concrete = r.resolveAbstractType(ctxt.getConfig(), beanDesc);
/* 211 */       if (concrete != null) {
/* 212 */         return concrete;
/*     */       }
/*     */     } 
/* 215 */     return null;
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
/*     */   public JsonDeserializer<Object> buildBeanDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/*     */     ValueInstantiator valueInstantiator;
/*     */     JsonDeserializer<?> deserializer;
/*     */     try {
/* 245 */       valueInstantiator = findValueInstantiator(ctxt, beanDesc);
/* 246 */     } catch (NoClassDefFoundError error) {
/* 247 */       return (JsonDeserializer<Object>)new ErrorThrowingDeserializer(error);
/* 248 */     } catch (IllegalArgumentException e) {
/*     */ 
/*     */ 
/*     */       
/* 252 */       throw InvalidDefinitionException.from(ctxt.getParser(), e.getMessage(), beanDesc, null);
/*     */     } 
/*     */     
/* 255 */     BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(ctxt, beanDesc);
/* 256 */     builder.setValueInstantiator(valueInstantiator);
/*     */     
/* 258 */     addBeanProps(ctxt, beanDesc, builder);
/* 259 */     addObjectIdReader(ctxt, beanDesc, builder);
/*     */ 
/*     */     
/* 262 */     addBackReferenceProperties(ctxt, beanDesc, builder);
/* 263 */     addInjectables(ctxt, beanDesc, builder);
/*     */     
/* 265 */     DeserializationConfig config = ctxt.getConfig();
/* 266 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 267 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 268 */         builder = mod.updateBuilder(config, beanDesc, builder);
/*     */       }
/*     */     }
/*     */     
/* 272 */     if (type.isAbstract() && !valueInstantiator.canInstantiate()) {
/* 273 */       deserializer = builder.buildAbstract();
/*     */     } else {
/* 275 */       deserializer = builder.build();
/*     */     } 
/*     */ 
/*     */     
/* 279 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 280 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 281 */         deserializer = mod.modifyDeserializer(config, beanDesc, deserializer);
/*     */       }
/*     */     }
/* 284 */     return (JsonDeserializer)deserializer;
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
/*     */   protected JsonDeserializer<Object> buildBuilderBasedDeserializer(DeserializationContext ctxt, JavaType valueType, BeanDescription builderDesc) throws JsonMappingException {
/*     */     ValueInstantiator valueInstantiator;
/*     */     try {
/* 302 */       valueInstantiator = findValueInstantiator(ctxt, builderDesc);
/* 303 */     } catch (NoClassDefFoundError error) {
/* 304 */       return (JsonDeserializer<Object>)new ErrorThrowingDeserializer(error);
/* 305 */     } catch (IllegalArgumentException e) {
/*     */ 
/*     */ 
/*     */       
/* 309 */       throw InvalidDefinitionException.from(ctxt.getParser(), e.getMessage(), builderDesc, null);
/*     */     } 
/*     */     
/* 312 */     DeserializationConfig config = ctxt.getConfig();
/* 313 */     BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(ctxt, builderDesc);
/* 314 */     builder.setValueInstantiator(valueInstantiator);
/*     */     
/* 316 */     addBeanProps(ctxt, builderDesc, builder);
/* 317 */     addObjectIdReader(ctxt, builderDesc, builder);
/*     */ 
/*     */     
/* 320 */     addBackReferenceProperties(ctxt, builderDesc, builder);
/* 321 */     addInjectables(ctxt, builderDesc, builder);
/*     */     
/* 323 */     JsonPOJOBuilder.Value builderConfig = builderDesc.findPOJOBuilderConfig();
/* 324 */     String buildMethodName = (builderConfig == null) ? "build" : builderConfig.buildMethodName;
/*     */ 
/*     */ 
/*     */     
/* 328 */     AnnotatedMethod buildMethod = builderDesc.findMethod(buildMethodName, null);
/* 329 */     if (buildMethod != null && 
/* 330 */       config.canOverrideAccessModifiers()) {
/* 331 */       ClassUtil.checkAndFixAccess(buildMethod.getMember(), config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */     }
/*     */     
/* 334 */     builder.setPOJOBuilder(buildMethod, builderConfig);
/*     */     
/* 336 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 337 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 338 */         builder = mod.updateBuilder(config, builderDesc, builder);
/*     */       }
/*     */     }
/* 341 */     JsonDeserializer<?> deserializer = builder.buildBuilderBased(valueType, buildMethodName);
/*     */ 
/*     */ 
/*     */     
/* 345 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 346 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 347 */         deserializer = mod.modifyDeserializer(config, builderDesc, deserializer);
/*     */       }
/*     */     }
/* 350 */     return (JsonDeserializer)deserializer;
/*     */   }
/*     */   
/*     */   protected void addObjectIdReader(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
/*     */     JavaType idType;
/*     */     SettableBeanProperty idProp;
/*     */     ObjectIdGenerator<?> gen;
/* 357 */     ObjectIdInfo objectIdInfo = beanDesc.getObjectIdInfo();
/* 358 */     if (objectIdInfo == null) {
/*     */       return;
/*     */     }
/* 361 */     Class<?> implClass = objectIdInfo.getGeneratorType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     ObjectIdResolver resolver = ctxt.objectIdResolverInstance((Annotated)beanDesc.getClassInfo(), objectIdInfo);
/*     */ 
/*     */     
/* 369 */     if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
/* 370 */       PropertyName propName = objectIdInfo.getPropertyName();
/* 371 */       idProp = builder.findProperty(propName);
/* 372 */       if (idProp == null) {
/* 373 */         throw new IllegalArgumentException("Invalid Object Id definition for " + beanDesc.getBeanClass().getName() + ": cannot find property with name '" + propName + "'");
/*     */       }
/*     */       
/* 376 */       idType = idProp.getType();
/* 377 */       PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
/*     */     } else {
/* 379 */       JavaType type = ctxt.constructType(implClass);
/* 380 */       idType = ctxt.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
/* 381 */       idProp = null;
/* 382 */       gen = ctxt.objectIdGeneratorInstance((Annotated)beanDesc.getClassInfo(), objectIdInfo);
/*     */     } 
/*     */     
/* 385 */     JsonDeserializer<?> deser = ctxt.findRootValueDeserializer(idType);
/* 386 */     builder.setObjectIdReader(ObjectIdReader.construct(idType, objectIdInfo.getPropertyName(), gen, deser, idProp, resolver));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> buildThrowableDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/*     */     ThrowableDeserializer throwableDeserializer;
/*     */     JsonDeserializer<?> jsonDeserializer1;
/* 395 */     DeserializationConfig config = ctxt.getConfig();
/*     */     
/* 397 */     BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(ctxt, beanDesc);
/* 398 */     builder.setValueInstantiator(findValueInstantiator(ctxt, beanDesc));
/*     */     
/* 400 */     addBeanProps(ctxt, beanDesc, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     AnnotatedMethod am = beanDesc.findMethod("initCause", INIT_CAUSE_PARAMS);
/* 408 */     if (am != null) {
/* 409 */       SimpleBeanPropertyDefinition propDef = SimpleBeanPropertyDefinition.construct((MapperConfig)ctxt.getConfig(), (AnnotatedMember)am, new PropertyName("cause"));
/*     */       
/* 411 */       SettableBeanProperty prop = constructSettableProperty(ctxt, beanDesc, (BeanPropertyDefinition)propDef, am.getParameterType(0));
/*     */       
/* 413 */       if (prop != null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 418 */         builder.addOrReplaceProperty(prop, true);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 423 */     builder.addIgnorable("localizedMessage");
/*     */     
/* 425 */     builder.addIgnorable("suppressed");
/*     */ 
/*     */ 
/*     */     
/* 429 */     builder.addIgnorable("message");
/*     */ 
/*     */     
/* 432 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 433 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 434 */         builder = mod.updateBuilder(config, beanDesc, builder);
/*     */       }
/*     */     }
/* 437 */     JsonDeserializer<?> deserializer = builder.build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 442 */     if (deserializer instanceof BeanDeserializer) {
/* 443 */       throwableDeserializer = new ThrowableDeserializer((BeanDeserializer)deserializer);
/*     */     }
/*     */ 
/*     */     
/* 447 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 448 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 449 */         jsonDeserializer1 = mod.modifyDeserializer(config, beanDesc, (JsonDeserializer<?>)throwableDeserializer);
/*     */       }
/*     */     }
/* 452 */     return (JsonDeserializer)jsonDeserializer1;
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
/*     */   protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext ctxt, BeanDescription beanDesc) {
/* 469 */     return new BeanDeserializerBuilder(beanDesc, ctxt);
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
/*     */   protected void addBeanProps(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
/*     */     Set<String> ignored;
/* 483 */     boolean isConcrete = !beanDesc.getType().isAbstract();
/* 484 */     SettableBeanProperty[] creatorProps = isConcrete ? builder.getValueInstantiator().getFromObjectArguments(ctxt.getConfig()) : null;
/*     */ 
/*     */     
/* 487 */     boolean hasCreatorProps = (creatorProps != null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 493 */     JsonIgnoreProperties.Value ignorals = ctxt.getConfig().getDefaultPropertyIgnorals(beanDesc.getBeanClass(), beanDesc.getClassInfo());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     if (ignorals != null) {
/* 499 */       boolean ignoreAny = ignorals.getIgnoreUnknown();
/* 500 */       builder.setIgnoreUnknownProperties(ignoreAny);
/*     */       
/* 502 */       ignored = ignorals.findIgnoredForDeserialization();
/* 503 */       for (String propName : ignored) {
/* 504 */         builder.addIgnorable(propName);
/*     */       }
/*     */     } else {
/* 507 */       ignored = Collections.emptySet();
/*     */     } 
/*     */ 
/*     */     
/* 511 */     AnnotatedMember anySetter = beanDesc.findAnySetterAccessor();
/* 512 */     if (anySetter != null) {
/* 513 */       builder.setAnySetter(constructAnySetter(ctxt, beanDesc, anySetter));
/*     */     } else {
/* 515 */       Collection<String> ignored2 = beanDesc.getIgnoredPropertyNames();
/* 516 */       if (ignored2 != null) {
/* 517 */         for (String propName : ignored2)
/*     */         {
/*     */           
/* 520 */           builder.addIgnorable(propName);
/*     */         }
/*     */       }
/*     */     } 
/* 524 */     boolean useGettersAsSetters = (ctxt.isEnabled(MapperFeature.USE_GETTERS_AS_SETTERS) && ctxt.isEnabled(MapperFeature.AUTO_DETECT_GETTERS));
/*     */ 
/*     */ 
/*     */     
/* 528 */     List<BeanPropertyDefinition> propDefs = filterBeanProps(ctxt, beanDesc, builder, beanDesc.findProperties(), ignored);
/*     */ 
/*     */ 
/*     */     
/* 532 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 533 */       for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
/* 534 */         propDefs = mod.updateProperties(ctxt.getConfig(), beanDesc, propDefs);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 539 */     for (BeanPropertyDefinition propDef : propDefs) {
/* 540 */       SettableBeanProperty prop = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 546 */       if (propDef.hasSetter()) {
/* 547 */         AnnotatedMethod setter = propDef.getSetter();
/* 548 */         JavaType propertyType = setter.getParameterType(0);
/* 549 */         prop = constructSettableProperty(ctxt, beanDesc, propDef, propertyType);
/* 550 */       } else if (propDef.hasField()) {
/* 551 */         AnnotatedField field = propDef.getField();
/* 552 */         JavaType propertyType = field.getType();
/* 553 */         prop = constructSettableProperty(ctxt, beanDesc, propDef, propertyType);
/*     */       } else {
/*     */         
/* 556 */         AnnotatedMethod getter = propDef.getGetter();
/* 557 */         if (getter != null) {
/* 558 */           if (useGettersAsSetters && _isSetterlessType(getter.getRawType())) {
/* 559 */             prop = constructSetterlessProperty(ctxt, beanDesc, propDef);
/* 560 */           } else if (!propDef.hasConstructorParameter()) {
/* 561 */             PropertyMetadata md = propDef.getMetadata();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 566 */             if (md.getMergeInfo() != null) {
/* 567 */               prop = constructSetterlessProperty(ctxt, beanDesc, propDef);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 575 */       if (hasCreatorProps && propDef.hasConstructorParameter()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 581 */         String name = propDef.getName();
/* 582 */         CreatorProperty cprop = null;
/* 583 */         if (creatorProps != null) {
/* 584 */           for (SettableBeanProperty cp : creatorProps) {
/* 585 */             if (name.equals(cp.getName()) && cp instanceof CreatorProperty) {
/* 586 */               cprop = (CreatorProperty)cp;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 591 */         if (cprop == null) {
/* 592 */           List<String> n = new ArrayList<>();
/* 593 */           for (SettableBeanProperty cp : creatorProps) {
/* 594 */             n.add(cp.getName());
/*     */           }
/* 596 */           ctxt.reportBadPropertyDefinition(beanDesc, propDef, "Could not find creator property with name '%s' (known Creator properties: %s)", new Object[] { name, n });
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 601 */         if (prop != null) {
/* 602 */           cprop.setFallbackSetter(prop);
/*     */         }
/* 604 */         Class<?>[] views = propDef.findViews();
/* 605 */         if (views == null) {
/* 606 */           views = beanDesc.findDefaultViews();
/*     */         }
/* 608 */         cprop.setViews(views);
/* 609 */         builder.addCreatorProperty(cprop);
/*     */         continue;
/*     */       } 
/* 612 */       if (prop != null) {
/*     */         
/* 614 */         Class<?>[] views = propDef.findViews();
/* 615 */         if (views == null) {
/* 616 */           views = beanDesc.findDefaultViews();
/*     */         }
/* 618 */         prop.setViews(views);
/* 619 */         builder.addProperty(prop);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _isSetterlessType(Class<?> rawType) {
/* 628 */     return (Collection.class.isAssignableFrom(rawType) || Map.class.isAssignableFrom(rawType));
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
/*     */   protected List<BeanPropertyDefinition> filterBeanProps(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder, List<BeanPropertyDefinition> propDefsIn, Set<String> ignored) throws JsonMappingException {
/* 644 */     ArrayList<BeanPropertyDefinition> result = new ArrayList<>(Math.max(4, propDefsIn.size()));
/*     */     
/* 646 */     HashMap<Class<?>, Boolean> ignoredTypes = new HashMap<>();
/*     */     
/* 648 */     for (BeanPropertyDefinition property : propDefsIn) {
/* 649 */       String name = property.getName();
/* 650 */       if (ignored.contains(name)) {
/*     */         continue;
/*     */       }
/* 653 */       if (!property.hasConstructorParameter()) {
/* 654 */         Class<?> rawPropertyType = property.getRawPrimaryType();
/*     */         
/* 656 */         if (rawPropertyType != null && isIgnorableType(ctxt.getConfig(), property, rawPropertyType, ignoredTypes)) {
/*     */ 
/*     */           
/* 659 */           builder.addIgnorable(name);
/*     */           continue;
/*     */         } 
/*     */       } 
/* 663 */       result.add(property);
/*     */     } 
/* 665 */     return result;
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
/*     */   protected void addBackReferenceProperties(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
/* 679 */     List<BeanPropertyDefinition> refProps = beanDesc.findBackReferences();
/* 680 */     if (refProps != null) {
/* 681 */       for (BeanPropertyDefinition refProp : refProps) {
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
/* 698 */         String refName = refProp.findReferenceName();
/* 699 */         builder.addBackReferenceProperty(refName, constructSettableProperty(ctxt, beanDesc, refProp, refProp.getPrimaryType()));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void addReferenceProperties(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
/* 710 */     addBackReferenceProperties(ctxt, beanDesc, builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addInjectables(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
/* 721 */     Map<Object, AnnotatedMember> raw = beanDesc.findInjectables();
/* 722 */     if (raw != null) {
/* 723 */       for (Map.Entry<Object, AnnotatedMember> entry : raw.entrySet()) {
/* 724 */         AnnotatedMember m = entry.getValue();
/* 725 */         builder.addInjectable(PropertyName.construct(m.getName()), m.getType(), beanDesc.getClassAnnotations(), m, entry.getKey());
/*     */       } 
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
/*     */ 
/*     */   
/*     */   protected SettableAnyProperty constructAnySetter(DeserializationContext ctxt, BeanDescription beanDesc, AnnotatedMember mutator) throws JsonMappingException {
/*     */     BeanProperty.Std std;
/*     */     JavaType keyType, valueType;
/* 750 */     if (mutator instanceof AnnotatedMethod) {
/*     */       
/* 752 */       AnnotatedMethod am = (AnnotatedMethod)mutator;
/* 753 */       keyType = am.getParameterType(0);
/* 754 */       valueType = am.getParameterType(1);
/* 755 */       valueType = resolveMemberAndTypeAnnotations(ctxt, mutator, valueType);
/* 756 */       std = new BeanProperty.Std(PropertyName.construct(mutator.getName()), valueType, null, mutator, PropertyMetadata.STD_OPTIONAL);
/*     */ 
/*     */     
/*     */     }
/* 760 */     else if (mutator instanceof AnnotatedField) {
/* 761 */       AnnotatedField af = (AnnotatedField)mutator;
/*     */       
/* 763 */       JavaType mapType = af.getType();
/* 764 */       mapType = resolveMemberAndTypeAnnotations(ctxt, mutator, mapType);
/* 765 */       keyType = mapType.getKeyType();
/* 766 */       valueType = mapType.getContentType();
/* 767 */       std = new BeanProperty.Std(PropertyName.construct(mutator.getName()), mapType, null, mutator, PropertyMetadata.STD_OPTIONAL);
/*     */     } else {
/*     */       
/* 770 */       return (SettableAnyProperty)ctxt.reportBadDefinition(beanDesc.getType(), String.format("Unrecognized mutator type for any setter: %s", new Object[] { mutator.getClass() }));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 775 */     KeyDeserializer keyDeser = findKeyDeserializerFromAnnotation(ctxt, (Annotated)mutator);
/* 776 */     if (keyDeser == null) {
/* 777 */       keyDeser = (KeyDeserializer)keyType.getValueHandler();
/*     */     }
/* 779 */     if (keyDeser == null) {
/* 780 */       keyDeser = ctxt.findKeyDeserializer(keyType, (BeanProperty)std);
/*     */     }
/* 782 */     else if (keyDeser instanceof ContextualKeyDeserializer) {
/* 783 */       keyDeser = ((ContextualKeyDeserializer)keyDeser).createContextual(ctxt, (BeanProperty)std);
/*     */     } 
/*     */ 
/*     */     
/* 787 */     JsonDeserializer<Object> deser = findContentDeserializerFromAnnotation(ctxt, (Annotated)mutator);
/* 788 */     if (deser == null) {
/* 789 */       deser = (JsonDeserializer<Object>)valueType.getValueHandler();
/*     */     }
/* 791 */     if (deser != null)
/*     */     {
/* 793 */       deser = ctxt.handlePrimaryContextualization(deser, (BeanProperty)std, valueType);
/*     */     }
/* 795 */     TypeDeserializer typeDeser = (TypeDeserializer)valueType.getTypeHandler();
/* 796 */     return new SettableAnyProperty((BeanProperty)std, mutator, valueType, keyDeser, deser, typeDeser);
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
/*     */   protected SettableBeanProperty constructSettableProperty(DeserializationContext ctxt, BeanDescription beanDesc, BeanPropertyDefinition propDef, JavaType propType0) throws JsonMappingException {
/*     */     FieldProperty fieldProperty;
/*     */     SettableBeanProperty settableBeanProperty;
/* 813 */     AnnotatedMember mutator = propDef.getNonConstructorMutator();
/*     */ 
/*     */ 
/*     */     
/* 817 */     if (mutator == null) {
/* 818 */       ctxt.reportBadPropertyDefinition(beanDesc, propDef, "No non-constructor mutator available", new Object[0]);
/*     */     }
/* 820 */     JavaType type = resolveMemberAndTypeAnnotations(ctxt, mutator, propType0);
/*     */     
/* 822 */     TypeDeserializer typeDeser = (TypeDeserializer)type.getTypeHandler();
/*     */     
/* 824 */     if (mutator instanceof AnnotatedMethod) {
/* 825 */       MethodProperty methodProperty = new MethodProperty(propDef, type, typeDeser, beanDesc.getClassAnnotations(), (AnnotatedMethod)mutator);
/*     */     }
/*     */     else {
/*     */       
/* 829 */       fieldProperty = new FieldProperty(propDef, type, typeDeser, beanDesc.getClassAnnotations(), (AnnotatedField)mutator);
/*     */     } 
/*     */     
/* 832 */     JsonDeserializer<?> deser = findDeserializerFromAnnotation(ctxt, (Annotated)mutator);
/* 833 */     if (deser == null) {
/* 834 */       deser = (JsonDeserializer)type.getValueHandler();
/*     */     }
/* 836 */     if (deser != null) {
/* 837 */       deser = ctxt.handlePrimaryContextualization(deser, (BeanProperty)fieldProperty, type);
/* 838 */       settableBeanProperty = fieldProperty.withValueDeserializer(deser);
/*     */     } 
/*     */     
/* 841 */     AnnotationIntrospector.ReferenceProperty ref = propDef.findReferenceType();
/* 842 */     if (ref != null && ref.isManagedReference()) {
/* 843 */       settableBeanProperty.setManagedReferenceName(ref.getName());
/*     */     }
/* 845 */     ObjectIdInfo objectIdInfo = propDef.findObjectIdInfo();
/* 846 */     if (objectIdInfo != null) {
/* 847 */       settableBeanProperty.setObjectIdInfo(objectIdInfo);
/*     */     }
/* 849 */     return settableBeanProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty constructSetterlessProperty(DeserializationContext ctxt, BeanDescription beanDesc, BeanPropertyDefinition propDef) throws JsonMappingException {
/*     */     SettableBeanProperty settableBeanProperty;
/* 860 */     AnnotatedMethod getter = propDef.getGetter();
/* 861 */     JavaType type = resolveMemberAndTypeAnnotations(ctxt, (AnnotatedMember)getter, getter.getType());
/* 862 */     TypeDeserializer typeDeser = (TypeDeserializer)type.getTypeHandler();
/* 863 */     SetterlessProperty setterlessProperty = new SetterlessProperty(propDef, type, typeDeser, beanDesc.getClassAnnotations(), getter);
/*     */     
/* 865 */     JsonDeserializer<?> deser = findDeserializerFromAnnotation(ctxt, (Annotated)getter);
/* 866 */     if (deser == null) {
/* 867 */       deser = (JsonDeserializer)type.getValueHandler();
/*     */     }
/* 869 */     if (deser != null) {
/* 870 */       deser = ctxt.handlePrimaryContextualization(deser, (BeanProperty)setterlessProperty, type);
/* 871 */       settableBeanProperty = setterlessProperty.withValueDeserializer(deser);
/*     */     } 
/* 873 */     return settableBeanProperty;
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
/*     */   protected boolean isPotentialBeanType(Class<?> type) {
/* 892 */     String typeStr = ClassUtil.canBeABeanType(type);
/* 893 */     if (typeStr != null) {
/* 894 */       throw new IllegalArgumentException("Cannot deserialize Class " + type.getName() + " (of type " + typeStr + ") as a Bean");
/*     */     }
/* 896 */     if (ClassUtil.isProxyType(type)) {
/* 897 */       throw new IllegalArgumentException("Cannot deserialize Proxy class " + type.getName() + " as a Bean");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 902 */     typeStr = ClassUtil.isLocalType(type, true);
/* 903 */     if (typeStr != null) {
/* 904 */       throw new IllegalArgumentException("Cannot deserialize Class " + type.getName() + " (of type " + typeStr + ") as a Bean");
/*     */     }
/* 906 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isIgnorableType(DeserializationConfig config, BeanPropertyDefinition propDef, Class<?> type, Map<Class<?>, Boolean> ignoredTypes) {
/* 916 */     Boolean status = ignoredTypes.get(type);
/* 917 */     if (status != null) {
/* 918 */       return status.booleanValue();
/*     */     }
/*     */     
/* 921 */     if (type == String.class || type.isPrimitive()) {
/* 922 */       status = Boolean.FALSE;
/*     */     } else {
/*     */       
/* 925 */       status = config.getConfigOverride(type).getIsIgnoredType();
/* 926 */       if (status == null) {
/* 927 */         BeanDescription desc = config.introspectClassAnnotations(type);
/* 928 */         status = config.getAnnotationIntrospector().isIgnorableType(desc.getClassInfo());
/*     */         
/* 930 */         if (status == null) {
/* 931 */           status = Boolean.FALSE;
/*     */         }
/*     */       } 
/*     */     } 
/* 935 */     ignoredTypes.put(type, status);
/* 936 */     return status.booleanValue();
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
/*     */   protected void checkIllegalTypes(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 948 */     String full = type.getRawClass().getName();
/*     */     
/* 950 */     if (this._cfgIllegalClassNames.contains(full))
/* 951 */       ctxt.reportBadTypeDefinition(beanDesc, "Illegal type (%s) to deserialize: prevented for security reasons", new Object[] { full }); 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\BeanDeserializerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */