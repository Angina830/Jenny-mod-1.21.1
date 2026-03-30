/*     */ package org.apache.commons.lang3.concurrent.locks;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.Supplier;
/*     */ import org.apache.commons.lang3.function.Failable;
/*     */ import org.apache.commons.lang3.function.FailableConsumer;
/*     */ import org.apache.commons.lang3.function.FailableFunction;
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
/*     */ public class LockingVisitors
/*     */ {
/*     */   public static class LockVisitor<O, L>
/*     */   {
/*     */     private final L lock;
/*     */     private final O object;
/*     */     private final Supplier<Lock> readLockSupplier;
/*     */     private final Supplier<Lock> writeLockSupplier;
/*     */     
/*     */     protected LockVisitor(O object, L lock, Supplier<Lock> readLockSupplier, Supplier<Lock> writeLockSupplier) {
/* 123 */       this.object = Objects.requireNonNull(object, "object");
/* 124 */       this.lock = Objects.requireNonNull(lock, "lock");
/* 125 */       this.readLockSupplier = Objects.<Supplier<Lock>>requireNonNull(readLockSupplier, "readLockSupplier");
/* 126 */       this.writeLockSupplier = Objects.<Supplier<Lock>>requireNonNull(writeLockSupplier, "writeLockSupplier");
/*     */     }
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
/*     */     public void acceptReadLocked(FailableConsumer<O, ?> consumer) {
/* 148 */       lockAcceptUnlock(this.readLockSupplier, consumer);
/*     */     }
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
/*     */     public void acceptWriteLocked(FailableConsumer<O, ?> consumer) {
/* 170 */       lockAcceptUnlock(this.writeLockSupplier, consumer);
/*     */     }
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
/*     */     public <T> T applyReadLocked(FailableFunction<O, T, ?> function) {
/* 210 */       return lockApplyUnlock(this.readLockSupplier, function);
/*     */     }
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
/*     */     public <T> T applyWriteLocked(FailableFunction<O, T, ?> function) {
/* 238 */       return lockApplyUnlock(this.writeLockSupplier, function);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public L getLock() {
/* 247 */       return this.lock;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public O getObject() {
/* 256 */       return this.object;
/*     */     }
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
/*     */     protected void lockAcceptUnlock(Supplier<Lock> lockSupplier, FailableConsumer<O, ?> consumer) {
/* 271 */       Lock lock = lockSupplier.get();
/* 272 */       lock.lock();
/*     */       try {
/* 274 */         consumer.accept(this.object);
/* 275 */       } catch (Throwable t) {
/* 276 */         throw Failable.rethrow(t);
/*     */       } finally {
/* 278 */         lock.unlock();
/*     */       } 
/*     */     }
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
/*     */     protected <T> T lockApplyUnlock(Supplier<Lock> lockSupplier, FailableFunction<O, T, ?> function) {
/* 298 */       Lock lock = lockSupplier.get();
/* 299 */       lock.lock();
/*     */       try {
/* 301 */         return (T)function.apply(this.object);
/* 302 */       } catch (Throwable t) {
/* 303 */         throw Failable.rethrow(t);
/*     */       } finally {
/* 305 */         lock.unlock();
/*     */       } 
/*     */     }
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
/*     */   
/*     */   public static class ReadWriteLockVisitor<O>
/*     */     extends LockVisitor<O, ReadWriteLock>
/*     */   {
/*     */     protected ReadWriteLockVisitor(O object, ReadWriteLock readWriteLock) {
/* 330 */       super(object, readWriteLock, readWriteLock::readLock, readWriteLock::writeLock);
/*     */     }
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
/*     */   public static class StampedLockVisitor<O>
/*     */     extends LockVisitor<O, StampedLock>
/*     */   {
/*     */     protected StampedLockVisitor(O object, StampedLock stampedLock) {
/* 353 */       super(object, stampedLock, stampedLock::asReadLock, stampedLock::asWriteLock);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <O> ReadWriteLockVisitor<O> reentrantReadWriteLockVisitor(O object) {
/* 365 */     return new ReadWriteLockVisitor<>(object, new ReentrantReadWriteLock());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <O> StampedLockVisitor<O> stampedLockVisitor(O object) {
/* 376 */     return new StampedLockVisitor<>(object, new StampedLock());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\locks\LockingVisitors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */