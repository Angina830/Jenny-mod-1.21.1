/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyFilter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyFilter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyWriter;
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
/*     */ public class SimpleBeanPropertyFilter
/*     */   implements BeanPropertyFilter, PropertyFilter
/*     */ {
/*     */   public static SimpleBeanPropertyFilter serializeAll() {
/*  41 */     return SerializeExceptFilter.INCLUDE_ALL;
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
/*     */   public static SimpleBeanPropertyFilter serializeAll(Set<String> properties) {
/*  53 */     return new FilterExceptFilter(properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleBeanPropertyFilter filterOutAllExcept(Set<String> properties) {
/*  61 */     return new FilterExceptFilter(properties);
/*     */   }
/*     */   
/*     */   public static SimpleBeanPropertyFilter filterOutAllExcept(String... propertyArray) {
/*  65 */     HashSet<String> properties = new HashSet<>(propertyArray.length);
/*  66 */     Collections.addAll(properties, propertyArray);
/*  67 */     return new FilterExceptFilter(properties);
/*     */   }
/*     */   
/*     */   public static SimpleBeanPropertyFilter serializeAllExcept(Set<String> properties) {
/*  71 */     return new SerializeExceptFilter(properties);
/*     */   }
/*     */   
/*     */   public static SimpleBeanPropertyFilter serializeAllExcept(String... propertyArray) {
/*  75 */     HashSet<String> properties = new HashSet<>(propertyArray.length);
/*  76 */     Collections.addAll(properties, propertyArray);
/*  77 */     return new SerializeExceptFilter(properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PropertyFilter from(final BeanPropertyFilter src) {
/*  88 */     return new PropertyFilter()
/*     */       {
/*     */         
/*     */         public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer) throws Exception
/*     */         {
/*  93 */           src.serializeAsField(pojo, jgen, prov, (BeanPropertyWriter)writer);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
/* 100 */           src.depositSchemaProperty((BeanPropertyWriter)writer, propertiesNode, provider);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
/* 107 */           src.depositSchemaProperty((BeanPropertyWriter)writer, objectVisitor, provider);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void serializeAsElement(Object elementValue, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer) throws Exception {
/* 115 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
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
/*     */   protected boolean include(BeanPropertyWriter writer) {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean include(PropertyWriter writer) {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean includeElement(Object elementValue) {
/* 153 */     return true;
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
/*     */   @Deprecated
/*     */   public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider provider, BeanPropertyWriter writer) throws Exception {
/* 167 */     if (include(writer)) {
/* 168 */       writer.serializeAsField(bean, jgen, provider);
/* 169 */     } else if (!jgen.canOmitFields()) {
/* 170 */       writer.serializeAsOmittedField(bean, jgen, provider);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void depositSchemaProperty(BeanPropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
/* 180 */     if (include(writer)) {
/* 181 */       writer.depositSchemaProperty(propertiesNode, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void depositSchemaProperty(BeanPropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
/* 191 */     if (include(writer)) {
/* 192 */       writer.depositSchemaProperty(objectVisitor, provider);
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
/*     */   public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
/* 207 */     if (include(writer)) {
/* 208 */       writer.serializeAsField(pojo, jgen, provider);
/* 209 */     } else if (!jgen.canOmitFields()) {
/* 210 */       writer.serializeAsOmittedField(pojo, jgen, provider);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsElement(Object elementValue, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
/* 219 */     if (includeElement(elementValue)) {
/* 220 */       writer.serializeAsElement(elementValue, jgen, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
/* 230 */     if (include(writer)) {
/* 231 */       writer.depositSchemaProperty(propertiesNode, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
/* 240 */     if (include(writer)) {
/* 241 */       writer.depositSchemaProperty(objectVisitor, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FilterExceptFilter
/*     */     extends SimpleBeanPropertyFilter
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final Set<String> _propertiesToInclude;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FilterExceptFilter(Set<String> properties) {
/* 267 */       this._propertiesToInclude = properties;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(BeanPropertyWriter writer) {
/* 272 */       return this._propertiesToInclude.contains(writer.getName());
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(PropertyWriter writer) {
/* 277 */       return this._propertiesToInclude.contains(writer.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SerializeExceptFilter
/*     */     extends SimpleBeanPropertyFilter
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/* 291 */     static final SerializeExceptFilter INCLUDE_ALL = new SerializeExceptFilter();
/*     */ 
/*     */     
/*     */     protected final Set<String> _propertiesToExclude;
/*     */ 
/*     */ 
/*     */     
/*     */     SerializeExceptFilter() {
/* 299 */       this._propertiesToExclude = Collections.emptySet();
/*     */     }
/*     */     
/*     */     public SerializeExceptFilter(Set<String> properties) {
/* 303 */       this._propertiesToExclude = properties;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(BeanPropertyWriter writer) {
/* 308 */       return !this._propertiesToExclude.contains(writer.getName());
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(PropertyWriter writer) {
/* 313 */       return !this._propertiesToExclude.contains(writer.getName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\SimpleBeanPropertyFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */