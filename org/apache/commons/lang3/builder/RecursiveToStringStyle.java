/*    */ package org.apache.commons.lang3.builder;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.apache.commons.lang3.ClassUtils;
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
/*    */ 
/*    */ 
/*    */ public class RecursiveToStringStyle
/*    */   extends ToStringStyle
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public void appendDetail(StringBuffer buffer, String fieldName, Object value) {
/* 71 */     if (!ClassUtils.isPrimitiveWrapper(value.getClass()) && 
/* 72 */       !String.class.equals(value.getClass()) && 
/* 73 */       accept(value.getClass())) {
/* 74 */       buffer.append(ReflectionToStringBuilder.toString(value, this));
/*    */     } else {
/* 76 */       super.appendDetail(buffer, fieldName, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll) {
/* 82 */     appendClassName(buffer, coll);
/* 83 */     appendIdentityHashCode(buffer, coll);
/* 84 */     appendDetail(buffer, fieldName, coll.toArray());
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
/*    */   protected boolean accept(Class<?> clazz) {
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\RecursiveToStringStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */