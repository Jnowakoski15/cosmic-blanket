import { Link } from 'react-router-dom';
import { useAuth } from '@/auth/AuthProvider';

export default function Header() {
  const { isAuthenticated, user, login, logout } = useAuth();

  return (
    <header style={{
      backgroundColor: '#1a365d',
      color: 'white',
      padding: '0 2rem',
      borderBottom: '4px solid #c4a35a',
    }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <Link to="/" style={{ color: 'white', textDecoration: 'none', display: 'flex', alignItems: 'center', gap: '1rem', padding: '1rem 0' }}>
          <div style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>State of Nova</div>
          <div style={{ fontSize: '0.85rem', opacity: 0.8 }}>Official Government Portal</div>
        </Link>
        <div style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
          <nav style={{ display: 'flex', gap: '1.5rem' }}>
            <Link to="/licensing" style={{ color: 'white', textDecoration: 'none', padding: '0.5rem 0', fontSize: '0.9rem' }}>Licensing</Link>
            <Link to="/vital-records" style={{ color: 'white', textDecoration: 'none', padding: '0.5rem 0', fontSize: '0.9rem' }}>Vital Records</Link>
            <Link to="/property-tax" style={{ color: 'white', textDecoration: 'none', padding: '0.5rem 0', fontSize: '0.9rem' }}>Property & Tax</Link>
            <Link to="/chat" style={{ color: 'white', textDecoration: 'none', padding: '0.5rem 0', fontSize: '0.9rem' }}>AI Assistant</Link>
            <Link to="/search" style={{ color: 'white', textDecoration: 'none', padding: '0.5rem 0', fontSize: '0.9rem' }}>Search</Link>
          </nav>
          {isAuthenticated ? (
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
              <span style={{ fontSize: '0.85rem', opacity: 0.9 }}>{user?.profile.preferred_username}</span>
              <button
                onClick={logout}
                style={{
                  backgroundColor: 'transparent',
                  border: '1px solid rgba(255,255,255,0.5)',
                  color: 'white',
                  padding: '0.375rem 0.75rem',
                  borderRadius: '0.25rem',
                  cursor: 'pointer',
                  fontSize: '0.8rem',
                }}
              >
                Logout
              </button>
            </div>
          ) : (
            <button
              onClick={login}
              style={{
                backgroundColor: '#c4a35a',
                border: 'none',
                color: '#1a365d',
                padding: '0.375rem 0.75rem',
                borderRadius: '0.25rem',
                cursor: 'pointer',
                fontSize: '0.8rem',
                fontWeight: 600,
              }}
            >
              Login
            </button>
          )}
        </div>
      </div>
    </header>
  );
}
