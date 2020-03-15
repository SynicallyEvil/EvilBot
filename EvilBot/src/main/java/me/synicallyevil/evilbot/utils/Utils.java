package me.synicallyevil.evilbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Utils {

    /**
     * Checks if the string is actually a number using Regex.
     * @param s
     *        The string that get's checked.
     * @return
     *        True if the string is actually a number, else false.
     */
    public static boolean isNumber(String s){
        return s.matches("\\d+");
    }

    public static String capitalizeFirstCharacter(String s){
        return s.length() == 0 ? s : s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Checks if an array contains the value.
     * @param array
     *        The Object array.
     * @param value
     *        The value that needs to be checked.
     * @return
     *        True if the value is in the array, else false.
     */
    public static boolean arrayContains(Object[] array, Object value) {
        return Arrays.asList(array).contains(value);
    }

    /**
     * Creates a simples EmbedBuilder.
     * @param author
     *        The author.
     * @param title
     *        The title.
     * @param message
     *        The description.
     * @param avatar
     *        The avatar.
     * @param color
     *        The color.
     * @return
     *        A built EmbedBuilder.
     */
    public static EmbedBuilder embedBuilder(String author, String title, String message, String avatar, Color color){
        EmbedBuilder emBuilder = new EmbedBuilder();
        emBuilder.setAuthor(author);
        emBuilder.setTitle(title);
        emBuilder.setDescription(message);

        if(avatar != null)
            emBuilder.setThumbnail(avatar);

        emBuilder.setColor(color);

        return emBuilder;
    }

    /**
     * get's a random rainbow Color
     * @return
     *        A random Color.
     */
    public static Color getRandomColor(){
        Color color = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));

        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;
        final float luminance = 1.0f;
        color = Color.getHSBColor(hue, saturation, luminance);

        return color;
    }
}
