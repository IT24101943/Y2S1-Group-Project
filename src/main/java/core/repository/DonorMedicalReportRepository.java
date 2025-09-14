package core.repository;

import core.entity.DonorMedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for DonorMedicalReport entity
 * Provides data access methods for medical reports using Spring Data JPA
 */
@Repository
public interface DonorMedicalReportRepository extends JpaRepository<DonorMedicalReport, Long> {
    
    /**
     * Find medical report by donor ID
     * @param donorId the donor's ID
     * @return Optional containing the medical report if found
     */
    Optional<DonorMedicalReport> findByDonorId(Long donorId);
    
    /**
     * Find medical report by donor ID and appointment date
     * @param donorId the donor's ID
     * @param appointmentDate the appointment date
     * @return Optional containing the medical report if found
     */
    Optional<DonorMedicalReport> findByDonorIdAndAppointmentDate(Long donorId, LocalDateTime appointmentDate);
    
    /**
     * Find all medical reports by doctor ID
     * @param doctorId the doctor's ID
     * @return List of medical reports for the doctor
     */
    List<DonorMedicalReport> findByDoctorId(Long doctorId);
    
    /**
     * Find all approved medical reports
     * @return List of approved medical reports
     */
    List<DonorMedicalReport> findByIsApprovedTrue();
    
    /**
     * Find all unapproved medical reports
     * @return List of unapproved medical reports
     */
    List<DonorMedicalReport> findByIsApprovedFalse();
    
    /**
     * Find medical reports by health status
     * @param healthStatus the health status to search for
     * @return List of medical reports with the specified health status
     */
    List<DonorMedicalReport> findByHealthStatus(String healthStatus);
    
    /**
     * Find medical reports by appointment date range
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return List of medical reports within the date range
     */
    @Query("SELECT d FROM DonorMedicalReport d WHERE d.appointmentDate BETWEEN :startDate AND :endDate")
    List<DonorMedicalReport> findByAppointmentDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                          @Param("endDate") LocalDateTime endDate);
    
    /**
     * Count medical reports by doctor ID
     * @param doctorId the doctor's ID
     * @return Number of medical reports for the doctor
     */
    long countByDoctorId(Long doctorId);
    
    /**
     * Count approved medical reports by doctor ID
     * @param doctorId the doctor's ID
     * @return Number of approved medical reports for the doctor
     */
    long countByDoctorIdAndIsApprovedTrue(Long doctorId);
    
    /**
     * Find the latest medical report for a donor
     * @param donorId the donor's ID
     * @return Optional containing the latest medical report if found
     */
    @Query("SELECT d FROM DonorMedicalReport d WHERE d.donorId = :donorId ORDER BY d.createdAt DESC")
    Optional<DonorMedicalReport> findLatestByDonorId(@Param("donorId") Long donorId);
}