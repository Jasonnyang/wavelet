import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> arr = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String str = "";
            for(String s: arr){
                str += s + ", ";
            }
            if(str.equals("")){
                return "Empty List";
            }
            return String.format(str);
        } else if (url.getPath().contains("/add")) {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                arr.add(parameters[1]);
                return String.format("%s added to list!", parameters[1]);
            }
        } else if(url.getPath().contains("/search")){
            System.out.println("Path: " + url.getPath());
            String str = "";
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for(String s: arr){
                    if(s.contains(parameters[1])){
                        str += s + ", ";
                    }
                }
            }
            if(str.equals("")){
                return "Can't find specified String";
            }
            return String.format(str);
            
        }
        return "404 Not Found!";
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
