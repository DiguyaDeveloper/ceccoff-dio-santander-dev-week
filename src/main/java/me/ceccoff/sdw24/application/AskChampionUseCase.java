package me.ceccoff.sdw24.application;

import me.ceccoff.sdw24.domain.exception.ChampionNotFoundException;
import me.ceccoff.sdw24.domain.model.Champion;
import me.ceccoff.sdw24.domain.ports.ChampionsRepository;

public record AskChampionUseCase(ChampionsRepository repository) {

    public String askChampion(Long championId, String question) {

        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        return championContext;
    }
}
