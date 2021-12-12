package pl.mjaron.etudes.table;

@Deprecated
// StaticTable is not ready yet...
public class StaticTable {

    public final int rows;
    public final int cols;
    public final String[] data;

    public StaticTable(final int rows, final int cols, final String[] data) {
        this.rows = rows;
        this.cols = cols;
        this.data = data;
    }

    public StaticTable(final int rows, final int cols) {
        this(rows, cols, new String[rows * cols]);
    }

    public void set(final int row, final int col, final String what) {
        data[row * cols + col] = what;
    }

    public String get(final int row, final int col) {
        return data[row * cols + col];
    }

}
