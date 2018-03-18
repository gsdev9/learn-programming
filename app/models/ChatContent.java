package models;

import javax.persistence.*;

@Entity
public class ChatContent {

    /** チャットコンテンツID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long chatContentId;

    /** メッセージ */
    public String message;

    /** ファイルパス */
    public String filePath;

    /** チャットルーム */
    @OneToOne(cascade = CascadeType.ALL)
    public ChatRoom chatRoom;

}
