package com.codepraxis.orrery;

public class OrreryData {
	
    

    /* This header file is simply a data file that defines the
     * Keplerian Elements of each planets orbit, that will be
     * used to compute each planets location at each time step.
     * There are six elements for each orbit, each with a
     * corresponding error factor.
     * The elements are given in the following format:
     *
     * Semi-Major Axis: PLANETNAME_SM_AXIS
     *    These constants have units of au, or astronomical units
     *
     * Semi-Major Axis Error: PLANETNAME_SM_ERROR
     *    These constants have units of au/century, or astronomical
     *    units per century
     *
     * Eccentricity: PLANETNAME_ECC
     *    These constants are unitless, describing the shape of the
     *    orbit
     *
     * Eccentricity Error: PLANETNAME_ECC_ERROR
     *    These constants have units of units per century
     *
     * Inclination: PLANETNAME_INC
     *    Though these may not be used in final calculations, these
     *    constants have units of degrees
     *
     * Inclination Error: PLANETNAME_INC_ERROR
     *    Though these may not be used in final calculations, these
     *    constants have units of degrees per century
     *
     * Mean Longitude: PLANETNAME_MLONG
     *    These constants have units of degrees
     *
     * Mean Longitude Error: PLANETNAME_MLONG_ERROR
     *    These constants have units of degrees per century
     * 
     * Longitude of Perihelion: PLANETNAME_PERI
     *    These constants have units of degrees
     *
     * Longitude of Perihelion Error: PLANETNAME_PERI_ERROR
     *    These constants have units of degrees per century
     *
     * Longitude of Ascending Node: PLANETNAME_ASC
     *    These constants have units of degrees
     *
     * Longitude of Ascending Node Error: PLANETNAME_ASC_ERROR
     *    These constants have units of degrees per century
     *
     * Radius of Planetary Sphere: PLANETNAME_RADIUS
     *    These constants have units of astronautical unit
     */

     /* NOTE: ********************************************
      * "A" values are for the more specific time interval
      * 1800 AD - 2050 AD
      *
      * "B" values are for the less specific time interval
      * 3000 BC - 3000 AD
      */

                      /*MERCURY*/
    public static float MERCURY_RADIUS                = 0.00016308f;

    /* "A" SET */

    public static float MERCURY_SM_AXIS_A             = 0.38709927f;
    public static float MERCURY_SM_ERROR_A            = 0.00000037f;

    public static float MERCURY_ECC_A                 = 0.20563593f;
    public static float MERCURY_ECC_ERROR_A           = 0.00001906f;

    public static float MERCURY_INC_A                 = 7.00497902f;
    public static float MERCURY_INC_ERROR_A          = -0.00594749f;

    public static float MERCURY_MLONG_A             = 252.25032350f;
    public static float MERCURY_MLONG_ERROR_A    = 149472.67411175f;

    public static float MERCURY_PERI_A              =  77.45779628f;
    public static float MERCURY_PERI_ERROR_A        =   0.16047689f;

    public static float MERCURY_ASC_A               =  48.33076593f;
    public static float MERCURY_ASC_ERROR_A         =  -0.12534081f;

    /* "B" SET */

    public static float MERCURY_SM_AXIS_B            =  0.38709843f;
    public static float MERCURY_SM_ERROR_B           =  0.00000000f;

    public static float MERCURY_ECC_B                =  0.20563661f;
    public static float MERCURY_ECC_ERROR_B          =  0.00002123f;

    public static float MERCURY_INC_B                =  7.00559432f;
    public static float MERCURY_INC_ERROR_B          = -0.00590158f;

    public static float MERCURY_MLONG_B             = 252.25166724f;
    public static float MERCURY_MLONG_ERROR_B    = 149472.67486623f;

    public static float MERCURY_PERI_B               = 77.45771895f;
    public static float MERCURY_PERI_ERROR_B         =  0.15940013f;

    public static float MERCURY_ASC_B                = 48.33961819f;
    public static float MERCURY_ASC_ERROR_B          = -0.12214182f;

                        /*VENUS*/

    public static float VENUS_RADIUS                = 0.00040454f;

    /* "A" SET */

    public static float VENUS_SM_AXIS_A             = 0.72333566f;
    public static float VENUS_SM_ERROR_A           =  0.00000390f;

    public static float VENUS_ECC_A                =  0.00677672f;
    public static float VENUS_ECC_ERROR_A          = -0.00004107f;

    public static float VENUS_INC_A                =  3.39467605f;
    public static float VENUS_INC_ERROR_A         =  -0.00078890f;

