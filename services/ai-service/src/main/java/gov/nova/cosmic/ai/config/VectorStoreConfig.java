package gov.nova.cosmic.ai.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "cosmic.ai.vector")
public interface VectorStoreConfig {

    String host();

    int port();

    String collection();
}
