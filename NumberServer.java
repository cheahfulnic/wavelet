import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) { //returns 'Number: 0' when there is no path
            return String.format("Nicholas' number: %d", num);
        } else if (url.getPath().equals("/increment")) { //increments num and returns 'Number incremented!' when path is /increment
            num += 1;
            return String.format("Number incremented!");
        } else { //returns '404 Not Found' if path is not /, /increment or a valid /add path
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) { //increments num by the number after the equals sign and returns 'Number increased by', 
                                                  //followed by the number, '! It's now', and then the sum of the original num and your number.
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
