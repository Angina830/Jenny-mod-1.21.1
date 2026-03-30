/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigOverrides
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected Map<Class<?>, MutableConfigOverride> _overrides;
/*     */   protected JsonInclude.Value _defaultInclusion;
/*     */   protected JsonSetter.Value _defaultSetterInfo;
/*     */   protected VisibilityChecker<?> _visibilityChecker;
/*     */   protected Boolean _defaultMergeable;
/*     */   
/*     */   public ConfigOverrides() {
/*  53 */     this(null, JsonInclude.Value.empty(), JsonSetter.Value.empty(), (VisibilityChecker<?>)VisibilityChecker.Std.defaultInstance(), null);
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
/*     */   protected ConfigOverrides(Map<Class<?>, MutableConfigOverride> overrides, JsonInclude.Value defIncl, JsonSetter.Value defSetter, VisibilityChecker<?> defVisibility, Boolean defMergeable) {
/*  67 */     this._overrides = overrides;
/*  68 */     this._defaultInclusion = defIncl;
/*  69 */     this._defaultSetterInfo = defSetter;
/*  70 */     this._visibilityChecker = defVisibility;
/*  71 */     this._defaultMergeable = defMergeable;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigOverrides copy() {
/*     */     Map<Class<?>, MutableConfigOverride> newOverrides;
/*  77 */     if (this._overrides == null) {
/*  78 */       newOverrides = null;
/*     */     } else {
/*  80 */       newOverrides = _newMap();
/*  81 */       for (Map.Entry<Class<?>, MutableConfigOverride> entry : this._overrides.entrySet()) {
/*  82 */         newOverrides.put(entry.getKey(), ((MutableConfigOverride)entry.getValue()).copy());
/*     */       }
/*     */     } 
/*  85 */     return new ConfigOverrides(newOverrides, this._defaultInclusion, this._defaultSetterInfo, this._visibilityChecker, this._defaultMergeable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfigOverride findOverride(Class<?> type) {
/*  96 */     if (this._overrides == null) {
/*  97 */       return null;
/*     */     }
/*  99 */     return this._overrides.get(type);
/*     */   }
/*     */   
/*     */   public MutableConfigOverride findOrCreateOverride(Class<?> type) {
/* 103 */     if (this._overrides == null) {
/* 104 */       this._overrides = _newMap();
/*     */     }
/* 106 */     MutableConfigOverride override = this._overrides.get(type);
/* 107 */     if (override == null) {
/* 108 */       override = new MutableConfigOverride();
/* 109 */       this._overrides.put(type, override);
/*     */     } 
/* 111 */     return override;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonInclude.Value getDefaultInclusion() {
/* 121 */     return this._defaultInclusion;
/*     */   }
/*     */   
/*     */   public JsonSetter.Value getDefaultSetterInfo() {
/* 125 */     return this._defaultSetterInfo;
/*     */   }
/*     */   
/*     */   public Boolean getDefaultMergeable() {
/* 129 */     return this._defaultMergeable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibilityChecker<?> getDefaultVisibility() {
/* 136 */     return this._visibilityChecker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultInclusion(JsonInclude.Value v) {
/* 143 */     this._defaultInclusion = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultSetterInfo(JsonSetter.Value v) {
/* 150 */     this._defaultSetterInfo = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultMergeable(Boolean v) {
/* 157 */     this._defaultMergeable = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultVisibility(VisibilityChecker<?> v) {
/* 164 */     this._visibilityChecker = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<Class<?>, MutableConfigOverride> _newMap() {
/* 174 */     return new HashMap<>();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\ConfigOverrides.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */