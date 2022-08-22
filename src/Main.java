import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies",
                "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]));
        }
        long minors = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();

        List<String> conscripts = persons.stream()
                .filter(x -> x.getAge() <= 27 && x.getAge() >= 18)
                .map(x -> x.getFamily())
                .collect(Collectors.toList());

        List<String> workers = persons.stream()
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18) // можно было прописать в условии if ниже
                .filter(x -> x.getAge() <= 65) // но так визуально лучше воспринимается
                .filter(x -> {
                    if (x.getSex().equals(Sex.MAN)) {
                        return true;
                    } else if (x.getSex().equals(Sex.WOMAN) && x.getAge() <= 60) {
                        return true;
                        // можно было сократить код за счет:
                        // сразу else return x.getSex().equals(Sex.WOMAN) && x.getAge() <= 60;
                        // но это была идея IDEA, писал как привычней и более знакомо
                    } else {
                        return false;
                    }
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .map(x -> x.getFamily())
                .collect(Collectors.toList());

        //System.out.println("Призывники = " + conscripts); //Их много
        //System.out.println();
        System.out.println("Лондонских несовершеннолетних = " + minors);
        //System.out.println();
        //System.out.println("Работники во всем Лондоне " + workers );// Этих еще больше
    }
}