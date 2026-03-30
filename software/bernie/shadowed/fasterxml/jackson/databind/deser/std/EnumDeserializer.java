/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.CompactStringObjectMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.EnumResolver;
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
/*     */ @JacksonStdImpl
/*     */ public class EnumDeserializer
/*     */   extends StdScalarDeserializer<Object>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected Object[] _enumsByIndex;
/*     */   private final Enum<?> _enumDefaultValue;
/*     */   protected final CompactStringObjectMap _lookupByName;
/*     */   protected CompactStringObjectMap _lookupByToString;
/*     */   protected final Boolean _caseInsensitive;
/*     */   
/*     */   public EnumDeserializer(EnumResolver byNameResolver, Boolean caseInsensitive) {
/*  57 */     super(byNameResolver.getEnumClass());
/*  58 */     this._lookupByName = byNameResolver.constructLookup();
/*  59 */     this._enumsByIndex = (Object[])byNameResolver.getRawEnums();
/*  60 */     this._enumDefaultValue = byNameResolver.getDefaultValue();
/*  61 */     this._caseInsensitive = caseInsensitive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumDeserializer(EnumDeserializer base, Boolean caseInsensitive) {
/*  69 */     super(base);
/*  70 */     this._lookupByName = base._lookupByName;
/*  71 */     this._enumsByIndex = base._enumsByIndex;
/*  72 */     this._enumDefaultValue = base._enumDefaultValue;
/*  73 */     this._caseInsensitive = caseInsensitive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EnumDeserializer(EnumResolver byNameResolver) {
/*  81 */     this(byNameResolver, (Boolean)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig config, Class<?> enumClass, AnnotatedMethod factory) {
/*  90 */     return deserializerForCreator(config, enumClass, factory, (ValueInstantiator)null, (SettableBeanProperty[])null);
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
/*     */   public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig config, Class<?> enumClass, AnnotatedMethod factory, ValueInstantiator valueInstantiator, SettableBeanProperty[] creatorProps) {
/* 105 */     if (config.canOverrideAccessModifiers()) {
/* 106 */       ClassUtil.checkAndFixAccess(factory.getMember(), config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */     }
/*     */     
/* 109 */     return new FactoryBasedEnumDeserializer(enumClass, factory, factory.getParameterType(0), valueInstantiator, creatorProps);
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
/*     */   public static JsonDeserializer<?> deserializerForNoArgsCreator(DeserializationConfig config, Class<?> enumClass, AnnotatedMethod factory) {
/* 125 */     if (config.canOverrideAccessModifiers()) {
/* 126 */       ClassUtil.checkAndFixAccess(factory.getMember(), config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */     }
/*     */     
/* 129 */     return new FactoryBasedEnumDeserializer(enumClass, factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDeserializer withResolved(Boolean caseInsensitive) {
/* 136 */     if (this._caseInsensitive == caseInsensitive) {
/* 137 */       return this;
/*     */     }
/* 139 */     return new EnumDeserializer(this, caseInsensitive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 146 */     Boolean caseInsensitive = findFormatFeature(ctxt, property, handledType(), JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
/*     */     
/* 148 */     if (caseInsensitive == null) {
/* 149 */       caseInsensitive = this._caseInsensitive;
/*     */     }
/* 151 */     return withResolved(caseInsensitive);
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
/*     */   public boolean isCachable() {
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 170 */     JsonToken curr = p.getCurrentToken();
/*     */ 
/*     */     
/* 173 */     if (curr == JsonToken.VALUE_STRING || curr == JsonToken.FIELD_NAME) {
/* 174 */       CompactStringObjectMap lookup = ctxt.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING) ? _getToStringLookup(ctxt) : this._lookupByName;
/*     */       
/* 176 */       String name = p.getText();
/* 177 */       Object result = lookup.find(name);
/* 178 */       if (result == null) {
/* 179 */         return _deserializeAltString(p, ctxt, lookup, name);
/*     */       }
/* 181 */       return result;
/*     */     } 
/*     */     
/* 184 */     if (curr == JsonToken.VALUE_NUMBER_INT) {
/*     */       
/* 186 */       int index = p.getIntValue();
/* 187 */       if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
/* 188 */         return ctxt.handleWeirdNumberValue(_enumClass(), Integer.valueOf(index), "not allowed to deserialize Enum value out of number: disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 192 */       if (index >= 0 && index < this._enumsByIndex.length) {
/* 193 */         return this._enumsByIndex[index];
/*     */       }
/* 195 */       if (this._enumDefaultValue != null && ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE))
/*     */       {
/* 197 */         return this._enumDefaultValue;
/*     */       }
/* 199 */       if (!ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 200 */         return ctxt.handleWeirdNumberValue(_enumClass(), Integer.valueOf(index), "index value outside legal index range [0..%s]", new Object[] { Integer.valueOf(this._enumsByIndex.length - 1) });
/*     */       }
/*     */ 
/*     */       
/* 204 */       return null;
/*     */     } 
/* 206 */     return _deserializeOther(p, ctxt);
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
/*     */   private final Object _deserializeAltString(JsonParser p, DeserializationContext ctxt, CompactStringObjectMap lookup, String name) throws IOException {
/* 218 */     name = name.trim();
/* 219 */     if (name.length() == 0) {
/* 220 */       if (ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
/* 221 */         return getEmptyValue(ctxt);
/*     */       
/*     */       }
/*     */     }
/* 225 */     else if (Boolean.TRUE.equals(this._caseInsensitive)) {
/* 226 */       Object match = lookup.findCaseInsensitive(name);
/* 227 */       if (match != null) {
/* 228 */         return match;
/*     */       }
/* 230 */     } else if (!ctxt.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
/*     */       
/* 232 */       char c = name.charAt(0);
/* 233 */       if (c >= '0' && c <= '9') {
/*     */         try {
/* 235 */           int index = Integer.parseInt(name);
/* 236 */           if (!ctxt.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
/* 237 */             return ctxt.handleWeirdStringValue(_enumClass(), name, "value looks like quoted Enum index, but `MapperFeature.ALLOW_COERCION_OF_SCALARS` prevents use", new Object[0]);
/*     */           }
/*     */ 
/*     */           
/* 241 */           if (index >= 0 && index < this._enumsByIndex.length) {
/* 242 */             return this._enumsByIndex[index];
/*     */           }
/* 244 */         } catch (NumberFormatException e) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 250 */     if (this._enumDefaultValue != null && ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE))
/*     */     {
/* 252 */       return this._enumDefaultValue;
/*     */     }
/* 254 */     if (!ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 255 */       return ctxt.handleWeirdStringValue(_enumClass(), name, "value not one of declared Enum instance names: %s", new Object[] { lookup.keys() });
/*     */     }
/*     */     
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _deserializeOther(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 264 */     if (p.hasToken(JsonToken.START_ARRAY)) {
/* 265 */       return _deserializeFromArray(p, ctxt);
/*     */     }
/* 267 */     return ctxt.handleUnexpectedToken(_enumClass(), p);
/*     */   }
/*     */   
/*     */   protected Class<?> _enumClass() {
/* 271 */     return handledType();
/*     */   }
/*     */ 
/*     */   
/*     */   protected CompactStringObjectMap _getToStringLookup(DeserializationContext ctxt) {
/* 276 */     CompactStringObjectMap lookup = this._lookupByToString;
/*     */ 
/*     */     
/* 279 */     if (lookup == null) {
/* 280 */       synchronized (this) {
/* 281 */         lookup = EnumResolver.constructUnsafeUsingToString(_enumClass(), ctxt.getAnnotationIntrospector()).constructLookup();
/*     */       } 
/*     */ 
/*     */       
/* 285 */       this._lookupByToString = lookup;
/*     */     } 
/* 287 */     return lookup;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\EnumDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */