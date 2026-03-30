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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IgnoredPropertyException
/*    */   extends PropertyBindingException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public IgnoredPropertyException(JsonParser p, String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
/* 28 */     super(p, msg, loc, referringClass, propName, propertyIds);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public IgnoredPropertyException(String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
/* 39 */     super(msg, loc, referringClass, propName, propertyIds);
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
/*    */   public static IgnoredPropertyException from(JsonParser p, Object fromObjectOrClass, String propertyName, Collection<Object> propertyIds) {
/*    */     Class<?> ref;
/* 57 */     if (fromObjectOrClass instanceof Class) {
/* 58 */       ref = (Class)fromObjectOrClass;
/*    */     } else {
/* 60 */       ref = fromObjectOrClass.getClass();
/*    */     } 
/* 62 */     String msg = String.format("Ignored field \"%s\" (class %s) encountered; mapper configured not to allow this", new Object[] { propertyName, ref.getName() });
/*    */     
/* 64 */     IgnoredPropertyException e = new IgnoredPropertyException(p, msg, p.getCurrentLocation(), ref, propertyName, propertyIds);
/*    */ 
/*    */     
/* 67 */     e.prependPath(fromObjectOrClass, propertyName);
/* 68 */     return e;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\exc\IgnoredPropertyException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */