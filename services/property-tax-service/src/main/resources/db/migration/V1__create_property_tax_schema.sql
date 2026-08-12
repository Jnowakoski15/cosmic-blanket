CREATE SCHEMA IF NOT EXISTS property_tax;

CREATE TABLE property_tax.properties (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    parcel_number VARCHAR(50) UNIQUE NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL DEFAULT 'Nova',
    zip_code VARCHAR(20) NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    assessed_value NUMERIC(15,2) NOT NULL,
    land_use_type VARCHAR(50) NOT NULL
);

CREATE TABLE property_tax.tax_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    property_id UUID REFERENCES property_tax.properties(id),
    tax_year INT NOT NULL,
    assessed_value NUMERIC(15,2) NOT NULL,
    tax_rate NUMERIC(5,4) NOT NULL,
    tax_amount NUMERIC(15,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'BILLED',
    due_date DATE NOT NULL,
    paid_date DATE
);
