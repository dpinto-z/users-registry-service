package com.users.registry.service.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "userId", updatable = false, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String token;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdDate;
    @CreationTimestamp
    private Date modifiedDate;
    @CreationTimestamp
    private Date lastLoginDate;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phoneList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
