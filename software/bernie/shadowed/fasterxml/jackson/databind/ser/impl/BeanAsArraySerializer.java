/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
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
/*     */ public class BeanAsArraySerializer
/*     */   extends BeanSerializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final BeanSerializerBase _defaultSerializer;
/*     */   
/*     */   public BeanAsArraySerializer(BeanSerializerBase src) {
/*  64 */     super(src, (ObjectIdWriter)null);
/*  65 */     this._defaultSerializer = src;
/*     */   }
/*     */   
/*     */   protected BeanAsArraySerializer(BeanSerializerBase src, Set<String> toIgnore) {
/*  69 */     super(src, toIgnore);
/*  70 */     this._defaultSerializer = src;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanAsArraySerializer(BeanSerializerBase src, ObjectIdWriter oiw, Object filterId) {
/*  75 */     super(src, oiw, filterId);
/*  76 */     this._defaultSerializer = src;
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
/*     */   public JsonSerializer<Object> unwrappingSerializer(NameTransformer transformer) {
/*  90 */     return this._defaultSerializer.unwrappingSerializer(transformer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnwrappingSerializer() {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
/* 101 */     return this._defaultSerializer.withObjectIdWriter(objectIdWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanSerializerBase withFilterId(Object filterId) {
/* 106 */     return new BeanAsArraySerializer(this, this._objectIdWriter, filterId);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanAsArraySerializer withIgnorals(Set<String> toIgnore) {
/* 111 */     return new BeanAsArraySerializer(this, toIgnore);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase asArraySerializer() {
/* 117 */     return this;
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
/*     */   public void serializeWithType(Object bean, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 135 */     if (this._objectIdWriter != null) {
/* 136 */       _serializeWithObjectId(bean, gen, provider, typeSer);
/*     */       return;
/*     */     } 
/* 139 */     gen.setCurrentValue(bean);
/* 140 */     WritableTypeId typeIdDef = _typeIdDef(typeSer, bean, JsonToken.START_ARRAY);
/* 141 */     typeSer.writeTypePrefix(gen, typeIdDef);
/* 142 */     serializeAsArray(bean, gen, provider);
/* 143 */     typeSer.writeTypeSuffix(gen, typeIdDef);
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
/*     */   public final void serialize(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 155 */     if (provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && hasSingleElement(provider)) {
/*     */       
/* 157 */       serializeAsArray(bean, gen, provider);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 164 */     gen.writeStartArray();
/*     */     
/* 166 */     gen.setCurrentValue(bean);
/* 167 */     serializeAsArray(bean, gen, provider);
/* 168 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasSingleElement(SerializerProvider provider) {
/*     */     BeanPropertyWriter[] props;
/* 179 */     if (this._filteredProps != null && provider.getActiveView() != null) {
/* 180 */       props = this._filteredProps;
/*     */     } else {
/* 182 */       props = this._props;
/*     */     } 
/* 184 */     return (props.length == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void serializeAsArray(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*     */     BeanPropertyWriter[] props;
/* 191 */     if (this._filteredProps != null && provider.getActiveView() != null) {
/* 192 */       props = this._filteredProps;
/*     */     } else {
/* 194 */       props = this._props;
/*     */     } 
/*     */     
/* 197 */     int i = 0;
/*     */     try {
/* 199 */       for (int len = props.length; i < len; i++) {
/* 200 */         BeanPropertyWriter prop = props[i];
/* 201 */         if (prop == null) {
/* 202 */           gen.writeNull();
/*     */         } else {
/* 204 */           prop.serializeAsElement(bean, gen, provider);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 211 */     catch (Exception e) {
/* 212 */       String name = (i == props.length) ? "[anySetter]" : props[i].getName();
/* 213 */       wrapAndThrow(provider, e, bean, name);
/* 214 */     } catch (StackOverflowError e) {
/* 215 */       JsonMappingException mapE = JsonMappingException.from(gen, "Infinite recursion (StackOverflowError)", e);
/* 216 */       String name = (i == props.length) ? "[anySetter]" : props[i].getName();
/* 217 */       mapE.prependPath(new JsonMappingException.Reference(bean, name));
/* 218 */       throw mapE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 229 */     return "BeanAsArraySerializer for " + handledType().getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\BeanAsArraySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */