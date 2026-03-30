/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MathHelper
/*    */ {
/*    */   public static float wrapDegrees(float value) {
/*  9 */     value %= 360.0F;
/*    */     
/* 11 */     if (value >= 180.0F) {
/* 12 */       value -= 360.0F;
/*    */     }
/*    */     
/* 15 */     if (value < -180.0F) {
/* 16 */       value += 360.0F;
/*    */     }
/*    */     
/* 19 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double wrapDegrees(double value) {
/* 27 */     value %= 360.0D;
/*    */     
/* 29 */     if (value >= 180.0D) {
/* 30 */       value -= 360.0D;
/*    */     }
/*    */     
/* 33 */     if (value < -180.0D) {
/* 34 */       value += 360.0D;
/*    */     }
/*    */     
/* 37 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int wrapDegrees(int angle) {
/* 44 */     angle %= 360;
/*    */     
/* 46 */     if (angle >= 180) {
/* 47 */       angle -= 360;
/*    */     }
/*    */     
/* 50 */     if (angle < -180) {
/* 51 */       angle += 360;
/*    */     }
/*    */     
/* 54 */     return angle;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mcli\\utils\MathHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */