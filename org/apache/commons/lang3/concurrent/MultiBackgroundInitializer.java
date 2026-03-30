/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
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
/*     */ public class MultiBackgroundInitializer
/*     */   extends BackgroundInitializer<MultiBackgroundInitializer.MultiBackgroundInitializerResults>
/*     */ {
/* 102 */   private final Map<String, BackgroundInitializer<?>> childInitializers = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiBackgroundInitializer(ExecutorService exec) {
/* 120 */     super(exec);
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
/*     */   public void addInitializer(String name, BackgroundInitializer<?> init) {
/* 136 */     Validate.notNull(name, "Name of child initializer must not be null!", new Object[0]);
/* 137 */     Validate.notNull(init, "Child initializer must not be null!", new Object[0]);
/*     */     
/* 139 */     synchronized (this) {
/* 140 */       if (isStarted()) {
/* 141 */         throw new IllegalStateException("addInitializer() must not be called after start()!");
/*     */       }
/*     */       
/* 144 */       this.childInitializers.put(name, init);
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
/*     */   protected int getTaskCount() {
/* 160 */     int result = 1;
/*     */     
/* 162 */     for (BackgroundInitializer<?> bi : this.childInitializers.values()) {
/* 163 */       result += bi.getTaskCount();
/*     */     }
/*     */     
/* 166 */     return result;
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
/*     */   protected MultiBackgroundInitializerResults initialize() throws Exception {
/*     */     Map<String, BackgroundInitializer<?>> inits;
/* 182 */     synchronized (this) {
/*     */       
/* 184 */       inits = new HashMap<>(this.childInitializers);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 189 */     ExecutorService exec = getActiveExecutor();
/* 190 */     for (BackgroundInitializer<?> bi : inits.values()) {
/* 191 */       if (bi.getExternalExecutor() == null)
/*     */       {
/* 193 */         bi.setExternalExecutor(exec);
/*     */       }
/* 195 */       bi.start();
/*     */     } 
/*     */ 
/*     */     
/* 199 */     Map<String, Object> results = new HashMap<>();
/* 200 */     Map<String, ConcurrentException> excepts = new HashMap<>();
/* 201 */     for (Map.Entry<String, BackgroundInitializer<?>> e : inits.entrySet()) {
/*     */       try {
/* 203 */         results.put(e.getKey(), ((BackgroundInitializer)e.getValue()).get());
/* 204 */       } catch (ConcurrentException cex) {
/* 205 */         excepts.put(e.getKey(), cex);
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return new MultiBackgroundInitializerResults(inits, results, excepts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiBackgroundInitializer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MultiBackgroundInitializerResults
/*     */   {
/*     */     private final Map<String, BackgroundInitializer<?>> initializers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Map<String, Object> resultObjects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Map<String, ConcurrentException> exceptions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private MultiBackgroundInitializerResults(Map<String, BackgroundInitializer<?>> inits, Map<String, Object> results, Map<String, ConcurrentException> excepts) {
/* 245 */       this.initializers = inits;
/* 246 */       this.resultObjects = results;
/* 247 */       this.exceptions = excepts;
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
/*     */     public BackgroundInitializer<?> getInitializer(String name) {
/* 259 */       return checkName(name);
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
/*     */     public Object getResultObject(String name) {
/* 275 */       checkName(name);
/* 276 */       return this.resultObjects.get(name);
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
/*     */     public boolean isException(String name) {
/* 288 */       checkName(name);
/* 289 */       return this.exceptions.containsKey(name);
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
/*     */     public ConcurrentException getException(String name) {
/* 303 */       checkName(name);
/* 304 */       return this.exceptions.get(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> initializerNames() {
/* 315 */       return Collections.unmodifiableSet(this.initializers.keySet());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSuccessful() {
/* 325 */       return this.exceptions.isEmpty();
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
/*     */     private BackgroundInitializer<?> checkName(String name) {
/* 338 */       BackgroundInitializer<?> init = this.initializers.get(name);
/* 339 */       if (init == null) {
/* 340 */         throw new NoSuchElementException("No child initializer with name " + name);
/*     */       }
/*     */ 
/*     */       
/* 344 */       return init;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\MultiBackgroundInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */