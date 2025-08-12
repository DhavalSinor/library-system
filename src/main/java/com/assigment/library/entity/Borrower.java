package com.assigment.library.entity;

import com.assigment.library.baseclasses.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Borrower entity with unique email constraint.
 */
@Entity
@Table(name = "borrowers", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Getter @Setter
public class Borrower extends AbstractBaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
}
