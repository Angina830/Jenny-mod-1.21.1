/*    */ package org.apache.commons.lang3.text.translate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
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
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class LookupTranslator
/*    */   extends CharSequenceTranslator
/*    */ {
/* 50 */   private final HashMap<String, String> lookupMap = new HashMap<>();
/* 51 */   private final HashSet<Character> prefixSet = new HashSet<>(); public LookupTranslator(CharSequence[]... lookup) {
/* 52 */     int _shortest = Integer.MAX_VALUE;
/* 53 */     int _longest = 0;
/* 54 */     if (lookup != null) {
/* 55 */       for (CharSequence[] seq : lookup) {
/* 56 */         this.lookupMap.put(seq[0].toString(), seq[1].toString());
/* 57 */         this.prefixSet.add(Character.valueOf(seq[0].charAt(0)));
/* 58 */         int sz = seq[0].length();
/* 59 */         if (sz < _shortest) {
/* 60 */           _shortest = sz;
/*    */         }
/* 62 */         if (sz > _longest) {
/* 63 */           _longest = sz;
/*    */         }
/*    */       } 
/*    */     }
/* 67 */     this.shortest = _shortest;
/* 68 */     this.longest = _longest;
/*    */   }
/*    */ 
/*    */   
/*    */   private final int shortest;
/*    */   
/*    */   private final int longest;
/*    */   
/*    */   public int translate(CharSequence input, int index, Writer out) throws IOException {
/* 77 */     if (this.prefixSet.contains(Character.valueOf(input.charAt(index)))) {
/* 78 */       int max = this.longest;
/* 79 */       if (index + this.longest > input.length()) {
/* 80 */         max = input.length() - index;
/*    */       }
/*    */       
/* 83 */       for (int i = max; i >= this.shortest; i--) {
/* 84 */         CharSequence subSeq = input.subSequence(index, index + i);
/* 85 */         String result = this.lookupMap.get(subSeq.toString());
/*    */         
/* 87 */         if (result != null) {
/* 88 */           out.write(result);
/* 89 */           return i;
/*    */         } 
/*    */       } 
/*    */     } 
/* 93 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\translate\LookupTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */