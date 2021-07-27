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
import java.util.HashMap;
import java.util.Map;

/**
 * Common object code.
 */
public abstract class Obj {

    /**
     * Provides map with fields and its values.
     * @param what Analyzed object.
     * @param <T> Type of object.
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
                Method getter = null;
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

}
