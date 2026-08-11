import client from './client';
import type { PagedResponse } from '@/types/common';
import type { Property, TaxRecord } from '@/types/property-tax';

export async function getProperties(page = 0, size = 20): Promise<PagedResponse<Property>> {
  const { data } = await client.get('/property-tax/properties', { params: { page, size } });
  return data;
}

export async function getProperty(parcelNumber: string): Promise<Property> {
  const { data } = await client.get(`/property-tax/properties/${parcelNumber}`);
  return data;
}

export async function searchProperties(address?: string, owner?: string): Promise<Property[]> {
  const { data } = await client.get('/property-tax/properties/search', { params: { address, owner } });
  return data;
}

export async function getTaxRecords(parcelNumber: string): Promise<TaxRecord[]> {
  const { data } = await client.get(`/property-tax/records/property/${parcelNumber}`);
  return data;
}

export async function getTaxRecord(id: string): Promise<TaxRecord> {
  const { data } = await client.get(`/property-tax/records/${id}`);
  return data;
}
