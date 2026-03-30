/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.RuntimeJsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ReferenceType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.BeanUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ReferenceTypeSerializer<T>
/*     */   extends StdSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _referredType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanProperty _property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _valueSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final NameTransformer _unwrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient PropertySerializerMap _dynamicSerializers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object _suppressableValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _suppressNulls;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceTypeSerializer(ReferenceType fullType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> ser) {
/* 100 */     super((JavaType)fullType);
/* 101 */     this._referredType = fullType.getReferencedType();
/* 102 */     this._property = null;
/* 103 */     this._valueTypeSerializer = vts;
/* 104 */     this._valueSerializer = ser;
/* 105 */     this._unwrapper = null;
/* 106 */     this._suppressableValue = null;
/* 107 */     this._suppressNulls = false;
/* 108 */     this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ReferenceTypeSerializer(ReferenceTypeSerializer<?> base, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper, Object suppressableValue, boolean suppressNulls) {
/* 117 */     super(base);
/* 118 */     this._referredType = base._referredType;
/* 119 */     this._dynamicSerializers = base._dynamicSerializers;
/* 120 */     this._property = property;
/* 121 */     this._valueTypeSerializer = vts;
/* 122 */     this._valueSerializer = (JsonSerializer)valueSer;
/* 123 */     this._unwrapper = unwrapper;
/* 124 */     this._suppressableValue = suppressableValue;
/* 125 */     this._suppressNulls = suppressNulls;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<T> unwrappingSerializer(NameTransformer transformer) {
/* 130 */     JsonSerializer<Object> valueSer = this._valueSerializer;
/* 131 */     if (valueSer != null) {
/* 132 */       valueSer = valueSer.unwrappingSerializer(transformer);
/*     */     }
/* 134 */     NameTransformer unwrapper = (this._unwrapper == null) ? transformer : NameTransformer.chainedTransformer(transformer, this._unwrapper);
/*     */     
/* 136 */     if (this._valueSerializer == valueSer && this._unwrapper == unwrapper) {
/* 137 */       return this;
/*     */     }
/* 139 */     return withResolved(this._property, this._valueTypeSerializer, valueSer, unwrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ReferenceTypeSerializer<T> withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer, NameTransformer paramNameTransformer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ReferenceTypeSerializer<T> withContentInclusion(Object paramObject, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean _isValuePresent(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object _getReferenced(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object _getReferencedIfPresent(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/*     */     ReferenceTypeSerializer<?> refSer;
/* 195 */     TypeSerializer typeSer = this._valueTypeSerializer;
/* 196 */     if (typeSer != null) {
/* 197 */       typeSer = typeSer.forProperty(property);
/*     */     }
/*     */     
/* 200 */     JsonSerializer<?> ser = findAnnotatedContentSerializer(provider, property);
/* 201 */     if (ser == null) {
/*     */       
/* 203 */       ser = this._valueSerializer;
/* 204 */       if (ser == null) {
/*     */         
/* 206 */         if (_useStatic(provider, property, this._referredType)) {
/* 207 */           ser = _findSerializer(provider, this._referredType, property);
/*     */         }
/*     */       } else {
/* 210 */         ser = provider.handlePrimaryContextualization(ser, property);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 215 */     if (this._property == property && this._valueTypeSerializer == typeSer && this._valueSerializer == ser) {
/*     */       
/* 217 */       refSer = this;
/*     */     } else {
/* 219 */       refSer = withResolved(property, typeSer, ser, this._unwrapper);
/*     */     } 
/*     */ 
/*     */     
/* 223 */     if (property != null) {
/* 224 */       JsonInclude.Value inclV = property.findPropertyInclusion((MapperConfig)provider.getConfig(), handledType());
/* 225 */       if (inclV != null) {
/* 226 */         JsonInclude.Include incl = inclV.getContentInclusion();
/*     */         
/* 228 */         if (incl != JsonInclude.Include.USE_DEFAULTS) {
/*     */           Object valueToSuppress;
/*     */           boolean suppressNulls;
/* 231 */           switch (incl) {
/*     */             case NON_DEFAULT:
/* 233 */               valueToSuppress = BeanUtil.getDefaultValue(this._referredType);
/* 234 */               suppressNulls = true;
/* 235 */               if (valueToSuppress != null && 
/* 236 */                 valueToSuppress.getClass().isArray()) {
/* 237 */                 valueToSuppress = ArrayBuilders.getArrayComparator(valueToSuppress);
/*     */               }
/*     */               break;
/*     */             
/*     */             case NON_ABSENT:
/* 242 */               suppressNulls = true;
/* 243 */               valueToSuppress = this._referredType.isReferenceType() ? MARKER_FOR_EMPTY : null;
/*     */               break;
/*     */             case NON_EMPTY:
/* 246 */               suppressNulls = true;
/* 247 */               valueToSuppress = MARKER_FOR_EMPTY;
/*     */               break;
/*     */             case CUSTOM:
/* 250 */               valueToSuppress = provider.includeFilterInstance(null, inclV.getContentFilter());
/* 251 */               if (valueToSuppress == null) {
/* 252 */                 suppressNulls = true; break;
/*     */               } 
/* 254 */               suppressNulls = provider.includeFilterSuppressNulls(valueToSuppress);
/*     */               break;
/*     */             
/*     */             case NON_NULL:
/* 258 */               valueToSuppress = null;
/* 259 */               suppressNulls = true;
/*     */               break;
/*     */             
/*     */             default:
/* 263 */               valueToSuppress = null;
/* 264 */               suppressNulls = false;
/*     */               break;
/*     */           } 
/* 267 */           if (this._suppressableValue != valueToSuppress || this._suppressNulls != suppressNulls)
/*     */           {
/* 269 */             refSer = refSer.withContentInclusion(valueToSuppress, suppressNulls);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 274 */     return refSer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _useStatic(SerializerProvider provider, BeanProperty property, JavaType referredType) {
/* 281 */     if (referredType.isJavaLangObject()) {
/* 282 */       return false;
/*     */     }
/*     */     
/* 285 */     if (referredType.isFinal()) {
/* 286 */       return true;
/*     */     }
/*     */     
/* 289 */     if (referredType.useStaticType()) {
/* 290 */       return true;
/*     */     }
/*     */     
/* 293 */     AnnotationIntrospector intr = provider.getAnnotationIntrospector();
/* 294 */     if (intr != null && property != null) {
/* 295 */       AnnotatedMember annotatedMember = property.getMember();
/* 296 */       if (annotatedMember != null) {
/* 297 */         JsonSerialize.Typing t = intr.findSerializationTyping((Annotated)property.getMember());
/* 298 */         if (t == JsonSerialize.Typing.STATIC) {
/* 299 */           return true;
/*     */         }
/* 301 */         if (t == JsonSerialize.Typing.DYNAMIC) {
/* 302 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     return provider.isEnabled(MapperFeature.USE_STATIC_TYPING);
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
/*     */   public boolean isEmpty(SerializerProvider provider, T value) {
/* 320 */     if (!_isValuePresent(value)) {
/* 321 */       return true;
/*     */     }
/* 323 */     Object contents = _getReferenced(value);
/* 324 */     if (contents == null) {
/* 325 */       return this._suppressNulls;
/*     */     }
/* 327 */     if (this._suppressableValue == null) {
/* 328 */       return false;
/*     */     }
/* 330 */     JsonSerializer<Object> ser = this._valueSerializer;
/* 331 */     if (ser == null) {
/*     */       try {
/* 333 */         ser = _findCachedSerializer(provider, contents.getClass());
/* 334 */       } catch (JsonMappingException e) {
/* 335 */         throw new RuntimeJsonMappingException(e);
/*     */       } 
/*     */     }
/* 338 */     if (this._suppressableValue == MARKER_FOR_EMPTY) {
/* 339 */       return ser.isEmpty(provider, contents);
/*     */     }
/* 341 */     return this._suppressableValue.equals(contents);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnwrappingSerializer() {
/* 346 */     return (this._unwrapper != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getReferredType() {
/* 353 */     return this._referredType;
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
/*     */   public void serialize(T ref, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 366 */     Object value = _getReferencedIfPresent(ref);
/* 367 */     if (value == null) {
/* 368 */       if (this._unwrapper == null) {
/* 369 */         provider.defaultSerializeNull(g);
/*     */       }
/*     */       return;
/*     */     } 
/* 373 */     JsonSerializer<Object> ser = this._valueSerializer;
/* 374 */     if (ser == null) {
/* 375 */       ser = _findCachedSerializer(provider, value.getClass());
/*     */     }
/* 377 */     if (this._valueTypeSerializer != null) {
/* 378 */       ser.serializeWithType(value, g, provider, this._valueTypeSerializer);
/*     */     } else {
/* 380 */       ser.serialize(value, g, provider);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(T ref, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 389 */     Object value = _getReferencedIfPresent(ref);
/* 390 */     if (value == null) {
/* 391 */       if (this._unwrapper == null) {
/* 392 */         provider.defaultSerializeNull(g);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     JsonSerializer<Object> ser = this._valueSerializer;
/* 408 */     if (ser == null) {
/* 409 */       ser = _findCachedSerializer(provider, value.getClass());
/*     */     }
/* 411 */     ser.serializeWithType(value, g, provider, typeSer);
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
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 424 */     JsonSerializer<?> ser = this._valueSerializer;
/* 425 */     if (ser == null) {
/* 426 */       ser = _findSerializer(visitor.getProvider(), this._referredType, this._property);
/* 427 */       if (this._unwrapper != null) {
/* 428 */         ser = ser.unwrappingSerializer(this._unwrapper);
/*     */       }
/*     */     } 
/* 431 */     ser.acceptJsonFormatVisitor(visitor, this._referredType);
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
/*     */   private final JsonSerializer<Object> _findCachedSerializer(SerializerProvider provider, Class<?> type) throws JsonMappingException {
/* 447 */     JsonSerializer<Object> ser = this._dynamicSerializers.serializerFor(type);
/* 448 */     if (ser == null) {
/* 449 */       ser = _findSerializer(provider, type, this._property);
/* 450 */       if (this._unwrapper != null) {
/* 451 */         ser = ser.unwrappingSerializer(this._unwrapper);
/*     */       }
/* 453 */       this._dynamicSerializers = this._dynamicSerializers.newWith(type, ser);
/*     */     } 
/* 455 */     return ser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JsonSerializer<Object> _findSerializer(SerializerProvider provider, Class<?> type, BeanProperty prop) throws JsonMappingException {
/* 464 */     return provider.findValueSerializer(type, prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JsonSerializer<Object> _findSerializer(SerializerProvider provider, JavaType type, BeanProperty prop) throws JsonMappingException {
/* 473 */     return provider.findValueSerializer(type, prop);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ReferenceTypeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */