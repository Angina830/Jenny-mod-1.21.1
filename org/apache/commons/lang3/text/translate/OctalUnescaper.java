/*    */ package org.apache.commons.lang3.text.translate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class OctalUnescaper
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   public int translate(CharSequence input, int index, Writer out) throws IOException {
/* 43 */     int remaining = input.length() - index - 1;
/* 44 */     StringBuilder builder = new StringBuilder();
/* 45 */     if (input.charAt(index) == '\\' && remaining > 0 && isOctalDigit(input.charAt(index + 1))) {
/* 46 */       int next = index + 1;
/* 47 */       int next2 = index + 2;
/* 48 */       int next3 = index + 3;
/*    */ 
/*    */       
/* 51 */       builder.append(input.charAt(next));
/*    */       
/* 53 */       if (remaining > 1 && isOctalDigit(input.charAt(next2))) {
/* 54 */         builder.append(input.charAt(next2));
/* 55 */         if (remaining > 2 && isZeroToThree(input.charAt(next)) && isOctalDigit(input.charAt(next3))) {
/* 56 */           builder.append(input.charAt(next3));
/*    */         }
/*    */       } 
/*    */       
/* 60 */       out.write(Integer.parseInt(builder.toString(), 8));
/* 61 */       return 1 + builder.length();
/*    */     } 
/* 63 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isOctalDigit(char ch) {
/* 72 */     return (ch >= '0' && ch <= '7');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isZeroToThree(char ch) {
/* 81 */     return (ch >= '0' && ch <= '3');
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\OctalUnescaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */