package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DecimalFormat;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    BankAccount bankAccount;

    @BeforeEach
    public void initAccount(){
        bankAccount = new BankAccount(1000);
    }

    @ParameterizedTest
    @ValueSource(ints = {100,400,1000})
    public void withdrawPossibleTest(int amount){

        int balanceBeforeWithdraw = bankAccount.getBalance();

            assertTrue(bankAccount.withdraw(amount));
        assertEquals(bankAccount.getBalance(), balanceBeforeWithdraw - amount);

    }

    @ParameterizedTest
    @ValueSource(ints = {7000,9999,1001})
    public void withdrawNotPossibleBecauseNoEnoughMoneyTest(int amount){

        int balanceBeforeWithdraw = bankAccount.getBalance();

        assertFalse(bankAccount.withdraw(amount));
        assertEquals(bankAccount.getBalance(), balanceBeforeWithdraw);

    }

    @ParameterizedTest
    @ValueSource(ints = {-7000,-1,-1001})
    public void withdrawNotPossibleAmountNegativeTest(int amount){

        int balanceBeforeWithdraw = bankAccount.getBalance();

        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.withdraw(amount);

        });

    }


    @ParameterizedTest
    @ValueSource(ints = {100,400,1000})
    public void depositPossibleTest(int amount){

        int balanceBeforeDeposit = bankAccount.getBalance();

        assertEquals(bankAccount.deposit(amount),balanceBeforeDeposit+amount);

    }

    @ParameterizedTest
    @ValueSource(ints = {-989,-50,-1})
    public void depositNotPossibleAndThrowsErrorBecauseAmountNegativeTest(int amount){

        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.deposit(amount);

        });

    }

    @Test
    public void paymentWithDifferentsValuesTest(){

        DecimalFormat df = new DecimalFormat("#.##");


        double total_amount =5000;
        double interes = 0.05;
        int months = 12;

        assertEquals(df.format(bankAccount.payment(total_amount,interes,months)),"564,13");

        total_amount =10000;
        interes = 0.005;
        months = 24;

        assertEquals(df.format(bankAccount.payment(total_amount,interes,months)),"443,21");


    }

    @Test
    public void paymentWithZeroInterestTest() {
        DecimalFormat df = new DecimalFormat("#.##");

        double total_amount = 5000;
        double interest = 0;
        int months = 12;

        assertEquals("416,67", df.format(bankAccount.payment(total_amount, interest, months)));

        total_amount = 10000;
        interest = 0;
        months = 24;

        assertEquals("416,67", df.format(bankAccount.payment(total_amount, interest, months)));
    }

    @Test
    public void pendingWithDifferentsValuesTest(){

        DecimalFormat df = new DecimalFormat("#.##");


        double total_amount =5000;
        double interes = 0.05;
        int months = 12;
        int aftermonths = 5;

        assertEquals(df.format(bankAccount.pending(total_amount,interes,months,aftermonths)),"3264,25");

        total_amount =10000;
        interes = 0.005;
        months = 24;

        assertEquals(df.format(bankAccount.pending(total_amount,interes,months,aftermonths)),"8014,21");


    }

    @Test
    public void pendingWithMonthZeroTest(){



        double total_amount =5000;
        double interes = 0.05;
        int months = 12;
        int aftermonths = 0;

        assertEquals((bankAccount.pending(total_amount,interes,months,aftermonths)),total_amount);


    }
    @Test
    public void pendingWithZeroInterestTest() {
        DecimalFormat df = new DecimalFormat("#.##");

        double total_amount = 5000;
        double interest = 0;
        int months = 12;
        int aftermonths = 11;

        System.out.println(bankAccount.payment(total_amount,interest,months));

        assertEquals("416,67", df.format(bankAccount.pending(total_amount, interest ,months,aftermonths)));

        total_amount = 10000;
        interest = 0;
        months = 24;
        aftermonths = 7;


        assertEquals("7083,33", df.format(bankAccount.pending(total_amount, interest,months,aftermonths)));
    }


    @Test
    public void paymentThrowErrorArgumentNegativeTest(){

        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.payment(-1,1,3);

        });
        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.payment(1,-1,3);

        });
        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.payment(1,1,-3);

        });

    }

    @Test
    public void pendingThrowErrorArgumentNegativeTest(){

        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.pending(-1,1,5,2);

        });
        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.pending(1,-1,5,2);

        });
        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.pending(1,1,-3,2);

        });
        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.pending(1,1,5,-2);

        });

    }

    @Test
    public void pendingThrowErrorMonthSuperiorToNpaymentTest(){

        assertThrows(IllegalArgumentException.class,()->{

            bankAccount.pending(2,1,6,8);

        });
    }


}
