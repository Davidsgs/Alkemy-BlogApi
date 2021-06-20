package com.alkemy.challenge.Blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageChecker {
    //Validar si una imagen termina con jpe/jpge/png/gif/bmp.
    public static boolean isValidExtension(String imageRoute){
        //Verificamos que la ruta no sea nula.
        if(imageRoute == null){
            return false;
        }
        //Hacemos un patron regex para validar que la imagen termine con la extencion correcta.
        //Sacado de -> https://www.geeksforgeeks.org/how-to-validate-image-file-extension-using-regular-expression/
        String imageRegex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
        //Compilamos el Regex.
        Pattern p = Pattern.compile(imageRegex);
        return p.matcher(imageRoute).matches();
    }
}
