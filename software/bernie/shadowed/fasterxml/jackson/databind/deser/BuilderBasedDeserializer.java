/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.BeanAsArrayBuilderDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
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
/*     */ public class BuilderBasedDeserializer
/*     */   extends BeanDeserializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMethod _buildMethod;
/*     */   protected final JavaType _targetType;
/*     */   
/*     */   public BuilderBasedDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, JavaType targetType, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, Set<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
/*  53 */     super(builder, beanDesc, properties, backRefs, ignorableProps, ignoreAllUnknown, hasViews);
/*     */     
/*  55 */     this._targetType = targetType;
/*  56 */     this._buildMethod = builder.getBuildMethod();
/*     */     
/*  58 */     if (this._objectIdReader != null) {
/*  59 */       throw new IllegalArgumentException("Cannot use Object Id with Builder-based deserialization (type " + beanDesc.getType() + ")");
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
/*     */   @Deprecated
/*     */   public BuilderBasedDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, Set<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
/*  74 */     this(builder, beanDesc, beanDesc.getType(), properties, backRefs, ignorableProps, ignoreAllUnknown, hasViews);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BuilderBasedDeserializer(BuilderBasedDeserializer src) {
/*  85 */     this(src, src._ignoreAllUnknown);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BuilderBasedDeserializer(BuilderBasedDeserializer src, boolean ignoreAllUnknown) {
/*  90 */     super(src, ignoreAllUnknown);
/*  91 */     this._buildMethod = src._buildMethod;
/*  92 */     this._targetType = src._targetType;
/*     */   }
/*     */   
/*     */   protected BuilderBasedDeserializer(BuilderBasedDeserializer src, NameTransformer unwrapper) {
/*  96 */     super(src, unwrapper);
/*  97 */     this._buildMethod = src._buildMethod;
/*  98 */     this._targetType = src._targetType;
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer(BuilderBasedDeserializer src, ObjectIdReader oir) {
/* 102 */     super(src, oir);
/* 103 */     this._buildMethod = src._buildMethod;
/* 104 */     this._targetType = src._targetType;
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer(BuilderBasedDeserializer src, Set<String> ignorableProps) {
/* 108 */     super(src, ignorableProps);
/* 109 */     this._buildMethod = src._buildMethod;
/* 110 */     this._targetType = src._targetType;
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer(BuilderBasedDeserializer src, BeanPropertyMap props) {
/* 114 */     super(src, props);
/* 115 */     this._buildMethod = src._buildMethod;
/* 116 */     this._targetType = src._targetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
/* 126 */     return (JsonDeserializer<Object>)new BuilderBasedDeserializer(this, unwrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withObjectIdReader(ObjectIdReader oir) {
/* 131 */     return new BuilderBasedDeserializer(this, oir);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withIgnorableProperties(Set<String> ignorableProps) {
/* 136 */     return new BuilderBasedDeserializer(this, ignorableProps);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withBeanProperties(BeanPropertyMap props) {
/* 141 */     return new BuilderBasedDeserializer(this, props);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanDeserializerBase asArrayDeserializer() {
/* 146 */     SettableBeanProperty[] props = this._beanProperties.getPropertiesInInsertionOrder();
/* 147 */     return (BeanDeserializerBase)new BeanAsArrayBuilderDeserializer(this, this._targetType, props, this._buildMethod);
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
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 159 */     return Boolean.FALSE;
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
/*     */   protected final Object finishBuild(DeserializationContext ctxt, Object builder) throws IOException {
/* 172 */     if (null == this._buildMethod) {
/* 173 */       return builder;
/*     */     }
/*     */     try {
/* 176 */       return this._buildMethod.getMember().invoke(builder, (Object[])null);
/* 177 */     } catch (Exception e) {
/* 178 */       return wrapInstantiationProblem(e, ctxt);
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
/*     */   public final Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 190 */     if (p.isExpectedStartObjectToken()) {
/* 191 */       JsonToken t = p.nextToken();
/* 192 */       if (this._vanillaProcessing) {
/* 193 */         return finishBuild(ctxt, vanillaDeserialize(p, ctxt, t));
/*     */       }
/* 195 */       Object builder = deserializeFromObject(p, ctxt);
/* 196 */       return finishBuild(ctxt, builder);
/*     */     } 
/*     */     
/* 199 */     switch (p.getCurrentTokenId()) {
/*     */       case 6:
/* 201 */         return finishBuild(ctxt, deserializeFromString(p, ctxt));
/*     */       case 7:
/* 203 */         return finishBuild(ctxt, deserializeFromNumber(p, ctxt));
/*     */       case 8:
/* 205 */         return finishBuild(ctxt, deserializeFromDouble(p, ctxt));
/*     */       case 12:
/* 207 */         return p.getEmbeddedObject();
/*     */       case 9:
/*     */       case 10:
/* 210 */         return finishBuild(ctxt, deserializeFromBoolean(p, ctxt));
/*     */       
/*     */       case 3:
/* 213 */         return finishBuild(ctxt, deserializeFromArray(p, ctxt));
/*     */       case 2:
/*     */       case 5:
/* 216 */         return finishBuild(ctxt, deserializeFromObject(p, ctxt));
/*     */     } 
/*     */     
/* 219 */     return ctxt.handleUnexpectedToken(handledType(), p);
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
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt, Object value) throws IOException {
/* 233 */     JavaType valueType = this._targetType;
/*     */     
/* 235 */     Class<?> builderRawType = handledType();
/* 236 */     Class<?> instRawType = value.getClass();
/* 237 */     if (builderRawType.isAssignableFrom(instRawType)) {
/* 238 */       return ctxt.reportBadDefinition(valueType, String.format("Deserialization of %s by passing existing Builder (%s) instance not supported", new Object[] { valueType, builderRawType.getName() }));
/*     */     }
/*     */ 
/*     */     
/* 242 */     return ctxt.reportBadDefinition(valueType, String.format("Deserialization of %s by passing existing instance (of %s) not supported", new Object[] { valueType, instRawType.getName() }));
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
/*     */   private final Object vanillaDeserialize(JsonParser p, DeserializationContext ctxt, JsonToken t) throws IOException {
/* 261 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/* 262 */     for (; p.getCurrentToken() != JsonToken.END_OBJECT; p.nextToken()) {
/* 263 */       String propName = p.getCurrentName();
/*     */       
/* 265 */       p.nextToken();
/* 266 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/* 267 */       if (prop != null) {
/*     */         try {
/* 269 */           bean = prop.deserializeSetAndReturn(p, ctxt, bean);
/* 270 */         } catch (Exception e) {
/* 271 */           wrapAndThrow(e, bean, propName, ctxt);
/*     */         } 
/*     */       } else {
/* 274 */         handleUnknownVanilla(p, ctxt, bean, propName);
/*     */       } 
/*     */     } 
/* 277 */     return bean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 288 */     if (this._nonStandardCreation) {
/* 289 */       if (this._unwrappedPropertyHandler != null) {
/* 290 */         return deserializeWithUnwrapped(p, ctxt);
/*     */       }
/* 292 */       if (this._externalTypeIdHandler != null) {
/* 293 */         return deserializeWithExternalTypeId(p, ctxt);
/*     */       }
/* 295 */       return deserializeFromObjectUsingNonDefault(p, ctxt);
/*     */     } 
/* 297 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/* 298 */     if (this._injectables != null) {
/* 299 */       injectValues(ctxt, bean);
/*     */     }
/* 301 */     if (this._needViewProcesing) {
/* 302 */       Class<?> view = ctxt.getActiveView();
/* 303 */       if (view != null) {
/* 304 */         return deserializeWithView(p, ctxt, bean, view);
/*     */       }
/*     */     } 
/* 307 */     for (; p.getCurrentToken() != JsonToken.END_OBJECT; p.nextToken()) {
/* 308 */       String propName = p.getCurrentName();
/*     */       
/* 310 */       p.nextToken();
/* 311 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/* 312 */       if (prop != null) {
/*     */         try {
/* 314 */           bean = prop.deserializeSetAndReturn(p, ctxt, bean);
/* 315 */         } catch (Exception e) {
/* 316 */           wrapAndThrow(e, bean, propName, ctxt);
/*     */         } 
/*     */       } else {
/*     */         
/* 320 */         handleUnknownVanilla(p, ctxt, bean, propName);
/*     */       } 
/* 322 */     }  return bean;
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
/*     */   protected final Object _deserializeUsingPropertyBased(JsonParser p, DeserializationContext ctxt) throws IOException {
/*     */     Object object;
/* 341 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/* 342 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/* 343 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*     */ 
/*     */     
/* 346 */     TokenBuffer unknown = null;
/*     */     
/* 348 */     JsonToken t = p.getCurrentToken();
/* 349 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 350 */       object = p.getCurrentName();
/* 351 */       p.nextToken();
/*     */       
/* 353 */       SettableBeanProperty creatorProp = creator.findCreatorProperty((String)object);
/* 354 */       if (creatorProp != null) {
/* 355 */         if (activeView != null && !creatorProp.visibleInView(activeView)) {
/* 356 */           p.skipChildren();
/*     */ 
/*     */         
/*     */         }
/* 360 */         else if (buffer.assignParameter(creatorProp, creatorProp.deserialize(p, ctxt))) {
/* 361 */           Object builder; p.nextToken();
/*     */           
/*     */           try {
/* 364 */             builder = creator.build(ctxt, buffer);
/* 365 */           } catch (Exception e) {
/* 366 */             wrapAndThrow(e, this._beanType.getRawClass(), (String)object, ctxt);
/*     */           } 
/*     */ 
/*     */           
/* 370 */           if (builder.getClass() != this._beanType.getRawClass()) {
/* 371 */             return handlePolymorphic(p, ctxt, builder, unknown);
/*     */           }
/* 373 */           if (unknown != null) {
/* 374 */             builder = handleUnknownProperties(ctxt, builder, unknown);
/*     */           }
/*     */           
/* 377 */           return _deserialize(p, ctxt, builder);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 382 */       else if (!buffer.readIdProperty((String)object)) {
/*     */ 
/*     */ 
/*     */         
/* 386 */         SettableBeanProperty prop = this._beanProperties.find((String)object);
/* 387 */         if (prop != null) {
/* 388 */           buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 393 */         else if (this._ignorableProps != null && this._ignorableProps.contains(object)) {
/* 394 */           handleIgnoredProperty(p, ctxt, handledType(), (String)object);
/*     */ 
/*     */         
/*     */         }
/* 398 */         else if (this._anySetter != null) {
/* 399 */           buffer.bufferAnyProperty(this._anySetter, (String)object, this._anySetter.deserialize(p, ctxt));
/*     */         }
/*     */         else {
/*     */           
/* 403 */           if (unknown == null) {
/* 404 */             unknown = new TokenBuffer(p, ctxt);
/*     */           }
/* 406 */           unknown.writeFieldName((String)object);
/* 407 */           unknown.copyCurrentStructure(p);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 413 */       object = creator.build(ctxt, buffer);
/* 414 */     } catch (Exception e) {
/* 415 */       object = wrapInstantiationProblem(e, ctxt);
/*     */     } 
/* 417 */     if (unknown != null) {
/*     */       
/* 419 */       if (object.getClass() != this._beanType.getRawClass()) {
/* 420 */         return handlePolymorphic(null, ctxt, object, unknown);
/*     */       }
/*     */       
/* 423 */       return handleUnknownProperties(ctxt, object, unknown);
/*     */     } 
/* 425 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object _deserialize(JsonParser p, DeserializationContext ctxt, Object builder) throws IOException {
/* 432 */     if (this._injectables != null) {
/* 433 */       injectValues(ctxt, builder);
/*     */     }
/* 435 */     if (this._unwrappedPropertyHandler != null) {
/* 436 */       if (p.hasToken(JsonToken.START_OBJECT)) {
/* 437 */         p.nextToken();
/*     */       }
/* 439 */       TokenBuffer tokens = new TokenBuffer(p, ctxt);
/* 440 */       tokens.writeStartObject();
/* 441 */       return deserializeWithUnwrapped(p, ctxt, builder, tokens);
/*     */     } 
/* 443 */     if (this._externalTypeIdHandler != null) {
/* 444 */       return deserializeWithExternalTypeId(p, ctxt, builder);
/*     */     }
/* 446 */     if (this._needViewProcesing) {
/* 447 */       Class<?> view = ctxt.getActiveView();
/* 448 */       if (view != null) {
/* 449 */         return deserializeWithView(p, ctxt, builder, view);
/*     */       }
/*     */     } 
/* 452 */     JsonToken t = p.getCurrentToken();
/*     */     
/* 454 */     if (t == JsonToken.START_OBJECT) {
/* 455 */       t = p.nextToken();
/*     */     }
/* 457 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 458 */       String propName = p.getCurrentName();
/*     */       
/* 460 */       p.nextToken();
/* 461 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/*     */       
/* 463 */       if (prop != null) {
/*     */         try {
/* 465 */           builder = prop.deserializeSetAndReturn(p, ctxt, builder);
/* 466 */         } catch (Exception e) {
/* 467 */           wrapAndThrow(e, builder, propName, ctxt);
/*     */         } 
/*     */       } else {
/*     */         
/* 471 */         handleUnknownVanilla(p, ctxt, handledType(), propName);
/*     */       } 
/* 473 */     }  return builder;
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
/*     */   protected final Object deserializeWithView(JsonParser p, DeserializationContext ctxt, Object bean, Class<?> activeView) throws IOException {
/* 486 */     JsonToken t = p.getCurrentToken();
/* 487 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 488 */       String propName = p.getCurrentName();
/*     */       
/* 490 */       p.nextToken();
/* 491 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/* 492 */       if (prop != null) {
/* 493 */         if (!prop.visibleInView(activeView)) {
/* 494 */           p.skipChildren();
/*     */         } else {
/*     */           
/*     */           try {
/* 498 */             bean = prop.deserializeSetAndReturn(p, ctxt, bean);
/* 499 */           } catch (Exception e) {
/* 500 */             wrapAndThrow(e, bean, propName, ctxt);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 504 */         handleUnknownVanilla(p, ctxt, bean, propName);
/*     */       } 
/* 506 */     }  return bean;
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
/*     */   protected Object deserializeWithUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 523 */     if (this._delegateDeserializer != null) {
/* 524 */       return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
/*     */     }
/* 526 */     if (this._propertyBasedCreator != null) {
/* 527 */       return deserializeUsingPropertyBasedWithUnwrapped(p, ctxt);
/*     */     }
/* 529 */     TokenBuffer tokens = new TokenBuffer(p, ctxt);
/* 530 */     tokens.writeStartObject();
/* 531 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/*     */     
/* 533 */     if (this._injectables != null) {
/* 534 */       injectValues(ctxt, bean);
/*     */     }
/*     */     
/* 537 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*     */     
/* 539 */     for (; p.getCurrentToken() != JsonToken.END_OBJECT; p.nextToken()) {
/* 540 */       String propName = p.getCurrentName();
/* 541 */       p.nextToken();
/* 542 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/* 543 */       if (prop != null) {
/* 544 */         if (activeView != null && !prop.visibleInView(activeView)) {
/* 545 */           p.skipChildren();
/*     */         } else {
/*     */           
/*     */           try {
/* 549 */             bean = prop.deserializeSetAndReturn(p, ctxt, bean);
/* 550 */           } catch (Exception e) {
/* 551 */             wrapAndThrow(e, bean, propName, ctxt);
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 556 */       } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/* 557 */         handleIgnoredProperty(p, ctxt, bean, propName);
/*     */       }
/*     */       else {
/*     */         
/* 561 */         tokens.writeFieldName(propName);
/* 562 */         tokens.copyCurrentStructure(p);
/*     */         
/* 564 */         if (this._anySetter != null) {
/*     */           try {
/* 566 */             this._anySetter.deserializeAndSet(p, ctxt, bean, propName);
/* 567 */           } catch (Exception e) {
/* 568 */             wrapAndThrow(e, bean, propName, ctxt);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 573 */     tokens.writeEndObject();
/* 574 */     return this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 582 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/* 583 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/*     */     
/* 585 */     TokenBuffer tokens = new TokenBuffer(p, ctxt);
/* 586 */     tokens.writeStartObject();
/* 587 */     Object builder = null;
/*     */     
/* 589 */     JsonToken t = p.getCurrentToken();
/* 590 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 591 */       String propName = p.getCurrentName();
/* 592 */       p.nextToken();
/*     */       
/* 594 */       SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
/* 595 */       if (creatorProp != null) {
/*     */         
/* 597 */         if (buffer.assignParameter(creatorProp, creatorProp.deserialize(p, ctxt))) {
/* 598 */           t = p.nextToken();
/*     */           try {
/* 600 */             builder = creator.build(ctxt, buffer);
/* 601 */           } catch (Exception e) {
/* 602 */             wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
/*     */           } 
/*     */           
/* 605 */           if (builder.getClass() != this._beanType.getRawClass()) {
/* 606 */             return handlePolymorphic(p, ctxt, builder, tokens);
/*     */           }
/* 608 */           return deserializeWithUnwrapped(p, ctxt, builder, tokens);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 613 */       else if (!buffer.readIdProperty(propName)) {
/*     */ 
/*     */ 
/*     */         
/* 617 */         SettableBeanProperty prop = this._beanProperties.find(propName);
/* 618 */         if (prop != null) {
/* 619 */           buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
/*     */         
/*     */         }
/* 622 */         else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/* 623 */           handleIgnoredProperty(p, ctxt, handledType(), propName);
/*     */         } else {
/*     */           
/* 626 */           tokens.writeFieldName(propName);
/* 627 */           tokens.copyCurrentStructure(p);
/*     */           
/* 629 */           if (this._anySetter != null)
/* 630 */             buffer.bufferAnyProperty(this._anySetter, propName, this._anySetter.deserialize(p, ctxt)); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 634 */     if (builder == null) {
/*     */       try {
/* 636 */         builder = creator.build(ctxt, buffer);
/* 637 */       } catch (Exception e) {
/* 638 */         return wrapInstantiationProblem(e, ctxt);
/*     */       } 
/*     */     }
/* 641 */     return this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, builder, tokens);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeWithUnwrapped(JsonParser p, DeserializationContext ctxt, Object builder, TokenBuffer tokens) throws IOException {
/* 648 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/* 649 */     for (JsonToken t = p.getCurrentToken(); t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 650 */       String propName = p.getCurrentName();
/* 651 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/* 652 */       p.nextToken();
/* 653 */       if (prop != null) {
/* 654 */         if (activeView != null && !prop.visibleInView(activeView)) {
/* 655 */           p.skipChildren();
/*     */         } else {
/*     */           
/*     */           try {
/* 659 */             builder = prop.deserializeSetAndReturn(p, ctxt, builder);
/* 660 */           } catch (Exception e) {
/* 661 */             wrapAndThrow(e, builder, propName, ctxt);
/*     */           }
/*     */         
/*     */         } 
/* 665 */       } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/* 666 */         handleIgnoredProperty(p, ctxt, builder, propName);
/*     */       }
/*     */       else {
/*     */         
/* 670 */         tokens.writeFieldName(propName);
/* 671 */         tokens.copyCurrentStructure(p);
/*     */         
/* 673 */         if (this._anySetter != null)
/* 674 */           this._anySetter.deserializeAndSet(p, ctxt, builder, propName); 
/*     */       } 
/*     */     } 
/* 677 */     tokens.writeEndObject();
/* 678 */     return this._unwrappedPropertyHandler.processUnwrapped(p, ctxt, builder, tokens);
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
/*     */   protected Object deserializeWithExternalTypeId(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 691 */     if (this._propertyBasedCreator != null) {
/* 692 */       return deserializeUsingPropertyBasedWithExternalTypeId(p, ctxt);
/*     */     }
/* 694 */     return deserializeWithExternalTypeId(p, ctxt, this._valueInstantiator.createUsingDefault(ctxt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeWithExternalTypeId(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/* 701 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/* 702 */     ExternalTypeHandler ext = this._externalTypeIdHandler.start();
/*     */     
/* 704 */     for (JsonToken t = p.getCurrentToken(); t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 705 */       String propName = p.getCurrentName();
/* 706 */       t = p.nextToken();
/* 707 */       SettableBeanProperty prop = this._beanProperties.find(propName);
/* 708 */       if (prop != null) {
/*     */         
/* 710 */         if (t.isScalarValue()) {
/* 711 */           ext.handleTypePropertyValue(p, ctxt, propName, bean);
/*     */         }
/* 713 */         if (activeView != null && !prop.visibleInView(activeView)) {
/* 714 */           p.skipChildren();
/*     */         } else {
/*     */           
/*     */           try {
/* 718 */             bean = prop.deserializeSetAndReturn(p, ctxt, bean);
/* 719 */           } catch (Exception e) {
/* 720 */             wrapAndThrow(e, bean, propName, ctxt);
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 725 */       } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
/* 726 */         handleIgnoredProperty(p, ctxt, bean, propName);
/*     */ 
/*     */       
/*     */       }
/* 730 */       else if (!ext.handlePropertyValue(p, ctxt, propName, bean)) {
/*     */ 
/*     */ 
/*     */         
/* 734 */         if (this._anySetter != null) {
/*     */           try {
/* 736 */             this._anySetter.deserializeAndSet(p, ctxt, bean, propName);
/* 737 */           } catch (Exception e) {
/* 738 */             wrapAndThrow(e, bean, propName, ctxt);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 743 */           handleUnknownProperty(p, ctxt, bean, propName);
/*     */         } 
/*     */       } 
/*     */     } 
/* 747 */     return ext.complete(p, ctxt, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 755 */     JavaType t = this._targetType;
/* 756 */     return ctxt.reportBadDefinition(t, String.format("Deserialization (of %s) with Builder, External type id, @JsonCreator not yet implemented", new Object[] { t }));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\BuilderBasedDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */