package fr.pentagone.akcess.repository.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import fr.pentagone.akcess.dto.RoleDTO;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends AbstractEntity<Integer>{
    private String name;
    @ManyToOne
    private Application application;
    public Role(){}

    public Role(String value, Application application){
        this.name = value;
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public Application getApplication() {
        return application;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(RoleDTO inputRole, Application application){
        setApplication(application);
        this.name = inputRole.label();
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
