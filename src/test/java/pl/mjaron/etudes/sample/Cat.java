package pl.mjaron.etudes.sample;

@Deprecated
@SuppressWarnings("unused")
public class Cat {

    /**
     * Cat's name.
     */
    public String name;

    /**
     * How many legs this cat has.
     * Maybe this cat lost its legs while crossing the street.
     */
    public int legsCount = 0;

    /**
     * Tells whether the cat is lazy or hardworking.
     * This variable doesn't matter if the cat has no legs.
     */
    private boolean lazy = true;

    /**
     * The maximum speed of this cat.
     * This variable doesn't matter if the cat has no legs.
     */
    private double topSpeed = 0;

    public static Cat sampleCat() {
        final Cat cat = new Cat();
        cat.name = "John";
        cat.legsCount = 4;
        cat.setLazy(true);
        cat.setTopSpeed(35.24);
        return cat;
    }

    public static Cat otherCat() {
        final Cat cat = new Cat();
        cat.name = "Bob";
        cat.legsCount = 5;
        cat.setLazy(false);
        cat.setTopSpeed(75.00);
        return cat;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(final double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(final boolean lazy) {
        this.lazy = lazy;
    }
}
