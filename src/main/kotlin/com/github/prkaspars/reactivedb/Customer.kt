package com.github.prkaspars.reactivedb

import org.springframework.data.annotation.Id

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

data class Customer(
    @Id
    var id: Long,
    var firstName: String,
    var lastName: String
) {
    constructor(firstName: String, lastName: String) : this(0, firstName, lastName)
}

interface CustomerRepository : ReactiveCrudRepository<Customer, Long> {
    @Query("SELECT * FROM customer WHERE last_name = :lastname")
    fun findByLastName(lastName: String): Flux<Customer>
}
