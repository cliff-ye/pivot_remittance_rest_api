package com.wellTech.pivotbank.entity;


import com.wellTech.pivotbank.dto.AccountDetailsDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.security.cert.CertPathBuilder;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 2)
    private String firstName;
    @NotNull
    @Size(min = 2)
    private String lastName;
    private String otherName;
    @NotNull
    private String gender;
    @NotNull
    private String address;
    @NotNull
    private String countryOfOrigin;
    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal accountBalance;
    @Email
    private String email;
    @NotNull
    @Size(min=10)
    private String phoneNumber;
    private String otherPhoneNumber;
    @NotNull
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
