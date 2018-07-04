package it.polimi.se2018;

import it.polimi.se2018.controller.effects.*;

public class ToolCardFactory {
    private static final String GROZING_PLIERS = "Grozing Pliers";
    private static final String ENGLOMISE_BRUSH = "Englomise Brush";
    private static final String COPPER_FOIL_BRUSHER = "Copper Foil Burnisher";
    private static final String LATHEKIN = "Lathekin";
    private static final String LENS_CUTTER = "Lens Cutter";
    private static final String FLUX_BRUSH = "Flux Brush";
    private static final String GLAZING_HAMMER = "Glazing Hammer";
    private static final String RUNNING_PLIERS = "Running Pliers";
    private static final String CORK_BACKED_STRAIGHTEDGE = "Cork-backed straightedge";
    private static final String GRINDING_STONE = "Grinding Stone";
    private static final String FLUX_REMOVER = "Flux Remover";
    private static final String TAP_WHEEL = "Tap Wheel";

    public Effect createEffect(String title){
        if (title.equalsIgnoreCase(GROZING_PLIERS))
            return new IncDecEffect();

        else if(title.equalsIgnoreCase(GRINDING_STONE))
            return new ReverseDieEffect();

        else if(title.equalsIgnoreCase(FLUX_BRUSH))
            return new RollDieEffect();

        else if(title.equalsIgnoreCase(ENGLOMISE_BRUSH)) {
            return new MoveDieEffect(false, true, 1);
        }

        else if(title.equalsIgnoreCase(COPPER_FOIL_BRUSHER))
            return new MoveDieEffect(true, false, 1);

        else if(title.equalsIgnoreCase(LATHEKIN))
            return new MoveDieEffect(true, true, 2);

        /*else if(title.equalsIgnoreCase(ROW_SHADE_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(COLUMN_COLOR_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(ROW_COLOR_VARIETY))
            return new MediumShade(title, description, score);

        else if(title.equalsIgnoreCase(COLOR_DIAGONALS))
            return new MediumShade(title, description, score);*/

        return null;
    }
}
