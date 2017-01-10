package com.peter.util.data.serializable.json.hibernate.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="items")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name = "name")
    private String name;


    @Type(type = "org.rayjars.hibernate.LabelUserType")
    @Column(name = "label")
    private Label label;

    public Item() {
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Label getLabel() {
        return label;
    }

    public Item label(Label label) {
        this.label = label;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }
}
