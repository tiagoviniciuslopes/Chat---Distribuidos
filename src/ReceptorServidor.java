import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceptorServidor implements Runnable{

	Servidor servidor;
	private Socket usuario;
	
	public ReceptorServidor(Socket usuario, Servidor servidor){
		this.usuario = usuario;
		this.servidor = servidor;
	}
	
	public void run(){
		while(true){ 
			
			if(usuario.isBound() && usuario.isConnected() && !usuario.isClosed()){
				recebe(usuario);
			}else{
				 return;
			}
		}
	}
	
	public void recebe(Socket socket){
		try{  	   
			InputStream in = socket.getInputStream();
			DataInputStream socketData = new DataInputStream(in);   
			String mensagem = socketData.readUTF();
			String nome = socketData.readUTF();
					
			System.out.println(nome +  " diz: " + mensagem);
			
			mensagemBroadcast(mensagem, nome);
			
		}catch(IOException e){
			System.out.println("catch receive: " + e.getMessage());
		}
	}
	
	public void mensagemBroadcast(String mensagem, String nome) {
		servidor.mensagemBroadcast(mensagem, nome);
	}
	
	
}
