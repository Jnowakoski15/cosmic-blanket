import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSubmitApplication } from '@/hooks/useLicensingQueries';
import ErrorAlert from '@/components/ui/ErrorAlert';
import type { LicenseType } from '@/types/licensing';

export default function NewApplicationPage() {
  const navigate = useNavigate();
  const mutation = useSubmitApplication();

  const [form, setForm] = useState({
    applicantFirstName: '',
    applicantLastName: '',
    applicantEmail: '',
    licenseType: 'DRIVERS_LICENSE' as LicenseType,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    mutation.mutate(form, {
      onSuccess: (data) => {
        navigate(`/licensing/applications/${data.id}`);
      },
    });
  };

  const inputStyle = {
    width: '100%',
    padding: '0.625rem',
    border: '1px solid #d1d5db',
    borderRadius: '0.375rem',
    fontSize: '0.9rem',
  };

  return (
    <div style={{ maxWidth: '600px', margin: '2rem auto', padding: '0 2rem' }}>
      <h1 style={{ fontSize: '1.75rem', color: '#1a365d', marginBottom: '2rem' }}>New License Application</h1>

      {mutation.error && <ErrorAlert error={mutation.error} />}

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
        <div>
          <label style={{ display: 'block', marginBottom: '0.25rem', fontWeight: 500, fontSize: '0.9rem' }}>First Name</label>
          <input
            required
            style={inputStyle}
            value={form.applicantFirstName}
            onChange={(e) => setForm({ ...form, applicantFirstName: e.target.value })}
          />
        </div>
        <div>
          <label style={{ display: 'block', marginBottom: '0.25rem', fontWeight: 500, fontSize: '0.9rem' }}>Last Name</label>
          <input
            required
            style={inputStyle}
            value={form.applicantLastName}
            onChange={(e) => setForm({ ...form, applicantLastName: e.target.value })}
          />
        </div>
        <div>
          <label style={{ display: 'block', marginBottom: '0.25rem', fontWeight: 500, fontSize: '0.9rem' }}>Email</label>
          <input
            required
            type="email"
            style={inputStyle}
            value={form.applicantEmail}
            onChange={(e) => setForm({ ...form, applicantEmail: e.target.value })}
          />
        </div>
        <div>
          <label style={{ display: 'block', marginBottom: '0.25rem', fontWeight: 500, fontSize: '0.9rem' }}>License Type</label>
          <select
            style={inputStyle}
            value={form.licenseType}
            onChange={(e) => setForm({ ...form, licenseType: e.target.value as LicenseType })}
          >
            <option value="DRIVERS_LICENSE">Driver&apos;s License</option>
            <option value="BUSINESS_LICENSE">Business License</option>
          </select>
        </div>
        <button
          type="submit"
          disabled={mutation.isPending}
          style={{
            backgroundColor: '#1a365d',
            color: 'white',
            padding: '0.75rem',
            borderRadius: '0.375rem',
            border: 'none',
            fontSize: '1rem',
            cursor: mutation.isPending ? 'not-allowed' : 'pointer',
            opacity: mutation.isPending ? 0.7 : 1,
          }}
        >
          {mutation.isPending ? 'Submitting...' : 'Submit Application'}
        </button>
      </form>
    </div>
  );
}
