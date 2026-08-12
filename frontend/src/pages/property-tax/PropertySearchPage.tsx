import { useState } from 'react';
import { usePropertySearch } from '@/hooks/usePropertyTaxQueries';
import LoadingSpinner from '@/components/ui/LoadingSpinner';
import ErrorAlert from '@/components/ui/ErrorAlert';

export default function PropertySearchPage() {
  const [searchType, setSearchType] = useState<'address' | 'owner'>('address');
  const [query, setQuery] = useState('');
  const [searchQuery, setSearchQuery] = useState<{ address?: string; owner?: string }>({});

  const { data, isLoading, error } = usePropertySearch(searchQuery.address, searchQuery.owner);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (!query.trim()) return;
    setSearchQuery(searchType === 'address' ? { address: query } : { owner: query });
  };

  const inputStyle = {
    flex: 1,
    padding: '0.625rem',
    border: '1px solid #d1d5db',
    borderRadius: '0.375rem',
    fontSize: '0.9rem',
  };

  return (
    <div style={{ maxWidth: '1200px', margin: '2rem auto', padding: '0 2rem' }}>
      <h1 style={{ fontSize: '1.75rem', color: '#1a365d', marginBottom: '2rem' }}>Property & Tax Search</h1>

      <form onSubmit={handleSearch} style={{ display: 'flex', gap: '0.75rem', marginBottom: '2rem' }}>
        <select
          style={{ ...inputStyle, flex: 'none', width: '150px' }}
          value={searchType}
          onChange={(e) => setSearchType(e.target.value as 'address' | 'owner')}
        >
          <option value="address">By Address</option>
          <option value="owner">By Owner</option>
        </select>
        <input
          style={inputStyle}
          placeholder={searchType === 'address' ? 'Enter address...' : 'Enter owner name...'}
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button
          type="submit"
          style={{
            backgroundColor: '#1a365d',
            color: 'white',
            padding: '0.625rem 1.25rem',
            borderRadius: '0.375rem',
            border: 'none',
            cursor: 'pointer',
          }}
        >
          Search
        </button>
      </form>

      {isLoading && <LoadingSpinner />}
      {error && <ErrorAlert error={error} />}

      {data && (
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr style={{ borderBottom: '2px solid #e2e8f0', textAlign: 'left' }}>
              <th style={{ padding: '0.75rem' }}>Parcel #</th>
              <th style={{ padding: '0.75rem' }}>Address</th>
              <th style={{ padding: '0.75rem' }}>Owner</th>
              <th style={{ padding: '0.75rem' }}>Assessed Value</th>
              <th style={{ padding: '0.75rem' }}>Land Use</th>
            </tr>
          </thead>
          <tbody>
            {data.map((property) => (
              <tr key={property.id} style={{ borderBottom: '1px solid #e2e8f0' }}>
                <td style={{ padding: '0.75rem', fontFamily: 'monospace' }}>{property.parcelNumber}</td>
                <td style={{ padding: '0.75rem' }}>
                  {property.addressLine1}{property.addressLine2 ? `, ${property.addressLine2}` : ''}, {property.city}, {property.state} {property.zipCode}
                </td>
                <td style={{ padding: '0.75rem' }}>{property.ownerName}</td>
                <td style={{ padding: '0.75rem' }}>${property.assessedValue.toLocaleString()}</td>
                <td style={{ padding: '0.75rem', color: '#6b7280' }}>{property.landUseType.replace(/_/g, ' ')}</td>
              </tr>
            ))}
            {data.length === 0 && (
              <tr>
                <td colSpan={5} style={{ padding: '2rem', textAlign: 'center', color: '#9ca3af' }}>
                  No properties found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
