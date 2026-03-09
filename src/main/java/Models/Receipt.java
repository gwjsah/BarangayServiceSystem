package Models;

import Models.Enums.PaymentMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Receipt {

//    private String receiptNo;
    private PaymentTransaction pT;

    private final LocalDateTime printedDate;

    public Receipt(PaymentTransaction paymentTransaction) {
        this.pT = paymentTransaction;
        this.printedDate = LocalDateTime.now();
    }

    public PaymentTransaction getPaymentTransaction() {
        return pT;
    }

    public LocalDateTime getPrintedDate() {
        return printedDate;
    }

    public void printReceipt(String processedBy, ArrayList<ServiceRequest> requests) {
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
        System.out.println("Transaction ID : " + pT.getId());
        System.out.println("Date           : " + dtf.format(printedDate));
        System.out.println("Payment Method : " + pT.getPaymentMethod());
        System.out.println("Processed By   : " + processedBy);
        System.out.println("-------------------------------");
        System.out.println("Items Paid:");

        int count = 1;
        for (ServiceRequest req : requests) {
            System.out.printf("%d) %-25s - %.2f\n", count++, req.getServiceName(), req.getFee());
        }

        System.out.println("-------------------------------");
        System.out.printf("Total Amount : %.2f\n", total);
        System.out.println("Status      : Paid");
        System.out.println("\nThank you for your payment!");
        System.out.println("===============================");
    }

    @Override
    public String toString() {
        return  "Transaction ID: " + pT.getId() +
                "\nAmount Paid: " + pT.getAmountPaid() +
                "\nPayment Method: " + pT.getPaymentMethod() +
                "\nPrinted Date: " + printedDate;
    }
}