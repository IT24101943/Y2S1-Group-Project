package core.controller;

import core.dto.MedicalNotesRequest;
import core.entity.DonorMedicalReport;
import core.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Doctor-related operations
 * Provides endpoints for Senior Doctors to manage donor health reports and donation appointments
 */
@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "*") // Allow CORS for frontend integration
public class DoctorController {
    
    private final DoctorService doctorService;
    
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    
    /**
     * Approve a donation
     * PUT /api/doctor/reports/{reportId}/approve
     * @param reportId the ID of the medical report to approve
     * @return ResponseEntity with the updated medical report
     */
    @PutMapping("/reports/{reportId}/approve")
    public ResponseEntity<DonorMedicalReport> approveDonation(@PathVariable Long reportId) {
        DonorMedicalReport updatedReport = doctorService.approveDonation(reportId);
        return ResponseEntity.ok(updatedReport);
    }
    
    /**
     * Reject a donation
     * PUT /api/doctor/reports/{reportId}/reject
     * @param reportId the ID of the medical report to reject
     * @return ResponseEntity with the updated medical report
     */
    @PutMapping("/reports/{reportId}/reject")
    public ResponseEntity<DonorMedicalReport> rejectDonation(@PathVariable Long reportId) {
        DonorMedicalReport updatedReport = doctorService.rejectDonation(reportId);
        return ResponseEntity.ok(updatedReport);
    }
    
    /**
     * Add medical notes to a report
     * PUT /api/doctor/reports/{reportId}/add-notes
     * @param reportId the ID of the medical report
     * @param request the medical notes request containing the notes
     * @return ResponseEntity with the updated medical report
     */
    @PutMapping("/reports/{reportId}/add-notes")
    public ResponseEntity<DonorMedicalReport> addMedicalNotes(
            @PathVariable Long reportId,
            @Valid @RequestBody MedicalNotesRequest request) {
        DonorMedicalReport updatedReport = doctorService.addMedicalNotes(reportId, request.getNotes());
        return ResponseEntity.ok(updatedReport);
    }
    
    /**
     * Get a medical report by donor ID
     * GET /api/doctor/reports/donor/{donorId}
     * @param donorId the donor's ID
     * @return ResponseEntity with the medical report
     */
    @GetMapping("/reports/donor/{donorId}")
    public ResponseEntity<DonorMedicalReport> getMedicalReport(@PathVariable Long donorId) {
        DonorMedicalReport report = doctorService.getMedicalReport(donorId);
        return ResponseEntity.ok(report);
    }
    
    /**
     * Get the latest medical report by donor ID
     * GET /api/doctor/reports/donor/{donorId}/latest
     * @param donorId the donor's ID
     * @return ResponseEntity with the latest medical report
     */
    @GetMapping("/reports/donor/{donorId}/latest")
    public ResponseEntity<DonorMedicalReport> getLatestMedicalReport(@PathVariable Long donorId) {
        DonorMedicalReport report = doctorService.getLatestMedicalReport(donorId);
        return ResponseEntity.ok(report);
    }
    
    /**
     * Get all medical reports for a specific doctor
     * GET /api/doctor/reports/doctor/{doctorId}
     * @param doctorId the doctor's ID
     * @return ResponseEntity with the list of medical reports
     */
    @GetMapping("/reports/doctor/{doctorId}")
    public ResponseEntity<List<DonorMedicalReport>> getMedicalReportsByDoctor(@PathVariable Long doctorId) {
        List<DonorMedicalReport> reports = doctorService.getMedicalReportsByDoctor(doctorId);
        return ResponseEntity.ok(reports);
    }
    
    /**
     * Get all approved medical reports
     * GET /api/doctor/reports/approved
     * @return ResponseEntity with the list of approved medical reports
     */
    @GetMapping("/reports/approved")
    public ResponseEntity<List<DonorMedicalReport>> getApprovedMedicalReports() {
        List<DonorMedicalReport> reports = doctorService.getApprovedMedicalReports();
        return ResponseEntity.ok(reports);
    }
    
    /**
     * Get all unapproved medical reports
     * GET /api/doctor/reports/unapproved
     * @return ResponseEntity with the list of unapproved medical reports
     */
    @GetMapping("/reports/unapproved")
    public ResponseEntity<List<DonorMedicalReport>> getUnapprovedMedicalReports() {
        List<DonorMedicalReport> reports = doctorService.getUnapprovedMedicalReports();
        return ResponseEntity.ok(reports);
    }
    
    /**
     * Get medical reports by health status
     * GET /api/doctor/reports/status/{healthStatus}
     * @param healthStatus the health status to filter by
     * @return ResponseEntity with the list of medical reports
     */
    @GetMapping("/reports/status/{healthStatus}")
    public ResponseEntity<List<DonorMedicalReport>> getMedicalReportsByHealthStatus(@PathVariable String healthStatus) {
        List<DonorMedicalReport> reports = doctorService.getMedicalReportsByHealthStatus(healthStatus);
        return ResponseEntity.ok(reports);
    }
    
    /**
     * Create a new medical report
     * POST /api/doctor/reports
     * @param report the medical report to create
     * @return ResponseEntity with the created medical report
     */
    @PostMapping("/reports")
    public ResponseEntity<DonorMedicalReport> createMedicalReport(@Valid @RequestBody DonorMedicalReport report) {
        DonorMedicalReport createdReport = doctorService.createMedicalReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }
    
    /**
     * Update an existing medical report
     * PUT /api/doctor/reports/{reportId}
     * @param reportId the ID of the medical report to update
     * @param report the updated medical report data
     * @return ResponseEntity with the updated medical report
     */
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<DonorMedicalReport> updateMedicalReport(
            @PathVariable Long reportId,
            @Valid @RequestBody DonorMedicalReport report) {
        report.setId(reportId); // Ensure the ID is set correctly
        DonorMedicalReport updatedReport = doctorService.updateMedicalReport(report);
        return ResponseEntity.ok(updatedReport);
    }
    
    /**
     * Delete a medical report
     * DELETE /api/doctor/reports/{reportId}
     * @param reportId the ID of the medical report to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteMedicalReport(@PathVariable Long reportId) {
        doctorService.deleteMedicalReport(reportId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Check if a medical report exists for a donor
     * GET /api/doctor/reports/donor/{donorId}/exists
     * @param donorId the donor's ID
     * @return ResponseEntity with boolean indicating if report exists
     */
    @GetMapping("/reports/donor/{donorId}/exists")
    public ResponseEntity<Boolean> hasMedicalReport(@PathVariable Long donorId) {
        boolean exists = doctorService.hasMedicalReport(donorId);
        return ResponseEntity.ok(exists);
    }
    
    /**
     * Get statistics for a doctor
     * GET /api/doctor/statistics/{doctorId}
     * @param doctorId the doctor's ID
     * @return ResponseEntity with doctor statistics
     */
    @GetMapping("/statistics/{doctorId}")
    public ResponseEntity<DoctorStatistics> getDoctorStatistics(@PathVariable Long doctorId) {
        long[] stats = doctorService.getDoctorStatistics(doctorId);
        DoctorStatistics statistics = new DoctorStatistics(stats[0], stats[1], stats[2]);
        return ResponseEntity.ok(statistics);
    }
    
    /**
     * Inner class for doctor statistics response
     */
    public static class DoctorStatistics {
        private long totalReports;
        private long approvedReports;
        private long rejectedReports;
        
        public DoctorStatistics(long totalReports, long approvedReports, long rejectedReports) {
            this.totalReports = totalReports;
            this.approvedReports = approvedReports;
            this.rejectedReports = rejectedReports;
        }
        
        // Getters and Setters
        public long getTotalReports() {
            return totalReports;
        }
        
        public void setTotalReports(long totalReports) {
            this.totalReports = totalReports;
        }
        
        public long getApprovedReports() {
            return approvedReports;
        }
        
        public void setApprovedReports(long approvedReports) {
            this.approvedReports = approvedReports;
        }
        
        public long getRejectedReports() {
            return rejectedReports;
        }
        
        public void setRejectedReports(long rejectedReports) {
            this.rejectedReports = rejectedReports;
        }
    }
}
