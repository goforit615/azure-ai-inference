package com.github.models.inference.samples;

import com.azure.ai.inference.ModelServiceVersion;
import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestAssistantMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.ai.inference.models.StreamingChatCompletionsUpdate;
import com.azure.ai.inference.models.StreamingChatResponseMessageUpdate;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.IterableStream;

import java.util.ArrayList;
import java.util.List;

public final class StreamingChatSample {
    /**
     * @param args Unused. Arguments to the program.
     */
    public static void main(String[] args) {
        String endpoint = "https://models.github.ai/inference";
        String model = "gpt-4.1";

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential("<API_KEY>"))
                .endpoint(endpoint)

                .buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant."));
        chatMessages.add(new ChatRequestUserMessage("I am going to Paris, what should I see?"));
        chatMessages.add(new ChatRequestAssistantMessage("Paris, the capital of France, is known for its stunning architecture, art museums, historical landmarks, and romantic atmosphere. Here are some of the top attractions to see in Paris:\n \n 1. The Eiffel Tower: The iconic Eiffel Tower is one of the most recognizable landmarks in the world and offers breathtaking views of the city.\n 2. The Louvre Museum: The Louvre is one of the world's largest and most famous museums, housing an impressive collection of art and artifacts, including the Mona Lisa.\n 3. Notre-Dame Cathedral: This beautiful cathedral is one of the most famous landmarks in Paris and is known for its Gothic architecture and stunning stained glass windows.\n \n These are just a few of the many attractions that Paris has to offer. With so much to see and do, it's no wonder that Paris is one of the most popular tourist destinations in the world."));
        chatMessages.add(new ChatRequestUserMessage("What is so great about #1?"));

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
        chatCompletionsOptions.setMaxTokens(800);
        chatCompletionsOptions.setTemperature(1d);
        chatCompletionsOptions.setTopP(1d);
        chatCompletionsOptions.setFrequencyPenalty(0d);
        chatCompletionsOptions.setPresencePenalty(0d);
        chatCompletionsOptions.setModel(model);

        IterableStream<StreamingChatCompletionsUpdate> chatCompletionsStream = client.completeStream(
                chatCompletionsOptions);

        chatCompletionsStream
                .stream()
                .forEach(chatCompletions -> {
                    if (CoreUtils.isNullOrEmpty(chatCompletions.getChoices())) {
                        return;
                    }

                    StreamingChatResponseMessageUpdate delta = chatCompletions.getChoices().get(0).getDelta();

                    if (delta.getRole() != null) {
                        System.out.println("Role = " + delta.getRole());
                    }

                    if (delta.getContent() != null) {
                        String content = delta.getContent();
                        System.out.print(content);
                    }
                });

    }
}