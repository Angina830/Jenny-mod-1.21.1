/*     */ package org.apache.commons.lang3.function;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import org.apache.commons.lang3.stream.Streams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Failable
/*     */ {
/*     */   public static <T, U, E extends Throwable> void accept(FailableBiConsumer<T, U, E> consumer, T object1, U object2) {
/*  83 */     run(() -> consumer.accept(object1, object2));
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
/*     */   public static <T, E extends Throwable> void accept(FailableConsumer<T, E> consumer, T object) {
/*  95 */     run(() -> consumer.accept(object));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Throwable> void accept(FailableDoubleConsumer<E> consumer, double value) {
/* 106 */     run(() -> consumer.accept(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Throwable> void accept(FailableIntConsumer<E> consumer, int value) {
/* 117 */     run(() -> consumer.accept(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Throwable> void accept(FailableLongConsumer<E> consumer, long value) {
/* 128 */     run(() -> consumer.accept(value));
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
/*     */   public static <T, U, R, E extends Throwable> R apply(FailableBiFunction<T, U, R, E> function, T input1, U input2) {
/* 145 */     return get(() -> function.apply(input1, input2));
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
/*     */   public static <T, R, E extends Throwable> R apply(FailableFunction<T, R, E> function, T input) {
/* 159 */     return get(() -> function.apply(input));
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
/*     */   public static <E extends Throwable> double applyAsDouble(FailableDoubleBinaryOperator<E> function, double left, double right) {
/* 173 */     return getAsDouble(() -> function.applyAsDouble(left, right));
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
/*     */   public static <T, U> BiConsumer<T, U> asBiConsumer(FailableBiConsumer<T, U, ?> consumer) {
/* 185 */     return (input1, input2) -> accept(consumer, input1, input2);
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
/*     */   public static <T, U, R> BiFunction<T, U, R> asBiFunction(FailableBiFunction<T, U, R, ?> function) {
/* 198 */     return (input1, input2) -> apply(function, input1, input2);
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
/*     */   public static <T, U> BiPredicate<T, U> asBiPredicate(FailableBiPredicate<T, U, ?> predicate) {
/* 210 */     return (input1, input2) -> test(predicate, input1, input2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Callable<V> asCallable(FailableCallable<V, ?> callable) {
/* 221 */     return () -> call(callable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Consumer<T> asConsumer(FailableConsumer<T, ?> consumer) {
/* 232 */     return input -> accept(consumer, input);
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
/*     */   public static <T, R> Function<T, R> asFunction(FailableFunction<T, R, ?> function) {
/* 244 */     return input -> apply(function, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> asPredicate(FailablePredicate<T, ?> predicate) {
/* 255 */     return input -> test(predicate, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Runnable asRunnable(FailableRunnable<?> runnable) {
/* 265 */     return () -> run(runnable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Supplier<T> asSupplier(FailableSupplier<T, ?> supplier) {
/* 276 */     return () -> get(supplier);
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
/*     */   public static <V, E extends Throwable> V call(FailableCallable<V, E> callable) {
/* 288 */     return get(callable::call);
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
/*     */   public static <T, E extends Throwable> T get(FailableSupplier<T, E> supplier) {
/*     */     try {
/* 301 */       return supplier.get();
/* 302 */     } catch (Throwable t) {
/* 303 */       throw rethrow(t);
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
/*     */   public static <E extends Throwable> boolean getAsBoolean(FailableBooleanSupplier<E> supplier) {
/*     */     try {
/* 316 */       return supplier.getAsBoolean();
/* 317 */     } catch (Throwable t) {
/* 318 */       throw rethrow(t);
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
/*     */   public static <E extends Throwable> double getAsDouble(FailableDoubleSupplier<E> supplier) {
/*     */     try {
/* 331 */       return supplier.getAsDouble();
/* 332 */     } catch (Throwable t) {
/* 333 */       throw rethrow(t);
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
/*     */   public static <E extends Throwable> int getAsInt(FailableIntSupplier<E> supplier) {
/*     */     try {
/* 346 */       return supplier.getAsInt();
/* 347 */     } catch (Throwable t) {
/* 348 */       throw rethrow(t);
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
/*     */   public static <E extends Throwable> long getAsLong(FailableLongSupplier<E> supplier) {
/*     */     try {
/* 361 */       return supplier.getAsLong();
/* 362 */     } catch (Throwable t) {
/* 363 */       throw rethrow(t);
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
/*     */   public static RuntimeException rethrow(Throwable throwable) {
/* 395 */     Objects.requireNonNull(throwable, "throwable");
/* 396 */     if (throwable instanceof RuntimeException)
/* 397 */       throw (RuntimeException)throwable; 
/* 398 */     if (throwable instanceof Error)
/* 399 */       throw (Error)throwable; 
/* 400 */     if (throwable instanceof IOException) {
/* 401 */       throw new UncheckedIOException((IOException)throwable);
/*     */     }
/* 403 */     throw new UndeclaredThrowableException(throwable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Throwable> void run(FailableRunnable<E> runnable) {
/*     */     try {
/* 415 */       runnable.run();
/* 416 */     } catch (Throwable t) {
/* 417 */       throw rethrow(t);
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
/*     */   public static <E> Streams.FailableStream<E> stream(Collection<E> collection) {
/* 434 */     return new Streams.FailableStream(collection.stream());
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
/*     */   public static <T> Streams.FailableStream<T> stream(Stream<T> stream) {
/* 448 */     return new Streams.FailableStream(stream);
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
/*     */   public static <T, U, E extends Throwable> boolean test(FailableBiPredicate<T, U, E> predicate, T object1, U object2) {
/* 464 */     return getAsBoolean(() -> predicate.test(object1, object2));
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
/*     */   public static <T, E extends Throwable> boolean test(FailablePredicate<T, E> predicate, T object) {
/* 477 */     return getAsBoolean(() -> predicate.test(object));
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
/*     */   @SafeVarargs
/*     */   public static void tryWithResources(FailableRunnable<? extends Throwable> action, FailableConsumer<Throwable, ? extends Throwable> errorHandler, FailableRunnable<? extends Throwable>... resources) {
/*     */     FailableConsumer<Throwable, ? extends Throwable> actualErrorHandler;
/* 504 */     if (errorHandler == null) {
/* 505 */       actualErrorHandler = Failable::rethrow;
/*     */     } else {
/* 507 */       actualErrorHandler = errorHandler;
/*     */     } 
/* 509 */     if (resources != null) {
/* 510 */       for (FailableRunnable<? extends Throwable> failableRunnable : resources) {
/* 511 */         Objects.requireNonNull(failableRunnable, "runnable");
/*     */       }
/*     */     }
/* 514 */     Throwable th = null;
/*     */     try {
/* 516 */       action.run();
/* 517 */     } catch (Throwable t) {
/* 518 */       th = t;
/*     */     } 
/* 520 */     if (resources != null) {
/* 521 */       for (FailableRunnable<?> runnable : resources) {
/*     */         try {
/* 523 */           runnable.run();
/* 524 */         } catch (Throwable t) {
/* 525 */           if (th == null) {
/* 526 */             th = t;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 531 */     if (th != null) {
/*     */       try {
/* 533 */         actualErrorHandler.accept(th);
/* 534 */       } catch (Throwable t) {
/* 535 */         throw rethrow(t);
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
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   public static void tryWithResources(FailableRunnable<? extends Throwable> action, FailableRunnable<? extends Throwable>... resources) {
/* 560 */     tryWithResources(action, null, resources);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\Failable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */