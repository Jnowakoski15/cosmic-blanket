const statusColors: Record<string, { bg: string; text: string }> = {
  SUBMITTED: { bg: '#e2e8f0', text: '#475569' },
  UNDER_REVIEW: { bg: '#fef3c7', text: '#92400e' },
  PROCESSING: { bg: '#fef3c7', text: '#92400e' },
  APPROVED: { bg: '#d1fae5', text: '#065f46' },
  READY: { bg: '#d1fae5', text: '#065f46' },
  MAILED: { bg: '#dbeafe', text: '#1e40af' },
  DENIED: { bg: '#fee2e2', text: '#991b1b' },
  ACTIVE: { bg: '#d1fae5', text: '#065f46' },
  EXPIRED: { bg: '#fee2e2', text: '#991b1b' },
  REVOKED: { bg: '#fee2e2', text: '#991b1b' },
  SUSPENDED: { bg: '#fef3c7', text: '#92400e' },
  BILLED: { bg: '#e2e8f0', text: '#475569' },
  PAID: { bg: '#d1fae5', text: '#065f46' },
  DELINQUENT: { bg: '#fee2e2', text: '#991b1b' },
};

export default function StatusBadge({ status }: { status: string }) {
  const colors = statusColors[status] || { bg: '#e2e8f0', text: '#475569' };
  return (
    <span style={{
      backgroundColor: colors.bg,
      color: colors.text,
      padding: '0.25rem 0.75rem',
      borderRadius: '9999px',
      fontSize: '0.75rem',
      fontWeight: 600,
      textTransform: 'uppercase',
      letterSpacing: '0.05em',
    }}>
      {status.replace(/_/g, ' ')}
    </span>
  );
}
