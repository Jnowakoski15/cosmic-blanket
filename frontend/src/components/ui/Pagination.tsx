interface PaginationProps {
  page: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

export default function Pagination({ page, totalPages, onPageChange }: PaginationProps) {
  if (totalPages <= 1) return null;

  return (
    <div style={{ display: 'flex', justifyContent: 'center', gap: '0.5rem', padding: '1rem 0' }}>
      <button
        onClick={() => onPageChange(page - 1)}
        disabled={page === 0}
        style={{
          padding: '0.5rem 1rem',
          border: '1px solid #d1d5db',
          borderRadius: '0.375rem',
          cursor: page === 0 ? 'not-allowed' : 'pointer',
          opacity: page === 0 ? 0.5 : 1,
        }}
      >
        Previous
      </button>
      <span style={{ padding: '0.5rem 1rem', color: '#6b7280' }}>
        Page {page + 1} of {totalPages}
      </span>
      <button
        onClick={() => onPageChange(page + 1)}
        disabled={page >= totalPages - 1}
        style={{
          padding: '0.5rem 1rem',
          border: '1px solid #d1d5db',
          borderRadius: '0.375rem',
          cursor: page >= totalPages - 1 ? 'not-allowed' : 'pointer',
          opacity: page >= totalPages - 1 ? 0.5 : 1,
        }}
      >
        Next
      </button>
    </div>
  );
}
