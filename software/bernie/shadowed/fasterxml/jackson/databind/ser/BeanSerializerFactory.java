/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerators;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.FilteredBeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.MapSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ReferenceType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
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
/*     */ public class BeanSerializerFactory
/*     */   extends BasicSerializerFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  64 */   public static final BeanSerializerFactory instance = new BeanSerializerFactory(null);
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
/*     */   protected BeanSerializerFactory(SerializerFactoryConfig config) {
/*  77 */     super(config);
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
/*     */   public SerializerFactory withConfig(SerializerFactoryConfig config) {
/*  89 */     if (this._factoryConfig == config) {
/*  90 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (getClass() != BeanSerializerFactory.class) {
/*  99 */       throw new IllegalStateException("Subtype of BeanSerializerFactory (" + getClass().getName() + ") has not properly overridden method 'withAdditionalSerializers': cannot instantiate subtype with " + "additional serializer definitions");
/*     */     }
/*     */ 
/*     */     
/* 103 */     return new BeanSerializerFactory(config);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<Serializers> customSerializers() {
/* 108 */     return this._factoryConfig.serializers();
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
/*     */   public JsonSerializer<Object> createSerializer(SerializerProvider prov, JavaType origType) throws JsonMappingException {
/*     */     boolean staticTyping;
/*     */     JavaType type;
/* 134 */     SerializationConfig config = prov.getConfig();
/* 135 */     BeanDescription beanDesc = config.introspect(origType);
/* 136 */     JsonSerializer<?> ser = findSerializerFromAnnotation(prov, (Annotated)beanDesc.getClassInfo());
/* 137 */     if (ser != null) {
/* 138 */       return (JsonSerializer)ser;
/*     */     }
/*     */ 
/*     */     
/* 142 */     AnnotationIntrospector intr = config.getAnnotationIntrospector();
/*     */ 
/*     */     
/* 145 */     if (intr == null) {
/* 146 */       type = origType;
/*     */     } else {
/*     */       try {
/* 149 */         type = intr.refineSerializationType((MapperConfig)config, (Annotated)beanDesc.getClassInfo(), origType);
/* 150 */       } catch (JsonMappingException e) {
/* 151 */         return (JsonSerializer<Object>)prov.reportBadTypeDefinition(beanDesc, e.getMessage(), new Object[0]);
/*     */       } 
/*     */     } 
/* 154 */     if (type == origType) {
/* 155 */       staticTyping = false;
/*     */     } else {
/* 157 */       staticTyping = true;
/* 158 */       if (!type.hasRawClass(origType.getRawClass())) {
/* 159 */         beanDesc = config.introspect(type);
/*     */       }
/*     */     } 
/*     */     
/* 163 */     Converter<Object, Object> conv = beanDesc.findSerializationConverter();
/* 164 */     if (conv == null) {
/* 165 */       return (JsonSerializer)_createSerializer2(prov, type, beanDesc, staticTyping);
/*     */     }
/* 167 */     JavaType delegateType = conv.getOutputType(prov.getTypeFactory());
/*     */ 
/*     */     
/* 170 */     if (!delegateType.hasRawClass(type.getRawClass())) {
/* 171 */       beanDesc = config.introspect(delegateType);
/*     */       
/* 173 */       ser = findSerializerFromAnnotation(prov, (Annotated)beanDesc.getClassInfo());
/*     */     } 
/*     */     
/* 176 */     if (ser == null && !delegateType.isJavaLangObject()) {
/* 177 */       ser = _createSerializer2(prov, delegateType, beanDesc, true);
/*     */     }
/* 179 */     return (JsonSerializer<Object>)new StdDelegatingSerializer(conv, delegateType, ser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonSerializer<?> _createSerializer2(SerializerProvider prov, JavaType type, BeanDescription beanDesc, boolean staticTyping) throws JsonMappingException {
/* 186 */     JsonSerializer<?> ser = null;
/* 187 */     SerializationConfig config = prov.getConfig();
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (type.isContainerType()) {
/* 192 */       if (!staticTyping) {
/* 193 */         staticTyping = usesStaticTyping(config, beanDesc, null);
/*     */       }
/*     */       
/* 196 */       ser = buildContainerSerializer(prov, type, beanDesc, staticTyping);
/*     */       
/* 198 */       if (ser != null) {
/* 199 */         return ser;
/*     */       }
/*     */     } else {
/* 202 */       if (type.isReferenceType()) {
/* 203 */         ser = findReferenceSerializer(prov, (ReferenceType)type, beanDesc, staticTyping);
/*     */       } else {
/*     */         
/* 206 */         for (Serializers serializers : customSerializers()) {
/* 207 */           ser = serializers.findSerializer(config, type, beanDesc);
/* 208 */           if (ser != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 215 */       if (ser == null) {
/* 216 */         ser = findSerializerByAnnotations(prov, type, beanDesc);
/*     */       }
/*     */     } 
/*     */     
/* 220 */     if (ser == null) {
/*     */ 
/*     */ 
/*     */       
/* 224 */       ser = findSerializerByLookup(type, config, beanDesc, staticTyping);
/* 225 */       if (ser == null) {
/* 226 */         ser = findSerializerByPrimaryType(prov, type, beanDesc, staticTyping);
/* 227 */         if (ser == null) {
/*     */ 
/*     */ 
/*     */           
/* 231 */           ser = findBeanSerializer(prov, type, beanDesc);
/*     */           
/* 233 */           if (ser == null) {
/* 234 */             ser = findSerializerByAddonType(config, type, beanDesc, staticTyping);
/*     */ 
/*     */ 
/*     */             
/* 238 */             if (ser == null) {
/* 239 */               ser = prov.getUnknownTypeSerializer(beanDesc.getBeanClass());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 245 */     if (ser != null)
/*     */     {
/* 247 */       if (this._factoryConfig.hasSerializerModifiers()) {
/* 248 */         for (BeanSerializerModifier mod : this._factoryConfig.serializerModifiers()) {
/* 249 */           ser = mod.modifySerializer(config, beanDesc, ser);
/*     */         }
/*     */       }
/*     */     }
/* 253 */     return ser;
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
/*     */   public JsonSerializer<Object> findBeanSerializer(SerializerProvider prov, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
/* 272 */     if (!isPotentialBeanType(type.getRawClass()))
/*     */     {
/*     */       
/* 275 */       if (!type.isEnumType()) {
/* 276 */         return null;
/*     */       }
/*     */     }
/* 279 */     return constructBeanSerializer(prov, beanDesc);
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
/*     */   public TypeSerializer findPropertyTypeSerializer(JavaType baseType, SerializationConfig config, AnnotatedMember accessor) throws JsonMappingException {
/*     */     TypeSerializer typeSer;
/* 296 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 297 */     TypeResolverBuilder<?> b = ai.findPropertyTypeResolver((MapperConfig)config, accessor, baseType);
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (b == null) {
/* 302 */       typeSer = createTypeSerializer(config, baseType);
/*     */     } else {
/* 304 */       Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByClass((MapperConfig)config, accessor, baseType);
/*     */       
/* 306 */       typeSer = b.buildTypeSerializer(config, baseType, subtypes);
/*     */     } 
/* 308 */     return typeSer;
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
/*     */   public TypeSerializer findPropertyContentTypeSerializer(JavaType containerType, SerializationConfig config, AnnotatedMember accessor) throws JsonMappingException {
/*     */     TypeSerializer typeSer;
/* 325 */     JavaType contentType = containerType.getContentType();
/* 326 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 327 */     TypeResolverBuilder<?> b = ai.findPropertyContentTypeResolver((MapperConfig)config, accessor, containerType);
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (b == null) {
/* 332 */       typeSer = createTypeSerializer(config, contentType);
/*     */     } else {
/* 334 */       Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByClass((MapperConfig)config, accessor, contentType);
/*     */       
/* 336 */       typeSer = b.buildTypeSerializer(config, contentType, subtypes);
/*     */     } 
/* 338 */     return typeSer;
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
/*     */   protected JsonSerializer<Object> constructBeanSerializer(SerializerProvider prov, BeanDescription beanDesc) throws JsonMappingException {
/* 359 */     if (beanDesc.getBeanClass() == Object.class) {
/* 360 */       return prov.getUnknownTypeSerializer(Object.class);
/*     */     }
/*     */     
/* 363 */     SerializationConfig config = prov.getConfig();
/* 364 */     BeanSerializerBuilder builder = constructBeanSerializerBuilder(beanDesc);
/* 365 */     builder.setConfig(config);
/*     */ 
/*     */     
/* 368 */     List<BeanPropertyWriter> props = findBeanProperties(prov, beanDesc, builder);
/* 369 */     if (props == null) {
/* 370 */       props = new ArrayList<>();
/*     */     } else {
/* 372 */       props = removeOverlappingTypeIds(prov, beanDesc, builder, props);
/*     */     } 
/*     */ 
/*     */     
/* 376 */     prov.getAnnotationIntrospector().findAndAddVirtualProperties((MapperConfig)config, beanDesc.getClassInfo(), props);
/*     */ 
/*     */     
/* 379 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 380 */       for (BeanSerializerModifier mod : this._factoryConfig.serializerModifiers()) {
/* 381 */         props = mod.changeProperties(config, beanDesc, props);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 386 */     props = filterBeanProperties(config, beanDesc, props);
/*     */ 
/*     */     
/* 389 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 390 */       for (BeanSerializerModifier mod : this._factoryConfig.serializerModifiers()) {
/* 391 */         props = mod.orderProperties(config, beanDesc, props);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     builder.setObjectIdWriter(constructObjectIdHandler(prov, beanDesc, props));
/*     */     
/* 400 */     builder.setProperties(props);
/* 401 */     builder.setFilterId(findFilterId(config, beanDesc));
/*     */     
/* 403 */     AnnotatedMember anyGetter = beanDesc.findAnyGetter();
/* 404 */     if (anyGetter != null) {
/* 405 */       MapSerializer mapSerializer; JavaType type = anyGetter.getType();
/*     */       
/* 407 */       boolean staticTyping = config.isEnabled(MapperFeature.USE_STATIC_TYPING);
/* 408 */       JavaType valueType = type.getContentType();
/* 409 */       TypeSerializer typeSer = createTypeSerializer(config, valueType);
/*     */ 
/*     */       
/* 412 */       JsonSerializer<?> anySer = findSerializerFromAnnotation(prov, (Annotated)anyGetter);
/* 413 */       if (anySer == null)
/*     */       {
/* 415 */         mapSerializer = MapSerializer.construct((Set)null, type, staticTyping, typeSer, null, null, null);
/*     */       }
/*     */ 
/*     */       
/* 419 */       PropertyName name = PropertyName.construct(anyGetter.getName());
/* 420 */       BeanProperty.Std anyProp = new BeanProperty.Std(name, valueType, null, anyGetter, PropertyMetadata.STD_OPTIONAL);
/*     */       
/* 422 */       builder.setAnyGetter(new AnyGetterWriter((BeanProperty)anyProp, anyGetter, (JsonSerializer<?>)mapSerializer));
/*     */     } 
/*     */     
/* 425 */     processViews(config, builder);
/*     */ 
/*     */     
/* 428 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 429 */       for (BeanSerializerModifier mod : this._factoryConfig.serializerModifiers()) {
/* 430 */         builder = mod.updateBuilder(config, beanDesc, builder);
/*     */       }
/*     */     }
/*     */     
/* 434 */     JsonSerializer<Object> ser = null;
/*     */     try {
/* 436 */       ser = (JsonSerializer)builder.build();
/* 437 */     } catch (RuntimeException e) {
/* 438 */       prov.reportBadTypeDefinition(beanDesc, "Failed to construct BeanSerializer for %s: (%s) %s", new Object[] { beanDesc.getType(), e.getClass().getName(), e.getMessage() });
/*     */     } 
/*     */     
/* 441 */     if (ser == null)
/*     */     {
/*     */ 
/*     */       
/* 445 */       if (beanDesc.hasKnownClassAnnotations()) {
/* 446 */         return (JsonSerializer<Object>)builder.createDummy();
/*     */       }
/*     */     }
/* 449 */     return ser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectIdWriter constructObjectIdHandler(SerializerProvider prov, BeanDescription beanDesc, List<BeanPropertyWriter> props) throws JsonMappingException {
/* 456 */     ObjectIdInfo objectIdInfo = beanDesc.getObjectIdInfo();
/* 457 */     if (objectIdInfo == null) {
/* 458 */       return null;
/*     */     }
/*     */     
/* 461 */     Class<?> implClass = objectIdInfo.getGeneratorType();
/*     */ 
/*     */     
/* 464 */     if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
/* 465 */       String propName = objectIdInfo.getPropertyName().getSimpleName();
/* 466 */       BeanPropertyWriter idProp = null;
/*     */       
/* 468 */       for (int i = 0, len = props.size();; i++) {
/* 469 */         if (i == len) {
/* 470 */           throw new IllegalArgumentException("Invalid Object Id definition for " + beanDesc.getBeanClass().getName() + ": cannot find property with name '" + propName + "'");
/*     */         }
/*     */         
/* 473 */         BeanPropertyWriter prop = props.get(i);
/* 474 */         if (propName.equals(prop.getName())) {
/* 475 */           idProp = prop;
/*     */ 
/*     */           
/* 478 */           if (i > 0) {
/* 479 */             props.remove(i);
/* 480 */             props.add(0, idProp);
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/* 485 */       JavaType javaType = idProp.getType();
/* 486 */       PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo, idProp);
/*     */       
/* 488 */       return ObjectIdWriter.construct(javaType, (PropertyName)null, (ObjectIdGenerator)propertyBasedObjectIdGenerator, objectIdInfo.getAlwaysAsId());
/*     */     } 
/*     */ 
/*     */     
/* 492 */     JavaType type = prov.constructType(implClass);
/*     */     
/* 494 */     JavaType idType = prov.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
/* 495 */     ObjectIdGenerator<?> gen = prov.objectIdGeneratorInstance((Annotated)beanDesc.getClassInfo(), objectIdInfo);
/* 496 */     return ObjectIdWriter.construct(idType, objectIdInfo.getPropertyName(), gen, objectIdInfo.getAlwaysAsId());
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
/*     */   protected BeanPropertyWriter constructFilteredBeanWriter(BeanPropertyWriter writer, Class<?>[] inViews) {
/* 508 */     return FilteredBeanPropertyWriter.constructViewBased(writer, inViews);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PropertyBuilder constructPropertyBuilder(SerializationConfig config, BeanDescription beanDesc) {
/* 514 */     return new PropertyBuilder(config, beanDesc);
/*     */   }
/*     */   
/*     */   protected BeanSerializerBuilder constructBeanSerializerBuilder(BeanDescription beanDesc) {
/* 518 */     return new BeanSerializerBuilder(beanDesc);
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
/* 537 */     return (ClassUtil.canBeABeanType(type) == null && !ClassUtil.isProxyType(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<BeanPropertyWriter> findBeanProperties(SerializerProvider prov, BeanDescription beanDesc, BeanSerializerBuilder builder) throws JsonMappingException {
/* 548 */     List<BeanPropertyDefinition> properties = beanDesc.findProperties();
/* 549 */     SerializationConfig config = prov.getConfig();
/*     */ 
/*     */     
/* 552 */     removeIgnorableTypes(config, beanDesc, properties);
/*     */ 
/*     */     
/* 555 */     if (config.isEnabled(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)) {
/* 556 */       removeSetterlessGetters(config, beanDesc, properties);
/*     */     }
/*     */ 
/*     */     
/* 560 */     if (properties.isEmpty()) {
/* 561 */       return null;
/*     */     }
/*     */     
/* 564 */     boolean staticTyping = usesStaticTyping(config, beanDesc, null);
/* 565 */     PropertyBuilder pb = constructPropertyBuilder(config, beanDesc);
/*     */     
/* 567 */     ArrayList<BeanPropertyWriter> result = new ArrayList<>(properties.size());
/* 568 */     for (BeanPropertyDefinition property : properties) {
/* 569 */       AnnotatedMember accessor = property.getAccessor();
/*     */       
/* 571 */       if (property.isTypeId()) {
/* 572 */         if (accessor != null) {
/* 573 */           builder.setTypeId(accessor);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 578 */       AnnotationIntrospector.ReferenceProperty refType = property.findReferenceType();
/* 579 */       if (refType != null && refType.isBackReference()) {
/*     */         continue;
/*     */       }
/* 582 */       if (accessor instanceof software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod) {
/* 583 */         result.add(_constructWriter(prov, property, pb, staticTyping, accessor)); continue;
/*     */       } 
/* 585 */       result.add(_constructWriter(prov, property, pb, staticTyping, accessor));
/*     */     } 
/*     */     
/* 588 */     return result;
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
/*     */   protected List<BeanPropertyWriter> filterBeanProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> props) {
/* 608 */     JsonIgnoreProperties.Value ignorals = config.getDefaultPropertyIgnorals(beanDesc.getBeanClass(), beanDesc.getClassInfo());
/*     */     
/* 610 */     if (ignorals != null) {
/* 611 */       Set<String> ignored = ignorals.findIgnoredForSerialization();
/* 612 */       if (!ignored.isEmpty()) {
/* 613 */         Iterator<BeanPropertyWriter> it = props.iterator();
/* 614 */         while (it.hasNext()) {
/* 615 */           if (ignored.contains(((BeanPropertyWriter)it.next()).getName())) {
/* 616 */             it.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 621 */     return props;
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
/*     */   protected void processViews(SerializationConfig config, BeanSerializerBuilder builder) {
/* 636 */     List<BeanPropertyWriter> props = builder.getProperties();
/* 637 */     boolean includeByDefault = config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
/* 638 */     int propCount = props.size();
/* 639 */     int viewsFound = 0;
/* 640 */     BeanPropertyWriter[] filtered = new BeanPropertyWriter[propCount];
/*     */     
/* 642 */     for (int i = 0; i < propCount; i++) {
/* 643 */       BeanPropertyWriter bpw = props.get(i);
/* 644 */       Class<?>[] views = bpw.getViews();
/* 645 */       if (views == null) {
/* 646 */         if (includeByDefault) {
/* 647 */           filtered[i] = bpw;
/*     */         }
/*     */       } else {
/* 650 */         viewsFound++;
/* 651 */         filtered[i] = constructFilteredBeanWriter(bpw, views);
/*     */       } 
/*     */     } 
/*     */     
/* 655 */     if (includeByDefault && viewsFound == 0) {
/*     */       return;
/*     */     }
/* 658 */     builder.setFilteredProperties(filtered);
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
/*     */   protected void removeIgnorableTypes(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyDefinition> properties) {
/* 670 */     AnnotationIntrospector intr = config.getAnnotationIntrospector();
/* 671 */     HashMap<Class<?>, Boolean> ignores = new HashMap<>();
/* 672 */     Iterator<BeanPropertyDefinition> it = properties.iterator();
/* 673 */     while (it.hasNext()) {
/* 674 */       BeanPropertyDefinition property = it.next();
/* 675 */       AnnotatedMember accessor = property.getAccessor();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 680 */       if (accessor == null) {
/* 681 */         it.remove();
/*     */         continue;
/*     */       } 
/* 684 */       Class<?> type = property.getRawPrimaryType();
/* 685 */       Boolean result = ignores.get(type);
/* 686 */       if (result == null) {
/*     */         
/* 688 */         result = config.getConfigOverride(type).getIsIgnoredType();
/* 689 */         if (result == null) {
/* 690 */           BeanDescription desc = config.introspectClassAnnotations(type);
/* 691 */           AnnotatedClass ac = desc.getClassInfo();
/* 692 */           result = intr.isIgnorableType(ac);
/*     */           
/* 694 */           if (result == null) {
/* 695 */             result = Boolean.FALSE;
/*     */           }
/*     */         } 
/* 698 */         ignores.put(type, result);
/*     */       } 
/*     */       
/* 701 */       if (result.booleanValue()) {
/* 702 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeSetterlessGetters(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyDefinition> properties) {
/* 713 */     Iterator<BeanPropertyDefinition> it = properties.iterator();
/* 714 */     while (it.hasNext()) {
/* 715 */       BeanPropertyDefinition property = it.next();
/*     */ 
/*     */       
/* 718 */       if (!property.couldDeserialize() && !property.isExplicitlyIncluded()) {
/* 719 */         it.remove();
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
/*     */   protected List<BeanPropertyWriter> removeOverlappingTypeIds(SerializerProvider prov, BeanDescription beanDesc, BeanSerializerBuilder builder, List<BeanPropertyWriter> props) {
/* 734 */     for (int i = 0, end = props.size(); i < end; i++) {
/* 735 */       BeanPropertyWriter bpw = props.get(i);
/* 736 */       TypeSerializer td = bpw.getTypeSerializer();
/* 737 */       if (td != null && td.getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
/*     */ 
/*     */         
/* 740 */         String n = td.getPropertyName();
/* 741 */         PropertyName typePropName = PropertyName.construct(n);
/*     */         
/* 743 */         for (BeanPropertyWriter w2 : props) {
/* 744 */           if (w2 != bpw && w2.wouldConflictWithName(typePropName)) {
/* 745 */             bpw.assignTypeSerializer(null); break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 750 */     return props;
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
/*     */   protected BeanPropertyWriter _constructWriter(SerializerProvider prov, BeanPropertyDefinition propDef, PropertyBuilder pb, boolean staticTyping, AnnotatedMember accessor) throws JsonMappingException {
/* 768 */     PropertyName name = propDef.getFullName();
/* 769 */     JavaType type = accessor.getType();
/* 770 */     BeanProperty.Std property = new BeanProperty.Std(name, type, propDef.getWrapperName(), accessor, propDef.getMetadata());
/*     */ 
/*     */ 
/*     */     
/* 774 */     JsonSerializer<?> annotatedSerializer = findSerializerFromAnnotation(prov, (Annotated)accessor);
/*     */ 
/*     */ 
/*     */     
/* 778 */     if (annotatedSerializer instanceof ResolvableSerializer) {
/* 779 */       ((ResolvableSerializer)annotatedSerializer).resolve(prov);
/*     */     }
/*     */     
/* 782 */     annotatedSerializer = prov.handlePrimaryContextualization(annotatedSerializer, (BeanProperty)property);
/*     */     
/* 784 */     TypeSerializer contentTypeSer = null;
/*     */     
/* 786 */     if (type.isContainerType() || type.isReferenceType()) {
/* 787 */       contentTypeSer = findPropertyContentTypeSerializer(type, prov.getConfig(), accessor);
/*     */     }
/*     */     
/* 790 */     TypeSerializer typeSer = findPropertyTypeSerializer(type, prov.getConfig(), accessor);
/* 791 */     return pb.buildWriter(prov, propDef, type, annotatedSerializer, typeSer, contentTypeSer, accessor, staticTyping);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\BeanSerializerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */