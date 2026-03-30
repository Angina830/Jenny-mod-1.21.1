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
/*    */ public abstract class CodePointTranslator
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   public final int translate(CharSequence input, int index, Writer out) throws IOException {
/* 40 */     int codepoint = Character.codePointAt(input, index);
/* 41 */     boolean consumed = translate(codepoint, out);
/* 42 */     return consumed ? 1 : 0;
/*    */   }
/*    */   
/*    */   public abstract boolean translate(int paramInt, Writer paramWriter) throws IOException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\CodePointTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */