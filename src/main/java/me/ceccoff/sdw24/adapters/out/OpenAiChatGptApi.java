package me.ceccoff.sdw24.adapters.out;

import me.ceccoff.sdw24.domain.ports.GenerativeAiApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "openAiChatGptApi", url = "${openai.base-url}")
public interface OpenAiChatGptApi extends GenerativeAiApi {


    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResponse chatCompletion(OpenAiChatCompletionRequest request);

    @Override
    default String generateContent(String objective, String context) {
        String model = "${openai.model}";

        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", objective)
        );
        OpenAiChatCompletionRequest request = new OpenAiChatCompletionRequest(model, messages);

        OpenAiChatCompletionResponse response = chatCompletion(request);

        return response.choices().getFirst().message().content();
    }

    record OpenAiChatCompletionRequest(String model, List<Message> messages) {}
    record Message(String role, String content) {}

    record OpenAiChatCompletionResponse(List<Choice> choices) {}
    record Choice(Message message) {}
}
