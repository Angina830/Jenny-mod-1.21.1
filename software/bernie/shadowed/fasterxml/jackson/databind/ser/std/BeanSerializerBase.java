/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyFilter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.WritableObjectId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ public abstract class BeanSerializerBase extends StdSerializer<Object> implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware {
/*  41 */   protected static final PropertyName NAME_FOR_OBJECT_REF = new PropertyName("#object-ref");
/*     */   
/*  43 */   protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _beanType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanPropertyWriter[] _props;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanPropertyWriter[] _filteredProps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnyGetterWriter _anyGetterWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object _propertyFilterId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnnotatedMember _typeId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ObjectIdWriter _objectIdWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonFormat.Shape _serializationShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase(JavaType type, BeanSerializerBuilder builder, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
/* 112 */     super(type);
/* 113 */     this._beanType = type;
/* 114 */     this._props = properties;
/* 115 */     this._filteredProps = filteredProperties;
/* 116 */     if (builder == null) {
/* 117 */       this._typeId = null;
/* 118 */       this._anyGetterWriter = null;
/* 119 */       this._propertyFilterId = null;
/* 120 */       this._objectIdWriter = null;
/* 121 */       this._serializationShape = null;
/*     */     } else {
/* 123 */       this._typeId = builder.getTypeId();
/* 124 */       this._anyGetterWriter = builder.getAnyGetter();
/* 125 */       this._propertyFilterId = builder.getFilterId();
/* 126 */       this._objectIdWriter = builder.getObjectIdWriter();
/* 127 */       JsonFormat.Value format = builder.getBeanDescription().findExpectedFormat(null);
/* 128 */       this._serializationShape = (format == null) ? null : format.getShape();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSerializerBase(BeanSerializerBase src, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
/* 135 */     super(src._handledType);
/* 136 */     this._beanType = src._beanType;
/* 137 */     this._props = properties;
/* 138 */     this._filteredProps = filteredProperties;
/*     */     
/* 140 */     this._typeId = src._typeId;
/* 141 */     this._anyGetterWriter = src._anyGetterWriter;
/* 142 */     this._objectIdWriter = src._objectIdWriter;
/* 143 */     this._propertyFilterId = src._propertyFilterId;
/* 144 */     this._serializationShape = src._serializationShape;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase(BeanSerializerBase src, ObjectIdWriter objectIdWriter) {
/* 150 */     this(src, objectIdWriter, src._propertyFilterId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase(BeanSerializerBase src, ObjectIdWriter objectIdWriter, Object filterId) {
/* 159 */     super(src._handledType);
/* 160 */     this._beanType = src._beanType;
/* 161 */     this._props = src._props;
/* 162 */     this._filteredProps = src._filteredProps;
/*     */     
/* 164 */     this._typeId = src._typeId;
/* 165 */     this._anyGetterWriter = src._anyGetterWriter;
/* 166 */     this._objectIdWriter = objectIdWriter;
/* 167 */     this._propertyFilterId = filterId;
/* 168 */     this._serializationShape = src._serializationShape;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected BeanSerializerBase(BeanSerializerBase src, String[] toIgnore) {
/* 174 */     this(src, ArrayBuilders.arrayToSet((Object[])toIgnore));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase(BeanSerializerBase src, Set<String> toIgnore) {
/* 179 */     super(src._handledType);
/*     */     
/* 181 */     this._beanType = src._beanType;
/* 182 */     BeanPropertyWriter[] propsIn = src._props;
/* 183 */     BeanPropertyWriter[] fpropsIn = src._filteredProps;
/* 184 */     int len = propsIn.length;
/*     */     
/* 186 */     ArrayList<BeanPropertyWriter> propsOut = new ArrayList<>(len);
/* 187 */     ArrayList<BeanPropertyWriter> fpropsOut = (fpropsIn == null) ? null : new ArrayList<>(len);
/*     */     
/* 189 */     for (int i = 0; i < len; i++) {
/* 190 */       BeanPropertyWriter bpw = propsIn[i];
/*     */       
/* 192 */       if (toIgnore == null || !toIgnore.contains(bpw.getName())) {
/*     */ 
/*     */         
/* 195 */         propsOut.add(bpw);
/* 196 */         if (fpropsIn != null)
/* 197 */           fpropsOut.add(fpropsIn[i]); 
/*     */       } 
/*     */     } 
/* 200 */     this._props = propsOut.<BeanPropertyWriter>toArray(new BeanPropertyWriter[propsOut.size()]);
/* 201 */     this._filteredProps = (fpropsOut == null) ? null : fpropsOut.<BeanPropertyWriter>toArray(new BeanPropertyWriter[fpropsOut.size()]);
/*     */     
/* 203 */     this._typeId = src._typeId;
/* 204 */     this._anyGetterWriter = src._anyGetterWriter;
/* 205 */     this._objectIdWriter = src._objectIdWriter;
/* 206 */     this._propertyFilterId = src._propertyFilterId;
/* 207 */     this._serializationShape = src._serializationShape;
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
/*     */   @Deprecated
/*     */   protected BeanSerializerBase withIgnorals(String[] toIgnore) {
/* 234 */     return withIgnorals(ArrayBuilders.arrayToSet((Object[])toIgnore));
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
/*     */   protected BeanSerializerBase(BeanSerializerBase src) {
/* 260 */     this(src, src._props, src._filteredProps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase(BeanSerializerBase src, NameTransformer unwrapper) {
/* 268 */     this(src, rename(src._props, unwrapper), rename(src._filteredProps, unwrapper));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] props, NameTransformer transformer) {
/* 274 */     if (props == null || props.length == 0 || transformer == null || transformer == NameTransformer.NOP) {
/* 275 */       return props;
/*     */     }
/* 277 */     int len = props.length;
/* 278 */     BeanPropertyWriter[] result = new BeanPropertyWriter[len];
/* 279 */     for (int i = 0; i < len; i++) {
/* 280 */       BeanPropertyWriter bpw = props[i];
/* 281 */       if (bpw != null) {
/* 282 */         result[i] = bpw.rename(transformer);
/*     */       }
/*     */     } 
/* 285 */     return result;
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
/*     */   public void resolve(SerializerProvider provider) throws JsonMappingException {
/* 302 */     int filteredCount = (this._filteredProps == null) ? 0 : this._filteredProps.length;
/* 303 */     for (int i = 0, len = this._props.length; i < len; i++) {
/* 304 */       ContainerSerializer containerSerializer; BeanPropertyWriter prop = this._props[i];
/*     */       
/* 306 */       if (!prop.willSuppressNulls() && !prop.hasNullSerializer()) {
/* 307 */         JsonSerializer<Object> nullSer = provider.findNullValueSerializer((BeanProperty)prop);
/* 308 */         if (nullSer != null) {
/* 309 */           prop.assignNullSerializer(nullSer);
/*     */           
/* 311 */           if (i < filteredCount) {
/* 312 */             BeanPropertyWriter w2 = this._filteredProps[i];
/* 313 */             if (w2 != null) {
/* 314 */               w2.assignNullSerializer(nullSer);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 320 */       if (prop.hasSerializer()) {
/*     */         continue;
/*     */       }
/*     */       
/* 324 */       JsonSerializer<Object> ser = findConvertingSerializer(provider, prop);
/* 325 */       if (ser == null) {
/*     */         
/* 327 */         JavaType type = prop.getSerializationType();
/*     */ 
/*     */ 
/*     */         
/* 331 */         if (type == null) {
/* 332 */           type = prop.getType();
/* 333 */           if (!type.isFinal()) {
/* 334 */             if (type.isContainerType() || type.containedTypeCount() > 0) {
/* 335 */               prop.setNonTrivialBaseType(type);
/*     */             }
/*     */             continue;
/*     */           } 
/*     */         } 
/* 340 */         ser = provider.findValueSerializer(type, (BeanProperty)prop);
/*     */ 
/*     */ 
/*     */         
/* 344 */         if (type.isContainerType()) {
/* 345 */           TypeSerializer typeSer = (TypeSerializer)type.getContentType().getTypeHandler();
/* 346 */           if (typeSer != null)
/*     */           {
/* 348 */             if (ser instanceof ContainerSerializer) {
/*     */ 
/*     */               
/* 351 */               ContainerSerializer containerSerializer1 = ((ContainerSerializer)ser).withValueTypeSerializer(typeSer);
/* 352 */               containerSerializer = containerSerializer1;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 358 */       if (i < filteredCount) {
/* 359 */         BeanPropertyWriter w2 = this._filteredProps[i];
/* 360 */         if (w2 != null) {
/* 361 */           w2.assignSerializer((JsonSerializer)containerSerializer);
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       
/* 368 */       prop.assignSerializer((JsonSerializer)containerSerializer);
/*     */       
/*     */       continue;
/*     */     } 
/* 372 */     if (this._anyGetterWriter != null)
/*     */     {
/* 374 */       this._anyGetterWriter.resolve(provider);
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
/*     */   protected JsonSerializer<Object> findConvertingSerializer(SerializerProvider provider, BeanPropertyWriter prop) throws JsonMappingException {
/* 389 */     AnnotationIntrospector intr = provider.getAnnotationIntrospector();
/* 390 */     if (intr != null) {
/* 391 */       AnnotatedMember m = prop.getMember();
/* 392 */       if (m != null) {
/* 393 */         Object convDef = intr.findSerializationConverter((Annotated)m);
/* 394 */         if (convDef != null) {
/* 395 */           Converter<Object, Object> conv = provider.converterInstance((Annotated)prop.getMember(), convDef);
/* 396 */           JavaType delegateType = conv.getOutputType(provider.getTypeFactory());
/*     */           
/* 398 */           JsonSerializer<?> ser = delegateType.isJavaLangObject() ? null : provider.findValueSerializer(delegateType, (BeanProperty)prop);
/*     */           
/* 400 */           return new StdDelegatingSerializer(conv, delegateType, ser);
/*     */         } 
/*     */       } 
/*     */     } 
/* 404 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/* 413 */     AnnotationIntrospector intr = provider.getAnnotationIntrospector();
/* 414 */     AnnotatedMember accessor = (property == null || intr == null) ? null : property.getMember();
/*     */     
/* 416 */     SerializationConfig config = provider.getConfig();
/*     */ 
/*     */ 
/*     */     
/* 420 */     JsonFormat.Value format = findFormatOverrides(provider, property, handledType());
/* 421 */     JsonFormat.Shape shape = null;
/* 422 */     if (format != null && format.hasShape()) {
/* 423 */       shape = format.getShape();
/*     */       
/* 425 */       if (shape != JsonFormat.Shape.ANY && shape != this._serializationShape) {
/* 426 */         if (this._handledType.isEnum()) {
/* 427 */           BeanDescription desc; JsonSerializer<?> ser; switch (shape) {
/*     */ 
/*     */             
/*     */             case STRING:
/*     */             case NUMBER:
/*     */             case NUMBER_INT:
/* 433 */               desc = config.introspectClassAnnotations(this._beanType);
/* 434 */               ser = EnumSerializer.construct(this._beanType.getRawClass(), provider.getConfig(), desc, format);
/*     */               
/* 436 */               return provider.handlePrimaryContextualization(ser, property);
/*     */           } 
/*     */         
/* 439 */         } else if (shape == JsonFormat.Shape.NATURAL && (
/* 440 */           !this._beanType.isMapLikeType() || !Map.class.isAssignableFrom(this._handledType))) {
/*     */           
/* 442 */           if (Map.Entry.class.isAssignableFrom(this._handledType)) {
/* 443 */             JavaType mapEntryType = this._beanType.findSuperType(Map.Entry.class);
/*     */             
/* 445 */             JavaType kt = mapEntryType.containedTypeOrUnknown(0);
/* 446 */             JavaType vt = mapEntryType.containedTypeOrUnknown(1);
/*     */ 
/*     */ 
/*     */             
/* 450 */             MapEntrySerializer mapEntrySerializer = new MapEntrySerializer(this._beanType, kt, vt, false, null, property);
/*     */             
/* 452 */             return provider.handlePrimaryContextualization((JsonSerializer)mapEntrySerializer, property);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 458 */     ObjectIdWriter oiw = this._objectIdWriter;
/* 459 */     Set<String> ignoredProps = null;
/* 460 */     Object newFilterId = null;
/*     */ 
/*     */     
/* 463 */     if (accessor != null) {
/* 464 */       JsonIgnoreProperties.Value ignorals = intr.findPropertyIgnorals((Annotated)accessor);
/* 465 */       if (ignorals != null) {
/* 466 */         ignoredProps = ignorals.findIgnoredForSerialization();
/*     */       }
/* 468 */       ObjectIdInfo objectIdInfo = intr.findObjectIdInfo((Annotated)accessor);
/* 469 */       if (objectIdInfo == null) {
/*     */         
/* 471 */         if (oiw != null) {
/* 472 */           objectIdInfo = intr.findObjectReferenceInfo((Annotated)accessor, null);
/* 473 */           if (objectIdInfo != null) {
/* 474 */             oiw = this._objectIdWriter.withAlwaysAsId(objectIdInfo.getAlwaysAsId());
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 482 */         objectIdInfo = intr.findObjectReferenceInfo((Annotated)accessor, objectIdInfo);
/*     */         
/* 484 */         Class<?> implClass = objectIdInfo.getGeneratorType();
/* 485 */         JavaType type = provider.constructType(implClass);
/* 486 */         JavaType idType = provider.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
/*     */         
/* 488 */         if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
/* 489 */           String propName = objectIdInfo.getPropertyName().getSimpleName();
/* 490 */           BeanPropertyWriter idProp = null;
/*     */           
/* 492 */           for (int i = 0, len = this._props.length;; i++) {
/* 493 */             if (i == len) {
/* 494 */               provider.reportBadDefinition(this._beanType, String.format("Invalid Object Id definition for %s: cannot find property with name '%s'", new Object[] { handledType().getName(), propName }));
/*     */             }
/*     */ 
/*     */             
/* 498 */             BeanPropertyWriter prop = this._props[i];
/* 499 */             if (propName.equals(prop.getName())) {
/* 500 */               idProp = prop;
/*     */ 
/*     */               
/* 503 */               if (i > 0) {
/* 504 */                 System.arraycopy(this._props, 0, this._props, 1, i);
/* 505 */                 this._props[0] = idProp;
/* 506 */                 if (this._filteredProps != null) {
/* 507 */                   BeanPropertyWriter fp = this._filteredProps[i];
/* 508 */                   System.arraycopy(this._filteredProps, 0, this._filteredProps, 1, i);
/* 509 */                   this._filteredProps[0] = fp;
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */             } 
/*     */           } 
/* 515 */           idType = idProp.getType();
/* 516 */           PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo, idProp);
/* 517 */           oiw = ObjectIdWriter.construct(idType, (PropertyName)null, (ObjectIdGenerator)propertyBasedObjectIdGenerator, objectIdInfo.getAlwaysAsId());
/*     */         } else {
/* 519 */           ObjectIdGenerator<?> gen = provider.objectIdGeneratorInstance((Annotated)accessor, objectIdInfo);
/* 520 */           oiw = ObjectIdWriter.construct(idType, objectIdInfo.getPropertyName(), gen, objectIdInfo.getAlwaysAsId());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 525 */       Object filterId = intr.findFilterId((Annotated)accessor);
/* 526 */       if (filterId != null)
/*     */       {
/* 528 */         if (this._propertyFilterId == null || !filterId.equals(this._propertyFilterId)) {
/* 529 */           newFilterId = filterId;
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 534 */     BeanSerializerBase contextual = this;
/* 535 */     if (oiw != null) {
/* 536 */       JsonSerializer<?> ser = provider.findValueSerializer(oiw.idType, property);
/* 537 */       oiw = oiw.withSerializer(ser);
/* 538 */       if (oiw != this._objectIdWriter) {
/* 539 */         contextual = contextual.withObjectIdWriter(oiw);
/*     */       }
/*     */     } 
/*     */     
/* 543 */     if (ignoredProps != null && !ignoredProps.isEmpty()) {
/* 544 */       contextual = contextual.withIgnorals(ignoredProps);
/*     */     }
/* 546 */     if (newFilterId != null) {
/* 547 */       contextual = contextual.withFilterId(newFilterId);
/*     */     }
/* 549 */     if (shape == null) {
/* 550 */       shape = this._serializationShape;
/*     */     }
/*     */     
/* 553 */     if (shape == JsonFormat.Shape.ARRAY) {
/* 554 */       return contextual.asArraySerializer();
/*     */     }
/* 556 */     return contextual;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<PropertyWriter> properties() {
/* 567 */     return Arrays.asList((Object[])this._props).iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesObjectId() {
/* 578 */     return (this._objectIdWriter != null);
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
/*     */   public void serializeWithType(Object bean, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 592 */     if (this._objectIdWriter != null) {
/* 593 */       gen.setCurrentValue(bean);
/* 594 */       _serializeWithObjectId(bean, gen, provider, typeSer);
/*     */       
/*     */       return;
/*     */     } 
/* 598 */     gen.setCurrentValue(bean);
/* 599 */     WritableTypeId typeIdDef = _typeIdDef(typeSer, bean, JsonToken.START_OBJECT);
/* 600 */     typeSer.writeTypePrefix(gen, typeIdDef);
/* 601 */     if (this._propertyFilterId != null) {
/* 602 */       serializeFieldsFiltered(bean, gen, provider);
/*     */     } else {
/* 604 */       serializeFields(bean, gen, provider);
/*     */     } 
/* 606 */     typeSer.writeTypeSuffix(gen, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void _serializeWithObjectId(Object bean, JsonGenerator gen, SerializerProvider provider, boolean startEndObject) throws IOException {
/* 612 */     ObjectIdWriter w = this._objectIdWriter;
/* 613 */     WritableObjectId objectId = provider.findObjectId(bean, w.generator);
/*     */     
/* 615 */     if (objectId.writeAsId(gen, provider, w)) {
/*     */       return;
/*     */     }
/*     */     
/* 619 */     Object id = objectId.generateId(bean);
/* 620 */     if (w.alwaysAsId) {
/* 621 */       w.serializer.serialize(id, gen, provider);
/*     */       return;
/*     */     } 
/* 624 */     if (startEndObject) {
/* 625 */       gen.writeStartObject(bean);
/*     */     }
/* 627 */     objectId.writeAsField(gen, provider, w);
/* 628 */     if (this._propertyFilterId != null) {
/* 629 */       serializeFieldsFiltered(bean, gen, provider);
/*     */     } else {
/* 631 */       serializeFields(bean, gen, provider);
/*     */     } 
/* 633 */     if (startEndObject) {
/* 634 */       gen.writeEndObject();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void _serializeWithObjectId(Object bean, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 641 */     ObjectIdWriter w = this._objectIdWriter;
/* 642 */     WritableObjectId objectId = provider.findObjectId(bean, w.generator);
/*     */     
/* 644 */     if (objectId.writeAsId(gen, provider, w)) {
/*     */       return;
/*     */     }
/*     */     
/* 648 */     Object id = objectId.generateId(bean);
/* 649 */     if (w.alwaysAsId) {
/* 650 */       w.serializer.serialize(id, gen, provider);
/*     */       return;
/*     */     } 
/* 653 */     _serializeObjectId(bean, gen, provider, typeSer, objectId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _serializeObjectId(Object bean, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer, WritableObjectId objectId) throws IOException {
/* 660 */     ObjectIdWriter w = this._objectIdWriter;
/* 661 */     WritableTypeId typeIdDef = _typeIdDef(typeSer, bean, JsonToken.START_OBJECT);
/*     */     
/* 663 */     typeSer.writeTypePrefix(g, typeIdDef);
/* 664 */     objectId.writeAsField(g, provider, w);
/* 665 */     if (this._propertyFilterId != null) {
/* 666 */       serializeFieldsFiltered(bean, g, provider);
/*     */     } else {
/* 668 */       serializeFields(bean, g, provider);
/*     */     } 
/* 670 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final WritableTypeId _typeIdDef(TypeSerializer typeSer, Object bean, JsonToken valueShape) {
/* 678 */     if (this._typeId == null) {
/* 679 */       return typeSer.typeId(bean, valueShape);
/*     */     }
/* 681 */     Object typeId = this._typeId.getValue(bean);
/* 682 */     if (typeId == null)
/*     */     {
/* 684 */       typeId = "";
/*     */     }
/* 686 */     return typeSer.typeId(bean, valueShape, typeId);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected final String _customTypeId(Object bean) {
/* 692 */     Object typeId = this._typeId.getValue(bean);
/* 693 */     if (typeId == null) {
/* 694 */       return "";
/*     */     }
/* 696 */     return (typeId instanceof String) ? (String)typeId : typeId.toString();
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
/*     */   protected void serializeFields(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*     */     BeanPropertyWriter[] props;
/* 709 */     if (this._filteredProps != null && provider.getActiveView() != null) {
/* 710 */       props = this._filteredProps;
/*     */     } else {
/* 712 */       props = this._props;
/*     */     } 
/* 714 */     int i = 0;
/*     */     try {
/* 716 */       for (int len = props.length; i < len; i++) {
/* 717 */         BeanPropertyWriter prop = props[i];
/* 718 */         if (prop != null) {
/* 719 */           prop.serializeAsField(bean, gen, provider);
/*     */         }
/*     */       } 
/* 722 */       if (this._anyGetterWriter != null) {
/* 723 */         this._anyGetterWriter.getAndSerialize(bean, gen, provider);
/*     */       }
/* 725 */     } catch (Exception e) {
/* 726 */       String name = (i == props.length) ? "[anySetter]" : props[i].getName();
/* 727 */       wrapAndThrow(provider, e, bean, name);
/* 728 */     } catch (StackOverflowError e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 734 */       JsonMappingException mapE = new JsonMappingException((Closeable)gen, "Infinite recursion (StackOverflowError)", e);
/*     */       
/* 736 */       String name = (i == props.length) ? "[anySetter]" : props[i].getName();
/* 737 */       mapE.prependPath(new JsonMappingException.Reference(bean, name));
/* 738 */       throw mapE;
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
/*     */   protected void serializeFieldsFiltered(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
/*     */     BeanPropertyWriter[] props;
/* 755 */     if (this._filteredProps != null && provider.getActiveView() != null) {
/* 756 */       props = this._filteredProps;
/*     */     } else {
/* 758 */       props = this._props;
/*     */     } 
/* 760 */     PropertyFilter filter = findPropertyFilter(provider, this._propertyFilterId, bean);
/*     */     
/* 762 */     if (filter == null) {
/* 763 */       serializeFields(bean, gen, provider);
/*     */       return;
/*     */     } 
/* 766 */     int i = 0;
/*     */     try {
/* 768 */       for (int len = props.length; i < len; i++) {
/* 769 */         BeanPropertyWriter prop = props[i];
/* 770 */         if (prop != null) {
/* 771 */           filter.serializeAsField(bean, gen, provider, (PropertyWriter)prop);
/*     */         }
/*     */       } 
/* 774 */       if (this._anyGetterWriter != null) {
/* 775 */         this._anyGetterWriter.getAndFilter(bean, gen, provider, filter);
/*     */       }
/* 777 */     } catch (Exception e) {
/* 778 */       String name = (i == props.length) ? "[anySetter]" : props[i].getName();
/* 779 */       wrapAndThrow(provider, e, bean, name);
/* 780 */     } catch (StackOverflowError e) {
/*     */ 
/*     */       
/* 783 */       JsonMappingException mapE = new JsonMappingException((Closeable)gen, "Infinite recursion (StackOverflowError)", e);
/* 784 */       String name = (i == props.length) ? "[anySetter]" : props[i].getName();
/* 785 */       mapE.prependPath(new JsonMappingException.Reference(bean, name));
/* 786 */       throw mapE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/*     */     PropertyFilter filter;
/* 795 */     ObjectNode o = createSchemaNode("object", true);
/*     */ 
/*     */     
/* 798 */     JsonSerializableSchema ann = (JsonSerializableSchema)this._handledType.getAnnotation(JsonSerializableSchema.class);
/* 799 */     if (ann != null) {
/* 800 */       String id = ann.id();
/* 801 */       if (id != null && id.length() > 0) {
/* 802 */         o.put("id", id);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 808 */     ObjectNode propertiesNode = o.objectNode();
/*     */     
/* 810 */     if (this._propertyFilterId != null) {
/* 811 */       filter = findPropertyFilter(provider, this._propertyFilterId, null);
/*     */     } else {
/* 813 */       filter = null;
/*     */     } 
/*     */     
/* 816 */     for (int i = 0; i < this._props.length; i++) {
/* 817 */       BeanPropertyWriter prop = this._props[i];
/* 818 */       if (filter == null) {
/* 819 */         prop.depositSchemaProperty(propertiesNode, provider);
/*     */       } else {
/* 821 */         filter.depositSchemaProperty((PropertyWriter)prop, propertiesNode, provider);
/*     */       } 
/*     */     } 
/*     */     
/* 825 */     o.set("properties", (JsonNode)propertiesNode);
/* 826 */     return (JsonNode)o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 834 */     if (visitor == null) {
/*     */       return;
/*     */     }
/* 837 */     JsonObjectFormatVisitor objectVisitor = visitor.expectObjectFormat(typeHint);
/* 838 */     if (objectVisitor == null) {
/*     */       return;
/*     */     }
/* 841 */     SerializerProvider provider = visitor.getProvider();
/* 842 */     if (this._propertyFilterId != null) {
/* 843 */       PropertyFilter filter = findPropertyFilter(visitor.getProvider(), this._propertyFilterId, null);
/*     */       
/* 845 */       for (int i = 0, end = this._props.length; i < end; i++)
/* 846 */         filter.depositSchemaProperty((PropertyWriter)this._props[i], objectVisitor, provider); 
/*     */     } else {
/*     */       BeanPropertyWriter[] props;
/* 849 */       Class<?> view = (this._filteredProps == null || provider == null) ? null : provider.getActiveView();
/*     */ 
/*     */       
/* 852 */       if (view != null) {
/* 853 */         props = this._filteredProps;
/*     */       } else {
/* 855 */         props = this._props;
/*     */       } 
/*     */       
/* 858 */       for (int i = 0, end = props.length; i < end; i++) {
/* 859 */         BeanPropertyWriter prop = props[i];
/* 860 */         if (prop != null)
/* 861 */           prop.depositSchemaProperty(objectVisitor, provider); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter paramObjectIdWriter);
/*     */   
/*     */   protected abstract BeanSerializerBase withIgnorals(Set<String> paramSet);
/*     */   
/*     */   protected abstract BeanSerializerBase asArraySerializer();
/*     */   
/*     */   public abstract BeanSerializerBase withFilterId(Object paramObject);
/*     */   
/*     */   public abstract void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException;
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\BeanSerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */