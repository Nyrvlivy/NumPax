package br.com.numpax.application.enums;

public enum AccountType {
    CHECKING, SAVINGS, INVESTMENT, GOAL;

    public enum InvestmentSubtypeAccount {
        FIXED_INVESTMENT, VARIABLE_INVESTMENT, STOCKS, BONDS, ETF, FII, CRYPTO, FOREX, OTHER
    }

    private InvestmentSubtypeAccount investmentSubtype;

    AccountType() {
    }

    AccountType(InvestmentSubtypeAccount investmentSubtype) {
        this.investmentSubtype = investmentSubtype;
    }

    public InvestmentSubtypeAccount getInvestmentSubtype() {
        return investmentSubtype;
    }
}
