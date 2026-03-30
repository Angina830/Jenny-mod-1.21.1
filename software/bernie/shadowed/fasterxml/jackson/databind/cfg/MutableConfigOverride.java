/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonAutoDetect;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MutableConfigOverride
/*    */   extends ConfigOverride
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MutableConfigOverride() {}
/*    */   
/*    */   protected MutableConfigOverride(MutableConfigOverride src) {
/* 27 */     super(src);
/*    */   }
/*    */   
/*    */   public MutableConfigOverride copy() {
/* 31 */     return new MutableConfigOverride(this);
/*    */   }
/*    */   
/*    */   public MutableConfigOverride setFormat(JsonFormat.Value v) {
/* 35 */     this._format = v;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MutableConfigOverride setInclude(JsonInclude.Value v) {
/* 46 */     this._include = v;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MutableConfigOverride setIncludeAsProperty(JsonInclude.Value v) {
/* 59 */     this._includeAsProperty = v;
/* 60 */     return this;
/*    */   }
/*    */   
/*    */   public MutableConfigOverride setIgnorals(JsonIgnoreProperties.Value v) {
/* 64 */     this._ignorals = v;
/* 65 */     return this;
/*    */   }
/*    */   
/*    */   public MutableConfigOverride setIsIgnoredType(Boolean v) {
/* 69 */     this._isIgnoredType = v;
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MutableConfigOverride setSetterInfo(JsonSetter.Value v) {
/* 77 */     this._setterInfo = v;
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MutableConfigOverride setVisibility(JsonAutoDetect.Value v) {
/* 85 */     this._visibility = v;
/* 86 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MutableConfigOverride setMergeable(Boolean v) {
/* 93 */     this._mergeable = v;
/* 94 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\MutableConfigOverride.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */