export default function Footer() {
  return (
    <footer style={{
      backgroundColor: '#1a365d',
      color: 'white',
      padding: '2rem',
      marginTop: 'auto',
      textAlign: 'center',
      fontSize: '0.85rem',
    }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
        <p>State of Nova - Official Government Portal</p>
        <p style={{ opacity: 0.7, marginTop: '0.5rem' }}>
          This is a demonstration application. Not affiliated with any real government entity.
        </p>
      </div>
    </footer>
  );
}
