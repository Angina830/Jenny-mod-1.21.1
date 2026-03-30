/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.BitSet;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableAnyProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyValueBuffer
/*     */ {
/*     */   protected final JsonParser _parser;
/*     */   protected final DeserializationContext _context;
/*     */   protected final ObjectIdReader _objectIdReader;
/*     */   protected final Object[] _creatorParameters;
/*     */   protected int _paramsNeeded;
/*     */   protected int _paramsSeen;
/*     */   protected final BitSet _paramsSeenBig;
/*     */   protected PropertyValue _buffered;
/*     */   protected Object _idValue;
/*     */   
/*     */   public PropertyValueBuffer(JsonParser p, DeserializationContext ctxt, int paramCount, ObjectIdReader oir) {
/*  88 */     this._parser = p;
/*  89 */     this._context = ctxt;
/*  90 */     this._paramsNeeded = paramCount;
/*  91 */     this._objectIdReader = oir;
/*  92 */     this._creatorParameters = new Object[paramCount];
/*  93 */     if (paramCount < 32) {
/*  94 */       this._paramsSeenBig = null;
/*     */     } else {
/*  96 */       this._paramsSeenBig = new BitSet();
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
/*     */   public final boolean hasParameter(SettableBeanProperty prop) {
/* 108 */     if (this._paramsSeenBig == null) {
/* 109 */       return ((this._paramsSeen >> prop.getCreatorIndex() & 0x1) == 1);
/*     */     }
/* 111 */     return this._paramsSeenBig.get(prop.getCreatorIndex());
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
/*     */   public Object getParameter(SettableBeanProperty prop) throws JsonMappingException {
/*     */     Object value;
/* 128 */     if (hasParameter(prop)) {
/* 129 */       value = this._creatorParameters[prop.getCreatorIndex()];
/*     */     } else {
/* 131 */       value = this._creatorParameters[prop.getCreatorIndex()] = _findMissing(prop);
/*     */     } 
/* 133 */     if (value == null && this._context.isEnabled(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)) {
/* 134 */       return this._context.reportInputMismatch((BeanProperty)prop, String.format("Null value for creator property '%s'; DeserializationFeature.FAIL_ON_NULL_FOR_CREATOR_PARAMETERS enabled", new Object[] { prop.getName(), Integer.valueOf(prop.getCreatorIndex()) }), new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 138 */     return value;
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
/*     */   public Object[] getParameters(SettableBeanProperty[] props) throws JsonMappingException {
/* 152 */     if (this._paramsNeeded > 0) {
/* 153 */       if (this._paramsSeenBig == null) {
/* 154 */         int mask = this._paramsSeen;
/*     */ 
/*     */         
/* 157 */         for (int ix = 0, len = this._creatorParameters.length; ix < len; ix++, mask >>= 1) {
/* 158 */           if ((mask & 0x1) == 0) {
/* 159 */             this._creatorParameters[ix] = _findMissing(props[ix]);
/*     */           }
/*     */         } 
/*     */       } else {
/* 163 */         int len = this._creatorParameters.length;
/* 164 */         for (int ix = 0; (ix = this._paramsSeenBig.nextClearBit(ix)) < len; ix++) {
/* 165 */           this._creatorParameters[ix] = _findMissing(props[ix]);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 170 */     if (this._context.isEnabled(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)) {
/* 171 */       for (int ix = 0; ix < props.length; ix++) {
/* 172 */         if (this._creatorParameters[ix] == null) {
/* 173 */           SettableBeanProperty prop = props[ix];
/* 174 */           this._context.reportInputMismatch(prop.getType(), "Null value for creator property '%s' (index %d); DeserializationFeature.FAIL_ON_NULL_FOR_CREATOR_PARAMETERS enabled", new Object[] { prop.getName(), Integer.valueOf(props[ix].getCreatorIndex()) });
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 181 */     return this._creatorParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _findMissing(SettableBeanProperty prop) throws JsonMappingException {
/* 187 */     Object injectableValueId = prop.getInjectableValueId();
/* 188 */     if (injectableValueId != null) {
/* 189 */       return this._context.findInjectableValue(prop.getInjectableValueId(), (BeanProperty)prop, null);
/*     */     }
/*     */ 
/*     */     
/* 193 */     if (prop.isRequired()) {
/* 194 */       this._context.reportInputMismatch((BeanProperty)prop, String.format("Missing required creator property '%s' (index %d)", new Object[] { prop.getName(), Integer.valueOf(prop.getCreatorIndex()) }), new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 198 */     if (this._context.isEnabled(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)) {
/* 199 */       this._context.reportInputMismatch((BeanProperty)prop, String.format("Missing creator property '%s' (index %d); DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES enabled", new Object[] { prop.getName(), Integer.valueOf(prop.getCreatorIndex()) }), new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 204 */     JsonDeserializer<Object> deser = prop.getValueDeserializer();
/* 205 */     return deser.getNullValue(this._context);
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
/*     */   public boolean readIdProperty(String propName) throws IOException {
/* 222 */     if (this._objectIdReader != null && propName.equals(this._objectIdReader.propertyName.getSimpleName())) {
/* 223 */       this._idValue = this._objectIdReader.readObjectReference(this._parser, this._context);
/* 224 */       return true;
/*     */     } 
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object handleIdValue(DeserializationContext ctxt, Object bean) throws IOException {
/* 234 */     if (this._objectIdReader != null) {
/* 235 */       if (this._idValue != null) {
/* 236 */         ReadableObjectId roid = ctxt.findObjectId(this._idValue, this._objectIdReader.generator, this._objectIdReader.resolver);
/* 237 */         roid.bindItem(bean);
/*     */         
/* 239 */         SettableBeanProperty idProp = this._objectIdReader.idProperty;
/* 240 */         if (idProp != null) {
/* 241 */           return idProp.setAndReturn(bean, this._idValue);
/*     */         }
/*     */       } else {
/*     */         
/* 245 */         ctxt.reportUnresolvedObjectId(this._objectIdReader, bean);
/*     */       } 
/*     */     }
/* 248 */     return bean;
/*     */   }
/*     */   protected PropertyValue buffered() {
/* 251 */     return this._buffered;
/*     */   } public boolean isComplete() {
/* 253 */     return (this._paramsNeeded <= 0);
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
/*     */   public boolean assignParameter(SettableBeanProperty prop, Object value) {
/* 265 */     int ix = prop.getCreatorIndex();
/* 266 */     this._creatorParameters[ix] = value;
/*     */     
/* 268 */     if (this._paramsSeenBig == null) {
/* 269 */       int old = this._paramsSeen;
/* 270 */       int newValue = old | 1 << ix;
/*     */       
/* 272 */       this._paramsSeen = newValue;
/* 273 */       if (old != newValue && --this._paramsNeeded <= 0)
/*     */       {
/* 275 */         return (this._objectIdReader == null || this._idValue != null);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 280 */       this._paramsSeenBig.set(ix);
/* 281 */       if (this._paramsSeenBig.get(ix) || --this._paramsNeeded <= 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 286 */     return false;
/*     */   }
/*     */   
/*     */   public void bufferProperty(SettableBeanProperty prop, Object value) {
/* 290 */     this._buffered = new PropertyValue.Regular(this._buffered, value, prop);
/*     */   }
/*     */   
/*     */   public void bufferAnyProperty(SettableAnyProperty prop, String propName, Object value) {
/* 294 */     this._buffered = new PropertyValue.Any(this._buffered, value, prop, propName);
/*     */   }
/*     */   
/*     */   public void bufferMapProperty(Object key, Object value) {
/* 298 */     this._buffered = new PropertyValue.Map(this._buffered, value, key);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\PropertyValueBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */