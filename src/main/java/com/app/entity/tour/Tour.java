package com.app.entity.tour;

import com.app.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tours")
@Entity
@Setter
@Getter
public class Tour extends BaseEntity {
    protected Integer agencyId;
    protected Integer countryId;
    protected BigDecimal pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;
}