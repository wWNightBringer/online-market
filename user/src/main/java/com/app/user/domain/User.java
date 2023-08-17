package com.app.user.domain;

import com.app.common.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "public", name = "users")
@Builder
@Where(clause = "is_deleted = false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
    @Enumerated(EnumType.STRING)
    private Role role;
}
