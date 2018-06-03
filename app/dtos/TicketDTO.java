package dtos;

import dtos.utils.DateUtils;
import forms.TicketForm;
import models.Ticket;
import models.TicketLabel;

public class TicketDTO {

    /**
     * フォームから受け取ったチケットデータをエンティティに変換
     *
     * @param ticketForm
     */
    public static Ticket convertToEntity(Ticket ticket, TicketForm ticketForm, TicketLabel ticketLabel) {
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
        ticket.setThumbNailPath(ticketForm.thumbnailPath);
//        ticket.setTicketLabel(TicketLabelToEntity(ticketForm));
        ticket.setTicketLabel(ticketLabel);
        return ticket;
    }

    /**
     * フォームから受け取ったTicketLabelのデータをエンティティに変換
     *
     * @param ticketForm
     * @return
     */
    private static TicketLabel TicketLabelToEntity(TicketForm ticketForm) {
        TicketLabel ticketLabel = new TicketLabel();
        ticketLabel.c = ticketForm.c;
        ticketLabel.cPlusPlus = ticketForm.cPlusPlus;
        ticketLabel.cSharp = ticketForm.cSharp;
        ticketLabel.java = ticketForm.java;
        ticketLabel.javaScript = ticketForm.javaScript;
        ticketLabel.php = ticketForm.php;
        ticketLabel.ruby = ticketForm.ruby;
        ticketLabel.python = ticketForm.python;
        ticketLabel.perl = ticketForm.perl;
        ticketLabel.r = ticketForm.r;
        ticketLabel.go = ticketForm.go;
        ticketLabel.scala = ticketForm.scala;
        ticketLabel.objectiveC = ticketForm.objectiveC;
        ticketLabel.swift = ticketForm.swift;
        ticketLabel.kotlin = ticketForm.kotlin;
        ticketLabel.scratch = ticketForm.scratch;
        ticketLabel.blockly = ticketForm.blockly;
        ticketLabel.sqlLang = ticketForm.sqlLang;
        return ticketLabel;
    }

    /**
     * 更新の際のエンティティ変換DTO
     *
     * @param ticket
     * @param ticketForm
     * @return
     */
    public static Ticket ticketLabelToEntityForUpdate(Ticket ticket, TicketForm ticketForm) {
        ticket.setDate(DateUtils.toLocalDate(ticketForm.date, "uuuu-MM-dd"));
        ticket.setStartAt(DateUtils.toLocalTime(ticketForm.startAt + ":00", "HH:mm:ss"));
        ticket.setEndAt(DateUtils.toLocalTime(ticketForm.endAt + ":00", "HH:mm:ss"));
        ticket.setPrice(Integer.valueOf(ticketForm.price));
        ticket.setTitle(ticketForm.title);
        ticket.setBody(ticketForm.body);
        ticket.getTicketLabel().c = ticketForm.c;
        ticket.getTicketLabel().cPlusPlus = ticketForm.cPlusPlus;
        ticket.getTicketLabel().cSharp = ticketForm.cSharp;
        ticket.getTicketLabel().java = ticketForm.java;
        ticket.getTicketLabel().javaScript = ticketForm.javaScript;
        ticket.getTicketLabel().php = ticketForm.php;
        ticket.getTicketLabel().ruby = ticketForm.ruby;
        ticket.getTicketLabel().python = ticketForm.python;
        ticket.getTicketLabel().perl = ticketForm.perl;
        ticket.getTicketLabel().r = ticketForm.r;
        ticket.getTicketLabel().go = ticketForm.go;
        ticket.getTicketLabel().scala = ticketForm.scala;
        ticket.getTicketLabel().objectiveC = ticketForm.objectiveC;
        ticket.getTicketLabel().swift = ticketForm.swift;
        ticket.getTicketLabel().kotlin = ticketForm.kotlin;
        ticket.getTicketLabel().scratch = ticketForm.scratch;
        ticket.getTicketLabel().blockly = ticketForm.blockly;
        ticket.getTicketLabel().sqlLang = ticketForm.sqlLang;
        ticket.setThumbNailPath(ticketForm.thumbnailPath);
        return ticket;
    }

}
