/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
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
/*     */ public class SimpleBeanPropertyDefinition
/*     */   extends BeanPropertyDefinition
/*     */ {
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */   protected final AnnotatedMember _member;
/*     */   protected final PropertyMetadata _metadata;
/*     */   protected final PropertyName _fullName;
/*     */   protected final JsonInclude.Value _inclusion;
/*     */   
/*     */   protected SimpleBeanPropertyDefinition(AnnotationIntrospector intr, AnnotatedMember member, PropertyName fullName, PropertyMetadata metadata, JsonInclude.Value inclusion) {
/*  61 */     this._annotationIntrospector = intr;
/*  62 */     this._member = member;
/*  63 */     this._fullName = fullName;
/*  64 */     this._metadata = (metadata == null) ? PropertyMetadata.STD_OPTIONAL : metadata;
/*  65 */     this._inclusion = inclusion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member) {
/*  74 */     return new SimpleBeanPropertyDefinition(config.getAnnotationIntrospector(), member, PropertyName.construct(member.getName()), null, EMPTY_INCLUDE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, PropertyName name) {
/*  83 */     return construct(config, member, name, (PropertyMetadata)null, EMPTY_INCLUDE);
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
/*     */   public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, PropertyName name, PropertyMetadata metadata, JsonInclude.Include inclusion) {
/*  95 */     JsonInclude.Value inclValue = (inclusion == null || inclusion == JsonInclude.Include.USE_DEFAULTS) ? EMPTY_INCLUDE : JsonInclude.Value.construct(inclusion, null);
/*     */ 
/*     */     
/*  98 */     return new SimpleBeanPropertyDefinition(config.getAnnotationIntrospector(), member, name, metadata, inclValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, PropertyName name, PropertyMetadata metadata, JsonInclude.Value inclusion) {
/* 108 */     return new SimpleBeanPropertyDefinition(config.getAnnotationIntrospector(), member, name, metadata, inclusion);
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
/*     */   public BeanPropertyDefinition withSimpleName(String newName) {
/* 120 */     if (this._fullName.hasSimpleName(newName) && !this._fullName.hasNamespace()) {
/* 121 */       return this;
/*     */     }
/* 123 */     return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, new PropertyName(newName), this._metadata, this._inclusion);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyDefinition withName(PropertyName newName) {
/* 129 */     if (this._fullName.equals(newName)) {
/* 130 */       return this;
/*     */     }
/* 132 */     return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, newName, this._metadata, this._inclusion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyDefinition withMetadata(PropertyMetadata metadata) {
/* 140 */     if (metadata.equals(this._metadata)) {
/* 141 */       return this;
/*     */     }
/* 143 */     return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, metadata, this._inclusion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyDefinition withInclusion(JsonInclude.Value inclusion) {
/* 151 */     if (this._inclusion == inclusion) {
/* 152 */       return this;
/*     */     }
/* 154 */     return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, this._metadata, inclusion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 165 */     return this._fullName.getSimpleName();
/*     */   }
/*     */   public PropertyName getFullName() {
/* 168 */     return this._fullName;
/*     */   }
/*     */   
/*     */   public boolean hasName(PropertyName name) {
/* 172 */     return this._fullName.equals(name);
/*     */   }
/*     */   
/*     */   public String getInternalName() {
/* 176 */     return getName();
/*     */   }
/*     */   
/*     */   public PropertyName getWrapperName() {
/* 180 */     if (this._annotationIntrospector == null || this._member == null) {
/* 181 */       return null;
/*     */     }
/* 183 */     return this._annotationIntrospector.findWrapperName((Annotated)this._member);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExplicitlyIncluded() {
/* 188 */     return false; } public boolean isExplicitlyNamed() {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyMetadata getMetadata() {
/* 197 */     return this._metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getPrimaryType() {
/* 202 */     if (this._member == null) {
/* 203 */       return TypeFactory.unknownType();
/*     */     }
/* 205 */     return this._member.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> getRawPrimaryType() {
/* 210 */     if (this._member == null) {
/* 211 */       return Object.class;
/*     */     }
/* 213 */     return this._member.getRawType();
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonInclude.Value findInclusion() {
/* 218 */     return this._inclusion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasGetter() {
/* 228 */     return (getGetter() != null);
/*     */   }
/*     */   public boolean hasSetter() {
/* 231 */     return (getSetter() != null);
/*     */   }
/*     */   public boolean hasField() {
/* 234 */     return this._member instanceof AnnotatedField;
/*     */   }
/*     */   public boolean hasConstructorParameter() {
/* 237 */     return this._member instanceof AnnotatedParameter;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod getGetter() {
/* 241 */     if (this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 0)
/*     */     {
/* 243 */       return (AnnotatedMethod)this._member;
/*     */     }
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMethod getSetter() {
/* 250 */     if (this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 1)
/*     */     {
/* 252 */       return (AnnotatedMethod)this._member;
/*     */     }
/* 254 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedField getField() {
/* 259 */     return (this._member instanceof AnnotatedField) ? (AnnotatedField)this._member : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedParameter getConstructorParameter() {
/* 264 */     return (this._member instanceof AnnotatedParameter) ? (AnnotatedParameter)this._member : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<AnnotatedParameter> getConstructorParameters() {
/* 269 */     AnnotatedParameter param = getConstructorParameter();
/* 270 */     if (param == null) {
/* 271 */       return ClassUtil.emptyIterator();
/*     */     }
/* 273 */     return Collections.<AnnotatedParameter>singleton(param).iterator();
/*     */   }
/*     */   
/*     */   public AnnotatedMember getPrimaryMember() {
/* 277 */     return this._member;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\SimpleBeanPropertyDefinition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */