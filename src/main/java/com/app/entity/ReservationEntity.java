package com.app.entity;

import com.app.dto.ReservationDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "countries")
@Entity
@Setter
@Getter
public class ReservationEntity extends BaseEntity {
    protected Integer tourId;
    protected Integer agencyId;
    protected Integer customerId;
    protected Integer quantityOfPeople;
    protected Integer discount;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "components",
            joinColumns = @JoinColumn(name = "reservation_id",
                    referencedColumnName = "id"))
    @Column(name = "component")
    @Enumerated(EnumType.STRING)
    private List<ReservationComponent> components = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tour_id", unique = true)
    private TourEntity tourEntity;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "reservations_persons",
            joinColumns = {@JoinColumn(name = "reservation_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    @Builder.Default
    private List<PersonEntity> personEntities = new ArrayList<>();

    public ReservationDto toReservationDto() {
        return new ReservationDto(tourEntity.getId(), tourEntity.getTravelAgencyEntity().getName(),
                personEntities.get(customerId).toPersonDto(), quantityOfPeople, discount, components);
    }

}
