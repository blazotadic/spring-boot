package com.academy.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // @RequiredArgsConstructor
@Entity
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column(name = "short_code")
    private String shortCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "country")
    @ToString.Exclude // LAZY
    private List<City> cities = new ArrayList<>();

}
