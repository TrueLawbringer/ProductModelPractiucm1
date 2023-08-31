import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 *
 * @author wulft
 *
 * Demonstrates how to use Java NIO, a thread safe File IO library
 * to write a text file
 */
public class ProductGenerator
{


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Test data the lines of the file we will write
        Boolean response = true;
        Scanner in = new Scanner(System.in);
        ArrayList <String>recs = new ArrayList<>();
        while(response)
        {
            recs.add(SafeInput.getNonZeroLenString(in, "Input product data; format in IDno, Name, Description, Cost"));
            response = SafeInput.getYNConfirm(in, "Do you have more products?");
        }

/*
        Here is the data file we are reading:
        000001, Pipeweed, Long Bottom Leaf, 600.0
        000002, Lembas, Elven Wayfare Bread, 200.0
        000003, Wine, Woodland Elf Wine, 400.0
        000004, Mushrooms, Farmer Took’s Finest, 125.0
        000005, Mithril, Enchanted Dwarven Armor, 3000.0
*/
        // uses a fixed known path:
        //  Path file = Paths.get("c:\\My Documents\\data.txt");

        // use the toolkit to get the current working directory of the IDE
        // will create the file within the Netbeans project src folder
        // (may need to adjust for other IDE)
        // Not sure if the toolkit is thread safe...
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\producttestdata.txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));


            for(String rec : recs)
            {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
