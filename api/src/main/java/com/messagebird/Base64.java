package com.messagebird;

/**
 * Cutted version of iharder's base64 implementation
 * @todo replace with actual library on next major bump
 *
 * <p>Encodes and decodes to and from Base64 notation.</p>
 * <p>Homepage: <a href="http://iharder.net/base64">http://iharder.net/base64</a>.</p>
 * 
 * <p>
 * I am placing this code in the Public Domain. Do with it as you will.
 * This software comes with no guarantees or warranties but with
 * plenty of well-wishing instead!
 * Please visit <a href="http://iharder.net/base64">http://iharder.net/base64</a>
 * periodically to check for updates or to contribute improvements.
 * </p>
 *
 * @author Robert Harder
 * @author rob@iharder.net
 * @version 2.3.7
 */
class Base64 {

/* ********  P U B L I C   F I E L D S  ******** */


    /** No options specified. Value is zero. */
    public final static int NO_OPTIONS = 0;

    /** Specify that gzipped data should <em>not</em> be automatically gunzipped. */
    public final static int DONT_GUNZIP = 4;

    /**
     * Encode using Base64-like encoding that is URL- and Filename-safe as described
     * in Section 4 of RFC3548:
     * <a href="http://www.faqs.org/rfcs/rfc3548.html">http://www.faqs.org/rfcs/rfc3548.html</a>.
     * It is important to note that data encoded this way is <em>not</em> officially valid Base64,
     * or at the very least should not be called Base64 without also specifying that is
     * was encoded using the URL- and Filename-safe dialect.
     */
     public final static int URL_SAFE = 16;


     /**
      * Encode using the special "ordered" dialect of Base64 described here:
      * <a href="http://www.faqs.org/qa/rfcc-1940.html">http://www.faqs.org/qa/rfcc-1940.html</a>.
      */
     public final static int ORDERED = 32;


/* ********  P R I V A T E   F I E L D S  ******** */


    /** The equals sign (=) as a byte. */
    private final static byte EQUALS_SIGN = (byte)'=';


    /** Preferred encoding. */
    private final static String PREFERRED_ENCODING = "US-ASCII";


    private final static byte WHITE_SPACE_ENC = -5; // Indicates white space in encoding
    private final static byte EQUALS_SIGN_ENC = -1; // Indicates equals sign in encoding


/* ********  S T A N D A R D   B A S E 6 4   A L P H A B E T  ******** */

    /**
     * Translates a Base64 value to either its 6-bit reconstruction value
     * or a negative number indicating some other meaning.
     **/
    private final static byte[] _STANDARD_DECODABET = {
        -9,-9,-9,-9,-9,-9,-9,-9,-9,                 // Decimal  0 -  8
        -5,-5,                                      // Whitespace: Tab and Linefeed
        -9,-9,                                      // Decimal 11 - 12
        -5,                                         // Whitespace: Carriage Return
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 14 - 26
        -9,-9,-9,-9,-9,                             // Decimal 27 - 31
        -5,                                         // Whitespace: Space
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,              // Decimal 33 - 42
        62,                                         // Plus sign at decimal 43
        -9,-9,-9,                                   // Decimal 44 - 46
        63,                                         // Slash at decimal 47
        52,53,54,55,56,57,58,59,60,61,              // Numbers zero through nine
        -9,-9,-9,                                   // Decimal 58 - 60
        -1,                                         // Equals sign at decimal 61
        -9,-9,-9,                                      // Decimal 62 - 64
        0,1,2,3,4,5,6,7,8,9,10,11,12,13,            // Letters 'A' through 'N'
        14,15,16,17,18,19,20,21,22,23,24,25,        // Letters 'O' through 'Z'
        -9,-9,-9,-9,-9,-9,                          // Decimal 91 - 96
        26,27,28,29,30,31,32,33,34,35,36,37,38,     // Letters 'a' through 'm'
        39,40,41,42,43,44,45,46,47,48,49,50,51,     // Letters 'n' through 'z'
        -9,-9,-9,-9,-9                              // Decimal 123 - 127
        ,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,       // Decimal 128 - 139
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 140 - 152
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 153 - 165
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 166 - 178
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 179 - 191
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 192 - 204
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 205 - 217
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 218 - 230
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 231 - 243
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9         // Decimal 244 - 255
    };


/* ********  U R L   S A F E   B A S E 6 4   A L P H A B E T  ******** */

