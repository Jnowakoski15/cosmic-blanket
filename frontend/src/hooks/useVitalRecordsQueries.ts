import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import * as vitalRecordsApi from '@/api/vital-records';
import type { CertificateRequestInput } from '@/types/vital-records';

export function useRequests(page = 0, size = 20) {
  return useQuery({
    queryKey: ['vital-records', 'requests', page, size],
    queryFn: () => vitalRecordsApi.getRequests(page, size),
  });
}

export function useRequest(id: string) {
  return useQuery({
    queryKey: ['vital-records', 'requests', id],
    queryFn: () => vitalRecordsApi.getRequest(id),
    enabled: !!id,
  });
}

export function useSubmitRequest() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (request: CertificateRequestInput) => vitalRecordsApi.submitRequest(request),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['vital-records', 'requests'] });
    },
  });
}

export function useTrackingLookup(trackingNumber: string) {
  return useQuery({
    queryKey: ['vital-records', 'tracking', trackingNumber],
    queryFn: () => vitalRecordsApi.getByTrackingNumber(trackingNumber),
    enabled: !!trackingNumber,
  });
}
