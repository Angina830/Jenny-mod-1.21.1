/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*      */ 
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonInject;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyNamingStrategy;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.BeanUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class POJOPropertiesCollector
/*      */ {
/*      */   protected final MapperConfig<?> _config;
/*      */   protected final boolean _forSerialization;
/*      */   protected final boolean _stdBeanNaming;
/*      */   protected final JavaType _type;
/*      */   protected final AnnotatedClass _classDef;
/*      */   protected final VisibilityChecker<?> _visibilityChecker;
/*      */   protected final AnnotationIntrospector _annotationIntrospector;
/*      */   protected final boolean _useAnnotations;
/*      */   protected final String _mutatorPrefix;
/*      */   protected boolean _collected;
/*      */   protected LinkedHashMap<String, POJOPropertyBuilder> _properties;
/*      */   protected LinkedList<POJOPropertyBuilder> _creatorProperties;
/*      */   protected LinkedList<AnnotatedMember> _anyGetters;
/*      */   protected LinkedList<AnnotatedMethod> _anySetters;
/*      */   protected LinkedList<AnnotatedMember> _anySetterField;
/*      */   protected LinkedList<AnnotatedMember> _jsonValueAccessors;
/*      */   protected HashSet<String> _ignoredPropertyNames;
/*      */   protected LinkedHashMap<Object, AnnotatedMember> _injectables;
/*      */   
/*      */   protected POJOPropertiesCollector(MapperConfig<?> config, boolean forSerialization, JavaType type, AnnotatedClass classDef, String mutatorPrefix) {
/*  129 */     this._config = config;
/*  130 */     this._stdBeanNaming = config.isEnabled(MapperFeature.USE_STD_BEAN_NAMING);
/*  131 */     this._forSerialization = forSerialization;
/*  132 */     this._type = type;
/*  133 */     this._classDef = classDef;
/*  134 */     this._mutatorPrefix = (mutatorPrefix == null) ? "set" : mutatorPrefix;
/*  135 */     if (config.isAnnotationProcessingEnabled()) {
/*  136 */       this._useAnnotations = true;
/*  137 */       this._annotationIntrospector = this._config.getAnnotationIntrospector();
/*      */     } else {
/*  139 */       this._useAnnotations = false;
/*  140 */       this._annotationIntrospector = AnnotationIntrospector.nopInstance();
/*      */     } 
/*  142 */     this._visibilityChecker = this._config.getDefaultVisibilityChecker(type.getRawClass(), classDef);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapperConfig<?> getConfig() {
/*  153 */     return this._config;
/*      */   }
/*      */   
/*      */   public JavaType getType() {
/*  157 */     return this._type;
/*      */   }
/*      */   
/*      */   public AnnotatedClass getClassDef() {
/*  161 */     return this._classDef;
/*      */   }
/*      */   
/*      */   public AnnotationIntrospector getAnnotationIntrospector() {
/*  165 */     return this._annotationIntrospector;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<BeanPropertyDefinition> getProperties() {
/*  170 */     Map<String, POJOPropertyBuilder> props = getPropertyMap();
/*  171 */     return new ArrayList<>(props.values());
/*      */   }
/*      */   
/*      */   public Map<Object, AnnotatedMember> getInjectables() {
/*  175 */     if (!this._collected) {
/*  176 */       collectAll();
/*      */     }
/*  178 */     return this._injectables;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public AnnotatedMethod getJsonValueMethod() {
/*  183 */     AnnotatedMember m = getJsonValueAccessor();
/*  184 */     if (m instanceof AnnotatedMethod) {
/*  185 */       return (AnnotatedMethod)m;
/*      */     }
/*  187 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedMember getJsonValueAccessor() {
/*  195 */     if (!this._collected) {
/*  196 */       collectAll();
/*      */     }
/*      */     
/*  199 */     if (this._jsonValueAccessors != null) {
/*  200 */       if (this._jsonValueAccessors.size() > 1) {
/*  201 */         reportProblem("Multiple 'as-value' properties defined (%s vs %s)", new Object[] { this._jsonValueAccessors.get(0), this._jsonValueAccessors.get(1) });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  206 */       return this._jsonValueAccessors.get(0);
/*      */     } 
/*  208 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public AnnotatedMember getAnyGetter() {
/*  213 */     if (!this._collected) {
/*  214 */       collectAll();
/*      */     }
/*  216 */     if (this._anyGetters != null) {
/*  217 */       if (this._anyGetters.size() > 1) {
/*  218 */         reportProblem("Multiple 'any-getters' defined (%s vs %s)", new Object[] { this._anyGetters.get(0), this._anyGetters.get(1) });
/*      */       }
/*      */       
/*  221 */       return this._anyGetters.getFirst();
/*      */     } 
/*  223 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public AnnotatedMember getAnySetterField() {
/*  228 */     if (!this._collected) {
/*  229 */       collectAll();
/*      */     }
/*  231 */     if (this._anySetterField != null) {
/*  232 */       if (this._anySetterField.size() > 1) {
/*  233 */         reportProblem("Multiple 'any-setter' fields defined (%s vs %s)", new Object[] { this._anySetterField.get(0), this._anySetterField.get(1) });
/*      */       }
/*      */       
/*  236 */       return this._anySetterField.getFirst();
/*      */     } 
/*  238 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public AnnotatedMethod getAnySetterMethod() {
/*  243 */     if (!this._collected) {
/*  244 */       collectAll();
/*      */     }
/*  246 */     if (this._anySetters != null) {
/*  247 */       if (this._anySetters.size() > 1) {
/*  248 */         reportProblem("Multiple 'any-setter' methods defined (%s vs %s)", new Object[] { this._anySetters.get(0), this._anySetters.get(1) });
/*      */       }
/*      */       
/*  251 */       return this._anySetters.getFirst();
/*      */     } 
/*  253 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getIgnoredPropertyNames() {
/*  261 */     return this._ignoredPropertyNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectIdInfo getObjectIdInfo() {
/*  270 */     ObjectIdInfo info = this._annotationIntrospector.findObjectIdInfo(this._classDef);
/*  271 */     if (info != null) {
/*  272 */       info = this._annotationIntrospector.findObjectReferenceInfo(this._classDef, info);
/*      */     }
/*  274 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> findPOJOBuilderClass() {
/*  281 */     return this._annotationIntrospector.findPOJOBuilder(this._classDef);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Map<String, POJOPropertyBuilder> getPropertyMap() {
/*  286 */     if (!this._collected) {
/*  287 */       collectAll();
/*      */     }
/*  289 */     return this._properties;
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
/*      */   protected void collectAll() {
/*  305 */     LinkedHashMap<String, POJOPropertyBuilder> props = new LinkedHashMap<>();
/*      */ 
/*      */     
/*  308 */     _addFields(props);
/*  309 */     _addMethods(props);
/*      */ 
/*      */     
/*  312 */     if (!this._classDef.isNonStaticInnerClass()) {
/*  313 */       _addCreators(props);
/*      */     }
/*  315 */     _addInjectables(props);
/*      */ 
/*      */ 
/*      */     
/*  319 */     _removeUnwantedProperties(props);
/*      */ 
/*      */     
/*  322 */     for (POJOPropertyBuilder property : props.values()) {
/*  323 */       property.mergeAnnotations(this._forSerialization);
/*      */     }
/*      */     
/*  326 */     _removeUnwantedAccessor(props);
/*      */ 
/*      */     
/*  329 */     _renameProperties(props);
/*      */ 
/*      */     
/*  332 */     PropertyNamingStrategy naming = _findNamingStrategy();
/*  333 */     if (naming != null) {
/*  334 */       _renameUsing(props, naming);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  341 */     for (POJOPropertyBuilder property : props.values()) {
/*  342 */       property.trimByVisibility();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  348 */     if (this._config.isEnabled(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)) {
/*  349 */       _renameWithWrappers(props);
/*      */     }
/*      */ 
/*      */     
/*  353 */     _sortProperties(props);
/*  354 */     this._properties = props;
/*  355 */     this._collected = true;
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
/*      */   protected void _addFields(Map<String, POJOPropertyBuilder> props) {
/*  369 */     AnnotationIntrospector ai = this._annotationIntrospector;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  374 */     boolean pruneFinalFields = (!this._forSerialization && !this._config.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS));
/*  375 */     boolean transientAsIgnoral = this._config.isEnabled(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
/*      */     
/*  377 */     for (AnnotatedField f : this._classDef.fields()) {
/*  378 */       PropertyName pn; String implName = ai.findImplicitPropertyName(f);
/*      */       
/*  380 */       if (Boolean.TRUE.equals(ai.hasAsValue(f))) {
/*  381 */         if (this._jsonValueAccessors == null) {
/*  382 */           this._jsonValueAccessors = new LinkedList<>();
/*      */         }
/*  384 */         this._jsonValueAccessors.add(f);
/*      */         
/*      */         continue;
/*      */       } 
/*  388 */       if (Boolean.TRUE.equals(ai.hasAnySetter(f))) {
/*  389 */         if (this._anySetterField == null) {
/*  390 */           this._anySetterField = new LinkedList<>();
/*      */         }
/*  392 */         this._anySetterField.add(f);
/*      */         continue;
/*      */       } 
/*  395 */       if (implName == null) {
/*  396 */         implName = f.getName();
/*      */       }
/*      */ 
/*      */       
/*  400 */       if (this._forSerialization) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  406 */         pn = ai.findNameForSerialization(f);
/*      */       } else {
/*  408 */         pn = ai.findNameForDeserialization(f);
/*      */       } 
/*  410 */       boolean hasName = (pn != null);
/*  411 */       boolean nameExplicit = hasName;
/*      */       
/*  413 */       if (nameExplicit && pn.isEmpty()) {
/*  414 */         pn = _propNameFromSimple(implName);
/*  415 */         nameExplicit = false;
/*      */       } 
/*      */       
/*  418 */       boolean visible = (pn != null);
/*  419 */       if (!visible) {
/*  420 */         visible = this._visibilityChecker.isFieldVisible(f);
/*      */       }
/*      */       
/*  423 */       boolean ignored = ai.hasIgnoreMarker(f);
/*      */ 
/*      */       
/*  426 */       if (f.isTransient())
/*      */       {
/*      */         
/*  429 */         if (!hasName) {
/*  430 */           visible = false;
/*  431 */           if (transientAsIgnoral) {
/*  432 */             ignored = true;
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  441 */       if (pruneFinalFields && pn == null && !ignored && Modifier.isFinal(f.getModifiers())) {
/*      */         continue;
/*      */       }
/*      */       
/*  445 */       _property(props, implName).addField(f, pn, nameExplicit, visible, ignored);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _addCreators(Map<String, POJOPropertyBuilder> props) {
/*  455 */     if (!this._useAnnotations) {
/*      */       return;
/*      */     }
/*  458 */     for (AnnotatedConstructor ctor : this._classDef.getConstructors()) {
/*  459 */       if (this._creatorProperties == null) {
/*  460 */         this._creatorProperties = new LinkedList<>();
/*      */       }
/*  462 */       for (int i = 0, len = ctor.getParameterCount(); i < len; i++) {
/*  463 */         _addCreatorParam(props, ctor.getParameter(i));
/*      */       }
/*      */     } 
/*  466 */     for (AnnotatedMethod factory : this._classDef.getFactoryMethods()) {
/*  467 */       if (this._creatorProperties == null) {
/*  468 */         this._creatorProperties = new LinkedList<>();
/*      */       }
/*  470 */       for (int i = 0, len = factory.getParameterCount(); i < len; i++) {
/*  471 */         _addCreatorParam(props, factory.getParameter(i));
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
/*      */   protected void _addCreatorParam(Map<String, POJOPropertyBuilder> props, AnnotatedParameter param) {
/*  483 */     String impl = this._annotationIntrospector.findImplicitPropertyName(param);
/*  484 */     if (impl == null) {
/*  485 */       impl = "";
/*      */     }
/*  487 */     PropertyName pn = this._annotationIntrospector.findNameForDeserialization(param);
/*  488 */     boolean expl = (pn != null && !pn.isEmpty());
/*  489 */     if (!expl) {
/*  490 */       if (impl.isEmpty()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  496 */       JsonCreator.Mode creatorMode = this._annotationIntrospector.findCreatorAnnotation(this._config, param.getOwner());
/*      */       
/*  498 */       if (creatorMode == null || creatorMode == JsonCreator.Mode.DISABLED) {
/*      */         return;
/*      */       }
/*  501 */       pn = PropertyName.construct(impl);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  511 */     POJOPropertyBuilder prop = (expl && impl.isEmpty()) ? _property(props, pn) : _property(props, impl);
/*      */     
/*  513 */     prop.addCtor(param, pn, expl, true, false);
/*  514 */     this._creatorProperties.add(prop);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _addMethods(Map<String, POJOPropertyBuilder> props) {
/*  522 */     AnnotationIntrospector ai = this._annotationIntrospector;
/*  523 */     for (AnnotatedMethod m : this._classDef.memberMethods()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  529 */       int argCount = m.getParameterCount();
/*  530 */       if (argCount == 0) {
/*  531 */         _addGetterMethod(props, m, ai); continue;
/*  532 */       }  if (argCount == 1) {
/*  533 */         _addSetterMethod(props, m, ai); continue;
/*  534 */       }  if (argCount == 2 && 
/*  535 */         ai != null && 
/*  536 */         Boolean.TRUE.equals(ai.hasAnySetter(m))) {
/*  537 */         if (this._anySetters == null) {
/*  538 */           this._anySetters = new LinkedList<>();
/*      */         }
/*  540 */         this._anySetters.add(m);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _addGetterMethod(Map<String, POJOPropertyBuilder> props, AnnotatedMethod m, AnnotationIntrospector ai) {
/*      */     String implName;
/*      */     boolean visible;
/*  551 */     if (!m.hasReturnType()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  557 */     if (Boolean.TRUE.equals(ai.hasAnyGetter(m))) {
/*  558 */       if (this._anyGetters == null) {
/*  559 */         this._anyGetters = new LinkedList<>();
/*      */       }
/*  561 */       this._anyGetters.add(m);
/*      */       
/*      */       return;
/*      */     } 
/*  565 */     if (Boolean.TRUE.equals(ai.hasAsValue(m))) {
/*  566 */       if (this._jsonValueAccessors == null) {
/*  567 */         this._jsonValueAccessors = new LinkedList<>();
/*      */       }
/*  569 */       this._jsonValueAccessors.add(m);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  575 */     PropertyName pn = ai.findNameForSerialization(m);
/*  576 */     boolean nameExplicit = (pn != null);
/*      */     
/*  578 */     if (!nameExplicit) {
/*  579 */       implName = ai.findImplicitPropertyName(m);
/*  580 */       if (implName == null) {
/*  581 */         implName = BeanUtil.okNameForRegularGetter(m, m.getName(), this._stdBeanNaming);
/*      */       }
/*  583 */       if (implName == null) {
/*  584 */         implName = BeanUtil.okNameForIsGetter(m, m.getName(), this._stdBeanNaming);
/*  585 */         if (implName == null) {
/*      */           return;
/*      */         }
/*  588 */         visible = this._visibilityChecker.isIsGetterVisible(m);
/*      */       } else {
/*  590 */         visible = this._visibilityChecker.isGetterVisible(m);
/*      */       } 
/*      */     } else {
/*      */       
/*  594 */       implName = ai.findImplicitPropertyName(m);
/*  595 */       if (implName == null) {
/*  596 */         implName = BeanUtil.okNameForGetter(m, this._stdBeanNaming);
/*      */       }
/*      */       
/*  599 */       if (implName == null) {
/*  600 */         implName = m.getName();
/*      */       }
/*  602 */       if (pn.isEmpty()) {
/*      */         
/*  604 */         pn = _propNameFromSimple(implName);
/*  605 */         nameExplicit = false;
/*      */       } 
/*  607 */       visible = true;
/*      */     } 
/*  609 */     boolean ignore = ai.hasIgnoreMarker(m);
/*  610 */     _property(props, implName).addGetter(m, pn, nameExplicit, visible, ignore);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _addSetterMethod(Map<String, POJOPropertyBuilder> props, AnnotatedMethod m, AnnotationIntrospector ai) {
/*      */     String implName;
/*      */     boolean visible;
/*  618 */     PropertyName pn = (ai == null) ? null : ai.findNameForDeserialization(m);
/*  619 */     boolean nameExplicit = (pn != null);
/*  620 */     if (!nameExplicit) {
/*  621 */       implName = (ai == null) ? null : ai.findImplicitPropertyName(m);
/*  622 */       if (implName == null) {
/*  623 */         implName = BeanUtil.okNameForMutator(m, this._mutatorPrefix, this._stdBeanNaming);
/*      */       }
/*  625 */       if (implName == null) {
/*      */         return;
/*      */       }
/*  628 */       visible = this._visibilityChecker.isSetterVisible(m);
/*      */     } else {
/*      */       
/*  631 */       implName = (ai == null) ? null : ai.findImplicitPropertyName(m);
/*  632 */       if (implName == null) {
/*  633 */         implName = BeanUtil.okNameForMutator(m, this._mutatorPrefix, this._stdBeanNaming);
/*      */       }
/*      */       
/*  636 */       if (implName == null) {
/*  637 */         implName = m.getName();
/*      */       }
/*  639 */       if (pn.isEmpty()) {
/*      */         
/*  641 */         pn = _propNameFromSimple(implName);
/*  642 */         nameExplicit = false;
/*      */       } 
/*  644 */       visible = true;
/*      */     } 
/*  646 */     boolean ignore = (ai == null) ? false : ai.hasIgnoreMarker(m);
/*  647 */     _property(props, implName).addSetter(m, pn, nameExplicit, visible, ignore);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _addInjectables(Map<String, POJOPropertyBuilder> props) {
/*  652 */     AnnotationIntrospector ai = this._annotationIntrospector;
/*      */     
/*  654 */     for (AnnotatedField f : this._classDef.fields()) {
/*  655 */       _doAddInjectable(ai.findInjectableValue(f), f);
/*      */     }
/*      */     
/*  658 */     for (AnnotatedMethod m : this._classDef.memberMethods()) {
/*      */       
/*  660 */       if (m.getParameterCount() != 1) {
/*      */         continue;
/*      */       }
/*  663 */       _doAddInjectable(ai.findInjectableValue(m), m);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _doAddInjectable(JacksonInject.Value injectable, AnnotatedMember m) {
/*  669 */     if (injectable == null) {
/*      */       return;
/*      */     }
/*  672 */     Object id = injectable.getId();
/*  673 */     if (this._injectables == null) {
/*  674 */       this._injectables = new LinkedHashMap<>();
/*      */     }
/*  676 */     AnnotatedMember prev = this._injectables.put(id, m);
/*  677 */     if (prev != null)
/*      */     {
/*  679 */       if (prev.getClass() == m.getClass()) {
/*  680 */         String type = id.getClass().getName();
/*  681 */         throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf(id) + "' (of type " + type + ")");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private PropertyName _propNameFromSimple(String simpleName) {
/*  688 */     return PropertyName.construct(simpleName, null);
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
/*      */   protected void _removeUnwantedProperties(Map<String, POJOPropertyBuilder> props) {
/*  703 */     Iterator<POJOPropertyBuilder> it = props.values().iterator();
/*  704 */     while (it.hasNext()) {
/*  705 */       POJOPropertyBuilder prop = it.next();
/*      */ 
/*      */       
/*  708 */       if (!prop.anyVisible()) {
/*  709 */         it.remove();
/*      */         
/*      */         continue;
/*      */       } 
/*  713 */       if (prop.anyIgnorals()) {
/*      */         
/*  715 */         if (!prop.isExplicitlyIncluded()) {
/*  716 */           it.remove();
/*  717 */           _collectIgnorals(prop.getName());
/*      */           
/*      */           continue;
/*      */         } 
/*  721 */         prop.removeIgnored();
/*  722 */         if (!this._forSerialization && !prop.couldDeserialize()) {
/*  723 */           _collectIgnorals(prop.getName());
/*      */         }
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
/*      */   protected void _removeUnwantedAccessor(Map<String, POJOPropertyBuilder> props) {
/*  736 */     boolean inferMutators = this._config.isEnabled(MapperFeature.INFER_PROPERTY_MUTATORS);
/*  737 */     Iterator<POJOPropertyBuilder> it = props.values().iterator();
/*      */     
/*  739 */     while (it.hasNext()) {
/*  740 */       POJOPropertyBuilder prop = it.next();
/*      */       
/*  742 */       JsonProperty.Access acc = prop.removeNonVisible(inferMutators);
/*  743 */       if (!this._forSerialization && acc == JsonProperty.Access.READ_ONLY) {
/*  744 */         _collectIgnorals(prop.getName());
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
/*      */   private void _collectIgnorals(String name) {
/*  756 */     if (!this._forSerialization) {
/*  757 */       if (this._ignoredPropertyNames == null) {
/*  758 */         this._ignoredPropertyNames = new HashSet<>();
/*      */       }
/*  760 */       this._ignoredPropertyNames.add(name);
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
/*      */   protected void _renameProperties(Map<String, POJOPropertyBuilder> props) {
/*  773 */     Iterator<Map.Entry<String, POJOPropertyBuilder>> it = props.entrySet().iterator();
/*  774 */     LinkedList<POJOPropertyBuilder> renamed = null;
/*  775 */     while (it.hasNext()) {
/*  776 */       Map.Entry<String, POJOPropertyBuilder> entry = it.next();
/*  777 */       POJOPropertyBuilder prop = entry.getValue();
/*      */       
/*  779 */       Collection<PropertyName> l = prop.findExplicitNames();
/*      */ 
/*      */       
/*  782 */       if (l.isEmpty()) {
/*      */         continue;
/*      */       }
/*  785 */       it.remove();
/*  786 */       if (renamed == null) {
/*  787 */         renamed = new LinkedList<>();
/*      */       }
/*      */       
/*  790 */       if (l.size() == 1) {
/*  791 */         PropertyName n = l.iterator().next();
/*  792 */         renamed.add(prop.withName(n));
/*      */         
/*      */         continue;
/*      */       } 
/*  796 */       renamed.addAll(prop.explode(l));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  812 */     if (renamed != null) {
/*  813 */       for (POJOPropertyBuilder prop : renamed) {
/*  814 */         String name = prop.getName();
/*  815 */         POJOPropertyBuilder old = props.get(name);
/*  816 */         if (old == null) {
/*  817 */           props.put(name, prop);
/*      */         } else {
/*  819 */           old.addAll(prop);
/*      */         } 
/*      */         
/*  822 */         _updateCreatorProperty(prop, this._creatorProperties);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _renameUsing(Map<String, POJOPropertyBuilder> propMap, PropertyNamingStrategy naming) {
/*  830 */     POJOPropertyBuilder[] props = (POJOPropertyBuilder[])propMap.values().toArray((Object[])new POJOPropertyBuilder[propMap.size()]);
/*  831 */     propMap.clear();
/*  832 */     for (POJOPropertyBuilder prop : props) {
/*  833 */       String simpleName; PropertyName fullName = prop.getFullName();
/*  834 */       String rename = null;
/*      */ 
/*      */       
/*  837 */       if (!prop.isExplicitlyNamed() || this._config.isEnabled(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING)) {
/*  838 */         if (this._forSerialization) {
/*  839 */           if (prop.hasGetter()) {
/*  840 */             rename = naming.nameForGetterMethod(this._config, prop.getGetter(), fullName.getSimpleName());
/*  841 */           } else if (prop.hasField()) {
/*  842 */             rename = naming.nameForField(this._config, prop.getField(), fullName.getSimpleName());
/*      */           }
/*      */         
/*  845 */         } else if (prop.hasSetter()) {
/*  846 */           rename = naming.nameForSetterMethod(this._config, prop.getSetter(), fullName.getSimpleName());
/*  847 */         } else if (prop.hasConstructorParameter()) {
/*  848 */           rename = naming.nameForConstructorParameter(this._config, prop.getConstructorParameter(), fullName.getSimpleName());
/*  849 */         } else if (prop.hasField()) {
/*  850 */           rename = naming.nameForField(this._config, prop.getField(), fullName.getSimpleName());
/*  851 */         } else if (prop.hasGetter()) {
/*      */ 
/*      */ 
/*      */           
/*  855 */           rename = naming.nameForGetterMethod(this._config, prop.getGetter(), fullName.getSimpleName());
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  860 */       if (rename != null && !fullName.hasSimpleName(rename)) {
/*  861 */         prop = prop.withSimpleName(rename);
/*  862 */         simpleName = rename;
/*      */       } else {
/*  864 */         simpleName = fullName.getSimpleName();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  869 */       POJOPropertyBuilder old = propMap.get(simpleName);
/*  870 */       if (old == null) {
/*  871 */         propMap.put(simpleName, prop);
/*      */       } else {
/*  873 */         old.addAll(prop);
/*      */       } 
/*      */       
/*  876 */       _updateCreatorProperty(prop, this._creatorProperties);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _renameWithWrappers(Map<String, POJOPropertyBuilder> props) {
/*  884 */     Iterator<Map.Entry<String, POJOPropertyBuilder>> it = props.entrySet().iterator();
/*  885 */     LinkedList<POJOPropertyBuilder> renamed = null;
/*  886 */     while (it.hasNext()) {
/*  887 */       Map.Entry<String, POJOPropertyBuilder> entry = it.next();
/*  888 */       POJOPropertyBuilder prop = entry.getValue();
/*  889 */       AnnotatedMember member = prop.getPrimaryMember();
/*  890 */       if (member == null) {
/*      */         continue;
/*      */       }
/*  893 */       PropertyName wrapperName = this._annotationIntrospector.findWrapperName(member);
/*      */ 
/*      */ 
/*      */       
/*  897 */       if (wrapperName == null || !wrapperName.hasSimpleName()) {
/*      */         continue;
/*      */       }
/*  900 */       if (!wrapperName.equals(prop.getFullName())) {
/*  901 */         if (renamed == null) {
/*  902 */           renamed = new LinkedList<>();
/*      */         }
/*  904 */         prop = prop.withName(wrapperName);
/*  905 */         renamed.add(prop);
/*  906 */         it.remove();
/*      */       } 
/*      */     } 
/*      */     
/*  910 */     if (renamed != null) {
/*  911 */       for (POJOPropertyBuilder prop : renamed) {
/*  912 */         String name = prop.getName();
/*  913 */         POJOPropertyBuilder old = props.get(name);
/*  914 */         if (old == null) {
/*  915 */           props.put(name, prop); continue;
/*      */         } 
/*  917 */         old.addAll(prop);
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
/*      */   protected void _sortProperties(Map<String, POJOPropertyBuilder> props) {
/*      */     boolean sort;
/*      */     Map<String, POJOPropertyBuilder> all;
/*  935 */     AnnotationIntrospector intr = this._annotationIntrospector;
/*  936 */     Boolean alpha = intr.findSerializationSortAlphabetically(this._classDef);
/*      */ 
/*      */     
/*  939 */     if (alpha == null) {
/*  940 */       sort = this._config.shouldSortPropertiesAlphabetically();
/*      */     } else {
/*  942 */       sort = alpha.booleanValue();
/*      */     } 
/*  944 */     String[] propertyOrder = intr.findSerializationPropertyOrder(this._classDef);
/*      */ 
/*      */     
/*  947 */     if (!sort && this._creatorProperties == null && propertyOrder == null) {
/*      */       return;
/*      */     }
/*  950 */     int size = props.size();
/*      */ 
/*      */     
/*  953 */     if (sort) {
/*  954 */       all = new TreeMap<>();
/*      */     } else {
/*  956 */       all = new LinkedHashMap<>(size + size);
/*      */     } 
/*      */     
/*  959 */     for (POJOPropertyBuilder prop : props.values()) {
/*  960 */       all.put(prop.getName(), prop);
/*      */     }
/*  962 */     Map<String, POJOPropertyBuilder> ordered = new LinkedHashMap<>(size + size);
/*      */     
/*  964 */     if (propertyOrder != null) {
/*  965 */       for (String name : propertyOrder) {
/*  966 */         POJOPropertyBuilder w = all.get(name);
/*  967 */         if (w == null) {
/*  968 */           for (POJOPropertyBuilder prop : props.values()) {
/*  969 */             if (name.equals(prop.getInternalName())) {
/*  970 */               w = prop;
/*      */               
/*  972 */               name = prop.getName();
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*  977 */         if (w != null) {
/*  978 */           ordered.put(name, w);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  983 */     if (this._creatorProperties != null) {
/*      */       Collection<POJOPropertyBuilder> cr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  990 */       if (sort) {
/*  991 */         TreeMap<String, POJOPropertyBuilder> sorted = new TreeMap<>();
/*      */         
/*  993 */         for (POJOPropertyBuilder prop : this._creatorProperties) {
/*  994 */           sorted.put(prop.getName(), prop);
/*      */         }
/*  996 */         cr = sorted.values();
/*      */       } else {
/*  998 */         cr = this._creatorProperties;
/*      */       } 
/* 1000 */       for (POJOPropertyBuilder prop : cr) {
/*      */ 
/*      */         
/* 1003 */         String name = prop.getName();
/* 1004 */         if (all.containsKey(name)) {
/* 1005 */           ordered.put(name, prop);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1010 */     ordered.putAll(all);
/* 1011 */     props.clear();
/* 1012 */     props.putAll(ordered);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reportProblem(String msg, Object... args) {
/* 1022 */     if (args.length > 0) {
/* 1023 */       msg = String.format(msg, args);
/*      */     }
/* 1025 */     throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + msg);
/*      */   }
/*      */ 
/*      */   
/*      */   protected POJOPropertyBuilder _property(Map<String, POJOPropertyBuilder> props, PropertyName name) {
/* 1030 */     String simpleName = name.getSimpleName();
/* 1031 */     POJOPropertyBuilder prop = props.get(simpleName);
/* 1032 */     if (prop == null) {
/* 1033 */       prop = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, name);
/*      */       
/* 1035 */       props.put(simpleName, prop);
/*      */     } 
/* 1037 */     return prop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected POJOPropertyBuilder _property(Map<String, POJOPropertyBuilder> props, String implName) {
/* 1044 */     POJOPropertyBuilder prop = props.get(implName);
/* 1045 */     if (prop == null) {
/* 1046 */       prop = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, PropertyName.construct(implName));
/*      */       
/* 1048 */       props.put(implName, prop);
/*      */     } 
/* 1050 */     return prop;
/*      */   }
/*      */ 
/*      */   
/*      */   private PropertyNamingStrategy _findNamingStrategy() {
/* 1055 */     Object namingDef = this._annotationIntrospector.findNamingStrategy(this._classDef);
/* 1056 */     if (namingDef == null) {
/* 1057 */       return this._config.getPropertyNamingStrategy();
/*      */     }
/* 1059 */     if (namingDef instanceof PropertyNamingStrategy) {
/* 1060 */       return (PropertyNamingStrategy)namingDef;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1065 */     if (!(namingDef instanceof Class)) {
/* 1066 */       throw new IllegalStateException("AnnotationIntrospector returned PropertyNamingStrategy definition of type " + namingDef.getClass().getName() + "; expected type PropertyNamingStrategy or Class<PropertyNamingStrategy> instead");
/*      */     }
/*      */     
/* 1069 */     Class<?> namingClass = (Class)namingDef;
/*      */     
/* 1071 */     if (namingClass == PropertyNamingStrategy.class) {
/* 1072 */       return null;
/*      */     }
/*      */     
/* 1075 */     if (!PropertyNamingStrategy.class.isAssignableFrom(namingClass)) {
/* 1076 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + namingClass.getName() + "; expected Class<PropertyNamingStrategy>");
/*      */     }
/*      */     
/* 1079 */     HandlerInstantiator hi = this._config.getHandlerInstantiator();
/* 1080 */     if (hi != null) {
/* 1081 */       PropertyNamingStrategy pns = hi.namingStrategyInstance(this._config, this._classDef, namingClass);
/* 1082 */       if (pns != null) {
/* 1083 */         return pns;
/*      */       }
/*      */     } 
/* 1086 */     return (PropertyNamingStrategy)ClassUtil.createInstance(namingClass, this._config.canOverrideAccessModifiers());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _updateCreatorProperty(POJOPropertyBuilder prop, List<POJOPropertyBuilder> creatorProperties) {
/* 1091 */     if (creatorProperties != null)
/* 1092 */       for (int i = 0, len = creatorProperties.size(); i < len; i++) {
/* 1093 */         if (((POJOPropertyBuilder)creatorProperties.get(i)).getInternalName().equals(prop.getInternalName())) {
/* 1094 */           creatorProperties.set(i, prop);
/*      */           break;
/*      */         } 
/*      */       }  
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\POJOPropertiesCollector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */