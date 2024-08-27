
<h1 align="center">NumPax RESTful API</h1>
<p align="center">Back-end Development Project: Building a Fintech RESTful API with Java.</p>

<div align="center">

![Java](https://img.shields.io/badge/Java-v21-red)

</div>

##

## Table of contents

* [Project Description](#project-description)
* [Data Dictionary](#data-dictionary)
  <details>
    <summary>Click to expand!</summary>
  
  * [Account](#classentity-name-account)
  * [Category](#classentity-name-category)
  * [CheckingAccount](#classentity-name-checkingaccount)
  * [FixedInvestment](#classentity-name-fixedinvestment)
  * [GoalAccount](#classentity-name-goalaccount)
  * [InvestmentAccount](#classentity-name-investmentaccount)
  * [RegularAccount](#classentity-name-regularaccount)
  * [RelatedAccount](#classentity-name-relatedaccounts)
  * [SavingsAccount](#classentity-name-savingsaccount)
  * [Transaction](#classentity-name-transaction)
  * [User](#classentity-name-user)
  * [VariableInvestment](#classentity-name-variableinvestment)
  
  </details>

##

## Project Description

NumPax is a fintech RESTful API designed to empower users to manage their personal finances efficiently while also enhancing their financial knowledge and skills. Built using popular and well-established technologies, NumPax seamlessly integrates all aspects of a user's financial life — from budgeting to investments — into a cohesive API. It offers a robust educational framework, allowing users to make more informed decisions and foster better long-term financial health, all through a flexible and accessible interface.

##

# Data Dictionary

This document provides a comprehensive overview of the classes/entities used in the project, detailing the attributes, types, constraints, default values, and descriptions for each.

## Class/Entity Name: Account

**Definition:**  
Represents a general account entity in the system, used to manage financial operations.

| Attribute Name  | Type            | Size | Constraint | Default Value | Description                            |
|-----------------|-----------------|------|------------|---------------|----------------------------------------|
| id              | String          | 10   | PK         | UUID          | Unique identifier for the account.     |
| name            | String          | 30   | Not Null   | N/A           | Name of the account.                   |
| description     | String          | 50   | N/A        | N/A           | Description of the account.            |
| balance         | BigDecimal      | 7    | Not Null   | 0.0           | Current balance of the account.        |
| isActive        | boolean         | N/A  | N/A        | true          | Indicates if the account is active.    |
| user            | User            | 10   | N/A        | N/A           | Associated user with the account.      |
| createdAt       | LocalDateTime   | N/A  | Final      | Current Date  | The date when the account was created. |
| updatedAt       | LocalDateTime   | N/A  | N/A        | Current Date  | The date when the account was last updated. |

## Class/Entity Name: Category

**Definition:**  
Represents different categories used to classify accounts, transactions, etc.

| Attribute Name  | Type            | Size | Constraint | Default Value | Description                             |
|-----------------|-----------------|------|------------|---------------|-----------------------------------------|
| id              | String          | 10   | PK         | UUID          | Unique identifier for the category.     |
| name            | String          | 30   | Not Null   | N/A           | Name of the category.                   |
| description     | String          | 50   | N/A        | N/A           | Description of the category.            |
| icon            | String          | N/A  | N/A        | N/A           | Icon representing the category.         |
| categoryType    | CategoryType    | N/A  | N/A        | N/A           | Type of the category (e.g., Accounts, Transactions). |
| isActive        | boolean         | N/A  | N/A        | true          | Indicates if the category is active.    |
| isDefault       | boolean         | N/A  | N/A        | N/A           | Indicates if the category is default.   |
| createdAt       | LocalDateTime   | N/A  | Final      | Current Date  | The date when the category was created. |
| updatedAt       | LocalDateTime   | N/A  | N/A        | Current Date  | The date when the category was last updated. |

## Class/Entity Name: CheckingAccount

**Definition:**  
A specific type of RegularAccount used to manage checking accounts, including details about the bank and account number.

| Attribute Name  | Type            | Size | Constraint | Default Value | Description                            |
|-----------------|-----------------|------|------------|---------------|----------------------------------------|
| bankName        | String          | 20   | Not Null   | N/A           | Name of the bank associated with the account. |
| agency          | String          | 4    | N/A        | N/A           | Agency number of the account.          |
| accountNumber   | String          | 10   | Not Null   | N/A           | The account number.                    |

## Class/Entity Name: FixedInvestment

**Definition:**  
Represents a fixed investment product, capturing details like investment type, dates, institution, and financial metrics.

| Attribute Name       | Type               | Size | Constraint | Default Value | Description                                         |
|----------------------|--------------------|------|------------|---------------|-----------------------------------------------------|
| fixedInvestmentType  | FixedInvestmentType | 7   | Not Null   | N/A           | The type of fixed investment.                       |
| investmentDate       | LocalDate          | N/A  | Not Null   | N/A           | The date when the investment was made.              |
| expirationDate       | LocalDate          | N/A  | Not Null   | N/A           | The date when the investment expires.               |
| institution          | String             | 10   | Not Null   | N/A           | The financial institution managing the investment.  |
| TaxRates             | Double[]           | N/A  | N/A        | N/A           | The rates of return associated with the investment. |
| redeemValue          | Double             | N/A  | N/A        | N/A           | The value at which the investment can be redeemed.  |
| redeemDate           | LocalDate          | N/A  | N/A        | N/A           | The date when the investment can be redeemed.       |
| liquidityPeriod      | Integer            | N/A  | N/A        | N/A           | The period required for liquidity.                  |
| netGainLoss          | Double             | N/A  | N/A        | N/A           | Net gain or loss from the investment.               |
| updatedAt            | LocalDateTime      | N/A  | N/A        | Current Date  | The date when the investment was last updated.      |

## Class/Entity Name: GoalAccount

**Definition:**  
A type of RegularAccount focused on achieving specific financial goals, tracking target values, interest rates, and achievement metrics.

| Attribute Name          | Type            | Size | Constraint | Default Value | Description                                           |
|-------------------------|-----------------|------|------------|---------------|-------------------------------------------------------|
| targetValue             | Double          | N/A  | Not Null   | N/A           | The financial goal to be achieved.                    |
| amountValue             | Double          | N/A  | Not Null   | 0.0           | The accumulated value towards the goal.               |
| targetTaxRate           | Double          | N/A  | N/A        | N/A           | Interest rate applied to the target value.            |
| monthlyTaxRate          | Double          | N/A  | N/A        | N/A           | Monthly interest rate applied to the accumulated value. |
| monthlyEstimate         | Double          | N/A  | N/A        | N/A           | Estimated monthly contribution towards the goal.      |
| monthlyAchievement      | Double          | N/A  | N/A        | N/A           | Monthly target achievement.                           |
| category                | Category        | N/A  | N/A        | N/A           | Category associated with the goal.                    |
| targetDate              | LocalDate       | N/A  | Not Null   | N/A           | The target date to achieve the goal.                  |
| startDate               | LocalDate       | N/A  | N/A        | N/A           | Start date of the goal (if different from creation date). |
| endDate                 | LocalDate       | N/A  | N/A        | N/A           | The date when the goal is achieved.                   |

## Class/Entity Name: InvestmentAccount

**Definition:**  
An account for managing various investments, tracking total amounts, profits, and risk levels.

| Attribute Name          | Type            | Size | Constraint | Default Value | Description                                     |
|-------------------------|-----------------|------|------------|---------------|-------------------------------------------------|
| totalInvestedAmount      | Double          | N/A  | Not Null   | N/A           | Total amount invested over time.                |
| totalProfit              | Double          | N/A  | N/A        | N/A           | Total profit from investments.                  |
| totalCurrentAmount       | Double          | N/A  | N/A        | N/A           | Current total amount including profits and withdrawals. |
| totalWithdrawnAmount     | Double          | N/A  | N/A        | N/A           | Total amount withdrawn.                         |
| numberOfWithdrawals      | Double          | N/A  | N/A        | N/A           | Number of withdrawals made.                     |
| numberOfEntries          | Double          | N/A  | N/A        | N/A           | Number of investment entries.                   |
| numberOfAssets           | Double          | N/A  | N/A        | N/A           | Number of different assets held.                |
| averagePurchasePrice     | Double          | N/A  | N/A        | N/A           | Average purchase price of assets.               |
| totalGainLoss            | Double          | N/A  | N/A        | N/A           | Total gain or loss from investments.            |
| totalDividendYield       | Double          | N/A  | N/A        | N/A           | Total dividends received.                       |
| riskLevelType            | RiskLevelType   | N/A  | N/A        | N/A           | The risk level associated with the investments. |

## Class/Entity Name: RegularAccount

**Definition:**  
Represents a standard account that can be of various types, such as checking, savings, investment, or goal-oriented accounts.

| Attribute Name  | Type   | Size | Constraint | Default Value | Description                                |
|-----------------|--------|------|------------|---------------|--------------------------------------------|
| type            | String | N/A  | Not Null   | N/A           | The type of the account (e.g., Checking, Savings, Investment, Goal). |

## Class/Entity Name: RelatedAccounts

**Definition:**  
Represents a collection of related accounts, aggregating balances and other account-specific information.

| Attribute Name      | Type      | Size | Constraint | Default Value | Description                                   |
|---------------------|-----------|------|------------|---------------|-----------------------------------------------|
| totalBalance        | Double    | N/A  | N/A        | N/A           | The total balance of all related accounts.    |
| relatedAccounts     | Account[] | N/A  | N/A        | N/A           | Array of related accounts.                    |
| totalAccounts       | Integer   | N/A  | N/A        | N/A           | The total number of related accounts.         |

## Class/Entity Name: SavingsAccount

**Definition:**  
A type of RegularAccount designed for savings, tracking interest rates, and investment maturity.

| Attribute Name          | Type            | Size | Constraint | Default Value | Description                                       |
|-------------------------|-----------------|------|------------|---------------|---------------------------------------------------|
| nearestDeadline         | LocalDateTime   | N/A  | N/A        | N/A           | The nearest maturity date among the investments.  |
| furthestDeadline        | LocalDateTime   | N/A  | N/A        | N/A           | The furthest maturity date among the investments. |
| lastestDeadline         | LocalDateTime   | N/A  | N/A        | N/A           | The latest maturity date of the investments.      |
| averageTaxRate          | Double          | N/A  | N/A        | N/A           | The average interest rate of the savings accounts. |
| numberOfFixedInvestments| Double          | N/A  | N/A        | N/A           | The total number of different fixed-income investments. |
| totalMaturityAmount     | Double          | N/A  | N/A        | N/A           | The total expected value from all investments.    |
| totalDepositAmount      | Double          | N/A  | N/A        | N/A           | The total amount deposited into the investments.  |

## Class/Entity Name: Transaction

**Definition:**  
Represents a financial transaction, detailing the amount, nature, and associated accounts.

| Attribute Name          | Type                   | Size | Constraint | Default Value | Description                                              |
|-------------------------|------------------------|------|------------|---------------|----------------------------------------------------------|
| id                      | String                 | N/A  | Final      | UUID          | Unique identifier for the transaction.                   |
| code                    | String                 | N/A  | Not Null   | N/A           | Code of the transaction.                                 |
| name                    | String                 | N/A  | Not Null   | N/A           | Name of the transaction.                                 |
| description             | String                 | N/A  | N/A        | N/A           | Description of the transaction.                          |
| amount                  | BigDecimal             | N/A  | Not Null   | N/A           | Amount involved in the transaction.                      |
| category                | Category               | N/A  | N/A        | N/A           | Associated category of the transaction.                  |
| account                 | Account                | N/A  | N/A        | N/A           | Account associated with the transaction.                 |
| natureOfTransaction     | NatureOfTransaction    | N/A  | Not Null   | N/A           | Nature of the transaction (e.g., Income, Expense).       |
| receiver                | String                 | N/A  | N/A        | N/A           | Receiver of the transaction.                             |
| sender                  | String                 | N/A  | N/A        | N/A           | Sender of the transaction.                               |
| transactionDate         | LocalDate              | N/A  | Not Null   | N/A           | Date when the transaction occurred.                      |
| isRepeatable            | boolean                | N/A  | N/A        | false         | Indicates if the transaction is repeatable.              |
| repeatableType          | RepeatableType         | N/A  | N/A        | Never         | Type of repeatable schedule (e.g., Daily, Weekly).       |
| note                    | String                 | N/A  | N/A        | N/A           | Note associated with the transaction.                    |
| isActive                | boolean                | N/A  | N/A        | true          | Indicates if the transaction is active.                  |
| createdAt               | LocalDateTime          | N/A  | Final      | Current Date  | Date when the transaction was created.                   |
| updatedAt               | LocalDateTime          | N/A  | N/A        | Current Date  | Date when the transaction was last updated.              |
| isEffective             | boolean                | N/A  | N/A        | false         | Indicates if the transaction has been applied.           |

## Class/Entity Name: User

**Definition:**  
Represents a user in the system, capturing personal information and account status.

| Attribute Name  | Type            | Size | Constraint | Default Value | Description                             |
|-----------------|-----------------|------|------------|---------------|-----------------------------------------|
| id              | String          | 10   | PK         | UUID          | Unique identifier for the user.         |
| name            | String          | 30   | Not Null   | N/A           | Full name of the user.                  |
| email           | String          | 50   | Not Null   | N/A           | Email address of the user.              |
| password        | String          | 20   | Not Null   | N/A           | Password for user authentication.       |
| birthdate       | LocalDate       | N/A  | Not Null   | N/A           | Birthdate of the user.                  |
| isActive        | boolean         | N/A  | N/A        | true          | Indicates if the user account is active.|
| createdAt       | LocalDateTime   | N/A  | Final      | Current Date  | The date when the user was created.     |
| updatedAt       | LocalDateTime   | N/A  | N/A        | Current Date  | The date when the user was last updated.|

## Class/Entity Name: VariableInvestment

**Definition:**  
Represents a variable investment product, capturing details like asset type, broker, purchase date, and financial metrics.

| Attribute Name          | Type                    | Size | Constraint | Default Value | Description                                     |
|-------------------------|-------------------------|------|------------|---------------|-------------------------------------------------|
| variableInvestmentType   | VariableInvestmentType  | N/A  | Not Null   | N/A           | The type of variable investment.                |
| broker                  | String                  | N/A  | Not Null   | N/A           | The broker managing the investment.             |
| purchaseDate            | LocalDate               | N/A  | N/A        | N/A           | The date when the investment was purchased.     |
| expirationDate          | LocalDate               | N/A  | N/A        | N/A           | The date when the investment expires.           |
| assetCode               | String                  | N/A  | Final      | N/A           | The unique code identifying the asset.          |
| quantity                | BigDecimal              | N/A  | Not Null   | N/A           | The quantity of assets purchased.               |
| unitPrice               | BigDecimal              | N/A  | Not Null   | N/A           | The price per unit of the asset.                |
| saleDate                | LocalDate               | N/A  | N/A        | N/A           | The date when the investment was sold (if applicable). |
| salePrice               | BigDecimal              | N/A  | N/A        | N/A           | The price at which the asset was sold (if applicable). |
| brokerFees              | BigDecimal[]            | N/A  | N/A        | N/A           | The fees charged by the broker.                 |
| otherFees               | BigDecimal[]            | N/A  | N/A        | N/A           | Any other fees associated with the investment.  |
| updatedAt               | LocalDateTime           | N/A  | N/A        | Current Date  | The date when the investment was last updated.  |

##

###### tags: `NumPax` `Java` `RESTful API` `CRUD` `Back-end`
