import java.io.*;
import java.util.*;

public class TestSer {

  public static void main(String... aArguments) {
    //create a Serializable List
    Player player = new Player(6,"Charlotte");

    //serialize the List
    //note the use of abstract base class references

    try{
      //use buffering
      OutputStream file = new FileOutputStream( "test.ser" );
      OutputStream buffer = new BufferedOutputStream( file );
      ObjectOutput output = new ObjectOutputStream( buffer );
      try{
        output.writeObject(player);
      }
      finally{
        output.close();
      }
    }  
    catch(IOException ex){
      ex.printStackTrace();
    }

    //deserialize the quarks.ser file
    //note the use of abstract base class references
    
    try{
      //use buffering
      InputStream file = new FileInputStream( "test.ser" );
      InputStream buffer = new BufferedInputStream( file );
      ObjectInput input = new ObjectInputStream ( buffer );
      try{
        //deserialize the List
        Player recoveredPlayer = (Player)input.readObject();
        //display its data
        System.out.println(recoveredPlayer.playerName);
      }
      finally{
        input.close();
      }
    }
    catch(ClassNotFoundException ex){
      
    }
    catch(IOException ex){
      
    }
  }

  // PRIVATE //

  //Use Java's logging facilities to record exceptions.
  //The behavior of the logger can be configured through a
  //text file, or programmatically through the logging API.
}