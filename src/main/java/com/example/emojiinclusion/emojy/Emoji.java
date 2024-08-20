package com.example.emojiinclusion.emojy;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Random;

@Entity
public class Emoji {

    @Id
    @GeneratedValue
    private Long id;

    private String stringValue;

    public Emoji() { }

    public Emoji(String value) {
        this.stringValue = value;
    }

    // gender: male, female, robot, alien1, alien2, ghost
    private static final List<String> gender = List.of("&#128104;","&#128105;");
    // skin colors
    private static final List<String> colors = List.of("&#127999;","&#127998;","&#127997;","&#127996;","&#127995;");
    // not humans: robot, alien1, alien2, ghost
    private static final List<String> nothuman = List.of("&#129302;","&#128125;","&#128126;","&#128123;");

    static Emoji random() {

        boolean ishuman = (new Random().nextInt(2) == 0);
        String emojiValue = "";
        var random = new Random();

        if (ishuman)
            emojiValue = gender.get(random.nextInt(gender.size())) + colors.get(random.nextInt(colors.size()));
        else 
            emojiValue = nothuman.get(random.nextInt(nothuman.size()));

        return new Emoji(emojiValue);
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String value) {
        this.stringValue = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}