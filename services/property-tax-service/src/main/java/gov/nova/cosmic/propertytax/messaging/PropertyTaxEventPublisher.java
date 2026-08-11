package gov.nova.cosmic.propertytax.messaging;

import gov.nova.cosmic.common.event.DocumentChangedEvent;
import gov.nova.cosmic.common.event.ServiceNotificationEvent;
import gov.nova.cosmic.propertytax.entity.Property;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class PropertyTaxEventPublisher {

    @Channel("documents-changed")
    Emitter<DocumentChangedEvent> documentEmitter;

    @Channel("service-notifications")
    Emitter<ServiceNotificationEvent> notificationEmitter;

    public void publishPropertyRegistered(Property property) {
        DocumentChangedEvent event = new DocumentChangedEvent(
                "property-tax",
                "PROPERTY",
                property.id,
                DocumentChangedEvent.Action.CREATED,
                "Property " + property.parcelNumber,
                "Address: " + property.addressLine1 + ", " + property.city
                        + ", Owner: " + property.ownerName
        );
        documentEmitter.send(event);
    }

    public void publishPropertyUpdated(Property property) {
        DocumentChangedEvent event = new DocumentChangedEvent(
                "property-tax",
                "PROPERTY",
                property.id,
                DocumentChangedEvent.Action.UPDATED,
                "Property " + property.parcelNumber,
                "Address: " + property.addressLine1 + ", " + property.city
                        + ", Owner: " + property.ownerName
                        + ", Assessed Value: " + property.assessedValue
        );
        documentEmitter.send(event);
    }

    public void publishAssessmentChanged(Property property) {
        ServiceNotificationEvent event = new ServiceNotificationEvent(
                "PROPERTY_ASSESSMENT_CHANGED",
                "property-tax",
                property.id,
                null,
                "Property " + property.parcelNumber + " assessment updated to " + property.assessedValue
        );
        notificationEmitter.send(event);
    }
}
