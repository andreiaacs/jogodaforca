package Forca;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) throws IOException {

        char letra;
        int z = 0, j = 0, k = 0, l = 0, m = 0, n = 0;
        char underline = '_';

        //cliente se conecta ao servidor    
        Socket conexao = new Socket("127.0.0.1", 50000);
        System.out.println("Conectou");

        DataInputStream entrada = new DataInputStream(conexao.getInputStream());
        DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String comando = "N";

        while (comando.equalsIgnoreCase("n")) {
            
            System.out.print("\nDiga um comando(qualquer letra: para sair do jogo / n: para novo jogo): ");

            comando = br.readLine();
            saida.writeUTF(comando);
            
            if (!comando.equalsIgnoreCase("N")) {
                System.out.println("\nVocê saiu do jogo");
                System.exit(0);
            }

            System.out.println("\nUm novo jogo foi iniciado...");

            String palavra = "CARRO";//entrada.readUTF();
            int tam_palavra = 5;//palavra.length() - 1;

            char[] palavra_temp = new char[tam_palavra];

            for (j = 0; j < tam_palavra; j++) {
                palavra_temp[j] = underline;
                System.out.print(" ");
                System.out.print(palavra_temp[j]);
            }

            z = 0;

            n = 0;

            while (z < tam_palavra) {
                System.out.print("\n\nDigite uma letra: ");
                letra = br.readLine().toUpperCase().charAt(0);
                l = palavra.indexOf(letra);
                if (l < 0) {
                    System.out.println("\nVocê errou a letra, tente novamente...");
                    n++;
                }
                for (m = 0; m < tam_palavra; m++) {
                    if (letra == palavra_temp[m]) {
                        System.out.println("\nVocê ja digitou esta letra, tente outra...");
                        z--;
                    }
                }
                for (int i = 0; i < tam_palavra; i++) {
                    l = 0;
                    if (palavra.charAt(i) == letra) {
                        palavra_temp[i] = letra;
                        for (k = 0; k < tam_palavra; k++) {
                            System.out.print(" ");
                            System.out.print(palavra_temp[k]);
                        }
                        z++;
                    }
                }
                if(z == tam_palavra){
                    System.out.println("\n\nPARABENS!!!! \n\nVocê acertou a palavra! \n\nDeseja jogar novamente?");
                }
                if (n == 7) {
                    System.out.println("\n\n\nENFORCADO :P");
                    z = tam_palavra;
                }
            }
        }

    }

}
