    package com.aurawave.service;

    import com.aurawave.dao.WarehouseDao;
    import com.aurawave.domain.model.Warehouse;
    import com.aurawave.dto.warehouseDto.WarehouseRequestDto;
    import com.aurawave.dto.warehouseDto.WarehouseResponseDto;
    import lombok.RequiredArgsConstructor;
    import org.modelmapper.ModelMapper;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class WarehouseService {

        private final WarehouseDao warehouseDao;
        private final ModelMapper mapper;

        // Cria um registro de um almoxarifado
        public WarehouseResponseDto create(WarehouseRequestDto dto) {
            Warehouse wh = mapper.map(dto, Warehouse.class);
            Long id = warehouseDao.create(wh);
            Warehouse saved = warehouseDao.getById(id);
            return mapper.map(saved, WarehouseResponseDto.class);
        }

        // Atualiza um registro de um almoxarifado
        public WarehouseResponseDto update(Long id, WarehouseRequestDto dto) {
            Warehouse wh = mapper.map(dto, Warehouse.class);
            warehouseDao.update(id, wh);
            Warehouse updated = warehouseDao.getById(id);
            return mapper.map(updated, WarehouseResponseDto.class);
        }

        //
        public WarehouseResponseDto getById(Long id) {
            return mapper.map(warehouseDao.getById(id), WarehouseResponseDto.class);
        }

        public List<WarehouseResponseDto> getAll() {
            return warehouseDao.getAll().stream()
                    .map(w -> mapper.map(w, WarehouseResponseDto.class))
                    .toList();
        }

        public void delete(Long id) {
            warehouseDao.delete(id);
        }

    }
