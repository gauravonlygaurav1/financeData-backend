package com.example.financeData.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long id;

    @Column(nullable= false, length= 50)
    private String name;

    @Column(nullable= false, unique= true)
    private String email;

    @Column(nullable= false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;
}
