/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.PackageVersion;
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
/*    */ public abstract class NopAnnotationIntrospector
/*    */   extends AnnotationIntrospector
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 23 */   public static final NopAnnotationIntrospector instance = new NopAnnotationIntrospector()
/*    */     {
/*    */       private static final long serialVersionUID = 1L;
/*    */       
/*    */       public Version version() {
/* 28 */         return PackageVersion.VERSION;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public Version version() {
/* 34 */     return Version.unknownVersion();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\NopAnnotationIntrospector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */