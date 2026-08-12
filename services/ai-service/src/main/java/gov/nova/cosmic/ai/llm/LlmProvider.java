package gov.nova.cosmic.ai.llm;

import java.util.List;

public interface LlmProvider {

    String complete(String systemPrompt, List<String> messages);

    float[] embed(String text);
}
