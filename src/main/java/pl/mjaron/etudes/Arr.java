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

import java.util.Arrays;

/**
 * Array utilities.
 */
public abstract class Arr {

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
            if (entry == elem)
                return true;
        return false;
    }
}
