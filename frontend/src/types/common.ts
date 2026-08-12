export interface ApiError {
  timestamp: string;
  status: number;
  message: string;
  path: string;
  traceId?: string;
}

export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
