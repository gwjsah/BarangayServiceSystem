package Models;

import java.time.LocalDateTime;

public class Receipt {

    private String receiptNo;
    private PaymentTransaction paymentTransaction;

    private LocalDateTime printedDate;

    public Receipt(String receiptNo, PaymentTransaction paymentTransaction) {
        this.receiptNo = receiptNo;
        this.paymentTransaction = paymentTransaction;
        this.printedDate = LocalDateTime.now();
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public LocalDateTime getPrintedDate() {
        return printedDate;
    }

    @Override
    public String toString() {
        return "Receipt No: " + receiptNo +
                "\nTransaction ID: " + paymentTransaction.getTransactionId() +
                "\nAmount Paid: " + paymentTransaction.getAmountPaid() +
                "\nPayment Method: " + paymentTransaction.getPaymentMethod() +
                "\nPrinted Date: " + printedDate;
    }
}