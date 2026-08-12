export type LicenseType = 'DRIVERS_LICENSE' | 'BUSINESS_LICENSE';
export type ApplicationStatus = 'SUBMITTED' | 'UNDER_REVIEW' | 'APPROVED' | 'DENIED';
export type LicenseStatus = 'ACTIVE' | 'EXPIRED' | 'REVOKED' | 'SUSPENDED';

export interface LicenseApplicationRequest {
  applicantFirstName: string;
  applicantLastName: string;
  applicantEmail: string;
  licenseType: LicenseType;
}

export interface LicenseApplication {
  id: string;
  applicantFirstName: string;
  applicantLastName: string;
  applicantEmail: string;
  licenseType: LicenseType;
  status: ApplicationStatus;
  submittedAt: string;
  updatedAt?: string;
  notes?: string;
}

export interface License {
  id: string;
  applicationId: string;
  licenseNumber: string;
  licenseType: LicenseType;
  holderName: string;
  issuedDate: string;
  expiryDate: string;
  status: LicenseStatus;
}
