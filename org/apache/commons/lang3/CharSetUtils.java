/*     */ package org.apache.commons.lang3;
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
/*     */ public class CharSetUtils
/*     */ {
/*     */   public static boolean containsAny(String str, String... set) {
/*  52 */     if (StringUtils.isEmpty(str) || deepEmpty(set)) {
/*  53 */       return false;
/*     */     }
/*  55 */     CharSet chars = CharSet.getInstance(set);
/*  56 */     for (char c : str.toCharArray()) {
/*  57 */       if (chars.contains(c)) {
/*  58 */         return true;
/*     */       }
/*     */     } 
/*  61 */     return false;
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
/*     */   public static int count(String str, String... set) {
/*  83 */     if (StringUtils.isEmpty(str) || deepEmpty(set)) {
/*  84 */       return 0;
/*     */     }
/*  86 */     CharSet chars = CharSet.getInstance(set);
/*  87 */     int count = 0;
/*  88 */     for (char c : str.toCharArray()) {
/*  89 */       if (chars.contains(c)) {
/*  90 */         count++;
/*     */       }
/*     */     } 
/*  93 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean deepEmpty(String[] strings) {
/* 104 */     if (strings != null) {
/* 105 */       for (String s : strings) {
/* 106 */         if (StringUtils.isNotEmpty(s)) {
/* 107 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 111 */     return true;
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
/*     */   public static String delete(String str, String... set) {
/* 133 */     if (StringUtils.isEmpty(str) || deepEmpty(set)) {
/* 134 */       return str;
/*     */     }
/* 136 */     return modify(str, set, false);
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
/*     */   public static String keep(String str, String... set) {
/* 159 */     if (str == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     if (str.isEmpty() || deepEmpty(set)) {
/* 163 */       return "";
/*     */     }
/* 165 */     return modify(str, set, true);
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
/*     */   private static String modify(String str, String[] set, boolean expect) {
/* 177 */     CharSet chars = CharSet.getInstance(set);
/* 178 */     StringBuilder buffer = new StringBuilder(str.length());
/* 179 */     char[] chrs = str.toCharArray();
/* 180 */     for (char chr : chrs) {
/* 181 */       if (chars.contains(chr) == expect) {
/* 182 */         buffer.append(chr);
/*     */       }
/*     */     } 
/* 185 */     return buffer.toString();
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
/*     */   public static String squeeze(String str, String... set) {
/* 207 */     if (StringUtils.isEmpty(str) || deepEmpty(set)) {
/* 208 */       return str;
/*     */     }
/* 210 */     CharSet chars = CharSet.getInstance(set);
/* 211 */     StringBuilder buffer = new StringBuilder(str.length());
/* 212 */     char[] chrs = str.toCharArray();
/* 213 */     int sz = chrs.length;
/* 214 */     char lastChar = chrs[0];
/* 215 */     char ch = ' ';
/* 216 */     Character inChars = null;
/* 217 */     Character notInChars = null;
/* 218 */     buffer.append(lastChar);
/* 219 */     int i = 1; while (true) { if (i < sz)
/* 220 */       { ch = chrs[i];
/* 221 */         if (ch == lastChar)
/* 222 */         { if (inChars != null && ch == inChars.charValue()) {
/*     */             continue;
/*     */           }
/* 225 */           if (notInChars == null || ch != notInChars.charValue())
/* 226 */           { if (chars.contains(ch))
/* 227 */             { inChars = Character.valueOf(ch); }
/*     */             else
/*     */             
/* 230 */             { notInChars = Character.valueOf(ch);
/*     */ 
/*     */               
/* 233 */               buffer.append(ch);
/* 234 */               lastChar = ch; }  continue; }  }  } else { break; }  buffer.append(ch); lastChar = ch; i++; }
/*     */     
/* 236 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\CharSetUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */