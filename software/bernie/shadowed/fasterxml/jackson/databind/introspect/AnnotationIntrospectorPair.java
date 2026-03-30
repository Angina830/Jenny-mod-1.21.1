/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonInject;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
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
/*     */ public class AnnotationIntrospectorPair
/*     */   extends AnnotationIntrospector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotationIntrospector _primary;
/*     */   protected final AnnotationIntrospector _secondary;
/*     */   
/*     */   public AnnotationIntrospectorPair(AnnotationIntrospector p, AnnotationIntrospector s) {
/*  52 */     this._primary = p;
/*  53 */     this._secondary = s;
/*     */   }
/*     */ 
/*     */   
/*     */   public Version version() {
/*  58 */     return this._primary.version();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotationIntrospector create(AnnotationIntrospector primary, AnnotationIntrospector secondary) {
/*  69 */     if (primary == null) {
/*  70 */       return secondary;
/*     */     }
/*  72 */     if (secondary == null) {
/*  73 */       return primary;
/*     */     }
/*  75 */     return new AnnotationIntrospectorPair(primary, secondary);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AnnotationIntrospector> allIntrospectors() {
/*  80 */     return allIntrospectors(new ArrayList<>());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> result) {
/*  86 */     this._primary.allIntrospectors(result);
/*  87 */     this._secondary.allIntrospectors(result);
/*  88 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnotationBundle(Annotation ann) {
/*  95 */     return (this._primary.isAnnotationBundle(ann) || this._secondary.isAnnotationBundle(ann));
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
/*     */   public PropertyName findRootName(AnnotatedClass ac) {
/* 107 */     PropertyName name1 = this._primary.findRootName(ac);
/* 108 */     if (name1 == null) {
/* 109 */       return this._secondary.findRootName(ac);
/*     */     }
/* 111 */     if (name1.hasSimpleName()) {
/* 112 */       return name1;
/*     */     }
/*     */     
/* 115 */     PropertyName name2 = this._secondary.findRootName(ac);
/* 116 */     return (name2 == null) ? name1 : name2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated a) {
/* 122 */     JsonIgnoreProperties.Value v2 = this._secondary.findPropertyIgnorals(a);
/* 123 */     JsonIgnoreProperties.Value v1 = this._primary.findPropertyIgnorals(a);
/* 124 */     return (v2 == null) ? v1 : v2.withOverrides(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isIgnorableType(AnnotatedClass ac) {
/* 131 */     Boolean result = this._primary.isIgnorableType(ac);
/* 132 */     if (result == null) {
/* 133 */       result = this._secondary.isIgnorableType(ac);
/*     */     }
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findFilterId(Annotated ann) {
/* 141 */     Object id = this._primary.findFilterId(ann);
/* 142 */     if (id == null) {
/* 143 */       id = this._secondary.findFilterId(ann);
/*     */     }
/* 145 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findNamingStrategy(AnnotatedClass ac) {
/* 151 */     Object str = this._primary.findNamingStrategy(ac);
/* 152 */     if (str == null) {
/* 153 */       str = this._secondary.findNamingStrategy(ac);
/*     */     }
/* 155 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public String findClassDescription(AnnotatedClass ac) {
/* 160 */     String str = this._primary.findClassDescription(ac);
/* 161 */     if (str == null || str.isEmpty()) {
/* 162 */       str = this._secondary.findClassDescription(ac);
/*     */     }
/* 164 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String[] findPropertiesToIgnore(Annotated ac) {
/* 170 */     String[] result = this._primary.findPropertiesToIgnore(ac);
/* 171 */     if (result == null) {
/* 172 */       result = this._secondary.findPropertiesToIgnore(ac);
/*     */     }
/* 174 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String[] findPropertiesToIgnore(Annotated ac, boolean forSerialization) {
/* 180 */     String[] result = this._primary.findPropertiesToIgnore(ac, forSerialization);
/* 181 */     if (result == null) {
/* 182 */       result = this._secondary.findPropertiesToIgnore(ac, forSerialization);
/*     */     }
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
/* 191 */     Boolean result = this._primary.findIgnoreUnknownProperties(ac);
/* 192 */     if (result == null) {
/* 193 */       result = this._secondary.findIgnoreUnknownProperties(ac);
/*     */     }
/* 195 */     return result;
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
/*     */   public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
/* 211 */     checker = this._secondary.findAutoDetectVisibility(ac, checker);
/* 212 */     return this._primary.findAutoDetectVisibility(ac, checker);
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
/*     */   public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
/* 225 */     TypeResolverBuilder<?> b = this._primary.findTypeResolver(config, ac, baseType);
/* 226 */     if (b == null) {
/* 227 */       b = this._secondary.findTypeResolver(config, ac, baseType);
/*     */     }
/* 229 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
/* 236 */     TypeResolverBuilder<?> b = this._primary.findPropertyTypeResolver(config, am, baseType);
/* 237 */     if (b == null) {
/* 238 */       b = this._secondary.findPropertyTypeResolver(config, am, baseType);
/*     */     }
/* 240 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
/* 247 */     TypeResolverBuilder<?> b = this._primary.findPropertyContentTypeResolver(config, am, baseType);
/* 248 */     if (b == null) {
/* 249 */       b = this._secondary.findPropertyContentTypeResolver(config, am, baseType);
/*     */     }
/* 251 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NamedType> findSubtypes(Annotated a) {
/* 257 */     List<NamedType> types1 = this._primary.findSubtypes(a);
/* 258 */     List<NamedType> types2 = this._secondary.findSubtypes(a);
/* 259 */     if (types1 == null || types1.isEmpty()) return types2; 
/* 260 */     if (types2 == null || types2.isEmpty()) return types1; 
/* 261 */     ArrayList<NamedType> result = new ArrayList<>(types1.size() + types2.size());
/* 262 */     result.addAll(types1);
/* 263 */     result.addAll(types2);
/* 264 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String findTypeName(AnnotatedClass ac) {
/* 270 */     String name = this._primary.findTypeName(ac);
/* 271 */     if (name == null || name.length() == 0) {
/* 272 */       name = this._secondary.findTypeName(ac);
/*     */     }
/* 274 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember member) {
/* 284 */     AnnotationIntrospector.ReferenceProperty r = this._primary.findReferenceType(member);
/* 285 */     return (r == null) ? this._secondary.findReferenceType(member) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
/* 290 */     NameTransformer r = this._primary.findUnwrappingNameTransformer(member);
/* 291 */     return (r == null) ? this._secondary.findUnwrappingNameTransformer(member) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public JacksonInject.Value findInjectableValue(AnnotatedMember m) {
/* 296 */     JacksonInject.Value r = this._primary.findInjectableValue(m);
/* 297 */     return (r == null) ? this._secondary.findInjectableValue(m) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIgnoreMarker(AnnotatedMember m) {
/* 302 */     return (this._primary.hasIgnoreMarker(m) || this._secondary.hasIgnoreMarker(m));
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean hasRequiredMarker(AnnotatedMember m) {
/* 307 */     Boolean r = this._primary.hasRequiredMarker(m);
/* 308 */     return (r == null) ? this._secondary.hasRequiredMarker(m) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Object findInjectableValueId(AnnotatedMember m) {
/* 314 */     Object r = this._primary.findInjectableValueId(m);
/* 315 */     return (r == null) ? this._secondary.findInjectableValueId(m) : r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findSerializer(Annotated am) {
/* 322 */     Object r = this._primary.findSerializer(am);
/* 323 */     if (_isExplicitClassOrOb(r, JsonSerializer.None.class)) {
/* 324 */       return r;
/*     */     }
/* 326 */     return _explicitClassOrOb(this._secondary.findSerializer(am), JsonSerializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findKeySerializer(Annotated a) {
/* 332 */     Object r = this._primary.findKeySerializer(a);
/* 333 */     if (_isExplicitClassOrOb(r, JsonSerializer.None.class)) {
/* 334 */       return r;
/*     */     }
/* 336 */     return _explicitClassOrOb(this._secondary.findKeySerializer(a), JsonSerializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findContentSerializer(Annotated a) {
/* 342 */     Object r = this._primary.findContentSerializer(a);
/* 343 */     if (_isExplicitClassOrOb(r, JsonSerializer.None.class)) {
/* 344 */       return r;
/*     */     }
/* 346 */     return _explicitClassOrOb(this._secondary.findContentSerializer(a), JsonSerializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findNullSerializer(Annotated a) {
/* 352 */     Object r = this._primary.findNullSerializer(a);
/* 353 */     if (_isExplicitClassOrOb(r, JsonSerializer.None.class)) {
/* 354 */       return r;
/*     */     }
/* 356 */     return _explicitClassOrOb(this._secondary.findNullSerializer(a), JsonSerializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonInclude.Include findSerializationInclusion(Annotated a, JsonInclude.Include defValue) {
/* 366 */     defValue = this._secondary.findSerializationInclusion(a, defValue);
/* 367 */     defValue = this._primary.findSerializationInclusion(a, defValue);
/* 368 */     return defValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonInclude.Include findSerializationInclusionForContent(Annotated a, JsonInclude.Include defValue) {
/* 376 */     defValue = this._secondary.findSerializationInclusionForContent(a, defValue);
/* 377 */     defValue = this._primary.findSerializationInclusionForContent(a, defValue);
/* 378 */     return defValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonInclude.Value findPropertyInclusion(Annotated a) {
/* 384 */     JsonInclude.Value v2 = this._secondary.findPropertyInclusion(a);
/* 385 */     JsonInclude.Value v1 = this._primary.findPropertyInclusion(a);
/*     */     
/* 387 */     if (v2 == null) {
/* 388 */       return v1;
/*     */     }
/* 390 */     return v2.withOverrides(v1);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerialize.Typing findSerializationTyping(Annotated a) {
/* 395 */     JsonSerialize.Typing r = this._primary.findSerializationTyping(a);
/* 396 */     return (r == null) ? this._secondary.findSerializationTyping(a) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object findSerializationConverter(Annotated a) {
/* 401 */     Object r = this._primary.findSerializationConverter(a);
/* 402 */     return (r == null) ? this._secondary.findSerializationConverter(a) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object findSerializationContentConverter(AnnotatedMember a) {
/* 407 */     Object r = this._primary.findSerializationContentConverter(a);
/* 408 */     return (r == null) ? this._secondary.findSerializationContentConverter(a) : r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?>[] findViews(Annotated a) {
/* 417 */     Class<?>[] result = this._primary.findViews(a);
/* 418 */     if (result == null) {
/* 419 */       result = this._secondary.findViews(a);
/*     */     }
/* 421 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isTypeId(AnnotatedMember member) {
/* 426 */     Boolean b = this._primary.isTypeId(member);
/* 427 */     return (b == null) ? this._secondary.isTypeId(member) : b;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectIdInfo findObjectIdInfo(Annotated ann) {
/* 432 */     ObjectIdInfo r = this._primary.findObjectIdInfo(ann);
/* 433 */     return (r == null) ? this._secondary.findObjectIdInfo(ann) : r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdInfo findObjectReferenceInfo(Annotated ann, ObjectIdInfo objectIdInfo) {
/* 439 */     objectIdInfo = this._secondary.findObjectReferenceInfo(ann, objectIdInfo);
/* 440 */     objectIdInfo = this._primary.findObjectReferenceInfo(ann, objectIdInfo);
/* 441 */     return objectIdInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonFormat.Value findFormat(Annotated ann) {
/* 446 */     JsonFormat.Value v1 = this._primary.findFormat(ann);
/* 447 */     JsonFormat.Value v2 = this._secondary.findFormat(ann);
/* 448 */     if (v2 == null) {
/* 449 */       return v1;
/*     */     }
/* 451 */     return v2.withOverrides(v1);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyName findWrapperName(Annotated ann) {
/* 456 */     PropertyName name = this._primary.findWrapperName(ann);
/* 457 */     if (name == null) {
/* 458 */       name = this._secondary.findWrapperName(ann);
/* 459 */     } else if (name == PropertyName.USE_DEFAULT) {
/*     */       
/* 461 */       PropertyName name2 = this._secondary.findWrapperName(ann);
/* 462 */       if (name2 != null) {
/* 463 */         name = name2;
/*     */       }
/*     */     } 
/* 466 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String findPropertyDefaultValue(Annotated ann) {
/* 471 */     String str = this._primary.findPropertyDefaultValue(ann);
/* 472 */     return (str == null || str.isEmpty()) ? this._secondary.findPropertyDefaultValue(ann) : str;
/*     */   }
/*     */ 
/*     */   
/*     */   public String findPropertyDescription(Annotated ann) {
/* 477 */     String r = this._primary.findPropertyDescription(ann);
/* 478 */     return (r == null) ? this._secondary.findPropertyDescription(ann) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer findPropertyIndex(Annotated ann) {
/* 483 */     Integer r = this._primary.findPropertyIndex(ann);
/* 484 */     return (r == null) ? this._secondary.findPropertyIndex(ann) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public String findImplicitPropertyName(AnnotatedMember ann) {
/* 489 */     String r = this._primary.findImplicitPropertyName(ann);
/* 490 */     return (r == null) ? this._secondary.findImplicitPropertyName(ann) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PropertyName> findPropertyAliases(Annotated ann) {
/* 495 */     List<PropertyName> r = this._primary.findPropertyAliases(ann);
/* 496 */     return (r == null) ? this._secondary.findPropertyAliases(ann) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonProperty.Access findPropertyAccess(Annotated ann) {
/* 501 */     JsonProperty.Access acc = this._primary.findPropertyAccess(ann);
/* 502 */     if (acc != null && acc != JsonProperty.Access.AUTO) {
/* 503 */       return acc;
/*     */     }
/* 505 */     acc = this._secondary.findPropertyAccess(ann);
/* 506 */     if (acc != null) {
/* 507 */       return acc;
/*     */     }
/* 509 */     return JsonProperty.Access.AUTO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMethod resolveSetterConflict(MapperConfig<?> config, AnnotatedMethod setter1, AnnotatedMethod setter2) {
/* 516 */     AnnotatedMethod res = this._primary.resolveSetterConflict(config, setter1, setter2);
/* 517 */     if (res == null) {
/* 518 */       res = this._secondary.resolveSetterConflict(config, setter1, setter2);
/*     */     }
/* 520 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refineSerializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
/* 529 */     JavaType t = this._secondary.refineSerializationType(config, a, baseType);
/* 530 */     return this._primary.refineSerializationType(config, a, t);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Class<?> findSerializationType(Annotated a) {
/* 536 */     Class<?> r = this._primary.findSerializationType(a);
/* 537 */     return (r == null) ? this._secondary.findSerializationType(a) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
/* 543 */     Class<?> r = this._primary.findSerializationKeyType(am, baseType);
/* 544 */     return (r == null) ? this._secondary.findSerializationKeyType(am, baseType) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
/* 550 */     Class<?> r = this._primary.findSerializationContentType(am, baseType);
/* 551 */     return (r == null) ? this._secondary.findSerializationContentType(am, baseType) : r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
/* 558 */     String[] r = this._primary.findSerializationPropertyOrder(ac);
/* 559 */     return (r == null) ? this._secondary.findSerializationPropertyOrder(ac) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean findSerializationSortAlphabetically(Annotated ann) {
/* 564 */     Boolean r = this._primary.findSerializationSortAlphabetically(ann);
/* 565 */     return (r == null) ? this._secondary.findSerializationSortAlphabetically(ann) : r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void findAndAddVirtualProperties(MapperConfig<?> config, AnnotatedClass ac, List<BeanPropertyWriter> properties) {
/* 572 */     this._primary.findAndAddVirtualProperties(config, ac, properties);
/* 573 */     this._secondary.findAndAddVirtualProperties(config, ac, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyName findNameForSerialization(Annotated a) {
/* 580 */     PropertyName n = this._primary.findNameForSerialization(a);
/*     */     
/* 582 */     if (n == null) {
/* 583 */       n = this._secondary.findNameForSerialization(a);
/* 584 */     } else if (n == PropertyName.USE_DEFAULT) {
/* 585 */       PropertyName n2 = this._secondary.findNameForSerialization(a);
/* 586 */       if (n2 != null) {
/* 587 */         n = n2;
/*     */       }
/*     */     } 
/* 590 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean hasAsValue(Annotated a) {
/* 595 */     Boolean b = this._primary.hasAsValue(a);
/* 596 */     if (b == null) {
/* 597 */       b = this._secondary.hasAsValue(a);
/*     */     }
/* 599 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean hasAnyGetter(Annotated a) {
/* 604 */     Boolean b = this._primary.hasAnyGetter(a);
/* 605 */     if (b == null) {
/* 606 */       b = this._secondary.hasAnyGetter(a);
/*     */     }
/* 608 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
/* 614 */     names = this._secondary.findEnumValues(enumType, (Enum[])enumValues, names);
/* 615 */     names = this._primary.findEnumValues(enumType, (Enum[])enumValues, names);
/* 616 */     return names;
/*     */   }
/*     */ 
/*     */   
/*     */   public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
/* 621 */     Enum<?> en = this._primary.findDefaultEnumValue(enumCls);
/* 622 */     return (en == null) ? this._secondary.findDefaultEnumValue(enumCls) : en;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String findEnumValue(Enum<?> value) {
/* 628 */     String r = this._primary.findEnumValue(value);
/* 629 */     return (r == null) ? this._secondary.findEnumValue(value) : r;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasAsValueAnnotation(AnnotatedMethod am) {
/* 635 */     return (this._primary.hasAsValueAnnotation(am) || this._secondary.hasAsValueAnnotation(am));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
/* 641 */     return (this._primary.hasAnyGetterAnnotation(am) || this._secondary.hasAnyGetterAnnotation(am));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findDeserializer(Annotated a) {
/* 648 */     Object r = this._primary.findDeserializer(a);
/* 649 */     if (_isExplicitClassOrOb(r, JsonDeserializer.None.class)) {
/* 650 */       return r;
/*     */     }
/* 652 */     return _explicitClassOrOb(this._secondary.findDeserializer(a), JsonDeserializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findKeyDeserializer(Annotated a) {
/* 658 */     Object r = this._primary.findKeyDeserializer(a);
/* 659 */     if (_isExplicitClassOrOb(r, KeyDeserializer.None.class)) {
/* 660 */       return r;
/*     */     }
/* 662 */     return _explicitClassOrOb(this._secondary.findKeyDeserializer(a), KeyDeserializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findContentDeserializer(Annotated am) {
/* 668 */     Object r = this._primary.findContentDeserializer(am);
/* 669 */     if (_isExplicitClassOrOb(r, JsonDeserializer.None.class)) {
/* 670 */       return r;
/*     */     }
/* 672 */     return _explicitClassOrOb(this._secondary.findContentDeserializer(am), JsonDeserializer.None.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findDeserializationConverter(Annotated a) {
/* 679 */     Object ob = this._primary.findDeserializationConverter(a);
/* 680 */     return (ob == null) ? this._secondary.findDeserializationConverter(a) : ob;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object findDeserializationContentConverter(AnnotatedMember a) {
/* 685 */     Object ob = this._primary.findDeserializationContentConverter(a);
/* 686 */     return (ob == null) ? this._secondary.findDeserializationContentConverter(a) : ob;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType refineDeserializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
/* 696 */     JavaType t = this._secondary.refineDeserializationType(config, a, baseType);
/* 697 */     return this._primary.refineDeserializationType(config, a, t);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
/* 703 */     Class<?> r = this._primary.findDeserializationType(am, baseType);
/* 704 */     return (r != null) ? r : this._secondary.findDeserializationType(am, baseType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
/* 710 */     Class<?> result = this._primary.findDeserializationKeyType(am, baseKeyType);
/* 711 */     return (result == null) ? this._secondary.findDeserializationKeyType(am, baseKeyType) : result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
/* 717 */     Class<?> result = this._primary.findDeserializationContentType(am, baseContentType);
/* 718 */     return (result == null) ? this._secondary.findDeserializationContentType(am, baseContentType) : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findValueInstantiator(AnnotatedClass ac) {
/* 725 */     Object result = this._primary.findValueInstantiator(ac);
/* 726 */     return (result == null) ? this._secondary.findValueInstantiator(ac) : result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> findPOJOBuilder(AnnotatedClass ac) {
/* 731 */     Class<?> result = this._primary.findPOJOBuilder(ac);
/* 732 */     return (result == null) ? this._secondary.findPOJOBuilder(ac) : result;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
/* 737 */     JsonPOJOBuilder.Value result = this._primary.findPOJOBuilderConfig(ac);
/* 738 */     return (result == null) ? this._secondary.findPOJOBuilderConfig(ac) : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyName findNameForDeserialization(Annotated a) {
/* 747 */     PropertyName n = this._primary.findNameForDeserialization(a);
/* 748 */     if (n == null) {
/* 749 */       n = this._secondary.findNameForDeserialization(a);
/* 750 */     } else if (n == PropertyName.USE_DEFAULT) {
/* 751 */       PropertyName n2 = this._secondary.findNameForDeserialization(a);
/* 752 */       if (n2 != null) {
/* 753 */         n = n2;
/*     */       }
/*     */     } 
/* 756 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean hasAnySetter(Annotated a) {
/* 761 */     Boolean b = this._primary.hasAnySetter(a);
/* 762 */     if (b == null) {
/* 763 */       b = this._secondary.hasAnySetter(a);
/*     */     }
/* 765 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSetter.Value findSetterInfo(Annotated a) {
/* 770 */     JsonSetter.Value v2 = this._secondary.findSetterInfo(a);
/* 771 */     JsonSetter.Value v1 = this._primary.findSetterInfo(a);
/* 772 */     return (v2 == null) ? v1 : v2.withOverrides(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean findMergeInfo(Annotated a) {
/* 778 */     Boolean b = this._primary.findMergeInfo(a);
/* 779 */     if (b == null) {
/* 780 */       b = this._secondary.findMergeInfo(a);
/*     */     }
/* 782 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasCreatorAnnotation(Annotated a) {
/* 788 */     return (this._primary.hasCreatorAnnotation(a) || this._secondary.hasCreatorAnnotation(a));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonCreator.Mode findCreatorBinding(Annotated a) {
/* 794 */     JsonCreator.Mode mode = this._primary.findCreatorBinding(a);
/* 795 */     if (mode != null) {
/* 796 */       return mode;
/*     */     }
/* 798 */     return this._secondary.findCreatorBinding(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonCreator.Mode findCreatorAnnotation(MapperConfig<?> config, Annotated a) {
/* 803 */     JsonCreator.Mode mode = this._primary.findCreatorAnnotation(config, a);
/* 804 */     return (mode == null) ? this._secondary.findCreatorAnnotation(config, a) : mode;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
/* 810 */     return (this._primary.hasAnySetterAnnotation(am) || this._secondary.hasAnySetterAnnotation(am));
/*     */   }
/*     */   
/*     */   protected boolean _isExplicitClassOrOb(Object maybeCls, Class<?> implicit) {
/* 814 */     if (maybeCls == null || maybeCls == implicit) {
/* 815 */       return false;
/*     */     }
/* 817 */     if (maybeCls instanceof Class) {
/* 818 */       return !ClassUtil.isBogusClass((Class)maybeCls);
/*     */     }
/* 820 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object _explicitClassOrOb(Object maybeCls, Class<?> implicit) {
/* 825 */     if (maybeCls == null || maybeCls == implicit) {
/* 826 */       return null;
/*     */     }
/* 828 */     if (maybeCls instanceof Class && ClassUtil.isBogusClass((Class)maybeCls)) {
/* 829 */       return null;
/*     */     }
/* 831 */     return maybeCls;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotationIntrospectorPair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */