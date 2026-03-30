/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*     */ public class ExternalTypeHandler
/*     */ {
/*     */   private final JavaType _beanType;
/*     */   private final ExtTypedProperty[] _properties;
/*     */   private final Map<String, Object> _nameToPropertyIndex;
/*     */   private final String[] _typeIds;
/*     */   private final TokenBuffer[] _tokens;
/*     */   
/*     */   protected ExternalTypeHandler(JavaType beanType, ExtTypedProperty[] properties, Map<String, Object> nameToPropertyIndex, String[] typeIds, TokenBuffer[] tokens) {
/*  41 */     this._beanType = beanType;
/*  42 */     this._properties = properties;
/*  43 */     this._nameToPropertyIndex = nameToPropertyIndex;
/*  44 */     this._typeIds = typeIds;
/*  45 */     this._tokens = tokens;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ExternalTypeHandler(ExternalTypeHandler h) {
/*  50 */     this._beanType = h._beanType;
/*  51 */     this._properties = h._properties;
/*  52 */     this._nameToPropertyIndex = h._nameToPropertyIndex;
/*  53 */     int len = this._properties.length;
/*  54 */     this._typeIds = new String[len];
/*  55 */     this._tokens = new TokenBuffer[len];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder(JavaType beanType) {
/*  62 */     return new Builder(beanType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExternalTypeHandler start() {
/*  70 */     return new ExternalTypeHandler(this);
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
/*     */   public boolean handleTypePropertyValue(JsonParser p, DeserializationContext ctxt, String propName, Object bean) throws IOException {
/*  85 */     Object ob = this._nameToPropertyIndex.get(propName);
/*  86 */     if (ob == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     String typeId = p.getText();
/*     */     
/*  91 */     if (ob instanceof List) {
/*  92 */       boolean result = false;
/*  93 */       for (Integer index : ob) {
/*  94 */         if (_handleTypePropertyValue(p, ctxt, propName, bean, typeId, index.intValue()))
/*     */         {
/*  96 */           result = true;
/*     */         }
/*     */       } 
/*  99 */       return result;
/*     */     } 
/* 101 */     return _handleTypePropertyValue(p, ctxt, propName, bean, typeId, ((Integer)ob).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean _handleTypePropertyValue(JsonParser p, DeserializationContext ctxt, String propName, Object bean, String typeId, int index) throws IOException {
/* 109 */     ExtTypedProperty prop = this._properties[index];
/* 110 */     if (!prop.hasTypePropertyName(propName)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     boolean canDeserialize = (bean != null && this._tokens[index] != null);
/*     */     
/* 116 */     if (canDeserialize) {
/* 117 */       _deserializeAndSet(p, ctxt, bean, index, typeId);
/*     */       
/* 119 */       this._tokens[index] = null;
/*     */     } else {
/* 121 */       this._typeIds[index] = typeId;
/*     */     } 
/* 123 */     return true;
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
/*     */   public boolean handlePropertyValue(JsonParser p, DeserializationContext ctxt, String propName, Object bean) throws IOException {
/*     */     boolean canDeserialize;
/* 138 */     Object ob = this._nameToPropertyIndex.get(propName);
/* 139 */     if (ob == null) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     if (ob instanceof List) {
/* 144 */       Iterator<Integer> it = ((List<Integer>)ob).iterator();
/* 145 */       Integer integer = it.next();
/*     */       
/* 147 */       ExtTypedProperty extTypedProperty = this._properties[integer.intValue()];
/*     */ 
/*     */       
/* 150 */       if (extTypedProperty.hasTypePropertyName(propName)) {
/* 151 */         String typeId = p.getText();
/* 152 */         p.skipChildren();
/* 153 */         this._typeIds[integer.intValue()] = typeId;
/* 154 */         while (it.hasNext()) {
/* 155 */           this._typeIds[((Integer)it.next()).intValue()] = typeId;
/*     */         }
/*     */       } else {
/*     */         
/* 159 */         TokenBuffer tokens = new TokenBuffer(p, ctxt);
/* 160 */         tokens.copyCurrentStructure(p);
/* 161 */         this._tokens[integer.intValue()] = tokens;
/* 162 */         while (it.hasNext()) {
/* 163 */           this._tokens[((Integer)it.next()).intValue()] = tokens;
/*     */         }
/*     */       } 
/* 166 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 171 */     int index = ((Integer)ob).intValue();
/* 172 */     ExtTypedProperty prop = this._properties[index];
/*     */     
/* 174 */     if (prop.hasTypePropertyName(propName)) {
/* 175 */       this._typeIds[index] = p.getText();
/* 176 */       p.skipChildren();
/* 177 */       canDeserialize = (bean != null && this._tokens[index] != null);
/*     */     } else {
/*     */       
/* 180 */       TokenBuffer tokens = new TokenBuffer(p, ctxt);
/* 181 */       tokens.copyCurrentStructure(p);
/* 182 */       this._tokens[index] = tokens;
/* 183 */       canDeserialize = (bean != null && this._typeIds[index] != null);
/*     */     } 
/*     */ 
/*     */     
/* 187 */     if (canDeserialize) {
/* 188 */       String typeId = this._typeIds[index];
/*     */       
/* 190 */       this._typeIds[index] = null;
/* 191 */       _deserializeAndSet(p, ctxt, bean, index, typeId);
/* 192 */       this._tokens[index] = null;
/*     */     } 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object complete(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/* 205 */     int i = 0, len = this._properties.length; while (true) { String typeId; if (i < len)
/* 206 */       { typeId = this._typeIds[i];
/* 207 */         if (typeId == null)
/* 208 */         { TokenBuffer tokens = this._tokens[i];
/*     */ 
/*     */           
/* 211 */           if (tokens == null) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 216 */           JsonToken t = tokens.firstToken();
/* 217 */           if (t.isScalarValue())
/* 218 */           { JsonParser buffered = tokens.asParser(p);
/* 219 */             buffered.nextToken();
/* 220 */             SettableBeanProperty extProp = this._properties[i].getProperty();
/* 221 */             Object result = TypeDeserializer.deserializeIfNatural(buffered, ctxt, extProp.getType());
/* 222 */             if (result != null)
/* 223 */             { extProp.set(bean, result); }
/*     */             
/*     */             else
/*     */             
/* 227 */             { if (!this._properties[i].hasDefaultType()) {
/* 228 */                 ctxt.reportInputMismatch(bean.getClass(), "Missing external type id property '%s'", new Object[] { this._properties[i].getTypePropertyName() });
/*     */               }
/*     */               else {
/*     */                 
/* 232 */                 typeId = this._properties[i].getDefaultTypeId();
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 246 */               _deserializeAndSet(p, ctxt, bean, i, typeId); }  continue; }  } else if (this._tokens[i] == null) { SettableBeanProperty prop = this._properties[i].getProperty(); if (prop.isRequired() || ctxt.isEnabled(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)) ctxt.reportInputMismatch(bean.getClass(), "Missing property '%s' for external type id '%s'", new Object[] { prop.getName(), this._properties[i].getTypePropertyName() });  return bean; }  } else { break; }  _deserializeAndSet(p, ctxt, bean, i, typeId); i++; }
/*     */     
/* 248 */     return bean;
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
/*     */   public Object complete(JsonParser p, DeserializationContext ctxt, PropertyValueBuffer buffer, PropertyBasedCreator creator) throws IOException {
/* 260 */     int len = this._properties.length;
/* 261 */     Object[] values = new Object[len];
/* 262 */     for (int i = 0; i < len; i++) {
/* 263 */       String typeId = this._typeIds[i];
/* 264 */       ExtTypedProperty extProp = this._properties[i];
/*     */       
/* 266 */       if (typeId == null) {
/*     */         
/* 268 */         if (this._tokens[i] == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 273 */         if (!extProp.hasDefaultType()) {
/* 274 */           ctxt.reportInputMismatch(this._beanType, "Missing external type id property '%s'", new Object[] { extProp.getTypePropertyName() });
/*     */         }
/*     */         else {
/*     */           
/* 278 */           typeId = extProp.getDefaultTypeId();
/*     */         } 
/* 280 */       } else if (this._tokens[i] == null) {
/* 281 */         SettableBeanProperty settableBeanProperty = extProp.getProperty();
/* 282 */         ctxt.reportInputMismatch(this._beanType, "Missing property '%s' for external type id '%s'", new Object[] { settableBeanProperty.getName(), this._properties[i].getTypePropertyName() });
/*     */       } 
/*     */ 
/*     */       
/* 286 */       values[i] = _deserialize(p, ctxt, i, typeId);
/*     */       
/* 288 */       SettableBeanProperty prop = extProp.getProperty();
/*     */       
/* 290 */       if (prop.getCreatorIndex() >= 0) {
/* 291 */         buffer.assignParameter(prop, values[i]);
/*     */ 
/*     */         
/* 294 */         SettableBeanProperty typeProp = extProp.getTypeProperty();
/*     */         
/* 296 */         if (typeProp != null && typeProp.getCreatorIndex() >= 0) {
/* 297 */           buffer.assignParameter(typeProp, typeId);
/*     */         }
/*     */       } 
/*     */       continue;
/*     */     } 
/* 302 */     Object bean = creator.build(ctxt, buffer);
/*     */     
/* 304 */     for (int j = 0; j < len; j++) {
/* 305 */       SettableBeanProperty prop = this._properties[j].getProperty();
/* 306 */       if (prop.getCreatorIndex() < 0) {
/* 307 */         prop.set(bean, values[j]);
/*     */       }
/*     */     } 
/* 310 */     return bean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object _deserialize(JsonParser p, DeserializationContext ctxt, int index, String typeId) throws IOException {
/* 317 */     JsonParser p2 = this._tokens[index].asParser(p);
/* 318 */     JsonToken t = p2.nextToken();
/*     */     
/* 320 */     if (t == JsonToken.VALUE_NULL) {
/* 321 */       return null;
/*     */     }
/* 323 */     TokenBuffer merged = new TokenBuffer(p, ctxt);
/* 324 */     merged.writeStartArray();
/* 325 */     merged.writeString(typeId);
/* 326 */     merged.copyCurrentStructure(p2);
/* 327 */     merged.writeEndArray();
/*     */ 
/*     */     
/* 330 */     JsonParser mp = merged.asParser(p);
/* 331 */     mp.nextToken();
/* 332 */     return this._properties[index].getProperty().deserialize(mp, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void _deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object bean, int index, String typeId) throws IOException {
/* 342 */     JsonParser p2 = this._tokens[index].asParser(p);
/* 343 */     JsonToken t = p2.nextToken();
/*     */     
/* 345 */     if (t == JsonToken.VALUE_NULL) {
/* 346 */       this._properties[index].getProperty().set(bean, null);
/*     */       return;
/*     */     } 
/* 349 */     TokenBuffer merged = new TokenBuffer(p, ctxt);
/* 350 */     merged.writeStartArray();
/* 351 */     merged.writeString(typeId);
/*     */     
/* 353 */     merged.copyCurrentStructure(p2);
/* 354 */     merged.writeEndArray();
/*     */     
/* 356 */     JsonParser mp = merged.asParser(p);
/* 357 */     mp.nextToken();
/* 358 */     this._properties[index].getProperty().deserializeAndSet(mp, ctxt, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final JavaType _beanType;
/*     */ 
/*     */ 
/*     */     
/* 371 */     private final List<ExternalTypeHandler.ExtTypedProperty> _properties = new ArrayList<>();
/* 372 */     private final Map<String, Object> _nameToPropertyIndex = new HashMap<>();
/*     */     
/*     */     protected Builder(JavaType t) {
/* 375 */       this._beanType = t;
/*     */     }
/*     */ 
/*     */     
/*     */     public void addExternal(SettableBeanProperty property, TypeDeserializer typeDeser) {
/* 380 */       Integer index = Integer.valueOf(this._properties.size());
/* 381 */       this._properties.add(new ExternalTypeHandler.ExtTypedProperty(property, typeDeser));
/* 382 */       _addPropertyIndex(property.getName(), index);
/* 383 */       _addPropertyIndex(typeDeser.getPropertyName(), index);
/*     */     }
/*     */     
/*     */     private void _addPropertyIndex(String name, Integer index) {
/* 387 */       Object ob = this._nameToPropertyIndex.get(name);
/* 388 */       if (ob == null) {
/* 389 */         this._nameToPropertyIndex.put(name, index);
/* 390 */       } else if (ob instanceof List) {
/*     */         
/* 392 */         List<Object> list = (List<Object>)ob;
/* 393 */         list.add(index);
/*     */       } else {
/* 395 */         List<Object> list = new LinkedList();
/* 396 */         list.add(ob);
/* 397 */         list.add(index);
/* 398 */         this._nameToPropertyIndex.put(name, list);
/*     */       } 
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
/*     */     public ExternalTypeHandler build(BeanPropertyMap otherProps) {
/* 411 */       int len = this._properties.size();
/* 412 */       ExternalTypeHandler.ExtTypedProperty[] extProps = new ExternalTypeHandler.ExtTypedProperty[len];
/* 413 */       for (int i = 0; i < len; i++) {
/* 414 */         ExternalTypeHandler.ExtTypedProperty extProp = this._properties.get(i);
/* 415 */         String typePropId = extProp.getTypePropertyName();
/* 416 */         SettableBeanProperty typeProp = otherProps.find(typePropId);
/* 417 */         if (typeProp != null) {
/* 418 */           extProp.linkTypeProperty(typeProp);
/*     */         }
/* 420 */         extProps[i] = extProp;
/*     */       } 
/* 422 */       return new ExternalTypeHandler(this._beanType, extProps, this._nameToPropertyIndex, null, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ExtTypedProperty
/*     */   {
/*     */     private final SettableBeanProperty _property;
/*     */     
/*     */     private final TypeDeserializer _typeDeserializer;
/*     */     
/*     */     private final String _typePropertyName;
/*     */     
/*     */     private SettableBeanProperty _typeProperty;
/*     */ 
/*     */     
/*     */     public ExtTypedProperty(SettableBeanProperty property, TypeDeserializer typeDeser) {
/* 440 */       this._property = property;
/* 441 */       this._typeDeserializer = typeDeser;
/* 442 */       this._typePropertyName = typeDeser.getPropertyName();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void linkTypeProperty(SettableBeanProperty p) {
/* 449 */       this._typeProperty = p;
/*     */     }
/*     */     
/*     */     public boolean hasTypePropertyName(String n) {
/* 453 */       return n.equals(this._typePropertyName);
/*     */     }
/*     */     
/*     */     public boolean hasDefaultType() {
/* 457 */       return (this._typeDeserializer.getDefaultImpl() != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDefaultTypeId() {
/* 466 */       Class<?> defaultType = this._typeDeserializer.getDefaultImpl();
/* 467 */       if (defaultType == null) {
/* 468 */         return null;
/*     */       }
/* 470 */       return this._typeDeserializer.getTypeIdResolver().idFromValueAndType(null, defaultType);
/*     */     }
/*     */     public String getTypePropertyName() {
/* 473 */       return this._typePropertyName;
/*     */     }
/*     */     public SettableBeanProperty getProperty() {
/* 476 */       return this._property;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SettableBeanProperty getTypeProperty() {
/* 483 */       return this._typeProperty;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ExternalTypeHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */