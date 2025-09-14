-- MySQL Database Schema for Blood Donation System
-- Senior Doctor Module - Donor Medical Reports

-- Create the doctors table
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the donor_medical_reports table
CREATE TABLE IF NOT EXISTS donor_medical_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id BIGINT NOT NULL,
    doctor_notes TEXT,
    health_status VARCHAR(50),
    is_approved BOOLEAN NOT NULL DEFAULT FALSE,
    appointment_date TIMESTAMP NULL,
    doctor_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Foreign key constraints (assuming donors table exists)
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    
    -- Indexes for better performance
    INDEX idx_donor_id (donor_id),
    INDEX idx_doctor_id (doctor_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_is_approved (is_approved),
    INDEX idx_health_status (health_status),
    INDEX idx_created_at (created_at)
);

-- Insert sample doctor data
INSERT INTO doctors (name, specialization) VALUES 
('Ms. Shanika Jayawardena', 'Senior Doctor'),
('Dr. John Smith', 'Senior Doctor'),
('Dr. Sarah Johnson', 'Senior Doctor');

-- Create indexes for better query performance
CREATE INDEX idx_donor_medical_reports_donor_appointment ON donor_medical_reports(donor_id, appointment_date);
CREATE INDEX idx_donor_medical_reports_doctor_approved ON donor_medical_reports(doctor_id, is_approved);

-- Add comments to tables for documentation
ALTER TABLE doctors COMMENT = 'Table storing information about doctors who can approve/reject donations';
ALTER TABLE donor_medical_reports COMMENT = 'Table storing medical reports and assessments for donors';

-- Add comments to columns for documentation
ALTER TABLE doctors MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT 'Primary key for doctor';
ALTER TABLE doctors MODIFY COLUMN name VARCHAR(100) NOT NULL COMMENT 'Full name of the doctor';
ALTER TABLE doctors MODIFY COLUMN specialization VARCHAR(50) NOT NULL COMMENT 'Medical specialization of the doctor';

ALTER TABLE donor_medical_reports MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT 'Primary key for medical report';
ALTER TABLE donor_medical_reports MODIFY COLUMN donor_id BIGINT NOT NULL COMMENT 'Foreign key referencing donors table';
ALTER TABLE donor_medical_reports MODIFY COLUMN doctor_notes TEXT COMMENT 'Notes and observations by the doctor';
ALTER TABLE donor_medical_reports MODIFY COLUMN health_status VARCHAR(50) COMMENT 'Current health status of the donor';
ALTER TABLE donor_medical_reports MODIFY COLUMN is_approved BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Whether the donation is approved by doctor';
ALTER TABLE donor_medical_reports MODIFY COLUMN appointment_date TIMESTAMP NULL COMMENT 'Date and time of the medical appointment';
ALTER TABLE donor_medical_reports MODIFY COLUMN doctor_id BIGINT NOT NULL COMMENT 'Foreign key referencing doctors table';
ALTER TABLE donor_medical_reports MODIFY COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp';
ALTER TABLE donor_medical_reports MODIFY COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp';
