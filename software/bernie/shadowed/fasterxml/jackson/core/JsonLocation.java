/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonLocation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int MAX_CONTENT_SNIPPET = 500;
/*  36 */   public static final JsonLocation NA = new JsonLocation(null, -1L, -1L, -1, -1);
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long _totalBytes;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long _totalChars;
/*     */ 
/*     */   
/*     */   protected final int _lineNr;
/*     */ 
/*     */   
/*     */   protected final int _columnNr;
/*     */ 
/*     */   
/*     */   final transient Object _sourceRef;
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonLocation(Object srcRef, long totalChars, int lineNr, int colNr) {
/*  58 */     this(srcRef, -1L, totalChars, lineNr, colNr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonLocation(Object sourceRef, long totalBytes, long totalChars, int lineNr, int columnNr) {
/*  64 */     this._sourceRef = sourceRef;
/*  65 */     this._totalBytes = totalBytes;
/*  66 */     this._totalChars = totalChars;
/*  67 */     this._lineNr = lineNr;
/*  68 */     this._columnNr = columnNr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSourceRef() {
/*  79 */     return this._sourceRef;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLineNr() {
/*  84 */     return this._lineNr;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnNr() {
/*  89 */     return this._columnNr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCharOffset() {
/*  95 */     return this._totalChars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getByteOffset() {
/* 103 */     return this._totalBytes;
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
/*     */   public String sourceDescription() {
/* 116 */     return _appendSourceDesc(new StringBuilder(100)).toString();
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
/*     */   public int hashCode() {
/* 128 */     int hash = (this._sourceRef == null) ? 1 : this._sourceRef.hashCode();
/* 129 */     hash ^= this._lineNr;
/* 130 */     hash += this._columnNr;
/* 131 */     hash ^= (int)this._totalChars;
/* 132 */     hash += (int)this._totalBytes;
/* 133 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 139 */     if (other == this) return true; 
/* 140 */     if (other == null) return false; 
/* 141 */     if (!(other instanceof JsonLocation)) return false; 
/* 142 */     JsonLocation otherLoc = (JsonLocation)other;
/*     */     
/* 144 */     if (this._sourceRef == null)
/* 145 */     { if (otherLoc._sourceRef != null) return false;  }
/* 146 */     else if (!this._sourceRef.equals(otherLoc._sourceRef)) { return false; }
/*     */     
/* 148 */     return (this._lineNr == otherLoc._lineNr && this._columnNr == otherLoc._columnNr && this._totalChars == otherLoc._totalChars && getByteOffset() == otherLoc.getByteOffset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     StringBuilder sb = new StringBuilder(80);
/* 159 */     sb.append("[Source: ");
/* 160 */     _appendSourceDesc(sb);
/* 161 */     sb.append("; line: ");
/* 162 */     sb.append(this._lineNr);
/* 163 */     sb.append(", column: ");
/* 164 */     sb.append(this._columnNr);
/* 165 */     sb.append(']');
/* 166 */     return sb.toString();
/*     */   }
/*     */   
/*     */   protected StringBuilder _appendSourceDesc(StringBuilder sb) {
/*     */     int len;
/* 171 */     Object srcRef = this._sourceRef;
/*     */     
/* 173 */     if (srcRef == null) {
/* 174 */       sb.append("UNKNOWN");
/* 175 */       return sb;
/*     */     } 
/*     */     
/* 178 */     Class<?> srcType = (srcRef instanceof Class) ? (Class)srcRef : srcRef.getClass();
/*     */     
/* 180 */     String tn = srcType.getName();
/*     */     
/* 182 */     if (tn.startsWith("java.")) {
/* 183 */       tn = srcType.getSimpleName();
/* 184 */     } else if (srcRef instanceof byte[]) {
/* 185 */       tn = "byte[]";
/* 186 */     } else if (srcRef instanceof char[]) {
/* 187 */       tn = "char[]";
/*     */     } 
/* 189 */     sb.append('(').append(tn).append(')');
/*     */ 
/*     */     
/* 192 */     String charStr = " chars";
/*     */     
/* 194 */     if (srcRef instanceof CharSequence) {
/* 195 */       CharSequence cs = (CharSequence)srcRef;
/* 196 */       len = cs.length();
/* 197 */       len -= _append(sb, cs.subSequence(0, Math.min(len, 500)).toString());
/* 198 */     } else if (srcRef instanceof char[]) {
/* 199 */       char[] ch = (char[])srcRef;
/* 200 */       len = ch.length;
/* 201 */       len -= _append(sb, new String(ch, 0, Math.min(len, 500)));
/* 202 */     } else if (srcRef instanceof byte[]) {
/* 203 */       byte[] b = (byte[])srcRef;
/* 204 */       int maxLen = Math.min(b.length, 500);
/* 205 */       _append(sb, new String(b, 0, maxLen, Charset.forName("UTF-8")));
/* 206 */       len = b.length - maxLen;
/* 207 */       charStr = " bytes";
/*     */     } else {
/* 209 */       len = 0;
/*     */     } 
/* 211 */     if (len > 0) {
/* 212 */       sb.append("[truncated ").append(len).append(charStr).append(']');
/*     */     }
/* 214 */     return sb;
/*     */   }
/*     */   
/*     */   private int _append(StringBuilder sb, String content) {
/* 218 */     sb.append('"').append(content).append('"');
/* 219 */     return content.length();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\JsonLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */