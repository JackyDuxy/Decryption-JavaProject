import java.io.*;
// import java.util.Random.nextInt.*;
import java.util.*;
// import testcases;



// import javax.security.auth.kerberos.EncryptionKey;

public class App {
    // public datastructure that stores everything

    public static Map<String, String> userinfo = new HashMap<String, String>(); 

    public static boolean DEBUG = true;

    public static void signUp(String encryptionKey, Scanner scanner){

        // username
        // Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while(repeat){
            System.out.println("What is your username: ");
            String username_input = scanner.nextLine();

            System.out.println("What is your password: ");
            String password_input = scanner.nextLine();
            
            // conditional username and password can only signup if username and password not inside userinfo.txt
            if(userinfo.containsKey(username_input)){
                // username is used already in use 
                System.out.println("username already in use try again! ");
            }else{
                updatetxt(username_input, password_input, encryptionKey);
                userinfo.put(username_input,password_input);
                System.out.println(username_input+" successfuly signedup");
                repeat = false;
                sync_user_info();
            }

        }
        // scanner.close();
     
    }

    public static void signIn(String encryptionKey, Scanner scanner){
        // Scanner scanner = new Scanner(System.in);
        boolean repeat = true;
        // String keyFrom_pinit = _pinit();

        while(repeat){
            System.out.println("What is your username: ");
            String username_input = scanner.nextLine().toLowerCase();


            System.out.println("What is your password: ");
            String password_input = scanner.nextLine().toLowerCase();
            

            // print out user info
            System.out.println(userinfo);


            // conditional username and password can only signup if username and password not inside userinfo.txt
            if(userinfo.containsKey(encryption(username_input, encryptionKey)) && userinfo.get(encryption(username_input, encryptionKey)).equals(encryption(password_input, encryptionKey))){
                System.out.println("Success");
                repeat = false;
            }else{
                System.out.println("Something went wrong, fail to log in");
                repeat = false;

            }
    

        }
        // scanner.close();
     
    }

    public static void sync_user_info (){
        String filename = "Java1/src/userinfo.txt";
        String line = null;
        try{
             //synch user info with userinfo txt
            FileReader read_info_txt = new FileReader(filename);
            // does the sync user info method need any outside information to preform this said task
            BufferedReader bufferedReader = new BufferedReader(read_info_txt);
            

            System.out.println("SYNCH USER INFO DEBUG");
            line = bufferedReader.readLine();
            int cnt = 0;

            while(line != null){
                // handle encryption key because encryption key can not be split by ; 
                System.out.println("********************");
                System.out.print("entire line");
                System.out.print(cnt);
                System.out.print(" : ");
                System.out.println(line);

                if (cnt != 0){
                    System.out.println("Splitted line");
                    String [] splittedline = line.split(";");
                    System.out.println(splittedline[0] + " " + splittedline[1]);
                    userinfo.put(splittedline[0],splittedline[1]);
                }
                line = bufferedReader.readLine();
                cnt ++;

            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("file not found");
        }
        catch(IOException ex) {
            System.out.println("io exception");
        }
    };

    public static String encryption(String word, String encryptionKey){
        String ep_word = "";
        int loopedcharacter ;
        for(int i = 0; i < word.length();i++){
            loopedcharacter = word.charAt(i)-97;
            ep_word += (char)(encryptionKey.charAt(loopedcharacter));
        }
        return ep_word;
    };

    public static String decryption(String word, String encryptionKey){
        String dp_word = "";
        // int loopedcharacterReverse ;
        int dpWordIndex;
        for(int i = 0; i < word.length();i++){
            // // grab the letter we are decrypting
            // dpWordIndex = encryptionKey.charAt(word.charAt(i));
            // loopedCharacterReverse = word.charAt(dpWordIndex)+97;
            // dp_word += (char)(encryptionKey.charAt(loopedCharacterReverse));
            // cba
            // find index of grabbed letter in encryption key

            // add 97 to find ascii value

            // change ascii value into char

            // add char into dp_word


            dpWordIndex = encryptionKey.indexOf( (char) word.charAt(i));
            // dp_word += encryptionKey.charAt(loopedcharacterReverse);
            dp_word += (char)(dpWordIndex+97);
            // System.out.println(dp_word);
        
        }
        return dp_word;
    };

    public static String randomKeyGenerator() {
        Random randomNumberGenerator = new Random();
        ArrayList<Character> Letters = new ArrayList<Character>();
        String alphabet = "";
        for (int i = 0; i < 26; i++) {
            char letter = (char)('a'+i);
            Letters.add(letter);
        }
        while (Letters.size() > 0){
            int randomNumber = randomNumberGenerator.nextInt(Letters.size());
            alphabet += (char) (Letters.get(randomNumber));
            Letters.remove(randomNumber);
        } 

        return alphabet;
    }
    // 26 unique letters in a bag -> create a list that contains 26 letters
    // we grab one each time and we dont put it back ->random pick the letter based on the index and delete the letter has the index
    
    
    // update userinfo.txt throught updateinfo

    // if user successfully signed up add said user and password into userinfo.txt
    public static void updatetxt(String username_input, String password_input, String encryptionKey){
        String filename = "Java1/src/userinfo.txt";


        try{
            
            //synch user info with userinfo txt
            FileWriter write_info_txt = new FileWriter(filename , true);
            // does the sync user info method need any outside information to preform this said task
            BufferedWriter bufferedWriter = new BufferedWriter(write_info_txt);
            
            bufferedWriter.newLine();
            String username_ep = encryption(username_input, encryptionKey);
            String password_ep = encryption(password_input, encryptionKey);
            bufferedWriter.write(username_ep+";"+password_ep);
            
            bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("file not found");
        }
        catch(IOException ex) {
            System.out.println("io exception");
        }
    }
    private static String _pinit(){
        String filename = "Java1/src/userinfo.txt"; 

        try{
            //synch user info with userinfo txt
            FileReader read_info_txt = new FileReader(filename);
            // does the sync user info method need any outside information to preform this said task
            BufferedReader bufferedReader = new BufferedReader(read_info_txt);
            String reader_stuff = bufferedReader.readLine();
            bufferedReader.close();
            //bufferedReader.readLine() == null && bufferedReader.readLine().length() < 0
            if (reader_stuff == null) {
                String newRandomKey = randomKeyGenerator();
                // System.out.println(randomnumber);
                //synch user info with userinfo txt
                FileWriter write_info_txt = new FileWriter(filename , true);
                // // does the sync user info method need any outside information to preform this said task
                BufferedWriter bufferedWriter = new BufferedWriter(write_info_txt);
                bufferedWriter.write(newRandomKey);
                bufferedWriter.close();
                return newRandomKey;
            }
            else{
                System.out.println("Encryption is set");
                return reader_stuff;
            }
            // HW: based on userinfo encryption key translate everything we input


            // //synch user info with userinfo txt
            // FileWriter write_info_txt = new FileWriter(filename , true);
            // // does the sync user info method need any outside information to preform this said task
            // BufferedWriter bufferedWriter = new BufferedWriter(write_info_txt);
            
            // bufferedWriter.newLine();
            // // bufferedWriter.write(username_input+";"+password_input);
            
            // bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open the file");
            return "Error";
        }
        catch(IOException ex) {
            System.out.println("Unable to open the file");
            return "Error";
        }
    }

    
    public static boolean _encryptionDecryptionTest(String keyFrom_pinit){

        System.out.println();

        return (
            encryption("xyz", keyFrom_pinit).equals("okw") &&
            encryption("xab", keyFrom_pinit).equals("oqn") &&
            encryption("yza", keyFrom_pinit).equals("kwq") &&
            encryption("sit", keyFrom_pinit).equals("bhj") &&
            encryption("hoop", keyFrom_pinit).equals("pttz")
        );

    }
    public static boolean _decryptionTest(String keyFrom_pinit){

        System.out.println();

        return (
            decryption("qnv", keyFrom_pinit).equals("abc")
            // encryption("oqn", keyFrom_pinit).equals("xab") &&
            // encryption("kwq", keyFrom_pinit).equals("yza") &&
            // encryption("bhj", keyFrom_pinit).equals("sit") &&
            // encryption("pttz", keyFrom_pinit).equals("hoop")
        );

    }


    public static void main(String[] args) throws Exception {



        // PROGRAM
        String keyFrom_pinit = _pinit();
        System.out.print("ENCRYPTION KEY FROM PINIT: ");
        System.out.println(keyFrom_pinit);
        boolean repeat = true;

        // synching the user hashmap
        sync_user_info();

        // interface for singin and sighup
        Scanner scanner = new Scanner(System.in);
        
        while(repeat){
            
            System.out.println("Press 1 to sign in; Press 2 to sign up; 3 exit");
            String userOption = scanner.nextLine();

            // conditional username and password can only signup if username and password not inside userinfo.txt
            if(userOption.equals("1")){
                // username is used already in use 
                System.out.println("You are signing in: ");
                signIn(keyFrom_pinit, scanner);
                // repeat = false;
            }else if(userOption.equals("2")){
                System.out.println("You are signing up: ");
                signUp(keyFrom_pinit, scanner);
                // repeat = false;
            }else if(userOption.equals("3")){
                System.out.println("You are leaving this program");
                repeat = false;
            }else{
                System.out.println("Your input is not understandable.");
            }
    


        
    }//wuwt happened
    scanner.close();


}}

