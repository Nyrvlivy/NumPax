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
DROP TABLE RegularAccounts CASCADE CONSTRAINTS;
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

-- Create RegularAccounts table
CREATE TABLE RegularAccounts (
                                 account_id      VARCHAR2(36) PRIMARY KEY,
                                 account_type    VARCHAR2(50) NOT NULL,
                                 CONSTRAINT fk_regular_accounts_account_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
                                 CONSTRAINT chk_account_type CHECK (account_type IN ('CHECKING', 'SAVINGS', 'INVESTMENT', 'GOAL'))
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
                                  CONSTRAINT fk_checking_accounts_account_id FOREIGN KEY (account_id) REFERENCES RegularAccounts(account_id)
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
                                 CONSTRAINT fk_savings_accounts_account_id FOREIGN KEY (account_id) REFERENCES RegularAccounts(account_id)
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
                                    CONSTRAINT fk_investment_accounts_account_id FOREIGN KEY (account_id) REFERENCES RegularAccounts(account_id),
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
                              CONSTRAINT fk_goal_accounts_account_id FOREIGN KEY (account_id) REFERENCES RegularAccounts(account_id),
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
                      CONSTRAINT fk_fees_transaction_id FOREIGN KEY (transaction_id) REFERENCES Transactions(transaction_id),
                      CONSTRAINT chk_fee_type CHECK (fee_type IN ('BROKER', 'OTHER'))
);

-- Create indexes
CREATE INDEX idx_accounts_user_id ON Accounts(user_id);
CREATE INDEX idx_transactions_account_id ON Transactions(account_id);
CREATE INDEX idx_transactions_category_id ON Transactions(category_id);
CREATE INDEX idx_goal_accounts_category_id ON GoalAccounts(category_id);

-- CADASTRO E ALTERAÇÃO

-- 1. Cadastrar os dados do usuário:
INSERT INTO Users (user_id, name, email, password, birthdate)
VALUES ('[USER_ID]', '[NOME]', '[EMAIL]', '[SENHA]', TO_DATE('[DATA_NASCIMENTO]', 'YYYY-MM-DD'));

-- Exemplo:
INSERT INTO Users (user_id, name, email, password, birthdate)
VALUES (
           'c0a80123-5a3c-4b4f-8326-fd77e2131f9e',
           'Frederico Adriel',
           'frederico.adriel@example.com',
           'p@assw0rd',
           TO_DATE('1999-11-10', 'YYYY-MM-DD')
       );

-- 2. Cadastrar os dados para a conta do usuário:
INSERT INTO Accounts (account_id, name, description, balance, is_active, user_id)
VALUES ('[ACCOUNT_ID]', '[NOME_CONTA]', '[DESCRIÇÃO]', [SALDO], [IS_ACTIVE], '[USER_ID]');

-- Exemplo:
INSERT INTO Accounts (account_id, name, description, balance, is_active, user_id)
VALUES (
           '550e8400-e29b-41d4-a716-446655440000',
           'Conta Corrente Principal',
           'Conta corrente para despesas diárias',
           2500.00,
           1,
           'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
       );

-- 3. Alterar todos os dados do usuário:
UPDATE Users
SET name = '[NOME]',
    email = '[EMAIL]',
    password = '[SENHA]',
    birthdate = TO_DATE('[DATA_NASCIMENTO]', 'YYYY-MM-DD'),
    is_active = [IS_ACTIVE],
    updated_at = CURRENT_TIMESTAMP
WHERE user_id = '[USER_ID]';

-- Exemplo:
UPDATE Users
SET name = 'Frederico Adriel Nino',
    email = 'frederico.nino@example.com',
    password = 'nov@S3nh@',
    birthdate = TO_DATE('2002-02-28', 'YYYY-MM-DD'),
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e';

-- 4. Cadastrar as receitas do usuário:
INSERT INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
)
VALUES (
           '[TRANSACTION_ID]', '[CÓDIGO]', [IS_EFFECTIVE], '[NOME]', '[DESCRIÇÃO]', [VALOR], '[CATEGORY_ID]',
           '[ACCOUNT_ID]', 'INCOME', '[RECEBEDOR]', '[REMETENTE]', TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'),
           [IS_REPEATABLE], '[REPEATABLE_TYPE]', '[NOTA]', [IS_ACTIVE]
       );

-- Exemplo:
INSERT INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
)
VALUES (
           '123e4567-e89b-12d3-a456-426614174000',
           'REC-20240928',
           1,
           'Salário Mensal',
           'Recebimento do Salário - Novembro/2024',
           5000.00,
           '11111111-2222-3333-4444-555555555555',
           '550e8400-e29b-41d4-a716-446655440000',
           'INCOME',
           'Frederico Adriel Nino',
           'Empresa Jogos Vorazes',
           TO_DATE('2024-09-28', 'YYYY-MM-DD'),
           1,
           'MONTHLY',
           'Salário depositado na conta corrente',
           1
       );

-- 5. Alterar todos os dados das receitas do usuário:
UPDATE Transactions
SET code = '[CÓDIGO]',
    is_effective = [IS_EFFECTIVE],
    name = '[NOME]',
    description = '[DESCRIÇÃO]',
    amount = [VALOR],
    category_id = '[CATEGORY_ID]',
    account_id = '[ACCOUNT_ID]',
    receiver = '[RECEBEDOR]',
    sender = '[REMETENTE]',
    transaction_date = TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'),
    is_repeatable = [IS_REPEATABLE],
    repeatable_type = '[REPEATABLE_TYPE]',
    note = '[NOTA]',
    is_active = [IS_ACTIVE],
    updated_at = CURRENT_TIMESTAMP
WHERE transaction_id = '[TRANSACTION_ID]' AND nature_of_transaction = 'INCOME'; -- Receita

-- Exemplo:
UPDATE Transactions
SET code = 'REC-20231101-ALT',
    is_effective = 1,
    name = 'Salário Mensal Ajustado',
    description = 'Recebimento do Salário com Bônus - Novembro/2024',
    amount = 5500.00,
    category_id = '11111111-2222-3333-4444-555555555555',
    account_id = '550e8400-e29b-41d4-a716-446655440000',
    receiver = 'Frederico Adriel Nino',
    sender = 'Empresa Jogos Vorazes',
    transaction_date = TO_DATE('2024-09-28', 'YYYY-MM-DD'),
    is_repeatable = 1,
    repeatable_type = 'MONTHLY',
    note = 'Inclui Bônus de Desempenho',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE transaction_id = '123e4567-e89b-12d3-a456-426614174000' AND nature_of_transaction = 'INCOME';

-- 6. Cadastrar as despesas do usuário:
INSERT INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
)
VALUES (
           '[TRANSACTION_ID]', '[CÓDIGO]', [IS_EFFECTIVE], '[NOME]', '[DESCRIÇÃO]', [VALOR], '[CATEGORY_ID]',
           '[ACCOUNT_ID]', 'EXPENSE', '[RECEBEDOR]', '[REMETENTE]', TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'),
           [IS_REPEATABLE], '[REPEATABLE_TYPE]', '[NOTA]', [IS_ACTIVE]
       );

-- Exemplo:
INSERT INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
)
VALUES (
           '987f6543-21ba-43dc-ba98-76543210fedc',
           'DESP-20231105',
           1,
           'Pagamento de Aluguel',
           'Pagamento do Aluguel - Novembro/2024',
           1500.00,
           '66666666-7777-8888-9999-000000000000',
           '550e8400-e29b-41d4-a716-446655440000',
           'EXPENSE',
           'Imobiliária Star Wars',
           'Frederico Adriel Nino',
           TO_DATE('2024-09-28', 'YYYY-MM-DD'),
           1,
           'MONTHLY',
           'Aluguel do Apartamento',
           1
       );

