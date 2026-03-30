/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
/*     */ 
/*     */ public class BasicBeanDescription extends BeanDescription {
/*  31 */   private static final Class<?>[] NO_VIEWS = new Class[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final POJOPropertiesCollector _propCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MapperConfig<?> _config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnnotatedClass _classInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?>[] _defaultViews;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _defaultViewsResolved;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<BeanPropertyDefinition> _properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectIdInfo _objectIdInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicBeanDescription(POJOPropertiesCollector coll, JavaType type, AnnotatedClass classDef) {
/*  96 */     super(type);
/*  97 */     this._propCollector = coll;
/*  98 */     this._config = coll.getConfig();
/*     */     
/* 100 */     if (this._config == null) {
/* 101 */       this._annotationIntrospector = null;
/*     */     } else {
/* 103 */       this._annotationIntrospector = this._config.getAnnotationIntrospector();
/*     */     } 
/* 105 */     this._classInfo = classDef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicBeanDescription(MapperConfig<?> config, JavaType type, AnnotatedClass classDef, List<BeanPropertyDefinition> props) {
/* 115 */     super(type);
/* 116 */     this._propCollector = null;
/* 117 */     this._config = config;
/*     */     
/* 119 */     if (this._config == null) {
/* 120 */       this._annotationIntrospector = null;
/*     */     } else {
/* 122 */       this._annotationIntrospector = this._config.getAnnotationIntrospector();
/*     */     } 
/* 124 */     this._classInfo = classDef;
/* 125 */     this._properties = props;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasicBeanDescription(POJOPropertiesCollector coll) {
/* 130 */     this(coll, coll.getType(), coll.getClassDef());
/* 131 */     this._objectIdInfo = coll.getObjectIdInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicBeanDescription forDeserialization(POJOPropertiesCollector coll) {
/* 139 */     return new BasicBeanDescription(coll);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicBeanDescription forSerialization(POJOPropertiesCollector coll) {
/* 147 */     return new BasicBeanDescription(coll);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicBeanDescription forOtherUse(MapperConfig<?> config, JavaType type, AnnotatedClass ac) {
/* 158 */     return new BasicBeanDescription(config, type, ac, Collections.emptyList());
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<BeanPropertyDefinition> _properties() {
/* 163 */     if (this._properties == null) {
/* 164 */       this._properties = this._propCollector.getProperties();
/*     */     }
/* 166 */     return this._properties;
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
/*     */   
/*     */   public boolean removeProperty(String propName) {
/* 184 */     Iterator<BeanPropertyDefinition> it = _properties().iterator();
/* 185 */     while (it.hasNext()) {
/* 186 */       BeanPropertyDefinition prop = it.next();
/* 187 */       if (prop.getName().equals(propName)) {
/* 188 */         it.remove();
/* 189 */         return true;
/*     */       } 
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addProperty(BeanPropertyDefinition def) {
/* 198 */     if (hasProperty(def.getFullName())) {
/* 199 */       return false;
/*     */     }
/* 201 */     _properties().add(def);
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProperty(PropertyName name) {
/* 209 */     return (findProperty(name) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyDefinition findProperty(PropertyName name) {
/* 217 */     for (BeanPropertyDefinition prop : _properties()) {
/* 218 */       if (prop.hasName(name)) {
/* 219 */         return prop;
/*     */       }
/*     */     } 
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedClass getClassInfo() {
/* 232 */     return this._classInfo;
/*     */   }
/*     */   public ObjectIdInfo getObjectIdInfo() {
/* 235 */     return this._objectIdInfo;
/*     */   }
/*     */   
/*     */   public List<BeanPropertyDefinition> findProperties() {
/* 239 */     return _properties();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public AnnotatedMethod findJsonValueMethod() {
/* 245 */     return (this._propCollector == null) ? null : this._propCollector.getJsonValueMethod();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMember findJsonValueAccessor() {
/* 251 */     return (this._propCollector == null) ? null : this._propCollector.getJsonValueAccessor();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIgnoredPropertyNames() {
/* 257 */     Set<String> ign = (this._propCollector == null) ? null : this._propCollector.getIgnoredPropertyNames();
/*     */     
/* 259 */     if (ign == null) {
/* 260 */       return Collections.emptySet();
/*     */     }
/* 262 */     return ign;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasKnownClassAnnotations() {
/* 267 */     return this._classInfo.hasAnnotations();
/*     */   }
/*     */ 
/*     */   
/*     */   public Annotations getClassAnnotations() {
/* 272 */     return this._classInfo.getAnnotations();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TypeBindings bindingsForBeanType() {
/* 278 */     return this._type.getBindings();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JavaType resolveType(Type jdkType) {
/* 284 */     if (jdkType == null) {
/* 285 */       return null;
/*     */     }
/* 287 */     return this._config.getTypeFactory().constructType(jdkType, this._type.getBindings());
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedConstructor findDefaultConstructor() {
/* 292 */     return this._classInfo.getDefaultConstructor();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMember findAnySetterAccessor() throws IllegalArgumentException {
/* 298 */     if (this._propCollector != null) {
/* 299 */       AnnotatedMethod anyMethod = this._propCollector.getAnySetterMethod();
/* 300 */       if (anyMethod != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 307 */         Class<?> type = anyMethod.getRawParameterType(0);
/* 308 */         if (type != String.class && type != Object.class) {
/* 309 */           throw new IllegalArgumentException(String.format("Invalid 'any-setter' annotation on method '%s()': first argument not of type String or Object, but %s", new Object[] { anyMethod.getName(), type.getName() }));
/*     */         }
/*     */ 
/*     */         
/* 313 */         return anyMethod;
/*     */       } 
/* 315 */       AnnotatedMember anyField = this._propCollector.getAnySetterField();
/* 316 */       if (anyField != null) {
/*     */ 
/*     */         
/* 319 */         Class<?> type = anyField.getRawType();
/* 320 */         if (!Map.class.isAssignableFrom(type)) {
/* 321 */           throw new IllegalArgumentException(String.format("Invalid 'any-setter' annotation on field '%s': type is not instance of java.util.Map", new Object[] { anyField.getName() }));
/*     */         }
/*     */ 
/*     */         
/* 325 */         return anyField;
/*     */       } 
/*     */     } 
/* 328 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Object, AnnotatedMember> findInjectables() {
/* 333 */     if (this._propCollector != null) {
/* 334 */       return this._propCollector.getInjectables();
/*     */     }
/* 336 */     return Collections.emptyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AnnotatedConstructor> getConstructors() {
/* 341 */     return this._classInfo.getConstructors();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object instantiateBean(boolean fixAccess) {
/* 346 */     AnnotatedConstructor ac = this._classInfo.getDefaultConstructor();
/* 347 */     if (ac == null) {
/* 348 */       return null;
/*     */     }
/* 350 */     if (fixAccess) {
/* 351 */       ac.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */     }
/*     */     try {
/* 354 */       return ac.getAnnotated().newInstance(new Object[0]);
/* 355 */     } catch (Exception e) {
/* 356 */       Throwable t = e;
/* 357 */       while (t.getCause() != null) {
/* 358 */         t = t.getCause();
/*     */       }
/* 360 */       ClassUtil.throwIfError(t);
/* 361 */       ClassUtil.throwIfRTE(t);
/* 362 */       throw new IllegalArgumentException("Failed to instantiate bean of type " + this._classInfo.getAnnotated().getName() + ": (" + t.getClass().getName() + ") " + t.getMessage(), t);
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
/*     */   public AnnotatedMethod findMethod(String name, Class<?>[] paramTypes) {
/* 374 */     return this._classInfo.findMethod(name, paramTypes);
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
/*     */   public JsonFormat.Value findExpectedFormat(JsonFormat.Value defValue) {
/* 388 */     if (this._annotationIntrospector != null) {
/* 389 */       JsonFormat.Value value = this._annotationIntrospector.findFormat(this._classInfo);
/* 390 */       if (value != null) {
/* 391 */         if (defValue == null) {
/* 392 */           defValue = value;
/*     */         } else {
/* 394 */           defValue = defValue.withOverrides(value);
/*     */         } 
/*     */       }
/*     */     } 
/* 398 */     JsonFormat.Value v = this._config.getDefaultPropertyFormat(this._classInfo.getRawType());
/* 399 */     if (v != null) {
/* 400 */       if (defValue == null) {
/* 401 */         defValue = v;
/*     */       } else {
/* 403 */         defValue = defValue.withOverrides(v);
/*     */       } 
/*     */     }
/* 406 */     return defValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?>[] findDefaultViews() {
/* 412 */     if (!this._defaultViewsResolved) {
/* 413 */       this._defaultViewsResolved = true;
/* 414 */       Class<?>[] def = (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findViews(this._classInfo);
/*     */ 
/*     */       
/* 417 */       if (def == null && 
/* 418 */         !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
/* 419 */         def = NO_VIEWS;
/*     */       }
/*     */       
/* 422 */       this._defaultViews = def;
/*     */     } 
/* 424 */     return this._defaultViews;
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
/*     */   public Converter<Object, Object> findSerializationConverter() {
/* 436 */     if (this._annotationIntrospector == null) {
/* 437 */       return null;
/*     */     }
/* 439 */     return _createConverter(this._annotationIntrospector.findSerializationConverter(this._classInfo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonInclude.Value findPropertyInclusion(JsonInclude.Value defValue) {
/* 450 */     if (this._annotationIntrospector != null) {
/* 451 */       JsonInclude.Value incl = this._annotationIntrospector.findPropertyInclusion(this._classInfo);
/* 452 */       if (incl != null) {
/* 453 */         return (defValue == null) ? incl : defValue.withOverrides(incl);
/*     */       }
/*     */     } 
/* 456 */     return defValue;
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
/*     */   public AnnotatedMember findAnyGetter() throws IllegalArgumentException {
/* 468 */     AnnotatedMember anyGetter = (this._propCollector == null) ? null : this._propCollector.getAnyGetter();
/*     */     
/* 470 */     if (anyGetter != null) {
/*     */ 
/*     */ 
/*     */       
/* 474 */       Class<?> type = anyGetter.getRawType();
/* 475 */       if (!Map.class.isAssignableFrom(type)) {
/* 476 */         throw new IllegalArgumentException("Invalid 'any-getter' annotation on method " + anyGetter.getName() + "(): return type is not instance of java.util.Map");
/*     */       }
/*     */     } 
/* 479 */     return anyGetter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BeanPropertyDefinition> findBackReferences() {
/* 485 */     List<BeanPropertyDefinition> result = null;
/* 486 */     HashSet<String> names = null;
/* 487 */     for (BeanPropertyDefinition property : _properties()) {
/* 488 */       AnnotationIntrospector.ReferenceProperty refDef = property.findReferenceType();
/* 489 */       if (refDef == null || !refDef.isBackReference()) {
/*     */         continue;
/*     */       }
/* 492 */       String refName = refDef.getName();
/* 493 */       if (result == null) {
/* 494 */         result = new ArrayList<>();
/* 495 */         names = new HashSet<>();
/* 496 */         names.add(refName);
/*     */       }
/* 498 */       else if (!names.add(refName)) {
/* 499 */         throw new IllegalArgumentException("Multiple back-reference properties with name '" + refName + "'");
/*     */       } 
/*     */       
/* 502 */       result.add(property);
/*     */     } 
/* 504 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Map<String, AnnotatedMember> findBackReferenceProperties() {
/* 511 */     List<BeanPropertyDefinition> props = findBackReferences();
/* 512 */     if (props == null) {
/* 513 */       return null;
/*     */     }
/* 515 */     Map<String, AnnotatedMember> result = new HashMap<>();
/* 516 */     for (BeanPropertyDefinition prop : props) {
/* 517 */       result.put(prop.getName(), prop.getMutator());
/*     */     }
/* 519 */     return result;
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
/*     */   public List<AnnotatedMethod> getFactoryMethods() {
/* 532 */     List<AnnotatedMethod> candidates = this._classInfo.getFactoryMethods();
/* 533 */     if (candidates.isEmpty()) {
/* 534 */       return candidates;
/*     */     }
/* 536 */     ArrayList<AnnotatedMethod> result = new ArrayList<>();
/* 537 */     for (AnnotatedMethod am : candidates) {
/* 538 */       if (isFactoryMethod(am)) {
/* 539 */         result.add(am);
/*     */       }
/*     */     } 
/* 542 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Constructor<?> findSingleArgConstructor(Class<?>... argTypes) {
/* 548 */     for (AnnotatedConstructor ac : this._classInfo.getConstructors()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 553 */       if (ac.getParameterCount() == 1) {
/* 554 */         Class<?> actArg = ac.getRawParameterType(0);
/* 555 */         for (Class<?> expArg : argTypes) {
/* 556 */           if (expArg == actArg) {
/* 557 */             return ac.getAnnotated();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 562 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method findFactoryMethod(Class<?>... expArgTypes) {
/* 569 */     for (AnnotatedMethod am : this._classInfo.getFactoryMethods()) {
/*     */       
/* 571 */       if (isFactoryMethod(am) && am.getParameterCount() == 1) {
/*     */         
/* 573 */         Class<?> actualArgType = am.getRawParameterType(0);
/* 574 */         for (Class<?> expArgType : expArgTypes) {
/*     */           
/* 576 */           if (actualArgType.isAssignableFrom(expArgType)) {
/* 577 */             return am.getAnnotated();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 582 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isFactoryMethod(AnnotatedMethod am) {
/* 589 */     Class<?> rt = am.getRawReturnType();
/* 590 */     if (!getBeanClass().isAssignableFrom(rt)) {
/* 591 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 597 */     JsonCreator.Mode mode = this._annotationIntrospector.findCreatorAnnotation(this._config, am);
/* 598 */     if (mode != null && mode != JsonCreator.Mode.DISABLED) {
/* 599 */       return true;
/*     */     }
/* 601 */     String name = am.getName();
/*     */     
/* 603 */     if ("valueOf".equals(name) && 
/* 604 */       am.getParameterCount() == 1) {
/* 605 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 609 */     if ("fromString".equals(name) && 
/* 610 */       am.getParameterCount() == 1) {
/* 611 */       Class<?> cls = am.getRawParameterType(0);
/* 612 */       if (cls == String.class || CharSequence.class.isAssignableFrom(cls)) {
/* 613 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 617 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected PropertyName _findCreatorPropertyName(AnnotatedParameter param) {
/* 626 */     PropertyName name = this._annotationIntrospector.findNameForDeserialization(param);
/* 627 */     if (name == null || name.isEmpty()) {
/* 628 */       String str = this._annotationIntrospector.findImplicitPropertyName(param);
/* 629 */       if (str != null && !str.isEmpty()) {
/* 630 */         name = PropertyName.construct(str);
/*     */       }
/*     */     } 
/* 633 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findPOJOBuilder() {
/* 644 */     return (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findPOJOBuilder(this._classInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPOJOBuilder.Value findPOJOBuilderConfig() {
/* 651 */     return (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findPOJOBuilderConfig(this._classInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Object, Object> findDeserializationConverter() {
/* 658 */     if (this._annotationIntrospector == null) {
/* 659 */       return null;
/*     */     }
/* 661 */     return _createConverter(this._annotationIntrospector.findDeserializationConverter(this._classInfo));
/*     */   }
/*     */ 
/*     */   
/*     */   public String findClassDescription() {
/* 666 */     return (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findClassDescription(this._classInfo);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public LinkedHashMap<String, AnnotatedField> _findPropertyFields(Collection<String> ignoredProperties, boolean forSerialization) {
/* 692 */     LinkedHashMap<String, AnnotatedField> results = new LinkedHashMap<>();
/* 693 */     for (BeanPropertyDefinition property : _properties()) {
/* 694 */       AnnotatedField f = property.getField();
/* 695 */       if (f != null) {
/* 696 */         String name = property.getName();
/* 697 */         if (ignoredProperties != null && 
/* 698 */           ignoredProperties.contains(name)) {
/*     */           continue;
/*     */         }
/*     */         
/* 702 */         results.put(name, f);
/*     */       } 
/*     */     } 
/* 705 */     return results;
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
/*     */   protected Converter<Object, Object> _createConverter(Object converterDef) {
/* 717 */     if (converterDef == null) {
/* 718 */       return null;
/*     */     }
/* 720 */     if (converterDef instanceof Converter) {
/* 721 */       return (Converter<Object, Object>)converterDef;
/*     */     }
/* 723 */     if (!(converterDef instanceof Class)) {
/* 724 */       throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + converterDef.getClass().getName() + "; expected type Converter or Class<Converter> instead");
/*     */     }
/*     */     
/* 727 */     Class<?> converterClass = (Class)converterDef;
/*     */     
/* 729 */     if (converterClass == Converter.None.class || ClassUtil.isBogusClass(converterClass)) {
/* 730 */       return null;
/*     */     }
/* 732 */     if (!Converter.class.isAssignableFrom(converterClass)) {
/* 733 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + converterClass.getName() + "; expected Class<Converter>");
/*     */     }
/*     */     
/* 736 */     HandlerInstantiator hi = this._config.getHandlerInstantiator();
/* 737 */     Converter<?, ?> conv = (hi == null) ? null : hi.converterInstance(this._config, this._classInfo, converterClass);
/* 738 */     if (conv == null) {
/* 739 */       conv = (Converter<?, ?>)ClassUtil.createInstance(converterClass, this._config.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 742 */     return (Converter)conv;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\BasicBeanDescription.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */