/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class RandomStringUtils
/*     */ {
/*  56 */   private static final Random RANDOM = new Random();
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
/*     */   public static String random(int count) {
/*  82 */     return random(count, false, false);
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
/*     */   public static String randomAscii(int count) {
/*  96 */     return random(count, 32, 127, false, false);
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
/*     */   public static String randomAscii(int minLengthInclusive, int maxLengthExclusive) {
/* 112 */     return randomAscii(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
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
/*     */   public static String randomAlphabetic(int count) {
/* 126 */     return random(count, true, false);
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
/*     */   public static String randomAlphabetic(int minLengthInclusive, int maxLengthExclusive) {
/* 141 */     return randomAlphabetic(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
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
/*     */   public static String randomAlphanumeric(int count) {
/* 155 */     return random(count, true, true);
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
/*     */   public static String randomAlphanumeric(int minLengthInclusive, int maxLengthExclusive) {
/* 171 */     return randomAlphanumeric(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
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
/*     */   public static String randomGraph(int count) {
/* 186 */     return random(count, 33, 126, false, false);
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
/*     */   public static String randomGraph(int minLengthInclusive, int maxLengthExclusive) {
/* 201 */     return randomGraph(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
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
/*     */   public static String randomNumeric(int count) {
/* 215 */     return random(count, false, true);
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
/*     */   public static String randomNumeric(int minLengthInclusive, int maxLengthExclusive) {
/* 230 */     return randomNumeric(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
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
/*     */   public static String randomPrint(int count) {
/* 245 */     return random(count, 32, 126, false, false);
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
/*     */   public static String randomPrint(int minLengthInclusive, int maxLengthExclusive) {
/* 260 */     return randomPrint(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
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
/*     */   public static String random(int count, boolean letters, boolean numbers) {
/* 278 */     return random(count, 0, 0, letters, numbers);
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
/*     */   public static String random(int count, int start, int end, boolean letters, boolean numbers) {
/* 298 */     return random(count, start, end, letters, numbers, null, RANDOM);
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
/*     */   public static String random(int count, int start, int end, boolean letters, boolean numbers, char... chars) {
/* 322 */     return random(count, start, end, letters, numbers, chars, RANDOM);
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
/*     */   public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
/* 360 */     if (count == 0)
/* 361 */       return ""; 
/* 362 */     if (count < 0) {
/* 363 */       throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
/*     */     }
/* 365 */     if (chars != null && chars.length == 0) {
/* 366 */       throw new IllegalArgumentException("The chars array must not be empty");
/*     */     }
/*     */     
/* 369 */     if (start == 0 && end == 0) {
/* 370 */       if (chars != null) {
/* 371 */         end = chars.length;
/*     */       }
/* 373 */       else if (!letters && !numbers) {
/* 374 */         end = 1114111;
/*     */       } else {
/* 376 */         end = 123;
/* 377 */         start = 32;
/*     */       }
/*     */     
/*     */     }
/* 381 */     else if (end <= start) {
/* 382 */       throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
/*     */     } 
/*     */ 
/*     */     
/* 386 */     int zero_digit_ascii = 48;
/* 387 */     int first_letter_ascii = 65;
/*     */     
/* 389 */     if (chars == null && ((numbers && end <= 48) || (letters && end <= 65)))
/*     */     {
/* 391 */       throw new IllegalArgumentException("Parameter end (" + end + ") must be greater then (" + '0' + ") for generating digits or greater then (" + 'A' + ") for generating letters.");
/*     */     }
/*     */ 
/*     */     
/* 395 */     StringBuilder builder = new StringBuilder(count);
/* 396 */     int gap = end - start;
/*     */     
/* 398 */     while (count-- != 0) {
/*     */       int codePoint;
/* 400 */       if (chars == null) {
/* 401 */         codePoint = random.nextInt(gap) + start;
/*     */         
/* 403 */         switch (Character.getType(codePoint)) {
/*     */           case 0:
/*     */           case 18:
/*     */           case 19:
/* 407 */             count++;
/*     */             continue;
/*     */         } 
/*     */       
/*     */       } else {
/* 412 */         codePoint = chars[random.nextInt(gap) + start];
/*     */       } 
/*     */       
/* 415 */       int numberOfChars = Character.charCount(codePoint);
/* 416 */       if (count == 0 && numberOfChars > 1) {
/* 417 */         count++;
/*     */         
/*     */         continue;
/*     */       } 
/* 421 */       if ((letters && Character.isLetter(codePoint)) || (numbers && 
/* 422 */         Character.isDigit(codePoint)) || (!letters && !numbers)) {
/*     */         
/* 424 */         builder.appendCodePoint(codePoint);
/*     */         
/* 426 */         if (numberOfChars == 2) {
/* 427 */           count--;
/*     */         }
/*     */         continue;
/*     */       } 
/* 431 */       count++;
/*     */     } 
/*     */     
/* 434 */     return builder.toString();
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
/*     */   public static String random(int count, String chars) {
/* 453 */     if (chars == null) {
/* 454 */       return random(count, 0, 0, false, false, null, RANDOM);
/*     */     }
/* 456 */     return random(count, chars.toCharArray());
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
/*     */   public static String random(int count, char... chars) {
/* 472 */     if (chars == null) {
/* 473 */       return random(count, 0, 0, false, false, null, RANDOM);
/*     */     }
/* 475 */     return random(count, 0, chars.length, false, false, chars, RANDOM);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\RandomStringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */