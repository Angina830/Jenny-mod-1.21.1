/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultIndenter
/*     */   extends DefaultPrettyPrinter.NopIndenter
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String SYS_LF;
/*     */   
/*     */   static {
/*     */     String str;
/*     */     try {
/*  22 */       str = System.getProperty("line.separator");
/*  23 */     } catch (Throwable t) {
/*  24 */       str = "\n";
/*     */     } 
/*  26 */     SYS_LF = str;
/*     */   }
/*     */   
/*  29 */   public static final DefaultIndenter SYSTEM_LINEFEED_INSTANCE = new DefaultIndenter("  ", SYS_LF);
/*     */ 
/*     */   
/*     */   private static final int INDENT_LEVELS = 16;
/*     */ 
/*     */   
/*     */   private final char[] indents;
/*     */ 
/*     */   
/*     */   private final int charsPerLevel;
/*     */   
/*     */   private final String eol;
/*     */ 
/*     */   
/*     */   public DefaultIndenter() {
/*  44 */     this("  ", SYS_LF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultIndenter(String indent, String eol) {
/*  53 */     this.charsPerLevel = indent.length();
/*     */     
/*  55 */     this.indents = new char[indent.length() * 16];
/*  56 */     int offset = 0;
/*  57 */     for (int i = 0; i < 16; i++) {
/*  58 */       indent.getChars(0, indent.length(), this.indents, offset);
/*  59 */       offset += indent.length();
/*     */     } 
/*     */     
/*  62 */     this.eol = eol;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultIndenter withLinefeed(String lf) {
/*  67 */     if (lf.equals(this.eol)) {
/*  68 */       return this;
/*     */     }
/*  70 */     return new DefaultIndenter(getIndent(), lf);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultIndenter withIndent(String indent) {
/*  75 */     if (indent.equals(getIndent())) {
/*  76 */       return this;
/*     */     }
/*  78 */     return new DefaultIndenter(indent, this.eol);
/*     */   }
/*     */   
/*     */   public boolean isInline() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeIndentation(JsonGenerator jg, int level) throws IOException {
/*  87 */     jg.writeRaw(this.eol);
/*  88 */     if (level > 0) {
/*  89 */       level *= this.charsPerLevel;
/*  90 */       while (level > this.indents.length) {
/*  91 */         jg.writeRaw(this.indents, 0, this.indents.length);
/*  92 */         level -= this.indents.length;
/*     */       } 
/*  94 */       jg.writeRaw(this.indents, 0, level);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getEol() {
/*  99 */     return this.eol;
/*     */   }
/*     */   
/*     */   public String getIndent() {
/* 103 */     return new String(this.indents, 0, this.charsPerLevel);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\DefaultIndenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */