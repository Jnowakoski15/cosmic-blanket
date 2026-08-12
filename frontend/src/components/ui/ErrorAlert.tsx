import type { ApiError } from '@/types/common';

export default function ErrorAlert({ error }: { error: ApiError | Error | unknown }) {
  const message = error instanceof Error
    ? error.message
    : (error as ApiError)?.message || 'An unexpected error occurred';

  return (
    <div style={{
      backgroundColor: '#fee2e2',
      border: '1px solid #fecaca',
      color: '#991b1b',
      padding: '1rem',
      borderRadius: '0.5rem',
      margin: '1rem 0',
    }}>
      {message}
    </div>
  );
}
