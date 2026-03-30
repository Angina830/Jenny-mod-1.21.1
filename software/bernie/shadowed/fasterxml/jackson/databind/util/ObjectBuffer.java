/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectBuffer
/*     */ {
/*     */   private static final int SMALL_CHUNK = 16384;
/*     */   private static final int MAX_CHUNK = 262144;
/*     */   private LinkedNode<Object[]> _head;
/*     */   private LinkedNode<Object[]> _tail;
/*     */   private int _size;
/*     */   private Object[] _freeBuffer;
/*     */   
/*     */   public Object[] resetAndStart() {
/*  68 */     _reset();
/*  69 */     if (this._freeBuffer == null) {
/*  70 */       return this._freeBuffer = new Object[12];
/*     */     }
/*  72 */     return this._freeBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] resetAndStart(Object[] base, int count) {
/*  80 */     _reset();
/*  81 */     if (this._freeBuffer == null || this._freeBuffer.length < count) {
/*  82 */       this._freeBuffer = new Object[Math.max(12, count)];
/*     */     }
/*  84 */     System.arraycopy(base, 0, this._freeBuffer, 0, count);
/*  85 */     return this._freeBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] appendCompletedChunk(Object[] fullChunk) {
/* 104 */     LinkedNode<Object[]> next = new LinkedNode(fullChunk, null);
/* 105 */     if (this._head == null) {
/* 106 */       this._head = this._tail = next;
/*     */     } else {
/* 108 */       this._tail.linkNext(next);
/* 109 */       this._tail = next;
/*     */     } 
/* 111 */     int len = fullChunk.length;
/* 112 */     this._size += len;
/*     */     
/* 114 */     if (len < 16384) {
/* 115 */       len += len;
/* 116 */     } else if (len < 262144) {
/* 117 */       len += len >> 2;
/*     */     } 
/* 119 */     return new Object[len];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] completeAndClearBuffer(Object[] lastChunk, int lastChunkEntries) {
/* 134 */     int totalSize = lastChunkEntries + this._size;
/* 135 */     Object[] result = new Object[totalSize];
/* 136 */     _copyTo(result, totalSize, lastChunk, lastChunkEntries);
/* 137 */     _reset();
/* 138 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T[] completeAndClearBuffer(Object[] lastChunk, int lastChunkEntries, Class<T> componentType) {
/* 151 */     int totalSize = lastChunkEntries + this._size;
/*     */     
/* 153 */     T[] result = (T[])Array.newInstance(componentType, totalSize);
/* 154 */     _copyTo(result, totalSize, lastChunk, lastChunkEntries);
/* 155 */     _reset();
/* 156 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void completeAndClearBuffer(Object[] lastChunk, int lastChunkEntries, List<Object> resultList) {
/* 161 */     for (LinkedNode<Object[]> n = this._head; n != null; n = n.next()) {
/* 162 */       Object[] curr = n.value();
/* 163 */       for (int j = 0, len = curr.length; j < len; j++) {
/* 164 */         resultList.add(curr[j]);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     for (int i = 0; i < lastChunkEntries; i++) {
/* 169 */       resultList.add(lastChunk[i]);
/*     */     }
/* 171 */     _reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int initialCapacity() {
/* 181 */     return (this._freeBuffer == null) ? 0 : this._freeBuffer.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int bufferedSize() {
/* 188 */     return this._size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _reset() {
/* 199 */     if (this._tail != null) {
/* 200 */       this._freeBuffer = this._tail.value();
/*     */     }
/*     */     
/* 203 */     this._head = this._tail = null;
/* 204 */     this._size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void _copyTo(Object resultArray, int totalSize, Object[] lastChunk, int lastChunkEntries) {
/* 210 */     int ptr = 0;
/*     */     
/* 212 */     for (LinkedNode<Object[]> n = this._head; n != null; n = n.next()) {
/* 213 */       Object[] curr = n.value();
/* 214 */       int len = curr.length;
/* 215 */       System.arraycopy(curr, 0, resultArray, ptr, len);
/* 216 */       ptr += len;
/*     */     } 
/* 218 */     System.arraycopy(lastChunk, 0, resultArray, ptr, lastChunkEntries);
/* 219 */     ptr += lastChunkEntries;
/*     */ 
/*     */     
/* 222 */     if (ptr != totalSize)
/* 223 */       throw new IllegalStateException("Should have gotten " + totalSize + " entries, got " + ptr); 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ObjectBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */