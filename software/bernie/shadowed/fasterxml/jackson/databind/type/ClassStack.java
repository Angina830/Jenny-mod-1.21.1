/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ClassStack
/*    */ {
/*    */   protected final ClassStack _parent;
/*    */   protected final Class<?> _current;
/*    */   private ArrayList<ResolvedRecursiveType> _selfRefs;
/*    */   
/*    */   public ClassStack(Class<?> rootType) {
/* 21 */     this(null, rootType);
/*    */   }
/*    */   
/*    */   private ClassStack(ClassStack parent, Class<?> curr) {
/* 25 */     this._parent = parent;
/* 26 */     this._current = curr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClassStack child(Class<?> cls) {
/* 33 */     return new ClassStack(this, cls);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSelfReference(ResolvedRecursiveType ref) {
/* 42 */     if (this._selfRefs == null) {
/* 43 */       this._selfRefs = new ArrayList<>();
/*    */     }
/* 45 */     this._selfRefs.add(ref);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resolveSelfReferences(JavaType resolved) {
/* 55 */     if (this._selfRefs != null) {
/* 56 */       for (ResolvedRecursiveType ref : this._selfRefs) {
/* 57 */         ref.setReference(resolved);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ClassStack find(Class<?> cls) {
/* 64 */     if (this._current == cls) return this; 
/* 65 */     for (ClassStack curr = this._parent; curr != null; curr = curr._parent) {
/* 66 */       if (curr._current == cls) {
/* 67 */         return curr;
/*    */       }
/*    */     } 
/* 70 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     StringBuilder sb = new StringBuilder();
/* 76 */     sb.append("[ClassStack (self-refs: ").append((this._selfRefs == null) ? "0" : String.valueOf(this._selfRefs.size())).append(')');
/*    */ 
/*    */ 
/*    */     
/* 80 */     for (ClassStack curr = this; curr != null; curr = curr._parent) {
/* 81 */       sb.append(' ').append(curr._current.getName());
/*    */     }
/* 83 */     sb.append(']');
/* 84 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\ClassStack.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */