/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.MergingSettableBeanProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ValueInjector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
/*      */ 
/*      */ public abstract class BeanDeserializerBase extends StdDeserializer<Object> implements ContextualDeserializer, ResolvableDeserializer, ValueInstantiator.Gettable, Serializable {
/*   34 */   protected static final PropertyName TEMP_PROPERTY_NAME = new PropertyName("#temporary-name");
/*      */ 
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
/*      */   
/*      */   protected final JavaType _beanType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonFormat.Shape _serializationShape;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ValueInstantiator _valueInstantiator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> _delegateDeserializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> _arrayDelegateDeserializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyBasedCreator _propertyBasedCreator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _nonStandardCreation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _vanillaProcessing;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final BeanPropertyMap _beanProperties;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ValueInjector[] _injectables;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SettableAnyProperty _anySetter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Set<String> _ignorableProps;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _ignoreAllUnknown;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _needViewProcesing;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Map<String, SettableBeanProperty> _backRefs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ExternalTypeHandler _externalTypeIdHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ObjectIdReader _objectIdReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, Set<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
/*  201 */     super(beanDesc.getType());
/*  202 */     this._beanType = beanDesc.getType();
/*  203 */     this._valueInstantiator = builder.getValueInstantiator();
/*      */     
/*  205 */     this._beanProperties = properties;
/*  206 */     this._backRefs = backRefs;
/*  207 */     this._ignorableProps = ignorableProps;
/*  208 */     this._ignoreAllUnknown = ignoreAllUnknown;
/*      */     
/*  210 */     this._anySetter = builder.getAnySetter();
/*  211 */     List<ValueInjector> injectables = builder.getInjectables();
/*  212 */     this._injectables = (injectables == null || injectables.isEmpty()) ? null : injectables.<ValueInjector>toArray(new ValueInjector[injectables.size()]);
/*      */     
/*  214 */     this._objectIdReader = builder.getObjectIdReader();
/*  215 */     this._nonStandardCreation = (this._unwrappedPropertyHandler != null || this._valueInstantiator.canCreateUsingDelegate() || this._valueInstantiator.canCreateUsingArrayDelegate() || this._valueInstantiator.canCreateFromObjectWith() || !this._valueInstantiator.canCreateUsingDefault());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  223 */     JsonFormat.Value format = beanDesc.findExpectedFormat(null);
/*  224 */     this._serializationShape = (format == null) ? null : format.getShape();
/*      */     
/*  226 */     this._needViewProcesing = hasViews;
/*  227 */     this._vanillaProcessing = (!this._nonStandardCreation && this._injectables == null && !this._needViewProcesing && this._objectIdReader == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBase src) {
/*  236 */     this(src, src._ignoreAllUnknown);
/*      */   }
/*      */ 
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBase src, boolean ignoreAllUnknown) {
/*  241 */     super(src._beanType);
/*      */     
/*  243 */     this._beanType = src._beanType;
/*      */     
/*  245 */     this._valueInstantiator = src._valueInstantiator;
/*  246 */     this._delegateDeserializer = src._delegateDeserializer;
/*  247 */     this._propertyBasedCreator = src._propertyBasedCreator;
/*      */     
/*  249 */     this._beanProperties = src._beanProperties;
/*  250 */     this._backRefs = src._backRefs;
/*  251 */     this._ignorableProps = src._ignorableProps;
/*  252 */     this._ignoreAllUnknown = ignoreAllUnknown;
/*  253 */     this._anySetter = src._anySetter;
/*  254 */     this._injectables = src._injectables;
/*  255 */     this._objectIdReader = src._objectIdReader;
/*      */     
/*  257 */     this._nonStandardCreation = src._nonStandardCreation;
/*  258 */     this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
/*  259 */     this._needViewProcesing = src._needViewProcesing;
/*  260 */     this._serializationShape = src._serializationShape;
/*      */     
/*  262 */     this._vanillaProcessing = src._vanillaProcessing;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBase src, NameTransformer unwrapper) {
/*  267 */     super(src._beanType);
/*      */     
/*  269 */     this._beanType = src._beanType;
/*      */     
/*  271 */     this._valueInstantiator = src._valueInstantiator;
/*  272 */     this._delegateDeserializer = src._delegateDeserializer;
/*  273 */     this._propertyBasedCreator = src._propertyBasedCreator;
/*      */     
/*  275 */     this._backRefs = src._backRefs;
/*  276 */     this._ignorableProps = src._ignorableProps;
/*  277 */     this._ignoreAllUnknown = (unwrapper != null || src._ignoreAllUnknown);
/*  278 */     this._anySetter = src._anySetter;
/*  279 */     this._injectables = src._injectables;
/*  280 */     this._objectIdReader = src._objectIdReader;
/*      */     
/*  282 */     this._nonStandardCreation = src._nonStandardCreation;
/*  283 */     UnwrappedPropertyHandler uph = src._unwrappedPropertyHandler;
/*      */     
/*  285 */     if (unwrapper != null) {
/*      */       
/*  287 */       if (uph != null) {
/*  288 */         uph = uph.renameAll(unwrapper);
/*      */       }
/*      */       
/*  291 */       this._beanProperties = src._beanProperties.renameAll(unwrapper);
/*      */     } else {
/*  293 */       this._beanProperties = src._beanProperties;
/*      */     } 
/*  295 */     this._unwrappedPropertyHandler = uph;
/*  296 */     this._needViewProcesing = src._needViewProcesing;
/*  297 */     this._serializationShape = src._serializationShape;
/*      */ 
/*      */     
/*  300 */     this._vanillaProcessing = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanDeserializerBase(BeanDeserializerBase src, ObjectIdReader oir) {
/*  305 */     super(src._beanType);
/*  306 */     this._beanType = src._beanType;
/*      */     
/*  308 */     this._valueInstantiator = src._valueInstantiator;
/*  309 */     this._delegateDeserializer = src._delegateDeserializer;
/*  310 */     this._propertyBasedCreator = src._propertyBasedCreator;
/*      */     
/*  312 */     this._backRefs = src._backRefs;
/*  313 */     this._ignorableProps = src._ignorableProps;
/*  314 */     this._ignoreAllUnknown = src._ignoreAllUnknown;
/*  315 */     this._anySetter = src._anySetter;
/*  316 */     this._injectables = src._injectables;
/*      */     
/*  318 */     this._nonStandardCreation = src._nonStandardCreation;
/*  319 */     this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
/*  320 */     this._needViewProcesing = src._needViewProcesing;
/*  321 */     this._serializationShape = src._serializationShape;
/*      */ 
/*      */     
/*  324 */     this._objectIdReader = oir;
/*      */     
/*  326 */     if (oir == null) {
/*  327 */       this._beanProperties = src._beanProperties;
/*  328 */       this._vanillaProcessing = src._vanillaProcessing;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  334 */       ObjectIdValueProperty idProp = new ObjectIdValueProperty(oir, PropertyMetadata.STD_REQUIRED);
/*  335 */       this._beanProperties = src._beanProperties.withProperty((SettableBeanProperty)idProp);
/*  336 */       this._vanillaProcessing = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanDeserializerBase(BeanDeserializerBase src, Set<String> ignorableProps) {
/*  342 */     super(src._beanType);
/*  343 */     this._beanType = src._beanType;
/*      */     
/*  345 */     this._valueInstantiator = src._valueInstantiator;
/*  346 */     this._delegateDeserializer = src._delegateDeserializer;
/*  347 */     this._propertyBasedCreator = src._propertyBasedCreator;
/*      */     
/*  349 */     this._backRefs = src._backRefs;
/*  350 */     this._ignorableProps = ignorableProps;
/*  351 */     this._ignoreAllUnknown = src._ignoreAllUnknown;
/*  352 */     this._anySetter = src._anySetter;
/*  353 */     this._injectables = src._injectables;
/*      */     
/*  355 */     this._nonStandardCreation = src._nonStandardCreation;
/*  356 */     this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
/*  357 */     this._needViewProcesing = src._needViewProcesing;
/*  358 */     this._serializationShape = src._serializationShape;
/*      */     
/*  360 */     this._vanillaProcessing = src._vanillaProcessing;
/*  361 */     this._objectIdReader = src._objectIdReader;
/*      */ 
/*      */ 
/*      */     
/*  365 */     this._beanProperties = src._beanProperties.withoutProperties(ignorableProps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBase src, BeanPropertyMap beanProps) {
/*  373 */     super(src._beanType);
/*  374 */     this._beanType = src._beanType;
/*      */     
/*  376 */     this._valueInstantiator = src._valueInstantiator;
/*  377 */     this._delegateDeserializer = src._delegateDeserializer;
/*  378 */     this._propertyBasedCreator = src._propertyBasedCreator;
/*      */     
/*  380 */     this._beanProperties = beanProps;
/*  381 */     this._backRefs = src._backRefs;
/*  382 */     this._ignorableProps = src._ignorableProps;
/*  383 */     this._ignoreAllUnknown = src._ignoreAllUnknown;
/*  384 */     this._anySetter = src._anySetter;
/*  385 */     this._injectables = src._injectables;
/*  386 */     this._objectIdReader = src._objectIdReader;
/*      */     
/*  388 */     this._nonStandardCreation = src._nonStandardCreation;
/*  389 */     this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
/*  390 */     this._needViewProcesing = src._needViewProcesing;
/*  391 */     this._serializationShape = src._serializationShape;
/*      */     
/*  393 */     this._vanillaProcessing = src._vanillaProcessing;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader paramObjectIdReader);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract BeanDeserializerBase withIgnorableProperties(Set<String> paramSet);
/*      */ 
/*      */   
/*      */   public BeanDeserializerBase withBeanProperties(BeanPropertyMap props) {
/*  410 */     throw new UnsupportedOperationException("Class " + getClass().getName() + " does not override `withBeanProperties()`, needs to");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract BeanDeserializerBase asArrayDeserializer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resolve(DeserializationContext ctxt) throws JsonMappingException {
/*      */     SettableBeanProperty[] creatorProps;
/*  438 */     ExternalTypeHandler.Builder extTypes = null;
/*      */ 
/*      */ 
/*      */     
/*  442 */     if (this._valueInstantiator.canCreateFromObjectWith()) {
/*  443 */       creatorProps = this._valueInstantiator.getFromObjectArguments(ctxt.getConfig());
/*      */     } else {
/*  445 */       creatorProps = null;
/*      */     } 
/*  447 */     UnwrappedPropertyHandler unwrapped = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  455 */     for (SettableBeanProperty prop : this._beanProperties) {
/*  456 */       if (!prop.hasValueDeserializer()) {
/*      */         
/*  458 */         JsonDeserializer<?> deser = findConvertingDeserializer(ctxt, prop);
/*  459 */         if (deser == null) {
/*  460 */           deser = ctxt.findNonContextualValueDeserializer(prop.getType());
/*      */         }
/*  462 */         SettableBeanProperty newProp = prop.withValueDeserializer(deser);
/*  463 */         _replaceProperty(this._beanProperties, creatorProps, prop, newProp);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  468 */     for (SettableBeanProperty origProp : this._beanProperties) {
/*  469 */       SettableBeanProperty prop = origProp;
/*  470 */       JsonDeserializer<?> deser = prop.getValueDeserializer();
/*  471 */       deser = ctxt.handlePrimaryContextualization(deser, (BeanProperty)prop, prop.getType());
/*  472 */       prop = prop.withValueDeserializer(deser);
/*      */       
/*  474 */       prop = _resolveManagedReferenceProperty(ctxt, prop);
/*      */ 
/*      */       
/*  477 */       if (!(prop instanceof ManagedReferenceProperty)) {
/*  478 */         prop = _resolvedObjectIdProperty(ctxt, prop);
/*      */       }
/*      */       
/*  481 */       NameTransformer xform = _findPropertyUnwrapper(ctxt, prop);
/*  482 */       if (xform != null) {
/*  483 */         JsonDeserializer<Object> orig = prop.getValueDeserializer();
/*  484 */         JsonDeserializer<Object> unwrapping = orig.unwrappingDeserializer(xform);
/*  485 */         if (unwrapping != orig && unwrapping != null) {
/*  486 */           prop = prop.withValueDeserializer(unwrapping);
/*  487 */           if (unwrapped == null) {
/*  488 */             unwrapped = new UnwrappedPropertyHandler();
/*      */           }
/*  490 */           unwrapped.addProperty(prop);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  495 */           this._beanProperties.remove(prop);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/*  502 */       PropertyMetadata md = prop.getMetadata();
/*  503 */       prop = _resolveMergeAndNullSettings(ctxt, prop, md);
/*      */ 
/*      */       
/*  506 */       prop = _resolveInnerClassValuedProperty(ctxt, prop);
/*  507 */       if (prop != origProp) {
/*  508 */         _replaceProperty(this._beanProperties, creatorProps, origProp, prop);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  513 */       if (prop.hasValueTypeDeserializer()) {
/*  514 */         TypeDeserializer typeDeser = prop.getValueTypeDeserializer();
/*  515 */         if (typeDeser.getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
/*  516 */           if (extTypes == null) {
/*  517 */             extTypes = ExternalTypeHandler.builder(this._beanType);
/*      */           }
/*  519 */           extTypes.addExternal(prop, typeDeser);
/*      */           
/*  521 */           this._beanProperties.remove(prop);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  527 */     if (this._anySetter != null && !this._anySetter.hasValueDeserializer()) {
/*  528 */       this._anySetter = this._anySetter.withValueDeserializer(findDeserializer(ctxt, this._anySetter.getType(), this._anySetter.getProperty()));
/*      */     }
/*      */ 
/*      */     
/*  532 */     if (this._valueInstantiator.canCreateUsingDelegate()) {
/*  533 */       JavaType delegateType = this._valueInstantiator.getDelegateType(ctxt.getConfig());
/*  534 */       if (delegateType == null) {
/*  535 */         ctxt.reportBadDefinition(this._beanType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", new Object[] { this._beanType, this._valueInstantiator.getClass().getName() }));
/*      */       }
/*      */ 
/*      */       
/*  539 */       this._delegateDeserializer = _findDelegateDeserializer(ctxt, delegateType, this._valueInstantiator.getDelegateCreator());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  544 */     if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
/*  545 */       JavaType delegateType = this._valueInstantiator.getArrayDelegateType(ctxt.getConfig());
/*  546 */       if (delegateType == null) {
/*  547 */         ctxt.reportBadDefinition(this._beanType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", new Object[] { this._beanType, this._valueInstantiator.getClass().getName() }));
/*      */       }
/*      */ 
/*      */       
/*  551 */       this._arrayDelegateDeserializer = _findDelegateDeserializer(ctxt, delegateType, this._valueInstantiator.getArrayDelegateCreator());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  556 */     if (creatorProps != null) {
/*  557 */       this._propertyBasedCreator = PropertyBasedCreator.construct(ctxt, this._valueInstantiator, creatorProps, this._beanProperties);
/*      */     }
/*      */ 
/*      */     
/*  561 */     if (extTypes != null) {
/*      */ 
/*      */       
/*  564 */       this._externalTypeIdHandler = extTypes.build(this._beanProperties);
/*      */       
/*  566 */       this._nonStandardCreation = true;
/*      */     } 
/*      */     
/*  569 */     this._unwrappedPropertyHandler = unwrapped;
/*  570 */     if (unwrapped != null) {
/*  571 */       this._nonStandardCreation = true;
/*      */     }
/*      */     
/*  574 */     this._vanillaProcessing = (this._vanillaProcessing && !this._nonStandardCreation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _replaceProperty(BeanPropertyMap props, SettableBeanProperty[] creatorProps, SettableBeanProperty origProp, SettableBeanProperty newProp) {
/*  583 */     props.replace(newProp);
/*      */     
/*  585 */     if (creatorProps != null)
/*      */     {
/*      */       
/*  588 */       for (int i = 0, len = creatorProps.length; i < len; i++) {
/*  589 */         if (creatorProps[i] == origProp) {
/*  590 */           creatorProps[i] = newProp;
/*      */           return;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JsonDeserializer<Object> _findDelegateDeserializer(DeserializationContext ctxt, JavaType delegateType, AnnotatedWithParams delegateCreator) throws JsonMappingException {
/*  608 */     BeanProperty.Std property = new BeanProperty.Std(TEMP_PROPERTY_NAME, delegateType, null, (AnnotatedMember)delegateCreator, PropertyMetadata.STD_OPTIONAL);
/*      */ 
/*      */ 
/*      */     
/*  612 */     TypeDeserializer td = (TypeDeserializer)delegateType.getTypeHandler();
/*  613 */     if (td == null) {
/*  614 */       td = ctxt.getConfig().findTypeDeserializer(delegateType);
/*      */     }
/*  616 */     JsonDeserializer<Object> dd = findDeserializer(ctxt, delegateType, (BeanProperty)property);
/*  617 */     if (td != null) {
/*  618 */       td = td.forProperty((BeanProperty)property);
/*  619 */       return (JsonDeserializer<Object>)new TypeWrappedDeserializer(td, dd);
/*      */     } 
/*  621 */     return dd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext ctxt, SettableBeanProperty prop) throws JsonMappingException {
/*  639 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/*  640 */     if (intr != null) {
/*  641 */       Object convDef = intr.findDeserializationConverter((Annotated)prop.getMember());
/*  642 */       if (convDef != null) {
/*  643 */         Converter<Object, Object> conv = ctxt.converterInstance((Annotated)prop.getMember(), convDef);
/*  644 */         JavaType delegateType = conv.getInputType(ctxt.getTypeFactory());
/*      */ 
/*      */         
/*  647 */         JsonDeserializer<?> deser = ctxt.findNonContextualValueDeserializer(delegateType);
/*  648 */         return (JsonDeserializer<Object>)new StdDelegatingDeserializer(conv, delegateType, deser);
/*      */       } 
/*      */     } 
/*  651 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/*  665 */     ObjectIdReader oir = this._objectIdReader;
/*      */ 
/*      */     
/*  668 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/*  669 */     AnnotatedMember accessor = _neitherNull(property, intr) ? property.getMember() : null;
/*  670 */     if (accessor != null) {
/*  671 */       ObjectIdInfo objectIdInfo = intr.findObjectIdInfo((Annotated)accessor);
/*  672 */       if (objectIdInfo != null) {
/*      */         JavaType idType; SettableBeanProperty idProp; ObjectIdGenerator<?> idGen;
/*  674 */         objectIdInfo = intr.findObjectReferenceInfo((Annotated)accessor, objectIdInfo);
/*      */         
/*  676 */         Class<?> implClass = objectIdInfo.getGeneratorType();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  681 */         ObjectIdResolver resolver = ctxt.objectIdResolverInstance((Annotated)accessor, objectIdInfo);
/*  682 */         if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
/*  683 */           PropertyName propName = objectIdInfo.getPropertyName();
/*  684 */           idProp = findProperty(propName);
/*  685 */           if (idProp == null) {
/*  686 */             ctxt.reportBadDefinition(this._beanType, String.format("Invalid Object Id definition for %s: cannot find property with name '%s'", new Object[] { handledType().getName(), propName }));
/*      */           }
/*      */ 
/*      */           
/*  690 */           idType = idProp.getType();
/*  691 */           PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
/*      */         } else {
/*  693 */           JavaType type = ctxt.constructType(implClass);
/*  694 */           idType = ctxt.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
/*  695 */           idProp = null;
/*  696 */           idGen = ctxt.objectIdGeneratorInstance((Annotated)accessor, objectIdInfo);
/*      */         } 
/*  698 */         JsonDeserializer<?> deser = ctxt.findRootValueDeserializer(idType);
/*  699 */         oir = ObjectIdReader.construct(idType, objectIdInfo.getPropertyName(), idGen, deser, idProp, resolver);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  704 */     BeanDeserializerBase contextual = this;
/*  705 */     if (oir != null && oir != this._objectIdReader) {
/*  706 */       contextual = contextual.withObjectIdReader(oir);
/*      */     }
/*      */     
/*  709 */     if (accessor != null) {
/*  710 */       JsonIgnoreProperties.Value ignorals = intr.findPropertyIgnorals((Annotated)accessor);
/*  711 */       if (ignorals != null) {
/*  712 */         Set<String> ignored = ignorals.findIgnoredForDeserialization();
/*  713 */         if (!ignored.isEmpty()) {
/*  714 */           Set<String> prev = contextual._ignorableProps;
/*  715 */           if (prev != null && !prev.isEmpty()) {
/*  716 */             ignored = new HashSet<>(ignored);
/*  717 */             ignored.addAll(prev);
/*      */           } 
/*  719 */           contextual = contextual.withIgnorableProperties(ignored);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  725 */     JsonFormat.Value format = findFormatOverrides(ctxt, property, handledType());
/*  726 */     JsonFormat.Shape shape = null;
/*  727 */     if (format != null) {
/*  728 */       if (format.hasShape()) {
/*  729 */         shape = format.getShape();
/*      */       }
/*      */       
/*  732 */       Boolean B = format.getFeature(JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
/*  733 */       if (B != null) {
/*  734 */         BeanPropertyMap propsOrig = this._beanProperties;
/*  735 */         BeanPropertyMap props = propsOrig.withCaseInsensitivity(B.booleanValue());
/*  736 */         if (props != propsOrig) {
/*  737 */           contextual = contextual.withBeanProperties(props);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  742 */     if (shape == null) {
/*  743 */       shape = this._serializationShape;
/*      */     }
/*  745 */     if (shape == JsonFormat.Shape.ARRAY) {
/*  746 */       contextual = contextual.asArrayDeserializer();
/*      */     }
/*  748 */     return (JsonDeserializer<?>)contextual;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext ctxt, SettableBeanProperty prop) throws JsonMappingException {
/*  759 */     String refName = prop.getManagedReferenceName();
/*  760 */     if (refName == null) {
/*  761 */       return prop;
/*      */     }
/*  763 */     JsonDeserializer<?> valueDeser = prop.getValueDeserializer();
/*  764 */     SettableBeanProperty backProp = valueDeser.findBackReference(refName);
/*  765 */     if (backProp == null) {
/*  766 */       ctxt.reportBadDefinition(this._beanType, String.format("Cannot handle managed/back reference '%s': no back reference property found from type %s", new Object[] { refName, prop.getType() }));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  771 */     JavaType referredType = this._beanType;
/*  772 */     JavaType backRefType = backProp.getType();
/*  773 */     boolean isContainer = prop.getType().isContainerType();
/*  774 */     if (!backRefType.getRawClass().isAssignableFrom(referredType.getRawClass())) {
/*  775 */       ctxt.reportBadDefinition(this._beanType, String.format("Cannot handle managed/back reference '%s': back reference type (%s) not compatible with managed type (%s)", new Object[] { refName, backRefType.getRawClass().getName(), referredType.getRawClass().getName() }));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  780 */     return (SettableBeanProperty)new ManagedReferenceProperty(prop, refName, backProp, isContainer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SettableBeanProperty _resolvedObjectIdProperty(DeserializationContext ctxt, SettableBeanProperty prop) throws JsonMappingException {
/*  790 */     ObjectIdInfo objectIdInfo = prop.getObjectIdInfo();
/*  791 */     JsonDeserializer<Object> valueDeser = prop.getValueDeserializer();
/*  792 */     ObjectIdReader objectIdReader = (valueDeser == null) ? null : valueDeser.getObjectIdReader();
/*  793 */     if (objectIdInfo == null && objectIdReader == null) {
/*  794 */       return prop;
/*      */     }
/*  796 */     return (SettableBeanProperty)new ObjectIdReferenceProperty(prop, objectIdInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NameTransformer _findPropertyUnwrapper(DeserializationContext ctxt, SettableBeanProperty prop) throws JsonMappingException {
/*  807 */     AnnotatedMember am = prop.getMember();
/*  808 */     if (am != null) {
/*  809 */       NameTransformer unwrapper = ctxt.getAnnotationIntrospector().findUnwrappingNameTransformer(am);
/*  810 */       if (unwrapper != null) {
/*      */ 
/*      */         
/*  813 */         if (prop instanceof CreatorProperty) {
/*  814 */           ctxt.reportBadDefinition(getValueType(), String.format("Cannot define Creator property \"%s\" as `@JsonUnwrapped`: combination not yet supported", new Object[] { prop.getName() }));
/*      */         }
/*      */ 
/*      */         
/*  818 */         return unwrapper;
/*      */       } 
/*      */     } 
/*  821 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext ctxt, SettableBeanProperty prop) {
/*  834 */     JsonDeserializer<Object> deser = prop.getValueDeserializer();
/*      */     
/*  836 */     if (deser instanceof BeanDeserializerBase) {
/*  837 */       BeanDeserializerBase bd = (BeanDeserializerBase)deser;
/*  838 */       ValueInstantiator vi = bd.getValueInstantiator();
/*  839 */       if (!vi.canCreateUsingDefault()) {
/*  840 */         Class<?> valueClass = prop.getType().getRawClass();
/*      */         
/*  842 */         Class<?> enclosing = ClassUtil.getOuterClass(valueClass);
/*      */         
/*  844 */         if (enclosing != null && enclosing == this._beanType.getRawClass()) {
/*  845 */           for (Constructor<?> ctor : valueClass.getConstructors()) {
/*  846 */             Class<?>[] paramTypes = ctor.getParameterTypes();
/*  847 */             if (paramTypes.length == 1 && 
/*  848 */               enclosing.equals(paramTypes[0])) {
/*  849 */               if (ctxt.canOverrideAccessModifiers()) {
/*  850 */                 ClassUtil.checkAndFixAccess(ctor, ctxt.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*      */               }
/*  852 */               return (SettableBeanProperty)new InnerClassProperty(prop, ctor);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  859 */     return prop;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SettableBeanProperty _resolveMergeAndNullSettings(DeserializationContext ctxt, SettableBeanProperty prop, PropertyMetadata propMetadata) throws JsonMappingException {
/*      */     MergingSettableBeanProperty mergingSettableBeanProperty;
/*      */     SettableBeanProperty settableBeanProperty;
/*  867 */     PropertyMetadata.MergeInfo merge = propMetadata.getMergeInfo();
/*      */     
/*  869 */     if (merge != null) {
/*  870 */       JsonDeserializer<?> valueDeser = prop.getValueDeserializer();
/*  871 */       Boolean mayMerge = valueDeser.supportsUpdate(ctxt.getConfig());
/*      */       
/*  873 */       if (mayMerge == null) {
/*      */         
/*  875 */         if (merge.fromDefaults) {
/*  876 */           return prop;
/*      */         }
/*  878 */       } else if (!mayMerge.booleanValue()) {
/*  879 */         if (!merge.fromDefaults)
/*      */         {
/*      */           
/*  882 */           ctxt.reportBadMerge(valueDeser);
/*      */         }
/*  884 */         return prop;
/*      */       } 
/*      */       
/*  887 */       AnnotatedMember accessor = merge.getter;
/*  888 */       accessor.fixAccess(ctxt.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*  889 */       if (!(prop instanceof software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.SetterlessProperty)) {
/*  890 */         mergingSettableBeanProperty = MergingSettableBeanProperty.construct(prop, accessor);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  895 */     NullValueProvider nuller = findValueNullProvider(ctxt, (SettableBeanProperty)mergingSettableBeanProperty, propMetadata);
/*  896 */     if (nuller != null) {
/*  897 */       settableBeanProperty = mergingSettableBeanProperty.withNullProvider(nuller);
/*      */     }
/*  899 */     return settableBeanProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessPattern getNullAccessPattern() {
/*  911 */     return AccessPattern.ALWAYS_NULL;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessPattern getEmptyAccessPattern() {
/*  917 */     return AccessPattern.DYNAMIC;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/*      */     try {
/*  924 */       return this._valueInstantiator.createUsingDefault(ctxt);
/*  925 */     } catch (IOException e) {
/*  926 */       return ClassUtil.throwAsMappingException(ctxt, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCachable() {
/*  937 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean supportsUpdate(DeserializationConfig config) {
/*  944 */     return Boolean.TRUE;
/*      */   }
/*      */ 
/*      */   
/*      */   public Class<?> handledType() {
/*  949 */     return this._beanType.getRawClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectIdReader getObjectIdReader() {
/*  959 */     return this._objectIdReader;
/*      */   }
/*      */   
/*      */   public boolean hasProperty(String propertyName) {
/*  963 */     return (this._beanProperties.find(propertyName) != null);
/*      */   }
/*      */   
/*      */   public boolean hasViews() {
/*  967 */     return this._needViewProcesing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPropertyCount() {
/*  974 */     return this._beanProperties.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public Collection<Object> getKnownPropertyNames() {
/*  979 */     ArrayList<Object> names = new ArrayList();
/*  980 */     for (SettableBeanProperty prop : this._beanProperties) {
/*  981 */       names.add(prop.getName());
/*      */     }
/*  983 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final Class<?> getBeanClass() {
/*  990 */     return this._beanType.getRawClass();
/*      */   }
/*      */   public JavaType getValueType() {
/*  993 */     return this._beanType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<SettableBeanProperty> properties() {
/* 1004 */     if (this._beanProperties == null) {
/* 1005 */       throw new IllegalStateException("Can only call after BeanDeserializer has been resolved");
/*      */     }
/* 1007 */     return this._beanProperties.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<SettableBeanProperty> creatorProperties() {
/* 1019 */     if (this._propertyBasedCreator == null) {
/* 1020 */       return Collections.<SettableBeanProperty>emptyList().iterator();
/*      */     }
/* 1022 */     return this._propertyBasedCreator.properties().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SettableBeanProperty findProperty(PropertyName propertyName) {
/* 1028 */     return findProperty(propertyName.getSimpleName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SettableBeanProperty findProperty(String propertyName) {
/* 1040 */     SettableBeanProperty prop = (this._beanProperties == null) ? null : this._beanProperties.find(propertyName);
/*      */     
/* 1042 */     if (_neitherNull(prop, this._propertyBasedCreator)) {
/* 1043 */       prop = this._propertyBasedCreator.findCreatorProperty(propertyName);
/*      */     }
/* 1045 */     return prop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SettableBeanProperty findProperty(int propertyIndex) {
/* 1060 */     SettableBeanProperty prop = (this._beanProperties == null) ? null : this._beanProperties.find(propertyIndex);
/*      */     
/* 1062 */     if (_neitherNull(prop, this._propertyBasedCreator)) {
/* 1063 */       prop = this._propertyBasedCreator.findCreatorProperty(propertyIndex);
/*      */     }
/* 1065 */     return prop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SettableBeanProperty findBackReference(String logicalName) {
/* 1075 */     if (this._backRefs == null) {
/* 1076 */       return null;
/*      */     }
/* 1078 */     return this._backRefs.get(logicalName);
/*      */   }
/*      */ 
/*      */   
/*      */   public ValueInstantiator getValueInstantiator() {
/* 1083 */     return this._valueInstantiator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceProperty(SettableBeanProperty original, SettableBeanProperty replacement) {
/* 1107 */     this._beanProperties.replace(replacement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 1129 */     if (this._objectIdReader != null) {
/*      */       
/* 1131 */       if (p.canReadObjectId()) {
/* 1132 */         Object id = p.getObjectId();
/* 1133 */         if (id != null) {
/* 1134 */           Object ob = typeDeserializer.deserializeTypedFromObject(p, ctxt);
/* 1135 */           return _handleTypedObjectId(p, ctxt, ob, id);
/*      */         } 
/*      */       } 
/*      */       
/* 1139 */       JsonToken t = p.getCurrentToken();
/* 1140 */       if (t != null) {
/*      */         
/* 1142 */         if (t.isScalarValue()) {
/* 1143 */           return deserializeFromObjectId(p, ctxt);
/*      */         }
/*      */         
/* 1146 */         if (t == JsonToken.START_OBJECT) {
/* 1147 */           t = p.nextToken();
/*      */         }
/* 1149 */         if (t == JsonToken.FIELD_NAME && this._objectIdReader.maySerializeAsObject() && this._objectIdReader.isValidReferencePropertyName(p.getCurrentName(), p))
/*      */         {
/* 1151 */           return deserializeFromObjectId(p, ctxt);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1156 */     return typeDeserializer.deserializeTypedFromObject(p, ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _handleTypedObjectId(JsonParser p, DeserializationContext ctxt, Object pojo, Object rawId) throws IOException {
/*      */     Object id;
/* 1171 */     JsonDeserializer<Object> idDeser = this._objectIdReader.getDeserializer();
/*      */ 
/*      */ 
/*      */     
/* 1175 */     if (idDeser.handledType() == rawId.getClass()) {
/*      */       
/* 1177 */       id = rawId;
/*      */     } else {
/* 1179 */       id = _convertObjectId(p, ctxt, rawId, idDeser);
/*      */     } 
/*      */     
/* 1182 */     ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator, this._objectIdReader.resolver);
/* 1183 */     roid.bindItem(pojo);
/*      */     
/* 1185 */     SettableBeanProperty idProp = this._objectIdReader.idProperty;
/* 1186 */     if (idProp != null) {
/* 1187 */       return idProp.setAndReturn(pojo, id);
/*      */     }
/* 1189 */     return pojo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _convertObjectId(JsonParser p, DeserializationContext ctxt, Object rawId, JsonDeserializer<Object> idDeser) throws IOException {
/* 1205 */     TokenBuffer buf = new TokenBuffer(p, ctxt);
/* 1206 */     if (rawId instanceof String) {
/* 1207 */       buf.writeString((String)rawId);
/* 1208 */     } else if (rawId instanceof Long) {
/* 1209 */       buf.writeNumber(((Long)rawId).longValue());
/* 1210 */     } else if (rawId instanceof Integer) {
/* 1211 */       buf.writeNumber(((Integer)rawId).intValue());
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1218 */       buf.writeObject(rawId);
/*      */     } 
/* 1220 */     JsonParser bufParser = buf.asParser();
/* 1221 */     bufParser.nextToken();
/* 1222 */     return idDeser.deserialize(bufParser, ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeWithObjectId(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1235 */     return deserializeFromObject(p, ctxt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeFromObjectId(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1244 */     Object id = this._objectIdReader.readObjectReference(p, ctxt);
/* 1245 */     ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator, this._objectIdReader.resolver);
/*      */     
/* 1247 */     Object pojo = roid.resolve();
/* 1248 */     if (pojo == null) {
/* 1249 */       throw new UnresolvedForwardReference(p, "Could not resolve Object Id [" + id + "] (for " + this._beanType + ").", p.getCurrentLocation(), roid);
/*      */     }
/*      */ 
/*      */     
/* 1253 */     return pojo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeFromObjectUsingNonDefault(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1259 */     JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
/* 1260 */     if (delegateDeser != null) {
/* 1261 */       return this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */     }
/*      */     
/* 1264 */     if (this._propertyBasedCreator != null) {
/* 1265 */       return _deserializeUsingPropertyBased(p, ctxt);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1270 */     Class<?> raw = this._beanType.getRawClass();
/* 1271 */     if (ClassUtil.isNonStaticInnerClass(raw)) {
/* 1272 */       return ctxt.handleMissingInstantiator(raw, null, p, "can only instantiate non-static inner class by using default, no-argument constructor", new Object[0]);
/*      */     }
/*      */     
/* 1275 */     return ctxt.handleMissingInstantiator(raw, getValueInstantiator(), p, "cannot deserialize from Object value (no delegate- or property-based Creator)", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromNumber(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1286 */     if (this._objectIdReader != null) {
/* 1287 */       return deserializeFromObjectId(p, ctxt);
/*      */     }
/* 1289 */     JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
/* 1290 */     JsonParser.NumberType nt = p.getNumberType();
/* 1291 */     if (nt == JsonParser.NumberType.INT) {
/* 1292 */       if (delegateDeser != null && 
/* 1293 */         !this._valueInstantiator.canCreateFromInt()) {
/* 1294 */         Object bean = this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */         
/* 1296 */         if (this._injectables != null) {
/* 1297 */           injectValues(ctxt, bean);
/*      */         }
/* 1299 */         return bean;
/*      */       } 
/*      */       
/* 1302 */       return this._valueInstantiator.createFromInt(ctxt, p.getIntValue());
/*      */     } 
/* 1304 */     if (nt == JsonParser.NumberType.LONG) {
/* 1305 */       if (delegateDeser != null && 
/* 1306 */         !this._valueInstantiator.canCreateFromInt()) {
/* 1307 */         Object bean = this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */         
/* 1309 */         if (this._injectables != null) {
/* 1310 */           injectValues(ctxt, bean);
/*      */         }
/* 1312 */         return bean;
/*      */       } 
/*      */       
/* 1315 */       return this._valueInstantiator.createFromLong(ctxt, p.getLongValue());
/*      */     } 
/*      */     
/* 1318 */     if (delegateDeser != null) {
/* 1319 */       Object bean = this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */       
/* 1321 */       if (this._injectables != null) {
/* 1322 */         injectValues(ctxt, bean);
/*      */       }
/* 1324 */       return bean;
/*      */     } 
/* 1326 */     return ctxt.handleMissingInstantiator(handledType(), getValueInstantiator(), p, "no suitable creator method found to deserialize from Number value (%s)", new Object[] { p.getNumberValue() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromString(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1335 */     if (this._objectIdReader != null) {
/* 1336 */       return deserializeFromObjectId(p, ctxt);
/*      */     }
/*      */ 
/*      */     
/* 1340 */     JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
/* 1341 */     if (delegateDeser != null && 
/* 1342 */       !this._valueInstantiator.canCreateFromString()) {
/* 1343 */       Object bean = this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */       
/* 1345 */       if (this._injectables != null) {
/* 1346 */         injectValues(ctxt, bean);
/*      */       }
/* 1348 */       return bean;
/*      */     } 
/*      */     
/* 1351 */     return this._valueInstantiator.createFromString(ctxt, p.getText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromDouble(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1360 */     JsonParser.NumberType t = p.getNumberType();
/*      */     
/* 1362 */     if (t == JsonParser.NumberType.DOUBLE || t == JsonParser.NumberType.FLOAT) {
/* 1363 */       JsonDeserializer<Object> jsonDeserializer = _delegateDeserializer();
/* 1364 */       if (jsonDeserializer != null && 
/* 1365 */         !this._valueInstantiator.canCreateFromDouble()) {
/* 1366 */         Object bean = this._valueInstantiator.createUsingDelegate(ctxt, jsonDeserializer.deserialize(p, ctxt));
/*      */         
/* 1368 */         if (this._injectables != null) {
/* 1369 */           injectValues(ctxt, bean);
/*      */         }
/* 1371 */         return bean;
/*      */       } 
/*      */       
/* 1374 */       return this._valueInstantiator.createFromDouble(ctxt, p.getDoubleValue());
/*      */     } 
/*      */     
/* 1377 */     JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
/* 1378 */     if (delegateDeser != null) {
/* 1379 */       return this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */     }
/*      */     
/* 1382 */     return ctxt.handleMissingInstantiator(handledType(), getValueInstantiator(), p, "no suitable creator method found to deserialize from Number value (%s)", new Object[] { p.getNumberValue() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromBoolean(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1392 */     JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
/* 1393 */     if (delegateDeser != null && 
/* 1394 */       !this._valueInstantiator.canCreateFromBoolean()) {
/* 1395 */       Object bean = this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */       
/* 1397 */       if (this._injectables != null) {
/* 1398 */         injectValues(ctxt, bean);
/*      */       }
/* 1400 */       return bean;
/*      */     } 
/*      */     
/* 1403 */     boolean value = (p.getCurrentToken() == JsonToken.VALUE_TRUE);
/* 1404 */     return this._valueInstantiator.createFromBoolean(ctxt, value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1410 */     JsonDeserializer<Object> delegateDeser = this._arrayDelegateDeserializer;
/*      */     
/* 1412 */     if (delegateDeser != null || (delegateDeser = this._delegateDeserializer) != null) {
/* 1413 */       Object bean = this._valueInstantiator.createUsingArrayDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */       
/* 1415 */       if (this._injectables != null) {
/* 1416 */         injectValues(ctxt, bean);
/*      */       }
/* 1418 */       return bean;
/*      */     } 
/* 1420 */     if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/* 1421 */       JsonToken t = p.nextToken();
/* 1422 */       if (t == JsonToken.END_ARRAY && ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
/* 1423 */         return null;
/*      */       }
/* 1425 */       Object value = deserialize(p, ctxt);
/* 1426 */       if (p.nextToken() != JsonToken.END_ARRAY) {
/* 1427 */         handleMissingEndArrayForSingle(p, ctxt);
/*      */       }
/* 1429 */       return value;
/*      */     } 
/* 1431 */     if (ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
/* 1432 */       JsonToken t = p.nextToken();
/* 1433 */       if (t == JsonToken.END_ARRAY) {
/* 1434 */         return null;
/*      */       }
/* 1436 */       return ctxt.handleUnexpectedToken(handledType(), JsonToken.START_ARRAY, p, null, new Object[0]);
/*      */     } 
/*      */     
/* 1439 */     return ctxt.handleUnexpectedToken(handledType(), p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromEmbedded(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 1447 */     if (this._objectIdReader != null) {
/* 1448 */       return deserializeFromObjectId(p, ctxt);
/*      */     }
/*      */     
/* 1451 */     JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
/* 1452 */     if (delegateDeser != null && 
/* 1453 */       !this._valueInstantiator.canCreateFromString()) {
/* 1454 */       Object bean = this._valueInstantiator.createUsingDelegate(ctxt, delegateDeser.deserialize(p, ctxt));
/*      */       
/* 1456 */       if (this._injectables != null) {
/* 1457 */         injectValues(ctxt, bean);
/*      */       }
/* 1459 */       return bean;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1467 */     Object value = p.getEmbeddedObject();
/* 1468 */     if (value != null && 
/* 1469 */       !this._beanType.getClass().isInstance(value))
/*      */     {
/* 1471 */       value = ctxt.handleWeirdNativeValue(this._beanType, value, p);
/*      */     }
/*      */     
/* 1474 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonDeserializer<Object> _delegateDeserializer() {
/* 1481 */     JsonDeserializer<Object> deser = this._delegateDeserializer;
/* 1482 */     if (deser == null) {
/* 1483 */       deser = this._arrayDelegateDeserializer;
/*      */     }
/* 1485 */     return deser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void injectValues(DeserializationContext ctxt, Object bean) throws IOException {
/* 1497 */     for (ValueInjector injector : this._injectables) {
/* 1498 */       injector.inject(ctxt, bean);
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
/*      */   protected Object handleUnknownProperties(DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws IOException {
/* 1513 */     unknownTokens.writeEndObject();
/*      */ 
/*      */     
/* 1516 */     JsonParser bufferParser = unknownTokens.asParser();
/* 1517 */     while (bufferParser.nextToken() != JsonToken.END_OBJECT) {
/* 1518 */       String propName = bufferParser.getCurrentName();
/*      */       
/* 1520 */       bufferParser.nextToken();
/* 1521 */       handleUnknownProperty(bufferParser, ctxt, bean, propName);
/*      */     } 
/* 1523 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleUnknownVanilla(JsonParser p, DeserializationContext ctxt, Object bean, String propName) throws IOException {
/* 1534 */     if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/* 1535 */       handleIgnoredProperty(p, ctxt, bean, propName);
/* 1536 */     } else if (this._anySetter != null) {
/*      */       
/*      */       try {
/* 1539 */         this._anySetter.deserializeAndSet(p, ctxt, bean, propName);
/* 1540 */       } catch (Exception e) {
/* 1541 */         wrapAndThrow(e, bean, propName, ctxt);
/*      */       } 
/*      */     } else {
/*      */       
/* 1545 */       handleUnknownProperty(p, ctxt, bean, propName);
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
/*      */   protected void handleUnknownProperty(JsonParser p, DeserializationContext ctxt, Object beanOrClass, String propName) throws IOException {
/* 1558 */     if (this._ignoreAllUnknown) {
/* 1559 */       p.skipChildren();
/*      */       return;
/*      */     } 
/* 1562 */     if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/* 1563 */       handleIgnoredProperty(p, ctxt, beanOrClass, propName);
/*      */     }
/*      */ 
/*      */     
/* 1567 */     super.handleUnknownProperty(p, ctxt, beanOrClass, propName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleIgnoredProperty(JsonParser p, DeserializationContext ctxt, Object beanOrClass, String propName) throws IOException {
/* 1580 */     if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)) {
/* 1581 */       throw IgnoredPropertyException.from(p, beanOrClass, propName, getKnownPropertyNames());
/*      */     }
/* 1583 */     p.skipChildren();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object handlePolymorphic(JsonParser p, DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws IOException {
/* 1603 */     JsonDeserializer<Object> subDeser = _findSubclassDeserializer(ctxt, bean, unknownTokens);
/* 1604 */     if (subDeser != null) {
/* 1605 */       if (unknownTokens != null) {
/*      */         
/* 1607 */         unknownTokens.writeEndObject();
/* 1608 */         JsonParser p2 = unknownTokens.asParser();
/* 1609 */         p2.nextToken();
/* 1610 */         bean = subDeser.deserialize(p2, ctxt, bean);
/*      */       } 
/*      */       
/* 1613 */       if (p != null) {
/* 1614 */         bean = subDeser.deserialize(p, ctxt, bean);
/*      */       }
/* 1616 */       return bean;
/*      */     } 
/*      */     
/* 1619 */     if (unknownTokens != null) {
/* 1620 */       bean = handleUnknownProperties(ctxt, bean, unknownTokens);
/*      */     }
/*      */     
/* 1623 */     if (p != null) {
/* 1624 */       bean = deserialize(p, ctxt, bean);
/*      */     }
/* 1626 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws IOException {
/* 1640 */     synchronized (this) {
/* 1641 */       subDeser = (this._subDeserializers == null) ? null : this._subDeserializers.get(new ClassKey(bean.getClass()));
/*      */     } 
/* 1643 */     if (subDeser != null) {
/* 1644 */       return subDeser;
/*      */     }
/*      */     
/* 1647 */     JavaType type = ctxt.constructType(bean.getClass());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1654 */     JsonDeserializer<Object> subDeser = ctxt.findRootValueDeserializer(type);
/*      */     
/* 1656 */     if (subDeser != null) {
/* 1657 */       synchronized (this) {
/* 1658 */         if (this._subDeserializers == null) {
/* 1659 */           this._subDeserializers = new HashMap<>();
/*      */         }
/* 1661 */         this._subDeserializers.put(new ClassKey(bean.getClass()), subDeser);
/*      */       } 
/*      */     }
/* 1664 */     return subDeser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wrapAndThrow(Throwable t, Object bean, String fieldName, DeserializationContext ctxt) throws IOException {
/* 1689 */     throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(t, ctxt), bean, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Throwable throwOrReturnThrowable(Throwable t, DeserializationContext ctxt) throws IOException {
/* 1699 */     while (t instanceof java.lang.reflect.InvocationTargetException && t.getCause() != null) {
/* 1700 */       t = t.getCause();
/*      */     }
/*      */     
/* 1703 */     ClassUtil.throwIfError(t);
/* 1704 */     boolean wrap = (ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS));
/*      */     
/* 1706 */     if (t instanceof IOException) {
/* 1707 */       if (!wrap || !(t instanceof software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException)) {
/* 1708 */         throw (IOException)t;
/*      */       }
/* 1710 */     } else if (!wrap) {
/* 1711 */       ClassUtil.throwIfRTE(t);
/*      */     } 
/* 1713 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object wrapInstantiationProblem(Throwable t, DeserializationContext ctxt) throws IOException {
/* 1719 */     while (t instanceof java.lang.reflect.InvocationTargetException && t.getCause() != null) {
/* 1720 */       t = t.getCause();
/*      */     }
/*      */     
/* 1723 */     ClassUtil.throwIfError(t);
/* 1724 */     if (t instanceof IOException)
/*      */     {
/* 1726 */       throw (IOException)t;
/*      */     }
/* 1728 */     boolean wrap = (ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS));
/* 1729 */     if (!wrap) {
/* 1730 */       ClassUtil.throwIfRTE(t);
/*      */     }
/* 1732 */     return ctxt.handleInstantiationProblem(this._beanType.getRawClass(), null, t);
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\BeanDeserializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */