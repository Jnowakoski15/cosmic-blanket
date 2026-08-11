import { useState } from 'react';
import { search as searchApi } from '@/api/ai';
import type { SearchResult } from '@/api/ai';
import LoadingSpinner from '@/components/ui/LoadingSpinner';
import ErrorAlert from '@/components/ui/ErrorAlert';

export default function SearchPage() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<SearchResult[] | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!query.trim()) return;

    setIsLoading(true);
    setError(null);
    try {
      const data = await searchApi(query.trim());
      setResults(data);
    } catch (err) {
      setError(err as Error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '800px', margin: '2rem auto', padding: '0 2rem' }}>
      <h1 style={{ fontSize: '1.75rem', color: '#1a365d', marginBottom: '1.5rem' }}>Search State Services</h1>

      <form onSubmit={handleSearch} style={{ display: 'flex', gap: '0.5rem', marginBottom: '2rem' }}>
        <input
          style={{
            flex: 1,
            padding: '0.75rem',
            border: '1px solid #d1d5db',
            borderRadius: '0.5rem',
            fontSize: '1rem',
          }}
          placeholder="Search documents, services, and records..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button
          type="submit"
          disabled={isLoading}
          style={{
            backgroundColor: '#1a365d',
            color: 'white',
            padding: '0.75rem 1.5rem',
            borderRadius: '0.5rem',
            border: 'none',
            cursor: 'pointer',
          }}
        >
          Search
        </button>
      </form>

      {isLoading && <LoadingSpinner />}
      {error && <ErrorAlert error={error} />}

      {results && (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          {results.length === 0 && (
            <p style={{ color: '#9ca3af', textAlign: 'center', padding: '2rem' }}>No results found</p>
          )}
          {results.map((result) => (
            <div key={result.id} style={{ border: '1px solid #e2e8f0', borderRadius: '0.5rem', padding: '1rem' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'baseline', marginBottom: '0.5rem' }}>
                <h3 style={{ fontSize: '1rem', color: '#1a365d' }}>{result.title}</h3>
                <span style={{ fontSize: '0.75rem', color: '#9ca3af' }}>
                  Score: {(result.score * 100).toFixed(1)}%
                </span>
              </div>
              <p style={{ fontSize: '0.9rem', color: '#4b5563', lineHeight: 1.5 }}>{result.content}</p>
              <div style={{ fontSize: '0.75rem', color: '#9ca3af', marginTop: '0.5rem' }}>Source: {result.source}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
