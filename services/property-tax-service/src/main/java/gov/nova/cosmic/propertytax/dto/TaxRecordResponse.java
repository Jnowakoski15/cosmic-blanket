package gov.nova.cosmic.propertytax.dto;

import gov.nova.cosmic.propertytax.entity.TaxStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TaxRecordResponse {

    private UUID id;
    private UUID propertyId;
    private int taxYear;
    private BigDecimal assessedValue;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private TaxStatus status;
    private LocalDate dueDate;
    private LocalDate paidDate;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPropertyId() { return propertyId; }
    public void setPropertyId(UUID propertyId) { this.propertyId = propertyId; }
    public int getTaxYear() { return taxYear; }
    public void setTaxYear(int taxYear) { this.taxYear = taxYear; }
    public BigDecimal getAssessedValue() { return assessedValue; }
    public void setAssessedValue(BigDecimal assessedValue) { this.assessedValue = assessedValue; }
    public BigDecimal getTaxRate() { return taxRate; }
    public void setTaxRate(BigDecimal taxRate) { this.taxRate = taxRate; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public TaxStatus getStatus() { return status; }
    public void setStatus(TaxStatus status) { this.status = status; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }
}
