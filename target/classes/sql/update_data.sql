-- Update Users
UPDATE Users
SET name = 'Frederico Adriel Nino',
    email = 'frederico.nino@example.com',
    password = 'nov@S3nh@',
    birthdate = TO_DATE('2002-02-28', 'YYYY-MM-DD'),
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE user_id = 'c0a80123-5a3c-4b4f-8326-fd77e2131f9e';

-- Update Transactions for income
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

-- Update Transactions for expenses
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

-- Update FixedInvestments
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

-- Update VariableInvestments
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