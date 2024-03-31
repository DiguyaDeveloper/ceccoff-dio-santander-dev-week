package me.ceccoff.sdw24.adapters.out;

import feign.RequestInterceptor;
import me.ceccoff.sdw24.domain.ports.GenerativeAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.core.env.Environment;

import java.util.List;

@FeignClient(name = "openAiChatGptApi", url = "${openai.base-url}", configuration = OpenAiChatGptApi.Config.class)
public interface OpenAiChatGptApi extends GenerativeAiApi {


    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResponse chatCompletion(OpenAiChatCompletionRequest request);

    @Override
    default String generateContent(String objective, String context) {
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", objective)
        );

        OpenAiChatCompletionRequest request = new OpenAiChatCompletionRequest("gpt-3.5-turbo", messages);

        OpenAiChatCompletionResponse response = chatCompletion(request);

        return response.choices().getFirst().message().content();
    }

    record OpenAiChatCompletionRequest(String model, List<Message> messages) {}
    record Message(String role, String content) {}

    record OpenAiChatCompletionResponse(List<Choice> choices) {}
    record Choice(Message message) {}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
