package org.loose.vvs.mocking;

import java.util.List;

public class StudentService {

    private StudentProvider studentProvider;

    /**
     * @param n the nth student that you want in the order the studentProvider gives them
     * @return the first name of the nth student in uppercase characters
     */
    public String getFirstNameOfNthStudentInUppercase(int n) {
        List<String> studentNames = studentProvider.getStudentNames();
        if (studentNames.size() <= n) {
            throw new NoSuchStudentException();
        }

        String studentFullName = studentNames.get(n);

        String[] nameParts = studentFullName.split(" ");
        if (nameParts.length != 2) {
            throw new NameFormatException();
        }

        return nameParts[0].toUpperCase();
    }

    /**
     * @param name   the name of the students you want to update
     * @param newAge the new age of the students
     * @return the number of students that were updated
     */
    public int updateTheAgeOfAllTheStudentsWithName(String name, int newAge) {
        int updatedStudents = 0;

        for (Student student : studentProvider.getAllStudents()) {
            if (student.getName().equals(name)) {
                updatedStudents++;
                studentProvider.replaceStudent(new Student(name, newAge));
            }
        }

        if (updatedStudents == 0) {
            throw new NoSuchStudentException();
        }

        return updatedStudents;
    }

    /**
     * @param possibleNames the names of the students you want to get
     * @return the average age of the students with the given names
     */
    public double getTheAverageAgeOfTheStudentThatHaveTheName(List<String> possibleNames) {
        int totalAge = 0;
        int numberOfStudents = 0;

        for (String name : possibleNames) {
            Student student = studentProvider.getStudentByName(name);

            if (student == null) {
                continue;
            }

            totalAge += student.getAge();
            numberOfStudents++;
        }

        if (numberOfStudents == 0) {
            throw new NoSuchStudentException();
        }

        return (double) totalAge / numberOfStudents;

    }

    public void setStudentProvider(StudentProvider studentProvider) {
        this.studentProvider = studentProvider;
    }
}
