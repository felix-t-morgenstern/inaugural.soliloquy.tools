package inaugural.soliloquy.tools.reflection;

import inaugural.soliloquy.tools.collections.Collections;
import soliloquy.specs.common.entities.*;
import soliloquy.specs.common.entities.Runnable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static soliloquy.specs.common.entities.BiConsumer.biConsumer;
import static soliloquy.specs.common.entities.BiFunction.biFunction;
import static soliloquy.specs.common.entities.Consumer.consumer;
import static soliloquy.specs.common.entities.Function.function;
import static soliloquy.specs.common.entities.Runnable.runnable;
import static soliloquy.specs.common.entities.Supplier.supplier;

public class Reflection {
    private static final Set<String> BASE_OBJECT_METHODS =
            Arrays.stream(Object.class.getMethods()).map(Method::getName)
                    .collect(Collectors.toSet());

    @SuppressWarnings("rawtypes")
    public static Methods readMethods(Class aClass) {
        return readMethods(getInstance(aClass));
    }

    @SuppressWarnings("rawtypes")
    public static Methods readMethods(Object instance) {
        var runnables = Collections.<Runnable>setOf();
        var suppliers = Collections.<Supplier>setOf();
        var consumers = Collections.<Consumer>setOf();
        var biConsumers = Collections.<BiConsumer>setOf();
        var functions = Collections.<Function>setOf();
        var biFunctions = Collections.<BiFunction>setOf();

        Arrays.stream(instance.getClass().getMethods())
                .filter(m -> !BASE_OBJECT_METHODS.contains(m.getName()) &&
                        !m.isAnnotationPresent(DoNotReadMethod.class))
                .forEach(m -> {
                    var name = m.getName();
                    var returnType = m.getReturnType();
                    switch (m.getParameterCount()) {
                        case 0 -> {
                            runnables.add(makeRunnable(name, m, instance));
                            if (!returnType.equals(Void.TYPE)) {
                                suppliers.add(makeSupplier(name, m, instance));
                            }
                        }
                        case 1 -> {
                            consumers.add(makeConsumer(name, m, instance));
                            if (!returnType.equals(Void.TYPE)) {
                                functions.add(makeFunction(name, m, instance));
                            }
                        }
                        case 2 -> {
                            biConsumers.add(makeBiConsumer(name, m, instance));
                            if (!returnType.equals(Void.TYPE)) {
                                biFunctions.add(makeBiFunction(name, m, instance));
                            }
                        }
                        default -> throw new IllegalArgumentException(
                                "Reflection#readMethods: method (" + name +
                                        ") has unsupported param count [" + m.getParameterCount() +
                                        "]");
                    }
                });

        return new Methods(runnables, suppliers, consumers, biConsumers, functions, biFunctions);
    }

    private static Object getInstance(@SuppressWarnings("rawtypes") Class aClass) {
        try {
            //noinspection unchecked
            return aClass.getConstructor().newInstance();
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "Reflection.readMethods: aClass has no public, zero-param constructor");
        }
        catch (InstantiationException | InvocationTargetException |
               IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Runnable makeRunnable(String id, Method method, Object containingClass) {
        checkMethodParams(method);
        return runnable(id, () -> {
            try {
                method.invoke(containingClass);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <T> Supplier<T> makeSupplier(String id, Method method, Object containingClass) {
        checkMethodParams(method);
        //noinspection unchecked
        return (Supplier<T>) supplier(id, () -> {
            try {
                return method.invoke(containingClass);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <T> Consumer<T> makeConsumer(String id, Method method, Object containingClass) {
        checkMethodParams(method);
        return consumer(id, i -> {
            try {
                method.invoke(containingClass, i);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <TInput1, TInput2> BiConsumer<TInput1, TInput2> makeBiConsumer(
            String id, Method method, Object containingClass) {
        return biConsumer(id, (i1, i2) -> {
            try {
                method.invoke(containingClass, i1, i2);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <TInput, TOutput> Function<TInput, TOutput> makeFunction(
            String id, Method method, Object containingClass) {
        //noinspection unchecked
        return (Function<TInput, TOutput>) function(id, i -> {
            try {
                return method.invoke(containingClass, i);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <TInput1, TInput2, TOutput> BiFunction<TInput1, TInput2, TOutput> makeBiFunction(
            String id, Method method, Object containingClass) {
        //noinspection unchecked
        return (BiFunction<TInput1, TInput2, TOutput>) biFunction(id, (i1, i2) -> {
            try {
                return method.invoke(containingClass, i1, i2);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void checkMethodParams(Method method) {
        if (method.getParameterCount() > 1) {
            throw new IllegalArgumentException(
                    "Reflection.readMethods: method (" + method.getName() +
                            ") cannot have more than 1 parameter");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DoNotReadMethod {
    }
}
