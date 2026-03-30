/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MemberKey
/*    */ {
/* 13 */   static final Class<?>[] NO_CLASSES = new Class[0];
/*    */   
/*    */   final String _name;
/*    */   
/*    */   final Class<?>[] _argTypes;
/*    */   
/*    */   public MemberKey(Method m) {
/* 20 */     this(m.getName(), m.getParameterTypes());
/*    */   }
/*    */ 
/*    */   
/*    */   public MemberKey(Constructor<?> ctor) {
/* 25 */     this("", ctor.getParameterTypes());
/*    */   }
/*    */ 
/*    */   
/*    */   public MemberKey(String name, Class<?>[] argTypes) {
/* 30 */     this._name = name;
/* 31 */     this._argTypes = (argTypes == null) ? NO_CLASSES : argTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return this._name + "(" + this._argTypes.length + "-args)";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 42 */     return this._name.hashCode() + this._argTypes.length;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 48 */     if (o == this) return true; 
/* 49 */     if (o == null) return false; 
/* 50 */     if (o.getClass() != getClass()) {
/* 51 */       return false;
/*    */     }
/* 53 */     MemberKey other = (MemberKey)o;
/* 54 */     if (!this._name.equals(other._name)) {
/* 55 */       return false;
/*    */     }
/* 57 */     Class<?>[] otherArgs = other._argTypes;
/* 58 */     int len = this._argTypes.length;
/* 59 */     if (otherArgs.length != len) {
/* 60 */       return false;
/*    */     }
/* 62 */     for (int i = 0; i < len; ) {
/* 63 */       Class<?> type1 = otherArgs[i];
/* 64 */       Class<?> type2 = this._argTypes[i];
/* 65 */       if (type1 == type2) {
/*    */         i++;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         continue;
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 85 */       return false;
/*    */     } 
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\MemberKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */