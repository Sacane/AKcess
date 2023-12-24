package fr.pentagone.akcess.repository.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Application extends AbstractEntity<Integer>{

    private String label;
    private String url;
    @OneToMany(mappedBy = Role_.APPLICATION)
    private List<Role> roles;

    public Application(){}
    public Application(String label, String url){
        this.url = url;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
