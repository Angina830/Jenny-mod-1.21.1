package com.schnurritv.sexmod;

import net.minecraft.entity.Entity;

public class bh extends bj {
  int i = 0;
  
  int j = 0;
  
  public bh(Q paramQ) {
    super(paramQ);
  }
  
  public void func_75251_c() {
    super.func_75251_c();
    this.g.field_70747_aH = 0.02F;
  }
  
  protected bj.a a() {
    float f = this.g.func_70032_d((Entity)this.b);
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    boolean bool = (f > 5.0F) ? true : false;
    try {
      if (this.g.B() == null)
        try {
          if (!bool)
            try {
              if (this.f == bj.a.FOLLOW)
                if (++this.i > 60) {
                  bool = false;
                  this.i = 0;
                } else {
                  bool = true;
                }  
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (bool)
        return bj.a.FOLLOW; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return bj.a.IDLE;
  }
  
  protected void a(bj.a parama) {
    double d;
    switch (a.a[parama.ordinal()]) {
      case 1:
        d = this.g.func_70032_d((Entity)this.b);
        try {
          if (this.h.func_111269_d() > d) {
            this.h.func_75499_g();
            this.h.func_75497_a((Entity)this.b, 0.5D);
          } else {
            b();
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        this.j = 300;
        c();
        break;
      case 2:
        c();
        break;
    } 
  }
  
  protected double c() {
    float f1 = this.g.func_70032_d((Entity)this.b);
    float f2 = 0.02F;
    double d = Math.min(0.7D, Math.floor((f1 / 3.0F)) * 0.05D);
    f2 = (float)(f2 + d);
    this.g.field_70747_aH = f2;
    return f2;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */