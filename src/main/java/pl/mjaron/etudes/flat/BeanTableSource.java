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

package pl.mjaron.etudes.flat;

import pl.mjaron.etudes.Obj;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creates table source from java bean.
 *
 * @param <BeanT> Bean type.
 */
public class BeanTableSource<BeanT> implements ITableSource {

    final private Class<BeanT> tClass;
    final private Iterable<BeanT> values;
    final private Field[] tFields;
    final private List<String> headers;

    public BeanTableSource(final Iterable<BeanT> values, final Class<BeanT> tClass) {
        this.tClass = tClass;
        this.values = values;
        this.tFields = Obj.getFields(this.tClass);
        this.headers = Obj.getFieldNames(this.tClass, tFields);
    }

    @Override
    public boolean hasHeaders() {
        return true;
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
    public Iterator<Iterable<String>> iterator() {
        return new RowsIterator<>(tClass, tFields, values);
    }

    private static class RowsIterator<BeanT> implements Iterator<Iterable<String>> {

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

        @Override
        public Iterable<String> next() {
            final BeanT bean = beanIterator.next();
            List<String> stringSeries = new ArrayList<>(tFields.length);
            Obj.visitFieldValues(bean, this.tClass, this.tFields, new Obj.IFieldVisitor() {
                @Override
                public void visit(final String name, final Object value) {
                    stringSeries.add(value.toString());
                }
            });
            return stringSeries;
        }
    }
}
