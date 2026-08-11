import client from './client';
import type { PagedResponse } from '@/types/common';
import type { LicenseApplication, LicenseApplicationRequest, License } from '@/types/licensing';

export async function getApplications(page = 0, size = 20): Promise<PagedResponse<LicenseApplication>> {
  const { data } = await client.get('/licensing/applications', { params: { page, size } });
  return data;
}

export async function getApplication(id: string): Promise<LicenseApplication> {
  const { data } = await client.get(`/licensing/applications/${id}`);
  return data;
}

export async function submitApplication(request: LicenseApplicationRequest): Promise<LicenseApplication> {
  const { data } = await client.post('/licensing/applications', request);
  return data;
}

export async function updateApplicationStatus(id: string, status: string): Promise<LicenseApplication> {
  const { data } = await client.patch(`/licensing/applications/${id}/status`, { status });
  return data;
}

export async function getLicenses(page = 0, size = 20): Promise<PagedResponse<License>> {
  const { data } = await client.get('/licensing/licenses', { params: { page, size } });
  return data;
}

export async function getLicense(licenseNumber: string): Promise<License> {
  const { data } = await client.get(`/licensing/licenses/${licenseNumber}`);
  return data;
}
