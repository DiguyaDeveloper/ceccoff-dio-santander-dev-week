package me.ceccoff.sdw24.adapters.out;

import feign.FeignException;
import feign.RequestInterceptor;
import me.ceccoff.sdw24.domain.ports.GenerativeAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name = "openAiChatGptApi", url = "${openai.base-url}", configuration = OpenAiChatGptApi.Config.class)
public interface OpenAiChatGptApi extends GenerativeAiApi {


    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResponse chatCompletion(OpenAiChatCompletionRequest request);

    @Override
    default String generateContent(String objective, String context) {
        List<Message> messages = List.of(new Message("system", objective), new Message("user", objective));

        OpenAiChatCompletionRequest request = new OpenAiChatCompletionRequest("gpt-3.5-turbo", messages);

        try {
            OpenAiChatCompletionResponse response = chatCompletion(request);

            return response.choices().getFirst().message().content();
        } catch (FeignException httpErrors) {
            httpErrors.printStackTrace();
            return "Deu ruim! Erro de comunicação com a API do Chatgpt";
        } catch (Exception unexpectedError) {
            unexpectedError.printStackTrace();
            return "Deu mais ruim ainda! O Retorno da API do Chatgpt não contem os dados esperados";
        }
    }

    record OpenAiChatCompletionRequest(String model, List<Message> messages) {
    }

    record Message(String role, String content) {
    }

    record OpenAiChatCompletionResponse(List<Choice> choices) {
    }

    record Choice(Message message) {
    }

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
