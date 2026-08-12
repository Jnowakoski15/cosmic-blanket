export type CertificateType = 'BIRTH_CERTIFICATE' | 'DEATH_CERTIFICATE';
export type RequestStatus = 'SUBMITTED' | 'PROCESSING' | 'READY' | 'MAILED' | 'DENIED';

export interface CertificateRequestInput {
  requesterFirstName: string;
  requesterLastName: string;
  requesterEmail: string;
  certificateType: CertificateType;
  subjectFirstName: string;
  subjectLastName: string;
  subjectDateOfBirth?: string;
  subjectDateOfDeath?: string;
}

export interface CertificateRequest {
  id: string;
  requesterFirstName: string;
  requesterLastName: string;
  requesterEmail: string;
  certificateType: CertificateType;
  subjectFirstName: string;
  subjectLastName: string;
  subjectDateOfBirth?: string;
  subjectDateOfDeath?: string;
  status: RequestStatus;
  trackingNumber: string;
  submittedAt: string;
  updatedAt?: string;
}

export interface Certificate {
  id: string;
  requestId: string;
  certificateNumber: string;
  certificateType: CertificateType;
  issuedDate: string;
}
