/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.StdDateFormat;
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
/*     */ public final class BaseSettings
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  29 */   private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ClassIntrospector _classIntrospector;
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected final PropertyNamingStrategy _propertyNamingStrategy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TypeFactory _typeFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TypeResolverBuilder<?> _typeResolverBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final DateFormat _dateFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final HandlerInstantiator _handlerInstantiator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Locale _locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TimeZone _timeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Base64Variant _defaultBase64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings(ClassIntrospector ci, AnnotationIntrospector ai, PropertyNamingStrategy pns, TypeFactory tf, TypeResolverBuilder<?> typer, DateFormat dateFormat, HandlerInstantiator hi, Locale locale, TimeZone tz, Base64Variant defaultBase64) {
/* 138 */     this._classIntrospector = ci;
/* 139 */     this._annotationIntrospector = ai;
/* 140 */     this._propertyNamingStrategy = pns;
/* 141 */     this._typeFactory = tf;
/* 142 */     this._typeResolverBuilder = typer;
/* 143 */     this._dateFormat = dateFormat;
/* 144 */     this._handlerInstantiator = hi;
/* 145 */     this._locale = locale;
/* 146 */     this._timeZone = tz;
/* 147 */     this._defaultBase64 = defaultBase64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withClassIntrospector(ClassIntrospector ci) {
/* 157 */     if (this._classIntrospector == ci) {
/* 158 */       return this;
/*     */     }
/* 160 */     return new BaseSettings(ci, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withAnnotationIntrospector(AnnotationIntrospector ai) {
/* 166 */     if (this._annotationIntrospector == ai) {
/* 167 */       return this;
/*     */     }
/* 169 */     return new BaseSettings(this._classIntrospector, ai, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withInsertedAnnotationIntrospector(AnnotationIntrospector ai) {
/* 175 */     return withAnnotationIntrospector(AnnotationIntrospectorPair.create(ai, this._annotationIntrospector));
/*     */   }
/*     */   
/*     */   public BaseSettings withAppendedAnnotationIntrospector(AnnotationIntrospector ai) {
/* 179 */     return withAnnotationIntrospector(AnnotationIntrospectorPair.create(this._annotationIntrospector, ai));
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
/*     */   public BaseSettings withPropertyNamingStrategy(PropertyNamingStrategy pns) {
/* 193 */     if (this._propertyNamingStrategy == pns) {
/* 194 */       return this;
/*     */     }
/* 196 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, pns, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withTypeFactory(TypeFactory tf) {
/* 202 */     if (this._typeFactory == tf) {
/* 203 */       return this;
/*     */     }
/* 205 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, tf, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withTypeResolverBuilder(TypeResolverBuilder<?> typer) {
/* 211 */     if (this._typeResolverBuilder == typer) {
/* 212 */       return this;
/*     */     }
/* 214 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, typer, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withDateFormat(DateFormat df) {
/* 220 */     if (this._dateFormat == df) {
/* 221 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 225 */     if (df != null && hasExplicitTimeZone()) {
/* 226 */       df = _force(df, this._timeZone);
/*     */     }
/* 228 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, df, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings withHandlerInstantiator(HandlerInstantiator hi) {
/* 234 */     if (this._handlerInstantiator == hi) {
/* 235 */       return this;
/*     */     }
/* 237 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, hi, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings with(Locale l) {
/* 243 */     if (this._locale == l) {
/* 244 */       return this;
/*     */     }
/* 246 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, l, this._timeZone, this._defaultBase64);
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
/*     */   public BaseSettings with(TimeZone tz) {
/* 258 */     if (tz == null) {
/* 259 */       throw new IllegalArgumentException();
/*     */     }
/* 261 */     if (tz == this._timeZone) {
/* 262 */       return this;
/*     */     }
/*     */     
/* 265 */     DateFormat df = _force(this._dateFormat, tz);
/* 266 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, df, this._handlerInstantiator, this._locale, tz, this._defaultBase64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSettings with(Base64Variant base64) {
/* 276 */     if (base64 == this._defaultBase64) {
/* 277 */       return this;
/*     */     }
/* 279 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, base64);
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
/*     */   public ClassIntrospector getClassIntrospector() {
/* 292 */     return this._classIntrospector;
/*     */   }
/*     */   
/*     */   public AnnotationIntrospector getAnnotationIntrospector() {
/* 296 */     return this._annotationIntrospector;
/*     */   }
/*     */   
/*     */   public PropertyNamingStrategy getPropertyNamingStrategy() {
/* 300 */     return this._propertyNamingStrategy;
/*     */   }
/*     */   
/*     */   public TypeFactory getTypeFactory() {
/* 304 */     return this._typeFactory;
/*     */   }
/*     */   
/*     */   public TypeResolverBuilder<?> getTypeResolverBuilder() {
/* 308 */     return this._typeResolverBuilder;
/*     */   }
/*     */   
/*     */   public DateFormat getDateFormat() {
/* 312 */     return this._dateFormat;
/*     */   }
/*     */   
/*     */   public HandlerInstantiator getHandlerInstantiator() {
/* 316 */     return this._handlerInstantiator;
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/* 320 */     return this._locale;
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 324 */     TimeZone tz = this._timeZone;
/* 325 */     return (tz == null) ? DEFAULT_TIMEZONE : tz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasExplicitTimeZone() {
/* 336 */     return (this._timeZone != null);
/*     */   }
/*     */   
/*     */   public Base64Variant getBase64Variant() {
/* 340 */     return this._defaultBase64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DateFormat _force(DateFormat df, TimeZone tz) {
/* 351 */     if (df instanceof StdDateFormat) {
/* 352 */       return (DateFormat)((StdDateFormat)df).withTimeZone(tz);
/*     */     }
/*     */     
/* 355 */     df = (DateFormat)df.clone();
/* 356 */     df.setTimeZone(tz);
/* 357 */     return df;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\BaseSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */