package com.lms.app.query;

public class CourseQuery {
    public static final String INSERT_INTO_COURSE_QUERY = """
            INSERT INTO Courses (name, thumbnail, semester) VALUES (:name, :thumbnail, :semester) 
            """;;
    public static final String SELECT_ALL_FROM_COURSE_QUERY = """
            SELECT * FROM Courses LIMIT :limit OFFSET :offset
            """;

    public static final String SELECT_COURSE_WHERE_ID_QUERY = """
            SELECT * FROM Courses WHERE id = :id
            """;

    public static final String UPDATE_COURSE_QUERY = """
            UPDATE Courses SET 
                name = :name,
                thumbnail = :thumbnail,
                semester = :semester,
                last_updated_at = : lastUpdatedAt
            WHERE id = :id
            """;

    public static final String DELETE_COURSE_QUERY = "DELETE FROM Courses WHERE id = :id";
}
