import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import * as licensingApi from '@/api/licensing';
import type { LicenseApplicationRequest } from '@/types/licensing';

export function useApplications(page = 0, size = 20) {
  return useQuery({
    queryKey: ['licensing', 'applications', page, size],
    queryFn: () => licensingApi.getApplications(page, size),
  });
}

export function useApplication(id: string) {
  return useQuery({
    queryKey: ['licensing', 'applications', id],
    queryFn: () => licensingApi.getApplication(id),
    enabled: !!id,
  });
}

export function useSubmitApplication() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (request: LicenseApplicationRequest) => licensingApi.submitApplication(request),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['licensing', 'applications'] });
    },
  });
}

export function useLicenses(page = 0, size = 20) {
  return useQuery({
    queryKey: ['licensing', 'licenses', page, size],
    queryFn: () => licensingApi.getLicenses(page, size),
  });
}
