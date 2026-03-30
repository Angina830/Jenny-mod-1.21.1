/*     */ package org.apache.commons.lang3.stream;
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
/*     */ import org.apache.commons.lang3.function.Failable;
/*     */ import org.apache.commons.lang3.function.FailableConsumer;
/*     */ import org.apache.commons.lang3.function.FailableFunction;
/*     */ import org.apache.commons.lang3.function.FailablePredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Streams
/*     */ {
/*     */   public static class ArrayCollector<O>
/*     */     implements Collector<O, List<O>, O[]>
/*     */   {
/*  70 */     private static final Set<Collector.Characteristics> characteristics = Collections.emptySet();
/*     */     private final Class<O> elementType;
/*     */     
/*     */     public ArrayCollector(Class<O> elementType) {
/*  74 */       this.elementType = elementType;
/*     */     }
/*     */ 
/*     */     
/*     */     public BiConsumer<List<O>, O> accumulator() {
/*  79 */       return List::add;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Collector.Characteristics> characteristics() {
/*  84 */       return characteristics;
/*     */     }
/*     */ 
/*     */     
/*     */     public BinaryOperator<List<O>> combiner() {
/*  89 */       return (left, right) -> {
/*     */           left.addAll(right);
/*     */           return left;
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public Function<List<O>, O[]> finisher() {
/*  97 */       return list -> {
/*     */           O[] array = (O[])Array.newInstance(this.elementType, list.size());
/*     */           return list.toArray((Object[])array);
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Supplier<List<O>> supplier() {
/* 106 */       return java.util.ArrayList::new;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FailableStream<O>
/*     */   {
/*     */     private Stream<O> stream;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean terminated;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FailableStream(Stream<O> stream) {
/* 126 */       this.stream = stream;
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
/*     */     public boolean allMatch(FailablePredicate<O, ?> predicate) {
/* 146 */       assertNotTerminated();
/* 147 */       return stream().allMatch(Failable.asPredicate(predicate));
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
/*     */     public boolean anyMatch(FailablePredicate<O, ?> predicate) {
/* 165 */       assertNotTerminated();
/* 166 */       return stream().anyMatch(Failable.asPredicate(predicate));
/*     */     }
/*     */     
/*     */     protected void assertNotTerminated() {
/* 170 */       if (this.terminated) {
/* 171 */         throw new IllegalStateException("This stream is already terminated.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 231 */       makeTerminated();
/* 232 */       return stream().collect(collector);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A, R> R collect(Supplier<R> pupplier, BiConsumer<R, ? super O> accumulator, BiConsumer<R, R> combiner) {
/* 289 */       makeTerminated();
/* 290 */       return stream().collect(pupplier, accumulator, combiner);
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
/*     */     public FailableStream<O> filter(FailablePredicate<O, ?> predicate) {
/* 304 */       assertNotTerminated();
/* 305 */       this.stream = this.stream.filter(Failable.asPredicate(predicate));
/* 306 */       return this;
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
/*     */     public void forEach(FailableConsumer<O, ?> action) {
/* 325 */       makeTerminated();
/* 326 */       stream().forEach(Failable.asConsumer(action));
/*     */     }
/*     */     
/*     */     protected void makeTerminated() {
/* 330 */       assertNotTerminated();
/* 331 */       this.terminated = true;
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
/*     */     public <R> FailableStream<R> map(FailableFunction<O, R, ?> mapper) {
/* 345 */       assertNotTerminated();
/* 346 */       return new FailableStream(this.stream.map(Failable.asFunction(mapper)));
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
/*     */     
/*     */     public O reduce(O identity, BinaryOperator<O> accumulator) {
/* 399 */       makeTerminated();
/* 400 */       return stream().reduce(identity, accumulator);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Stream<O> stream() {
/* 409 */       return this.stream;
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
/*     */   public static <O> FailableStream<O> stream(Collection<O> stream) {
/* 450 */     return stream(stream.stream());
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
/*     */   public static <O> FailableStream<O> stream(Stream<O> stream) {
/* 490 */     return new FailableStream<>(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <O> Collector<O, ?, O[]> toArray(Class<O> pElementType) {
/* 501 */     return new ArrayCollector<>(pElementType);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\stream\Streams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */