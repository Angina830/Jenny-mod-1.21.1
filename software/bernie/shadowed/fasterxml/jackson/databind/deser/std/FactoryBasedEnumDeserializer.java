/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FactoryBasedEnumDeserializer
/*     */   extends StdDeserializer<Object>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _inputType;
/*     */   protected final boolean _hasArgs;
/*     */   protected final AnnotatedMethod _factory;
/*     */   protected final JsonDeserializer<?> _deser;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final SettableBeanProperty[] _creatorProps;
/*     */   private transient PropertyBasedCreator _propCreator;
/*     */   
/*     */   public FactoryBasedEnumDeserializer(Class<?> cls, AnnotatedMethod f, JavaType paramType, ValueInstantiator valueInstantiator, SettableBeanProperty[] creatorProps) {
/*  49 */     super(cls);
/*  50 */     this._factory = f;
/*  51 */     this._hasArgs = true;
/*     */     
/*  53 */     this._inputType = paramType.hasRawClass(String.class) ? null : paramType;
/*  54 */     this._deser = null;
/*  55 */     this._valueInstantiator = valueInstantiator;
/*  56 */     this._creatorProps = creatorProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FactoryBasedEnumDeserializer(Class<?> cls, AnnotatedMethod f) {
/*  64 */     super(cls);
/*  65 */     this._factory = f;
/*  66 */     this._hasArgs = false;
/*  67 */     this._inputType = null;
/*  68 */     this._deser = null;
/*  69 */     this._valueInstantiator = null;
/*  70 */     this._creatorProps = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected FactoryBasedEnumDeserializer(FactoryBasedEnumDeserializer base, JsonDeserializer<?> deser) {
/*  75 */     super(base._valueClass);
/*  76 */     this._inputType = base._inputType;
/*  77 */     this._factory = base._factory;
/*  78 */     this._hasArgs = base._hasArgs;
/*  79 */     this._valueInstantiator = base._valueInstantiator;
/*  80 */     this._creatorProps = base._creatorProps;
/*     */     
/*  82 */     this._deser = deser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/*  90 */     if (this._deser == null && this._inputType != null && this._creatorProps == null) {
/*  91 */       return new FactoryBasedEnumDeserializer(this, ctxt.findContextualValueDeserializer(this._inputType, property));
/*     */     }
/*     */     
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/*  99 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 105 */     Object value = null;
/* 106 */     if (this._deser != null) {
/* 107 */       value = this._deser.deserialize(p, ctxt);
/* 108 */     } else if (this._hasArgs) {
/* 109 */       JsonToken curr = p.getCurrentToken();
/*     */ 
/*     */       
/* 112 */       if (curr == JsonToken.VALUE_STRING || curr == JsonToken.FIELD_NAME)
/* 113 */       { value = p.getText(); }
/* 114 */       else { if (this._creatorProps != null && p.isExpectedStartObjectToken()) {
/* 115 */           if (this._propCreator == null) {
/* 116 */             this._propCreator = PropertyBasedCreator.construct(ctxt, this._valueInstantiator, this._creatorProps, ctxt.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
/*     */           }
/*     */           
/* 119 */           p.nextToken();
/* 120 */           return deserializeEnumUsingPropertyBased(p, ctxt, this._propCreator);
/*     */         } 
/* 122 */         value = p.getValueAsString(); }
/*     */     
/*     */     } else {
/* 125 */       p.skipChildren();
/*     */       try {
/* 127 */         return this._factory.call();
/* 128 */       } catch (Exception e) {
/* 129 */         Throwable t = ClassUtil.throwRootCauseIfIOE(e);
/* 130 */         return ctxt.handleInstantiationProblem(this._valueClass, null, t);
/*     */       } 
/*     */     } 
/*     */     try {
/* 134 */       return this._factory.callOnWith(this._valueClass, new Object[] { value });
/* 135 */     } catch (Exception e) {
/* 136 */       Throwable t = ClassUtil.throwRootCauseIfIOE(e);
/*     */       
/* 138 */       if (ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL) && t instanceof IllegalArgumentException)
/*     */       {
/* 140 */         return null;
/*     */       }
/* 142 */       return ctxt.handleInstantiationProblem(this._valueClass, value, t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 148 */     if (this._deser == null) {
/* 149 */       return deserialize(p, ctxt);
/*     */     }
/* 151 */     return typeDeserializer.deserializeTypedFromAny(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeEnumUsingPropertyBased(JsonParser p, DeserializationContext ctxt, PropertyBasedCreator creator) throws IOException {
/* 158 */     PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, null);
/*     */     
/* 160 */     JsonToken t = p.getCurrentToken();
/* 161 */     for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
/* 162 */       String propName = p.getCurrentName();
/* 163 */       p.nextToken();
/*     */       
/* 165 */       SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
/* 166 */       if (creatorProp != null) {
/* 167 */         buffer.assignParameter(creatorProp, _deserializeWithErrorWrapping(p, ctxt, creatorProp));
/*     */       
/*     */       }
/* 170 */       else if (buffer.readIdProperty(propName)) {
/*     */       
/*     */       } 
/*     */     } 
/* 174 */     return creator.build(ctxt, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object _deserializeWithErrorWrapping(JsonParser p, DeserializationContext ctxt, SettableBeanProperty prop) throws IOException {
/*     */     try {
/* 182 */       return prop.deserialize(p, ctxt);
/* 183 */     } catch (Exception e) {
/* 184 */       wrapAndThrow(e, this._valueClass.getClass(), prop.getName(), ctxt);
/*     */       
/* 186 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void wrapAndThrow(Throwable t, Object bean, String fieldName, DeserializationContext ctxt) throws IOException {
/* 193 */     throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(t, ctxt), bean, fieldName);
/*     */   }
/*     */ 
/*     */   
/*     */   private Throwable throwOrReturnThrowable(Throwable t, DeserializationContext ctxt) throws IOException {
/* 198 */     t = ClassUtil.getRootCause(t);
/*     */     
/* 200 */     ClassUtil.throwIfError(t);
/* 201 */     boolean wrap = (ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS));
/*     */     
/* 203 */     if (t instanceof IOException) {
/* 204 */       if (!wrap || !(t instanceof software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException)) {
/* 205 */         throw (IOException)t;
/*     */       }
/* 207 */     } else if (!wrap) {
/* 208 */       ClassUtil.throwIfRTE(t);
/*     */     } 
/* 210 */     return t;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\FactoryBasedEnumDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */