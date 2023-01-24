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

import pl.mjaron.etudes.table.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Common object code.
 */
public abstract class Obj {

    public static <T> Field[] getFields(final Class<T> tClass) {
        return tClass.getDeclaredFields();
    }

    public static <T> List<String> getFieldNames(final Class<T> tClass, final Field[] fields) {
        List<String> names = new ArrayList<>(fields.length);
        for (final Field entry : fields) {
            names.add(entry.getName());
        }
        return names;
    }

    public static <T> List<String> getFieldNames(final Class<T> tClass) {
        final Field[] fields = getFields(tClass);
        return getFieldNames(tClass, fields);
    }

    /**
     * Abstract visitor of any types of field values.
     */
    public interface IFieldVisitor {
        void visit(final String name, final Object value);
    }

    /**
     * Allows to visit all values of Java Bean object.
     *
     * @param what    Object reference.
     * @param tClass  Class of object.
     * @param fields  Fields of object class.
     * @param visitor visitor instance.
     * @param <T>     Type of object.
     */
    public static <T> void visitFieldValues(final T what, final Class<T> tClass, final Field[] fields, final IFieldVisitor visitor) {
        for (final Field field : fields) {
            try {
                final Object fieldValue = field.get(what);
                visitor.visit(field.getName(), fieldValue);
                continue;
            } catch (IllegalAccessException e) {
//                throw new RuntimeException("Cannot obtain value of field: [" + field.getName() +
//                        "], of type: [" + field.getType() + "]: Getting field value has failed.");
            }

            // Failed to get field value. Trying to obtain getter method.
            final String fieldNameCapitalized = Str.capitalize(field.getName());
            Method getter;
            try {
                getter = tClass.getMethod("get" + fieldNameCapitalized);
            } catch (final NoSuchMethodException e1) {
                if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                    try {
                        getter = tClass.getMethod("is" + fieldNameCapitalized);
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
                visitor.visit(field.getName(), getterResult);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot obtain value of field: [" + field.getName() +
                        "], of type: [" + field.getType() + "]: invocation of getter has failed.", e);
            }
        }
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

        visitFieldValues(what, clazz, fields, new IFieldVisitor() {
            @Override
            public void visit(String name, Object value) {
                map.put(name, value);
            }
        });
        return map;
    }

    public static <T> Map<String, String> getFieldValuesAsStrings(final T what) {
        final Map<String, Object> fieldObjects = getFieldValues(what);
        final Map<String, String> fieldStrings = new HashMap<>();
        for (final Map.Entry<String, Object> entry : fieldObjects.entrySet()) {
            fieldStrings.put(entry.getKey(), entry.getValue().toString());
        }
        return fieldStrings;
    }

    /**
     * Prints data object as a table, e.g:
     *
     * <pre>
     * | name | legsCount | lazy | topSpeed |
     * | ---- | --------- | ---- | -------- |
     * | John |         4 | true |    35.24 |
     * </pre>
     *
     * @param iterable Any iterable collection.
     * @param tClass   Class of iterated entries.
     * @param <T>      Type of iterated entries.
     * @return Markdown table.
     * @deprecated Use {@link Table#render(Iterable, Class)}
     */
    public static <T> String asTable(final Iterable<T> iterable, final Class<T> tClass) {
        final BeanTableSource<T> tableSource = new BeanTableSource<>(iterable, tClass);
        final MarkdownTableWriter tableWriter = new MarkdownTableWriter();
        return asTable(tableSource, tableWriter);
    }

    /**
     * Read from table source and write to table writer.
     *
     * @param source    Table source.
     * @param writer    Table destination.
     * @param <SourceT> Type of table source.
     * @param <WriterT> Type of table destination.
     * @return Table written as a String.
     * @deprecated Use {@link Table#renderWith(ITableSource, ITableWriter)}
     */
    public static <SourceT extends ITableSource, WriterT extends ITableWriter> String asTable(final SourceT source, final WriterT writer) {
        return Table.renderWith(source, writer);
    }
}
