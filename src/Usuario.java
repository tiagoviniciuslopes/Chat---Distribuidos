import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Usuario {

	private Socket usuario;
	private String nome;
	private String mensagem;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public static void main(String[] args) throws Exception{
		Usuario u = new Usuario();
		u.abreServidor();
		u.executa();		
	}
	
	public void abreServidor()throws Exception{
		ReceptorUsuario ru = new ReceptorUsuario(this);
		new Thread(ru).start();
	}
	
	public void executa() throws Exception{
		Usuario u = new Usuario();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int port=8889;
		
		
		System.out.println("Entre com o ip do servidor: ");
		String ip = reader.readLine().trim();
		u.conecta(ip,port);
			
		for (int i = 0; i < 100; ++i) System.out.println();
		
		System.out.println("Entre com seu nome de usuario");
		u.setNome(reader.readLine().trim());
	
		
		while(true) {
			u.setMensagem(reader.readLine().trim());
			u.enviaMenssagem();
		}	
	}
	
	public void conecta(String server, int porta) throws UnknownHostException, IOException{
		usuario = new Socket(server,porta);	
		System.out.println(" ");
	}
	
	public void enviaMenssagem() throws IOException{
        OutputStream os = usuario.getOutputStream();    
        DataOutputStream dos = new DataOutputStream(os);    
        dos.writeUTF(this.getMensagem());
        dos.writeUTF(this.getNome());
        dos.flush(); //Forca o envio
              
	}
	
}
