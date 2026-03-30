/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventCountCircuitBreaker
/*     */   extends AbstractCircuitBreaker<Integer>
/*     */ {
/* 141 */   private static final Map<AbstractCircuitBreaker.State, StateStrategy> STRATEGY_MAP = createStrategyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final AtomicReference<CheckIntervalData> checkIntervalData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int openingThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long openingInterval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int closingThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long closingInterval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventCountCircuitBreaker(int openingThreshold, long openingInterval, TimeUnit openingUnit, int closingThreshold, long closingInterval, TimeUnit closingUnit) {
/* 178 */     this.checkIntervalData = new AtomicReference<>(new CheckIntervalData(0, 0L));
/* 179 */     this.openingThreshold = openingThreshold;
/* 180 */     this.openingInterval = openingUnit.toNanos(openingInterval);
/* 181 */     this.closingThreshold = closingThreshold;
/* 182 */     this.closingInterval = closingUnit.toNanos(closingInterval);
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
/*     */   public EventCountCircuitBreaker(int openingThreshold, long checkInterval, TimeUnit checkUnit, int closingThreshold) {
/* 200 */     this(openingThreshold, checkInterval, checkUnit, closingThreshold, checkInterval, checkUnit);
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
/*     */   public EventCountCircuitBreaker(int threshold, long checkInterval, TimeUnit checkUnit) {
/* 215 */     this(threshold, checkInterval, checkUnit, threshold);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOpeningThreshold() {
/* 226 */     return this.openingThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getOpeningInterval() {
/* 235 */     return this.openingInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClosingThreshold() {
/* 246 */     return this.closingThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getClosingInterval() {
/* 255 */     return this.closingInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkState() {
/* 265 */     return performStateCheck(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean incrementAndCheckState(Integer increment) {
/* 273 */     return performStateCheck(increment.intValue());
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
/*     */   public boolean incrementAndCheckState() {
/* 285 */     return incrementAndCheckState(Integer.valueOf(1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open() {
/* 296 */     super.open();
/* 297 */     this.checkIntervalData.set(new CheckIntervalData(0, now()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 308 */     super.close();
/* 309 */     this.checkIntervalData.set(new CheckIntervalData(0, now()));
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
/*     */   private boolean performStateCheck(int increment) {
/*     */     CheckIntervalData currentData;
/*     */     CheckIntervalData nextData;
/*     */     AbstractCircuitBreaker.State currentState;
/*     */     do {
/* 325 */       long time = now();
/* 326 */       currentState = this.state.get();
/* 327 */       currentData = this.checkIntervalData.get();
/* 328 */       nextData = nextCheckIntervalData(increment, currentData, currentState, time);
/* 329 */     } while (!updateCheckIntervalData(currentData, nextData));
/*     */ 
/*     */ 
/*     */     
/* 333 */     if (stateStrategy(currentState).isStateTransition(this, currentData, nextData)) {
/* 334 */       currentState = currentState.oppositeState();
/* 335 */       changeStateAndStartNewCheckInterval(currentState);
/*     */     } 
/* 337 */     return !isOpen(currentState);
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
/*     */   private boolean updateCheckIntervalData(CheckIntervalData currentData, CheckIntervalData nextData) {
/* 352 */     return (currentData == nextData || this.checkIntervalData
/* 353 */       .compareAndSet(currentData, nextData));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void changeStateAndStartNewCheckInterval(AbstractCircuitBreaker.State newState) {
/* 363 */     changeState(newState);
/* 364 */     this.checkIntervalData.set(new CheckIntervalData(0, now()));
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
/*     */   private CheckIntervalData nextCheckIntervalData(int increment, CheckIntervalData currentData, AbstractCircuitBreaker.State currentState, long time) {
/*     */     CheckIntervalData nextData;
/* 381 */     if (stateStrategy(currentState).isCheckIntervalFinished(this, currentData, time)) {
/* 382 */       nextData = new CheckIntervalData(increment, time);
/*     */     } else {
/* 384 */       nextData = currentData.increment(increment);
/*     */     } 
/* 386 */     return nextData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long now() {
/* 396 */     return System.nanoTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static StateStrategy stateStrategy(AbstractCircuitBreaker.State state) {
/* 407 */     return STRATEGY_MAP.get(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<AbstractCircuitBreaker.State, StateStrategy> createStrategyMap() {
/* 417 */     Map<AbstractCircuitBreaker.State, StateStrategy> map = new EnumMap<>(AbstractCircuitBreaker.State.class);
/* 418 */     map.put(AbstractCircuitBreaker.State.CLOSED, new StateStrategyClosed());
/* 419 */     map.put(AbstractCircuitBreaker.State.OPEN, new StateStrategyOpen());
/* 420 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CheckIntervalData
/*     */   {
/*     */     private final int eventCount;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final long checkIntervalStart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CheckIntervalData(int count, long intervalStart) {
/* 442 */       this.eventCount = count;
/* 443 */       this.checkIntervalStart = intervalStart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getEventCount() {
/* 452 */       return this.eventCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getCheckIntervalStart() {
/* 461 */       return this.checkIntervalStart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CheckIntervalData increment(int delta) {
/* 472 */       return (delta == 0) ? this : new CheckIntervalData(getEventCount() + delta, 
/* 473 */           getCheckIntervalStart());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class StateStrategy
/*     */   {
/*     */     private StateStrategy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCheckIntervalFinished(EventCountCircuitBreaker breaker, EventCountCircuitBreaker.CheckIntervalData currentData, long now) {
/* 493 */       return (now - currentData.getCheckIntervalStart() > fetchCheckInterval(breaker));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract boolean isStateTransition(EventCountCircuitBreaker param1EventCountCircuitBreaker, EventCountCircuitBreaker.CheckIntervalData param1CheckIntervalData1, EventCountCircuitBreaker.CheckIntervalData param1CheckIntervalData2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract long fetchCheckInterval(EventCountCircuitBreaker param1EventCountCircuitBreaker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StateStrategyClosed
/*     */     extends StateStrategy
/*     */   {
/*     */     private StateStrategyClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isStateTransition(EventCountCircuitBreaker breaker, EventCountCircuitBreaker.CheckIntervalData currentData, EventCountCircuitBreaker.CheckIntervalData nextData) {
/* 530 */       return (nextData.getEventCount() > breaker.getOpeningThreshold());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected long fetchCheckInterval(EventCountCircuitBreaker breaker) {
/* 538 */       return breaker.getOpeningInterval();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StateStrategyOpen
/*     */     extends StateStrategy
/*     */   {
/*     */     private StateStrategyOpen() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isStateTransition(EventCountCircuitBreaker breaker, EventCountCircuitBreaker.CheckIntervalData currentData, EventCountCircuitBreaker.CheckIntervalData nextData) {
/* 552 */       return (nextData.getCheckIntervalStart() != currentData
/* 553 */         .getCheckIntervalStart() && currentData
/* 554 */         .getEventCount() < breaker.getClosingThreshold());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected long fetchCheckInterval(EventCountCircuitBreaker breaker) {
/* 562 */       return breaker.getClosingInterval();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\EventCountCircuitBreaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */