package com.netcracker.checkapp.server.service.fdspservice;

import com.netcracker.checkapp.server.model.FDSP;
import com.netcracker.checkapp.server.persistance.FDSPRepository;
import org.springframework.stereotype.Service;

@Service
public class FDSPServiceImpl implements FDSPService {
    FDSPRepository fdspRepository;

    FDSPServiceImpl(FDSPRepository fdspRepository) {
        this.fdspRepository = fdspRepository;
    }

    @Override
    public FDSP addFDSP(FDSP fdsp) {
        FDSP fdspl = findFDSP(fdsp.getFiscalDriveNumber());
        return (fdspl == null) ? fdspRepository.save(fdsp) : fdspl;
    }

    @Override
    public FDSP findFDSP(String fiscalDriveNumber) {
        return fdspRepository.findByFiscalDriveNumber(fiscalDriveNumber);
    }
}
