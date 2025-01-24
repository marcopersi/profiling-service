-- Entferne existierende Tabellen (inkl. Abhängigkeiten).
DROP TABLE IF EXISTS BUSINESS_OBJECT_ATTRIBUTES CASCADE;
DROP TABLE IF EXISTS BUSINESS_OBJECT CASCADE;
DROP TABLE IF EXISTS CUSTOMER_PROFILES CASCADE;
DROP TABLE IF EXISTS CUSTOMERS CASCADE;

-- Erweiterung für UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabelle: customers
CREATE TABLE customers (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    salutation VARCHAR(50), -- Enum: HERR, FRAU, DR, PROF
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birthdate DATE,
    civil_status VARCHAR(50), -- Enum: LEDIG, VERHEIRATET, GESCHIEDEN, VERWITWET, EINGETRAGENE_PARTNERSCHAFT
    social_security_number VARCHAR(100),
    occupation VARCHAR(255),
    employer VARCHAR(255),
    academic_degree VARCHAR(255),
    segmentation VARCHAR(50), -- Enum: PRIVATKUNDEN, PROFESSIONELLE_KUNDEN, INSTITUTIONELLE_KUNDEN
    contact_details JSONB, -- JSON für Kontaktinformationen
    hobbies JSONB, -- JSON für Hobbys (z. B. LESEN, SPORT, MUSIK)
    address JSONB, -- JSON für Adressinformationen
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

RETURN NEW;

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
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
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON customer_profiles
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Tabelle: business_object
CREATE TABLE business_object (
                                 id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                                 name VARCHAR(255) NULL,
                                 type VARCHAR(255) NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabelle: business_object_attributes
CREATE TABLE business_object_attributes (
                                            id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                                            business_object_id UUID NOT NULL,
                                            attribute_name VARCHAR(255) NOT NULL,
                                            attribute_value VARCHAR(255) NOT NULL,
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (business_object_id) REFERENCES business_object(id)
);

-- Einschränkungen für Enums
ALTER TABLE customers ADD CONSTRAINT chk_civil_status CHECK (
    civil_status IN ('LEDIG', 'VERHEIRATET', 'GESCHIEDEN', 'VERWITWET', 'EINGETRAGENE_PARTNERSCHAFT')
    );
ALTER TABLE customers ADD CONSTRAINT chk_segmentation CHECK (
    segmentation IN ('PRIVATKUNDEN', 'PROFESSIONELLE_KUNDEN', 'INSTITUTIONELLE_KUNDEN')
    );

-- Trigger und Funktionen für updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_at_customers
    BEFORE UPDATE ON customers
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER set_updated_at_customer_profiles
    BEFORE UPDATE ON customer_profiles
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Indizes für häufige Abfragen
CREATE INDEX idx_customers_first_name ON customers(first_name);
CREATE INDEX idx_customers_last_name ON customers(last_name);
CREATE INDEX idx_business_object_type ON business_object(type);
CREATE INDEX idx_business_object_attributes_name ON business_object_attributes(attribute_name);
