/*    */ package org.apache.commons.lang3.time;
/*    */ 
/*    */ import java.util.TimeZone;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastTimeZone
/*    */ {
/* 30 */   private static final Pattern GMT_PATTERN = Pattern.compile("^(?:(?i)GMT)?([+-])?(\\d\\d?)?(:?(\\d\\d?))?$");
/*    */   
/* 32 */   private static final TimeZone GREENWICH = new GmtTimeZone(false, 0, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TimeZone getGmtTimeZone() {
/* 39 */     return GREENWICH;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TimeZone getGmtTimeZone(String pattern) {
/* 50 */     if ("Z".equals(pattern) || "UTC".equals(pattern)) {
/* 51 */       return GREENWICH;
/*    */     }
/*    */     
/* 54 */     Matcher m = GMT_PATTERN.matcher(pattern);
/* 55 */     if (m.matches()) {
/* 56 */       int hours = parseInt(m.group(2));
/* 57 */       int minutes = parseInt(m.group(4));
/* 58 */       if (hours == 0 && minutes == 0) {
/* 59 */         return GREENWICH;
/*    */       }
/* 61 */       return new GmtTimeZone(parseSign(m.group(1)), hours, minutes);
/*    */     } 
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TimeZone getTimeZone(String id) {
/* 76 */     TimeZone tz = getGmtTimeZone(id);
/* 77 */     if (tz != null) {
/* 78 */       return tz;
/*    */     }
/* 80 */     return TimeZone.getTimeZone(id);
/*    */   }
/*    */   
/*    */   private static int parseInt(String group) {
/* 84 */     return (group != null) ? Integer.parseInt(group) : 0;
/*    */   }
/*    */   
/*    */   private static boolean parseSign(String group) {
/* 88 */     return (group != null && group.charAt(0) == '-');
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\FastTimeZone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */