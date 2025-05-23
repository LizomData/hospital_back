package com.example.yiyuan.entity.config;

import com.example.yiyuan.utils.StringTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appConfig")
public class Appconfig {

    @Value("${ws.port}")
    private Integer wsPort;

    @Value("${project.folder}")
    private String projectFolder;

    @Value("${admin.emails}")
    private String adminEmails;

    public Integer getWsPort() {
        return wsPort;
    }

    public String getProjectFolder() {
        if (StringTools.isEmpty(projectFolder) && !projectFolder.endsWith("/")) {
            projectFolder = projectFolder + "/";
        }
        return projectFolder;
    }

    public String getAdminEmails() {
        return adminEmails;
    }
}
