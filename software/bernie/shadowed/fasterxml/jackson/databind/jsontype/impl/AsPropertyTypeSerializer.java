/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AsPropertyTypeSerializer
/*    */   extends AsArrayTypeSerializer
/*    */ {
/*    */   protected final String _typePropertyName;
/*    */   
/*    */   public AsPropertyTypeSerializer(TypeIdResolver idRes, BeanProperty property, String propName) {
/* 22 */     super(idRes, property);
/* 23 */     this._typePropertyName = propName;
/*    */   }
/*    */ 
/*    */   
/*    */   public AsPropertyTypeSerializer forProperty(BeanProperty prop) {
/* 28 */     return (this._property == prop) ? this : new AsPropertyTypeSerializer(this._idResolver, prop, this._typePropertyName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPropertyName() {
/* 33 */     return this._typePropertyName;
/*    */   }
/*    */   public JsonTypeInfo.As getTypeInclusion() {
/* 36 */     return JsonTypeInfo.As.PROPERTY;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsPropertyTypeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */