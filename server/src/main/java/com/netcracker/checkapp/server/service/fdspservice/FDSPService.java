package com.netcracker.checkapp.server.service.fdspservice;

import com.netcracker.checkapp.server.model.FDSP;

public interface FDSPService {
    public FDSP addFDSP(FDSP fdsp);

    public FDSP findFDSP(String fiscalDriveNumber);
}
