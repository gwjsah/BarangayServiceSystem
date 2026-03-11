package Models;

import java.time.LocalDateTime;
import java.util.UUID;

import Models.Enums.PaymentMethod;
import Models.Enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentTransaction {

    private String id;
    private String serviceRequestId;

    private double amountPaid;
    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;
    private String paidDate;

    public PaymentTransaction() { }

    public PaymentTransaction(String serviceRequestId, double amountPaid, PaymentMethod paymentMethod) {
        this.id = UUID.randomUUID().toString();
        this.serviceRequestId = serviceRequestId;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.pending;
        this.paidDate = "";
    }

    public String getId() {
        return id;
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaidDate() {
        return paidDate;
    }

    // when payment is confirmed
    public void confirmPayment() {
        this.paymentStatus = PaymentStatus.paid;
        this.paidDate = java.time.LocalDate.now().toString();
    }

    // if cancelled
    public void cancelPayment() {
        this.paymentStatus = PaymentStatus.cancelled;
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" +
                "id='" + id + '\'' +
                ", serviceRequestId='" + serviceRequestId + '\'' +
                ", amountPaid=" + amountPaid +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", paidDate='" + paidDate + '\'' +
                '}';
    }
}