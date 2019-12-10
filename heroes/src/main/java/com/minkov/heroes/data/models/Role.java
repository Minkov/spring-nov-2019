package com.minkov.heroes.data.models;

import com.minkov.heroes.data.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role
        extends BaseEntity
        implements GrantedAuthority {
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
