/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicThreadFactory
/*     */   implements ThreadFactory
/*     */ {
/*     */   private final AtomicLong threadCounter;
/*     */   private final ThreadFactory wrappedFactory;
/*     */   private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
/*     */   private final String namingPattern;
/*     */   private final Integer priority;
/*     */   private final Boolean daemon;
/*     */   
/*     */   private BasicThreadFactory(Builder builder) {
/* 117 */     if (builder.wrappedFactory == null) {
/* 118 */       this.wrappedFactory = Executors.defaultThreadFactory();
/*     */     } else {
/* 120 */       this.wrappedFactory = builder.wrappedFactory;
/*     */     } 
/*     */     
/* 123 */     this.namingPattern = builder.namingPattern;
/* 124 */     this.priority = builder.priority;
/* 125 */     this.daemon = builder.daemon;
/* 126 */     this.uncaughtExceptionHandler = builder.exceptionHandler;
/*     */     
/* 128 */     this.threadCounter = new AtomicLong();
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
/*     */   public final ThreadFactory getWrappedFactory() {
/* 140 */     return this.wrappedFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getNamingPattern() {
/* 150 */     return this.namingPattern;
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
/*     */   public final Boolean getDaemonFlag() {
/* 162 */     return this.daemon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Integer getPriority() {
/* 172 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
/* 182 */     return this.uncaughtExceptionHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getThreadCount() {
/* 193 */     return this.threadCounter.get();
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
/*     */   public Thread newThread(Runnable runnable) {
/* 206 */     Thread thread = getWrappedFactory().newThread(runnable);
/* 207 */     initializeThread(thread);
/*     */     
/* 209 */     return thread;
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
/*     */   private void initializeThread(Thread thread) {
/* 222 */     if (getNamingPattern() != null) {
/* 223 */       Long count = Long.valueOf(this.threadCounter.incrementAndGet());
/* 224 */       thread.setName(String.format(getNamingPattern(), new Object[] { count }));
/*     */     } 
/*     */     
/* 227 */     if (getUncaughtExceptionHandler() != null) {
/* 228 */       thread.setUncaughtExceptionHandler(getUncaughtExceptionHandler());
/*     */     }
/*     */     
/* 231 */     if (getPriority() != null) {
/* 232 */       thread.setPriority(getPriority().intValue());
/*     */     }
/*     */     
/* 235 */     if (getDaemonFlag() != null) {
/* 236 */       thread.setDaemon(getDaemonFlag().booleanValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */     implements org.apache.commons.lang3.builder.Builder<BasicThreadFactory>
/*     */   {
/*     */     private ThreadFactory wrappedFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Thread.UncaughtExceptionHandler exceptionHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String namingPattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Integer priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Boolean daemon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder wrappedFactory(ThreadFactory factory) {
/* 283 */       Validate.notNull(factory, "Wrapped ThreadFactory must not be null!", new Object[0]);
/*     */       
/* 285 */       this.wrappedFactory = factory;
/* 286 */       return this;
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
/*     */     public Builder namingPattern(String pattern) {
/* 298 */       Validate.notNull(pattern, "Naming pattern must not be null!", new Object[0]);
/*     */       
/* 300 */       this.namingPattern = pattern;
/* 301 */       return this;
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
/*     */     public Builder daemon(boolean daemon) {
/* 313 */       this.daemon = Boolean.valueOf(daemon);
/* 314 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder priority(int priority) {
/* 325 */       this.priority = Integer.valueOf(priority);
/* 326 */       return this;
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
/*     */     public Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
/* 340 */       Validate.notNull(handler, "Uncaught exception handler must not be null!", new Object[0]);
/*     */       
/* 342 */       this.exceptionHandler = handler;
/* 343 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset() {
/* 353 */       this.wrappedFactory = null;
/* 354 */       this.exceptionHandler = null;
/* 355 */       this.namingPattern = null;
/* 356 */       this.priority = null;
/* 357 */       this.daemon = null;
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
/*     */     public BasicThreadFactory build() {
/* 369 */       BasicThreadFactory factory = new BasicThreadFactory(this);
/* 370 */       reset();
/* 371 */       return factory;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\BasicThreadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */