package Models;

import Models.Enums.PaymentMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Receipt {

//    private String receiptNo;
    private PaymentTransaction paymentTransaction;

    private LocalDateTime printedDate;

    public Receipt(PaymentTransaction paymentTransaction) {
//        this.paymentTransaction = paymentTransaction;
        this.printedDate = LocalDateTime.now();
    }

//    public String getReceiptNo() {
//        return receiptNo;
//    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public LocalDateTime getPrintedDate() {
        return printedDate;
    }

    public static void printReceipt(String transactionId, String processedBy, PaymentMethod method, ArrayList<ServiceRequest> requests) {

        // Get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Calculate total
        double total = 0;
        for (ServiceRequest req : requests) {
            total += req.getFee();
        }

        // Print receipt
        System.out.println("===============================");
        System.out.println("         BARANGAY PAYMENT");
        System.out.println("===============================");
        System.out.println("Transaction ID : " + transactionId);
        System.out.println("Date          : " + dtf.format(now));
        System.out.println("Payment Method: " + method);
        System.out.println("Processed By  : " + processedBy);
        System.out.println("-------------------------------");
        System.out.println("Items Paid:");

        int count = 1;
        for (ServiceRequest req : requests) {
            System.out.printf("%d) %-25s - %.2f\n", count++, req.getServiceName(), req.getFee());
        }

        System.out.println("-------------------------------");
        System.out.printf("Total Amount: %.2f\n", total);
        System.out.println("Status      : Paid");
        System.out.println("\nThank you for your payment!");
        System.out.println("===============================");
    }

    @Override
    public String toString() {
        return  "Transaction ID: " + paymentTransaction.getId() +
                "\nAmount Paid: " + paymentTransaction.getAmountPaid() +
                "\nPayment Method: " + paymentTransaction.getPaymentMethod() +
                "\nPrinted Date: " + printedDate;
    }
}