/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GmtTimeZone
/*     */   extends TimeZone
/*     */ {
/*     */   private static final int MILLISECONDS_PER_MINUTE = 60000;
/*     */   private static final int MINUTES_PER_HOUR = 60;
/*     */   private static final int HOURS_PER_DAY = 24;
/*     */   static final long serialVersionUID = 1L;
/*     */   private final int offset;
/*     */   private final String zoneId;
/*     */   
/*     */   GmtTimeZone(boolean negate, int hours, int minutes) {
/*  40 */     if (hours >= 24) {
/*  41 */       throw new IllegalArgumentException(hours + " hours out of range");
/*     */     }
/*  43 */     if (minutes >= 60) {
/*  44 */       throw new IllegalArgumentException(minutes + " minutes out of range");
/*     */     }
/*  46 */     int milliseconds = (minutes + hours * 60) * 60000;
/*  47 */     this.offset = negate ? -milliseconds : milliseconds;
/*  48 */     this
/*     */       
/*  50 */       .zoneId = twoDigits(twoDigits((new StringBuilder(9)).append("GMT").append(negate ? 45 : 43), hours).append(':'), minutes).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static StringBuilder twoDigits(StringBuilder sb, int n) {
/*  55 */     return sb.append((char)(48 + n / 10)).append((char)(48 + n % 10));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
/*  60 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRawOffset(int offsetMillis) {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRawOffset() {
/*  70 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID() {
/*  75 */     return this.zoneId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useDaylightTime() {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inDaylightTime(Date date) {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return "[GmtTimeZone id=\"" + this.zoneId + "\",offset=" + this.offset + ']';
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  95 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 100 */     if (!(other instanceof GmtTimeZone)) {
/* 101 */       return false;
/*     */     }
/* 103 */     return (this.zoneId == ((GmtTimeZone)other).zoneId);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\GmtTimeZone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */