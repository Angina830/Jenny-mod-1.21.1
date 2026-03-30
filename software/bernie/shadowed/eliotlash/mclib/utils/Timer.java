/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ public class Timer {
/*    */   public boolean enabled;
/*    */   public long time;
/*    */   public long duration;
/*    */   
/*    */   public Timer(long duration) {
/*  9 */     this.duration = duration;
/*    */   }
/*    */   
/*    */   public long getRemaining() {
/* 13 */     return this.time - System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void mark() {
/* 17 */     mark(this.duration);
/*    */   }
/*    */   
/*    */   public void mark(long duration) {
/* 21 */     this.enabled = true;
/* 22 */     this.time = System.currentTimeMillis() + duration;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 26 */     this.enabled = false;
/*    */   }
/*    */   
/*    */   public boolean checkReset() {
/* 30 */     boolean enabled = check();
/*    */     
/* 32 */     if (enabled) {
/* 33 */       reset();
/*    */     }
/*    */     
/* 36 */     return enabled;
/*    */   }
/*    */   
/*    */   public boolean check() {
/* 40 */     return (this.enabled && isTime());
/*    */   }
/*    */   
/*    */   public boolean isTime() {
/* 44 */     return (System.currentTimeMillis() >= this.time);
/*    */   }
/*    */   
/*    */   public boolean checkRepeat() {
/* 48 */     if (!this.enabled) {
/* 49 */       mark();
/*    */     }
/*    */     
/* 52 */     return checkReset();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mcli\\utils\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */