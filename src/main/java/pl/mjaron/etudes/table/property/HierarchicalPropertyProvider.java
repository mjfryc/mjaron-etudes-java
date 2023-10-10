/*
 * Copyright  2023  Michał Jaroń <m.jaron@protonmail.com>
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

package pl.mjaron.etudes.table.property;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HierarchicalPropertyProvider<T> {

    /**
     * Contains properties depending on class type.
     * By-class properties have bigger priority than root node, but lower than direct rows / cells.
     *
     * @since 0.3.1
     */
    private Map<Class<?>, T> byClassProperties = null;

    private final PropertyNode<T> tableNode = new PropertyNode<>();

    public void setByClass(final Class<?> clazz, final T prop) {
        if (byClassProperties == null) { // Ensure not null.
            byClassProperties = new HashMap<>();
        }
        byClassProperties.put(clazz, prop);
    }

    public PropertyNode<T> getRootNode() {
        return tableNode;
    }

    T getByClassPropertyOrRootValue(final @Nullable Class<?> clazz) {
        if (clazz != null && byClassProperties != null) {
            final T byClassValue = byClassProperties.get(clazz);
            if (byClassValue != null) {
                return byClassValue;
            }
        }
        return tableNode.getValue();
    }

    /**
     * Provides property value from three-level tree, where the first level is a root node.
     *
     * @param a Second level.
     * @param b Third level.
     * @return Property value.
     */
    T getValue(int a, int b, final Class<?> clazz) {
        PropertyNode<T> aChild = tableNode.getChild(a);
        if (aChild == null) {
            return getByClassPropertyOrRootValue(clazz); // If a-child missing, provide root node.
        }
        PropertyNode<T> bChild = aChild.getChild(b);
        if (bChild == null) {
            if (aChild.getValue() != null) {
                return aChild.getValue();
            }
            return getByClassPropertyOrRootValue(clazz);
        }
        if (bChild.getValue() != null) {
            return bChild.getValue();
        }
        if (aChild.getValue() != null) {
            return aChild.getValue();
        }
        return getByClassPropertyOrRootValue(clazz);
    }
}
