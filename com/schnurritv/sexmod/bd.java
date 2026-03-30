package com.schnurritv.sexmod;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class bd extends GuiScreen {
  final Q d;
  
  final EntityPlayer a;
  
  final String[] b;
  
  @Nullable
  final ItemStack[] e;
  
  static final ResourceLocation j = new ResourceLocation("sexmod", "textures/gui/girlinventory.png");
  
  EntityDataManager h;
  
  final boolean m;
  
  float k = 0.0F;
  
  float c = 0.0F;
  
  String[] l = new String[] { "action.names.followme", "action.names.stopfollowme", "action.names.gohome", "action.names.setnewhome", "action.names.equipment" };
  
  int[] i = new int[] { 0, 0, 0, 0, 0 };
  
  int[] g = new int[] { 64, 80, 47, 32, 96 };
  
  int[] f = new int[] { 4, 4, 5, 5, 4 };
  
  int[] n = new int[] { 50, 90, 50, 80, 60 };
  
  public bd(Q paramQ, EntityPlayer paramEntityPlayer) {
    this.d = paramQ;
    this.a = paramEntityPlayer;
    this.b = new String[0];
    this.e = new ItemStack[0];
    this.m = true;
    this.h = paramQ.func_184212_Q();
  }
  
  public bd(Q paramQ, EntityPlayer paramEntityPlayer, String[] paramArrayOfString, @Nullable ItemStack[] paramArrayOfItemStack, boolean paramBoolean) {
    this.d = paramQ;
    this.a = paramEntityPlayer;
    this.b = paramArrayOfString;
    this.e = paramArrayOfItemStack;
    this.m = paramBoolean;
    this.h = paramQ.func_184212_Q();
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_146281_b() {
    super.func_146281_b();
    this.d.e();
  }
  
  protected void func_146284_a(GuiButton paramGuiButton) {
    try {
      if (paramGuiButton.field_146127_k >= 5)
        try {
          if (this.e != null)
            try {
              if (this.e[paramGuiButton.field_146127_k - 5] != null)
                try {
                  if (!this.a.field_71075_bZ.field_75098_d) {
                    for (ItemStack itemStack : this.a.field_71071_by.field_70462_a) {
                      try {
                        if (itemStack.func_77973_b().equals(this.e[paramGuiButton.field_146127_k - 5].func_77973_b()))
                          try {
                            if (itemStack.func_190916_E() >= this.e[paramGuiButton.field_146127_k - 5].func_190916_E())
                              try {
                                if (itemStack.func_77960_j() == this.e[paramGuiButton.field_146127_k - 5].func_77960_j()) {
                                  bn.a.sendToServer(new ah(this.a.getPersistentID(), this.e[paramGuiButton.field_146127_k - 5]));
                                  a(paramGuiButton);
                                  return;
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
                    } 
                    this.a.func_145747_a((ITextComponent)new TextComponentString("<" + this.d.func_70005_c_() + "> you cannot afford that..."));
                    this.d.a(c.GIRLS_JENNY_SADOH[1]);
                    return;
                  } 
                  a(paramGuiButton);
                  return;
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
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
    a(paramGuiButton);
  }
  
  void a(GuiButton paramGuiButton) {
    String str;
    if (paramGuiButton.field_146127_k < 5) {
      str = this.l[paramGuiButton.field_146127_k];
    } else {
      str = this.b[paramGuiButton.field_146127_k - 5];
    } 
    this.d.a(str, this.a.getPersistentID());
    (Minecraft.func_71410_x()).field_71439_g.func_71053_j();
  }
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: iload_2
    //   3: fload_3
    //   4: invokespecial func_73863_a : (IIF)V
    //   7: aload_0
    //   8: getfield field_146292_n : Ljava/util/List;
    //   11: invokeinterface clear : ()V
    //   16: new net/minecraft/client/gui/ScaledResolution
    //   19: dup
    //   20: aload_0
    //   21: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   24: invokespecial <init> : (Lnet/minecraft/client/Minecraft;)V
    //   27: astore #4
    //   29: aload #4
    //   31: invokevirtual func_78326_a : ()I
    //   34: istore #5
    //   36: aload #4
    //   38: invokevirtual func_78328_b : ()I
    //   41: istore #6
    //   43: aload_0
    //   44: fconst_1
    //   45: aload_0
    //   46: getfield k : F
    //   49: aload_0
    //   50: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   53: invokevirtual func_193989_ak : ()F
    //   56: ldc 5.0
    //   58: fdiv
    //   59: fadd
    //   60: invokestatic min : (FF)F
    //   63: putfield k : F
    //   66: aload_0
    //   67: getfield k : F
    //   70: fconst_1
    //   71: fcmpl
    //   72: ifne -> 105
    //   75: aload_0
    //   76: fconst_1
    //   77: aload_0
    //   78: getfield c : F
    //   81: aload_0
    //   82: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   85: invokevirtual func_193989_ak : ()F
    //   88: ldc 5.0
    //   90: fdiv
    //   91: fadd
    //   92: invokestatic min : (FF)F
    //   95: putfield c : F
    //   98: goto -> 105
    //   101: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   104: athrow
    //   105: ldc 115.0
    //   107: ldc 161.0
    //   109: aload_0
    //   110: getfield c : F
    //   113: invokestatic a : (FFF)F
    //   116: f2i
    //   117: istore #7
    //   119: ldc 91.0
    //   121: ldc 137.0
    //   123: aload_0
    //   124: getfield c : F
    //   127: invokestatic a : (FFF)F
    //   130: f2i
    //   131: istore #8
    //   133: ldc -30.0
    //   135: ldc 120.0
    //   137: aload_0
    //   138: getfield k : F
    //   141: invokestatic a : (FFF)F
    //   144: f2i
    //   145: istore #9
    //   147: bipush #70
    //   149: istore #10
    //   151: bipush #52
    //   153: istore #11
    //   155: bipush #68
    //   157: istore #12
    //   159: iconst_5
    //   160: istore #13
    //   162: iload #13
    //   164: aload_0
    //   165: getfield b : [Ljava/lang/String;
    //   168: arraylength
    //   169: iconst_5
    //   170: iadd
    //   171: if_icmpge -> 428
    //   174: aload_0
    //   175: getfield c : F
    //   178: fconst_0
    //   179: fcmpl
    //   180: ifle -> 364
    //   183: goto -> 190
    //   186: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   189: athrow
    //   190: aload_0
    //   191: getfield e : [Lnet/minecraft/item/ItemStack;
    //   194: ifnull -> 364
    //   197: goto -> 204
    //   200: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   203: athrow
    //   204: aload_0
    //   205: getfield e : [Lnet/minecraft/item/ItemStack;
    //   208: iload #13
    //   210: iconst_5
    //   211: isub
    //   212: aaload
    //   213: ifnull -> 364
    //   216: goto -> 223
    //   219: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   222: athrow
    //   223: aload_0
    //   224: getfield e : [Lnet/minecraft/item/ItemStack;
    //   227: iload #13
    //   229: iconst_5
    //   230: isub
    //   231: aaload
    //   232: invokevirtual func_190916_E : ()I
    //   235: ifeq -> 364
    //   238: goto -> 245
    //   241: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   244: athrow
    //   245: aload_0
    //   246: ldc -300.0
    //   248: putfield field_73735_i : F
    //   251: aload_0
    //   252: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   255: ldc -300.0
    //   257: putfield field_77023_b : F
    //   260: aload_0
    //   261: iconst_1
    //   262: anewarray java/lang/String
    //   265: dup
    //   266: iconst_0
    //   267: new java/lang/StringBuilder
    //   270: dup
    //   271: invokespecial <init> : ()V
    //   274: aload_0
    //   275: getfield e : [Lnet/minecraft/item/ItemStack;
    //   278: iload #13
    //   280: iconst_5
    //   281: isub
    //   282: aaload
    //   283: invokevirtual func_190916_E : ()I
    //   286: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   289: ldc 'x    '
    //   291: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: invokevirtual toString : ()Ljava/lang/String;
    //   297: aastore
    //   298: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
    //   301: iload #5
    //   303: iload #7
    //   305: isub
    //   306: iload #6
    //   308: iload #11
    //   310: isub
    //   311: aload_0
    //   312: getfield field_146289_q : Lnet/minecraft/client/gui/FontRenderer;
    //   315: invokevirtual a : (Ljava/util/List;IILnet/minecraft/client/gui/FontRenderer;)V
    //   318: aload_0
    //   319: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   322: aload_0
    //   323: getfield e : [Lnet/minecraft/item/ItemStack;
    //   326: iload #13
    //   328: iconst_5
    //   329: isub
    //   330: aaload
    //   331: iload #5
    //   333: iload #8
    //   335: isub
    //   336: iload #6
    //   338: iload #12
    //   340: isub
    //   341: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   344: aload_0
    //   345: fconst_0
    //   346: putfield field_73735_i : F
    //   349: aload_0
    //   350: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   353: fconst_0
    //   354: putfield field_77023_b : F
    //   357: goto -> 364
    //   360: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   363: athrow
    //   364: aload_0
    //   365: getfield field_146292_n : Ljava/util/List;
    //   368: new net/minecraft/client/gui/GuiButton
    //   371: dup
    //   372: iload #13
    //   374: iload #5
    //   376: iload #9
    //   378: isub
    //   379: iload #6
    //   381: iload #10
    //   383: isub
    //   384: bipush #100
    //   386: bipush #20
    //   388: aload_0
    //   389: getfield b : [Ljava/lang/String;
    //   392: iload #13
    //   394: iconst_5
    //   395: isub
    //   396: aaload
    //   397: iconst_0
    //   398: anewarray java/lang/Object
    //   401: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   404: invokespecial <init> : (IIIIILjava/lang/String;)V
    //   407: invokeinterface add : (Ljava/lang/Object;)Z
    //   412: pop
    //   413: iinc #10, 30
    //   416: iinc #11, 30
    //   419: iinc #12, 30
    //   422: iinc #13, 1
    //   425: goto -> 162
    //   428: aload_0
    //   429: getfield m : Z
    //   432: ifeq -> 448
    //   435: aload_0
    //   436: iload_1
    //   437: iload_2
    //   438: invokevirtual a : (II)V
    //   441: goto -> 448
    //   444: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   447: athrow
    //   448: return
    // Exception table:
    //   from	to	target	type
    //   43	98	101	java/lang/NullPointerException
    //   162	183	186	java/lang/NullPointerException
    //   174	197	200	java/lang/NullPointerException
    //   190	216	219	java/lang/NullPointerException
    //   204	238	241	java/lang/NullPointerException
    //   223	357	360	java/lang/NullPointerException
    //   428	441	444	java/lang/NullPointerException
  }
  
  void a(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: ldc -30.0
    //   2: ldc 120.0
    //   4: aload_0
    //   5: getfield k : F
    //   8: invokestatic a : (FFF)F
    //   11: f2i
    //   12: istore_3
    //   13: aload_0
    //   14: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   17: aload_0
    //   18: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   21: getstatic com/schnurritv/sexmod/S.M : Lnet/minecraft/network/datasync/DataParameter;
    //   24: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   27: checkcast net/minecraft/item/ItemStack
    //   30: iload_3
    //   31: bipush #105
    //   33: isub
    //   34: bipush #68
    //   36: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   39: aload_0
    //   40: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   43: aload_0
    //   44: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   47: getstatic com/schnurritv/sexmod/S.G : Lnet/minecraft/network/datasync/DataParameter;
    //   50: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   53: checkcast net/minecraft/item/ItemStack
    //   56: iload_3
    //   57: bipush #105
    //   59: isub
    //   60: bipush #87
    //   62: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   65: aload_0
    //   66: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   69: aload_0
    //   70: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   73: getstatic com/schnurritv/sexmod/S.L : Lnet/minecraft/network/datasync/DataParameter;
    //   76: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   79: checkcast net/minecraft/item/ItemStack
    //   82: iload_3
    //   83: bipush #105
    //   85: isub
    //   86: bipush #109
    //   88: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   91: aload_0
    //   92: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   95: aload_0
    //   96: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   99: getstatic com/schnurritv/sexmod/S.I : Lnet/minecraft/network/datasync/DataParameter;
    //   102: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   105: checkcast net/minecraft/item/ItemStack
    //   108: iload_3
    //   109: bipush #105
    //   111: isub
    //   112: bipush #127
    //   114: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   117: aload_0
    //   118: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   121: aload_0
    //   122: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   125: getstatic com/schnurritv/sexmod/S.K : Lnet/minecraft/network/datasync/DataParameter;
    //   128: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   131: checkcast net/minecraft/item/ItemStack
    //   134: iload_3
    //   135: bipush #105
    //   137: isub
    //   138: sipush #146
    //   141: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   144: aload_0
    //   145: getfield field_146296_j : Lnet/minecraft/client/renderer/RenderItem;
    //   148: aload_0
    //   149: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   152: getstatic com/schnurritv/sexmod/S.P : Lnet/minecraft/network/datasync/DataParameter;
    //   155: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   158: checkcast net/minecraft/item/ItemStack
    //   161: iload_3
    //   162: bipush #105
    //   164: isub
    //   165: sipush #166
    //   168: invokevirtual func_175042_a : (Lnet/minecraft/item/ItemStack;II)V
    //   171: aload_0
    //   172: getfield c : F
    //   175: fconst_0
    //   176: fcmpl
    //   177: ifne -> 185
    //   180: return
    //   181: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   184: athrow
    //   185: aload_0
    //   186: getfield h : Lnet/minecraft/network/datasync/EntityDataManager;
    //   189: getstatic com/schnurritv/sexmod/Q.p : Lnet/minecraft/network/datasync/DataParameter;
    //   192: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   195: checkcast java/lang/String
    //   198: ldc ''
    //   200: invokevirtual equals : (Ljava/lang/Object;)Z
    //   203: ifne -> 214
    //   206: iconst_1
    //   207: goto -> 215
    //   210: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   213: athrow
    //   214: iconst_0
    //   215: istore #4
    //   217: bipush #35
    //   219: istore #5
    //   221: bipush #70
    //   223: istore #6
    //   225: iconst_0
    //   226: istore #7
    //   228: iload #7
    //   230: iconst_5
    //   231: if_icmpge -> 595
    //   234: iload #7
    //   236: ifne -> 264
    //   239: goto -> 246
    //   242: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   245: athrow
    //   246: iload #4
    //   248: ifeq -> 264
    //   251: goto -> 258
    //   254: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   257: athrow
    //   258: iconst_1
    //   259: istore #7
    //   261: goto -> 285
    //   264: iload #7
    //   266: iconst_1
    //   267: if_icmpne -> 285
    //   270: iload #4
    //   272: ifne -> 285
    //   275: goto -> 282
    //   278: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   281: athrow
    //   282: iconst_2
    //   283: istore #7
    //   285: iload_1
    //   286: iload #5
    //   288: if_icmplt -> 378
    //   291: iload_1
    //   292: iload #5
    //   294: bipush #23
    //   296: iadd
    //   297: aload_0
    //   298: getfield i : [I
    //   301: iload #7
    //   303: iaload
    //   304: iadd
    //   305: if_icmpgt -> 378
    //   308: goto -> 315
    //   311: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   314: athrow
    //   315: iload_2
    //   316: iload #6
    //   318: if_icmplt -> 378
    //   321: goto -> 328
    //   324: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   327: athrow
    //   328: iload_2
    //   329: iload #6
    //   331: bipush #20
    //   333: iadd
    //   334: if_icmpgt -> 378
    //   337: goto -> 344
    //   340: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   343: athrow
    //   344: aload_0
    //   345: getfield i : [I
    //   348: iload #7
    //   350: aload_0
    //   351: getfield n : [I
    //   354: iload #7
    //   356: iaload
    //   357: aload_0
    //   358: getfield i : [I
    //   361: iload #7
    //   363: iaload
    //   364: bipush #7
    //   366: iadd
    //   367: invokestatic min : (II)I
    //   370: iastore
    //   371: goto -> 399
    //   374: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   377: athrow
    //   378: aload_0
    //   379: getfield i : [I
    //   382: iload #7
    //   384: iconst_0
    //   385: aload_0
    //   386: getfield i : [I
    //   389: iload #7
    //   391: iaload
    //   392: bipush #7
    //   394: isub
    //   395: invokestatic max : (II)I
    //   398: iastore
    //   399: new java/lang/StringBuilder
    //   402: dup
    //   403: aload_0
    //   404: getfield l : [Ljava/lang/String;
    //   407: iload #7
    //   409: aaload
    //   410: iconst_0
    //   411: anewarray java/lang/Object
    //   414: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   417: invokespecial <init> : (Ljava/lang/String;)V
    //   420: astore #8
    //   422: iconst_0
    //   423: istore #9
    //   425: iload #9
    //   427: aload_0
    //   428: getfield f : [I
    //   431: iload #7
    //   433: iaload
    //   434: if_icmpge -> 455
    //   437: aload #8
    //   439: ldc ' '
    //   441: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   444: pop
    //   445: iinc #9, 1
    //   448: goto -> 425
    //   451: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   454: athrow
    //   455: aload_0
    //   456: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   459: getfield field_71446_o : Lnet/minecraft/client/renderer/texture/TextureManager;
    //   462: getstatic com/schnurritv/sexmod/bd.j : Lnet/minecraft/util/ResourceLocation;
    //   465: invokevirtual func_110577_a : (Lnet/minecraft/util/ResourceLocation;)V
    //   468: aload_0
    //   469: aload_0
    //   470: getfield i : [I
    //   473: iload #7
    //   475: iaload
    //   476: iload #5
    //   478: iadd
    //   479: bipush #18
    //   481: isub
    //   482: fconst_0
    //   483: ldc 23.0
    //   485: aload_0
    //   486: getfield c : F
    //   489: invokestatic a : (FFF)F
    //   492: f2i
    //   493: iadd
    //   494: iload #6
    //   496: iconst_2
    //   497: iadd
    //   498: aload_0
    //   499: getfield g : [I
    //   502: iload #7
    //   504: iaload
    //   505: iconst_0
    //   506: bipush #16
    //   508: bipush #16
    //   510: invokevirtual func_73729_b : (IIIIII)V
    //   513: aload_0
    //   514: getfield field_146292_n : Ljava/util/List;
    //   517: new net/minecraft/client/gui/GuiButton
    //   520: dup
    //   521: iload #7
    //   523: iload #5
    //   525: iconst_1
    //   526: iadd
    //   527: iload #6
    //   529: fconst_0
    //   530: ldc 23.0
    //   532: aload_0
    //   533: getfield c : F
    //   536: invokestatic a : (FFF)F
    //   539: aload_0
    //   540: getfield i : [I
    //   543: iload #7
    //   545: iaload
    //   546: i2f
    //   547: fadd
    //   548: f2i
    //   549: bipush #20
    //   551: aload_0
    //   552: getfield i : [I
    //   555: iload #7
    //   557: iaload
    //   558: bipush #14
    //   560: if_icmpgt -> 572
    //   563: ldc ''
    //   565: goto -> 577
    //   568: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   571: athrow
    //   572: aload #8
    //   574: invokevirtual toString : ()Ljava/lang/String;
    //   577: invokespecial <init> : (IIIIILjava/lang/String;)V
    //   580: invokeinterface add : (Ljava/lang/Object;)Z
    //   585: pop
    //   586: iinc #6, 30
    //   589: iinc #7, 1
    //   592: goto -> 228
    //   595: aload_0
    //   596: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   599: getfield field_71446_o : Lnet/minecraft/client/renderer/texture/TextureManager;
    //   602: getstatic com/schnurritv/sexmod/bd.j : Lnet/minecraft/util/ResourceLocation;
    //   605: invokevirtual func_110577_a : (Lnet/minecraft/util/ResourceLocation;)V
    //   608: aload_0
    //   609: iload_3
    //   610: bipush #113
    //   612: isub
    //   613: bipush #60
    //   615: iconst_0
    //   616: iconst_0
    //   617: bipush #32
    //   619: sipush #130
    //   622: invokevirtual func_73729_b : (IIIIII)V
    //   625: return
    // Exception table:
    //   from	to	target	type
    //   13	181	181	java/lang/NullPointerException
    //   185	210	210	java/lang/NullPointerException
    //   228	239	242	java/lang/NullPointerException
    //   234	251	254	java/lang/NullPointerException
    //   264	275	278	java/lang/NullPointerException
    //   285	308	311	java/lang/NullPointerException
    //   291	321	324	java/lang/NullPointerException
    //   315	337	340	java/lang/NullPointerException
    //   328	374	374	java/lang/NullPointerException
    //   425	451	451	java/lang/NullPointerException
    //   455	568	568	java/lang/NullPointerException
  }
  
  void a(List<String> paramList, int paramInt1, int paramInt2, FontRenderer paramFontRenderer) {
    GlStateManager.func_179101_C();
    RenderHelper.func_74518_a();
    GlStateManager.func_179140_f();
    int i = 0;
    for (String str : paramList) {
      int n = this.field_146289_q.func_78256_a(str);
      if (n > i)
        i = n; 
    } 
    int j = paramInt1 + 12;
    int k = paramInt2 - 12;
    int m = 8;
    if (paramList.size() > 1)
      m += 2 + (paramList.size() - 1) * 10; 
    if (j + i > this.field_146294_l)
      j -= 28 + i; 
    if (k + m + 6 > this.field_146295_m)
      k = this.field_146295_m - m - 6; 
    func_73733_a(j - 3, k - 4, j + i + 3, k - 3, -267386864, -267386864);
    func_73733_a(j - 3, k + m + 3, j + i + 3, k + m + 4, -267386864, -267386864);
    func_73733_a(j - 3, k - 3, j + i + 3, k + m + 3, -267386864, -267386864);
    func_73733_a(j - 4, k - 3, j - 3, k + m + 3, -267386864, -267386864);
    func_73733_a(j + i + 3, k - 3, j + i + 4, k + m + 3, -267386864, -267386864);
    func_73733_a(j - 3, k - 3 + 1, j - 3 + 1, k + m + 3 - 1, 1347420415, 1344798847);
    func_73733_a(j + i + 2, k - 3 + 1, j + i + 3, k + m + 3 - 1, 1347420415, 1344798847);
    func_73733_a(j - 3, k - 3, j + i + 3, k - 3 + 1, 1347420415, 1347420415);
    func_73733_a(j - 3, k + m + 2, j + i + 3, k + m + 3, 1344798847, 1344798847);
    for (byte b = 0; b < paramList.size(); b++) {
      String str = paramList.get(b);
      try {
        this.field_146289_q.func_175063_a(str, j, k, -1);
        if (b == 0)
          k += 2; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      k += 10;
    } 
    GlStateManager.func_179145_e();
    RenderHelper.func_74519_b();
    GlStateManager.func_179091_B();
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */