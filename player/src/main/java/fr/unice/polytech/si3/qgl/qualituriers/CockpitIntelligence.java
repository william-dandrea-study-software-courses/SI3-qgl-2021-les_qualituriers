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
     * @return un sring au format JSON qui indique a tout les marins de ramer
     */
    public static String premierRendu(List<Optional<Marin>> sailors) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        List<Oar> trueSailors= new ArrayList<>();

        if (sailors == null || sailors.isEmpty()) {
            return "[]";
        }
        for (Optional<Marin> marinOp: sailors) {

            if (marinOp.isPresent()) {
                Marin marin = marinOp.get();
                Oar oar = new Oar(marin.getId());
                trueSailors.add(oar);

            }
        }

        return om.writeValueAsString(trueSailors);

    }


}
