-- Drop existing tables and functions if they exist
DROP TABLE IF EXISTS customer_profiles CASCADE;
DROP TABLE IF EXISTS business_object CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP FUNCTION IF EXISTS update_updated_at_column CASCADE;
DROP TRIGGER IF EXISTS set_updated_at_customers ON customers;
DROP TRIGGER IF EXISTS set_updated_at_customer_profiles ON customer_profiles;

-- Create necessary extensions
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Create the update function for the updated_at column
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the customers table
CREATE TABLE customers (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the customer_profiles table
CREATE TABLE customer_profiles (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    customer_id UUID NOT NULL, -- Fremdschlüssel zu CUSTOMERS
    economic_circumstances JSONB NOT NULL, -- JSON für wirtschaftliche Verhältnisse
    knowledge_and_experience JSONB NOT NULL, -- JSON für Kenntnisse und Erfahrungen
    risk_tolerance VARCHAR(50) NOT NULL, -- Enum: LOW, MEDIUM, HIGH
    investment_purpose TEXT NOT NULL, -- Anlageziel
    investment_objectives TEXT NULL, -- Anlageziele
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Create the business_object table
CREATE TABLE business_object (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NULL,
    description TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the trigger for the updated_at column on customers
CREATE TRIGGER set_updated_at_customers
BEFORE UPDATE ON customers
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Create the trigger for the updated_at column on customer_profiles
CREATE TRIGGER set_updated_at_customer_profiles
BEFORE UPDATE ON customer_profiles
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Indizes für häufige Abfragen
CREATE INDEX idx_customers_first_name ON customers(first_name);
CREATE INDEX idx_customers_last_name ON customers(last_name);
CREATE INDEX idx_business_object_name ON business_object(name);
CREATE INDEX idx_customer_profiles_customer_id ON customer_profiles(customer_id);