/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Named;
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
/*     */ public interface BeanProperty
/*     */   extends Named
/*     */ {
/*  39 */   public static final JsonFormat.Value EMPTY_FORMAT = new JsonFormat.Value();
/*  40 */   public static final JsonInclude.Value EMPTY_INCLUDE = JsonInclude.Value.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PropertyName getFullName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JavaType getType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PropertyName getWrapperName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PropertyMetadata getMetadata();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isRequired();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isVirtual();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A extends java.lang.annotation.Annotation> A getContextAnnotation(Class<A> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotatedMember getMember();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   JsonFormat.Value findFormatOverrides(AnnotationIntrospector paramAnnotationIntrospector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JsonFormat.Value findPropertyFormat(MapperConfig<?> paramMapperConfig, Class<?> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JsonInclude.Value findPropertyInclusion(MapperConfig<?> paramMapperConfig, Class<?> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<PropertyName> findAliases(MapperConfig<?> paramMapperConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor, SerializerProvider paramSerializerProvider) throws JsonMappingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Std
/*     */     implements BeanProperty, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final PropertyName _name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final JavaType _type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final PropertyName _wrapperName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final PropertyMetadata _metadata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final AnnotatedMember _member;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(PropertyName name, JavaType type, PropertyName wrapperName, AnnotatedMember member, PropertyMetadata metadata) {
/* 235 */       this._name = name;
/* 236 */       this._type = type;
/* 237 */       this._wrapperName = wrapperName;
/* 238 */       this._metadata = metadata;
/* 239 */       this._member = member;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Std(PropertyName name, JavaType type, PropertyName wrapperName, Annotations contextAnnotations, AnnotatedMember member, PropertyMetadata metadata) {
/* 250 */       this(name, type, wrapperName, member, metadata);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(Std base, JavaType newType) {
/* 257 */       this(base._name, newType, base._wrapperName, base._member, base._metadata);
/*     */     }
/*     */     
/*     */     public Std withType(JavaType type) {
/* 261 */       return new Std(this, type);
/*     */     }
/*     */ 
/*     */     
/*     */     public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/* 266 */       return (this._member == null) ? null : (A)this._member.getAnnotation(acls);
/*     */     }
/*     */ 
/*     */     
/*     */     public <A extends java.lang.annotation.Annotation> A getContextAnnotation(Class<A> acls) {
/* 271 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public JsonFormat.Value findFormatOverrides(AnnotationIntrospector intr) {
/* 277 */       if (this._member != null && intr != null) {
/* 278 */         JsonFormat.Value v = intr.findFormat((Annotated)this._member);
/* 279 */         if (v != null) {
/* 280 */           return v;
/*     */         }
/*     */       } 
/* 283 */       return EMPTY_FORMAT;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonFormat.Value findPropertyFormat(MapperConfig<?> config, Class<?> baseType) {
/* 288 */       JsonFormat.Value v0 = config.getDefaultPropertyFormat(baseType);
/* 289 */       AnnotationIntrospector intr = config.getAnnotationIntrospector();
/* 290 */       if (intr == null || this._member == null) {
/* 291 */         return v0;
/*     */       }
/* 293 */       JsonFormat.Value v = intr.findFormat((Annotated)this._member);
/* 294 */       if (v == null) {
/* 295 */         return v0;
/*     */       }
/* 297 */       return v0.withOverrides(v);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonInclude.Value findPropertyInclusion(MapperConfig<?> config, Class<?> baseType) {
/* 303 */       JsonInclude.Value v0 = config.getDefaultInclusion(baseType, this._type.getRawClass());
/* 304 */       AnnotationIntrospector intr = config.getAnnotationIntrospector();
/* 305 */       if (intr == null || this._member == null) {
/* 306 */         return v0;
/*     */       }
/* 308 */       JsonInclude.Value v = intr.findPropertyInclusion((Annotated)this._member);
/* 309 */       if (v == null) {
/* 310 */         return v0;
/*     */       }
/* 312 */       return v0.withOverrides(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<PropertyName> findAliases(MapperConfig<?> config) {
/* 319 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 322 */     public String getName() { return this._name.getSimpleName(); }
/* 323 */     public PropertyName getFullName() { return this._name; }
/* 324 */     public JavaType getType() { return this._type; }
/* 325 */     public PropertyName getWrapperName() { return this._wrapperName; }
/* 326 */     public boolean isRequired() { return this._metadata.isRequired(); }
/* 327 */     public PropertyMetadata getMetadata() { return this._metadata; } public AnnotatedMember getMember() {
/* 328 */       return this._member;
/*     */     }
/*     */     public boolean isVirtual() {
/* 331 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) {
/* 342 */       throw new UnsupportedOperationException("Instances of " + getClass().getName() + " should not get visited");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Bogus
/*     */     implements BeanProperty
/*     */   {
/*     */     public String getName() {
/* 356 */       return "";
/*     */     }
/*     */ 
/*     */     
/*     */     public PropertyName getFullName() {
/* 361 */       return PropertyName.NO_NAME;
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getType() {
/* 366 */       return TypeFactory.unknownType();
/*     */     }
/*     */ 
/*     */     
/*     */     public PropertyName getWrapperName() {
/* 371 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public PropertyMetadata getMetadata() {
/* 376 */       return PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRequired() {
/* 381 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isVirtual() {
/* 386 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/* 391 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public <A extends java.lang.annotation.Annotation> A getContextAnnotation(Class<A> acls) {
/* 396 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedMember getMember() {
/* 401 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public JsonFormat.Value findFormatOverrides(AnnotationIntrospector intr) {
/* 407 */       return JsonFormat.Value.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonFormat.Value findPropertyFormat(MapperConfig<?> config, Class<?> baseType) {
/* 412 */       return JsonFormat.Value.empty();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonInclude.Value findPropertyInclusion(MapperConfig<?> config, Class<?> baseType) {
/* 419 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<PropertyName> findAliases(MapperConfig<?> config) {
/* 424 */       return Collections.emptyList();
/*     */     }
/*     */     
/*     */     public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {}
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\BeanProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */