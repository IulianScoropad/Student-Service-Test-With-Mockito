package org.loose.vvs.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    private StudentService studentService;
    private static final List<String> names = Arrays.asList("Ion Popescu","Catalin Ancuta","Vlad Ionete");
    private static final List<Student> students = Arrays.asList(new Student("Ion Popescu",20),
            new Student("Catalin Ancuta",21),new Student("Vlad Ionete",24));

    @BeforeEach
    void setUp()
    {
        studentService= new StudentService();
    }

    @Test
    void testGetFirstNameOfNthStudentInUppercase(@Mock StudentProvider studentProvider)
    {
        when(studentProvider.getStudentNames()).thenReturn(names);
        studentService.setStudentProvider(studentProvider);

        assertEquals("CATALIN",studentService.getFirstNameOfNthStudentInUppercase(1));
    }

    @Test
    void testGetFirstNameOfNthStudentInUppercaseWhenNIsBiggerThanListSize(@Mock StudentProvider studentProvider)
    {
        when(studentProvider.getStudentNames()).thenReturn(names);
        studentService.setStudentProvider(studentProvider);

        assertThrows(NoSuchStudentException.class, () -> studentService.getFirstNameOfNthStudentInUppercase(4));
    }

    @Test
    void testGetFirstNameOfNthStudentInUppercaseWhenNIsBiggerThanListSizeWhenFormatOfNamesIsWrong(@Mock StudentProvider studentProvider)
    {
        List<String> namesModified = Arrays.asList("Ion Popescu","Catalin","Vlad Ionete");
        when(studentProvider.getStudentNames()).thenReturn(namesModified);
        studentService.setStudentProvider(studentProvider);

        assertThrows(NameFormatException.class, () -> studentService.getFirstNameOfNthStudentInUppercase(1));
    }

    @Test
    void testGetTheAverageAgeOfTheStudentThatHasTheName(@Mock StudentProvider studentProvider)
    {
        for(int i =0;i<students.size();i++)
        {
            when(studentProvider.getStudentByName(students.get(i).getName())).thenReturn(students.get(i));
        }

        studentService.setStudentProvider(studentProvider);

        assertEquals(21.666666666666668,studentService.getTheAverageAgeOfTheStudentThatHaveTheName(names));
    }

    @Test
    void testGetTheAverageAgeOfTheStudentThatHasTheNameButNotFoundInTheList(@Mock StudentProvider studentProvider)
    {
        for(int i =0;i<students.size();i++)
        {
            when(studentProvider.getStudentByName(students.get(i).getName())).thenReturn(null);
        }

        studentService.setStudentProvider(studentProvider);

        assertThrows(NoSuchStudentException.class,() -> studentService.getTheAverageAgeOfTheStudentThatHaveTheName(names));
    }

    @Test
    void testUpdateAllStudentsWithName(@Mock StudentProvider studentProvider)
    {
        when(studentProvider.getAllStudents()).thenReturn(students);

        doNothing().when(studentProvider).replaceStudent(any(Student.class));

        studentService.setStudentProvider(studentProvider);

        assertEquals(1,studentService.updateTheAgeOfAllTheStudentsWithName("Vlad Ionete",25));
        verify(studentProvider,times(1)).replaceStudent(any(Student.class));
        verify(studentProvider,atLeastOnce()).getAllStudents();
    }

    @Test
    void testUpdateAllStudentsWithNameWhenTheStudentIsNotFound(@Mock StudentProvider studentProvider)
    {
        when(studentProvider.getAllStudents()).thenReturn(students);

        studentService.setStudentProvider(studentProvider);
        
        assertThrows(NoSuchStudentException.class,()->studentService.updateTheAgeOfAllTheStudentsWithName("Andrei Ionescu",26));
        verify(studentProvider, never()).replaceStudent(any(Student.class));
        verify(studentProvider,atLeastOnce()).getAllStudents();
    }




}
