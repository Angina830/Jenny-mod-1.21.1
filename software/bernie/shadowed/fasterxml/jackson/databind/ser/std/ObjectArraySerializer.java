/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
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
/*     */ @JacksonStdImpl
/*     */ public class ObjectArraySerializer
/*     */   extends ArraySerializerBase<Object[]>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final boolean _staticTyping;
/*     */   protected final JavaType _elementType;
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */   protected JsonSerializer<Object> _elementSerializer;
/*     */   protected PropertySerializerMap _dynamicSerializers;
/*     */   
/*     */   public ObjectArraySerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> elementSerializer) {
/*  65 */     super(Object[].class);
/*  66 */     this._elementType = elemType;
/*  67 */     this._staticTyping = staticTyping;
/*  68 */     this._valueTypeSerializer = vts;
/*  69 */     this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
/*  70 */     this._elementSerializer = elementSerializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectArraySerializer(ObjectArraySerializer src, TypeSerializer vts) {
/*  75 */     super(src);
/*  76 */     this._elementType = src._elementType;
/*  77 */     this._valueTypeSerializer = vts;
/*  78 */     this._staticTyping = src._staticTyping;
/*  79 */     this._dynamicSerializers = src._dynamicSerializers;
/*  80 */     this._elementSerializer = src._elementSerializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArraySerializer(ObjectArraySerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
/*  88 */     super(src, property, unwrapSingle);
/*  89 */     this._elementType = src._elementType;
/*  90 */     this._valueTypeSerializer = vts;
/*  91 */     this._staticTyping = src._staticTyping;
/*  92 */     this._dynamicSerializers = src._dynamicSerializers;
/*  93 */     this._elementSerializer = (JsonSerializer)elementSerializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/*  98 */     return (JsonSerializer<?>)new ObjectArraySerializer(this, prop, this._valueTypeSerializer, this._elementSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/* 104 */     return new ObjectArraySerializer(this._elementType, this._staticTyping, vts, this._elementSerializer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectArraySerializer withResolved(BeanProperty prop, TypeSerializer vts, JsonSerializer<?> ser, Boolean unwrapSingle) {
/* 109 */     if (this._property == prop && ser == this._elementSerializer && this._valueTypeSerializer == vts && this._unwrapSingle == unwrapSingle)
/*     */     {
/* 111 */       return this;
/*     */     }
/* 113 */     return new ObjectArraySerializer(this, prop, vts, ser, unwrapSingle);
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
/*     */   public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/* 127 */     TypeSerializer vts = this._valueTypeSerializer;
/* 128 */     if (vts != null) {
/* 129 */       vts = vts.forProperty(property);
/*     */     }
/* 131 */     JsonSerializer<?> ser = null;
/* 132 */     Boolean unwrapSingle = null;
/*     */ 
/*     */     
/* 135 */     if (property != null) {
/* 136 */       AnnotatedMember m = property.getMember();
/* 137 */       AnnotationIntrospector intr = serializers.getAnnotationIntrospector();
/* 138 */       if (m != null) {
/* 139 */         Object serDef = intr.findContentSerializer((Annotated)m);
/* 140 */         if (serDef != null) {
/* 141 */           ser = serializers.serializerInstance((Annotated)m, serDef);
/*     */         }
/*     */       } 
/*     */     } 
/* 145 */     JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/* 146 */     if (format != null) {
/* 147 */       unwrapSingle = format.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
/*     */     }
/* 149 */     if (ser == null) {
/* 150 */       ser = this._elementSerializer;
/*     */     }
/*     */     
/* 153 */     ser = findContextualConvertingSerializer(serializers, property, ser);
/* 154 */     if (ser == null)
/*     */     {
/*     */       
/* 157 */       if (this._elementType != null && 
/* 158 */         this._staticTyping && !this._elementType.isJavaLangObject()) {
/* 159 */         ser = serializers.findValueSerializer(this._elementType, property);
/*     */       }
/*     */     }
/*     */     
/* 163 */     return (JsonSerializer<?>)withResolved(property, vts, ser, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getContentType() {
/* 174 */     return this._elementType;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer() {
/* 179 */     return this._elementSerializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, Object[] value) {
/* 184 */     return (value.length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSingleElement(Object[] value) {
/* 189 */     return (value.length == 1);
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
/*     */   public final void serialize(Object[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 201 */     int len = value.length;
/* 202 */     if (len == 1 && ((
/* 203 */       this._unwrapSingle == null && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
/*     */ 
/*     */       
/* 206 */       serializeContents(value, gen, provider);
/*     */       
/*     */       return;
/*     */     } 
/* 210 */     gen.writeStartArray(len);
/* 211 */     serializeContents(value, gen, provider);
/* 212 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContents(Object[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 218 */     int len = value.length;
/* 219 */     if (len == 0) {
/*     */       return;
/*     */     }
/* 222 */     if (this._elementSerializer != null) {
/* 223 */       serializeContentsUsing(value, gen, provider, this._elementSerializer);
/*     */       return;
/*     */     } 
/* 226 */     if (this._valueTypeSerializer != null) {
/* 227 */       serializeTypedContents(value, gen, provider);
/*     */       return;
/*     */     } 
/* 230 */     int i = 0;
/* 231 */     Object elem = null;
/*     */     try {
/* 233 */       PropertySerializerMap serializers = this._dynamicSerializers;
/* 234 */       for (; i < len; i++) {
/* 235 */         elem = value[i];
/* 236 */         if (elem == null)
/* 237 */         { provider.defaultSerializeNull(gen); }
/*     */         else
/*     */         
/* 240 */         { Class<?> cc = elem.getClass();
/* 241 */           JsonSerializer<Object> serializer = serializers.serializerFor(cc);
/* 242 */           if (serializer == null) {
/* 243 */             if (this._elementType.hasGenericTypes()) {
/* 244 */               serializer = _findAndAddDynamic(serializers, provider.constructSpecializedType(this._elementType, cc), provider);
/*     */             } else {
/*     */               
/* 247 */               serializer = _findAndAddDynamic(serializers, cc, provider);
/*     */             } 
/*     */           }
/* 250 */           serializer.serialize(elem, gen, provider); } 
/*     */       } 
/* 252 */     } catch (Exception e) {
/* 253 */       wrapAndThrow(provider, e, elem, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContentsUsing(Object[] value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
/* 260 */     int len = value.length;
/* 261 */     TypeSerializer typeSer = this._valueTypeSerializer;
/*     */     
/* 263 */     int i = 0;
/* 264 */     Object elem = null;
/*     */     try {
/* 266 */       for (; i < len; i++) {
/* 267 */         elem = value[i];
/* 268 */         if (elem == null) {
/* 269 */           provider.defaultSerializeNull(jgen);
/*     */         
/*     */         }
/* 272 */         else if (typeSer == null) {
/* 273 */           ser.serialize(elem, jgen, provider);
/*     */         } else {
/* 275 */           ser.serializeWithType(elem, jgen, provider, typeSer);
/*     */         } 
/*     */       } 
/* 278 */     } catch (Exception e) {
/* 279 */       wrapAndThrow(provider, e, elem, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeTypedContents(Object[] value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
/* 285 */     int len = value.length;
/* 286 */     TypeSerializer typeSer = this._valueTypeSerializer;
/* 287 */     int i = 0;
/* 288 */     Object elem = null;
/*     */     try {
/* 290 */       PropertySerializerMap serializers = this._dynamicSerializers;
/* 291 */       for (; i < len; i++) {
/* 292 */         elem = value[i];
/* 293 */         if (elem == null)
/* 294 */         { provider.defaultSerializeNull(jgen); }
/*     */         else
/*     */         
/* 297 */         { Class<?> cc = elem.getClass();
/* 298 */           JsonSerializer<Object> serializer = serializers.serializerFor(cc);
/* 299 */           if (serializer == null) {
/* 300 */             serializer = _findAndAddDynamic(serializers, cc, provider);
/*     */           }
/* 302 */           serializer.serializeWithType(elem, jgen, provider, typeSer); } 
/*     */       } 
/* 304 */     } catch (Exception e) {
/* 305 */       wrapAndThrow(provider, e, elem, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 313 */     JsonArrayFormatVisitor arrayVisitor = visitor.expectArrayFormat(typeHint);
/* 314 */     if (arrayVisitor != null) {
/* 315 */       TypeFactory tf = visitor.getProvider().getTypeFactory();
/* 316 */       JavaType contentType = tf.moreSpecificType(this._elementType, typeHint.getContentType());
/* 317 */       if (contentType == null) {
/* 318 */         visitor.getProvider().reportBadDefinition(this._elementType, "Could not resolve type: " + this._elementType);
/*     */       }
/*     */       
/* 321 */       JsonSerializer<?> valueSer = this._elementSerializer;
/* 322 */       if (valueSer == null) {
/* 323 */         valueSer = visitor.getProvider().findValueSerializer(contentType, this._property);
/*     */       }
/* 325 */       arrayVisitor.itemsFormat((JsonFormatVisitable)valueSer, contentType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
/* 332 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/*     */     
/* 334 */     if (map != result.map) {
/* 335 */       this._dynamicSerializers = result.map;
/*     */     }
/* 337 */     return result.serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
/* 343 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/*     */     
/* 345 */     if (map != result.map) {
/* 346 */       this._dynamicSerializers = result.map;
/*     */     }
/* 348 */     return result.serializer;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ObjectArraySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */