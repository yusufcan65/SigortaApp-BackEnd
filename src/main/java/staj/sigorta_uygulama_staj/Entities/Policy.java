package staj.sigorta_uygulama_staj.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer policyNumber;
    private Double prim;

    private Integer customerNumber;

    private String branch_code;
    private String status;
    private Integer remainderTime;

    private LocalDate tanzim_date; //teklifin başladığı tarih
    private LocalDate start_date; //teklifin poliçeleştiği tarih
    private LocalDate finish_date;//teklifin bittiği tarih

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(mappedBy = "policy")
    private Payment payment;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;


}
