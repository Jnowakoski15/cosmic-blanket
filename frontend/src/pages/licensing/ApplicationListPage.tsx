import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useApplications } from '@/hooks/useLicensingQueries';
import LoadingSpinner from '@/components/ui/LoadingSpinner';
import ErrorAlert from '@/components/ui/ErrorAlert';
import StatusBadge from '@/components/ui/StatusBadge';
import Pagination from '@/components/ui/Pagination';

export default function ApplicationListPage() {
  const [page, setPage] = useState(0);
  const { data, isLoading, error } = useApplications(page);

  return (
    <div style={{ maxWidth: '1200px', margin: '2rem auto', padding: '0 2rem' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h1 style={{ fontSize: '1.75rem', color: '#1a365d' }}>License Applications</h1>
        <Link
          to="/licensing/new"
          style={{
            backgroundColor: '#1a365d',
            color: 'white',
            padding: '0.625rem 1.25rem',
            borderRadius: '0.375rem',
            textDecoration: 'none',
            fontSize: '0.9rem',
          }}
        >
          New Application
        </Link>
      </div>

      {isLoading && <LoadingSpinner />}
      {error && <ErrorAlert error={error} />}

      {data && (
        <>
          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr style={{ borderBottom: '2px solid #e2e8f0', textAlign: 'left' }}>
                <th style={{ padding: '0.75rem' }}>Applicant</th>
                <th style={{ padding: '0.75rem' }}>Type</th>
                <th style={{ padding: '0.75rem' }}>Status</th>
                <th style={{ padding: '0.75rem' }}>Submitted</th>
              </tr>
            </thead>
            <tbody>
              {data.content.map((app) => (
                <tr key={app.id} style={{ borderBottom: '1px solid #e2e8f0' }}>
                  <td style={{ padding: '0.75rem' }}>
                    <Link to={`/licensing/applications/${app.id}`} style={{ color: '#1a365d' }}>
                      {app.applicantFirstName} {app.applicantLastName}
                    </Link>
                  </td>
                  <td style={{ padding: '0.75rem', color: '#6b7280' }}>
                    {app.licenseType.replace(/_/g, ' ')}
                  </td>
                  <td style={{ padding: '0.75rem' }}>
                    <StatusBadge status={app.status} />
                  </td>
                  <td style={{ padding: '0.75rem', color: '#6b7280' }}>
                    {new Date(app.submittedAt).toLocaleDateString()}
                  </td>
                </tr>
              ))}
              {data.content.length === 0 && (
                <tr>
                  <td colSpan={4} style={{ padding: '2rem', textAlign: 'center', color: '#9ca3af' }}>
                    No applications found
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
