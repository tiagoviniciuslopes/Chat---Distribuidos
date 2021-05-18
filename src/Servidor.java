import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	
	ServerSocket servidor;
	ArrayList<Socket> clientes= new ArrayList<Socket>();
	
	public static void main(String[] args) throws Exception{
		Servidor s = new Servidor();
		
		s.abreServidor();
		s.executa();
	}
	
	public void abreServidor() throws Exception{
		servidor = new ServerSocket(8889);
		InetAddress ip = InetAddress.getLocalHost();	
		
		System.out.println("Porta "+servidor.getLocalPort()+" aberta!");
		System.out.println("IP do servidor: "+ip.getHostAddress());
		
	}
	
	public void executa() throws IOException{
			
		while(true){
			try{
				//Aceita um cliente
				Socket cliente = servidor.accept();
				Socket aux=null;
				
				for (Socket s:clientes) {
					if(s.getInetAddress().getHostAddress().equals(cliente.getInetAddress().getHostAddress())){
						aux = s;
					}
				}
				clientes.remove(aux);
				clientes.add(cliente);
				
				System.out.println("\nCliente conectado: " + cliente.getInetAddress().getHostAddress());
				if(cliente.isBound()){
					ReceptorServidor rs = new ReceptorServidor(cliente, this);
					new Thread(rs).start();
				}
			}catch(IOException e){
				System.out.println("catch accept: " + e.getMessage());
			}
		}
	}
	
	
	public void mensagemBroadcast(String mensagem, String nome){	
		try{
			Socket sender;

			for (Socket s:clientes) {
				if(s.isBound() && s.isConnected() && !s.isClosed() && !s.isInputShutdown() && !s.isOutputShutdown()){
					sender = new Socket(s.getInetAddress().getHostAddress(),8080);
				
					OutputStream os = sender.getOutputStream();   
					DataOutputStream dos = new DataOutputStream(os);     
					dos.writeUTF(mensagem); //Enviando a mensagem
					dos.writeUTF(nome); //Enviando o nome de usuario 
					dos.flush(); //Forca o envio

					sender.close();
					sender = null;
				}
			}
			
		}catch(IOException e){
			System.out.println("catch sendlist: " + e.getMessage());
		}
	}
	
}
