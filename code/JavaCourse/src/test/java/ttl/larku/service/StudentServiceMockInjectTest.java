package ttl.larku.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceMockInjectTest {

	@Spy
	private InMemoryStudentDAO dao;

	@InjectMocks
	private StudentService studentService;

	private Student getStudent;
	private Student newStudent;
	private int goodId = 1;
	private int badId = 1000;
	
	private List<Student> students;

	@Before
	public void setup() {
		dao.createStore();
		getStudent = new Student("Joe", "838 939 0202", Student.Status.FULL_TIME);
		getStudent.setId(goodId);
		newStudent = new Student("Sammy", "928 749 0303", Student.Status.PART_TIME);
		newStudent.setId(goodId+1);
		
		studentService.createStudent(getStudent);
		studentService.createStudent(newStudent);
	}
	
	
	@Test
	public void getStudentGood() throws Exception{
		int idToTest = 1;
		Student result = studentService.getStudent(idToTest);
		assertEquals("Joe", result.getName());
	}

	@Test
	public void testGetStudentWithBadId() {
		int idToTest = 14;
		Student student = studentService.getStudent(idToTest);
		
		assertTrue("Student Should Not be Null", student == null);
	}

	@Test
	public void testCreateStudent() {
		Student student = studentService.createStudent("Sammy", "982 749 0033", Student.Status.HIBERNATING);

		assertNotEquals(0, student.getId());
		Mockito.verify(dao, Mockito.atLeast(1)).create(Mockito.any(Student.class));
	}

	@Test
	public void testCreateStudentWithStudent() {
		Student student = studentService.createStudent(newStudent);
		
		assertNotEquals(0, student.getId());
		Mockito.verify(dao, Mockito.atLeast(1)).create(Mockito.any(Student.class));
	}

	@Test
	public void deleteGoodStudent() {
		studentService.deleteStudent(goodId);
		
		Mockito.verify(dao).get(goodId);
		Mockito.verify(dao).delete(getStudent);
	}

	@Test
	public void deleteStudentWithBadId() {
		studentService.deleteStudent(badId);
		
		Mockito.verify(dao).get(badId);
	}

	@Test
	public void testUpdateStudent() {
		Student student = studentService.getStudent(goodId);
		studentService.updateStudent(student);
		
		Mockito.verify(dao).update(student);
	}

	@Test
	public void testGetAll() {
		List<Student> students = studentService.getAllStudents();
		
		assertEquals(2, students.size());
		Mockito.verify(dao).getAll();
	}
}
