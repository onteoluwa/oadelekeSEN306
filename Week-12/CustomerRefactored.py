from src.customer  import Customer
from src.constants import (
    CUSTOMER_TYPE_STANDARD,
    CUSTOMER_TYPE_VIP,
    STANDARD_DISCOUNT_RATE,
    VIP_DISCOUNT_RATE,
    VALID_CUSTOMER_TYPES,
)



# Routine 1 — validateCustomerInput
def validateCustomerInput(customer: Customer) -> None:
    if customer.customer_type not in VALID_CUSTOMER_TYPES:
        raise ValueError(
            f"Unknown customer_type '{customer.customer_type}'. "
            f"Must be one of {VALID_CUSTOMER_TYPES}."
        )

    for index, amount in enumerate(customer.orders):
        if amount < 0:
            raise ValueError(
                f"Order at index {index} is negative ({amount}). "
                "All order amounts must be non-negative."
            )


# Routine 2 — calculateOrderTotal
def calculateOrderTotal(orders: list) -> float:
    """
    Sums all individual order amounts and returns the gross total.

    This was originally inlined as:
        double sum = 0;
        for (int i = 0; i < x; i++) sum += orders[i];
    The extra parameter 'x' (order count) is also eliminated because
    Python's len() / sum() handles it implicitly.

    Args:
        orders: list of float order amounts (pre-validated as non-negative)

    Returns:
        Gross total (float)
    """
    return sum(orders)

# Routine 3 — applyDiscount
def applyDiscount(gross_total: float, customer_type: int) -> float:
    if customer_type == CUSTOMER_TYPE_VIP:
        rate = VIP_DISCOUNT_RATE
    else:
        rate = STANDARD_DISCOUNT_RATE

    return gross_total - gross_total * rate


# Routine 4 — buildGreetingMessage
def buildGreetingMessage(customer: Customer, discounted_total: float) -> str:
    msg = f"Hello {customer.name} of {customer.address}, your total is {discounted_total:.2f}"
    if customer.is_vip:
        msg += " (VIP)"
    return msg


# Routine 5 — sendCustomerEmail
def sendCustomerEmail(email: str, message: str) -> None:
    if not email:
        return  

    if "@" not in email:
        raise ValueError(f"Malformed email address: '{email}'")


    print(f"[EMAIL → {email}] {message}")


def processCustomer(customer: Customer) -> float:
    validateCustomerInput(customer)

    gross_total      = calculateOrderTotal(customer.orders)
    discounted_total = applyDiscount(gross_total, customer.customer_type)
    message          = buildGreetingMessage(customer, discounted_total)

    print(message)
    sendCustomerEmail(customer.email, message)

    return discounted_total  
