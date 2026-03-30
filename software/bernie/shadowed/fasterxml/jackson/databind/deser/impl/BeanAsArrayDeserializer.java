/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerBase;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
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
/*     */ public class BeanAsArrayDeserializer
/*     */   extends BeanDeserializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final BeanDeserializerBase _delegate;
/*     */   protected final SettableBeanProperty[] _orderedProperties;
/*     */   
/*     */   public BeanAsArrayDeserializer(BeanDeserializerBase delegate, SettableBeanProperty[] ordered) {
/*  47 */     super(delegate);
/*  48 */     this._delegate = delegate;
/*  49 */     this._orderedProperties = ordered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
/*  59 */     return this._delegate.unwrappingDeserializer(unwrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withObjectIdReader(ObjectIdReader oir) {
/*  64 */     return new BeanAsArrayDeserializer(this._delegate.withObjectIdReader(oir), this._orderedProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withIgnorableProperties(Set<String> ignorableProps) {
/*  70 */     return new BeanAsArrayDeserializer(this._delegate.withIgnorableProperties(ignorableProps), this._orderedProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDeserializerBase withBeanProperties(BeanPropertyMap props) {
/*  76 */     return new BeanAsArrayDeserializer(this._delegate.withBeanProperties(props), this._orderedProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanDeserializerBase asArrayDeserializer() {
/*  82 */     return this;
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
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  96 */     if (!p.isExpectedStartArrayToken()) {
/*  97 */       return _deserializeFromNonArray(p, ctxt);
/*     */     }
/*  99 */     if (!this._vanillaProcessing) {
/* 100 */       return _deserializeNonVanilla(p, ctxt);
/*     */     }
/* 102 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/*     */     
/* 104 */     p.setCurrentValue(bean);
/*     */     
/* 106 */     SettableBeanProperty[] props = this._orderedProperties;
/* 107 */     int i = 0;
/* 108 */     int propCount = props.length;
/*     */     while (true) {
/* 110 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 111 */         return bean;
/*     */       }
/* 113 */       if (i == propCount) {
/*     */         break;
/*     */       }
/* 116 */       SettableBeanProperty prop = props[i];
/* 117 */       if (prop != null) {
/*     */         try {
/* 119 */           prop.deserializeAndSet(p, ctxt, bean);
/* 120 */         } catch (Exception e) {
/* 121 */           wrapAndThrow(e, bean, prop.getName(), ctxt);
/*     */         } 
/*     */       } else {
/* 124 */         p.skipChildren();
/*     */       } 
/* 126 */       i++;
/*     */     } 
/*     */     
/* 129 */     if (!this._ignoreAllUnknown && ctxt.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/* 130 */       ctxt.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON values; expected at most %d properties (in JSON Array)", new Object[] { Integer.valueOf(propCount) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 137 */       p.skipChildren();
/* 138 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 139 */         return bean;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/* 147 */     p.setCurrentValue(bean);
/*     */     
/* 149 */     if (!p.isExpectedStartArrayToken()) {
/* 150 */       return _deserializeFromNonArray(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     if (this._injectables != null) {
/* 157 */       injectValues(ctxt, bean);
/*     */     }
/* 159 */     SettableBeanProperty[] props = this._orderedProperties;
/* 160 */     int i = 0;
/* 161 */     int propCount = props.length;
/*     */     while (true) {
/* 163 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 164 */         return bean;
/*     */       }
/* 166 */       if (i == propCount) {
/*     */         break;
/*     */       }
/* 169 */       SettableBeanProperty prop = props[i];
/* 170 */       if (prop != null) {
/*     */         try {
/* 172 */           prop.deserializeAndSet(p, ctxt, bean);
/* 173 */         } catch (Exception e) {
/* 174 */           wrapAndThrow(e, bean, prop.getName(), ctxt);
/*     */         } 
/*     */       } else {
/* 177 */         p.skipChildren();
/*     */       } 
/* 179 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 183 */     if (!this._ignoreAllUnknown && ctxt.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/* 184 */       ctxt.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON values; expected at most %d properties (in JSON Array)", new Object[] { Integer.valueOf(propCount) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 191 */       p.skipChildren();
/* 192 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 193 */         return bean;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 201 */     return _deserializeFromNonArray(p, ctxt);
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
/*     */   protected Object _deserializeNonVanilla(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 217 */     if (this._nonStandardCreation) {
/* 218 */       return deserializeFromObjectUsingNonDefault(p, ctxt);
/*     */     }
/* 220 */     Object bean = this._valueInstantiator.createUsingDefault(ctxt);
/*     */     
/* 222 */     p.setCurrentValue(bean);
/* 223 */     if (this._injectables != null) {
/* 224 */       injectValues(ctxt, bean);
/*     */     }
/* 226 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/* 227 */     SettableBeanProperty[] props = this._orderedProperties;
/* 228 */     int i = 0;
/* 229 */     int propCount = props.length;
/*     */     
/*     */     while (true) {
/* 232 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 233 */         return bean;
/*     */       }
/* 235 */       if (i == propCount) {
/*     */         break;
/*     */       }
/* 238 */       SettableBeanProperty prop = props[i];
/* 239 */       i++;
/* 240 */       if (prop != null && (
/* 241 */         activeView == null || prop.visibleInView(activeView))) {
/*     */         try {
/* 243 */           prop.deserializeAndSet(p, ctxt, bean);
/* 244 */         } catch (Exception e) {
/* 245 */           wrapAndThrow(e, bean, prop.getName(), ctxt);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 251 */       p.skipChildren();
/*     */     } 
/*     */     
/* 254 */     if (!this._ignoreAllUnknown) {
/* 255 */       ctxt.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON values; expected at most %d properties (in JSON Array)", new Object[] { Integer.valueOf(propCount) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 262 */       p.skipChildren();
/* 263 */       if (p.nextToken() == JsonToken.END_ARRAY) {
/* 264 */         return bean;
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
/*     */   protected final Object _deserializeUsingPropertyBased(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 279 */     PropertyBasedCreator creator = this._propertyBasedCreator;
/* 280 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, this._objectIdReader);
/*     */     
/* 282 */     SettableBeanProperty[] props = this._orderedProperties;
/* 283 */     int propCount = props.length;
/* 284 */     int i = 0;
/* 285 */     Object bean = null;
/* 286 */     Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
/*     */     
/* 288 */     for (; p.nextToken() != JsonToken.END_ARRAY; i++) {
/* 289 */       SettableBeanProperty prop = (i < propCount) ? props[i] : null;
/* 290 */       if (prop == null) {
/* 291 */         p.skipChildren();
/*     */       
/*     */       }
/* 294 */       else if (activeView != null && !prop.visibleInView(activeView)) {
/* 295 */         p.skipChildren();
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 300 */       else if (bean != null) {
/*     */         try {
/* 302 */           prop.deserializeAndSet(p, ctxt, bean);
/* 303 */         } catch (Exception e) {
/* 304 */           wrapAndThrow(e, bean, prop.getName(), ctxt);
/*     */         } 
/*     */       } else {
/*     */         
/* 308 */         String propName = prop.getName();
/*     */         
/* 310 */         SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
/* 311 */         if (creatorProp != null) {
/*     */           
/* 313 */           if (buffer.assignParameter(creatorProp, creatorProp.deserialize(p, ctxt))) {
/*     */             try {
/* 315 */               bean = creator.build(ctxt, buffer);
/* 316 */             } catch (Exception e) {
/* 317 */               wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
/*     */             } 
/*     */ 
/*     */             
/* 321 */             p.setCurrentValue(bean);
/*     */ 
/*     */             
/* 324 */             if (bean.getClass() != this._beanType.getRawClass())
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 329 */               ctxt.reportBadDefinition(this._beanType, String.format("Cannot support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type %s, actual type %s", new Object[] { this._beanType.getRawClass().getName(), bean.getClass().getName() }));
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 338 */         else if (!buffer.readIdProperty(propName)) {
/*     */ 
/*     */ 
/*     */           
/* 342 */           buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
/*     */         } 
/*     */       } 
/*     */     } 
/* 346 */     if (bean == null) {
/*     */       try {
/* 348 */         bean = creator.build(ctxt, buffer);
/* 349 */       } catch (Exception e) {
/* 350 */         return wrapInstantiationProblem(e, ctxt);
/*     */       } 
/*     */     }
/* 353 */     return bean;
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
/*     */   protected Object _deserializeFromNonArray(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 365 */     return ctxt.handleUnexpectedToken(handledType(), p.getCurrentToken(), p, "Cannot deserialize a POJO (of type %s) from non-Array representation (token: %s): type/property designed to be serialized as JSON Array", new Object[] { this._beanType.getRawClass().getName(), p.getCurrentToken() });
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\BeanAsArrayDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */