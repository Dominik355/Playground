package basics.lambdasAndStreams.devoxxConference.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.*;

@FunctionalInterface
public interface CurrencyConverterWithRounding {

    double convert(double amount);

    interface BiFunction {
        Double convert(Double amount, String toCurrency);

        default CurrencyConverterWithRounding to(String toCurrency) {
            return amount -> convert(amount, toCurrency);
        }
    }

    interface TriFunction {
        Double convert(Double amount, String fromCurrency, String toCurrency);

        default BiFunction from(String fromCurrency) {
            return (amount, toCurrency) -> convert(amount, fromCurrency, toCurrency);
        }
    }

    interface FourFunction {
        Double convert(Double amount, String fromCurrency, String toCurrency, Boolean round);

        default TriFunction roundTo(Boolean round) {
            return (amount, fromCurrency, toCurrency) -> {
                Double result = convert(amount, fromCurrency, toCurrency, round);
                return TRUE.equals(round) ? Double.valueOf(Math.round(result)) : result;
            };
        }
    }

    static FourFunction of(LocalDate date) {

        return (amount, fromCurrency, toCurrency, round) -> {

            Path path = Paths.get("data/currency.txt");
            try (Stream<String> lines = Files.lines(path)) {

                Map<String, Double> converterMap =
                        lines.skip(1L)
                                .collect(
                                        Collectors.toMap(
                                                line -> line.substring(0, 3),
                                                line -> Double.parseDouble(line.substring(4))
                                        )
                                );

                return amount*(converterMap.get(toCurrency)/converterMap.get(fromCurrency));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        };
    }
}