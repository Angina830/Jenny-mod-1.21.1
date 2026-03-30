/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Java7Support
/*    */ {
/*    */   private static final Java7Support IMPL;
/*    */   
/*    */   static {
/* 22 */     Java7Support impl = null;
/*    */     try {
/* 24 */       Class<?> cls = Class.forName("software.bernie.shadowed.fasterxml.jackson.databind.ext.Java7SupportImpl");
/* 25 */       impl = (Java7Support)ClassUtil.createInstance(cls, false);
/* 26 */     } catch (Throwable t) {
/*    */       
/* 28 */       Logger.getLogger(Java7Support.class.getName()).warning("Unable to load JDK7 types (annotations, java.nio.file.Path): no Java7 support added");
/*    */     } 
/*    */     
/* 31 */     IMPL = impl;
/*    */   }
/*    */   
/*    */   public static Java7Support instance() {
/* 35 */     return IMPL;
/*    */   }
/*    */   
/*    */   public abstract JsonSerializer<?> getSerializerForJavaNioFilePath(Class<?> paramClass);
/*    */   
/*    */   public abstract JsonDeserializer<?> getDeserializerForJavaNioFilePath(Class<?> paramClass);
/*    */   
/*    */   public abstract Class<?> getClassJavaNioFilePath();
/*    */   
/*    */   public abstract PropertyName findConstructorName(AnnotatedParameter paramAnnotatedParameter);
/*    */   
/*    */   public abstract Boolean hasCreatorAnnotation(Annotated paramAnnotated);
/*    */   
/*    */   public abstract Boolean findTransient(Annotated paramAnnotated);
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\Java7Support.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */