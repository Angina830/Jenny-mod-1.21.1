/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
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
/*     */ public abstract class AsArraySerializerBase<T>
/*     */   extends ContainerSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final JavaType _elementType;
/*     */   protected final BeanProperty _property;
/*     */   protected final boolean _staticTyping;
/*     */   protected final Boolean _unwrapSingle;
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */   protected final JsonSerializer<Object> _elementSerializer;
/*     */   protected PropertySerializerMap _dynamicSerializers;
/*     */   
/*     */   protected AsArraySerializerBase(Class<?> cls, JavaType et, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> elementSerializer) {
/*  79 */     super(cls, false);
/*  80 */     this._elementType = et;
/*     */     
/*  82 */     this._staticTyping = (staticTyping || (et != null && et.isFinal()));
/*  83 */     this._valueTypeSerializer = vts;
/*  84 */     this._property = null;
/*  85 */     this._elementSerializer = elementSerializer;
/*  86 */     this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
/*  87 */     this._unwrapSingle = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected AsArraySerializerBase(Class<?> cls, JavaType et, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> elementSerializer) {
/*  99 */     super(cls, false);
/* 100 */     this._elementType = et;
/*     */     
/* 102 */     this._staticTyping = (staticTyping || (et != null && et.isFinal()));
/* 103 */     this._valueTypeSerializer = vts;
/* 104 */     this._property = property;
/* 105 */     this._elementSerializer = elementSerializer;
/* 106 */     this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
/* 107 */     this._unwrapSingle = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AsArraySerializerBase(AsArraySerializerBase<?> src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
/* 115 */     super(src);
/* 116 */     this._elementType = src._elementType;
/* 117 */     this._staticTyping = src._staticTyping;
/* 118 */     this._valueTypeSerializer = vts;
/* 119 */     this._property = property;
/* 120 */     this._elementSerializer = (JsonSerializer)elementSerializer;
/* 121 */     this._dynamicSerializers = src._dynamicSerializers;
/* 122 */     this._unwrapSingle = unwrapSingle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected AsArraySerializerBase(AsArraySerializerBase<?> src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
/* 132 */     this(src, property, vts, elementSerializer, src._unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final AsArraySerializerBase<T> withResolved(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
/* 141 */     return withResolved(property, vts, elementSerializer, this._unwrapSingle);
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
/*     */   public abstract AsArraySerializerBase<T> withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer, Boolean paramBoolean);
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
/* 168 */     TypeSerializer typeSer = this._valueTypeSerializer;
/* 169 */     if (typeSer != null) {
/* 170 */       typeSer = typeSer.forProperty(property);
/*     */     }
/* 172 */     JsonSerializer<?> ser = null;
/* 173 */     Boolean unwrapSingle = null;
/*     */ 
/*     */     
/* 176 */     if (property != null) {
/* 177 */       AnnotationIntrospector intr = serializers.getAnnotationIntrospector();
/* 178 */       AnnotatedMember m = property.getMember();
/* 179 */       if (m != null) {
/* 180 */         Object serDef = intr.findContentSerializer((Annotated)m);
/* 181 */         if (serDef != null) {
/* 182 */           ser = serializers.serializerInstance((Annotated)m, serDef);
/*     */         }
/*     */       } 
/*     */     } 
/* 186 */     JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/* 187 */     if (format != null) {
/* 188 */       unwrapSingle = format.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
/*     */     }
/* 190 */     if (ser == null) {
/* 191 */       ser = this._elementSerializer;
/*     */     }
/*     */     
/* 194 */     ser = findContextualConvertingSerializer(serializers, property, ser);
/* 195 */     if (ser == null)
/*     */     {
/*     */       
/* 198 */       if (this._elementType != null && 
/* 199 */         this._staticTyping && !this._elementType.isJavaLangObject()) {
/* 200 */         ser = serializers.findValueSerializer(this._elementType, property);
/*     */       }
/*     */     }
/*     */     
/* 204 */     if (ser != this._elementSerializer || property != this._property || this._valueTypeSerializer != typeSer || this._unwrapSingle != unwrapSingle)
/*     */     {
/*     */ 
/*     */       
/* 208 */       return (JsonSerializer<?>)withResolved(property, typeSer, ser, unwrapSingle);
/*     */     }
/* 210 */     return (JsonSerializer<?>)this;
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
/* 221 */     return this._elementType;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer() {
/* 226 */     return this._elementSerializer;
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
/*     */   public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 241 */     if (provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && hasSingleElement(value)) {
/*     */       
/* 243 */       serializeContents(value, gen, provider);
/*     */       return;
/*     */     } 
/* 246 */     gen.writeStartArray();
/*     */     
/* 248 */     gen.setCurrentValue(value);
/* 249 */     serializeContents(value, gen, provider);
/* 250 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(T value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 258 */     g.setCurrentValue(value);
/* 259 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
/*     */     
/* 261 */     serializeContents(value, g, provider);
/* 262 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void serializeContents(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/* 273 */     ObjectNode o = createSchemaNode("array", true);
/* 274 */     if (this._elementSerializer != null) {
/* 275 */       JsonNode schemaNode = null;
/* 276 */       if (this._elementSerializer instanceof SchemaAware) {
/* 277 */         schemaNode = ((SchemaAware)this._elementSerializer).getSchema(provider, null);
/*     */       }
/* 279 */       if (schemaNode == null) {
/* 280 */         schemaNode = JsonSchema.getDefaultSchemaNode();
/*     */       }
/* 282 */       o.set("items", schemaNode);
/*     */     } 
/* 284 */     return (JsonNode)o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 291 */     JsonSerializer<?> valueSer = this._elementSerializer;
/* 292 */     if (valueSer == null)
/*     */     {
/*     */       
/* 295 */       if (this._elementType != null) {
/* 296 */         valueSer = visitor.getProvider().findValueSerializer(this._elementType, this._property);
/*     */       }
/*     */     }
/* 299 */     visitArrayFormat(visitor, typeHint, valueSer, this._elementType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
/* 305 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/*     */     
/* 307 */     if (map != result.map) {
/* 308 */       this._dynamicSerializers = result.map;
/*     */     }
/* 310 */     return result.serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
/* 316 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/* 317 */     if (map != result.map) {
/* 318 */       this._dynamicSerializers = result.map;
/*     */     }
/* 320 */     return result.serializer;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\AsArraySerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */