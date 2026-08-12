export type LandUseType = 'RESIDENTIAL' | 'COMMERCIAL' | 'AGRICULTURAL' | 'INDUSTRIAL';
export type TaxStatus = 'BILLED' | 'PAID' | 'DELINQUENT';

export interface Property {
  id: string;
  parcelNumber: string;
  addressLine1: string;
  addressLine2?: string;
  city: string;
  state: string;
  zipCode: string;
  ownerName: string;
  assessedValue: number;
  landUseType: LandUseType;
}

export interface TaxRecord {
  id: string;
  propertyId: string;
  taxYear: number;
  assessedValue: number;
  taxRate: number;
  taxAmount: number;
  status: TaxStatus;
  dueDate: string;
  paidDate?: string;
}
