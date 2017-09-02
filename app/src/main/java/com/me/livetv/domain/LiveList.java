package com.me.livetv.domain;

import java.util.List;

/**
 * Created by zhangxuan on 2017/9/2.
 */

public class LiveList {

    /**
     * lessons : [{"courseId":25,"courseName":"小寨政治直播课：第1讲","playurl":"rtmp://live.kaoshidian.com/zhibo/xuefu_140"},{"courseId":25,"courseName":"小寨政治直播课：第2讲","playurl":"rtmp://live.kaoshidian.com/zhibo/xuefu_141"},{"courseId":25,"courseName":"小寨政治直播课：第3讲","playurl":"rtmp://live.kaoshidian.com/zhibo/xuefu_142"},{"courseId":60,"courseName":"万伟直播课程：第1讲","playurl":"rtmp://alive.kaoshidian.com/zhibo/zhibo_13283"},{"courseId":62,"courseName":"政治全程班：第1讲","playurl":"rtsp://192.168.1.146/channel1"}]
     * statuscode : 0
     */

    private int statuscode;
    private List<LessonsEntity> lessons;

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public List<LessonsEntity> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonsEntity> lessons) {
        this.lessons = lessons;
    }

    public static class LessonsEntity {
        /**
         * courseId : 25
         * courseName : 小寨政治直播课：第1讲
         * playurl : rtmp://live.kaoshidian.com/zhibo/xuefu_140
         */

        private int courseId;
        private String courseName;
        private String playurl;

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getPlayurl() {
            return playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }
    }
}
