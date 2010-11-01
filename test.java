/*
javac test.java
java -classpath . test
*/

public class test
{
    public static void main( String [] args )
    {
        String path = args[0];
        String newpath = path.replaceAll( System.getProperty("file.separator") + "*$", "" );
        
        System.out.println( "Old path: " + path );
        System.out.println( "New path: " + newpath );
    }
}
