/*     */ package software.bernie.shadowed.fasterxml.jackson.annotation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ @JacksonAnnotation
/*     */ public @interface JsonIgnoreProperties
/*     */ {
/*     */   String[] value() default {};
/*     */   
/*     */   boolean ignoreUnknown() default false;
/*     */   
/*     */   boolean allowGetters() default false;
/*     */   
/*     */   boolean allowSetters() default false;
/*     */   
/*     */   public static class Value
/*     */     implements JacksonAnnotationValue<JsonIgnoreProperties>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 109 */     protected static final Value EMPTY = new Value(Collections.emptySet(), false, false, false, true);
/*     */ 
/*     */     
/*     */     protected final Set<String> _ignored;
/*     */ 
/*     */     
/*     */     protected final boolean _ignoreUnknown;
/*     */ 
/*     */     
/*     */     protected final boolean _allowGetters;
/*     */ 
/*     */     
/*     */     protected final boolean _allowSetters;
/*     */     
/*     */     protected final boolean _merge;
/*     */ 
/*     */     
/*     */     protected Value(Set<String> ignored, boolean ignoreUnknown, boolean allowGetters, boolean allowSetters, boolean merge) {
/* 127 */       if (ignored == null) {
/* 128 */         this._ignored = Collections.emptySet();
/*     */       } else {
/* 130 */         this._ignored = ignored;
/*     */       } 
/* 132 */       this._ignoreUnknown = ignoreUnknown;
/* 133 */       this._allowGetters = allowGetters;
/* 134 */       this._allowSetters = allowSetters;
/* 135 */       this._merge = merge;
/*     */     }
/*     */     
/*     */     public static Value from(JsonIgnoreProperties src) {
/* 139 */       if (src == null) {
/* 140 */         return EMPTY;
/*     */       }
/* 142 */       return construct(_asSet(src.value()), src.ignoreUnknown(), src.allowGetters(), src.allowSetters(), false);
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
/*     */ 
/*     */     
/*     */     public static Value construct(Set<String> ignored, boolean ignoreUnknown, boolean allowGetters, boolean allowSetters, boolean merge) {
/* 160 */       if (_empty(ignored, ignoreUnknown, allowGetters, allowSetters, merge)) {
/* 161 */         return EMPTY;
/*     */       }
/* 163 */       return new Value(ignored, ignoreUnknown, allowGetters, allowSetters, merge);
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value empty() {
/* 183 */       return EMPTY;
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
/*     */     public static Value merge(Value base, Value overrides) {
/* 197 */       return (base == null) ? overrides : base.withOverrides(overrides);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value mergeAll(Value... values) {
/* 206 */       Value result = null;
/* 207 */       for (Value curr : values) {
/* 208 */         if (curr != null) {
/* 209 */           result = (result == null) ? curr : result.withOverrides(curr);
/*     */         }
/*     */       } 
/* 212 */       return result;
/*     */     }
/*     */     
/*     */     public static Value forIgnoredProperties(Set<String> propNames) {
/* 216 */       return EMPTY.withIgnored(propNames);
/*     */     }
/*     */     
/*     */     public static Value forIgnoredProperties(String... propNames) {
/* 220 */       if (propNames.length == 0) {
/* 221 */         return EMPTY;
/*     */       }
/* 223 */       return EMPTY.withIgnored(_asSet(propNames));
/*     */     }
/*     */     
/*     */     public static Value forIgnoreUnknown(boolean state) {
/* 227 */       return state ? EMPTY.withIgnoreUnknown() : EMPTY.withoutIgnoreUnknown();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withOverrides(Value overrides) {
/* 238 */       if (overrides == null || overrides == EMPTY) {
/* 239 */         return this;
/*     */       }
/*     */ 
/*     */       
/* 243 */       if (!overrides._merge) {
/* 244 */         return overrides;
/*     */       }
/* 246 */       if (_equals(this, overrides)) {
/* 247 */         return this;
/*     */       }
/*     */ 
/*     */       
/* 251 */       Set<String> ignored = _merge(this._ignored, overrides._ignored);
/* 252 */       boolean ignoreUnknown = (this._ignoreUnknown || overrides._ignoreUnknown);
/* 253 */       boolean allowGetters = (this._allowGetters || overrides._allowGetters);
/* 254 */       boolean allowSetters = (this._allowSetters || overrides._allowSetters);
/*     */ 
/*     */       
/* 257 */       return construct(ignored, ignoreUnknown, allowGetters, allowSetters, true);
/*     */     }
/*     */     
/*     */     public Value withIgnored(Set<String> ignored) {
/* 261 */       return construct(ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
/*     */     }
/*     */     
/*     */     public Value withIgnored(String... ignored) {
/* 265 */       return construct(_asSet(ignored), this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
/*     */     }
/*     */     
/*     */     public Value withoutIgnored() {
/* 269 */       return construct(null, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
/*     */     }
/*     */     
/*     */     public Value withIgnoreUnknown() {
/* 273 */       return this._ignoreUnknown ? this : construct(this._ignored, true, this._allowGetters, this._allowSetters, this._merge);
/*     */     }
/*     */     
/*     */     public Value withoutIgnoreUnknown() {
/* 277 */       return !this._ignoreUnknown ? this : construct(this._ignored, false, this._allowGetters, this._allowSetters, this._merge);
/*     */     }
/*     */ 
/*     */     
/*     */     public Value withAllowGetters() {
/* 282 */       return this._allowGetters ? this : construct(this._ignored, this._ignoreUnknown, true, this._allowSetters, this._merge);
/*     */     }
/*     */     
/*     */     public Value withoutAllowGetters() {
/* 286 */       return !this._allowGetters ? this : construct(this._ignored, this._ignoreUnknown, false, this._allowSetters, this._merge);
/*     */     }
/*     */ 
/*     */     
/*     */     public Value withAllowSetters() {
/* 291 */       return this._allowSetters ? this : construct(this._ignored, this._ignoreUnknown, this._allowGetters, true, this._merge);
/*     */     }
/*     */     
/*     */     public Value withoutAllowSetters() {
/* 295 */       return !this._allowSetters ? this : construct(this._ignored, this._ignoreUnknown, this._allowGetters, false, this._merge);
/*     */     }
/*     */ 
/*     */     
/*     */     public Value withMerge() {
/* 300 */       return this._merge ? this : construct(this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public Value withoutMerge() {
/* 305 */       return !this._merge ? this : construct(this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<JsonIgnoreProperties> valueFor() {
/* 311 */       return JsonIgnoreProperties.class;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object readResolve() {
/* 316 */       if (_empty(this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge)) {
/* 317 */         return EMPTY;
/*     */       }
/* 319 */       return this;
/*     */     }
/*     */     
/*     */     public Set<String> getIgnored() {
/* 323 */       return this._ignored;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> findIgnoredForSerialization() {
/* 334 */       if (this._allowGetters) {
/* 335 */         return Collections.emptySet();
/*     */       }
/* 337 */       return this._ignored;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> findIgnoredForDeserialization() {
/* 348 */       if (this._allowSetters) {
/* 349 */         return Collections.emptySet();
/*     */       }
/* 351 */       return this._ignored;
/*     */     }
/*     */     
/*     */     public boolean getIgnoreUnknown() {
/* 355 */       return this._ignoreUnknown;
/*     */     }
/*     */     
/*     */     public boolean getAllowGetters() {
/* 359 */       return this._allowGetters;
/*     */     }
/*     */     
/*     */     public boolean getAllowSetters() {
/* 363 */       return this._allowSetters;
/*     */     }
/*     */     
/*     */     public boolean getMerge() {
/* 367 */       return this._merge;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 372 */       return String.format("JsonIgnoreProperties.Value(ignored=%s,ignoreUnknown=%s,allowGetters=%s,allowSetters=%s,merge=%s)", new Object[] { this._ignored, Boolean.valueOf(this._ignoreUnknown), Boolean.valueOf(this._allowGetters), Boolean.valueOf(this._allowSetters), Boolean.valueOf(this._merge) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 378 */       return this._ignored.size() + (this._ignoreUnknown ? 1 : -3) + (this._allowGetters ? 3 : -7) + (this._allowSetters ? 7 : -11) + (this._merge ? 11 : -13);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 388 */       if (o == this) return true; 
/* 389 */       if (o == null) return false; 
/* 390 */       return (o.getClass() == getClass() && _equals(this, (Value)o));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean _equals(Value a, Value b) {
/* 396 */       return (a._ignoreUnknown == b._ignoreUnknown && a._merge == b._merge && a._allowGetters == b._allowGetters && a._allowSetters == b._allowSetters && a._ignored.equals(b._ignored));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Set<String> _asSet(String[] v) {
/* 406 */       if (v == null || v.length == 0) {
/* 407 */         return Collections.emptySet();
/*     */       }
/* 409 */       Set<String> s = new HashSet<String>(v.length);
/* 410 */       for (String str : v) {
/* 411 */         s.add(str);
/*     */       }
/* 413 */       return s;
/*     */     }
/*     */ 
/*     */     
/*     */     private static Set<String> _merge(Set<String> s1, Set<String> s2) {
/* 418 */       if (s1.isEmpty())
/* 419 */         return s2; 
/* 420 */       if (s2.isEmpty()) {
/* 421 */         return s1;
/*     */       }
/* 423 */       HashSet<String> result = new HashSet<String>(s1.size() + s2.size());
/* 424 */       result.addAll(s1);
/* 425 */       result.addAll(s2);
/* 426 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean _empty(Set<String> ignored, boolean ignoreUnknown, boolean allowGetters, boolean allowSetters, boolean merge) {
/* 432 */       if (ignoreUnknown == EMPTY._ignoreUnknown && allowGetters == EMPTY._allowGetters && allowSetters == EMPTY._allowSetters && merge == EMPTY._merge)
/*     */       {
/*     */ 
/*     */         
/* 436 */         return (ignored == null || ignored.size() == 0);
/*     */       }
/* 438 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\annotation\JsonIgnoreProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */