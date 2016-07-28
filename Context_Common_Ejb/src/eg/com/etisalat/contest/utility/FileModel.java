package eg.com.etisalat.contest.utility;

public class FileModel {

	private String filePath;

	private String fileName;

	public FileModel() {

	}

	public FileModel(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
