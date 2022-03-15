package com.academy.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String email;

    @Column
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registered_at")
    private Date registeredAt;

    @ManyToMany
    @JoinTable(
        name = "customer_address",
        joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private Set<Address> addresses = new HashSet<>();
}
