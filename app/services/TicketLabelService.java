package services;

import models.TicketLabel;
import repositories.TicketLabelRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * チケットラベルに関するサービス
 *
 * @author arapiku
 */
public class TicketLabelService {

    @Inject
    private TicketLabelRepository ticketLabelRepository;

    /**
     * TicketLabelRepositoryのfindAllを呼び出す
     *
     * @return チケットラベル全件
     */
    public List<TicketLabel> findAll() { return ticketLabelRepository.findAll(); }

    /**
     * TicketLabelRepositoryのfinByIdを呼び出す
     *
     * @param id
     * @return
     */
    public TicketLabel findById(Long id) { return ticketLabelRepository.findById(id); }
}
