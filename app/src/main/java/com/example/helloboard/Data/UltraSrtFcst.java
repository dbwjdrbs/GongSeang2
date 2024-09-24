package com.example.helloboard.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UltraSrtFcst {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {

        @SerializedName("header")
        @Expose
        private Header header;
        @SerializedName("body")
        @Expose
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public static class Header {

            @SerializedName("resultCode")
            @Expose
            private String resultCode;
            @SerializedName("resultMsg")
            @Expose
            private String resultMsg;

            public String getResultCode() {
                return resultCode;
            }

            public void setResultCode(String resultCode) {
                this.resultCode = resultCode;
            }

            public String getResultMsg() {
                return resultMsg;
            }

            public void setResultMsg(String resultMsg) {
                this.resultMsg = resultMsg;
            }

        }

        public static class Body {

            @SerializedName("dataType")
            @Expose
            private String dataType;
            @SerializedName("items")
            @Expose
            private Items items;
            @SerializedName("pageNo")
            @Expose
            private Integer pageNo;
            @SerializedName("numOfRows")
            @Expose
            private Integer numOfRows;
            @SerializedName("totalCount")
            @Expose
            private Integer totalCount;

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public Items getItems() {
                return items;
            }

            public void setItems(Items items) {
                this.items = items;
            }

            public Integer getPageNo() {
                return pageNo;
            }

            public void setPageNo(Integer pageNo) {
                this.pageNo = pageNo;
            }

            public Integer getNumOfRows() {
                return numOfRows;
            }

            public void setNumOfRows(Integer numOfRows) {
                this.numOfRows = numOfRows;
            }

            public Integer getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(Integer totalCount) {
                this.totalCount = totalCount;
            }

            public static class Items {

                @SerializedName("item")
                @Expose
                private List<Item> item = null;

                public List<Item> getItem() {
                    return item;
                }

                public void setItem(List<Item> item) {
                    this.item = item;
                }

                public static class Item {

                    @SerializedName("baseDate")
                    @Expose
                    private String baseDate;
                    @SerializedName("baseTime")
                    @Expose
                    private String baseTime;
                    @SerializedName("category")
                    @Expose
                    private String category;
                    @SerializedName("fcstDate")
                    @Expose
                    private String fcstDate;
                    @SerializedName("fcstTime")
                    @Expose
                    private String fcstTime;
                    @SerializedName("fcstValue")
                    @Expose
                    private String fcstValue;
                    @SerializedName("nx")
                    @Expose
                    private Integer nx;
                    @SerializedName("ny")
                    @Expose
                    private Integer ny;

                    public String getBaseDate() {
                        return baseDate;
                    }

                    public void setBaseDate(String baseDate) {
                        this.baseDate = baseDate;
                    }

                    public String getBaseTime() {
                        return baseTime;
                    }

                    public void setBaseTime(String baseTime) {
                        this.baseTime = baseTime;
                    }

                    public String getCategory() {
                        return category;
                    }

                    public void setCategory(String category) {
                        this.category = category;
                    }

                    public String getFcstDate() {
                        return fcstDate;
                    }

                    public void setFcstDate(String fcstDate) {
                        this.fcstDate = fcstDate;
                    }

                    public String getFcstTime() {
                        return fcstTime;
                    }

                    public void setFcstTime(String fcstTime) {
                        this.fcstTime = fcstTime;
                    }

                    public String getFcstValue() {
                        return fcstValue;
                    }

                    public void setFcstValue(String fcstValue) {
                        this.fcstValue = fcstValue;
                    }

                    public Integer getNx() {
                        return nx;
                    }

                    public void setNx(Integer nx) {
                        this.nx = nx;
                    }

                    public Integer getNy() {
                        return ny;
                    }

                    public void setNy(Integer ny) {
                        this.ny = ny;
                    }

                }
            }
        }
    }
}


