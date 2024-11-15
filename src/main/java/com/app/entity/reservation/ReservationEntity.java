package com.app.entity.reservation;

import com.app.entity.BaseEntity;
import com.app.entity.ReservationComponent;
import com.app.entity.person.PersonEntity;
import com.app.entity.tour.TourEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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
    List<ReservationComponent> components;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tour_id", unique = true)
    private TourEntity tourEntity;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "reservations_persons",
            joinColumns = {@JoinColumn(name = "reservation_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    @Builder.Default
    private List<PersonEntity> personEntities = new ArrayList<>();

}
