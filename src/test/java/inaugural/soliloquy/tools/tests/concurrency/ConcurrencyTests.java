package inaugural.soliloquy.tools.tests.concurrency;

import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import static inaugural.soliloquy.tools.concurrency.Concurrency.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ConcurrencyTests {
    private final ExecutorService EXECUTOR_SERVICE = ForkJoinPool.commonPool();

    // NB: I really hate having all these static and long-winded variables, but Java does not
    // permit local and effectively final variables within lambda functions.
    private static boolean WaitUntilTaskCompletedHasExecuted = false;
    private static boolean WaitUntilTaskCompletedTaskExceptionThrown = false;
    private static boolean WaitUntilTaskCompletedHasMetExceptionCaught = false;

    private static boolean WaitUntilTasksCompletedFromListHasCompleted = false;
    private static boolean WaitUntilTasksCompletedFromListExceptionThrown = false;
    private static boolean WaitUntilTasksCompletedFromListExceptionCaught = false;

    private static boolean WaitUntilTasksCompletedWithPredefinedNumberHasCompleted = false;
    private static boolean WaitUntilTasksCompletedWithPredefinedNumberExceptionThrown = false;
    private static boolean WaitUntilTasksCompletedWithPredefinedNumberExceptionCaught = false;

    @Test
    void testRunTaskAsync() {
        ArrayList<Boolean> taskOutputs = new ArrayList<>();

        runTaskAsync(() -> taskOutputs.add(true), t -> {}, EXECUTOR_SERVICE);
        CheckedExceptionWrapper.sleep(50);

        assertEquals(1, taskOutputs.size());
    }

    @Test
    void testRunTaskAsyncHandlesThrowables() {
        RuntimeException t = mock(RuntimeException.class);
        ArrayList<Throwable> throwables = new ArrayList<>();

        runTaskAsync(() -> {throw t;}, throwables::add, EXECUTOR_SERVICE);
        CheckedExceptionWrapper.sleep(50);

        assertEquals(1, throwables.size());
        assertSame(t, throwables.get(0).getCause());
    }

    @Test
    void testRunTaskAsyncWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> runTaskAsync(null, t -> {}, EXECUTOR_SERVICE));
        assertThrows(IllegalArgumentException.class,
                () -> runTaskAsync(() -> {}, null, EXECUTOR_SERVICE));
        assertThrows(IllegalArgumentException.class, () -> runTaskAsync(() -> {}, t -> {}, null));
    }

    @Test
    void testWaitUntilTaskCompleted() {
        CompletableFuture<Void> task =
                CompletableFuture.runAsync(() -> CheckedExceptionWrapper.sleep(100));

        new Thread(() -> {
            waitUntilTaskCompleted(task, () -> false);
            WaitUntilTaskCompletedHasExecuted = true;
        }).start();

        assertFalse(WaitUntilTaskCompletedHasExecuted);

        CheckedExceptionWrapper.sleep(250);

        assertTrue(WaitUntilTaskCompletedHasExecuted);
    }

    @Test
    void testWaitUntilTaskCompletedAbortsOnException() {
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            CheckedExceptionWrapper.sleep(100);
            WaitUntilTaskCompletedTaskExceptionThrown = true;
        });

        new Thread(() -> {
            waitUntilTaskCompleted(task, () -> WaitUntilTaskCompletedTaskExceptionThrown);
            WaitUntilTaskCompletedHasMetExceptionCaught = true;
        }).start();

        assertFalse(WaitUntilTaskCompletedHasMetExceptionCaught);

        CheckedExceptionWrapper.sleep(250);

        assertTrue(WaitUntilTaskCompletedHasMetExceptionCaught);
    }

    @Test
    void testWaitUntilTaskCompletedWithInvalidArgs() {
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {});

        assertThrows(IllegalArgumentException.class,
                () -> waitUntilTaskCompleted(null, () -> true));
        assertThrows(IllegalArgumentException.class, () -> waitUntilTaskCompleted(task, null));
    }

    @Test
    void testWaitUntilTasksCompletedFromList() {
        CompletableFuture<Void> task1 =
                CompletableFuture.runAsync(() -> CheckedExceptionWrapper.sleep(100));
        CompletableFuture<Void> task2 =
                CompletableFuture.runAsync(() -> CheckedExceptionWrapper.sleep(300));

        new Thread(() -> {
            waitUntilTasksCompleted(new ArrayList<>() {{
                add(task1);
                add(task2);
            }}, () -> false);
            WaitUntilTasksCompletedFromListHasCompleted = true;
        }).start();

        assertFalse(WaitUntilTasksCompletedFromListHasCompleted);

        CheckedExceptionWrapper.sleep(200);

        assertFalse(WaitUntilTasksCompletedFromListHasCompleted);

        CheckedExceptionWrapper.sleep(200);

        assertTrue(WaitUntilTasksCompletedFromListHasCompleted);
    }

    @Test
    void testWaitUntilTasksCompletedFromListAbortsOnException() {
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            CheckedExceptionWrapper.sleep(100);
            WaitUntilTasksCompletedFromListExceptionThrown = true;
        });

        new Thread(() -> {
            waitUntilTasksCompleted(new ArrayList<>() {{
                add(task);
            }}, () -> WaitUntilTasksCompletedFromListExceptionThrown);
            WaitUntilTasksCompletedFromListExceptionCaught = true;
        }).start();

        assertFalse(WaitUntilTasksCompletedFromListExceptionCaught);

        CheckedExceptionWrapper.sleep(250);

        assertTrue(WaitUntilTasksCompletedFromListExceptionCaught);
    }

    @Test
    void testWaitUntilTasksCompletedFromListWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> waitUntilTasksCompleted(null, () -> true));
        assertThrows(IllegalArgumentException.class,
                () -> waitUntilTasksCompleted(new ArrayList<>(), null));
    }

    @Test
    void testWaitUntilTasksCompletedWithPresetNumber() {
        ArrayList<CompletableFuture<Void>> tasks = new ArrayList<>();

        new Thread(() -> {
            waitUntilTasksCompleted(tasks, 1, () -> false);
            WaitUntilTasksCompletedWithPredefinedNumberHasCompleted = true;
        }).start();

        assertFalse(WaitUntilTasksCompletedWithPredefinedNumberHasCompleted);

        tasks.add(CompletableFuture.runAsync(() -> {}));
        CheckedExceptionWrapper.sleep(100);

        assertTrue(WaitUntilTasksCompletedWithPredefinedNumberHasCompleted);
    }

    @Test
    void testWaitUntilTasksCompletedWithPresetNumberAbortsOnException() {
        ArrayList<CompletableFuture<Void>> tasks = new ArrayList<>();

        new Thread(() -> {
            waitUntilTasksCompleted(tasks, Integer.MAX_VALUE,
                    () -> WaitUntilTasksCompletedWithPredefinedNumberExceptionThrown);
            WaitUntilTasksCompletedWithPredefinedNumberExceptionCaught = true;
        }).start();

        assertFalse(WaitUntilTasksCompletedWithPredefinedNumberExceptionCaught);

        tasks.add(CompletableFuture.runAsync(() ->
                WaitUntilTasksCompletedWithPredefinedNumberExceptionThrown = true));
        CheckedExceptionWrapper.sleep(100);

        assertTrue(WaitUntilTasksCompletedWithPredefinedNumberExceptionCaught);
    }

    @Test
    void testWaitUntilTasksCompletedWithPresetNumberWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> waitUntilTasksCompleted(null, 1, () -> true));
        assertThrows(IllegalArgumentException.class,
                () -> waitUntilTasksCompleted(new ArrayList<>(), 0, () -> true));
        assertThrows(IllegalArgumentException.class,
                () -> waitUntilTasksCompleted(new ArrayList<>(), 1, null));
    }
}
