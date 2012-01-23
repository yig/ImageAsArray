/*
javac test.java
java -classpath . test
*/

public class test
{
    public static void main( String [] args )
    {
        // rstripslash( args[0] );
        testSplitExt( args[0] );
    }
    
    public static void rstripslash( String path )
    {
        String newpath = path.replaceAll( System.getProperty("file.separator") + "*$", "" );
        
        System.out.println( "Old path: " + path );
        System.out.println( "New path: " + newpath );
    }
    
    public static void testSplitExt( String path )
    {
        String [] file_ext = SplitExt( path );
        
        System.out.println( "File: " + file_ext[0] );
        System.out.println( "Ext: " + file_ext[1] );
    }
    
    public static String [] SplitExt( String path )
    {
        String [] result = new String[2];
        
        int split_dot = path.lastIndexOf( "." );
        int split_slash = path.lastIndexOf( System.getProperty("file.separator") );
        if( -1 != split_dot && (-1 == split_slash || split_slash < split_dot) )
        {
            result[0] = path.substring( 0, split_dot );
            result[1] = path.substring( split_dot );
        }
        else
        {
            result[0] = path;
            result[1] = "";
        }
        
        return result;
    }
}
