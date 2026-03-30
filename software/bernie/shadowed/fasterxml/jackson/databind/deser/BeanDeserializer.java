/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
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
/*      */ public class BeanDeserializer
/*      */   extends BeanDeserializerBase
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   protected transient Exception _nullFromCreator;
/*      */   private volatile transient NameTransformer _currentlyTransforming;
/*      */   
/*      */   public BeanDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, HashSet<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
/*   64 */     super(builder, beanDesc, properties, backRefs, ignorableProps, ignoreAllUnknown, hasViews);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BeanDeserializer(BeanDeserializerBase src) {
/*   73 */     super(src, src._ignoreAllUnknown);
/*      */   }
/*      */   
/*      */   protected BeanDeserializer(BeanDeserializerBase src, boolean ignoreAllUnknown) {
/*   77 */     super(src, ignoreAllUnknown);
/*      */   }
/*      */   
/*      */   protected BeanDeserializer(BeanDeserializerBase src, NameTransformer unwrapper) {
/*   81 */     super(src, unwrapper);
/*      */   }
/*      */   
/*      */   public BeanDeserializer(BeanDeserializerBase src, ObjectIdReader oir) {
/*   85 */     super(src, oir);
/*      */   }
/*      */   
/*      */   public BeanDeserializer(BeanDeserializerBase src, Set<String> ignorableProps) {
/*   89 */     super(src, ignorableProps);
/*      */   }
/*      */   
/*      */   public BeanDeserializer(BeanDeserializerBase src, BeanPropertyMap props) {
/*   93 */     super(src, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer transformer) {
/*  101 */     if (getClass() != BeanDeserializer.class) {
/*  102 */       return (JsonDeserializer<Object>)this;
/*      */     }
/*      */ 
/*      */     
/*  106 */     if (this._currentlyTransforming == transformer) {
/*  107 */       return (JsonDeserializer<Object>)this;
/*      */     }
/*  109 */     this._currentlyTransforming = transformer;
/*      */     
/*  111 */     try { return (JsonDeserializer<Object>)new BeanDeserializer(this, transformer); }
/*  112 */     finally { this._currentlyTransforming = null; }
/*      */   
/*      */   }
/*      */   
/*      */   public BeanDeserializer withObjectIdReader(ObjectIdReader oir) {
/*  117 */     return new BeanDeserializer(this, oir);
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanDeserializer withIgnorableProperties(Set<String> ignorableProps) {
/*  122 */     return new BeanDeserializer(this, ignorableProps);
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanDeserializerBase withBeanProperties(BeanPropertyMap props) {
/*  127 */     return new BeanDeserializer(this, props);
/*      */   }
/*      */ 
/*      */   
/*      */   protected BeanDeserializerBase asArrayDeserializer() {
/*  132 */     SettableBeanProperty[] props = this._beanProperties.getPropertiesInInsertionOrder();
/*  133 */     return (BeanDeserializerBase)new BeanAsArrayDeserializer(this, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  149 */     if (p.isExpectedStartObjectToken()) {
/*  150 */       if (this._vanillaProcessing) {
/*  151 */         return vanillaDeserialize(p, ctxt, p.nextToken());
/*      */       }
/*      */ 
/*      */       
/*  155 */       p.nextToken();
/*  156 */       if (this._objectIdReader != null) {
/*  157 */         return deserializeWithObjectId(p, ctxt);
/*      */       }
/*  159 */       return deserializeFromObject(p, ctxt);
/*      */     } 
/*  161 */     return _deserializeOther(p, ctxt, p.getCurrentToken());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object _deserializeOther(JsonParser p, DeserializationContext ctxt, JsonToken t) throws IOException {
/*  168 */     switch (t) {
/*      */       case VALUE_STRING:
/*  170 */         return deserializeFromString(p, ctxt);
/*      */       case VALUE_NUMBER_INT:
/*  172 */         return deserializeFromNumber(p, ctxt);
/*      */       case VALUE_NUMBER_FLOAT:
/*  174 */         return deserializeFromDouble(p, ctxt);
/*      */       case VALUE_EMBEDDED_OBJECT:
/*  176 */         return deserializeFromEmbedded(p, ctxt);
/*      */       case VALUE_TRUE:
/*      */       case VALUE_FALSE:
/*  179 */         return deserializeFromBoolean(p, ctxt);
/*      */       
/*      */       case VALUE_NULL:
/*  182 */         return deserializeFromNull(p, ctxt);
/*      */       
/*      */       case START_ARRAY:
/*  185 */         return deserializeFromArray(p, ctxt);
/*      */       case FIELD_NAME:
/*      */       case END_OBJECT:
/*  188 */         if (this._vanillaProcessing) {
/*  189 */           return vanillaDeserialize(p, ctxt, t);
/*      */         }
/*  191 */         if (this._objectIdReader != null) {
/*  192 */           return deserializeWithObjectId(p, ctxt);
/*      */         }
/*  194 */         return deserializeFromObject(p, ctxt);
/*      */     } 
/*      */     
/*  197 */     return ctxt.handleUnexpectedToken(handledType(), p);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   protected Object _missingToken(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  202 */     throw ctxt.endOfInputException(handledType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserialize(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/*      */     String propName;
/*  214 */     p.setCurrentValue(bean);
/*  215 */     if (this._injectables != null) {
/*  216 */       injectValues(ctxt, bean);
/*      */     }
/*  218 */     if (this._unwrappedPropertyHandler != null) {
/*  219 */       return deserializeWithUnwrapped(p, ctxt, bean);
/*      */     }
/*  221 */     if (this._externalTypeIdHandler != null) {
/*  222 */       return deserializeWithExternalTypeId(p, ctxt, bean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  227 */     if (p.isExpectedStartObjectToken()) {
/*  228 */       propName = p.nextFieldName();
/*  229 */       if (propName == null) {
/*  230 */         return bean;
/*      */       }
/*      */     }
/*  233 */     else if (p.hasTokenId(5)) {
/*  234 */       propName = p.getCurrentName();
/*      */     } else {
/*  236 */       return bean;
/*      */     } 
/*      */     
/*  239 */     if (this._needViewProcesing) {
/*  240 */       Class<?> view = ctxt.getActiveView();
/*  241 */       if (view != null) {
/*  242 */         return deserializeWithView(p, ctxt, bean, view);
/*      */       }
/*      */     } 
/*      */     while (true) {
/*  246 */       p.nextToken();
/*  247 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/*      */       
/*  249 */       if (prop != null) {
/*      */         try {
/*  251 */           prop.deserializeAndSet(p, ctxt, bean);
/*  252 */         } catch (Exception e) {
/*  253 */           wrapAndThrow(e, bean, propName, ctxt);
/*      */         } 
/*      */       } else {
/*      */         
/*  257 */         handleUnknownVanilla(p, ctxt, bean, propName);
/*  258 */       }  if ((propName = p.nextFieldName()) == null) {
/*  259 */         return bean;
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
/*      */   private final Object vanillaDeserialize(JsonParser p, DeserializationContext ctxt, JsonToken t) throws IOException {
/*  276 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/*      */     
/*  278 */     p.setCurrentValue(bean);
/*  279 */     if (p.hasTokenId(5)) {
/*  280 */       String propName = p.getCurrentName();
/*      */       do {
/*  282 */         p.nextToken();
/*  283 */         SettableBeanProperty prop = this._beanProperties.find(propName);
/*      */         
/*  285 */         if (prop != null)
/*      */         { try {
/*  287 */             prop.deserializeAndSet(p, ctxt, bean);
/*  288 */           } catch (Exception e) {
/*  289 */             wrapAndThrow(e, bean, propName, ctxt);
/*      */           }  }
/*      */         else
/*      */         
/*  293 */         { handleUnknownVanilla(p, ctxt, bean, propName); } 
/*  294 */       } while ((propName = p.nextFieldName()) != null);
/*      */     } 
/*  296 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  312 */     if (this._objectIdReader != null && this._objectIdReader.maySerializeAsObject() && 
/*  313 */       p.hasTokenId(5) && this._objectIdReader.isValidReferencePropertyName(p.getCurrentName(), p))
/*      */     {
/*  315 */       return deserializeFromObjectId(p, ctxt);
/*      */     }
/*      */     
/*  318 */     if (this._nonStandardCreation) {
/*  319 */       if (this._unwrappedPropertyHandler != null) {
/*  320 */         return deserializeWithUnwrapped(p, ctxt);
/*      */       }
/*  322 */       if (this._externalTypeIdHandler != null) {
/*  323 */         return deserializeWithExternalTypeId(p, ctxt);
/*      */       }
/*  325 */       Object object = deserializeFromObjectUsingNonDefault(p, ctxt);
/*  326 */       if (this._injectables != null) {
/*  327 */         injectValues(ctxt, object);
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
/*  341 */       return object;
/*      */     } 
/*  343 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/*      */     
/*  345 */     p.setCurrentValue(bean);
/*  346 */     if (p.canReadObjectId()) {
/*  347 */       Object id = p.getObjectId();
/*  348 */       if (id != null) {
/*  349 */         _handleTypedObjectId(p, ctxt, bean, id);
/*      */       }
/*      */     } 
/*  352 */     if (this._injectables != null) {
/*  353 */       injectValues(ctxt, bean);
/*      */     }
/*  355 */     if (this._needViewProcesing) {
/*  356 */       Class<?> view = ctxt.getActiveView();
/*  357 */       if (view != null) {
/*  358 */         return deserializeWithView(p, ctxt, bean, view);
/*      */       }
/*      */     } 
/*  361 */     if (p.hasTokenId(5)) {
/*  362 */       String propName = p.getCurrentName();
/*      */       do {
/*  364 */         p.nextToken();
/*  365 */         SettableBeanProperty prop = this._beanProperties.find(propName);
/*  366 */         if (prop != null)
/*      */         { try {
/*  368 */             prop.deserializeAndSet(p, ctxt, bean);
/*  369 */           } catch (Exception e) {
/*  370 */             wrapAndThrow(e, bean, propName, ctxt);
/*      */           }  }
/*      */         else
/*      */         
/*  374 */         { handleUnknownVanilla(p, ctxt, bean, propName); } 
/*  375 */       } while ((propName = p.nextFieldName()) != null);
/*      */     } 
/*  377 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object _deserializeUsingPropertyBased(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     Object object;
/*  393 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/*  394 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/*  395 */     TokenBuffer unknown = null;
/*  396 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*      */     
/*  398 */     JsonToken t = p.getCurrentToken();
/*  399 */     List<BeanReferring> referrings = null;
/*  400 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/*  401 */       object = p.getCurrentName();
/*  402 */       p.nextToken();
/*      */       
/*  404 */       if (!buffer.readIdProperty((String)object)) {
/*      */ 
/*      */ 
/*      */         
/*  408 */         SettableBeanProperty creatorProp = creator.findCreatorProperty((String)object);
/*  409 */         if (creatorProp != null) {
/*      */ 
/*      */           
/*  412 */           if (activeView != null && !creatorProp.visibleInView(activeView)) {
/*  413 */             p.skipChildren();
/*      */           } else {
/*      */             
/*  416 */             Object value = _deserializeWithErrorWrapping(p, ctxt, creatorProp);
/*  417 */             if (buffer.assignParameter(creatorProp, value)) {
/*  418 */               Object object1; p.nextToken();
/*      */               
/*      */               try {
/*  421 */                 object1 = creator.build(ctxt, buffer);
/*  422 */               } catch (Exception e) {
/*  423 */                 object1 = wrapInstantiationProblem(e, ctxt);
/*      */               } 
/*  425 */               if (object1 == null) {
/*  426 */                 return ctxt.handleInstantiationProblem(handledType(), null, _creatorReturnedNullException());
/*      */               }
/*      */ 
/*      */               
/*  430 */               p.setCurrentValue(object1);
/*      */ 
/*      */               
/*  433 */               if (object1.getClass() != this._beanType.getRawClass()) {
/*  434 */                 return handlePolymorphic(p, ctxt, object1, unknown);
/*      */               }
/*  436 */               if (unknown != null) {
/*  437 */                 object1 = handleUnknownProperties(ctxt, object1, unknown);
/*      */               }
/*      */               
/*  440 */               return deserialize(p, ctxt, object1);
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/*  445 */           SettableBeanProperty prop = this._beanProperties.find((String)object);
/*  446 */           if (prop != null) {
/*      */             try {
/*  448 */               buffer.bufferProperty(prop, _deserializeWithErrorWrapping(p, ctxt, prop));
/*  449 */             } catch (UnresolvedForwardReference reference) {
/*      */ 
/*      */ 
/*      */               
/*  453 */               BeanReferring referring = handleUnresolvedReference(ctxt, prop, buffer, reference);
/*      */               
/*  455 */               if (referrings == null) {
/*  456 */                 referrings = new ArrayList<>();
/*      */               }
/*  458 */               referrings.add(referring);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  463 */           else if (this._ignorableProps != null && this._ignorableProps.contains(object)) {
/*  464 */             handleIgnoredProperty(p, ctxt, handledType(), (String)object);
/*      */ 
/*      */           
/*      */           }
/*  468 */           else if (this._anySetter != null) {
/*      */             try {
/*  470 */               buffer.bufferAnyProperty(this._anySetter, (String)object, this._anySetter.deserialize(p, ctxt));
/*  471 */             } catch (Exception e) {
/*  472 */               wrapAndThrow(e, this._beanType.getRawClass(), (String)object, ctxt);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  477 */             if (unknown == null) {
/*  478 */               unknown = new TokenBuffer(p, ctxt);
/*      */             }
/*  480 */             unknown.writeFieldName((String)object);
/*  481 */             unknown.copyCurrentStructure(p);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     try {
/*  487 */       object = creator.build(ctxt, buffer);
/*  488 */     } catch (Exception e) {
/*  489 */       wrapInstantiationProblem(e, ctxt);
/*  490 */       object = null;
/*      */     } 
/*  492 */     if (referrings != null) {
/*  493 */       for (BeanReferring referring : referrings) {
/*  494 */         referring.setBean(object);
/*      */       }
/*      */     }
/*  497 */     if (unknown != null) {
/*      */       
/*  499 */       if (object.getClass() != this._beanType.getRawClass()) {
/*  500 */         return handlePolymorphic(null, ctxt, object, unknown);
/*      */       }
/*      */       
/*  503 */       return handleUnknownProperties(ctxt, object, unknown);
/*      */     } 
/*  505 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanReferring handleUnresolvedReference(DeserializationContext ctxt, SettableBeanProperty prop, PropertyValueBuffer buffer, UnresolvedForwardReference reference) throws JsonMappingException {
/*  516 */     BeanReferring referring = new BeanReferring(ctxt, reference, prop.getType(), buffer, prop);
/*      */     
/*  518 */     reference.getRoid().appendReferring(referring);
/*  519 */     return referring;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object _deserializeWithErrorWrapping(JsonParser p, DeserializationContext ctxt, SettableBeanProperty prop) throws IOException {
/*      */     try {
/*  527 */       return prop.deserialize(p, ctxt);
/*  528 */     } catch (Exception e) {
/*  529 */       wrapAndThrow(e, this._beanType.getRawClass(), prop.getName(), ctxt);
/*      */       
/*  531 */       return null;
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
/*      */   protected Object deserializeFromNull(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  549 */     if (p.requiresCustomCodec()) {
/*      */       
/*  551 */       TokenBuffer tb = new TokenBuffer(p, ctxt);
/*  552 */       tb.writeEndObject();
/*  553 */       JsonParser p2 = tb.asParser(p);
/*  554 */       p2.nextToken();
/*      */       
/*  556 */       Object ob = this._vanillaProcessing ? vanillaDeserialize(p2, ctxt, JsonToken.END_OBJECT) : deserializeFromObject(p2, ctxt);
/*      */       
/*  558 */       p2.close();
/*  559 */       return ob;
/*      */     } 
/*  561 */     return ctxt.handleUnexpectedToken(handledType(), p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object deserializeWithView(JsonParser p, DeserializationContext ctxt, Object bean, Class<?> activeView) throws IOException {
/*  574 */     if (p.hasTokenId(5)) {
/*  575 */       String propName = p.getCurrentName();
/*      */       do {
/*  577 */         p.nextToken();
/*      */         
/*  579 */         SettableBeanProperty prop = this._beanProperties.find(propName);
/*  580 */         if (prop != null)
/*  581 */         { if (!prop.visibleInView(activeView)) {
/*  582 */             p.skipChildren();
/*      */           } else {
/*      */             
/*      */             try {
/*  586 */               prop.deserializeAndSet(p, ctxt, bean);
/*  587 */             } catch (Exception e) {
/*  588 */               wrapAndThrow(e, bean, propName, ctxt);
/*      */             } 
/*      */           }  }
/*      */         else
/*  592 */         { handleUnknownVanilla(p, ctxt, bean, propName); } 
/*  593 */       } while ((propName = p.nextFieldName()) != null);
/*      */     } 
/*  595 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeWithUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  612 */     if (this._delegateDeserializer != null) {
/*  613 */       return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
/*      */     }
/*  615 */     if (this._propertyBasedCreator != null) {
/*  616 */       return deserializeUsingPropertyBasedWithUnwrapped(p, ctxt);
/*      */     }
/*  618 */     TokenBuffer tokens = new TokenBuffer(p, ctxt);
/*  619 */     tokens.writeStartObject();
/*  620 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/*      */ 
/*      */     
/*  623 */     p.setCurrentValue(bean);
/*      */     
/*  625 */     if (this._injectables != null) {
/*  626 */       injectValues(ctxt, bean);
/*      */     }
/*  628 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*  629 */     String propName = p.hasTokenId(5) ? p.getCurrentName() : null;
/*      */     
/*  631 */     for (; propName != null; propName = p.nextFieldName()) {
/*  632 */       p.nextToken();
/*  633 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/*  634 */       if (prop != null) {
/*  635 */         if (activeView != null && !prop.visibleInView(activeView)) {
/*  636 */           p.skipChildren();
/*      */         } else {
/*      */           
/*      */           try {
/*  640 */             prop.deserializeAndSet(p, ctxt, bean);
/*  641 */           } catch (Exception e) {
/*  642 */             wrapAndThrow(e, bean, propName, ctxt);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*  647 */       } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/*  648 */         handleIgnoredProperty(p, ctxt, bean, propName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  655 */       else if (this._anySetter == null) {
/*      */         
/*  657 */         tokens.writeFieldName(propName);
/*  658 */         tokens.copyCurrentStructure(p);
/*      */       }
/*      */       else {
/*      */         
/*  662 */         TokenBuffer b2 = TokenBuffer.asCopyOfValue(p);
/*  663 */         tokens.writeFieldName(propName);
/*  664 */         tokens.append(b2);
/*      */         try {
/*  666 */           this._anySetter.deserializeAndSet(b2.asParserOnFirstToken(), ctxt, bean, propName);
/*  667 */         } catch (Exception e) {
/*  668 */           wrapAndThrow(e, bean, propName, ctxt);
/*      */         } 
/*      */       } 
/*  671 */     }  tokens.writeEndObject();
/*  672 */     this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
/*  673 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeWithUnwrapped(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/*  681 */     JsonToken t = p.getCurrentToken();
/*  682 */     if (t == JsonToken.START_OBJECT) {
/*  683 */       t = p.nextToken();
/*      */     }
/*  685 */     TokenBuffer tokens = new TokenBuffer(p, ctxt);
/*  686 */     tokens.writeStartObject();
/*  687 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*  688 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/*  689 */       String propName = p.getCurrentName();
/*  690 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/*  691 */       p.nextToken();
/*  692 */       if (prop != null) {
/*  693 */         if (activeView != null && !prop.visibleInView(activeView)) {
/*  694 */           p.skipChildren();
/*      */         } else {
/*      */           
/*      */           try {
/*  698 */             prop.deserializeAndSet(p, ctxt, bean);
/*  699 */           } catch (Exception e) {
/*  700 */             wrapAndThrow(e, bean, propName, ctxt);
/*      */           }
/*      */         
/*      */         } 
/*  704 */       } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/*  705 */         handleIgnoredProperty(p, ctxt, bean, propName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  712 */       else if (this._anySetter == null) {
/*      */         
/*  714 */         tokens.writeFieldName(propName);
/*  715 */         tokens.copyCurrentStructure(p);
/*      */       } else {
/*      */         
/*  718 */         TokenBuffer b2 = TokenBuffer.asCopyOfValue(p);
/*  719 */         tokens.writeFieldName(propName);
/*  720 */         tokens.append(b2);
/*      */         try {
/*  722 */           this._anySetter.deserializeAndSet(b2.asParserOnFirstToken(), ctxt, bean, propName);
/*  723 */         } catch (Exception e) {
/*  724 */           wrapAndThrow(e, bean, propName, ctxt);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  729 */     tokens.writeEndObject();
/*  730 */     this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
/*  731 */     return bean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/*      */     Object bean;
/*  742 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/*  743 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/*      */     
/*  745 */     TokenBuffer tokens = new TokenBuffer(p, ctxt);
/*  746 */     tokens.writeStartObject();
/*      */     
/*  748 */     JsonToken t = p.getCurrentToken();
/*  749 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/*  750 */       String propName = p.getCurrentName();
/*  751 */       p.nextToken();
/*      */       
/*  753 */       SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
/*  754 */       if (creatorProp != null) {
/*      */         
/*  756 */         if (buffer.assignParameter(creatorProp, _deserializeWithErrorWrapping(p, ctxt, creatorProp))) {
/*      */           Object object;
/*  758 */           t = p.nextToken();
/*      */           
/*      */           try {
/*  761 */             object = creator.build(ctxt, buffer);
/*  762 */           } catch (Exception e) {
/*  763 */             object = wrapInstantiationProblem(e, ctxt);
/*      */           } 
/*      */           
/*  766 */           p.setCurrentValue(object);
/*      */           
/*  768 */           while (t == JsonToken.FIELD_NAME) {
/*  769 */             p.nextToken();
/*  770 */             tokens.copyCurrentStructure(p);
/*  771 */             t = p.nextToken();
/*      */           } 
/*  773 */           tokens.writeEndObject();
/*  774 */           if (object.getClass() != this._beanType.getRawClass()) {
/*      */ 
/*      */             
/*  777 */             ctxt.reportInputMismatch((BeanProperty)creatorProp, "Cannot create polymorphic instances with unwrapped values", new Object[0]);
/*      */             
/*  779 */             return null;
/*      */           } 
/*  781 */           return this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, object, tokens);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  786 */       else if (!buffer.readIdProperty(propName)) {
/*      */ 
/*      */ 
/*      */         
/*  790 */         SettableBeanProperty prop = this._beanProperties.find(propName);
/*  791 */         if (prop != null) {
/*  792 */           buffer.bufferProperty(prop, _deserializeWithErrorWrapping(p, ctxt, prop));
/*      */ 
/*      */         
/*      */         }
/*  796 */         else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/*  797 */           handleIgnoredProperty(p, ctxt, handledType(), propName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  804 */         else if (this._anySetter == null) {
/*      */           
/*  806 */           tokens.writeFieldName(propName);
/*  807 */           tokens.copyCurrentStructure(p);
/*      */         } else {
/*      */           
/*  810 */           TokenBuffer b2 = TokenBuffer.asCopyOfValue(p);
/*  811 */           tokens.writeFieldName(propName);
/*  812 */           tokens.append(b2);
/*      */           try {
/*  814 */             buffer.bufferAnyProperty(this._anySetter, propName, this._anySetter.deserialize(b2.asParserOnFirstToken(), ctxt));
/*      */           }
/*  816 */           catch (Exception e) {
/*  817 */             wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  826 */       bean = creator.build(ctxt, buffer);
/*  827 */     } catch (Exception e) {
/*  828 */       wrapInstantiationProblem(e, ctxt);
/*  829 */       return null;
/*      */     } 
/*  831 */     return this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeWithExternalTypeId(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  844 */     if (this._propertyBasedCreator != null) {
/*  845 */       return deserializeUsingPropertyBasedWithExternalTypeId(p, ctxt);
/*      */     }
/*  847 */     if (this._delegateDeserializer != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  853 */       return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
/*      */     }
/*      */ 
/*      */     
/*  857 */     return deserializeWithExternalTypeId(p, ctxt, this._valueInstantiator.createUsingDefault(ctxt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeWithExternalTypeId(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/*  864 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*  865 */     ExternalTypeHandler ext = this._externalTypeIdHandler.start();
/*      */     
/*  867 */     for (JsonToken t = p.getCurrentToken(); t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/*  868 */       String propName = p.getCurrentName();
/*  869 */       t = p.nextToken();
/*  870 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/*  871 */       if (prop != null) {
/*      */         
/*  873 */         if (t.isScalarValue()) {
/*  874 */           ext.handleTypePropertyValue(p, ctxt, propName, bean);
/*      */         }
/*  876 */         if (activeView != null && !prop.visibleInView(activeView)) {
/*  877 */           p.skipChildren();
/*      */         } else {
/*      */           
/*      */           try {
/*  881 */             prop.deserializeAndSet(p, ctxt, bean);
/*  882 */           } catch (Exception e) {
/*  883 */             wrapAndThrow(e, bean, propName, ctxt);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*  888 */       } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/*  889 */         handleIgnoredProperty(p, ctxt, bean, propName);
/*      */ 
/*      */       
/*      */       }
/*  893 */       else if (!ext.handlePropertyValue(p, ctxt, propName, bean)) {
/*      */ 
/*      */ 
/*      */         
/*  897 */         if (this._anySetter != null) {
/*      */           try {
/*  899 */             this._anySetter.deserializeAndSet(p, ctxt, bean, propName);
/*  900 */           } catch (Exception e) {
/*  901 */             wrapAndThrow(e, bean, propName, ctxt);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  906 */           handleUnknownProperty(p, ctxt, bean, propName);
/*      */         } 
/*      */       } 
/*  909 */     }  return ext.complete(p, ctxt, bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  916 */     ExternalTypeHandler ext = this._externalTypeIdHandler.start();
/*  917 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/*  918 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/*      */     
/*  920 */     TokenBuffer tokens = new TokenBuffer(p, ctxt);
/*  921 */     tokens.writeStartObject();
/*      */     
/*  923 */     JsonToken t = p.getCurrentToken();
/*  924 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/*  925 */       String propName = p.getCurrentName();
/*  926 */       p.nextToken();
/*      */       
/*  928 */       SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
/*  929 */       if (creatorProp != null) {
/*      */ 
/*      */ 
/*      */         
/*  933 */         if (!ext.handlePropertyValue(p, ctxt, propName, null))
/*      */         {
/*      */ 
/*      */           
/*  937 */           if (buffer.assignParameter(creatorProp, _deserializeWithErrorWrapping(p, ctxt, creatorProp))) {
/*  938 */             Object bean; t = p.nextToken();
/*      */             
/*      */             try {
/*  941 */               bean = creator.build(ctxt, buffer);
/*  942 */             } catch (Exception e) {
/*  943 */               wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
/*      */             } 
/*      */ 
/*      */             
/*  947 */             while (t == JsonToken.FIELD_NAME) {
/*  948 */               p.nextToken();
/*  949 */               tokens.copyCurrentStructure(p);
/*  950 */               t = p.nextToken();
/*      */             } 
/*  952 */             if (bean.getClass() != this._beanType.getRawClass())
/*      */             {
/*      */               
/*  955 */               return ctxt.reportBadDefinition(this._beanType, String.format("Cannot create polymorphic instances with external type ids (%s -> %s)", new Object[] { this._beanType, bean.getClass() }));
/*      */             }
/*      */ 
/*      */             
/*  959 */             return ext.complete(p, ctxt, bean);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       }
/*  965 */       else if (!buffer.readIdProperty(propName)) {
/*      */ 
/*      */ 
/*      */         
/*  969 */         SettableBeanProperty prop = this._beanProperties.find(propName);
/*  970 */         if (prop != null) {
/*  971 */           buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
/*      */ 
/*      */         
/*      */         }
/*  975 */         else if (!ext.handlePropertyValue(p, ctxt, propName, null)) {
/*      */ 
/*      */ 
/*      */           
/*  979 */           if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/*  980 */             handleIgnoredProperty(p, ctxt, handledType(), propName);
/*      */ 
/*      */           
/*      */           }
/*  984 */           else if (this._anySetter != null) {
/*  985 */             buffer.bufferAnyProperty(this._anySetter, propName, this._anySetter.deserialize(p, ctxt));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/*  992 */       return ext.complete(p, ctxt, buffer, creator);
/*  993 */     } catch (Exception e) {
/*  994 */       return wrapInstantiationProblem(e, ctxt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Exception _creatorReturnedNullException() {
/* 1005 */     if (this._nullFromCreator == null) {
/* 1006 */       this._nullFromCreator = new NullPointerException("JSON Creator returned null");
/*      */     }
/* 1008 */     return this._nullFromCreator;
/*      */   }
/*      */ 
/*      */   
/*      */   static class BeanReferring
/*      */     extends ReadableObjectId.Referring
/*      */   {
/*      */     private final DeserializationContext _context;
/*      */     
/*      */     private final SettableBeanProperty _prop;
/*      */     
/*      */     private Object _bean;
/*      */ 
/*      */     
/*      */     BeanReferring(DeserializationContext ctxt, UnresolvedForwardReference ref, JavaType valueType, PropertyValueBuffer buffer, SettableBeanProperty prop) {
/* 1023 */       super(ref, valueType);
/* 1024 */       this._context = ctxt;
/* 1025 */       this._prop = prop;
/*      */     }
/*      */     
/*      */     public void setBean(Object bean) {
/* 1029 */       this._bean = bean;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleResolvedForwardReference(Object id, Object value) throws IOException {
/* 1035 */       if (this._bean == null) {
/* 1036 */         this._context.reportInputMismatch((BeanProperty)this._prop, "Cannot resolve ObjectId forward reference using property '%s' (of type %s): Bean not yet resolved", new Object[] { this._prop.getName(), this._prop.getDeclaringClass().getName() });
/*      */       }
/*      */ 
/*      */       
/* 1040 */       this._prop.set(this._bean, value);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\BeanDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */