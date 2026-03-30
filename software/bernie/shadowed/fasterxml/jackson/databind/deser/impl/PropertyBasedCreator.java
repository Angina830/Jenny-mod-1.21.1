/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
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
/*     */ public final class PropertyBasedCreator
/*     */ {
/*     */   protected final int _propertyCount;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final HashMap<String, SettableBeanProperty> _propertyLookup;
/*     */   protected final SettableBeanProperty[] _allProperties;
/*     */   
/*     */   protected PropertyBasedCreator(DeserializationContext ctxt, ValueInstantiator valueInstantiator, SettableBeanProperty[] creatorProps, boolean caseInsensitive, boolean addAliases) {
/*  59 */     this._valueInstantiator = valueInstantiator;
/*  60 */     if (caseInsensitive) {
/*  61 */       this._propertyLookup = new CaseInsensitiveMap();
/*     */     } else {
/*  63 */       this._propertyLookup = new HashMap<>();
/*     */     } 
/*  65 */     int len = creatorProps.length;
/*  66 */     this._propertyCount = len;
/*  67 */     this._allProperties = new SettableBeanProperty[len];
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (addAliases) {
/*  72 */       DeserializationConfig config = ctxt.getConfig();
/*  73 */       for (SettableBeanProperty prop : creatorProps) {
/*  74 */         List<PropertyName> aliases = prop.findAliases((MapperConfig)config);
/*  75 */         if (!aliases.isEmpty()) {
/*  76 */           for (PropertyName pn : aliases) {
/*  77 */             this._propertyLookup.put(pn.getSimpleName(), prop);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*  82 */     for (int i = 0; i < len; i++) {
/*  83 */       SettableBeanProperty prop = creatorProps[i];
/*  84 */       this._allProperties[i] = prop;
/*  85 */       this._propertyLookup.put(prop.getName(), prop);
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
/*     */   public static PropertyBasedCreator construct(DeserializationContext ctxt, ValueInstantiator valueInstantiator, SettableBeanProperty[] srcCreatorProps, BeanPropertyMap allProperties) throws JsonMappingException {
/* 100 */     int len = srcCreatorProps.length;
/* 101 */     SettableBeanProperty[] creatorProps = new SettableBeanProperty[len];
/* 102 */     for (int i = 0; i < len; i++) {
/* 103 */       SettableBeanProperty prop = srcCreatorProps[i];
/* 104 */       if (!prop.hasValueDeserializer()) {
/* 105 */         prop = prop.withValueDeserializer(ctxt.findContextualValueDeserializer(prop.getType(), (BeanProperty)prop));
/*     */       }
/* 107 */       creatorProps[i] = prop;
/*     */     } 
/* 109 */     return new PropertyBasedCreator(ctxt, valueInstantiator, creatorProps, allProperties.isCaseInsensitive(), allProperties.hasAliases());
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
/*     */   public static PropertyBasedCreator construct(DeserializationContext ctxt, ValueInstantiator valueInstantiator, SettableBeanProperty[] srcCreatorProps, boolean caseInsensitive) throws JsonMappingException {
/* 126 */     int len = srcCreatorProps.length;
/* 127 */     SettableBeanProperty[] creatorProps = new SettableBeanProperty[len];
/* 128 */     for (int i = 0; i < len; i++) {
/* 129 */       SettableBeanProperty prop = srcCreatorProps[i];
/* 130 */       if (!prop.hasValueDeserializer()) {
/* 131 */         prop = prop.withValueDeserializer(ctxt.findContextualValueDeserializer(prop.getType(), (BeanProperty)prop));
/*     */       }
/* 133 */       creatorProps[i] = prop;
/*     */     } 
/* 135 */     return new PropertyBasedCreator(ctxt, valueInstantiator, creatorProps, caseInsensitive, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PropertyBasedCreator construct(DeserializationContext ctxt, ValueInstantiator valueInstantiator, SettableBeanProperty[] srcCreatorProps) throws JsonMappingException {
/* 144 */     return construct(ctxt, valueInstantiator, srcCreatorProps, ctxt.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<SettableBeanProperty> properties() {
/* 155 */     return this._propertyLookup.values();
/*     */   }
/*     */   
/*     */   public SettableBeanProperty findCreatorProperty(String name) {
/* 159 */     return this._propertyLookup.get(name);
/*     */   }
/*     */   
/*     */   public SettableBeanProperty findCreatorProperty(int propertyIndex) {
/* 163 */     for (SettableBeanProperty prop : this._propertyLookup.values()) {
/* 164 */       if (prop.getPropertyIndex() == propertyIndex) {
/* 165 */         return prop;
/*     */       }
/*     */     } 
/* 168 */     return null;
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
/*     */   public PropertyValueBuffer startBuilding(JsonParser p, DeserializationContext ctxt, ObjectIdReader oir) {
/* 184 */     return new PropertyValueBuffer(p, ctxt, this._propertyCount, oir);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object build(DeserializationContext ctxt, PropertyValueBuffer buffer) throws IOException {
/* 189 */     Object bean = this._valueInstantiator.createFromObjectWith(ctxt, this._allProperties, buffer);
/*     */ 
/*     */     
/* 192 */     if (bean != null) {
/*     */       
/* 194 */       bean = buffer.handleIdValue(ctxt, bean);
/*     */ 
/*     */       
/* 197 */       for (PropertyValue pv = buffer.buffered(); pv != null; pv = pv.next) {
/* 198 */         pv.assign(bean);
/*     */       }
/*     */     } 
/* 201 */     return bean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CaseInsensitiveMap
/*     */     extends HashMap<String, SettableBeanProperty>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SettableBeanProperty get(Object key0) {
/* 222 */       return super.get(((String)key0).toLowerCase());
/*     */     }
/*     */ 
/*     */     
/*     */     public SettableBeanProperty put(String key, SettableBeanProperty value) {
/* 227 */       key = key.toLowerCase();
/* 228 */       return super.put(key, value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\PropertyBasedCreator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */