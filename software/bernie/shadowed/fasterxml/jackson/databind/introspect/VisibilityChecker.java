/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonAutoDetect;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.PropertyAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface VisibilityChecker<T extends VisibilityChecker<T>>
/*     */ {
/*     */   T with(JsonAutoDetect paramJsonAutoDetect);
/*     */   
/*     */   T withOverrides(JsonAutoDetect.Value paramValue);
/*     */   
/*     */   T with(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   T withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   T withGetterVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   T withIsGetterVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   T withSetterVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   T withCreatorVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   T withFieldVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   boolean isGetterVisible(Method paramMethod);
/*     */   
/*     */   boolean isGetterVisible(AnnotatedMethod paramAnnotatedMethod);
/*     */   
/*     */   boolean isIsGetterVisible(Method paramMethod);
/*     */   
/*     */   boolean isIsGetterVisible(AnnotatedMethod paramAnnotatedMethod);
/*     */   
/*     */   boolean isSetterVisible(Method paramMethod);
/*     */   
/*     */   boolean isSetterVisible(AnnotatedMethod paramAnnotatedMethod);
/*     */   
/*     */   boolean isCreatorVisible(Member paramMember);
/*     */   
/*     */   boolean isCreatorVisible(AnnotatedMember paramAnnotatedMember);
/*     */   
/*     */   boolean isFieldVisible(Field paramField);
/*     */   
/*     */   boolean isFieldVisible(AnnotatedField paramAnnotatedField);
/*     */   
/*     */   public static class Std
/*     */     implements VisibilityChecker<Std>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 164 */     protected static final Std DEFAULT = new Std(JsonAutoDetect.Visibility.PUBLIC_ONLY, JsonAutoDetect.Visibility.PUBLIC_ONLY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.PUBLIC_ONLY);
/*     */     
/*     */     protected final JsonAutoDetect.Visibility _getterMinLevel;
/*     */     
/*     */     protected final JsonAutoDetect.Visibility _isGetterMinLevel;
/*     */     
/*     */     protected final JsonAutoDetect.Visibility _setterMinLevel;
/*     */     
/*     */     protected final JsonAutoDetect.Visibility _creatorMinLevel;
/*     */     
/*     */     protected final JsonAutoDetect.Visibility _fieldMinLevel;
/*     */ 
/*     */     
/*     */     public static Std defaultInstance() {
/* 178 */       return DEFAULT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(JsonAutoDetect ann) {
/* 189 */       this._getterMinLevel = ann.getterVisibility();
/* 190 */       this._isGetterMinLevel = ann.isGetterVisibility();
/* 191 */       this._setterMinLevel = ann.setterVisibility();
/* 192 */       this._creatorMinLevel = ann.creatorVisibility();
/* 193 */       this._fieldMinLevel = ann.fieldVisibility();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(JsonAutoDetect.Visibility getter, JsonAutoDetect.Visibility isGetter, JsonAutoDetect.Visibility setter, JsonAutoDetect.Visibility creator, JsonAutoDetect.Visibility field) {
/* 202 */       this._getterMinLevel = getter;
/* 203 */       this._isGetterMinLevel = isGetter;
/* 204 */       this._setterMinLevel = setter;
/* 205 */       this._creatorMinLevel = creator;
/* 206 */       this._fieldMinLevel = field;
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
/*     */     public Std(JsonAutoDetect.Visibility v) {
/* 218 */       if (v == JsonAutoDetect.Visibility.DEFAULT) {
/* 219 */         this._getterMinLevel = DEFAULT._getterMinLevel;
/* 220 */         this._isGetterMinLevel = DEFAULT._isGetterMinLevel;
/* 221 */         this._setterMinLevel = DEFAULT._setterMinLevel;
/* 222 */         this._creatorMinLevel = DEFAULT._creatorMinLevel;
/* 223 */         this._fieldMinLevel = DEFAULT._fieldMinLevel;
/*     */       } else {
/* 225 */         this._getterMinLevel = v;
/* 226 */         this._isGetterMinLevel = v;
/* 227 */         this._setterMinLevel = v;
/* 228 */         this._creatorMinLevel = v;
/* 229 */         this._fieldMinLevel = v;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Std construct(JsonAutoDetect.Value vis) {
/* 237 */       return DEFAULT.withOverrides(vis);
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
/*     */     protected Std _with(JsonAutoDetect.Visibility g, JsonAutoDetect.Visibility isG, JsonAutoDetect.Visibility s, JsonAutoDetect.Visibility cr, JsonAutoDetect.Visibility f) {
/* 249 */       if (g == this._getterMinLevel && isG == this._isGetterMinLevel && s == this._setterMinLevel && cr == this._creatorMinLevel && f == this._fieldMinLevel)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 255 */         return this;
/*     */       }
/* 257 */       return new Std(g, isG, s, cr, f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Std with(JsonAutoDetect ann) {
/* 263 */       Std curr = this;
/* 264 */       if (ann != null) {
/* 265 */         return _with(_defaultOrOverride(this._getterMinLevel, ann.getterVisibility()), _defaultOrOverride(this._isGetterMinLevel, ann.isGetterVisibility()), _defaultOrOverride(this._setterMinLevel, ann.setterVisibility()), _defaultOrOverride(this._creatorMinLevel, ann.creatorVisibility()), _defaultOrOverride(this._fieldMinLevel, ann.fieldVisibility()));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       return curr;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Std withOverrides(JsonAutoDetect.Value vis) {
/* 279 */       Std curr = this;
/* 280 */       if (vis != null) {
/* 281 */         return _with(_defaultOrOverride(this._getterMinLevel, vis.getGetterVisibility()), _defaultOrOverride(this._isGetterMinLevel, vis.getIsGetterVisibility()), _defaultOrOverride(this._setterMinLevel, vis.getSetterVisibility()), _defaultOrOverride(this._creatorMinLevel, vis.getCreatorVisibility()), _defaultOrOverride(this._fieldMinLevel, vis.getFieldVisibility()));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       return curr;
/*     */     }
/*     */     
/*     */     private JsonAutoDetect.Visibility _defaultOrOverride(JsonAutoDetect.Visibility defaults, JsonAutoDetect.Visibility override) {
/* 293 */       if (override == JsonAutoDetect.Visibility.DEFAULT) {
/* 294 */         return defaults;
/*     */       }
/* 296 */       return override;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Std with(JsonAutoDetect.Visibility v) {
/* 302 */       if (v == JsonAutoDetect.Visibility.DEFAULT) {
/* 303 */         return DEFAULT;
/*     */       }
/* 305 */       return new Std(v);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Std withVisibility(PropertyAccessor method, JsonAutoDetect.Visibility v) {
/* 311 */       switch (method) {
/*     */         case GETTER:
/* 313 */           return withGetterVisibility(v);
/*     */         case SETTER:
/* 315 */           return withSetterVisibility(v);
/*     */         case CREATOR:
/* 317 */           return withCreatorVisibility(v);
/*     */         case FIELD:
/* 319 */           return withFieldVisibility(v);
/*     */         case IS_GETTER:
/* 321 */           return withIsGetterVisibility(v);
/*     */         case ALL:
/* 323 */           return with(v);
/*     */       } 
/*     */ 
/*     */       
/* 327 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Std withGetterVisibility(JsonAutoDetect.Visibility v) {
/* 333 */       if (v == JsonAutoDetect.Visibility.DEFAULT) v = DEFAULT._getterMinLevel; 
/* 334 */       if (this._getterMinLevel == v) return this; 
/* 335 */       return new Std(v, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
/*     */     }
/*     */ 
/*     */     
/*     */     public Std withIsGetterVisibility(JsonAutoDetect.Visibility v) {
/* 340 */       if (v == JsonAutoDetect.Visibility.DEFAULT) v = DEFAULT._isGetterMinLevel; 
/* 341 */       if (this._isGetterMinLevel == v) return this; 
/* 342 */       return new Std(this._getterMinLevel, v, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
/*     */     }
/*     */ 
/*     */     
/*     */     public Std withSetterVisibility(JsonAutoDetect.Visibility v) {
/* 347 */       if (v == JsonAutoDetect.Visibility.DEFAULT) v = DEFAULT._setterMinLevel; 
/* 348 */       if (this._setterMinLevel == v) return this; 
/* 349 */       return new Std(this._getterMinLevel, this._isGetterMinLevel, v, this._creatorMinLevel, this._fieldMinLevel);
/*     */     }
/*     */ 
/*     */     
/*     */     public Std withCreatorVisibility(JsonAutoDetect.Visibility v) {
/* 354 */       if (v == JsonAutoDetect.Visibility.DEFAULT) v = DEFAULT._creatorMinLevel; 
/* 355 */       if (this._creatorMinLevel == v) return this; 
/* 356 */       return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, v, this._fieldMinLevel);
/*     */     }
/*     */ 
/*     */     
/*     */     public Std withFieldVisibility(JsonAutoDetect.Visibility v) {
/* 361 */       if (v == JsonAutoDetect.Visibility.DEFAULT) v = DEFAULT._fieldMinLevel; 
/* 362 */       if (this._fieldMinLevel == v) return this; 
/* 363 */       return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCreatorVisible(Member m) {
/* 374 */       return this._creatorMinLevel.isVisible(m);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCreatorVisible(AnnotatedMember m) {
/* 379 */       return isCreatorVisible(m.getMember());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFieldVisible(Field f) {
/* 384 */       return this._fieldMinLevel.isVisible(f);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFieldVisible(AnnotatedField f) {
/* 389 */       return isFieldVisible(f.getAnnotated());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGetterVisible(Method m) {
/* 394 */       return this._getterMinLevel.isVisible(m);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGetterVisible(AnnotatedMethod m) {
/* 399 */       return isGetterVisible(m.getAnnotated());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isIsGetterVisible(Method m) {
/* 404 */       return this._isGetterMinLevel.isVisible(m);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isIsGetterVisible(AnnotatedMethod m) {
/* 409 */       return isIsGetterVisible(m.getAnnotated());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSetterVisible(Method m) {
/* 414 */       return this._setterMinLevel.isVisible(m);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSetterVisible(AnnotatedMethod m) {
/* 419 */       return isSetterVisible(m.getAnnotated());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 430 */       return String.format("[Visibility: getter=%s,isGetter=%s,setter=%s,creator=%s,field=%s]", new Object[] { this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\VisibilityChecker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */