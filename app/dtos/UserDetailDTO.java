package dtos;

public class UserDetailDTO {

    /**
     * 登録に必要なユーザーbeanの加工(チケット情報の引き継ぎ)
     *
     * @return
     */
    public models.User oldToNew(models.User olduser, models.User newuser) {
        newuser.setTickets(olduser.getTickets());
        newuser.setUUID(olduser.getUUID());
        //画像のファイル別途処理必要(js?)
        if (!newuser.getThumbnailPath().equals(olduser.getThumbnailPath())) {
            newuser.setThumbnailPath(newuser.getThumbnailPath());
        }
        return newuser;
    }
}