    public static float VENUS_MLONG_A            =  181.97909950f;
    public static float VENUS_MLONG_ERROR_A     = 58517.81538729f;

    public static float VENUS_PERI_A              = 131.60246718f;
    public static float VENUS_PERI_ERROR_A         =  0.00268329f;

    public static float VENUS_ASC_A               =  76.67984255f;
    public static float VENUS_ASC_ERROR_A         =  -0.27769418f;

    /* "B" SET */

    public static float VENUS_SM_AXIS_B          =    0.72332102f;
    public static float VENUS_SM_ERROR_B         =   -0.00000026f;

    public static float VENUS_ECC_B              =    0.00676399f;
    public static float VENUS_ECC_ERROR_B        =   -0.00005107f;

    public static float VENUS_INC_B              =    3.39777545f;
    public static float VENUS_INC_ERROR_B        =    0.00043494f;

    public static float VENUS_MLONG_B            =  181.97970850f;
    public static float VENUS_MLONG_ERROR_B    =  58517.81560260f;

    public static float VENUS_PERI_B             =  131.76755713f;
    public static float VENUS_PERI_ERROR_B       =    0.05679648f;

    public static float VENUS_ASC_B              =   76.67261496f;
    public static float VENUS_ASC_ERROR_B        =   -0.27274174f;


                        /*EARTH*/

    public static float EARTH_RADIUS               =  0.00042587f;

    /* "A" SET */

    public static float EARTH_SM_AXIS_A          =    1.00000261f;
    public static float EARTH_SM_ERROR_A         =    0.00000562f;

    public static float EARTH_ECC_A              =    0.01671123f;
    public static float EARTH_ECC_ERROR_A        =   -0.00004392f;

    public static float EARTH_INC_A              =   -0.00001531f;
    public static float EARTH_INC_ERROR_A        =   -0.01294668f;

    public static float EARTH_MLONG_A           =   100.46457166f;
    public static float EARTH_MLONG_ERROR_A    =  35999.37244981f;

    public static float EARTH_PERI_A           =    102.93768193f;
    public static float EARTH_PERI_ERROR_A     =      0.32327364f;

    public static float EARTH_ASC_A            =      0.00000000f;
    public static float EARTH_ASC_ERROR_A      =      0.00000000f;

    /* "B" SET */

    public static float EARTH_SM_AXIS_B         =     1.00000018f;
    public static float EARTH_SM_ERROR_B        =    -0.00000003f;

    public static float EARTH_ECC_B            =      0.01673163f;
    public static float EARTH_ECC_ERROR_B      =     -0.00003661f;

    public static float EARTH_INC_B            =     -0.00054346f;
    public static float EARTH_INC_ERROR_B      =     -0.01337178f;

    public static float EARTH_MLONG_B          =    100.46691572f;
    public static float EARTH_MLONG_ERROR_B    =  35999.37306329f;

    public static float EARTH_PERI_B           =    102.93005885f;
    public static float EARTH_PERI_ERROR_B     =      0.31795260f;

    public static float EARTH_ASC_B            =     -5.11260389f;
    public static float EARTH_ASC_ERROR_B      =     -0.24123856f;

                        /*MARS*/

    public static float MARS_RADIUS             =    0.00022635f;

    /* "A" SET */

    public static float MARS_SM_AXIS_A         =     1.52371034f;
    public static float MARS_SM_ERROR_A        =     0.00001847f;

    public static float MARS_ECC_A             =     0.09339410f;
    public static float MARS_ECC_ERROR_A       =     0.00007882f;

    public static float MARS_INC_A             =     1.84969142f;
    public static float MARS_INC_ERROR_A       =    -0.00813131f;

    public static float MARS_MLONG_A           =    -4.55343205f;
    public static float MARS_MLONG_ERROR_A     = 19140.30268499f;

    public static float MARS_PERI_A            =   -23.94362959f;
    public static float MARS_PERI_ERROR_A      =     0.44441088f;

    public static float MARS_ASC_A             =    49.55953891f;
    public static float MARS_ASC_ERROR_A       =    -0.29257343f;

    /* "B" SET */

    public static float MARS_SM_AXIS_B         =     1.52371243f;
    public static float MARS_SM_ERROR_B        =     0.00000097f;

    public static float MARS_ECC_B             =     0.09336511f;
    public static float MARS_ECC_ERROR_B       =     0.00009149f;

    public static float MARS_INC_B             =     1.85181869f;
    public static float MARS_INC_ERROR_B       =    -0.00724757f;

