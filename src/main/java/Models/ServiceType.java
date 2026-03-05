package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceType {
    private String serviceTypeId;
    private String name;
    private double baseFee;

    public ServiceType() { }

    public ServiceType(String serviceTypeId, String name, double baseFee) {
        this.serviceTypeId = serviceTypeId;
        this.name = name;
        this.baseFee = baseFee;
    }

    public String getServiceTypeId() { return serviceTypeId; }

    public void setServiceTypeId(String serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getBaseFee() { return baseFee; }

    public void setBaseFee(double baseFee) { this.baseFee = baseFee; }

    @Override
    public String toString() {
        return name + " (Fee: " + baseFee + ")";
    }
}
