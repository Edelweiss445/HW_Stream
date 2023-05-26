import Persons.Education;
import Persons.Person;
import Persons.Sex;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                            names.get(new Random().nextInt(names.size())),
                            families.get(new Random().nextInt(families.size())),
                            new Random().nextInt(100),
                            Sex.values()[new Random().nextInt(Sex.values().length)],
                            Education.values()[new Random().nextInt(Education.values().length)]
                    )
            );
        }

// 1-ый стрим по задаче поиска несовершеннолетних
        long count = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + count);

// 2-ой стрим призывников
        System.out.println(" ");

        List<String> army = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getAge() < 27)
                .map(Person::getFamily) // преобразование в строку - фамилию
                .collect(Collectors.toList()); // собираем фамилии в коллекцию
        System.out.println("Фамилии новобранцев: " + army);

// 3-ий стрим с высшим образованием
        System.out.println(" ");
//
        List<Person> worker = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER &&
                        ((person.getSex() == Sex.WOMAN && person.getAge() >= 18 && person.getAge() <= 60) ||
                                (person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 65)))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Список потенциально работоспособных людей с высшим образованием: ");
        worker.forEach(System.out::println);
    }
}
