/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.exc;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
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
/*    */ public class UnrecognizedPropertyException
/*    */   extends PropertyBindingException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public UnrecognizedPropertyException(JsonParser p, String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
/* 24 */     super(p, msg, loc, referringClass, propName, propertyIds);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public UnrecognizedPropertyException(String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
/* 35 */     super(msg, loc, referringClass, propName, propertyIds);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UnrecognizedPropertyException from(JsonParser p, Object fromObjectOrClass, String propertyName, Collection<Object> propertyIds) {
/*    */     Class<?> ref;
/* 53 */     if (fromObjectOrClass instanceof Class) {
/* 54 */       ref = (Class)fromObjectOrClass;
/*    */     } else {
/* 56 */       ref = fromObjectOrClass.getClass();
/*    */     } 
/* 58 */     String msg = String.format("Unrecognized field \"%s\" (class %s), not marked as ignorable", new Object[] { propertyName, ref.getName() });
/*    */     
/* 60 */     UnrecognizedPropertyException e = new UnrecognizedPropertyException(p, msg, p.getCurrentLocation(), ref, propertyName, propertyIds);
/*    */ 
/*    */     
/* 63 */     e.prependPath(fromObjectOrClass, propertyName);
/* 64 */     return e;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\exc\UnrecognizedPropertyException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */