/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonAutoDetect;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RootNameLookup;
/*     */ 
/*     */ public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>> extends MapperConfig<T> implements Serializable {
/*  30 */   protected static final ConfigOverride EMPTY_OVERRIDE = ConfigOverride.empty();
/*     */   
/*  32 */   private static final int DEFAULT_MAPPER_FEATURES = collectFeatureDefaults(MapperFeature.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final int AUTO_DETECT_MASK = MapperFeature.AUTO_DETECT_FIELDS.getMask() | MapperFeature.AUTO_DETECT_GETTERS.getMask() | MapperFeature.AUTO_DETECT_IS_GETTERS.getMask() | MapperFeature.AUTO_DETECT_SETTERS.getMask() | MapperFeature.AUTO_DETECT_CREATORS.getMask();
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
/*     */   protected final SimpleMixInResolver _mixIns;
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
/*     */   protected final SubtypeResolver _subtypeResolver;
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
/*     */   protected final PropertyName _rootName;
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
/*     */   protected final Class<?> _view;
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
/*     */   protected final ContextAttributes _attributes;
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
/*     */   protected final RootNameLookup _rootNames;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ConfigOverrides _configOverrides;
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
/*     */   protected MapperConfigBase(BaseSettings base, SubtypeResolver str, SimpleMixInResolver mixins, RootNameLookup rootNames, ConfigOverrides configOverrides) {
/* 125 */     super(base, DEFAULT_MAPPER_FEATURES);
/* 126 */     this._mixIns = mixins;
/* 127 */     this._subtypeResolver = str;
/* 128 */     this._rootNames = rootNames;
/* 129 */     this._rootName = null;
/* 130 */     this._view = null;
/*     */     
/* 132 */     this._attributes = ContextAttributes.getEmpty();
/* 133 */     this._configOverrides = configOverrides;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, SimpleMixInResolver mixins, RootNameLookup rootNames, ConfigOverrides configOverrides) {
/* 143 */     super(src);
/* 144 */     this._mixIns = mixins;
/* 145 */     this._subtypeResolver = src._subtypeResolver;
/* 146 */     this._rootNames = rootNames;
/* 147 */     this._rootName = src._rootName;
/* 148 */     this._view = src._view;
/* 149 */     this._attributes = src._attributes;
/* 150 */     this._configOverrides = configOverrides;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src) {
/* 159 */     super(src);
/* 160 */     this._mixIns = src._mixIns;
/* 161 */     this._subtypeResolver = src._subtypeResolver;
/* 162 */     this._rootNames = src._rootNames;
/* 163 */     this._rootName = src._rootName;
/* 164 */     this._view = src._view;
/* 165 */     this._attributes = src._attributes;
/* 166 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, BaseSettings base) {
/* 171 */     super(src, base);
/* 172 */     this._mixIns = src._mixIns;
/* 173 */     this._subtypeResolver = src._subtypeResolver;
/* 174 */     this._rootNames = src._rootNames;
/* 175 */     this._rootName = src._rootName;
/* 176 */     this._view = src._view;
/* 177 */     this._attributes = src._attributes;
/* 178 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, int mapperFeatures) {
/* 183 */     super(src, mapperFeatures);
/* 184 */     this._mixIns = src._mixIns;
/* 185 */     this._subtypeResolver = src._subtypeResolver;
/* 186 */     this._rootNames = src._rootNames;
/* 187 */     this._rootName = src._rootName;
/* 188 */     this._view = src._view;
/* 189 */     this._attributes = src._attributes;
/* 190 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, SubtypeResolver str) {
/* 194 */     super(src);
/* 195 */     this._mixIns = src._mixIns;
/* 196 */     this._subtypeResolver = str;
/* 197 */     this._rootNames = src._rootNames;
/* 198 */     this._rootName = src._rootName;
/* 199 */     this._view = src._view;
/* 200 */     this._attributes = src._attributes;
/* 201 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, PropertyName rootName) {
/* 205 */     super(src);
/* 206 */     this._mixIns = src._mixIns;
/* 207 */     this._subtypeResolver = src._subtypeResolver;
/* 208 */     this._rootNames = src._rootNames;
/* 209 */     this._rootName = rootName;
/* 210 */     this._view = src._view;
/* 211 */     this._attributes = src._attributes;
/* 212 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, Class<?> view) {
/* 217 */     super(src);
/* 218 */     this._mixIns = src._mixIns;
/* 219 */     this._subtypeResolver = src._subtypeResolver;
/* 220 */     this._rootNames = src._rootNames;
/* 221 */     this._rootName = src._rootName;
/* 222 */     this._view = view;
/* 223 */     this._attributes = src._attributes;
/* 224 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, SimpleMixInResolver mixins) {
/* 232 */     super(src);
/* 233 */     this._mixIns = mixins;
/* 234 */     this._subtypeResolver = src._subtypeResolver;
/* 235 */     this._rootNames = src._rootNames;
/* 236 */     this._rootName = src._rootName;
/* 237 */     this._view = src._view;
/* 238 */     this._attributes = src._attributes;
/* 239 */     this._configOverrides = src._configOverrides;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> src, ContextAttributes attr) {
/* 247 */     super(src);
/* 248 */     this._mixIns = src._mixIns;
/* 249 */     this._subtypeResolver = src._subtypeResolver;
/* 250 */     this._rootNames = src._rootNames;
/* 251 */     this._rootName = src._rootName;
/* 252 */     this._view = src._view;
/* 253 */     this._attributes = attr;
/* 254 */     this._configOverrides = src._configOverrides;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T with(MapperFeature... features) {
/* 287 */     int newMapperFlags = this._mapperFeatures;
/* 288 */     for (MapperFeature f : features) {
/* 289 */       newMapperFlags |= f.getMask();
/*     */     }
/* 291 */     if (newMapperFlags == this._mapperFeatures) {
/* 292 */       return (T)this;
/*     */     }
/* 294 */     return _withMapperFeatures(newMapperFlags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T without(MapperFeature... features) {
/* 305 */     int newMapperFlags = this._mapperFeatures;
/* 306 */     for (MapperFeature f : features) {
/* 307 */       newMapperFlags &= f.getMask() ^ 0xFFFFFFFF;
/*     */     }
/* 309 */     if (newMapperFlags == this._mapperFeatures) {
/* 310 */       return (T)this;
/*     */     }
/* 312 */     return _withMapperFeatures(newMapperFlags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T with(MapperFeature feature, boolean state) {
/*     */     int newMapperFlags;
/* 320 */     if (state) {
/* 321 */       newMapperFlags = this._mapperFeatures | feature.getMask();
/*     */     } else {
/* 323 */       newMapperFlags = this._mapperFeatures & (feature.getMask() ^ 0xFFFFFFFF);
/*     */     } 
/* 325 */     if (newMapperFlags == this._mapperFeatures) {
/* 326 */       return (T)this;
/*     */     }
/* 328 */     return _withMapperFeatures(newMapperFlags);
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
/*     */   public final T with(AnnotationIntrospector ai) {
/* 345 */     return _withBase(this._base.withAnnotationIntrospector(ai));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T withAppendedAnnotationIntrospector(AnnotationIntrospector ai) {
/* 353 */     return _withBase(this._base.withAppendedAnnotationIntrospector(ai));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T withInsertedAnnotationIntrospector(AnnotationIntrospector ai) {
/* 361 */     return _withBase(this._base.withInsertedAnnotationIntrospector(ai));
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
/*     */   public final T with(ClassIntrospector ci) {
/* 373 */     return _withBase(this._base.withClassIntrospector(ci));
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
/*     */   public T withAttributes(Map<?, ?> attributes) {
/* 397 */     return with(getAttributes().withSharedAttributes(attributes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T withAttribute(Object key, Object value) {
/* 407 */     return with(getAttributes().withSharedAttribute(key, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T withoutAttribute(Object key) {
/* 417 */     return with(getAttributes().withoutSharedAttribute(key));
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
/*     */   public final T with(TypeFactory tf) {
/* 432 */     return _withBase(this._base.withTypeFactory(tf));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T with(TypeResolverBuilder<?> trb) {
/* 440 */     return _withBase(this._base.withTypeResolverBuilder(trb));
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
/*     */   public final T with(PropertyNamingStrategy pns) {
/* 452 */     return _withBase(this._base.withPropertyNamingStrategy(pns));
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
/*     */   public final T with(HandlerInstantiator hi) {
/* 464 */     return _withBase(this._base.withHandlerInstantiator(hi));
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
/*     */   public final T with(Base64Variant base64) {
/* 478 */     return _withBase(this._base.with(base64));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T with(DateFormat df) {
/* 489 */     return _withBase(this._base.withDateFormat(df));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T with(Locale l) {
/* 497 */     return _withBase(this._base.with(l));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T with(TimeZone tz) {
/* 505 */     return _withBase(this._base.with(tz));
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
/*     */   public T withRootName(String rootName) {
/* 527 */     if (rootName == null) {
/* 528 */       return withRootName((PropertyName)null);
/*     */     }
/* 530 */     return withRootName(PropertyName.construct(rootName));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SubtypeResolver getSubtypeResolver() {
/* 562 */     return this._subtypeResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final String getRootName() {
/* 570 */     return (this._rootName == null) ? null : this._rootName.getSimpleName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PropertyName getFullRootName() {
/* 577 */     return this._rootName;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Class<?> getActiveView() {
/* 582 */     return this._view;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ContextAttributes getAttributes() {
/* 587 */     return this._attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ConfigOverride getConfigOverride(Class<?> type) {
/* 598 */     ConfigOverride override = this._configOverrides.findOverride(type);
/* 599 */     return (override == null) ? EMPTY_OVERRIDE : override;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ConfigOverride findConfigOverride(Class<?> type) {
/* 604 */     return this._configOverrides.findOverride(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public final JsonInclude.Value getDefaultPropertyInclusion() {
/* 609 */     return this._configOverrides.getDefaultInclusion();
/*     */   }
/*     */ 
/*     */   
/*     */   public final JsonInclude.Value getDefaultPropertyInclusion(Class<?> baseType) {
/* 614 */     JsonInclude.Value v = getConfigOverride(baseType).getInclude();
/* 615 */     JsonInclude.Value def = getDefaultPropertyInclusion();
/* 616 */     if (def == null) {
/* 617 */       return v;
/*     */     }
/* 619 */     return def.withOverrides(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final JsonInclude.Value getDefaultInclusion(Class<?> baseType, Class<?> propertyType) {
/* 625 */     JsonInclude.Value v = getConfigOverride(propertyType).getIncludeAsProperty();
/* 626 */     JsonInclude.Value def = getDefaultPropertyInclusion(baseType);
/* 627 */     if (def == null) {
/* 628 */       return v;
/*     */     }
/* 630 */     return def.withOverrides(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final JsonFormat.Value getDefaultPropertyFormat(Class<?> type) {
/* 635 */     ConfigOverride overrides = this._configOverrides.findOverride(type);
/* 636 */     if (overrides != null) {
/* 637 */       JsonFormat.Value v = overrides.getFormat();
/* 638 */       if (v != null) {
/* 639 */         return v;
/*     */       }
/*     */     } 
/* 642 */     return EMPTY_FORMAT;
/*     */   }
/*     */ 
/*     */   
/*     */   public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> type) {
/* 647 */     ConfigOverride overrides = this._configOverrides.findOverride(type);
/* 648 */     if (overrides != null) {
/* 649 */       JsonIgnoreProperties.Value v = overrides.getIgnorals();
/* 650 */       if (v != null) {
/* 651 */         return v;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 656 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> baseType, AnnotatedClass actualClass) {
/* 663 */     AnnotationIntrospector intr = getAnnotationIntrospector();
/* 664 */     JsonIgnoreProperties.Value base = (intr == null) ? null : intr.findPropertyIgnorals((Annotated)actualClass);
/*     */     
/* 666 */     JsonIgnoreProperties.Value overrides = getDefaultPropertyIgnorals(baseType);
/* 667 */     return JsonIgnoreProperties.Value.merge(base, overrides);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final VisibilityChecker<?> getDefaultVisibilityChecker() {
/* 673 */     VisibilityChecker<?> vchecker = this._configOverrides.getDefaultVisibility();
/*     */     
/* 675 */     if ((this._mapperFeatures & AUTO_DETECT_MASK) != 0) {
/* 676 */       if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
/* 677 */         vchecker = vchecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
/*     */       }
/* 679 */       if (!isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
/* 680 */         vchecker = vchecker.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
/*     */       }
/* 682 */       if (!isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
/* 683 */         vchecker = vchecker.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
/*     */       }
/* 685 */       if (!isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
/* 686 */         vchecker = vchecker.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
/*     */       }
/* 688 */       if (!isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
/* 689 */         vchecker = vchecker.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
/*     */       }
/*     */     } 
/* 692 */     return vchecker;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final VisibilityChecker<?> getDefaultVisibilityChecker(Class<?> baseType, AnnotatedClass actualClass) {
/* 698 */     VisibilityChecker<?> vc = getDefaultVisibilityChecker();
/* 699 */     AnnotationIntrospector intr = getAnnotationIntrospector();
/* 700 */     if (intr != null) {
/* 701 */       vc = intr.findAutoDetectVisibility(actualClass, vc);
/*     */     }
/* 703 */     ConfigOverride overrides = this._configOverrides.findOverride(baseType);
/* 704 */     if (overrides != null) {
/* 705 */       vc = vc.withOverrides(overrides.getVisibility());
/*     */     }
/* 707 */     return vc;
/*     */   }
/*     */ 
/*     */   
/*     */   public final JsonSetter.Value getDefaultSetterInfo() {
/* 712 */     return this._configOverrides.getDefaultSetterInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getDefaultMergeable() {
/* 717 */     return this._configOverrides.getDefaultMergeable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getDefaultMergeable(Class<?> baseType) {
/* 723 */     ConfigOverride cfg = this._configOverrides.findOverride(baseType);
/* 724 */     if (cfg != null) {
/* 725 */       Boolean b = cfg.getMergeable();
/* 726 */       if (b != null) {
/* 727 */         return b;
/*     */       }
/*     */     } 
/* 730 */     return this._configOverrides.getDefaultMergeable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyName findRootName(JavaType rootType) {
/* 741 */     if (this._rootName != null) {
/* 742 */       return this._rootName;
/*     */     }
/* 744 */     return this._rootNames.findRootName(rootType, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyName findRootName(Class<?> rawRootType) {
/* 749 */     if (this._rootName != null) {
/* 750 */       return this._rootName;
/*     */     }
/* 752 */     return this._rootNames.findRootName(rawRootType, this);
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
/*     */   public final Class<?> findMixInClassFor(Class<?> cls) {
/* 767 */     return this._mixIns.findMixInClassFor(cls);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassIntrospector.MixInResolver copy() {
/* 773 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int mixInCount() {
/* 781 */     return this._mixIns.localSize();
/*     */   }
/*     */   
/*     */   protected abstract T _withBase(BaseSettings paramBaseSettings);
/*     */   
/*     */   protected abstract T _withMapperFeatures(int paramInt);
/*     */   
/*     */   public abstract T with(ContextAttributes paramContextAttributes);
/*     */   
/*     */   public abstract T withRootName(PropertyName paramPropertyName);
/*     */   
/*     */   public abstract T with(SubtypeResolver paramSubtypeResolver);
/*     */   
/*     */   public abstract T withView(Class<?> paramClass);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\MapperConfigBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */