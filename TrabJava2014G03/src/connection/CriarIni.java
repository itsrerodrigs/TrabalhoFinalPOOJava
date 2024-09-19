package connection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CriarIni {
	public static void criarIni() {
		File configFile = new File("config.ini");
		if (configFile.exists()) {
			System.out.println("Verificando se arquivo config.ini já existe...");
			System.out.println("O arquivo config.ini já existe! \nContinuando com o programa...\n");
			return;
		}
        try (Scanner scanner = new Scanner(System.in); FileWriter writer = new FileWriter("config.ini")) {
        	System.out.println("Criando arquivo config.ini...\n");
        	
        	System.out.print("Digite o host do banco de dados (padrão: localhost): ");
            String host = scanner.nextLine();
        	System.out.print("Digite a porta do banco de dados (padrão: 5432): ");
            String port = scanner.nextLine();
            System.out.print("Digite o nome do banco de dados: ");
            String database = scanner.nextLine();
            System.out.print("Digite o usuário do banco de dados (padrão: postgres): ");
            String user = scanner.nextLine();
            System.out.print("Digite a senha do banco de dados: ");
            String password = scanner.nextLine();

            writer.write("host=" + host + "\n");
            writer.write("port=" + port + "\n");
            writer.write("database=" + database + "\n");
            writer.write("user=" + user + "\n");
            writer.write("password=" + password + "\n");

            System.out.println("Arquivo config.ini criado com sucesso!");
        } 
        catch (IOException e) {
            System.out.println("Erro ao criar arquivo config.ini");
            e.printStackTrace();
        }
    }
}
