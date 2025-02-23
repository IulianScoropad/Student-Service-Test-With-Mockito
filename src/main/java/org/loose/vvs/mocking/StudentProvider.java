package org.loose.vvs.mocking;

import java.util.List;

public interface StudentProvider {
    /**
     * @return A list of strings that represent the names of the students in the following format: "FirstName LastName"
     * ex: John Doe, Jane Sandro, Emily Smith
     * ATTENTION: there are no middle names, each name contains only first name and last name separated by one space.
     */
    List<String> getStudentNames();

    /**
     * @return A list of all students
     */
    List<Student> getAllStudents();

    /**
     * @param name the name of the student you want to get
     * @return the student with the given name
     */
    Student getStudentByName(String name);

    /**
     * @param student the student you want to replace
     */
    void replaceStudent(Student student);
}
