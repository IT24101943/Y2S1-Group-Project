package core.service;

import core.entity.DonorMedicalReport;
import core.exception.ResourceNotFoundException;
import core.repository.DonorMedicalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Doctor-related business logic
 * Handles medical report management operations for Senior Doctors
 */
@Service
@Transactional
public class DoctorService {
    
    private final DonorMedicalReportRepository donorMedicalReportRepository;
    
    @Autowired
    public DoctorService(DonorMedicalReportRepository donorMedicalReportRepository) {
        this.donorMedicalReportRepository = donorMedicalReportRepository;
    }
    
    /**
     * Approve a donation by setting isApproved to true
     * @param reportId the ID of the medical report to approve
     * @return the updated medical report
     * @throws ResourceNotFoundException if the report is not found
     */
    public DonorMedicalReport approveDonation(Long reportId) {
        DonorMedicalReport report = donorMedicalReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical report not found with id: " + reportId));
        
        report.setIsApproved(true);
        report.setHealthStatus("Fit to Donate");
        // updatedAt will be set automatically by @PreUpdate callback
        
        return donorMedicalReportRepository.save(report);
    }
    
    /**
     * Reject a donation by setting isApproved to false and healthStatus to "Unfit"
     * @param reportId the ID of the medical report to reject
     * @return the updated medical report
     * @throws ResourceNotFoundException if the report is not found
     */
    public DonorMedicalReport rejectDonation(Long reportId) {
        DonorMedicalReport report = donorMedicalReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical report not found with id: " + reportId));
        
        report.setIsApproved(false);
        report.setHealthStatus("Unfit");
        // updatedAt will be set automatically by @PreUpdate callback
        
        return donorMedicalReportRepository.save(report);
    }
    
    /**
     * Add medical notes to an existing report
     * @param reportId the ID of the medical report
     * @param notes the notes to add
     * @return the updated medical report
     * @throws ResourceNotFoundException if the report is not found
     */
    public DonorMedicalReport addMedicalNotes(Long reportId, String notes) {
        DonorMedicalReport report = donorMedicalReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical report not found with id: " + reportId));
        
        String existingNotes = report.getDoctorNotes() != null ? report.getDoctorNotes() : "";
        String updatedNotes = existingNotes.isEmpty() ? notes : existingNotes + "\n\n" + notes;
        
        report.setDoctorNotes(updatedNotes);
        // updatedAt will be set automatically by @PreUpdate callback
        
        return donorMedicalReportRepository.save(report);
    }
    
    /**
     * Get a medical report by donor ID
     * @param donorId the donor's ID
     * @return the medical report if found
     * @throws ResourceNotFoundException if the report is not found
     */
    @Transactional(readOnly = true)
    public DonorMedicalReport getMedicalReport(Long donorId) {
        return donorMedicalReportRepository.findByDonorId(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical report not found for donor id: " + donorId));
    }
    
    /**
     * Get the latest medical report by donor ID
     * @param donorId the donor's ID
     * @return the latest medical report if found
     * @throws ResourceNotFoundException if the report is not found
     */
    @Transactional(readOnly = true)
    public DonorMedicalReport getLatestMedicalReport(Long donorId) {
        return donorMedicalReportRepository.findLatestByDonorId(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("No medical report found for donor id: " + donorId));
    }
    
    /**
     * Get all medical reports for a specific doctor
     * @param doctorId the doctor's ID
     * @return list of medical reports for the doctor
     */
    @Transactional(readOnly = true)
    public List<DonorMedicalReport> getMedicalReportsByDoctor(Long doctorId) {
        return donorMedicalReportRepository.findByDoctorId(doctorId);
    }
    
    /**
     * Get all approved medical reports
     * @return list of approved medical reports
     */
    @Transactional(readOnly = true)
    public List<DonorMedicalReport> getApprovedMedicalReports() {
        return donorMedicalReportRepository.findByIsApprovedTrue();
    }
    
    /**
     * Get all unapproved medical reports
     * @return list of unapproved medical reports
     */
    @Transactional(readOnly = true)
    public List<DonorMedicalReport> getUnapprovedMedicalReports() {
        return donorMedicalReportRepository.findByIsApprovedFalse();
    }
    
    /**
     * Get medical reports by health status
     * @param healthStatus the health status to filter by
     * @return list of medical reports with the specified health status
     */
    @Transactional(readOnly = true)
    public List<DonorMedicalReport> getMedicalReportsByHealthStatus(String healthStatus) {
        return donorMedicalReportRepository.findByHealthStatus(healthStatus);
    }
    
    /**
     * Create a new medical report
     * @param report the medical report to create
     * @return the created medical report
     */
    public DonorMedicalReport createMedicalReport(DonorMedicalReport report) {
        // Timestamps will be set automatically by @PrePersist callback
        return donorMedicalReportRepository.save(report);
    }
    
    /**
     * Update an existing medical report
     * @param report the medical report to update
     * @return the updated medical report
     */
    public DonorMedicalReport updateMedicalReport(DonorMedicalReport report) {
        // updatedAt will be set automatically by @PreUpdate callback
        return donorMedicalReportRepository.save(report);
    }
    
    /**
     * Delete a medical report
     * @param reportId the ID of the medical report to delete
     * @throws ResourceNotFoundException if the report is not found
     */
    public void deleteMedicalReport(Long reportId) {
        if (!donorMedicalReportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Medical report not found with id: " + reportId);
        }
        donorMedicalReportRepository.deleteById(reportId);
    }
    
    /**
     * Check if a medical report exists for a donor
     * @param donorId the donor's ID
     * @return true if a report exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean hasMedicalReport(Long donorId) {
        return donorMedicalReportRepository.findByDonorId(donorId).isPresent();
    }
    
    /**
     * Get statistics for a doctor
     * @param doctorId the doctor's ID
     * @return array with [total reports, approved reports, rejected reports]
     */
    @Transactional(readOnly = true)
    public long[] getDoctorStatistics(Long doctorId) {
        long totalReports = donorMedicalReportRepository.countByDoctorId(doctorId);
        long approvedReports = donorMedicalReportRepository.countByDoctorIdAndIsApprovedTrue(doctorId);
        long rejectedReports = totalReports - approvedReports;
        
        return new long[]{totalReports, approvedReports, rejectedReports};
    }
}