-- 7. Alterar todos os dados das despesas do usuário:
UPDATE Transactions
SET code = '[CÓDIGO]',
    is_effective = [IS_EFFECTIVE],
    name = '[NOME]',
    description = '[DESCRIÇÃO]',
    amount = [VALOR],
    category_id = '[CATEGORY_ID]',
    account_id = '[ACCOUNT_ID]',
    receiver = '[RECEBEDOR]',
    sender = '[REMETENTE]',
    transaction_date = TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'),
    is_repeatable = [IS_REPEATABLE],
    repeatable_type = '[REPEATABLE_TYPE]',
    note = '[NOTA]',
    is_active = [IS_ACTIVE],
    updated_at = CURRENT_TIMESTAMP
WHERE transaction_id = '[TRANSACTION_ID]' AND nature_of_transaction = 'EXPENSE'; -- Despesa

-- Exemplo:
UPDATE Transactions
SET code = 'DESP-20231105-ALT',
    is_effective = 1,
    name = 'Pagamento de Aluguel Ajustado',
    description = 'Pagamento do Aluguel com Desconto - Novembro/2024',
    amount = 1400.00,
    category_id = '66666666-7777-8888-9999-000000000000',
    account_id = '550e8400-e29b-41d4-a716-446655440000',
    receiver = 'Imobiliária Star Wars',
    sender = 'Frederico Adriel Nino',
    transaction_date = TO_DATE('2024-09-28', 'YYYY-MM-DD'),
    is_repeatable = 1,
    repeatable_type = 'MONTHLY',
    note = 'Desconto aplicado por antecipação',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE transaction_id = '987f6543-21ba-43dc-ba98-76543210fedc' AND nature_of_transaction = 'EXPENSE';

-- 8. Cadastrar os dados para investimentos:
-- Para investimentos fixos (FixedInvestments):
INSERT ALL
    INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
) VALUES (
             '[TRANSACTION_ID]', '[CÓDIGO]', [IS_EFFECTIVE], '[NOME]', '[DESCRIÇÃO]', [VALOR],
             '[CATEGORY_ID]', '[ACCOUNT_ID]', 'INVESTMENT', '[RECEBEDOR]', '[REMETENTE]',
             TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'), [IS_REPEATABLE], '[REPEATABLE_TYPE]',
             '[NOTA]', [IS_ACTIVE]
         )
INTO FixedInvestments (
    transaction_id, fixed_investment_type, investment_date, expiration_date,
    institution, redeem_value, redeem_date, liquidity_period, net_gain_loss
) VALUES (
             '[TRANSACTION_ID]', '[TIPO_INVESTIMENTO_FIXO]', TO_DATE('[DATA_INVESTIMENTO]', 'YYYY-MM-DD'),
             TO_DATE('[DATA_VENCIMENTO]', 'YYYY-MM-DD'), '[INSTITUIÇÃO]', [VALOR_RESGATE],
             TO_DATE('[DATA_RESGATE]', 'YYYY-MM-DD'), [PER?DO_LIQUIDEZ], [GANHO_PERDA_L?UIDO]
         )
SELECT * FROM dual;

-- Notas: Precisa de uma inserção em Transactions antes

-- Exemplo:
INSERT ALL
    INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
) VALUES (
             '11223344-5566-7788-99aa-bbccddeeff00',
             'INVFIX-20240928', 1, 'Aplicação em CDB',
             'Investimento em CDB de 12 meses',
             10000.00,
             '22222222-3333-4444-5555-666666666666',
             '550e8400-e29b-41d4-a716-446655440000',
             'INVESTMENT', 'Banco BOM',
             'Frederico Adriel Nino',
             TO_DATE('2024-09-28', 'YYYY-MM-DD'),
             0,
             'NONE',
             'CDB com rendimento de 8% a.a.', 1
         )
INTO FixedInvestments (
    transaction_id, fixed_investment_type, investment_date, expiration_date,
    institution, redeem_value, redeem_date, liquidity_period, net_gain_loss
) VALUES (
             '11223344-5566-7788-99aa-bbccddeeff00',
             'CDB',
             TO_DATE('2024-09-28', 'YYYY-MM-DD'),
             TO_DATE('2025-09-28', 'YYYY-MM-DD'),
             'Banco BOM', NULL, NULL, 365, NULL
         )
SELECT * FROM dual;

-- Para investimentos variáveis (VariableInvestments):
INSERT ALL
    INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
) VALUES (
             '[TRANSACTION_ID]', '[CÓDIGO]', [IS_EFFECTIVE], '[NOME]', '[DESCRIÇÃO]', [VALOR],
             '[CATEGORY_ID]', '[ACCOUNT_ID]', 'INVESTMENT', '[RECEBEDOR]', '[REMETENTE]',
             TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'), [IS_REPEATABLE], '[REPEATABLE_TYPE]',
             '[NOTA]', [IS_ACTIVE]
         )
INTO VariableInvestments (
    transaction_id, variable_investment_type, broker, purchase_date, expiration_date,
    asset_code, quantity, unit_price, sale_date, sale_price
) VALUES (
             '[TRANSACTION_ID]', '[TIPO_INVESTIMENTO_VARIÁVEL]', '[CORRETORA]', TO_DATE('[DATA_COMPRA]', 'YYYY-MM-DD'),
             TO_DATE('[DATA_VENCIMENTO]', 'YYYY-MM-DD'), '[CÓDIGO_ATIVO]', [QUANTIDADE], [PREÇO_UNITÁRIO],
             TO_DATE('[DATA_VENDA]', 'YYYY-MM-DD'), [PREÇO_VENDA]
         )
SELECT * FROM dual;

-- Exemplo:
INSERT ALL
    INTO Transactions (
    transaction_id, code, is_effective, name, description, amount, category_id,
    account_id, nature_of_transaction, receiver, sender, transaction_date,
    is_repeatable, repeatable_type, note, is_active
) VALUES (
             'ffeeddcc-bbaa-9988-7766-554433221100',
             'INVVAR-20240928', 1, 'Compra de Ações BOAS',
             'Compra de 200 ações da BOA Corp',
             8000.00,
             '22222222-3333-4444-5555-666666666666',
             '550e8400-e29b-41d4-a716-446655440000',
             'INVESTMENT',
             'Bolsa de Valores',
             'Frederico Adriel Nino',
             TO_DATE('2024-09-28', 'YYYY-MM-DD'),
             0,
             'NONE',
             'Investimento em ações para longo prazo', 1
         )
INTO VariableInvestments (
    transaction_id, variable_investment_type, broker, purchase_date, expiration_date,
    asset_code, quantity, unit_price, sale_date, sale_price
) VALUES (
             'ffeeddcc-bbaa-9988-7766-554433221100',
             'STOCK',
             'Corretora BOA',
             TO_DATE('2024-09-28', 'YYYY-MM-DD'),
             NULL,
             'XYZ3',
             200,
             40.00,
             NULL,
             NULL
         )
SELECT * FROM dual;

-- 9. Alterar todos os dados para investimentos do usuário:

-- Para investimentos fixos (FixedInvestments):
-- Bloco para atualizar um investimento fixo completo
BEGIN
    UPDATE Transactions
    SET code = '[CÓDIGO]',
        is_effective = [IS_EFFECTIVE],
        name = '[NOME]',
        description = '[DESCRIÇÃO]',
        amount = [VALOR],
        category_id = '[CATEGORY_ID]',
        account_id = '[ACCOUNT_ID]',
        receiver = '[RECEBEDOR]',
        sender = '[REMETENTE]',
        transaction_date = TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'),
        is_repeatable = [IS_REPEATABLE],
        repeatable_type = '[REPEATABLE_TYPE]',
        note = '[NOTA]',
        is_active = [IS_ACTIVE],
        updated_at = CURRENT_TIMESTAMP
    WHERE transaction_id = '[TRANSACTION_ID]' AND nature_of_transaction = 'INVESTMENT';
    UPDATE FixedInvestments
    SET fixed_investment_type = '[TIPO_INVESTIMENTO_FIXO]',
        investment_date = TO_DATE('[DATA_INVESTIMENTO]', 'YYYY-MM-DD'),
        expiration_date = TO_DATE('[DATA_VENCIMENTO]', 'YYYY-MM-DD'),
        institution = '[INSTITUIÇÃO]',
        redeem_value = [VALOR_RESGATE],
        redeem_date = TO_DATE('[DATA_RESGATE]', 'YYYY-MM-DD'),
        liquidity_period = [PERÍODO_LIQUIDEZ],
        net_gain_loss = [GANHO_PERDA_LÍQUIDO]
    WHERE transaction_id = '[TRANSACTION_ID]';
END;
/

-- Exemplo:
BEGIN
    UPDATE Transactions
    SET code = 'INVFIX-20240928-ALT',
        is_effective = 1,
        name = 'Aplicação em CDB Ajustada',
        description = 'Investimento em CDB com novo prazo e valor',
        amount = 12000.00,
        category_id = '22222222-3333-4444-5555-666666666666',
        account_id = '550e8400-e29b-41d4-a716-446655440000',
        receiver = 'Banco BOM',
        sender = 'Frederico Adriel Nino',
        transaction_date = TO_DATE('2024-09-30', 'YYYY-MM-DD'),
        is_repeatable = 0,
        repeatable_type = 'NONE',
        note = 'Ajuste de valor e prazo do investimento',
        is_active = 1,
        updated_at = CURRENT_TIMESTAMP
    WHERE transaction_id = '11223344-5566-7788-99aa-bbccddeeff00' AND nature_of_transaction = 'INVESTMENT';
    UPDATE FixedInvestments
    SET fixed_investment_type = 'CDB_PRE',
        investment_date = TO_DATE('2024-09-30', 'YYYY-MM-DD'),
        expiration_date = TO_DATE('2026-09-30', 'YYYY-MM-DD'),
        institution = 'Banco BOM',
        redeem_value = NULL,
        redeem_date = NULL,
        liquidity_period = 730,
        net_gain_loss = NULL
    WHERE transaction_id = '11223344-5566-7788-99aa-bbccddeeff00';
END;
/

-- Para investimentos variáveis (VariableInvestments):
-- Bloco para atualizar um investimento variável completo
BEGIN
    UPDATE Transactions
    SET code = '[CÓDIGO]',
        is_effective = [IS_EFFECTIVE],
        name = '[NOME]',
        description = '[DESCRIÇÃO]',
        amount = [VALOR],
        category_id = '[CATEGORY_ID]',
        account_id = '[ACCOUNT_ID]',
        receiver = '[RECEBEDOR]',
        sender = '[REMETENTE]',
        transaction_date = TO_DATE('[DATA_TRANSAÇÃO]', 'YYYY-MM-DD'),
        is_repeatable = [IS_REPEATABLE],
        repeatable_type = '[REPEATABLE_TYPE]',
        note = '[NOTA]',
        is_active = [IS_ACTIVE],
        updated_at = CURRENT_TIMESTAMP
    WHERE transaction_id = '[TRANSACTION_ID]' AND nature_of_transaction = 'INVESTMENT';
    UPDATE VariableInvestments
    SET variable_investment_type = '[TIPO_INVESTIMENTO_VARIÁVEL]',
        broker = '[CORRETORA]',
        purchase_date = TO_DATE('[DATA_COMPRA]', 'YYYY-MM-DD'),
        expiration_date = TO_DATE('[DATA_VENCIMENTO]', 'YYYY-MM-DD'),
        asset_code = '[CÓDIGO_ATIVO]',
        quantity = [QUANTIDADE],
        unit_price = [PREÇO_UNITÁRIO],
        sale_date = TO_DATE('[DATA_VENDA]', 'YYYY-MM-DD'),
        sale_price = [PREÇO_VENDA]
    WHERE transaction_id = '[TRANSACTION_ID]';
END;
/

-- Exemplo:
BEGIN
    UPDATE Transactions
    SET code = 'INVVAR-20240928-ALT',
        is_effective = 1,
        name = 'Compra de ações BOAS Ajustada',
        description = 'Ajuste na quantidade e preço das ações adquiridas',
        amount = 10000.00,
        category_id = '22222222-3333-4444-5555-666666666666',
        account_id = '550e8400-e29b-41d4-a716-446655440000',
        receiver = 'Bolsa de Valores',
        sender = 'Frederico Adriel Nino',
        transaction_date = TO_DATE('2024-09-29', 'YYYY-MM-DD'),
        is_repeatable = 0,
        repeatable_type = 'NONE',
        note = 'Compra adicional de ações BOAS',
        is_active = 1,
        updated_at = CURRENT_TIMESTAMP
    WHERE transaction_id = 'ffeeddcc-bbaa-9988-7766-554433221100' AND nature_of_transaction = 'INVESTMENT';
    UPDATE VariableInvestments
    SET variable_investment_type = 'STOCK',
        broker = 'Corretora BOA',
        purchase_date = TO_DATE('2024-09-29', 'YYYY-MM-DD'),
        expiration_date = NULL,
        asset_code = 'BOAS3',
        quantity = 250,
        unit_price = 40.00,
        sale_date = NULL,
        sale_price = NULL
    WHERE transaction_id = 'ffeeddcc-bbaa-9988-7766-554433221100';
END;
/

-- CONSULTAS

-- 1. Consultar os dados de um usuário:
SELECT *
FROM Users
WHERE user_id = '[USER_ID]';

-- Exemplo:
SELECT *
FROM Users
WHERE user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e';

-- 2. Consultar os dados de um único registro de despesa de um usuário:
SELECT t.*
FROM Transactions t
         JOIN Accounts a ON t.account_id = a.account_id
WHERE t.transaction_id = '[TRANSACTION_ID]'
  AND a.user_id = '[USER_ID]'
  AND t.nature_of_transaction = 'EXPENSE';

-- Exemplo:
SELECT t.*
FROM Transactions t
         JOIN Accounts a ON t.account_id = a.account_id
WHERE t.transaction_id = '987f6543-21ba-43dc-ba98-76543210fedc'
  AND a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
  AND t.nature_of_transaction = 'EXPENSE';

-- 3. Consultar os dados de todos os registros de despesas de um usuário, ordenando dos mais recentes para os mais antigos:
SELECT t.*
FROM Transactions t
         JOIN Accounts a ON t.account_id = a.account_id
WHERE a.user_id = '[USER_ID]'
  AND t.nature_of_transaction = 'EXPENSE'
ORDER BY t.transaction_date DESC;

-- Exemplo:
SELECT t.*
FROM Transactions t
         JOIN Accounts a ON t.account_id = a.account_id
WHERE a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
  AND t.nature_of_transaction = 'EXPENSE'
ORDER BY t.transaction_date DESC;

-- 4. Consultar os dados de um único registro de investimento de um usuário:
SELECT t.*, fi.*, vi.*
FROM Transactions t
         LEFT JOIN FixedInvestments fi ON t.transaction_id = fi.transaction_id
         LEFT JOIN VariableInvestments vi ON t.transaction_id = vi.transaction_id
         JOIN Accounts a ON t.account_id = a.account_id
WHERE t.transaction_id = '[TRANSACTION_ID]'
  AND a.user_id = '[USER_ID]'
  AND t.nature_of_transaction = 'INVESTMENT';

-- Exemplo (para investimento fixo):
SELECT t.*, fi.*
FROM Transactions t
         JOIN FixedInvestments fi ON t.transaction_id = fi.transaction_id
         JOIN Accounts a ON t.account_id = a.account_id
