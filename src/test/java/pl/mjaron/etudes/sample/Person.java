package pl.mjaron.etudes.sample;

import java.util.Calendar;
import java.util.Date;

public class Person {
    private final String name;
    private final String surname;
    private final Date birthDay;
    private final String address;
    private final String contact;

    public Person(String name, String surname, Date birthDay, String address, String contact) {
        this.name = name;
        this.surname = surname;
        this.birthDay = birthDay;
        this.address = address;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public static Person[] getSampleData() {
        return new Person[]{
                new Person("Sally", "Fox", new Date(1990, Calendar.FEBRUARY, 20), "London", "sally@sallymfox.com"),
                new Person("Jay", "Acunzo", new Date(1920, Calendar.APRIL, 20), "Paris", "jay.acunzo@protonmail.com.com"),
                new Person("Bella", "Tran", new Date(1963, Calendar.JUNE, 12), "China", "a@b.com")
        };
    }
}