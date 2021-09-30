package com.lastcoffee.subjectbitcoin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableScheduling
//@RetrofitScan("com.lastcoffee.subjectbitcoin.http")
class SubjectBitCoinApplication

fun main(args: Array<String>) {
    runApplication<SubjectBitCoinApplication>(*args)
}
