/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
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
/*     */ public final class Base64Variants
/*     */ {
/*     */   static final String STD_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
/*  34 */   public static final Base64Variant MIME = new Base64Variant("MIME", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", true, '=', 76);
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
/*  45 */   public static final Base64Variant MIME_NO_LINEFEEDS = new Base64Variant(MIME, "MIME-NO-LINEFEEDS", 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final Base64Variant PEM = new Base64Variant(MIME, "PEM", true, '=', 64);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Base64Variant MODIFIED_FOR_URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  67 */     StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
/*     */     
/*  69 */     sb.setCharAt(sb.indexOf("+"), '-');
/*  70 */     sb.setCharAt(sb.indexOf("/"), '_');
/*     */ 
/*     */ 
/*     */     
/*  74 */     MODIFIED_FOR_URL = new Base64Variant("MODIFIED-FOR-URL", sb.toString(), false, false, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Base64Variant getDefaultVariant() {
/*  84 */     return MIME_NO_LINEFEEDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Base64Variant valueOf(String name) throws IllegalArgumentException {
/*  92 */     if (MIME._name.equals(name)) {
/*  93 */       return MIME;
/*     */     }
/*  95 */     if (MIME_NO_LINEFEEDS._name.equals(name)) {
/*  96 */       return MIME_NO_LINEFEEDS;
/*     */     }
/*  98 */     if (PEM._name.equals(name)) {
/*  99 */       return PEM;
/*     */     }
/* 101 */     if (MODIFIED_FOR_URL._name.equals(name)) {
/* 102 */       return MODIFIED_FOR_URL;
/*     */     }
/* 104 */     if (name == null) {
/* 105 */       name = "<null>";
/*     */     } else {
/* 107 */       name = "'" + name + "'";
/*     */     } 
/* 109 */     throw new IllegalArgumentException("No Base64Variant with name " + name);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\Base64Variants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */