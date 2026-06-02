// ============================================================
//  SEN306 – Project: Refactor processCustomer
//  File   : CustomerRefactored.java
// ============================================================

// ---------- Customer class (replaces the 8 loose parameters) ----------
class Customer {
    String name;
    String address;
    String email;
    boolean isVIP;

    Customer(String name, String address, String email, boolean isVIP) {
        this.name    = name;
        this.address = address;
        this.email   = email;
        this.isVIP   = isVIP;
    }
}

// ---------- Main class with refactored routines ----------
public class CustomerRefactored {

    // --- Constants (replaces magic numbers 0.1 and 0.2) ---
    static final double DISCOUNT_STANDARD = 0.10;
    static final double DISCOUNT_PREMIUM  = 0.20;
    static final int    TYPE_STANDARD     = 1;
    static final int    TYPE_PREMIUM      = 2;

    // -------------------------------------------------------
    // 1. calculateOrderTotal
    //    Purpose : sum all orders in the array
    //    Cohesion: Functional
    // -------------------------------------------------------
    static double calculateOrderTotal(double[] orders) {
        // Input validation – no negative order amounts
        for (double order : orders) {
            if (order < 0) {
                throw new IllegalArgumentException(
                    "Order amounts must be non-negative. Found: " + order);
            }
        }
        double sum = 0;
        for (double order : orders) {
            sum += order;
        }
        return sum;
    }

    // -------------------------------------------------------
    // 2. discountRate
    //    Purpose : return the discount rate for a customer type
    //    Cohesion: Functional
    // -------------------------------------------------------
    static double discountRate(int customerType) {
        // Input validation – only types 1 and 2 are valid
        if (customerType == TYPE_STANDARD) return DISCOUNT_STANDARD;
        if (customerType == TYPE_PREMIUM)  return DISCOUNT_PREMIUM;
        throw new IllegalArgumentException(
            "Invalid customer type: " + customerType + ". Expected 1 or 2.");
    }

    // -------------------------------------------------------
    // 3. calculateDiscountedTotal
    //    Purpose : apply discount to a subtotal and return final total
    //    Cohesion: Functional
    // -------------------------------------------------------
    static double calculateDiscountedTotal(double subtotal, int customerType) {
        double rate = discountRate(customerType);
        return subtotal - subtotal * rate;
    }

    // -------------------------------------------------------
    // 4. buildReceiptMessage
    //    Purpose : build the greeting + total message string
    //    Cohesion: Functional
    // -------------------------------------------------------
    static String buildReceiptMessage(Customer customer, double total) {
        String msg = "Hello " + customer.name + " of " + customer.address
                     + ", your total is " + total;
        if (customer.isVIP) {
            msg += " (VIP)";
        }
        return msg;
    }

    // -------------------------------------------------------
    // 5. notifyCustomer
    //    Purpose : print message and optionally send email
    //    Cohesion: Functional
    // -------------------------------------------------------
    static void notifyCustomer(Customer customer, String message) {
        System.out.println(message);
        if (customer.email != null) {
            sendEmail(customer.email, message);
        }
    }

    // -------------------------------------------------------
    // 6. processCustomer  (refactored main routine)
    //    Now clean: delegates each concern to a focused routine
    //    Returns the final total (fixes the broken d = total bug)
    // -------------------------------------------------------
    static double processCustomer(Customer customer, int customerType,
                                  double[] orders) {
        double subtotal    = calculateOrderTotal(orders);
        double total       = calculateDiscountedTotal(subtotal, customerType);
        String message     = buildReceiptMessage(customer, total);
        notifyCustomer(customer, message);
        return total;   // caller can store this – no pass-by-value problem
    }

    // Stub – assume this exists elsewhere in the real system
    static void sendEmail(String email, String message) {
        System.out.println("[EMAIL to " + email + "]: " + message);
    }

    // -------------------------------------------------------
    // Quick demo / test
    // -------------------------------------------------------
    public static void main(String[] args) {
        Customer alice = new Customer("Alice", "Lagos", "alice@example.com", true);
        double[] aliceOrders = {50.0, 30.0, 20.0};

        double finalTotal = processCustomer(alice, 1, aliceOrders);
        System.out.println("Stored total: " + finalTotal);
    }
}
