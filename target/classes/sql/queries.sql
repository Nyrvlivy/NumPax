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
