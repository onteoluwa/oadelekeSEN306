### 1. How did you achieve functional cohesion? Which routines did you extract?

I achieved functional cohesion by breaking the original `processCustomer()` method into smaller routines where each routine performs exactly one well-defined task

The routines are:

1. **`validateCustomerInput()`**

   * Validates customer type and order amounts

2. **`calculateOrderTotal()`**

   * Calculates the sum of all orders

3. **`applyDiscount()`**

   * Determines the correct discount rate and computes the discounted total

4. **`buildGreetingMessage()`**

   * Creates the customer message

5. **`sendCustomerEmail()`**

   * Handles email delivery and email validation

The remaining `processCustomer()` function acts only as an orchestrator, coordinating the workflow by calling the specialized routines in sequence

---

### 2. What parameter passing issues did you encounter (e.g., `d` modified but not returned)?

The main issue involved the variable d, which was modified inside the method but not returned. Since Java uses pass-by-value for primitive data types, changes made to d only affected a local copy and were not reflected outside the method. To fix this, the refactored version returns the updated value directly, making the data flow clear and ensuring the caller receives the correct result


---

### 3. How would the `d` update behave differently if the language used pass-by-value-result?
 Under pass-by-value-result, the assignment to `d` would be copied back to the caller, so the update would be visible outside the method. Under Java's pass-by-value semantics, the update is lost because only a local copy is modified
