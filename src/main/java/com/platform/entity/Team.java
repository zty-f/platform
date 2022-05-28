package com.platform.entity;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Team implements Serializable {
    private Integer id;

    private String name;

    private String memberIds;

    private Integer projectId;

    private String leaderId;

    private Boolean committed;

    private Integer teacherId;

    private String projectPath;

    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", memberIds=").append(memberIds);
        sb.append(", projectId=").append(projectId);
        sb.append(", leaderId=").append(leaderId);
        sb.append(", committed=").append(committed);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", projectPath=").append(projectPath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}