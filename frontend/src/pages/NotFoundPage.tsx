import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <div style={{ textAlign: 'center', padding: '4rem 2rem' }}>
      <h1 style={{ fontSize: '4rem', color: '#1a365d', marginBottom: '1rem' }}>404</h1>
      <p style={{ fontSize: '1.2rem', color: '#6b7280', marginBottom: '2rem' }}>Page not found</p>
      <Link
        to="/"
        style={{
          backgroundColor: '#1a365d',
          color: 'white',
          padding: '0.75rem 1.5rem',
          borderRadius: '0.375rem',
          textDecoration: 'none',
        }}
      >
        Return Home
      </Link>
    </div>
  );
}
