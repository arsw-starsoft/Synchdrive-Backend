package edu.eci.arsw.synchdrive.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import edu.eci.arsw.synchdrive.model.App;
import edu.eci.arsw.synchdrive.model.Customer;
import org.springframework.stereotype.Component;

import edu.eci.arsw.synchdrive.model.Coordinate;
import edu.eci.arsw.synchdrive.model.Servicio;

@Component
public class HttpConnectionService {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
     
    public static String getUberApp(String name) throws IOException {
    	String url = "https://uber-backend-starsoft.herokuapp.com/users/" + name;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
         return Integer.toString(responseCode);
    }

    private static String getServiceUber(Servicio serv) throws IOException {
    	String url = "https://uber-backend-starsoft.herokuapp.com/servicios/generate";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.connect(); 
        String jsonInputString = gson.toJson(serv);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(jsonInputString.toString());
        osw.flush();
        osw.close();

        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return null;
    }

    private static String getServiceDidi(Servicio serv) throws IOException {
    	String url = "https://didi-backend-starsoft.herokuapp.com/servicios/generate";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.connect(); 
        String jsonInputString = gson.toJson(serv);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(jsonInputString.toString());
        osw.flush();
        osw.close();

        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return null;
    }

    private static String getServiceBeat(Servicio serv) throws IOException {
    	String url = "https://beat-backend-starsoft.herokuapp.com/servicios/generate";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.connect(); 
        String jsonInputString = gson.toJson(serv);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(jsonInputString.toString());
        osw.flush();
        osw.close();

        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return null;
    }

    
    public static String getUberAppDriver(String name) throws IOException {
    	String url = "https://uber-backend-starsoft.herokuapp.com/drivers/" + name;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
         return Integer.toString(responseCode);
    }

    private static String getAPPS(String appName,Coordinate coordinate) throws IOException {
    	String url = "https://uber-backend-starsoft.herokuapp.com/servicios";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return null;
    }


    public static Servicio[] getCloseServices(String appName,Coordinate coordinate) throws IOException{

		String data = getAPPS(appName, coordinate);
		Servicio[] service = gson.fromJson(data,Servicio[].class);
			
	
		return service;

    }
    

    public static Servicio getGenerateUber(Servicio serv) throws IOException{
		String data = getServiceUber(serv);
		Servicio service = gson.fromJson(data,Servicio.class);
		return service;

    }
    
    public static Servicio getGenerateDidi(Servicio serv) throws IOException{
		String data = getServiceDidi(serv);
		Servicio service = gson.fromJson(data,Servicio.class);
		return service;

    }
    
    public static Servicio getGenerateBeat(Servicio serv) throws IOException{
		String data = getServiceBeat(serv);
		Servicio service = gson.fromJson(data,Servicio.class);
		return service;

	}


    private static String getLowDidiApp(String destino) throws IOException {
        String url = "https://didi-backend-starsoft.herokuapp.com/servicios/generate/" + destino;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);


        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");

        return null;
    }

    private static String getLowUberApp(String destino) throws IOException{
        String url = "https://uber-backend-starsoft.herokuapp.com/servicios/generate/" + destino;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);


        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return null;

    }

    private static String getLowBeatApp(String destino) throws IOException {
        String url = "https://beat-backend-starsoft.herokuapp.com/servicios/generate/" + destino;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);


        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return null;
    }

    public static Double getLowDidiCost(String destino) throws IOException{
        String data = getLowDidiApp(destino);
        Double service = gson.fromJson(data,Double.class);
        return service;

    }

    public static Double getLowUberCost(String destino) throws IOException{
        String data = getLowUberApp(destino);
        Double service = gson.fromJson(data,Double.class);
        return service;

    }

    public static Double getLowBeatCost(String destino) throws IOException{
        String data = getLowBeatApp(destino);
        Double service = gson.fromJson(data,Double.class);
        return service;

    }



}