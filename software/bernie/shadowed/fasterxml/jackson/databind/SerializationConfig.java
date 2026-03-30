/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.FormatFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.Instantiatable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.BaseSettings;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ConfigOverrides;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ContextAttributes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfigBase;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RootNameLookup;
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
/*     */ public final class SerializationConfig
/*     */   extends MapperConfigBase<SerializationFeature, SerializationConfig>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  37 */   protected static final PrettyPrinter DEFAULT_PRETTY_PRINTER = (PrettyPrinter)new DefaultPrettyPrinter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final FilterProvider _filterProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final PrettyPrinter _defaultPrettyPrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _serFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _generatorFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _generatorFeaturesToChange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _formatWriteFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _formatWriteFeaturesToChange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationConfig(BaseSettings base, SubtypeResolver str, SimpleMixInResolver mixins, RootNameLookup rootNames, ConfigOverrides configOverrides) {
/* 114 */     super(base, str, mixins, rootNames, configOverrides);
/* 115 */     this._serFeatures = collectFeatureDefaults(SerializationFeature.class);
/* 116 */     this._filterProvider = null;
/* 117 */     this._defaultPrettyPrinter = DEFAULT_PRETTY_PRINTER;
/* 118 */     this._generatorFeatures = 0;
/* 119 */     this._generatorFeaturesToChange = 0;
/* 120 */     this._formatWriteFeatures = 0;
/* 121 */     this._formatWriteFeaturesToChange = 0;
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
/*     */   protected SerializationConfig(SerializationConfig src, SimpleMixInResolver mixins, RootNameLookup rootNames, ConfigOverrides configOverrides) {
/* 133 */     super(src, mixins, rootNames, configOverrides);
/* 134 */     this._serFeatures = src._serFeatures;
/* 135 */     this._filterProvider = src._filterProvider;
/* 136 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 137 */     this._generatorFeatures = src._generatorFeatures;
/* 138 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 139 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 140 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
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
/*     */   private SerializationConfig(SerializationConfig src, SubtypeResolver str) {
/* 152 */     super(src, str);
/* 153 */     this._serFeatures = src._serFeatures;
/* 154 */     this._filterProvider = src._filterProvider;
/* 155 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 156 */     this._generatorFeatures = src._generatorFeatures;
/* 157 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 158 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 159 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SerializationConfig(SerializationConfig src, int mapperFeatures, int serFeatures, int generatorFeatures, int generatorFeatureMask, int formatFeatures, int formatFeaturesMask) {
/* 167 */     super(src, mapperFeatures);
/* 168 */     this._serFeatures = serFeatures;
/* 169 */     this._filterProvider = src._filterProvider;
/* 170 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 171 */     this._generatorFeatures = generatorFeatures;
/* 172 */     this._generatorFeaturesToChange = generatorFeatureMask;
/* 173 */     this._formatWriteFeatures = formatFeatures;
/* 174 */     this._formatWriteFeaturesToChange = formatFeaturesMask;
/*     */   }
/*     */ 
/*     */   
/*     */   private SerializationConfig(SerializationConfig src, BaseSettings base) {
/* 179 */     super(src, base);
/* 180 */     this._serFeatures = src._serFeatures;
/* 181 */     this._filterProvider = src._filterProvider;
/* 182 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 183 */     this._generatorFeatures = src._generatorFeatures;
/* 184 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 185 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 186 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */   
/*     */   private SerializationConfig(SerializationConfig src, FilterProvider filters) {
/* 191 */     super(src);
/* 192 */     this._serFeatures = src._serFeatures;
/* 193 */     this._filterProvider = filters;
/* 194 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 195 */     this._generatorFeatures = src._generatorFeatures;
/* 196 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 197 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 198 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */   
/*     */   private SerializationConfig(SerializationConfig src, Class<?> view) {
/* 203 */     super(src, view);
/* 204 */     this._serFeatures = src._serFeatures;
/* 205 */     this._filterProvider = src._filterProvider;
/* 206 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 207 */     this._generatorFeatures = src._generatorFeatures;
/* 208 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 209 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 210 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */   
/*     */   private SerializationConfig(SerializationConfig src, PropertyName rootName) {
/* 215 */     super(src, rootName);
/* 216 */     this._serFeatures = src._serFeatures;
/* 217 */     this._filterProvider = src._filterProvider;
/* 218 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 219 */     this._generatorFeatures = src._generatorFeatures;
/* 220 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 221 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 222 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializationConfig(SerializationConfig src, ContextAttributes attrs) {
/* 230 */     super(src, attrs);
/* 231 */     this._serFeatures = src._serFeatures;
/* 232 */     this._filterProvider = src._filterProvider;
/* 233 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 234 */     this._generatorFeatures = src._generatorFeatures;
/* 235 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 236 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 237 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializationConfig(SerializationConfig src, SimpleMixInResolver mixins) {
/* 245 */     super(src, mixins);
/* 246 */     this._serFeatures = src._serFeatures;
/* 247 */     this._filterProvider = src._filterProvider;
/* 248 */     this._defaultPrettyPrinter = src._defaultPrettyPrinter;
/* 249 */     this._generatorFeatures = src._generatorFeatures;
/* 250 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 251 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 252 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializationConfig(SerializationConfig src, PrettyPrinter defaultPP) {
/* 260 */     super(src);
/* 261 */     this._serFeatures = src._serFeatures;
/* 262 */     this._filterProvider = src._filterProvider;
/* 263 */     this._defaultPrettyPrinter = defaultPP;
/* 264 */     this._generatorFeatures = src._generatorFeatures;
/* 265 */     this._generatorFeaturesToChange = src._generatorFeaturesToChange;
/* 266 */     this._formatWriteFeatures = src._formatWriteFeatures;
/* 267 */     this._formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SerializationConfig _withBase(BaseSettings newBase) {
/* 278 */     return (this._base == newBase) ? this : new SerializationConfig(this, newBase);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final SerializationConfig _withMapperFeatures(int mapperFeatures) {
/* 283 */     return new SerializationConfig(this, mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationConfig withRootName(PropertyName rootName) {
/* 290 */     if (rootName == null) {
/* 291 */       if (this._rootName == null) {
/* 292 */         return this;
/*     */       }
/* 294 */     } else if (rootName.equals(this._rootName)) {
/* 295 */       return this;
/*     */     } 
/* 297 */     return new SerializationConfig(this, rootName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SerializationConfig with(SubtypeResolver str) {
/* 302 */     return (str == this._subtypeResolver) ? this : new SerializationConfig(this, str);
/*     */   }
/*     */ 
/*     */   
/*     */   public SerializationConfig withView(Class<?> view) {
/* 307 */     return (this._view == view) ? this : new SerializationConfig(this, view);
/*     */   }
/*     */ 
/*     */   
/*     */   public SerializationConfig with(ContextAttributes attrs) {
/* 312 */     return (attrs == this._attributes) ? this : new SerializationConfig(this, attrs);
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
/*     */   public SerializationConfig with(DateFormat df) {
/* 328 */     SerializationConfig cfg = (SerializationConfig)super.with(df);
/*     */     
/* 330 */     if (df == null) {
/* 331 */       return cfg.with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */     }
/* 333 */     return cfg.without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
/*     */   public SerializationConfig with(SerializationFeature feature) {
/* 348 */     int newSerFeatures = this._serFeatures | feature.getMask();
/* 349 */     return (newSerFeatures == this._serFeatures) ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig with(SerializationFeature first, SerializationFeature... features) {
/* 361 */     int newSerFeatures = this._serFeatures | first.getMask();
/* 362 */     for (SerializationFeature f : features) {
/* 363 */       newSerFeatures |= f.getMask();
/*     */     }
/* 365 */     return (newSerFeatures == this._serFeatures) ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig withFeatures(SerializationFeature... features) {
/* 377 */     int newSerFeatures = this._serFeatures;
/* 378 */     for (SerializationFeature f : features) {
/* 379 */       newSerFeatures |= f.getMask();
/*     */     }
/* 381 */     return (newSerFeatures == this._serFeatures) ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig without(SerializationFeature feature) {
/* 393 */     int newSerFeatures = this._serFeatures & (feature.getMask() ^ 0xFFFFFFFF);
/* 394 */     return (newSerFeatures == this._serFeatures) ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig without(SerializationFeature first, SerializationFeature... features) {
/* 406 */     int newSerFeatures = this._serFeatures & (first.getMask() ^ 0xFFFFFFFF);
/* 407 */     for (SerializationFeature f : features) {
/* 408 */       newSerFeatures &= f.getMask() ^ 0xFFFFFFFF;
/*     */     }
/* 410 */     return (newSerFeatures == this._serFeatures) ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig withoutFeatures(SerializationFeature... features) {
/* 422 */     int newSerFeatures = this._serFeatures;
/* 423 */     for (SerializationFeature f : features) {
/* 424 */       newSerFeatures &= f.getMask() ^ 0xFFFFFFFF;
/*     */     }
/* 426 */     return (newSerFeatures == this._serFeatures) ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig with(JsonGenerator.Feature feature) {
/* 445 */     int newSet = this._generatorFeatures | feature.getMask();
/* 446 */     int newMask = this._generatorFeaturesToChange | feature.getMask();
/* 447 */     return (this._generatorFeatures == newSet && this._generatorFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, newSet, newMask, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig withFeatures(JsonGenerator.Feature... features) {
/* 461 */     int newSet = this._generatorFeatures;
/* 462 */     int newMask = this._generatorFeaturesToChange;
/* 463 */     for (JsonGenerator.Feature f : features) {
/* 464 */       int mask = f.getMask();
/* 465 */       newSet |= mask;
/* 466 */       newMask |= mask;
/*     */     } 
/* 468 */     return (this._generatorFeatures == newSet && this._generatorFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, newSet, newMask, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig without(JsonGenerator.Feature feature) {
/* 482 */     int newSet = this._generatorFeatures & (feature.getMask() ^ 0xFFFFFFFF);
/* 483 */     int newMask = this._generatorFeaturesToChange | feature.getMask();
/* 484 */     return (this._generatorFeatures == newSet && this._generatorFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, newSet, newMask, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig withoutFeatures(JsonGenerator.Feature... features) {
/* 498 */     int newSet = this._generatorFeatures;
/* 499 */     int newMask = this._generatorFeaturesToChange;
/* 500 */     for (JsonGenerator.Feature f : features) {
/* 501 */       int mask = f.getMask();
/* 502 */       newSet &= mask ^ 0xFFFFFFFF;
/* 503 */       newMask |= mask;
/*     */     } 
/* 505 */     return (this._generatorFeatures == newSet && this._generatorFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, newSet, newMask, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */   public SerializationConfig with(FormatFeature feature) {
/* 524 */     int newSet = this._formatWriteFeatures | feature.getMask();
/* 525 */     int newMask = this._formatWriteFeaturesToChange | feature.getMask();
/* 526 */     return (this._formatWriteFeatures == newSet && this._formatWriteFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, newSet, newMask);
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
/*     */   public SerializationConfig withFeatures(FormatFeature... features) {
/* 540 */     int newSet = this._formatWriteFeatures;
/* 541 */     int newMask = this._formatWriteFeaturesToChange;
/* 542 */     for (FormatFeature f : features) {
/* 543 */       int mask = f.getMask();
/* 544 */       newSet |= mask;
/* 545 */       newMask |= mask;
/*     */     } 
/* 547 */     return (this._formatWriteFeatures == newSet && this._formatWriteFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, newSet, newMask);
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
/*     */   public SerializationConfig without(FormatFeature feature) {
/* 561 */     int newSet = this._formatWriteFeatures & (feature.getMask() ^ 0xFFFFFFFF);
/* 562 */     int newMask = this._formatWriteFeaturesToChange | feature.getMask();
/* 563 */     return (this._formatWriteFeatures == newSet && this._formatWriteFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, newSet, newMask);
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
/*     */   public SerializationConfig withoutFeatures(FormatFeature... features) {
/* 577 */     int newSet = this._formatWriteFeatures;
/* 578 */     int newMask = this._formatWriteFeaturesToChange;
/* 579 */     for (FormatFeature f : features) {
/* 580 */       int mask = f.getMask();
/* 581 */       newSet &= mask ^ 0xFFFFFFFF;
/* 582 */       newMask |= mask;
/*     */     } 
/* 584 */     return (this._formatWriteFeatures == newSet && this._formatWriteFeaturesToChange == newMask) ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, newSet, newMask);
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
/*     */   public SerializationConfig withFilters(FilterProvider filterProvider) {
/* 597 */     return (filterProvider == this._filterProvider) ? this : new SerializationConfig(this, filterProvider);
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
/*     */   @Deprecated
/*     */   public SerializationConfig withPropertyInclusion(JsonInclude.Value incl) {
/* 610 */     this._configOverrides.setDefaultInclusion(incl);
/* 611 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationConfig withDefaultPrettyPrinter(PrettyPrinter pp) {
/* 618 */     return (this._defaultPrettyPrinter == pp) ? this : new SerializationConfig(this, pp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrettyPrinter constructDefaultPrettyPrinter() {
/* 628 */     PrettyPrinter pp = this._defaultPrettyPrinter;
/* 629 */     if (pp instanceof Instantiatable) {
/* 630 */       pp = (PrettyPrinter)((Instantiatable)pp).createInstance();
/*     */     }
/* 632 */     return pp;
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
/*     */   public void initialize(JsonGenerator g) {
/* 650 */     if (SerializationFeature.INDENT_OUTPUT.enabledIn(this._serFeatures))
/*     */     {
/* 652 */       if (g.getPrettyPrinter() == null) {
/* 653 */         PrettyPrinter pp = constructDefaultPrettyPrinter();
/* 654 */         if (pp != null) {
/* 655 */           g.setPrettyPrinter(pp);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 660 */     boolean useBigDec = SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._serFeatures);
/*     */     
/* 662 */     int mask = this._generatorFeaturesToChange;
/* 663 */     if (mask != 0 || useBigDec) {
/* 664 */       int newFlags = this._generatorFeatures;
/*     */       
/* 666 */       if (useBigDec) {
/* 667 */         int f = JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN.getMask();
/* 668 */         newFlags |= f;
/* 669 */         mask |= f;
/*     */       } 
/* 671 */       g.overrideStdFeatures(newFlags, mask);
/*     */     } 
/* 673 */     if (this._formatWriteFeaturesToChange != 0) {
/* 674 */       g.overrideFormatFeatures(this._formatWriteFeatures, this._formatWriteFeaturesToChange);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonInclude.Include getSerializationInclusion() {
/* 690 */     JsonInclude.Include incl = getDefaultPropertyInclusion().getValueInclusion();
/* 691 */     return (incl == JsonInclude.Include.USE_DEFAULTS) ? JsonInclude.Include.ALWAYS : incl;
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
/*     */   public boolean useRootWrapping() {
/* 703 */     if (this._rootName != null) {
/* 704 */       return !this._rootName.isEmpty();
/*     */     }
/* 706 */     return isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
/*     */   }
/*     */   
/*     */   public final boolean isEnabled(SerializationFeature f) {
/* 710 */     return ((this._serFeatures & f.getMask()) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isEnabled(JsonGenerator.Feature f, JsonFactory factory) {
/* 721 */     int mask = f.getMask();
/* 722 */     if ((this._generatorFeaturesToChange & mask) != 0) {
/* 723 */       return ((this._generatorFeatures & f.getMask()) != 0);
/*     */     }
/* 725 */     return factory.isEnabled(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasSerializationFeatures(int featureMask) {
/* 735 */     return ((this._serFeatures & featureMask) == featureMask);
/*     */   }
/*     */   
/*     */   public final int getSerializationFeatures() {
/* 739 */     return this._serFeatures;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterProvider getFilterProvider() {
/* 749 */     return this._filterProvider;
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
/*     */   public PrettyPrinter getDefaultPrettyPrinter() {
/* 763 */     return this._defaultPrettyPrinter;
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
/*     */   public <T extends BeanDescription> T introspect(JavaType type) {
/* 778 */     return (T)getClassIntrospector().forSerialization(this, type, (ClassIntrospector.MixInResolver)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\SerializationConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */