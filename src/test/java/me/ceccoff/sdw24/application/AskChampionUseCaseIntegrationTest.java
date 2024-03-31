package me.ceccoff.sdw24.application;

import me.ceccoff.sdw24.domain.model.Champion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AskChampionUseCaseIntegrationTest {

    @Autowired
    private AskChampionUseCase askChampionUseCase;

    @Test
    void shouldAskChampionAndReturnString() {
        String askResponse = askChampionUseCase.askChampion(1L, "Qual seu nome?");

        assertEquals(askResponse, """
        Pergunta: Qual seu nome?
        Nome do campeão: Jinx
        Função: Atirador
        Lore (História): Uma criminosa impulsiva e manÃ­aca de Zaun, Jinx vive para disseminar o caos sem se preocupar com as consequÃªncias. Com um arsenal de armas mortais, ela detona as explosÃµes mais altas e mais luminosas para deixar um rastro de destruiÃ§Ã£o e pÃ¢nico por onde passa. Jinx abomina o tÃ©dio e deixa alegremente sua marca caÃ³tica de pandemÃ´nio aonde quer que vÃ¡.
        """);
    }

}