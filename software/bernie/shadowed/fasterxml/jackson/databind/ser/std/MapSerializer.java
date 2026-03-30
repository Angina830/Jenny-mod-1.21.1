/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyFilter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.BeanUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ @JacksonStdImpl
/*      */ public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer {
/*   40 */   protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 1L;
/*      */   
/*   45 */   public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final BeanProperty _property;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _valueTypeIsStatic;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JavaType _keyType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JavaType _valueType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _keySerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonSerializer<Object> _valueSerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TypeSerializer _valueTypeSerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertySerializerMap _dynamicValueSerializers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Set<String> _ignoredEntries;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object _filterId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object _suppressableValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _suppressNulls;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _sortKeys;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MapSerializer(Set<String> ignoredEntries, JavaType keyType, JavaType valueType, boolean valueTypeIsStatic, TypeSerializer vts, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer) {
/*  168 */     super(Map.class, false);
/*  169 */     this._ignoredEntries = (ignoredEntries == null || ignoredEntries.isEmpty()) ? null : ignoredEntries;
/*      */     
/*  171 */     this._keyType = keyType;
/*  172 */     this._valueType = valueType;
/*  173 */     this._valueTypeIsStatic = valueTypeIsStatic;
/*  174 */     this._valueTypeSerializer = vts;
/*  175 */     this._keySerializer = (JsonSerializer)keySerializer;
/*  176 */     this._valueSerializer = (JsonSerializer)valueSerializer;
/*  177 */     this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
/*  178 */     this._property = null;
/*  179 */     this._filterId = null;
/*  180 */     this._sortKeys = false;
/*  181 */     this._suppressableValue = null;
/*  182 */     this._suppressNulls = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MapSerializer(MapSerializer src, BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Set<String> ignoredEntries) {
/*  190 */     super(Map.class, false);
/*  191 */     this._ignoredEntries = (ignoredEntries == null || ignoredEntries.isEmpty()) ? null : ignoredEntries;
/*      */     
/*  193 */     this._keyType = src._keyType;
/*  194 */     this._valueType = src._valueType;
/*  195 */     this._valueTypeIsStatic = src._valueTypeIsStatic;
/*  196 */     this._valueTypeSerializer = src._valueTypeSerializer;
/*  197 */     this._keySerializer = (JsonSerializer)keySerializer;
/*  198 */     this._valueSerializer = (JsonSerializer)valueSerializer;
/*  199 */     this._dynamicValueSerializers = src._dynamicValueSerializers;
/*  200 */     this._property = property;
/*  201 */     this._filterId = src._filterId;
/*  202 */     this._sortKeys = src._sortKeys;
/*  203 */     this._suppressableValue = src._suppressableValue;
/*  204 */     this._suppressNulls = src._suppressNulls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MapSerializer(MapSerializer src, TypeSerializer vts, Object suppressableValue, boolean suppressNulls) {
/*  213 */     super(Map.class, false);
/*  214 */     this._ignoredEntries = src._ignoredEntries;
/*  215 */     this._keyType = src._keyType;
/*  216 */     this._valueType = src._valueType;
/*  217 */     this._valueTypeIsStatic = src._valueTypeIsStatic;
/*  218 */     this._valueTypeSerializer = vts;
/*  219 */     this._keySerializer = src._keySerializer;
/*  220 */     this._valueSerializer = src._valueSerializer;
/*  221 */     this._dynamicValueSerializers = src._dynamicValueSerializers;
/*  222 */     this._property = src._property;
/*  223 */     this._filterId = src._filterId;
/*  224 */     this._sortKeys = src._sortKeys;
/*  225 */     this._suppressableValue = suppressableValue;
/*  226 */     this._suppressNulls = suppressNulls;
/*      */   }
/*      */ 
/*      */   
/*      */   protected MapSerializer(MapSerializer src, Object filterId, boolean sortKeys) {
/*  231 */     super(Map.class, false);
/*  232 */     this._ignoredEntries = src._ignoredEntries;
/*  233 */     this._keyType = src._keyType;
/*  234 */     this._valueType = src._valueType;
/*  235 */     this._valueTypeIsStatic = src._valueTypeIsStatic;
/*  236 */     this._valueTypeSerializer = src._valueTypeSerializer;
/*  237 */     this._keySerializer = src._keySerializer;
/*  238 */     this._valueSerializer = src._valueSerializer;
/*  239 */     this._dynamicValueSerializers = src._dynamicValueSerializers;
/*  240 */     this._property = src._property;
/*  241 */     this._filterId = filterId;
/*  242 */     this._sortKeys = sortKeys;
/*  243 */     this._suppressableValue = src._suppressableValue;
/*  244 */     this._suppressNulls = src._suppressNulls;
/*      */   }
/*      */ 
/*      */   
/*      */   public MapSerializer _withValueTypeSerializer(TypeSerializer vts) {
/*  249 */     if (this._valueTypeSerializer == vts) {
/*  250 */       return this;
/*      */     }
/*  252 */     _ensureOverride("_withValueTypeSerializer");
/*  253 */     return new MapSerializer(this, vts, this._suppressableValue, this._suppressNulls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapSerializer withResolved(BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Set<String> ignored, boolean sortKeys) {
/*  263 */     _ensureOverride("withResolved");
/*  264 */     MapSerializer ser = new MapSerializer(this, property, keySerializer, valueSerializer, ignored);
/*  265 */     if (sortKeys != ser._sortKeys) {
/*  266 */       ser = new MapSerializer(ser, this._filterId, sortKeys);
/*      */     }
/*  268 */     return ser;
/*      */   }
/*      */ 
/*      */   
/*      */   public MapSerializer withFilterId(Object filterId) {
/*  273 */     if (this._filterId == filterId) {
/*  274 */       return this;
/*      */     }
/*  276 */     _ensureOverride("withFilterId");
/*  277 */     return new MapSerializer(this, filterId, this._sortKeys);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapSerializer withContentInclusion(Object suppressableValue, boolean suppressNulls) {
/*  287 */     if (suppressableValue == this._suppressableValue && suppressNulls == this._suppressNulls) {
/*  288 */       return this;
/*      */     }
/*  290 */     _ensureOverride("withContentInclusion");
/*  291 */     return new MapSerializer(this, this._valueTypeSerializer, suppressableValue, suppressNulls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MapSerializer construct(Set<String> ignoredEntries, JavaType mapType, boolean staticValueType, TypeSerializer vts, JsonSerializer<Object> keySerializer, JsonSerializer<Object> valueSerializer, Object filterId) {
/*      */     JavaType keyType, valueType;
/*  304 */     if (mapType == null) {
/*  305 */       keyType = valueType = UNSPECIFIED_TYPE;
/*      */     } else {
/*  307 */       keyType = mapType.getKeyType();
/*  308 */       valueType = mapType.getContentType();
/*      */     } 
/*      */     
/*  311 */     if (!staticValueType) {
/*  312 */       staticValueType = (valueType != null && valueType.isFinal());
/*      */     
/*      */     }
/*  315 */     else if (valueType.getRawClass() == Object.class) {
/*  316 */       staticValueType = false;
/*      */     } 
/*      */     
/*  319 */     MapSerializer ser = new MapSerializer(ignoredEntries, keyType, valueType, staticValueType, vts, keySerializer, valueSerializer);
/*      */     
/*  321 */     if (filterId != null) {
/*  322 */       ser = ser.withFilterId(filterId);
/*      */     }
/*  324 */     return ser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _ensureOverride(String method) {
/*  331 */     ClassUtil.verifyMustOverride(MapSerializer.class, this, method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void _ensureOverride() {
/*  339 */     _ensureOverride("N/A");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected MapSerializer(MapSerializer src, TypeSerializer vts, Object suppressableValue) {
/*  356 */     this(src, vts, suppressableValue, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MapSerializer withContentInclusion(Object suppressableValue) {
/*  364 */     return new MapSerializer(this, this._valueTypeSerializer, suppressableValue, this._suppressNulls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static MapSerializer construct(String[] ignoredList, JavaType mapType, boolean staticValueType, TypeSerializer vts, JsonSerializer<Object> keySerializer, JsonSerializer<Object> valueSerializer, Object filterId) {
/*  378 */     Set<String> ignoredEntries = ArrayBuilders.arrayToSet((Object[])ignoredList);
/*  379 */     return construct(ignoredEntries, mapType, staticValueType, vts, keySerializer, valueSerializer, filterId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
/*  394 */     JsonSerializer<?> ser = null;
/*  395 */     JsonSerializer<?> keySer = null;
/*  396 */     AnnotationIntrospector intr = provider.getAnnotationIntrospector();
/*  397 */     AnnotatedMember propertyAcc = (property == null) ? null : property.getMember();
/*      */ 
/*      */     
/*  400 */     if (_neitherNull(propertyAcc, intr)) {
/*  401 */       Object serDef = intr.findKeySerializer((Annotated)propertyAcc);
/*  402 */       if (serDef != null) {
/*  403 */         keySer = provider.serializerInstance((Annotated)propertyAcc, serDef);
/*      */       }
/*  405 */       serDef = intr.findContentSerializer((Annotated)propertyAcc);
/*  406 */       if (serDef != null) {
/*  407 */         ser = provider.serializerInstance((Annotated)propertyAcc, serDef);
/*      */       }
/*      */     } 
/*  410 */     if (ser == null) {
/*  411 */       ser = this._valueSerializer;
/*      */     }
/*      */     
/*  414 */     ser = findContextualConvertingSerializer(provider, property, ser);
/*  415 */     if (ser == null)
/*      */     {
/*      */ 
/*      */       
/*  419 */       if (this._valueTypeIsStatic && !this._valueType.isJavaLangObject()) {
/*  420 */         ser = provider.findValueSerializer(this._valueType, property);
/*      */       }
/*      */     }
/*  423 */     if (keySer == null) {
/*  424 */       keySer = this._keySerializer;
/*      */     }
/*  426 */     if (keySer == null) {
/*  427 */       keySer = provider.findKeySerializer(this._keyType, property);
/*      */     } else {
/*  429 */       keySer = provider.handleSecondaryContextualization(keySer, property);
/*      */     } 
/*  431 */     Set<String> ignored = this._ignoredEntries;
/*  432 */     boolean sortKeys = false;
/*  433 */     if (_neitherNull(propertyAcc, intr)) {
/*  434 */       JsonIgnoreProperties.Value ignorals = intr.findPropertyIgnorals((Annotated)propertyAcc);
/*  435 */       if (ignorals != null) {
/*  436 */         Set<String> newIgnored = ignorals.findIgnoredForSerialization();
/*  437 */         if (_nonEmpty(newIgnored)) {
/*  438 */           ignored = (ignored == null) ? new HashSet<>() : new HashSet<>(ignored);
/*  439 */           for (String str : newIgnored) {
/*  440 */             ignored.add(str);
/*      */           }
/*      */         } 
/*      */       } 
/*  444 */       Boolean b = intr.findSerializationSortAlphabetically((Annotated)propertyAcc);
/*  445 */       sortKeys = Boolean.TRUE.equals(b);
/*      */     } 
/*  447 */     JsonFormat.Value format = findFormatOverrides(provider, property, Map.class);
/*  448 */     if (format != null) {
/*  449 */       Boolean B = format.getFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);
/*  450 */       if (B != null) {
/*  451 */         sortKeys = B.booleanValue();
/*      */       }
/*      */     } 
/*  454 */     MapSerializer mser = withResolved(property, keySer, ser, ignored, sortKeys);
/*      */ 
/*      */     
/*  457 */     if (property != null) {
/*  458 */       AnnotatedMember m = property.getMember();
/*  459 */       if (m != null) {
/*  460 */         Object filterId = intr.findFilterId((Annotated)m);
/*  461 */         if (filterId != null) {
/*  462 */           mser = mser.withFilterId(filterId);
/*      */         }
/*      */       } 
/*  465 */       JsonInclude.Value inclV = property.findPropertyInclusion((MapperConfig)provider.getConfig(), null);
/*  466 */       if (inclV != null) {
/*  467 */         JsonInclude.Include incl = inclV.getContentInclusion();
/*      */         
/*  469 */         if (incl != JsonInclude.Include.USE_DEFAULTS) {
/*      */           Object valueToSuppress;
/*      */           boolean suppressNulls;
/*  472 */           switch (incl) {
/*      */             case NON_DEFAULT:
/*  474 */               valueToSuppress = BeanUtil.getDefaultValue(this._valueType);
/*  475 */               suppressNulls = true;
/*  476 */               if (valueToSuppress != null && 
/*  477 */                 valueToSuppress.getClass().isArray()) {
/*  478 */                 valueToSuppress = ArrayBuilders.getArrayComparator(valueToSuppress);
/*      */               }
/*      */               break;
/*      */             
/*      */             case NON_ABSENT:
/*  483 */               suppressNulls = true;
/*  484 */               valueToSuppress = this._valueType.isReferenceType() ? MARKER_FOR_EMPTY : null;
/*      */               break;
/*      */             case NON_EMPTY:
/*  487 */               suppressNulls = true;
/*  488 */               valueToSuppress = MARKER_FOR_EMPTY;
/*      */               break;
/*      */             case CUSTOM:
/*  491 */               valueToSuppress = provider.includeFilterInstance(null, inclV.getContentFilter());
/*  492 */               if (valueToSuppress == null) {
/*  493 */                 suppressNulls = true; break;
/*      */               } 
/*  495 */               suppressNulls = provider.includeFilterSuppressNulls(valueToSuppress);
/*      */               break;
/*      */             
/*      */             case NON_NULL:
/*  499 */               valueToSuppress = null;
/*  500 */               suppressNulls = true;
/*      */               break;
/*      */             
/*      */             default:
/*  504 */               valueToSuppress = null;
/*      */ 
/*      */               
/*  507 */               suppressNulls = false;
/*      */               break;
/*      */           } 
/*  510 */           mser = mser.withContentInclusion(valueToSuppress, suppressNulls);
/*      */         } 
/*      */       } 
/*      */     } 
/*  514 */     return (JsonSerializer<?>)mser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType getContentType() {
/*  525 */     return this._valueType;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonSerializer<?> getContentSerializer() {
/*  530 */     return this._valueSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty(SerializerProvider prov, Map<?, ?> value) {
/*  536 */     if (value.isEmpty()) {
/*  537 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  542 */     Object supp = this._suppressableValue;
/*  543 */     if (supp == null && !this._suppressNulls) {
/*  544 */       return false;
/*      */     }
/*  546 */     JsonSerializer<Object> valueSer = this._valueSerializer;
/*  547 */     boolean checkEmpty = (MARKER_FOR_EMPTY == supp);
/*  548 */     if (valueSer != null) {
/*  549 */       for (Object elemValue : value.values()) {
/*  550 */         if (elemValue == null) {
/*  551 */           if (this._suppressNulls) {
/*      */             continue;
/*      */           }
/*  554 */           return false;
/*      */         } 
/*  556 */         if (checkEmpty) {
/*  557 */           if (!valueSer.isEmpty(prov, elemValue))
/*  558 */             return false;  continue;
/*      */         } 
/*  560 */         if (supp == null || !supp.equals(value)) {
/*  561 */           return false;
/*      */         }
/*      */       } 
/*  564 */       return true;
/*      */     } 
/*      */     
/*  567 */     for (Object elemValue : value.values()) {
/*  568 */       if (elemValue == null) {
/*  569 */         if (this._suppressNulls) {
/*      */           continue;
/*      */         }
/*  572 */         return false;
/*      */       } 
/*      */       try {
/*  575 */         valueSer = _findSerializer(prov, elemValue);
/*  576 */       } catch (JsonMappingException e) {
/*      */         
/*  578 */         return false;
/*      */       } 
/*  580 */       if (checkEmpty) {
/*  581 */         if (!valueSer.isEmpty(prov, elemValue))
/*  582 */           return false;  continue;
/*      */       } 
/*  584 */       if (supp == null || !supp.equals(value)) {
/*  585 */         return false;
/*      */       }
/*      */     } 
/*  588 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasSingleElement(Map<?, ?> value) {
/*  593 */     return (value.size() == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerializer<?> getKeySerializer() {
/*  613 */     return this._keySerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serialize(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*  626 */     gen.writeStartObject(value);
/*  627 */     if (!value.isEmpty()) {
/*  628 */       if (this._sortKeys || provider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
/*  629 */         value = _orderEntries(value, gen, provider);
/*      */       }
/*      */       PropertyFilter pf;
/*  632 */       if (this._filterId != null && (pf = findPropertyFilter(provider, this._filterId, value)) != null) {
/*  633 */         serializeFilteredFields(value, gen, provider, pf, this._suppressableValue);
/*  634 */       } else if (this._suppressableValue != null || this._suppressNulls) {
/*  635 */         serializeOptionalFields(value, gen, provider, this._suppressableValue);
/*  636 */       } else if (this._valueSerializer != null) {
/*  637 */         serializeFieldsUsing(value, gen, provider, this._valueSerializer);
/*      */       } else {
/*  639 */         serializeFields(value, gen, provider);
/*      */       } 
/*      */     } 
/*  642 */     gen.writeEndObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeWithType(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*  651 */     gen.setCurrentValue(value);
/*  652 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));
/*      */     
/*  654 */     if (!value.isEmpty()) {
/*  655 */       if (this._sortKeys || provider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
/*  656 */         value = _orderEntries(value, gen, provider);
/*      */       }
/*      */       PropertyFilter pf;
/*  659 */       if (this._filterId != null && (pf = findPropertyFilter(provider, this._filterId, value)) != null) {
/*  660 */         serializeFilteredFields(value, gen, provider, pf, this._suppressableValue);
/*  661 */       } else if (this._suppressableValue != null || this._suppressNulls) {
/*  662 */         serializeOptionalFields(value, gen, provider, this._suppressableValue);
/*  663 */       } else if (this._valueSerializer != null) {
/*  664 */         serializeFieldsUsing(value, gen, provider, this._valueSerializer);
/*      */       } else {
/*  666 */         serializeFields(value, gen, provider);
/*      */       } 
/*      */     } 
/*  669 */     typeSer.writeTypeSuffix(gen, typeIdDef);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*  687 */     if (this._valueTypeSerializer != null) {
/*  688 */       serializeTypedFields(value, gen, provider, (Object)null);
/*      */       return;
/*      */     } 
/*  691 */     JsonSerializer<Object> keySerializer = this._keySerializer;
/*  692 */     Set<String> ignored = this._ignoredEntries;
/*  693 */     Object keyElem = null;
/*      */     
/*      */     try {
/*  696 */       for (Map.Entry<?, ?> entry : value.entrySet()) {
/*  697 */         Object valueElem = entry.getValue();
/*      */         
/*  699 */         keyElem = entry.getKey();
/*  700 */         if (keyElem == null) {
/*  701 */           provider.findNullKeySerializer(this._keyType, this._property).serialize(null, gen, provider);
/*      */         } else {
/*      */           
/*  704 */           if (ignored != null && ignored.contains(keyElem)) {
/*      */             continue;
/*      */           }
/*  707 */           keySerializer.serialize(keyElem, gen, provider);
/*      */         } 
/*      */         
/*  710 */         if (valueElem == null) {
/*  711 */           provider.defaultSerializeNull(gen);
/*      */           continue;
/*      */         } 
/*  714 */         JsonSerializer<Object> serializer = this._valueSerializer;
/*  715 */         if (serializer == null) {
/*  716 */           serializer = _findSerializer(provider, valueElem);
/*      */         }
/*  718 */         serializer.serialize(valueElem, gen, provider);
/*      */       } 
/*  720 */     } catch (Exception e) {
/*  721 */       wrapAndThrow(provider, e, value, String.valueOf(keyElem));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeOptionalFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, Object suppressableValue) throws IOException {
/*  733 */     if (this._valueTypeSerializer != null) {
/*  734 */       serializeTypedFields(value, gen, provider, suppressableValue);
/*      */       return;
/*      */     } 
/*  737 */     Set<String> ignored = this._ignoredEntries;
/*  738 */     boolean checkEmpty = (MARKER_FOR_EMPTY == suppressableValue);
/*      */     
/*  740 */     for (Map.Entry<?, ?> entry : value.entrySet()) {
/*      */       JsonSerializer<Object> keySerializer, valueSer;
/*  742 */       Object keyElem = entry.getKey();
/*      */       
/*  744 */       if (keyElem == null) {
/*  745 */         keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
/*      */       } else {
/*  747 */         if (ignored != null && ignored.contains(keyElem))
/*  748 */           continue;  keySerializer = this._keySerializer;
/*      */       } 
/*      */ 
/*      */       
/*  752 */       Object valueElem = entry.getValue();
/*      */       
/*  754 */       if (valueElem == null) {
/*  755 */         if (this._suppressNulls) {
/*      */           continue;
/*      */         }
/*  758 */         valueSer = provider.getDefaultNullValueSerializer();
/*      */       } else {
/*  760 */         valueSer = this._valueSerializer;
/*  761 */         if (valueSer == null) {
/*  762 */           valueSer = _findSerializer(provider, valueElem);
/*      */         }
/*      */         
/*  765 */         if (checkEmpty ? 
/*  766 */           valueSer.isEmpty(provider, valueElem) : (
/*      */ 
/*      */           
/*  769 */           suppressableValue != null && 
/*  770 */           suppressableValue.equals(valueElem))) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  777 */         keySerializer.serialize(keyElem, gen, provider);
/*  778 */         valueSer.serialize(valueElem, gen, provider);
/*  779 */       } catch (Exception e) {
/*  780 */         wrapAndThrow(provider, e, value, String.valueOf(keyElem));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeFieldsUsing(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
/*  794 */     JsonSerializer<Object> keySerializer = this._keySerializer;
/*  795 */     Set<String> ignored = this._ignoredEntries;
/*  796 */     TypeSerializer typeSer = this._valueTypeSerializer;
/*      */     
/*  798 */     for (Map.Entry<?, ?> entry : value.entrySet()) {
/*  799 */       Object keyElem = entry.getKey();
/*  800 */       if (ignored != null && ignored.contains(keyElem))
/*      */         continue; 
/*  802 */       if (keyElem == null) {
/*  803 */         provider.findNullKeySerializer(this._keyType, this._property).serialize(null, gen, provider);
/*      */       } else {
/*  805 */         keySerializer.serialize(keyElem, gen, provider);
/*      */       } 
/*  807 */       Object valueElem = entry.getValue();
/*  808 */       if (valueElem == null) {
/*  809 */         provider.defaultSerializeNull(gen); continue;
/*      */       } 
/*      */       try {
/*  812 */         if (typeSer == null) {
/*  813 */           ser.serialize(valueElem, gen, provider); continue;
/*      */         } 
/*  815 */         ser.serializeWithType(valueElem, gen, provider, typeSer);
/*      */       }
/*  817 */       catch (Exception e) {
/*  818 */         wrapAndThrow(provider, e, value, String.valueOf(keyElem));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeFilteredFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, PropertyFilter filter, Object suppressableValue) throws IOException {
/*  835 */     Set<String> ignored = this._ignoredEntries;
/*  836 */     MapProperty prop = new MapProperty(this._valueTypeSerializer, this._property);
/*  837 */     boolean checkEmpty = (MARKER_FOR_EMPTY == suppressableValue);
/*      */     
/*  839 */     for (Map.Entry<?, ?> entry : value.entrySet()) {
/*      */       JsonSerializer<Object> keySerializer, valueSer;
/*  841 */       Object keyElem = entry.getKey();
/*  842 */       if (ignored != null && ignored.contains(keyElem)) {
/*      */         continue;
/*      */       }
/*  845 */       if (keyElem == null) {
/*  846 */         keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
/*      */       } else {
/*  848 */         keySerializer = this._keySerializer;
/*      */       } 
/*      */       
/*  851 */       Object valueElem = entry.getValue();
/*      */ 
/*      */ 
/*      */       
/*  855 */       if (valueElem == null) {
/*  856 */         if (this._suppressNulls) {
/*      */           continue;
/*      */         }
/*  859 */         valueSer = provider.getDefaultNullValueSerializer();
/*      */       } else {
/*  861 */         valueSer = this._valueSerializer;
/*  862 */         if (valueSer == null) {
/*  863 */           valueSer = _findSerializer(provider, valueElem);
/*      */         }
/*      */         
/*  866 */         if (checkEmpty ? 
/*  867 */           valueSer.isEmpty(provider, valueElem) : (
/*      */ 
/*      */           
/*  870 */           suppressableValue != null && 
/*  871 */           suppressableValue.equals(valueElem))) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  877 */       prop.reset(keyElem, valueElem, keySerializer, valueSer);
/*      */       try {
/*  879 */         filter.serializeAsField(value, gen, provider, prop);
/*  880 */       } catch (Exception e) {
/*  881 */         wrapAndThrow(provider, e, value, String.valueOf(keyElem));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeTypedFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, Object suppressableValue) throws IOException {
/*  893 */     Set<String> ignored = this._ignoredEntries;
/*  894 */     boolean checkEmpty = (MARKER_FOR_EMPTY == suppressableValue);
/*      */     
/*  896 */     for (Map.Entry<?, ?> entry : value.entrySet()) {
/*  897 */       JsonSerializer<Object> keySerializer, valueSer; Object keyElem = entry.getKey();
/*      */       
/*  899 */       if (keyElem == null) {
/*  900 */         keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
/*      */       } else {
/*      */         
/*  903 */         if (ignored != null && ignored.contains(keyElem))
/*  904 */           continue;  keySerializer = this._keySerializer;
/*      */       } 
/*  906 */       Object valueElem = entry.getValue();
/*      */ 
/*      */ 
/*      */       
/*  910 */       if (valueElem == null) {
/*  911 */         if (this._suppressNulls) {
/*      */           continue;
/*      */         }
/*  914 */         valueSer = provider.getDefaultNullValueSerializer();
/*      */       } else {
/*  916 */         valueSer = this._valueSerializer;
/*  917 */         if (valueSer == null) {
/*  918 */           valueSer = _findSerializer(provider, valueElem);
/*      */         }
/*      */         
/*  921 */         if (checkEmpty ? 
/*  922 */           valueSer.isEmpty(provider, valueElem) : (
/*      */ 
/*      */           
/*  925 */           suppressableValue != null && 
/*  926 */           suppressableValue.equals(valueElem))) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */       
/*  931 */       keySerializer.serialize(keyElem, gen, provider);
/*      */       try {
/*  933 */         valueSer.serializeWithType(valueElem, gen, provider, this._valueTypeSerializer);
/*  934 */       } catch (Exception e) {
/*  935 */         wrapAndThrow(provider, e, value, String.valueOf(keyElem));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeFilteredAnyProperties(SerializerProvider provider, JsonGenerator gen, Object bean, Map<?, ?> value, PropertyFilter filter, Object suppressableValue) throws IOException {
/*  953 */     Set<String> ignored = this._ignoredEntries;
/*  954 */     MapProperty prop = new MapProperty(this._valueTypeSerializer, this._property);
/*  955 */     boolean checkEmpty = (MARKER_FOR_EMPTY == suppressableValue);
/*      */     
/*  957 */     for (Map.Entry<?, ?> entry : value.entrySet()) {
/*      */       JsonSerializer<Object> keySerializer, valueSer;
/*  959 */       Object keyElem = entry.getKey();
/*  960 */       if (ignored != null && ignored.contains(keyElem)) {
/*      */         continue;
/*      */       }
/*  963 */       if (keyElem == null) {
/*  964 */         keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
/*      */       } else {
/*  966 */         keySerializer = this._keySerializer;
/*      */       } 
/*      */       
/*  969 */       Object valueElem = entry.getValue();
/*      */ 
/*      */ 
/*      */       
/*  973 */       if (valueElem == null) {
/*  974 */         if (this._suppressNulls) {
/*      */           continue;
/*      */         }
/*  977 */         valueSer = provider.getDefaultNullValueSerializer();
/*      */       } else {
/*  979 */         valueSer = this._valueSerializer;
/*  980 */         if (valueSer == null) {
/*  981 */           valueSer = _findSerializer(provider, valueElem);
/*      */         }
/*      */         
/*  984 */         if (checkEmpty ? 
/*  985 */           valueSer.isEmpty(provider, valueElem) : (
/*      */ 
/*      */           
/*  988 */           suppressableValue != null && 
/*  989 */           suppressableValue.equals(valueElem))) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  995 */       prop.reset(keyElem, valueElem, keySerializer, valueSer);
/*      */       try {
/*  997 */         filter.serializeAsField(bean, gen, provider, prop);
/*  998 */       } catch (Exception e) {
/*  999 */         wrapAndThrow(provider, e, value, String.valueOf(keyElem));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 1015 */     return (JsonNode)createSchemaNode("object", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 1022 */     JsonMapFormatVisitor v2 = visitor.expectMapFormat(typeHint);
/* 1023 */     if (v2 != null) {
/* 1024 */       v2.keyFormat((JsonFormatVisitable)this._keySerializer, this._keyType);
/* 1025 */       JsonSerializer<?> valueSer = this._valueSerializer;
/* 1026 */       if (valueSer == null) {
/* 1027 */         valueSer = _findAndAddDynamic(this._dynamicValueSerializers, this._valueType, visitor.getProvider());
/*      */       }
/*      */       
/* 1030 */       v2.valueFormat((JsonFormatVisitable)valueSer, this._valueType);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
/* 1043 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/*      */     
/* 1045 */     if (map != result.map) {
/* 1046 */       this._dynamicValueSerializers = result.map;
/*      */     }
/* 1048 */     return result.serializer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
/* 1054 */     PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
/* 1055 */     if (map != result.map) {
/* 1056 */       this._dynamicValueSerializers = result.map;
/*      */     }
/* 1058 */     return result.serializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<?, ?> _orderEntries(Map<?, ?> input, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 1065 */     if (input instanceof java.util.SortedMap) {
/* 1066 */       return input;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1072 */     if (_hasNullKey(input)) {
/* 1073 */       TreeMap<Object, Object> result = new TreeMap<>();
/* 1074 */       for (Map.Entry<?, ?> entry : input.entrySet()) {
/* 1075 */         Object key = entry.getKey();
/* 1076 */         if (key == null) {
/* 1077 */           _writeNullKeyedEntry(gen, provider, entry.getValue());
/*      */           continue;
/*      */         } 
/* 1080 */         result.put(key, entry.getValue());
/*      */       } 
/* 1082 */       return result;
/*      */     } 
/* 1084 */     return new TreeMap<>(input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasNullKey(Map<?, ?> input) {
/* 1100 */     return (input instanceof java.util.HashMap && input.containsKey(null));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _writeNullKeyedEntry(JsonGenerator gen, SerializerProvider provider, Object value) throws IOException {
/* 1106 */     JsonSerializer<Object> valueSer, keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
/*      */     
/* 1108 */     if (value == null) {
/* 1109 */       if (this._suppressNulls) {
/*      */         return;
/*      */       }
/* 1112 */       valueSer = provider.getDefaultNullValueSerializer();
/*      */     } else {
/* 1114 */       valueSer = this._valueSerializer;
/* 1115 */       if (valueSer == null) {
/* 1116 */         valueSer = _findSerializer(provider, value);
/*      */       }
/* 1118 */       if (this._suppressableValue == MARKER_FOR_EMPTY) {
/* 1119 */         if (valueSer.isEmpty(provider, value)) {
/*      */           return;
/*      */         }
/* 1122 */       } else if (this._suppressableValue != null && this._suppressableValue.equals(value)) {
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1129 */       keySerializer.serialize(null, gen, provider);
/* 1130 */       valueSer.serialize(value, gen, provider);
/* 1131 */     } catch (Exception e) {
/* 1132 */       wrapAndThrow(provider, e, value, "");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final JsonSerializer<Object> _findSerializer(SerializerProvider provider, Object value) throws JsonMappingException {
/* 1139 */     Class<?> cc = value.getClass();
/* 1140 */     JsonSerializer<Object> valueSer = this._dynamicValueSerializers.serializerFor(cc);
/* 1141 */     if (valueSer != null) {
/* 1142 */       return valueSer;
/*      */     }
/* 1144 */     if (this._valueType.hasGenericTypes()) {
/* 1145 */       return _findAndAddDynamic(this._dynamicValueSerializers, provider.constructSpecializedType(this._valueType, cc), provider);
/*      */     }
/*      */     
/* 1148 */     return _findAndAddDynamic(this._dynamicValueSerializers, cc, provider);
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\MapSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */