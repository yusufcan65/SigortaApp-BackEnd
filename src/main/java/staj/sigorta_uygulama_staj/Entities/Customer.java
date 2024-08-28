package staj.sigorta_uygulama_staj.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String id_number;//tc kimlik numarası


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate birth_date;
    private String city;
    private String district;
    private String phone_number;
    private String email;

    private Integer customerNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;


    //ilşkiler
    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Policy> policy;



}
