package gov.nova.cosmic.vitalrecords.messaging;

import gov.nova.cosmic.common.event.DocumentChangedEvent;
import gov.nova.cosmic.common.event.ServiceNotificationEvent;
import gov.nova.cosmic.vitalrecords.entity.CertificateRequest;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class VitalRecordsEventPublisher {

    @Channel("documents-changed")
    Emitter<DocumentChangedEvent> documentEmitter;

    @Channel("service-notifications")
    Emitter<ServiceNotificationEvent> notificationEmitter;

    public void publishRequestCreated(CertificateRequest request) {
        DocumentChangedEvent event = new DocumentChangedEvent(
                "vital-records",
                "CERTIFICATE_REQUEST",
                request.id,
                DocumentChangedEvent.Action.CREATED,
                request.certificateType + " Certificate Request",
                "Requester: " + request.requesterFirstName + " " + request.requesterLastName
                        + ", Subject: " + request.subjectFirstName + " " + request.subjectLastName
                        + ", Type: " + request.certificateType
        );
        documentEmitter.send(event);
    }

    public void publishStatusChanged(CertificateRequest request) {
        ServiceNotificationEvent event = new ServiceNotificationEvent(
                "CERTIFICATE_REQUEST_STATUS_CHANGED",
                "vital-records",
                request.id,
                request.requesterEmail,
                "Certificate request status changed to " + request.status
        );
        notificationEmitter.send(event);
    }
}
