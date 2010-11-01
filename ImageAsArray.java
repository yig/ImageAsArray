/*
javac ImageAsArray.java
java -classpath . ImageAsArray
*/

import java.nio.*; // IntBuffer, ByteBuffer
import java.lang.management.*; // getCPUTimeNanos()
import java.text.*; // DecimalFormat

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.regex;

public class ImageAsArray
{
    // http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking
    public static long getCPUTimeNanos( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
            bean.getCurrentThreadCpuTime( ) : 0L;
    }
    
    /*
    Given an input path 'path',
    returns a 
    */
    public static String UniquePath( String path )
    {
        int startingNumber = 1;
        String suffix = "";
        
        {
            regex.Pattern endPattern = regex.Pattern.compile( "( [0-9]+)?.[a-zA-Z0-9]+$" );
            regex.Matcher endMatcher = endPattern.matches( path );
            if( endMatcher.find() )
            {
                
            }
        }
        
        while( new File( path ).exists() )
        {
            
        }
    }
    
    public static byte[] LoadImageAsByteArray( String path )
    {
        BufferedImage image = ImageIO.read( new File( path ) );
    }
    
    public static void SaveImageFromByteArray( byte[], String path )
    throws java.io.IOException
    {
        final String kImageType = "png";
        
        // If it doesn't end with the right extension, add it on.
        if( !path.toLowerCase().endsWith( "." + kImageType.toLowerCase() ) )
        {
            path = path + "." + kImageType;
        }
        
        // If the file exists, bail!
        File output_path = new File( path );
        if( output_path.exists() )
        {
            throw new java.io.IOException( "File exists: " + path );
        }
        
        BufferedImage image = ...;
        ImageIO.write( image, kImageType, new File( path ) );
    }
    
    public static void main( String [] args )
    {
        for( int i = 0; i < args.length; ++i )
        {
            System.out.println( "args[" + i + "]: " + args[i] );
        }
        
        int length = Integer.parseInt( args[0] );
        
        IntBuffer buf = IntBuffer.allocate( length );
        
        boolean slow = false;
        if( "slow".equals( args[1] ) ) slow = true;
        System.out.println( "Slow: " + slow );
        
        for( int i = 0; i < length; ++i )
        {
            buf.put( i, 100 );
        }
        
        long cputimenanos = 0;
        if( slow )
        {
            cputimenanos = getCPUTimeNanos();
            for( int i = 0; i < length; ++i )
            {
                buf.put( i, (int)( buf.get(i) * .7 ) );
            }
            cputimenanos = getCPUTimeNanos() - cputimenanos;
        }
        else
        {
            int[] arr = buf.array();
            
            cputimenanos = getCPUTimeNanos();
            for( int i = 0; i < arr.length; ++i )
            {
                arr[i] *= .7;
            }
            cputimenanos = getCPUTimeNanos() - cputimenanos;
        }
        
        // So that the above can't be optimized away.
        // UPDATE: Didn't make a difference.
        long sum = 0;
        for( int i = 0; i < length; ++i )
        {
            sum += buf.get(i);
        }
        System.out.println( "sum: " + sum );
        
        System.out.println( "CPU time nanos: " + cputimenanos );
        
        DecimalFormat df = new DecimalFormat("#.#########");
        System.out.println( "CPU time seconds: " + df.format( ((double)cputimenanos)*1e-9 ) );
    }
}
