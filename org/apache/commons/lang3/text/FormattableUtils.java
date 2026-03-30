/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.Formattable;
/*     */ import java.util.Formatter;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.apache.commons.lang3.Validate;
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
/*     */ @Deprecated
/*     */ public class FormattableUtils
/*     */ {
/*     */   private static final String SIMPLEST_FORMAT = "%s";
/*     */   
/*     */   public static String toString(Formattable formattable) {
/*  69 */     return String.format("%s", new Object[] { formattable });
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
/*     */   public static Formatter append(CharSequence seq, Formatter formatter, int flags, int width, int precision) {
/*  86 */     return append(seq, formatter, flags, width, precision, ' ', null);
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
/*     */   public static Formatter append(CharSequence seq, Formatter formatter, int flags, int width, int precision, char padChar) {
/* 103 */     return append(seq, formatter, flags, width, precision, padChar, null);
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
/*     */   public static Formatter append(CharSequence seq, Formatter formatter, int flags, int width, int precision, CharSequence ellipsis) {
/* 121 */     return append(seq, formatter, flags, width, precision, ' ', ellipsis);
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
/*     */   public static Formatter append(CharSequence seq, Formatter formatter, int flags, int width, int precision, char padChar, CharSequence ellipsis) {
/* 139 */     Validate.isTrue((ellipsis == null || precision < 0 || ellipsis.length() <= precision), "Specified ellipsis '%1$s' exceeds precision of %2$s", new Object[] { ellipsis, 
/* 140 */           Integer.valueOf(precision) });
/* 141 */     StringBuilder buf = new StringBuilder(seq);
/* 142 */     if (precision >= 0 && precision < seq.length()) {
/* 143 */       CharSequence _ellipsis = (CharSequence)ObjectUtils.defaultIfNull(ellipsis, "");
/* 144 */       buf.replace(precision - _ellipsis.length(), seq.length(), _ellipsis.toString());
/*     */     } 
/* 146 */     boolean leftJustify = ((flags & 0x1) == 1);
/* 147 */     for (int i = buf.length(); i < width; i++) {
/* 148 */       buf.insert(leftJustify ? i : 0, padChar);
/*     */     }
/* 150 */     formatter.format(buf.toString(), new Object[0]);
/* 151 */     return formatter;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\FormattableUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */