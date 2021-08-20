//Written by
//Ruth Mesfin mesfi020
//Namita Nair nair0025

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class HashTable <T extends Comparable<T>> {
    NGen<T>[] newTable;

    public HashTable() {
        newTable = new NGen[53];
    }


    public void add(T item, int hashFunction) { //added parameter for hash function number
        int itemIndex = 0;
        boolean noDuplicates = true;
        //determines which hash function to call
        if (hashFunction == 1) {
            itemIndex = hash1(item);
        } else if (hashFunction == 2) {
            itemIndex = hash2(item);
        } else if (hashFunction == 3) {
            itemIndex = hash3(item);
        }

        //creating new node
        NGen newItem = new NGen(item, null);
        NGen currNode = newTable[itemIndex];

        if(newTable[itemIndex] != null) { //checks if index already has a key placed in it
            while (currNode.getNext() != null) {
                if (currNode.getData().equals(newItem.getData())) {
                    noDuplicates = false;
                    break;
                }
                currNode = currNode.getNext();
            }

            if (noDuplicates){ //only adds the item if there are no duplicates
                currNode.setNext(newItem);
            }
        } else { //creates node if there is not one existing
            newTable[itemIndex]= new NGen(item, null);
        }

    }

    public void display() {
        int longest_c = 0;
        int unique_tokens=0;//class var
        int empty = 0;

        for(int i = 0; i<newTable.length; i++){
            int numb_keys = 0;
            if (newTable[i] != null) {
                NGen<T> currNode = newTable[i];
                while (currNode != null) {
                    numb_keys++;
                    currNode = currNode.getNext();
                }
            }
            //print put tokens for checks
            System.out.println(i + ": " + numb_keys);

            //The following if statement is to get number of unique tokens and the number of non-empty
            longest_c = Math.max(longest_c,numb_keys);
            if(numb_keys >= 1){
                unique_tokens += numb_keys;
            }else if(numb_keys == 0){
                empty++;
            }
        }

        int avg_c_len= unique_tokens/((newTable.length) - empty); //average collision length


        //table statistics
        System.out.println("average collision length: " + avg_c_len);
        System.out.println("longest chain: " + longest_c);
        System.out.println("unique tokens: " + unique_tokens);
        System.out.println("empty indices: " + empty);
        System.out.println("non-empty indices: " + ((newTable.length) - empty));



    }

    private int hash1(T key) { //first char + length of word
        return (key.toString().charAt(0) + key.toString().length())%newTable.length;
    }
    /*
    Hash 1 creates an index based off of the first character and the total length of the key
     */

    private int hash2(T key) { //first two char length

        if(key.toString().length() ==1){ //in the case that the key is only one char long
            return key.toString().charAt(0)%newTable.length;
        }
        return  (26 * key.toString().charAt(0)+key.toString().charAt(1))% newTable.length;

    }
    /*
    Hash 2 creates an index by adding the first two characters integer values together
     */


    private int hash3(T key) { //last char + length of word
        char lastchar = key.toString().charAt( key.toString().length()-1 );
        return (lastchar + key.toString().length())%newTable.length;
    }
    /*
    Hash 3 creates an index by adding together the length of the key and the last character
     */

    //Comparing hash functions:
    //The collision length of functions 1 and 3 are similar, most likely because the keys are of similar length and
    //there is not much difference between int values of the first char vs the last one. The 2nd hash function
    //has fewer collisions, probably because the int values of two characters added together varies more than
    //functions 1 and 3. The functions have lower collision lengths when they have smaller sets of data which is expected.

    /* 5: A Specific Hash Table
    We couldn't get the number of collisions to 0 even with table lengths over 500, but it seems it can go as low as
    the length of the number of keys before it starts having average collision lengths of 1-2. When the length of the table
    is very close to the number of keys there are less empty indices total. It is difficult to very evenly distribute
    the collisions, it seems generally they are spaced out but a few indices towards the beginning have larger chain lengths.
    However, when the table lengths are a prime number the longer chains are more evenly distributed across the table.
     */


    public static void main(String[] args) { //modified from TextScan.Java

        //finds tokens, add h.add/h.display
        HashTable newHash1 = new HashTable();
        HashTable newHash2 = new HashTable();
        HashTable newHash3 = new HashTable();
        //^^hash 1-3
        args = new String[1];
        args[0] = "keywords.txt"; //enter file name here

        Scanner readFile = null;
        String s;
        int count = 0;

        System.out.println();
        System.out.println("Attempting to read from file: " + args[0]);
        try {
            readFile = new Scanner(new File(args[0]));
        }
        catch (FileNotFoundException e) {
            System.out.println("File: " + args[0] + " not found");
            System.exit(1);
        }

        System.out.println("Connection to file: " + args[0] + " successful");
        System.out.println();

        while (readFile.hasNext()) {
            s = readFile.next();
            newHash1.add(s, 1);
            newHash2.add(s, 2);
            newHash3.add(s, 3);
            count++;
        }
        System.out.println("HASH FUNCTION 1");
        newHash1.display();
        System.out.println();
        System.out.println("HASH FUNCTION 2");
        newHash2.display();
        System.out.println();
        System.out.println("HASH FUNCTION 3");
        newHash3.display();

        System.out.println();
        System.out.println(count + " Tokens found");
        System.out.println();
//dabble:
//        String key = "+";
//        char newc = key.toString().charAt(0);
//        int a = newc;
//        System.out.println("KEY: "+(key.toString().charAt(0)+1)+ "WACK: "+ a);



    }
}
