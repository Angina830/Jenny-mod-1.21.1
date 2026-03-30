package com.schnurritv.sexmod;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class cr extends bi {
  public static boolean v = false;
  
  ItemStack q = ItemStack.field_190927_a;
  
  ItemStack x = ItemStack.field_190927_a;
  
  boolean t = false;
  
  boolean u = false;
  
  protected U r;
  
  protected float w;
  
  Integer p = null;
  
  Integer o = null;
  
  Integer s = null;
  
  float n = 0.0F;
  
  public cr(RenderManager paramRenderManager, AnimatedGeoModel<T> paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel, 0.0D);
  }
  
  public void func_76979_b(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {}
  
  public void a(Q paramQ, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    try {
      if (v) {
        v = false;
      } else {
        return;
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    U u = (U)paramQ;
    try {
      if (u.u() == null)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = (Minecraft.func_71410_x()).field_71439_g.field_70170_p.func_152378_a(u.u());
    try {
      if (entityPlayer == null)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      this.q = entityPlayer.func_184614_ca();
      this.x = entityPlayer.func_184592_cb();
      this.u = u.W;
      this.t = u.af;
      this.r = (U)paramQ;
      this.w = paramFloat2;
      u.d(entityPlayer);
      if (a(entityPlayer, paramQ))
        func_147906_a((Entity)paramQ, entityPlayer.func_70005_c_(), paramDouble1, paramDouble2 + u.z(), paramDouble3, 300); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    super.a(paramQ, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
  }
  
  boolean a(EntityPlayer paramEntityPlayer, Q paramQ) {
    try {
      if (paramEntityPlayer.getPersistentID().equals((Minecraft.func_71410_x()).field_71439_g.getPersistentID()))
        return false; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    b1 b1 = paramQ.h();
    try {
      if (b1 == null)
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
    
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return !b1.hideNameTag;
  }
  
  protected void a(String paramString, GeoBone paramGeoBone) {}
  
  protected void a(String paramString, GeoBone paramGeoBone, U paramU, BufferBuilder paramBufferBuilder) {}
  
  public void renderRecursively(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual getName : ()Ljava/lang/String;
    //   4: astore #7
    //   6: aload_0
    //   7: getfield t : Z
    //   10: ifeq -> 120
    //   13: aload #7
    //   15: ldc 'upperBody'
    //   17: invokevirtual equals : (Ljava/lang/Object;)Z
    //   20: ifeq -> 48
    //   23: goto -> 30
    //   26: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   29: athrow
    //   30: aload_2
    //   31: aload_2
    //   32: invokevirtual getRotationX : ()F
    //   35: ldc 0.5
    //   37: fsub
    //   38: invokevirtual setRotationX : (F)V
    //   41: goto -> 48
    //   44: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   47: athrow
    //   48: aload #7
    //   50: ldc 'head'
    //   52: invokevirtual equals : (Ljava/lang/Object;)Z
    //   55: ifeq -> 76
    //   58: aload_2
    //   59: aload_2
    //   60: invokevirtual getRotationX : ()F
    //   63: ldc 0.5
    //   65: fadd
    //   66: invokevirtual setRotationX : (F)V
    //   69: goto -> 76
    //   72: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   75: athrow
    //   76: aload #7
    //   78: ldc 'legL'
    //   80: invokevirtual equals : (Ljava/lang/Object;)Z
    //   83: ifne -> 103
    //   86: aload #7
    //   88: ldc 'legR'
    //   90: invokevirtual equals : (Ljava/lang/Object;)Z
    //   93: ifeq -> 120
    //   96: goto -> 103
    //   99: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   102: athrow
    //   103: aload_2
    //   104: aload_2
    //   105: invokevirtual getPositionZ : ()F
    //   108: fconst_1
    //   109: fadd
    //   110: invokevirtual setPositionZ : (F)V
    //   113: goto -> 120
    //   116: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   119: athrow
    //   120: aload #7
    //   122: ldc 'head'
    //   124: invokevirtual equals : (Ljava/lang/Object;)Z
    //   127: ifeq -> 151
    //   130: aload_0
    //   131: aload_1
    //   132: aload_2
    //   133: fload_3
    //   134: fload #4
    //   136: fload #5
    //   138: invokestatic ofRGB : (FFF)Lsoftware/bernie/geckolib3/core/util/Color;
    //   141: invokevirtual a : (Lnet/minecraft/client/renderer/BufferBuilder;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;Lsoftware/bernie/geckolib3/core/util/Color;)V
    //   144: goto -> 151
    //   147: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   150: athrow
    //   151: aload_0
    //   152: aload #7
    //   154: aload_2
    //   155: invokevirtual a : (Ljava/lang/String;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   158: aload_0
    //   159: aload #7
    //   161: aload_2
    //   162: aload_0
    //   163: getfield r : Lcom/schnurritv/sexmod/U;
    //   166: aload_1
    //   167: invokevirtual a : (Ljava/lang/String;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;Lcom/schnurritv/sexmod/U;Lnet/minecraft/client/renderer/BufferBuilder;)V
    //   170: aload_0
    //   171: getfield u : Z
    //   174: ifeq -> 329
    //   177: aload_0
    //   178: getfield q : Lnet/minecraft/item/ItemStack;
    //   181: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   184: instanceof net/minecraft/item/ItemBow
    //   187: ifne -> 217
    //   190: goto -> 197
    //   193: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   196: athrow
    //   197: aload_0
    //   198: getfield x : Lnet/minecraft/item/ItemStack;
    //   201: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   204: instanceof net/minecraft/item/ItemBow
    //   207: ifeq -> 329
    //   210: goto -> 217
    //   213: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   216: athrow
    //   217: aload #7
    //   219: ldc 'armR'
    //   221: invokevirtual equals : (Ljava/lang/Object;)Z
    //   224: ifeq -> 260
    //   227: goto -> 234
    //   230: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   233: athrow
    //   234: aload_2
    //   235: aload_2
    //   236: invokevirtual getRotationX : ()F
    //   239: aload_0
    //   240: getfield k : Lcom/schnurritv/sexmod/Q;
    //   243: getfield field_70125_A : F
    //   246: ldc 50.0
    //   248: fdiv
    //   249: fsub
    //   250: invokevirtual setRotationX : (F)V
    //   253: goto -> 260
    //   256: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   259: athrow
    //   260: aload #7
    //   262: ldc 'armL'
    //   264: invokevirtual equals : (Ljava/lang/Object;)Z
    //   267: ifeq -> 296
    //   270: aload_2
    //   271: aload_2
    //   272: invokevirtual getRotationY : ()F
    //   275: aload_0
    //   276: getfield k : Lcom/schnurritv/sexmod/Q;
    //   279: getfield field_70125_A : F
    //   282: ldc 50.0
    //   284: fdiv
    //   285: fsub
    //   286: invokevirtual setRotationY : (F)V
    //   289: goto -> 296
    //   292: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   295: athrow
    //   296: aload_0
    //   297: getfield x : Lnet/minecraft/item/ItemStack;
    //   300: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   303: instanceof net/minecraft/item/ItemBow
    //   306: ifeq -> 329
    //   309: aload_0
    //   310: getfield x : Lnet/minecraft/item/ItemStack;
    //   313: astore #8
    //   315: aload_0
    //   316: aload_0
    //   317: getfield q : Lnet/minecraft/item/ItemStack;
    //   320: putfield x : Lnet/minecraft/item/ItemStack;
    //   323: aload_0
    //   324: aload #8
    //   326: putfield q : Lnet/minecraft/item/ItemStack;
    //   329: aload_0
    //   330: getfield u : Z
    //   333: ifeq -> 439
    //   336: aload_0
    //   337: getfield q : Lnet/minecraft/item/ItemStack;
    //   340: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   343: instanceof net/minecraft/item/ItemShield
    //   346: ifeq -> 439
    //   349: goto -> 356
    //   352: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   355: athrow
    //   356: aload #7
    //   358: ldc 'armR'
    //   360: invokevirtual equals : (Ljava/lang/Object;)Z
    //   363: ifeq -> 391
    //   366: goto -> 373
    //   369: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   372: athrow
    //   373: aload_2
    //   374: fconst_0
    //   375: invokevirtual setRotationZ : (F)V
    //   378: aload_2
    //   379: ldc 0.5
    //   381: invokevirtual setRotationX : (F)V
    //   384: goto -> 439
    //   387: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   390: athrow
    //   391: aload_0
    //   392: getfield x : Lnet/minecraft/item/ItemStack;
    //   395: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   398: instanceof net/minecraft/item/ItemShield
    //   401: ifeq -> 439
    //   404: aload #7
    //   406: ldc 'armL'
    //   408: invokevirtual equals : (Ljava/lang/Object;)Z
    //   411: ifeq -> 439
    //   414: goto -> 421
    //   417: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   420: athrow
    //   421: aload_2
    //   422: fconst_0
    //   423: invokevirtual setRotationZ : (F)V
    //   426: aload_2
    //   427: ldc 0.5
    //   429: invokevirtual setRotationX : (F)V
    //   432: goto -> 439
    //   435: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   438: athrow
    //   439: aload #7
    //   441: ldc 'weapon'
    //   443: invokevirtual equals : (Ljava/lang/Object;)Z
    //   446: ifeq -> 480
    //   449: aload_0
    //   450: getfield q : Lnet/minecraft/item/ItemStack;
    //   453: invokevirtual func_190926_b : ()Z
    //   456: ifne -> 480
    //   459: goto -> 466
    //   462: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   465: athrow
    //   466: aload_0
    //   467: aload_1
    //   468: aload_2
    //   469: iconst_0
    //   470: invokevirtual a : (Lnet/minecraft/client/renderer/BufferBuilder;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;Z)V
    //   473: goto -> 480
    //   476: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   479: athrow
    //   480: aload #7
    //   482: ldc 'offhand'
    //   484: invokevirtual equals : (Ljava/lang/Object;)Z
    //   487: ifeq -> 521
    //   490: aload_0
    //   491: getfield x : Lnet/minecraft/item/ItemStack;
    //   494: invokevirtual func_190926_b : ()Z
    //   497: ifne -> 521
    //   500: goto -> 507
    //   503: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   506: athrow
    //   507: aload_0
    //   508: aload_1
    //   509: aload_2
    //   510: iconst_1
    //   511: invokevirtual a : (Lnet/minecraft/client/renderer/BufferBuilder;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;Z)V
    //   514: goto -> 521
    //   517: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   520: athrow
    //   521: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   524: invokevirtual push : ()V
    //   527: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   530: aload_2
    //   531: invokevirtual translate : (Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   534: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   537: aload_2
    //   538: invokevirtual moveToPivot : (Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   541: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   544: aload_2
    //   545: invokevirtual rotate : (Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   548: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   551: aload_2
    //   552: invokevirtual scale : (Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   555: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   558: aload_2
    //   559: invokevirtual moveBackFromPivot : (Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   562: ldc 'Head2'
    //   564: aload #7
    //   566: invokevirtual equals : (Ljava/lang/Object;)Z
    //   569: ifeq -> 597
    //   572: aload_0
    //   573: invokevirtual b : ()Z
    //   576: ifne -> 597
    //   579: goto -> 586
    //   582: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   585: athrow
    //   586: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   589: invokevirtual pop : ()V
    //   592: return
    //   593: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   596: athrow
    //   597: ldc 'neck'
    //   599: aload #7
    //   601: invokevirtual equals : (Ljava/lang/Object;)Z
    //   604: ifne -> 624
    //   607: ldc 'head'
    //   609: aload #7
    //   611: invokevirtual equals : (Ljava/lang/Object;)Z
    //   614: ifeq -> 649
    //   617: goto -> 624
    //   620: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   623: athrow
    //   624: aload_0
    //   625: invokevirtual c : ()Z
    //   628: ifne -> 649
    //   631: goto -> 638
    //   634: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   637: athrow
    //   638: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   641: invokevirtual pop : ()V
    //   644: return
    //   645: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   648: athrow
    //   649: aload_2
    //   650: getfield isHidden : Z
    //   653: ifne -> 859
    //   656: aload_0
    //   657: aload #7
    //   659: fload_3
    //   660: fload #4
    //   662: fload #5
    //   664: invokevirtual a : (Ljava/lang/String;FFF)Ljavax/vecmath/Vector4f;
    //   667: astore #8
    //   669: aload #8
    //   671: getfield x : F
    //   674: fstore_3
    //   675: aload #8
    //   677: getfield y : F
    //   680: fstore #4
    //   682: aload #8
    //   684: getfield z : F
    //   687: fstore #5
    //   689: aload #8
    //   691: getfield w : F
    //   694: f2d
    //   695: dstore #9
    //   697: aload_0
    //   698: getfield j : Ljava/util/HashSet;
    //   701: aload #7
    //   703: invokevirtual contains : (Ljava/lang/Object;)Z
    //   706: ifne -> 779
    //   709: aload_2
    //   710: getfield childCubes : Ljava/util/List;
    //   713: invokeinterface iterator : ()Ljava/util/Iterator;
    //   718: astore #11
    //   720: aload #11
    //   722: invokeinterface hasNext : ()Z
    //   727: ifeq -> 779
    //   730: aload #11
    //   732: invokeinterface next : ()Ljava/lang/Object;
    //   737: checkcast software/bernie/geckolib3/geo/render/built/GeoCube
    //   740: astore #12
    //   742: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   745: invokevirtual push : ()V
    //   748: invokestatic func_179094_E : ()V
    //   751: aload_0
    //   752: aload_1
    //   753: aload #12
    //   755: fload_3
    //   756: fload #4
    //   758: fload #5
    //   760: fload #6
    //   762: dload #9
    //   764: invokevirtual a : (Lnet/minecraft/client/renderer/BufferBuilder;Lsoftware/bernie/geckolib3/geo/render/built/GeoCube;FFFFD)V
    //   767: invokestatic func_179121_F : ()V
    //   770: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   773: invokevirtual pop : ()V
    //   776: goto -> 720
    //   779: aload_2
    //   780: getfield childBones : Ljava/util/List;
    //   783: invokeinterface iterator : ()Ljava/util/Iterator;
    //   788: astore #11
    //   790: aload #11
    //   792: invokeinterface hasNext : ()Z
    //   797: ifeq -> 859
    //   800: aload #11
    //   802: invokeinterface next : ()Ljava/lang/Object;
    //   807: checkcast software/bernie/geckolib3/geo/render/built/GeoBone
    //   810: astore #12
    //   812: dload #9
    //   814: dconst_0
    //   815: dcmpl
    //   816: ifne -> 840
    //   819: aload_0
    //   820: aload_1
    //   821: aload #12
    //   823: fload_3
    //   824: fload #4
    //   826: fload #5
    //   828: fload #6
    //   830: invokevirtual renderRecursively : (Lnet/minecraft/client/renderer/BufferBuilder;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;FFFF)V
    //   833: goto -> 856
    //   836: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   839: athrow
    //   840: aload_0
    //   841: aload_1
    //   842: aload #12
    //   844: fload_3
    //   845: fload #4
    //   847: fload #5
    //   849: fload #6
    //   851: dload #9
    //   853: invokevirtual a : (Lnet/minecraft/client/renderer/BufferBuilder;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;FFFFD)V
    //   856: goto -> 790
    //   859: getstatic com/schnurritv/sexmod/cr.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   862: invokevirtual pop : ()V
    //   865: goto -> 870
    //   868: astore #8
    //   870: return
    // Exception table:
    //   from	to	target	type
    //   6	23	26	java/lang/IllegalStateException
    //   13	41	44	java/lang/IllegalStateException
    //   48	69	72	java/lang/IllegalStateException
    //   76	96	99	java/lang/IllegalStateException
    //   86	113	116	java/lang/IllegalStateException
    //   120	144	147	java/lang/IllegalStateException
    //   151	190	193	java/lang/IllegalStateException
    //   177	210	213	java/lang/IllegalStateException
    //   197	227	230	java/lang/IllegalStateException
    //   217	253	256	java/lang/IllegalStateException
    //   260	289	292	java/lang/IllegalStateException
    //   329	349	352	java/lang/IllegalStateException
    //   336	366	369	java/lang/IllegalStateException
    //   356	387	387	java/lang/IllegalStateException
    //   391	414	417	java/lang/IllegalStateException
    //   404	432	435	java/lang/IllegalStateException
    //   439	459	462	java/lang/IllegalStateException
    //   449	473	476	java/lang/IllegalStateException
    //   480	500	503	java/lang/IllegalStateException
    //   490	514	517	java/lang/IllegalStateException
    //   521	579	582	java/lang/IllegalStateException
    //   572	593	593	java/lang/IllegalStateException
    //   597	617	620	java/lang/IllegalStateException
    //   607	631	634	java/lang/IllegalStateException
    //   624	645	645	java/lang/IllegalStateException
    //   812	836	836	java/lang/IllegalStateException
    //   859	865	868	java/lang/IllegalStateException
  }
  
  boolean c() {
    try {
      if (!((U)this.k).n())
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (this.i.field_71474_y.field_74320_O != 0)
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (!(this.i.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
        try {
          if (this.i.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainerCreative);
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        } 
        return false;
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
  }
  
  void a(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, Color paramColor) {
    GlStateManager.func_179094_E();
    Tessellator.func_178181_a().func_78381_a();
    bE.a(IGeoRenderer.MATRIX_STACK, paramGeoBone);
    GL11.glEnable(2896);
    b();
    (new aV((IGeoRenderer)this)).render((EntityLivingBase)this.k, ((Q)this.k).field_184619_aG, ((Q)this.k).field_70721_aZ, this.w, 0.0F, 0.0F, 0.0F, paramColor);
    func_110776_a(Objects.<ResourceLocation>requireNonNull(getEntityTexture((EntityLivingBase)this.k)));
    paramBufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181712_l);
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GL11.glDisable(2896);
    GlStateManager.func_179121_F();
  }
  
  protected void b() {}
  
  void a(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, boolean paramBoolean) {
    // Byte code:
    //   0: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   3: invokevirtual func_175597_ag : ()Lnet/minecraft/client/renderer/ItemRenderer;
    //   6: astore #4
    //   8: invokestatic func_179094_E : ()V
    //   11: invokestatic func_178181_a : ()Lnet/minecraft/client/renderer/Tessellator;
    //   14: invokevirtual func_78381_a : ()V
    //   17: getstatic software/bernie/geckolib3/renderers/geo/IGeoRenderer.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   20: aload_2
    //   21: invokestatic a : (Lsoftware/bernie/geckolib3/util/MatrixStack;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   24: sipush #2896
    //   27: invokestatic glEnable : (I)V
    //   30: invokestatic func_179147_l : ()V
    //   33: getstatic net/minecraft/client/renderer/GlStateManager$SourceFactor.SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;
    //   36: getstatic net/minecraft/client/renderer/GlStateManager$DestFactor.ONE_MINUS_SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$DestFactor;
    //   39: invokestatic func_187401_a : (Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;)V
    //   42: iload_3
    //   43: ifeq -> 57
    //   46: aload_0
    //   47: getfield x : Lnet/minecraft/item/ItemStack;
    //   50: goto -> 61
    //   53: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   56: athrow
    //   57: aload_0
    //   58: getfield q : Lnet/minecraft/item/ItemStack;
    //   61: astore #5
    //   63: getstatic com/schnurritv/sexmod/cr$a.a : [I
    //   66: aload #5
    //   68: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   71: aload #5
    //   73: invokevirtual func_77661_b : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumAction;
    //   76: invokevirtual ordinal : ()I
    //   79: iaload
    //   80: lookupswitch default -> 129, 1 -> 108, 2 -> 120
    //   108: aload_0
    //   109: iload_3
    //   110: invokevirtual a : (Z)V
    //   113: goto -> 129
    //   116: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   119: athrow
    //   120: aload_0
    //   121: iload_3
    //   122: aload_0
    //   123: getfield u : Z
    //   126: invokevirtual a : (ZZ)V
    //   129: aload_0
    //   130: getfield u : Z
    //   133: ifeq -> 237
    //   136: iload_3
    //   137: ifne -> 237
    //   140: goto -> 147
    //   143: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   146: athrow
    //   147: aload #5
    //   149: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   152: instanceof net/minecraft/item/ItemBow
    //   155: ifeq -> 237
    //   158: goto -> 165
    //   161: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   164: athrow
    //   165: aload_0
    //   166: dup
    //   167: getfield n : F
    //   170: ldc 0.015
    //   172: fadd
    //   173: putfield n : F
    //   176: aload_0
    //   177: getfield k : Lcom/schnurritv/sexmod/Q;
    //   180: aload_0
    //   181: getfield n : F
    //   184: fneg
    //   185: ldc 20.0
    //   187: fmul
    //   188: aload #5
    //   190: invokevirtual func_77988_m : ()I
    //   193: i2f
    //   194: fadd
    //   195: invokestatic round : (F)I
    //   198: invokevirtual b : (I)V
    //   201: aload_0
    //   202: getfield k : Lcom/schnurritv/sexmod/Q;
    //   205: aload #5
    //   207: invokevirtual a : (Lnet/minecraft/item/ItemStack;)V
    //   210: aload_0
    //   211: getfield k : Lcom/schnurritv/sexmod/Q;
    //   214: getstatic net/minecraft/util/EnumHand.MAIN_HAND : Lnet/minecraft/util/EnumHand;
    //   217: invokevirtual func_184598_c : (Lnet/minecraft/util/EnumHand;)V
    //   220: aload_0
    //   221: getfield k : Lcom/schnurritv/sexmod/Q;
    //   224: checkcast com/schnurritv/sexmod/U
    //   227: invokevirtual a : ()V
    //   230: goto -> 260
    //   233: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   236: athrow
    //   237: aload_0
    //   238: fconst_0
    //   239: putfield n : F
    //   242: aload_0
    //   243: getfield k : Lcom/schnurritv/sexmod/Q;
    //   246: iconst_0
    //   247: invokevirtual b : (I)V
    //   250: aload_0
    //   251: getfield k : Lcom/schnurritv/sexmod/Q;
    //   254: getstatic net/minecraft/item/ItemStack.field_190927_a : Lnet/minecraft/item/ItemStack;
    //   257: invokevirtual a : (Lnet/minecraft/item/ItemStack;)V
    //   260: aload_0
    //   261: iload_3
    //   262: aload #5
    //   264: invokevirtual a : (ZLnet/minecraft/item/ItemStack;)V
    //   267: ldc 0.75
    //   269: ldc 0.75
    //   271: ldc 0.75
    //   273: invokestatic func_179152_a : (FFF)V
    //   276: aload #4
    //   278: aload_0
    //   279: getfield k : Lcom/schnurritv/sexmod/Q;
    //   282: aload #5
    //   284: getstatic net/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType.THIRD_PERSON_RIGHT_HAND : Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;
    //   287: invokevirtual func_178099_a : (Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V
    //   290: aload_1
    //   291: bipush #7
    //   293: getstatic net/minecraft/client/renderer/vertex/DefaultVertexFormats.field_181712_l : Lnet/minecraft/client/renderer/vertex/VertexFormat;
    //   296: invokevirtual func_181668_a : (ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
    //   299: aload_0
    //   300: aload_0
    //   301: aload_0
    //   302: getfield k : Lcom/schnurritv/sexmod/Q;
    //   305: invokevirtual getEntityTexture : (Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/ResourceLocation;
    //   308: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   311: checkcast net/minecraft/util/ResourceLocation
    //   314: invokevirtual func_110776_a : (Lnet/minecraft/util/ResourceLocation;)V
    //   317: sipush #2896
    //   320: invokestatic glDisable : (I)V
    //   323: invokestatic func_179121_F : ()V
    //   326: invokestatic func_179147_l : ()V
    //   329: getstatic net/minecraft/client/renderer/GlStateManager$SourceFactor.SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;
    //   332: getstatic net/minecraft/client/renderer/GlStateManager$DestFactor.ONE_MINUS_SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$DestFactor;
    //   335: invokestatic func_187401_a : (Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;)V
    //   338: return
    // Exception table:
    //   from	to	target	type
    //   8	53	53	java/lang/IllegalStateException
    //   63	116	116	java/lang/IllegalStateException
    //   129	140	143	java/lang/IllegalStateException
    //   136	158	161	java/lang/IllegalStateException
    //   147	233	233	java/lang/IllegalStateException
  }
  
  protected void a(boolean paramBoolean, ItemStack paramItemStack) {
    try {
    
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    GlStateManager.func_179114_b(paramBoolean ? 200.0F : 90.0F, 1.0F, 0.0F, 0.0F);
  }
  
  protected void a(boolean paramBoolean) {
    GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 0.0F);
  }
  
  protected void a(boolean paramBoolean1, boolean paramBoolean2) {
    try {
      if (paramBoolean1) {
        try {
          GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
          if (paramBoolean2) {
            GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(35.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, 0.0F, 0.228F);
          } 
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        } 
      } else {
        try {
          if (paramBoolean2) {
            GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179109_b(0.0F, 0.165F, 0.0F);
          } 
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        } 
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
  }
  
  private static IllegalStateException a(IllegalStateException paramIllegalStateException) {
    return paramIllegalStateException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */