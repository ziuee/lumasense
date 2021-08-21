package me.luma.client.management.gui.alt.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;

public class Strings {
    //public static final Pattern COLOR_CODE_PATTERN = Pattern.compile("(?i)\§[0-9A-FK-OR]");

    public static Color getColor(String name) {
        try {
            return (Color)Color.class.getField(name.toUpperCase()).get((Object)null);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException var2) {
            return null;
        }
    }

    public static String translateColors(String string) {
        String[] raw = string.split(" ");
        String result = "";
        List<String> styles = new ArrayList();
        String lastColor = "";

        for(int wordIndex = 0; wordIndex < raw.length; ++wordIndex) {
            String word = raw[wordIndex];
            char[] chars = word.toCharArray();

            for(int charIndex = 0; charIndex < chars.length; ++charIndex) {
                char c = chars[charIndex];
                if (isColorSign(c) && charIndex + 1 < chars.length) {
                    char nextChar = chars[charIndex + 1];
                    String color = c + String.valueOf(nextChar);
                    /*if (isColor(color)) {
                        if (nextChar != 'l' && nextChar != 'o' && nextChar != 'm' && nextChar != 'n') {
                            lastColor = color;
                            styles.clear();
                        } else if (!styles.contains(String.valueOf(nextChar))) {
                            styles.add(String.valueOf(nextChar));
                        }
                    }*/
                }
            }

            String styleCodes = "";

            for(int i = 0; i < styles.size(); ++i) {
                styleCodes = styleCodes + "&" + (String)styles.get(i);
            }

            result = result + (wordIndex == 0 ? "" : " ") + word + lastColor + styleCodes;
        }

        return simpleTranslateColors(result);
    }

    public static String simpleTranslateColors(String string) {
        return string.replace("&", "§");
    }

    /*public static String stripColors(String string) {
        return COLOR_CODE_PATTERN.matcher(simpleTranslateColors(string)).replaceAll("");
    }*/

    /*public static boolean startsWithColor(String string) {
        string = simpleTranslateColors(string);
        return string.length() < 2 ? false : COLOR_CODE_PATTERN.matcher(string.substring(0, 2)).matches();
    }*/

    public static boolean isColorSign(char c) {
        return simpleTranslateColors(String.valueOf(c)).equals("§");
    }

    /*public static boolean isColor(String text) {
        return COLOR_CODE_PATTERN.matcher(simpleTranslateColors(text)).matches();
    }*/

    public static String capitalizeOnlyFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase();
    }

    public static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
    }

    /*public static int getStringWidth(String text) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(stripColors(text));
    }*/

    /*public static int getMaxWidth(String[] lines) {
        int maxWidth = 0;

        for(int i = 0; i < lines.length; ++i) {
            int width = getStringWidth(lines[i]);
            if (maxWidth < width) {
                maxWidth = width;
            }
        }

        return maxWidth;
    }*/

    public static int getMaxChars(String[] lines) {
        int maxChars = 0;

        for(int i = 0; i < lines.length; ++i) {
            int chars = lines[i].toCharArray().length;
            if (maxChars < chars) {
                maxChars = chars;
            }
        }

        return maxChars;
    }

    public static String multiplyString(String string, int amount) {
        return multiplyString(string, (String)null, amount);
    }

    public static String multiplyString(String string, String glue, int amount) {
        String result = string;
        if (glue == null) {
            glue = "";
        }

        for(int i = 0; i < amount; ++i) {
            result = result + (i == 0 ? "" : glue) + string;
        }

        return result;
    }

    public static boolean isEmpty(String string) {
        if (string.isEmpty()) {
            return true;
        } else {
            string = string.trim();
            if (string.isEmpty()) {
                return true;
            } else {
                return string == "" || string == null;
            }
        }
    }

    public static boolean isEmpty(char[] chars) {
        return chars.length == 0 ? true : isEmpty(chars.toString());
    }

    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String convertToUnix(String windows, String unix) {
        return !getOsName().contains("windows") ? unix : windows;
    }

    public static String getSplitter() {
        return getOsName().contains("windows") ? "\\" : "/";
    }

    public static String getSplittedPath(String path) {
        path = path.replace("/", getSplitter());
        path = path.replace("\\", getSplitter());
        return path;
    }

    public static String[] replaceFromStringArray(String[] string, String[] parameters, String[] values) {
        String[] result = string;

        for(int i = 0; i < parameters.length; ++i) {
            if (result[i] != null && parameters[i] != null && result[i].contains(parameters[i])) {
                if (values[i] == null) {
                    result[i] = result[i].replace(parameters[i], "");
                }

                result[i] = result[i].replace(parameters[i], values[i]);
            }
        }

        return result;
    }

    public static String stringFormatter(String... args) {
        String[] parameters = Lists.objectArrayToString(Lists.removeElementFromArray(args, 0)).split("<br>");
        return stringFormatter(args[0], parameters[0].split(", "), parameters[1].split(", "));
    }

    public static String stringFormatter(String string, String[] parameters, String[] values) {
        String result = string;

        for(int i = 0; i < parameters.length; ++i) {
            result = result.replace(parameters[i], values[i]);
        }

        return result;
    }

    public static String randomString(int length, boolean letters, boolean numbers, boolean uppercases) {
        String SALTCHARS = "";
        if (letters && uppercases) {
            SALTCHARS = SALTCHARS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }

        if (letters) {
            SALTCHARS = SALTCHARS + "abcdefghijklmnopqrstuvwxyz";
        }

        if (numbers) {
            SALTCHARS = SALTCHARS + "1234567890";
        }

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while(salt.length() < length) {
            int index = (int)(rnd.nextFloat() * (float)SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String saltStr = salt.toString();
        return saltStr;
    }

    public static String removeFirstChar(String text) {
        return text.substring(1, text.length());
    }

    /** @deprecated */
    @Deprecated
    public static boolean isInteger(String string) {
        return string.matches("-?\\d+");
    }

    /** @deprecated */
    @Deprecated
    public static boolean isInteger(char c) {
        return String.valueOf(c).matches("-?\\d+");
    }

    public static String intToRoman(int integer) {
        int[] bases = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        Map<Integer, String> map = new HashMap();
        map.put(1, "I");
        map.put(4, "IV");
        map.put(5, "V");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(40, "XL");
        map.put(50, "L");
        map.put(90, "XC");
        map.put(100, "C");
        map.put(400, "CD");
        map.put(500, "D");
        map.put(900, "CM");
        map.put(1000, "M");
        String result = new String();
        int[] var4 = bases;
        int var5 = bases.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            for(int i = var4[var6]; integer >= i; integer -= i) {
                result = result + (String)map.get(i);
            }
        }

        return result;
    }

    public static boolean isBoolean(String string) {
        return string.equalsIgnoreCase("y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("true") || string.equalsIgnoreCase("on") || Integers.isInteger(string) || string.equalsIgnoreCase("n") || string.equalsIgnoreCase("no") || string.equalsIgnoreCase("false") || string.equalsIgnoreCase("off");
    }

    public static boolean getBooleanValue(String string) {
        return string.equalsIgnoreCase("y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("true") || string.equalsIgnoreCase("on") || Integers.isInteger(string) && Integer.parseInt(string) > 0;
    }
}
 