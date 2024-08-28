package staj.sigorta_uygulama_staj.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Integer policy_number;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_id")
    private Policy policy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date payment_date;
    private String card_number;
    private String card_owner;

    private String expiry_date;//AY YIL
    private String cvv;
}
