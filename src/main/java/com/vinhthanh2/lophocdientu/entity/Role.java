package com.vinhthanh2.lophocdientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles", schema = "auth")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Transient
    private List<String> permissions;
}
