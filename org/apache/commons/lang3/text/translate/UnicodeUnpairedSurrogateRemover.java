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
/*    */ @Deprecated
/*    */ public class UnicodeUnpairedSurrogateRemover
/*    */   extends CodePointTranslator
/*    */ {
/*    */   public boolean translate(int codepoint, Writer out) throws IOException {
/* 37 */     if (codepoint >= 55296 && codepoint <= 57343)
/*    */     {
/* 39 */       return true;
/*    */     }
/*    */     
/* 42 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\UnicodeUnpairedSurrogateRemover.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */