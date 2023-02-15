package fr.benril.localmailserver.database;

import java.util.ArrayList;
import java.util.List;

public class Hasher {
    public String hash(String toHash){
        int lenght = toHash.length();
        List<Character> a = new ArrayList<>();
        List<Character> b = new ArrayList<>();

        for(int i = 0 ; i < lenght ; i++){
            if(i%2 == 0){
                a.add(toHash.charAt(i));
            }else{
                b.add(toHash.charAt(i));
            }
        }
        StringBuilder hashed = new StringBuilder();
        hashed.append(lenght);
        for(char c : a){hashed.append(c);}
        for(char c : b){hashed.append(c);}
        return hashed.toString();
    }
}
