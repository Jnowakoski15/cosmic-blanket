package gov.nova.cosmic.licensing.messaging;

import gov.nova.cosmic.common.event.DocumentChangedEvent;
import gov.nova.cosmic.common.event.ServiceNotificationEvent;
import gov.nova.cosmic.licensing.entity.LicenseApplication;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class LicensingEventPublisher {

    @Channel("documents-changed")
    Emitter<DocumentChangedEvent> documentEmitter;

    @Channel("service-notifications")
    Emitter<ServiceNotificationEvent> notificationEmitter;

    public void publishApplicationCreated(LicenseApplication app) {
        DocumentChangedEvent event = new DocumentChangedEvent(
                "licensing",
                "LICENSE_APPLICATION",
                app.id,
                DocumentChangedEvent.Action.CREATED,
                app.licenseType + " License Application",
                "Applicant: " + app.applicantFirstName + " " + app.applicantLastName
                        + ", License Type: " + app.licenseType
        );
        documentEmitter.send(event);
    }

    public void publishStatusChanged(LicenseApplication app) {
        ServiceNotificationEvent event = new ServiceNotificationEvent(
                "LICENSE_STATUS_CHANGED",
                "licensing",
                app.id,
                app.applicantEmail,
                "License application status changed to " + app.status
        );
        notificationEmitter.send(event);
    }
}
