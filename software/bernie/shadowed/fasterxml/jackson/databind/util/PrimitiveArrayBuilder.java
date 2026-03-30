/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
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
/*     */ public abstract class PrimitiveArrayBuilder<T>
/*     */ {
/*     */   static final int INITIAL_CHUNK_SIZE = 12;
/*     */   static final int SMALL_CHUNK_SIZE = 16384;
/*     */   static final int MAX_CHUNK_SIZE = 262144;
/*     */   protected T _freeBuffer;
/*     */   protected Node<T> _bufferHead;
/*     */   protected Node<T> _bufferTail;
/*     */   protected int _bufferedEntryCount;
/*     */   
/*     */   public int bufferedSize() {
/*  53 */     return this._bufferedEntryCount;
/*     */   }
/*     */   
/*     */   public T resetAndStart() {
/*  57 */     _reset();
/*  58 */     return (this._freeBuffer == null) ? _constructArray(12) : this._freeBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T appendCompletedChunk(T fullChunk, int fullChunkLength) {
/*  67 */     Node<T> next = new Node<>(fullChunk, fullChunkLength);
/*  68 */     if (this._bufferHead == null) {
/*  69 */       this._bufferHead = this._bufferTail = next;
/*     */     } else {
/*  71 */       this._bufferTail.linkNext(next);
/*  72 */       this._bufferTail = next;
/*     */     } 
/*  74 */     this._bufferedEntryCount += fullChunkLength;
/*  75 */     int nextLen = fullChunkLength;
/*     */     
/*  77 */     if (nextLen < 16384) {
/*  78 */       nextLen += nextLen;
/*     */     } else {
/*  80 */       nextLen += nextLen >> 2;
/*     */     } 
/*  82 */     return _constructArray(nextLen);
/*     */   }
/*     */ 
/*     */   
/*     */   public T completeAndClearBuffer(T lastChunk, int lastChunkEntries) {
/*  87 */     int totalSize = lastChunkEntries + this._bufferedEntryCount;
/*  88 */     T resultArray = _constructArray(totalSize);
/*     */     
/*  90 */     int ptr = 0;
/*     */     
/*  92 */     for (Node<T> n = this._bufferHead; n != null; n = n.next()) {
/*  93 */       ptr = n.copyData(resultArray, ptr);
/*     */     }
/*  95 */     System.arraycopy(lastChunk, 0, resultArray, ptr, lastChunkEntries);
/*  96 */     ptr += lastChunkEntries;
/*     */ 
/*     */     
/*  99 */     if (ptr != totalSize) {
/* 100 */       throw new IllegalStateException("Should have gotten " + totalSize + " entries, got " + ptr);
/*     */     }
/* 102 */     return resultArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T _constructArray(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _reset() {
/* 122 */     if (this._bufferTail != null) {
/* 123 */       this._freeBuffer = this._bufferTail.getData();
/*     */     }
/*     */     
/* 126 */     this._bufferHead = this._bufferTail = null;
/* 127 */     this._bufferedEntryCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Node<T>
/*     */   {
/*     */     final T _data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int _dataLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Node<T> _next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node(T data, int dataLen) {
/* 159 */       this._data = data;
/* 160 */       this._dataLength = dataLen;
/*     */     }
/*     */     public T getData() {
/* 163 */       return this._data;
/*     */     }
/*     */     
/*     */     public int copyData(T dst, int ptr) {
/* 167 */       System.arraycopy(this._data, 0, dst, ptr, this._dataLength);
/* 168 */       ptr += this._dataLength;
/* 169 */       return ptr;
/*     */     }
/*     */     public Node<T> next() {
/* 172 */       return this._next;
/*     */     }
/*     */     
/*     */     public void linkNext(Node<T> next) {
/* 176 */       if (this._next != null) {
/* 177 */         throw new IllegalStateException();
/*     */       }
/* 179 */       this._next = next;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\PrimitiveArrayBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */