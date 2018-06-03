package forms;

import play.data.validation.Constraints;

import javax.validation.constraints.NotNull;

public class TicketForm {

    /** チケットタイトル */
    @NotNull
    @Constraints.Required(message = "タイトルが入力されていません。")
    public String title;

    /** チケット内容 */
    @NotNull
    @Constraints.Required(message = "内容が入力されていません。")
    public String body;

    /** 授業日 */
    @NotNull
    @Constraints.Required(message = "授業日が入力されていません。")
    public String date;

    /** 授業開始時間 */
    @NotNull
    @Constraints.Required(message = "授業開始時間が入力されていません。")
    public String startAt;

    /** 授業終了時間 */
    @NotNull
    @Constraints.Required(message = "授業終了時間が入力されていません。")
    public String endAt;

    /** 価格 */
    @Constraints.Required(message = "金額が入力されていません。")
    public String price;

    /**
     * サムネイル画像パス
     */
    public String thumbnailPath;

    /** C */
    public Boolean c;

    /** C++ */
    public Boolean cPlusPlus;

    /** C# */
    public Boolean cSharp;

    /** Java */
    public Boolean java;

    /** JavaScript */
    public Boolean javaScript;

    /** PHP */
    public Boolean php;

    /** Ruby */
    public Boolean ruby;

    /** Python */
    public Boolean python;

    /** Perl */
    public Boolean perl;

    /** R */
    public Boolean r;

    /** Go */
    public Boolean go;

    /** Scala */
    public Boolean scala;

    /** Objective-C */
    public Boolean objectiveC;

    /** Swift */
    public Boolean swift;

    /** Kotlin */
    public Boolean kotlin;

    /** Scratch */
    public Boolean scratch;

    /** blockly */
    public Boolean blockly;

    /** SQL */
    public Boolean sqlLang;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getC() {
        return c;
    }

    public void setC(Boolean c) {
        this.c = c;
    }

    public Boolean getcPlusPlus() {
        return cPlusPlus;
    }

    public void setcPlusPlus(Boolean cPlusPlus) {
        this.cPlusPlus = cPlusPlus;
    }

    public Boolean getcSharp() {
        return cSharp;
    }

    public void setcSharp(Boolean cSharp) {
        this.cSharp = cSharp;
    }

    public Boolean getJava() {
        return java;
    }

    public void setJava(Boolean java) {
        this.java = java;
    }

    public Boolean getJavaScript() {
        return javaScript;
    }

    public void setJavaScript(Boolean javaScript) {
        this.javaScript = javaScript;
    }

    public Boolean getPhp() {
        return php;
    }

    public void setPhp(Boolean php) {
        this.php = php;
    }

    public Boolean getRuby() {
        return ruby;
    }

    public void setRuby(Boolean ruby) {
        this.ruby = ruby;
    }

    public Boolean getPython() {
        return python;
    }

    public void setPython(Boolean python) {
        this.python = python;
    }

    public Boolean getPerl() {
        return perl;
    }

    public void setPerl(Boolean perl) {
        this.perl = perl;
    }

    public Boolean getR() {
        return r;
    }

    public void setR(Boolean r) {
        this.r = r;
    }

    public Boolean getGo() {
        return go;
    }

    public void setGo(Boolean go) {
        this.go = go;
    }

    public Boolean getScala() {
        return scala;
    }

    public void setScala(Boolean scala) {
        this.scala = scala;
    }

    public Boolean getObjectiveC() {
        return objectiveC;
    }

    public void setObjectiveC(Boolean objectiveC) {
        this.objectiveC = objectiveC;
    }

    public Boolean getSwift() {
        return swift;
    }

    public void setSwift(Boolean swift) {
        this.swift = swift;
    }

    public Boolean getKotlin() {
        return kotlin;
    }

    public void setKotlin(Boolean kotlin) {
        this.kotlin = kotlin;
    }

    public Boolean getScratch() {
        return scratch;
    }

    public void setScratch(Boolean scratch) {
        this.scratch = scratch;
    }

    public Boolean getBlockly() {
        return blockly;
    }

    public void setBlockly(Boolean blockly) {
        this.blockly = blockly;
    }

    public Boolean getSqlLang() {
        return sqlLang;
    }

    public void setSqlLang(Boolean sqlLang) {
        this.sqlLang = sqlLang;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
