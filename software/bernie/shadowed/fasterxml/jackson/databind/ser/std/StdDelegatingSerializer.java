/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ResolvableSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
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
/*     */ public class StdDelegatingSerializer
/*     */   extends StdSerializer<Object>
/*     */   implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware
/*     */ {
/*     */   protected final Converter<Object, ?> _converter;
/*     */   protected final JavaType _delegateType;
/*     */   protected final JsonSerializer<Object> _delegateSerializer;
/*     */   
/*     */   public StdDelegatingSerializer(Converter<?, ?> converter) {
/*  55 */     super(Object.class);
/*  56 */     this._converter = (Converter)converter;
/*  57 */     this._delegateType = null;
/*  58 */     this._delegateSerializer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> StdDelegatingSerializer(Class<T> cls, Converter<T, ?> converter) {
/*  64 */     super(cls, false);
/*  65 */     this._converter = (Converter)converter;
/*  66 */     this._delegateType = null;
/*  67 */     this._delegateSerializer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdDelegatingSerializer(Converter<Object, ?> converter, JavaType delegateType, JsonSerializer<?> delegateSerializer) {
/*  74 */     super(delegateType);
/*  75 */     this._converter = converter;
/*  76 */     this._delegateType = delegateType;
/*  77 */     this._delegateSerializer = (JsonSerializer)delegateSerializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdDelegatingSerializer withDelegate(Converter<Object, ?> converter, JavaType delegateType, JsonSerializer<?> delegateSerializer) {
/*  87 */     ClassUtil.verifyMustOverride(StdDelegatingSerializer.class, this, "withDelegate");
/*  88 */     return new StdDelegatingSerializer(converter, delegateType, delegateSerializer);
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
/*     */   public void resolve(SerializerProvider provider) throws JsonMappingException {
/* 100 */     if (this._delegateSerializer != null && this._delegateSerializer instanceof ResolvableSerializer)
/*     */     {
/* 102 */       ((ResolvableSerializer)this._delegateSerializer).resolve(provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/* 110 */     JsonSerializer<?> delSer = this._delegateSerializer;
/* 111 */     JavaType delegateType = this._delegateType;
/*     */     
/* 113 */     if (delSer == null) {
/*     */       
/* 115 */       if (delegateType == null) {
/* 116 */         delegateType = this._converter.getOutputType(provider.getTypeFactory());
/*     */       }
/*     */ 
/*     */       
/* 120 */       if (!delegateType.isJavaLangObject()) {
/* 121 */         delSer = provider.findValueSerializer(delegateType);
/*     */       }
/*     */     } 
/* 124 */     if (delSer instanceof ContextualSerializer) {
/* 125 */       delSer = provider.handleSecondaryContextualization(delSer, property);
/*     */     }
/* 127 */     if (delSer == this._delegateSerializer && delegateType == this._delegateType) {
/* 128 */       return this;
/*     */     }
/* 130 */     return withDelegate(this._converter, delegateType, delSer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Converter<Object, ?> getConverter() {
/* 140 */     return this._converter;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> getDelegatee() {
/* 145 */     return this._delegateSerializer;
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
/*     */   public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 157 */     Object delegateValue = convertValue(value);
/*     */     
/* 159 */     if (delegateValue == null) {
/* 160 */       provider.defaultSerializeNull(gen);
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     JsonSerializer<Object> ser = this._delegateSerializer;
/* 165 */     if (ser == null) {
/* 166 */       ser = _findSerializer(delegateValue, provider);
/*     */     }
/* 168 */     ser.serialize(delegateValue, gen, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 178 */     Object delegateValue = convertValue(value);
/* 179 */     JsonSerializer<Object> ser = this._delegateSerializer;
/* 180 */     if (ser == null) {
/* 181 */       ser = _findSerializer(value, provider);
/*     */     }
/* 183 */     ser.serializeWithType(delegateValue, gen, provider, typeSer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, Object value) {
/* 189 */     Object delegateValue = convertValue(value);
/* 190 */     if (delegateValue == null) {
/* 191 */       return true;
/*     */     }
/* 193 */     if (this._delegateSerializer == null) {
/* 194 */       return (value == null);
/*     */     }
/* 196 */     return this._delegateSerializer.isEmpty(prov, delegateValue);
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
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
/* 209 */     if (this._delegateSerializer instanceof SchemaAware) {
/* 210 */       return ((SchemaAware)this._delegateSerializer).getSchema(provider, typeHint);
/*     */     }
/* 212 */     return super.getSchema(provider, typeHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint, boolean isOptional) throws JsonMappingException {
/* 219 */     if (this._delegateSerializer instanceof SchemaAware) {
/* 220 */       return ((SchemaAware)this._delegateSerializer).getSchema(provider, typeHint, isOptional);
/*     */     }
/* 222 */     return super.getSchema(provider, typeHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 233 */     if (this._delegateSerializer != null) {
/* 234 */       this._delegateSerializer.acceptJsonFormatVisitor(visitor, typeHint);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object convertValue(Object value) {
/* 256 */     return this._converter.convert(value);
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
/*     */   protected JsonSerializer<Object> _findSerializer(Object value, SerializerProvider serializers) throws JsonMappingException {
/* 271 */     return serializers.findValueSerializer(value.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StdDelegatingSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */