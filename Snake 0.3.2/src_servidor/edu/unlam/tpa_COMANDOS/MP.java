package edu.unlam.tpa_COMANDOS;

import java.io.IOException;
import java.net.Socket;

import edu.unlam.tpa_COMUNICACION.Servidor;
import edu.unlam.tpa_PAQUETES.Comando;
import edu.unlam.tpa_PAQUETES.PaqueteMensaje;


public class MP  extends ComandoServer{

	@Override
	public void ejecutar() {
		PaqueteMensaje paqueteMensaje = (PaqueteMensaje) (gson.fromJson(cadenaLeida, PaqueteMensaje.class));
		try {
			if (Servidor.mensajeAUsuario(paqueteMensaje)) {

				paqueteMensaje.setComando(Comando.MP);

				Socket socketDestino = Servidor.getMapConectados().get(paqueteMensaje.getUserReceptor());

				int index = Servidor.getSocketsConectados().indexOf(socketDestino);
				Servidor.getClientesConectados().get(index).getSalida().writeObject(gson.toJson(paqueteMensaje));

			} 
		} catch (IOException e) {
			Servidor.getLog().append("Error al intentar enviar el mensaje de " + paqueteMensaje.getUserEmisor() + "para "+ paqueteMensaje.getUserReceptor() + System.lineSeparator());
		}
		
	}

}