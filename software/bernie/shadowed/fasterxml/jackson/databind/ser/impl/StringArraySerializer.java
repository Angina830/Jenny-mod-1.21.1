/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class StringArraySerializer
/*     */   extends ArraySerializerBase<String[]>
/*     */   implements ContextualSerializer
/*     */ {
/*  32 */   private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(String.class);
/*     */   
/*  34 */   public static final StringArraySerializer instance = new StringArraySerializer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonSerializer<Object> _elementSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringArraySerializer() {
/*  49 */     super(String[].class);
/*  50 */     this._elementSerializer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StringArraySerializer(StringArraySerializer src, BeanProperty prop, JsonSerializer<?> ser, Boolean unwrapSingle) {
/*  56 */     super(src, prop, unwrapSingle);
/*  57 */     this._elementSerializer = (JsonSerializer)ser;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/*  62 */     return (JsonSerializer<?>)new StringArraySerializer(this, prop, this._elementSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/*  71 */     return (ContainerSerializer<?>)this;
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
/*     */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/*  89 */     JsonSerializer<?> ser = null;
/*     */ 
/*     */     
/*  92 */     if (property != null) {
/*  93 */       AnnotationIntrospector ai = provider.getAnnotationIntrospector();
/*  94 */       AnnotatedMember m = property.getMember();
/*  95 */       if (m != null) {
/*  96 */         Object serDef = ai.findContentSerializer((Annotated)m);
/*  97 */         if (serDef != null) {
/*  98 */           ser = provider.serializerInstance((Annotated)m, serDef);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 104 */     Boolean unwrapSingle = findFormatFeature(provider, property, String[].class, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
/*     */     
/* 106 */     if (ser == null) {
/* 107 */       ser = this._elementSerializer;
/*     */     }
/*     */     
/* 110 */     ser = findContextualConvertingSerializer(provider, property, ser);
/* 111 */     if (ser == null) {
/* 112 */       ser = provider.findValueSerializer(String.class, property);
/*     */     }
/*     */     
/* 115 */     if (isDefaultSerializer(ser)) {
/* 116 */       ser = null;
/*     */     }
/*     */     
/* 119 */     if (ser == this._elementSerializer && unwrapSingle == this._unwrapSingle) {
/* 120 */       return (JsonSerializer<?>)this;
/*     */     }
/* 122 */     return (JsonSerializer<?>)new StringArraySerializer(this, property, ser, unwrapSingle);
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
/* 133 */     return VALUE_TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer() {
/* 138 */     return this._elementSerializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, String[] value) {
/* 143 */     return (value.length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSingleElement(String[] value) {
/* 148 */     return (value.length == 1);
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
/*     */   public final void serialize(String[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 161 */     int len = value.length;
/* 162 */     if (len == 1 && ((
/* 163 */       this._unwrapSingle == null && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
/*     */ 
/*     */       
/* 166 */       serializeContents(value, gen, provider);
/*     */       
/*     */       return;
/*     */     } 
/* 170 */     gen.writeStartArray(len);
/* 171 */     serializeContents(value, gen, provider);
/* 172 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContents(String[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 179 */     int len = value.length;
/* 180 */     if (len == 0) {
/*     */       return;
/*     */     }
/* 183 */     if (this._elementSerializer != null) {
/* 184 */       serializeContentsSlow(value, gen, provider, this._elementSerializer);
/*     */       return;
/*     */     } 
/* 187 */     for (int i = 0; i < len; i++) {
/* 188 */       String str = value[i];
/* 189 */       if (str == null) {
/* 190 */         gen.writeNull();
/*     */       } else {
/* 192 */         gen.writeString(value[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void serializeContentsSlow(String[] value, JsonGenerator gen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
/* 200 */     for (int i = 0, len = value.length; i < len; i++) {
/* 201 */       String str = value[i];
/* 202 */       if (str == null) {
/* 203 */         provider.defaultSerializeNull(gen);
/*     */       } else {
/* 205 */         ser.serialize(value[i], gen, provider);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 212 */     return createSchemaNode("array", true).set("items", (JsonNode)createSchemaNode("string"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 218 */     visitArrayFormat(visitor, typeHint, JsonFormatTypes.STRING);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\StringArraySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */