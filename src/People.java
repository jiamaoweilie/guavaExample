import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

public class People {
    List<Person> people;

    public People(List<Person> people) {
        this.people = people;
    }

    public List<Person> peopleFilter() {
//        List<Person> peopleFromChina = newArrayList();
//        for(Person person : people){
//            if("China".equals(person.getCountry()))
//                peopleFromChina.add(person);
//        }

        List<Person> filteredPeople = newArrayList(filter(people, and(form("China"), orderThan(25))));

        return filteredPeople;
    }

    private Predicate<Person> form(final String country) {
        return new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return country.equals(person.getCountry());
            }
        };
    }

    private Predicate<Person> orderThan(final int age) {
        return new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return person.getAge() > age;
            }
        };
    }

    public List<String> getName(List<Person> people){
//        List<String> names = newArrayList();
//        for(Person     person : people){
//            names.add(person.getName());
//        }

        List<String> names = newArrayList(transform(people, new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.getName();
            }
        }));
        return names;
    }

    public ImmutableList<String> chineseName(List<Person> people){
        return FluentIterable.from(people)
                .filter(new Predicate<Person>() {
                    @Override
                    public boolean apply(Person person) {
                        return "China".equals(person.getCountry());
                    }
                })
                .transform(new Function<Person, String>() {
                    @Override
                    public String apply(Person person) {
                        return person.getName();
                    }
                })
                .toList();
    }

    public static void main(String[] args) {
        List<Person> people = newArrayList(new Person("Wei", "China", 25), new Person("Tong", "China", 26), new Person("Nive", "India", 23), new Person("Tim", "USA", 56));
        People people1 = new People(people);
        System.out.println(people1.peopleFilter().get(0).getName());
        System.out.println(people1.getName(people).get(0));
        System.out.println(people1.chineseName(people).get(0));
    }
}
