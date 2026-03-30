/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.Serializable;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public class LRUMap<K, V>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final transient int _maxEntries;
/*    */   protected final transient ConcurrentHashMap<K, V> _map;
/*    */   protected transient int _jdkSerializeMaxEntries;
/*    */   
/*    */   public LRUMap(int initialEntries, int maxEntries) {
/* 35 */     this._map = new ConcurrentHashMap<>(initialEntries, 0.8F, 4);
/* 36 */     this._maxEntries = maxEntries;
/*    */   }
/*    */   
/*    */   public V put(K key, V value) {
/* 40 */     if (this._map.size() >= this._maxEntries)
/*    */     {
/* 42 */       synchronized (this) {
/* 43 */         if (this._map.size() >= this._maxEntries) {
/* 44 */           clear();
/*    */         }
/*    */       } 
/*    */     }
/* 48 */     return this._map.put(key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V putIfAbsent(K key, V value) {
/* 57 */     if (this._map.size() >= this._maxEntries) {
/* 58 */       synchronized (this) {
/* 59 */         if (this._map.size() >= this._maxEntries) {
/* 60 */           clear();
/*    */         }
/*    */       } 
/*    */     }
/* 64 */     return this._map.putIfAbsent(key, value);
/*    */   }
/*    */   
/*    */   public V get(Object key) {
/* 68 */     return this._map.get(key);
/*    */   }
/* 70 */   public void clear() { this._map.clear(); } public int size() {
/* 71 */     return this._map.size();
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
/*    */   private void readObject(ObjectInputStream in) throws IOException {
/* 88 */     this._jdkSerializeMaxEntries = in.readInt();
/*    */   }
/*    */   
/*    */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 92 */     out.writeInt(this._jdkSerializeMaxEntries);
/*    */   }
/*    */   
/*    */   protected Object readResolve() {
/* 96 */     return new LRUMap(this._jdkSerializeMaxEntries, this._jdkSerializeMaxEntries);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\LRUMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */