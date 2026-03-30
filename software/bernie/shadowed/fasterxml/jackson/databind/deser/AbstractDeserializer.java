/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerators;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractDeserializer
/*     */   extends JsonDeserializer<Object>
/*     */   implements ContextualDeserializer, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _baseType;
/*     */   protected final ObjectIdReader _objectIdReader;
/*     */   protected final Map<String, SettableBeanProperty> _backRefProperties;
/*     */   protected transient Map<String, SettableBeanProperty> _properties;
/*     */   protected final boolean _acceptString;
/*     */   protected final boolean _acceptBoolean;
/*     */   protected final boolean _acceptInt;
/*     */   protected final boolean _acceptDouble;
/*     */   
/*     */   public AbstractDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, Map<String, SettableBeanProperty> backRefProps, Map<String, SettableBeanProperty> props) {
/*  65 */     this._baseType = beanDesc.getType();
/*  66 */     this._objectIdReader = builder.getObjectIdReader();
/*  67 */     this._backRefProperties = backRefProps;
/*  68 */     this._properties = props;
/*  69 */     Class<?> cls = this._baseType.getRawClass();
/*  70 */     this._acceptString = cls.isAssignableFrom(String.class);
/*  71 */     this._acceptBoolean = (cls == boolean.class || cls.isAssignableFrom(Boolean.class));
/*  72 */     this._acceptInt = (cls == int.class || cls.isAssignableFrom(Integer.class));
/*  73 */     this._acceptDouble = (cls == double.class || cls.isAssignableFrom(Double.class));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public AbstractDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, Map<String, SettableBeanProperty> backRefProps) {
/*  79 */     this(builder, beanDesc, backRefProps, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractDeserializer(BeanDescription beanDesc) {
/*  84 */     this._baseType = beanDesc.getType();
/*  85 */     this._objectIdReader = null;
/*  86 */     this._backRefProperties = null;
/*  87 */     Class<?> cls = this._baseType.getRawClass();
/*  88 */     this._acceptString = cls.isAssignableFrom(String.class);
/*  89 */     this._acceptBoolean = (cls == boolean.class || cls.isAssignableFrom(Boolean.class));
/*  90 */     this._acceptInt = (cls == int.class || cls.isAssignableFrom(Integer.class));
/*  91 */     this._acceptDouble = (cls == double.class || cls.isAssignableFrom(Double.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractDeserializer(AbstractDeserializer base, ObjectIdReader objectIdReader, Map<String, SettableBeanProperty> props) {
/* 100 */     this._baseType = base._baseType;
/* 101 */     this._backRefProperties = base._backRefProperties;
/* 102 */     this._acceptString = base._acceptString;
/* 103 */     this._acceptBoolean = base._acceptBoolean;
/* 104 */     this._acceptInt = base._acceptInt;
/* 105 */     this._acceptDouble = base._acceptDouble;
/*     */     
/* 107 */     this._objectIdReader = objectIdReader;
/* 108 */     this._properties = props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AbstractDeserializer constructForNonPOJO(BeanDescription beanDesc) {
/* 118 */     return new AbstractDeserializer(beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 125 */     AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
/* 126 */     if (property != null && intr != null) {
/* 127 */       AnnotatedMember accessor = property.getMember();
/* 128 */       if (accessor != null) {
/* 129 */         ObjectIdInfo objectIdInfo = intr.findObjectIdInfo((Annotated)accessor);
/* 130 */         if (objectIdInfo != null) {
/*     */           JavaType idType;
/*     */           ObjectIdGenerator<?> idGen;
/* 133 */           SettableBeanProperty idProp = null;
/* 134 */           ObjectIdResolver resolver = ctxt.objectIdResolverInstance((Annotated)accessor, objectIdInfo);
/*     */ 
/*     */           
/* 137 */           objectIdInfo = intr.findObjectReferenceInfo((Annotated)accessor, objectIdInfo);
/* 138 */           Class<?> implClass = objectIdInfo.getGeneratorType();
/*     */           
/* 140 */           if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
/* 141 */             PropertyName propName = objectIdInfo.getPropertyName();
/* 142 */             idProp = (this._properties == null) ? null : this._properties.get(propName.getSimpleName());
/* 143 */             if (idProp == null) {
/* 144 */               ctxt.reportBadDefinition(this._baseType, String.format("Invalid Object Id definition for %s: cannot find property with name '%s'", new Object[] { handledType().getName(), propName }));
/*     */             }
/*     */ 
/*     */             
/* 148 */             idType = idProp.getType();
/* 149 */             PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 157 */             resolver = ctxt.objectIdResolverInstance((Annotated)accessor, objectIdInfo);
/* 158 */             JavaType type = ctxt.constructType(implClass);
/* 159 */             idType = ctxt.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
/* 160 */             idGen = ctxt.objectIdGeneratorInstance((Annotated)accessor, objectIdInfo);
/*     */           } 
/* 162 */           JsonDeserializer<?> deser = ctxt.findRootValueDeserializer(idType);
/* 163 */           ObjectIdReader oir = ObjectIdReader.construct(idType, objectIdInfo.getPropertyName(), idGen, deser, idProp, resolver);
/*     */           
/* 165 */           return new AbstractDeserializer(this, oir, null);
/*     */         } 
/*     */       } 
/*     */     } 
/* 169 */     if (this._properties == null) {
/* 170 */       return this;
/*     */     }
/*     */     
/* 173 */     return new AbstractDeserializer(this, this._objectIdReader, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> handledType() {
/* 184 */     return this._baseType.getRawClass();
/*     */   }
/*     */   
/*     */   public boolean isCachable() {
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 197 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdReader getObjectIdReader() {
/* 207 */     return this._objectIdReader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SettableBeanProperty findBackReference(String logicalName) {
/* 216 */     return (this._backRefProperties == null) ? null : this._backRefProperties.get(logicalName);
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
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 232 */     if (this._objectIdReader != null) {
/* 233 */       JsonToken t = p.getCurrentToken();
/* 234 */       if (t != null) {
/*     */         
/* 236 */         if (t.isScalarValue()) {
/* 237 */           return _deserializeFromObjectId(p, ctxt);
/*     */         }
/*     */         
/* 240 */         if (t == JsonToken.START_OBJECT) {
/* 241 */           t = p.nextToken();
/*     */         }
/* 243 */         if (t == JsonToken.FIELD_NAME && this._objectIdReader.maySerializeAsObject() && this._objectIdReader.isValidReferencePropertyName(p.getCurrentName(), p))
/*     */         {
/* 245 */           return _deserializeFromObjectId(p, ctxt);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     Object result = _deserializeIfNatural(p, ctxt);
/* 251 */     if (result != null) {
/* 252 */       return result;
/*     */     }
/* 254 */     return typeDeserializer.deserializeTypedFromObject(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 264 */     ValueInstantiator bogus = new ValueInstantiator.Base(this._baseType);
/* 265 */     return ctxt.handleMissingInstantiator(this._baseType.getRawClass(), bogus, p, "abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information", new Object[0]);
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
/*     */   protected Object _deserializeIfNatural(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 283 */     switch (p.getCurrentTokenId()) {
/*     */       case 6:
/* 285 */         if (this._acceptString) {
/* 286 */           return p.getText();
/*     */         }
/*     */         break;
/*     */       case 7:
/* 290 */         if (this._acceptInt) {
/* 291 */           return Integer.valueOf(p.getIntValue());
/*     */         }
/*     */         break;
/*     */       case 8:
/* 295 */         if (this._acceptDouble) {
/* 296 */           return Double.valueOf(p.getDoubleValue());
/*     */         }
/*     */         break;
/*     */       case 9:
/* 300 */         if (this._acceptBoolean) {
/* 301 */           return Boolean.TRUE;
/*     */         }
/*     */         break;
/*     */       case 10:
/* 305 */         if (this._acceptBoolean) {
/* 306 */           return Boolean.FALSE;
/*     */         }
/*     */         break;
/*     */     } 
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _deserializeFromObjectId(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 319 */     Object id = this._objectIdReader.readObjectReference(p, ctxt);
/* 320 */     ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator, this._objectIdReader.resolver);
/*     */     
/* 322 */     Object pojo = roid.resolve();
/* 323 */     if (pojo == null) {
/* 324 */       throw new UnresolvedForwardReference(p, "Could not resolve Object Id [" + id + "] -- unresolved forward-reference?", p.getCurrentLocation(), roid);
/*     */     }
/*     */     
/* 327 */     return pojo;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\AbstractDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */