import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite sua matricula: ");
		String matricula = scanner.next();
		// String matricula = "105457";

		Aluno aluno = getAluno(matricula);
		if (aluno != null) {
			if (aluno.getStatus().equals("Ativo")) {
				if (aluno.getUffMail().equals("")) {
					String[] emails = geraEmails(aluno);
					System.out.println(aluno.getNome().split(" ")[0]
							+ ", por favor escolha uma das opções abaixo para o seu UFFMail");
					System.out.println("1 - " + emails[0]);
					System.out.println("2 - " + emails[1]);
					System.out.println("3 - " + emails[2]);
					System.out.println("4 - " + emails[3]);
					System.out.println("5 - " + emails[4]);
					String email = "";
					while (!email.equals("1") && !email.equals("2") && !email.equals("3") && !email.equals("4")
							&& !email.equals("5")) {
						email = scanner.next();
					}
					aluno.setUffMail(emails[Integer.parseInt(email) - 1]);
					String chave = gerarChave();
					sendSMS(aluno.getTelefone(), chave);
					setAlunoCSV(aluno, chave);
					System.out.println(
							"A criação de seu e-mail (" + aluno.getUffMail() + ") será feita nos próximos minutos."
									+ "Um SMS foi enviado para " + aluno.getTelefone() + " com a sua senha de acesso.");
					
				} else {
					System.out.println("esse aluno ja possui email da UFF");
				}
			} else {
				System.out.println("esse aluno nao esta Inativo");
			}
		} else {
			System.out.println("aluno nao encontrado");
		}
		scanner.close();
	}

	static Aluno getAluno(String matricula) { // Busca no arquivo alunos.csv o aluno e retorna o objeto aluno.
		// Se não encontrar retorna null.

		Aluno aluno = null;
		String alunosFile = "src/alunos.csv"; // Nome do arquivo com dados dos alunos
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(alunosFile));
			while ((line = br.readLine()) != null) {
				String data[] = line.split(",");
				if (data[1].equals(matricula)) {
					// System.out.println(line);
					aluno = new Aluno();
					aluno.setNome(data[0]);
					aluno.setMatricula(data[1]);
					aluno.setTelefone(data[2]);
					aluno.seteMail(data[3]);
					aluno.setUffMail(data[4]);
					aluno.setStatus(data[5]);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return aluno;
	}

	static String[] geraEmails(Aluno aluno) { // Gera os possíveis e-mails para o aluno.
		String[] eMail = new String[5];
		String[] Nomes = aluno.getNome().toLowerCase().split(" ");
		eMail[0] = Nomes[0] + "_" + Nomes[1] + "@id.uff.br";
		eMail[1] = Nomes[0] + Nomes[1].charAt(0) + Nomes[2].charAt(0) + "@id.uff.br";
		eMail[2] = Nomes[0] + Nomes[2] + "@id.uff.br";
		eMail[3] = Nomes[0].charAt(0) + Nomes[2] + "@id.uff.br";
		eMail[4] = Nomes[0].charAt(0) + Nomes[1] + Nomes[2] + "@id.uff.br";
		return eMail;
	}

	static String gerarChave() { // Gera uma senha para o aluno.
		int qtdeMaximaCaracteres = 8;
		String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}
		return senha.toString();

	}

	static void sendSMS(String telefone, String Chave) { // Função que chama a API para envio de SMS
		// enviaSMS(telefone,"sua senha para acesso no UFFmail é " + Chave.toString());
		System.out.println("sua senha para acesso no UFFmail é " + Chave.toString());
	}

	static void setAlunoCSV(Aluno aluno, String Key) { // Salva o novo UFFmail no arquivo com dados dos alunos.
		String alunosFile = "src/alunos.csv";
		String alunosTempFile = "src/alunostemp.csv"; // Nome do Arquivo temporario
		BufferedReader br = null;
		PrintWriter pw = null;
		String line = "";
		boolean sucesso = false;
		try {
			br = new BufferedReader(new FileReader(alunosFile));
			pw = new PrintWriter(new BufferedWriter(new FileWriter(alunosTempFile)));
			while ((line = br.readLine()) != null) {
				String data[] = line.split(",");
				if (data[1].equals(aluno.getMatricula())) {
					pw.println(aluno.getNome() + "," + aluno.getMatricula() + "," + aluno.getTelefone() + ","
							+ aluno.geteMail() + "," + aluno.getUffMail() + "," + aluno.getStatus());
				} else {
					// copia a linha
					pw.println(line);

				}
				sucesso = true;
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					pw.flush();
					pw.close();
					br.close();
					if (sucesso) {
						File oldFile = new File(alunosFile);
						File newFile = new File(alunosTempFile);
						oldFile.delete();
						newFile.renameTo(oldFile);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
