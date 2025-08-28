package com.users.registry.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="phone")
public class Phone {

    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name="phoneId", updatable = false, nullable = false)
    private String phoneId;
    private String number;
    private String cityCode;
    private String countryCode;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User users;
}
