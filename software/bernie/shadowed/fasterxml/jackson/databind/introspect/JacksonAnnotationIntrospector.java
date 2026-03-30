/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.List;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonInject;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonBackReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIdentityInfo;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonPropertyOrder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSubTypes;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonUnwrapped;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonValue;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonView;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonAppend;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonDeserialize;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapLikeType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ public class JacksonAnnotationIntrospector extends AnnotationIntrospector implements Serializable {
/*   37 */   private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_SER = new Class[] { JsonSerialize.class, JsonView.class, JsonFormat.class, JsonTypeInfo.class, JsonRawValue.class, JsonUnwrapped.class, JsonBackReference.class, JsonManagedReference.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   50 */   private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_DESER = new Class[] { JsonDeserialize.class, JsonView.class, JsonFormat.class, JsonTypeInfo.class, JsonUnwrapped.class, JsonBackReference.class, JsonManagedReference.class, JsonMerge.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Java7Support _java7Helper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*   66 */     Java7Support x = null;
/*      */     try {
/*   68 */       x = Java7Support.instance();
/*   69 */     } catch (Throwable t) {}
/*   70 */     _java7Helper = x;
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
/*   82 */   protected transient LRUMap<Class<?>, Boolean> _annotationsInside = new LRUMap(48, 48);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _cfgConstructorPropertiesImpliesCreator = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Version version() {
/*  110 */     return PackageVersion.VERSION;
/*      */   }
/*      */   
/*      */   protected Object readResolve() {
/*  114 */     if (this._annotationsInside == null) {
/*  115 */       this._annotationsInside = new LRUMap(48, 48);
/*      */     }
/*  117 */     return this;
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
/*      */   public JacksonAnnotationIntrospector setConstructorPropertiesImpliesCreator(boolean b) {
/*  138 */     this._cfgConstructorPropertiesImpliesCreator = b;
/*  139 */     return this;
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
/*      */   public boolean isAnnotationBundle(Annotation ann) {
/*  158 */     Class<?> type = ann.annotationType();
/*  159 */     Boolean b = (Boolean)this._annotationsInside.get(type);
/*  160 */     if (b == null) {
/*  161 */       b = Boolean.valueOf((type.getAnnotation(JacksonAnnotationsInside.class) != null));
/*  162 */       this._annotationsInside.putIfAbsent(type, b);
/*      */     } 
/*  164 */     return b.booleanValue();
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
/*      */   @Deprecated
/*      */   public String findEnumValue(Enum<?> value) {
/*      */     try {
/*  186 */       Field f = value.getClass().getField(value.name());
/*  187 */       if (f != null) {
/*  188 */         JsonProperty prop = f.<JsonProperty>getAnnotation(JsonProperty.class);
/*  189 */         if (prop != null) {
/*  190 */           String n = prop.value();
/*  191 */           if (n != null && !n.isEmpty()) {
/*  192 */             return n;
/*      */           }
/*      */         } 
/*      */       } 
/*  196 */     } catch (SecurityException e) {
/*      */     
/*  198 */     } catch (NoSuchFieldException e) {}
/*      */ 
/*      */     
/*  201 */     return value.name();
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
/*  206 */     HashMap<String, String> expl = null;
/*  207 */     for (Field f : ClassUtil.getDeclaredFields(enumType)) {
/*  208 */       if (f.isEnumConstant()) {
/*      */ 
/*      */         
/*  211 */         JsonProperty prop = f.<JsonProperty>getAnnotation(JsonProperty.class);
/*  212 */         if (prop != null) {
/*      */ 
/*      */           
/*  215 */           String n = prop.value();
/*  216 */           if (!n.isEmpty())
/*      */           
/*      */           { 
/*  219 */             if (expl == null) {
/*  220 */               expl = new HashMap<>();
/*      */             }
/*  222 */             expl.put(f.getName(), n); } 
/*      */         } 
/*      */       } 
/*  225 */     }  if (expl != null) {
/*  226 */       for (int i = 0, end = enumValues.length; i < end; i++) {
/*  227 */         String defName = enumValues[i].name();
/*  228 */         String explValue = expl.get(defName);
/*  229 */         if (explValue != null) {
/*  230 */           names[i] = explValue;
/*      */         }
/*      */       } 
/*      */     }
/*  234 */     return names;
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
/*      */   public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
/*  248 */     return ClassUtil.findFirstAnnotatedEnumValue(enumCls, JsonEnumDefaultValue.class);
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
/*      */   public PropertyName findRootName(AnnotatedClass ac) {
/*  260 */     JsonRootName ann = (JsonRootName)_findAnnotation(ac, JsonRootName.class);
/*  261 */     if (ann == null) {
/*  262 */       return null;
/*      */     }
/*  264 */     String ns = ann.namespace();
/*  265 */     if (ns != null && ns.length() == 0) {
/*  266 */       ns = null;
/*      */     }
/*  268 */     return PropertyName.construct(ann.value(), ns);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated a) {
/*  274 */     JsonIgnoreProperties v = (JsonIgnoreProperties)_findAnnotation(a, JsonIgnoreProperties.class);
/*  275 */     if (v == null) {
/*  276 */       return JsonIgnoreProperties.Value.empty();
/*      */     }
/*  278 */     return JsonIgnoreProperties.Value.from(v);
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean isIgnorableType(AnnotatedClass ac) {
/*  283 */     JsonIgnoreType ignore = (JsonIgnoreType)_findAnnotation(ac, JsonIgnoreType.class);
/*  284 */     return (ignore == null) ? null : Boolean.valueOf(ignore.value());
/*      */   }
/*      */ 
/*      */   
/*      */   public Object findFilterId(Annotated a) {
/*  289 */     JsonFilter ann = (JsonFilter)_findAnnotation(a, JsonFilter.class);
/*  290 */     if (ann != null) {
/*  291 */       String id = ann.value();
/*      */       
/*  293 */       if (id.length() > 0) {
/*  294 */         return id;
/*      */       }
/*      */     } 
/*  297 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findNamingStrategy(AnnotatedClass ac) {
/*  303 */     JsonNaming ann = (JsonNaming)_findAnnotation(ac, JsonNaming.class);
/*  304 */     return (ann == null) ? null : ann.value();
/*      */   }
/*      */ 
/*      */   
/*      */   public String findClassDescription(AnnotatedClass ac) {
/*  309 */     JsonClassDescription ann = (JsonClassDescription)_findAnnotation(ac, JsonClassDescription.class);
/*  310 */     return (ann == null) ? null : ann.value();
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
/*      */   public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
/*  323 */     JsonAutoDetect ann = (JsonAutoDetect)_findAnnotation(ac, JsonAutoDetect.class);
/*  324 */     return (ann == null) ? checker : (VisibilityChecker<?>)checker.with(ann);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String findImplicitPropertyName(AnnotatedMember m) {
/*  335 */     PropertyName n = _findConstructorName(m);
/*  336 */     return (n == null) ? null : n.getSimpleName();
/*      */   }
/*      */ 
/*      */   
/*      */   public List<PropertyName> findPropertyAliases(Annotated m) {
/*  341 */     JsonAlias ann = (JsonAlias)_findAnnotation(m, JsonAlias.class);
/*  342 */     if (ann == null) {
/*  343 */       return null;
/*      */     }
/*  345 */     String[] strs = ann.value();
/*  346 */     int len = strs.length;
/*  347 */     if (len == 0) {
/*  348 */       return Collections.emptyList();
/*      */     }
/*  350 */     List<PropertyName> result = new ArrayList<>(len);
/*  351 */     for (int i = 0; i < len; i++) {
/*  352 */       result.add(PropertyName.construct(strs[i]));
/*      */     }
/*  354 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasIgnoreMarker(AnnotatedMember m) {
/*  359 */     return _isIgnorable(m);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean hasRequiredMarker(AnnotatedMember m) {
/*  365 */     JsonProperty ann = (JsonProperty)_findAnnotation(m, JsonProperty.class);
/*  366 */     if (ann != null) {
/*  367 */       return Boolean.valueOf(ann.required());
/*      */     }
/*  369 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonProperty.Access findPropertyAccess(Annotated m) {
/*  374 */     JsonProperty ann = (JsonProperty)_findAnnotation(m, JsonProperty.class);
/*  375 */     if (ann != null) {
/*  376 */       return ann.access();
/*      */     }
/*  378 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String findPropertyDescription(Annotated ann) {
/*  383 */     JsonPropertyDescription desc = (JsonPropertyDescription)_findAnnotation(ann, JsonPropertyDescription.class);
/*  384 */     return (desc == null) ? null : desc.value();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer findPropertyIndex(Annotated ann) {
/*  389 */     JsonProperty prop = (JsonProperty)_findAnnotation(ann, JsonProperty.class);
/*  390 */     if (prop != null) {
/*  391 */       int ix = prop.index();
/*  392 */       if (ix != -1) {
/*  393 */         return Integer.valueOf(ix);
/*      */       }
/*      */     } 
/*  396 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String findPropertyDefaultValue(Annotated ann) {
/*  401 */     JsonProperty prop = (JsonProperty)_findAnnotation(ann, JsonProperty.class);
/*  402 */     if (prop == null) {
/*  403 */       return null;
/*      */     }
/*  405 */     String str = prop.defaultValue();
/*      */     
/*  407 */     return str.isEmpty() ? null : str;
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonFormat.Value findFormat(Annotated ann) {
/*  412 */     JsonFormat f = (JsonFormat)_findAnnotation(ann, JsonFormat.class);
/*  413 */     return (f == null) ? null : new JsonFormat.Value(f);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember member) {
/*  419 */     JsonManagedReference ref1 = (JsonManagedReference)_findAnnotation(member, JsonManagedReference.class);
/*  420 */     if (ref1 != null) {
/*  421 */       return AnnotationIntrospector.ReferenceProperty.managed(ref1.value());
/*      */     }
/*  423 */     JsonBackReference ref2 = (JsonBackReference)_findAnnotation(member, JsonBackReference.class);
/*  424 */     if (ref2 != null) {
/*  425 */       return AnnotationIntrospector.ReferenceProperty.back(ref2.value());
/*      */     }
/*  427 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
/*  433 */     JsonUnwrapped ann = (JsonUnwrapped)_findAnnotation(member, JsonUnwrapped.class);
/*      */ 
/*      */     
/*  436 */     if (ann == null || !ann.enabled()) {
/*  437 */       return null;
/*      */     }
/*  439 */     String prefix = ann.prefix();
/*  440 */     String suffix = ann.suffix();
/*  441 */     return NameTransformer.simpleTransformer(prefix, suffix);
/*      */   }
/*      */ 
/*      */   
/*      */   public JacksonInject.Value findInjectableValue(AnnotatedMember m) {
/*  446 */     JacksonInject ann = (JacksonInject)_findAnnotation(m, JacksonInject.class);
/*  447 */     if (ann == null) {
/*  448 */       return null;
/*      */     }
/*      */     
/*  451 */     JacksonInject.Value v = JacksonInject.Value.from(ann);
/*  452 */     if (!v.hasId()) {
/*      */       Object id;
/*      */       
/*  455 */       if (!(m instanceof AnnotatedMethod)) {
/*  456 */         id = m.getRawType().getName();
/*      */       } else {
/*  458 */         AnnotatedMethod am = (AnnotatedMethod)m;
/*  459 */         if (am.getParameterCount() == 0) {
/*  460 */           id = m.getRawType().getName();
/*      */         } else {
/*  462 */           id = am.getRawParameterType(0).getName();
/*      */         } 
/*      */       } 
/*  465 */       v = v.withId(id);
/*      */     } 
/*  467 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Object findInjectableValueId(AnnotatedMember m) {
/*  473 */     JacksonInject.Value v = findInjectableValue(m);
/*  474 */     return (v == null) ? null : v.getId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?>[] findViews(Annotated a) {
/*  480 */     JsonView ann = (JsonView)_findAnnotation(a, JsonView.class);
/*  481 */     return (ann == null) ? null : ann.value();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedMethod resolveSetterConflict(MapperConfig<?> config, AnnotatedMethod setter1, AnnotatedMethod setter2) {
/*  488 */     Class<?> cls1 = setter1.getRawParameterType(0);
/*  489 */     Class<?> cls2 = setter2.getRawParameterType(0);
/*      */ 
/*      */ 
/*      */     
/*  493 */     if (cls1.isPrimitive()) {
/*  494 */       if (!cls2.isPrimitive()) {
/*  495 */         return setter1;
/*      */       }
/*  497 */     } else if (cls2.isPrimitive()) {
/*  498 */       return setter2;
/*      */     } 
/*      */     
/*  501 */     if (cls1 == String.class) {
/*  502 */       if (cls2 != String.class) {
/*  503 */         return setter1;
/*      */       }
/*  505 */     } else if (cls2 == String.class) {
/*  506 */       return setter2;
/*      */     } 
/*      */     
/*  509 */     return null;
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
/*      */   public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
/*  522 */     return _findTypeResolver(config, ac, baseType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
/*  533 */     if (baseType.isContainerType() || baseType.isReferenceType()) {
/*  534 */       return null;
/*      */     }
/*      */     
/*  537 */     return _findTypeResolver(config, am, baseType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
/*  547 */     if (containerType.getContentType() == null) {
/*  548 */       throw new IllegalArgumentException("Must call method with a container or reference type (got " + containerType + ")");
/*      */     }
/*  550 */     return _findTypeResolver(config, am, containerType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<NamedType> findSubtypes(Annotated a) {
/*  556 */     JsonSubTypes t = (JsonSubTypes)_findAnnotation(a, JsonSubTypes.class);
/*  557 */     if (t == null) return null; 
/*  558 */     JsonSubTypes.Type[] types = t.value();
/*  559 */     ArrayList<NamedType> result = new ArrayList<>(types.length);
/*  560 */     for (JsonSubTypes.Type type : types) {
/*  561 */       result.add(new NamedType(type.value(), type.name()));
/*      */     }
/*  563 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String findTypeName(AnnotatedClass ac) {
/*  569 */     JsonTypeName tn = (JsonTypeName)_findAnnotation(ac, JsonTypeName.class);
/*  570 */     return (tn == null) ? null : tn.value();
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean isTypeId(AnnotatedMember member) {
/*  575 */     return Boolean.valueOf(_hasAnnotation(member, JsonTypeId.class));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectIdInfo findObjectIdInfo(Annotated ann) {
/*  586 */     JsonIdentityInfo info = (JsonIdentityInfo)_findAnnotation(ann, JsonIdentityInfo.class);
/*  587 */     if (info == null || info.generator() == ObjectIdGenerators.None.class) {
/*  588 */       return null;
/*      */     }
/*      */     
/*  591 */     PropertyName name = PropertyName.construct(info.property());
/*  592 */     return new ObjectIdInfo(name, info.scope(), info.generator(), info.resolver());
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectIdInfo findObjectReferenceInfo(Annotated ann, ObjectIdInfo objectIdInfo) {
/*  597 */     JsonIdentityReference ref = (JsonIdentityReference)_findAnnotation(ann, JsonIdentityReference.class);
/*  598 */     if (ref == null) {
/*  599 */       return objectIdInfo;
/*      */     }
/*  601 */     if (objectIdInfo == null) {
/*  602 */       objectIdInfo = ObjectIdInfo.empty();
/*      */     }
/*  604 */     return objectIdInfo.withAlwaysAsId(ref.alwaysAsId());
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
/*      */   public Object findSerializer(Annotated a) {
/*  616 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  617 */     if (ann != null) {
/*      */       
/*  619 */       Class<? extends JsonSerializer> serClass = ann.using();
/*  620 */       if (serClass != JsonSerializer.None.class) {
/*  621 */         return serClass;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  629 */     JsonRawValue annRaw = (JsonRawValue)_findAnnotation(a, JsonRawValue.class);
/*  630 */     if (annRaw != null && annRaw.value()) {
/*      */       
/*  632 */       Class<?> cls = a.getRawType();
/*  633 */       return new RawSerializer(cls);
/*      */     } 
/*  635 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findKeySerializer(Annotated a) {
/*  641 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  642 */     if (ann != null) {
/*      */       
/*  644 */       Class<? extends JsonSerializer> serClass = ann.keyUsing();
/*  645 */       if (serClass != JsonSerializer.None.class) {
/*  646 */         return serClass;
/*      */       }
/*      */     } 
/*  649 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findContentSerializer(Annotated a) {
/*  655 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  656 */     if (ann != null) {
/*      */       
/*  658 */       Class<? extends JsonSerializer> serClass = ann.contentUsing();
/*  659 */       if (serClass != JsonSerializer.None.class) {
/*  660 */         return serClass;
/*      */       }
/*      */     } 
/*  663 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findNullSerializer(Annotated a) {
/*  669 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  670 */     if (ann != null) {
/*      */       
/*  672 */       Class<? extends JsonSerializer> serClass = ann.nullsUsing();
/*  673 */       if (serClass != JsonSerializer.None.class) {
/*  674 */         return serClass;
/*      */       }
/*      */     } 
/*  677 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonInclude.Value findPropertyInclusion(Annotated a) {
/*  683 */     JsonInclude inc = (JsonInclude)_findAnnotation(a, JsonInclude.class);
/*  684 */     JsonInclude.Value value = (inc == null) ? JsonInclude.Value.empty() : JsonInclude.Value.from(inc);
/*      */ 
/*      */     
/*  687 */     if (value.getValueInclusion() == JsonInclude.Include.USE_DEFAULTS) {
/*  688 */       value = _refinePropertyInclusion(a, value);
/*      */     }
/*  690 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private JsonInclude.Value _refinePropertyInclusion(Annotated a, JsonInclude.Value value) {
/*  695 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  696 */     if (ann != null) {
/*  697 */       switch (ann.include()) {
/*      */         case ALWAYS:
/*  699 */           return value.withValueInclusion(JsonInclude.Include.ALWAYS);
/*      */         case NON_NULL:
/*  701 */           return value.withValueInclusion(JsonInclude.Include.NON_NULL);
/*      */         case NON_DEFAULT:
/*  703 */           return value.withValueInclusion(JsonInclude.Include.NON_DEFAULT);
/*      */         case NON_EMPTY:
/*  705 */           return value.withValueInclusion(JsonInclude.Include.NON_EMPTY);
/*      */       } 
/*      */ 
/*      */     
/*      */     }
/*  710 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonSerialize.Typing findSerializationTyping(Annotated a) {
/*  716 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  717 */     return (ann == null) ? null : ann.typing();
/*      */   }
/*      */ 
/*      */   
/*      */   public Object findSerializationConverter(Annotated a) {
/*  722 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  723 */     return (ann == null) ? null : _classIfExplicit(ann.converter(), Converter.None.class);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object findSerializationContentConverter(AnnotatedMember a) {
/*  728 */     JsonSerialize ann = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*  729 */     return (ann == null) ? null : _classIfExplicit(ann.contentConverter(), Converter.None.class);
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
/*      */   public JavaType refineSerializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
/*      */     MapLikeType mapLikeType;
/*  742 */     JavaType javaType1, type = baseType;
/*  743 */     TypeFactory tf = config.getTypeFactory();
/*      */     
/*  745 */     JsonSerialize jsonSer = (JsonSerialize)_findAnnotation(a, JsonSerialize.class);
/*      */ 
/*      */ 
/*      */     
/*  749 */     Class<?> serClass = (jsonSer == null) ? null : _classIfExplicit(jsonSer.as());
/*  750 */     if (serClass != null) {
/*  751 */       if (type.hasRawClass(serClass)) {
/*      */ 
/*      */         
/*  754 */         type = type.withStaticTyping();
/*      */       } else {
/*  756 */         Class<?> currRaw = type.getRawClass();
/*      */ 
/*      */         
/*      */         try {
/*  760 */           if (serClass.isAssignableFrom(currRaw)) {
/*  761 */             type = tf.constructGeneralizedType(type, serClass);
/*  762 */           } else if (currRaw.isAssignableFrom(serClass)) {
/*  763 */             type = tf.constructSpecializedType(type, serClass);
/*  764 */           } else if (_primitiveAndWrapper(currRaw, serClass)) {
/*      */             
/*  766 */             type = type.withStaticTyping();
/*      */           } else {
/*  768 */             throw new JsonMappingException(null, String.format("Cannot refine serialization type %s into %s; types not related", new Object[] { type, serClass.getName() }));
/*      */           }
/*      */         
/*      */         }
/*  772 */         catch (IllegalArgumentException iae) {
/*  773 */           throw new JsonMappingException(null, String.format("Failed to widen type %s with annotation (value %s), from '%s': %s", new Object[] { type, serClass.getName(), a.getName(), iae.getMessage() }), iae);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  783 */     if (type.isMapLikeType()) {
/*  784 */       JavaType keyType = type.getKeyType();
/*  785 */       Class<?> keyClass = (jsonSer == null) ? null : _classIfExplicit(jsonSer.keyAs());
/*  786 */       if (keyClass != null) {
/*  787 */         if (keyType.hasRawClass(keyClass)) {
/*  788 */           keyType = keyType.withStaticTyping();
/*      */         } else {
/*  790 */           Class<?> currRaw = keyType.getRawClass();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  795 */             if (keyClass.isAssignableFrom(currRaw)) {
/*  796 */               keyType = tf.constructGeneralizedType(keyType, keyClass);
/*  797 */             } else if (currRaw.isAssignableFrom(keyClass)) {
/*  798 */               keyType = tf.constructSpecializedType(keyType, keyClass);
/*  799 */             } else if (_primitiveAndWrapper(currRaw, keyClass)) {
/*      */               
/*  801 */               keyType = keyType.withStaticTyping();
/*      */             } else {
/*  803 */               throw new JsonMappingException(null, String.format("Cannot refine serialization key type %s into %s; types not related", new Object[] { keyType, keyClass.getName() }));
/*      */             }
/*      */           
/*      */           }
/*  807 */           catch (IllegalArgumentException iae) {
/*  808 */             throw new JsonMappingException(null, String.format("Failed to widen key type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[] { type, keyClass.getName(), a.getName(), iae.getMessage() }), iae);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  814 */         mapLikeType = ((MapLikeType)type).withKeyType(keyType);
/*      */       } 
/*      */     } 
/*      */     
/*  818 */     JavaType contentType = mapLikeType.getContentType();
/*  819 */     if (contentType != null) {
/*      */       
/*  821 */       Class<?> contentClass = (jsonSer == null) ? null : _classIfExplicit(jsonSer.contentAs());
/*  822 */       if (contentClass != null) {
/*  823 */         if (contentType.hasRawClass(contentClass)) {
/*  824 */           contentType = contentType.withStaticTyping();
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  829 */           Class<?> currRaw = contentType.getRawClass();
/*      */           try {
/*  831 */             if (contentClass.isAssignableFrom(currRaw)) {
/*  832 */               contentType = tf.constructGeneralizedType(contentType, contentClass);
/*  833 */             } else if (currRaw.isAssignableFrom(contentClass)) {
/*  834 */               contentType = tf.constructSpecializedType(contentType, contentClass);
/*  835 */             } else if (_primitiveAndWrapper(currRaw, contentClass)) {
/*      */               
/*  837 */               contentType = contentType.withStaticTyping();
/*      */             } else {
/*  839 */               throw new JsonMappingException(null, String.format("Cannot refine serialization content type %s into %s; types not related", new Object[] { contentType, contentClass.getName() }));
/*      */             }
/*      */           
/*      */           }
/*  843 */           catch (IllegalArgumentException iae) {
/*  844 */             throw new JsonMappingException(null, String.format("Internal error: failed to refine value type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[] { mapLikeType, contentClass.getName(), a.getName(), iae.getMessage() }), iae);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  850 */         javaType1 = mapLikeType.withContentType(contentType);
/*      */       } 
/*      */     } 
/*  853 */     return javaType1;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findSerializationType(Annotated am) {
/*  859 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
/*  865 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
/*  871 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
/*  882 */     JsonPropertyOrder order = (JsonPropertyOrder)_findAnnotation(ac, JsonPropertyOrder.class);
/*  883 */     return (order == null) ? null : order.value();
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean findSerializationSortAlphabetically(Annotated ann) {
/*  888 */     return _findSortAlpha(ann);
/*      */   }
/*      */   
/*      */   private final Boolean _findSortAlpha(Annotated ann) {
/*  892 */     JsonPropertyOrder order = (JsonPropertyOrder)_findAnnotation(ann, JsonPropertyOrder.class);
/*      */ 
/*      */     
/*  895 */     if (order != null && order.alphabetic()) {
/*  896 */       return Boolean.TRUE;
/*      */     }
/*  898 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void findAndAddVirtualProperties(MapperConfig<?> config, AnnotatedClass ac, List<BeanPropertyWriter> properties) {
/*  904 */     JsonAppend ann = (JsonAppend)_findAnnotation(ac, JsonAppend.class);
/*  905 */     if (ann == null) {
/*      */       return;
/*      */     }
/*  908 */     boolean prepend = ann.prepend();
/*  909 */     JavaType propType = null;
/*      */ 
/*      */     
/*  912 */     JsonAppend.Attr[] attrs = ann.attrs();
/*  913 */     for (int i = 0, len = attrs.length; i < len; i++) {
/*  914 */       if (propType == null) {
/*  915 */         propType = config.constructType(Object.class);
/*      */       }
/*  917 */       BeanPropertyWriter bpw = _constructVirtualProperty(attrs[i], config, ac, propType);
/*      */       
/*  919 */       if (prepend) {
/*  920 */         properties.add(i, bpw);
/*      */       } else {
/*  922 */         properties.add(bpw);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  927 */     JsonAppend.Prop[] props = ann.props();
/*  928 */     for (int j = 0, k = props.length; j < k; j++) {
/*  929 */       BeanPropertyWriter bpw = _constructVirtualProperty(props[j], config, ac);
/*      */       
/*  931 */       if (prepend) {
/*  932 */         properties.add(j, bpw);
/*      */       } else {
/*  934 */         properties.add(bpw);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected BeanPropertyWriter _constructVirtualProperty(JsonAppend.Attr attr, MapperConfig<?> config, AnnotatedClass ac, JavaType type) {
/*  942 */     PropertyMetadata metadata = attr.required() ? PropertyMetadata.STD_REQUIRED : PropertyMetadata.STD_OPTIONAL;
/*      */ 
/*      */     
/*  945 */     String attrName = attr.value();
/*      */ 
/*      */     
/*  948 */     PropertyName propName = _propertyName(attr.propName(), attr.propNamespace());
/*  949 */     if (!propName.hasSimpleName()) {
/*  950 */       propName = PropertyName.construct(attrName);
/*      */     }
/*      */     
/*  953 */     AnnotatedMember member = new VirtualAnnotatedMember(ac, ac.getRawType(), attrName, type);
/*      */ 
/*      */     
/*  956 */     SimpleBeanPropertyDefinition propDef = SimpleBeanPropertyDefinition.construct(config, member, propName, metadata, attr.include());
/*      */ 
/*      */     
/*  959 */     return (BeanPropertyWriter)AttributePropertyWriter.construct(attrName, (BeanPropertyDefinition)propDef, ac.getAnnotations(), type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BeanPropertyWriter _constructVirtualProperty(JsonAppend.Prop prop, MapperConfig<?> config, AnnotatedClass ac) {
/*  966 */     PropertyMetadata metadata = prop.required() ? PropertyMetadata.STD_REQUIRED : PropertyMetadata.STD_OPTIONAL;
/*      */     
/*  968 */     PropertyName propName = _propertyName(prop.name(), prop.namespace());
/*  969 */     JavaType type = config.constructType(prop.type());
/*      */     
/*  971 */     AnnotatedMember member = new VirtualAnnotatedMember(ac, ac.getRawType(), propName.getSimpleName(), type);
/*      */ 
/*      */     
/*  974 */     SimpleBeanPropertyDefinition propDef = SimpleBeanPropertyDefinition.construct(config, member, propName, metadata, prop.include());
/*      */ 
/*      */     
/*  977 */     Class<?> implClass = prop.value();
/*      */     
/*  979 */     HandlerInstantiator hi = config.getHandlerInstantiator();
/*  980 */     VirtualBeanPropertyWriter bpw = (hi == null) ? null : hi.virtualPropertyWriterInstance(config, implClass);
/*      */     
/*  982 */     if (bpw == null) {
/*  983 */       bpw = (VirtualBeanPropertyWriter)ClassUtil.createInstance(implClass, config.canOverrideAccessModifiers());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  988 */     return (BeanPropertyWriter)bpw.withConfig(config, ac, (BeanPropertyDefinition)propDef, type);
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
/*      */   public PropertyName findNameForSerialization(Annotated a) {
/* 1000 */     JsonGetter jg = (JsonGetter)_findAnnotation(a, JsonGetter.class);
/* 1001 */     if (jg != null) {
/* 1002 */       return PropertyName.construct(jg.value());
/*      */     }
/* 1004 */     JsonProperty pann = (JsonProperty)_findAnnotation(a, JsonProperty.class);
/* 1005 */     if (pann != null) {
/* 1006 */       return PropertyName.construct(pann.value());
/*      */     }
/* 1008 */     if (_hasOneOf(a, (Class[])ANNOTATIONS_TO_INFER_SER)) {
/* 1009 */       return PropertyName.USE_DEFAULT;
/*      */     }
/* 1011 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean hasAsValue(Annotated a) {
/* 1016 */     JsonValue ann = (JsonValue)_findAnnotation(a, JsonValue.class);
/* 1017 */     if (ann == null) {
/* 1018 */       return null;
/*      */     }
/* 1020 */     return Boolean.valueOf(ann.value());
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean hasAnyGetter(Annotated a) {
/* 1025 */     JsonAnyGetter ann = (JsonAnyGetter)_findAnnotation(a, JsonAnyGetter.class);
/* 1026 */     if (ann == null) {
/* 1027 */       return null;
/*      */     }
/* 1029 */     return Boolean.valueOf(ann.enabled());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
/* 1036 */     return _hasAnnotation(am, JsonAnyGetter.class);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAsValueAnnotation(AnnotatedMethod am) {
/* 1042 */     JsonValue ann = (JsonValue)_findAnnotation(am, JsonValue.class);
/*      */     
/* 1044 */     return (ann != null && ann.value());
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
/*      */   public Object findDeserializer(Annotated a) {
/* 1056 */     JsonDeserialize ann = (JsonDeserialize)_findAnnotation(a, JsonDeserialize.class);
/* 1057 */     if (ann != null) {
/*      */       
/* 1059 */       Class<? extends JsonDeserializer> deserClass = ann.using();
/* 1060 */       if (deserClass != JsonDeserializer.None.class) {
/* 1061 */         return deserClass;
/*      */       }
/*      */     } 
/* 1064 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findKeyDeserializer(Annotated a) {
/* 1070 */     JsonDeserialize ann = (JsonDeserialize)_findAnnotation(a, JsonDeserialize.class);
/* 1071 */     if (ann != null) {
/* 1072 */       Class<? extends KeyDeserializer> deserClass = ann.keyUsing();
/* 1073 */       if (deserClass != KeyDeserializer.None.class) {
/* 1074 */         return deserClass;
/*      */       }
/*      */     } 
/* 1077 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findContentDeserializer(Annotated a) {
/* 1083 */     JsonDeserialize ann = (JsonDeserialize)_findAnnotation(a, JsonDeserialize.class);
/* 1084 */     if (ann != null) {
/*      */       
/* 1086 */       Class<? extends JsonDeserializer> deserClass = ann.contentUsing();
/* 1087 */       if (deserClass != JsonDeserializer.None.class) {
/* 1088 */         return deserClass;
/*      */       }
/*      */     } 
/* 1091 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findDeserializationConverter(Annotated a) {
/* 1097 */     JsonDeserialize ann = (JsonDeserialize)_findAnnotation(a, JsonDeserialize.class);
/* 1098 */     return (ann == null) ? null : _classIfExplicit(ann.converter(), Converter.None.class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findDeserializationContentConverter(AnnotatedMember a) {
/* 1104 */     JsonDeserialize ann = (JsonDeserialize)_findAnnotation(a, JsonDeserialize.class);
/* 1105 */     return (ann == null) ? null : _classIfExplicit(ann.contentConverter(), Converter.None.class);
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
/*      */   public JavaType refineDeserializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
/*      */     MapLikeType mapLikeType;
/* 1118 */     JavaType javaType1, type = baseType;
/* 1119 */     TypeFactory tf = config.getTypeFactory();
/*      */     
/* 1121 */     JsonDeserialize jsonDeser = (JsonDeserialize)_findAnnotation(a, JsonDeserialize.class);
/*      */ 
/*      */     
/* 1124 */     Class<?> valueClass = (jsonDeser == null) ? null : _classIfExplicit(jsonDeser.as());
/* 1125 */     if (valueClass != null && !type.hasRawClass(valueClass) && !_primitiveAndWrapper(type, valueClass)) {
/*      */       
/*      */       try {
/* 1128 */         type = tf.constructSpecializedType(type, valueClass);
/* 1129 */       } catch (IllegalArgumentException iae) {
/* 1130 */         throw new JsonMappingException(null, String.format("Failed to narrow type %s with annotation (value %s), from '%s': %s", new Object[] { type, valueClass.getName(), a.getName(), iae.getMessage() }), iae);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1139 */     if (type.isMapLikeType()) {
/* 1140 */       JavaType keyType = type.getKeyType();
/* 1141 */       Class<?> keyClass = (jsonDeser == null) ? null : _classIfExplicit(jsonDeser.keyAs());
/* 1142 */       if (keyClass != null && !_primitiveAndWrapper(keyType, keyClass)) {
/*      */         
/*      */         try {
/* 1145 */           keyType = tf.constructSpecializedType(keyType, keyClass);
/* 1146 */           mapLikeType = ((MapLikeType)type).withKeyType(keyType);
/* 1147 */         } catch (IllegalArgumentException iae) {
/* 1148 */           throw new JsonMappingException(null, String.format("Failed to narrow key type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[] { mapLikeType, keyClass.getName(), a.getName(), iae.getMessage() }), iae);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1155 */     JavaType contentType = mapLikeType.getContentType();
/* 1156 */     if (contentType != null) {
/*      */       
/* 1158 */       Class<?> contentClass = (jsonDeser == null) ? null : _classIfExplicit(jsonDeser.contentAs());
/* 1159 */       if (contentClass != null && !_primitiveAndWrapper(contentType, contentClass)) {
/*      */         
/*      */         try {
/* 1162 */           contentType = tf.constructSpecializedType(contentType, contentClass);
/* 1163 */           javaType1 = mapLikeType.withContentType(contentType);
/* 1164 */         } catch (IllegalArgumentException iae) {
/* 1165 */           throw new JsonMappingException(null, String.format("Failed to narrow value type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[] { javaType1, contentClass.getName(), a.getName(), iae.getMessage() }), iae);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1172 */     return javaType1;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
/* 1178 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
/* 1184 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
/* 1190 */     return null;
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
/*      */   public Object findValueInstantiator(AnnotatedClass ac) {
/* 1202 */     JsonValueInstantiator ann = (JsonValueInstantiator)_findAnnotation(ac, JsonValueInstantiator.class);
/*      */     
/* 1204 */     return (ann == null) ? null : ann.value();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> findPOJOBuilder(AnnotatedClass ac) {
/* 1210 */     JsonDeserialize ann = (JsonDeserialize)_findAnnotation(ac, JsonDeserialize.class);
/* 1211 */     return (ann == null) ? null : _classIfExplicit(ann.builder());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
/* 1217 */     JsonPOJOBuilder ann = (JsonPOJOBuilder)_findAnnotation(ac, JsonPOJOBuilder.class);
/* 1218 */     return (ann == null) ? null : new JsonPOJOBuilder.Value(ann);
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
/*      */   public PropertyName findNameForDeserialization(Annotated a) {
/* 1232 */     JsonSetter js = (JsonSetter)_findAnnotation(a, JsonSetter.class);
/* 1233 */     if (js != null) {
/* 1234 */       return PropertyName.construct(js.value());
/*      */     }
/* 1236 */     JsonProperty pann = (JsonProperty)_findAnnotation(a, JsonProperty.class);
/* 1237 */     if (pann != null) {
/* 1238 */       return PropertyName.construct(pann.value());
/*      */     }
/* 1240 */     if (_hasOneOf(a, (Class[])ANNOTATIONS_TO_INFER_DESER)) {
/* 1241 */       return PropertyName.USE_DEFAULT;
/*      */     }
/* 1243 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean hasAnySetter(Annotated a) {
/* 1248 */     JsonAnySetter ann = (JsonAnySetter)_findAnnotation(a, JsonAnySetter.class);
/* 1249 */     return (ann == null) ? null : Boolean.valueOf(ann.enabled());
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonSetter.Value findSetterInfo(Annotated a) {
/* 1254 */     return JsonSetter.Value.from((JsonSetter)_findAnnotation(a, JsonSetter.class));
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean findMergeInfo(Annotated a) {
/* 1259 */     JsonMerge ann = (JsonMerge)_findAnnotation(a, JsonMerge.class);
/* 1260 */     return (ann == null) ? null : ann.value().asBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
/* 1266 */     return _hasAnnotation(am, JsonAnySetter.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasCreatorAnnotation(Annotated a) {
/* 1276 */     JsonCreator ann = (JsonCreator)_findAnnotation(a, JsonCreator.class);
/* 1277 */     if (ann != null) {
/* 1278 */       return (ann.mode() != JsonCreator.Mode.DISABLED);
/*      */     }
/*      */ 
/*      */     
/* 1282 */     if (this._cfgConstructorPropertiesImpliesCreator && 
/* 1283 */       a instanceof AnnotatedConstructor && 
/* 1284 */       _java7Helper != null) {
/* 1285 */       Boolean b = _java7Helper.hasCreatorAnnotation(a);
/* 1286 */       if (b != null) {
/* 1287 */         return b.booleanValue();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1292 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JsonCreator.Mode findCreatorBinding(Annotated a) {
/* 1298 */     JsonCreator ann = (JsonCreator)_findAnnotation(a, JsonCreator.class);
/* 1299 */     return (ann == null) ? null : ann.mode();
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonCreator.Mode findCreatorAnnotation(MapperConfig<?> config, Annotated a) {
/* 1304 */     JsonCreator ann = (JsonCreator)_findAnnotation(a, JsonCreator.class);
/* 1305 */     if (ann != null) {
/* 1306 */       return ann.mode();
/*      */     }
/* 1308 */     if (this._cfgConstructorPropertiesImpliesCreator && config.isEnabled(MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES))
/*      */     {
/*      */       
/* 1311 */       if (a instanceof AnnotatedConstructor && 
/* 1312 */         _java7Helper != null) {
/* 1313 */         Boolean b = _java7Helper.hasCreatorAnnotation(a);
/* 1314 */         if (b != null && b.booleanValue())
/*      */         {
/*      */           
/* 1317 */           return JsonCreator.Mode.PROPERTIES;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1322 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _isIgnorable(Annotated a) {
/* 1333 */     JsonIgnore ann = (JsonIgnore)_findAnnotation(a, JsonIgnore.class);
/* 1334 */     if (ann != null) {
/* 1335 */       return ann.value();
/*      */     }
/* 1337 */     if (_java7Helper != null) {
/* 1338 */       Boolean b = _java7Helper.findTransient(a);
/* 1339 */       if (b != null) {
/* 1340 */         return b.booleanValue();
/*      */       }
/*      */     } 
/* 1343 */     return false;
/*      */   }
/*      */   
/*      */   protected Class<?> _classIfExplicit(Class<?> cls) {
/* 1347 */     if (cls == null || ClassUtil.isBogusClass(cls)) {
/* 1348 */       return null;
/*      */     }
/* 1350 */     return cls;
/*      */   }
/*      */   
/*      */   protected Class<?> _classIfExplicit(Class<?> cls, Class<?> implicit) {
/* 1354 */     cls = _classIfExplicit(cls);
/* 1355 */     return (cls == null || cls == implicit) ? null : cls;
/*      */   }
/*      */   
/*      */   protected PropertyName _propertyName(String localName, String namespace) {
/* 1359 */     if (localName.isEmpty()) {
/* 1360 */       return PropertyName.USE_DEFAULT;
/*      */     }
/* 1362 */     if (namespace == null || namespace.isEmpty()) {
/* 1363 */       return PropertyName.construct(localName);
/*      */     }
/* 1365 */     return PropertyName.construct(localName, namespace);
/*      */   }
/*      */ 
/*      */   
/*      */   protected PropertyName _findConstructorName(Annotated a) {
/* 1370 */     if (a instanceof AnnotatedParameter) {
/* 1371 */       AnnotatedParameter p = (AnnotatedParameter)a;
/* 1372 */       AnnotatedWithParams ctor = p.getOwner();
/*      */       
/* 1374 */       if (ctor != null && 
/* 1375 */         _java7Helper != null) {
/* 1376 */         PropertyName name = _java7Helper.findConstructorName(p);
/* 1377 */         if (name != null) {
/* 1378 */           return name;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1383 */     return null;
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
/*      */   protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> config, Annotated ann, JavaType baseType) {
/*      */     StdTypeResolverBuilder stdTypeResolverBuilder;
/* 1396 */     JsonTypeInfo info = (JsonTypeInfo)_findAnnotation(ann, JsonTypeInfo.class);
/* 1397 */     JsonTypeResolver resAnn = (JsonTypeResolver)_findAnnotation(ann, JsonTypeResolver.class);
/*      */     
/* 1399 */     if (resAnn != null) {
/* 1400 */       if (info == null) {
/* 1401 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1407 */       TypeResolverBuilder<?> b = config.typeResolverBuilderInstance(ann, resAnn.value());
/*      */     } else {
/* 1409 */       if (info == null) {
/* 1410 */         return null;
/*      */       }
/*      */       
/* 1413 */       if (info.use() == JsonTypeInfo.Id.NONE) {
/* 1414 */         return (TypeResolverBuilder<?>)_constructNoTypeResolverBuilder();
/*      */       }
/* 1416 */       stdTypeResolverBuilder = _constructStdTypeResolverBuilder();
/*      */     } 
/*      */     
/* 1419 */     JsonTypeIdResolver idResInfo = (JsonTypeIdResolver)_findAnnotation(ann, JsonTypeIdResolver.class);
/* 1420 */     TypeIdResolver idRes = (idResInfo == null) ? null : config.typeIdResolverInstance(ann, idResInfo.value());
/*      */     
/* 1422 */     if (idRes != null) {
/* 1423 */       idRes.init(baseType);
/*      */     }
/* 1425 */     TypeResolverBuilder<?> typeResolverBuilder = stdTypeResolverBuilder.init(info.use(), idRes);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1430 */     JsonTypeInfo.As inclusion = info.include();
/* 1431 */     if (inclusion == JsonTypeInfo.As.EXTERNAL_PROPERTY && ann instanceof AnnotatedClass) {
/* 1432 */       inclusion = JsonTypeInfo.As.PROPERTY;
/*      */     }
/* 1434 */     typeResolverBuilder = typeResolverBuilder.inclusion(inclusion);
/* 1435 */     typeResolverBuilder = typeResolverBuilder.typeProperty(info.property());
/* 1436 */     Class<?> defaultImpl = info.defaultImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1442 */     if (defaultImpl != JsonTypeInfo.None.class && !defaultImpl.isAnnotation()) {
/* 1443 */       typeResolverBuilder = typeResolverBuilder.defaultImpl(defaultImpl);
/*      */     }
/* 1445 */     typeResolverBuilder = typeResolverBuilder.typeIdVisibility(info.visible());
/* 1446 */     return typeResolverBuilder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
/* 1454 */     return new StdTypeResolverBuilder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StdTypeResolverBuilder _constructNoTypeResolverBuilder() {
/* 1462 */     return StdTypeResolverBuilder.noTypeInfoBuilder();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean _primitiveAndWrapper(Class<?> baseType, Class<?> refinement) {
/* 1467 */     if (baseType.isPrimitive()) {
/* 1468 */       return (baseType == ClassUtil.primitiveType(refinement));
/*      */     }
/* 1470 */     if (refinement.isPrimitive()) {
/* 1471 */       return (refinement == ClassUtil.primitiveType(baseType));
/*      */     }
/* 1473 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean _primitiveAndWrapper(JavaType baseType, Class<?> refinement) {
/* 1478 */     if (baseType.isPrimitive()) {
/* 1479 */       return baseType.hasRawClass(ClassUtil.primitiveType(refinement));
/*      */     }
/* 1481 */     if (refinement.isPrimitive()) {
/* 1482 */       return (refinement == ClassUtil.primitiveType(baseType.getRawClass()));
/*      */     }
/* 1484 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\JacksonAnnotationIntrospector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */