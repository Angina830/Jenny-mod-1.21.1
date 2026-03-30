/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*     */ public interface JsonFormatVisitorWrapper
/*     */   extends JsonFormatVisitorWithSerializerProvider
/*     */ {
/*     */   JsonObjectFormatVisitor expectObjectFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonArrayFormatVisitor expectArrayFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonStringFormatVisitor expectStringFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonNumberFormatVisitor expectNumberFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonIntegerFormatVisitor expectIntegerFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonBooleanFormatVisitor expectBooleanFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonNullFormatVisitor expectNullFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonAnyFormatVisitor expectAnyFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   JsonMapFormatVisitor expectMapFormat(JavaType paramJavaType) throws JsonMappingException;
/*     */   
/*     */   public static class Base
/*     */     implements JsonFormatVisitorWrapper
/*     */   {
/*     */     protected SerializerProvider _provider;
/*     */     
/*     */     public Base() {}
/*     */     
/*     */     public Base(SerializerProvider p) {
/*  78 */       this._provider = p;
/*     */     }
/*     */ 
/*     */     
/*     */     public SerializerProvider getProvider() {
/*  83 */       return this._provider;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setProvider(SerializerProvider p) {
/*  88 */       this._provider = p;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectFormatVisitor expectObjectFormat(JavaType type) throws JsonMappingException {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonArrayFormatVisitor expectArrayFormat(JavaType type) throws JsonMappingException {
/* 100 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonStringFormatVisitor expectStringFormat(JavaType type) throws JsonMappingException {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNumberFormatVisitor expectNumberFormat(JavaType type) throws JsonMappingException {
/* 112 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonIntegerFormatVisitor expectIntegerFormat(JavaType type) throws JsonMappingException {
/* 118 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonBooleanFormatVisitor expectBooleanFormat(JavaType type) throws JsonMappingException {
/* 124 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNullFormatVisitor expectNullFormat(JavaType type) throws JsonMappingException {
/* 130 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonAnyFormatVisitor expectAnyFormat(JavaType type) throws JsonMappingException {
/* 136 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonMapFormatVisitor expectMapFormat(JavaType type) throws JsonMappingException {
/* 142 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonFormatVisitorWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */