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
/*    */ @Deprecated
/*    */ public class UnicodeUnescaper
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   public int translate(CharSequence input, int index, Writer out) throws IOException {
/* 40 */     if (input.charAt(index) == '\\' && index + 1 < input.length() && input.charAt(index + 1) == 'u') {
/*    */       
/* 42 */       int i = 2;
/* 43 */       while (index + i < input.length() && input.charAt(index + i) == 'u') {
/* 44 */         i++;
/*    */       }
/*    */       
/* 47 */       if (index + i < input.length() && input.charAt(index + i) == '+') {
/* 48 */         i++;
/*    */       }
/*    */       
/* 51 */       if (index + i + 4 <= input.length()) {
/*    */         
/* 53 */         CharSequence unicode = input.subSequence(index + i, index + i + 4);
/*    */         
/*    */         try {
/* 56 */           int value = Integer.parseInt(unicode.toString(), 16);
/* 57 */           out.write((char)value);
/* 58 */         } catch (NumberFormatException nfe) {
/* 59 */           throw new IllegalArgumentException("Unable to parse unicode value: " + unicode, nfe);
/*    */         } 
/* 61 */         return i + 4;
/*    */       } 
/* 63 */       throw new IllegalArgumentException("Less than 4 hex digits in unicode value: '" + input.subSequence(index, input.length()) + "' due to end of CharSequence");
/*    */     } 
/*    */     
/* 66 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\UnicodeUnescaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */