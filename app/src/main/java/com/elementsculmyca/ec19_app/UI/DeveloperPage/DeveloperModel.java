package com.elementsculmyca.ec19_app.UI.DeveloperPage;

public class DeveloperModel {
    String imageUri,name,designation,githubLink,linkedInLink;

    public DeveloperModel(String imageUri,String name, String designation, String githubLink,  String linkedInLink) {
        this.imageUri = imageUri;
        this.name = name;
        this.designation = designation;
        this.githubLink = githubLink;
        this.linkedInLink = linkedInLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
