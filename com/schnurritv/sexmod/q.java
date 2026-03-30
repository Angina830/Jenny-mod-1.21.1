package com.schnurritv.sexmod;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class q extends EntityAINearestAttackableTarget<aD> {
  private final int b;
  
  private final boolean a;
  
  public q(EntityCreature paramEntityCreature, boolean paramBoolean1, boolean paramBoolean2) {
    this(paramEntityCreature, paramBoolean1, false, paramBoolean2);
  }
  
  public q(EntityCreature paramEntityCreature, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    this(paramEntityCreature, 10, paramBoolean1, paramBoolean2, (Predicate)null, paramBoolean3);
  }
  
  public q(EntityCreature paramEntityCreature, int paramInt, boolean paramBoolean1, boolean paramBoolean2, @Nullable Predicate paramPredicate, boolean paramBoolean3) {
    super(paramEntityCreature, aD.class, paramInt, paramBoolean1, paramBoolean2, paramPredicate);
    this.b = paramInt;
    this.a = paramBoolean3;
  }
  
  public boolean func_75250_a() {
    if (this.a) {
      float f = this.field_75299_d.func_70013_c();
      try {
        if (f >= 0.5F)
          return false; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    try {
      if (this.b > 0)
        try {
          if (this.field_75299_d.func_70681_au().nextInt(this.b) != 0)
            return false; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    List list = this.field_75299_d.field_70170_p.func_175647_a(this.field_75307_b, func_188511_a(func_111175_f()), this.field_82643_g);
    try {
      if (list.isEmpty())
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    ArrayList<aD> arrayList = new ArrayList();
    for (aD aD : list) {
      try {
        if (aD.N())
          arrayList.add(aD); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    try {
      if (arrayList.isEmpty())
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    arrayList.sort((Comparator<? super aD>)this.field_75306_g);
    this.field_75309_a = (EntityLivingBase)arrayList.get(0);
    return true;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\q.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */