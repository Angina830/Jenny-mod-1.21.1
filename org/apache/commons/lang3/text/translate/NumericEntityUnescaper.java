/*     */ package org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class NumericEntityUnescaper
/*     */   extends CharSequenceTranslator
/*     */ {
/*     */   private final EnumSet<OPTION> options;
/*     */   
/*     */   public enum OPTION
/*     */   {
/*  40 */     semiColonRequired, semiColonOptional, errorIfNoSemiColon;
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
/*     */   public NumericEntityUnescaper(OPTION... options) {
/*  63 */     if (options.length > 0) {
/*  64 */       this.options = EnumSet.copyOf(Arrays.asList(options));
/*     */     } else {
/*  66 */       this.options = EnumSet.copyOf(Collections.singletonList(OPTION.semiColonRequired));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSet(OPTION option) {
/*  77 */     return (this.options != null && this.options.contains(option));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int translate(CharSequence input, int index, Writer out) throws IOException {
/*  85 */     int seqEnd = input.length();
/*     */     
/*  87 */     if (input.charAt(index) == '&' && index < seqEnd - 2 && input.charAt(index + 1) == '#') {
/*  88 */       int entityValue, start = index + 2;
/*  89 */       boolean isHex = false;
/*     */       
/*  91 */       char firstChar = input.charAt(start);
/*  92 */       if (firstChar == 'x' || firstChar == 'X') {
/*  93 */         start++;
/*  94 */         isHex = true;
/*     */ 
/*     */         
/*  97 */         if (start == seqEnd) {
/*  98 */           return 0;
/*     */         }
/*     */       } 
/*     */       
/* 102 */       int end = start;
/*     */       
/* 104 */       while (end < seqEnd && ((input.charAt(end) >= '0' && input.charAt(end) <= '9') || (input
/* 105 */         .charAt(end) >= 'a' && input.charAt(end) <= 'f') || (input
/* 106 */         .charAt(end) >= 'A' && input.charAt(end) <= 'F'))) {
/* 107 */         end++;
/*     */       }
/*     */       
/* 110 */       boolean semiNext = (end != seqEnd && input.charAt(end) == ';');
/*     */       
/* 112 */       if (!semiNext) {
/* 113 */         if (isSet(OPTION.semiColonRequired)) {
/* 114 */           return 0;
/*     */         }
/* 116 */         if (isSet(OPTION.errorIfNoSemiColon)) {
/* 117 */           throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 123 */         if (isHex) {
/* 124 */           entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
/*     */         } else {
/* 126 */           entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
/*     */         } 
/* 128 */       } catch (NumberFormatException nfe) {
/* 129 */         return 0;
/*     */       } 
/*     */       
/* 132 */       if (entityValue > 65535) {
/* 133 */         char[] chars = Character.toChars(entityValue);
/* 134 */         out.write(chars[0]);
/* 135 */         out.write(chars[1]);
/*     */       } else {
/* 137 */         out.write(entityValue);
/*     */       } 
/*     */       
/* 140 */       return 2 + end - start + (isHex ? 1 : 0) + (semiNext ? 1 : 0);
/*     */     } 
/* 142 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\NumericEntityUnescaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */