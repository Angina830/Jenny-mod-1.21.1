package com.schnurritv.sexmod;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ch extends GuiScreen {
  static final float m = 100.0F;
  
  static final float k = 15.0F;
  
  static final float f = 0.5F;
  
  static final ResourceLocation e = new ResourceLocation("sexmod", "textures/gui/command.png");
  
  static final HashSet<Material> j = new HashSet<>(Arrays.asList(new Material[] { Material.field_151571_B, Material.field_151576_e, Material.field_151595_p, Material.field_151578_c }));
  
  public static boolean h = false;
  
  float n = 0.0F;
  
  float c = 0.0F;
  
  float i = 0.0F;
  
  float d = 0.0F;
  
  float b = 0.0F;
  
  IBlockState g;
  
  BlockPos l;
  
  EnumFacing a;
  
  public ch() {
    Minecraft minecraft = Minecraft.func_71410_x();
    try {
      this.l = minecraft.field_71476_x.func_178782_a();
      if (minecraft.field_71476_x.field_178784_b == null) {
        this.a = EnumFacing.NORTH;
      } else {
        this.a = minecraft.field_71476_x.field_178784_b.func_176734_d();
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.l == null)
        this.l = BlockPos.field_177992_a; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.g = minecraft.field_71441_e.func_180495_p(this.l);
  }
  
  public void func_146281_b() {
    super.func_146281_b();
    List<Float> list = Arrays.asList(new Float[] { Float.valueOf(this.c), Float.valueOf(this.i), Float.valueOf(this.d), Float.valueOf(this.b) });
    float f = ((Float)Collections.<Float>max(list)).floatValue();
    try {
      if (f == 0.0F)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.c == f)
        b(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.i == f)
        e(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.d == f)
        a(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.b == f)
        d(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void b() {
    // Byte code:
    //   0: aload_0
    //   1: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   4: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   7: aload_0
    //   8: getfield l : Lnet/minecraft/util/math/BlockPos;
    //   11: invokevirtual func_180495_p : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
    //   14: astore_1
    //   15: aload_1
    //   16: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   21: instanceof net/minecraft/block/BlockBed
    //   24: ifne -> 46
    //   27: aload_1
    //   28: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   33: instanceof net/minecraft/block/BlockChest
    //   36: ifeq -> 89
    //   39: goto -> 46
    //   42: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   45: athrow
    //   46: getstatic com/schnurritv/sexmod/bn.a : Lnet/minecraftforge/fml/common/network/simpleimpl/SimpleNetworkWrapper;
    //   49: new com/schnurritv/sexmod/bg
    //   52: dup
    //   53: aload_0
    //   54: getfield l : Lnet/minecraft/util/math/BlockPos;
    //   57: aload_0
    //   58: getfield l : Lnet/minecraft/util/math/BlockPos;
    //   61: invokestatic b : (Lnet/minecraft/util/math/BlockPos;)Z
    //   64: ifne -> 82
    //   67: goto -> 74
    //   70: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   73: athrow
    //   74: iconst_1
    //   75: goto -> 83
    //   78: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   81: athrow
    //   82: iconst_0
    //   83: invokespecial <init> : (Lnet/minecraft/util/math/BlockPos;Z)V
    //   86: invokevirtual sendToServer : (Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;)V
    //   89: return
    // Exception table:
    //   from	to	target	type
    //   15	39	42	java/lang/NullPointerException
    //   27	67	70	java/lang/NullPointerException
    //   46	78	78	java/lang/NullPointerException
  }
  
  void e() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    bn.a.sendToServer(new be(!h));
  }
  
  void a() {
    bv.b();
  }
  
  void d() {
    Block block = this.g.func_177230_c();
    try {
      if (block instanceof net.minecraft.block.BlockLog) {
        try {
          if (bX.b(this.l)) {
            bn.a.sendToServer(new H(this.l));
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        bn.a.sendToServer(new ak(this.l));
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Object[] arrayOfObject = c();
    try {
      if (arrayOfObject != null) {
        try {
          if (bX.b(this.l)) {
            bn.a.sendToServer(new H(this.l));
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        bn.a.sendToServer(new t((BlockPos)arrayOfObject[0], (EnumFacing)arrayOfObject[1]));
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @Nullable
  Object[] c() {
    Material material = this.field_146297_k.field_71441_e.func_180495_p(this.l).func_185904_a();
    EntityPlayerSP entityPlayerSP = this.field_146297_k.field_71439_g;
    try {
      if (!j.contains(material))
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (entityPlayerSP.func_180425_c().func_177956_o() > this.l.func_177956_o())
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    BlockPos blockPos;
    for (blockPos = this.l; this.field_146297_k.field_71441_e.func_180495_p(blockPos.func_177977_b().func_177971_a(this.a.func_176734_d().func_176730_m())).func_177230_c() == Blocks.field_150350_a; blockPos = blockPos.func_177977_b());
    try {
      if (this.l.func_177956_o() - blockPos.func_177956_o() > 3)
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return new Object[] { blockPos, this.a };
  }
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: iload_2
    //   3: fload_3
    //   4: invokespecial func_73863_a : (IIF)V
    //   7: sipush #3042
    //   10: invokestatic glEnable : (I)V
    //   13: sipush #770
    //   16: sipush #771
    //   19: iconst_1
    //   20: iconst_0
    //   21: invokestatic func_148821_a : (IIII)V
    //   24: sipush #770
    //   27: sipush #771
    //   30: invokestatic glBlendFunc : (II)V
    //   33: aload_0
    //   34: fconst_1
    //   35: aload_0
    //   36: getfield n : F
    //   39: aload_0
    //   40: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   43: invokevirtual func_193989_ak : ()F
    //   46: ldc 5.0
    //   48: fdiv
    //   49: fadd
    //   50: invokestatic min : (FF)F
    //   53: putfield n : F
    //   56: goto -> 61
    //   59: astore #4
    //   61: aload_0
    //   62: aload_0
    //   63: getfield n : F
    //   66: f2d
    //   67: invokevirtual a : (D)D
    //   70: d2f
    //   71: fstore #4
    //   73: fconst_1
    //   74: fload #4
    //   76: fsub
    //   77: ldc 100.0
    //   79: fmul
    //   80: fstore #5
    //   82: aload_0
    //   83: dup
    //   84: getfield c : F
    //   87: iload_1
    //   88: aload_0
    //   89: getfield field_146294_l : I
    //   92: iconst_2
    //   93: idiv
    //   94: if_icmpge -> 122
    //   97: iload_2
    //   98: aload_0
    //   99: getfield field_146295_m : I
    //   102: iconst_2
    //   103: idiv
    //   104: if_icmple -> 122
    //   107: goto -> 114
    //   110: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   113: athrow
    //   114: iconst_1
    //   115: goto -> 123
    //   118: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   121: athrow
    //   122: iconst_m1
    //   123: i2f
    //   124: aload_0
    //   125: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   128: invokevirtual func_193989_ak : ()F
    //   131: fmul
    //   132: fadd
    //   133: putfield c : F
    //   136: aload_0
    //   137: dup
    //   138: getfield i : F
    //   141: iload_1
    //   142: aload_0
    //   143: getfield field_146294_l : I
    //   146: iconst_2
    //   147: idiv
    //   148: if_icmpge -> 176
    //   151: iload_2
    //   152: aload_0
    //   153: getfield field_146295_m : I
    //   156: iconst_2
    //   157: idiv
    //   158: if_icmpge -> 176
    //   161: goto -> 168
    //   164: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   167: athrow
    //   168: iconst_1
    //   169: goto -> 177
    //   172: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   175: athrow
    //   176: iconst_m1
    //   177: i2f
    //   178: aload_0
    //   179: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   182: invokevirtual func_193989_ak : ()F
    //   185: fmul
    //   186: fadd
    //   187: putfield i : F
    //   190: aload_0
    //   191: dup
    //   192: getfield d : F
    //   195: iload_1
    //   196: aload_0
    //   197: getfield field_146294_l : I
    //   200: iconst_2
    //   201: idiv
    //   202: if_icmple -> 230
    //   205: iload_2
    //   206: aload_0
    //   207: getfield field_146295_m : I
    //   210: iconst_2
    //   211: idiv
    //   212: if_icmple -> 230
    //   215: goto -> 222
    //   218: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   221: athrow
    //   222: iconst_1
    //   223: goto -> 231
    //   226: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   229: athrow
    //   230: iconst_m1
    //   231: i2f
    //   232: aload_0
    //   233: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   236: invokevirtual func_193989_ak : ()F
    //   239: fmul
    //   240: fadd
    //   241: putfield d : F
    //   244: aload_0
    //   245: dup
    //   246: getfield b : F
    //   249: iload_1
    //   250: aload_0
    //   251: getfield field_146294_l : I
    //   254: iconst_2
    //   255: idiv
    //   256: if_icmple -> 284
    //   259: iload_2
    //   260: aload_0
    //   261: getfield field_146295_m : I
    //   264: iconst_2
    //   265: idiv
    //   266: if_icmpge -> 284
    //   269: goto -> 276
    //   272: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   275: athrow
    //   276: iconst_1
    //   277: goto -> 285
    //   280: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   283: athrow
    //   284: iconst_m1
    //   285: i2f
    //   286: aload_0
    //   287: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   290: invokevirtual func_193989_ak : ()F
    //   293: fmul
    //   294: fadd
    //   295: putfield b : F
    //   298: aload_0
    //   299: aload_0
    //   300: getfield c : F
    //   303: fconst_0
    //   304: fconst_1
    //   305: invokestatic b : (FFF)F
    //   308: putfield c : F
    //   311: aload_0
    //   312: aload_0
    //   313: getfield i : F
    //   316: fconst_0
    //   317: fconst_1
    //   318: invokestatic b : (FFF)F
    //   321: putfield i : F
    //   324: aload_0
    //   325: aload_0
    //   326: getfield d : F
    //   329: fconst_0
    //   330: fconst_1
    //   331: invokestatic b : (FFF)F
    //   334: putfield d : F
    //   337: aload_0
    //   338: aload_0
    //   339: getfield b : F
    //   342: fconst_0
    //   343: fconst_1
    //   344: invokestatic b : (FFF)F
    //   347: putfield b : F
    //   350: invokestatic func_179094_E : ()V
    //   353: aload_0
    //   354: getfield field_146294_l : I
    //   357: i2f
    //   358: fconst_2
    //   359: fdiv
    //   360: aload_0
    //   361: getfield field_146295_m : I
    //   364: i2f
    //   365: fconst_2
    //   366: fdiv
    //   367: fconst_0
    //   368: invokestatic func_179109_b : (FFF)V
    //   371: fload #4
    //   373: fload #4
    //   375: fload #4
    //   377: invokestatic func_179152_a : (FFF)V
    //   380: aload_0
    //   381: getfield field_146297_k : Lnet/minecraft/client/Minecraft;
    //   384: getfield field_71446_o : Lnet/minecraft/client/renderer/texture/TextureManager;
    //   387: getstatic com/schnurritv/sexmod/ch.e : Lnet/minecraft/util/ResourceLocation;
    //   390: invokevirtual func_110577_a : (Lnet/minecraft/util/ResourceLocation;)V
    //   393: invokestatic func_179094_E : ()V
    //   396: fconst_1
    //   397: aload_0
    //   398: getfield i : F
    //   401: ldc 0.5
    //   403: fmul
    //   404: fadd
    //   405: fconst_1
    //   406: aload_0
    //   407: getfield i : F
    //   410: ldc 0.5
    //   412: fmul
    //   413: fadd
    //   414: fconst_1
    //   415: invokestatic func_179152_a : (FFF)V
    //   418: aload_0
    //   419: ldc -62.0
    //   421: fload #5
    //   423: fadd
    //   424: aload_0
    //   425: getfield i : F
    //   428: ldc 15.0
    //   430: fmul
    //   431: fsub
    //   432: ldc -62.0
    //   434: fload #5
    //   436: fadd
    //   437: aload_0
    //   438: getfield i : F
    //   441: ldc 15.0
    //   443: fmul
    //   444: fsub
    //   445: iconst_0
    //   446: iconst_0
    //   447: bipush #64
    //   449: bipush #64
    //   451: invokevirtual func_175174_a : (FFIIII)V
    //   454: aload_0
    //   455: fload #5
    //   457: invokevirtual f : (F)V
    //   460: getstatic com/schnurritv/sexmod/ch.h : Z
    //   463: ifeq -> 512
    //   466: aload_0
    //   467: ldc -62.0
    //   469: fload #5
    //   471: fadd
    //   472: aload_0
    //   473: getfield i : F
    //   476: ldc 15.0
    //   478: fmul
    //   479: fsub
    //   480: ldc -62.0
    //   482: fload #5
    //   484: fadd
    //   485: aload_0
    //   486: getfield i : F
    //   489: ldc 15.0
    //   491: fmul
    //   492: fsub
    //   493: sipush #128
    //   496: bipush #64
    //   498: bipush #64
    //   500: bipush #64
    //   502: invokevirtual func_175174_a : (FFIIII)V
    //   505: goto -> 512
    //   508: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   511: athrow
    //   512: invokestatic func_179121_F : ()V
    //   515: invokestatic func_179094_E : ()V
    //   518: fconst_1
    //   519: aload_0
    //   520: getfield d : F
    //   523: ldc 0.5
    //   525: fmul
    //   526: fadd
    //   527: fconst_1
    //   528: aload_0
    //   529: getfield d : F
    //   532: ldc 0.5
    //   534: fmul
    //   535: fadd
    //   536: fconst_1
    //   537: invokestatic func_179152_a : (FFF)V
    //   540: aload_0
    //   541: ldc -2.0
    //   543: fload #5
    //   545: fsub
    //   546: aload_0
    //   547: getfield d : F
    //   550: ldc 15.0
    //   552: fmul
    //   553: fadd
    //   554: ldc -2.0
    //   556: fload #5
    //   558: fsub
    //   559: aload_0
    //   560: getfield d : F
    //   563: ldc 15.0
    //   565: fmul
    //   566: fadd
    //   567: iconst_0
    //   568: iconst_0
    //   569: bipush #64
    //   571: bipush #64
    //   573: invokevirtual func_175174_a : (FFIIII)V
    //   576: aload_0
    //   577: fload #5
    //   579: invokevirtual b : (F)V
    //   582: invokestatic c : ()Z
    //   585: ifeq -> 634
    //   588: aload_0
    //   589: ldc -2.0
    //   591: fload #5
    //   593: fsub
    //   594: aload_0
    //   595: getfield d : F
    //   598: ldc 15.0
    //   600: fmul
    //   601: fadd
    //   602: ldc -2.0
    //   604: fload #5
    //   606: fsub
    //   607: aload_0
    //   608: getfield d : F
    //   611: ldc 15.0
    //   613: fmul
    //   614: fadd
    //   615: sipush #128
    //   618: bipush #64
    //   620: bipush #64
    //   622: bipush #64
    //   624: invokevirtual func_175174_a : (FFIIII)V
    //   627: goto -> 634
    //   630: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   633: athrow
    //   634: invokestatic func_179121_F : ()V
    //   637: aload_0
    //   638: getfield g : Lnet/minecraft/block/state/IBlockState;
    //   641: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   646: astore #6
    //   648: aload #6
    //   650: instanceof net/minecraft/block/BlockChest
    //   653: istore #7
    //   655: aload #6
    //   657: instanceof net/minecraft/block/BlockBed
    //   660: istore #8
    //   662: iload #7
    //   664: ifne -> 679
    //   667: iload #8
    //   669: ifeq -> 842
    //   672: goto -> 679
    //   675: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   678: athrow
    //   679: invokestatic func_179094_E : ()V
    //   682: fconst_1
    //   683: aload_0
    //   684: getfield c : F
    //   687: ldc 0.5
    //   689: fmul
    //   690: fadd
    //   691: fconst_1
    //   692: aload_0
    //   693: getfield c : F
    //   696: ldc 0.5
    //   698: fmul
    //   699: fadd
    //   700: fconst_1
    //   701: invokestatic func_179152_a : (FFF)V
    //   704: aload_0
    //   705: ldc -62.0
    //   707: fload #5
    //   709: fadd
    //   710: aload_0
    //   711: getfield c : F
    //   714: ldc 15.0
    //   716: fmul
    //   717: fsub
    //   718: ldc -2.0
    //   720: fload #5
    //   722: fsub
    //   723: aload_0
    //   724: getfield c : F
    //   727: ldc 15.0
    //   729: fmul
    //   730: fadd
    //   731: iconst_0
    //   732: iconst_0
    //   733: bipush #64
    //   735: bipush #64
    //   737: invokevirtual func_175174_a : (FFIIII)V
    //   740: iload #7
    //   742: ifeq -> 765
    //   745: goto -> 752
    //   748: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   751: athrow
    //   752: aload_0
    //   753: fload #5
    //   755: invokevirtual d : (F)V
    //   758: goto -> 765
    //   761: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   764: athrow
    //   765: iload #8
    //   767: ifeq -> 783
    //   770: aload_0
    //   771: fload #5
    //   773: invokevirtual c : (F)V
    //   776: goto -> 783
    //   779: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   782: athrow
    //   783: aload_0
    //   784: getfield l : Lnet/minecraft/util/math/BlockPos;
    //   787: invokestatic b : (Lnet/minecraft/util/math/BlockPos;)Z
    //   790: ifeq -> 839
    //   793: aload_0
    //   794: ldc -62.0
    //   796: fload #5
    //   798: fadd
    //   799: aload_0
    //   800: getfield c : F
    //   803: ldc 15.0
    //   805: fmul
    //   806: fsub
    //   807: ldc -2.0
    //   809: fload #5
    //   811: fsub
    //   812: aload_0
    //   813: getfield c : F
    //   816: ldc 15.0
    //   818: fmul
    //   819: fadd
    //   820: sipush #128
    //   823: bipush #64
    //   825: bipush #64
    //   827: bipush #64
    //   829: invokevirtual func_175174_a : (FFIIII)V
    //   832: goto -> 839
    //   835: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   838: athrow
    //   839: invokestatic func_179121_F : ()V
    //   842: aload #6
    //   844: instanceof net/minecraft/block/BlockLog
    //   847: istore #9
    //   849: aload_0
    //   850: invokevirtual c : ()[Ljava/lang/Object;
    //   853: ifnull -> 864
    //   856: iconst_1
    //   857: goto -> 865
    //   860: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   863: athrow
    //   864: iconst_0
    //   865: istore #10
    //   867: iload #9
    //   869: ifne -> 884
    //   872: iload #10
    //   874: ifeq -> 1047
    //   877: goto -> 884
    //   880: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   883: athrow
    //   884: invokestatic func_179094_E : ()V
    //   887: fconst_1
    //   888: aload_0
    //   889: getfield b : F
    //   892: ldc 0.5
    //   894: fmul
    //   895: fadd
    //   896: fconst_1
    //   897: aload_0
    //   898: getfield b : F
    //   901: ldc 0.5
    //   903: fmul
    //   904: fadd
    //   905: fconst_1
    //   906: invokestatic func_179152_a : (FFF)V
    //   909: aload_0
    //   910: ldc -2.0
    //   912: fload #5
    //   914: fsub
    //   915: aload_0
    //   916: getfield b : F
    //   919: ldc 15.0
    //   921: fmul
    //   922: fadd
    //   923: ldc -62.0
    //   925: fload #5
    //   927: fadd
    //   928: aload_0
    //   929: getfield b : F
    //   932: ldc 15.0
    //   934: fmul
    //   935: fsub
    //   936: iconst_0
    //   937: iconst_0
    //   938: bipush #64
    //   940: bipush #64
    //   942: invokevirtual func_175174_a : (FFIIII)V
    //   945: iload #9
    //   947: ifeq -> 970
    //   950: goto -> 957
    //   953: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   956: athrow
    //   957: aload_0
    //   958: fload #5
    //   960: invokevirtual e : (F)V
    //   963: goto -> 970
    //   966: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   969: athrow
    //   970: iload #10
    //   972: ifeq -> 988
    //   975: aload_0
    //   976: fload #5
    //   978: invokevirtual a : (F)V
    //   981: goto -> 988
    //   984: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   987: athrow
    //   988: aload_0
    //   989: getfield l : Lnet/minecraft/util/math/BlockPos;
    //   992: invokestatic b : (Lnet/minecraft/util/math/BlockPos;)Z
    //   995: ifeq -> 1044
    //   998: aload_0
    //   999: ldc -2.0
    //   1001: fload #5
    //   1003: fsub
    //   1004: aload_0
    //   1005: getfield b : F
    //   1008: ldc 15.0
    //   1010: fmul
    //   1011: fadd
    //   1012: ldc -62.0
    //   1014: fload #5
    //   1016: fadd
    //   1017: aload_0
    //   1018: getfield b : F
    //   1021: ldc 15.0
    //   1023: fmul
    //   1024: fsub
    //   1025: sipush #128
    //   1028: bipush #64
    //   1030: bipush #64
    //   1032: bipush #64
    //   1034: invokevirtual func_175174_a : (FFIIII)V
    //   1037: goto -> 1044
    //   1040: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   1043: athrow
    //   1044: invokestatic func_179121_F : ()V
    //   1047: invokestatic func_179121_F : ()V
    //   1050: sipush #3042
    //   1053: invokestatic glDisable : (I)V
    //   1056: return
    // Exception table:
    //   from	to	target	type
    //   33	56	59	java/lang/NullPointerException
    //   82	107	110	java/lang/NullPointerException
    //   97	118	118	java/lang/NullPointerException
    //   123	161	164	java/lang/NullPointerException
    //   151	172	172	java/lang/NullPointerException
    //   177	215	218	java/lang/NullPointerException
    //   205	226	226	java/lang/NullPointerException
    //   231	269	272	java/lang/NullPointerException
    //   259	280	280	java/lang/NullPointerException
    //   285	505	508	java/lang/NullPointerException
    //   512	627	630	java/lang/NullPointerException
    //   662	672	675	java/lang/NullPointerException
    //   667	745	748	java/lang/NullPointerException
    //   679	758	761	java/lang/NullPointerException
    //   765	776	779	java/lang/NullPointerException
    //   783	832	835	java/lang/NullPointerException
    //   849	860	860	java/lang/NullPointerException
    //   867	877	880	java/lang/NullPointerException
    //   872	950	953	java/lang/NullPointerException
    //   884	963	966	java/lang/NullPointerException
    //   970	981	984	java/lang/NullPointerException
    //   988	1037	1040	java/lang/NullPointerException
  }
  
  void b(float paramFloat) {
    func_175174_a(-2.0F - paramFloat + this.d * 15.0F, -2.0F - paramFloat + this.d * 15.0F, 192, 64, 64, 64);
  }
  
  void f(float paramFloat) {
    func_175174_a(-62.0F + paramFloat - this.i * 15.0F, -62.0F + paramFloat - this.i * 15.0F, 64, 64, 64, 64);
  }
  
  void e(float paramFloat) {
    func_175174_a(-2.0F - paramFloat + this.b * 15.0F, -62.0F + paramFloat - this.b * 15.0F, 64, 0, 64, 64);
  }
  
  void a(float paramFloat) {
    func_175174_a(-2.0F - paramFloat + this.b * 15.0F, -62.0F + paramFloat - this.b * 15.0F, 128, 0, 64, 64);
  }
  
  void c(float paramFloat) {
    func_175174_a(-62.0F + paramFloat - this.c * 15.0F, -2.0F - paramFloat + this.c * 15.0F, 0, 64, 64, 64);
  }
  
  void d(float paramFloat) {
    func_175174_a(-62.0F + paramFloat - this.c * 15.0F, -2.0F - paramFloat + this.c * 15.0F, 192, 0, 64, 64);
  }
  
  double a(double paramDouble) {
    double d1 = 1.70158D;
    double d2 = d1 + 1.0D;
    return 1.0D + d2 * Math.pow(paramDouble - 1.0D, 3.0D) + d1 * Math.pow(paramDouble - 1.0D, 2.0D);
  }
  
  protected void func_146286_b(int paramInt1, int paramInt2, int paramInt3) {
    this.field_146297_k.field_71439_g.func_71053_j();
    super.func_146286_b(paramInt1, paramInt2, paramInt3);
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */