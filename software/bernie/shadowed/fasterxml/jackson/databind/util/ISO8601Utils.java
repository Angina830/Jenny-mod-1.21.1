/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class ISO8601Utils
/*     */ {
/*  18 */   protected static final int DEF_8601_LEN = "yyyy-MM-ddThh:mm:ss.SSS+00:00".length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   private static final TimeZone TIMEZONE_Z = TimeZone.getTimeZone("UTC");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String format(Date date) {
/*  39 */     return format(date, false, TIMEZONE_Z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String format(Date date, boolean millis) {
/*  50 */     return format(date, millis, TIMEZONE_Z);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static String format(Date date, boolean millis, TimeZone tz) {
/*  55 */     return format(date, millis, tz, Locale.US);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String format(Date date, boolean millis, TimeZone tz, Locale loc) {
/*  69 */     Calendar calendar = new GregorianCalendar(tz, loc);
/*  70 */     calendar.setTime(date);
/*     */ 
/*     */     
/*  73 */     StringBuilder sb = new StringBuilder(30);
/*  74 */     sb.append(String.format("%04d-%02d-%02dT%02d:%02d:%02d", new Object[] { Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)) }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (millis) {
/*  84 */       sb.append(String.format(".%03d", new Object[] { Integer.valueOf(calendar.get(14)) }));
/*     */     }
/*     */     
/*  87 */     int offset = tz.getOffset(calendar.getTimeInMillis());
/*  88 */     if (offset != 0) {
/*  89 */       int hours = Math.abs(offset / 60000 / 60);
/*  90 */       int minutes = Math.abs(offset / 60000 % 60);
/*  91 */       sb.append(String.format("%c%02d:%02d", new Object[] { Character.valueOf((offset < 0) ? 45 : 43), Integer.valueOf(hours), Integer.valueOf(minutes) }));
/*     */     }
/*     */     else {
/*     */       
/*  95 */       sb.append('Z');
/*     */     } 
/*  97 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date parse(String date, ParsePosition pos) throws ParseException {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_2
/*     */     //   2: aload_1
/*     */     //   3: invokevirtual getIndex : ()I
/*     */     //   6: istore_3
/*     */     //   7: aload_0
/*     */     //   8: iload_3
/*     */     //   9: iinc #3, 4
/*     */     //   12: iload_3
/*     */     //   13: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   16: istore #4
/*     */     //   18: aload_0
/*     */     //   19: iload_3
/*     */     //   20: bipush #45
/*     */     //   22: invokestatic checkOffset : (Ljava/lang/String;IC)Z
/*     */     //   25: ifeq -> 31
/*     */     //   28: iinc #3, 1
/*     */     //   31: aload_0
/*     */     //   32: iload_3
/*     */     //   33: iinc #3, 2
/*     */     //   36: iload_3
/*     */     //   37: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   40: istore #5
/*     */     //   42: aload_0
/*     */     //   43: iload_3
/*     */     //   44: bipush #45
/*     */     //   46: invokestatic checkOffset : (Ljava/lang/String;IC)Z
/*     */     //   49: ifeq -> 55
/*     */     //   52: iinc #3, 1
/*     */     //   55: aload_0
/*     */     //   56: iload_3
/*     */     //   57: iinc #3, 2
/*     */     //   60: iload_3
/*     */     //   61: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   64: istore #6
/*     */     //   66: iconst_0
/*     */     //   67: istore #7
/*     */     //   69: iconst_0
/*     */     //   70: istore #8
/*     */     //   72: iconst_0
/*     */     //   73: istore #9
/*     */     //   75: iconst_0
/*     */     //   76: istore #10
/*     */     //   78: aload_0
/*     */     //   79: iload_3
/*     */     //   80: bipush #84
/*     */     //   82: invokestatic checkOffset : (Ljava/lang/String;IC)Z
/*     */     //   85: istore #11
/*     */     //   87: iload #11
/*     */     //   89: ifne -> 128
/*     */     //   92: aload_0
/*     */     //   93: invokevirtual length : ()I
/*     */     //   96: iload_3
/*     */     //   97: if_icmpgt -> 128
/*     */     //   100: new java/util/GregorianCalendar
/*     */     //   103: dup
/*     */     //   104: iload #4
/*     */     //   106: iload #5
/*     */     //   108: iconst_1
/*     */     //   109: isub
/*     */     //   110: iload #6
/*     */     //   112: invokespecial <init> : (III)V
/*     */     //   115: astore #12
/*     */     //   117: aload_1
/*     */     //   118: iload_3
/*     */     //   119: invokevirtual setIndex : (I)V
/*     */     //   122: aload #12
/*     */     //   124: invokevirtual getTime : ()Ljava/util/Date;
/*     */     //   127: areturn
/*     */     //   128: iload #11
/*     */     //   130: ifeq -> 347
/*     */     //   133: aload_0
/*     */     //   134: iinc #3, 1
/*     */     //   137: iload_3
/*     */     //   138: iinc #3, 2
/*     */     //   141: iload_3
/*     */     //   142: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   145: istore #7
/*     */     //   147: aload_0
/*     */     //   148: iload_3
/*     */     //   149: bipush #58
/*     */     //   151: invokestatic checkOffset : (Ljava/lang/String;IC)Z
/*     */     //   154: ifeq -> 160
/*     */     //   157: iinc #3, 1
/*     */     //   160: aload_0
/*     */     //   161: iload_3
/*     */     //   162: iinc #3, 2
/*     */     //   165: iload_3
/*     */     //   166: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   169: istore #8
/*     */     //   171: aload_0
/*     */     //   172: iload_3
/*     */     //   173: bipush #58
/*     */     //   175: invokestatic checkOffset : (Ljava/lang/String;IC)Z
/*     */     //   178: ifeq -> 184
/*     */     //   181: iinc #3, 1
/*     */     //   184: aload_0
/*     */     //   185: invokevirtual length : ()I
/*     */     //   188: iload_3
/*     */     //   189: if_icmple -> 347
/*     */     //   192: aload_0
/*     */     //   193: iload_3
/*     */     //   194: invokevirtual charAt : (I)C
/*     */     //   197: istore #12
/*     */     //   199: iload #12
/*     */     //   201: bipush #90
/*     */     //   203: if_icmpeq -> 347
/*     */     //   206: iload #12
/*     */     //   208: bipush #43
/*     */     //   210: if_icmpeq -> 347
/*     */     //   213: iload #12
/*     */     //   215: bipush #45
/*     */     //   217: if_icmpeq -> 347
/*     */     //   220: aload_0
/*     */     //   221: iload_3
/*     */     //   222: iinc #3, 2
/*     */     //   225: iload_3
/*     */     //   226: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   229: istore #9
/*     */     //   231: iload #9
/*     */     //   233: bipush #59
/*     */     //   235: if_icmple -> 249
/*     */     //   238: iload #9
/*     */     //   240: bipush #63
/*     */     //   242: if_icmpge -> 249
/*     */     //   245: bipush #59
/*     */     //   247: istore #9
/*     */     //   249: aload_0
/*     */     //   250: iload_3
/*     */     //   251: bipush #46
/*     */     //   253: invokestatic checkOffset : (Ljava/lang/String;IC)Z
/*     */     //   256: ifeq -> 347
/*     */     //   259: iinc #3, 1
/*     */     //   262: aload_0
/*     */     //   263: iload_3
/*     */     //   264: iconst_1
/*     */     //   265: iadd
/*     */     //   266: invokestatic indexOfNonDigit : (Ljava/lang/String;I)I
/*     */     //   269: istore #13
/*     */     //   271: iload #13
/*     */     //   273: iload_3
/*     */     //   274: iconst_3
/*     */     //   275: iadd
/*     */     //   276: invokestatic min : (II)I
/*     */     //   279: istore #14
/*     */     //   281: aload_0
/*     */     //   282: iload_3
/*     */     //   283: iload #14
/*     */     //   285: invokestatic parseInt : (Ljava/lang/String;II)I
/*     */     //   288: istore #15
/*     */     //   290: iload #14
/*     */     //   292: iload_3
/*     */     //   293: isub
/*     */     //   294: lookupswitch default -> 340, 1 -> 330, 2 -> 320
/*     */     //   320: iload #15
/*     */     //   322: bipush #10
/*     */     //   324: imul
/*     */     //   325: istore #10
/*     */     //   327: goto -> 344
/*     */     //   330: iload #15
/*     */     //   332: bipush #100
/*     */     //   334: imul
/*     */     //   335: istore #10
/*     */     //   337: goto -> 344
/*     */     //   340: iload #15
/*     */     //   342: istore #10
/*     */     //   344: iload #13
/*     */     //   346: istore_3
/*     */     //   347: aload_0
/*     */     //   348: invokevirtual length : ()I
/*     */     //   351: iload_3
/*     */     //   352: if_icmpgt -> 365
/*     */     //   355: new java/lang/IllegalArgumentException
/*     */     //   358: dup
/*     */     //   359: ldc 'No time zone indicator'
/*     */     //   361: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   364: athrow
/*     */     //   365: aconst_null
/*     */     //   366: astore #12
/*     */     //   368: aload_0
/*     */     //   369: iload_3
/*     */     //   370: invokevirtual charAt : (I)C
/*     */     //   373: istore #13
/*     */     //   375: iload #13
/*     */     //   377: bipush #90
/*     */     //   379: if_icmpne -> 393
/*     */     //   382: getstatic software/bernie/shadowed/fasterxml/jackson/databind/util/ISO8601Utils.TIMEZONE_Z : Ljava/util/TimeZone;
/*     */     //   385: astore #12
/*     */     //   387: iinc #3, 1
/*     */     //   390: goto -> 594
/*     */     //   393: iload #13
/*     */     //   395: bipush #43
/*     */     //   397: if_icmpeq -> 407
/*     */     //   400: iload #13
/*     */     //   402: bipush #45
/*     */     //   404: if_icmpne -> 561
/*     */     //   407: aload_0
/*     */     //   408: iload_3
/*     */     //   409: invokevirtual substring : (I)Ljava/lang/String;
/*     */     //   412: astore #14
/*     */     //   414: iload_3
/*     */     //   415: aload #14
/*     */     //   417: invokevirtual length : ()I
/*     */     //   420: iadd
/*     */     //   421: istore_3
/*     */     //   422: ldc '+0000'
/*     */     //   424: aload #14
/*     */     //   426: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   429: ifne -> 442
/*     */     //   432: ldc '+00:00'
/*     */     //   434: aload #14
/*     */     //   436: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   439: ifeq -> 450
/*     */     //   442: getstatic software/bernie/shadowed/fasterxml/jackson/databind/util/ISO8601Utils.TIMEZONE_Z : Ljava/util/TimeZone;
/*     */     //   445: astore #12
/*     */     //   447: goto -> 558
/*     */     //   450: new java/lang/StringBuilder
/*     */     //   453: dup
/*     */     //   454: invokespecial <init> : ()V
/*     */     //   457: ldc 'GMT'
/*     */     //   459: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   462: aload #14
/*     */     //   464: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   467: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   470: astore #15
/*     */     //   472: aload #15
/*     */     //   474: invokestatic getTimeZone : (Ljava/lang/String;)Ljava/util/TimeZone;
/*     */     //   477: astore #12
/*     */     //   479: aload #12
/*     */     //   481: invokevirtual getID : ()Ljava/lang/String;
/*     */     //   484: astore #16
/*     */     //   486: aload #16
/*     */     //   488: aload #15
/*     */     //   490: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   493: ifne -> 558
/*     */     //   496: aload #16
/*     */     //   498: ldc ':'
/*     */     //   500: ldc ''
/*     */     //   502: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   505: astore #17
/*     */     //   507: aload #17
/*     */     //   509: aload #15
/*     */     //   511: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   514: ifne -> 558
/*     */     //   517: new java/lang/IndexOutOfBoundsException
/*     */     //   520: dup
/*     */     //   521: new java/lang/StringBuilder
/*     */     //   524: dup
/*     */     //   525: invokespecial <init> : ()V
/*     */     //   528: ldc 'Mismatching time zone indicator: '
/*     */     //   530: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   533: aload #15
/*     */     //   535: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   538: ldc ' given, resolves to '
/*     */     //   540: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   543: aload #12
/*     */     //   545: invokevirtual getID : ()Ljava/lang/String;
/*     */     //   548: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   551: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   554: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   557: athrow
/*     */     //   558: goto -> 594
/*     */     //   561: new java/lang/IndexOutOfBoundsException
/*     */     //   564: dup
/*     */     //   565: new java/lang/StringBuilder
/*     */     //   568: dup
/*     */     //   569: invokespecial <init> : ()V
/*     */     //   572: ldc 'Invalid time zone indicator ''
/*     */     //   574: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   577: iload #13
/*     */     //   579: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   582: ldc '''
/*     */     //   584: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   587: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   590: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   593: athrow
/*     */     //   594: new java/util/GregorianCalendar
/*     */     //   597: dup
/*     */     //   598: aload #12
/*     */     //   600: invokespecial <init> : (Ljava/util/TimeZone;)V
/*     */     //   603: astore #14
/*     */     //   605: aload #14
/*     */     //   607: iconst_0
/*     */     //   608: invokevirtual setLenient : (Z)V
/*     */     //   611: aload #14
/*     */     //   613: iconst_1
/*     */     //   614: iload #4
/*     */     //   616: invokevirtual set : (II)V
/*     */     //   619: aload #14
/*     */     //   621: iconst_2
/*     */     //   622: iload #5
/*     */     //   624: iconst_1
/*     */     //   625: isub
/*     */     //   626: invokevirtual set : (II)V
/*     */     //   629: aload #14
/*     */     //   631: iconst_5
/*     */     //   632: iload #6
/*     */     //   634: invokevirtual set : (II)V
/*     */     //   637: aload #14
/*     */     //   639: bipush #11
/*     */     //   641: iload #7
/*     */     //   643: invokevirtual set : (II)V
/*     */     //   646: aload #14
/*     */     //   648: bipush #12
/*     */     //   650: iload #8
/*     */     //   652: invokevirtual set : (II)V
/*     */     //   655: aload #14
/*     */     //   657: bipush #13
/*     */     //   659: iload #9
/*     */     //   661: invokevirtual set : (II)V
/*     */     //   664: aload #14
/*     */     //   666: bipush #14
/*     */     //   668: iload #10
/*     */     //   670: invokevirtual set : (II)V
/*     */     //   673: aload_1
/*     */     //   674: iload_3
/*     */     //   675: invokevirtual setIndex : (I)V
/*     */     //   678: aload #14
/*     */     //   680: invokevirtual getTime : ()Ljava/util/Date;
/*     */     //   683: areturn
/*     */     //   684: astore_3
/*     */     //   685: aload_3
/*     */     //   686: astore_2
/*     */     //   687: aload_0
/*     */     //   688: ifnonnull -> 695
/*     */     //   691: aconst_null
/*     */     //   692: goto -> 719
/*     */     //   695: new java/lang/StringBuilder
/*     */     //   698: dup
/*     */     //   699: invokespecial <init> : ()V
/*     */     //   702: bipush #34
/*     */     //   704: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   707: aload_0
/*     */     //   708: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   711: bipush #34
/*     */     //   713: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   716: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   719: astore_3
/*     */     //   720: aload_2
/*     */     //   721: invokevirtual getMessage : ()Ljava/lang/String;
/*     */     //   724: astore #4
/*     */     //   726: aload #4
/*     */     //   728: ifnull -> 739
/*     */     //   731: aload #4
/*     */     //   733: invokevirtual isEmpty : ()Z
/*     */     //   736: ifeq -> 771
/*     */     //   739: new java/lang/StringBuilder
/*     */     //   742: dup
/*     */     //   743: invokespecial <init> : ()V
/*     */     //   746: ldc '('
/*     */     //   748: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   751: aload_2
/*     */     //   752: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   755: invokevirtual getName : ()Ljava/lang/String;
/*     */     //   758: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   761: ldc ')'
/*     */     //   763: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   766: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   769: astore #4
/*     */     //   771: new java/text/ParseException
/*     */     //   774: dup
/*     */     //   775: new java/lang/StringBuilder
/*     */     //   778: dup
/*     */     //   779: invokespecial <init> : ()V
/*     */     //   782: ldc 'Failed to parse date '
/*     */     //   784: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   787: aload_3
/*     */     //   788: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   791: ldc ': '
/*     */     //   793: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   796: aload #4
/*     */     //   798: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   801: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   804: aload_1
/*     */     //   805: invokevirtual getIndex : ()I
/*     */     //   808: invokespecial <init> : (Ljava/lang/String;I)V
/*     */     //   811: astore #5
/*     */     //   813: aload #5
/*     */     //   815: aload_2
/*     */     //   816: invokevirtual initCause : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*     */     //   819: pop
/*     */     //   820: aload #5
/*     */     //   822: athrow
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #116	-> 0
/*     */     //   #118	-> 2
/*     */     //   #121	-> 7
/*     */     //   #122	-> 18
/*     */     //   #123	-> 28
/*     */     //   #127	-> 31
/*     */     //   #128	-> 42
/*     */     //   #129	-> 52
/*     */     //   #133	-> 55
/*     */     //   #135	-> 66
/*     */     //   #136	-> 69
/*     */     //   #137	-> 72
/*     */     //   #138	-> 75
/*     */     //   #141	-> 78
/*     */     //   #143	-> 87
/*     */     //   #144	-> 100
/*     */     //   #146	-> 117
/*     */     //   #147	-> 122
/*     */     //   #150	-> 128
/*     */     //   #153	-> 133
/*     */     //   #154	-> 147
/*     */     //   #155	-> 157
/*     */     //   #158	-> 160
/*     */     //   #159	-> 171
/*     */     //   #160	-> 181
/*     */     //   #163	-> 184
/*     */     //   #164	-> 192
/*     */     //   #165	-> 199
/*     */     //   #166	-> 220
/*     */     //   #167	-> 231
/*     */     //   #169	-> 249
/*     */     //   #170	-> 259
/*     */     //   #171	-> 262
/*     */     //   #172	-> 271
/*     */     //   #173	-> 281
/*     */     //   #175	-> 290
/*     */     //   #177	-> 320
/*     */     //   #178	-> 327
/*     */     //   #180	-> 330
/*     */     //   #181	-> 337
/*     */     //   #183	-> 340
/*     */     //   #185	-> 344
/*     */     //   #192	-> 347
/*     */     //   #193	-> 355
/*     */     //   #196	-> 365
/*     */     //   #197	-> 368
/*     */     //   #199	-> 375
/*     */     //   #200	-> 382
/*     */     //   #201	-> 387
/*     */     //   #202	-> 393
/*     */     //   #203	-> 407
/*     */     //   #204	-> 414
/*     */     //   #206	-> 422
/*     */     //   #207	-> 442
/*     */     //   #213	-> 450
/*     */     //   #216	-> 472
/*     */     //   #218	-> 479
/*     */     //   #219	-> 486
/*     */     //   #225	-> 496
/*     */     //   #226	-> 507
/*     */     //   #227	-> 517
/*     */     //   #232	-> 558
/*     */     //   #233	-> 561
/*     */     //   #236	-> 594
/*     */     //   #237	-> 605
/*     */     //   #238	-> 611
/*     */     //   #239	-> 619
/*     */     //   #240	-> 629
/*     */     //   #241	-> 637
/*     */     //   #242	-> 646
/*     */     //   #243	-> 655
/*     */     //   #244	-> 664
/*     */     //   #246	-> 673
/*     */     //   #247	-> 678
/*     */     //   #250	-> 684
/*     */     //   #251	-> 685
/*     */     //   #253	-> 687
/*     */     //   #254	-> 720
/*     */     //   #255	-> 726
/*     */     //   #256	-> 739
/*     */     //   #258	-> 771
/*     */     //   #259	-> 813
/*     */     //   #260	-> 820
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   117	11	12	calendar	Ljava/util/Calendar;
/*     */     //   271	76	13	endOffset	I
/*     */     //   281	66	14	parseEndOffset	I
/*     */     //   290	57	15	fraction	I
/*     */     //   199	148	12	c	C
/*     */     //   507	51	17	cleaned	Ljava/lang/String;
/*     */     //   472	86	15	timezoneId	Ljava/lang/String;
/*     */     //   486	72	16	act	Ljava/lang/String;
/*     */     //   414	144	14	timezoneOffset	Ljava/lang/String;
/*     */     //   7	677	3	offset	I
/*     */     //   18	666	4	year	I
/*     */     //   42	642	5	month	I
/*     */     //   66	618	6	day	I
/*     */     //   69	615	7	hour	I
/*     */     //   72	612	8	minutes	I
/*     */     //   75	609	9	seconds	I
/*     */     //   78	606	10	milliseconds	I
/*     */     //   87	597	11	hasT	Z
/*     */     //   368	316	12	timezone	Ljava/util/TimeZone;
/*     */     //   375	309	13	timezoneIndicator	C
/*     */     //   605	79	14	calendar	Ljava/util/Calendar;
/*     */     //   685	2	3	e	Ljava/lang/Exception;
/*     */     //   0	823	0	date	Ljava/lang/String;
/*     */     //   0	823	1	pos	Ljava/text/ParsePosition;
/*     */     //   2	821	2	fail	Ljava/lang/Exception;
/*     */     //   720	103	3	input	Ljava/lang/String;
/*     */     //   726	97	4	msg	Ljava/lang/String;
/*     */     //   813	10	5	ex	Ljava/text/ParseException;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   2	127	684	java/lang/Exception
/*     */     //   128	683	684	java/lang/Exception
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkOffset(String value, int offset, char expected) {
/* 272 */     return (offset < value.length() && value.charAt(offset) == expected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
/* 285 */     if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
/* 286 */       throw new NumberFormatException(value);
/*     */     }
/*     */     
/* 289 */     int i = beginIndex;
/* 290 */     int result = 0;
/*     */     
/* 292 */     if (i < endIndex) {
/* 293 */       int digit = Character.digit(value.charAt(i++), 10);
/* 294 */       if (digit < 0) {
/* 295 */         throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
/*     */       }
/* 297 */       result = -digit;
/*     */     } 
/* 299 */     while (i < endIndex) {
/* 300 */       int digit = Character.digit(value.charAt(i++), 10);
/* 301 */       if (digit < 0) {
/* 302 */         throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
/*     */       }
/* 304 */       result *= 10;
/* 305 */       result -= digit;
/*     */     } 
/* 307 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfNonDigit(String string, int offset) {
/* 314 */     for (int i = offset; i < string.length(); i++) {
/* 315 */       char c = string.charAt(i);
/* 316 */       if (c < '0' || c > '9') return i; 
/*     */     } 
/* 318 */     return string.length();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ISO8601Utils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */