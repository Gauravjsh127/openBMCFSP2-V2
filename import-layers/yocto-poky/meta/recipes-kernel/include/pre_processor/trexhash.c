/******************************************************************************
 *
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * IBM Flexible Support Processor
 *
 * (c) Copyright IBM Corp. 2004
 *
 * The source code is for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office.
 *
 *****************************************************************************/


/*! \file trexhash.C
 *  \brief Program to generate hash values based on character strings.
 *
 * This is a simple and fast hash function to be used
 * to hash a printf like string into a four byte value.
 * The string is to be input as a parameter to the
 * executable of this code.  This program will print the
 * hash value to standard out.  It is up to the caller to
 * redirect the output of this program to the necessary
 * location.
 *
 * Usage: trexhash <printf_string> <key>
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <sys/types.h>
typedef u_int32_t u32 ;
typedef u_int8_t u8 ;
#include "jhash.h"

/*!
 * \fn int main(int argc, char *argv[])
 * \brief hash a variable-length key into a 32-bit value
 *
 * Every bit of the key affects every bit of the return value.  Every 1-bit
 * and 2-bit delta achieves avalanche.\n
 * About 36+6len instructions.\n
 * The best hash table sizes are powers of 2.  There is no need to do mod
 * a prime (mod is sooo slow!).\n
 * If you need less than 32 bits, use a bitmask. For example, if you need
 * only 10 bits, do  h = (h & hashmask(10));  In which case, the hash table
 * should have hashsize(10) elements.\n
 * If you are hashing n strings (ub1 **)k, do it like this:\n
 * for (i=0, h=0; i<n; ++i) h = hash( k[i], len[i], h);\n\n
 * By Bob Jenkins, 1996.  bob_jenkins@burtleburtle.net.  You may use this
 * code any way you wish, private, educational, or commercial.  It's free.
 * See http://burlteburtle.net/bob/hash/evahash.html
 * Use for hash table lookup, or anything where one collision in 2^32 is
 * acceptable.  Do NOT use for cryptographic purposes.
 * \return 32-bit hash value
*/
/*
static const char copyright [] __attribute__((unused))
                               __attribute__((section (".comment"))) =
	"Licensed Materials - Property of IBM\n"
	"IBM Flexible Support Processor Licensed Material\n"
	"(c) Copyright IBM Corp 2004 All Rights Reserved\n"
	"US Government Users Restricted Rights - Use, duplication\n"
	"or disclosure restricted by GSA ADP Schedule Contract\n"
	"with IBM Corp.";
*/
int main(int argc, char *argv[])
{
    char *k;
    unsigned int c;

    if (argc != 3)
    {
        printf("USAGE: %s <printf_string> <key>\n", __FILE__);
        exit(1);
    }

    k = argv[1];

    c = atoi(argv[2]);

    c = jhash(k,strlen(k),c);

    /*-------------------------------------------- report the result */
    printf("%u",c);

    return(0);
}
