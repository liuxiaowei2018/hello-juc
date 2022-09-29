package com.open.juc.CompletableFuture;

/**
 * @author liuxiaowei
 * @date 2022年09月27日 14:55
 * @Description CompletableFuture实现了CompletionStage接口和Future接口
 * 前者是对后者的一个扩展，增加了异步回调、流式处理、多个Future组合处理的能力，使Java在处理多任务的协同工作时更加顺畅便利。
 * 没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池，则使用指定的线程池运行。以下所有的方法都类同。
 * runAsync方法不支持返回值。
 * supplyAsync可以支持返回值。
 */
public class CompletableFutureDemo {

    //static CompletableFuture<Void> runAsync(Runnable runnable)
    //public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
    //public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
    //public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)


    // whenComplete可以处理正常和异常的计算结果
    // whenComplete():执行当前任务的线程执行继续执行 whenComplete 的任务
    //public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action);

    // whenCompleteAsync()：是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行。
    //public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action);
    //public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor);

    // exceptionally处理异常情况
    //public CompletableFuture<T> exceptionally(Function<Throwable,? extends T> fn);

    // thenApply 方法：当一个线程依赖另一个线程时，获取上一个任务返回的结果，并返回当前任务的返回值。
    // thenAccept方法：消费处理结果。接收任务的处理结果，并消费处理，无返回结果。
    // thenRun方法：只要上面的任务执行完成，就开始执行thenRun，只是处理完任务后，执行 thenRun的后续操作
    // 带有Async默认是异步执行的。这里所谓的异步指的是不在当前线程内执行。
    //public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
    //public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
    //public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
    //public CompletionStage<Void> thenAccept(Consumer<? super T> action);
    //public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
    //public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
    //public CompletionStage<Void> thenRun(Runnable action);
    //public CompletionStage<Void> thenRunAsync(Runnable action);
    //public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);


    // allOf：等待所有任务完成; anyOf：只要有一个任务完成
    //public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs);
    //public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs);

    public static void main(String[] args) {
        System.out.println("CompletableFutureDemo");
    }

}
