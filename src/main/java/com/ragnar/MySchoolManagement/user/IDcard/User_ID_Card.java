package com.ragnar.MySchoolManagement.user.IDcard;


import com.ragnar.MySchoolManagement.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "UserIdCard")
@Table(name = "user_id_card",
       uniqueConstraints = {
		       @UniqueConstraint(
		    		   name="user_card_number_unique" ,
		    		   columnNames = "card_Number"
		   )    		
        }	  
)
public class User_ID_Card {

	@Id
	@SequenceGenerator(
			name = "user_card_id_sequence",
			sequenceName =  "user_card_id_sequence",
			allocationSize = 1			
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator =  "user_card_id_sequence"
	)
	@Column(
			name = "card_id",
			updatable = false
	)
	private Long id;
	
	@Column(
			name = "card_number",
			nullable =   true,
			length = 15
	)
	private String cardNumber;

	
	 @OneToOne(fetch = FetchType.LAZY)
     @MapsId
     @JoinColumn(name = "id")
	private User user;
	
	
	

	public User_ID_Card(String cardNumber, User user) {
		super();
		this.cardNumber = cardNumber;
		this.user = user;
	}

	public User_ID_Card() {
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
}
