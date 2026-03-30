package com.schnurritv.sexmod;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class aj {
  static final UUID b = UUID.fromString("b91e6484-8911-4def-ab04-9fa3452fca5f");
  
  static final UUID a = UUID.fromString("adf20149-2adc-4a9d-9af5-8e9aeda019d6");
  
  @SubscribeEvent
  public void a(PlayerEvent.PlayerLoggedInEvent paramPlayerLoggedInEvent) {
    EntityPlayerMP entityPlayerMP = paramPlayerLoggedInEvent.player.field_70170_p.func_73046_m().func_184103_al().func_177451_a(paramPlayerLoggedInEvent.player.getPersistentID());
    try {
      entityPlayerMP.func_82142_c(false);
      entityPlayerMP.func_189654_d(false);
      entityPlayerMP.field_70145_X = false;
      if (!entityPlayerMP.field_71075_bZ.field_75098_d)
        try {
          if (entityPlayerMP.field_71075_bZ.field_75100_b)
            entityPlayerMP.field_71075_bZ.field_75100_b = false; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    bn.a.sendTo(new b5(true), entityPlayerMP);
    for (ItemStack itemStack : entityPlayerMP.field_71071_by.field_70462_a) {
      try {
        if (itemStack.func_77973_b() == w.e)
          try {
            if (itemStack.func_77942_o())
              itemStack.func_77978_p().func_186854_a("user", UUID.randomUUID()); 
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
    } 
    UUID uUID1 = bF.a(entityPlayerMP.getPersistentID());
    if (uUID1 != null) {
      HashSet<BlockPos> hashSet = bF.e(uUID1);
      bn.a.sendTo(new bg(hashSet, true), entityPlayerMP);
    } 
    U.t();
    U u = U.b(paramPlayerLoggedInEvent.player.getPersistentID());
    World world = FMLCommonHandler.instance().getMinecraftServerInstance().func_130014_f_();
    try {
      a(world, (EntityPlayer)entityPlayerMP, u);
      if (u != null) {
        u.a(false);
        u.b(b1.NULL);
        aZ.a.a(u);
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    UUID uUID2 = paramPlayerLoggedInEvent.player.getPersistentID();
    try {
      if (uUID2.equals(b))
        a(world, (EntityPlayer)entityPlayerMP, uUID2); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (uUID2.equals(a))
        b(world, (EntityPlayer)entityPlayerMP, uUID2); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  void a(World paramWorld, EntityPlayer paramEntityPlayer, UUID paramUUID) {
    X x = new X(paramWorld, paramUUID);
    x.func_189654_d(true);
    x.field_70145_X = true;
    x.field_70159_w = 0.0D;
    x.field_70181_x = 0.0D;
    x.field_70179_y = 0.0D;
    x.func_70107_b(paramEntityPlayer.field_70165_t, paramEntityPlayer.field_70163_u + 69.0D, paramEntityPlayer.field_70161_v);
    paramWorld.func_72838_d((Entity)x);
    x.x();
  }
  
  void b(World paramWorld, EntityPlayer paramEntityPlayer, UUID paramUUID) {
    aP aP = new aP(paramWorld, paramUUID);
    aP.func_189654_d(true);
    aP.field_70145_X = true;
    aP.field_70159_w = 0.0D;
    aP.field_70181_x = 0.0D;
    aP.field_70179_y = 0.0D;
    aP.func_70107_b(paramEntityPlayer.field_70165_t, paramEntityPlayer.field_70163_u + 69.0D, paramEntityPlayer.field_70161_v);
    paramWorld.func_72838_d((Entity)aP);
    aP.x();
  }
  
  void a(World paramWorld, EntityPlayer paramEntityPlayer, U paramU) {
    Predicate predicate = paramU -> true;
    List list = paramWorld.func_175644_a(U.class, predicate::test);
    for (U u : list) {
      try {
        if (!u.u().equals(paramEntityPlayer.getPersistentID()))
          continue; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      try {
        if (paramU != null)
          try {
            if (u.func_145782_y() == paramU.func_145782_y())
              continue; 
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      paramWorld.func_72900_e((Entity)u);
    } 
  }
  
  @SubscribeEvent
  public void a(PlayerEvent.PlayerLoggedOutEvent paramPlayerLoggedOutEvent) {
    // Byte code:
    //   0: invokestatic f : ()Ljava/util/HashSet;
    //   3: invokevirtual iterator : ()Ljava/util/Iterator;
    //   6: astore_2
    //   7: aload_2
    //   8: invokeinterface hasNext : ()Z
    //   13: ifeq -> 206
    //   16: aload_2
    //   17: invokeinterface next : ()Ljava/lang/Object;
    //   22: checkcast com/schnurritv/sexmod/Q
    //   25: astore_3
    //   26: aload_3
    //   27: invokevirtual B : ()Ljava/util/UUID;
    //   30: ifnonnull -> 40
    //   33: goto -> 7
    //   36: invokestatic a : (Ljava/util/ConcurrentModificationException;)Ljava/util/ConcurrentModificationException;
    //   39: athrow
    //   40: aload_3
    //   41: invokevirtual B : ()Ljava/util/UUID;
    //   44: aload_1
    //   45: getfield player : Lnet/minecraft/entity/player/EntityPlayer;
    //   48: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   51: invokevirtual equals : (Ljava/lang/Object;)Z
    //   54: ifne -> 81
    //   57: aload_3
    //   58: invokevirtual B : ()Ljava/util/UUID;
    //   61: aload_1
    //   62: getfield player : Lnet/minecraft/entity/player/EntityPlayer;
    //   65: invokevirtual func_110124_au : ()Ljava/util/UUID;
    //   68: invokevirtual equals : (Ljava/lang/Object;)Z
    //   71: ifeq -> 104
    //   74: goto -> 81
    //   77: invokestatic a : (Ljava/util/ConcurrentModificationException;)Ljava/util/ConcurrentModificationException;
    //   80: athrow
    //   81: aload_3
    //   82: invokestatic a : (Lcom/schnurritv/sexmod/Q;)V
    //   85: aload_3
    //   86: iconst_0
    //   87: invokevirtual a : (Z)V
    //   90: aload_3
    //   91: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
    //   94: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
    //   97: goto -> 104
    //   100: invokestatic a : (Ljava/util/ConcurrentModificationException;)Ljava/util/ConcurrentModificationException;
    //   103: athrow
    //   104: aload_3
    //   105: instanceof com/schnurritv/sexmod/U
    //   108: ifeq -> 203
    //   111: aload_3
    //   112: checkcast com/schnurritv/sexmod/U
    //   115: invokevirtual u : ()Ljava/util/UUID;
    //   118: aload_1
    //   119: getfield player : Lnet/minecraft/entity/player/EntityPlayer;
    //   122: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   125: invokevirtual equals : (Ljava/lang/Object;)Z
    //   128: ifeq -> 203
    //   131: goto -> 138
    //   134: invokestatic a : (Ljava/util/ConcurrentModificationException;)Ljava/util/ConcurrentModificationException;
    //   137: athrow
    //   138: aload_3
    //   139: invokevirtual B : ()Ljava/util/UUID;
    //   142: ifnull -> 203
    //   145: goto -> 152
    //   148: invokestatic a : (Ljava/util/ConcurrentModificationException;)Ljava/util/ConcurrentModificationException;
    //   151: athrow
    //   152: aload_1
    //   153: getfield player : Lnet/minecraft/entity/player/EntityPlayer;
    //   156: getfield field_70170_p : Lnet/minecraft/world/World;
    //   159: aload_3
    //   160: invokevirtual B : ()Ljava/util/UUID;
    //   163: invokevirtual func_152378_a : (Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;
    //   166: checkcast net/minecraft/entity/player/EntityPlayerMP
    //   169: astore #4
    //   171: getstatic com/schnurritv/sexmod/bn.a : Lnet/minecraftforge/fml/common/network/simpleimpl/SimpleNetworkWrapper;
    //   174: new com/schnurritv/sexmod/b5
    //   177: dup
    //   178: iconst_1
    //   179: invokespecial <init> : (Z)V
    //   182: aload #4
    //   184: invokevirtual sendTo : (Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;Lnet/minecraft/entity/player/EntityPlayerMP;)V
    //   187: aload #4
    //   189: invokestatic a : (Lnet/minecraft/entity/player/EntityPlayerMP;)V
    //   192: aload #4
    //   194: iconst_0
    //   195: invokevirtual func_82142_c : (Z)V
    //   198: aload_3
    //   199: aconst_null
    //   200: invokevirtual d : (Ljava/util/UUID;)V
    //   203: goto -> 7
    //   206: goto -> 210
    //   209: astore_2
    //   210: return
    // Exception table:
    //   from	to	target	type
    //   0	206	209	java/util/ConcurrentModificationException
    //   26	36	36	java/util/ConcurrentModificationException
    //   40	74	77	java/util/ConcurrentModificationException
    //   57	97	100	java/util/ConcurrentModificationException
    //   104	131	134	java/util/ConcurrentModificationException
    //   111	145	148	java/util/ConcurrentModificationException
  }
  
  private static ConcurrentModificationException a(ConcurrentModificationException paramConcurrentModificationException) {
    return paramConcurrentModificationException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\aj.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */