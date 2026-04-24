package io.viana.change_governance_service.dto;

public class DeployRequestDTO {

    private String executedBy;
    private String reason;

    public String getExecutedBy() { return executedBy; }
    public void setExecutedBy(String executedBy) { this.executedBy = executedBy; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}