package in.co.rays.exception;
/**
 * RecordNotFoundException thrown when a record not found occurred.
 * 
 * @author Prabhakar Mandloi
 * 
 */
public class RecordNotFoundException extends Exception {
	public RecordNotFoundException(String msg){
		super(msg);
	}
}
