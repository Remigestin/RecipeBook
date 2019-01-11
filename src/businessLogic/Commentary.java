package businessLogic;

public class Commentary {

	public int idComment;
	public String text;


	public Commentary(int idComment, String text) {
		this.idComment = idComment;
		this.text = text;
	}


	public Commentary() {
		// TODO Auto-generated constructor stub
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
