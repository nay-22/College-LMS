package com.lms.app.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class utils {
    public static SqlParameterSource getPaginationParams(int pageIndex, int pageSize) {
        return new MapSqlParameterSource()
        .addValue("limit", pageSize)
        .addValue("offset", pageIndex*pageSize);
    }
}
