DROP TABLE FixedInvestmentTaxRates CASCADE CONSTRAINTS;
DROP TABLE Fees CASCADE CONSTRAINTS;
DROP TABLE VariableInvestments CASCADE CONSTRAINTS;
DROP TABLE FixedInvestments CASCADE CONSTRAINTS;
DROP TABLE Transactions CASCADE CONSTRAINTS;
DROP TABLE AccountRelations CASCADE CONSTRAINTS;
DROP TABLE RelatedAccounts CASCADE CONSTRAINTS;
DROP TABLE InvestmentAccounts CASCADE CONSTRAINTS;
DROP TABLE GoalAccounts CASCADE CONSTRAINTS;
DROP TABLE SavingsAccounts CASCADE CONSTRAINTS;
DROP TABLE CheckingAccounts CASCADE CONSTRAINTS;
DROP TABLE Accounts CASCADE CONSTRAINTS;
DROP TABLE Accounts CASCADE CONSTRAINTS;
DROP TABLE Categories CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;

-- Create Users table
CREATE TABLE Users (
                       user_id         VARCHAR2(36) PRIMARY KEY,
                       name            VARCHAR2(100) NOT NULL,
                       email           VARCHAR2(320) UNIQUE NOT NULL,
                       password        VARCHAR2(256) NOT NULL,
                       birthdate       DATE,
                       is_active       NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1)) NOT NULL,
                       created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                       updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create Accounts table

CREATE TABLE Accounts (
                          account_id      VARCHAR2(36) PRIMARY KEY,
                          name            VARCHAR2(100) NOT NULL,
                          description     VARCHAR2(255),
                          balance         NUMBER(14,2) DEFAULT 0 NOT NULL,
                          account_type    VARCHAR2(50) NOT NULL,
                          is_active       NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1)) NOT NULL,
                          user_id         VARCHAR2(36) NOT NULL,
                          created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          CONSTRAINT fk_accounts_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create RelatedAccounts table
CREATE TABLE RelatedAccounts (
                                 account_id      VARCHAR2(36) PRIMARY KEY,
                                 total_balance   NUMBER(14,2),
                                 total_accounts  NUMBER(10),
                                 CONSTRAINT fk_related_accounts_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);

-- Create AccountRelations table
CREATE TABLE AccountRelations (
                                  parent_account_id    VARCHAR2(36) NOT NULL,
                                  related_account_id   VARCHAR2(36) NOT NULL,
                                  CONSTRAINT fk_account_relations_parent_account FOREIGN KEY (parent_account_id) REFERENCES RelatedAccounts(account_id),
                                  CONSTRAINT fk_account_relations_related_account FOREIGN KEY (related_account_id) REFERENCES Accounts(account_id)
);

-- Create CheckingAccounts table
CREATE TABLE CheckingAccounts (
                                  account_id      VARCHAR2(36) PRIMARY KEY,
                                  bank_code       VARCHAR2(4),
                                  agency          VARCHAR2(4),
                                  account_number  VARCHAR2(12),
                                  CONSTRAINT fk_checking_accounts_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);

-- Create SavingsAccounts table
CREATE TABLE SavingsAccounts (
                                 account_id                  VARCHAR2(36) PRIMARY KEY,
                                 nearest_deadline            TIMESTAMP,
                                 furthest_deadline           TIMESTAMP,
                                 latest_deadline             TIMESTAMP,
                                 average_tax_rate            NUMBER(5,2),
                                 number_of_fixed_investments NUMBER(14,2),
                                 total_maturity_amount       NUMBER(14,2),
                                 total_deposit_amount        NUMBER(14,2),
                                 CONSTRAINT fk_savings_accounts_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);

