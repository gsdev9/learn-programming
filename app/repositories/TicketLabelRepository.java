package repositories;

import models.*;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.util.List;

/**
 * チケットラベルのCRUD処理
 *
 * @author arapiku
 */
public class TicketLabelRepository {

    private final JPAApi jpa;

    @Inject
    public TicketLabelRepository(JPAApi jpa) {
        this.jpa = jpa;
    }

    /**
     * チケットラベルの全件取得
     *
     * @return チケットラベル全件
     */
    public List<TicketLabel> findAll() {
        return jpa.em().createQuery("SELECT t FROM TicketLabel t", TicketLabel.class).getResultList();
    }

    /**
     * チケットラベルのID検索
     *
     * @param id
     * @return
     */
    public TicketLabel findById(Long id) {
        return jpa.em().find(TicketLabel.class, id);
    }
}
