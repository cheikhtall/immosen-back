package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.BienDTO;

import java.util.List;

public interface BienService {
    BienDTO create(BienDTO bienDTO);
    BienDTO getById(Long id);
    List<BienDTO> getAll();
    BienDTO update(BienDTO bienDTO, Long id);
    void delete(Long id);
}
