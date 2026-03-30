/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NamedType
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final Class<?> _class;
/*    */   protected final int _hashCode;
/*    */   protected String _name;
/*    */   
/*    */   public NamedType(Class<?> c) {
/* 16 */     this(c, null);
/*    */   }
/*    */   public NamedType(Class<?> c, String name) {
/* 19 */     this._class = c;
/* 20 */     this._hashCode = c.getName().hashCode();
/* 21 */     setName(name);
/*    */   }
/*    */   
/* 24 */   public Class<?> getType() { return this._class; }
/* 25 */   public String getName() { return this._name; } public void setName(String name) {
/* 26 */     this._name = (name == null || name.length() == 0) ? null : name;
/*    */   } public boolean hasName() {
/* 28 */     return (this._name != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 35 */     if (o == this) return true; 
/* 36 */     if (o == null) return false; 
/* 37 */     if (o.getClass() != getClass()) return false; 
/* 38 */     return (this._class == ((NamedType)o)._class);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 42 */     return this._hashCode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     return "[NamedType, class " + this._class.getName() + ", name: " + ((this._name == null) ? "null" : ("'" + this._name + "'")) + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\NamedType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */