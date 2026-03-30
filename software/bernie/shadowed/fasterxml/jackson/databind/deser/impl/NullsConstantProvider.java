/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullsConstantProvider
/*    */   implements NullValueProvider, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   private static final NullsConstantProvider SKIPPER = new NullsConstantProvider(null);
/*    */   
/* 19 */   private static final NullsConstantProvider NULLER = new NullsConstantProvider(null);
/*    */   
/*    */   protected final Object _nullValue;
/*    */   
/*    */   protected final AccessPattern _access;
/*    */   
/*    */   protected NullsConstantProvider(Object nvl) {
/* 26 */     this._nullValue = nvl;
/* 27 */     this._access = (this._nullValue == null) ? AccessPattern.ALWAYS_NULL : AccessPattern.CONSTANT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NullsConstantProvider skipper() {
/* 38 */     return SKIPPER;
/*    */   }
/*    */   
/*    */   public static NullsConstantProvider nuller() {
/* 42 */     return NULLER;
/*    */   }
/*    */   
/*    */   public static NullsConstantProvider forValue(Object nvl) {
/* 46 */     if (nvl == null) {
/* 47 */       return NULLER;
/*    */     }
/* 49 */     return new NullsConstantProvider(nvl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isSkipper(NullValueProvider p) {
/* 58 */     return (p == SKIPPER);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isNuller(NullValueProvider p) {
/* 67 */     return (p == NULLER);
/*    */   }
/*    */ 
/*    */   
/*    */   public AccessPattern getNullAccessPattern() {
/* 72 */     return this._access;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getNullValue(DeserializationContext ctxt) {
/* 77 */     return this._nullValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\NullsConstantProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */