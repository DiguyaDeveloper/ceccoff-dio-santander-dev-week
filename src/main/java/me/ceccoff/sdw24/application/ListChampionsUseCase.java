package me.ceccoff.sdw24.application;

import me.ceccoff.sdw24.domain.model.Champion;
import me.ceccoff.sdw24.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    public List<Champion> findAll() {
        return repository.findAll();
    }
}
