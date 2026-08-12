CREATE SCHEMA IF NOT EXISTS vital_records;

CREATE TABLE vital_records.certificate_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    requester_first_name VARCHAR(255) NOT NULL,
    requester_last_name VARCHAR(255) NOT NULL,
    requester_email VARCHAR(255) NOT NULL,
    certificate_type VARCHAR(50) NOT NULL,
    subject_first_name VARCHAR(255) NOT NULL,
    subject_last_name VARCHAR(255) NOT NULL,
    subject_date_of_birth DATE,
    subject_date_of_death DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'SUBMITTED',
    tracking_number VARCHAR(50) UNIQUE,
    submitted_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ
);

CREATE TABLE vital_records.certificates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    request_id UUID REFERENCES vital_records.certificate_requests(id),
    certificate_number VARCHAR(50) UNIQUE NOT NULL,
    certificate_type VARCHAR(50) NOT NULL,
    issued_date DATE NOT NULL
);
