/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayIterator<T>
/*    */   implements Iterator<T>, Iterable<T>
/*    */ {
/*    */   private final T[] _a;
/*    */   private int _index;
/*    */   
/*    */   public ArrayIterator(T[] a) {
/* 17 */     this._a = a;
/* 18 */     this._index = 0;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 22 */     return (this._index < this._a.length);
/*    */   }
/*    */   
/*    */   public T next() {
/* 26 */     if (this._index >= this._a.length) {
/* 27 */       throw new NoSuchElementException();
/*    */     }
/* 29 */     return this._a[this._index++];
/*    */   }
/*    */   
/* 32 */   public void remove() { throw new UnsupportedOperationException(); } public Iterator<T> iterator() {
/* 33 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ArrayIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */