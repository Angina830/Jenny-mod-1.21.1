/*    */ package software.bernie.shadowed.fasterxml.jackson.core.type;
/*    */ 
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
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
/*    */ public abstract class TypeReference<T>
/*    */   implements Comparable<TypeReference<T>>
/*    */ {
/*    */   protected final Type _type;
/*    */   
/*    */   protected TypeReference() {
/* 33 */     Type superClass = getClass().getGenericSuperclass();
/* 34 */     if (superClass instanceof Class) {
/* 35 */       throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     this._type = ((ParameterizedType)superClass).getActualTypeArguments()[0];
/*    */   }
/*    */   public Type getType() {
/* 47 */     return this._type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(TypeReference<T> o) {
/* 55 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\type\TypeReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */