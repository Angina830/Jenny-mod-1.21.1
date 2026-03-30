/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
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
/*    */ 
/*    */ public interface JsonObjectFormatVisitor
/*    */   extends JsonFormatVisitorWithSerializerProvider
/*    */ {
/*    */   void property(BeanProperty paramBeanProperty) throws JsonMappingException;
/*    */   
/*    */   void property(String paramString, JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   void optionalProperty(BeanProperty paramBeanProperty) throws JsonMappingException;
/*    */   
/*    */   void optionalProperty(String paramString, JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   public static class Base
/*    */     implements JsonObjectFormatVisitor
/*    */   {
/*    */     protected SerializerProvider _provider;
/*    */     
/*    */     public Base() {}
/*    */     
/*    */     public Base(SerializerProvider p) {
/* 43 */       this._provider = p;
/*    */     }
/*    */     public SerializerProvider getProvider() {
/* 46 */       return this._provider;
/*    */     }
/*    */     public void setProvider(SerializerProvider p) {
/* 49 */       this._provider = p;
/*    */     }
/*    */     
/*    */     public void property(BeanProperty prop) throws JsonMappingException {}
/*    */     
/*    */     public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) throws JsonMappingException {}
/*    */     
/*    */     public void optionalProperty(BeanProperty prop) throws JsonMappingException {}
/*    */     
/*    */     public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) throws JsonMappingException {}
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonObjectFormatVisitor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */