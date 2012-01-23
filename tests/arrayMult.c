/*
gcc -O3 arrayMult.c -o arrayMult
# 50 MB = 50*1024*1024/4 = 13107200
./arrayMult 13107200

## With -O3 (or -O2 or -O1):
sum: 917504000
CPU time seconds: 0.048889000
## Without any -O flag:
sum: 917504000
CPU time seconds: 0.060138000
*/

#include <stdlib.h> // malloc, atoi, exit
#include <stdio.h> // printf
#include <sys/time.h> // gettimeofday(), getrusage()
#include <sys/resource.h> // getrusage()

static double cpuusage()
{
    struct rusage rus;
    getrusage( RUSAGE_SELF, &rus );
    double user = (double)rus.ru_utime.tv_sec + 1e-6 * (double)rus.ru_utime.tv_usec;
    double sys = (double)rus.ru_stime.tv_sec + 1e-6 * (double)rus.ru_stime.tv_usec;

    return user + sys;
}

int main( int argc, char* argv[] )
{
    int length = 0;
    int i = 0;
    int* arr = 0;
    double cputimesecs = 0.;
    long sum = 0;
    
    for( i = 0; i < argc; ++i )
    {
        printf( "argv[%d]: %s\n", i, argv[i] );
    }
    
    if( argc != 2 )
    {
        fprintf( stderr, "Usage: %s length-of-int-buffer\n", argv[0] );
        exit(-1);
    }
    length = atoi( argv[1] );
    
    
    arr = calloc( length, sizeof(int) );
    for( i = 0; i < length; ++i )
    {
        arr[i] = 100;
    }
    
    cputimesecs = cpuusage();
    for( i = 0; i < length; ++i )
    {
        arr[i] *= .7;
    }
    cputimesecs = cpuusage() - cputimesecs;
    
    for( i = 0; i < length; ++i )
    {
        sum += arr[i];
    }
    printf( "sum: %ld\n", sum );
    
    // To verify that the right amount of memory is allocated.
    // sleep( 5 );
    free( arr );
    arr = 0;
    
    printf( "CPU time seconds: %.9f\n", cputimesecs );
    
    return 0;
}
