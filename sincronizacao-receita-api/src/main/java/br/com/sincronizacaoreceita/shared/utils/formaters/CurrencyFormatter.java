package br.com.sincronizacaoreceita.shared.utils.formaters;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Component
public class CurrencyFormatter {

    public Double parse(final String value) throws ParseException {
        Locale defaultLocale = new Locale("pt", "BR");
        return parse(value, defaultLocale);
    }

    public Double parse(final String value, final Locale locale) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        if (numberFormat instanceof DecimalFormat) {
            ((DecimalFormat) numberFormat).setParseBigDecimal(true);
        }
        return numberFormat.parse(value.replaceAll("[^\\d.,]","")).doubleValue();
    }
}
