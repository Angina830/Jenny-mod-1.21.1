/*    */ package org.apache.commons.lang3.compare;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ObjectToStringComparator
/*    */   implements Comparator<Object>, Serializable
/*    */ {
/* 37 */   public static final ObjectToStringComparator INSTANCE = new ObjectToStringComparator();
/*    */ 
/*    */ 
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 46 */     if (o1 == null && o2 == null) {
/* 47 */       return 0;
/*    */     }
/* 49 */     if (o1 == null) {
/* 50 */       return 1;
/*    */     }
/* 52 */     if (o2 == null) {
/* 53 */       return -1;
/*    */     }
/* 55 */     String string1 = o1.toString();
/* 56 */     String string2 = o2.toString();
/*    */     
/* 58 */     if (string1 == null && string2 == null) {
/* 59 */       return 0;
/*    */     }
/* 61 */     if (string1 == null) {
/* 62 */       return 1;
/*    */     }
/* 64 */     if (string2 == null) {
/* 65 */       return -1;
/*    */     }
/* 67 */     return string1.compareTo(string2);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\compare\ObjectToStringComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */