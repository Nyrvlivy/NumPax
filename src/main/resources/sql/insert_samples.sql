-- Sample User
INSERT INTO Users (user_id, name, email, password, birthdate)
VALUES (
    'c0a80123-5a3c-4b4f-8326-fd77e2131f9e',
    'Frederico Adriel',
    'frederico.adriel@example.com',
    'p@assw0rd',
    TO_DATE('1999-11-10', 'YYYY-MM-DD')
);

-- Sample Account
INSERT INTO Accounts (account_id, name, description, balance, is_active, user_id)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    'Conta Corrente Principal',
    'Conta corrente para despesas diárias',
    2500.00,
    1,
    'c0a80123-5a3c-4b4f-8326-fd77e2131f9e'
);

-- Sample Income Transaction
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
    'INCOME',                -- NatureOfTransaction.INCOME
    'Frederico Adriel Nino',
    'Empresa Jogos Vorazes',
    TO_DATE('2024-09-28', 'YYYY-MM-DD'),
    1,
    'MONTHLY',              -- RepeatableType.MONTHLY
    'Salário depositado na conta corrente',
    1
);

-- Sample Expense Transaction
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
    '33333333-2222-3333-4444-555555555555',    -- Categoria 'Aluguel'
    '550e8400-e29b-41d4-a716-446655440000',
    'EXPENSE',              -- NatureOfTransaction.EXPENSE
    'Imobiliária Star Wars',
    'Frederico Adriel Nino',
    TO_DATE('2024-09-28', 'YYYY-MM-DD'),
    1,
    'MONTHLY',              -- RepeatableType.MONTHLY
    'Aluguel do Apartamento',
    1
);

-- Sample Fixed Investment
INSERT ALL
    INTO Transactions (
        transaction_id, code, is_effective, name, description, amount, category_id,
        account_id, nature_of_transaction, receiver, sender, transaction_date,
        is_repeatable, repeatable_type, note, is_active
    ) VALUES (
        '11223344-5566-7788-99aa-bbccddeeff00',
        'INVFIX-20240928',
        1,
        'Aplicação em CDB',
        'Investimento em CDB de 12 meses',
        10000.00,
        '55555555-2222-3333-4444-555555555555',  -- Categoria 'Renda Fixa'
        '550e8400-e29b-41d4-a716-446655440000',
        'INVESTMENT',        -- NatureOfTransaction.INVESTMENT
        'Banco BOM',
        'Frederico Adriel Nino',
        TO_DATE('2024-09-28', 'YYYY-MM-DD'),
        0,
        'NONE',             -- RepeatableType.NONE
        'CDB com rendimento de 8% a.a.',
        1
    )
    INTO FixedInvestments (
        transaction_id, fixed_investment_type, investment_date, expiration_date,
        institution, redeem_value, redeem_date, liquidity_period, net_gain_loss
    ) VALUES (
        '11223344-5566-7788-99aa-bbccddeeff00',
        'CDB_PRE',          -- FixedInvestmentType.CDB_PRE
        TO_DATE('2024-09-28', 'YYYY-MM-DD'),
        TO_DATE('2025-09-28', 'YYYY-MM-DD'),
        'Banco BOM',
        NULL,
        NULL,
        365,
        NULL
    )
SELECT * FROM dual;

-- Sample Variable Investment
INSERT ALL
    INTO Transactions (
        transaction_id, code, is_effective, name, description, amount, category_id,
        account_id, nature_of_transaction, receiver, sender, transaction_date,
        is_repeatable, repeatable_type, note, is_active
    ) VALUES (
        'ffeeddcc-bbaa-9988-7766-554433221100',
        'INVVAR-20240928',
        1,
        'Compra de Ações BOAS',
        'Compra de 200 ações da BOA Corp',
        8000.00,
        '66666666-2222-3333-4444-555555555555',  -- Categoria 'Renda Variável'
        '550e8400-e29b-41d4-a716-446655440000',
        'INVESTMENT',        -- NatureOfTransaction.INVESTMENT
        'Bolsa de Valores',
        'Frederico Adriel Nino',
        TO_DATE('2024-09-28', 'YYYY-MM-DD'),
        0,
        'NONE',             -- RepeatableType.NONE
        'Investimento em ações para longo prazo',
        1
    )
    INTO VariableInvestments (
        transaction_id, variable_investment_type, broker, purchase_date, expiration_date,
        asset_code
    ) VALUES (
        'ffeeddcc-bbaa-9988-7766-554433221100',
        'INVESTMENT',
        'Broker XYZ',
        TO_DATE('2024-09-28', 'YYYY-MM-DD'),
        TO_DATE('2025-09-28', 'YYYY-MM-DD'),
        'BOAS'
    )
SELECT * FROM dual; 