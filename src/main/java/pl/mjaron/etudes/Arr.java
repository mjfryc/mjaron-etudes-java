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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Array utilities.
 */
public abstract class Arr {

    /**
     * Appends <code>null</code> values to the array's end until the array has required minimum size.
     * <p>
     * If array is bigger than given size, no any action is performed.
     *
     * @param arrayList {@link ArrayList} to update.
     * @param size      Required minimal size of {@link ArrayList}.
     * @since 0.2.2
     */
    public static void ensureSize(@NotNull ArrayList<?> arrayList, @Range(from = 0, to = Integer.MAX_VALUE) final int size) {
        arrayList.ensureCapacity(size);
        while (arrayList.size() < size) {
            arrayList.add(null);
        }
    }

    /**
     * Removes elements from the array's end until the array has required maximum size.
     * <p>
     * If array is smaller than given size, no any action is performed.
     *
     * @param arrayList {@link ArrayList} to update.
     * @param size      Required maximal size of {@link ArrayList}.
     * @since 0.2.2
     */
    public static void trimToSize(@NotNull ArrayList<?> arrayList, @Range(from = 0, to = Integer.MAX_VALUE) final int size) {
        while (arrayList.size() > size) {
            arrayList.remove(arrayList.size() - 1);
        }
    }

    /**
     * Extends or trims the array to given size. Removes elements from the end or appends null values.
     *
     * @param arrayList {@link ArrayList} to update.
     * @param size      Required maximal size of {@link ArrayList}.
     * @since 0.2.2
     */
    public static void resize(@NotNull ArrayList<?> arrayList, @Range(from = 0, to = Integer.MAX_VALUE) final int size) {
        ensureSize(arrayList, size);
        trimToSize(arrayList, size);
    }

    /**
     * Determines maximum value in array.
     *
     * @param array Given array.
     * @return Maximum value in array.
     * @since 0.2.2
     */
    public static int max(final int[] array) {
        int v = Integer.MIN_VALUE;
        for (final int i : array) {
            if (i > v) {
                v = i;
            }
        }
        return v;
    }

    /**
     * Determines minimum value in array.
     *
     * @param array Given array.
     * @return Minimum value in array.
     * @since 0.2.2
     */
    public static int min(final int[] array) {
        int v = Integer.MAX_VALUE;
        for (final int i : array) {
            if (i < v) {
                v = i;
            }
        }
        return v;
    }

    public static <T> T[] add(final T[] array, final T what) {
        T[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static <T> T[] add(final T[] array, final T[] other) {
        T[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }


    public static byte[] add(byte[] array, final byte what) {
        byte[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static byte[] add(final byte[] array, final byte[] other) {
        byte[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }


    public static char[] add(char[] array, final char what) {
        char[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static char[] add(final char[] array, final char[] other) {
        char[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }


    public static short[] add(short[] array, final short what) {
        short[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static short[] add(final short[] array, final short[] other) {
        short[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }

    public static int[] add(int[] array, final int what) {
        int[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static int[] add(final int[] array, final int[] other) {
        int[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }

    public static long[] add(long[] array, final long what) {
        long[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static long[] add(final long[] array, final long[] other) {
        long[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }


    public static float[] add(float[] array, final float what) {
        float[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static float[] add(final float[] array, final float[] other) {
        float[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }


    public static double[] add(double[] array, final double what) {
        double[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static double[] add(final double[] array, final double[] other) {
        double[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }


    public static boolean[] add(boolean[] array, final boolean what) {
        boolean[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static boolean[] add(final boolean[] array, final boolean[] other) {
        boolean[] newArray = Arrays.copyOf(array, array.length + other.length);
        System.arraycopy(other, 0, newArray, array.length, other.length);
        return newArray;
    }

    public static <T> boolean containsReference(final T[] arr, final T elem) {
        for (final T entry : arr) {
            if (entry == elem) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(final char[] arr, final char elem) {
        for (final char entry : arr)
            if (entry == elem) return true;
        return false;
    }
}
