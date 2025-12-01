package com.vinhthanh2.lophocdientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lop", schema = "school")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String ten;

    @Column
    private String hinhAnh;

    @Transient
    private Truong truong;

    @Transient
    private User giaoVien;
}
