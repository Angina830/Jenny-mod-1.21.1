/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerBase;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanAsArrayBuilderDeserializer
/*     */   extends BeanDeserializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final BeanDeserializerBase _delegate;
/*     */   protected final SettableBeanProperty[] _orderedProperties;
/*     */   protected final AnnotatedMethod _buildMethod;
/*     */   protected final JavaType _targetType;
/*     */   
/*     */   public BeanAsArrayBuilderDeserializer(BeanDeserializerBase delegate, JavaType targetType, SettableBeanProperty[] ordered, AnnotatedMethod buildMethod) {
/*  55 */     super(delegate);
/*  56 */     this._delegate = delegate;
/*  57 */     this._targetType = targetType;
/*  58 */     this._orderedProperties = ordered;
/*  59 */     this._buildMethod = buildMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
/*  69 */     return this._delegate.unwrappingDeserializer(unwrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withObjectIdReader(ObjectIdReader oir) {
/*  74 */     return new BeanAsArrayBuilderDeserializer(this._delegate.withObjectIdReader(oir), this._targetType, this._orderedProperties, this._buildMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withIgnorableProperties(Set<String> ignorableProps) {
/*  80 */     return new BeanAsArrayBuilderDeserializer(this._delegate.withIgnorableProperties(ignorableProps), this._targetType, this._orderedProperties, this._buildMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withBeanProperties(BeanPropertyMap props) {
/*  86 */     return new BeanAsArrayBuilderDeserializer(this._delegate.withBeanProperties(props), this._targetType, this._orderedProperties, this._buildMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanDeserializerBase asArrayDeserializer() {
/*  92 */     return this;
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
/* 104 */     return Boolean.FALSE;
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
/*     */   protected final Object finishBuild(DeserializationContext ctxt, Object builder) throws IOException {
/*     */     try {
/* 117 */       return this._buildMethod.getMember().invoke(builder, (Object[])null);
/* 118 */     } catch (Exception e) {
/* 119 */       return wrapInstantiationProblem(e, ctxt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 128 */     if (!p.isExpectedStartArrayToken()) {
/* 129 */       return finishBuild(ctxt, _deserializeFromNonArray(p, ctxt));
/*     */     }
/* 131 */     if (!this._vanillaProcessing) {
/* 132 */       return finishBuild(ctxt, _deserializeNonVanilla(p, ctxt));
/*     */     }
/* 134 */     Object builder = this._valueInstantiator.createUsingDefault(ctxt);
/* 135 */     SettableBeanProperty[] props = this._orderedProperties;
/* 136 */     int i = 0;
/* 137 */     int propCount = props.length;
/*     */     while (true) {
/* 139 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 140 */         return finishBuild(ctxt, builder);
/*     */       }
/* 142 */       if (i == propCount) {
/*     */         break;
/*     */       }
/* 145 */       SettableBeanProperty prop = props[i];
/* 146 */       if (prop != null) {
/*     */         try {
/* 148 */           builder = prop.deserializeSetAndReturn(p, ctxt, builder);
/* 149 */         } catch (Exception e) {
/* 150 */           wrapAndThrow(e, builder, prop.getName(), ctxt);
/*     */         } 
/*     */       } else {
/* 153 */         p.skipChildren();
/*     */       } 
/* 155 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 159 */     if (!this._ignoreAllUnknown && ctxt.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/* 160 */       ctxt.reportInputMismatch(handledType(), "Unexpected JSON values; expected at most %d properties (in JSON Array)", new Object[] { Integer.valueOf(propCount) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     while (p.nextToken() != JsonToken.END_ARRAY) {
/* 167 */       p.skipChildren();
/*     */     }
/* 169 */     return finishBuild(ctxt, builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt, Object value) throws IOException {
/* 177 */     return this._delegate.deserialize(p, ctxt, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 184 */     return _deserializeFromNonArray(p, ctxt);
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
/*     */   protected Object _deserializeNonVanilla(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 203 */     if (this._nonStandardCreation) {
/* 204 */       return deserializeFromObjectUsingNonDefault(p, ctxt);
/*     */     }
/* 206 */     Object builder = this._valueInstantiator.createUsingDefault(ctxt);
/* 207 */     if (this._injectables != null) {
/* 208 */       injectValues(ctxt, builder);
/*     */     }
/* 210 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/* 211 */     SettableBeanProperty[] props = this._orderedProperties;
/* 212 */     int i = 0;
/* 213 */     int propCount = props.length;
/*     */     while (true) {
/* 215 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 216 */         return builder;
/*     */       }
/* 218 */       if (i == propCount) {
/*     */         break;
/*     */       }
/* 221 */       SettableBeanProperty prop = props[i];
/* 222 */       i++;
/* 223 */       if (prop != null && (
/* 224 */         activeView == null || prop.visibleInView(activeView))) {
/*     */         try {
/* 226 */           prop.deserializeSetAndReturn(p, ctxt, builder);
/* 227 */         } catch (Exception e) {
/* 228 */           wrapAndThrow(e, builder, prop.getName(), ctxt);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 234 */       p.skipChildren();
/*     */     } 
/*     */     
/* 237 */     if (!this._ignoreAllUnknown && ctxt.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/* 238 */       ctxt.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON value(s); expected at most %d properties (in JSON Array)", new Object[] { Integer.valueOf(propCount) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     while (p.nextToken() != JsonToken.END_ARRAY) {
/* 245 */       p.skipChildren();
/*     */     }
/* 247 */     return builder;
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
/*     */   protected final Object _deserializeUsingPropertyBased(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 263 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/* 264 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/*     */     
/* 266 */     SettableBeanProperty[] props = this._orderedProperties;
/* 267 */     int propCount = props.length;
/* 268 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/* 269 */     int i = 0;
/* 270 */     Object builder = null;
/*     */     
/* 272 */     for (; p.nextToken() != JsonToken.END_ARRAY; i++) {
/* 273 */       SettableBeanProperty prop = (i < propCount) ? props[i] : null;
/* 274 */       if (prop == null) {
/* 275 */         p.skipChildren();
/*     */       
/*     */       }
/* 278 */       else if (activeView != null && !prop.visibleInView(activeView)) {
/* 279 */         p.skipChildren();
/*     */ 
/*     */       
/*     */       }
/* 283 */       else if (builder != null) {
/*     */         try {
/* 285 */           builder = prop.deserializeSetAndReturn(p, ctxt, builder);
/* 286 */         } catch (Exception e) {
/* 287 */           wrapAndThrow(e, builder, prop.getName(), ctxt);
/*     */         } 
/*     */       } else {
/*     */         
/* 291 */         String propName = prop.getName();
/*     */         
/* 293 */         SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
/* 294 */         if (creatorProp != null) {
/*     */           
/* 296 */           if (buffer.assignParameter(creatorProp, creatorProp.deserialize(p, ctxt))) {
/*     */             try {
/* 298 */               builder = creator.build(ctxt, buffer);
/* 299 */             } catch (Exception e) {
/* 300 */               wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
/*     */             } 
/*     */ 
/*     */             
/* 304 */             if (builder.getClass() != this._beanType.getRawClass())
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 309 */               return ctxt.reportBadDefinition(this._beanType, String.format("Cannot support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type %s, actual type %s", new Object[] { this._beanType.getRawClass().getName(), builder.getClass().getName() }));
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 317 */         else if (!buffer.readIdProperty(propName)) {
/*     */ 
/*     */ 
/*     */           
/* 321 */           buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
/*     */         } 
/*     */       } 
/*     */     } 
/* 325 */     if (builder == null) {
/*     */       try {
/* 327 */         builder = creator.build(ctxt, buffer);
/* 328 */       } catch (Exception e) {
/* 329 */         return wrapInstantiationProblem(e, ctxt);
/*     */       } 
/*     */     }
/* 332 */     return builder;
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
/*     */   protected Object _deserializeFromNonArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 345 */     return ctxt.handleUnexpectedToken(handledType(), p.getCurrentToken(), p, "Cannot deserialize a POJO (of type %s) from non-Array representation (token: %s): type/property designed to be serialized as JSON Array", new Object[] { this._beanType.getRawClass().getName(), p.getCurrentToken() });
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\BeanAsArrayBuilderDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */