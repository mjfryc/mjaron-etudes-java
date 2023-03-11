import org.junit.jupiter.api.Test;
import pl.mjaron.etudes.Table;

class Fish {
    private final String name;

    private final int age;

    private final int eyesCount;

    public Fish(String name, int age, int eyesCount) {
        this.name = name;
        this.age = age;
        this.eyesCount = eyesCount;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getEyesCount() {
        return eyesCount;
    }
}

public class OutOfPackageTest {

    @Test
    void outOfPackageTableTest() {
        Fish[] fishes = new Fish[]{new Fish("Tom", 5, 2), new Fish("Michael", 3, 27), new Fish("Daniel", 7, 123)};
        Table.render(fishes, Fish.class).run();
    }
}
