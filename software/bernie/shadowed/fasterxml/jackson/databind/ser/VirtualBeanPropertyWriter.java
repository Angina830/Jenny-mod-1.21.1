/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VirtualBeanPropertyWriter
/*     */   extends BeanPropertyWriter
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   protected VirtualBeanPropertyWriter(BeanPropertyDefinition propDef, Annotations contextAnnotations, JavaType declaredType) {
/*  35 */     this(propDef, contextAnnotations, declaredType, (JsonSerializer<?>)null, (TypeSerializer)null, (JavaType)null, propDef.findInclusion());
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
/*     */   protected VirtualBeanPropertyWriter() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VirtualBeanPropertyWriter(BeanPropertyDefinition propDef, Annotations contextAnnotations, JavaType declaredType, JsonSerializer<?> ser, TypeSerializer typeSer, JavaType serType, JsonInclude.Value inclusion, Class<?>[] includeInViews) {
/*  57 */     super(propDef, propDef.getPrimaryMember(), contextAnnotations, declaredType, ser, typeSer, serType, _suppressNulls(inclusion), _suppressableValue(inclusion), includeInViews);
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
/*     */   protected VirtualBeanPropertyWriter(BeanPropertyDefinition propDef, Annotations contextAnnotations, JavaType declaredType, JsonSerializer<?> ser, TypeSerializer typeSer, JavaType serType, JsonInclude.Value inclusion) {
/*  69 */     this(propDef, contextAnnotations, declaredType, ser, typeSer, serType, inclusion, (Class<?>[])null);
/*     */   }
/*     */   
/*     */   protected VirtualBeanPropertyWriter(VirtualBeanPropertyWriter base) {
/*  73 */     super(base);
/*     */   }
/*     */   
/*     */   protected VirtualBeanPropertyWriter(VirtualBeanPropertyWriter base, PropertyName name) {
/*  77 */     super(base, name);
/*     */   }
/*     */   
/*     */   protected static boolean _suppressNulls(JsonInclude.Value inclusion) {
/*  81 */     if (inclusion == null) {
/*  82 */       return false;
/*     */     }
/*  84 */     JsonInclude.Include incl = inclusion.getValueInclusion();
/*  85 */     return (incl != JsonInclude.Include.ALWAYS && incl != JsonInclude.Include.USE_DEFAULTS);
/*     */   }
/*     */   
/*     */   protected static Object _suppressableValue(JsonInclude.Value inclusion) {
/*  89 */     if (inclusion == null) {
/*  90 */       return Boolean.valueOf(false);
/*     */     }
/*  92 */     JsonInclude.Include incl = inclusion.getValueInclusion();
/*  93 */     if (incl == JsonInclude.Include.ALWAYS || incl == JsonInclude.Include.NON_NULL || incl == JsonInclude.Include.USE_DEFAULTS)
/*     */     {
/*     */       
/*  96 */       return null;
/*     */     }
/*  98 */     return MARKER_FOR_EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVirtual() {
/* 108 */     return true;
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
/*     */   protected abstract Object value(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract VirtualBeanPropertyWriter withConfig(MapperConfig<?> paramMapperConfig, AnnotatedClass paramAnnotatedClass, BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/* 151 */     Object value = value(bean, gen, prov);
/*     */     
/* 153 */     if (value == null) {
/* 154 */       if (this._nullSerializer != null) {
/* 155 */         gen.writeFieldName((SerializableString)this._name);
/* 156 */         this._nullSerializer.serialize(null, gen, prov);
/*     */       } 
/*     */       return;
/*     */     } 
/* 160 */     JsonSerializer<Object> ser = this._serializer;
/* 161 */     if (ser == null) {
/* 162 */       Class<?> cls = value.getClass();
/* 163 */       PropertySerializerMap m = this._dynamicSerializers;
/* 164 */       ser = m.serializerFor(cls);
/* 165 */       if (ser == null) {
/* 166 */         ser = _findAndAddDynamic(m, cls, prov);
/*     */       }
/*     */     } 
/* 169 */     if (this._suppressableValue != null) {
/* 170 */       if (MARKER_FOR_EMPTY == this._suppressableValue) {
/* 171 */         if (ser.isEmpty(prov, value)) {
/*     */           return;
/*     */         }
/* 174 */       } else if (this._suppressableValue.equals(value)) {
/*     */         return;
/*     */       } 
/*     */     }
/* 178 */     if (value == bean)
/*     */     {
/* 180 */       if (_handleSelfReference(bean, gen, prov, ser)) {
/*     */         return;
/*     */       }
/*     */     }
/* 184 */     gen.writeFieldName((SerializableString)this._name);
/* 185 */     if (this._typeSerializer == null) {
/* 186 */       ser.serialize(value, gen, prov);
/*     */     } else {
/* 188 */       ser.serializeWithType(value, gen, prov, this._typeSerializer);
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
/*     */   public void serializeAsElement(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/* 200 */     Object value = value(bean, gen, prov);
/*     */     
/* 202 */     if (value == null) {
/* 203 */       if (this._nullSerializer != null) {
/* 204 */         this._nullSerializer.serialize(null, gen, prov);
/*     */       } else {
/* 206 */         gen.writeNull();
/*     */       } 
/*     */       return;
/*     */     } 
/* 210 */     JsonSerializer<Object> ser = this._serializer;
/* 211 */     if (ser == null) {
/* 212 */       Class<?> cls = value.getClass();
/* 213 */       PropertySerializerMap map = this._dynamicSerializers;
/* 214 */       ser = map.serializerFor(cls);
/* 215 */       if (ser == null) {
/* 216 */         ser = _findAndAddDynamic(map, cls, prov);
/*     */       }
/*     */     } 
/* 219 */     if (this._suppressableValue != null) {
/* 220 */       if (MARKER_FOR_EMPTY == this._suppressableValue) {
/* 221 */         if (ser.isEmpty(prov, value)) {
/* 222 */           serializeAsPlaceholder(bean, gen, prov);
/*     */           return;
/*     */         } 
/* 225 */       } else if (this._suppressableValue.equals(value)) {
/* 226 */         serializeAsPlaceholder(bean, gen, prov);
/*     */         return;
/*     */       } 
/*     */     }
/* 230 */     if (value == bean && 
/* 231 */       _handleSelfReference(bean, gen, prov, ser)) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     if (this._typeSerializer == null) {
/* 236 */       ser.serialize(value, gen, prov);
/*     */     } else {
/* 238 */       ser.serializeWithType(value, gen, prov, this._typeSerializer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\VirtualBeanPropertyWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */