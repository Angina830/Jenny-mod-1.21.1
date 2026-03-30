package com.schnurritv.sexmod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class b_ {
  static final String a = "sexmod/custom_models";
  
  static Map<String, a> b = new HashMap<>();
  
  public static boolean c = false;
  
  public static boolean j(String paramString) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (b.get(paramString) != null);
  }
  
  public static int b() {
    for (Map.Entry<String, a> entry : b.entrySet()) {
      a a = (a)entry.getValue();
      try {
        if (a == null)
          continue; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      ResourceLocation resourceLocation1 = a.b();
      ResourceLocation resourceLocation2 = a.e();
      try {
        if (resourceLocation1 != null)
          GeckoLibCache.getInstance().getGeoModels().remove(resourceLocation1); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (resourceLocation2 != null)
          (Minecraft.func_71410_x()).field_71446_o.func_147645_c(resourceLocation2); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    b.clear();
    return a();
  }
  
  static void b(Level paramLevel, String paramString) {
    try {
      if (Main.proxy instanceof ClientProxy) {
        a(paramLevel, paramString);
      } else {
        Main.LOGGER.log(paramLevel, paramString);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  static void a(Level paramLevel, String paramString) {
    TextFormatting textFormatting;
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    try {
      if (entityPlayerSP == null) {
        Main.LOGGER.log(paramLevel, paramString);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    if (Level.DEBUG.equals(paramLevel)) {
      textFormatting = TextFormatting.DARK_GREEN;
    } else if (Level.ERROR.equals(paramLevel)) {
      textFormatting = TextFormatting.RED;
    } else {
      textFormatting = TextFormatting.WHITE;
    } 
    entityPlayerSP.func_145747_a((ITextComponent)new TextComponentString(textFormatting.toString() + paramString));
  }
  
  public static int a() {
    b(Level.INFO, "loading up custom models...");
    File file = new File("sexmod/custom_models");
    file.mkdirs();
    String[] arrayOfString = file.list((paramFile, paramString) -> (new File(paramFile, paramString)).isDirectory());
    try {
      if (arrayOfString == null) {
        b(Level.ERROR, String.format("Something is wrong with the custom models folder at '%s'. Check if it exists, if not - make the directory yourself because Minecraft cannot do it itself for some reason", new Object[] { file.getAbsolutePath() }));
        return -1;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    b(Level.INFO, String.format("found %s custom model(s)", new Object[] { Integer.valueOf(arrayOfString.length) }));
    byte b = 0;
    for (String str1 : arrayOfString) {
      String str2 = e(str1);
      try {
        if (!"".equals(str2)) {
          b(Level.ERROR, str2);
          return -1;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      str2 = h(str1);
      try {
        if (!"".equals(str2)) {
          b(Level.ERROR, str2);
          return -1;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      b++;
    } 
    b(Level.DEBUG, String.format("successfully registered %s custom models", new Object[] { Integer.valueOf(b) }));
    return 0;
  }
  
  static String e(String paramString) {
    String str = String.format("%s/%s/", new Object[] { "sexmod/custom_models", paramString });
    File file1 = new File(String.format("%s/%s.geo.json", new Object[] { str, paramString }));
    File file2 = new File(String.format("%s/%s.png", new Object[] { str, paramString }));
    File file3 = new File(String.format("%s/%s.cfg", new Object[] { str, paramString }));
    try {
      if (!file1.exists())
        return String.format("couldn't find model File for '%s'. It should have been at '%s'. Are you sure it exists?", new Object[] { paramString, file1.getAbsolutePath() }); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!file2.exists())
        return String.format("couldn't find texture File for '%s'. It should have been at '%s'. Are you sure it exists?", new Object[] { paramString, file2.getAbsolutePath() }); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!file3.exists())
        return String.format("couldn't find cfg File for '%s'. It should have been at '%s'. Are you sure it exists?", new Object[] { paramString, file3.getAbsolutePath() }); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return "";
  }
  
  public static String h(String paramString) {
    // Byte code:
    //   0: getstatic com/schnurritv/sexmod/b_.b : Ljava/util/Map;
    //   3: aload_0
    //   4: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   9: ifnull -> 30
    //   12: ldc 'already registered '%s'... honestly, unsure how this could happen lol'
    //   14: iconst_1
    //   15: anewarray java/lang/Object
    //   18: dup
    //   19: iconst_0
    //   20: aload_0
    //   21: aastore
    //   22: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   25: areturn
    //   26: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   29: athrow
    //   30: ldc '%s/%s/'
    //   32: iconst_2
    //   33: anewarray java/lang/Object
    //   36: dup
    //   37: iconst_0
    //   38: ldc 'sexmod/custom_models'
    //   40: aastore
    //   41: dup
    //   42: iconst_1
    //   43: aload_0
    //   44: aastore
    //   45: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   48: astore_1
    //   49: new java/lang/StringBuilder
    //   52: dup
    //   53: invokespecial <init> : ()V
    //   56: aload_1
    //   57: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: aload_0
    //   61: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: ldc '.cfg'
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: invokevirtual toString : ()Ljava/lang/String;
    //   72: astore_2
    //   73: new java/io/File
    //   76: dup
    //   77: aload_2
    //   78: invokespecial <init> : (Ljava/lang/String;)V
    //   81: astore_3
    //   82: aload_3
    //   83: invokevirtual exists : ()Z
    //   86: ifne -> 111
    //   89: ldc 'couldn't find cfg File for '%s'. It should have been at '%s'. Are you sure it exists?'
    //   91: iconst_2
    //   92: anewarray java/lang/Object
    //   95: dup
    //   96: iconst_0
    //   97: aload_0
    //   98: aastore
    //   99: dup
    //   100: iconst_1
    //   101: aload_2
    //   102: aastore
    //   103: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   106: areturn
    //   107: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   110: athrow
    //   111: new com/schnurritv/sexmod/b_$a
    //   114: dup
    //   115: aload_3
    //   116: aload_0
    //   117: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   120: astore #4
    //   122: aload #4
    //   124: getfield d : Ljava/lang/String;
    //   127: ifnull -> 140
    //   130: aload #4
    //   132: getfield d : Ljava/lang/String;
    //   135: areturn
    //   136: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   139: athrow
    //   140: new java/lang/StringBuilder
    //   143: dup
    //   144: invokespecial <init> : ()V
    //   147: aload_1
    //   148: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: aload_0
    //   152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: ldc '.png'
    //   157: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: invokevirtual toString : ()Ljava/lang/String;
    //   163: astore #6
    //   165: new java/io/File
    //   168: dup
    //   169: aload #6
    //   171: invokespecial <init> : (Ljava/lang/String;)V
    //   174: astore #5
    //   176: aload #5
    //   178: invokevirtual exists : ()Z
    //   181: ifne -> 207
    //   184: ldc 'The texture for the custom model '%s' couldn't be found at '%s' are you sure it exists?'
    //   186: iconst_2
    //   187: anewarray java/lang/Object
    //   190: dup
    //   191: iconst_0
    //   192: aload_0
    //   193: aastore
    //   194: dup
    //   195: iconst_1
    //   196: aload #6
    //   198: aastore
    //   199: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   202: areturn
    //   203: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   206: athrow
    //   207: aload #5
    //   209: invokestatic read : (Ljava/io/File;)Ljava/awt/image/BufferedImage;
    //   212: astore #8
    //   214: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   217: getfield field_71446_o : Lnet/minecraft/client/renderer/texture/TextureManager;
    //   220: aload_0
    //   221: new net/minecraft/client/renderer/texture/DynamicTexture
    //   224: dup
    //   225: aload #8
    //   227: invokespecial <init> : (Ljava/awt/image/BufferedImage;)V
    //   230: invokevirtual func_110578_a : (Ljava/lang/String;Lnet/minecraft/client/renderer/texture/DynamicTexture;)Lnet/minecraft/util/ResourceLocation;
    //   233: astore #7
    //   235: goto -> 280
    //   238: astore #8
    //   240: ldc 'The texture for the custom model '%s' at '%s' appears to be corrupted. Try making a new one'
    //   242: iconst_2
    //   243: anewarray java/lang/Object
    //   246: dup
    //   247: iconst_0
    //   248: aload_0
    //   249: aastore
    //   250: dup
    //   251: iconst_1
    //   252: aload #6
    //   254: aastore
    //   255: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   258: areturn
    //   259: astore #8
    //   261: ldc 'Couldn't load the texture for the custom model '%s' at '%s'. Maybe try increasing the amount of RAM of ur Minecraft client'
    //   263: iconst_2
    //   264: anewarray java/lang/Object
    //   267: dup
    //   268: iconst_0
    //   269: aload_0
    //   270: aastore
    //   271: dup
    //   272: iconst_1
    //   273: aload #5
    //   275: aastore
    //   276: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   279: areturn
    //   280: new net/minecraft/util/ResourceLocation
    //   283: dup
    //   284: ldc 'sexmod'
    //   286: new java/lang/StringBuilder
    //   289: dup
    //   290: invokespecial <init> : ()V
    //   293: aload_0
    //   294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: ldc 'Model'
    //   299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: invokevirtual toString : ()Ljava/lang/String;
    //   305: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   308: astore #8
    //   310: new java/lang/StringBuilder
    //   313: dup
    //   314: invokespecial <init> : ()V
    //   317: aload_1
    //   318: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: aload_0
    //   322: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   325: ldc '.geo.json'
    //   327: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: invokevirtual toString : ()Ljava/lang/String;
    //   333: astore #10
    //   335: new java/io/File
    //   338: dup
    //   339: aload #10
    //   341: invokespecial <init> : (Ljava/lang/String;)V
    //   344: astore #11
    //   346: aload #11
    //   348: invokevirtual exists : ()Z
    //   351: ifne -> 373
    //   354: ldc 'The geo model for the custom model '%s' couldn't be found at '%s' are you sure it exists?'
    //   356: iconst_2
    //   357: anewarray java/lang/Object
    //   360: dup
    //   361: iconst_0
    //   362: aload_0
    //   363: aastore
    //   364: dup
    //   365: iconst_1
    //   366: aload #10
    //   368: aastore
    //   369: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   372: areturn
    //   373: new java/lang/StringBuilder
    //   376: dup
    //   377: invokespecial <init> : ()V
    //   380: astore #12
    //   382: new java/io/BufferedReader
    //   385: dup
    //   386: new java/io/FileReader
    //   389: dup
    //   390: aload #11
    //   392: invokespecial <init> : (Ljava/io/File;)V
    //   395: invokespecial <init> : (Ljava/io/Reader;)V
    //   398: astore #13
    //   400: aconst_null
    //   401: astore #14
    //   403: aload #13
    //   405: invokevirtual readLine : ()Ljava/lang/String;
    //   408: dup
    //   409: astore #15
    //   411: ifnull -> 429
    //   414: aload #12
    //   416: aload #15
    //   418: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   421: pop
    //   422: goto -> 403
    //   425: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   428: athrow
    //   429: aload #13
    //   431: ifnull -> 530
    //   434: aload #14
    //   436: ifnull -> 466
    //   439: goto -> 446
    //   442: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   445: athrow
    //   446: aload #13
    //   448: invokevirtual close : ()V
    //   451: goto -> 530
    //   454: astore #15
    //   456: aload #14
    //   458: aload #15
    //   460: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   463: goto -> 530
    //   466: aload #13
    //   468: invokevirtual close : ()V
    //   471: goto -> 530
    //   474: astore #15
    //   476: aload #15
    //   478: astore #14
    //   480: aload #15
    //   482: athrow
    //   483: astore #16
    //   485: aload #13
    //   487: ifnull -> 527
    //   490: aload #14
    //   492: ifnull -> 522
    //   495: goto -> 502
    //   498: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   501: athrow
    //   502: aload #13
    //   504: invokevirtual close : ()V
    //   507: goto -> 527
    //   510: astore #17
    //   512: aload #14
    //   514: aload #17
    //   516: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   519: goto -> 527
    //   522: aload #13
    //   524: invokevirtual close : ()V
    //   527: aload #16
    //   529: athrow
    //   530: aload #12
    //   532: invokevirtual toString : ()Ljava/lang/String;
    //   535: astore #13
    //   537: aload #13
    //   539: invokestatic fromJsonString : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/geo/raw/pojo/RawGeoModel;
    //   542: astore #9
    //   544: goto -> 568
    //   547: astore #11
    //   549: ldc 'The geo model for the custom model '%s' at '%s' appears to be corrupted. Try replacing it.'
    //   551: iconst_2
    //   552: anewarray java/lang/Object
    //   555: dup
    //   556: iconst_0
    //   557: aload_0
    //   558: aastore
    //   559: dup
    //   560: iconst_1
    //   561: aload #10
    //   563: aastore
    //   564: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   567: areturn
    //   568: aload #9
    //   570: aload #8
    //   572: invokestatic parseHierarchy : (Lsoftware/bernie/geckolib3/geo/raw/pojo/RawGeoModel;Lnet/minecraft/util/ResourceLocation;)Lsoftware/bernie/geckolib3/geo/raw/tree/RawGeometryTree;
    //   575: astore #11
    //   577: aload #8
    //   579: invokevirtual func_110624_b : ()Ljava/lang/String;
    //   582: invokestatic getGeoBuilder : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/geo/render/IGeoBuilder;
    //   585: aload #11
    //   587: invokeinterface constructGeoModel : (Lsoftware/bernie/geckolib3/geo/raw/tree/RawGeometryTree;)Lsoftware/bernie/geckolib3/geo/render/built/GeoModel;
    //   592: astore #12
    //   594: invokestatic getInstance : ()Lsoftware/bernie/geckolib3/resource/GeckoLibCache;
    //   597: invokevirtual getGeoModels : ()Ljava/util/HashMap;
    //   600: aload #8
    //   602: aload #12
    //   604: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   607: pop
    //   608: goto -> 632
    //   611: astore #11
    //   613: ldc 'The geo model for the custom model '%s' at '%s' appears to be corrupted. Try replacing it.'
    //   615: iconst_2
    //   616: anewarray java/lang/Object
    //   619: dup
    //   620: iconst_0
    //   621: aload_0
    //   622: aastore
    //   623: dup
    //   624: iconst_1
    //   625: aload #10
    //   627: aastore
    //   628: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   631: areturn
    //   632: aload #4
    //   634: aload #8
    //   636: invokevirtual b : (Lnet/minecraft/util/ResourceLocation;)V
    //   639: aload #4
    //   641: aload #7
    //   643: invokevirtual a : (Lnet/minecraft/util/ResourceLocation;)V
    //   646: getstatic com/schnurritv/sexmod/b_.b : Ljava/util/Map;
    //   649: aload_0
    //   650: aload #4
    //   652: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   657: pop
    //   658: getstatic org/apache/logging/log4j/Level.DEBUG : Lorg/apache/logging/log4j/Level;
    //   661: ldc 'successfully registered custom model '%s''
    //   663: iconst_1
    //   664: anewarray java/lang/Object
    //   667: dup
    //   668: iconst_0
    //   669: aload_0
    //   670: aastore
    //   671: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   674: invokestatic b : (Lorg/apache/logging/log4j/Level;Ljava/lang/String;)V
    //   677: ldc ''
    //   679: areturn
    // Exception table:
    //   from	to	target	type
    //   0	26	26	java/io/IOException
    //   82	107	107	java/io/IOException
    //   122	136	136	java/io/IOException
    //   176	203	203	java/io/IOException
    //   207	235	238	java/io/IOException
    //   207	235	259	java/lang/Exception
    //   335	372	547	java/io/IOException
    //   373	544	547	java/io/IOException
    //   403	429	474	java/lang/Throwable
    //   403	429	483	finally
    //   411	425	425	java/io/IOException
    //   429	439	442	java/io/IOException
    //   446	451	454	java/lang/Throwable
    //   474	485	483	finally
    //   485	495	498	java/io/IOException
    //   502	507	510	java/lang/Throwable
    //   568	608	611	java/lang/Exception
  }
  
  public static ResourceLocation a(String paramString) {
    a a = b.get(paramString);
    try {
      if (a == null) {
        try {
          if (!paramString.equals("cross"))
            System.out.printf("The custom model for '%s', hasn't been registered, but gamers tried to use it anyways. Crash is imminent%n", new Object[] { paramString }); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return null;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return a.b();
  }
  
  public static ResourceLocation b(String paramString) {
    a a = b.get(paramString);
    try {
      if (a == null) {
        try {
          if (!paramString.equals("cross"))
            System.out.printf("The custom texture for '%s', hasn't been registered, but gamers tried to use it anyways. Crash is imminent%n", new Object[] { paramString }); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return null;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return a.e();
  }
  
  public static GeoModel k(String paramString) {
    return (GeoModel)GeckoLibCache.getInstance().getGeoModels().get(a(paramString));
  }
  
  public static y f(String paramString) {
    a a = b.get(paramString);
    try {
      if (a == null) {
        try {
          if (!paramString.equals("cross"))
            System.out.printf("The ClothingType for '%s', hasn't been registered, but gamers tried to use it anyways. Crash is imminent%n", new Object[] { paramString }); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return y.HEAD;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return a.e;
  }
  
  public static HashSet<n> c(String paramString) {
    a a = b.get(paramString);
    try {
      if (a == null) {
        try {
          if (!paramString.equals("cross"))
            System.out.printf("The HashSet<GirlType> for '%s', hasn't been registered, but gamers tried to use it anyways. Crash is imminent%n", new Object[] { paramString }); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return new HashSet<>();
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return a.j;
  }
  
  public static HashSet<String> i(String paramString) {
    a a = b.get(paramString);
    try {
      if (a == null) {
        try {
          if (!paramString.equals("cross"))
            System.out.printf("The HashSet<String> for '%s', hasn't been registered, but gamers tried to use it anyways. Crash is imminent%n", new Object[] { paramString }); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return new HashSet<>();
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return a.c;
  }
  
  public static String g(String paramString) {
    a a = b.get(paramString);
    try {
      if (a == null) {
        try {
          if (!paramString.equals("cross"))
            System.out.printf("The author for '%s', hasn't been registered, but gamers tried to use it anyways. Crash is imminent%n", new Object[] { paramString }); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return "";
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return a.g;
  }
  
  @Nullable
  public static a d(String paramString) {
    return b.get(paramString);
  }
  
  public static HashMap<y, List<String>> a(Q paramQ) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (y y : y.values())
      hashMap.put(y, new ArrayList()); 
    for (Map.Entry<String, a> entry : b.entrySet()) {
      String str = (String)entry.getKey();
      a a = (a)entry.getValue();
      y y = a.e;
      List<String> list = (List)hashMap.get(y);
      try {
        if (!a.j.isEmpty())
          try {
            if (!a.j.contains(n.a(paramQ)))
              continue; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      list.add(str);
      hashMap.put(y, list);
    } 
    return (HashMap)hashMap;
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  public static class a {
    y e;
    
    HashSet<n> j;
    
    HashSet<String> c;
    
    String g;
    
    String h;
    
    boolean k;
    
    float f;
    
    float a;
    
    ResourceLocation i;
    
    ResourceLocation b;
    
    public String d;
    
    public a(File param1File, String param1String) {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial <init> : ()V
      //   4: aload_0
      //   5: new java/util/HashSet
      //   8: dup
      //   9: invokespecial <init> : ()V
      //   12: putfield j : Ljava/util/HashSet;
      //   15: aload_0
      //   16: new java/util/HashSet
      //   19: dup
      //   20: invokespecial <init> : ()V
      //   23: putfield c : Ljava/util/HashSet;
      //   26: aload_0
      //   27: fconst_1
      //   28: putfield f : F
      //   31: aload_0
      //   32: fconst_0
      //   33: putfield a : F
      //   36: aload_0
      //   37: aconst_null
      //   38: putfield d : Ljava/lang/String;
      //   41: new java/util/Properties
      //   44: dup
      //   45: invokespecial <init> : ()V
      //   48: astore_3
      //   49: new java/io/FileInputStream
      //   52: dup
      //   53: aload_1
      //   54: invokespecial <init> : (Ljava/io/File;)V
      //   57: astore #4
      //   59: goto -> 89
      //   62: astore #5
      //   64: aload_0
      //   65: ldc 'couldn't find cfg File for '%s'. It should have been at '%s'. Are you sure it exists?'
      //   67: iconst_2
      //   68: anewarray java/lang/Object
      //   71: dup
      //   72: iconst_0
      //   73: aload_2
      //   74: aastore
      //   75: dup
      //   76: iconst_1
      //   77: aload_1
      //   78: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   81: aastore
      //   82: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   85: putfield d : Ljava/lang/String;
      //   88: return
      //   89: aload_3
      //   90: aload #4
      //   92: invokevirtual load : (Ljava/io/InputStream;)V
      //   95: goto -> 125
      //   98: astore #5
      //   100: aload_0
      //   101: ldc 'couldn't read the cfg File for '%s' at '%s'. It appears to be corrupted. Try making a new one'
      //   103: iconst_2
      //   104: anewarray java/lang/Object
      //   107: dup
      //   108: iconst_0
      //   109: aload_2
      //   110: aastore
      //   111: dup
      //   112: iconst_1
      //   113: aload_1
      //   114: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   117: aastore
      //   118: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   121: putfield d : Ljava/lang/String;
      //   124: return
      //   125: aload_3
      //   126: ldc 'wear_type'
      //   128: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   131: astore #5
      //   133: aload #5
      //   135: ifnonnull -> 167
      //   138: aload_0
      //   139: ldc 'The cfg File for the model '%s' at '%s' is missing the 'wear_type'. Go to the bottom of the cfg File and write 'wear_type=HEAD'. Check the cfg files of my examples to see what values for 'wear_type' are possible'
      //   141: iconst_2
      //   142: anewarray java/lang/Object
      //   145: dup
      //   146: iconst_0
      //   147: aload_2
      //   148: aastore
      //   149: dup
      //   150: iconst_1
      //   151: aload_1
      //   152: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   155: aastore
      //   156: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   159: putfield d : Ljava/lang/String;
      //   162: return
      //   163: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   166: athrow
      //   167: aload_0
      //   168: aload #5
      //   170: invokestatic valueOf : (Ljava/lang/String;)Lcom/schnurritv/sexmod/y;
      //   173: putfield e : Lcom/schnurritv/sexmod/y;
      //   176: goto -> 211
      //   179: astore #6
      //   181: aload_0
      //   182: ldc 'you entered '%s' into the 'wear_type' field of the %s's cfg file at '%s'. This is not a valid value. Check my examples on what valid values are to enter into the field 'wear_type'
      //   184: iconst_3
      //   185: anewarray java/lang/Object
      //   188: dup
      //   189: iconst_0
      //   190: aload #5
      //   192: aastore
      //   193: dup
      //   194: iconst_1
      //   195: aload_2
      //   196: aastore
      //   197: dup
      //   198: iconst_2
      //   199: aload_1
      //   200: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   203: aastore
      //   204: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   207: putfield d : Ljava/lang/String;
      //   210: return
      //   211: getstatic com/schnurritv/sexmod/y.CUSTOM_BONE : Lcom/schnurritv/sexmod/y;
      //   214: aload_0
      //   215: getfield e : Lcom/schnurritv/sexmod/y;
      //   218: invokevirtual equals : (Ljava/lang/Object;)Z
      //   221: ifeq -> 282
      //   224: aload_0
      //   225: aload_3
      //   226: ldc 'custom_bone'
      //   228: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   231: putfield h : Ljava/lang/String;
      //   234: ldc ''
      //   236: aload_0
      //   237: getfield h : Ljava/lang/String;
      //   240: invokevirtual equals : (Ljava/lang/Object;)Z
      //   243: ifeq -> 282
      //   246: goto -> 253
      //   249: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   252: athrow
      //   253: aload_0
      //   254: ldc 'You selected CUSTOM_BONE as the 'wear_type' in the cfg file for '%s' at '%s', yet you left the 'custom_bone' field right underneath it empty. If you want ur model to be parented to a specific bone, you have to enter the name of that bone at the field 'custom_bone'.'
      //   256: iconst_2
      //   257: anewarray java/lang/Object
      //   260: dup
      //   261: iconst_0
      //   262: aload_2
      //   263: aastore
      //   264: dup
      //   265: iconst_1
      //   266: aload_1
      //   267: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   270: aastore
      //   271: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   274: putfield d : Ljava/lang/String;
      //   277: return
      //   278: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   281: athrow
      //   282: aload_3
      //   283: ldc 'which_girls'
      //   285: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   288: astore #6
      //   290: aload #6
      //   292: ldc ' '
      //   294: ldc ''
      //   296: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   299: astore #6
      //   301: aload #6
      //   303: ldc ','
      //   305: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
      //   308: astore #7
      //   310: aload #7
      //   312: astore #8
      //   314: aload #8
      //   316: arraylength
      //   317: istore #9
      //   319: iconst_0
      //   320: istore #10
      //   322: iload #10
      //   324: iload #9
      //   326: if_icmpge -> 390
      //   329: aload #8
      //   331: iload #10
      //   333: aaload
      //   334: astore #11
      //   336: aload_0
      //   337: getfield j : Ljava/util/HashSet;
      //   340: aload #11
      //   342: invokestatic valueOf : (Ljava/lang/String;)Lcom/schnurritv/sexmod/n;
      //   345: invokevirtual add : (Ljava/lang/Object;)Z
      //   348: pop
      //   349: goto -> 384
      //   352: astore #12
      //   354: aload_0
      //   355: ldc 'you entered '%s' as one of the girls, you put into the 'which_girls' field of the %s's cfg file at '%s'. This is not a valid value. Check my examples on what valid values are to enter into the field 'which_girls'.'
      //   357: iconst_3
      //   358: anewarray java/lang/Object
      //   361: dup
      //   362: iconst_0
      //   363: aload #11
      //   365: aastore
      //   366: dup
      //   367: iconst_1
      //   368: aload_2
      //   369: aastore
      //   370: dup
      //   371: iconst_2
      //   372: aload_1
      //   373: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   376: aastore
      //   377: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   380: putfield d : Ljava/lang/String;
      //   383: return
      //   384: iinc #10, 1
      //   387: goto -> 322
      //   390: aload_3
      //   391: ldc 'default_lighting'
      //   393: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   396: astore #8
      //   398: aload #8
      //   400: ifnonnull -> 432
      //   403: aload_0
      //   404: ldc 'The %s's cfg file at '%s' doesn't contain the field 'default_lighting'. Go to the bottom of the cfg file and write either 'default_lighting=ENABLED' or 'default_lighting=DISABLED'.'
      //   406: iconst_2
      //   407: anewarray java/lang/Object
      //   410: dup
      //   411: iconst_0
      //   412: aload_2
      //   413: aastore
      //   414: dup
      //   415: iconst_1
      //   416: aload_1
      //   417: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   420: aastore
      //   421: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   424: putfield d : Ljava/lang/String;
      //   427: return
      //   428: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   431: athrow
      //   432: ldc 'ENABLED'
      //   434: aload #8
      //   436: invokevirtual equals : (Ljava/lang/Object;)Z
      //   439: ifeq -> 454
      //   442: aload_0
      //   443: iconst_1
      //   444: putfield k : Z
      //   447: goto -> 506
      //   450: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   453: athrow
      //   454: ldc 'DISABLED'
      //   456: aload #8
      //   458: invokevirtual equals : (Ljava/lang/Object;)Z
      //   461: ifeq -> 476
      //   464: aload_0
      //   465: iconst_0
      //   466: putfield k : Z
      //   469: goto -> 506
      //   472: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   475: athrow
      //   476: aload_0
      //   477: ldc 'you entered '%s' into the 'default_lighting' field of the %s's cfg file at '%s'. This is not a valid value. Check my examples on what valid values are to enter into the field 'default_lighting'.'
      //   479: iconst_3
      //   480: anewarray java/lang/Object
      //   483: dup
      //   484: iconst_0
      //   485: aload #8
      //   487: aastore
      //   488: dup
      //   489: iconst_1
      //   490: aload_2
      //   491: aastore
      //   492: dup
      //   493: iconst_2
      //   494: aload_1
      //   495: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   498: aastore
      //   499: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   502: putfield d : Ljava/lang/String;
      //   505: return
      //   506: aload_3
      //   507: ldc 'author'
      //   509: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   512: astore #9
      //   514: aload #9
      //   516: ifnull -> 536
      //   519: ldc ''
      //   521: aload #9
      //   523: invokevirtual equals : (Ljava/lang/Object;)Z
      //   526: ifeq -> 549
      //   529: goto -> 536
      //   532: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   535: athrow
      //   536: aload_0
      //   537: ldc 'anon'
      //   539: putfield g : Ljava/lang/String;
      //   542: goto -> 555
      //   545: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   548: athrow
      //   549: aload_0
      //   550: aload #9
      //   552: putfield g : Ljava/lang/String;
      //   555: aload_3
      //   556: ldc 'bones_to_hide'
      //   558: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   561: astore #10
      //   563: aload #10
      //   565: ifnull -> 618
      //   568: ldc ''
      //   570: aload #10
      //   572: invokevirtual equals : (Ljava/lang/Object;)Z
      //   575: ifne -> 618
      //   578: goto -> 585
      //   581: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   584: athrow
      //   585: aload #10
      //   587: ldc ' '
      //   589: ldc ''
      //   591: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   594: astore #10
      //   596: aload #10
      //   598: ldc ','
      //   600: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
      //   603: astore #11
      //   605: aload_0
      //   606: getfield c : Ljava/util/HashSet;
      //   609: aload #11
      //   611: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
      //   614: invokevirtual addAll : (Ljava/util/Collection;)Z
      //   617: pop
      //   618: aload_3
      //   619: ldc 'gui_size_factor'
      //   621: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   624: astore #11
      //   626: aload #11
      //   628: ifnull -> 713
      //   631: ldc ''
      //   633: aload #11
      //   635: invokevirtual equals : (Ljava/lang/Object;)Z
      //   638: ifne -> 713
      //   641: goto -> 648
      //   644: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   647: athrow
      //   648: aload #11
      //   650: ldc ' '
      //   652: ldc ''
      //   654: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   657: astore #11
      //   659: aload #11
      //   661: ldc ','
      //   663: ldc '.'
      //   665: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   668: astore #11
      //   670: aload_0
      //   671: aload #11
      //   673: invokestatic parseFloat : (Ljava/lang/String;)F
      //   676: putfield f : F
      //   679: goto -> 713
      //   682: astore #12
      //   684: aload_0
      //   685: ldc 'you entered '%s' into the 'gui_size_factor' field of the %s's cfg file at '%s'. This is not a valid value. Check my examples on what valid values are to enter into the field 'gui_size_factor'.'
      //   687: iconst_3
      //   688: anewarray java/lang/Object
      //   691: dup
      //   692: iconst_0
      //   693: aload #11
      //   695: aastore
      //   696: dup
      //   697: iconst_1
      //   698: aload_2
      //   699: aastore
      //   700: dup
      //   701: iconst_2
      //   702: aload_1
      //   703: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   706: aastore
      //   707: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   710: putfield d : Ljava/lang/String;
      //   713: aload_3
      //   714: ldc 'gui_vertical_positioning'
      //   716: invokevirtual getProperty : (Ljava/lang/String;)Ljava/lang/String;
      //   719: astore #12
      //   721: aload #12
      //   723: ifnull -> 808
      //   726: ldc ''
      //   728: aload #12
      //   730: invokevirtual equals : (Ljava/lang/Object;)Z
      //   733: ifne -> 808
      //   736: goto -> 743
      //   739: invokestatic a : (Ljava/io/FileNotFoundException;)Ljava/io/FileNotFoundException;
      //   742: athrow
      //   743: aload #12
      //   745: ldc ' '
      //   747: ldc ''
      //   749: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   752: astore #12
      //   754: aload #12
      //   756: ldc ','
      //   758: ldc '.'
      //   760: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   763: astore #12
      //   765: aload_0
      //   766: aload #12
      //   768: invokestatic parseFloat : (Ljava/lang/String;)F
      //   771: putfield a : F
      //   774: goto -> 808
      //   777: astore #13
      //   779: aload_0
      //   780: ldc 'you entered '%s' into the 'gui_vertical_positioning' field of the %s's cfg file at '%s'. This is not a valid value. Check my examples on what valid values are to enter into the field 'gui_vertical_positioning'.'
      //   782: iconst_3
      //   783: anewarray java/lang/Object
      //   786: dup
      //   787: iconst_0
      //   788: aload #12
      //   790: aastore
      //   791: dup
      //   792: iconst_1
      //   793: aload_2
      //   794: aastore
      //   795: dup
      //   796: iconst_2
      //   797: aload_1
      //   798: invokevirtual getAbsolutePath : ()Ljava/lang/String;
      //   801: aastore
      //   802: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   805: putfield d : Ljava/lang/String;
      //   808: return
      // Exception table:
      //   from	to	target	type
      //   49	59	62	java/io/FileNotFoundException
      //   89	95	98	java/io/IOException
      //   133	163	163	java/io/FileNotFoundException
      //   167	176	179	java/lang/IllegalArgumentException
      //   211	246	249	java/io/FileNotFoundException
      //   224	278	278	java/io/FileNotFoundException
      //   336	349	352	java/lang/IllegalArgumentException
      //   398	428	428	java/io/FileNotFoundException
      //   432	450	450	java/io/FileNotFoundException
      //   454	472	472	java/io/FileNotFoundException
      //   514	529	532	java/io/FileNotFoundException
      //   519	545	545	java/io/FileNotFoundException
      //   563	578	581	java/io/FileNotFoundException
      //   626	641	644	java/io/FileNotFoundException
      //   670	679	682	java/lang/NumberFormatException
      //   721	736	739	java/io/FileNotFoundException
      //   765	774	777	java/lang/NumberFormatException
    }
    
    public float h() {
      return this.a;
    }
    
    public float d() {
      return this.f;
    }
    
    public y c() {
      return this.e;
    }
    
    public HashSet<n> f() {
      return this.j;
    }
    
    public String a() {
      return this.g;
    }
    
    public HashSet<String> g() {
      return this.c;
    }
    
    public ResourceLocation e() {
      return this.i;
    }
    
    public void a(ResourceLocation param1ResourceLocation) {
      this.i = param1ResourceLocation;
    }
    
    public ResourceLocation b() {
      return this.b;
    }
    
    public void b(ResourceLocation param1ResourceLocation) {
      this.b = param1ResourceLocation;
    }
    
    private static FileNotFoundException a(FileNotFoundException param1FileNotFoundException) {
      return param1FileNotFoundException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b_.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */