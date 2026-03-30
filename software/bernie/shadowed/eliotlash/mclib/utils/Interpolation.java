/*     */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*     */ 
/*     */ public enum Interpolation {
/*   4 */   LINEAR("linear")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*   7 */       return Interpolations.lerp(a, b, x);
/*     */     }
/*     */   },
/*  10 */   QUAD_IN("quad_in")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  13 */       return a + (b - a) * x * x;
/*     */     }
/*     */   },
/*  16 */   QUAD_OUT("quad_out")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  19 */       return a - (b - a) * x * (x - 2.0F);
/*     */     }
/*     */   },
/*  22 */   QUAD_INOUT("quad_inout")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  25 */       x *= 2.0F;
/*     */       
/*  27 */       if (x < 1.0F) {
/*  28 */         return a + (b - a) / 2.0F * x * x;
/*     */       }
/*  30 */       x--;
/*     */       
/*  32 */       return a - (b - a) / 2.0F * (x * (x - 2.0F) - 1.0F);
/*     */     }
/*     */   },
/*  35 */   CUBIC_IN("cubic_in")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  38 */       return a + (b - a) * x * x * x;
/*     */     }
/*     */   },
/*  41 */   CUBIC_OUT("cubic_out")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  44 */       x--;
/*  45 */       return a + (b - a) * (x * x * x + 1.0F);
/*     */     }
/*     */   },
/*  48 */   CUBIC_INOUT("cubic_inout")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  51 */       x *= 2.0F;
/*     */       
/*  53 */       if (x < 1.0F) {
/*  54 */         return a + (b - a) / 2.0F * x * x * x;
/*     */       }
/*  56 */       x -= 2.0F;
/*     */       
/*  58 */       return a + (b - a) / 2.0F * (x * x * x + 2.0F);
/*     */     }
/*     */   },
/*  61 */   EXP_IN("exp_in")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  64 */       return a + (b - a) * (float)Math.pow(2.0D, (10.0F * (x - 1.0F)));
/*     */     }
/*     */   },
/*  67 */   EXP_OUT("exp_out")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  70 */       return a + (b - a) * (float)(-Math.pow(2.0D, (-10.0F * x)) + 1.0D);
/*     */     }
/*     */   },
/*  73 */   EXP_INOUT("exp_inout")
/*     */   {
/*     */     public float interpolate(float a, float b, float x) {
/*  76 */       if (x == 0.0F)
/*  77 */         return a; 
/*  78 */       if (x == 1.0F) {
/*  79 */         return b;
/*     */       }
/*  81 */       x *= 2.0F;
/*     */       
/*  83 */       if (x < 1.0F) {
/*  84 */         return a + (b - a) / 2.0F * (float)Math.pow(2.0D, (10.0F * (x - 1.0F)));
/*     */       }
/*  86 */       x--;
/*     */       
/*  88 */       return a + (b - a) / 2.0F * (float)(-Math.pow(2.0D, (-10.0F * x)) + 2.0D);
/*     */     }
/*     */   };
/*     */   
/*     */   public final String key;
/*     */   
/*     */   Interpolation(String key) {
/*  95 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 101 */     return "mclib.interpolations." + this.key;
/*     */   }
/*     */   
/*     */   public abstract float interpolate(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mcli\\utils\Interpolation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */