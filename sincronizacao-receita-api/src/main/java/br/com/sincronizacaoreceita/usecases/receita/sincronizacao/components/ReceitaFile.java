package br.com.sincronizacaoreceita.usecases.receita.sincronizacao.components;

import br.com.sincronizacaoreceita.shared.utils.formaters.CurrencyFormatter;
import br.com.sincronizacaoreceita.usecases.receita.sincronizacao.services.ReceitaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReceitaFile implements ApplicationRunner {

    @Value("${header.csv}")
    String headerData;

    private final ReceitaService service;
    private final CurrencyFormatter currencyFormatter;

    public ReceitaFile(CurrencyFormatter currencyFormatter) {
        this.service = new ReceitaService();
        this.currencyFormatter = currencyFormatter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getNonOptionArgs().isEmpty()) {
            throw new RuntimeException("Não foi encontrado o caminho para o arquivo csv para importação!");
        }

        String filepath = args.getNonOptionArgs().get(0);

        File file = new File(filepath);

        if (!file.exists()) {
            throw new RuntimeException("Arquivo não encontrado!");
        }

        Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()));

        List<String> resultados = lines
                .filter(line -> !line.equals(headerData))
                .map(line -> {
                    String[] dados = line.split(";");
                    String resultado = "";

                    try {
                        String agencia = dados[0];
                        String conta = dados[1].replaceAll("(\\D)", "");
                        Double saldo = currencyFormatter.parse(dados[2]);
                        String status = dados[3];

                        boolean resultadoSincronizacao = service.atualizarConta(agencia, conta, saldo, status);
                        resultado = String.format("%s;%s", line, resultadoSincronizacao ? "ENVIADO" : "ERRO");
                    } catch (InterruptedException | ParseException e) {
                        resultado = String.format("%s;%s", line, "ERRO");
                    }
                    return resultado;
                })
                .collect(Collectors.toList());

        lines.close();

        resultados.add(0, String.format("%s;%s", headerData, "resultado"));

        String data = new SimpleDateFormat("ddMMyyyy-HHmmss").format(new Date());
        String resultFilename = String.format("%s_%s.csv", file.getName().replaceAll(".csv", ""), data);

        FileWriter resultFile = new FileWriter(file.getAbsolutePath().replaceAll(file.getName(), resultFilename));
        PrintWriter printWriter = new PrintWriter(resultFile);

        resultados.stream().forEach(line -> printWriter.println(line));

        printWriter.close();
    }

}
