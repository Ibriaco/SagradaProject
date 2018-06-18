package it.polimi.se2018;

import it.polimi.se2018.Model.DeepShade;
import it.polimi.se2018.Model.LightShade;
import it.polimi.se2018.Model.MediumShade;
import it.polimi.se2018.Model.PublicObjective;

public class PublicCardFactory {

    private static final String LIGHT_SHADES = "Light Shades";
    private static final String MEDIUM_SHADES = "Medium Shades";
    private static final String DEEP_SHADES = "Deep Shades";
    private static final String COLOR_VARIETY = "Color Variety";
    private static final String SHADE_VARIETY = "Shade Variety";
    private static final String COLUMN_SHADE_VARIETY = "Column Shade Variety";
    private static final String ROW_SHADE_VARIETY = "Row Shade Variety";
    private static final String COLUMN_COLOR_VARIETY = "Column Color Variety";
    private static final String ROW_COLOR_VARIETY = "Row Color Variety";
    private static final String COLOR_DIAGONALS = "Color Diagonals";

    public PublicObjective createCard(String title, String description, int score) {

        if (title.equalsIgnoreCase(LIGHT_SHADES))
            return new LightShade(title, description, score);

        else if(title.equalsIgnoreCase(MEDIUM_SHADES))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(DEEP_SHADES))
            return new DeepShade(title, description, score);

        else if(title.equalsIgnoreCase(COLOR_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(SHADE_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(COLUMN_SHADE_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(ROW_SHADE_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(COLUMN_COLOR_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(ROW_COLOR_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(COLOR_DIAGONALS))
            return new MediumShade(title, description, score);

        return null;
    }
}
