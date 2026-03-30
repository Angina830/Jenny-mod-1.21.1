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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface JsonArrayFormatVisitor
/*    */   extends JsonFormatVisitorWithSerializerProvider
/*    */ {
/*    */   void itemsFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   void itemsFormat(JsonFormatTypes paramJsonFormatTypes) throws JsonMappingException;
/*    */   
/*    */   public static class Base
/*    */     implements JsonArrayFormatVisitor
/*    */   {
/*    */     protected SerializerProvider _provider;
/*    */     
/*    */     public Base() {}
/*    */     
/*    */     public Base(SerializerProvider p) {
/* 37 */       this._provider = p;
/*    */     }
/*    */     public SerializerProvider getProvider() {
/* 40 */       return this._provider;
/*    */     }
/*    */     public void setProvider(SerializerProvider p) {
/* 43 */       this._provider = p;
/*    */     }
/*    */     
/*    */     public void itemsFormat(JsonFormatVisitable handler, JavaType elementType) throws JsonMappingException {}
/*    */     
/*    */     public void itemsFormat(JsonFormatTypes format) throws JsonMappingException {}
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonArrayFormatVisitor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */