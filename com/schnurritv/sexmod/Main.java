package com.schnurritv.sexmod;

import java.io.IOException;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(modid = "sexmod", name = "SchnurriTV's Sex Mod", version = "1.8.0", dependencies = "after:geckolib")
public class Main {
  @Instance
  public static Main instance;
  
  @SidedProxy(clientSide = "com.schnurritv.sexmod.ClientProxy", serverSide = "com.schnurritv.sexmod.CommonProxy")
  public static CommonProxy proxy;
  
  public static final Logger LOGGER = LogManager.getLogger("sexmod");
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {
    GeckoLib.initialize();
    proxy.preInitRegistries(paramFMLPreInitializationEvent);
  }
  
  @EventHandler
  public void init(FMLInitializationEvent paramFMLInitializationEvent) throws IOException {
    proxy.initRegistries(paramFMLInitializationEvent);
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) throws IOException {
    proxy.postInit(paramFMLPostInitializationEvent);
  }
  
  @EventHandler
  public static void clearEntityList(FMLServerStoppedEvent paramFMLServerStoppedEvent) {
    try {
      Q.f().clear();
      bF.a();
      aD.af.clear();
      if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        clientReset(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @EventHandler
  public static void clearEntityList(FMLServerStartingEvent paramFMLServerStartingEvent) {
    paramFMLServerStartingEvent.registerServerCommand((ICommand)B.a);
  }
  
  @SideOnly(Side.CLIENT)
  static void clientReset() {
    bX.a();
    bQ.b();
  }
  
  @SideOnly(Side.CLIENT)
  @EventHandler
  public void registerReplacedRenderers(FMLInitializationEvent paramFMLInitializationEvent) {
    GeckoLib.initialize();
  }
  
  public static void setConfigs() throws IOException {
    // Byte code:
    //   0: new java/io/File
    //   3: dup
    //   4: ldc 'config'
    //   6: invokespecial <init> : (Ljava/lang/String;)V
    //   9: astore_0
    //   10: aload_0
    //   11: invokevirtual mkdir : ()Z
    //   14: pop
    //   15: new java/io/File
    //   18: dup
    //   19: ldc 'config/sexmod.json'
    //   21: invokespecial <init> : (Ljava/lang/String;)V
    //   24: astore_1
    //   25: aload_1
    //   26: invokevirtual exists : ()Z
    //   29: ifne -> 56
    //   32: aload_1
    //   33: invokevirtual createNewFile : ()Z
    //   36: pop
    //   37: new java/io/FileWriter
    //   40: dup
    //   41: aload_1
    //   42: invokespecial <init> : (Ljava/io/File;)V
    //   45: astore_2
    //   46: aload_2
    //   47: ldc '{"shouldGenBuildings":true,"shouldLoadOtherSkins":false,"allowFlying":true}'
    //   49: invokevirtual write : (Ljava/lang/String;)V
    //   52: aload_2
    //   53: invokevirtual close : ()V
    //   56: new java/lang/StringBuilder
    //   59: dup
    //   60: invokespecial <init> : ()V
    //   63: astore_2
    //   64: new java/io/BufferedReader
    //   67: dup
    //   68: new java/io/FileReader
    //   71: dup
    //   72: aload_1
    //   73: invokespecial <init> : (Ljava/io/File;)V
    //   76: invokespecial <init> : (Ljava/io/Reader;)V
    //   79: astore_3
    //   80: aconst_null
    //   81: astore #4
    //   83: aload_3
    //   84: invokevirtual readLine : ()Ljava/lang/String;
    //   87: dup
    //   88: astore #5
    //   90: ifnull -> 107
    //   93: aload_2
    //   94: aload #5
    //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: goto -> 83
    //   103: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   106: athrow
    //   107: aload_3
    //   108: ifnull -> 202
    //   111: aload #4
    //   113: ifnull -> 142
    //   116: goto -> 123
    //   119: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   122: athrow
    //   123: aload_3
    //   124: invokevirtual close : ()V
    //   127: goto -> 202
    //   130: astore #5
    //   132: aload #4
    //   134: aload #5
    //   136: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   139: goto -> 202
    //   142: aload_3
    //   143: invokevirtual close : ()V
    //   146: goto -> 202
    //   149: astore #5
    //   151: aload #5
    //   153: astore #4
    //   155: aload #5
    //   157: athrow
    //   158: astore #6
    //   160: aload_3
    //   161: ifnull -> 199
    //   164: aload #4
    //   166: ifnull -> 195
    //   169: goto -> 176
    //   172: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   175: athrow
    //   176: aload_3
    //   177: invokevirtual close : ()V
    //   180: goto -> 199
    //   183: astore #7
    //   185: aload #4
    //   187: aload #7
    //   189: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   192: goto -> 199
    //   195: aload_3
    //   196: invokevirtual close : ()V
    //   199: aload #6
    //   201: athrow
    //   202: aload_2
    //   203: invokevirtual toString : ()Ljava/lang/String;
    //   206: astore_3
    //   207: aload_3
    //   208: ldc 'shouldGenBuildings'
    //   210: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   213: ifne -> 271
    //   216: aload_1
    //   217: invokevirtual delete : ()Z
    //   220: pop
    //   221: new java/io/File
    //   224: dup
    //   225: ldc 'config/sexmod.json'
    //   227: invokespecial <init> : (Ljava/lang/String;)V
    //   230: astore_1
    //   231: aload_1
    //   232: invokevirtual createNewFile : ()Z
    //   235: pop
    //   236: new java/io/FileWriter
    //   239: dup
    //   240: aload_1
    //   241: invokespecial <init> : (Ljava/io/File;)V
    //   244: astore #4
    //   246: aload #4
    //   248: ldc '{"shouldGenBuildings":true,"shouldLoadOtherSkins":false,"allowFlying":true}'
    //   250: invokevirtual write : (Ljava/lang/String;)V
    //   253: aload #4
    //   255: invokevirtual close : ()V
    //   258: iconst_1
    //   259: putstatic com/schnurritv/sexmod/b9.a : Z
    //   262: iconst_0
    //   263: putstatic com/schnurritv/sexmod/aM.a : Z
    //   266: iconst_1
    //   267: putstatic com/schnurritv/sexmod/U.ab : Z
    //   270: return
    //   271: aload_3
    //   272: ldc 'shouldGenBuildings'
    //   274: invokevirtual indexOf : (Ljava/lang/String;)I
    //   277: istore #4
    //   279: aload_3
    //   280: ldc 'shouldLoadOtherSkins'
    //   282: invokevirtual indexOf : (Ljava/lang/String;)I
    //   285: istore #5
    //   287: aload_3
    //   288: ldc 'allowFlying'
    //   290: invokevirtual indexOf : (Ljava/lang/String;)I
    //   293: istore #6
    //   295: bipush #116
    //   297: aload_3
    //   298: iload #4
    //   300: bipush #20
    //   302: iadd
    //   303: invokevirtual charAt : (I)C
    //   306: if_icmpne -> 317
    //   309: iconst_1
    //   310: goto -> 318
    //   313: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   316: athrow
    //   317: iconst_0
    //   318: putstatic com/schnurritv/sexmod/b9.a : Z
    //   321: bipush #116
    //   323: aload_3
    //   324: iload #5
    //   326: bipush #22
    //   328: iadd
    //   329: invokevirtual charAt : (I)C
    //   332: if_icmpne -> 343
    //   335: iconst_1
    //   336: goto -> 344
    //   339: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   342: athrow
    //   343: iconst_0
    //   344: putstatic com/schnurritv/sexmod/aM.a : Z
    //   347: bipush #116
    //   349: aload_3
    //   350: iload #6
    //   352: bipush #13
    //   354: iadd
    //   355: invokevirtual charAt : (I)C
    //   358: if_icmpne -> 369
    //   361: iconst_1
    //   362: goto -> 370
    //   365: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   368: athrow
    //   369: iconst_0
    //   370: putstatic com/schnurritv/sexmod/U.ab : Z
    //   373: return
    // Exception table:
    //   from	to	target	type
    //   83	107	149	java/lang/Throwable
    //   83	107	158	finally
    //   90	103	103	java/lang/Throwable
    //   107	116	119	java/lang/Throwable
    //   123	127	130	java/lang/Throwable
    //   149	160	158	finally
    //   160	169	172	java/lang/Throwable
    //   176	180	183	java/lang/Throwable
    //   295	313	313	java/lang/Throwable
    //   318	339	339	java/lang/Throwable
    //   344	365	365	java/lang/Throwable
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */