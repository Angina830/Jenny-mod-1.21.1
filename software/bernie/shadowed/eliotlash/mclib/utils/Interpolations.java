/*     */ package software.bernie.shadowed.eliotlash.mclib.utils;
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
/*     */ public class Interpolations
/*     */ {
/*     */   public static float lerp(float a, float b, float position) {
/*  18 */     return a + (b - a) * position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float lerpYaw(float a, float b, float position) {
/*  29 */     a = MathHelper.wrapDegrees(a);
/*  30 */     b = MathHelper.wrapDegrees(b);
/*     */     
/*  32 */     return lerp(a, normalizeYaw(a, b), position);
/*     */   }
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
/*     */   public static double cubicHermite(double y0, double y1, double y2, double y3, double x) {
/*  46 */     double a = -0.5D * y0 + 1.5D * y1 - 1.5D * y2 + 0.5D * y3;
/*  47 */     double b = y0 - 2.5D * y1 + 2.0D * y2 - 0.5D * y3;
/*  48 */     double c = -0.5D * y0 + 0.5D * y2;
/*     */     
/*  50 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicHermiteYaw(float y0, float y1, float y2, float y3, float position) {
/*  57 */     y0 = MathHelper.wrapDegrees(y0);
/*  58 */     y1 = MathHelper.wrapDegrees(y1);
/*  59 */     y2 = MathHelper.wrapDegrees(y2);
/*  60 */     y3 = MathHelper.wrapDegrees(y3);
/*     */     
/*  62 */     y1 = normalizeYaw(y0, y1);
/*  63 */     y2 = normalizeYaw(y1, y2);
/*  64 */     y3 = normalizeYaw(y2, y3);
/*     */     
/*  66 */     return cubicHermite(y0, y1, y2, y3, position);
/*     */   }
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
/*     */   public static float cubic(float y0, float y1, float y2, float y3, float x) {
/*  79 */     float a = y3 - y2 - y0 + y1;
/*  80 */     float b = y0 - y1 - a;
/*  81 */     float c = y2 - y0;
/*     */     
/*  83 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cubicYaw(float y0, float y1, float y2, float y3, float position) {
/*  90 */     y0 = MathHelper.wrapDegrees(y0);
/*  91 */     y1 = MathHelper.wrapDegrees(y1);
/*  92 */     y2 = MathHelper.wrapDegrees(y2);
/*  93 */     y3 = MathHelper.wrapDegrees(y3);
/*     */     
/*  95 */     y1 = normalizeYaw(y0, y1);
/*  96 */     y2 = normalizeYaw(y1, y2);
/*  97 */     y3 = normalizeYaw(y2, y3);
/*     */     
/*  99 */     return cubic(y0, y1, y2, y3, position);
/*     */   }
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
/*     */   public static float bezierX(float x1, float x2, float t, float epsilon) {
/* 112 */     float x = t;
/* 113 */     float init = bezier(0.0F, x1, x2, 1.0F, t);
/* 114 */     float factor = Math.copySign(0.1F, t - init);
/*     */     
/* 116 */     while (Math.abs(t - init) > epsilon) {
/* 117 */       float oldFactor = factor;
/*     */       
/* 119 */       x += factor;
/* 120 */       init = bezier(0.0F, x1, x2, 1.0F, x);
/*     */       
/* 122 */       if (Math.copySign(factor, t - init) != oldFactor) {
/* 123 */         factor *= -0.25F;
/*     */       }
/*     */     } 
/*     */     
/* 127 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float bezierX(float x1, float x2, float t) {
/* 135 */     return bezierX(x1, x2, t, 5.0E-4F);
/*     */   }
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
/*     */   public static float bezier(float x1, float x2, float x3, float x4, float t) {
/* 148 */     float t1 = lerp(x1, x2, t);
/* 149 */     float t2 = lerp(x2, x3, t);
/* 150 */     float t3 = lerp(x3, x4, t);
/* 151 */     float t4 = lerp(t1, t2, t);
/* 152 */     float t5 = lerp(t2, t3, t);
/*     */     
/* 154 */     return lerp(t4, t5, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float normalizeYaw(float a, float b) {
/* 162 */     float diff = a - b;
/*     */     
/* 164 */     if (diff > 180.0F || diff < -180.0F) {
/* 165 */       diff = Math.copySign(360.0F - Math.abs(diff), diff);
/*     */       
/* 167 */       return a + diff;
/*     */     } 
/*     */     
/* 170 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float envelope(float x, float duration, float fades) {
/* 180 */     return envelope(x, 0.0F, fades, duration - fades, duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float envelope(float x, float lowIn, float lowOut, float highIn, float highOut) {
/* 190 */     if (x < lowIn || x > highOut)
/* 191 */       return 0.0F; 
/* 192 */     if (x < lowOut)
/* 193 */       return (x - lowIn) / (lowOut - lowIn); 
/* 194 */     if (x > highIn) {
/* 195 */       return 1.0F - (x - highIn) / (highOut - highIn);
/*     */     }
/* 197 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerp(double a, double b, double position) {
/* 206 */     return a + (b - a) * position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerpYaw(double a, double b, double position) {
/* 217 */     a = MathHelper.wrapDegrees(a);
/* 218 */     b = MathHelper.wrapDegrees(b);
/*     */     
/* 220 */     return lerp(a, normalizeYaw(a, b), position);
/*     */   }
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
/*     */   public static double cubic(double y0, double y1, double y2, double y3, double x) {
/* 233 */     double a = y3 - y2 - y0 + y1;
/* 234 */     double b = y0 - y1 - a;
/* 235 */     double c = y2 - y0;
/*     */     
/* 237 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicYaw(double y0, double y1, double y2, double y3, double position) {
/* 244 */     y0 = MathHelper.wrapDegrees(y0);
/* 245 */     y1 = MathHelper.wrapDegrees(y1);
/* 246 */     y2 = MathHelper.wrapDegrees(y2);
/* 247 */     y3 = MathHelper.wrapDegrees(y3);
/*     */     
/* 249 */     y1 = normalizeYaw(y0, y1);
/* 250 */     y2 = normalizeYaw(y1, y2);
/* 251 */     y3 = normalizeYaw(y2, y3);
/*     */     
/* 253 */     return cubic(y0, y1, y2, y3, position);
/*     */   }
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
/*     */   public static double bezierX(double x1, double x2, double t, double epsilon) {
/* 266 */     double x = t;
/* 267 */     double init = bezier(0.0D, x1, x2, 1.0D, t);
/* 268 */     double factor = Math.copySign(0.10000000149011612D, t - init);
/*     */     
/* 270 */     while (Math.abs(t - init) > epsilon) {
/* 271 */       double oldFactor = factor;
/*     */       
/* 273 */       x += factor;
/* 274 */       init = bezier(0.0D, x1, x2, 1.0D, x);
/*     */       
/* 276 */       if (Math.copySign(factor, t - init) != oldFactor) {
/* 277 */         factor *= -0.25D;
/*     */       }
/*     */     } 
/*     */     
/* 281 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double bezierX(double x1, double x2, float t) {
/* 289 */     return bezierX(x1, x2, t, 5.000000237487257E-4D);
/*     */   }
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
/*     */   public static double bezier(double x1, double x2, double x3, double x4, double t) {
/* 302 */     double t1 = lerp(x1, x2, t);
/* 303 */     double t2 = lerp(x2, x3, t);
/* 304 */     double t3 = lerp(x3, x4, t);
/* 305 */     double t4 = lerp(t1, t2, t);
/* 306 */     double t5 = lerp(t2, t3, t);
/*     */     
/* 308 */     return lerp(t4, t5, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double normalizeYaw(double a, double b) {
/* 316 */     double diff = a - b;
/*     */     
/* 318 */     if (diff > 180.0D || diff < -180.0D) {
/* 319 */       diff = Math.copySign(360.0D - Math.abs(diff), diff);
/*     */       
/* 321 */       return a + diff;
/*     */     } 
/*     */     
/* 324 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double envelope(double x, double duration, double fades) {
/* 334 */     return envelope(x, 0.0D, fades, duration - fades, duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double envelope(double x, double lowIn, double lowOut, double highIn, double highOut) {
/* 344 */     if (x < lowIn || x > highOut)
/* 345 */       return 0.0D; 
/* 346 */     if (x < lowOut)
/* 347 */       return (x - lowIn) / (lowOut - lowIn); 
/* 348 */     if (x > highIn) {
/* 349 */       return 1.0D - (x - highIn) / (highOut - highIn);
/*     */     }
/* 351 */     return 1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mcli\\utils\Interpolations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */