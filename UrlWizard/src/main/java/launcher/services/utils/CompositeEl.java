package launcher.services.utils;

public class CompositeEl {

	private Long counter;
	private String word;

	public CompositeEl(Long counter, String word) {

		this.counter = counter;
		this.word = word;
	}

	public Long getCounter() {
		return counter;
	}

	public String getWord() {
		return word;
	}
}
