package inaugural.soliloquy.tools.concurrency;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.exception.CheckedExceptionWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Concurrency {
    public static CompletableFuture<Void> runTaskAsync(Runnable task,
                                                       Consumer<Throwable> handleThrowable,
                                                       ExecutorService executorService) {
        Check.ifNull(task, "task");
        Check.ifNull(handleThrowable, "handleThrowable");
        Check.ifNull(executorService, "executorService");

        return CompletableFuture.runAsync(task, executorService)
                .exceptionally(e -> {
                    handleThrowable.accept(e);
                    return null;
                });
    }

    // NB: It's usually better to push completion statuses rather than poll for them, but I'm
    //     assuming the performance hit to be negligible, and keeping everything on one thread
    //     makes it much easier to visualize the flow while reading the code.
    public static void waitUntilTaskCompleted(CompletableFuture<Void> task,
                                              Supplier<Boolean> innerThreadHasThrownException) {
        Check.ifNull(task, "task");
        Check.ifNull(innerThreadHasThrownException, "innerThreadHasThrownException");

        while (!innerThreadHasThrownException.get() && !task.isDone()) {
            CheckedExceptionWrapper.sleep(50);
        }
    }

    public static void waitUntilTasksCompleted(List<CompletableFuture<Void>> tasks,
                                               Supplier<Boolean> innerThreadHasThrownException) {
        Check.ifNull(tasks, "tasks");
        waitUntilTasksCompleted(tasks, tasks.size(), innerThreadHasThrownException);
    }

    public static void waitUntilTasksCompleted(List<CompletableFuture<Void>> tasks,
                                               int tasksToComplete,
                                               Supplier<Boolean> innerThreadHasThrownException) {
        Check.ifNull(tasks, "tasks");
        Check.throwOnLteZero(tasksToComplete, "tasksToComplete");
        Check.ifNull(innerThreadHasThrownException, "innerThreadHasThrownException");

        while (!innerThreadHasThrownException.get() &&
                tasks.size() < tasksToComplete ||
                tasks.stream().anyMatch(task -> !task.isDone())) {
            CheckedExceptionWrapper.sleep(50);
        }
    }
}
