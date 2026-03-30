package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import java.util.UUID;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class e implements IMessage {
  boolean e;
  
  UUID d;
  
  boolean c;
  
  boolean b;
  
  UUID a = null;
  
  public e() {
    this.e = false;
  }
  
  public e(UUID paramUUID1, UUID paramUUID2, boolean paramBoolean1, boolean paramBoolean2) {
    this.d = paramUUID1;
    this.c = paramBoolean1;
    this.a = paramUUID2;
    this.b = paramBoolean2;
    this.e = true;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.d = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = paramByteBuf.readBoolean();
    this.b = paramByteBuf.readBoolean();
    String str = ByteBufUtils.readUTF8String(paramByteBuf);
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.a = str.equals("null") ? null : UUID.fromString(str);
    this.e = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    try {
      ByteBufUtils.writeUTF8String(paramByteBuf, this.d.toString());
      paramByteBuf.writeBoolean(this.c);
      paramByteBuf.writeBoolean(this.b);
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    ByteBufUtils.writeUTF8String(paramByteBuf, (this.a == null) ? "null" : this.a.toString());
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public static class a implements IMessageHandler<e, IMessage> {
    public static void a(UUID param1UUID1, UUID param1UUID2, boolean param1Boolean1, boolean param1Boolean2) {
      // Byte code:
      //   0: aload_0
      //   1: invokestatic a : (Ljava/util/UUID;)Ljava/util/ArrayList;
      //   4: invokevirtual iterator : ()Ljava/util/Iterator;
      //   7: astore #4
      //   9: aload #4
      //   11: invokeinterface hasNext : ()Z
      //   16: ifeq -> 233
      //   19: aload #4
      //   21: invokeinterface next : ()Ljava/lang/Object;
      //   26: checkcast com/schnurritv/sexmod/Q
      //   29: astore #5
      //   31: aload #5
      //   33: getfield field_70170_p : Lnet/minecraft/world/World;
      //   36: getfield field_72995_K : Z
      //   39: ifeq -> 49
      //   42: goto -> 9
      //   45: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   48: athrow
      //   49: aload #5
      //   51: instanceof com/schnurritv/sexmod/aX
      //   54: ifne -> 87
      //   57: aload #5
      //   59: instanceof com/schnurritv/sexmod/ae
      //   62: ifne -> 87
      //   65: goto -> 72
      //   68: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   71: athrow
      //   72: aload #5
      //   74: instanceof com/schnurritv/sexmod/aI
      //   77: ifeq -> 120
      //   80: goto -> 87
      //   83: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   86: athrow
      //   87: aload #5
      //   89: getfield field_70714_bg : Lnet/minecraft/entity/ai/EntityAITasks;
      //   92: aload #5
      //   94: getfield w : Lcom/schnurritv/sexmod/k;
      //   97: invokevirtual func_85156_a : (Lnet/minecraft/entity/ai/EntityAIBase;)V
      //   100: aload #5
      //   102: getfield field_70714_bg : Lnet/minecraft/entity/ai/EntityAITasks;
      //   105: aload #5
      //   107: getfield k : Lnet/minecraft/entity/ai/EntityAIWanderAvoidWater;
      //   110: invokevirtual func_85156_a : (Lnet/minecraft/entity/ai/EntityAIBase;)V
      //   113: goto -> 120
      //   116: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   119: athrow
      //   120: aload #5
      //   122: invokevirtual func_70661_as : ()Lnet/minecraft/pathfinding/PathNavigate;
      //   125: invokevirtual func_75499_g : ()V
      //   128: aload #5
      //   130: dconst_0
      //   131: putfield field_70159_w : D
      //   134: aload #5
      //   136: dconst_0
      //   137: putfield field_70181_x : D
      //   140: aload #5
      //   142: dconst_0
      //   143: putfield field_70179_y : D
      //   146: aload #5
      //   148: invokevirtual B : ()Ljava/util/UUID;
      //   151: ifnonnull -> 167
      //   154: aload #5
      //   156: aload_1
      //   157: invokevirtual d : (Ljava/util/UUID;)V
      //   160: goto -> 167
      //   163: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   166: athrow
      //   167: iload_3
      //   168: ifeq -> 188
      //   171: aload #5
      //   173: aload #5
      //   175: invokevirtual v : ()Lnet/minecraft/util/math/Vec3d;
      //   178: invokevirtual a : (Lnet/minecraft/util/math/Vec3d;)V
      //   181: goto -> 188
      //   184: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   187: athrow
      //   188: aload #5
      //   190: aload #5
      //   192: invokevirtual B : ()Ljava/util/UUID;
      //   195: invokevirtual f : (Ljava/util/UUID;)V
      //   198: iload_2
      //   199: ifne -> 203
      //   202: return
      //   203: aload #5
      //   205: instanceof com/schnurritv/sexmod/as
      //   208: ifne -> 216
      //   211: return
      //   212: invokestatic a : (Ljava/lang/RuntimeException;)Ljava/lang/RuntimeException;
      //   215: athrow
      //   216: aload #5
      //   218: checkcast com/schnurritv/sexmod/as
      //   221: astore #6
      //   223: aload #6
      //   225: invokeinterface b : ()V
      //   230: goto -> 9
      //   233: goto -> 238
      //   236: astore #4
      //   238: return
      // Exception table:
      //   from	to	target	type
      //   0	202	236	java/util/ConcurrentModificationException
      //   31	45	45	java/util/ConcurrentModificationException
      //   49	65	68	java/util/ConcurrentModificationException
      //   57	80	83	java/util/ConcurrentModificationException
      //   72	113	116	java/util/ConcurrentModificationException
      //   120	160	163	java/util/ConcurrentModificationException
      //   167	181	184	java/util/ConcurrentModificationException
      //   203	211	236	java/util/ConcurrentModificationException
      //   203	212	212	java/lang/NullPointerException
      //   216	233	236	java/util/ConcurrentModificationException
    }
    
    public IMessage a(e param1e, MessageContext param1MessageContext) {
      try {
        if (param1e.e)
          try {
            if (param1MessageContext.side == Side.SERVER)
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> a(param1e.d, param1e.a, param1e.c, param1e.b)); 
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      return null;
    }
    
    private static RuntimeException a(RuntimeException param1RuntimeException) {
      return param1RuntimeException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */