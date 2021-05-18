import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceptorUsuario implements Runnable{

	Usuario usuario;
	ServerSocket receptor;
	
	public ReceptorUsuario(Usuario usuario) throws Exception{
		this.usuario = usuario;
		receptor = new ServerSocket(8080);
	}

	//Thread que aceita conexao do servidor para receber mesagens
	public void run () {
		while(true){
			try{
				//Aceita a conexao com o servidor
				Socket cliente = receptor.accept();
				if(cliente.isBound()){
					recebeMensagem(cliente); // Recebe mensagens do servidor
				}
			}catch(IOException e){
				System.out.println("catch accept: " + e.getMessage());
			}
		}
	}
	
	public void recebeMensagem(Socket socket){
		try{ 	   
			InputStream in = socket.getInputStream();
			DataInputStream socketData = new DataInputStream(in);   
			String mensagem = socketData.readUTF();
			String nome = socketData.readUTF();
			
			System.out.println(nome +  " diz: " + mensagem);
		
		}catch(IOException e){
			System.out.println("catch receive: " + e.getMessage());
		}
	}
	
}
