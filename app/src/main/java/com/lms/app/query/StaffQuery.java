package com.lms.app.query;

public class StaffQuery {
    public static final String SELECT_ALL_FROM_STAFF_QUERY = """
                        SELECT * FROM Staff LIMIT :limit OFFSET :offset
                        """;
        public static final String INSERT_INTO_STAFF_QUERY = """
                        INSERT INTO Staff (emp_no, first_name, last_name, email, password) VALUES (
                            :empNo,
                            :firstName,
                            :lastName,
                            :email,
                            :password
                        )
                        """;
        public static final String UPDATE_STAFF_PROFILE_QUERY = """
                        UPDATE Staff SET
                            first_name = :firstName,
                            middle_name = :middleName,
                            last_name = :lastName,
                            phone = :phone,
                            dob = :dob,
                            address = :address
                        WHERE id = :id
                        """;
        public static final String SELECT_STAFF_WHERE_ID_QUERY = "SELECT * FROM Staff WHERE id = :id";

        public static final String DELETE_STAFF_QUERY = "DELETE FROM Staff WHERE id = :id";

        public static final String COUNT_USER_EMAIL_QUERY = """
                        SELECT COUNT(*) FROM Staff WHERE email = :email
                        """;
}
