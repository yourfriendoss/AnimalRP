package lv.pi.animalrp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextDestroyer {
    String[] expressions;
    String[][] replaces;

    public TextDestroyer(String[] expressions, String[][] replaces) {
        this.expressions = expressions;
        this.replaces = replaces;
    }

    public String destroy(String message) {
        final String[] words = message.split(" ");
        final List<String> out = new ArrayList<>();

        Random y = new Random();

        for (String word : words) {
            if((word.startsWith("[") && word.endsWith("]")) || word.startsWith("@")) {
                out.add(word);
                continue;
            }
            
            if(y.nextBoolean()){
                out.add(word);
                continue;
            };

            for(String[] replacing: this.replaces) {
                word = word.replace(replacing[0], replacing[1]);
            }

            out.add(word);
            
            if(y.nextDouble() < 0.12) out.add(this.expressions[y.nextInt(this.expressions.length)]);
        }

        return String.join(" ", out);
    }
}