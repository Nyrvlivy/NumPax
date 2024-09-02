package br.com.numpax.domain.enums;

public enum AccountType {
    CHECKING, SAVINGS, INVESTMENT, GOAL;

    public enum InvestmentSubtypeAccount {
        FIXED_INVESTMENT, VARIABLE_INVESTMENT
    }

    private InvestmentSubtypeAccount investmentSubtype;

    AccountType() {}

    AccountType(InvestmentSubtypeAccount investmentSubtype) {
        this.investmentSubtype = investmentSubtype;
    }

    public InvestmentSubtypeAccount getInvestmentSubtype() {
        return investmentSubtype;
    }
}
