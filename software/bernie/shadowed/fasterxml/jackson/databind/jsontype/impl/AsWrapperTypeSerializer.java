/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AsWrapperTypeSerializer
/*    */   extends TypeSerializerBase
/*    */ {
/*    */   public AsWrapperTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
/* 23 */     super(idRes, property);
/*    */   }
/*    */ 
/*    */   
/*    */   public AsWrapperTypeSerializer forProperty(BeanProperty prop) {
/* 28 */     return (this._property == prop) ? this : new AsWrapperTypeSerializer(this._idResolver, prop);
/*    */   }
/*    */   
/*    */   public JsonTypeInfo.As getTypeInclusion() {
/* 32 */     return JsonTypeInfo.As.WRAPPER_OBJECT;
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
/*    */ 
/*    */ 
/*    */   
/*    */   protected String _validTypeId(String typeId) {
/* 47 */     return ClassUtil.nonNullString(typeId);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void _writeTypeId(JsonGenerator g, String typeId) throws IOException {
/* 53 */     if (typeId != null)
/* 54 */       g.writeTypeId(typeId); 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\AsWrapperTypeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */