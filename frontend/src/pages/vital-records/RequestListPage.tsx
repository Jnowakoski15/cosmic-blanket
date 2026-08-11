import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useRequests } from '@/hooks/useVitalRecordsQueries';
import LoadingSpinner from '@/components/ui/LoadingSpinner';
import ErrorAlert from '@/components/ui/ErrorAlert';
import StatusBadge from '@/components/ui/StatusBadge';
import Pagination from '@/components/ui/Pagination';

export default function RequestListPage() {
  const [page, setPage] = useState(0);
  const { data, isLoading, error } = useRequests(page);

  return (
    <div style={{ maxWidth: '1200px', margin: '2rem auto', padding: '0 2rem' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h1 style={{ fontSize: '1.75rem', color: '#1a365d' }}>Certificate Requests</h1>
        <Link
          to="/vital-records/new"
          style={{
            backgroundColor: '#1a365d',
            color: 'white',
            padding: '0.625rem 1.25rem',
            borderRadius: '0.375rem',
            textDecoration: 'none',
            fontSize: '0.9rem',
          }}
        >
          New Request
        </Link>
      </div>

      {isLoading && <LoadingSpinner />}
      {error && <ErrorAlert error={error} />}

      {data && (
        <>
          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr style={{ borderBottom: '2px solid #e2e8f0', textAlign: 'left' }}>
                <th style={{ padding: '0.75rem' }}>Requester</th>
                <th style={{ padding: '0.75rem' }}>Type</th>
                <th style={{ padding: '0.75rem' }}>Subject</th>
                <th style={{ padding: '0.75rem' }}>Status</th>
                <th style={{ padding: '0.75rem' }}>Tracking #</th>
              </tr>
            </thead>
            <tbody>
              {data.content.map((req) => (
                <tr key={req.id} style={{ borderBottom: '1px solid #e2e8f0' }}>
                  <td style={{ padding: '0.75rem' }}>
                    {req.requesterFirstName} {req.requesterLastName}
                  </td>
                  <td style={{ padding: '0.75rem', color: '#6b7280' }}>
                    {req.certificateType.replace(/_/g, ' ')}
                  </td>
                  <td style={{ padding: '0.75rem' }}>
                    {req.subjectFirstName} {req.subjectLastName}
                  </td>
                  <td style={{ padding: '0.75rem' }}>
                    <StatusBadge status={req.status} />
                  </td>
                  <td style={{ padding: '0.75rem', fontFamily: 'monospace', fontSize: '0.85rem' }}>
                    {req.trackingNumber}
                  </td>
                </tr>
              ))}
              {data.content.length === 0 && (
                <tr>
                  <td colSpan={5} style={{ padding: '2rem', textAlign: 'center', color: '#9ca3af' }}>
                    No requests found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
          <Pagination page={page} totalPages={data.totalPages} onPageChange={setPage} />
        </>
      )}
    </div>
  );
}
