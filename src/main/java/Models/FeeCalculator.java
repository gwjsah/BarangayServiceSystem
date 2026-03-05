package Models;

public class FeeCalculator {

    // basic calculation using service type
    public static double calculate(ServiceType serviceType) {
        if (serviceType == null) {
            return 0;
        }

        return serviceType.getBaseFee();
    }

    // calculation with extras (ex: penalties or additional fees)
    public static double calculate(ServiceType serviceType, double extraFee) {
        if (serviceType == null) {
            return extraFee;
        }

        return serviceType.getBaseFee() + extraFee;
    }

    // calculation with discount
    public static double calculateWithDiscount(ServiceType serviceType, double discount) {
        if (serviceType == null) {
            return 0;
        }

        double total = serviceType.getBaseFee() - discount;

        if (total < 0) {
            total = 0;
        }

        return total;
    }
}