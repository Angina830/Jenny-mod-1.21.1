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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class NumericEntityEscaper
/*     */   extends CodePointTranslator
/*     */ {
/*     */   private final int below;
/*     */   private final int above;
/*     */   private final boolean between;
/*     */   
/*     */   private NumericEntityEscaper(int below, int above, boolean between) {
/*  48 */     this.below = below;
/*  49 */     this.above = above;
/*  50 */     this.between = between;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumericEntityEscaper() {
/*  57 */     this(0, 2147483647, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NumericEntityEscaper below(int codepoint) {
/*  67 */     return outsideOf(codepoint, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NumericEntityEscaper above(int codepoint) {
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
/*     */   public static NumericEntityEscaper between(int codepointLow, int codepointHigh) {
/*  88 */     return new NumericEntityEscaper(codepointLow, codepointHigh, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NumericEntityEscaper outsideOf(int codepointLow, int codepointHigh) {
/*  99 */     return new NumericEntityEscaper(codepointLow, codepointHigh, false);
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
/* 117 */     out.write("&#");
/* 118 */     out.write(Integer.toString(codepoint, 10));
/* 119 */     out.write(59);
/* 120 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\NumericEntityEscaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */