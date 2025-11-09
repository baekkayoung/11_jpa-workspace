package restaurant.reservation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurant.reservation.domain.TableStatus;
import restaurant.reservation.domain.Tables;
import restaurant.reservation.repository.TableRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TablesService {

    private final TableRepository tableRepository;

    // 테이블 등록
    public Long createTable(int tableNumber, int capacity) {
        Tables tables = new Tables();
        tables.setTableNumber(tableNumber);
        tables.setCapacity(capacity);
        tables.setStatus(TableStatus.AVAILABLE); // 기본 상태

        tableRepository.save(tables);
        return tables.getId();
    }

    // 테이블 조회 (필요 시)
    @Transactional(readOnly = true)
    public Tables findTable(Long id) {
        return tableRepository.findById(id);
    }
}
