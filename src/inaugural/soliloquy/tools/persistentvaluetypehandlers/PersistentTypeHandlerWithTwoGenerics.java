package inaugural.soliloquy.tools.persistentvaluetypehandlers;

import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

public abstract class PersistentTypeHandlerWithTwoGenerics<T> extends PersistentTypeHandler<T> {
    protected final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    protected PersistentTypeHandlerWithTwoGenerics(PersistentValuesHandler persistentValuesHandler) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
    }

    protected abstract Object generateTypeFromFactory(Object archetype1, Object archetype2);

    private Object generateTypeFromGenericParameterNames(String genericParameters) {
        int caretLevel = 0;
        int substringSeparator = -1;
        for (int i = 0; i < genericParameters.length(); i++) {
            char c = genericParameters.charAt(i);
            if (c == ',' && caretLevel == 0) {
                substringSeparator = i;
                break;
            } else if (c == '<') {
                caretLevel++;
            } else if (c == '>') {
                caretLevel--;
            }
        }
        if (substringSeparator < 0) {
            throw new IllegalArgumentException(
                    "PersistentHandlerWithTwoGenerics.generateTypeFromGenericParameterNames: " +
                            "No comma found separating params");
        }
        String genericParameter1 = genericParameters.substring(0,substringSeparator);
        String genericParameter2 = genericParameters.substring(substringSeparator + 1,
                genericParameters.length() - 1);

        return generateTypeFromFactory(
                PERSISTENT_VALUES_HANDLER.generateArchetype(genericParameter1),
                PERSISTENT_VALUES_HANDLER.generateArchetype(genericParameter2));
    }

    @SuppressWarnings("unchecked")
    public T generateArchetype(String valueType) throws IllegalArgumentException {
        if (valueType == null) {
            throw new IllegalArgumentException(
                    "PersistentHandlerWithTwoGenerics.generateArchetype: valueType must be non-null");
        }
        if (valueType.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentHandlerWithTwoGenerics.generateArchetype: valueType must be non-empty");
        }
        int openingCaret = valueType.indexOf("<");
        if (openingCaret < 0) {
            throw new IllegalArgumentException(
                    "PersistentHandlerWithTwoGenerics.generateArchetype: valueType has no opening caret");
        }
        int closingCaret = valueType.lastIndexOf(">");
        if (closingCaret < 0) {
            throw new IllegalArgumentException(
                    "PersistentHandlerWithTwoGenerics.generateArchetype: valueType has no closing caret");
        }

        return (T) generateTypeFromGenericParameterNames(valueType
                .substring(openingCaret + 1, closingCaret + 1));
    }
}