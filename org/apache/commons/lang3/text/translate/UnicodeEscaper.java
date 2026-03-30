/*     */ package org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class UnicodeEscaper
/*     */   extends CodePointTranslator
/*     */ {
/*     */   private final int below;
/*     */   private final int above;
/*     */   private final boolean between;
/*     */   
/*     */   public UnicodeEscaper() {
/*  41 */     this(0, 2147483647, true);
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
/*     */   protected UnicodeEscaper(int below, int above, boolean between) {
/*  55 */     this.below = below;
/*  56 */     this.above = above;
/*  57 */     this.between = between;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper below(int codepoint) {
/*  67 */     return outsideOf(codepoint, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper above(int codepoint) {
/*  77 */     return outsideOf(0, codepoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper outsideOf(int codepointLow, int codepointHigh) {
/*  88 */     return new UnicodeEscaper(codepointLow, codepointHigh, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper between(int codepointLow, int codepointHigh) {
/*  99 */     return new UnicodeEscaper(codepointLow, codepointHigh, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean translate(int codepoint, Writer out) throws IOException {
/* 107 */     if (this.between) {
/* 108 */       if (codepoint < this.below || codepoint > this.above) {
/* 109 */         return false;
/*     */       }
/*     */     }
/* 112 */     else if (codepoint >= this.below && codepoint <= this.above) {
/* 113 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (codepoint > 65535) {
/* 119 */       out.write(toUtf16Escape(codepoint));
/*     */     } else {
/* 121 */       out.write("\\u");
/* 122 */       out.write(HEX_DIGITS[codepoint >> 12 & 0xF]);
/* 123 */       out.write(HEX_DIGITS[codepoint >> 8 & 0xF]);
/* 124 */       out.write(HEX_DIGITS[codepoint >> 4 & 0xF]);
/* 125 */       out.write(HEX_DIGITS[codepoint & 0xF]);
/*     */     } 
/* 127 */     return true;
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
/*     */   protected String toUtf16Escape(int codepoint) {
/* 140 */     return "\\u" + hex(codepoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\UnicodeEscaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */