/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*    */ 
/*    */ public class StackTraceElementDeserializer
/*    */   extends StdScalarDeserializer<StackTraceElement>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public StackTraceElementDeserializer() {
/* 16 */     super(StackTraceElement.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public StackTraceElement deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 21 */     JsonToken t = p.getCurrentToken();
/*    */     
/* 23 */     if (t == JsonToken.START_OBJECT) {
/* 24 */       String className = "", methodName = "", fileName = "";
/*    */       
/* 26 */       String moduleName = null, moduleVersion = null;
/* 27 */       String classLoaderName = null;
/* 28 */       int lineNumber = -1;
/*    */       
/* 30 */       while ((t = p.nextValue()) != JsonToken.END_OBJECT) {
/* 31 */         String propName = p.getCurrentName();
/*    */         
/* 33 */         if ("className".equals(propName)) {
/* 34 */           className = p.getText(); continue;
/* 35 */         }  if ("classLoaderName".equals(propName)) {
/* 36 */           classLoaderName = p.getText(); continue;
/* 37 */         }  if ("fileName".equals(propName)) {
/* 38 */           fileName = p.getText(); continue;
/* 39 */         }  if ("lineNumber".equals(propName)) {
/* 40 */           if (t.isNumeric()) {
/* 41 */             lineNumber = p.getIntValue(); continue;
/*    */           } 
/* 43 */           lineNumber = _parseIntPrimitive(p, ctxt); continue;
/*    */         } 
/* 45 */         if ("methodName".equals(propName)) {
/* 46 */           methodName = p.getText(); continue;
/* 47 */         }  if ("nativeMethod".equals(propName))
/*    */           continue; 
/* 49 */         if ("moduleName".equals(propName)) {
/* 50 */           moduleName = p.getText(); continue;
/* 51 */         }  if ("moduleVersion".equals(propName)) {
/* 52 */           moduleVersion = p.getText(); continue;
/*    */         } 
/* 54 */         handleUnknownProperty(p, ctxt, this._valueClass, propName);
/*    */       } 
/*    */       
/* 57 */       return constructValue(ctxt, className, methodName, fileName, lineNumber, moduleName, moduleVersion, classLoaderName);
/*    */     } 
/* 59 */     if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/* 60 */       p.nextToken();
/* 61 */       StackTraceElement value = deserialize(p, ctxt);
/* 62 */       if (p.nextToken() != JsonToken.END_ARRAY) {
/* 63 */         handleMissingEndArrayForSingle(p, ctxt);
/*    */       }
/* 65 */       return value;
/*    */     } 
/* 67 */     return (StackTraceElement)ctxt.handleUnexpectedToken(this._valueClass, p);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   protected StackTraceElement constructValue(DeserializationContext ctxt, String className, String methodName, String fileName, int lineNumber, String moduleName, String moduleVersion) {
/* 74 */     return constructValue(ctxt, className, methodName, fileName, lineNumber, moduleName, moduleVersion, (String)null);
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
/*    */   protected StackTraceElement constructValue(DeserializationContext ctxt, String className, String methodName, String fileName, int lineNumber, String moduleName, String moduleVersion, String classLoaderName) {
/* 89 */     return new StackTraceElement(className, methodName, fileName, lineNumber);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StackTraceElementDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */