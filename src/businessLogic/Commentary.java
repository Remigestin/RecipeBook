package businessLogic;

import java.util.Calendar;

public class Commentary {

	public int idComment;
	public String text;
	public Calendar date;
	public int idUser;


	public Commentary(int idComment, Calendar cal, String text, int idUser) {
		this.idComment = idComment;
		this.idUser=idUser;
		this.date=cal;
		this.text = text;
	}


	public Commentary() {
		// TODO Auto-generated constructor stub
	}

	

	public Calendar getCal() {
		return date;
	}


	public void setCal(Calendar cal) {
		this.date = cal;
	}


	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


	public int getIdComment() {
		return idComment;
	}


	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}



}