    /**
     * Used in decoding URL- and Filename-safe dialects of Base64.
     */
    private final static byte[] _URL_SAFE_DECODABET = {
      -9,-9,-9,-9,-9,-9,-9,-9,-9,                 // Decimal  0 -  8
      -5,-5,                                      // Whitespace: Tab and Linefeed
      -9,-9,                                      // Decimal 11 - 12
      -5,                                         // Whitespace: Carriage Return
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 14 - 26
      -9,-9,-9,-9,-9,                             // Decimal 27 - 31
      -5,                                         // Whitespace: Space
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,              // Decimal 33 - 42
      -9,                                         // Plus sign at decimal 43
      -9,                                         // Decimal 44
      62,                                         // Minus sign at decimal 45
      -9,                                         // Decimal 46
      -9,                                         // Slash at decimal 47
      52,53,54,55,56,57,58,59,60,61,              // Numbers zero through nine
      -9,-9,-9,                                   // Decimal 58 - 60
      -1,                                         // Equals sign at decimal 61
      -9,-9,-9,                                   // Decimal 62 - 64
      0,1,2,3,4,5,6,7,8,9,10,11,12,13,            // Letters 'A' through 'N'
      14,15,16,17,18,19,20,21,22,23,24,25,        // Letters 'O' through 'Z'
      -9,-9,-9,-9,                                // Decimal 91 - 94
      63,                                         // Underscore at decimal 95
      -9,                                         // Decimal 96
      26,27,28,29,30,31,32,33,34,35,36,37,38,     // Letters 'a' through 'm'
      39,40,41,42,43,44,45,46,47,48,49,50,51,     // Letters 'n' through 'z'
      -9,-9,-9,-9,-9                              // Decimal 123 - 127
      ,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 128 - 139
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 140 - 152
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 153 - 165
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 166 - 178
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 179 - 191
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 192 - 204
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 205 - 217
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 218 - 230
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 231 - 243
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9         // Decimal 244 - 255
    };



/* ********  O R D E R E D   B A S E 6 4   A L P H A B E T  ******** */

    /**
     * Used in decoding the "ordered" dialect of Base64.
     */
    private final static byte[] _ORDERED_DECODABET = {
      -9,-9,-9,-9,-9,-9,-9,-9,-9,                 // Decimal  0 -  8
      -5,-5,                                      // Whitespace: Tab and Linefeed
      -9,-9,                                      // Decimal 11 - 12
      -5,                                         // Whitespace: Carriage Return
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 14 - 26
      -9,-9,-9,-9,-9,                             // Decimal 27 - 31
      -5,                                         // Whitespace: Space
      -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,              // Decimal 33 - 42
      -9,                                         // Plus sign at decimal 43
      -9,                                         // Decimal 44
      0,                                          // Minus sign at decimal 45
      -9,                                         // Decimal 46
      -9,                                         // Slash at decimal 47
      1,2,3,4,5,6,7,8,9,10,                       // Numbers zero through nine
      -9,-9,-9,                                   // Decimal 58 - 60
      -1,                                         // Equals sign at decimal 61
      -9,-9,-9,                                   // Decimal 62 - 64
      11,12,13,14,15,16,17,18,19,20,21,22,23,     // Letters 'A' through 'M'
      24,25,26,27,28,29,30,31,32,33,34,35,36,     // Letters 'N' through 'Z'
      -9,-9,-9,-9,                                // Decimal 91 - 94
      37,                                         // Underscore at decimal 95
      -9,                                         // Decimal 96
      38,39,40,41,42,43,44,45,46,47,48,49,50,     // Letters 'a' through 'm'
      51,52,53,54,55,56,57,58,59,60,61,62,63,     // Letters 'n' through 'z'
      -9,-9,-9,-9,-9                                 // Decimal 123 - 127
       ,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 128 - 139
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 140 - 152
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 153 - 165
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 166 - 178
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 179 - 191
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 192 - 204
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 205 - 217
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 218 - 230
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 231 - 243
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9         // Decimal 244 - 255
    };


/* ********  D E T E R M I N E   W H I C H   A L H A B E T  ******** */

