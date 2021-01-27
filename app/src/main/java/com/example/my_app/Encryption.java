package com.example.my_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Encryption {

    public boolean Encrypt(String fileName,byte[] key)
    {
        File fin = new File (fileName);
        FileInputStream input = null ;
        FileOutputStream output = null ;

        byte [] buffer = new byte[(int)fin.length()];

        try {
            input = new FileInputStream(fin);
            input.read(buffer);
            output = new FileOutputStream(fin);

            buffer = DNAMapping(buffer);
            buffer = AddKey(buffer,key);
            buffer = NormalChange(buffer);

            output.write(buffer);


            return true ;
        } catch (Exception ex) {
            return false ;
        }
        finally{
            try {
                input.close();
            } catch (Exception ex) {
                System.out.print("Encryption Error");
            }
            try {
                output.close();
            } catch (Exception ex) {
                System.out.print("Error");
            }
        }//end finally

    }//end function


    private byte[] AddKey(byte[] s , byte[] key )
    {
        int i;
        for(i=0;i<key.length;i++) {
            if (s[i] == key[i])//the tow operands are equals so the output is A
                s[i] = 65;
            else if ((s[i] == 65 && key[i] == 84) || (s[i] == 67 && key[i] == 71) ||
                    (s[i] == 71 && key[i] == 67) || (s[i] == 84 && key[i] == 65))//one operand is the complement of other so the output is T
                s[i] = 84;
            else if (s[i] == 65 || key[i] == 65)//One operand is A then the output is the other oprand
            {
                if (s[i] == 65)
                    s[i] = key[i];
                else
                    s[i] = s[i];
            }//end else if
            else if (s[i] == 84 || key[i] == 84)//One operand is T then the output is the complement of other oprand
            {
                if (s[i] == 84) {
                    if (key[i] == 65)
                        s[i] = 84;
                    else if (key[i] == 67)
                        s[i] = 71;
                    else if (key[i] == 71)
                        s[i] = 67;
                    else
                        s[i] = 65;
                }//end inner if
                else {
                    if (s[i] == 65)
                        s[i] = 84;
                    else if (s[i] == 67)
                        s[i] = 71;
                    else if (s[i] == 71)
                        s[i] = 67;
                    else
                        s[i] = 65;
                }//end inner else
            }//end else if
        }//end for

        return s;
    }//end function


    public byte[] DNAMapping(byte[] b)
    {
        String c1,c2,c3,c4;
        int i,j;
        String bin;
        int len = b.length;
        byte [] map = new byte[4*len];
        String[] tt = new String [len];

        for(i = 0; i<len ; i++)
        {
            String  n = Integer.toBinaryString(b[i]);

            if(n.length() == 8)
                bin = n;
            else if(n.length() <8)
            {
                bin = "0";
                int leng = 8 - n.length() ;
                for(j = 1 ; j < leng ; j++)
                    bin = bin.concat("0");
                bin = bin.concat(n);
            }//end else if
            else//negative number take just 8 character
                bin = n.substring(n.length() - 8);

            c1 = bin.substring(0,2);
            c2 = bin.substring(2,4);
            c3 = bin.substring(4,6);
            c4 = bin.substring(6,8);

            if (c1.equals("00") )
                c1 = "A";
            else if (c1.equals("01"))
                c1 = "C" ;
            else if (c1.equals("10"))
                c1 = "G" ;
            else if(c1.equals("11"))
                c1 = "T" ;
            if (c2.equals("00") )
                c2 = "A";
            else if (c2.equals("01"))
                c2 = "C" ;
            else if (c2.equals("10"))
                c2 = "G" ;
            else if(c2.equals("11"))
                c2 = "T" ;
            if (c3.equals("00") )
                c3 = "A";
            else if (c3.equals("01"))
                c3 = "C" ;
            else if (c3.equals("10"))
                c3 = "G" ;
            else if(c3.equals("11"))
                c3 = "T" ;
            if (c4.equals("00") )
                c4 = "A";
            else if (c4.equals("01"))
                c4 = "C" ;
            else if (c4.equals("10"))
                c4 = "G" ;
            else if(c4.equals("11"))
                c4 = "T" ;
            bin = c1.concat(c2).concat(c3).concat(c4);
            byte[] value = bin.getBytes() ;
            for(j = 0 ;j<4;j++)
                map[4*i+j] = value[j];
        }//end for
        return map ;

    }//end function

    private  byte[] NormalChange(byte[] c)//change from DNA Values to normal character
    {
        String arr[] = {"AAAA" , "AAAC" , "AAAG" , "AAAT" , "AACA" , "AACC","AACG" , "AACT" , "AAGA" , "AAGC" , "AAGG" ,"AAGT" , "AATA" , "AATC" , "AATG" , "AATT" , "ACAA" , "ACAC" , "ACAG" , "ACAT" , "ACCA" , "ACCC" , "ACCG" , "ACCT" , "ACGA" ,"ACGC" , "ACGG" , "ACGT" , "ACTA" , "ACTC" , "ACTG" , "ACTT" ,
                "CAAA" , "CAAC" , "CAAG" , "CAAT" , "CACA" , "CACC" , "CACG" , "CACT" , "CAGA" , "CAGC" , "CAGG" , "CAGT" , "CATA" , "CATC" , "CATG" , "CATT" , "CCAA" , "CCAC" , "CCAG" , "CCAT" , "CCCA" , "CCCC" , "CCCG" , "CCCT" , "CCGA" , "CCGC" ,"CCGG" , "CCGT" , "CCTA" , "CCTC" , "CCTG" , "CCTT" ,
                "GAAA" , "GAAC" , "GAAG" , "GAAT" , "GACA" , "GACC" , "GACG" , "GACT" , "GAGA" , "GAGC" , "GAGG" , "GAGT" , "GATA" , "GATC" , "GATG" , "GATT" , "GCAA" , "GCAC" , "GCAG" , "GCAT" , "GCCA" , "GCCC" , "GCCG" , "GCCT" , "GCGA" , "GCGC" , "GCGG" , "GCGT" , "GCTA" , "GCTC" , "GCTG" , "GCTT" ,
                "TAAA" ,"TAAC" , "TAAG" , "TAAT" , "TACA" , "TACC" , "TACG" , "TACT" , "TAGA" , "TAGC" , "TAGG" , "TAGT" , "TATA" , "TATC" , "TATG" , "TATT" , "TCAA" , "TCAC" , "TCAG" , "TCAT" , "TCCA" , "TCCC" , "TCCG" , "TCCT" , "TCGA" , "TCGC" , "TCGG" , "TCGT" , "TCTA" , "TCTC" , "TCTG" , "TCTT" ,
                "AGAA" , "AGAC" , "AGAG" , "AGAT" , "AGCA" , "AGCC" , "AGCG" , "AGCT" ,"AGGA" , "AGGC" , "AGGG" , "AGGT" , "AGTA" , "AGTC" , "AGTG" , "AGTT" , "ATAA" , "ATAC" , "ATAG" , "ATAT" , "ATCA" ,"ATCC" , "ATCG" , "ATCT" , "ATGA" , "ATGC" , "ATGG" , "ATGT" , "ATTA" , "ATTC" , "ATTG" , "ATTT" ,
                "CGAA" , "CGAC" , "CGAG" , "CGAT" , "CGCA" , "CGCC" , "CGCG" , "CGCT" , "CGGA" , "CGGC" , "CGGG" , "CGGT" , "CGTA" , "CGTC" , "CGTG" ,"CGTT" , "CTAA" , "CTAC" , "CTAG" , "CTAT" , "CTCA" , "CTCC" , "CTCG" , "CTCT" , "CTGA" , "CTGC" , "CTGG" , "CTGT" , "CTTA" , "CTTC" , "CTTG" , "CTTT" ,
                "GGAA" , "GGAC" , "GGAG" , "GGAT" , "GGCA" , "GGCC" , "GGCG" , "GGCT" , "GGGA" , "GGGC" , "GGGG" , "GGGT" , "GGTA" , "GGTC" , "GGTG" , "GGTT" , "GTAA" , "GTAC" , "GTAG" , "GTAT" , "GTCA" , "GTCC" ,"GTCG" , "GTCT" , "GTGA" , "GTGC" , "GTGG" , "GTGT" , "GTTA" , "GTTC" , "GTTG" , "GTTT" ,
                "TGAA" , "TGAC" , "TGAG" ,"TGAT" , "TGCA" , "TGCC" , "TGCG" , "TGCT" , "TGGA" , "TGGC" , "TGGG" , "TGGT" , "TGTA" , "TGTC" , "TGTG" , "TGTT" ,"TTAA" , "TTAC" , "TTAG" , "TTAT" , "TTCA" , "TTCC" , "TTCG" , "TTCT" , "TTGA" , "TTGC" , "TTGG" , "TTGT" , "TTTA" ,"TTTC" , "TTTG" , "TTTT"};
        int len = c.length;
        int length = len/4 ;
        byte[] res = new byte[length];

        byte [] temp = new byte[4];
        String x = null;
        int i,j=0,k;

        for(i = 0 ; i<len ; i+=4)
        {

            for(k =0 ; k<4 ;k++)
                temp[k]= c[i+k];
            x= new String(temp);
            for(k=0 ; k<arr.length ; k++)
                if(x.equals(arr[k]))
                {
                    res[j] = (byte)k;
                    j++;
                    break;
                }

        }//end for

        return res ;

    }//end function

}//end of class
