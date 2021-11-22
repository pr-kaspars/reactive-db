package com.github.prkaspars.reactivedb

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.Duration
import java.util.*


@SpringBootApplication
class ReactivedbApplication {
    private val log: Logger = LoggerFactory.getLogger(ReactivedbApplication::class.java)

    @Bean
    fun demo(repository: CustomerRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String> ->
            // save a few customers
            repository.saveAll(
                Arrays.asList(
                    Customer("Jack", "Bauer"),
                    Customer("Chloe", "O'Brian"),
                    Customer("Kim", "Bauer"),
                    Customer("David", "Palmer"),
                    Customer("Michelle", "Dessler")
                )
            )
                .blockLast(Duration.ofSeconds(10))

            // fetch all customers
            log.info("Customers found with findAll():")
            log.info("-------------------------------")
            repository.findAll()
                .doOnNext { customer: Customer -> log.info(customer.toString()) }
                .blockLast(Duration.ofSeconds(10))
            log.info("")

            // fetch an individual customer by ID
            repository.findById(1L).doOnNext { customer: Customer ->
                log.info("Customer found with findById(1L):")
                log.info("--------------------------------")
                log.info(customer.toString())
                log.info("")
            }.block(Duration.ofSeconds(10))


            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):")
            log.info("--------------------------------------------")
            repository.findByLastName("Bauer").doOnNext { bauer: Customer ->
                log.info(
                    bauer.toString()
                )
            }.blockLast(Duration.ofSeconds(10))
            log.info("")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ReactivedbApplication>(*args)
}
