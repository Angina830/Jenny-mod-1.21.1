/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultilineRecursiveToStringStyle
/*     */   extends RecursiveToStringStyle
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int INDENT = 2;
/*  76 */   private int spaces = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultilineRecursiveToStringStyle() {
/*  83 */     resetIndent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetIndent() {
/*  91 */     setArrayStart("{" + System.lineSeparator() + spacer(this.spaces));
/*  92 */     setArraySeparator("," + System.lineSeparator() + spacer(this.spaces));
/*  93 */     setArrayEnd(System.lineSeparator() + spacer(this.spaces - 2) + "}");
/*     */     
/*  95 */     setContentStart("[" + System.lineSeparator() + spacer(this.spaces));
/*  96 */     setFieldSeparator("," + System.lineSeparator() + spacer(this.spaces));
/*  97 */     setContentEnd(System.lineSeparator() + spacer(this.spaces - 2) + "]");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder spacer(int spaces) {
/* 107 */     StringBuilder sb = new StringBuilder();
/* 108 */     for (int i = 0; i < spaces; i++) {
/* 109 */       sb.append(" ");
/*     */     }
/* 111 */     return sb;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendDetail(StringBuffer buffer, String fieldName, Object value) {
/* 116 */     if (!ClassUtils.isPrimitiveWrapper(value.getClass()) && !String.class.equals(value.getClass()) && 
/* 117 */       accept(value.getClass())) {
/* 118 */       this.spaces += 2;
/* 119 */       resetIndent();
/* 120 */       buffer.append(ReflectionToStringBuilder.toString(value, this));
/* 121 */       this.spaces -= 2;
/* 122 */       resetIndent();
/*     */     } else {
/* 124 */       super.appendDetail(buffer, fieldName, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
/* 130 */     this.spaces += 2;
/* 131 */     resetIndent();
/* 132 */     super.appendDetail(buffer, fieldName, array);
/* 133 */     this.spaces -= 2;
/* 134 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reflectionAppendArrayDetail(StringBuffer buffer, String fieldName, Object array) {
/* 139 */     this.spaces += 2;
/* 140 */     resetIndent();
/* 141 */     super.reflectionAppendArrayDetail(buffer, fieldName, array);
/* 142 */     this.spaces -= 2;
/* 143 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, long[] array) {
/* 148 */     this.spaces += 2;
/* 149 */     resetIndent();
/* 150 */     super.appendDetail(buffer, fieldName, array);
/* 151 */     this.spaces -= 2;
/* 152 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, int[] array) {
/* 157 */     this.spaces += 2;
/* 158 */     resetIndent();
/* 159 */     super.appendDetail(buffer, fieldName, array);
/* 160 */     this.spaces -= 2;
/* 161 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, short[] array) {
/* 166 */     this.spaces += 2;
/* 167 */     resetIndent();
/* 168 */     super.appendDetail(buffer, fieldName, array);
/* 169 */     this.spaces -= 2;
/* 170 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array) {
/* 175 */     this.spaces += 2;
/* 176 */     resetIndent();
/* 177 */     super.appendDetail(buffer, fieldName, array);
/* 178 */     this.spaces -= 2;
/* 179 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, char[] array) {
/* 184 */     this.spaces += 2;
/* 185 */     resetIndent();
/* 186 */     super.appendDetail(buffer, fieldName, array);
/* 187 */     this.spaces -= 2;
/* 188 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, double[] array) {
/* 193 */     this.spaces += 2;
/* 194 */     resetIndent();
/* 195 */     super.appendDetail(buffer, fieldName, array);
/* 196 */     this.spaces -= 2;
/* 197 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, float[] array) {
/* 202 */     this.spaces += 2;
/* 203 */     resetIndent();
/* 204 */     super.appendDetail(buffer, fieldName, array);
/* 205 */     this.spaces -= 2;
/* 206 */     resetIndent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean[] array) {
/* 211 */     this.spaces += 2;
/* 212 */     resetIndent();
/* 213 */     super.appendDetail(buffer, fieldName, array);
/* 214 */     this.spaces -= 2;
/* 215 */     resetIndent();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\MultilineRecursiveToStringStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */