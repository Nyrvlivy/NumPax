package br.com.numpax.domain.entities;

import br.com.numpax.domain.enums.FixedInvestmentType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FixedInvestment extends Transaction {
    private FixedInvestmentType fixedInvestmentType;   // Tipo de investimento -> CDB, LCI, LCA, LC, LF, CRI, CRA, Debêntures, FIDC, FII, Cotas de Fundos, Previdência Privada
    private LocalDate investmentDate;                  // Data do investimento
    private LocalDate maturityDate;                    // Data de vencimento
    private String institution;                        // Instituição financeira
    private Double[] TaxRates;                         // Taxas de rendimento
    private Double redeemValue;                        // Valor de resgate
    private LocalDate redeemDate;                      // Data de resgate
    private int liquidityPeriod;                       // Prazo de liquidez (quantidade de dias para receber o resgate)
    private Double netGainLoss;                        // Ganho ou perda líquido (Valor de resgate - Valor investido)

    public FixedInvestment(String code, String name, String description, BigDecimal amount, Category category, Account account, NatureOfTransaction natureOfTransaction, String receiver, String sender, LocalDate transactionDate, boolean isRepeatable, RepeatableType repeatableType, String note, FixedInvestmentType fixedInvestmentType, LocalDate investmentDate, LocalDate maturityDate, String institution, Double[] taxRates, Double redeemValue, LocalDate redeemDate, int liquidityPeriod, Double netGainLoss) {
        super(code, name, description, amount, category, account, natureOfTransaction, receiver, sender, transactionDate, repeatableType, note);
        this.fixedInvestmentType = fixedInvestmentType;
        this.investmentDate = investmentDate;
        this.maturityDate = maturityDate;
        this.institution = institution;
        TaxRates = taxRates;
        this.redeemValue = redeemValue;
        this.redeemDate = redeemDate;
        this.liquidityPeriod = liquidityPeriod;
        this.netGainLoss = netGainLoss;
    }
}
