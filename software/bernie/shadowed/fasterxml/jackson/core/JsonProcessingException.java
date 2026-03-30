/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonProcessingException
/*     */   extends IOException
/*     */ {
/*     */   static final long serialVersionUID = 123L;
/*     */   protected JsonLocation _location;
/*     */   
/*     */   protected JsonProcessingException(String msg, JsonLocation loc, Throwable rootCause) {
/*  25 */     super(msg);
/*  26 */     if (rootCause != null) {
/*  27 */       initCause(rootCause);
/*     */     }
/*  29 */     this._location = loc;
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(String msg) {
/*  33 */     super(msg);
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(String msg, JsonLocation loc) {
/*  37 */     this(msg, loc, (Throwable)null);
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(String msg, Throwable rootCause) {
/*  41 */     this(msg, (JsonLocation)null, rootCause);
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(Throwable rootCause) {
/*  45 */     this((String)null, (JsonLocation)null, rootCause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonLocation getLocation() {
/*  54 */     return this._location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearLocation() {
/*  63 */     this._location = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOriginalMessage() {
/*  72 */     return super.getMessage();
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
/*     */   public Object getProcessor() {
/*  88 */     return null;
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
/*     */   protected String getMessageSuffix() {
/* 101 */     return null;
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
/*     */   public String getMessage() {
/* 113 */     String msg = super.getMessage();
/* 114 */     if (msg == null) {
/* 115 */       msg = "N/A";
/*     */     }
/* 117 */     JsonLocation loc = getLocation();
/* 118 */     String suffix = getMessageSuffix();
/*     */     
/* 120 */     if (loc != null || suffix != null) {
/* 121 */       StringBuilder sb = new StringBuilder(100);
/* 122 */       sb.append(msg);
/* 123 */       if (suffix != null) {
/* 124 */         sb.append(suffix);
/*     */       }
/* 126 */       if (loc != null) {
/* 127 */         sb.append('\n');
/* 128 */         sb.append(" at ");
/* 129 */         sb.append(loc.toString());
/*     */       } 
/* 131 */       msg = sb.toString();
/*     */     } 
/* 133 */     return msg;
/*     */   }
/*     */   public String toString() {
/* 136 */     return getClass().getName() + ": " + getMessage();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\JsonProcessingException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */