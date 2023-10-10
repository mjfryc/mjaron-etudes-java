package pl.mjaron.etudes.sample;

import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("unused")
public class Person {
    private final String name;
    private final String surname;
    private final Date birthday;
    private final String address;
    private final String contact;

    public Person(String name, String surname, Date birthday, String address, String contact) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.address = address;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public static Person[] getSampleData() {
        return new Person[] {
                new Person("Sally", "Fox", new Date(1972 - 1900, Calendar.MARCH, 25), "London", "sally@sallymfox.com"),
                new Person("Jay", "Elephant", new Date(1920 - 1900, Calendar.MARCH, 20), "Paris", "jay.elephant@protonmail.com"),
                new Person("Bella", "Tran", new Date(1963 - 1900, Calendar.APRIL, 12), "Warszawa", "tran@bella.com")
        };
    }
}
