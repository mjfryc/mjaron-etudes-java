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

package pl.mjaron.etudes.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.mjaron.etudes.Obj;

/**
 * Creates table source from the java bean. Each table row describes single object instance. Each table column describes
 * object field value.
 *
 * @param <BeanT> Bean class type.
 */
public class BeanTableSource<BeanT> implements ITableSource {

    final private Class<BeanT> tClass;
    final private Iterable<BeanT> values;
    final private Field[] tFields;
    final private List<String> headers;

    /**
     * Initializes the object which is ready to iterate.
     *
     * <pre>
     * {@code
     *     List<MyClass> objects = ...
     *
     *     |MyClass.field0              |MyClass.field1              |...
     *     |----------------------------|----------------------------|...
     *     |objects[0].field0.toString()|objects[0].field1.toString()|...
     *     |objects[1].field0.toString()|objects[1].field1.toString()|...
     * }
     * </pre>
     *
     * @param values Any iterable object or container which stores the series of objects.
     * @param tClass Class of the iterated object.
     */
    public BeanTableSource(final Iterable<BeanT> values, final Class<BeanT> tClass) {
        this.tClass = tClass;
        this.values = values;
        this.tFields = Obj.getFields(this.tClass);
        this.headers = Obj.getFieldNames(tFields);
    }

    @Override
    public int getColumnsCount() {
        return tFields.length;
    }

    @Override
    public Iterable<String> getHeaders() {
        return this.headers;
    }

    @Override
    public Iterator<Iterable<Object>> iterator() {
        return new RowsIterator<>(tClass, tFields, values);
    }

    /**
     * Iterates the table rows. Single row represents single object instance.
     *
     * @param <BeanT> Type of the object.
     */
    private static class RowsIterator<BeanT> implements Iterator<Iterable<Object>> {

        final Iterator<BeanT> beanIterator;
        final private Class<BeanT> tClass;
        final private Field[] tFields;

        public RowsIterator(final Class<BeanT> tClass, final Field[] tFields, final Iterable<BeanT> values) {
            this.tClass = tClass;
            this.tFields = tFields;
            this.beanIterator = values.iterator();
        }

        @Override
        public boolean hasNext() {
            return beanIterator.hasNext();
        }

        /**
         * Prepares next table row. Iterating returned row allows obtaining row's cell values.
         *
         * @return Next table row.
         */
        @Override
        public Iterable<Object> next() {
            final BeanT bean = beanIterator.next();
            List<Object> record = new ArrayList<>(tFields.length);
            Obj.visitFieldValues(bean, this.tClass, this.tFields, (name, value) -> record.add(value));
            return record;
        }
    }
}
