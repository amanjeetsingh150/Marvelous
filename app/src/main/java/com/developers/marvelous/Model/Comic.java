package com.developers.marvelous.Model;

import android.os.Parcel;
import android.os.Parcelable;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Amanjeet Singh on 08-Jun-17.
 */
@SimpleSQLTable(table = "comics", provider = "MarvelProvider")
public class Comic implements Parcelable {

    @SimpleSQLColumn("id")
    private String id;
    @SimpleSQLColumn("title")
    private String title;
    @SimpleSQLColumn("description")
    private String description;
    @SimpleSQLColumn("thumbnail")
    private String thumbnail;
    @SimpleSQLColumn("issue_number")
    private String issueNumber;
    @SimpleSQLColumn("format")
    private String format;
    @SimpleSQLColumn("page_count")
    private String pageCount;

    public Comic() {
    }
    protected Comic(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        this.id = data[0];
        this.title = data[1];
        this.description = data[2];
        this.thumbnail = data[3];
        this.issueNumber = data[4];
        this.format = data[5];
        this.pageCount = data[6];
    }

    public Comic(String id,String title,String description,String thumbnail,String issueNumber,
                 String format,String pageCount){
        this.id=id;
        this.title=title;
        this.description=description;
        this.thumbnail=thumbnail;
        this.issueNumber=issueNumber;
        this.format=format;
        this.pageCount=pageCount;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                this.id,
                this.title,
                this.description,
                this.thumbnail,
                this.issueNumber,
                this.format,
                this.pageCount
        });
    }
}