    public static float MARS_MLONG_B           =    -4.56813164f;
    public static float MARS_MLONG_ERROR_B    =  19140.29934243f;

    public static float MARS_PERI_B            =   -23.91744784f;
    public static float MARS_PERI_ERROR_B      =     0.45223625f;

    public static float MARS_ASC_B             =    49.71320984f;
    public static float MARS_ASC_ERROR_B       =    -0.26852431f;


                        /*JUPITER*/

    public static float JUPITER_RADIUS        =         0.00462393f;

    /* "A" SET */

    public static float JUPITER_SM_AXIS_A        =      5.20288700f;
    public static float JUPITER_SM_ERROR_A       =     -0.00011607f;

    public static float JUPITER_ECC_A            =      0.04838624f;
    public static float JUPITER_ECC_ERROR_A      =     -0.00013253f;

    public static float JUPITER_INC_A            =      1.30439695f;
    public static float JUPITER_INC_ERROR_A      =     -0.00183714f;

    public static float JUPITER_MLONG_A         =      34.39644051f;
    public static float JUPITER_MLONG_ERROR_A   =    3034.74612775f;

    public static float JUPITER_PERI_A           =     14.72847983f;
    public static float JUPITER_PERI_ERROR_A     =      0.21252668f;

    public static float JUPITER_ASC_A            =    100.47390909f;
    public static float JUPITER_ASC_ERROR_A      =      0.20469106f;

    /* "B" SET - EXTRA CONSTANTS REQUIRED */

    public static float JUPITER_SM_AXIS_B       =       5.20248019f;
    public static float JUPITER_SM_ERROR_B      =      -0.00002864f;

    public static float JUPITER_ECC_B           =       0.04853590f;
    public static float JUPITER_ECC_ERROR_B     =      -0.00018026f;

    public static float JUPITER_INC_B            =      1.29861416f;
    public static float JUPITER_INC_ERROR_B      =     -0.00322699f;

    public static float JUPITER_MLONG_B          =     34.33479152f;
    public static float JUPITER_MLONG_ERROR_B    =   3034.90371757f;

    public static float JUPITER_PERI_B           =     14.27495244f;
    public static float JUPITER_PERI_ERROR_B     =      0.18199196f;

    public static float JUPITER_ASC_B           =     100.29282654f;
    public static float JUPITER_ASC_ERROR_B     =       0.13024619f;

    public static float JUPITER_b               =      -0.00012452f;
    public static float JUPITER_c               =       0.06064060f;
    public static float JUPITER_s               =      -0.35635438f;
    public static float JUPITER_f                =     38.35125000f;


                        /*SATURN*/

    public static float SATURN_RADIUS            =     0.00383133f;

    /* "A" SET */

    public static float SATURN_SM_AXIS_A         =     9.53667594f;
    public static float SATURN_SM_ERROR_A        =    -0.00125060f;

    public static float SATURN_ECC_A             =     0.05386179f;
    public static float SATURN_ECC_ERROR_A       =    -0.00050991f;

    public static float SATURN_INC_A             =     2.48599187f;
    public static float SATURN_INC_ERROR_A       =     0.00193609f;

    public static float SATURN_MLONG_A           =    49.95424423f;
    public static float SATURN_MLONG_ERROR_A    =   1222.49362201f;

    public static float SATURN_PERI_A            =    92.59887831f;
    public static float SATURN_PERI_ERROR_A      =    -0.41897216f;

    public static float SATURN_ASC_A             =   113.66242448f;
    public static float SATURN_ASC_ERROR_A       =    -0.28867794f;

    /* "B" SET - EXTRA CONSTANTS REQUIRED */

    public static float SATURN_SM_AXIS_B          =    9.54149883f;
    public static float SATURN_SM_ERROR_B         =   -0.00003065f;

    public static float SATURN_ECC_B              =    0.05550825f;
    public static float SATURN_ECC_ERROR_B        =   -0.00032044f;

    public static float SATURN_INC_B              =    2.49424102f;
    public static float SATURN_INC_ERROR_B        =    0.00451969f;

    public static float SATURN_MLONG_B            =   50.07571329f;
    public static float SATURN_MLONG_ERROR_B      = 1222.11494724f;

    public static float SATURN_PERI_B             =   92.86136063f;
    public static float SATURN_PERI_ERROR_B       =    0.54179478f;

    public static float SATURN_ASC_B              =  113.63998702f;
    public static float SATURN_ASC_ERROR_B        =   -0.35015002f;

