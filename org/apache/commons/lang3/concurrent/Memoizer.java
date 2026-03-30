/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.FutureTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Memoizer<I, O>
/*     */   implements Computable<I, O>
/*     */ {
/*  56 */   private final ConcurrentMap<I, Future<O>> cache = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Computable<I, O> computable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean recalculate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Memoizer(Computable<I, O> computable) {
/*  74 */     this(computable, false);
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
/*     */   public Memoizer(Computable<I, O> computable, boolean recalculate) {
/*  91 */     this.computable = computable;
/*  92 */     this.recalculate = recalculate;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public O compute(I arg) throws InterruptedException {
/*     */     while (true) {
/* 116 */       Future<O> future = this.cache.get(arg);
/* 117 */       if (future == null) {
/* 118 */         Callable<O> eval = () -> this.computable.compute((I)arg);
/* 119 */         FutureTask<O> futureTask = new FutureTask<>(eval);
/* 120 */         future = this.cache.putIfAbsent(arg, futureTask);
/* 121 */         if (future == null) {
/* 122 */           future = futureTask;
/* 123 */           futureTask.run();
/*     */         } 
/*     */       } 
/*     */       try {
/* 127 */         return future.get();
/* 128 */       } catch (CancellationException e) {
/* 129 */         this.cache.remove(arg, future);
/* 130 */       } catch (ExecutionException e) {
/* 131 */         if (this.recalculate) {
/* 132 */           this.cache.remove(arg, future);
/*     */         }
/*     */         
/* 135 */         throw launderException(e.getCause());
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
/*     */   private RuntimeException launderException(Throwable throwable) {
/* 151 */     if (throwable instanceof RuntimeException)
/* 152 */       return (RuntimeException)throwable; 
/* 153 */     if (throwable instanceof Error) {
/* 154 */       throw (Error)throwable;
/*     */     }
/* 156 */     throw new IllegalStateException("Unchecked exception", throwable);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\Memoizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */