package ttl.larku.demo;

import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import javax.naming.NameAlreadyBoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author whynot
 */
public class Sorting {
    public static void main(String[] args) {
        new Sorting().go();
    }

    public void go() {
        StudentService ss = new StudentService();
        init(ss);

        List<Student> students = ss.getAllStudents();
        Collections.sort(students);

        NameComparator nc = new NameComparator();

        Comparator<Student> ncAnon = new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().compareTo(s2.getName());
            }
        };

        Comparator<Student> ncLambda1 = (Student s1, Student s2) -> {
            return s1.getName().compareTo(s2.getName());
        };

        //Don't always need Argument types
        Comparator<Student> ncLambda2 = (s1, s2) -> {
            return s1.getName().compareTo(s2.getName());
        };

        //No braces necessary if only 1 statement
        Comparator<Student> ncLambda3 = (s1, s2) -> s1.getName().compareTo(s2.getName());

        Collections.sort(students, ncLambda3);

        Collections.sort(students, (s1, s2) -> s1.getName().compareTo(s2.getName()));

        //StatusComparator sc = new StatusComparator();
        //Collections.sort(students, sc);

//        for (Student s : students) {
//            System.out.println(s);
//        }

        Consumer<Student> cs = new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                System.out.println(student);
            }
        };

        //students.forEach(cs);

        //students.forEach((s) -> System.out.println(s));
        //students.forEach(s -> System.out.println(s));

        students.forEach(System.out::println);
        students.forEach(Sorting::ourPrint);
    }

    public static void ourPrint(Student s) {
        System.out.println(" { " + s + " } ");
    }

    class NameComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getName().compareTo(s2.getName());
        }
    }

    class StatusComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getStatus().compareTo(s2.getStatus());
        }
    }

    public static void init(StudentService ss) {
        ((InMemoryStudentDAO) ss.getStudentDAO()).createStore();
        ss.createStudent("Manoj", "282 939 9944", Student.Status.FULL_TIME);
        ss.createStudent("Charlene", "282 898 2145", Student.Status.FULL_TIME);
        ss.createStudent("Firoze", "228 678 8765", Student.Status.HIBERNATING);
        ss.createStudent("Joe", "3838 678 3838", Student.Status.PART_TIME);
    }
}