    public static float SATURN_b                  =    0.00025899f;
    public static float SATURN_c                  =   -0.13434469f;
    public static float SATURN_s                  =    0.87320147f;
    public static float SATURN_f                  =   38.35125000f;


                        /*URANUS*/

    public static float URANUS_RADIUS             =    0.00168893f;

    /* "A" SET */

    public static float URANUS_SM_AXIS_A         =    19.18916464f;
    public static float URANUS_SM_ERROR_A        =    -0.00196176f;

    public static float URANUS_ECC_A             =     0.04725744f;
    public static float URANUS_ECC_ERROR_A       =    -0.00004397f;

    public static float URANUS_INC_A             =     0.77263783f;
    public static float URANUS_INC_ERROR_A       =    -0.00242939f;

    public static float URANUS_MLONG_A          =    313.23810451f;
    public static float URANUS_MLONG_ERROR_A    =    428.48202785f;

    public static float URANUS_PERI_A           =    170.95427630f;
    public static float URANUS_PERI_ERROR_A     =      0.40805281f;

    public static float URANUS_ASC_A            =     74.01692503f;
    public static float URANUS_ASC_ERROR_A      =      0.04240589f;

    /* "B" SET - EXTRA CONSTANTS REQUIRED */

    public static float URANUS_SM_AXIS_B        =     19.18797948f;
    public static float URANUS_SM_ERROR_B       =     -0.00020455f;

    public static float URANUS_ECC_B            =      0.04685740f;
    public static float URANUS_ECC_ERROR_B      =     -0.00001550f;

    public static float URANUS_INC_B            =      0.77298127f;
    public static float URANUS_INC_ERROR_B      =     -0.00180155f;

    public static float URANUS_MLONG_B          =    314.20276625f;
    public static float URANUS_MLONG_ERROR_B    =    428.49512595f;

    public static float URANUS_PERI_B           =    172.43404441f;
    public static float URANUS_PERI_ERROR_B     =      0.09266985f;

    public static float URANUS_ASC_B            =     73.96250215f;
    public static float URANUS_ASC_ERROR_B      =      0.05739699f;

    public static float URANUS_b                =      0.00058331f;
    public static float URANUS_c                =     -0.97731848f;
    public static float URANUS_s                =      0.17689245f;
    public static float URANUS_f                =      7.67025000f;


                        /*NEPTUNE*/

    public static float NEPTUNE_RADIUS           =     0.00164123f;

    /* "A" SET */

    public static float NEPTUNE_SM_AXIS_A        =     30.06992276f;
    public static float NEPTUNE_SM_ERROR_A       =      0.00026291f;

    public static float NEPTUNE_ECC_A            =      0.00859048f;
    public static float NEPTUNE_ECC_ERROR_A      =      0.00005105f;

    public static float NEPTUNE_INC_A            =      1.77004347f;
    public static float NEPTUNE_INC_ERROR_A      =      0.00035372f;

    public static float NEPTUNE_MLONG_A          =    -55.12002969f;
    public static float NEPTUNE_MLONG_ERROR_A    =    218.45945325f;

    public static float NEPTUNE_PERI_A           =     44.96476227f;
    public static float NEPTUNE_PERI_ERROR_A    =      -0.32241464f;

    public static float NEPTUNE_ASC_A            =    131.78422574f;
    public static float NEPTUNE_ASC_ERROR_A      =     -0.00508664f;

    /* "B" SET - EXTRA CONSTANTS REQUIRED */

    public static float NEPTUNE_SM_AXIS_B        =     30.06952752f;
    public static float NEPTUNE_SM_ERROR_B       =      0.00006447f;

    public static float NEPTUNE_ECC_B            =      0.00895439f;
    public static float NEPTUNE_ECC_ERROR_B      =      0.00000818f;

    public static float NEPTUNE_INC_B            =      1.77005520f;
    public static float NEPTUNE_INC_ERROR_B      =      0.00022400f;

    public static float NEPTUNE_MLONG_B          =    304.22289287f;
    public static float NEPTUNE_MLONG_ERROR_B    =    218.46515314f;

    public static float NEPTUNE_PERI_B           =     46.68158724f;
    public static float NEPTUNE_PERI_ERROR_B     =      0.01009938f;

    public static float NEPTUNE_ASC_B            =    131.78635853f;
    public static float NEPTUNE_ASC_ERROR_B      =     -0.00606302f;

    public static float NEPTUNE_b               =      -0.00041348f;
    public static float NEPTUNE_c                =      0.68346318f;
    public static float NEPTUNE_s                =     -0.10162547f;
    public static float NEPTUNE_f                =      7.67025000f;


}
