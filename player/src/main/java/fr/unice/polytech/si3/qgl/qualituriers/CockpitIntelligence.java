package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author williamdandrea
 */
public class CockpitIntelligence {


    /**
     * Algo pour le premier rendu : juste faire bouger les rams
     *
     * {
     *     "sailorId": 2,
     *     "type": "OAR"
     * },
     *
     * @return
     */
    public static String premierRendu(List<Optional<Marin>> sailors) throws JsonProcessingException {

        List<Oar> trueSailors= new ArrayList<>();

        if (sailors == null) {
            return "";
        }
        for (Optional<Marin> marinOp: sailors) {

            Marin marin = marinOp.get();
            Oar oar = new Oar(marin.getId());
            trueSailors.add(oar);

            //System.out.println(oar.toString());
        }


        ObjectMapper om = new ObjectMapper();
        String json = "";

        String jsonArray = om.writeValueAsString(trueSailors);

        // [{"stringValue":"a","intValue":1,"booleanValue":true},
        // {"stringValue":"bc","intValue":3,"booleanValue":false}]




        return jsonArray;
    }


}
