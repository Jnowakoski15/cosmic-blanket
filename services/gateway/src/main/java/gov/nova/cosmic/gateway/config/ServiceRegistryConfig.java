package gov.nova.cosmic.gateway.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "cosmic.services")
public interface ServiceRegistryConfig {

    @WithName("licensing")
    ServiceConfig licensing();

    @WithName("vital-records")
    ServiceConfig vitalRecords();

    @WithName("property-tax")
    ServiceConfig propertyTax();

    @WithName("ai")
    ServiceConfig ai();

    interface ServiceConfig {
        String url();
    }
}
