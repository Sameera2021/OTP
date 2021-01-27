package com.example.my_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decryption {


    public boolean Decrypt(String fileName,byte[] key )
    {

        File fin = new File (fileName);
        FileInputStream in = null ;
        FileOutputStream out = null ;
        byte [] buffer = new byte[(int)fin.length()];
        try {
            in = new FileInputStream(fin);
            in.read(buffer);
            out = new FileOutputStream(fin);


            buffer = DNAChange(buffer);
            buffer = AddKey(buffer,key);
            buffer = DNAMapping(buffer);
            out.write(buffer);

            return true ;
        } catch (Exception ex) {
            return false ;

        }
        finally{
            try {
                in.close();
            } catch (Exception ex) {
                System.out.print("Error");
            }
            try {
                out.close();
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
            else if (s[i] == 84 || key[i] == 84)//One operand is T then the output is the complement other oprand
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

    private byte[] DNAChange(byte[] c)//change from normal character to DNA
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
        int length = len*4 ;
        byte[] res = new byte[length];
        String x;
        byte[] b =new byte[4];
        String [] s = new String[(len)];
        int i,j=0,k;
        for(i=0;i<len;i++)
        {
            x = Integer.toBinaryString(c[i]);
            if(x.length()>8)//if( Integer.toBinaryString(text.charAt(i)).length() > 8 )
            {
                x = x.substring(x.length()-8, x.length());
            }
            s[i] = arr[Integer.parseInt(x,2)];
        }//end for
        System.out.println();
        for(i = 0 ; i<length ; i+=4)
        {
            b = s[j].getBytes();
            for(k=0;k<4;k++)
                res[i+k] = b[k];
            j++ ;
        }//end for
        return res ;

    }//end function

    public byte[] DNAMapping(byte[] b)
    {
        String c1,c2,c3,c4;
        int i,j,num;
        String bin = null;
        int len = b.length ;
        byte [] map = new byte[(int)(len/4)];
        String temp = null  ;
        String n = null;

        for(i = 0; i<(len/4) ; i ++)
        {
            temp = Integer.toString(b[4*i]);
            for(j=1;j<4;j++) {
                temp = temp.concat(Integer.toString(b[4*i+j]));
            }//end inner for

            c1 = temp.substring(0 , 2);
            c2 = temp.substring(2 , 4);
            c3 = temp.substring(4 , 6);
            c4 = temp.substring(6 , 8);

            if(c1.equals("65"))
                c1="00";
            else if(c1.equals("67"))
                c1="01";
            else if(c1.equals("71"))
                c1 = "10";
            else if (c1.equals("84"))
                c1 = "11";
            if(c2.equals("65"))
                c2="00";
            else if(c2.equals("67"))
                c2="01";
            else if(c2.equals("71"))
                c2 = "10";
            else if (c2.equals("84"))
                c2 = "11";
            if(c3.equals("65"))
                c3="00";
            else if(c3.equals("67"))
                c3="01";
            else if(c3.equals("71"))
                c3 = "10";
            else if (c3.equals("84"))
                c3 = "11";
            if(c4.equals("65"))
                c4="00";
            else if(c4.equals("67"))
                c4="01";
            else if(c4.equals("71"))
                c4 = "10";
            else if (c4.equals("84"))
                c4 = "11";
            n = c1.concat(c2).concat(c3).concat(c4);
            //لتحويل السالب
            if(n.substring(0,1).equals("1"))
            {
                bin="0";
                for(j = 1;j<n.length() ; j++ )
                    if(n.substring(j, j+1).equals("1"))
                        bin = bin.concat("0");
                    else
                        bin = bin.concat("1");
                num = -1*(Integer.parseInt(bin,2)+1);
            }//end if
            else
                num = Integer.parseInt(n, 2);

            map[i] = (byte)num ;

        }//end for

        return map ;
    }//end function


}
