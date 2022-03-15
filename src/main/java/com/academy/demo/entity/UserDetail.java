package com.academy.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user_details")
public class UserDetail {

    @Id
    @Column(name = "user_id")
    private Integer id;

    @Column
    private String address;

    @Column
    private Integer age;

    @Column(name = "phone_number")
    private String phoneNumber;

    @MapsId
    @OneToOne
    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;
}
