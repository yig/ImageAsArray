/*
javac arrayMult.java
# 50 MB = 50*1024*1024/4 = 13107200
java -classpath . arrayMult 13107200 slow
java -classpath . arrayMult 13107200 fast

## slow:
sum: 917504000
CPU time seconds: 0.195225
## fast:
sum: 917504000
CPU time seconds: 0.050273
*/

import java.nio.*; // IntBuffer, ByteBuffer
import java.lang.management.*; // getCPUTimeNanos()
import java.text.*; // DecimalFormat

public class arrayMult
{
    // From http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking
    public static long getCPUTimeNanos()
    {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return
            bean.isCurrentThreadCpuTimeSupported()
            ? bean.getCurrentThreadCpuTime()
            : 0L
            ;
    }
    public static double getCPUTimeSeconds()
    {
        return (double)getCPUTimeNanos() * 1e-9;
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
