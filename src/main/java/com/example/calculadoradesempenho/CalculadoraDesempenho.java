package com.example.calculadoradesempenho;

public class CalculadoraDesempenho {

    // Método para calcular a taxa de transmissão (T)
    public double calcularTaxaTransmissao(double larguraBanda, double snrDb) {
        double snrLinear = Math.pow(10, snrDb / 10);  // Converte SNR de dB para linear
        return larguraBanda * Math.log(1 + snrLinear) / Math.log(2);
    }

    // Método para calcular a capacidade do canal (C)
    public double calcularCapacidadeCanal(double larguraBanda, double snrDb) {
        double snrLinear = Math.pow(10, snrDb / 10);  // Converte SNR de dB para linear
        return larguraBanda * Math.log(1 + snrLinear) / Math.log(2);
    }

    // Método para calcular a taxa de erro de bit (BER)
    public double calcularBER(double snrDb) {
        double snrLinear = Math.pow(10, snrDb / 10);  // Converte SNR de dB para linear
        double x = Math.sqrt(snrLinear);

        // Exibindo o valor de SNR Linear para depuração
        System.out.println("SNR Linear: " + snrLinear);

        double ber = 0.5 * erfc(x / Math.sqrt(2));  // Função erfc corretamente aplicada

        // Exibindo o valor de BER para depuração
        System.out.println("BER: " + ber);

        return ber;
    }

    // Aproximação da função erro complementar (erfc)
    public double erfc(double x) {
        // Fórmula de aproximação para erfc
        double t = 1.0 / (1.0 + 0.5 * x);
        double sum = 1.0 + t * Math.exp(-x * x - 1.26551223 + t * (1.00002368 + t * (0.37409196 + t * (0.09678418 + t * (-0.18628806 + t * (0.27886807 + t * (-1.13520398 + t * 1.48851587)))))));
        return sum;
    }
}
