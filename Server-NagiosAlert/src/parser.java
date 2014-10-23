import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class parser {

	// Funcion para parsear el archivo status.dat en busca de todos los hosts
	@SuppressWarnings("deprecation")
	public static ArrayList<host> parseHosts() {

		String archivo = "/usr/local/nagios/var/status.dat";
		ArrayList<host> hosts = new ArrayList<host>();

		Scanner scan = null;
		try {
			scan = new Scanner(new File(archivo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (scan.hasNextLine()) {

			String t = scan.nextLine();

			if (t.contains("hoststatus")) {

				String n = ""; // Nombre del host
				int s = 3; // Codigo de estado desconocido
				String r = ""; // Ultimo checkeo
				String d = ""; // Tiempo activo

				String tmp = "";
				Date temp;

				int pos;

				t = scan.nextLine();

				while (!t.contains("}")) {

					if (t.contains("host_name")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						n = tmp;

					} else if (t.contains("current_state")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						s = Integer.parseInt(tmp);

					} else if (t.contains("last_check")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						r = tmp;
						temp = new java.util.Date(
								(long) Integer.parseInt(r) * 1000);
						r = temp.toLocaleString();

					} else if (t.contains("last_time_up")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						d = tmp;
						/** REVISAR COMO CALCULAR LA DURACION UP **/

						temp = new java.util.Date(
								(long) Integer.parseInt(d) * 1000);
						d = temp.toLocaleString();
					}

					t = scan.nextLine();
				}

				host h = new host(n, s, r, d);

				hosts.add(h);
			}
		}

		scan.close();

		return hosts;
	}

	// Funcion para parsear el archivo status.dat en busca de todos los
	// servicios
	public static ArrayList<servicio> parseServicios() {

		String archivo = "/usr/local/nagios/var/status.dat";
		ArrayList<servicio> serv = new ArrayList<servicio>();

		Scanner scan = null;
		try {
			scan = new Scanner(new File(archivo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (scan.hasNextLine()) {
			String t = scan.nextLine();

			if (t.contains("servicestatus")) {

				String h = ""; // Nombre del host
				String n = ""; // Nombre del servicio
				int s = 3; // Codigo de estado desconocido
				String r = ""; // Ultimo checkeo
				String d = ""; // Tiempo activo
				String i = ""; // Informacion del servicio
				String tmp = "";
				int pos;
				Date temp;

				t = scan.nextLine();

				while (!t.contains("}")) {

					if (t.contains("service_description")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						n = tmp;

					} else if (t.contains("host_name")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						h = tmp;

					} else if (t.contains("current_state")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						s = Integer.parseInt(tmp);

					} else if (t.contains("last_check")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						r = tmp;
						temp = new java.util.Date(
								(long) Integer.parseInt(r) * 1000);
						r = temp.toLocaleString();

					} else if (t.contains("last_time_ok")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						d = tmp;
						/** REVISAR COMO CALCULAR LA DURACION UP **/

						temp = new java.util.Date(
								(long) Integer.parseInt(d) * 1000);
						d = temp.toLocaleString();

					} else if (t.contains("plugin_output")
							&& !t.contains("long_plugin_output")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						i = tmp;

					}

					t = scan.nextLine();
				}

				servicio se = new servicio(n, h, s, r, d, i);

				serv.add(se);
			}
		}

		scan.close();

		return serv;

	}

	public static void main(String[] arg) {

		ArrayList<servicio> s = parseServicios();

		Collections.sort(s, servicio.servicioName);
		   for(servicio temp: s){
				System.out.println(temp.getNombre());
				System.out.println(temp.getHost());
				System.out.println("------------------");
		   }
		   
	}
	// // funcion para ordenar una lista de servicios basado en el nombre del
	// host
	// // al que estan relacionados.
	// public ArrayList<servicio> sort(ArrayList<servicio> l){
	//
	// Collections.sort(l, servicio.servicioName);
	// return l;
	//
	// }
}