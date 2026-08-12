CREATE SCHEMA IF NOT EXISTS licensing;

CREATE TABLE licensing.license_applications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    applicant_first_name VARCHAR(255) NOT NULL,
    applicant_last_name VARCHAR(255) NOT NULL,
    applicant_email VARCHAR(255) NOT NULL,
    license_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'SUBMITTED',
    submitted_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ,
    notes TEXT
);

CREATE TABLE licensing.licenses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    application_id UUID REFERENCES licensing.license_applications(id),
    license_number VARCHAR(50) UNIQUE NOT NULL,
    license_type VARCHAR(50) NOT NULL,
    holder_name VARCHAR(255) NOT NULL,
    issued_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE'
);
