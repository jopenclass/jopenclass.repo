package org.jopenclass.form;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="batch_schedule")
public class BatchSchedule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Temporal(TemporalType.TIME)
	private Date startTime;
	
	@Temporal(TemporalType.TIME)
	private Date endTiime;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Batch batch;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTiime() {
		return endTiime;
	}
	public void setEndTiime(Date endTiime) {
		this.endTiime = endTiime;
	}
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
