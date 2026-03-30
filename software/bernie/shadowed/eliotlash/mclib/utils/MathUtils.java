/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ public class MathUtils {
/*    */   public static int clamp(int x, int min, int max) {
/*  5 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */   
/*    */   public static float clamp(float x, float min, float max) {
/*  9 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */   
/*    */   public static double clamp(double x, double min, double max) {
/* 13 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */   
/*    */   public static int cycler(int x, int min, int max) {
/* 17 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */   
/*    */   public static float cycler(float x, float min, float max) {
/* 21 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */   
/*    */   public static double cycler(double x, double min, double max) {
/* 25 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mcli\\utils\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */