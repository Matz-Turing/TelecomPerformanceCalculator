package com.example.calculadoradesempenho;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField larguraBandaField;

    @FXML
    private TextField snrField;

    @FXML
    private Button calcularButton;

    @FXML
    private Label resultadoLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private LineChart<Number, Number> grafico;

    private CalculadoraDesempenho calculadora = new CalculadoraDesempenho();

    @FXML
    public void initialize() {
        calcularButton.setOnAction(e -> calcularDesempenho());
    }

    private void calcularDesempenho() {
        String larguraBandaText = larguraBandaField.getText();
        String snrText = snrField.getText();

        if (larguraBandaText.isEmpty() || snrText.isEmpty()) {
            resultadoLabel.setText("Por favor, preencha todos os campos.");
            return;
        }

        try {
            double larguraBanda = Double.parseDouble(larguraBandaText);
            double snr = Double.parseDouble(snrText);

            // Validação para garantir que os valores sejam positivos
            if (larguraBanda <= 0 || snr <= 0) {
                resultadoLabel.setText("Por favor, insira valores positivos.");
                return;
            }

            // Iniciar a barra de progresso
            progressBar.setVisible(true);
            progressBar.setProgress(0.2);

            // Simulando um cálculo demorado
            new Thread(() -> {
                try {
                    Thread.sleep(1000);  // Simula o tempo de cálculo

                    double taxaTransmissao = calculadora.calcularTaxaTransmissao(larguraBanda, snr);
                    double capacidadeCanal = calculadora.calcularCapacidadeCanal(larguraBanda, snr);
                    double ber = calculadora.calcularBER(snr);

                    // Atualizar a UI na thread principal
                    javafx.application.Platform.runLater(() -> {
                        resultadoLabel.setText(String.format("Taxa de Transmissão: %.2f bps\nCapacidade do Canal: %.2f bps\nBER: %.5f",
                                taxaTransmissao, capacidadeCanal, ber));

                        // Adicionando dados no gráfico
                        XYChart.Series<Number, Number> series = new XYChart.Series<>();
                        series.setName("Capacidade do Canal");
                        series.getData().add(new XYChart.Data<>(larguraBanda, capacidadeCanal));

                        grafico.getData().clear();
                        grafico.getData().add(series);

                        progressBar.setVisible(false);  // Esconde a barra de progresso
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (NumberFormatException ex) {
            resultadoLabel.setText("Por favor, insira valores numéricos válidos.");
        }
    }
}
