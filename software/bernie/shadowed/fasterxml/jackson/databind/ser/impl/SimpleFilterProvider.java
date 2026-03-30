/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyFilter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyFilter;
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
/*     */ public class SimpleFilterProvider
/*     */   extends FilterProvider
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Map<String, PropertyFilter> _filtersById;
/*     */   protected PropertyFilter _defaultFilter;
/*     */   protected boolean _cfgFailOnUnknownId = true;
/*     */   
/*     */   public SimpleFilterProvider() {
/*  48 */     this(new HashMap<>());
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
/*     */   public SimpleFilterProvider(Map<String, ?> mapping) {
/*  61 */     for (Object ob : mapping.values()) {
/*  62 */       if (!(ob instanceof PropertyFilter)) {
/*  63 */         this._filtersById = _convert(mapping);
/*     */         return;
/*     */       } 
/*     */     } 
/*  67 */     this._filtersById = (Map)mapping;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Map<String, PropertyFilter> _convert(Map<String, ?> filters) {
/*  73 */     HashMap<String, PropertyFilter> result = new HashMap<>();
/*  74 */     for (Map.Entry<String, ?> entry : filters.entrySet()) {
/*  75 */       Object f = entry.getValue();
/*  76 */       if (f instanceof PropertyFilter) {
/*  77 */         result.put(entry.getKey(), (PropertyFilter)f); continue;
/*  78 */       }  if (f instanceof BeanPropertyFilter) {
/*  79 */         result.put(entry.getKey(), _convert((BeanPropertyFilter)f)); continue;
/*     */       } 
/*  81 */       throw new IllegalArgumentException("Unrecognized filter type (" + f.getClass().getName() + ")");
/*     */     } 
/*     */     
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final PropertyFilter _convert(BeanPropertyFilter f) {
/*  89 */     return SimpleBeanPropertyFilter.from(f);
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
/*     */   public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter f) {
/* 103 */     this._defaultFilter = SimpleBeanPropertyFilter.from(f);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleFilterProvider setDefaultFilter(PropertyFilter f) {
/* 109 */     this._defaultFilter = f;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFilterProvider setDefaultFilter(SimpleBeanPropertyFilter f) {
/* 118 */     this._defaultFilter = f;
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyFilter getDefaultFilter() {
/* 123 */     return this._defaultFilter;
/*     */   }
/*     */   
/*     */   public SimpleFilterProvider setFailOnUnknownId(boolean state) {
/* 127 */     this._cfgFailOnUnknownId = state;
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public boolean willFailOnUnknownId() {
/* 132 */     return this._cfgFailOnUnknownId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public SimpleFilterProvider addFilter(String id, BeanPropertyFilter filter) {
/* 140 */     this._filtersById.put(id, _convert(filter));
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public SimpleFilterProvider addFilter(String id, PropertyFilter filter) {
/* 145 */     this._filtersById.put(id, filter);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFilterProvider addFilter(String id, SimpleBeanPropertyFilter filter) {
/* 153 */     this._filtersById.put(id, filter);
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyFilter removeFilter(String id) {
/* 158 */     return this._filtersById.remove(id);
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
/*     */   @Deprecated
/*     */   public BeanPropertyFilter findFilter(Object filterId) {
/* 171 */     throw new UnsupportedOperationException("Access to deprecated filters not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
/* 177 */     PropertyFilter f = this._filtersById.get(filterId);
/* 178 */     if (f == null) {
/* 179 */       f = this._defaultFilter;
/* 180 */       if (f == null && this._cfgFailOnUnknownId) {
/* 181 */         throw new IllegalArgumentException("No filter configured with id '" + filterId + "' (type " + filterId.getClass().getName() + ")");
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return f;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\SimpleFilterProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */