package core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO class for medical notes request
 * Used to receive medical notes from the client
 */
public class MedicalNotesRequest {
    
    @NotBlank(message = "Medical notes are required")
    @Size(max = 1000, message = "Medical notes must not exceed 1000 characters")
    private String notes;
    
    // Default constructor
    public MedicalNotesRequest() {}
    
    // Constructor with parameters
    public MedicalNotesRequest(String notes) {
        this.notes = notes;
    }
    
    // Getters and Setters
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "MedicalNotesRequest{" +
                "notes='" + notes + '\'' +
                '}';
    }
}
