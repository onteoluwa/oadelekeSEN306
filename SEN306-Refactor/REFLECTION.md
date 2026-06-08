# REFLECTION.md – SEN306 Project: Refactor processCustomer

---

## Question 1: How did you achieve functional cohesion? Which routines did you extract?

Functional cohesion means each routine does **one clearly defined job and nothing else**.

The original `processCustomer` was doing at least five unrelated things at once:
summing orders, computing a discount, building a message, printing, and sending an email.
That is **communicational cohesion** at best (multiple tasks sharing the same data) and
makes the code hard to test, reuse, or change.

I broke it into five focused routines:

| Routine | Single responsibility | Cohesion level |
|---|---|---|
| `calculateOrderTotal(orders)` | Sum the order array | Functional |
| `discountRate(customerType)` | Return the correct discount rate | Functional |
| `calculateDiscountedTotal(subtotal, type)` | Apply discount and return final total | Functional |
| `buildReceiptMessage(customer, total)` | Compose the greeting string | Functional |
| `notifyCustomer(customer, message)` | Print and optionally email | Functional |

Each routine can now be tested in isolation. For example, I can call
`calculateOrderTotal` with a known array and assert the result without needing
a Customer object or a printer at all.

---

## Question 2: What parameter passing issues did you encounter?

The original code had this line at the end:

```java
d = total; // tries to update d?
```

This **does not work** in Java. Java uses **pass-by-value** for all primitive types,
including `double`. When `processCustomer` received `d`, it received a *copy* of
the caller's variable. Assigning `d = total` only changed that local copy inside
the method. The moment the method returned, the change was thrown away. The caller's
original variable was never updated.

To fix this properly I changed the return type of `processCustomer` from `void` to
`double` and returned `total` directly:

```java
static double processCustomer(Customer customer, int customerType, double[] orders) {
    ...
    return total;
}
```

The caller then stores the result:

```java
double finalTotal = processCustomer(alice, 1, aliceOrders);
```

This is the standard Java pattern for "returning a computed value" — no tricks needed.

---

## Question 3: How would the `d` update behave differently with pass-by-value-result?

In **pass-by-value-result** (also called copy-in/copy-out), the mechanism works in
two stages:

1. **Copy-in:** At the moment of the call, the value of the actual argument is copied
   into the parameter — exactly like pass-by-value.
2. **Copy-out:** At the moment the routine *returns*, the final value of the parameter
   is copied *back* to the original variable in the caller.

If Java used pass-by-value-result, the line `d = total;` **would actually work**.
Here is what would happen step by step:

- Caller passes `d = 0.0` → a copy `0.0` enters the method.
- Inside the method, the parameter is reassigned: `d = total` (e.g., `d = 90.0`).
- On return, that final value `90.0` is **written back** to the caller's variable `d`.
- After the call, the caller's `d` would equal `90.0`.

This is a meaningful behavioural difference. Under pure pass-by-value (Java's actual
rule), the caller sees no change. Under pass-by-value-result, the caller's variable
is updated. Languages like Ada and older Fortran use pass-by-value-result for `out`
and `in out` parameters.

The safer and clearer Java solution is still to use a return value, because it makes
the data flow explicit and visible to anyone reading the calling code.
