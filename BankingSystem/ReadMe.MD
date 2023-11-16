# Banking System

This project implements a simple banking system that allows users to create accounts, deposit and withdraw funds, and view their account balances. It also includes support for savings accounts with interest earning and checking accounts with overdraft protection.
Features

    Create savings and checking accounts
    Deposit funds into accounts
    Withdraw funds from accounts
    View account balances
    Calculate interest for savings accounts
    Apply overdraft for checking accounts

# Usage

To run the program, save the provided Java files (BankAccount.java, SavingsAccount.java, CheckingAccount.java, InterestCalculable.java, Overdrafteable.java, and Main.java) in a directory and compile them using the following command:

javac BankAccount.java SavingsAccount.java CheckingAccount.java InterestCalculable.java Overdrafteable.java Main.java

Once the files are compiled, run the program using the following command:

## java Main

The program will present a menu of options for creating accounts, performing transactions, and viewing balances. Follow the prompts to interact with the banking system.
Project Structure

# The project is organized into the following classes:

    BankAccount: Represents a generic bank account with attributes like account number, balance, and customer name.
    SavingsAccount: Inherits from BankAccount and implements the InterestCalculable interface. Provides methods for calculating interest.
    CheckingAccount: Inherits from BankAccount and implements the Overdrafteable interface. Provides methods for applying overdraft.
    InterestCalculable: Interface for calculating interest on an account.
    Overdrafteable: Interface for applying overdraft on an account.
    Main: The main class that handles user interaction and delegates operations to appropriate classes.