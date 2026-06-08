public class OverdraftAccount extends BankAccount {

    private static final double OVERDRAFT_LIMIT = -500.0;
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) return;

        double newBalance = balance - amount;
        if (newBalance >= OVERDRAFT_LIMIT) {
            balance = newBalance;
            System.out.println("[LOG] Withdrew £" + amount
                    + " | New balance: £" + balance);
        } else {
            System.out.println("[LOG] Withdrawal of £" + amount
                    + " refused — overdraft limit of £" + OVERDRAFT_LIMIT + " would be exceeded.");
        }
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);   
        System.out.println("[LOG] Deposited £" + amount
                + " | New balance: £" + balance);
    }
}
