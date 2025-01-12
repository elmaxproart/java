package Sea.incubator.sgdeb.mapper;
import Sea.incubator.sgdeb.dto.ECDTO;
import Sea.incubator.sgdeb.model.EC;
import org.springframework.stereotype.Component;

/**
 * The type Ec mapper.
 */
@Component
public class ECMapper {

    private final UEMapper ueMapper;


    /**
     * Instantiates a new Ec mapper.
     *
     * @param ueMapper the ue mapper
     */
    public ECMapper(UEMapper ueMapper) {
        this.ueMapper = ueMapper;
    }

    /**
     * To ecdto ecdto.
     *
     * @param ec the ec
     * @return the ecdto
     */
    public ECDTO toECDTO(EC ec) {
        if (ec == null) {
            return null;
        }

        ECDTO ecDTO = new ECDTO();
        ecDTO.setId(ec.getId());
        ecDTO.setLibelle(ec.getIntitule());
        ecDTO.setCode(ec.getCodeEc());
        ecDTO.setUE(ueMapper.toUEDTO(ec.getUE()));

        return ecDTO;
    }

    /**
     * To ec ec.
     *
     * @param ecDTO the ec dto
     * @return the ec
     */
    public EC toEC(ECDTO ecDTO) {
        if (ecDTO == null) {
            return null;
        }

        EC ec = new EC();
        ec.setId(ecDTO.getId());
        ec.setIntitule(ecDTO.getLibelle());
        ec.setCodeEc(ecDTO.getCode());
        ec.setUE(ueMapper.toUE(ecDTO.getUE()));

        return ec;
    }




}