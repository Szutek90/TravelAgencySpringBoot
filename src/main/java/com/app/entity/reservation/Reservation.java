package com.app.entity.reservation;

import com.app.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "countries")
@Entity
@Setter
@Getter
public class Reservation extends BaseEntity {
    protected Integer tourId;
    protected Integer agencyId;
    protected Integer customerId;
    protected Integer quantityOfPeople;
    protected Integer discount;
}
