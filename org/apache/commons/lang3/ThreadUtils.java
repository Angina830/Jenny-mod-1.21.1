/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ public class ThreadUtils
/*     */ {
/*     */   public static Thread findThreadById(long threadId, ThreadGroup threadGroup) {
/*  53 */     Validate.notNull(threadGroup, "The thread group must not be null", new Object[0]);
/*  54 */     Thread thread = findThreadById(threadId);
/*  55 */     if (thread != null && threadGroup.equals(thread.getThreadGroup())) {
/*  56 */       return thread;
/*     */     }
/*  58 */     return null;
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
/*     */   public static Thread findThreadById(long threadId, String threadGroupName) {
/*  76 */     Validate.notNull(threadGroupName, "The thread group name must not be null", new Object[0]);
/*  77 */     Thread thread = findThreadById(threadId);
/*  78 */     if (thread != null && thread.getThreadGroup() != null && thread.getThreadGroup().getName().equals(threadGroupName)) {
/*  79 */       return thread;
/*     */     }
/*  81 */     return null;
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
/*     */   public static Collection<Thread> findThreadsByName(String threadName, ThreadGroup threadGroup) {
/*  99 */     return findThreads(threadGroup, false, new NamePredicate(threadName));
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
/*     */   public static Collection<Thread> findThreadsByName(String threadName, String threadGroupName) {
/* 117 */     Validate.notNull(threadName, "The thread name must not be null", new Object[0]);
/* 118 */     Validate.notNull(threadGroupName, "The thread group name must not be null", new Object[0]);
/*     */     
/* 120 */     Collection<ThreadGroup> threadGroups = findThreadGroups(new NamePredicate(threadGroupName));
/*     */     
/* 122 */     if (threadGroups.isEmpty()) {
/* 123 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 126 */     Collection<Thread> result = new ArrayList<>();
/* 127 */     NamePredicate threadNamePredicate = new NamePredicate(threadName);
/* 128 */     for (ThreadGroup group : threadGroups) {
/* 129 */       result.addAll(findThreads(group, false, threadNamePredicate));
/*     */     }
/* 131 */     return Collections.unmodifiableCollection(result);
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
/*     */   public static Collection<ThreadGroup> findThreadGroupsByName(String threadGroupName) {
/* 147 */     return findThreadGroups(new NamePredicate(threadGroupName));
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
/*     */   public static Collection<ThreadGroup> getAllThreadGroups() {
/* 161 */     return findThreadGroups(ALWAYS_TRUE_PREDICATE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadGroup getSystemThreadGroup() {
/* 172 */     ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/* 173 */     while (threadGroup.getParent() != null) {
/* 174 */       threadGroup = threadGroup.getParent();
/*     */     }
/* 176 */     return threadGroup;
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
/*     */   public static Collection<Thread> getAllThreads() {
/* 190 */     return findThreads(ALWAYS_TRUE_PREDICATE);
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
/*     */   public static Collection<Thread> findThreadsByName(String threadName) {
/* 206 */     return findThreads(new NamePredicate(threadName));
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
/*     */   public static Thread findThreadById(long threadId) {
/* 222 */     Collection<Thread> result = findThreads(new ThreadIdPredicate(threadId));
/* 223 */     return result.isEmpty() ? null : result.iterator().next();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public static final AlwaysTruePredicate ALWAYS_TRUE_PREDICATE = new AlwaysTruePredicate();
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ThreadPredicate {
/*     */     boolean test(Thread param1Thread); }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ThreadGroupPredicate {
/*     */     boolean test(ThreadGroup param1ThreadGroup); }
/*     */   
/*     */   private static final class AlwaysTruePredicate implements ThreadPredicate, ThreadGroupPredicate {
/*     */     public boolean test(ThreadGroup threadGroup) {
/* 284 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Thread thread) {
/* 289 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AlwaysTruePredicate() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NamePredicate
/*     */     implements ThreadPredicate, ThreadGroupPredicate
/*     */   {
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */     
/*     */     public NamePredicate(String name) {
/* 308 */       Validate.notNull(name, "The name must not be null", new Object[0]);
/* 309 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(ThreadGroup threadGroup) {
/* 314 */       return (threadGroup != null && threadGroup.getName().equals(this.name));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Thread thread) {
/* 319 */       return (thread != null && thread.getName().equals(this.name));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ThreadIdPredicate
/*     */     implements ThreadPredicate
/*     */   {
/*     */     private final long threadId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ThreadIdPredicate(long threadId) {
/* 338 */       if (threadId <= 0L) {
/* 339 */         throw new IllegalArgumentException("The thread id must be greater than zero");
/*     */       }
/* 341 */       this.threadId = threadId;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Thread thread) {
/* 346 */       return (thread != null && thread.getId() == this.threadId);
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
/*     */   public static Collection<Thread> findThreads(ThreadPredicate predicate) {
/* 363 */     return findThreads(getSystemThreadGroup(), true, predicate);
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
/*     */   public static Collection<ThreadGroup> findThreadGroups(ThreadGroupPredicate predicate) {
/* 378 */     return findThreadGroups(getSystemThreadGroup(), true, predicate);
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
/*     */   public static Collection<Thread> findThreads(ThreadGroup group, boolean recurse, ThreadPredicate predicate) {
/*     */     Thread[] threads;
/* 393 */     Validate.notNull(group, "The group must not be null", new Object[0]);
/* 394 */     Validate.notNull(predicate, "The predicate must not be null", new Object[0]);
/*     */     
/* 396 */     int count = group.activeCount();
/*     */     
/*     */     do {
/* 399 */       threads = new Thread[count + count / 2 + 1];
/* 400 */       count = group.enumerate(threads, recurse);
/*     */     }
/* 402 */     while (count >= threads.length);
/*     */     
/* 404 */     List<Thread> result = new ArrayList<>(count);
/* 405 */     for (int i = 0; i < count; i++) {
/* 406 */       if (predicate.test(threads[i])) {
/* 407 */         result.add(threads[i]);
/*     */       }
/*     */     } 
/* 410 */     return Collections.unmodifiableCollection(result);
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
/*     */   public static Collection<ThreadGroup> findThreadGroups(ThreadGroup group, boolean recurse, ThreadGroupPredicate predicate) {
/*     */     ThreadGroup[] threadGroups;
/* 425 */     Validate.notNull(group, "The group must not be null", new Object[0]);
/* 426 */     Validate.notNull(predicate, "The predicate must not be null", new Object[0]);
/*     */     
/* 428 */     int count = group.activeGroupCount();
/*     */     
/*     */     do {
/* 431 */       threadGroups = new ThreadGroup[count + count / 2 + 1];
/* 432 */       count = group.enumerate(threadGroups, recurse);
/*     */     }
/* 434 */     while (count >= threadGroups.length);
/*     */     
/* 436 */     List<ThreadGroup> result = new ArrayList<>(count);
/* 437 */     for (int i = 0; i < count; i++) {
/* 438 */       if (predicate.test(threadGroups[i])) {
/* 439 */         result.add(threadGroups[i]);
/*     */       }
/*     */     } 
/* 442 */     return Collections.unmodifiableCollection(result);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\ThreadUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */