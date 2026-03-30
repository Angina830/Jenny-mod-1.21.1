/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
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
/*    */ public abstract class FilterProvider
/*    */ {
/*    */   @Deprecated
/*    */   public abstract BeanPropertyFilter findFilter(Object paramObject);
/*    */   
/*    */   public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
/* 52 */     BeanPropertyFilter old = findFilter(filterId);
/* 53 */     if (old == null) {
/* 54 */       return null;
/*    */     }
/* 56 */     return SimpleBeanPropertyFilter.from(old);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\FilterProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */