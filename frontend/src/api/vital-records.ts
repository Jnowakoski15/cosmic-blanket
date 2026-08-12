import client from './client';
import type { PagedResponse } from '@/types/common';
import type { CertificateRequest, CertificateRequestInput, Certificate } from '@/types/vital-records';

export async function getRequests(page = 0, size = 20): Promise<PagedResponse<CertificateRequest>> {
  const { data } = await client.get('/vital-records/requests', { params: { page, size } });
  return data;
}

export async function getRequest(id: string): Promise<CertificateRequest> {
  const { data } = await client.get(`/vital-records/requests/${id}`);
  return data;
}

export async function submitRequest(request: CertificateRequestInput): Promise<CertificateRequest> {
  const { data } = await client.post('/vital-records/requests', request);
  return data;
}

export async function updateRequestStatus(id: string, status: string): Promise<CertificateRequest> {
  const { data } = await client.patch(`/vital-records/requests/${id}/status`, { status });
  return data;
}

export async function getByTrackingNumber(trackingNumber: string): Promise<CertificateRequest> {
  const { data } = await client.get(`/vital-records/requests/tracking/${trackingNumber}`);
  return data;
}

export async function getCertificate(certificateNumber: string): Promise<Certificate> {
  const { data } = await client.get(`/vital-records/certificates/${certificateNumber}`);
  return data;
}
