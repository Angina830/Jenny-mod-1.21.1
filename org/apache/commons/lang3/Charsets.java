/*    */ package org.apache.commons.lang3;
/*    */ 
/*    */ import java.nio.charset.Charset;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Charsets
/*    */ {
/*    */   static Charset toCharset(Charset charset) {
/* 44 */     return (charset == null) ? Charset.defaultCharset() : charset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Charset toCharset(String charsetName) {
/* 56 */     return (charsetName == null) ? Charset.defaultCharset() : Charset.forName(charsetName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String toCharsetName(String charsetName) {
/* 66 */     return (charsetName == null) ? Charset.defaultCharset().name() : charsetName;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\Charsets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */