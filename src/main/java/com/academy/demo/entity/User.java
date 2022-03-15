package com.academy.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.TABLE | GenerationType.SEQUENCE | GenerationType.IDENTITY
    private Integer id;

    // select nextval() from sequence_table;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String username;

    @Column
    private String password;

    @Temporal(value = TemporalType.TIMESTAMP) // TemporalType.TIMESTAMP | TemporalType.TIME
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL) // LAZY
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // EAGER
    private UserDetail userDetail;


    public void addRole(Role role) {
        this.getRoles().add(role);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
    }

    public void removeByRoleId(int roleId)
    {
        Set<Role> roles = this.getRoles(); // select (relation = LAZY)
        for (Role role : roles)
        {
            if (role.getId().equals(roleId))
            {
                roles.remove(role);
                break;
            }
        }
    }

    public void syncDetails()
    {
        if (this.getUserDetail() != null) {
            this.getUserDetail().setUser(this);
        }
    }
}
