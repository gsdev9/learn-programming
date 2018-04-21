package models;

import javax.persistence.*;

/**
 * チケットの言語ラベル
 *
 * @author arapiku
 */
@Entity
public class TicketLabel {

    /** チケットラベルID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long TicketLabelId;

    /** C */
    @Column(nullable = false)
    public Boolean c;

    /** C++ */
    @Column(nullable = false)
    public Boolean cPlusPlus;

    /** C# */
    @Column(nullable = false)
    public Boolean cSharp;

    /** Java */
    @Column(nullable = false)
    public Boolean java;

    /** JavaScript */
    @Column(nullable = false)
    public Boolean javaScript;

    /** PHP */
    @Column(nullable = false)
    public Boolean php;

    /** Ruby */
    @Column(nullable = false)
    public Boolean ruby;

    /** Python */
    @Column(nullable = false)
    public Boolean python;

    /** Perl */
    @Column(nullable = false)
    public Boolean perl;

    /** R */
    @Column(nullable = false)
    public Boolean r;

    /** Go */
    @Column(nullable = false)
    public Boolean go;

    /** Scala */
    @Column(nullable = false)
    public Boolean scala;

    /** Objective-C */
    @Column(nullable = false)
    public Boolean objectiveC;

    /** Swift */
    @Column(nullable = false)
    public Boolean swift;

    /** Kotlin */
    @Column(nullable = false)
    public Boolean kotlin;

    /** Scratch */
    @Column(nullable = false)
    public Boolean scratch;

    /** blockly */
    @Column(nullable = false)
    public Boolean blockly;

    /** SQL */
    @Column(nullable = false)
    public Boolean sqlLang;

//    /** チケット情報 */
//    @MapsId
//    @OneToOne(mappedBy = "ticketLabel")
//    @JoinColumn(name = "ticketId")
//    public Ticket ticket;

}
