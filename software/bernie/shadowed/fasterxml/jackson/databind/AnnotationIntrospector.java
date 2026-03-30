/*      */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonInject;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AnnotationIntrospector
/*      */   implements Versioned, Serializable
/*      */ {
/*      */   public static class ReferenceProperty
/*      */   {
/*      */     private final Type _type;
/*      */     private final String _name;
/*      */     
/*      */     public enum Type
/*      */     {
/*   64 */       MANAGED_REFERENCE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*   72 */       BACK_REFERENCE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ReferenceProperty(Type t, String n) {
/*   80 */       this._type = t;
/*   81 */       this._name = n;
/*      */     }
/*      */     
/*   84 */     public static ReferenceProperty managed(String name) { return new ReferenceProperty(Type.MANAGED_REFERENCE, name); } public static ReferenceProperty back(String name) {
/*   85 */       return new ReferenceProperty(Type.BACK_REFERENCE, name);
/*      */     }
/*   87 */     public Type getType() { return this._type; } public String getName() {
/*   88 */       return this._name;
/*      */     }
/*   90 */     public boolean isManagedReference() { return (this._type == Type.MANAGED_REFERENCE); } public boolean isBackReference() {
/*   91 */       return (this._type == Type.BACK_REFERENCE);
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
/*      */   public static AnnotationIntrospector nopInstance() {
/*  106 */     return (AnnotationIntrospector)NopAnnotationIntrospector.instance;
/*      */   }
/*      */   
/*      */   public static AnnotationIntrospector pair(AnnotationIntrospector a1, AnnotationIntrospector a2) {
/*  110 */     return (AnnotationIntrospector)new AnnotationIntrospectorPair(a1, a2);
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
/*      */   
/*      */   public Collection<AnnotationIntrospector> allIntrospectors() {
/*  131 */     return Collections.singletonList(this);
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
/*      */   public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> result) {
/*  145 */     result.add(this);
/*  146 */     return result;
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
/*      */   public abstract Version version();
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
/*      */   public boolean isAnnotationBundle(Annotation ann) {
/*  172 */     return false;
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
/*      */   public ObjectIdInfo findObjectIdInfo(Annotated ann) {
/*  192 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectIdInfo findObjectReferenceInfo(Annotated ann, ObjectIdInfo objectIdInfo) {
/*  201 */     return objectIdInfo;
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
/*      */   public PropertyName findRootName(AnnotatedClass ac) {
/*  221 */     return null;
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
/*      */   public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated ac) {
/*  238 */     return JsonIgnoreProperties.Value.empty();
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
/*      */   public Boolean isIgnorableType(AnnotatedClass ac) {
/*  252 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findFilterId(Annotated ann) {
/*  261 */     return null;
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
/*      */   public Object findNamingStrategy(AnnotatedClass ac) {
/*  274 */     return null;
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
/*      */   public String findClassDescription(AnnotatedClass ac) {
/*  287 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String[] findPropertiesToIgnore(Annotated ac, boolean forSerialization) {
/*  299 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String[] findPropertiesToIgnore(Annotated ac) {
/*  307 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
/*  316 */     return null;
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
/*      */   public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
/*  332 */     return checker;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
/*  357 */     return null;
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
/*      */   public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
/*  377 */     return null;
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
/*      */ 
/*      */   
/*      */   public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
/*  399 */     return null;
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
/*      */   public List<NamedType> findSubtypes(Annotated a) {
/*  411 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String findTypeName(AnnotatedClass ac) {
/*  418 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean isTypeId(AnnotatedMember member) {
/*  425 */     return null;
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
/*      */   public ReferenceProperty findReferenceType(AnnotatedMember member) {
/*  437 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
/*  447 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasIgnoreMarker(AnnotatedMember m) {
/*  456 */     return false;
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
/*      */   public JacksonInject.Value findInjectableValue(AnnotatedMember m) {
/*  476 */     Object id = findInjectableValueId(m);
/*  477 */     if (id != null) {
/*  478 */       return JacksonInject.Value.forId(id);
/*      */     }
/*  480 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean hasRequiredMarker(AnnotatedMember m) {
/*  488 */     return null;
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
/*      */   public Class<?>[] findViews(Annotated a) {
/*  506 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonFormat.Value findFormat(Annotated memberOrClass) {
/*  517 */     return JsonFormat.Value.empty();
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
/*      */   public PropertyName findWrapperName(Annotated ann) {
/*  531 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String findPropertyDefaultValue(Annotated ann) {
/*  541 */     return null;
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
/*      */   public String findPropertyDescription(Annotated ann) {
/*  553 */     return null;
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
/*      */   public Integer findPropertyIndex(Annotated ann) {
/*  566 */     return null;
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
/*      */   public String findImplicitPropertyName(AnnotatedMember member) {
/*  581 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<PropertyName> findPropertyAliases(Annotated ann) {
/*  591 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonProperty.Access findPropertyAccess(Annotated ann) {
/*  602 */     return null;
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
/*      */   public AnnotatedMethod resolveSetterConflict(MapperConfig<?> config, AnnotatedMethod setter1, AnnotatedMethod setter2) {
/*  614 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Object findInjectableValueId(AnnotatedMember m) {
/*  622 */     return null;
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
/*      */   public Object findSerializer(Annotated am) {
/*  639 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findKeySerializer(Annotated am) {
/*  650 */     return null;
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
/*      */   public Object findContentSerializer(Annotated am) {
/*  662 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findNullSerializer(Annotated am) {
/*  672 */     return null;
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
/*      */   public JsonSerialize.Typing findSerializationTyping(Annotated a) {
/*  684 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findSerializationConverter(Annotated a) {
/*  709 */     return null;
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
/*      */ 
/*      */   
/*      */   public Object findSerializationContentConverter(AnnotatedMember a) {
/*  731 */     return null;
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
/*      */   public JsonInclude.Value findPropertyInclusion(Annotated a) {
/*  743 */     return JsonInclude.Value.empty();
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
/*      */   
/*      */   @Deprecated
/*      */   public JsonInclude.Include findSerializationInclusion(Annotated a, JsonInclude.Include defValue) {
/*  765 */     return defValue;
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
/*      */   public JsonInclude.Include findSerializationInclusionForContent(Annotated a, JsonInclude.Include defValue) {
/*  779 */     return defValue;
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
/*      */   public JavaType refineSerializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
/*  798 */     return baseType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findSerializationType(Annotated a) {
/*  806 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
/*  814 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
/*  822 */     return null;
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
/*      */   public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
/*  836 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean findSerializationSortAlphabetically(Annotated ann) {
/*  845 */     return null;
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
/*      */   public void findAndAddVirtualProperties(MapperConfig<?> config, AnnotatedClass ac, List<BeanPropertyWriter> properties) {}
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
/*      */   public PropertyName findNameForSerialization(Annotated a) {
/*  878 */     return null;
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
/*      */   public Boolean hasAsValue(Annotated a) {
/*  896 */     if (a instanceof AnnotatedMethod && 
/*  897 */       hasAsValueAnnotation((AnnotatedMethod)a)) {
/*  898 */       return Boolean.valueOf(true);
/*      */     }
/*      */     
/*  901 */     return null;
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
/*      */   public Boolean hasAnyGetter(Annotated a) {
/*  918 */     if (a instanceof AnnotatedMethod && 
/*  919 */       hasAnyGetterAnnotation((AnnotatedMethod)a)) {
/*  920 */       return Boolean.valueOf(true);
/*      */     }
/*      */     
/*  923 */     return null;
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
/*      */   public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
/*  939 */     return names;
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
/*      */   public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
/*  951 */     return null;
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
/*      */   public String findEnumValue(Enum<?> value) {
/*  968 */     return value.name();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAsValueAnnotation(AnnotatedMethod am) {
/*  976 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
/*  984 */     return false;
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
/*      */   public Object findDeserializer(Annotated am) {
/* 1002 */     return null;
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
/*      */   public Object findKeyDeserializer(Annotated am) {
/* 1014 */     return null;
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
/*      */   public Object findContentDeserializer(Annotated am) {
/* 1027 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findDeserializationConverter(Annotated a) {
/* 1053 */     return null;
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
/*      */ 
/*      */   
/*      */   public Object findDeserializationContentConverter(AnnotatedMember a) {
/* 1075 */     return null;
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
/*      */   public JavaType refineDeserializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
/* 1093 */     return baseType;
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
/*      */   @Deprecated
/*      */   public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
/* 1111 */     return null;
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
/*      */   public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
/* 1128 */     return null;
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
/*      */   @Deprecated
/*      */   public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
/* 1146 */     return null;
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
/*      */   public Object findValueInstantiator(AnnotatedClass ac) {
/* 1161 */     return null;
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
/*      */   public Class<?> findPOJOBuilder(AnnotatedClass ac) {
/* 1178 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
/* 1185 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyName findNameForDeserialization(Annotated a) {
/* 1209 */     return null;
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
/*      */   public Boolean hasAnySetter(Annotated a) {
/* 1224 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSetter.Value findSetterInfo(Annotated a) {
/* 1234 */     return JsonSetter.Value.empty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean findMergeInfo(Annotated a) {
/* 1243 */     return null;
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
/*      */   
/*      */   public JsonCreator.Mode findCreatorAnnotation(MapperConfig<?> config, Annotated a) {
/* 1264 */     if (hasCreatorAnnotation(a)) {
/* 1265 */       JsonCreator.Mode mode = findCreatorBinding(a);
/* 1266 */       if (mode == null) {
/* 1267 */         mode = JsonCreator.Mode.DEFAULT;
/*      */       }
/* 1269 */       return mode;
/*      */     } 
/* 1271 */     return null;
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
/*      */   public boolean hasCreatorAnnotation(Annotated a) {
/* 1288 */     return false;
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
/*      */   public JsonCreator.Mode findCreatorBinding(Annotated a) {
/* 1302 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
/* 1310 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <A extends Annotation> A _findAnnotation(Annotated annotated, Class<A> annoClass) {
/* 1336 */     return (A)annotated.getAnnotation(annoClass);
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
/*      */   protected boolean _hasAnnotation(Annotated annotated, Class<? extends Annotation> annoClass) {
/* 1353 */     return annotated.hasAnnotation(annoClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasOneOf(Annotated annotated, Class<? extends Annotation>[] annoClasses) {
/* 1363 */     return annotated.hasOneOf((Class[])annoClasses);
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\AnnotationIntrospector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */