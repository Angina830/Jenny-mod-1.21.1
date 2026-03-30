package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class b implements IMessage {
  boolean a;
  
  UUID d;
  
  String c;
  
  String b;
  
  public b() {
    this.a = false;
  }
  
  public b(UUID paramUUID, String paramString1, String paramString2) {
    this.d = paramUUID;
    this.c = paramString1;
    this.b = paramString2;
    this.a = true;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.d = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = ByteBufUtils.readUTF8String(paramByteBuf);
    this.b = ByteBufUtils.readUTF8String(paramByteBuf);
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    try {
      ByteBufUtils.writeUTF8String(paramByteBuf, this.d.toString());
      ByteBufUtils.writeUTF8String(paramByteBuf, this.c);
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    ByteBufUtils.writeUTF8String(paramByteBuf, (this.b == null) ? "null" : this.b);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public static class a implements IMessageHandler<b, IMessage> {
    public IMessage a(b param1b, MessageContext param1MessageContext) {
      try {
        if (!param1b.a) {
          System.out.println("received an invalid message @ChangeDataParameter :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
            for (Q q : Q.a(param1b.d)) {
              try {
                if (q.field_70170_p.field_72995_K)
                  continue; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (!param1b.d.equals(q.p()))
                  continue; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              String str = param1b.c;
              byte b1 = -1;
              try {
                switch (str.hashCode()) {
                  case -1287492899:
                    if (str.equals("pregnant"))
                      b1 = 0; 
                    break;
                  case 1452134704:
                    if (str.equals("currentModel"))
                      b1 = 1; 
                    break;
                  case 1712351503:
                    if (str.equals("currentAction"))
                      b1 = 2; 
                    break;
                  case 679533008:
                    if (str.equals("animationFollowUp"))
                      b1 = 3; 
                    break;
                  case -1206220351:
                    if (str.equals("playerSheHasSexWith"))
                      b1 = 4; 
                    break;
                  case -815590653:
                    if (str.equals("targetPos"))
                      b1 = 5; 
                    break;
                  case -1081267614:
                    if (str.equals("master"))
                      b1 = 6; 
                    break;
                  case -395351248:
                    if (str.equals("walk speed"))
                      b1 = 7; 
                    break;
                  case -1243330214:
                    if (str.equals("shouldbeattargetpos"))
                      b1 = 8; 
                    break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                String[] arrayOfString;
                Vec3d vec3d;
                switch (b1) {
                  case 0:
                    q.func_184212_Q().func_187227_b(aF.J, Integer.valueOf(param1b.b));
                  case 1:
                    q.func_184212_Q().func_187227_b(Q.v, Integer.valueOf(param1b.b));
                  case 2:
                    try {
                      if (b1.valueOf(param1b.b) == b1.ATTACK)
                        try {
                          if (q.h() != b1.NULL)
                            continue; 
                        } catch (NullPointerException nullPointerException) {
                          throw a(null);
                        }  
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    q.b(b1.valueOf(param1b.b));
                  case 3:
                    q.func_184212_Q().func_187227_b(Q.f, param1b.b);
                  case 4:
                    try {
                      if (param1b.b.equals("null")) {
                        q.d((UUID)null);
                        continue;
                      } 
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    q.d(UUID.fromString(param1b.b));
                  case 5:
                    arrayOfString = param1b.b.split("f");
                    vec3d = new Vec3d(Double.parseDouble(arrayOfString[0]), Double.parseDouble(arrayOfString[1]), Double.parseDouble(arrayOfString[2]));
                    q.a(vec3d);
                  case 6:
                    q.func_184212_Q().func_187227_b(Q.p, param1b.b);
                  case 7:
                    q.func_184212_Q().func_187227_b(Q.C, param1b.b);
                  case 8:
                    q.func_184212_Q().func_187227_b(Q.c, Boolean.valueOf(param1b.b));
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
            } 
          });
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */