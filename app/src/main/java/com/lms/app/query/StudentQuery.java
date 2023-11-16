package com.lms.app.query;

public class StudentQuery {
        public static final String SELECT_ALL_FROM_STUDENT_QUERY = """
                        SELECT * FROM Student LIMIT :limit OFFSET :offset
                        """;
        public static final String INSERT_INTO_STUDENT_QUERY = """
                        INSERT INTO Student (semester, roll_no, first_name, last_name, email, password) VALUES (
                            :semester,
                            :roll_no,
                            :firstName,
                            :lastName,
                            :email,
                            :password
                        )
                        """;
        public static final String UPDATE_STUDENT_PROFILE_QUERY = """
                        UPDATE Student SET
                            first_name = :firstName,
                            middle_name = :middleName,
                            last_name = :lastName,
                            phone = :phone,
                            dob = :dob,
                            address = :address
                        WHERE id = :id
                        """;
        public static final String SELECT_STUDENT_WHERE_ID_QUERY = "SELECT * FROM Student WHERE id = :id";

        public static final String DELETE_STUDENT_QUERY = "DELETE FROM Student WHERE id = :id";

        public static final String COUNT_USER_EMAIL_QUERY = """
                        SELECT COUNT(*) FROM Student WHERE email = :email
                        """;
}
