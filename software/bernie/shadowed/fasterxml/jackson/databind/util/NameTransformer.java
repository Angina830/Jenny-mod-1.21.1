/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NameTransformer
/*     */ {
/*  14 */   public static final NameTransformer NOP = new NopTransformer();
/*     */ 
/*     */   
/*     */   protected static final class NopTransformer
/*     */     extends NameTransformer
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public String transform(String name) {
/*  24 */       return name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String reverse(String transformed) {
/*  29 */       return transformed;
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
/*     */   public static NameTransformer simpleTransformer(final String prefix, final String suffix) {
/*  41 */     boolean hasPrefix = (prefix != null && prefix.length() > 0);
/*  42 */     boolean hasSuffix = (suffix != null && suffix.length() > 0);
/*     */     
/*  44 */     if (hasPrefix) {
/*  45 */       if (hasSuffix)
/*  46 */         return new NameTransformer() {
/*     */             public String transform(String name) {
/*  48 */               return prefix + name + suffix;
/*     */             }
/*     */             public String reverse(String transformed) {
/*  51 */               if (transformed.startsWith(prefix)) {
/*  52 */                 String str = transformed.substring(prefix.length());
/*  53 */                 if (str.endsWith(suffix)) {
/*  54 */                   return str.substring(0, str.length() - suffix.length());
/*     */                 }
/*     */               } 
/*  57 */               return null;
/*     */             }
/*     */             public String toString() {
/*  60 */               return "[PreAndSuffixTransformer('" + prefix + "','" + suffix + "')]";
/*     */             }
/*     */           }; 
/*  63 */       return new NameTransformer() {
/*     */           public String transform(String name) {
/*  65 */             return prefix + name;
/*     */           }
/*     */           public String reverse(String transformed) {
/*  68 */             if (transformed.startsWith(prefix)) {
/*  69 */               return transformed.substring(prefix.length());
/*     */             }
/*  71 */             return null;
/*     */           }
/*     */           
/*  74 */           public String toString() { return "[PrefixTransformer('" + prefix + "')]"; }
/*     */         };
/*     */     } 
/*  77 */     if (hasSuffix)
/*  78 */       return new NameTransformer() {
/*     */           public String transform(String name) {
/*  80 */             return name + suffix;
/*     */           }
/*     */           public String reverse(String transformed) {
/*  83 */             if (transformed.endsWith(suffix)) {
/*  84 */               return transformed.substring(0, transformed.length() - suffix.length());
/*     */             }
/*  86 */             return null;
/*     */           }
/*     */           public String toString() {
/*  89 */             return "[SuffixTransformer('" + suffix + "')]";
/*     */           }
/*     */         }; 
/*  92 */     return NOP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameTransformer chainedTransformer(NameTransformer t1, NameTransformer t2) {
/* 101 */     return new Chained(t1, t2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String transform(String paramString);
/*     */ 
/*     */   
/*     */   public abstract String reverse(String paramString);
/*     */ 
/*     */   
/*     */   public static class Chained
/*     */     extends NameTransformer
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected final NameTransformer _t1;
/*     */     
/*     */     protected final NameTransformer _t2;
/*     */ 
/*     */     
/*     */     public Chained(NameTransformer t1, NameTransformer t2) {
/* 124 */       this._t1 = t1;
/* 125 */       this._t2 = t2;
/*     */     }
/*     */ 
/*     */     
/*     */     public String transform(String name) {
/* 130 */       return this._t1.transform(this._t2.transform(name));
/*     */     }
/*     */ 
/*     */     
/*     */     public String reverse(String transformed) {
/* 135 */       transformed = this._t1.reverse(transformed);
/* 136 */       if (transformed != null) {
/* 137 */         transformed = this._t2.reverse(transformed);
/*     */       }
/* 139 */       return transformed;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 143 */       return "[ChainedTransformer(" + this._t1 + ", " + this._t2 + ")]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\NameTransformer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */