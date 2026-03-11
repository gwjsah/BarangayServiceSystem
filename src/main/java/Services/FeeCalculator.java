package Services;

import Models.ServiceRequest;
import Models.ServiceType;

public final class FeeCalculator {

    // basic calculation using service type
    public static double calculate(ServiceRequest serviceRequest) {
        if (serviceRequest == null) {
            return 0;
        }

        return serviceRequest.getFee();
    }

    // calculation with extras (ex: penalties or additional fees)
    public static double calculate(ServiceRequest serviceRequest, double extraFee) {
        if (serviceRequest == null) {
            return extraFee;
        }

        return serviceRequest.getFee() + extraFee;
    }

    // calculation with discount
    public static double calculateWithDiscount(ServiceRequest serviceRequest, double discount) {

        double total = serviceRequest.getFee() * discount;

        if (total < 0) {
            total = 0;
        }

        return total;
    }

    // calculation with discount for multiple requests
    public static double calculateWithDiscount(double baseFee, double discount) {

        double total = baseFee * discount;

        if (total < 0) {
            total = 0;
        }

        return total;
    }
}