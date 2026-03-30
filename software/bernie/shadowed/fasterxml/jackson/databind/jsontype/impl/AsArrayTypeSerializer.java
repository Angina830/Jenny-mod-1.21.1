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
/*    */ public class AsArrayTypeSerializer
/*    */   extends TypeSerializerBase
/*    */ {
/*    */   public AsArrayTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
/* 15 */     super(idRes, property);
/*    */   }
/*    */ 
/*    */   
/*    */   public AsArrayTypeSerializer forProperty(BeanProperty prop) {
/* 20 */     return (this._property == prop) ? this : new AsArrayTypeSerializer(this._idResolver, prop);
/*    */   }
/*    */   
/*    */   public JsonTypeInfo.As getTypeInclusion() {
/* 24 */     return JsonTypeInfo.As.WRAPPER_ARRAY;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsArrayTypeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */