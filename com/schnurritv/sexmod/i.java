package com.schnurritv.sexmod;

import java.util.ConcurrentModificationException;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class i implements IGuiHandler {
  public Object getServerGuiElement(int paramInt1, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt1 == 0)
      try {
        for (Q q : Q.f()) {
          try {
            if (!q.field_70170_p.field_72995_K)
              try {
                if (q.func_180425_c().func_177958_n() == paramInt2)
                  try {
                    if (q.func_180425_c().func_177956_o() == paramInt3)
                      try {
                        if (q.func_180425_c().func_177952_p() == paramInt4)
                          return (q instanceof aI) ? new am((aI)q, paramEntityPlayer.field_71071_by, UUID.randomUUID()) : new bG(q, paramEntityPlayer.field_71071_by, UUID.randomUUID()); 
                      } catch (ConcurrentModificationException concurrentModificationException) {
                        throw a(null);
                      }  
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  }  
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              }  
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          } 
        } 
      } catch (ConcurrentModificationException concurrentModificationException) {} 
    if (paramInt1 == 1)
      try {
        for (Q q : Q.f()) {
          try {
            if (!q.field_70170_p.field_72995_K)
              try {
                if (q instanceof IInventory)
                  try {
                    if (q.func_180425_c().func_177958_n() == paramInt2)
                      try {
                        if (q.func_180425_c().func_177956_o() == paramInt3 && q.func_180425_c().func_177952_p() == paramInt4) {
                          IInventory iInventory = (IInventory)q;
                          return new bp((IInventory)paramEntityPlayer.field_71071_by, iInventory, paramEntityPlayer, UUID.randomUUID());
                        } 
                      } catch (ConcurrentModificationException concurrentModificationException) {
                        throw a(null);
                      }  
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  }  
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              }  
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          } 
        } 
      } catch (ConcurrentModificationException concurrentModificationException) {} 
    return null;
  }
  
  public Object getClientGuiElement(int paramInt1, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt1 == 0)
      try {
        for (Q q : Q.f()) {
          try {
            if (q.field_70170_p.field_72995_K)
              try {
                if (q.func_180425_c().func_177958_n() == paramInt2)
                  try {
                    if (q.func_180425_c().func_177956_o() == paramInt3)
                      try {
                        if (q.func_180425_c().func_177952_p() == paramInt4)
                          return (q instanceof aI) ? new bC((aI)q, paramEntityPlayer.field_71071_by, UUID.randomUUID()) : new az(q, paramEntityPlayer.field_71071_by, UUID.randomUUID()); 
                      } catch (ConcurrentModificationException concurrentModificationException) {
                        throw a(null);
                      }  
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  }  
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              }  
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          } 
        } 
      } catch (ConcurrentModificationException concurrentModificationException) {} 
    if (paramInt1 == 1)
      try {
        for (Q q : Q.f()) {
          try {
            if (q.field_70170_p.field_72995_K)
              try {
                if (q instanceof IInventory)
                  try {
                    if (q.func_180425_c().func_177958_n() == paramInt2)
                      try {
                        if (q.func_180425_c().func_177956_o() == paramInt3 && q.func_180425_c().func_177952_p() == paramInt4)
                          return new bK(paramEntityPlayer, q, UUID.randomUUID()); 
                      } catch (ConcurrentModificationException concurrentModificationException) {
                        throw a(null);
                      }  
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  }  
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              }  
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          } 
        } 
      } catch (ConcurrentModificationException concurrentModificationException) {} 
    return null;
  }
  
  private static ConcurrentModificationException a(ConcurrentModificationException paramConcurrentModificationException) {
    return paramConcurrentModificationException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */