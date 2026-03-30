/*     */ package software.bernie.shadowed.fasterxml.jackson.annotation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
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
/*     */ @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ @JacksonAnnotation
/*     */ public @interface JsonFormat
/*     */ {
/*     */   public static final String DEFAULT_LOCALE = "##default";
/*     */   public static final String DEFAULT_TIMEZONE = "##default";
/*     */   
/*     */   String pattern() default "";
/*     */   
/*     */   Shape shape() default Shape.ANY;
/*     */   
/*     */   String locale() default "##default";
/*     */   
/*     */   String timezone() default "##default";
/*     */   
/*     */   OptBoolean lenient() default OptBoolean.DEFAULT;
/*     */   
/*     */   Feature[] with() default {};
/*     */   
/*     */   Feature[] without() default {};
/*     */   
/*     */   public enum Shape
/*     */   {
/* 156 */     ANY,
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
/* 167 */     NATURAL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     SCALAR,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     ARRAY,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     OBJECT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     NUMBER,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     NUMBER_FLOAT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     NUMBER_INT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     STRING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     BOOLEAN;
/*     */ 
/*     */     
/*     */     public boolean isNumeric() {
/* 216 */       return (this == NUMBER || this == NUMBER_INT || this == NUMBER_FLOAT);
/*     */     }
/*     */     
/*     */     public boolean isStructured() {
/* 220 */       return (this == OBJECT || this == ARRAY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Feature
/*     */   {
/* 243 */     ACCEPT_SINGLE_VALUE_AS_ARRAY,
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
/* 254 */     ACCEPT_CASE_INSENSITIVE_PROPERTIES,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     WRITE_DATES_WITH_ZONE_ID,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     WRITE_SORTED_MAP_ENTRIES,
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
/* 295 */     ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Features
/*     */   {
/*     */     private final int _enabled;
/*     */ 
/*     */     
/*     */     private final int _disabled;
/*     */ 
/*     */     
/* 308 */     private static final Features EMPTY = new Features(0, 0);
/*     */     
/*     */     private Features(int e, int d) {
/* 311 */       this._enabled = e;
/* 312 */       this._disabled = d;
/*     */     }
/*     */     
/*     */     public static Features empty() {
/* 316 */       return EMPTY;
/*     */     }
/*     */     
/*     */     public static Features construct(JsonFormat f) {
/* 320 */       return construct(f.with(), f.without());
/*     */     }
/*     */ 
/*     */     
/*     */     public static Features construct(JsonFormat.Feature[] enabled, JsonFormat.Feature[] disabled) {
/* 325 */       int e = 0;
/* 326 */       for (JsonFormat.Feature f : enabled) {
/* 327 */         e |= 1 << f.ordinal();
/*     */       }
/* 329 */       int d = 0;
/* 330 */       for (JsonFormat.Feature f : disabled) {
/* 331 */         d |= 1 << f.ordinal();
/*     */       }
/* 333 */       return new Features(e, d);
/*     */     }
/*     */ 
/*     */     
/*     */     public Features withOverrides(Features overrides) {
/* 338 */       if (overrides == null) {
/* 339 */         return this;
/*     */       }
/* 341 */       int overrideD = overrides._disabled;
/* 342 */       int overrideE = overrides._enabled;
/* 343 */       if (overrideD == 0 && overrideE == 0) {
/* 344 */         return this;
/*     */       }
/* 346 */       if (this._enabled == 0 && this._disabled == 0) {
/* 347 */         return overrides;
/*     */       }
/*     */       
/* 350 */       int newE = this._enabled & (overrideD ^ 0xFFFFFFFF) | overrideE;
/* 351 */       int newD = this._disabled & (overrideE ^ 0xFFFFFFFF) | overrideD;
/*     */ 
/*     */       
/* 354 */       if (newE == this._enabled && newD == this._disabled) {
/* 355 */         return this;
/*     */       }
/*     */       
/* 358 */       return new Features(newE, newD);
/*     */     }
/*     */     
/*     */     public Features with(JsonFormat.Feature... features) {
/* 362 */       int e = this._enabled;
/* 363 */       for (JsonFormat.Feature f : features) {
/* 364 */         e |= 1 << f.ordinal();
/*     */       }
/* 366 */       return (e == this._enabled) ? this : new Features(e, this._disabled);
/*     */     }
/*     */     
/*     */     public Features without(JsonFormat.Feature... features) {
/* 370 */       int d = this._disabled;
/* 371 */       for (JsonFormat.Feature f : features) {
/* 372 */         d |= 1 << f.ordinal();
/*     */       }
/* 374 */       return (d == this._disabled) ? this : new Features(this._enabled, d);
/*     */     }
/*     */     
/*     */     public Boolean get(JsonFormat.Feature f) {
/* 378 */       int mask = 1 << f.ordinal();
/* 379 */       if ((this._disabled & mask) != 0) {
/* 380 */         return Boolean.FALSE;
/*     */       }
/* 382 */       if ((this._enabled & mask) != 0) {
/* 383 */         return Boolean.TRUE;
/*     */       }
/* 385 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 390 */       return this._disabled + this._enabled;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 395 */       if (o == this) return true; 
/* 396 */       if (o == null) return false; 
/* 397 */       if (o.getClass() != getClass()) return false; 
/* 398 */       Features other = (Features)o;
/* 399 */       return (other._enabled == this._enabled && other._disabled == this._disabled);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Value
/*     */     implements JacksonAnnotationValue<JsonFormat>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/* 413 */     private static final Value EMPTY = new Value();
/*     */ 
/*     */     
/*     */     private final String _pattern;
/*     */ 
/*     */     
/*     */     private final JsonFormat.Shape _shape;
/*     */ 
/*     */     
/*     */     private final Locale _locale;
/*     */ 
/*     */     
/*     */     private final String _timezoneStr;
/*     */     
/*     */     private final Boolean _lenient;
/*     */     
/*     */     private final JsonFormat.Features _features;
/*     */     
/*     */     private transient TimeZone _timezone;
/*     */ 
/*     */     
/*     */     public Value() {
/* 435 */       this("", JsonFormat.Shape.ANY, "", "", JsonFormat.Features.empty(), (Boolean)null);
/*     */     }
/*     */     
/*     */     public Value(JsonFormat ann) {
/* 439 */       this(ann.pattern(), ann.shape(), ann.locale(), ann.timezone(), JsonFormat.Features.construct(ann), ann.lenient().asBoolean());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(String p, JsonFormat.Shape sh, String localeStr, String tzStr, JsonFormat.Features f, Boolean lenient) {
/* 449 */       this(p, sh, (localeStr == null || localeStr.length() == 0 || "##default".equals(localeStr)) ? null : new Locale(localeStr), (tzStr == null || tzStr.length() == 0 || "##default".equals(tzStr)) ? null : tzStr, null, f, lenient);
/*     */     }
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
/*     */     public Value(String p, JsonFormat.Shape sh, Locale l, TimeZone tz, JsonFormat.Features f, Boolean lenient) {
/* 463 */       this._pattern = p;
/* 464 */       this._shape = (sh == null) ? JsonFormat.Shape.ANY : sh;
/* 465 */       this._locale = l;
/* 466 */       this._timezone = tz;
/* 467 */       this._timezoneStr = null;
/* 468 */       this._features = (f == null) ? JsonFormat.Features.empty() : f;
/* 469 */       this._lenient = lenient;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(String p, JsonFormat.Shape sh, Locale l, String tzStr, TimeZone tz, JsonFormat.Features f, Boolean lenient) {
/* 478 */       this._pattern = p;
/* 479 */       this._shape = (sh == null) ? JsonFormat.Shape.ANY : sh;
/* 480 */       this._locale = l;
/* 481 */       this._timezone = tz;
/* 482 */       this._timezoneStr = tzStr;
/* 483 */       this._features = (f == null) ? JsonFormat.Features.empty() : f;
/* 484 */       this._lenient = lenient;
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Value(String p, JsonFormat.Shape sh, Locale l, String tzStr, TimeZone tz, JsonFormat.Features f) {
/* 489 */       this(p, sh, l, tzStr, tz, f, null);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Value(String p, JsonFormat.Shape sh, String localeStr, String tzStr, JsonFormat.Features f) {
/* 494 */       this(p, sh, localeStr, tzStr, f, (Boolean)null);
/*     */     }
/*     */     @Deprecated
/*     */     public Value(String p, JsonFormat.Shape sh, Locale l, TimeZone tz, JsonFormat.Features f) {
/* 498 */       this(p, sh, l, tz, f, (Boolean)null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final Value empty() {
/* 505 */       return EMPTY;
/*     */     }
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
/*     */     public static Value merge(Value base, Value overrides) {
/* 521 */       return (base == null) ? overrides : base.withOverrides(overrides);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value mergeAll(Value... values) {
/* 530 */       Value result = null;
/* 531 */       for (Value curr : values) {
/* 532 */         if (curr != null) {
/* 533 */           result = (result == null) ? curr : result.withOverrides(curr);
/*     */         }
/*     */       } 
/* 536 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final Value from(JsonFormat ann) {
/* 543 */       return (ann == null) ? EMPTY : new Value(ann);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final Value withOverrides(Value overrides) {
/*     */       TimeZone tz;
/* 550 */       if (overrides == null || overrides == EMPTY || overrides == this) {
/* 551 */         return this;
/*     */       }
/* 553 */       if (this == EMPTY) {
/* 554 */         return overrides;
/*     */       }
/* 556 */       String p = overrides._pattern;
/* 557 */       if (p == null || p.isEmpty()) {
/* 558 */         p = this._pattern;
/*     */       }
/* 560 */       JsonFormat.Shape sh = overrides._shape;
/* 561 */       if (sh == JsonFormat.Shape.ANY) {
/* 562 */         sh = this._shape;
/*     */       }
/* 564 */       Locale l = overrides._locale;
/* 565 */       if (l == null) {
/* 566 */         l = this._locale;
/*     */       }
/* 568 */       JsonFormat.Features f = this._features;
/* 569 */       if (f == null) {
/* 570 */         f = overrides._features;
/*     */       } else {
/* 572 */         f = f.withOverrides(overrides._features);
/*     */       } 
/* 574 */       Boolean lenient = overrides._lenient;
/* 575 */       if (lenient == null) {
/* 576 */         lenient = this._lenient;
/*     */       }
/*     */ 
/*     */       
/* 580 */       String tzStr = overrides._timezoneStr;
/*     */ 
/*     */       
/* 583 */       if (tzStr == null || tzStr.isEmpty()) {
/* 584 */         tzStr = this._timezoneStr;
/* 585 */         tz = this._timezone;
/*     */       } else {
/* 587 */         tz = overrides._timezone;
/*     */       } 
/* 589 */       return new Value(p, sh, l, tzStr, tz, f, lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value forPattern(String p) {
/* 596 */       return new Value(p, null, null, null, null, JsonFormat.Features.empty(), null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value forShape(JsonFormat.Shape sh) {
/* 603 */       return new Value(null, sh, null, null, null, JsonFormat.Features.empty(), null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value forLeniency(boolean lenient) {
/* 610 */       return new Value(null, null, null, null, null, JsonFormat.Features.empty(), Boolean.valueOf(lenient));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withPattern(String p) {
/* 618 */       return new Value(p, this._shape, this._locale, this._timezoneStr, this._timezone, this._features, this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withShape(JsonFormat.Shape s) {
/* 626 */       if (s == this._shape) {
/* 627 */         return this;
/*     */       }
/* 629 */       return new Value(this._pattern, s, this._locale, this._timezoneStr, this._timezone, this._features, this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withLocale(Locale l) {
/* 637 */       return new Value(this._pattern, this._shape, l, this._timezoneStr, this._timezone, this._features, this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withTimeZone(TimeZone tz) {
/* 645 */       return new Value(this._pattern, this._shape, this._locale, null, tz, this._features, this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withLenient(Boolean lenient) {
/* 653 */       if (lenient == this._lenient) {
/* 654 */         return this;
/*     */       }
/* 656 */       return new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this._timezone, this._features, lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withFeature(JsonFormat.Feature f) {
/* 664 */       JsonFormat.Features newFeats = this._features.with(new JsonFormat.Feature[] { f });
/* 665 */       return (newFeats == this._features) ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this._timezone, newFeats, this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withoutFeature(JsonFormat.Feature f) {
/* 674 */       JsonFormat.Features newFeats = this._features.without(new JsonFormat.Feature[] { f });
/* 675 */       return (newFeats == this._features) ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this._timezone, newFeats, this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<JsonFormat> valueFor() {
/* 682 */       return JsonFormat.class;
/*     */     }
/*     */     
/* 685 */     public String getPattern() { return this._pattern; }
/* 686 */     public JsonFormat.Shape getShape() { return this._shape; } public Locale getLocale() {
/* 687 */       return this._locale;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean getLenient() {
/* 693 */       return this._lenient;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isLenient() {
/* 699 */       return Boolean.TRUE.equals(this._lenient);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String timeZoneAsString() {
/* 710 */       if (this._timezone != null) {
/* 711 */         return this._timezone.getID();
/*     */       }
/* 713 */       return this._timezoneStr;
/*     */     }
/*     */     
/*     */     public TimeZone getTimeZone() {
/* 717 */       TimeZone tz = this._timezone;
/* 718 */       if (tz == null) {
/* 719 */         if (this._timezoneStr == null) {
/* 720 */           return null;
/*     */         }
/* 722 */         tz = TimeZone.getTimeZone(this._timezoneStr);
/* 723 */         this._timezone = tz;
/*     */       } 
/* 725 */       return tz;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasShape() {
/* 731 */       return (this._shape != JsonFormat.Shape.ANY);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasPattern() {
/* 737 */       return (this._pattern != null && this._pattern.length() > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasLocale() {
/* 743 */       return (this._locale != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasTimeZone() {
/* 749 */       return (this._timezone != null || (this._timezoneStr != null && !this._timezoneStr.isEmpty()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasLenient() {
/* 760 */       return (this._lenient != null);
/*     */     }
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
/*     */     public Boolean getFeature(JsonFormat.Feature f) {
/* 773 */       return this._features.get(f);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonFormat.Features getFeatures() {
/* 782 */       return this._features;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 788 */       return String.format("JsonFormat.Value(pattern=%s,shape=%s,lenient=%s,locale=%s,timezone=%s)", new Object[] { this._pattern, this._shape, this._lenient, this._locale, this._timezoneStr });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 794 */       int hash = (this._timezoneStr == null) ? 1 : this._timezoneStr.hashCode();
/* 795 */       if (this._pattern != null) {
/* 796 */         hash ^= this._pattern.hashCode();
/*     */       }
/* 798 */       hash += this._shape.hashCode();
/* 799 */       if (this._lenient != null) {
/* 800 */         hash ^= this._lenient.hashCode();
/*     */       }
/* 802 */       if (this._locale != null) {
/* 803 */         hash += this._locale.hashCode();
/*     */       }
/* 805 */       hash ^= this._features.hashCode();
/* 806 */       return hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 811 */       if (o == this) return true; 
/* 812 */       if (o == null) return false; 
/* 813 */       if (o.getClass() != getClass()) return false; 
/* 814 */       Value other = (Value)o;
/*     */       
/* 816 */       if (this._shape != other._shape || !this._features.equals(other._features))
/*     */       {
/* 818 */         return false;
/*     */       }
/* 820 */       return (_equal(this._lenient, other._lenient) && _equal(this._timezoneStr, other._timezoneStr) && _equal(this._pattern, other._pattern) && _equal(this._timezone, other._timezone) && _equal(this._locale, other._locale));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static <T> boolean _equal(T value1, T value2) {
/* 829 */       if (value1 == null) {
/* 830 */         return (value2 == null);
/*     */       }
/* 832 */       if (value2 == null) {
/* 833 */         return false;
/*     */       }
/* 835 */       return value1.equals(value2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\annotation\JsonFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */