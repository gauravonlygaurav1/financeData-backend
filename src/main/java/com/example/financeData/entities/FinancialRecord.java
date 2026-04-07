package com.example.financeData.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name= "financial_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false)
    @NotNull
    @Positive
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RecordType type;

    @Column(nullable= false)
    @NotBlank
    private String category;

    private LocalDate localDate;
    @PrePersist
    public void prePersist() {
        if(this.localDate == null){
            this.localDate = LocalDate.now();
        }
    }
    @PreUpdate
    public void preUpdate() {
        if (this.localDate == null) {
            this.localDate = LocalDate.now();
        }
    }

    private String description;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User createdBy;
}
