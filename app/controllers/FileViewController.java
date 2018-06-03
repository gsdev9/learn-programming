package controllers;

import play.mvc.Result;
import play.mvc.Results;

import java.io.File;

public class FileViewController {


    /**
     * ファイル表示クラス.
     *
     * @param fileName
     * @return
     */
    public Result fileView(String fileName) {

        String filePath = System.getProperty("user.dir") + "/public/uploadfiles/thumbnail/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            return Results.badRequest();
        }
        return Results.ok().sendFile(file, true);

    }
}
