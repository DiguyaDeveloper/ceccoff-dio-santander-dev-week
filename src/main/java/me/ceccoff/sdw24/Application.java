package me.ceccoff.sdw24;

import me.ceccoff.sdw24.adapters.out.OpenAiChatGptApi;
import me.ceccoff.sdw24.application.AskChampionUseCase;
import me.ceccoff.sdw24.application.ListChampionsUseCase;
import me.ceccoff.sdw24.domain.ports.ChampionsRepository;
import me.ceccoff.sdw24.domain.ports.GenerativeAiApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}

	@Bean
	public AskChampionUseCase provideAskChampionsUseCase(ChampionsRepository repository, GenerativeAiApi generativeAiApi) {
		return new AskChampionUseCase(repository, generativeAiApi);
	}

}
