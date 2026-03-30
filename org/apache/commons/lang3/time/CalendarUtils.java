/*    */ package org.apache.commons.lang3.time;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CalendarUtils
/*    */ {
/* 33 */   public static final CalendarUtils INSTANCE = new CalendarUtils(Calendar.getInstance());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Calendar calendar;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CalendarUtils(Calendar calendar) {
/* 44 */     this.calendar = Objects.<Calendar>requireNonNull(calendar, "calendar");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDayOfMonth() {
/* 53 */     return this.calendar.get(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMonth() {
/* 62 */     return this.calendar.get(2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getYear() {
/* 71 */     return this.calendar.get(1);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\CalendarUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */