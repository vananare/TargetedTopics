package ttl.larku.demo;

import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author whynot
 */
public class Checker {
    public static void main(String[] args) {
        new Checker().go();
    }

    public void go() {
        StudentService ss = new StudentService();
        init(ss);

        List<Student> students = ss.getAllStudents();

        List<Student> withM = getByName(students, "M");

        //withM.forEach(System.out::println);

        NameStartsWithM c1 = new NameStartsWithM();

        Checkit c2 = new Checkit() {
            public boolean check(Student s) {
                return s.getStatus() == Student.Status.PART_TIME;
            }
        };

        Checkit c3 = (Student s) -> {
            return s.getStatus() == Student.Status.PART_TIME;
        };

        Checkit c4 = s -> s.getStatus() == Student.Status.PART_TIME;
        Predicate<Student>  c5 = s -> s.getStatus() == Student.Status.PART_TIME;

        List<Student> withMYuch = check(students, new Checkit() {
            public boolean check(Student s) {
                return s.getStatus() == Student.Status.PART_TIME;
            }
        });

        List<Student> withMYuch2 = filter(students, new Predicate<Student>() {
            public boolean test(Student s) {
                return s.getStatus() == Student.Status.PART_TIME;
            }
        });

        List<Student> withM2 = filter(students, c5);

        List<Student> withM3 = filter(students, s -> s.getStatus() == Student.Status.FULL_TIME);



        withM3.forEach(System.out::println);

    }


    public List<Student> filter(List<Student> input, Predicate<Student> checker) {
        List<Student> result = new ArrayList<>();
        for (Student s : input) {
            if (checker.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> check(List<Student> input, Checkit checker) {
        List<Student> result = new ArrayList<>();
        for (Student s : input) {
            if (checker.check(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> getByName(List<Student> input, String prefix) {
        List<Student> result = new ArrayList<>();
        for (Student s : input) {
            if (s.getName().startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> getByPhoneNumber(List<Student> input, String prefix) {
        List<Student> result = new ArrayList<>();
        for (Student s : input) {
            if (s.getPhoneNumber().startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
    }


    public interface Checkit {
        public boolean check(Student s);
    }

    public class NameStartsWithM implements Checkit {
        @Override
        public boolean check(Student s) {
            return s.getName().startsWith("M");
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
