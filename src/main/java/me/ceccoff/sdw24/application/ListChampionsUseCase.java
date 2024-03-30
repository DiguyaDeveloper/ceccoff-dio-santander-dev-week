package me.ceccoff.sdw24.application;

import me.ceccoff.sdw24.domain.model.Champions;
import me.ceccoff.sdw24.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    public List<Champions> findAll() {
        return repository.findAll();
    }
}
