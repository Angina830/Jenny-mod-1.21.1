/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanSerializerBuilder
/*     */ {
/*  19 */   private static final BeanPropertyWriter[] NO_PROPERTIES = new BeanPropertyWriter[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanDescription _beanDesc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializationConfig _config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   protected List<BeanPropertyWriter> _properties = Collections.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanPropertyWriter[] _filteredProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnyGetterWriter _anyGetter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _filterId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMember _typeId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectIdWriter _objectIdWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSerializerBuilder(BeanDescription beanDesc) {
/*  77 */     this._beanDesc = beanDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanSerializerBuilder(BeanSerializerBuilder src) {
/*  84 */     this._beanDesc = src._beanDesc;
/*  85 */     this._properties = src._properties;
/*  86 */     this._filteredProperties = src._filteredProperties;
/*  87 */     this._anyGetter = src._anyGetter;
/*  88 */     this._filterId = src._filterId;
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
/*     */   protected void setConfig(SerializationConfig config) {
/* 101 */     this._config = config;
/*     */   }
/*     */   
/*     */   public void setProperties(List<BeanPropertyWriter> properties) {
/* 105 */     this._properties = properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFilteredProperties(BeanPropertyWriter[] properties) {
/* 114 */     if (properties != null && 
/* 115 */       properties.length != this._properties.size()) {
/* 116 */       throw new IllegalArgumentException(String.format("Trying to set %d filtered properties; must match length of non-filtered `properties` (%d)", new Object[] { Integer.valueOf(properties.length), Integer.valueOf(this._properties.size()) }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     this._filteredProperties = properties;
/*     */   }
/*     */   
/*     */   public void setAnyGetter(AnyGetterWriter anyGetter) {
/* 125 */     this._anyGetter = anyGetter;
/*     */   }
/*     */   
/*     */   public void setFilterId(Object filterId) {
/* 129 */     this._filterId = filterId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTypeId(AnnotatedMember idProp) {
/* 134 */     if (this._typeId != null) {
/* 135 */       throw new IllegalArgumentException("Multiple type ids specified with " + this._typeId + " and " + idProp);
/*     */     }
/* 137 */     this._typeId = idProp;
/*     */   }
/*     */   
/*     */   public void setObjectIdWriter(ObjectIdWriter w) {
/* 141 */     this._objectIdWriter = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedClass getClassInfo() {
/* 152 */     return this._beanDesc.getClassInfo();
/*     */   } public BeanDescription getBeanDescription() {
/* 154 */     return this._beanDesc;
/*     */   } public List<BeanPropertyWriter> getProperties() {
/* 156 */     return this._properties;
/*     */   } public boolean hasProperties() {
/* 158 */     return (this._properties != null && this._properties.size() > 0);
/*     */   }
/*     */   public BeanPropertyWriter[] getFilteredProperties() {
/* 161 */     return this._filteredProperties;
/*     */   } public AnyGetterWriter getAnyGetter() {
/* 163 */     return this._anyGetter;
/*     */   } public Object getFilterId() {
/* 165 */     return this._filterId;
/*     */   } public AnnotatedMember getTypeId() {
/* 167 */     return this._typeId;
/*     */   } public ObjectIdWriter getObjectIdWriter() {
/* 169 */     return this._objectIdWriter;
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
/*     */   public JsonSerializer<?> build() {
/*     */     BeanPropertyWriter[] properties;
/* 187 */     if (this._properties == null || this._properties.isEmpty()) {
/* 188 */       if (this._anyGetter == null && this._objectIdWriter == null) {
/* 189 */         return null;
/*     */       }
/* 191 */       properties = NO_PROPERTIES;
/*     */     } else {
/* 193 */       properties = this._properties.<BeanPropertyWriter>toArray(new BeanPropertyWriter[this._properties.size()]);
/* 194 */       if (this._config.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
/* 195 */         for (int i = 0, end = properties.length; i < end; i++) {
/* 196 */           properties[i].fixAccess(this._config);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 201 */     if (this._filteredProperties != null && 
/* 202 */       this._filteredProperties.length != this._properties.size()) {
/* 203 */       throw new IllegalStateException(String.format("Mismatch between `properties` size (%d), `filteredProperties` (%s): should have as many (or `null` for latter)", new Object[] { Integer.valueOf(this._properties.size()), Integer.valueOf(this._filteredProperties.length) }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 208 */     if (this._anyGetter != null) {
/* 209 */       this._anyGetter.fixAccess(this._config);
/*     */     }
/* 211 */     if (this._typeId != null && 
/* 212 */       this._config.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
/* 213 */       this._typeId.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */     }
/*     */     
/* 216 */     return (JsonSerializer<?>)new BeanSerializer(this._beanDesc.getType(), this, properties, this._filteredProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSerializer createDummy() {
/* 226 */     return BeanSerializer.createDummy(this._beanDesc.getType());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\BeanSerializerBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */