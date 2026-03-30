/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
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
/*     */ public class BeanSerializer
/*     */   extends BeanSerializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 29L;
/*     */   
/*     */   public BeanSerializer(JavaType type, BeanSerializerBuilder builder, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
/*  45 */     super(type, builder, properties, filteredProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializer(BeanSerializerBase src) {
/*  54 */     super(src);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanSerializer(BeanSerializerBase src, ObjectIdWriter objectIdWriter) {
/*  59 */     super(src, objectIdWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanSerializer(BeanSerializerBase src, ObjectIdWriter objectIdWriter, Object filterId) {
/*  64 */     super(src, objectIdWriter, filterId);
/*     */   }
/*     */   
/*     */   protected BeanSerializer(BeanSerializerBase src, Set<String> toIgnore) {
/*  68 */     super(src, toIgnore);
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
/*     */   public static BeanSerializer createDummy(JavaType forType) {
/*  83 */     return new BeanSerializer(forType, null, NO_PROPS, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> unwrappingSerializer(NameTransformer unwrapper) {
/*  88 */     return (JsonSerializer<Object>)new UnwrappingBeanSerializer(this, unwrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
/*  93 */     return new BeanSerializer(this, objectIdWriter, this._propertyFilterId);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanSerializerBase withFilterId(Object filterId) {
/*  98 */     return new BeanSerializer(this, this._objectIdWriter, filterId);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanSerializerBase withIgnorals(Set<String> toIgnore) {
/* 103 */     return new BeanSerializer(this, toIgnore);
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
/*     */   protected BeanSerializerBase asArraySerializer() {
/* 121 */     if (this._objectIdWriter == null && this._anyGetterWriter == null && this._propertyFilterId == null)
/*     */     {
/*     */ 
/*     */       
/* 125 */       return (BeanSerializerBase)new BeanAsArraySerializer(this);
/*     */     }
/*     */     
/* 128 */     return this;
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
/*     */   public final void serialize(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 146 */     if (this._objectIdWriter != null) {
/* 147 */       gen.setCurrentValue(bean);
/* 148 */       _serializeWithObjectId(bean, gen, provider, true);
/*     */       return;
/*     */     } 
/* 151 */     gen.writeStartObject(bean);
/* 152 */     if (this._propertyFilterId != null) {
/* 153 */       serializeFieldsFiltered(bean, gen, provider);
/*     */     } else {
/* 155 */       serializeFields(bean, gen, provider);
/*     */     } 
/* 157 */     gen.writeEndObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     return "BeanSerializer for " + handledType().getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\BeanSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */