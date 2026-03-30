/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AnnotatedMethodMap
/*    */   implements Iterable<AnnotatedMethod>
/*    */ {
/*    */   protected Map<MemberKey, AnnotatedMethod> _methods;
/*    */   
/*    */   public AnnotatedMethodMap() {}
/*    */   
/*    */   public AnnotatedMethodMap(Map<MemberKey, AnnotatedMethod> m) {
/* 22 */     this._methods = m;
/*    */   }
/*    */   
/*    */   public int size() {
/* 26 */     return (this._methods == null) ? 0 : this._methods.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotatedMethod find(String name, Class<?>[] paramTypes) {
/* 31 */     if (this._methods == null) {
/* 32 */       return null;
/*    */     }
/* 34 */     return this._methods.get(new MemberKey(name, paramTypes));
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotatedMethod find(Method m) {
/* 39 */     if (this._methods == null) {
/* 40 */       return null;
/*    */     }
/* 42 */     return this._methods.get(new MemberKey(m));
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
/*    */   public Iterator<AnnotatedMethod> iterator() {
/* 54 */     if (this._methods == null) {
/* 55 */       return Collections.emptyIterator();
/*    */     }
/* 57 */     return this._methods.values().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotatedMethodMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */