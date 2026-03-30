/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.util.UUID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Conversion
/*      */ {
/*   66 */   private static final boolean[] TTTT = new boolean[] { true, true, true, true };
/*   67 */   private static final boolean[] FTTT = new boolean[] { false, true, true, true };
/*   68 */   private static final boolean[] TFTT = new boolean[] { true, false, true, true };
/*   69 */   private static final boolean[] FFTT = new boolean[] { false, false, true, true };
/*   70 */   private static final boolean[] TTFT = new boolean[] { true, true, false, true };
/*   71 */   private static final boolean[] FTFT = new boolean[] { false, true, false, true };
/*   72 */   private static final boolean[] TFFT = new boolean[] { true, false, false, true };
/*   73 */   private static final boolean[] FFFT = new boolean[] { false, false, false, true };
/*   74 */   private static final boolean[] TTTF = new boolean[] { true, true, true, false };
/*   75 */   private static final boolean[] FTTF = new boolean[] { false, true, true, false };
/*   76 */   private static final boolean[] TFTF = new boolean[] { true, false, true, false };
/*   77 */   private static final boolean[] FFTF = new boolean[] { false, false, true, false };
/*   78 */   private static final boolean[] TTFF = new boolean[] { true, true, false, false };
/*   79 */   private static final boolean[] FTFF = new boolean[] { false, true, false, false };
/*   80 */   private static final boolean[] TFFF = new boolean[] { true, false, false, false };
/*   81 */   private static final boolean[] FFFF = new boolean[] { false, false, false, false };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hexDigitToInt(char hexDigit) {
/*   96 */     int digit = Character.digit(hexDigit, 16);
/*   97 */     if (digit < 0) {
/*   98 */       throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */     }
/*  100 */     return digit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hexDigitMsb0ToInt(char hexDigit) {
/*  116 */     switch (hexDigit) {
/*      */       case '0':
/*  118 */         return 0;
/*      */       case '1':
/*  120 */         return 8;
/*      */       case '2':
/*  122 */         return 4;
/*      */       case '3':
/*  124 */         return 12;
/*      */       case '4':
/*  126 */         return 2;
/*      */       case '5':
/*  128 */         return 10;
/*      */       case '6':
/*  130 */         return 6;
/*      */       case '7':
/*  132 */         return 14;
/*      */       case '8':
/*  134 */         return 1;
/*      */       case '9':
/*  136 */         return 9;
/*      */       case 'A':
/*      */       case 'a':
/*  139 */         return 5;
/*      */       case 'B':
/*      */       case 'b':
/*  142 */         return 13;
/*      */       case 'C':
/*      */       case 'c':
/*  145 */         return 3;
/*      */       case 'D':
/*      */       case 'd':
/*  148 */         return 11;
/*      */       case 'E':
/*      */       case 'e':
/*  151 */         return 7;
/*      */       case 'F':
/*      */       case 'f':
/*  154 */         return 15;
/*      */     } 
/*  156 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] hexDigitToBinary(char hexDigit) {
/*  174 */     switch (hexDigit) {
/*      */       case '0':
/*  176 */         return (boolean[])FFFF.clone();
/*      */       case '1':
/*  178 */         return (boolean[])TFFF.clone();
/*      */       case '2':
/*  180 */         return (boolean[])FTFF.clone();
/*      */       case '3':
/*  182 */         return (boolean[])TTFF.clone();
/*      */       case '4':
/*  184 */         return (boolean[])FFTF.clone();
/*      */       case '5':
/*  186 */         return (boolean[])TFTF.clone();
/*      */       case '6':
/*  188 */         return (boolean[])FTTF.clone();
/*      */       case '7':
/*  190 */         return (boolean[])TTTF.clone();
/*      */       case '8':
/*  192 */         return (boolean[])FFFT.clone();
/*      */       case '9':
/*  194 */         return (boolean[])TFFT.clone();
/*      */       case 'A':
/*      */       case 'a':
/*  197 */         return (boolean[])FTFT.clone();
/*      */       case 'B':
/*      */       case 'b':
/*  200 */         return (boolean[])TTFT.clone();
/*      */       case 'C':
/*      */       case 'c':
/*  203 */         return (boolean[])FFTT.clone();
/*      */       case 'D':
/*      */       case 'd':
/*  206 */         return (boolean[])TFTT.clone();
/*      */       case 'E':
/*      */       case 'e':
/*  209 */         return (boolean[])FTTT.clone();
/*      */       case 'F':
/*      */       case 'f':
/*  212 */         return (boolean[])TTTT.clone();
/*      */     } 
/*  214 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] hexDigitMsb0ToBinary(char hexDigit) {
/*  232 */     switch (hexDigit) {
/*      */       case '0':
/*  234 */         return (boolean[])FFFF.clone();
/*      */       case '1':
/*  236 */         return (boolean[])FFFT.clone();
/*      */       case '2':
/*  238 */         return (boolean[])FFTF.clone();
/*      */       case '3':
/*  240 */         return (boolean[])FFTT.clone();
/*      */       case '4':
/*  242 */         return (boolean[])FTFF.clone();
/*      */       case '5':
/*  244 */         return (boolean[])FTFT.clone();
/*      */       case '6':
/*  246 */         return (boolean[])FTTF.clone();
/*      */       case '7':
/*  248 */         return (boolean[])FTTT.clone();
/*      */       case '8':
/*  250 */         return (boolean[])TFFF.clone();
/*      */       case '9':
/*  252 */         return (boolean[])TFFT.clone();
/*      */       case 'A':
/*      */       case 'a':
/*  255 */         return (boolean[])TFTF.clone();
/*      */       case 'B':
/*      */       case 'b':
/*  258 */         return (boolean[])TFTT.clone();
/*      */       case 'C':
/*      */       case 'c':
/*  261 */         return (boolean[])TTFF.clone();
/*      */       case 'D':
/*      */       case 'd':
/*  264 */         return (boolean[])TTFT.clone();
/*      */       case 'E':
/*      */       case 'e':
/*  267 */         return (boolean[])TTTF.clone();
/*      */       case 'F':
/*      */       case 'f':
/*  270 */         return (boolean[])TTTT.clone();
/*      */     } 
/*  272 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigit(boolean[] src) {
/*  291 */     return binaryToHexDigit(src, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigit(boolean[] src, int srcPos) {
/*  310 */     if (src.length == 0) {
/*  311 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  313 */     if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  314 */       if (src[srcPos + 2]) {
/*  315 */         if (src[srcPos + 1]) {
/*  316 */           return src[srcPos] ? 'f' : 'e';
/*      */         }
/*  318 */         return src[srcPos] ? 'd' : 'c';
/*      */       } 
/*  320 */       if (src[srcPos + 1]) {
/*  321 */         return src[srcPos] ? 'b' : 'a';
/*      */       }
/*  323 */       return src[srcPos] ? '9' : '8';
/*      */     } 
/*  325 */     if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  326 */       if (src[srcPos + 1]) {
/*  327 */         return src[srcPos] ? '7' : '6';
/*      */       }
/*  329 */       return src[srcPos] ? '5' : '4';
/*      */     } 
/*  331 */     if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  332 */       return src[srcPos] ? '3' : '2';
/*      */     }
/*  334 */     return src[srcPos] ? '1' : '0';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] src) {
/*  353 */     return binaryToHexDigitMsb0_4bits(src, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] src, int srcPos) {
/*  374 */     if (src.length > 8) {
/*  375 */       throw new IllegalArgumentException("src.length>8: src.length=" + src.length);
/*      */     }
/*  377 */     if (src.length - srcPos < 4) {
/*  378 */       throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + src.length + ", srcPos=" + srcPos);
/*      */     }
/*  380 */     if (src[srcPos + 3]) {
/*  381 */       if (src[srcPos + 2]) {
/*  382 */         if (src[srcPos + 1]) {
/*  383 */           return src[srcPos] ? 'f' : '7';
/*      */         }
/*  385 */         return src[srcPos] ? 'b' : '3';
/*      */       } 
/*  387 */       if (src[srcPos + 1]) {
/*  388 */         return src[srcPos] ? 'd' : '5';
/*      */       }
/*  390 */       return src[srcPos] ? '9' : '1';
/*      */     } 
/*  392 */     if (src[srcPos + 2]) {
/*  393 */       if (src[srcPos + 1]) {
/*  394 */         return src[srcPos] ? 'e' : '6';
/*      */       }
/*  396 */       return src[srcPos] ? 'a' : '2';
/*      */     } 
/*  398 */     if (src[srcPos + 1]) {
/*  399 */       return src[srcPos] ? 'c' : '4';
/*      */     }
/*  401 */     return src[srcPos] ? '8' : '0';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] src) {
/*  420 */     return binaryBeMsb0ToHexDigit(src, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] src, int srcPos) {
/*  440 */     if (src.length == 0) {
/*  441 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  443 */     int beSrcPos = src.length - 1 - srcPos;
/*  444 */     int srcLen = Math.min(4, beSrcPos + 1);
/*  445 */     boolean[] paddedSrc = new boolean[4];
/*  446 */     System.arraycopy(src, beSrcPos + 1 - srcLen, paddedSrc, 4 - srcLen, srcLen);
/*  447 */     src = paddedSrc;
/*  448 */     srcPos = 0;
/*  449 */     if (src[srcPos]) {
/*  450 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  451 */         if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  452 */           return (src.length > srcPos + 3 && src[srcPos + 3]) ? 'f' : 'e';
/*      */         }
/*  454 */         return (src.length > srcPos + 3 && src[srcPos + 3]) ? 'd' : 'c';
/*      */       } 
/*  456 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  457 */         return (src.length > srcPos + 3 && src[srcPos + 3]) ? 'b' : 'a';
/*      */       }
/*  459 */       return (src.length > srcPos + 3 && src[srcPos + 3]) ? '9' : '8';
/*      */     } 
/*  461 */     if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  462 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  463 */         return (src.length > srcPos + 3 && src[srcPos + 3]) ? '7' : '6';
/*      */       }
/*  465 */       return (src.length > srcPos + 3 && src[srcPos + 3]) ? '5' : '4';
/*      */     } 
/*  467 */     if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  468 */       return (src.length > srcPos + 3 && src[srcPos + 3]) ? '3' : '2';
/*      */     }
/*  470 */     return (src.length > srcPos + 3 && src[srcPos + 3]) ? '1' : '0';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char intToHexDigit(int nibble) {
/*  492 */     char c = Character.forDigit(nibble, 16);
/*  493 */     if (c == '\000') {
/*  494 */       throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
/*      */     }
/*  496 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char intToHexDigitMsb0(int nibble) {
/*  518 */     switch (nibble) {
/*      */       case 0:
/*  520 */         return '0';
/*      */       case 1:
/*  522 */         return '8';
/*      */       case 2:
/*  524 */         return '4';
/*      */       case 3:
/*  526 */         return 'c';
/*      */       case 4:
/*  528 */         return '2';
/*      */       case 5:
/*  530 */         return 'a';
/*      */       case 6:
/*  532 */         return '6';
/*      */       case 7:
/*  534 */         return 'e';
/*      */       case 8:
/*  536 */         return '1';
/*      */       case 9:
/*  538 */         return '9';
/*      */       case 10:
/*  540 */         return '5';
/*      */       case 11:
/*  542 */         return 'd';
/*      */       case 12:
/*  544 */         return '3';
/*      */       case 13:
/*  546 */         return 'b';
/*      */       case 14:
/*  548 */         return '7';
/*      */       case 15:
/*  550 */         return 'f';
/*      */     } 
/*  552 */     throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long intArrayToLong(int[] src, int srcPos, long dstInit, int dstPos, int nInts) {
/*  575 */     if ((src.length == 0 && srcPos == 0) || 0 == nInts) {
/*  576 */       return dstInit;
/*      */     }
/*  578 */     if ((nInts - 1) * 32 + dstPos >= 64) {
/*  579 */       throw new IllegalArgumentException("(nInts-1)*32+dstPos is greater or equal to than 64");
/*      */     }
/*  581 */     long out = dstInit;
/*  582 */     for (int i = 0; i < nInts; i++) {
/*  583 */       int shift = i * 32 + dstPos;
/*  584 */       long bits = (0xFFFFFFFFL & src[i + srcPos]) << shift;
/*  585 */       long mask = 4294967295L << shift;
/*  586 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  588 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long shortArrayToLong(short[] src, int srcPos, long dstInit, int dstPos, int nShorts) {
/*  610 */     if ((src.length == 0 && srcPos == 0) || 0 == nShorts) {
/*  611 */       return dstInit;
/*      */     }
/*  613 */     if ((nShorts - 1) * 16 + dstPos >= 64) {
/*  614 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greater or equal to than 64");
/*      */     }
/*  616 */     long out = dstInit;
/*  617 */     for (int i = 0; i < nShorts; i++) {
/*  618 */       int shift = i * 16 + dstPos;
/*  619 */       long bits = (0xFFFFL & src[i + srcPos]) << shift;
/*  620 */       long mask = 65535L << shift;
/*  621 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  623 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int shortArrayToInt(short[] src, int srcPos, int dstInit, int dstPos, int nShorts) {
/*  645 */     if ((src.length == 0 && srcPos == 0) || 0 == nShorts) {
/*  646 */       return dstInit;
/*      */     }
/*  648 */     if ((nShorts - 1) * 16 + dstPos >= 32) {
/*  649 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greater or equal to than 32");
/*      */     }
/*  651 */     int out = dstInit;
/*  652 */     for (int i = 0; i < nShorts; i++) {
/*  653 */       int shift = i * 16 + dstPos;
/*  654 */       int bits = (0xFFFF & src[i + srcPos]) << shift;
/*  655 */       int mask = 65535 << shift;
/*  656 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  658 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long byteArrayToLong(byte[] src, int srcPos, long dstInit, int dstPos, int nBytes) {
/*  680 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  681 */       return dstInit;
/*      */     }
/*  683 */     if ((nBytes - 1) * 8 + dstPos >= 64) {
/*  684 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greater or equal to than 64");
/*      */     }
/*  686 */     long out = dstInit;
/*  687 */     for (int i = 0; i < nBytes; i++) {
/*  688 */       int shift = i * 8 + dstPos;
/*  689 */       long bits = (0xFFL & src[i + srcPos]) << shift;
/*  690 */       long mask = 255L << shift;
/*  691 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  693 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int byteArrayToInt(byte[] src, int srcPos, int dstInit, int dstPos, int nBytes) {
/*  715 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  716 */       return dstInit;
/*      */     }
/*  718 */     if ((nBytes - 1) * 8 + dstPos >= 32) {
/*  719 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greater or equal to than 32");
/*      */     }
/*  721 */     int out = dstInit;
/*  722 */     for (int i = 0; i < nBytes; i++) {
/*  723 */       int shift = i * 8 + dstPos;
/*  724 */       int bits = (0xFF & src[i + srcPos]) << shift;
/*  725 */       int mask = 255 << shift;
/*  726 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  728 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short byteArrayToShort(byte[] src, int srcPos, short dstInit, int dstPos, int nBytes) {
/*  750 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  751 */       return dstInit;
/*      */     }
/*  753 */     if ((nBytes - 1) * 8 + dstPos >= 16) {
/*  754 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greater or equal to than 16");
/*      */     }
/*  756 */     short out = dstInit;
/*  757 */     for (int i = 0; i < nBytes; i++) {
/*  758 */       int shift = i * 8 + dstPos;
/*  759 */       int bits = (0xFF & src[i + srcPos]) << shift;
/*  760 */       int mask = 255 << shift;
/*  761 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  763 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long hexToLong(String src, int srcPos, long dstInit, int dstPos, int nHex) {
/*  783 */     if (0 == nHex) {
/*  784 */       return dstInit;
/*      */     }
/*  786 */     if ((nHex - 1) * 4 + dstPos >= 64) {
/*  787 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 64");
/*      */     }
/*  789 */     long out = dstInit;
/*  790 */     for (int i = 0; i < nHex; i++) {
/*  791 */       int shift = i * 4 + dstPos;
/*  792 */       long bits = (0xFL & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  793 */       long mask = 15L << shift;
/*  794 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  796 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hexToInt(String src, int srcPos, int dstInit, int dstPos, int nHex) {
/*  815 */     if (0 == nHex) {
/*  816 */       return dstInit;
/*      */     }
/*  818 */     if ((nHex - 1) * 4 + dstPos >= 32) {
/*  819 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 32");
/*      */     }
/*  821 */     int out = dstInit;
/*  822 */     for (int i = 0; i < nHex; i++) {
/*  823 */       int shift = i * 4 + dstPos;
/*  824 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  825 */       int mask = 15 << shift;
/*  826 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  828 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short hexToShort(String src, int srcPos, short dstInit, int dstPos, int nHex) {
/*  848 */     if (0 == nHex) {
/*  849 */       return dstInit;
/*      */     }
/*  851 */     if ((nHex - 1) * 4 + dstPos >= 16) {
/*  852 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 16");
/*      */     }
/*  854 */     short out = dstInit;
/*  855 */     for (int i = 0; i < nHex; i++) {
/*  856 */       int shift = i * 4 + dstPos;
/*  857 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  858 */       int mask = 15 << shift;
/*  859 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  861 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte hexToByte(String src, int srcPos, byte dstInit, int dstPos, int nHex) {
/*  881 */     if (0 == nHex) {
/*  882 */       return dstInit;
/*      */     }
/*  884 */     if ((nHex - 1) * 4 + dstPos >= 8) {
/*  885 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 8");
/*      */     }
/*  887 */     byte out = dstInit;
/*  888 */     for (int i = 0; i < nHex; i++) {
/*  889 */       int shift = i * 4 + dstPos;
/*  890 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  891 */       int mask = 15 << shift;
/*  892 */       out = (byte)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  894 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long binaryToLong(boolean[] src, int srcPos, long dstInit, int dstPos, int nBools) {
/*  916 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/*  917 */       return dstInit;
/*      */     }
/*  919 */     if (nBools - 1 + dstPos >= 64) {
/*  920 */       throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 64");
/*      */     }
/*  922 */     long out = dstInit;
/*  923 */     for (int i = 0; i < nBools; i++) {
/*  924 */       int shift = i + dstPos;
/*  925 */       long bits = (src[i + srcPos] ? 1L : 0L) << shift;
/*  926 */       long mask = 1L << shift;
/*  927 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  929 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binaryToInt(boolean[] src, int srcPos, int dstInit, int dstPos, int nBools) {
/*  951 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/*  952 */       return dstInit;
/*      */     }
/*  954 */     if (nBools - 1 + dstPos >= 32) {
/*  955 */       throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 32");
/*      */     }
/*  957 */     int out = dstInit;
/*  958 */     for (int i = 0; i < nBools; i++) {
/*  959 */       int shift = i + dstPos;
/*  960 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/*  961 */       int mask = 1 << shift;
/*  962 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  964 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short binaryToShort(boolean[] src, int srcPos, short dstInit, int dstPos, int nBools) {
/*  986 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/*  987 */       return dstInit;
/*      */     }
/*  989 */     if (nBools - 1 + dstPos >= 16) {
/*  990 */       throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 16");
/*      */     }
/*  992 */     short out = dstInit;
/*  993 */     for (int i = 0; i < nBools; i++) {
/*  994 */       int shift = i + dstPos;
/*  995 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/*  996 */       int mask = 1 << shift;
/*  997 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  999 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte binaryToByte(boolean[] src, int srcPos, byte dstInit, int dstPos, int nBools) {
/* 1021 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1022 */       return dstInit;
/*      */     }
/* 1024 */     if (nBools - 1 + dstPos >= 8) {
/* 1025 */       throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 8");
/*      */     }
/* 1027 */     byte out = dstInit;
/* 1028 */     for (int i = 0; i < nBools; i++) {
/* 1029 */       int shift = i + dstPos;
/* 1030 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1031 */       int mask = 1 << shift;
/* 1032 */       out = (byte)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1034 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] longToIntArray(long src, int srcPos, int[] dst, int dstPos, int nInts) {
/* 1056 */     if (0 == nInts) {
/* 1057 */       return dst;
/*      */     }
/* 1059 */     if ((nInts - 1) * 32 + srcPos >= 64) {
/* 1060 */       throw new IllegalArgumentException("(nInts-1)*32+srcPos is greater or equal to than 64");
/*      */     }
/* 1062 */     for (int i = 0; i < nInts; i++) {
/* 1063 */       int shift = i * 32 + srcPos;
/* 1064 */       dst[dstPos + i] = (int)(0xFFFFFFFFFFFFFFFFL & src >> shift);
/*      */     } 
/* 1066 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] longToShortArray(long src, int srcPos, short[] dst, int dstPos, int nShorts) {
/* 1088 */     if (0 == nShorts) {
/* 1089 */       return dst;
/*      */     }
/* 1091 */     if ((nShorts - 1) * 16 + srcPos >= 64) {
/* 1092 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greater or equal to than 64");
/*      */     }
/* 1094 */     for (int i = 0; i < nShorts; i++) {
/* 1095 */       int shift = i * 16 + srcPos;
/* 1096 */       dst[dstPos + i] = (short)(int)(0xFFFFL & src >> shift);
/*      */     } 
/* 1098 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] intToShortArray(int src, int srcPos, short[] dst, int dstPos, int nShorts) {
/* 1120 */     if (0 == nShorts) {
/* 1121 */       return dst;
/*      */     }
/* 1123 */     if ((nShorts - 1) * 16 + srcPos >= 32) {
/* 1124 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greater or equal to than 32");
/*      */     }
/* 1126 */     for (int i = 0; i < nShorts; i++) {
/* 1127 */       int shift = i * 16 + srcPos;
/* 1128 */       dst[dstPos + i] = (short)(0xFFFF & src >> shift);
/*      */     } 
/* 1130 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] longToByteArray(long src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1152 */     if (0 == nBytes) {
/* 1153 */       return dst;
/*      */     }
/* 1155 */     if ((nBytes - 1) * 8 + srcPos >= 64) {
/* 1156 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greater or equal to than 64");
/*      */     }
/* 1158 */     for (int i = 0; i < nBytes; i++) {
/* 1159 */       int shift = i * 8 + srcPos;
/* 1160 */       dst[dstPos + i] = (byte)(int)(0xFFL & src >> shift);
/*      */     } 
/* 1162 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] intToByteArray(int src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1184 */     if (0 == nBytes) {
/* 1185 */       return dst;
/*      */     }
/* 1187 */     if ((nBytes - 1) * 8 + srcPos >= 32) {
/* 1188 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greater or equal to than 32");
/*      */     }
/* 1190 */     for (int i = 0; i < nBytes; i++) {
/* 1191 */       int shift = i * 8 + srcPos;
/* 1192 */       dst[dstPos + i] = (byte)(0xFF & src >> shift);
/*      */     } 
/* 1194 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] shortToByteArray(short src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1216 */     if (0 == nBytes) {
/* 1217 */       return dst;
/*      */     }
/* 1219 */     if ((nBytes - 1) * 8 + srcPos >= 16) {
/* 1220 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greater or equal to than 16");
/*      */     }
/* 1222 */     for (int i = 0; i < nBytes; i++) {
/* 1223 */       int shift = i * 8 + srcPos;
/* 1224 */       dst[dstPos + i] = (byte)(0xFF & src >> shift);
/*      */     } 
/* 1226 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String longToHex(long src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1247 */     if (0 == nHexs) {
/* 1248 */       return dstInit;
/*      */     }
/* 1250 */     if ((nHexs - 1) * 4 + srcPos >= 64) {
/* 1251 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 64");
/*      */     }
/* 1253 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1254 */     int append = sb.length();
/* 1255 */     for (int i = 0; i < nHexs; i++) {
/* 1256 */       int shift = i * 4 + srcPos;
/* 1257 */       int bits = (int)(0xFL & src >> shift);
/* 1258 */       if (dstPos + i == append) {
/* 1259 */         append++;
/* 1260 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1262 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1265 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String intToHex(int src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1286 */     if (0 == nHexs) {
/* 1287 */       return dstInit;
/*      */     }
/* 1289 */     if ((nHexs - 1) * 4 + srcPos >= 32) {
/* 1290 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 32");
/*      */     }
/* 1292 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1293 */     int append = sb.length();
/* 1294 */     for (int i = 0; i < nHexs; i++) {
/* 1295 */       int shift = i * 4 + srcPos;
/* 1296 */       int bits = 0xF & src >> shift;
/* 1297 */       if (dstPos + i == append) {
/* 1298 */         append++;
/* 1299 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1301 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1304 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String shortToHex(short src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1325 */     if (0 == nHexs) {
/* 1326 */       return dstInit;
/*      */     }
/* 1328 */     if ((nHexs - 1) * 4 + srcPos >= 16) {
/* 1329 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 16");
/*      */     }
/* 1331 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1332 */     int append = sb.length();
/* 1333 */     for (int i = 0; i < nHexs; i++) {
/* 1334 */       int shift = i * 4 + srcPos;
/* 1335 */       int bits = 0xF & src >> shift;
/* 1336 */       if (dstPos + i == append) {
/* 1337 */         append++;
/* 1338 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1340 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1343 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String byteToHex(byte src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1364 */     if (0 == nHexs) {
/* 1365 */       return dstInit;
/*      */     }
/* 1367 */     if ((nHexs - 1) * 4 + srcPos >= 8) {
/* 1368 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 8");
/*      */     }
/* 1370 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1371 */     int append = sb.length();
/* 1372 */     for (int i = 0; i < nHexs; i++) {
/* 1373 */       int shift = i * 4 + srcPos;
/* 1374 */       int bits = 0xF & src >> shift;
/* 1375 */       if (dstPos + i == append) {
/* 1376 */         append++;
/* 1377 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1379 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1382 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] longToBinary(long src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1404 */     if (0 == nBools) {
/* 1405 */       return dst;
/*      */     }
/* 1407 */     if (nBools - 1 + srcPos >= 64) {
/* 1408 */       throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 64");
/*      */     }
/* 1410 */     for (int i = 0; i < nBools; i++) {
/* 1411 */       int shift = i + srcPos;
/* 1412 */       dst[dstPos + i] = ((0x1L & src >> shift) != 0L);
/*      */     } 
/* 1414 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] intToBinary(int src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1436 */     if (0 == nBools) {
/* 1437 */       return dst;
/*      */     }
/* 1439 */     if (nBools - 1 + srcPos >= 32) {
/* 1440 */       throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 32");
/*      */     }
/* 1442 */     for (int i = 0; i < nBools; i++) {
/* 1443 */       int shift = i + srcPos;
/* 1444 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1446 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] shortToBinary(short src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1468 */     if (0 == nBools) {
/* 1469 */       return dst;
/*      */     }
/* 1471 */     if (nBools - 1 + srcPos >= 16) {
/* 1472 */       throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 16");
/*      */     }
/* 1474 */     assert nBools - 1 < 16 - srcPos;
/* 1475 */     for (int i = 0; i < nBools; i++) {
/* 1476 */       int shift = i + srcPos;
/* 1477 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1479 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] byteToBinary(byte src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1501 */     if (0 == nBools) {
/* 1502 */       return dst;
/*      */     }
/* 1504 */     if (nBools - 1 + srcPos >= 8) {
/* 1505 */       throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 8");
/*      */     }
/* 1507 */     for (int i = 0; i < nBools; i++) {
/* 1508 */       int shift = i + srcPos;
/* 1509 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1511 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] uuidToByteArray(UUID src, byte[] dst, int dstPos, int nBytes) {
/* 1531 */     if (0 == nBytes) {
/* 1532 */       return dst;
/*      */     }
/* 1534 */     if (nBytes > 16) {
/* 1535 */       throw new IllegalArgumentException("nBytes is greater than 16");
/*      */     }
/* 1537 */     longToByteArray(src.getMostSignificantBits(), 0, dst, dstPos, Math.min(nBytes, 8));
/* 1538 */     if (nBytes >= 8) {
/* 1539 */       longToByteArray(src.getLeastSignificantBits(), 0, dst, dstPos + 8, nBytes - 8);
/*      */     }
/* 1541 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UUID byteArrayToUuid(byte[] src, int srcPos) {
/* 1558 */     if (src.length - srcPos < 16) {
/* 1559 */       throw new IllegalArgumentException("Need at least 16 bytes for UUID");
/*      */     }
/* 1561 */     return new UUID(byteArrayToLong(src, srcPos, 0L, 0, 8), byteArrayToLong(src, srcPos + 8, 0L, 0, 8));
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\Conversion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */