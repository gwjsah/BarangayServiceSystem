package Models;

import java.time.LocalDateTime;

import Models.Enums.PaymentStatus;

public class PaymentTransaction {

    private String transactionId;
    private String serviceRequestId;

    private double amountPaid;
    private String paymentMethod;

    private PaymentStatus paymentStatus;
    private LocalDateTime paidDate;

    public PaymentTransaction(String transactionId, String serviceRequestId, double amountPaid, String paymentMethod) {
        this.transactionId = transactionId;
        this.serviceRequestId = serviceRequestId;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;

        this.paymentStatus = PaymentStatus.pending;
        this.paidDate = null;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    // when payment is confirmed
    public void confirmPayment() {
        this.paymentStatus = PaymentStatus.paid;
        this.paidDate = LocalDateTime.now();
    }

    // if cancelled
    public void cancelPayment() {
        this.paymentStatus = PaymentStatus.failed;
    }
}