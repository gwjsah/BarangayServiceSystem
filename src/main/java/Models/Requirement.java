package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Requirement {
    private String requirementId;
    private String serviceTypeId;
    private String name;
    private String description;
    private boolean isRequired;

    public Requirement() { }

    public Requirement(String requirementId, String serviceTypeId, String name, String description, boolean isRequired) {
        this.requirementId = requirementId;
        this.serviceTypeId = serviceTypeId;
        this.name = name;
        this.description = description;
        this.isRequired = isRequired;
    }

    public String getRequirementId() { return requirementId; }

    public void setRequirementId(String requirementId) { this.requirementId = requirementId; }

    public String getServiceTypeId() { return serviceTypeId; }

    public void setServiceTypeId(String serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public boolean isRequired() { return isRequired; }

    public void setRequired(boolean required) { isRequired = required; }
}
