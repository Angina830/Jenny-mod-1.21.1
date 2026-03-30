/*    */ package software.bernie.shadowed.fasterxml.jackson.core.json;
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
/* 13 */   public static final Version VERSION = VersionUtil.parseVersion("2.9.0", "software.bernie.shadowed.fasterxml.jackson.core", "jackson-core");
/*    */ 
/*    */ 
/*    */   
/*    */   public Version version() {
/* 18 */     return VERSION;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\PackageVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */