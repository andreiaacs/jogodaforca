package Forca;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    //objeto que ira receber a porta de comunicação
    private static ServerSocket server;   
    
    //objeto que ira receber a conexão do cliente
    private Socket conexao;               
    
    private static int porta = 8080;
    private static ArrayList<String> palavras = new ArrayList<String>();
    
    private int cont=-1;
    
    //quando a thread é startada
    public void run() {
        
        System.out.println("Cliente se conectou...");
        try {
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            
            String comando = entrada.readUTF();
            
            System.out.println("Entrada = "+comando);
            
            while(comando.equals("n")||comando.equals("N")) {
                
                cont++;
                
                System.out.println("Jogando!");
                System.out.println("cont = " +cont);
                String palavra = palavras.get(cont);
            
                saida.writeUTF(palavra);            //envia palavra para cliente
                
                comando = entrada.readUTF();        //recebe comando do cliente
         
            }
            if(comando.equals("s")||comando.equals("S")){
                System.out.println("Cliente saiu do jogo!!!");
                conexao.close();
            }
        }catch (IOException ex) {
            System.out.println("Um cliente se desconectou...");
        }
        
    } 
            
    public Servidor(Socket conn) {
        this.conexao = conn;
    }
    
    public static void main(String[] args) {
        
        try {
            Scanner scanner = new Scanner(new FileReader("palavras.txt")).useDelimiter("\\n");
            while (scanner.hasNext()) {
                palavras.add(scanner.next());     
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            server = new ServerSocket(porta);
        } catch(Exception e) {
            System.out.println("Erro ao escolher porta!");
        }
        
        while(true) {
            try {
                System.out.println("Esperando conexão...");
               
                Socket conn = server.accept();
                
                System.out.println("Conexao aceita");
                Servidor s = new Servidor(conn);
                
                s.start();
            
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }
}
