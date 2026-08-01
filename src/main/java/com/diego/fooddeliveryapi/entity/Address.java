package com.diego.fooddeliveryapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Column(name = "delivery_street", nullable = false, length = 150)
    private String street;

    @Column(name = "delivery_number", nullable = false, length = 20)
    private String number;

    @Column(name = "delivery_neighborhood", nullable = false, length = 100)
    private String neighborhood;

    @Column(name = "delivery_city", nullable = false, length = 100)
    private String city;

    @Column(name = "delivery_state", nullable = false, length = 2)
    private String state;

    @Column(name = "delivery_zip_code", nullable = false, length = 9)
    private String zipCode;

    @Column(name = "delivery_complement", length = 100)
    private String complement;
}
