/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pl.mjaron.etudes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common object code.
 */
public abstract class Obj {

    public static <T> List<String> getFieldNames(final Class<T> clazz) {
        final Field[] fields = clazz.getDeclaredFields();
        List<String> names = new ArrayList<>(fields.length);
        for (final Field entry : fields) {
            names.add(entry.getName());
        }
        return names;
    }

    /**
     * Provides map with fields and its values.
     *
     * @param what Analyzed object.
     * @param <T>  Type of object.
     * @return Map of field names related with field values.
     */
    public static <T> Map<String, Object> getFieldValues(final T what) {

        Map<String, Object> map = new HashMap<>();

        @SuppressWarnings("unchecked") final Class<T> clazz = (Class<T>) what.getClass();

        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            if (field.canAccess(what)) {
                try {
                    final Object fieldValue = field.get(what);
                    map.put(field.getName(), fieldValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot obtain value of field: [" + field.getName() +
                            "], of type: [" + field.getType() + "]: Getting field value has failed.");
                }
            } else {
                final String fieldNameCapitalized = Str.capitalize(field.getName());
                Method getter;
                try {
                    getter = clazz.getMethod("get" + fieldNameCapitalized);
                } catch (final NoSuchMethodException e1) {
                    if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                        try {
                            getter = clazz.getMethod("is" + fieldNameCapitalized);
                        } catch (final NoSuchMethodException e2) {
                            throw new RuntimeException("Cannot obtain value of field: [" + field.getName() +
                                    "], of type: [" + field.getType() + "]: Field is not public and there is no get"
                                    + fieldNameCapitalized + "() + nor is" + fieldNameCapitalized + "() method accessible.", e2);
                        }
                    } else {
                        throw new RuntimeException("Cannot obtain value of field: [" + field.getName() +
                                "], of type: [" + field.getType() + "]: Field is not public and there is no get"
                                + fieldNameCapitalized + "() method accessible.", e1);
                    }
                }
                try {
                    final Object getterResult = getter.invoke(what);
                    map.put(field.getName(), getterResult);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Cannot obtain value of field: [" + field.getName() +
                            "], of type: [" + field.getType() + "]: invocation of getter has failed.", e);
                }
            }
        }
        return map;
    }

    public static <T> Map<String, String> getFieldValuesAsStrings(final T what) {
        final Map<String, Object> fieldObjects = getFieldValues(what);
        final Map<String, String> fieldStrings = new HashMap<>();
        for (final var entry : fieldObjects.entrySet()) {
            fieldStrings.put(entry.getKey(), entry.getValue().toString());
        }
        return fieldStrings;
    }

    /**
     * Prints data object as a table, e.g:<br/> <br/>
     *
     * <pre>
     * | name | legsCount | lazy | topSpeed |
     * | ---- | --------- | ---- | -------- |
     * | John |         4 | true |    35.24 |
     * </pre>
     *
     * @param iterable
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String asTable(final Iterable<T> iterable, final Class<T> clazz) {
        // List of field names.
        final List<String> names = getFieldNames(clazz);

        // Max widths of field values.
        final Map<String, Integer> fieldWidths = new HashMap<>();
        for (final T tEntry : iterable) {
            final Map<String, String> fields = getFieldValuesAsStrings(tEntry);
            for (final var fieldKeyValue : fields.entrySet()) {
                final int oldEntryWidth = fieldWidths.getOrDefault(fieldKeyValue.getKey(), 0);
                final int newEntryWidth = Integer.max(oldEntryWidth, fieldKeyValue.getValue().length());
                fieldWidths.put(fieldKeyValue.getKey(), newEntryWidth);
            }
        }
        // Appending max widths of headers.
        for (final String name : names) {
            final int oldEntryWidth = fieldWidths.getOrDefault(name, 0);
            final int newEntryWidth = Integer.max(oldEntryWidth, name.length());
            fieldWidths.put(name, newEntryWidth);
        }

        // Time to start printing table.
        final StringBuilder ss = new StringBuilder();

        // Printing headers
        for (final String name : names) {
            ss.append("| ");
            Str.padLeft(name, ' ', fieldWidths.get(name), ss);
            ss.append(' ');
        }
        ss.append('|');
        ss.append('\n');

        // Printing headers separator
        for (final String name : names) {
            ss.append("| ");
            for (int i = fieldWidths.get(name); i > 0; --i) {
                ss.append('-');
            }
            ss.append(' ');
        }
        ss.append('|');
        ss.append('\n');

        // Printing values
        for (final T tEntry : iterable) {
            final Map<String, String> fields = getFieldValuesAsStrings(tEntry);
            for (final String name : names) {
                final String value = fields.get(name);
                ss.append("| ");
                Str.padLeft(value, ' ', fieldWidths.get(name), ss);
                ss.append(' ');
            }
            ss.append('|');
            ss.append('\n');
        }

        return ss.toString();
    }
}
