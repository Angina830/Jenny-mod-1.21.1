/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonAutoDetect;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ConfigOverride
/*     */ {
/*     */   protected JsonFormat.Value _format;
/*     */   protected JsonInclude.Value _include;
/*     */   protected JsonInclude.Value _includeAsProperty;
/*     */   protected JsonIgnoreProperties.Value _ignorals;
/*     */   protected JsonSetter.Value _setterInfo;
/*     */   protected JsonAutoDetect.Value _visibility;
/*     */   protected Boolean _isIgnoredType;
/*     */   protected Boolean _mergeable;
/*     */   
/*     */   protected ConfigOverride() {}
/*     */   
/*     */   protected ConfigOverride(ConfigOverride src) {
/*  78 */     this._format = src._format;
/*  79 */     this._include = src._include;
/*  80 */     this._includeAsProperty = src._includeAsProperty;
/*  81 */     this._ignorals = src._ignorals;
/*  82 */     this._isIgnoredType = src._isIgnoredType;
/*  83 */     this._mergeable = src._mergeable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigOverride empty() {
/*  92 */     return Empty.INSTANCE;
/*     */   }
/*     */   
/*  95 */   public JsonFormat.Value getFormat() { return this._format; } public JsonInclude.Value getInclude() {
/*  96 */     return this._include;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonInclude.Value getIncludeAsProperty() {
/* 101 */     return this._includeAsProperty;
/*     */   } public JsonIgnoreProperties.Value getIgnorals() {
/* 103 */     return this._ignorals;
/*     */   }
/*     */   public Boolean getIsIgnoredType() {
/* 106 */     return this._isIgnoredType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSetter.Value getSetterInfo() {
/* 112 */     return this._setterInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonAutoDetect.Value getVisibility() {
/* 117 */     return this._visibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getMergeable() {
/* 122 */     return this._mergeable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Empty
/*     */     extends ConfigOverride
/*     */   {
/* 131 */     static final Empty INSTANCE = new Empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\ConfigOverride.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */