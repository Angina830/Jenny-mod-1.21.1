/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class Streams
/*     */ {
/*     */   @Deprecated
/*     */   public static class FailableStream<O>
/*     */   {
/*     */     private Stream<O> stream;
/*     */     private boolean terminated;
/*     */     
/*     */     public FailableStream(Stream<O> stream) {
/*  87 */       this.stream = stream;
/*     */     }
/*     */     
/*     */     protected void assertNotTerminated() {
/*  91 */       if (this.terminated) {
/*  92 */         throw new IllegalStateException("This stream is already terminated.");
/*     */       }
/*     */     }
/*     */     
/*     */     protected void makeTerminated() {
/*  97 */       assertNotTerminated();
/*  98 */       this.terminated = true;
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
/*     */     public FailableStream<O> filter(Functions.FailablePredicate<O, ?> predicate) {
/* 112 */       assertNotTerminated();
/* 113 */       this.stream = this.stream.filter(Functions.asPredicate(predicate));
/* 114 */       return this;
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
/*     */     public void forEach(Functions.FailableConsumer<O, ?> action) {
/* 133 */       makeTerminated();
/* 134 */       stream().forEach(Functions.asConsumer(action));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A, R> R collect(Collector<? super O, A, R> collector) {
/* 186 */       makeTerminated();
/* 187 */       return stream().collect(collector);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A, R> R collect(Supplier<R> pupplier, BiConsumer<R, ? super O> accumulator, BiConsumer<R, R> combiner) {
/* 236 */       makeTerminated();
/* 237 */       return stream().collect(pupplier, accumulator, combiner);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public O reduce(O identity, BinaryOperator<O> accumulator) {
/* 284 */       makeTerminated();
/* 285 */       return stream().reduce(identity, accumulator);
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
/*     */     public <R> FailableStream<R> map(Functions.FailableFunction<O, R, ?> mapper) {
/* 299 */       assertNotTerminated();
/* 300 */       return new FailableStream(this.stream.map(Functions.asFunction(mapper)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Stream<O> stream() {
/* 308 */       return this.stream;
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
/*     */     public boolean allMatch(Functions.FailablePredicate<O, ?> predicate) {
/* 331 */       assertNotTerminated();
/* 332 */       return stream().allMatch(Functions.asPredicate(predicate));
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
/*     */     public boolean anyMatch(Functions.FailablePredicate<O, ?> predicate) {
/* 353 */       assertNotTerminated();
/* 354 */       return stream().anyMatch(Functions.asPredicate(predicate));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <O> FailableStream<O> stream(Stream<O> stream) {
/* 397 */     return new FailableStream<>(stream);
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
/*     */   public static <O> FailableStream<O> stream(Collection<O> stream) {
/* 439 */     return stream(stream.stream());
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static class ArrayCollector<O>
/*     */     implements Collector<O, List<O>, O[]>
/*     */   {
/* 447 */     private static final Set<Collector.Characteristics> characteristics = Collections.emptySet();
/*     */     private final Class<O> elementType;
/*     */     
/*     */     public ArrayCollector(Class<O> elementType) {
/* 451 */       this.elementType = elementType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Supplier<List<O>> supplier() {
/* 456 */       return java.util.ArrayList::new;
/*     */     }
/*     */ 
/*     */     
/*     */     public BiConsumer<List<O>, O> accumulator() {
/* 461 */       return List::add;
/*     */     }
/*     */ 
/*     */     
/*     */     public BinaryOperator<List<O>> combiner() {
/* 466 */       return (left, right) -> {
/*     */           left.addAll(right);
/*     */           return left;
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public Function<List<O>, O[]> finisher() {
/* 474 */       return list -> {
/*     */           O[] array = (O[])Array.newInstance(this.elementType, list.size());
/*     */           return list.toArray((Object[])array);
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Collector.Characteristics> characteristics() {
/* 483 */       return characteristics;
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
/*     */   public static <O> Collector<O, ?, O[]> toArray(Class<O> pElementType) {
/* 497 */     return new ArrayCollector<>(pElementType);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\Streams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */