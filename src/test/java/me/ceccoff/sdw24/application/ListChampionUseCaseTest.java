package me.ceccoff.sdw24.application;

import me.ceccoff.sdw24.domain.model.Champion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ListChampionUseCaseTest {

    @Autowired
    private ListChampionsUseCase listChampionsUseCase;

    @Test
    void findAll() {
        List<Champion> champions = listChampionsUseCase.findAll();

        assertEquals(champions.size(), 12);
    }

    @Test
    void repository() {
    }
}