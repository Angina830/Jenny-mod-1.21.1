/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*    */ public interface JsonMapFormatVisitor
/*    */   extends JsonFormatVisitorWithSerializerProvider
/*    */ {
/*    */   void keyFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   void valueFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   public static class Base
/*    */     implements JsonMapFormatVisitor
/*    */   {
/*    */     protected SerializerProvider _provider;
/*    */     
/*    */     public Base() {}
/*    */     
/*    */     public Base(SerializerProvider p) {
/* 32 */       this._provider = p;
/*    */     }
/*    */     public SerializerProvider getProvider() {
/* 35 */       return this._provider;
/*    */     }
/*    */     public void setProvider(SerializerProvider p) {
/* 38 */       this._provider = p;
/*    */     }
/*    */     
/*    */     public void keyFormat(JsonFormatVisitable handler, JavaType keyType) throws JsonMappingException {}
/*    */     
/*    */     public void valueFormat(JsonFormatVisitable handler, JavaType valueType) throws JsonMappingException {}
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonMapFormatVisitor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */