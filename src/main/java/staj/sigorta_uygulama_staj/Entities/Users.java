package staj.sigorta_uygulama_staj.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table
@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private String password;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)

    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    //ilişkiler
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Policy> policy;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Customer> customer;
}
