import { useParams, Link } from 'react-router-dom';
import { useApplication } from '@/hooks/useLicensingQueries';
import LoadingSpinner from '@/components/ui/LoadingSpinner';
import ErrorAlert from '@/components/ui/ErrorAlert';
import StatusBadge from '@/components/ui/StatusBadge';

export default function ApplicationDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading, error } = useApplication(id!);

  if (isLoading) return <LoadingSpinner />;
  if (error) return <div style={{ maxWidth: '800px', margin: '2rem auto', padding: '0 2rem' }}><ErrorAlert error={error} /></div>;
  if (!data) return null;

  return (
    <div style={{ maxWidth: '800px', margin: '2rem auto', padding: '0 2rem' }}>
      <Link to="/licensing" style={{ color: '#1a365d', textDecoration: 'none', fontSize: '0.9rem' }}>
        &larr; Back to Applications
      </Link>

      <h1 style={{ fontSize: '1.75rem', color: '#1a365d', margin: '1rem 0' }}>Application Details</h1>

      <div style={{ border: '1px solid #e2e8f0', borderRadius: '0.75rem', padding: '1.5rem' }}>
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.25rem' }}>
          <div>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Applicant</div>
            <div style={{ fontSize: '1rem', marginTop: '0.25rem' }}>{data.applicantFirstName} {data.applicantLastName}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Email</div>
            <div style={{ fontSize: '1rem', marginTop: '0.25rem' }}>{data.applicantEmail}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>License Type</div>
            <div style={{ fontSize: '1rem', marginTop: '0.25rem' }}>{data.licenseType.replace(/_/g, ' ')}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Status</div>
            <div style={{ marginTop: '0.25rem' }}><StatusBadge status={data.status} /></div>
          </div>
          <div>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Submitted</div>
            <div style={{ fontSize: '1rem', marginTop: '0.25rem' }}>{new Date(data.submittedAt).toLocaleString()}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Application ID</div>
            <div style={{ fontSize: '0.85rem', marginTop: '0.25rem', fontFamily: 'monospace' }}>{data.id}</div>
          </div>
        </div>
        {data.notes && (
          <div style={{ marginTop: '1.5rem', borderTop: '1px solid #e2e8f0', paddingTop: '1rem' }}>
            <div style={{ fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Notes</div>
            <p style={{ marginTop: '0.25rem' }}>{data.notes}</p>
          </div>
        )}
      </div>
    </div>
  );
}
