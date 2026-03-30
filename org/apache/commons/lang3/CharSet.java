/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class CharSet
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5947847346149275958L;
/*  47 */   public static final CharSet EMPTY = new CharSet(new String[] { (String)null });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final CharSet ASCII_ALPHA = new CharSet(new String[] { "a-zA-Z" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final CharSet ASCII_ALPHA_LOWER = new CharSet(new String[] { "a-z" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final CharSet ASCII_ALPHA_UPPER = new CharSet(new String[] { "A-Z" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final CharSet ASCII_NUMERIC = new CharSet(new String[] { "0-9" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected static final Map<String, CharSet> COMMON = Collections.synchronizedMap(new HashMap<>());
/*     */   
/*     */   static {
/*  81 */     COMMON.put(null, EMPTY);
/*  82 */     COMMON.put("", EMPTY);
/*  83 */     COMMON.put("a-zA-Z", ASCII_ALPHA);
/*  84 */     COMMON.put("A-Za-z", ASCII_ALPHA);
/*  85 */     COMMON.put("a-z", ASCII_ALPHA_LOWER);
/*  86 */     COMMON.put("A-Z", ASCII_ALPHA_UPPER);
/*  87 */     COMMON.put("0-9", ASCII_NUMERIC);
/*     */   }
/*     */ 
/*     */   
/*  91 */   private final Set<CharRange> set = Collections.synchronizedSet(new HashSet<>());
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
/*     */   public static CharSet getInstance(String... setStrs) {
/* 156 */     if (setStrs == null) {
/* 157 */       return null;
/*     */     }
/* 159 */     if (setStrs.length == 1) {
/* 160 */       CharSet common = COMMON.get(setStrs[0]);
/* 161 */       if (common != null) {
/* 162 */         return common;
/*     */       }
/*     */     } 
/* 165 */     return new CharSet(setStrs);
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
/*     */   protected CharSet(String... set) {
/* 178 */     for (String s : set) {
/* 179 */       add(s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void add(String str) {
/* 190 */     if (str == null) {
/*     */       return;
/*     */     }
/*     */     
/* 194 */     int len = str.length();
/* 195 */     int pos = 0;
/* 196 */     while (pos < len) {
/* 197 */       int remainder = len - pos;
/* 198 */       if (remainder >= 4 && str.charAt(pos) == '^' && str.charAt(pos + 2) == '-') {
/*     */         
/* 200 */         this.set.add(CharRange.isNotIn(str.charAt(pos + 1), str.charAt(pos + 3)));
/* 201 */         pos += 4; continue;
/* 202 */       }  if (remainder >= 3 && str.charAt(pos + 1) == '-') {
/*     */         
/* 204 */         this.set.add(CharRange.isIn(str.charAt(pos), str.charAt(pos + 2)));
/* 205 */         pos += 3; continue;
/* 206 */       }  if (remainder >= 2 && str.charAt(pos) == '^') {
/*     */         
/* 208 */         this.set.add(CharRange.isNot(str.charAt(pos + 1)));
/* 209 */         pos += 2;
/*     */         continue;
/*     */       } 
/* 212 */       this.set.add(CharRange.is(str.charAt(pos)));
/* 213 */       pos++;
/*     */     } 
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
/*     */   CharRange[] getCharRanges() {
/* 228 */     return this.set.<CharRange>toArray(new CharRange[0]);
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
/*     */   public boolean contains(char ch) {
/* 240 */     synchronized (this.set) {
/* 241 */       for (CharRange range : this.set) {
/* 242 */         if (range.contains(ch)) {
/* 243 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 247 */     return false;
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
/*     */   public boolean equals(Object obj) {
/* 265 */     if (obj == this) {
/* 266 */       return true;
/*     */     }
/* 268 */     if (!(obj instanceof CharSet)) {
/* 269 */       return false;
/*     */     }
/* 271 */     CharSet other = (CharSet)obj;
/* 272 */     return this.set.equals(other.set);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 283 */     return 89 + this.set.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 293 */     return this.set.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\CharSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */