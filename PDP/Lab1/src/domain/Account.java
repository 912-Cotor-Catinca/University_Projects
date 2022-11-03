package domain;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    public int uid;
    public int balance;
    public Log log;
    public int initialBalance;

    public Lock mutex;

    public Account(int uid, int balance){
        this.uid = uid;
        this.initialBalance = balance;
        this.balance = balance;
        this.mutex = new ReentrantLock();
        this.log = new Log();
    }

    public void logTransfer(Operation.OperationType type, int src, int dest, int sum, long timestamp){
        log.log(type, sum, src, dest, timestamp);
    }

    public boolean check() {
        int initialBalance = this.initialBalance;
        for(Operation operation : this.log.operations) {
            if(operation.type == Operation.OperationType.SEND)
                initialBalance -= operation.amount;
            else
                initialBalance += operation.amount;
        }
        return initialBalance == this.balance;
    }

    public boolean makeTransfer(Account other, int sum) {
        if (sum > balance)
            return false;
        if (this.uid < other.uid){
            this.mutex.lock();
            other.mutex.lock();
        }
        else {
            other.mutex.lock();
            this.mutex.lock();
        }

        balance -= sum;
        other.balance += sum;
        long timestamp = System.currentTimeMillis();
        logTransfer(Operation.OperationType.SEND, this.uid, other.uid, sum, timestamp);
        other.logTransfer(Operation.OperationType.RECEIVE, other.uid, this.uid, sum, timestamp);

        this.mutex.unlock();
        other.mutex.unlock();
        return true;
    }
}