-- Create InvestmentAccounts table 
CREATE TABLE InvestmentAccounts (
                                    account_id              VARCHAR2(36) PRIMARY KEY,
                                    total_invested_amount   NUMBER(14,2),
                                    total_profit            NUMBER(14,2),
                                    total_current_amount    NUMBER(14,2),
                                    total_withdrawn_amount  NUMBER(14,2),
                                    number_of_withdrawals   NUMBER(14,2),
                                    number_of_entries       NUMBER(14,2),
                                    number_of_assets        NUMBER(14,2),
                                    average_purchase_price  NUMBER(14,2),
                                    total_gain_loss         NUMBER(14,2),
                                    total_dividend_yield    NUMBER(14,2),
                                    risk_level_type         VARCHAR2(50),
                                    investment_subtype      VARCHAR2(50),
                                    CONSTRAINT fk_investment_accounts_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
                                    CONSTRAINT chk_risk_level_type CHECK (risk_level_type IN ('VERY_LOW', 'LOW', 'MEDIUM', 'HIGH', 'VERY_HIGH')),
                                    CONSTRAINT chk_investment_subtype CHECK (investment_subtype IN ('FIXED_INVESTMENT', 'VARIABLE_INVESTMENT', 'STOCKS', 'BONDS', 'ETF', 'FII', 'CRYPTO', 'FOREX', 'OTHER'))
);

-- Create Categories table
CREATE TABLE Categories (
                            category_id     VARCHAR2(36) PRIMARY KEY,
                            name            VARCHAR2(50) NOT NULL,
                            description     VARCHAR2(255),
                            icon            VARCHAR2(100),
                            category_type   VARCHAR2(50) NOT NULL,
                            is_active       NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1)) NOT NULL,
                            is_default      NUMBER(1) DEFAULT 0 CHECK (is_default IN (0, 1)) NOT NULL,
                            created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            CONSTRAINT chk_category_type CHECK (category_type IN ('ACCOUNTS', 'TRANSACTIONS', 'PERSONAL', 'EXPENSE', 'INCOME', 'SAVINGS', 'INVESTMENT'))
);

-- Exemplo:
INSERT ALL
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
VALUES
    ('11111111-2222-3333-4444-555555555555', 'Salário', 'Receitas provenientes de salário', 'salary_icon.png', 'INCOME', 1, 1)
INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
VALUES
    ('66666666-7777-8888-9999-000000000000', 'Aluguel', 'Despesas com aluguel de imóvel', 'rent_icon.png', 'EXPENSE', 1, 1)
INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
VALUES
    ('22222222-3333-4444-5555-666666666666', 'Investimentos', 'Aplicações em investimentos', 'investment_icon.png', 'INVESTMENT', 1, 1)
SELECT 1 FROM dual;

-- Create GoalAccounts table
CREATE TABLE GoalAccounts (
                              account_id              VARCHAR2(36) PRIMARY KEY,
                              target_value            NUMBER(14,2),
                              amount_value            NUMBER(14,2),
                              target_tax_rate         NUMBER(5,2),
                              monthly_tax_rate        NUMBER(5,2),
                              monthly_estimate        NUMBER(14,2),
                              monthly_achievement     NUMBER(14,2),
                              category_id             VARCHAR2(36),
                              target_date             DATE,
                              start_date              DATE,
                              end_date                DATE,
                              CONSTRAINT fk_goal_accounts_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
                              CONSTRAINT fk_goal_accounts_category_id FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);

-- Create Transactions table
CREATE TABLE Transactions (
                              transaction_id          VARCHAR2(36) PRIMARY KEY,
                              code                    VARCHAR2(50),
                              is_effective            NUMBER(1) DEFAULT 0 CHECK (is_effective IN (0, 1)) NOT NULL,
                              name                    VARCHAR2(100) NOT NULL,
                              description             VARCHAR2(255),
                              amount                  NUMBER(14,2) NOT NULL,
                              category_id             VARCHAR2(36),
                              account_id              VARCHAR2(36) NOT NULL,
                              nature_of_transaction   VARCHAR2(50) NOT NULL,
                              receiver                VARCHAR2(100),
                              sender                  VARCHAR2(100),
                              transaction_date        DATE NOT NULL,
                              is_repeatable           NUMBER(1) DEFAULT 0 CHECK (is_repeatable IN (0, 1)) NOT NULL,
                              repeatable_type         VARCHAR2(50),
                              note                    VARCHAR2(255),
                              is_active               NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1)) NOT NULL,
                              created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                              updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                              CONSTRAINT fk_transactions_category_id FOREIGN KEY (category_id) REFERENCES Categories(category_id),
                              CONSTRAINT fk_transactions_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
                              CONSTRAINT chk_nature_of_transaction CHECK (nature_of_transaction IN ('GOAL', 'INVESTMENT', 'INCOME', 'EXPENSE', 'TRANSFER')),
                              CONSTRAINT chk_repeatable_type CHECK (repeatable_type IN ('NEVER', 'DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY', 'CUSTOM', 'NONE'))
);

