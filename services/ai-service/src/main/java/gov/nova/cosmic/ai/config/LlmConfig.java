package gov.nova.cosmic.ai.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "cosmic.ai.llm")
public interface LlmConfig {

    String provider();

    String apiKey();

    String model();
}
