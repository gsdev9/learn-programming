package dtos;

import dtos.utils.DateUtils;
import forms.TicketForm;
import models.Ticket;

public class TicketDTO {

    /**
     * フォームから受け取ったチケットデータをエンティティに変換
     *
     * @param ticketForm
     */
    public static Ticket convertToEntity(Ticket ticket, TicketForm ticketForm) {
        ticket.setDate(DateUtils.toLocalDate(ticketForm.date, "uuuu-MM-dd"));
        ticket.setStartAt(DateUtils.toLocalTime(ticketForm.startAt + ":00", "HH:mm:ss"));
        ticket.setEndAt(DateUtils.toLocalTime(ticketForm.endAt + ":00", "HH:mm:ss"));
        // TODO ZonedDateTimeを使うかの判断
//        ticket.setDate(toZonedDateTime(ticketForm.date + " 00:00:00", "uuuu-MM-dd HH:mm:ss"));
//        ticket.setStartAt(toZonedDateTime(ticketForm.date + " " + ticketForm.startAt + ":00", "uuuu-MM-dd HH:mm:ss"));
//        ticket.setEndAt(toZonedDateTime(ticketForm.date + " " + ticketForm.endAt + ":00", "uuuu-MM-dd HH:mm:ss"));
        ticket.setPrice(Integer.valueOf(ticketForm.price));
        ticket.setTitle(ticketForm.title);
        ticket.setBody(ticketForm.body);
        return ticket;
    }

}
