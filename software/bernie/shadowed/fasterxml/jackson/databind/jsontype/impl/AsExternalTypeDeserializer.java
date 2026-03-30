/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AsExternalTypeDeserializer
/*    */   extends AsArrayTypeDeserializer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public AsExternalTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
/* 28 */     super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
/*    */   }
/*    */ 
/*    */   
/*    */   public AsExternalTypeDeserializer(AsExternalTypeDeserializer src, BeanProperty property) {
/* 33 */     super(src, property);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeDeserializer forProperty(BeanProperty prop) {
/* 38 */     if (prop == this._property) {
/* 39 */       return this;
/*    */     }
/* 41 */     return new AsExternalTypeDeserializer(this, prop);
/*    */   }
/*    */   
/*    */   public JsonTypeInfo.As getTypeInclusion() {
/* 45 */     return JsonTypeInfo.As.EXTERNAL_PROPERTY;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean _usesExternalId() {
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsExternalTypeDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */