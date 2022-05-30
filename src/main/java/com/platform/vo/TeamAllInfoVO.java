package com.platform.vo;

import java.util.List;

public class TeamAllInfoVO {

    /**
     * 从team表查询
     */
    private Integer TeamId;
    private String TeamName;
    private Integer leaderId;
    private Boolean committed;
    private Integer teacherId;
    private String projectPath;

    /**
     * 根据leaderId查询leader的信息
     */
    private String leaderUserName;
    private String leaderRealName;

    /**
     * 根据teacherId查询teacher的信息
     */
    private String teacherUserName;
    private String teacherRealName;

    /**
     * 查询成员的信息
     */
    private List<String[]> studentsInfo;

    public Integer getTeamId() {
        return TeamId;
    }

    public void setTeamId(Integer teamId) {
        TeamId = teamId;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Boolean getCommitted() {
        return committed;
    }

    public void setCommitted(Boolean committed) {
        this.committed = committed;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getLeaderUserName() {
        return leaderUserName;
    }

    public void setLeaderUserName(String leaderUserName) {
        this.leaderUserName = leaderUserName;
    }

    public String getLeaderRealName() {
        return leaderRealName;
    }

    public void setLeaderRealName(String leaderRealName) {
        this.leaderRealName = leaderRealName;
    }

    public String getTeacherUserName() {
        return teacherUserName;
    }

    public void setTeacherUserName(String teacherUserName) {
        this.teacherUserName = teacherUserName;
    }

    public String getTeacherRealName() {
        return teacherRealName;
    }

    public void setTeacherRealName(String teacherRealName) {
        this.teacherRealName = teacherRealName;
    }

    public List<String[]> getStudentsInfo() {
        return studentsInfo;
    }

    public void setStudentsInfo(List<String[]> studentsInfo) {
        this.studentsInfo = studentsInfo;
    }
}