-- Create FixedInvestments table
CREATE TABLE FixedInvestments (
                                  transaction_id          VARCHAR2(36) PRIMARY KEY,
                                  fixed_investment_type   VARCHAR2(50) NOT NULL,
                                  investment_date         DATE NOT NULL,
                                  expiration_date         DATE,
                                  institution             VARCHAR2(100),
                                  redeem_value            NUMBER(14,2),
                                  redeem_date             DATE,
                                  liquidity_period        NUMBER(10),
                                  net_gain_loss           NUMBER(14,2),
                                  CONSTRAINT fk_fixed_investments_transaction_id FOREIGN KEY (transaction_id) REFERENCES Transactions(transaction_id),
                                  CONSTRAINT chk_fixed_investment_type CHECK (fixed_investment_type IN ('SAVINGS', 'CDI', 'CDB', 'LCI', 'LCA', 'LC', 'CRI', 'CRA', 'FIDC', 'DEBENTURES', 'CDB_PRE', 'CDB_POS', 'LCI_PRE', 'LCI_POS', 'LCA_PRE', 'LCA_POS', 'LC_PRE', 'LC_POS', 'CRI_PRE', 'CRI_POS', 'CRA_PRE', 'CRA_POS', 'FIDC_PRE', 'FIDC_POS', 'DEBENTURES_PRE', 'DEBENTURES_POS'))
);

-- Create FixedInvestmentTaxRates table
CREATE TABLE FixedInvestmentTaxRates (
                                         tax_rate_id     VARCHAR2(36) PRIMARY KEY,
                                         transaction_id  VARCHAR2(36) NOT NULL,
                                         tax_rate        NUMBER(5,2) NOT NULL,
                                         CONSTRAINT fk_tax_rates_transaction_id FOREIGN KEY (transaction_id) REFERENCES FixedInvestments(transaction_id)
);

-- Create VariableInvestments table
CREATE TABLE VariableInvestments (
                                     transaction_id            VARCHAR2(36) PRIMARY KEY,
                                     variable_investment_type  VARCHAR2(50) NOT NULL,
                                     broker                    VARCHAR2(100),
                                     purchase_date             DATE NOT NULL,
                                     expiration_date           DATE,
                                     asset_code                VARCHAR2(50) NOT NULL,
                                     quantity                  NUMBER(14,2) NOT NULL,
                                     unit_price                NUMBER(14,2) NOT NULL,
                                     sale_date                 DATE,
                                     sale_price                NUMBER(14,2),
                                     CONSTRAINT fk_variable_investments_transaction_id FOREIGN KEY (transaction_id) REFERENCES Transactions(transaction_id),
                                     CONSTRAINT chk_variable_investment_type CHECK (variable_investment_type IN ('STOCK', 'FUND', 'TREASURY_BILL', 'SAVINGS_BOND', 'DEBENTURE', 'OTHERS', 'CRYPTOCURRENCY', 'REAL_ESTATE', 'COMMODITY', 'FOREX', 'INDEX', 'BONDS', 'ETF', 'REIT', 'P2P'))
);

-- Create Fees table
CREATE TABLE Fees (
                      fee_id          VARCHAR2(36) PRIMARY KEY,
                      transaction_id  VARCHAR2(36) NOT NULL,
                      fee_type        VARCHAR2(50) NOT NULL,
                      fee_amount      NUMBER(14,2) NOT NULL,