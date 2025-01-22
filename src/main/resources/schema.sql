-- Löscht die Tabellen, falls sie bereits existieren, und erstellt sie neu.
DROP TABLE IF EXISTS BUSINESS_OBJECT_ATTRIBUTES CASCADE;
DROP TABLE IF EXISTS BUSINESS_OBJECT CASCADE;
DROP TABLE IF EXISTS CUSTOMER_PROFILES CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

-- Erweiterung für UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabellen-Definitionen
CREATE TABLE customers (
                           id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL
);

CREATE TABLE BUSINESS_OBJECT (
                                 id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                                 name VARCHAR(255) NULL,
                                 type VARCHAR(255) NOT NULL
);

CREATE TABLE BUSINESS_OBJECT_ATTRIBUTES (
                                            id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                                            business_object_id UUID NOT NULL,
                                            attribute_name VARCHAR(255) NOT NULL,
                                            attribute_value VARCHAR(255) NOT NULL,
                                            FOREIGN KEY (business_object_id) REFERENCES BUSINESS_OBJECT(id)
);

CREATE TABLE CUSTOMER_PROFILES (
                                   id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                                   first_name VARCHAR(255) NOT NULL,
                                   last_name VARCHAR(255) NOT NULL,
                                   birthdate DATE NOT NULL,
                                   contact_details JSONB NULL,
                                   economic_circumstances JSONB NOT NULL,
                                   risk_tolerance VARCHAR(50) NOT NULL,
                                   investment_experience VARCHAR(255) NULL,
                                   investment_objectives VARCHAR(255) NULL
);