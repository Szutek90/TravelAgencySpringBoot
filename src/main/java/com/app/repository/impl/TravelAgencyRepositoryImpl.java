package com.app.repository.impl;

import com.app.converter.agencies.FileToAgenciesConverter;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.agency.TravelAgencyEntityMapper;
import com.app.repository.TravelAgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TravelAgencyRepositoryImpl implements TravelAgencyRepository {
    private final List<TravelAgencyEntity> travelAgencies = new ArrayList<>();
    private final ApplicationContext context;

    @Value("${agencies.file}")
    private String filename;

    @Value("${agencies.format}")
    private String format;

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToAgenciesConverterImpl".formatted(format),
                FileToAgenciesConverter.class);
        if (travelAgencies.isEmpty()) {
            travelAgencies.addAll(converter.convert(filename));
        }
    }

    @Override
    public Optional<TravelAgencyEntity> findById(int id) {
        return travelAgencies.stream()
                .filter(t -> TravelAgencyEntityMapper.toId.applyAsInt(t) == id)
                .findFirst();
    }

    @Override
    public Optional<TravelAgencyEntity> findByName(String name) {
        return travelAgencies.stream()
                .filter(t -> Objects.equals(TravelAgencyEntityMapper.toName.apply(t), name))
                .findFirst();
    }

    @Override
    public List<TravelAgencyEntity> findByCity(String city) {
        return travelAgencies.stream()
                .filter(t -> Objects.equals(TravelAgencyEntityMapper.toCity.apply(t), city))
                .toList();
    }

    @Override
    public List<TravelAgencyEntity> getAll() {
        return travelAgencies;
    }

    @Override
    public int save(TravelAgencyEntity travelAgencyEntity) {
        this.travelAgencies.add(travelAgencyEntity);
        return TravelAgencyEntityMapper.toId.applyAsInt(travelAgencyEntity);
    }

    @Override
    public List<TravelAgencyEntity> saveAll(List<TravelAgencyEntity> travelAgencies) {
        var currentIds = generateIds(getAll());
        for (var agency : travelAgencies) {
            if (currentIds.contains(TravelAgencyEntityMapper.toId.applyAsInt(agency))) {
                throw new IllegalArgumentException("Duplicate id: " + agency);
            }
        }
        this.travelAgencies.addAll(travelAgencies);
        return travelAgencies;
    }

    @Override
    public TravelAgencyEntity delete(int id) {
        var agencyToDelete = findById(id)
                .orElseThrow(() -> new RuntimeException("TravelAgency not found"));
        travelAgencies.remove(agencyToDelete);
        return agencyToDelete;
    }

    private List<Integer> generateIds(List<TravelAgencyEntity> travelAgencies) {
        return travelAgencies.stream()
                .map(TravelAgencyEntityMapper.toId::applyAsInt)
                .toList();
    }
}
