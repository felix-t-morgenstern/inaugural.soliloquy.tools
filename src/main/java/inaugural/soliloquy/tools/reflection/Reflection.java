package inaugural.soliloquy.tools.reflection;

import inaugural.soliloquy.tools.collections.Collections;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.valueobjects.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static soliloquy.specs.common.entities.Action.action;
import static soliloquy.specs.common.entities.Function.function;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class Reflection {
    private static final Set<String> BASE_OBJECT_METHODS =
            Arrays.stream(Object.class.getMethods()).map(Method::getName)
                    .collect(Collectors.toSet());

    @SuppressWarnings("rawtypes")
    public static Pair<Set<Action>, Set<Function>> readMethods(Class aClass) {
        return readMethods(getInstance(aClass));
    }

    @SuppressWarnings("rawtypes")
    public static Pair<Set<Action>, Set<Function>> readMethods(Object instance) {
        var actions = Collections.<Action>setOf();
        var functions = Collections.<Function>setOf();

        Arrays.stream(instance.getClass().getMethods()).filter(m -> !BASE_OBJECT_METHODS.contains(m.getName()))
                .forEach(m -> {
                    var name = m.getName();
                    var returnType = m.getReturnType();
                    if (returnType.equals(Void.TYPE)) {
                        actions.add(makeAction(name, m, instance));
                    }
                    else {
                        functions.add(makeFunction(name, m, instance));
                        actions.add(makeAction(name, m, instance));
                    }
                });

        return pairOf(actions, functions);
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

    private static <T> Action<T> makeAction(String id, Method method, Object containingClass) {
        checkMethodParams(method);
        return action(id, i -> {
            try {
                method.invoke(containingClass, i);
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

    private static void checkMethodParams(Method method) {
        if (method.getParameterCount() > 1) {
            throw new IllegalArgumentException(
                    "Reflection.readMethods: method (" + method.getName() +
                            ") cannot have more than 1 parameter");
        }
    }
}