WHERE t.transaction_id = '11223344-5566-7788-99aa-bbccddeeff00'
  AND a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
  AND t.nature_of_transaction = 'INVESTMENT';

-- Exemplo (para investimento variável):
SELECT t.*, vi.*
FROM Transactions t
         JOIN VariableInvestments vi ON t.transaction_id = vi.transaction_id
         JOIN Accounts a ON t.account_id = a.account_id
WHERE t.transaction_id = 'ffeeddcc-bbaa-9988-7766-554433221100'
  AND a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
  AND t.nature_of_transaction = 'INVESTMENT';

-- 5. Consultar os dados de todos os registros de investimentos de um usuário, ordenando dos mais recentes para os mais antigos:
SELECT t.*, fi.*, vi.*
FROM Transactions t
         LEFT JOIN FixedInvestments fi ON t.transaction_id = fi.transaction_id
         LEFT JOIN VariableInvestments vi ON t.transaction_id = vi.transaction_id
         JOIN Accounts a ON t.account_id = a.account_id
WHERE a.user_id = '[USER_ID]'
  AND t.nature_of_transaction = 'INVESTMENT'
ORDER BY t.transaction_date DESC;

-- Exemplo:
SELECT t.*, COALESCE(fi.fixed_investment_type, vi.variable_investment_type) AS investment_type
FROM Transactions t
         LEFT JOIN FixedInvestments fi ON t.transaction_id = fi.transaction_id
         LEFT JOIN VariableInvestments vi ON t.transaction_id = vi.transaction_id
         JOIN Accounts a ON t.account_id = a.account_id
WHERE a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
  AND t.nature_of_transaction = 'INVESTMENT'
ORDER BY t.transaction_date DESC;

-- 6. Consultar os dados básicos de um usuário, o último investimento registrado e a última despesa registrada:
SELECT u.name, u.email, u.birthdate,
       last_investment.transaction_id AS last_investment_id,
       last_investment.amount AS last_investment_amount,
       last_investment.transaction_date AS last_investment_date,
       last_expense.transaction_id AS last_expense_id,
       last_expense.amount AS last_expense_amount,
       last_expense.transaction_date AS last_expense_date
FROM Users u
         LEFT JOIN (
    SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.transaction_date DESC) rn
    FROM Transactions t
             JOIN Accounts a ON t.account_id = a.account_id
    WHERE a.user_id = '[USER_ID]' AND t.nature_of_transaction = 'INVESTMENT'
) last_investment ON last_investment.rn = 1
         LEFT JOIN (
    SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.transaction_date DESC) rn
    FROM Transactions t
             JOIN Accounts a ON t.account_id = a.account_id
    WHERE a.user_id = '[USER_ID]' AND t.nature_of_transaction = 'EXPENSE'
) last_expense ON last_expense.rn = 1
WHERE u.user_id = '[USER_ID]';

-- Exemplo:
WITH last_investment AS (
    SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.transaction_date DESC) rn
    FROM Transactions t
             JOIN Accounts a ON t.account_id = a.account_id
    WHERE a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e' AND t.nature_of_transaction = 'INVESTMENT'
),
     last_expense AS (
         SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.transaction_date DESC) rn
         FROM Transactions t
                  JOIN Accounts a ON t.account_id = a.account_id
         WHERE a.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e' AND t.nature_of_transaction = 'EXPENSE'
     )
SELECT u.name, u.email, u.birthdate,
       li.transaction_id AS last_investment_id,
       li.amount AS last_investment_amount,
       li.transaction_date AS last_investment_date,
       le.transaction_id AS last_expense_id,
       le.amount AS last_expense_amount,
       le.transaction_date AS last_expense_date
FROM Users u
         LEFT JOIN last_investment li ON li.rn = 1
         LEFT JOIN last_expense le ON le.rn = 1
WHERE u.user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e';
