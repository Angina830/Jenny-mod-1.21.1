/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PackageVersion
/*    */   implements Versioned
/*    */ {
/* 13 */   public static final Version VERSION = VersionUtil.parseVersion("2.9.0", "software.bernie.shadowed.fasterxml.jackson.core", "jackson-databind");
/*    */ 
/*    */ 
/*    */   
/*    */   public Version version() {
/* 18 */     return VERSION;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\PackageVersion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */