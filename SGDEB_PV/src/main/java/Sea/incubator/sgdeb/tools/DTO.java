package Sea.incubator.sgdeb.tools;

import Sea.incubator.sgdeb.dto.pvRecapDTO;
import Sea.incubator.sgdeb.external.GrilleDTO;
import Sea.incubator.sgdeb.model.PvRecap;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import org.mapstruct.ap.internal.model.Mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DTO {
    public static pvRecapDTO entityToDto(PvRecap entity) {
        pvRecapDTO dto = new pvRecapDTO();
        dto.setId(entity.getId());
        dto.setMatricule(entity.getMatricule());
        dto.setNom(entity.getNom());
        dto.setNiveaux(entity.getNiveaux());
        dto.setMgp(entity.getMgp());
        dto.setDecision(entity.getDecision());
        dto.setMention(entity.getMention());
        dto.setNotes(entity.getNotes());

        //pour le restamplate;
        dto.setUeDTOs(new ArrayList<>());
        dto.setNotes(entity.getNotes());
        dto.setGrilleDTOs(new GrilleDTO());

        return dto;
    }

    public static PvRecap dtoToEntity(pvRecapDTO dto) {
        PvRecap entity = new PvRecap();
        entity.setId(dto.getId());
        entity.setMatricule(dto.getMatricule());
        entity.setNom(dto.getNom());
        entity.setNiveaux(dto.getNiveaux());
        entity.setMgp(dto.getMgp());
        entity.setDecision(dto.getDecision());
        entity.setMention(dto.getMention());
        entity.setNotes(dto.getNotes());

        // Vous n'avez pas besoin de convertir les listes ueDTOs et grilleDTOs car elles ne sont pas dans PvRecap

        return entity;
    }
}