    /**
     * Returns one of the _SOMETHING_DECODABET byte arrays depending on
     * the options specified.
     * It's possible, though silly, to specify ORDERED and URL_SAFE
     * in which case one of them will be picked, though there is
     * no guarantee as to which one will be picked.
     */
    private final static byte[] getDecodabet( int options ) {
        if( (options & URL_SAFE) == URL_SAFE) {
            return _URL_SAFE_DECODABET;
        } else if ((options & ORDERED) == ORDERED) {
            return _ORDERED_DECODABET;
        } else {
            return _STANDARD_DECODABET;
        }
    }	// end getAlphabet



    /** Defeats instantiation. */
    private Base64(){}



/* ********  D E C O D I N G   M E T H O D S  ******** */


    /**
     * Decodes four bytes from array <var>source</var>
     * and writes the resulting bytes (up to three of them)
     * to <var>destination</var>.
     * The source and destination arrays can be manipulated
     * anywhere along their length by specifying
     * <var>srcOffset</var> and <var>destOffset</var>.
     * This method does not check to make sure your arrays
     * are large enough to accomodate <var>srcOffset</var> + 4 for
     * the <var>source</var> array or <var>destOffset</var> + 3 for
     * the <var>destination</var> array.
     * This method returns the actual number of bytes that
     * were converted from the Base64 encoding.
	 * <p>This is the lowest level of the decoding methods with
	 * all possible parameters.</p>
     *
     *
     * @param source the array to convert
     * @param srcOffset the index where conversion begins
     * @param destination the array to hold the conversion
     * @param destOffset the index where output will be put
	 * @param options alphabet type is pulled from this (standard, url-safe, ordered)
     * @return the number of decoded bytes converted
     * @throws NullPointerException if source or destination arrays are null
     * @throws IllegalArgumentException if srcOffset or destOffset are invalid
     *         or there is not enough room in the array.
     * @since 1.3
     */
    private static int decode4to3(
    byte[] source, int srcOffset,
    byte[] destination, int destOffset, int options ) {

        // Lots of error checking and exception throwing
        if( source == null ){
            throw new NullPointerException( "Source array was null." );
        }   // end if
        if( destination == null ){
            throw new NullPointerException( "Destination array was null." );
        }   // end if
        if( srcOffset < 0 || srcOffset + 3 >= source.length ){
            throw new IllegalArgumentException( String.format(
            "Source array with length %d cannot have offset of %d and still process four bytes.", source.length, srcOffset ) );
        }   // end if
        if( destOffset < 0 || destOffset +2 >= destination.length ){
            throw new IllegalArgumentException( String.format(
            "Destination array with length %d cannot have offset of %d and still store three bytes.", destination.length, destOffset ) );
        }   // end if


        byte[] DECODABET = getDecodabet( options );

        // Example: Dk==
        if( source[ srcOffset + 2] == EQUALS_SIGN ) {
            // Two ways to do the same thing. Don't know which way I like best.
          //int outBuff =   ( ( DECODABET[ source[ srcOffset    ] ] << 24 ) >>>  6 )
          //              | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
            int outBuff =   ( ( DECODABET[ source[ srcOffset    ] ] & 0xFF ) << 18 )
                          | ( ( DECODABET[ source[ srcOffset + 1] ] & 0xFF ) << 12 );

            destination[ destOffset ] = (byte)( outBuff >>> 16 );
            return 1;
        }

        // Example: DkL=
        else if( source[ srcOffset + 3 ] == EQUALS_SIGN ) {
            // Two ways to do the same thing. Don't know which way I like best.
          //int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] << 24 ) >>>  6 )
          //              | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
          //              | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
            int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] & 0xFF ) << 18 )
                          | ( ( DECODABET[ source[ srcOffset + 1 ] ] & 0xFF ) << 12 )
                          | ( ( DECODABET[ source[ srcOffset + 2 ] ] & 0xFF ) <<  6 );

            destination[ destOffset     ] = (byte)( outBuff >>> 16 );
            destination[ destOffset + 1 ] = (byte)( outBuff >>>  8 );
            return 2;
        }

        // Example: DkLE
        else {
            // Two ways to do the same thing. Don't know which way I like best.
          //int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] << 24 ) >>>  6 )
          //              | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
          //              | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
          //              | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
            int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] & 0xFF ) << 18 )
                          | ( ( DECODABET[ source[ srcOffset + 1 ] ] & 0xFF ) << 12 )
                          | ( ( DECODABET[ source[ srcOffset + 2 ] ] & 0xFF ) <<  6)
                          | ( ( DECODABET[ source[ srcOffset + 3 ] ] & 0xFF )      );


            destination[ destOffset     ] = (byte)( outBuff >> 16 );
            destination[ destOffset + 1 ] = (byte)( outBuff >>  8 );
            destination[ destOffset + 2 ] = (byte)( outBuff       );

            return 3;
        }
    }   // end decodeToBytes




    /**
     * Low-level access to decoding ASCII characters in
     * the form of a byte array. <strong>Ignores GUNZIP option, if
     * it's set.</strong> This is not generally a recommended method,
     * although it is used internally as part of the decoding process.
     * Special case: if len = 0, an empty array is returned. Still,
     * if you need more speed and reduced memory footprint (and aren't
     * gzipping), consider this method.
     *
     * @param source The Base64 encoded data
     * @param off    The offset of where to begin decoding
     * @param len    The length of characters to decode
     * @param options Can specify options such as alphabet type to use
     * @return decoded data
     * @throws java.io.IOException If bogus characters exist in source data
     * @since 1.3
     */
    public static byte[] decode( byte[] source, int off, int len, int options )
    throws java.io.IOException {

        // Lots of error checking and exception throwing
        if( source == null ){
            throw new NullPointerException( "Cannot decode null source array." );
        }   // end if
        if( off < 0 || off + len > source.length ){
            throw new IllegalArgumentException( String.format(
            "Source array with length %d cannot have offset of %d and process %d bytes.", source.length, off, len ) );
        }   // end if

        if( len == 0 ){
            return new byte[0];
        }else if( len < 4 ){
            throw new IllegalArgumentException(
            "Base64-encoded string must have at least four characters, but length specified was " + len );
        }   // end if

        byte[] DECODABET = getDecodabet( options );

        int    len34   = len * 3 / 4;       // Estimate on array size
        byte[] outBuff = new byte[ len34 ]; // Upper limit on size of output
        int    outBuffPosn = 0;             // Keep track of where we're writing

        byte[] b4        = new byte[4];     // Four byte buffer from source, eliminating white space
        int    b4Posn    = 0;               // Keep track of four byte input buffer
        int    i         = 0;               // Source array counter
        byte   sbiDecode = 0;               // Special value from DECODABET

        for( i = off; i < off+len; i++ ) {  // Loop through source

            sbiDecode = DECODABET[ source[i]&0xFF ];

            // White space, Equals sign, or legit Base64 character
            // Note the values such as -5 and -9 in the
            // DECODABETs at the top of the file.
            if( sbiDecode >= WHITE_SPACE_ENC )  {
                if( sbiDecode >= EQUALS_SIGN_ENC ) {
                    b4[ b4Posn++ ] = source[i];         // Save non-whitespace
                    if( b4Posn > 3 ) {                  // Time to decode?
                        outBuffPosn += decode4to3( b4, 0, outBuff, outBuffPosn, options );
                        b4Posn = 0;

                        // If that was the equals sign, break out of 'for' loop
                        if( source[i] == EQUALS_SIGN ) {
                            break;
                        }   // end if: equals sign
                    }   // end if: quartet built
                }   // end if: equals sign or better
            }   // end if: white space, equals sign or better
            else {
                // There's a bad input character in the Base64 stream.
                throw new java.io.IOException( String.format(
                "Bad Base64 input character decimal %d in array position %d", ((int)source[i])&0xFF, i ) );
            }   // end else:
        }   // each input character

        byte[] out = new byte[ outBuffPosn ];
        System.arraycopy( outBuff, 0, out, 0, outBuffPosn );
        return out;
    }   // end decode




    /**
     * Decodes data from Base64 notation, automatically
     * detecting gzip-compressed data and decompressing it.
     *
     * @param s the string to decode
     * @return the decoded data
     * @throws java.io.IOException If there is a problem
     * @since 1.4
     */
    public static byte[] decode( String s ) throws java.io.IOException {
        return decode( s, NO_OPTIONS );
    }



    /**
     * Decodes data from Base64 notation, automatically
     * detecting gzip-compressed data and decompressing it.
     *
     * @param s the string to decode
     * @param options encode options such as URL_SAFE
     * @return the decoded data
     * @throws java.io.IOException if there is an error
     * @throws NullPointerException if <tt>s</tt> is null
     * @since 1.4
     */
    public static byte[] decode( String s, int options ) throws java.io.IOException {

        if( s == null ){
            throw new NullPointerException( "Input string was null." );
        }   // end if

        byte[] bytes;
        try {
            bytes = s.getBytes( PREFERRED_ENCODING );
        }   // end try
        catch( java.io.UnsupportedEncodingException uee ) {
            bytes = s.getBytes();
        }   // end catch
		//</change>

        // Decode
        bytes = decode( bytes, 0, bytes.length, options );

        // Check to see if it's gzip-compressed
        // GZIP Magic Two-Byte Number: 0x8b1f (35615)
        boolean dontGunzip = (options & DONT_GUNZIP) != 0;
        if( (bytes != null) && (bytes.length >= 4) && (!dontGunzip) ) {

            int head = ((int)bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00);
            if( java.util.zip.GZIPInputStream.GZIP_MAGIC == head )  {
                java.io.ByteArrayInputStream  bais = null;
                java.util.zip.GZIPInputStream gzis = null;
                java.io.ByteArrayOutputStream baos = null;
                byte[] buffer = new byte[2048];
                int    length = 0;

                try {
                    baos = new java.io.ByteArrayOutputStream();
                    bais = new java.io.ByteArrayInputStream( bytes );
                    gzis = new java.util.zip.GZIPInputStream( bais );

                    while( ( length = gzis.read( buffer ) ) >= 0 ) {
                        baos.write(buffer,0,length);
                    }   // end while: reading input

                    // No error? Get new bytes.
                    bytes = baos.toByteArray();

                }   // end try
                catch( java.io.IOException e ) {
                    e.printStackTrace();
                    // Just return originally-decoded bytes
                }   // end catch
                finally {
                    try{ baos.close(); } catch( Exception e ){}
                    try{ gzis.close(); } catch( Exception e ){}
                    try{ bais.close(); } catch( Exception e ){}
                }   // end finally

            }   // end if: gzipped
        }   // end if: bytes.length >= 2

        return bytes;
    }   // end decode

}   // end class Base64
