import { Link } from 'react-router-dom';

const services = [
  {
    title: 'Licensing',
    description: "Apply for or renew driver's licenses and business licenses",
    path: '/licensing',
    icon: '📋',
  },
  {
    title: 'Vital Records',
    description: 'Request birth certificates, death certificates, and more',
    path: '/vital-records',
    icon: '📄',
  },
  {
    title: 'Property & Tax',
    description: 'Look up property records and tax information',
    path: '/property-tax',
    icon: '🏠',
  },
  {
    title: 'AI Assistant',
    description: 'Get help navigating state services with our AI chatbot',
    path: '/chat',
    icon: '💬',
  },
  {
    title: 'Search',
    description: 'Search across all state services and documents',
    path: '/search',
    icon: '🔍',
  },
];

export default function HomePage() {
  return (
    <div>
      <section style={{
        backgroundColor: '#1a365d',
        color: 'white',
        padding: '4rem 2rem',
        textAlign: 'center',
      }}>
        <h1 style={{ fontSize: '2.5rem', marginBottom: '1rem' }}>Welcome to the State of Nova</h1>
        <p style={{ fontSize: '1.2rem', opacity: 0.9, maxWidth: '600px', margin: '0 auto' }}>
          Your gateway to state government services. Find what you need quickly and easily.
        </p>
      </section>

      <section style={{ maxWidth: '1200px', margin: '3rem auto', padding: '0 2rem' }}>
        <h2 style={{ fontSize: '1.5rem', marginBottom: '2rem', color: '#1a365d' }}>Government Services</h2>
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
          gap: '1.5rem',
        }}>
          {services.map((service) => (
            <Link
              key={service.path}
              to={service.path}
              style={{
                textDecoration: 'none',
                color: 'inherit',
                border: '1px solid #e2e8f0',
                borderRadius: '0.75rem',
                padding: '1.5rem',
                transition: 'box-shadow 0.2s, transform 0.2s',
                display: 'block',
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.boxShadow = '0 4px 12px rgba(0,0,0,0.1)';
                e.currentTarget.style.transform = 'translateY(-2px)';
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.boxShadow = 'none';
                e.currentTarget.style.transform = 'none';
              }}
            >
              <div style={{ fontSize: '2rem', marginBottom: '0.75rem' }}>{service.icon}</div>
              <h3 style={{ fontSize: '1.1rem', color: '#1a365d', marginBottom: '0.5rem' }}>{service.title}</h3>
              <p style={{ color: '#6b7280', fontSize: '0.9rem', lineHeight: 1.5 }}>{service.description}</p>
            </Link>
          ))}
        </div>
      </section>
    </div>
  );
}
