package com.axonstech.tutorial.dao;

import com.axonstech.tutorial.entity.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TutorialDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Tutorial> getTutorialByCondition(Tutorial tutorial) {
//        List<Tutorial> tutorials = jdbcTemplate.queryForList("select * from tutorial", Tutorial.class);
        List<Tutorial> tutorials = jdbcTemplate.query("select * from tutorial", new BeanPropertyRowMapper(Tutorial.class));
        System.out.println(tutorials);
        return tutorials;
    }


    public Page<Tutorial> findTutorialByPage(Pageable pageable) {
        String rowCountSql = "SELECT count(1) AS row_count " + "FROM tutorial ";

        String querySql = "SELECT *  FROM tutorial ";

        return findByPage(rowCountSql, querySql, pageable, Tutorial.class);
    }

    public <T> Page<T> findByPage(String rowCountSql, String querySql, Pageable pageable, Class<T> clazz) {
        int total = jdbcTemplate.queryForObject(rowCountSql, Integer.class);
        String sql = querySql + " LIMIT " + pageable.getPageSize() + " " + " OFFSET " + pageable.getOffset();
        List<T> demos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(clazz));
        return new PageImpl<T>(demos, pageable, total);
    }
}
