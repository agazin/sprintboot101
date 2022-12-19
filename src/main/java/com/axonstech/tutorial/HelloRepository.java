package com.axonstech.tutorial;

import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {
    public String hello() {
        return "HELLO from repository";
    }
}
