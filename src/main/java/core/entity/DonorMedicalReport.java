package core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DonorMedicalReport entity representing a donor's medical status and doctor's assessment
 * This entity stores medical reports created by doctors for donor evaluations
 */
@Entity
@Table(name = "donor_medical_reports")
public class DonorMedicalReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Donor ID is required")
    @Column(name = "donor_id", nullable = false)
    private Long donorId;
    
    @Size(max = 1000, message = "Doctor notes must not exceed 1000 characters")
    @Column(name = "doctor_notes", length = 1000)
    private String doctorNotes;
    
    @Size(max = 50, message = "Health status must not exceed 50 characters")
    @Column(name = "health_status", length = 50)
    private String healthStatus;
    
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;
    
    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;
    
    @NotNull(message = "Doctor ID is required")
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Default constructor
    public DonorMedicalReport() {}
    
    // Constructor with parameters
    public DonorMedicalReport(Long donorId, String doctorNotes, String healthStatus, 
                             Boolean isApproved, LocalDateTime appointmentDate, Long doctorId) {
        this.donorId = donorId;
        this.doctorNotes = doctorNotes;
        this.healthStatus = healthStatus;
        this.isApproved = isApproved;
        this.appointmentDate = appointmentDate;
        this.doctorId = doctorId;
        // Timestamps will be set by @PrePersist callback
    }
    
    // JPA lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getDonorId() {
        return donorId;
    }
    
    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }
    
    public String getDoctorNotes() {
        return doctorNotes;
    }
    
    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }
    
    public String getHealthStatus() {
        return healthStatus;
    }
    
    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
    
    public Boolean getIsApproved() {
        return isApproved;
    }
    
    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public Long getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "DonorMedicalReport{" +
                "id=" + id +
                ", donorId=" + donorId +
                ", doctorNotes='" + doctorNotes + '\'' +
                ", healthStatus='" + healthStatus + '\'' +
                ", isApproved=" + isApproved +
                ", appointmentDate=" + appointmentDate +
                ", doctorId=" + doctorId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}