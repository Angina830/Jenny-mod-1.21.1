/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.FieldPosition;
/*    */ import java.text.ParseException;
/*    */ import java.text.ParsePosition;
/*    */ import java.util.Date;
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
/*    */ @Deprecated
/*    */ public class ISO8601DateFormat
/*    */   extends DateFormat
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
/* 29 */     toAppendTo.append(ISO8601Utils.format(date));
/* 30 */     return toAppendTo;
/*    */   }
/*    */ 
/*    */   
/*    */   public Date parse(String source, ParsePosition pos) {
/*    */     try {
/* 36 */       return ISO8601Utils.parse(source, pos);
/*    */     }
/* 38 */     catch (ParseException e) {
/* 39 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Date parse(String source) throws ParseException {
/* 47 */     return ISO8601Utils.parse(source, new ParsePosition(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 52 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ISO8601DateFormat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */