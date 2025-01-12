package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.ECDTO;
import Sea.incubator.sgdeb.mapper.ECMapper;
import Sea.incubator.sgdeb.mapper.UEMapper;
import Sea.incubator.sgdeb.model.EC;
import Sea.incubator.sgdeb.model.UE;
import Sea.incubator.sgdeb.repository.ECRepository;
import Sea.incubator.sgdeb.repository.UERepository;
import Sea.incubator.sgdeb.service.ECService;
import Sea.incubator.sgdeb.service.UEService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Ec service.
 * @author danilo
 */
@Service
public class ECServiceImpl implements ECService {
    private final ECRepository ecRepository;
    private final UERepository ueRepository;
    private final UEMapper ueMapper;
    private final ECMapper ecMapper;


    /**
     * Instantiates a new Ec service.
     *
     * @param ecRepository the ec repository
     * @param ecMapper     the ec mapper
     */
    public ECServiceImpl(ECRepository ecRepository, UERepository ueRepository, UEMapper ueMapper, ECMapper ecMapper) {
        this.ecRepository = ecRepository;
        this.ueRepository = ueRepository;
        this.ueMapper = ueMapper;
        this.ecMapper = ecMapper;
    }

    @Override
    public ECDTO createEc(ECDTO ecDTO, UUID ueId) {
        // Vérifier si l'UE existe
        Optional<UE> optionalue=ueRepository.findByIdAndDateSuppressionIsNull(ueId);
        if(optionalue.isPresent()){
            UE ue=optionalue.get();
            ecDTO.setUE(ueMapper.toUEDTO(ue));
            ECDTO ecdto=ecMapper.toECDTO(ecRepository.save(ecMapper.toEC(ecDTO)));
            return ecdto;
        }
        else
            return null;
    }

    @Override
    public ECDTO getEc(UUID ecId) {
        Optional<EC> optionalec = ecRepository.findByIdAndDateSuppressionIsNull(ecId);
        if (optionalec.isPresent()) {
            EC ec = optionalec.get();
            return ecMapper.toECDTO(ec);
        }
        else
            return null;
    }

    @Override
    public ECDTO getEc(String codeEc) {
        Optional<EC> optionalec = ecRepository.findByCodeEcAndDateSuppressionIsNull(codeEc);
        if (optionalec.isPresent()) {
            EC ec = optionalec.get();
            return ecMapper.toECDTO(ec);
        }
        return null;
    }

    @Override
    public ECDTO updateEc(ECDTO ecDTO, UUID ecId) {
        Optional<EC> optionalec = ecRepository.findByIdAndDateSuppressionIsNull(ecId);
        if (optionalec.isPresent()) {
            EC ec = optionalec.get();

            ec.setIntitule(ecDTO.getLibelle());
            ec.setCodeEc(ecDTO.getCode());
            ec = ecRepository.save(ec);
            return ecMapper.toECDTO(ec);
        }
        else
            return null;
    }

    @Override
    public ECDTO updateNomEc(ECDTO ecDTO, UUID ecId) {
        Optional<EC> optionalec = ecRepository.findByIdAndDateSuppressionIsNull(ecId);
        if (optionalec.isPresent()) {
            EC ec = optionalec.get();

            ec.setIntitule(ecDTO.getLibelle());
            ec = ecRepository.save(ec);
            return ecMapper.toECDTO(ec);
        }
        else
            return null;
    }

    @Override
    public ECDTO updateCode(ECDTO ecDTO, UUID ecId) {
        Optional<EC> optionalec = ecRepository.findByIdAndDateSuppressionIsNull(ecId);
        if (optionalec.isPresent()) {
            EC ec = optionalec.get();

            ec.setCodeEc(ecDTO.getCode());
            ec = ecRepository.save(ec);
            return ecMapper.toECDTO(ec);
        }
        else
            return null;
    }

    @Override
    public boolean deleteEc(UUID ecId) {
        Optional<EC> optionalec = ecRepository.findByIdAndDateSuppressionIsNull(ecId);
        if (optionalec.isPresent()) {
            EC ec=optionalec.get();
            ec.setDateSuppression(LocalDateTime.now());
            ecRepository.save(ec);
            return true;
        }
        else
            return false;
    }

    @Override
    public List<ECDTO> getAllByUE(UUID ueId) {
        List<EC> ecs = ecRepository.findAllByUEId(ueId);
        return ecs.stream()
                .map(ecMapper::toECDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ECDTO> getAllActiveByUE(UUID ueId) {
        List<EC> ecs = ecRepository.findAllActiveByUEId(ueId);
        return ecs.stream()
                .map(ecMapper::toECDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsDeletedEc(UE ue) {
        List<EC> ecs= ecRepository.findByUE(ue);

        for(EC ec:ecs){
            ec.setDateSuppression(LocalDateTime.now());
            ecRepository.save(ec);
        }
    }
}
