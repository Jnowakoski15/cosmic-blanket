import { useQuery } from '@tanstack/react-query';
import * as propertyTaxApi from '@/api/property-tax';

export function useProperties(page = 0, size = 20) {
  return useQuery({
    queryKey: ['property-tax', 'properties', page, size],
    queryFn: () => propertyTaxApi.getProperties(page, size),
  });
}

export function useProperty(parcelNumber: string) {
  return useQuery({
    queryKey: ['property-tax', 'properties', parcelNumber],
    queryFn: () => propertyTaxApi.getProperty(parcelNumber),
    enabled: !!parcelNumber,
  });
}

export function usePropertySearch(address?: string, owner?: string) {
  return useQuery({
    queryKey: ['property-tax', 'search', address, owner],
    queryFn: () => propertyTaxApi.searchProperties(address, owner),
    enabled: !!(address || owner),
  });
}

export function useTaxRecords(parcelNumber: string) {
  return useQuery({
    queryKey: ['property-tax', 'records', parcelNumber],
    queryFn: () => propertyTaxApi.getTaxRecords(parcelNumber),
    enabled: !!parcelNumber,
  });
}
